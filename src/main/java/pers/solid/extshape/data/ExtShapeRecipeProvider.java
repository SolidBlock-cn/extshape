package pers.solid.extshape.data;

import com.google.common.base.Preconditions;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.StonecuttingRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.block.CopperManager;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.block.ExtShapePressurePlateBlock;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.rrp.CrossShapeDataGeneration;
import pers.solid.extshape.util.BlockBiMaps;
import pers.solid.extshape.util.BlockCollections;

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

    CopperManager.generateWaxRecipes(exporter);


    // 羊毛压力板方块的反向合成配方。
    for (Block baseBlock : BlockCollections.WOOLS) {
      final ExtShapePressurePlateBlock block = (ExtShapePressurePlateBlock) BlockBiMaps.getBlockOf(BlockShape.PRESSURE_PLATE, baseBlock);
      Preconditions.checkNotNull(block, "pressure plate of %s is null?", baseBlock);
      final Identifier woolId = Registries.BLOCK.getId(baseBlock);
      final Identifier carpetId = Identifier.of(woolId.getNamespace(), woolId.getPath().replaceAll("_wool$", "_carpet"));
      final Item carpet = Registries.ITEM.get(carpetId);
      final StonecuttingRecipeJsonBuilder recipe = StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(block), block.getRecipeCategory(), carpet).criterion("has_pressure_plate", RecipeProvider.conditionsFromItem(block));
      final Identifier recipeId = ExtShape.id(carpetId.getPath() + "_from_pressure_plate");
      recipe.offerTo(exporter, recipeId);
    }

    // 苔藓的反向合成配方
    {
      final ExtShapePressurePlateBlock mossPressurePlate = (ExtShapePressurePlateBlock) BlockBiMaps.getBlockOf(BlockShape.PRESSURE_PLATE, Blocks.MOSS_BLOCK);
      Preconditions.checkNotNull(mossPressurePlate, "moss pressure plate block");
      final StonecuttingRecipeJsonBuilder recipe = StonecuttingRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(mossPressurePlate), mossPressurePlate.getRecipeCategory(), Blocks.MOSS_CARPET).criterion("has_pressure_plate", RecipeProvider.conditionsFromItem(mossPressurePlate));
      final Identifier recipeId = ExtShape.id("moss_carpet_from_pressure_plate");
      recipe.offerTo(exporter, recipeId);
    }
  }
}
