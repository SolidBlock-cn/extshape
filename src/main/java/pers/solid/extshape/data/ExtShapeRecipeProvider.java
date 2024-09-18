package pers.solid.extshape.data;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.block.CopperManager;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;

import java.util.concurrent.CompletableFuture;

public class ExtShapeRecipeProvider extends FabricRecipeProvider {
  public ExtShapeRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  public void generate(RecipeExporter exporter) {
    for (Block block : ExtShapeBlocks.getBlocks()) {
      if (block instanceof ExtShapeBlockInterface i) {
        if (i.getBaseBlock() == Blocks.TUFF && (i.getBlockShape() == BlockShape.STAIRS || i.getBlockShape() == BlockShape.SLAB || i.getBlockShape() == BlockShape.WALL)) {
          continue;
        }
        i.registerRecipes(exporter);
      }
    }

    for (Block baseBlock : ExtShapeBlocks.getBaseBlocks()) {
      final CrossShapeDataGeneration crossShapeDataGeneration = new CrossShapeDataGeneration(baseBlock, ExtShape.MOD_ID, exporter);
      crossShapeDataGeneration.generateCrossShapeData();
    }

    offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.PETRIFIED_OAK_SLAB, ExtShapeBlocks.PETRIFIED_OAK_PLANKS);
    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.PETRIFIED_OAK_SLAB, ExtShapeBlocks.PETRIFIED_OAK_PLANKS, 2);
    offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_STONE_SLAB, ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB);
    offerStonecuttingRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_STONE_SLAB, ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB, 2);

    final Predicate<Block> predicate = Predicates.in(ExtShapeBlocks.getBlocks());
    CopperManager.COPPER.generateWaxRecipes(exporter, predicate);
    CopperManager.CUT_COPPER.generateWaxRecipes(exporter, predicate);
  }
}
