package pers.solid.extshape;

import com.google.common.collect.Iterables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.stream.Streams;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * 检测合成表冲突的一些实用方法。因为扩展方块形状模组的合成表很容易出现冲突，因此用于检测合成表冲突的方法均置于此类。
 */
@ApiStatus.AvailableSince("1.5.2")
public final class RecipeConflict {
  private static final Logger LOGGER = LoggerFactory.getLogger(RecipeConflict.class);

  /**
   * 检测合成表中的冲突。
   *
   * @return 冲突的个数。
   */
  @SuppressWarnings("deprecation")
  public static int checkConflict(RecipeManager recipeManager, Level world, @Nullable Predicate<Identifier> filter, Consumer<Supplier<Component>> messageConsumer) {
    int numberOfConflicts = 0;
    for (RecipeHolder<?> recipeEntry : recipeManager.getRecipes()) {
      if (filter != null) {
        if (!filter.test(recipeEntry.id().identifier())) {
          continue;
        }
      }
      final CraftingInput craftingRecipeInput;
      final Recipe<?> recipe = recipeEntry.value();
      try {
        if (recipe instanceof ShapedRecipe shapedRecipe && recipe.getClass() == ShapedRecipe.class) {
          final List<Optional<Ingredient>> ingredients = shapedRecipe.getIngredients();
          final int width = shapedRecipe.getWidth();
          final int height = shapedRecipe.getHeight();
          craftingRecipeInput = CraftingInput.of(width, height, ingredients.stream().map(ingredient -> ingredient
              .map(Ingredient::items)
              .flatMap(Stream::findFirst)
              .map(Holder::value)
              .map(ItemStack::new)
              .orElse(ItemStack.EMPTY)).toList());
        } else if (recipe instanceof ShapelessRecipe shapelessRecipe && recipe.getClass() == ShapelessRecipe.class) {
          final List<Ingredient> ingredients = shapelessRecipe.placementInfo().ingredients();
          craftingRecipeInput = CraftingInput.of(3, 3, Stream.concat(ingredients.stream()
              .map(Ingredient::items)
              .map(Stream::findFirst)
              .flatMap(Optional::stream)
              .map(Holder::value)
              .map(ItemStack::new), Streams.of(Iterables.cycle(ItemStack.EMPTY)).limit(9 - ingredients.size())).toList());
        } else {
          continue;
        }
        final List<RecipeHolder<?>> allMatches = recipeManager.getRecipes().stream().filter((entry) -> {
          final Recipe<?> value = entry.value();
          return value instanceof CraftingRecipe craftingRecipe && craftingRecipe.matches(craftingRecipeInput, world);
        }).toList();
        final long numberOfMatches = allMatches.stream().filter(entry -> filter == null || filter.test(entry.id().identifier())).filter(r -> !r.value().placementInfo().ingredients().isEmpty()).count();
        // 有些特殊合成表的材料是空的，在统计匹配次数时，应当予以忽略。
        if (numberOfMatches == 0) {
          for (int i = 0; i < 9; i++) {
            LOGGER.info(String.valueOf(craftingRecipeInput.getItem(i)));
          }
          messageConsumer.accept(() -> Component.translatable("message.extshape.recipe_conflict.unknown", recipeEntry.id().identifier().toString()).withStyle(ChatFormatting.RED));
        } else if (numberOfMatches > 1) {
          messageConsumer.accept(() -> Component.translatable("message.extshape.recipe_conflict.detected", ComponentUtils.formatList(allMatches, craftingRecipe -> Component.literal(craftingRecipe.id().identifier().toString()))).withStyle(ChatFormatting.RED));
          ++numberOfConflicts;

        }
      } catch (Exception exception) {
        messageConsumer.accept(() -> Component.translatable("message.extshape.recipe_conflict.exception"));
        LOGGER.error("Unknown exception when testing recipe duplication: ", exception);
        break;
      }
    }
    int finalNumberOfConflicts = numberOfConflicts;
    messageConsumer.accept(() -> Component.translatable(finalNumberOfConflicts == 0 ? "message.extshape.recipe_conflict.finish.none" : finalNumberOfConflicts == 1 ? "message.extshape.recipe_conflict.finish.single" : "message.extshape.recipe_conflict.finish.plural", Integer.toString(finalNumberOfConflicts)));
    return numberOfConflicts;
  }

  /**
   * 注册用于检测合成表冲突的命令 {@code /extshape:check-conflict}。此命令只有由服主执行，执行时可能会花费一段时间。
   */
  public static void registerCommand(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registryAccess, Commands.CommandSelection environment) {
    dispatcher.register(Commands.literal("extshape:check-conflict")
        .requires(Commands.hasPermission(Commands.LEVEL_OWNERS))
        .executes(context -> executeCheckConflict(context, null))
        .then(Commands.argument("namespace", StringArgumentType.greedyString())
            .suggests((context, builder) -> {
              final Matcher matcher = SPLIT_PATTERN.matcher(builder.getRemaining());
              int offset = 0;
              while (matcher.find()) {
                offset = matcher.end();
              }
              return SharedSuggestionProvider.suggest(context.getSource().registryAccess().lookupOrThrow(Registries.RECIPE).listElementIds().map(ResourceKey::identifier).map(Identifier::getNamespace).distinct(), builder.createOffset(builder.getStart() + offset));
            })
            .executes(context -> executeCheckConflict(context, StringArgumentType.getString(context, "namespace")))));
  }

  private static final Pattern SPLIT_PATTERN = Pattern.compile("\\s+");

  private static int executeCheckConflict(CommandContext<CommandSourceStack> context, @Nullable String namespace) throws CommandSyntaxException {
    final CommandSourceStack source = context.getSource();
    source.sendSuccess(() -> Component.translatable("message.extshape.recipe_conflict.start"), true);
    final ServerLevel world = source.getLevel();
    final Predicate<Identifier> predicate;
    if (namespace == null) {
      predicate = null;
    } else {
      final String[] split = SPLIT_PATTERN.split(namespace);
      if (split.length == 1) {
        final String s = split[0];
        predicate = identifier -> Identifier.DEFAULT_NAMESPACE.equals(identifier.getNamespace()) || s.equals(identifier.getNamespace());
      } else {
        predicate = identifier -> ArrayUtils.contains(split, identifier.getNamespace());
      }
    }
    return checkConflict(world.recipeAccess(), world, predicate, text -> source.sendSuccess(text, true));
  }

}
