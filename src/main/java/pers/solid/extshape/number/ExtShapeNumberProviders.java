package pers.solid.extshape.number;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.builder.BlockShape;

/**
 * 用于本模组中的数值提供器。
 */
public final class ExtShapeNumberProviders {
  public static final ResourceKey<NumberProvider> SHAPE_VARIANT_MULTIPLIER_HALF = createKey("shape_variant_modifier/half");
  public static final ResourceKey<NumberProvider> SHAPE_VARIANT_MULTIPLIER_QUARTER = createKey("shape_variant_modifier/quarter");
  public static final ResourceKey<NumberProvider> SHAPE_VARIANT_MULTIPLIER_ONE_THIRD = createKey("shape_variant_modifier/one_third");

  public static final VariantSeries COMPOSTABLE_MEDIUM = VariantSeries.of(NumberProviders.COMPOSTABLE_MEDIUM);
  public static final VariantSeries COMPOSTABLE_MEDIUM_HIGH = VariantSeries.of(NumberProviders.COMPOSTABLE_MEDIUM_HIGH);
  public static final VariantSeries COOKING_TIME_WOOD_BLOCKS = VariantSeries.of(NumberProviders.COOKING_TIME_WOOD_BLOCKS);
  public static final VariantSeries COOKING_TIME_WOOL = VariantSeries.of(NumberProviders.COOKING_TIME_WOOL);
  public static final VariantSeries COOKING_TIME_COAL_BLOCK = VariantSeries.of(NumberProviders.COOKING_TIME_COAL_BLOCK);

  public static ResourceKey<NumberProvider> createKey(String name) {
    return ResourceKey.create(Registries.NUMBER_PROVIDER, ExtShape.id(name));
  }

  public record VariantSeries(ResourceKey<NumberProvider> base, ResourceKey<NumberProvider> half, ResourceKey<NumberProvider> quarter, ResourceKey<NumberProvider> oneThird) {
    public static VariantSeries of(ResourceKey<NumberProvider> base) {
      final String name = base.identifier().getPath();
      return new VariantSeries(
          base,
          createKey("shape_variant/" + name + "/half"),
          createKey("shape_variant/" + name + "/quarter"),
          createKey("shape_variant/" + name + "/one_third")
      );
    }

    public ResourceKey<NumberProvider> pickForShape(BlockShape shape) {
      final float logicalCompleteness = shape.logicalCompleteness;
      if (logicalCompleteness > 0.5) {
        return base;
      } else if (logicalCompleteness > 0.4) {
        return half;
      } else if (logicalCompleteness > 0.25) {
        return oneThird;
      } else {
        return quarter;
      }
    }
  }
}
