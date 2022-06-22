package pers.solid.extshape.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.ExtShapeItemGroup;

@Environment(EnvType.CLIENT)
public class ExtShapeOptionsScreen extends Screen {

  private final Screen parent;
  private final GameOptions gameOptions;
  public final ExtShapeConfig newConfig = ExtShapeConfig.CURRENT_CONFIG.clone();

  public ExtShapeOptionsScreen(Screen parent) {
    super(Text.translatable("options.extshape.title"));
    this.parent = parent;
    this.gameOptions = MinecraftClient.getInstance().options;
  }

  @Override
  protected void init() {
    // 里面的内容不需要被选中，所以只是drawable。
    addDrawable(new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 32, 25));
    ExtShapeConfig config = newConfig;
    this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, ScreenTexts.DONE, button -> close()));

    // addToVanillaGroups
    addDrawableChild(SimpleOption.ofBoolean(
        "options.extshape.addToVanillaGroups",
        SimpleOption.constantTooltip(
            Text.translatable("options.extshape.addToVanillaGroups.tooltip", ItemGroup.BUILDING_BLOCKS.getDisplayName(), ItemGroup.DECORATIONS.getDisplayName(), ItemGroup.REDSTONE.getDisplayName())
                .append("\n\n")
                .append(Text.translatable("options.extshape.default", ScreenTexts.onOrOff(ExtShapeConfig.DEFAULT_CONFIG.addToVanillaGroups)).formatted(Formatting.GRAY))),
        config.addToVanillaGroups,
        value -> config.addToVanillaGroups = value
    ).createButton(gameOptions, width / 2 - 205, 36, 200));
    // showSpecificGroups
    addDrawableChild(SimpleOption.ofBoolean(
        "options.extshape.showSpecificGroups",
        SimpleOption.constantTooltip(
            Text.translatable("options.extshape.showSpecificGroups.tooltip")
                .append("\n\n")
                .append(Text.translatable("options.extshape.default", ScreenTexts.onOrOff(ExtShapeConfig.DEFAULT_CONFIG.showSpecificGroups)).formatted(Formatting.GRAY))),
        config.showSpecificGroups,
        value -> config.showSpecificGroups = value
    ).createButton(gameOptions, width / 2 + 5, 36, 200));
    // registerBlockFamilies
    addDrawableChild(SimpleOption.ofBoolean(
        "options.extshape.registerBlockFamilies",
        SimpleOption.constantTooltip(
            Text.translatable("options.extshape.registerBlockFamilies.description")
                .append("\n\n")
                .append(Text.translatable("options.extshape.default", ScreenTexts.onOrOff(ExtShapeConfig.DEFAULT_CONFIG.registerBlockFamilies)).formatted(Formatting.GRAY))),
        config.registerBlockFamilies,
        value -> config.registerBlockFamilies = value
    ).createButton(gameOptions, width / 2 - 205, 61, 200));
    addDrawableChild(new ButtonWidget(width / 2 + 5, 61, 200, 20, Text.translatable("options.extshape.rrp.title"), button -> {
      if (client != null) client.setScreen(new ExtShapeRRPScreen(this));
    }, (button, matrices, mouseX, mouseY) -> renderOrderedTooltip(matrices, textRenderer.wrapLines(Text.translatable("options.extshape.rrp.description"), 200), mouseX, mouseY)));
  }

  @Override
  public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
    super.render(matrices, mouseX, mouseY, delta);
    drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 16, 0xffffff);
    for (Element child : children()) {
      if (child instanceof CyclingButtonWidget<?> widget && widget.isHovered()) {
        renderOrderedTooltip(matrices, widget.getOrderedTooltip(), mouseX, mouseY);
      }
    }
  }

  public void save() {
    final ExtShapeConfig oldConfig = ExtShapeConfig.CURRENT_CONFIG;
    ExtShapeConfig.CURRENT_CONFIG = newConfig;
    ExtShapeConfig.CURRENT_CONFIG.writeFile(ExtShapeConfig.CONFIG_FILE);

    // 应用物品组。
    if (oldConfig.showSpecificGroups != newConfig.showSpecificGroups) {
      if (newConfig.showSpecificGroups) {
        ExtShape.LOGGER.info("Adding item groups at runtime. This may cause some instability.");
        ExtShapeItemGroup.implementGroups();
      } else {
        ExtShape.LOGGER.info("Removing item groups at runtime. This may cause some instability.");
        ExtShapeItemGroup.removeGroups();
      }
    }
  }

  @Override
  public void close() {
    if (client != null && !newConfig.addToVanillaGroups && !newConfig.showSpecificGroups) {
      // 由于两个设置都被关闭，因此需要确认是否不添加到任何物品栏。
      client.setScreen(new ConfirmScreen(
          t -> {
            if (t) {
              // 确定要继续
              save();
              client.setScreen(parent);
            } else {
              // 返回重新修改
              client.setScreen(this);
            }
          },
          Text.translatable("options.extshape.confirm"),
          Text.translatable("options.extshape.confirm.noGroups", Text.translatable("options.extshape.addToVanillaGroups").formatted(Formatting.GRAY), Text.translatable("options.extshape.showSpecificGroups").formatted(Formatting.GRAY), ScreenTexts.OFF),
          ScreenTexts.YES,
          Text.translatable("options.extshape.confirm.redo")
      ));
      return;
    }
    save();
    client.setScreen(parent);
  }
}
