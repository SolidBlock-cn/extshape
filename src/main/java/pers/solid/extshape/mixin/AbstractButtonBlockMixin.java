package pers.solid.extshape.mixin;

import net.minecraft.block.AbstractButtonBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pers.solid.extshape.block.ExtShapeButtonBlock;

@Mixin(AbstractButtonBlock.class)
public class AbstractButtonBlockMixin {
  /**
   * 修改按钮的触发时间。
   */
  @Inject(method = "getPressTicks", at = @At("RETURN"), cancellable = true)
  public void getPressTicks(CallbackInfoReturnable<Integer> cir) {
    final AbstractButtonBlock block = (AbstractButtonBlock) (Object) this;
    if (block instanceof ExtShapeButtonBlock button) {
      cir.setReturnValue(button.pressTicks);
    }
  }
}
