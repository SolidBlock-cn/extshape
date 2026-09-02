package pers.solid.extshape.data;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.providers.number.floats.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProvider;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import pers.solid.extshape.number.ExtShapeNumberProviders;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ExtShapeContextFloatProviderProvider extends FabricCodecDataProvider<ContextFloatProvider> {
  protected ExtShapeContextFloatProviderProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(packOutput, registriesFuture, Registries.CONTEXT_FLOAT_PROVIDER, ContextFloatProviders.DIRECT_CODEC);
  }

  @Override
  protected void configure(BiConsumer<Identifier, ContextFloatProvider> provider, HolderLookup.Provider registryLookup) {

    BiConsumer<ResourceKey<ContextFloatProvider>, ContextFloatProvider> registry = (key, np) -> provider.accept(key.identifier(), np);

    registry.accept(ExtShapeNumberProviders.SHAPE_VARIANT_MULTIPLIER_HALF, new ConstantValue(0.5f));
    registry.accept(ExtShapeNumberProviders.SHAPE_VARIANT_MULTIPLIER_QUARTER, new ConstantValue(0.25f));
    registry.accept(ExtShapeNumberProviders.SHAPE_VARIANT_MULTIPLIER_ONE_THIRD, new ConstantValue(1f / 3f));
  }

  @Override
  public String getName() {
    return "Extended Block Shapes Context Float Providers";
  }
}
