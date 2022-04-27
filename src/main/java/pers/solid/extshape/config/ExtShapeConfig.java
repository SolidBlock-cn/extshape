package pers.solid.extshape.config;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * 本模组使用的配置文件。
 */
public class ExtShapeConfig implements Cloneable {
  /**
   * 本模组的默认配置，即为实例初始化时使用的值。用于屏幕显示。
   */
  public static final @Unmodifiable ExtShapeConfig DEFAULT_CONFIG = new ExtShapeConfig();
  public static final File CONFIG_FILE = new File("config/extshape.nbt");
  private static final Logger LOGGER = LoggerFactory.getLogger("EXTSHAPE-configs");
  /**
   * 本模组当前的配置。
   */
  public static ExtShapeConfig CURRENT_CONFIG = new ExtShapeConfig();

  static {
    CURRENT_CONFIG.readFile(CONFIG_FILE);
  }

  /**
   * 是否将本模组的物品添加到创造模式物品栏中的原版的物品组中。如果需要搭配合理排序（Reasonable Sorting）模组以显示在原版物品组中，可以设为 {@code true}。
   */
  public boolean addToVanillaGroup = false;
  /**
   * 是否为与本模组有关的方块创建专门的物品组。默认为 {@code true}。如果不创建，则建议将 {@link #addToVanillaGroup} 设为 {@code true} 以免这些物品不再出现在创造模式物品栏中。
   */
  public boolean hasSpecificGroup = true;

  public static void save() {
    final NbtCompound nbtCompound;
    try {
      nbtCompound = NbtIo.read(CONFIG_FILE);
    } catch (IOException e) {
      LOGGER.error("Failed to read NBT config file {}.", CONFIG_FILE, e);
      return;
    }
    try {
      CURRENT_CONFIG.writeFile(nbtCompound, CONFIG_FILE);
    } catch (IOException e) {
      LOGGER.error("Failed to write NBT config file {}.", CONFIG_FILE, e);
    }
  }

  public void writeFile(NbtCompound nbtCompound, File file) throws IOException {
    if (file.createNewFile()) {
      LOGGER.info("Creating a new config file.");
    }
    NbtIo.write(writeNbt(nbtCompound), file);
    LOGGER.info("Successfully wrote config file {}.", file);
  }

  public void readNbt(NbtCompound nbtCompound) {
    if (nbtCompound.contains("addToVanillaGroup")) {
      addToVanillaGroup = nbtCompound.getBoolean("addToVanillaGroup");
    }
    if (nbtCompound.contains("hasSpecificGroup")) {
      hasSpecificGroup = nbtCompound.getBoolean("hasSpecificGroup");
    }
  }

  public @NotNull NbtCompound readFile(File file) {
    final NbtCompound read;
    try {
      read = NbtIo.read(file);
    } catch (IOException e) {
      LOGGER.error("Failed to read config file {}.", file, e);
      return new NbtCompound();
    }
    if (read != null) {
      readNbt(read);
      LOGGER.info("Successfully read config file {}.", file);
    } else {
      LOGGER.info("Config file {} is invalid or does not exist. A new config file will be created.", file);
    }
    return read == null ? new NbtCompound() : read;
  }

  public NbtCompound writeNbt(NbtCompound nbtCompound) {
    nbtCompound.putBoolean("addToVanillaGroup", addToVanillaGroup);
    nbtCompound.putBoolean("hasSpecificGroup", hasSpecificGroup);
    return nbtCompound;
  }

  @Environment(EnvType.CLIENT)
  public void addOptionsTo(ButtonListWidget list) {
    list.addOptionEntry(
        CyclingOption.create("添加至原版物品组", ScreenTexts.ON, ScreenTexts.OFF, gameOptions -> this.addToVanillaGroup, (gameOptions, option, value) -> this.addToVanillaGroup = value)
            .tooltip(client -> b -> client.textRenderer.wrapLines(new TranslatableText("将本模组中的物品添加至Minecraft原版物品组中，可能导致物品杂乱，如果安装了合理排序（Reasonable Sorting）模组则可以开启此项。").append("\n\n").append(new TranslatableText("默认值：%s", ScreenTexts.onOrOff(DEFAULT_CONFIG.addToVanillaGroup)).formatted(Formatting.GRAY)), 200)),
        CyclingOption.create("添加特定物品组", ScreenTexts.ON, ScreenTexts.OFF, gameOptions -> this.hasSpecificGroup, (gameOptions, option, value) -> this.hasSpecificGroup = value)
            .tooltip(client -> b -> client.textRenderer.wrapLines(new TranslatableText("添加专门的物品组以分类安排各类方块的内容。").append("\n\n").append(new TranslatableText("默认值：%s", ScreenTexts.onOrOff(DEFAULT_CONFIG.hasSpecificGroup)).formatted(Formatting.GRAY)), 200))
    );
  }

  @Override
  public ExtShapeConfig clone() {
    try {
      return (ExtShapeConfig) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }
}
