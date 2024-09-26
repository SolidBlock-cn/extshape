package pers.solid.extshape.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import pers.solid.extshape.ExtShape;

public class ExtShapeDataGenerator implements DataGeneratorEntrypoint {
  @Override
  public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
    final FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
    pack.addProvider(ExtShapeModelProvider::new);
    pack.addProvider(ExtShapeRecipeProvider::new);
    pack.addProvider(ExtShapeLootTableProvider::new);
    final ExtShapeBlockTagProvider blockTagProvider = pack.addProvider(ExtShapeBlockTagProvider::new);
    pack.addProvider((output, registriesFuture) -> new ExtShapeItemTagProvider(output, registriesFuture, blockTagProvider));

    final FabricDataGenerator.Pack recipeTweak = fabricDataGenerator.createBuiltinResourcePack(ExtShape.id("recipe_tweak"));
    recipeTweak.addProvider(ExtShapeTweakRecipeProvider::new);
  }
}
