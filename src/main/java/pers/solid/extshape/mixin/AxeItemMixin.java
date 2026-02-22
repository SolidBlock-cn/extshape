package pers.solid.extshape.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import pers.solid.extshape.ExtShape;

import java.util.Optional;

@Mixin(AxeItem.class)
public class AxeItemMixin {
  @ModifyReturnValue(method = "getStripped", at = @At("RETURN"))
  private Optional<BlockState> getStrippedExtendedState(Optional<BlockState> original, @Local(argsOnly = true) BlockState state) {
    if (original.isEmpty() && ExtShape.EXTENDED_STRIPPABLE_BLOCKS.containsKey(state.getBlock())) {
      return Optional.ofNullable(ExtShape.EXTENDED_STRIPPABLE_BLOCKS.get(state.getBlock()).withPropertiesOf(state));
    }
    return original;
  }
}
