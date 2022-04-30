package pers.solid.extshape.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.impl.item.group.CreativeGuiExtensions;
import net.fabricmc.fabric.impl.item.group.FabricCreativeGuiComponents;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @see net.fabricmc.fabric.impl.item.group.CreativeGuiExtensions
 * @see net.fabricmc.fabric.mixin.item.group.client.MixinCreativePlayerInventoryGui
 */
@Mixin(CreativeInventoryScreen.class)
@Environment(EnvType.CLIENT)
public abstract class CreativeInventoryScreenMixin extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> implements CreativeGuiExtensions {
  @SuppressWarnings("unused")
  private CreativeInventoryScreenMixin(CreativeInventoryScreen.CreativeScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
    super(screenHandler, playerInventory, text);
  }

  /**
   * @see net.fabricmc.fabric.mixin.item.group.client.MixinCreativePlayerInventoryGui#fabric_getPageOffset(int)
   */
  @SuppressWarnings("JavadocReference")
  private int fabric_getPageOffset(int page) {
    return switch (page) {
      case 0 -> 0;
      case 1 -> 12;
      default -> 12 + ((12 - FabricCreativeGuiComponents.COMMON_GROUPS.size()) * (page - 1));
    };
  }

  /**
   * 避免因为页数超出而抛出 {@link ArrayIndexOutOfBoundsException}。
   */
  @Inject(method = "init", at = @At("HEAD"))
  private void injectedInit(CallbackInfo ci) {
    if (fabric_getPageOffset(fabric_currentPage()) >= ItemGroup.GROUPS.length) {
      fabric_previousPage();
    }
  }
}
