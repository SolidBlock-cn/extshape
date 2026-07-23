package pers.solid.extshape.data;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.*;
import net.minecraft.core.registries.EmptyTagLookupWrapper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchBlock;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberDispatcher;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import pers.solid.extshape.number.ExtShapeNumberProviders;
import pers.solid.extshape.number.ProductNumberProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ExtShapeNumberProviderProvider extends FabricCodecDataProvider<NumberProvider> {

  protected ExtShapeNumberProviderProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(packOutput, registriesFuture, Registries.NUMBER_PROVIDER, NumberProviders.DIRECT_CODEC);
  }

  @Override
  protected void configure(BiConsumer<Identifier, NumberProvider> provider, HolderLookup.Provider registryLookup) {
    HolderGetter<NumberProvider> numberProviders = registryLookup.lookupOrThrow(Registries.NUMBER_PROVIDER);
    final HolderLookup.RegistryLookup<Block> blocks = registryLookup.lookupOrThrow(Registries.BLOCK);

    BiConsumer<ResourceKey<NumberProvider>, NumberProvider> registry = (key, np) -> provider.accept(key.identifier(), np);

    registry.accept(ExtShapeNumberProviders.SHAPE_VARIANT_MULTIPLIER_HALF, new ConstantValue(0.5f));
    registry.accept(ExtShapeNumberProviders.SHAPE_VARIANT_MULTIPLIER_QUARTER, new ConstantValue(0.25f));
    registry.accept(ExtShapeNumberProviders.SHAPE_VARIANT_MULTIPLIER_ONE_THIRD, new ConstantValue(1f / 3f));

    addForCompostingVariantSeries(blocks, numberProviders, registry, ExtShapeNumberProviders.COMPOSTABLE_MEDIUM);
    addForCompostingVariantSeries(blocks, numberProviders, registry, ExtShapeNumberProviders.COMPOSTABLE_MEDIUM_HIGH);
    addForCookingVariantSeries(numberProviders, registry, ExtShapeNumberProviders.COOKING_TIME_WOOD_BLOCKS);
    addForCookingVariantSeries(numberProviders, registry, ExtShapeNumberProviders.COOKING_TIME_WOOL);
    addForCookingVariantSeries(numberProviders, registry, ExtShapeNumberProviders.COOKING_TIME_COAL_BLOCK);
  }

  protected static NumberProvider createCompostingVariantProvider(HolderGetter<Block> blocks, Holder<NumberProvider> base, Holder<NumberProvider> shapeVariant) {
    NumberDispatcher.Case emptyCase = new NumberDispatcher.Case(Holder.direct(MatchBlock.blockMatches(blocks, Blocks.COMPOSTER, StatePropertiesPredicate.Builder.properties().hasProperty(ComposterBlock.LEVEL, 0)).build()), ConstantValue.exactly(1.0F));
    NumberDispatcher.Case variantCase = new NumberDispatcher.Case(Holder.direct(new LootItemRandomChanceCondition(shapeVariant)), base);

    return new NumberDispatcher(List.of(emptyCase, variantCase), ConstantValue.exactly(0f));
  }

  protected static void addForCompostingVariantSeries(HolderGetter<Block> blocks, HolderGetter<NumberProvider> numberProviders, BiConsumer<ResourceKey<NumberProvider>, NumberProvider> registry, ExtShapeNumberProviders.VariantSeries variantSeries) {
    HolderOwner<NumberProvider> owner = numberProviders instanceof EmptyTagLookupWrapper<NumberProvider>(HolderLookup.RegistryLookup<NumberProvider> parent) ? parent : numberProviders;
    // todo 寻找替代方案
    final Holder.Reference<NumberProvider> base = Holder.Reference.createStandAlone(owner, variantSeries.base());
    final Holder.Reference<NumberProvider> half = Holder.Reference.createStandAlone(owner, ExtShapeNumberProviders.SHAPE_VARIANT_MULTIPLIER_HALF);
    final Holder.Reference<NumberProvider> quarter = Holder.Reference.createStandAlone(owner, ExtShapeNumberProviders.SHAPE_VARIANT_MULTIPLIER_QUARTER);
    final Holder.Reference<NumberProvider> oneThird = Holder.Reference.createStandAlone(owner, ExtShapeNumberProviders.SHAPE_VARIANT_MULTIPLIER_ONE_THIRD);
    registry.accept(variantSeries.half(), createCompostingVariantProvider(blocks, base, half));
    registry.accept(variantSeries.quarter(), createCompostingVariantProvider(blocks, base, quarter));
    registry.accept(variantSeries.oneThird(), createCompostingVariantProvider(blocks, base, oneThird));
  }

  protected static void addForCookingVariantSeries(HolderGetter<NumberProvider> numberProviders, BiConsumer<ResourceKey<NumberProvider>, NumberProvider> registry, ExtShapeNumberProviders.VariantSeries variantSeries) {
    HolderOwner<NumberProvider> owner = numberProviders instanceof EmptyTagLookupWrapper<NumberProvider>(HolderLookup.RegistryLookup<NumberProvider> parent) ? parent : numberProviders;
    // todo 寻找替代方案
    final Holder.Reference<NumberProvider> base = Holder.Reference.createStandAlone(owner, variantSeries.base());
    final Holder.Reference<NumberProvider> half = Holder.Reference.createStandAlone(owner, ExtShapeNumberProviders.SHAPE_VARIANT_MULTIPLIER_HALF);
    final Holder.Reference<NumberProvider> quarter = Holder.Reference.createStandAlone(owner, ExtShapeNumberProviders.SHAPE_VARIANT_MULTIPLIER_QUARTER);
    final Holder.Reference<NumberProvider> oneThird = Holder.Reference.createStandAlone(owner, ExtShapeNumberProviders.SHAPE_VARIANT_MULTIPLIER_ONE_THIRD);
    registry.accept(variantSeries.half(), new ProductNumberProvider(HolderSet.direct(base, half)));
    registry.accept(variantSeries.quarter(), new ProductNumberProvider(HolderSet.direct(base, quarter)));
    registry.accept(variantSeries.oneThird(), new ProductNumberProvider(HolderSet.direct(base, oneThird)));
  }

  @Override
  public String getName() {
    return "Extended Block Shapes Number Providers";
  }

}
