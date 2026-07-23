package pers.solid.extshape.number;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.Mth;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

/**
 * 用于将多个数值提供器相乘的数值提供器。
 */
public record ProductNumberProvider(HolderSet<NumberProvider> values) implements NumberProvider {
  public static final MapCodec<ProductNumberProvider> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
      NumberProviders.LIST_CODEC.fieldOf("values").forGetter(ProductNumberProvider::values)
  ).apply(i, ProductNumberProvider::new));

  @Override
  public int getInt(LootContext context) {
    return Mth.floor(getFloat(context)); // 参照 sum 的做法
  }

  @Override
  public float getFloat(LootContext context) {
    float result = 1;
    for (Holder<NumberProvider> value : values) {
      result *= value.value().getFloat(context);
    }
    return result;
  }

  @Override
  public MapCodec<? extends NumberProvider> codec() {
    return MAP_CODEC;
  }
}
