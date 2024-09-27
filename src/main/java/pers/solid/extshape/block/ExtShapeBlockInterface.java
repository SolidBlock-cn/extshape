package pers.solid.extshape.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.StonecuttingRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.loot.LootTable;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
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
  @NotNull
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
   * @see net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder#group(String)
   */
  default String getRecipeGroup() {
    return RecipeGroupRegistry.getRecipeGroup((ItemConvertible) this);
  }

  /**
   * 方块的切石配方。该方法在生成数据时，只有在确认了 {@link #isStoneCut(Block)} 的情况下才会被使用，因此该方法内部无需判断方块是否可以切石。
   *
   * @return 方块的切石配方。用于切石机。
   */
  default @Nullable StonecuttingRecipeJsonBuilder getStonecuttingRecipe() {
    return null;
  }

  /**
   * 通过判断方块的材料是否为石头，来确定方块是否可以被切石。用于生成运行时数据。
   *
   * @param baseBlock 基础方块。
   * @return 方块能否被切石机切石。
   */
  static boolean isStoneCut(Block baseBlock) {
    return baseBlock != null && STONECUTTABLE_BASE_BLOCKS.contains(baseBlock);
  }

  default LootTable.Builder getLootTable(BlockLootTableGenerator blockLootTableGenerator) {
    return blockLootTableGenerator.drops((ItemConvertible) this);
  }

  default CraftingRecipeJsonBuilder getCraftingRecipe() {
    return null;
  }

  default boolean shouldWriteStonecuttingRecipe() {
    return (this instanceof Block && STONECUTTABLE_BLOCKS.contains(this)) || isStoneCut(getBaseBlock());
  }

  default StonecuttingRecipeJsonBuilder simpleStoneCuttingRecipe(int resultCount) {
    final Block baseBlock = getBaseBlock();
    return StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(baseBlock), getRecipeCategory(), (ItemConvertible) this, resultCount).criterion("has_base_block", RecipeProvider.conditionsFromItem(baseBlock));
  }

  /**
   * 获取当前方块所属的形状。{@link BlockShape#getShapeOf(Block)} 方法会尝试先调用此方法，如果方块不在此接口，则使用其内在的方法判断。
   *
   * @return 方块所属的形状。
   * @implNote 实现了此类的必须适当返回。如果返回 {@code null}，那么即使 {@link BlockShape#getShapeOf} 找得到对应类的形状对象，也不会再去查找了。
   */
  @Contract(pure = true)
  default BlockShape getBlockShape() {
    return null;
  }

  default @Nullable RecipeCategory getRecipeCategory() {
    final BlockShape blockShape = getBlockShape();
    if (blockShape.isConstruction) {
      return RecipeCategory.BUILDING_BLOCKS;
    } else if (blockShape == BlockShape.FENCE || blockShape == BlockShape.WALL) {
      return RecipeCategory.DECORATIONS;
    } else if (blockShape == BlockShape.FENCE_GATE || blockShape == BlockShape.PRESSURE_PLATE || blockShape == BlockShape.BUTTON) {
      return RecipeCategory.REDSTONE;
    } else {
      return null;
    }
  }

  void registerModel(ExtShapeModelProvider modelProvider, BlockStateModelGenerator blockStateModelGenerator);

  default void registerRecipes(RecipeExporter exporter) {
    final CraftingRecipeJsonBuilder craftingRecipe = getCraftingRecipe();
    if (craftingRecipe != null) {
      craftingRecipe.offerTo(exporter);
    }
    if (shouldWriteStonecuttingRecipe()) {
      final StonecuttingRecipeJsonBuilder stonecuttingRecipe = getStonecuttingRecipe();
      if (stonecuttingRecipe != null) {
        stonecuttingRecipe.offerTo(exporter, Registries.ITEM.getId(stonecuttingRecipe.getOutputItem()).withSuffixedPath("_from_stonecutting"));
      }
    }
  }

  static <B extends Block & ExtShapeBlockInterface> MapCodec<B> createCodecWithBaseBlock(RecordCodecBuilder<B, AbstractBlock.Settings> settings, BiFunction<Block, AbstractBlock.Settings, B> function) {
    return RecordCodecBuilder.mapCodec(i -> i.group(Registries.BLOCK.getCodec().fieldOf("base_block").forGetter(ExtShapeBlockInterface::getBaseBlock), settings).apply(i, function));
  }
}
