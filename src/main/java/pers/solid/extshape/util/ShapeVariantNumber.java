package pers.solid.extshape.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.jetbrains.annotations.ApiStatus;

/**
 * 用于实现本模组中基于基础方块的指定倍率的堆肥概率的数值提供器。目前未被正式使用。
 * todo 若后续版本仍仅在物品属性中引用数值提供器 ID 而不支持内联，此类今后将被移除。
 */
@ApiStatus.Experimental
public record ShapeVariantNumber(ResourceKey<NumberProvider> baseName, float multiplier) implements NumberProvider {
  public static final MapCodec<ShapeVariantNumber> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
      ResourceKey.codec(Registries.NUMBER_PROVIDER).fieldOf("base_name").forGetter(ShapeVariantNumber::baseName),
      Codec.FLOAT.optionalFieldOf("multiplier", 1f).forGetter(ShapeVariantNumber::multiplier)
  ).apply(i, ShapeVariantNumber::new));

  @Override
  public float getFloat(LootContext context) {
    return context.getResolver().get(baseName).map(ref -> ref.value().getFloat(context) * multiplier).orElse(0f);
  }

  @Override
  public MapCodec<? extends NumberProvider> codec() {
    return MAP_CODEC;
  }
}
