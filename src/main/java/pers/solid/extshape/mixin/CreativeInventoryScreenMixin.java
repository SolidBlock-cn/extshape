package pers.solid.extshape.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.impl.client.itemgroup.CreativeGuiExtensions;
import net.fabricmc.fabric.impl.itemgroup.FabricItemGroup;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.item.ItemGroups;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.config.ExtShapeConfig;

@Mixin(CreativeInventoryScreen.class)
@Environment(EnvType.CLIENT)
public abstract class CreativeInventoryScreenMixin implements CreativeGuiExtensions {
  @Inject(method = "init", at = @At("RETURN"))
  private void removeIfNeeded(CallbackInfo ci) {
    if (!ExtShapeConfig.CURRENT_CONFIG.showSpecificGroups) {
      final int currentPage = fabric_currentPage();
      ExtShape.LOGGER.info("Current page in the creative inventory screen: {}.", currentPage);
      // ported from fabric_hasGroupForPage
      if (ItemGroups.getGroupsToDisplay().stream()
          .noneMatch(itemGroup -> ((FabricItemGroup) itemGroup).getPage() == currentPage)) {
        ExtShape.LOGGER.info("Invalid page detected. Switching to the previous page.");
        fabric_previousPage();
      }
    }
  }
}
