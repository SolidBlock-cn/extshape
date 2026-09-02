package pers.solid.extshape.data;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.registries.EmptyTagLookupWrapper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchBlock;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProvider;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.floats.FromInt;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.FromFloat;
import net.minecraft.world.level.storage.loot.providers.number.ints.NumberDispatcher;
import pers.solid.extshape.number.ExtShapeNumberProviders;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ExtShapeContextIntProviderProvider extends FabricCodecDataProvider<ContextIntProvider> {

  protected ExtShapeContextIntProviderProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(packOutput, registriesFuture, Registries.CONTEXT_INT_PROVIDER, ContextIntProviders.DIRECT_CODEC);
  }

  @Override
  protected void configure(BiConsumer<Identifier, ContextIntProvider> provider, HolderLookup.Provider registryLookup) {
    final HolderLookup.RegistryLookup<Block> blocks = registryLookup.lookupOrThrow(Registries.BLOCK);

    BiConsumer<ResourceKey<ContextIntProvider>, ContextIntProvider> registry = (key, np) -> provider.accept(key.identifier(), np);


    addForCompostingVariantSeries(blocks, registryLookup, registry, ExtShapeNumberProviders.COMPOSTABLE_MEDIUM);
    addForCompostingVariantSeries(blocks, registryLookup, registry, ExtShapeNumberProviders.COMPOSTABLE_MEDIUM_HIGH);
    addForCookingVariantSeries(registryLookup, registry, ExtShapeNumberProviders.COOKING_TIME_WOOD_BLOCKS);
    addForCookingVariantSeries(registryLookup, registry, ExtShapeNumberProviders.COOKING_TIME_WOOL);
    addForCookingVariantSeries(registryLookup, registry, ExtShapeNumberProviders.COOKING_TIME_COAL_BLOCK);
  }

  protected static ContextIntProvider createCompostingVariantProvider(HolderGetter<Block> blocks, Holder<ContextIntProvider> base, Holder<ContextFloatProvider> shapeVariant) {
    NumberDispatcher.Case<ContextIntProvider> emptyCase = new NumberDispatcher.Case<>(Holder.direct(MatchBlock.blockMatches(blocks, Blocks.COMPOSTER, StatePropertiesPredicate.Builder.properties().hasProperty(ComposterBlock.LEVEL, 0)).build()), ContextIntProviders.exactly(1));
    NumberDispatcher.Case<ContextIntProvider> variantCase = new NumberDispatcher.Case<>(Holder.direct(new LootItemRandomChanceCondition(shapeVariant)), base);

    return new NumberDispatcher(List.of(emptyCase, variantCase), ContextIntProviders.exactly(0));
  }

  protected static void addForCompostingVariantSeries(HolderGetter<Block> blocks, HolderLookup.Provider registryLookup, BiConsumer<ResourceKey<ContextIntProvider>, ContextIntProvider> registry, ExtShapeNumberProviders.VariantSeries<ContextIntProvider> variantSeries) {
    final HolderLookup.RegistryLookup<ContextIntProvider> contextIntProviders = registryLookup.lookupOrThrow(Registries.CONTEXT_INT_PROVIDER);
    final HolderLookup.RegistryLookup<ContextFloatProvider> contextFloatProviders = registryLookup.lookupOrThrow(Registries.CONTEXT_FLOAT_PROVIDER);
    HolderOwner<ContextIntProvider> intOwner = contextIntProviders instanceof EmptyTagLookupWrapper<ContextIntProvider>(HolderLookup.RegistryLookup<ContextIntProvider> parent) ? parent : contextIntProviders;
    HolderOwner<ContextFloatProvider> floatOwner = contextFloatProviders instanceof EmptyTagLookupWrapper<ContextFloatProvider>(HolderLookup.RegistryLookup<ContextFloatProvider> parent) ? parent : contextFloatProviders;
    // todo 寻找替代方案
    final Holder.Reference<ContextIntProvider> base = Holder.Reference.createStandAlone(intOwner, variantSeries.base());
    final Holder.Reference<ContextFloatProvider> half = Holder.Reference.createStandAlone(floatOwner, ExtShapeNumberProviders.SHAPE_VARIANT_MULTIPLIER_HALF);
    final Holder.Reference<ContextFloatProvider> quarter = Holder.Reference.createStandAlone(floatOwner, ExtShapeNumberProviders.SHAPE_VARIANT_MULTIPLIER_QUARTER);
    final Holder.Reference<ContextFloatProvider> oneThird = Holder.Reference.createStandAlone(floatOwner, ExtShapeNumberProviders.SHAPE_VARIANT_MULTIPLIER_ONE_THIRD);
    registry.accept(variantSeries.half(), createCompostingVariantProvider(blocks, base, half));
    registry.accept(variantSeries.quarter(), createCompostingVariantProvider(blocks, base, quarter));
    registry.accept(variantSeries.oneThird(), createCompostingVariantProvider(blocks, base, oneThird));
  }

  protected static void addForCookingVariantSeries(HolderLookup.Provider registryLookup, BiConsumer<ResourceKey<ContextIntProvider>, ContextIntProvider> registry, ExtShapeNumberProviders.VariantSeries<ContextIntProvider> variantSeries) {
    final HolderLookup.RegistryLookup<ContextIntProvider> contextIntProviders = registryLookup.lookupOrThrow(Registries.CONTEXT_INT_PROVIDER);
    final HolderLookup.RegistryLookup<ContextFloatProvider> contextFloatProviders = registryLookup.lookupOrThrow(Registries.CONTEXT_FLOAT_PROVIDER);
    HolderOwner<ContextIntProvider> intOwner = contextIntProviders instanceof EmptyTagLookupWrapper<ContextIntProvider>(HolderLookup.RegistryLookup<ContextIntProvider> parent) ? parent : contextIntProviders;
    HolderOwner<ContextFloatProvider> floatOwner = contextFloatProviders instanceof EmptyTagLookupWrapper<ContextFloatProvider>(HolderLookup.RegistryLookup<ContextFloatProvider> parent) ? parent : contextFloatProviders;

    // todo 寻找替代方案
    final Holder.Reference<ContextIntProvider> base = Holder.Reference.createStandAlone(intOwner, variantSeries.base());
    final Holder.Reference<ContextFloatProvider> half = Holder.Reference.createStandAlone(floatOwner, ExtShapeNumberProviders.SHAPE_VARIANT_MULTIPLIER_HALF);
    final Holder.Reference<ContextFloatProvider> quarter = Holder.Reference.createStandAlone(floatOwner, ExtShapeNumberProviders.SHAPE_VARIANT_MULTIPLIER_QUARTER);
    final Holder.Reference<ContextFloatProvider> oneThird = Holder.Reference.createStandAlone(floatOwner, ExtShapeNumberProviders.SHAPE_VARIANT_MULTIPLIER_ONE_THIRD);
    registry.accept(variantSeries.half(), new FromFloat(ContextFloatProviders.mul(Holder.direct(new FromInt(base)), half)));
    registry.accept(variantSeries.quarter(), new FromFloat(ContextFloatProviders.mul(Holder.direct(new FromInt(base)), quarter)));
    registry.accept(variantSeries.oneThird(), new FromFloat(ContextFloatProviders.mul(Holder.direct(new FromInt(base)), oneThird)));
  }

  @Override
  public String getName() {
    return "Extended Block Shapes Context Int Providers";
  }

}
