package pers.solid.extshape.blockus.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.registry.RegistryWrapper;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.blockus.BlockusCrossShapeDataGeneration;
import pers.solid.extshape.blockus.ExtShapeBlockusBlocks;
import pers.solid.extshape.rrp.CrossShapeDataGeneration;

import java.util.concurrent.CompletableFuture;

public class ExtShapeBlockusRecipeProvider extends FabricRecipeProvider {
  public ExtShapeBlockusRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  public void generate(RecipeExporter exporter) {
    for (Block block : ExtShapeBlockusBlocks.BLOCKUS_BLOCKS) {
      if (block instanceof ExtShapeBlockInterface i) {
        i.registerRecipes(exporter);
      }
    }

    for (Block baseBlock : ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS) {
      final CrossShapeDataGeneration crossShapeDataGeneration = new BlockusCrossShapeDataGeneration(baseBlock, ExtShape.MOD_ID, exporter);
      crossShapeDataGeneration.generateCrossShapeData();
    }
  }
}
