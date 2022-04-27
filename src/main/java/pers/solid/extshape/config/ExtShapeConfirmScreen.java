package pers.solid.extshape.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.MultilineText;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

/**
 * 当配置的内容存在一些问题时，进行确认
 */
@Environment(EnvType.CLIENT)
public class ExtShapeConfirmScreen extends Screen {
  public final Text text = new TranslatableText("您的 %s 和 %s 都设置为“%s”，这意味着您无法在创造模式物品栏中获取到本模组中的物品。建议您将其中的至少一个开启。确定要继续吗？");
  private final ExtShapeOptionsScreen parent;
  private MultilineText multilineText;
  private int textHeight;

  protected ExtShapeConfirmScreen(ExtShapeOptionsScreen parent) {
    super(new TranslatableText("确认"));
    this.parent = parent;
  }

  @Override
  protected void init() {
    super.init();
    multilineText = MultilineText.create(textRenderer, text, width * 3 / 4);
    textHeight = multilineText.count() * textRenderer.fontHeight;
    addDrawableChild(new ButtonWidget(
        width / 2 - 120, (height - textHeight - 20) / 2 + textHeight,
        100, 20,
        ScreenTexts.YES,
        button -> {
          ExtShapeConfig.CURRENT_CONFIG = parent.newConfig;
          parent.removed();
          if (client != null) {
            client.setScreen(parent.getParent());
          }
        }
    ));
    addDrawableChild(new ButtonWidget(
        width / 2 + 20, (height - textHeight - 20) / 2 + textHeight,
        100, 20,
        new TranslatableText("重新修改"),
        button -> {
          if (client != null) {
            client.setScreen(parent);
          }
        }
    ));
  }

  @Override
  public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
    renderBackground(matrices);
    super.render(matrices, mouseX, mouseY, delta);
    multilineText.drawCenterWithShadow(matrices, width / 2, (height - textHeight - 20) / 2, textRenderer.fontHeight, 0xffffff);
  }
}
