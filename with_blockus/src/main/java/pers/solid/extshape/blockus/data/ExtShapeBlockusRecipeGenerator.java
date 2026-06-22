package pers.solid.extshape.blockus.data;

import com.brand.blockus.datagen.providers.BlockusRecipeProvider;
import com.brand.blockus.registry.content.BlockusBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.blockus.BlockusCrossShapeDataGeneration;
import pers.solid.extshape.blockus.ExtShapeBlockus;
import pers.solid.extshape.blockus.ExtShapeBlockusBlocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.data.CrossShapeDataGeneration;
import pers.solid.extshape.util.BlockBiMaps;

public class ExtShapeBlockusRecipeGenerator extends RecipeGenerator {
  protected ExtShapeBlockusRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
    super(registries, exporter);
  }

  @Override
  public void generate() {
    for (Block block : ExtShapeBlockusBlocks.BLOCKUS_BLOCKS) {
      if (block instanceof ExtShapeBlockInterface i) {
        i.registerRecipes(this, exporter);
      }
    }

    for (Block baseBlock : ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS) {
      final CrossShapeDataGeneration crossShapeDataGeneration = new BlockusCrossShapeDataGeneration(baseBlock, ExtShapeBlockus.NAMESPACE, this, exporter);
      crossShapeDataGeneration.generateCrossShapeData();
    }
    // Blockus 的磨制末地石到原版的末地石砖
    final CrossShapeDataGeneration vanillaEndStoneBricks = new BlockusCrossShapeDataGeneration(Blocks.END_STONE_BRICKS, ExtShapeBlockus.NAMESPACE, this, exporter);
    vanillaEndStoneBricks.enableConversionWithinBlock = false;
    vanillaEndStoneBricks.generateCrossShapeData();

    registerShingleDyeingRecipes(exporter);
    registerStainedStoneBricksRecipe(exporter);
  }


  private void registerShingleDyeingRecipes(RecipeExporter exporter) {
    BlockusBlocks.STAINED_SHINGLES.colorMap().forEach((dyeColor, bsswBundle) -> {
      ItemConvertible dyeItem = BlockusRecipeProvider.DYE_MAP.get(dyeColor);
      for (BlockShape blockShape : BlockShape.values()) {
        final Block unDyed = BlockBiMaps.getBlockOf(blockShape, BlockusBlocks.SHINGLES.block());
        final Block dyed = BlockBiMaps.getBlockOf(blockShape, bsswBundle.block());
        if (unDyed == null || dyed == null || !ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(dyed)) continue;
        createEnclosedRecipe(dyed, Ingredient.ofItems(unDyed), dyeItem)
            .group("shingles_" + blockShape.asString() + "_from_dyeing")
            .criterion(hasItem(BlockusBlocks.SHINGLES.block()), conditionsFromItem(BlockusBlocks.SHINGLES.block()))
            .offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, ExtShapeBlockus.id(getItemPath(dyed) + "_from_dyeing")));
      }
    });
  }

  private void registerStainedStoneBricksRecipe(RecipeExporter exporter) {
    BlockusBlocks.STAINED_STONE_BRICKS.colorMap().forEach((dyeColor, bsswBundle) -> {
      ItemConvertible dyeItem = BlockusRecipeProvider.DYE_MAP.get(dyeColor);
      for (BlockShape blockShape : BlockShape.values()) {
        final Block unDyed = BlockBiMaps.getBlockOf(blockShape, Blocks.STONE_BRICKS);
        final Block dyed = BlockBiMaps.getBlockOf(blockShape, bsswBundle.block());
        if (unDyed == null || dyed == null || !ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(dyed)) continue;
        final CraftingRecipeJsonBuilder recipe = createEnclosedRecipe(dyed, Ingredient.ofItems(unDyed), dyeItem)
            .group("stained_stone_brick_" + blockShape.asString() + "_from_dyeing")
            .criterion(hasItem(Blocks.STONE_BRICKS), conditionsFromItem(Blocks.STONE_BRICKS));
        recipe.offerTo(exporter, RegistryKey.of(RegistryKeys.RECIPE, ExtShapeBlockus.id(getItemPath(dyed) + "_from_dyeing")));
      }
    });
  }

  protected CraftingRecipeJsonBuilder createEnclosedRecipe(ItemConvertible output, Ingredient input, ItemConvertible center) {
    return this.createShaped(RecipeCategory.BUILDING_BLOCKS, output, 8)
        .input('X', input)
        .input('#', center)
        .pattern("XXX")
        .pattern("X#X")
        .pattern("XXX");
  }
}
