package pers.solid.extshape;

import com.google.common.collect.Iterables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.CommandSource;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
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
  public static int checkConflict(ServerRecipeManager recipeManager, World world, @Nullable Predicate<Identifier> filter, Consumer<Supplier<Text>> messageConsumer) {
    int numberOfConflicts = 0;
    for (RecipeEntry<?> recipeEntry : recipeManager.values()) {
      if (filter != null) {
        if (!filter.test(recipeEntry.id().getValue())) {
          continue;
        }
      }
      final CraftingRecipeInput craftingRecipeInput;
      final Recipe<?> recipe = recipeEntry.value();
      try {
        if (recipe instanceof ShapedRecipe shapedRecipe && recipe.getClass() == ShapedRecipe.class) {
          final List<Optional<Ingredient>> ingredients = shapedRecipe.getIngredients();
          final int width = shapedRecipe.getWidth();
          final int height = shapedRecipe.getHeight();
          craftingRecipeInput = CraftingRecipeInput.create(width, height, ingredients.stream().map(ingredient -> ingredient
              .map(Ingredient::getMatchingItems)
              .flatMap(Stream::findFirst)
              .map(RegistryEntry::value)
              .map(ItemStack::new)
              .orElse(ItemStack.EMPTY)).toList());
        } else if (recipe instanceof ShapelessRecipe shapelessRecipe && recipe.getClass() == ShapelessRecipe.class) {
          final List<Ingredient> ingredients = shapelessRecipe.getIngredientPlacement().getIngredients();
          craftingRecipeInput = CraftingRecipeInput.create(3, 3, Stream.concat(ingredients.stream()
              .map(Ingredient::getMatchingItems)
              .map(Stream::findFirst)
              .flatMap(Optional::stream)
              .map(RegistryEntry::value)
              .map(ItemStack::new), Streams.of(Iterables.cycle(ItemStack.EMPTY)).limit(9 - ingredients.size())).toList());
        } else {
          continue;
        }
        final List<RecipeEntry<?>> allMatches = recipeManager.values().stream().filter((entry) -> {
          final Recipe<?> value = entry.value();
          return value instanceof CraftingRecipe craftingRecipe && craftingRecipe.matches(craftingRecipeInput, world);
        }).toList();
        final long numberOfMatches = allMatches.stream().filter(entry -> filter == null || filter.test(entry.id().getValue())).filter(r -> !r.value().getIngredientPlacement().getIngredients().isEmpty()).count();
        // 有些特殊合成表的材料是空的，在统计匹配次数时，应当予以忽略。
        if (numberOfMatches == 0) {
          for (int i = 0; i < 9; i++) {
            LOGGER.info(String.valueOf(craftingRecipeInput.getStackInSlot(i)));
          }
          messageConsumer.accept(() -> Text.translatable("message.extshape.recipe_conflict.unknown", recipeEntry.id().getValue().toString()).formatted(Formatting.RED));
        } else if (numberOfMatches > 1) {
          messageConsumer.accept(() -> Text.translatable("message.extshape.recipe_conflict.detected", Texts.join(allMatches, craftingRecipe -> Text.literal(craftingRecipe.id().getValue().toString()))).formatted(Formatting.RED));
          ++numberOfConflicts;

        }
      } catch (Exception exception) {
        messageConsumer.accept(() -> Text.translatable("message.extshape.recipe_conflict.exception"));
        LOGGER.error("Unknown exception when testing recipe duplication: ", exception);
        break;
      }
    }
    int finalNumberOfConflicts = numberOfConflicts;
    messageConsumer.accept(() -> Text.translatable(finalNumberOfConflicts == 0 ? "message.extshape.recipe_conflict.finish.none" : finalNumberOfConflicts == 1 ? "message.extshape.recipe_conflict.finish.single" : "message.extshape.recipe_conflict.finish.plural", Integer.toString(finalNumberOfConflicts)));
    return numberOfConflicts;
  }

  /**
   * 注册用于检测合成表冲突的命令 {@code /extshape:check-conflict}。此命令只有由服主执行，执行时可能会花费一段时间。
   */
  public static void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
    dispatcher.register(CommandManager.literal("extshape:check-conflict")
        .requires(CommandManager.requirePermissionLevel(CommandManager.OWNERS_CHECK))
        .executes(context -> executeCheckConflict(context, null))
        .then(CommandManager.argument("namespace", StringArgumentType.greedyString())
            .suggests((context, builder) -> {
              final Matcher matcher = SPLIT_PATTERN.matcher(builder.getRemaining());
              int offset = 0;
              while (matcher.find()) {
                offset = matcher.end();
              }
              return CommandSource.suggestMatching(context.getSource().getRegistryManager().getOrThrow(RegistryKeys.RECIPE).streamKeys().map(RegistryKey::getValue).map(Identifier::getNamespace).distinct(), builder.createOffset(builder.getStart() + offset));
            })
            .executes(context -> executeCheckConflict(context, StringArgumentType.getString(context, "namespace")))));
  }

  private static final Pattern SPLIT_PATTERN = Pattern.compile("\\s+");

  private static int executeCheckConflict(CommandContext<ServerCommandSource> context, @Nullable String namespace) throws CommandSyntaxException {
    final ServerCommandSource source = context.getSource();
    source.sendFeedback(() -> Text.translatable("message.extshape.recipe_conflict.start"), true);
    final ServerWorld world = source.getWorld();
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
    return checkConflict(world.getRecipeManager(), world, predicate, text -> source.sendFeedback(text, true));
  }

}
