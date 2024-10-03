package pers.solid.extshape.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeGenerator;
import net.minecraft.registry.RegistryWrapper;
import pers.solid.extshape.ExtShape;

public class ExtShapeDataGenerator implements DataGeneratorEntrypoint {
  @Override
  public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
    final FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
    pack.addProvider(ExtShapeModelProvider::new);
    pack.addProvider((output, registriesFuture) -> new FabricRecipeProvider(output, registriesFuture) {
      @Override
      protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        return new ExtShapeRecipeGenerator(registryLookup, exporter);
      }

      @Override
      public String getName() {
        return "Recipe";
      }
    });
    pack.addProvider(ExtShapeLootTableProvider::new);
    final ExtShapeBlockTagProvider blockTagProvider = pack.addProvider(ExtShapeBlockTagProvider::new);
    pack.addProvider((output, registriesFuture) -> new ExtShapeItemTagProvider(output, registriesFuture, blockTagProvider));

    final FabricDataGenerator.Pack recipeTweak = fabricDataGenerator.createBuiltinResourcePack(ExtShape.id("recipe_tweak"));
    recipeTweak.addProvider((output, registriesFuture) -> new FabricRecipeProvider(output, registriesFuture) {
      @Override
      public String getName() {
        return "RecipeTweak";
      }

      @Override
      protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        return new ExtShapeTweakRecipeProvider(registryLookup, exporter);
      }
    });
  }
}
