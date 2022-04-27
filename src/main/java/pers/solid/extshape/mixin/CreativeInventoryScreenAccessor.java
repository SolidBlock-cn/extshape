package pers.solid.extshape.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(CreativeInventoryScreen.class)
public interface CreativeInventoryScreenAccessor {
  @Accessor
  static int getSelectedTab() {
    throw new AssertionError("Mixin not loaded!");
  }

  @Accessor
  static void setSelectedTab(int selectedTab) {
    throw new AssertionError("Mixin not loaded!");
  }
}
