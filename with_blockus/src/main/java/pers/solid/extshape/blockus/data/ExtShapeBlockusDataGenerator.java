package pers.solid.extshape.blockus.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.crafting.Recipe;

public class ExtShapeBlockusDataGenerator implements DataGeneratorEntrypoint {
  @Override
  public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
    final FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
    pack.addProvider(ExtShapeBlockusModelProvider::new);
    pack.addProvider((fabricDataOutput, completableFuture) -> new FabricRecipeProvider(fabricDataOutput, completableFuture) {

      @Override
      protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
        return new ExtShapeBlockusRecipeGenerator(recipes, advancements);
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
