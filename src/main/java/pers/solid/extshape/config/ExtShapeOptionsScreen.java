package pers.solid.extshape.config;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Util;
import net.minecraft.world.item.CreativeModeTabs;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.BlockShape;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Environment(EnvType.CLIENT)
public class ExtShapeOptionsScreen extends Screen {

  private final @Nullable Screen parent;
  private final Options gameOptions = Minecraft.getInstance().options;
  public final ExtShapeConfig oldConfig = ExtShapeConfig.CURRENT_CONFIG;
  public final ExtShapeConfig newConfig = ExtShapeConfig.CURRENT_CONFIG.clone();
  private final EditBox shapesToAddToVanillaTextField = Util.make(new EditBox(Minecraft.getInstance().font, width / 2 - 205, 76, 358, 20, Component.translatable("options.extshape.shapesToAddToVanilla")), textFieldWidget -> {
    textFieldWidget.setMaxLength(Integer.MAX_VALUE);
    textFieldWidget.setValue(convertCollectionToString(newConfig.shapesToAddToVanilla));
    textFieldWidget.setEditable(newConfig.addToVanillaGroups);
    textFieldWidget.setResponder(s -> {
      newConfig.shapesToAddToVanilla = convertStringToCollection(s);
      textFieldWidget.setSuggestion(getSuggestion(textFieldWidget.getValue()));
    });
  });
  private final Button resetShapesToAddToVanillaButton = Button.builder(Component.translatable("options.extshape.reset"), button -> shapesToAddToVanillaTextField.setValue(convertCollectionToString(ExtShapeConfig.DEFAULT_CONFIG.shapesToAddToVanilla)))
      .bounds(width / 2 + 155, 76, 50, 20)
      .build();
  private final EditBox shapesInSpecificGroupsTextField = Util.make(new EditBox(Minecraft.getInstance().font, width / 2 - 205, 121, 358, 20, Component.translatable("options.extshape.shapesInSpecificGroups")), textFieldWidget -> {
    textFieldWidget.setMaxLength(Integer.MAX_VALUE);
    textFieldWidget.setValue(convertCollectionToString(newConfig.shapesInSpecificGroups));
    textFieldWidget.setEditable(newConfig.showSpecificGroups);
    textFieldWidget.setResponder(s -> {
      newConfig.shapesInSpecificGroups = convertStringToCollection(s);
      textFieldWidget.setSuggestion(getSuggestion(textFieldWidget.getValue()));
    });
  });
  private final Button resetShapesInSpecificGroupsButton = Button.builder(Component.translatable("options.extshape.reset"), button -> shapesInSpecificGroupsTextField.setValue(convertCollectionToString(ExtShapeConfig.DEFAULT_CONFIG.shapesInSpecificGroups)))
      .bounds(width / 2 + 155, 121, 50, 20)
      .build();
  private final AbstractWidget addToVanillaGroupsButton = OptionInstance.createBoolean(
      "options.extshape.addToVanillaGroups",
      OptionInstance.cachedConstantTooltip(
          Component.translatable("options.extshape.addToVanillaGroups.tooltip", BuiltInRegistries.CREATIVE_MODE_TAB.getValueOrThrow(CreativeModeTabs.BUILDING_BLOCKS).getDisplayItems(), BuiltInRegistries.CREATIVE_MODE_TAB.getValueOrThrow(CreativeModeTabs.COLORED_BLOCKS).getDisplayName(), BuiltInRegistries.CREATIVE_MODE_TAB.getValueOrThrow(CreativeModeTabs.NATURAL_BLOCKS).getDisplayName())
              .append(FabricLoader.getInstance().isModLoaded("extshape_blockus") ? Component.literal("\n\n").append(Component.translatable("options.extshape.addToVanillaGroups.blockus").withStyle(ChatFormatting.RED)) : Component.empty())
              .append("\n\n")
              .append(Component.translatable("options.extshape.default", CommonComponents.optionStatus(ExtShapeConfig.DEFAULT_CONFIG.addToVanillaGroups)).withStyle(ChatFormatting.GRAY))
              .append("\n\n")
              .append(Component.translatable("options.extshape.addToVanillaGroups.warning_for_1.20").withStyle(ChatFormatting.YELLOW))),
      true,
      value -> {
        newConfig.addToVanillaGroups = value;
        shapesToAddToVanillaTextField.setEditable(value);
      }
  ).createButton(gameOptions, width / 2 - 205, 36, 200);

  private final AbstractWidget showSpecificGroupsButton = OptionInstance.createBoolean(
      "options.extshape.showSpecificGroups",
      OptionInstance.cachedConstantTooltip(
          Component.translatable("options.extshape.showSpecificGroups.tooltip")
              .append("\n\n")
              .append(Component.translatable("options.extshape.default", CommonComponents.optionStatus(ExtShapeConfig.DEFAULT_CONFIG.showSpecificGroups)).withStyle(ChatFormatting.GRAY))
              .append("\n\n")
              .append(Component.translatable("options.extshape.showSpecificGroups.warning_for_1.20").withStyle(ChatFormatting.YELLOW))),
      false,
      value -> {
        newConfig.showSpecificGroups = value;
        shapesInSpecificGroupsTextField.setEditable(value);
      }
  ).createButton(gameOptions, width / 2 + 5, 36, 200);

  // 完成按钮
  private final Button finishButton = new Button.Builder(CommonComponents.GUI_DONE, button -> onClose()).pos(this.width / 2 - 100, this.height - 27).size(200, 20).build();

  public ExtShapeOptionsScreen(@Nullable Screen parent) {
    super(Component.translatable("options.extshape.title"));
    this.parent = parent;
    addToVanillaGroupsButton.active = false;
    showSpecificGroupsButton.active = false;
    shapesInSpecificGroupsTextField.active = false;
    resetShapesInSpecificGroupsButton.active = false;
  }

  @Override
  protected void init() {
    // 里面的内容不需要被选中，所以只是drawable。

    addToVanillaGroupsButton.setX(width / 2 - 205);
    addRenderableWidget(addToVanillaGroupsButton);
    showSpecificGroupsButton.setX(width / 2 + 5);
    addRenderableWidget(showSpecificGroupsButton);
    shapesToAddToVanillaTextField.setX(width / 2 - 205);
    addRenderableWidget(shapesToAddToVanillaTextField);
    resetShapesToAddToVanillaButton.setX(width / 2 + 155);
    addRenderableWidget(resetShapesToAddToVanillaButton);
    shapesInSpecificGroupsTextField.setX(width / 2 - 205);
    addRenderableWidget(shapesInSpecificGroupsTextField);
    resetShapesInSpecificGroupsButton.setX(width / 2 + 155);
    addRenderableWidget(resetShapesInSpecificGroupsButton);

    finishButton.setPosition(width / 2 - 100, height - 27);
    addRenderableWidget(finishButton);
  }

  @Override
  public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
    super.extractRenderState(graphics, mouseX, mouseY, a);
    graphics.centeredText(this.font, this.title.getVisualOrderText(), this.width / 2, 16, 0xffffffff);
    graphics.text(font, Component.translatable("options.extshape.shapesToAddToVanilla.description"), width / 2 - 205, 61, 0xffffffff);
    graphics.text(font, Component.translatable("options.extshape.shapesInSpecificGroups.description"), width / 2 - 205, 106, 0xffffffff);
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

    // 应用物品组。/*if (oldConfig.showSpecificGroups != newConfig.showSpecificGroups) {
    //      if (newConfig.showSpecificGroups) {
    //        ExtShape.LOGGER.info("Adding item groups at runtime. This may cause some instability.");
    //        ExtShapeItemGroup.implementGroups();
    //      } else {
    //        ExtShape.LOGGER.info("Removing item groups at runtime. This may cause some instability.");
    //        ExtShapeItemGroup.removeGroups();
    //      }
    //    }*/

  }

  private boolean suppressedGroupsWarning = false;

  @Override
  public void onClose() {
    if (!suppressedGroupsWarning && !newConfig.addToVanillaGroups && !newConfig.showSpecificGroups
        && !(!oldConfig.addToVanillaGroups && !oldConfig.showSpecificGroups)) {
      // 由于两个设置都被关闭，因此需要确认是否不添加到任何物品栏。
      minecraft.setScreenAndShow(new ConfirmScreen(
          t -> {
            if (t) {
              // 确定要继续
              suppressedGroupsWarning = true;
              onClose();
            } else {
              // 返回重新修改
              minecraft.setScreenAndShow(this);
            }
          },
          Component.translatable("options.extshape.confirm"),
          Component.translatable("options.extshape.confirm.noGroups", Component.translatable("options.extshape.addToVanillaGroups").withStyle(ChatFormatting.GRAY), Component.translatable("options.extshape.showSpecificGroups").withStyle(ChatFormatting.GRAY), CommonComponents.OPTION_OFF),
          CommonComponents.GUI_YES,
          Component.translatable("options.extshape.confirm.redo")
      ));
      return;
    }
    save();
    minecraft.setScreenAndShow(parent);
  }

  private static Collection<BlockShape> convertStringToCollection(String s) {
    return Arrays.stream(StringUtils.split(s)).map(BlockShape::byName).collect(ImmutableSet.toImmutableSet());
  }

  private static String convertCollectionToString(Collection<BlockShape> list) {
    return list.stream().map(BlockShape::getSerializedName).collect(Collectors.joining(StringUtils.SPACE));
  }

  private static @Nullable String getSuggestion(String currentValue) {
    final String[] split = StringUtils.split(currentValue);
    if (split.length == 0) return null;
    final String last = split[split.length - 1];
    if (StringUtils.isBlank(last)) {
      return null;
    }
    for (BlockShape value : BlockShape.values()) {
      final String name = value.getSerializedName();
      if (name.startsWith(last)) {
        return name.substring(last.length());
      }
    }
    return null;
  }
}
