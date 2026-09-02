package pers.solid.extshape.number;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.builder.BlockShape;

/**
 * 用于本模组中的数值提供器。
 */
public final class ExtShapeNumberProviders {
  public static final ResourceKey<ContextFloatProvider> SHAPE_VARIANT_MULTIPLIER_HALF = createFloatKey("shape_variant_modifier/half");
  public static final ResourceKey<ContextFloatProvider> SHAPE_VARIANT_MULTIPLIER_QUARTER = createFloatKey("shape_variant_modifier/quarter");
  public static final ResourceKey<ContextFloatProvider> SHAPE_VARIANT_MULTIPLIER_ONE_THIRD = createFloatKey("shape_variant_modifier/one_third");

  public static final VariantSeries<ContextIntProvider> COMPOSTABLE_MEDIUM = VariantSeries.ofInt(ContextIntProviders.COMPOSTABLE_MEDIUM);
  public static final VariantSeries<ContextIntProvider> COMPOSTABLE_MEDIUM_HIGH = VariantSeries.ofInt(ContextIntProviders.COMPOSTABLE_MEDIUM_HIGH);
  public static final VariantSeries<ContextIntProvider> COOKING_TIME_WOOD_BLOCKS = VariantSeries.ofInt(ContextIntProviders.COOKING_TIME_WOOD_BLOCKS);
  public static final VariantSeries<ContextIntProvider> COOKING_TIME_WOOL = VariantSeries.ofInt(ContextIntProviders.COOKING_TIME_WOOL);
  public static final VariantSeries<ContextIntProvider> COOKING_TIME_COAL_BLOCK = VariantSeries.ofInt(ContextIntProviders.COOKING_TIME_COAL_BLOCK);

  public static ResourceKey<ContextFloatProvider> createFloatKey(String name) {
    return ResourceKey.create(Registries.CONTEXT_FLOAT_PROVIDER, ExtShape.id(name));
  }

  public static ResourceKey<ContextIntProvider> createIntKey(String name) {
    return ResourceKey.create(Registries.CONTEXT_INT_PROVIDER, ExtShape.id(name));
  }

  public record VariantSeries<N>(ResourceKey<N> base, ResourceKey<N> half, ResourceKey<N> quarter, ResourceKey<N> oneThird) {
    public static VariantSeries<ContextFloatProvider> ofFloat(ResourceKey<ContextFloatProvider> base) {
      final String name = base.identifier().getPath();
      return new VariantSeries<>(
          base,
          createFloatKey("shape_variant/" + name + "/half"),
          createFloatKey("shape_variant/" + name + "/quarter"),
          createFloatKey("shape_variant/" + name + "/one_third")
      );
    }

    public static VariantSeries<ContextIntProvider> ofInt(ResourceKey<ContextIntProvider> base) {
      final String name = base.identifier().getPath();
      return new VariantSeries<>(
          base,
          createIntKey("shape_variant/" + name + "/half"),
          createIntKey("shape_variant/" + name + "/quarter"),
          createIntKey("shape_variant/" + name + "/one_third")
      );
    }

    public ResourceKey<N> pickForShape(BlockShape shape) {
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
