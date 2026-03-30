package pers.solid.extshape.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.data.ExtShapeModelProvider;
import pers.solid.extshape.data.RecipeGroupRegistry;

import java.util.function.BiFunction;

/**
 * 该模组中的绝大多数方块共用的接口。
 */
public interface ExtShapeBlockInterface {
  /**
   * 方块的基础方块。由于 codec 不支持 null，因此本模组（包括在加入方块 codec 之前的版本）不允许 null 的基础方块。
   *
   * @return 方块的基础方块。
   */
  Block getBaseBlock();

  /**
   * 所有可以被切石的方块，包含其他模组中的。其他模组不应该对此集合进行任何与本模组无关的修改。
   */
  ObjectSet<Block> STONECUTTABLE_BASE_BLOCKS = new ObjectOpenHashSet<>();
  ObjectSet<Block> STONECUTTABLE_BLOCKS = new ObjectOpenHashSet<>();

  /**
   * 方块所在的合成配方的组。
   *
   * @return 方块合成配方中的 {@code group} 字段。
   * @see net.minecraft.data.recipes.RecipeBuilder#group(String)
   */
  default String getRecipeGroup() {
    return RecipeGroupRegistry.getRecipeGroup((ItemLike) this);
  }

  /**
   * 方块的切石配方。该方法在生成数据时，只有在确认了 {@link #isStoneCut(Block)} 的情况下才会被使用，因此该方法内部无需判断方块是否可以切石。
   *
   * @return 方块的切石配方。用于切石机。
   */
  default @Nullable SingleItemRecipeBuilder getStonecuttingRecipe(RecipeProvider recipeGenerator) {
    return null;
  }

  /**
   * 通过判断方块的材料是否为石头，来确定方块是否可以被切石。用于生成运行时数据。
   *
   * @param baseBlock 基础方块。
   * @return 方块能否被切石机切石。
   */
  static boolean isStoneCut(Block baseBlock) {
    return STONECUTTABLE_BASE_BLOCKS.contains(baseBlock);
  }

  default LootTable.Builder getLootTable(BlockLootSubProvider blockLootTableGenerator) {
    return blockLootTableGenerator.createSingleItemTable((ItemLike) this);
  }

  default @Nullable RecipeBuilder getCraftingRecipe(RecipeProvider recipeGenerator) {
    return null;
  }

  default boolean shouldWriteStonecuttingRecipe() {
    return (this instanceof Block && STONECUTTABLE_BLOCKS.contains(this)) || isStoneCut(getBaseBlock());
  }

  default SingleItemRecipeBuilder simpleStoneCuttingRecipe(int resultCount, RecipeProvider recipeGenerator) {
    final Block baseBlock = getBaseBlock();
    return SingleItemRecipeBuilder.stonecutting(Ingredient.of(baseBlock), getRecipeCategory(), (ItemLike) this, resultCount).unlockedBy("has_base_block", recipeGenerator.has(baseBlock));
  }

  /**
   * 获取当前方块所属的形状。{@link BlockShape#getShapeOf(Block)} 方法会尝试先调用此方法，如果方块不在此接口，则使用其内在的方法判断。
   *
   * @return 方块所属的形状。
   * @implNote 实现了此类的必须适当返回。如果返回 {@code null}，那么即使 {@link BlockShape#getShapeOf} 找得到对应类的形状对象，也不会再去查找了。
   */
  @Contract(pure = true)
  default @Nullable BlockShape getBlockShape() {
    return null;
  }

  default RecipeCategory getRecipeCategory() {
    final BlockShape blockShape = getBlockShape();
    if (blockShape == null || blockShape.isConstruction) {
      return RecipeCategory.BUILDING_BLOCKS;
    } else if (blockShape == BlockShape.FENCE || blockShape == BlockShape.WALL) {
      return RecipeCategory.DECORATIONS;
    } else if (blockShape == BlockShape.FENCE_GATE || blockShape == BlockShape.PRESSURE_PLATE || blockShape == BlockShape.BUTTON) {
      return RecipeCategory.REDSTONE;
    } else {
      return RecipeCategory.MISC;
    }
  }

  @Environment(EnvType.CLIENT)
  void registerModel(ExtShapeModelProvider modelProvider, BlockModelGenerators blockStateModelGenerator);

  default void registerRecipes(RecipeProvider recipeGenerator, RecipeOutput exporter) {
    final RecipeBuilder craftingRecipe = getCraftingRecipe(recipeGenerator);
    if (craftingRecipe != null) {
      craftingRecipe.save(exporter);
    }
    if (shouldWriteStonecuttingRecipe()) {
      final SingleItemRecipeBuilder stonecuttingRecipe = getStonecuttingRecipe(recipeGenerator);
      if (stonecuttingRecipe != null) {
        stonecuttingRecipe.save(exporter, ResourceKey.create(Registries.RECIPE, BuiltInRegistries.ITEM.getKey(stonecuttingRecipe.getResult()).withSuffix("_from_stonecutting")));
      }
    }
  }

  static <B extends Block & ExtShapeBlockInterface> MapCodec<B> createCodecWithBaseBlock(RecordCodecBuilder<B, BlockBehaviour.Properties> settings, BiFunction<Block, BlockBehaviour.Properties, B> function) {
    return RecordCodecBuilder.mapCodec(i -> i.group(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("base_block").forGetter(ExtShapeBlockInterface::getBaseBlock), settings).apply(i, function));
  }
}
