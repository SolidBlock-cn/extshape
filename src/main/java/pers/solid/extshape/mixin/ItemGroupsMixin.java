package pers.solid.extshape.mixin;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.config.ExtShapeConfig;

@Mixin(CreativeModeTabs.class)
public abstract class ItemGroupsMixin {
  @Inject(method = "buildAllTabContents", at = @At("HEAD"))
  private static void modifiedUpdateDisplayParameters(CreativeModeTab.ItemDisplayParameters displayContext, CallbackInfo cir) {
    if (ExtShapeConfig.requireUpdateDisplay) {
      ExtShape.LOGGER.info("Updating entries triggered by Extended Block Shapes configuration changes.");
      ExtShapeConfig.requireUpdateDisplay = false;
    }
  }
}
