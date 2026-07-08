package pers.solid.extshape.data;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.block.CopperManager;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.block.ExtShapeBlocks;

public class ExtShapeRecipeGenerator extends RecipeProvider {

  protected ExtShapeRecipeGenerator(final BootstrapContext<Recipe<?>> recipeOutput, final BootstrapContext<Advancement> advancementOutput) {
    super(recipeOutput, advancementOutput);
  }

  @Override
  public void buildRecipes() {
    for (Block block : ExtShapeBlocks.getBlocks()) {
      if (block instanceof ExtShapeBlockInterface i) {
        i.registerRecipes(this, output);
      }
    }

    for (Block baseBlock : ExtShapeBlocks.getBaseBlocks()) {
      final CrossShapeDataGeneration crossShapeDataGeneration = new CrossShapeDataGeneration(baseBlock, ExtShape.MOD_ID, this, output);
      crossShapeDataGeneration.generateCrossShapeData();
    }

    slab(RecipeCategory.BUILDING_BLOCKS, Blocks.PETRIFIED_OAK_SLAB, ExtShapeBlocks.PETRIFIED_OAK_PLANKS);
    stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, Blocks.PETRIFIED_OAK_SLAB, ExtShapeBlocks.PETRIFIED_OAK_PLANKS, 2);
    slab(RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_STONE_SLAB, ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB);
    stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_STONE_SLAB, ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB, 2);

    final Predicate<Block> predicate = Predicates.in(ExtShapeBlocks.getBlocks());
    CopperManager.COPPER.generateWaxRecipes(this, output, predicate);
    CopperManager.CUT_COPPER.generateWaxRecipes(this, output, predicate);
  }
}
