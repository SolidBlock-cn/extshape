package pers.solid.extshape.mixin;

import net.minecraft.item.ItemGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemGroup.class)
public interface ItemGroupAccessor {
  @Accessor("GROUPS")
  static void setGroups(ItemGroup[] groups) {
    throw new AssertionError("Mixin is not loaded!");
  }

  @Accessor
  @Mutable
  void setIndex(int i);
}
