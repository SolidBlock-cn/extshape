package pers.solid.extshape.blockus.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.registry.RegistryWrapper;

public class ExtShapeBlockusDataGenerator implements DataGeneratorEntrypoint {
  @Override
  public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
    final FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
    pack.addProvider(ExtShapeBlockusModelProvider::new);
    pack.addProvider((fabricDataOutput, completableFuture) -> new FabricRecipeProvider(fabricDataOutput, completableFuture) {
      @Override
      protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new ExtShapeBlockusRecipeGenerator(wrapperLookup, recipeExporter);
      }

      @Override
      public String getName() {
        return "Extended Block Shapes Blockus Recipes";
      }
    });
    pack.addProvider(ExtShapeBlockusLootTableProvider::new);
    final ExtShapeBlockusBlockTagProvider blockTagProvider = pack.addProvider(ExtShapeBlockusBlockTagProvider::new);
    pack.addProvider((output, registriesFuture) -> new ExtShapeBlockusItemTagProvider(output, registriesFuture, blockTagProvider));
  }
}
