package pers.solid.extshape.blockus.data;

import com.brand.blockus.datagen.providers.BlockusRecipeProvider;
import com.brand.blockus.registry.content.BlockusBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.blockus.BlockusCrossShapeDataGeneration;
import pers.solid.extshape.blockus.ExtShapeBlockus;
import pers.solid.extshape.blockus.ExtShapeBlockusBlocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.data.CrossShapeDataGeneration;
import pers.solid.extshape.util.BlockBiMaps;

public class ExtShapeBlockusRecipeGenerator extends RecipeProvider {
  protected ExtShapeBlockusRecipeGenerator(HolderLookup.Provider registries, RecipeOutput exporter) {
    super(registries, exporter);
  }

  @Override
  public void buildRecipes() {
    for (Block block : ExtShapeBlockusBlocks.BLOCKUS_BLOCKS) {
      if (block instanceof ExtShapeBlockInterface i) {
        i.registerRecipes(this, output);
      }
    }

    for (Block baseBlock : ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS) {
      final CrossShapeDataGeneration crossShapeDataGeneration = new BlockusCrossShapeDataGeneration(baseBlock, ExtShapeBlockus.NAMESPACE, this, output);
      crossShapeDataGeneration.generateCrossShapeData();
    }
    // Blockus 的磨制末地石到原版的末地石砖
    final CrossShapeDataGeneration vanillaEndStoneBricks = new BlockusCrossShapeDataGeneration(Blocks.END_STONE_BRICKS, ExtShapeBlockus.NAMESPACE, this, output);
    vanillaEndStoneBricks.enableConversionWithinBlock = false;
    vanillaEndStoneBricks.generateCrossShapeData();

    registerShingleDyeingRecipes(output);
    registerStainedStoneBricksRecipe(output);
  }


  private void registerShingleDyeingRecipes(RecipeOutput exporter) {
    BlockusBlocks.STAINED_SHINGLES.colorMap().forEach((dyeColor, bsswBundle) -> {
      ItemLike dyeItem = BlockusRecipeProvider.DYE_MAP.get(dyeColor);
      for (BlockShape blockShape : BlockShape.values()) {
        final Block unDyed = BlockBiMaps.getBlockOf(blockShape, BlockusBlocks.SHINGLES.block());
        final Block dyed = BlockBiMaps.getBlockOf(blockShape, bsswBundle.block());
        if (unDyed == null || dyed == null || !ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(dyed)) continue;
        createEnclosedRecipe(dyed, Ingredient.of(unDyed), dyeItem)
            .group("shingles_" + blockShape.getSerializedName() + "_from_dyeing")
            .unlockedBy(getHasName(BlockusBlocks.SHINGLES.block()), has(BlockusBlocks.SHINGLES.block()))
            .save(exporter, ResourceKey.create(Registries.RECIPE, ExtShapeBlockus.id(getItemName(dyed) + "_from_dyeing")));
      }
    });
  }

  private void registerStainedStoneBricksRecipe(RecipeOutput exporter) {
    BlockusBlocks.STAINED_STONE_BRICKS.colorMap().forEach((dyeColor, bsswBundle) -> {
      ItemLike dyeItem = BlockusRecipeProvider.DYE_MAP.get(dyeColor);
      for (BlockShape blockShape : BlockShape.values()) {
        final Block unDyed = BlockBiMaps.getBlockOf(blockShape, Blocks.STONE_BRICKS);
        final Block dyed = BlockBiMaps.getBlockOf(blockShape, bsswBundle.block());
        if (unDyed == null || dyed == null || !ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(dyed)) continue;
        final RecipeBuilder recipe = createEnclosedRecipe(dyed, Ingredient.of(unDyed), dyeItem)
            .group("stained_stone_brick_" + blockShape.getSerializedName() + "_from_dyeing")
            .unlockedBy(getHasName(Blocks.STONE_BRICKS), has(Blocks.STONE_BRICKS));
        recipe.save(exporter, ResourceKey.create(Registries.RECIPE, ExtShapeBlockus.id(getItemName(dyed) + "_from_dyeing")));
      }
    });
  }

  protected RecipeBuilder createEnclosedRecipe(ItemLike output, Ingredient input, ItemLike center) {
    return this.shaped(RecipeCategory.BUILDING_BLOCKS, output, 8)
        .define('X', input)
        .define('#', center)
        .pattern("XXX")
        .pattern("X#X")
        .pattern("XXX");
  }
}
