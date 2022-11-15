package pers.solid.extshape.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @see net.fabricmc.fabric.impl.itemgroup.CreativeGuiExtensions
 * @see net.fabricmc.fabric.mixin.itemgroup.client.CreativeInventoryScreenMixin
 */
@Mixin(CreativeInventoryScreen.class)
@Environment(EnvType.CLIENT)
public abstract class CreativeInventoryScreenMixin extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> /*implements CreativeGuiExtensions*/ {
  public CreativeInventoryScreenMixin(CreativeInventoryScreen.CreativeScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
    super(screenHandler, playerInventory, text);
  }

  /*  *//**
   * @see net.fabricmc.fabric.mixin.item.group.client.MixinCreativePlayerInventoryGui#fabric_getPageOffset(int)
   *//*
  @SuppressWarnings("JavadocReference")
  private static int fabric_getPageOffset_copied(int page) {
    return switch (page) {
      case 0 -> 0;
      case 1 -> 12;
      default -> 12 + ((12 - FabricCreativeGuiComponents.COMMON_GROUPS.size()) * (page - 1));
    };
  }*/

  /**
   * 避免因为页数超出而抛出 {@link ArrayIndexOutOfBoundsException}。
   *//*
  @Inject(method = "init", at = @At("HEAD"))
  private void injectedInit(CallbackInfo ci) {
    while (fabric_getPageOffset_copied(fabric_currentPage()) >= ItemGroup.GROUPS.length) {
      fabric_previousPage();
    }
  }*/
}
