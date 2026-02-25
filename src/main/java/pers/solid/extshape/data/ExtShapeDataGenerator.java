package pers.solid.extshape.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;
import pers.solid.extshape.ExtShape;

public class ExtShapeDataGenerator implements DataGeneratorEntrypoint {
  @Override
  public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
    final FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
    FabricLoader.getInstance().getEnvironmentType();
    pack.addProvider(ExtShapeModelProvider::new);
    pack.addProvider((output, registriesFuture) -> new FabricRecipeProvider(output, registriesFuture) {
      @Override
      protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider registryLookup, @NonNull RecipeOutput exporter) {
        return new ExtShapeRecipeGenerator(registryLookup, exporter);
      }

      @Override
      public @NonNull String getName() {
        return "Recipe";
      }
    });
    pack.addProvider(ExtShapeLootTableProvider::new);
    final ExtShapeBlockTagProvider blockTagProvider = pack.addProvider(ExtShapeBlockTagProvider::new);
    pack.addProvider((output, registriesFuture) -> new ExtShapeItemTagProvider(output, registriesFuture, blockTagProvider));

    final FabricDataGenerator.Pack recipeTweak = fabricDataGenerator.createBuiltinResourcePack(ExtShape.id("recipe_tweak"));
    recipeTweak.addProvider((output, registriesFuture) -> new FabricRecipeProvider(output, registriesFuture) {
      @Override
      public @NonNull String getName() {
        return "RecipeTweak";
      }

      @Override
      protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider registryLookup, @NonNull RecipeOutput exporter) {
        return new ExtShapeTweakRecipeProvider(registryLookup, exporter);
      }

      @Override
      protected @NonNull Identifier getRecipeIdentifier(@NonNull Identifier identifier) {
        return identifier;
      }
    });
  }
}
