package pers.solid.extshape.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.ExtShapeItemGroup;

@Environment(EnvType.CLIENT)
public class ExtShapeOptionsScreen extends GameOptionsScreen {

  public ButtonListWidget list;
  public final ExtShapeConfig newConfig = ExtShapeConfig.CURRENT_CONFIG.clone();

  public ExtShapeOptionsScreen(Screen parent) {
    super(parent, MinecraftClient.getInstance().options, new TranslatableText("options.extshape.title"));
  }

  @Override
  protected void init() {
    list = new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
    addOptions(newConfig);
    this.addDrawableChild(list);
    this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, ScreenTexts.DONE, (button) -> onClose()));
  }

  @Override
  public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
    super.render(matrices, mouseX, mouseY, delta);
    drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 16, 0xffffff);
    renderOrderedTooltip(matrices, getHoveredButtonTooltip(list, mouseX, mouseY), mouseX, mouseY);
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
  public void onClose() {
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
          new TranslatableText("options.extshape.confirm"),
          new TranslatableText("options.extshape.confirm.noGroups", new TranslatableText("options.extshape.addToVanillaGroups").formatted(Formatting.GRAY), new TranslatableText("options.extshape.showSpecificGroups").formatted(Formatting.GRAY), ScreenTexts.OFF),
          ScreenTexts.YES,
          new TranslatableText("options.extshape.confirm.redo")
      ));
      return;
    }
    save();
    client.setScreen(parent);
  }

  public void addOptions(ExtShapeConfig config) {
    list.addOptionEntry(
        CyclingOption.create("options.extshape.addToVanillaGroups", ScreenTexts.ON, ScreenTexts.OFF, gameOptions -> config.addToVanillaGroups, (gameOptions, option, value) -> config.addToVanillaGroups = value)
            .tooltip(client -> b -> client.textRenderer.wrapLines(
                new TranslatableText("options.extshape.addToVanillaGroups.tooltip", ItemGroup.BUILDING_BLOCKS.getDisplayName(), ItemGroup.DECORATIONS.getDisplayName(), ItemGroup.REDSTONE.getDisplayName())
                    .append("\n\n")
                    .append(new TranslatableText("options.extshape.default", ScreenTexts.onOrOff(ExtShapeConfig.DEFAULT_CONFIG.addToVanillaGroups)).formatted(Formatting.GRAY)), 256)),
        CyclingOption.create("options.extshape.showSpecificGroups", ScreenTexts.ON, ScreenTexts.OFF, gameOptions -> config.showSpecificGroups, (gameOptions, option, value) -> config.showSpecificGroups = value)
            .tooltip(client -> b -> client.textRenderer.wrapLines(
                new TranslatableText("options.extshape.showSpecificGroups.tooltip")
                    .append("\n\n")
                    .append(new TranslatableText("options.extshape.default", ScreenTexts.onOrOff(ExtShapeConfig.DEFAULT_CONFIG.showSpecificGroups)).formatted(Formatting.GRAY)), 256))
    );
    list.addOptionEntry(
        CyclingOption.create("options.extshape.registerBlockFamilies", ScreenTexts.ON, ScreenTexts.OFF, gameOptions -> config.registerBlockFamilies, (gameOptions, option, value) -> config.registerBlockFamilies = value)
            .tooltip(client -> b -> client.textRenderer.wrapLines(
                new TranslatableText("options.extshape.registerBlockFamilies.description")
                    .append("\n\n")
                    .append(new TranslatableText("options.extshape.default", ScreenTexts.onOrOff(ExtShapeConfig.DEFAULT_CONFIG.registerBlockFamilies)).formatted(Formatting.GRAY)), 256)),
        new Option("options.extshape.rrp.title") {
          @Override
          public ClickableWidget createButton(GameOptions options, int x, int y, int width) {
            return new ButtonWidget(x, y, width, 20, getDisplayPrefix(), button -> {
              if (client != null) client.setScreen(new ExtShapeRRPScreen(ExtShapeOptionsScreen.this));
            });
          }
        }
    );
  }
}
