package pers.solid.extshape.mixin;

import net.minecraft.block.AbstractButtonBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractButtonBlock.class)
public interface AbstractButtonBlockAccessor {
  @Invoker
  int invokeGetPressTicks();
}
