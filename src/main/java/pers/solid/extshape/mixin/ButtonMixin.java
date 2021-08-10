package pers.solid.extshape.mixin;

import net.minecraft.block.AbstractButtonBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pers.solid.extshape.block.ExtShapeButtonBlock;

@Mixin(net.minecraft.block.AbstractButtonBlock.class)
public class ButtonMixin {
    @Inject(method = "getPressTicks", at = @At("RETURN"), cancellable = true)
    public void getPressTicks(CallbackInfoReturnable<Integer> cir) {
        final AbstractButtonBlock block = (AbstractButtonBlock) (Object) this;
        if (block instanceof ExtShapeButtonBlock && ((ExtShapeButtonBlock) block).type == ExtShapeButtonBlock.ButtonType.hard) {
            cir.setReturnValue(5);
            cir.cancel();
        } else if (block instanceof ExtShapeButtonBlock && ((ExtShapeButtonBlock) block).type == ExtShapeButtonBlock.ButtonType.soft) {
            cir.setReturnValue(60);
            cir.cancel();
        }
    }
}
