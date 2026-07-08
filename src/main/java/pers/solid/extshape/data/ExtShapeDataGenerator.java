package pers.solid.extshape.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;
import pers.solid.extshape.ExtShape;

public class ExtShapeDataGenerator implements DataGeneratorEntrypoint {
  @Override
  public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
    final FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
    FabricLoader.getInstance().getEnvironmentType();
    pack.addProvider(ExtShapeModelProvider::new);
    pack.addProvider((output, registriesFuture) -> new FabricRecipeProvider(output, registriesFuture) {
      @Override
      protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        return new ExtShapeRecipeGenerator(recipes, advancements);
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
      protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        return new ExtShapeTweakRecipeProvider(recipes, advancements);
      }

      @Override
      protected Identifier getRecipeIdentifier(Identifier identifier) {
        return identifier;
      }
    });
  }
}
