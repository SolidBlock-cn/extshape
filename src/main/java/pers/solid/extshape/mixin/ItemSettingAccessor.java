package pers.solid.extshape.mixin;

import net.minecraft.resources.DependantName;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Item.Properties.class)
public interface ItemSettingAccessor {
  @Accessor
  ResourceKey<Item> getId();

  @Accessor
  DependantName<Item, String> getDescriptionId();

  @Accessor
  void setDescriptionId(DependantName<Item, String> descriptionId);
}
