package pers.solid.extshape.mixin;

import net.minecraft.item.ItemGroups;
import net.minecraft.resource.featuretoggle.FeatureSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pers.solid.extshape.config.ExtShapeConfig;

@Mixin(ItemGroups.class)
public abstract class ItemGroupsMixin {
  private static boolean addExtshapeItems = false;
  private static boolean showExtshapeGroups = false;

  @Inject(method = "displayParametersMatch", at = @At("HEAD"), cancellable = true)
  private static void modifiedDisplayParametersMatch(FeatureSet enabledFeatures, boolean operatorEnabled, CallbackInfoReturnable<Boolean> cir) {
    if (addExtshapeItems != ExtShapeConfig.CURRENT_CONFIG.addToVanillaGroups || showExtshapeGroups != ExtShapeConfig.CURRENT_CONFIG.showSpecificGroups) {
      cir.setReturnValue(false);
    }
  }

  @Inject(method = "updateDisplayParameters", at = @At(value = "FIELD", target = "Lnet/minecraft/item/ItemGroups;operatorEnabled:Z", shift = At.Shift.AFTER))
  private static void modifiedUpdateDisplayParameters(FeatureSet enabledFeatures, boolean operatorEnabled, CallbackInfoReturnable<Boolean> cir) {
    addExtshapeItems = ExtShapeConfig.CURRENT_CONFIG.addToVanillaGroups;
    showExtshapeGroups = ExtShapeConfig.CURRENT_CONFIG.showSpecificGroups;
  }
}
