package pers.solid.extshape.mixin;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTab;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pers.solid.extshape.config.ExtShapeConfig;

@Mixin(CreativeModeTab.ItemDisplayParameters.class)
public class DisplayContextMixin {
  @Inject(method = "needsUpdate", at = @At("HEAD"), cancellable = true)
  private void modifyDoesNotMatch(FeatureFlagSet enabledFeatures, boolean hasPermissions, HolderLookup.Provider lookup, CallbackInfoReturnable<Boolean> cir) {
    if (ExtShapeConfig.requireUpdateDisplay) {
      cir.setReturnValue(true);
    }
  }
}
