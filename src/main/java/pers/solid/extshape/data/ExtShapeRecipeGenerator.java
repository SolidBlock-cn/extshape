package pers.solid.extshape.data;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeGenerator;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.block.CopperManager;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.block.ExtShapeBlocks;

public class ExtShapeRecipeGenerator extends RecipeGenerator {

  protected ExtShapeRecipeGenerator(RegistryWrapper.WrapperLookup registries, RecipeExporter exporter) {
    super(registries, exporter);
  }

  @Override
  public void generate() {
    for (Block block : ExtShapeBlocks.getBlocks()) {
      if (block instanceof ExtShapeBlockInterface i) {
        i.registerRecipes(this, exporter);
      }
    }

    for (Block baseBlock : ExtShapeBlocks.getBaseBlocks()) {
      final CrossShapeDataGeneration crossShapeDataGeneration = new CrossShapeDataGeneration(baseBlock, ExtShape.MOD_ID, this, exporter);
      crossShapeDataGeneration.generateCrossShapeData();
    }

    offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, Blocks.PETRIFIED_OAK_SLAB, ExtShapeBlocks.PETRIFIED_OAK_PLANKS);
    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, Blocks.PETRIFIED_OAK_SLAB, ExtShapeBlocks.PETRIFIED_OAK_PLANKS, 2);
    offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_STONE_SLAB, ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB);
    offerStonecuttingRecipe(RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_STONE_SLAB, ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB, 2);

    final Predicate<Block> predicate = Predicates.in(ExtShapeBlocks.getBlocks());
    CopperManager.COPPER.generateWaxRecipes(this, exporter, predicate);
    CopperManager.CUT_COPPER.generateWaxRecipes(this, exporter, predicate);
  }
}
