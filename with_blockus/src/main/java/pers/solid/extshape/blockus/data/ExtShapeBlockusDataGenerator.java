package pers.solid.extshape.blockus.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ExtShapeBlockusDataGenerator implements DataGeneratorEntrypoint {
  @Override
  public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
    final FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
    pack.addProvider(ExtShapeBlockusModelProvider::new);
    pack.addProvider(ExtShapeBlockusRecipeProvider::new);
    pack.addProvider(ExtShapeBlockusLootTableProvider::new);
    final ExtShapeBlockusBlockTagProvider blockTagProvider = pack.addProvider(ExtShapeBlockusBlockTagProvider::new);
    pack.addProvider((output, registriesFuture) -> new ExtShapeBlockusItemTagProvider(output, registriesFuture, blockTagProvider));
  }
}
