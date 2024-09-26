package pers.solid.extshape.data;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.registry.RegistryWrapper;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.block.CopperManager;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.rrp.CrossShapeDataGeneration;

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
      final CrossShapeDataGeneration crossShapeDataGeneration = new CrossShapeDataGeneration(baseBlock, ExtShape.MOD_ID, exporter);
      crossShapeDataGeneration.generateCrossShapeData();
    }

    final Predicate<Block> predicate = Predicates.in(ExtShapeBlocks.getBlocks());
    CopperManager.COPPER.generateWaxRecipes(exporter, predicate);
    CopperManager.CUT_COPPER.generateWaxRecipes(exporter, predicate);
  }
}
