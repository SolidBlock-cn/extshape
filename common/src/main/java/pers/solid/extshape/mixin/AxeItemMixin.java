package pers.solid.extshape.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pers.solid.extshape.ExtShape;

import java.util.Optional;

@Mixin(AxeItem.class)
public class AxeItemMixin {
  @Inject(method = "getStrippedState", at = @At("RETURN"), cancellable = true)
  private void getStrippedExtendedState(BlockState state, CallbackInfoReturnable<Optional<BlockState>> cir) {
    final Optional<BlockState> v = cir.getReturnValue();
    if (v.isEmpty() && ExtShape.EXTENDED_STRIPPABLE_BLOCKS.containsKey(state.getBlock())) {
      cir.setReturnValue(Optional.ofNullable(ExtShape.EXTENDED_STRIPPABLE_BLOCKS.get(state.getBlock()).getStateWithProperties(state)));
    }
  }
}
