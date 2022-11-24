package pers.solid.extshape.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.Tooltip;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemGroups;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.ExtShapeRRP;

@Environment(EnvType.CLIENT)
public class ExtShapeOptionsScreen extends Screen {

  private final Screen parent;
  private final GameOptions gameOptions;
  public final ExtShapeConfig oldConfig = ExtShapeConfig.CURRENT_CONFIG;
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
    this.addDrawableChild(new ButtonWidget.Builder(ScreenTexts.DONE, button -> close()).position(this.width / 2 - 100, this.height - 27).size(200, 20).build());

    // addToVanillaGroups
    addDrawableChild(SimpleOption.ofBoolean(
        "options.extshape.addToVanillaGroups",
        SimpleOption.constantTooltip(
            Text.translatable("options.extshape.addToVanillaGroups.tooltip", ItemGroups.BUILDING_BLOCKS.getDisplayName(), ItemGroups.FUNCTIONAL.getDisplayName(), ItemGroups.REDSTONE.getDisplayName())
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

    // preventWoodenWallRecipes
    addDrawableChild(SimpleOption.ofBoolean(
        "options.extshape.preventWoodenWallRecipes",
        SimpleOption.constantTooltip(
            Text.translatable("options.extshape.preventWoodenWallRecipes.tooltip")
                .append("\n\n")
                .append(Text.translatable("options.extshape.default", ScreenTexts.onOrOff(ExtShapeConfig.DEFAULT_CONFIG.preventWoodenWallRecipes)).formatted(Formatting.GRAY))),
        config.preventWoodenWallRecipes,
        value -> config.preventWoodenWallRecipes = value
    ).createButton(gameOptions, width / 2 + 5, 61, 200));
    // avoidSomeButtonRecipes
    addDrawableChild(SimpleOption.ofBoolean(
        "options.extshape.avoidSomeButtonRecipes",
        SimpleOption.constantTooltip(
            Text.translatable("options.extshape.avoidSomeButtonRecipes.tooltip")
                .append("\n\n")
                .append(Text.translatable("options.extshape.default", ScreenTexts.onOrOff(ExtShapeConfig.DEFAULT_CONFIG.avoidSomeButtonRecipes)).formatted(Formatting.GRAY))),
        config.avoidSomeButtonRecipes,
        value -> config.avoidSomeButtonRecipes = value
    ).createButton(gameOptions, width / 2 - 205, 86, 200));
    // specialPressurePlateRecipes
    addDrawableChild(SimpleOption.ofBoolean(
        "options.extshape.specialSnowSlabCrafting",
        SimpleOption.constantTooltip(
            Text.translatable("options.extshape.specialSnowSlabCrafting.tooltip")
                .append("\n\n")
                .append(Text.translatable("options.extshape.default", ScreenTexts.onOrOff(ExtShapeConfig.DEFAULT_CONFIG.specialSnowSlabCrafting)).formatted(Formatting.GRAY))),
        config.specialSnowSlabCrafting,
        value -> config.specialSnowSlabCrafting = value
    ).createButton(gameOptions, width / 2 + 5, 86, 200));

    // 运行时资源包设置。
    addDrawableChild(new ButtonWidget.Builder(Text.translatable("options.extshape.rrp.title"), button -> {
      if (client != null) client.setScreen(new ExtShapeRRPScreen(this));
    }).position(width / 2 - 150, 111).size(300, 20)
        .tooltip(Tooltip.of(Text.translatable("options.extshape.rrp.description")))
        .build());

    {
      final ButtonWidget reasonableSortingButton = new ButtonWidget.Builder(Text.translatable("options.extshape.reasonable-sorting"), button -> {
        if (client != null) {
          try {
//            client.setScreen(ConfigScreenFabric.INSTANCE.createScreen(this)); todo
          } catch (LinkageError e) {
            ExtShape.LOGGER.error("Failed to open Reasonable Sorting config screen:", e);
          }
        }
      }).position(width / 2 - 150, 135).setSize(300, 20)
          .setTooltip(Tooltip.of(Text.translatable("options.extshape.reasonable-sorting.description")))
          .build();
      reasonableSortingButton.active = client != null && FabricLoader.getInstance().isModLoaded("reasonable-sorting");
      addDrawableChild(reasonableSortingButton);
    }
  }

  @Override
  public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
    super.render(matrices, mouseX, mouseY, delta);
    drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 16, 0xffffff);
    for (Element child : children()) {
      if (child instanceof CyclingButtonWidget<?> widget && widget.isHovered()) {
//        renderOrderedTooltip(matrices, widget.method_47400();, mouseX, mouseY);
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
//        ExtShapeItemGroup.implementGroups();
      } else {
        ExtShape.LOGGER.info("Removing item groups at runtime. This may cause some instability.");
//        ExtShapeItemGroup.removeGroups();
      }
    }
  }

  private boolean suppressedGroupsWarning = false;
  private boolean suppressedDataWarning = false;

  @Override
  public void close() {
    assert client != null;
    if (!suppressedGroupsWarning && !newConfig.addToVanillaGroups && !newConfig.showSpecificGroups
        && !(!oldConfig.addToVanillaGroups && !oldConfig.showSpecificGroups)) {
      // 由于两个设置都被关闭，因此需要确认是否不添加到任何物品栏。
      client.setScreen(new ConfirmScreen(
          t -> {
            if (t) {
              // 确定要继续
              suppressedGroupsWarning = true;
              close();
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
    if (!suppressedDataWarning && (newConfig.preventWoodenWallRecipes != oldConfig.preventWoodenWallRecipes
        || newConfig.avoidSomeButtonRecipes != oldConfig.avoidSomeButtonRecipes || newConfig.specialSnowSlabCrafting != oldConfig.specialSnowSlabCrafting)) {
      client.setScreen(new ConfirmScreen(
          t -> {
            suppressedDataWarning = true;
            close();
            if (t) {
              ExtShapeRRP.STANDARD_PACK.clearResources();
              ExtShapeRRP.generateServerData(ExtShapeRRP.STANDARD_PACK);
              client.inGameHud.getChatHud().addMessage(
                  Text.translatable("options.dataChanged.finish",
                      Text.literal("/reload").styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/reload"))).formatted(Formatting.GRAY)));
            }
          },
          Text.translatable("options.extshape.dataChanged"),
          Text.translatable("options.extshape.dataChanged.details")
      ));
      return;
    }
    save();
    client.setScreen(parent);
  }
}
