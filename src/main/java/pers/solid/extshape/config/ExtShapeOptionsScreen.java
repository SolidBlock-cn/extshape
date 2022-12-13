package pers.solid.extshape.config;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemGroups;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import org.apache.commons.lang3.StringUtils;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.ExtShapeItemGroup;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.rrp.ExtShapeRRP;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Environment(EnvType.CLIENT)
public class ExtShapeOptionsScreen extends Screen {

  private final Screen parent;
  private final GameOptions gameOptions = MinecraftClient.getInstance().options;
  public final ExtShapeConfig oldConfig = ExtShapeConfig.CURRENT_CONFIG;
  public final ExtShapeConfig newConfig = ExtShapeConfig.CURRENT_CONFIG.clone();
  private final TextFieldWidget shapesToAddToVanillaTextField = Util.make(new TextFieldWidget(MinecraftClient.getInstance().textRenderer, width / 2 - 205, 76, 358, 20, Text.translatable("options.extshape.shapesToAddToVanilla")), textFieldWidget -> {
    textFieldWidget.setMaxLength(Integer.MAX_VALUE);
    textFieldWidget.setText(convertCollectionToString(newConfig.shapesToAddToVanilla));
    textFieldWidget.setEditable(newConfig.addToVanillaGroups);
    textFieldWidget.setChangedListener(s -> {
      newConfig.shapesToAddToVanilla = convertStringToCollection(s);
      textFieldWidget.setSuggestion(getSuggestion(textFieldWidget.getText()));
    });
  });
  private final ButtonWidget resetShapesToAddToVanillaButton = ButtonWidget.builder(Text.translatable("options.extshape.reset"), button -> shapesToAddToVanillaTextField.setText(convertCollectionToString(ExtShapeConfig.DEFAULT_CONFIG.shapesToAddToVanilla)))
      .dimensions(width / 2 + 155, 76, 50, 20)
      .build();
  private final TextFieldWidget shapesInSpecificGroupsTextField = Util.make(new TextFieldWidget(MinecraftClient.getInstance().textRenderer, width / 2 - 205, 121, 358, 20, Text.translatable("options.extshape.shapesInSpecificGroups")), textFieldWidget -> {
    textFieldWidget.setMaxLength(Integer.MAX_VALUE);
    textFieldWidget.setText(convertCollectionToString(newConfig.shapesInSpecificGroups));
    textFieldWidget.setEditable(newConfig.showSpecificGroups);
    textFieldWidget.setChangedListener(s -> {
      newConfig.shapesInSpecificGroups = convertStringToCollection(s);
      textFieldWidget.setSuggestion(getSuggestion(textFieldWidget.getText()));
    });
  });
  private final ButtonWidget resetShapesInSpecificGroupsButton = ButtonWidget.builder(Text.translatable("options.extshape.reset"), button -> shapesInSpecificGroupsTextField.setText(convertCollectionToString(ExtShapeConfig.DEFAULT_CONFIG.shapesInSpecificGroups)))
      .dimensions(width / 2 + 155, 121, 50, 20)
      .build();
  private final ClickableWidget addToVanillaGroupsButton = SimpleOption.ofBoolean(
      "options.extshape.addToVanillaGroups",
      SimpleOption.constantTooltip(
          Text.translatable("options.extshape.addToVanillaGroups.tooltip", ItemGroups.BUILDING_BLOCKS.getDisplayName(), ItemGroups.COLORED_BLOCKS.getDisplayName(), ItemGroups.NATURAL.getDisplayName())
              .append("\n\n")
              .append(Text.translatable("options.extshape.default", ScreenTexts.onOrOff(ExtShapeConfig.DEFAULT_CONFIG.addToVanillaGroups)).formatted(Formatting.GRAY))),
      newConfig.addToVanillaGroups,
      value -> {
        newConfig.addToVanillaGroups = value;
        shapesToAddToVanillaTextField.setEditable(value);
      }
  ).createButton(gameOptions, width / 2 - 205, 36, 200);

  private final ClickableWidget showSpecificGroupsButton = SimpleOption.ofBoolean(
      "options.extshape.showSpecificGroups",
      SimpleOption.constantTooltip(
          Text.translatable("options.extshape.showSpecificGroups.tooltip")
              .append("\n\n")
              .append(Text.translatable("options.extshape.default", ScreenTexts.onOrOff(ExtShapeConfig.DEFAULT_CONFIG.showSpecificGroups)).formatted(Formatting.GRAY))),
      newConfig.showSpecificGroups,
      value -> {
        newConfig.showSpecificGroups = value;
        shapesInSpecificGroupsTextField.setEditable(value);
      }
  ).createButton(gameOptions, width / 2 + 5, 36, 200);


  private final ClickableWidget registerBlockFamiliesButton = SimpleOption.ofBoolean(
      "options.extshape.registerBlockFamilies",
      SimpleOption.constantTooltip(
          Text.translatable("options.extshape.registerBlockFamilies.description")
              .append("\n\n")
              .append(Text.translatable("options.extshape.default", ScreenTexts.onOrOff(ExtShapeConfig.DEFAULT_CONFIG.registerBlockFamilies)).formatted(Formatting.GRAY))),
      newConfig.registerBlockFamilies,
      value -> newConfig.registerBlockFamilies = value
  ).createButton(gameOptions, width / 2 - 205, 151, 200);


  private final ClickableWidget preventWoodenWallRecipesButton = SimpleOption.ofBoolean(
      "options.extshape.preventWoodenWallRecipes",
      SimpleOption.constantTooltip(
          Text.translatable("options.extshape.preventWoodenWallRecipes.tooltip")
              .append("\n\n")
              .append(Text.translatable("options.extshape.default", ScreenTexts.onOrOff(ExtShapeConfig.DEFAULT_CONFIG.preventWoodenWallRecipes)).formatted(Formatting.GRAY))),
      newConfig.preventWoodenWallRecipes,
      value -> newConfig.preventWoodenWallRecipes = value
  ).createButton(gameOptions, width / 2 + 5, 151, 200);

  private final ClickableWidget avoidSomeButtonRecipesButton = SimpleOption.ofBoolean(
      "options.extshape.avoidSomeButtonRecipes",
      SimpleOption.constantTooltip(
          Text.translatable("options.extshape.avoidSomeButtonRecipes.tooltip")
              .append("\n\n")
              .append(Text.translatable("options.extshape.default", ScreenTexts.onOrOff(ExtShapeConfig.DEFAULT_CONFIG.avoidSomeButtonRecipes)).formatted(Formatting.GRAY))),
      newConfig.avoidSomeButtonRecipes,
      value -> newConfig.avoidSomeButtonRecipes = value
  ).createButton(gameOptions, width / 2 - 205, 171, 200);

  private final ClickableWidget specialPressurePlateRecipesButton = SimpleOption.ofBoolean(
      "options.extshape.specialSnowSlabCrafting",
      SimpleOption.constantTooltip(
          Text.translatable("options.extshape.specialSnowSlabCrafting.tooltip")
              .append("\n\n")
              .append(Text.translatable("options.extshape.default", ScreenTexts.onOrOff(ExtShapeConfig.DEFAULT_CONFIG.specialSnowSlabCrafting)).formatted(Formatting.GRAY))),
      newConfig.specialSnowSlabCrafting,
      value -> newConfig.specialSnowSlabCrafting = value
  ).createButton(gameOptions, width / 2 + 5, 171, 200);

  // 运行时资源包设置。
  private final ButtonWidget rrpOptionsButton = new ButtonWidget.Builder(Text.translatable("options.extshape.rrp.title"), button -> {
    if (client != null) client.setScreen(new ExtShapeRRPScreen(this));
  }).position(width / 2 - 205, 191).size(200, 20)
      .tooltip(Tooltip.of(Text.translatable("options.extshape.rrp.description")))
      .build();
  private final ButtonWidget reasonableSortingButton = new ButtonWidget.Builder(Text.translatable("options.extshape.reasonable-sorting"), button -> {
    if (client != null) {
      try {
        ExtShape.LOGGER.warn("Reasonable Sorting implementation is not yet supported in 1.19.3!");
      } catch (LinkageError e) {
        ExtShape.LOGGER.error("Failed to open Reasonable Sorting config screen:", e);
      }
    }
  }).position(width / 2 + 5, 191).size(200, 20)
      .tooltip(Tooltip.of(Text.translatable("options.extshape.reasonable-sorting.description")))
      .build();

  // 完成按钮
  private final ButtonWidget finishButton = new ButtonWidget.Builder(ScreenTexts.DONE, button -> close()).position(this.width / 2 - 100, this.height - 27).size(200, 20).build();

  public ExtShapeOptionsScreen(Screen parent) {
    super(Text.translatable("options.extshape.title"));
    this.parent = parent;
  }

  @Override
  protected void init() {
    // 里面的内容不需要被选中，所以只是drawable。
    addDrawable(new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 32, 25));

    addToVanillaGroupsButton.setX(width / 2 - 205);
    addDrawableChild(addToVanillaGroupsButton);
    showSpecificGroupsButton.setX(width / 2 + 5);
    addDrawableChild(showSpecificGroupsButton);
    shapesToAddToVanillaTextField.setX(width / 2 - 205);
    addDrawableChild(shapesToAddToVanillaTextField);
    resetShapesToAddToVanillaButton.setX(width / 2 + 155);
    addDrawableChild(resetShapesToAddToVanillaButton);
    shapesInSpecificGroupsTextField.setX(width / 2 - 205);
    addDrawableChild(shapesInSpecificGroupsTextField);
    resetShapesInSpecificGroupsButton.setX(width / 2 + 155);
    addDrawableChild(resetShapesInSpecificGroupsButton);
    registerBlockFamiliesButton.setX(width / 2 - 205);
    addDrawableChild(registerBlockFamiliesButton);

    preventWoodenWallRecipesButton.setX(width / 2 + 5);
    addDrawableChild(preventWoodenWallRecipesButton);
    avoidSomeButtonRecipesButton.setX(width / 2 - 205);
    addDrawableChild(avoidSomeButtonRecipesButton);
    specialPressurePlateRecipesButton.setX(width / 2 + 5);
    addDrawableChild(specialPressurePlateRecipesButton);
    rrpOptionsButton.setX(width / 2 - 205);
    addDrawableChild(rrpOptionsButton);

    reasonableSortingButton.active = client != null && FabricLoader.getInstance().isModLoaded("reasonable-sorting");
    reasonableSortingButton.setX(width / 2 + 5);
    addDrawableChild(reasonableSortingButton);

    finishButton.setPos(width / 2 - 100, height - 27);
    addDrawableChild(finishButton);
  }

  @Override
  public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
    super.render(matrices, mouseX, mouseY, delta);
    drawCenteredTextWithShadow(matrices, this.textRenderer, this.title.asOrderedText(), this.width / 2, 16, 0xffffff);
    drawTextWithShadow(matrices, textRenderer, Text.translatable("options.extshape.shapesToAddToVanilla.description"), width / 2 - 205, 61, 0xffffff);
    drawTextWithShadow(matrices, textRenderer, Text.translatable("options.extshape.shapesInSpecificGroups.description"), width / 2 - 205, 106, 0xffffff);
  }

  public void save() {
    final ExtShapeConfig oldConfig = ExtShapeConfig.CURRENT_CONFIG;
    ExtShapeConfig.CURRENT_CONFIG = newConfig;
    if (!oldConfig.equals(newConfig)) {
      ExtShapeConfig.CURRENT_CONFIG.tryWriteFile(ExtShapeConfig.CONFIG_FILE);
    }

    if (oldConfig.showSpecificGroups != newConfig.showSpecificGroups
        || oldConfig.addToVanillaGroups != newConfig.addToVanillaGroups
        || !oldConfig.shapesToAddToVanilla.equals(newConfig.shapesToAddToVanilla)
        || !oldConfig.shapesInSpecificGroups.equals(newConfig.shapesInSpecificGroups)) {
      ExtShapeConfig.requireUpdateDisplay = true;
    }
    if (!oldConfig.shapesToAddToVanilla.equals(newConfig.shapesToAddToVanilla)) {
      ExtShapeConfig.requireUpdateShapesToAddVanilla = true;
    }

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

  private static Collection<BlockShape> convertStringToCollection(String s) {
    return Arrays.stream(StringUtils.split(s)).map(BlockShape::byName).filter(Objects::nonNull).collect(ImmutableSet.toImmutableSet());
  }

  private static String convertCollectionToString(Collection<BlockShape> list) {
    return list.stream().map(BlockShape::asString).collect(Collectors.joining(StringUtils.SPACE));
  }

  private static String getSuggestion(String currentValue) {
    final String[] split = StringUtils.split(currentValue);
    if (split.length == 0) return null;
    final String last = split[split.length - 1];
    if (StringUtils.isBlank(last)) {
      return null;
    }
    for (BlockShape value : BlockShape.values()) {
      final String name = value.asString();
      if (name.startsWith(last)) {
        return name.substring(last.length());
      }
    }
    return null;
  }
}
