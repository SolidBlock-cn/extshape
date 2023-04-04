package pers.solid.extshape.rrp;

import com.google.common.collect.ImmutableCollection;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.generator.BlockResourceGenerator;
import net.devtech.arrp.generator.ResourceGeneratorHelper;
import net.devtech.arrp.json.recipe.*;
import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.util.BlockBiMaps;

import java.util.Collection;

import static net.devtech.arrp.generator.ResourceGeneratorHelper.getAdvancementIdForRecipe;

public class CrossShapeDataGeneration {
  public final Block baseBlock;
  public final @Nullable String defaultNamespace;
  public final RuntimeResourcePack pack;

  public CrossShapeDataGeneration(Block baseBlock, @Nullable String defaultNamespace, RuntimeResourcePack pack) {
    this.baseBlock = baseBlock;
    this.defaultNamespace = defaultNamespace;
    this.pack = pack;
  }

  public RecipeCategory getRecipeCategory() {
    return RecipeCategory.BUILDING_BLOCKS;
  }

  public Identifier recipeIdOf(ItemConvertible item, String suffix) {
    final Identifier identifier = ResourceGeneratorHelper.getRecipeId(item);
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
    final JShapelessRecipe recipe = new JShapelessRecipe(to, from);
    final String recipeGroup = getRecipeGroup(to);
    recipe.group(StringUtils.isEmpty(recipeGroup) ? recipeGroup : recipeGroup + "_from_rotation")
        .addInventoryChangedCriterion(criterionName, from)
        .recipeCategory(getRecipeCategory());
    pack.addRecipe(recipeId, recipe);
    pack.addRecipeAdvancement(recipeId, getAdvancementIdForRecipe(to, recipeId, recipe.recipeCategory), recipe);
  }

  @NotNull
  protected static String getRecipeGroup(@NotNull Block result) {
    return RecipeGroupRegistry.getRecipeGroup(result);
  }

  public void cutStairsToQuarterPiece(final @NotNull Block stairs, final @NotNull Block quarterPiece, @Nullable String suffix) {
    generateSimpleStonecuttingRecipe(stairs, quarterPiece, 3, suffix == null ? "_from_stairs_stonecutting" : suffix, "has_stairs");
  }

  public void craftSlabToQuarterPiece(final @NotNull Block slab, final @NotNull Block quarterPiece, @Nullable String suffix) {
    final Identifier recipeId = recipeIdOf(quarterPiece, suffix == null ? "_from_slab" : suffix);
    final String recipeGroup = getRecipeGroup(quarterPiece);
    final JShapedRecipe recipe = new JShapedRecipe(quarterPiece)
        .pattern("###")
        .addKey("#", slab)
        .resultCount(6)
        .group(StringUtils.isEmpty(recipeGroup) ? recipeGroup : recipeGroup + "_from_slab")
        .recipeCategory(getRecipeCategory());
    recipe.addInventoryChangedCriterion("has_slab", slab);
    pack.addRecipe(recipeId, recipe);
    pack.addRecipeAdvancement(recipeId, getAdvancementIdForRecipe(quarterPiece, recipeId, recipe), recipe);
  }

  public void cutSlabToQuarterPiece(final @NotNull Block slab, final @NotNull Block quarterPiece, @Nullable String suffix) {
    generateSimpleStonecuttingRecipe(slab, quarterPiece, 2, suffix == null ? "_from_slab_stonecutting" : suffix, "has_slab");
  }

  public void craftVerticalSlabToQuarterPiece(final @NotNull Block verticalSlab, final @NotNull Block quarterPiece, @Nullable String suffix) {
    final Identifier recipeId = recipeIdOf(quarterPiece, suffix == null ? "_from_vertical_slab" : suffix);
    final String recipeGroup = getRecipeGroup(quarterPiece);
    final JShapedRecipe recipe = new JShapedRecipe(quarterPiece)
        .pattern("###")
        .addKey("#", verticalSlab)
        .recipeCategory(getRecipeCategory())
        .group(StringUtils.isEmpty(recipeGroup) ? recipeGroup : recipeGroup + "_from_vertical_slab")
        .resultCount(6);
    recipe.addInventoryChangedCriterion("has_vertical_slab", verticalSlab);
    pack.addRecipe(recipeId, recipe);
    pack.addRecipeAdvancement(recipeId, getAdvancementIdForRecipe(quarterPiece, recipeId, recipe), recipe);
  }

  public void cutVerticalSlabToQuarterPiece(final @NotNull Block verticalSlab, final @NotNull Block quarterPiece, @Nullable String suffix) {
    generateSimpleStonecuttingRecipe(verticalSlab, quarterPiece, 2, suffix == null ? "_from_vertical_slab_stonecutting" : suffix, "has_vertical_slab");
  }

  public void craftVerticalSlabToVerticalQuarterPiece(final @NotNull Block verticalSlab, final @NotNull Block verticalQuarterPiece, @Nullable String suffix) {
    final Identifier recipeId = recipeIdOf(verticalQuarterPiece, suffix == null ? "_from_vertical_slab" : suffix);
    final String recipeGroup = getRecipeGroup(verticalQuarterPiece);
    final JShapedRecipe recipe = new JShapedRecipe(verticalQuarterPiece)
        .pattern("#", "#", "#")
        .addKey("#", verticalSlab)
        .recipeCategory(getRecipeCategory())
        .group(StringUtils.isEmpty(recipeGroup) ? recipeGroup : recipeGroup + "_from_vertical_slab")
        .resultCount(6);
    recipe.addInventoryChangedCriterion("has_vertical_slab", verticalSlab);
    pack.addRecipe(recipeId, recipe);
    pack.addRecipeAdvancement(recipeId, getAdvancementIdForRecipe(verticalQuarterPiece, recipeId, recipe), recipe);
  }

  public void cutVerticalSlabToVerticalQuarterPiece(final @NotNull Block verticalSlab, final @NotNull Block verticalQuarterPiece, @Nullable String suffix) {
    generateSimpleStonecuttingRecipe(verticalSlab, verticalQuarterPiece, 2, suffix == null ? "_from_vertical_slab_stonecutting" : suffix, "has_vertical_slab");
  }

  public void cutVerticalStairsToVerticalQuarterPiece(final @NotNull Block verticalStairs, final @NotNull Block verticalQuarterPiece, @Nullable String suffix) {
    generateSimpleStonecuttingRecipe(verticalStairs, verticalQuarterPiece, 3, suffix == null ? "_from_vertical_stairs_stonecutting" : suffix, "has_vertical_stairs");
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
    final JStonecuttingRecipe recipe = new JStonecuttingRecipe(ingredient, result, count).recipeCategory(RecipeCategory.BUILDING_BLOCKS).group(RecipeGroupRegistry.getRecipeGroup(result));
    recipe.addInventoryChangedCriterion(criterionName, ingredient);
    pack.addRecipe(recipeId, recipe);
    pack.addRecipeAdvancement(recipeId, getAdvancementIdForRecipe(result, recipeId, recipe.recipeCategory), recipe);
  }

  protected boolean shouldStoneCut(final @NotNull Block baseBlock) {
    return ExtShapeBlockInterface.isStoneCut(baseBlock);
  }

  public void generateCrossShapeData() {
    final Collection<Block> uncutBaseBlocks = getUncutBaseBlocks();

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
    final boolean shouldStoneCut = shouldStoneCut(baseBlock);

    // 跨方块切石
    for (final BlockShape blockShape : BlockShape.values()) {
      // block 是切石前的基础方块。
      for (final Block uncutBaseBlock : uncutBaseBlocks) {
        final String path = Registries.BLOCK.getId(uncutBaseBlock).getPath();
        final Block output = BlockBiMaps.getBlockOf(blockShape, baseBlock);
        if (!(output instanceof BlockResourceGenerator) || !((BlockResourceGenerator) output).shouldWriteStonecuttingRecipe()) continue;
        JRecipe recipe = ((BlockResourceGenerator) output).getStonecuttingRecipe();
        if (recipe instanceof JStonecuttingRecipe jStonecuttingRecipe) {
          final Identifier secondaryId = ResourceGeneratorHelper.getRecipeId(output).brrp_append("_from_" + path + "_stonecutting");
          final JStonecuttingRecipe secondaryRecipe = new JStonecuttingRecipe(
              JIngredient.ofItem(uncutBaseBlock),
              jStonecuttingRecipe.result,
              jStonecuttingRecipe.count
          ).recipeCategory(jStonecuttingRecipe.recipeCategory);
          secondaryRecipe.addInventoryChangedCriterion("has_" + path, uncutBaseBlock);
          pack.addRecipe(secondaryId, secondaryRecipe);
          pack.addRecipeAdvancement(secondaryId, ResourceGeneratorHelper.getAdvancementIdForRecipe(output, secondaryId, secondaryRecipe), secondaryRecipe);
        }
      }
    }

    if (quarterPiece != null) {
      // 1x楼梯 -> 3x横条
      if (stairs != null && shouldStoneCut) {
        cutStairsToQuarterPiece(stairs, quarterPiece, null);
        for (final Block uncutBaseBlock : uncutBaseBlocks) {
          final Block uncutStairs = BlockBiMaps.getBlockOf(BlockShape.STAIRS, uncutBaseBlock);
          if (uncutStairs == null) continue;
          final String name0 = Registries.BLOCK.getId(uncutStairs).getPath();
          cutStairsToQuarterPiece(uncutStairs, quarterPiece, "_from_" + name0 + "_stonecutting");
        }
      }

      // 1x台阶 -> 2x横条
      if (slab != null) {
        craftSlabToQuarterPiece(slab, quarterPiece, null);
        if (shouldStoneCut) {
          cutSlabToQuarterPiece(slab, quarterPiece, null);
          for (final Block uncutBaseBlock : uncutBaseBlocks) {
            final Block uncutSlab = BlockBiMaps.getBlockOf(BlockShape.SLAB, uncutBaseBlock);
            if (uncutSlab == null) continue;
            final String name0 = Registries.BLOCK.getId(uncutSlab).getPath();
            cutSlabToQuarterPiece(uncutSlab, quarterPiece, "_from_" + name0 + "_stonecutting");
          }
        }
      }

      // 1x纵台阶 -> 2x横条
      if (verticalSlab != null) {
        craftVerticalSlabToQuarterPiece(verticalSlab, quarterPiece, null);
        if (shouldStoneCut) {
          cutVerticalSlabToQuarterPiece(verticalSlab, quarterPiece, null);
          for (final Block uncutBaseBlock : uncutBaseBlocks) {
            final Block uncutVerticalSlab = BlockBiMaps.getBlockOf(BlockShape.VERTICAL_SLAB, uncutBaseBlock);
            if (uncutVerticalSlab == null) continue;
            cutVerticalSlabToQuarterPiece(uncutVerticalSlab, quarterPiece, "_from_" + Registries.BLOCK.getId(uncutVerticalSlab).getPath() + "_stonecutting");
          }
        }
      }
    }

    if (verticalQuarterPiece != null) {
      // 1x纵台阶 -> 2x纵条
      if (verticalSlab != null) {
        craftVerticalSlabToVerticalQuarterPiece(verticalSlab, verticalQuarterPiece, null);
        if (shouldStoneCut) {
          cutVerticalSlabToVerticalQuarterPiece(verticalSlab, verticalQuarterPiece, null);
          for (final Block uncutBaseBlock : uncutBaseBlocks) {
            final Block uncutVerticalSlab = BlockBiMaps.getBlockOf(BlockShape.VERTICAL_SLAB, uncutBaseBlock);
            if (uncutVerticalSlab == null) return;
            cutVerticalSlabToVerticalQuarterPiece(uncutVerticalSlab, verticalQuarterPiece, "_from_" + Registries.BLOCK.getId(uncutVerticalSlab).getPath() + "_stonecutting");
          }
        }
      }

      // 1x纵楼梯 -> 3x纵条
      if (verticalStairs != null && shouldStoneCut) {
        cutVerticalStairsToVerticalQuarterPiece(verticalStairs, verticalQuarterPiece, null);
        for (final Block uncutBaseBlock : uncutBaseBlocks) {
          final Block uncutVerticalStairs = BlockBiMaps.getBlockOf(BlockShape.VERTICAL_STAIRS, uncutBaseBlock);
          if (uncutVerticalStairs == null) continue;
          cutVerticalStairsToVerticalQuarterPiece(uncutVerticalStairs, verticalQuarterPiece, "_from_" + Registries.BLOCK.getId(uncutVerticalStairs).getPath() + "_stonecutting");
        }
      }
    }
  }

  @NotNull
  protected ImmutableCollection<Block> getUncutBaseBlocks() {
    return VanillaStonecutting.INSTANCE.get(baseBlock);
  }
}
