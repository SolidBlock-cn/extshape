package pers.solid.extshape.blockus.data;

import com.brand.blockus.content.BlockusBlocks;
import com.brand.blockus.data.providers.BlockusRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.blockus.BlockusCrossShapeDataGeneration;
import pers.solid.extshape.blockus.ExtShapeBlockus;
import pers.solid.extshape.blockus.ExtShapeBlockusBlocks;
import pers.solid.extshape.data.CrossShapeDataGeneration;
import pers.solid.extshape.util.BlockBiMaps;

public class ExtShapeBlockusRecipeProvider extends FabricRecipeProvider {
  public ExtShapeBlockusRecipeProvider(FabricDataOutput output) {
    super(output);
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
