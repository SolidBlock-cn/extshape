package pers.solid.extshape.data;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.StonecuttingRecipeJsonBuilder;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.block.CopperManager;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.block.ExtShapeBlocks;

import java.util.concurrent.CompletableFuture;

public class ExtShapeRecipeProvider extends FabricRecipeProvider {
  public ExtShapeRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  public void generate(RecipeExporter exporter) {
    for (Block block : ExtShapeBlocks.getBlocks()) {
      if (block instanceof ExtShapeBlockInterface i) {
        i.registerRecipes(exporter);
      }
    }

    for (Block baseBlock : ExtShapeBlocks.getBaseBlocks()) {
      final CrossShapeDataGeneration crossShapeDataGeneration = new CrossShapeDataGeneration(baseBlock, ExtShape.MOD_ID, VanillaStonecutting.INSTANCE, exporter);
      crossShapeDataGeneration.generateCrossShapeData();
    }

    // 石化橡木木板和平滑台阶的配方部分。在 1.21.4 中，为确保进度中提及配方的内容与配方 ID 一致，这里进行特殊处理。在 1.21.5 以上版本中不存在此问题。
    createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, Blocks.PETRIFIED_OAK_SLAB, Ingredient.ofItems(ExtShapeBlocks.PETRIFIED_OAK_PLANKS)).criterion(hasItem(ExtShapeBlocks.PETRIFIED_OAK_PLANKS), conditionsFromItem(ExtShapeBlocks.PETRIFIED_OAK_PLANKS)).offerTo(exporter, ExtShape.id(getItemPath(Blocks.PETRIFIED_OAK_SLAB)));
    StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(ExtShapeBlocks.PETRIFIED_OAK_PLANKS), RecipeCategory.BUILDING_BLOCKS, Blocks.PETRIFIED_OAK_SLAB, 2).criterion(hasItem(ExtShapeBlocks.PETRIFIED_OAK_PLANKS), conditionsFromItem(ExtShapeBlocks.PETRIFIED_OAK_PLANKS)).offerTo(exporter, ExtShape.id(convertBetween(Blocks.PETRIFIED_OAK_SLAB, ExtShapeBlocks.PETRIFIED_OAK_PLANKS) + "_stonecutting"));
    createSlabRecipe(RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_STONE_SLAB, Ingredient.ofItems(ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB)).criterion(hasItem(ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB), conditionsFromItem(ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB)).offerTo(exporter, ExtShape.id(getItemPath(Blocks.SMOOTH_STONE_SLAB)));
    StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB), RecipeCategory.BUILDING_BLOCKS, Blocks.SMOOTH_STONE_SLAB, 2).criterion(hasItem(ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB), conditionsFromItem(ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB)).offerTo(exporter, ExtShape.id(convertBetween(Blocks.SMOOTH_STONE_SLAB, ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB) + "_stonecutting"));

    final Predicate<Block> predicate = Predicates.in(ExtShapeBlocks.getBlocks());
    CopperManager.COPPER.generateWaxRecipes(exporter, predicate);
    CopperManager.CUT_COPPER.generateWaxRecipes(exporter, predicate);
  }
}
