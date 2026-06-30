package pers.solid.extshape.mixin;

import it.unimi.dsi.fastutil.objects.Object2IntSortedMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.FuelValues;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FuelValues.Builder.class)
public interface FuelValuesBuilderAccessor {
  @Accessor
  Object2IntSortedMap<Item> getValues();
}
