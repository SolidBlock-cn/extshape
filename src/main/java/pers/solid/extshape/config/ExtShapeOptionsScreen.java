package pers.solid.extshape.config;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import org.apache.commons.lang3.StringUtils;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.ExtShapeItemGroup;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.rrp.ExtShapeRRP;
import pers.solid.mod.fabric.ConfigScreenFabric;

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
  private final TextFieldWidget shapesToAddToVanillaTextField = Util.make(new TextFieldWidget(MinecraftClient.getInstance().textRenderer, width / 2 - 205, 76, 358, 20, new TranslatableText("options.extshape.shapesToAddToVanilla")), textFieldWidget -> {
    textFieldWidget.setMaxLength(Integer.MAX_VALUE);
    textFieldWidget.setText(convertCollectionToString(newConfig.shapesToAddToVanilla));
    textFieldWidget.setEditable(newConfig.addToVanillaGroups);
    textFieldWidget.setChangedListener(s -> {
      newConfig.shapesToAddToVanilla = convertStringToCollection(s);
      textFieldWidget.setSuggestion(getSuggestion(textFieldWidget.getText()));
    });
  });
  private final ButtonWidget resetShapesToAddToVanillaButton = new ButtonWidget(width / 2 + 155, 76, 50, 20, new TranslatableText("options.extshape.reset"), button -> shapesToAddToVanillaTextField.setText(convertCollectionToString(ExtShapeConfig.DEFAULT_CONFIG.shapesToAddToVanilla)));
  private final TextFieldWidget shapesInSpecificGroupsTextField = Util.make(new TextFieldWidget(MinecraftClient.getInstance().textRenderer, width / 2 - 205, 121, 358, 20, new TranslatableText("options.extshape.shapesInSpecificGroups")), textFieldWidget -> {
    textFieldWidget.setMaxLength(Integer.MAX_VALUE);
    textFieldWidget.setText(convertCollectionToString(newConfig.shapesInSpecificGroups));
    textFieldWidget.setEditable(newConfig.showSpecificGroups);
    textFieldWidget.setChangedListener(s -> {
      newConfig.shapesInSpecificGroups = convertStringToCollection(s);
      textFieldWidget.setSuggestion(getSuggestion(textFieldWidget.getText()));
    });
  });
  private final ButtonWidget resetShapesInSpecificGroupsButton = new ButtonWidget(width / 2 + 155, 121, 50, 20, new TranslatableText("options.extshape.reset"), button -> shapesInSpecificGroupsTextField.setText(convertCollectionToString(ExtShapeConfig.DEFAULT_CONFIG.shapesInSpecificGroups)));
  private final ClickableWidget addToVanillaGroupsButton = CyclingOption.create(
      "options.extshape.addToVanillaGroups",
      new TranslatableText("options.extshape.addToVanillaGroups.tooltip", ItemGroup.BUILDING_BLOCKS.getDisplayName(), ItemGroup.DECORATIONS.getDisplayName(), ItemGroup.REDSTONE.getDisplayName())
              .append(FabricLoader.getInstance().isModLoaded("extshape_blockus") ? Text.literal("\n\n").append(Text.translatable("options.extshape.addToVanillaGroups.blockus").formatted(Formatting.RED)) : Text.empty())
          .append("\n\n")
          .append(new TranslatableText("options.extshape.default", ScreenTexts.onOrOff(ExtShapeConfig.DEFAULT_CONFIG.addToVanillaGroups)).formatted(Formatting.GRAY)),
      gameOptions -> newConfig.addToVanillaGroups,
      (gameOptions, option, value) -> {
        newConfig.addToVanillaGroups = value;
        shapesToAddToVanillaTextField.setEditable(value);
      }
  ).createButton(gameOptions, width / 2 - 205, 36, 200);

  private final ClickableWidget showSpecificGroupsButton = CyclingOption.create(
      "options.extshape.showSpecificGroups",
      new TranslatableText("options.extshape.showSpecificGroups.tooltip")
          .append("\n\n")
          .append(new TranslatableText("options.extshape.default", ScreenTexts.onOrOff(ExtShapeConfig.DEFAULT_CONFIG.showSpecificGroups)).formatted(Formatting.GRAY)),
      gameOptions -> newConfig.showSpecificGroups,
      (gameOptions, option, value) -> {
        newConfig.showSpecificGroups = value;
        shapesInSpecificGroupsTextField.setEditable(value);
      }
  ).createButton(gameOptions, width / 2 + 5, 36, 200);


  private final ClickableWidget registerBlockFamiliesButton = CyclingOption.create(
      "options.extshape.registerBlockFamilies",
      new TranslatableText("options.extshape.registerBlockFamilies.description")
          .append("\n\n")
          .append(new TranslatableText("options.extshape.requires_restart"))
          .append("\n\n")
          .append(new TranslatableText("options.extshape.default", ScreenTexts.onOrOff(ExtShapeConfig.DEFAULT_CONFIG.registerBlockFamilies)).formatted(Formatting.GRAY)),
      gameOptions -> newConfig.registerBlockFamilies,
      (gameOptions, option, value) -> newConfig.registerBlockFamilies = value
  ).createButton(gameOptions, width / 2 - 205, 151, 200);


  private final ClickableWidget preventWoodenWallRecipesButton = CyclingOption.create(
      "options.extshape.preventWoodenWallRecipes",
      new TranslatableText("options.extshape.preventWoodenWallRecipes.tooltip")
          .append("\n\n")
          .append(new TranslatableText("options.extshape.default", ScreenTexts.onOrOff(ExtShapeConfig.DEFAULT_CONFIG.preventWoodenWallRecipes)).formatted(Formatting.GRAY)),
      gameOptions -> newConfig.preventWoodenWallRecipes,
      (gameOptions, option, value) -> newConfig.preventWoodenWallRecipes = value
  ).createButton(gameOptions, width / 2 + 5, 151, 200);

  private final ClickableWidget avoidSomeButtonRecipesButton = CyclingOption.create(
      "options.extshape.avoidSomeButtonRecipes",
      new TranslatableText("options.extshape.avoidSomeButtonRecipes.tooltip")
          .append("\n\n")
          .append(new TranslatableText("options.extshape.default", ScreenTexts.onOrOff(ExtShapeConfig.DEFAULT_CONFIG.avoidSomeButtonRecipes)).formatted(Formatting.GRAY)),
      gameOptions -> newConfig.avoidSomeButtonRecipes,
      (gameOptions, option, value) -> newConfig.avoidSomeButtonRecipes = value
  ).createButton(gameOptions, width / 2 - 205, 171, 200);

  private final ClickableWidget specialPressurePlateRecipesButton = CyclingOption.create(
      "options.extshape.specialSnowSlabCrafting",
      new TranslatableText("options.extshape.specialSnowSlabCrafting.tooltip")
          .append("\n\n")
          .append(new TranslatableText("options.extshape.default", ScreenTexts.onOrOff(ExtShapeConfig.DEFAULT_CONFIG.specialSnowSlabCrafting)).formatted(Formatting.GRAY)),
      gameOptions -> newConfig.specialSnowSlabCrafting,
      (gameOptions, option, value) -> newConfig.specialSnowSlabCrafting = value
  ).createButton(gameOptions, width / 2 + 5, 171, 200);

  private final ButtonWidget reasonableSortingButton = new ButtonWidget(width / 2 - 100, 191, 200, 20, new TranslatableText("options.extshape.reasonable-sorting"), button -> {
    if (client != null) {
      try {
        client.setScreen(ConfigScreenFabric.INSTANCE.createScreen(this));
      } catch (LinkageError e) {
        ExtShape.LOGGER.error("Failed to open Reasonable Sorting config screen:", e);
      }
    }
  }, (button, matrices, mouseX, mouseY) -> renderOrderedTooltip(matrices, textRenderer.wrapLines(new TranslatableText("options.extshape.reasonable-sorting.description"), 200), mouseX, mouseY));

  // 完成按钮
  private final ButtonWidget finishButton = new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, ScreenTexts.DONE, button -> close());

  public ExtShapeOptionsScreen(Screen parent) {
    super(new TranslatableText("options.extshape.title"));
    this.parent = parent;
  }

  @Override
  protected void init() {
    // 里面的内容不需要被选中，所以只是drawable。
    addDrawable(new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 32, 25));

    addToVanillaGroupsButton.x = width / 2 - 205;
    addDrawableChild(addToVanillaGroupsButton);
    showSpecificGroupsButton.x = width / 2 + 5;
    addDrawableChild(showSpecificGroupsButton);
    shapesToAddToVanillaTextField.x = width / 2 - 205;
    addDrawableChild(shapesToAddToVanillaTextField);
    resetShapesToAddToVanillaButton.x = width / 2 + 155;
    addDrawableChild(resetShapesToAddToVanillaButton);
    shapesInSpecificGroupsTextField.x = width / 2 - 205;
    addDrawableChild(shapesInSpecificGroupsTextField);
    resetShapesInSpecificGroupsButton.x = width / 2 + 155;
    addDrawableChild(resetShapesInSpecificGroupsButton);
    registerBlockFamiliesButton.x = width / 2 - 205;
    addDrawableChild(registerBlockFamiliesButton);

    preventWoodenWallRecipesButton.x = width / 2 + 5;
    addDrawableChild(preventWoodenWallRecipesButton);
    avoidSomeButtonRecipesButton.x = width / 2 - 205;
    addDrawableChild(avoidSomeButtonRecipesButton);
    specialPressurePlateRecipesButton.x = width / 2 + 5;
    addDrawableChild(specialPressurePlateRecipesButton);

    reasonableSortingButton.active = client != null && FabricLoader.getInstance().isModLoaded("reasonable-sorting");
    reasonableSortingButton.x = width / 2 - 100;
    addDrawableChild(reasonableSortingButton);

    finishButton.x = width / 2 - 100;
    finishButton.y = height - 27;
    addDrawableChild(finishButton);
  }

  @Override
  public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
    super.render(matrices, mouseX, mouseY, delta);
    drawCenteredTextWithShadow(matrices, this.textRenderer, this.title.asOrderedText(), this.width / 2, 16, 0xffffff);
    drawTextWithShadow(matrices, textRenderer, new TranslatableText("options.extshape.shapesToAddToVanilla.description"), width / 2 - 205, 61, 0xffffff);
    drawTextWithShadow(matrices, textRenderer, new TranslatableText("options.extshape.shapesInSpecificGroups.description"), width / 2 - 205, 106, 0xffffff);
    for (Element child : children()) {
      if (child instanceof CyclingButtonWidget<?> widget && widget.isHovered()) {
        renderOrderedTooltip(matrices, widget.getOrderedTooltip(), mouseX, mouseY);
      }
    }
  }

  public void save() {
    final ExtShapeConfig oldConfig = ExtShapeConfig.CURRENT_CONFIG;
    ExtShapeConfig.CURRENT_CONFIG = newConfig;
    if (!oldConfig.equals(newConfig)) {
      ExtShapeConfig.CURRENT_CONFIG.tryWriteFile(ExtShapeConfig.CONFIG_FILE);
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
          new TranslatableText("options.extshape.confirm"),
          new TranslatableText("options.extshape.confirm.noGroups", new TranslatableText("options.extshape.addToVanillaGroups").formatted(Formatting.GRAY), new TranslatableText("options.extshape.showSpecificGroups").formatted(Formatting.GRAY), ScreenTexts.OFF),
          ScreenTexts.YES,
          new TranslatableText("options.extshape.confirm.redo")
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
              ExtShapeRRP.PACK.clearResources(ResourceType.SERVER_DATA);
              ExtShapeRRP.generateServerData(ExtShapeRRP.PACK);
              client.inGameHud.getChatHud().addMessage(
                  new TranslatableText("options.dataChanged.finish",
                      new LiteralText("/reload").styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/reload"))).formatted(Formatting.GRAY)));
            }
          },
          new TranslatableText("options.extshape.dataChanged"),
          new TranslatableText("options.extshape.dataChanged.details")
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
