package pers.solid.extshape.blockus.data;

import com.brand.blockus.datagen.providers.BlockusRecipeProvider;
import com.brand.blockus.registry.content.BlockusBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.blockus.BlockusBlockCollections;
import pers.solid.extshape.blockus.BlockusCrossShapeDataGeneration;
import pers.solid.extshape.blockus.ExtShapeBlockus;
import pers.solid.extshape.blockus.ExtShapeBlockusBlocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.data.CrossShapeDataGeneration;
import pers.solid.extshape.util.BlockBiMaps;

import java.util.function.Consumer;

public class ExtShapeBlockusRecipeProvider extends FabricRecipeProvider {
  public ExtShapeBlockusRecipeProvider(FabricDataOutput output) {
    super(output);
  }

  @Override
  public void generate(Consumer<RecipeJsonProvider> exporter) {
    for (Block block : ExtShapeBlockusBlocks.BLOCKUS_BLOCKS) {
      if (block instanceof ExtShapeBlockInterface i) {
        i.registerRecipes(exporter);
      }
    }

    for (Block baseBlock : ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS) {
      final CrossShapeDataGeneration crossShapeDataGeneration = new BlockusCrossShapeDataGeneration(baseBlock, ExtShape.MOD_ID, exporter);
      crossShapeDataGeneration.generateCrossShapeData();
    }

    registerShingleDyeingRecipes(exporter);
    registerStainedStoneBricksRecipe(exporter);
  }


  private void registerShingleDyeingRecipes(Consumer<RecipeJsonProvider> exporter) {
    for (var bssTypes : BlockusBlockCollections.TINTED_SHINGLES) {
      Item dyeItem = Registries.ITEM.get(new Identifier(Identifier.DEFAULT_NAMESPACE, StringUtils.substringBefore(Registries.BLOCK.getId(bssTypes.block).getPath(), "_shingle") + "_dye"));
      for (BlockShape blockShape : BlockShape.values()) {
        final Block unDyed = BlockBiMaps.getBlockOf(blockShape, BlockusBlocks.SHINGLES.block);
        final Block dyed = BlockBiMaps.getBlockOf(blockShape, bssTypes.block);
        if (unDyed == null || dyed == null || !ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(dyed)) continue;
        BlockusRecipeProvider
            .createEnclosedRecipe(dyed, Ingredient.ofItems(unDyed), dyeItem)
            .group("shingles_" + blockShape.asString() + "_from_dyeing")
            .criterion(RecipeProvider.hasItem(BlockusBlocks.SHINGLES.block), RecipeProvider.conditionsFromItem(BlockusBlocks.SHINGLES.block))
            .offerTo(exporter, ExtShapeBlockus.id(RecipeProvider.getItemPath(dyed) + "_from_dyeing"));
      }
    }
  }

  private void registerStainedStoneBricksRecipe(Consumer<RecipeJsonProvider> exporter) {
    for (var bsswTypes : BlockusBlockCollections.STAINED_STONE_BRICKS) {
      Item dyeItem = Registries.ITEM.get(new Identifier(Identifier.DEFAULT_NAMESPACE, StringUtils.substringBefore(Registries.BLOCK.getId(bsswTypes.block).getPath(), "_stone_brick") + "_dye"));
      for (BlockShape blockShape : BlockShape.values()) {
        final Block unDyed = BlockBiMaps.getBlockOf(blockShape, Blocks.STONE_BRICKS);
        final Block dyed = BlockBiMaps.getBlockOf(blockShape, bsswTypes.block);
        if (unDyed == null || dyed == null || !ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(dyed)) continue;
        final CraftingRecipeJsonBuilder recipe = BlockusRecipeProvider
            .createEnclosedRecipe(dyed, Ingredient.ofItems(unDyed), dyeItem)
            .group("stained_stone_brick_" + blockShape.asString() + "_from_dyeing")
            .criterion(RecipeProvider.hasItem(Blocks.STONE_BRICKS), RecipeProvider.conditionsFromItem(Blocks.STONE_BRICKS));
        recipe.offerTo(exporter, ExtShapeBlockus.id(RecipeProvider.getItemPath(dyed) + "_from_dyeing"));
      }
    }
  }
}
