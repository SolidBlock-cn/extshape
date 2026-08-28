package pers.solid.extshape.data;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.BlockTransformer;
import pers.solid.extshape.ExtShapeBlockTransformers;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ExtShapeBlockTransformerProvider extends FabricDynamicRegistryProvider {
  public ExtShapeBlockTransformerProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected void configure(HolderLookup.Provider registries, Entries entries) {
    entries.add(ExtShapeBlockTransformers.AXE, new BlockTransformer(List.of(ExtShapeBlockTransformers.createEnhancedBlockTransformData())));
  }

  @Override
  public String getName() {
    return "Block Transformers (Enhanced Block Shapes)";
  }
}
