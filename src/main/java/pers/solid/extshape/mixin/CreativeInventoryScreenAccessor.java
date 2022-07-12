package pers.solid.extshape.mixin;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@OnlyIn(Dist.CLIENT)
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

  @Accessor
  static void setTabPage(int tabPage) {
    throw new AssertionError("Mixin not loaded!");
  }
}
