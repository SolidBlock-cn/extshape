package pers.solid.extshape.data;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.Iterables;
import it.unimi.dsi.fastutil.objects.ObjectIntPair;
import net.minecraft.block.Block;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.mixin.SingleItemRecipeJsonBuilderAccessor;
import pers.solid.extshape.util.BlockBiMaps;

import java.util.Collections;
import java.util.stream.Collectors;


/**
 * 处理同一方块不同形状的数据生成的类。只涉及基本的几种形状，且不涉及方块与基础方块之间的转化。
 */
public class CrossShapeDataGeneration {
  /**
   * 基础方块。
   */
  public final @NotNull Block baseBlock;
  public final @Nullable String defaultNamespace;
  public final @NotNull RecipeExporter exporter;
  /**
   * 是否启用同种形状之间的方块切石功能，例如将非切制的楼梯切成已切制的横条。如果未启用，则只能将非切制的楼梯切成非切制的横条，但是基础方块仍不在此限。将此设置为 {@code false} 是为了与原版的行为相匹配。
   */
  public final boolean enableCuttingShape = false;

  public CrossShapeDataGeneration(@NotNull Block baseBlock, @Nullable String defaultNamespace, @NotNull RecipeExporter exporter) {
    this.baseBlock = baseBlock;
    this.defaultNamespace = defaultNamespace;
    this.exporter = exporter;
  }

  public RecipeCategory getRecipeCategory() {
    return RecipeCategory.BUILDING_BLOCKS;
  }

  public Identifier recipeIdOf(ItemConvertible item, String suffix) {
    final Identifier identifier = CraftingRecipeJsonBuilder.getItemId(item);
    return new Identifier(defaultNamespace == null ? identifier.getNamespace() : defaultNamespace, suffix == null ? identifier.getPath() : identifier.getPath() + suffix);
  }

  public void slabToVerticalSlab(final @NotNull Block slab, final @NotNull Block verticalSlab) {
    writeBlockRotationRecipe(slab, verticalSlab, null, "has_slab");
  }

  public void verticalSlabToSlab(final @NotNull Block verticalSlab, final @NotNull Block slab) {
    writeBlockRotationRecipe(verticalSlab, slab, "_from_vertical_slab", "has_vertical_slab");
  }

  public void stairsToVerticalStairs(final @NotNull Block stairs, final @NotNull Block verticalStairs) {
    writeBlockRotationRecipe(stairs, verticalStairs, null, "has_stairs");
  }

  public void verticalStairsToStairs(final @NotNull Block verticalStairs, final @NotNull Block stairs) {
    writeBlockRotationRecipe(verticalStairs, stairs, "_from_vertical_stairs", "has_vertical_stairs");
  }

  public void quarterPieceToVerticalQuarterPiece(final @NotNull Block quarterPiece, final @NotNull Block verticalQuarterPiece) {
    writeBlockRotationRecipe(quarterPiece, verticalQuarterPiece, null, "has_quarter_piece");
  }

  public void verticalQuarterPieceToQuarterPiece(final @NotNull Block verticalQuarterPiece, final @NotNull Block quarterPiece) {
    writeBlockRotationRecipe(verticalQuarterPiece, quarterPiece, "_from_vertical_quarter_piece", "has_vertical_quarter_piece");
  }

  protected void writeBlockRotationRecipe(final @NotNull Block from, final @NotNull Block to, @Nullable String suffix, String criterionName) {
    final Identifier recipeId = recipeIdOf(to, suffix);
    final ShapelessRecipeJsonBuilder recipe = ShapelessRecipeJsonBuilder.create(getRecipeCategory(), to).input(Ingredient.ofItems(from));
    final String recipeGroup = RecipeGroupRegistry.getRecipeGroup(to);
    recipe.group(StringUtils.isEmpty(recipeGroup) ? recipeGroup : recipeGroup + "_from_rotation").criterion(criterionName, RecipeProvider.conditionsFromItem(from));
    recipe.offerTo(exporter, recipeId);
  }

  public void cutStairsToQuarterPiece(final @NotNull Block stairs, final @NotNull Block quarterPiece, @Nullable String suffix, int scale) {
    generateSimpleStonecuttingRecipe(stairs, quarterPiece, 3 * scale, suffix == null ? "_from_stairs_stonecutting" : suffix, "has_stairs");
  }

  public void craftSlabToQuarterPiece(final @NotNull Block slab, final @NotNull Block quarterPiece, @Nullable String suffix) {
    final Identifier recipeId = recipeIdOf(quarterPiece, suffix == null ? "_from_slab" : suffix);
    final String recipeGroup = RecipeGroupRegistry.getRecipeGroup(quarterPiece);
    final ShapedRecipeJsonBuilder recipe = ShapedRecipeJsonBuilder.create(getRecipeCategory(), quarterPiece, 6)
        .pattern("###")
        .input('#', slab)
        .group(StringUtils.isEmpty(recipeGroup) ? recipeGroup : recipeGroup + "_from_slab")
        .criterion("has_slab", RecipeProvider.conditionsFromItem(slab));
    recipe.offerTo(exporter, recipeId);
  }

  public void cutSlabToQuarterPiece(final @NotNull Block slab, final @NotNull Block quarterPiece, @Nullable String suffix, int scale) {
    generateSimpleStonecuttingRecipe(slab, quarterPiece, 2 * scale, suffix == null ? "_from_slab_stonecutting" : suffix, "has_slab");
  }

  public void craftVerticalSlabToQuarterPiece(final @NotNull Block verticalSlab, final @NotNull Block quarterPiece, @Nullable String suffix) {
    final Identifier recipeId = recipeIdOf(quarterPiece, suffix == null ? "_from_vertical_slab" : suffix);
    final String recipeGroup = RecipeGroupRegistry.getRecipeGroup(quarterPiece);
    final ShapedRecipeJsonBuilder recipe = ShapedRecipeJsonBuilder.create(getRecipeCategory(), quarterPiece, 6)
        .pattern("###")
        .input('#', verticalSlab)
        .group(StringUtils.isEmpty(recipeGroup) ? recipeGroup : recipeGroup + "_from_vertical_slab")
        .criterion("has_vertical_slab", RecipeProvider.conditionsFromItem(verticalSlab));
    recipe.offerTo(exporter, recipeId);
  }

  public void cutVerticalSlabToQuarterPiece(final @NotNull Block verticalSlab, final @NotNull Block quarterPiece, @Nullable String suffix, int scale) {
    generateSimpleStonecuttingRecipe(verticalSlab, quarterPiece, 2 * scale, suffix == null ? "_from_vertical_slab_stonecutting" : suffix, "has_vertical_slab");
  }

  public void craftVerticalSlabToVerticalQuarterPiece(final @NotNull Block verticalSlab, final @NotNull Block verticalQuarterPiece, @Nullable String suffix) {
    final Identifier recipeId = recipeIdOf(verticalQuarterPiece, suffix == null ? "_from_vertical_slab" : suffix);
    final String recipeGroup = RecipeGroupRegistry.getRecipeGroup(verticalQuarterPiece);
    final ShapedRecipeJsonBuilder recipe = ShapedRecipeJsonBuilder.create(getRecipeCategory(), verticalQuarterPiece, 6)
        .pattern("#").pattern("#").pattern("#")
        .input('#', verticalSlab)
        .group(StringUtils.isEmpty(recipeGroup) ? recipeGroup : recipeGroup + "_from_vertical_slab")
        .criterion("has_vertical_slab", RecipeProvider.conditionsFromItem(verticalSlab));
    recipe.offerTo(exporter, recipeId);
  }

  public void cutVerticalSlabToVerticalQuarterPiece(final @NotNull Block verticalSlab, final @NotNull Block verticalQuarterPiece, @Nullable String suffix, int scale) {
    generateSimpleStonecuttingRecipe(verticalSlab, verticalQuarterPiece, 2 * scale, suffix == null ? "_from_vertical_slab_stonecutting" : suffix, "has_vertical_slab");
  }

  public void cutVerticalStairsToVerticalQuarterPiece(final @NotNull Block verticalStairs, final @NotNull Block verticalQuarterPiece, @Nullable String suffix, int scale) {
    generateSimpleStonecuttingRecipe(verticalStairs, verticalQuarterPiece, 3 * scale, suffix == null ? "_from_vertical_stairs_stonecutting" : suffix, "has_vertical_stairs");
  }

  protected void generateSimpleStonecuttingRecipe(
      ItemConvertible ingredient,
      ItemConvertible result,
      int count,
      @Nullable String suffix,
      String criterionName
  ) {
    if (ingredient == null || result == null) return;
    final Identifier recipeId = recipeIdOf(result, suffix);
    final SingleItemRecipeJsonBuilder recipe = SingleItemRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(ingredient), getRecipeCategory(), result, count)
        .group(RecipeGroupRegistry.getRecipeGroup(result))
        .criterion(criterionName, RecipeProvider.conditionsFromItem(ingredient));
    recipe.offerTo(exporter, recipeId);
  }

  public void generateCrossShapeData() {
    final @NotNull Iterable<ObjectIntPair<Block>> uncutBaseBlocks = getUncutBaseBlocks();

    // 台阶与垂直台阶之间的配方。
    final @Nullable Block slab = BlockBiMaps.getBlockOf(BlockShape.SLAB, baseBlock);
    final @Nullable Block verticalSlab = BlockBiMaps.getBlockOf(BlockShape.VERTICAL_SLAB, baseBlock);
    if (slab != null && verticalSlab != null) {
      slabToVerticalSlab(slab, verticalSlab);
      verticalSlabToSlab(verticalSlab, slab);
    }

    // 楼梯与垂直楼梯之间的配方。
    final @Nullable Block stairs = BlockBiMaps.getBlockOf(BlockShape.STAIRS, baseBlock);
    final @Nullable Block verticalStairs = BlockBiMaps.getBlockOf(BlockShape.VERTICAL_STAIRS, baseBlock);
    if (stairs != null && verticalStairs != null) {
      stairsToVerticalStairs(stairs, verticalStairs);
      verticalStairsToStairs(verticalStairs, stairs);
    }

    // 横条与纵条之间的配方。
    final @Nullable Block quarterPiece = BlockBiMaps.getBlockOf(BlockShape.QUARTER_PIECE, baseBlock);
    final @Nullable Block verticalQuarterPiece = BlockBiMaps.getBlockOf(BlockShape.VERTICAL_QUARTER_PIECE, baseBlock);
    if (quarterPiece != null && verticalQuarterPiece != null) {
      quarterPieceToVerticalQuarterPiece(quarterPiece, verticalQuarterPiece);
      verticalQuarterPieceToQuarterPiece(verticalQuarterPiece, quarterPiece);
    }

    // 该方块是否允许被切石
    final boolean shouldStoneCut = ExtShapeBlockInterface.isStoneCut(baseBlock);

    // 跨方块切石
    for (final BlockShape blockShape : BlockShape.values()) {
      for (final var uncutBaseBlockInfo : uncutBaseBlocks) {
        final Block uncutBaseBlock = uncutBaseBlockInfo.left();
        final String path = Registries.BLOCK.getId(uncutBaseBlock).getPath();
        final Block output = BlockBiMaps.getBlockOf(blockShape, baseBlock);
        if (!(output instanceof ExtShapeBlockInterface) || !((ExtShapeBlockInterface) output).shouldWriteStonecuttingRecipe()) continue;
        SingleItemRecipeJsonBuilder recipe = ((ExtShapeBlockInterface) output).getStonecuttingRecipe();
        if (recipe != null) {
          final Identifier secondaryId = CraftingRecipeJsonBuilder.getItemId(output).withSuffixedPath("_from_" + path + "_stonecutting");
          SingleItemRecipeJsonBuilderAccessor accessor = (SingleItemRecipeJsonBuilderAccessor) recipe;
          final SingleItemRecipeJsonBuilder secondaryRecipe = SingleItemRecipeJsonBuilder.createStonecutting(
              Ingredient.ofItems(uncutBaseBlock),
              accessor.getCategory(),
              accessor.getOutput(),
              accessor.getCount() * uncutBaseBlockInfo.rightInt()
          ).criterion("has_" + path, RecipeProvider.conditionsFromItem(uncutBaseBlock));
          secondaryRecipe.offerTo(exporter, secondaryId);
        }
      }
    }

    if (quarterPiece != null) {
      // 1x楼梯 -> 3x横条
      if (stairs != null && shouldStoneCut) {
        cutStairsToQuarterPiece(stairs, quarterPiece, null, 1);
        if (enableCuttingShape) for (final var uncutBaseBlockInfo : uncutBaseBlocks) {
          final Block uncutBaseBlock = uncutBaseBlockInfo.left();
          final Block uncutStairs = BlockBiMaps.getBlockOf(BlockShape.STAIRS, uncutBaseBlock);
          if (uncutStairs == null) continue;
          final String name0 = Registries.BLOCK.getId(uncutStairs).getPath();
          cutStairsToQuarterPiece(uncutStairs, quarterPiece, "_from_" + name0 + "_stonecutting", uncutBaseBlockInfo.rightInt());
        }
      }

      // 1x台阶 -> 2x横条
      if (slab != null) {
        craftSlabToQuarterPiece(slab, quarterPiece, null);
        if (shouldStoneCut) {
          cutSlabToQuarterPiece(slab, quarterPiece, null, 1);
          if (enableCuttingShape) for (final var uncutBaseBlockInfo : uncutBaseBlocks) {
            final Block uncutBaseBlock = uncutBaseBlockInfo.left();
            final Block uncutSlab = BlockBiMaps.getBlockOf(BlockShape.SLAB, uncutBaseBlock);
            if (uncutSlab == null) continue;
            final String name0 = Registries.BLOCK.getId(uncutSlab).getPath();
            cutSlabToQuarterPiece(uncutSlab, quarterPiece, "_from_" + name0 + "_stonecutting", uncutBaseBlockInfo.rightInt());
          }
        }
      }

      // 1x纵台阶 -> 2x横条
      if (verticalSlab != null) {
        craftVerticalSlabToQuarterPiece(verticalSlab, quarterPiece, null);
        if (shouldStoneCut) {
          cutVerticalSlabToQuarterPiece(verticalSlab, quarterPiece, null, 1);
          if (enableCuttingShape) for (final var uncutBaseBlockInfo : uncutBaseBlocks) {
            final Block uncutBaseBlock = uncutBaseBlockInfo.left();
            final Block uncutVerticalSlab = BlockBiMaps.getBlockOf(BlockShape.VERTICAL_SLAB, uncutBaseBlock);
            if (uncutVerticalSlab == null) continue;
            cutVerticalSlabToQuarterPiece(uncutVerticalSlab, quarterPiece, "_from_" + Registries.BLOCK.getId(uncutVerticalSlab).getPath() + "_stonecutting", uncutBaseBlockInfo.rightInt());
          }
        }
      }
    }

    if (verticalQuarterPiece != null) {
      // 1x纵台阶 -> 2x纵条
      if (verticalSlab != null) {
        craftVerticalSlabToVerticalQuarterPiece(verticalSlab, verticalQuarterPiece, null);
        if (shouldStoneCut) {
          cutVerticalSlabToVerticalQuarterPiece(verticalSlab, verticalQuarterPiece, null, 1);
          if (enableCuttingShape) for (final var uncutBaseBlockInfo : uncutBaseBlocks) {
            final Block uncutBaseBlock = uncutBaseBlockInfo.left();
            final Block uncutVerticalSlab = BlockBiMaps.getBlockOf(BlockShape.VERTICAL_SLAB, uncutBaseBlock);
            if (uncutVerticalSlab == null) return;
            cutVerticalSlabToVerticalQuarterPiece(uncutVerticalSlab, verticalQuarterPiece, "_from_" + Registries.BLOCK.getId(uncutVerticalSlab).getPath() + "_stonecutting", uncutBaseBlockInfo.rightInt());
          }
        }
      }

      // 1x纵楼梯 -> 3x纵条
      if (verticalStairs != null && shouldStoneCut) {
        cutVerticalStairsToVerticalQuarterPiece(verticalStairs, verticalQuarterPiece, null, 1);
        if (enableCuttingShape) for (final var uncutBaseBlockInfo : uncutBaseBlocks) {
          final Block uncutBaseBlock = uncutBaseBlockInfo.left();
          final Block uncutVerticalStairs = BlockBiMaps.getBlockOf(BlockShape.VERTICAL_STAIRS, uncutBaseBlock);
          if (uncutVerticalStairs == null) continue;
          cutVerticalStairsToVerticalQuarterPiece(uncutVerticalStairs, verticalQuarterPiece, "_from_" + Registries.BLOCK.getId(uncutVerticalStairs).getPath() + "_stonecutting", uncutBaseBlockInfo.rightInt());
        }
      }
    }
  }

  @NotNull
  protected Iterable<ObjectIntPair<Block>> getUncutBaseBlocks() {
    final ImmutableCollection<Block> blocks = VanillaStonecutting.INSTANCE.get(baseBlock);
    final ImmutableCollection<ObjectIntPair<Block>> weightedBlocks = VanillaStonecutting.INSTANCE_WITH_WEIGHT.get(baseBlock);
    return Iterables.concat(blocks.isEmpty() ? Collections.emptyList() : blocks.stream().map(block -> ObjectIntPair.of(block, 1)).collect(Collectors.toList()), weightedBlocks);
  }
}
