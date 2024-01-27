package pers.solid.extshape.mixin;

import net.minecraft.block.BlockSetType;
import net.minecraft.block.ButtonBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ButtonBlock.class)
public interface ButtonBlockAccessor {
  @Accessor
  BlockSetType getBlockSetType();

  @Accessor
  int getPressTicks();
}
