package pers.solid.extshape.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.ExtShapeItemGroup;

@Environment(EnvType.CLIENT)
public class ExtShapeOptionsScreen extends GameOptionsScreen {

  public ButtonListWidget list;
  public ExtShapeConfig newConfig = ExtShapeConfig.CURRENT_CONFIG.clone();

  public ExtShapeOptionsScreen(Screen parent) {
    super(parent, MinecraftClient.getInstance().options, new TranslatableText("扩展方块形状模组配置屏幕"));
  }

  @Override
  protected void init() {
    list = new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
    newConfig.addOptionsTo(list);
    this.addDrawableChild(list);
    this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, ScreenTexts.DONE, (button) -> onClose()));
  }

  @Override
  public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
    super.render(matrices, mouseX, mouseY, delta);
    drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 5, 0xffffff);
    renderOrderedTooltip(matrices, getHoveredButtonTooltip(list, mouseX, mouseY), mouseX, mouseY);
  }

  public void save() {
    final ExtShapeConfig oldConfig = ExtShapeConfig.CURRENT_CONFIG;
    ExtShapeConfig.CURRENT_CONFIG = newConfig;
    ExtShapeConfig.save();

    // 应用物品组。
    if (oldConfig.hasSpecificGroup != newConfig.hasSpecificGroup) {
      if (newConfig.hasSpecificGroup) {
        ExtShape.LOGGER.info("Adding item groups at runtime. This may cause some instability.");
        ExtShapeItemGroup.implementGroups();
      } else {
        ExtShape.LOGGER.info("Adding item groups at runtime. This may cause some instability.");
        ExtShapeItemGroup.removeGroups();
      }
    }
  }

  @Override
  public void onClose() {
    if (client != null && !newConfig.addToVanillaGroup && !newConfig.hasSpecificGroup) {
      client.setScreen(new ExtShapeConfirmScreen(this));
      return;
    }
    save();
    client.setScreen(parent);
  }

  protected Screen getParent() {
    return parent;
  }
}
