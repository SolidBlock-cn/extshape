package pers.solid.extshape.mixin;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Item.Properties.class)
public interface ItemSettingAccessor {
  @Accessor
  ResourceKey<Item> getId();
}
