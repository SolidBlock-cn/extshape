package pers.solid.extshape.mixin;

import net.minecraft.item.ItemGroup;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.resource.featuretoggle.FeatureSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pers.solid.extshape.config.ExtShapeConfig;

@Mixin(ItemGroup.DisplayContext.class)
public class DisplayContextMixin {
  @Inject(method = "doesNotMatch", at = @At("HEAD"), cancellable = true)
  private void modifyDoesNotMatch(FeatureSet enabledFeatures, boolean hasPermissions, RegistryWrapper.WrapperLookup lookup, CallbackInfoReturnable<Boolean> cir) {
    if (ExtShapeConfig.requireUpdateDisplay) {
      cir.setReturnValue(true);
    }
  }
}
