package pers.solid.extshape;

import com.google.common.collect.Iterables;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.apache.commons.lang3.stream.Streams;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * 包含检测合成表冲突的一些实用方法。因为扩展方块形状模组的合成表总是会存在冲突，用于检测合成表冲突的方法均置于此类。
 */
@ApiStatus.AvailableSince("1.5.2")
public final class RecipeConflict {
  private static final Logger LOGGER = LoggerFactory.getLogger(RecipeConflict.class);

  public static int checkConflict(RecipeManager recipeManager, World world, PlayerEntity player, Consumer<Supplier<Text>> messageConsumer) {
    int numberOfConflicts = 0;
    for (RecipeEntry<?> recipeEntry : recipeManager.values()) {
      final CraftingRecipeInput craftingRecipeInput;
      final Recipe<?> recipe = recipeEntry.value();
      try {
        if (recipe instanceof ShapedRecipe shapedRecipe && recipe.getClass() == ShapedRecipe.class) {
          final DefaultedList<Ingredient> ingredients = shapedRecipe.getIngredients();
          final int width = shapedRecipe.getWidth();
          final int height = shapedRecipe.getHeight();
          craftingRecipeInput = CraftingRecipeInput.create(width, height, ingredients.stream().map(ingredient -> {
            final ItemStack[] matchingStacks = ingredient.getMatchingStacks();
            return matchingStacks.length == 0 ? ItemStack.EMPTY : matchingStacks[0];
          }).toList());
        } else if (recipe instanceof ShapelessRecipe shapelessRecipe && recipe.getClass() == ShapelessRecipe.class) {
          final DefaultedList<Ingredient> ingredients = shapelessRecipe.getIngredients();
          craftingRecipeInput = CraftingRecipeInput.create(3, 3, Stream.concat(ingredients.stream().map(ingredient -> {
            final ItemStack[] matchingStacks = ingredient.getMatchingStacks();
            return matchingStacks.length == 0 ? ItemStack.EMPTY : matchingStacks[0];
          }), Streams.of(Iterables.cycle(ItemStack.EMPTY)).limit(9 - ingredients.size())).toList());
        } else {
          continue;
        }
        final List<RecipeEntry<CraftingRecipe>> allMatches = recipeManager.getAllMatches(RecipeType.CRAFTING, craftingRecipeInput, world);
        final long numberOfMatches = allMatches.stream().filter(r -> !r.value().getIngredients().isEmpty()).count();
        // 有些特殊合成表的材料是空的，在统计匹配次数时，应当予以忽略。
        if (numberOfMatches == 0) {
          for (int i = 0; i < 9; i++) {
            LOGGER.info(String.valueOf(craftingRecipeInput.getStackInSlot(i)));
          }
          messageConsumer.accept(() -> Text.translatable("message.extshape.recipe_conflict.unknown", recipeEntry.id().toString()).formatted(Formatting.RED));
        } else if (numberOfMatches > 1) {
          messageConsumer.accept(() -> Text.translatable("message.extshape.recipe_conflict.detected", Texts.join(allMatches, craftingRecipe -> Text.literal(craftingRecipe.id().toString()))).formatted(Formatting.RED));
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
        .requires(source -> source.hasPermissionLevel(4))
        .executes(context -> {
          final ServerCommandSource source = context.getSource();
          source.sendFeedback(() -> Text.translatable("message.extshape.recipe_conflict.start"), true);
          final ServerWorld world = source.getWorld();
          final ServerPlayerEntity player = source.getPlayerOrThrow();
          return checkConflict(world.getRecipeManager(), world, player, text -> source.sendFeedback(text, true));
        }));
  }

}
