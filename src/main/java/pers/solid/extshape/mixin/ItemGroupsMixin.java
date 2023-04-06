package pers.solid.extshape.mixin;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pers.solid.extshape.VanillaItemGroup;
import pers.solid.extshape.config.ExtShapeConfig;

@Mixin(ItemGroups.class)
public abstract class ItemGroupsMixin {
  @Inject(method = "updateEntries", at = @At("HEAD"))
  private static void modifiedUpdateDisplayParameters(ItemGroup.DisplayContext displayContext, CallbackInfo cir) {
    if (ExtShapeConfig.requireUpdateShapesToAddVanilla) {
      MinecraftForge.EVENT_BUS.post(new VanillaItemGroup.UpdateShapesEvent());
      ExtShapeConfig.requireUpdateShapesToAddVanilla = false;
    }
    ExtShapeConfig.requireUpdateDisplay = false;
  }
}
