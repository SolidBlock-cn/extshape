package pers.solid.extshape.config;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.solid.extshape.block.ExtShapeSlabBlock;
import pers.solid.extshape.mappings.BlockMappings;

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
  public static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "extshape.nbt");
  static final Logger LOGGER = LoggerFactory.getLogger("EXTSHAPE-configs");
  /**
   * 本模组当前的配置。
   */
  public static ExtShapeConfig CURRENT_CONFIG = new ExtShapeConfig();

  public static void init() {
    CURRENT_CONFIG.writeFile(CURRENT_CONFIG.readFile(CONFIG_FILE), CONFIG_FILE);
    if (CURRENT_CONFIG.registerBlockFamilies) {
      BlockMappings.completeBlockFamilies();
    }
  }

  /**
   * 是否将本模组的物品添加到创造模式物品栏中的原版的物品组中。对于 1.19.3 的版本而言，默认为 {@code true}。
   */
  public boolean addToVanillaGroups = true;
  /**
   * 是否为与本模组有关的方块创建专门的物品组。默认为 {@code true}。如果不创建，则建议将 {@link #addToVanillaGroups} 设为 {@code true} 以免这些物品不再出现在创造模式物品栏中。注意：1.19.3 暂不支持。
   */
  public boolean showSpecificGroups = false;
  /**
   * 是否将本模组的物品关系注册到 Minecraft 原版的 {@link net.minecraft.data.family.BlockFamilies} 中。<span style=color:maroon>仅支持 Minecraft 1.17 以上版本。</span>
   */
  public boolean registerBlockFamilies = true;
  /**
   * 是否避免部分按钮的配方，以解决配方冲突的问题。默认为开启。如果安装了能修复合成配方冲突的问题的模组，则可以关闭。
   *
   * @see pers.solid.extshape.block.ExtShapeButtonBlock#REFUSE_RECIPES
   */
  public boolean avoidSomeButtonRecipes = true;

  /**
   * 是否避免木墙的合成配方，以解决与木质活板门的配方冲突问题。默认为开启。如果安装了能修复合成配方冲突的问题的模组，则可以关闭。
   */
  public boolean preventWoodenWallRecipes = true;
  /**
   * 是否为雪台阶方块应用特殊合成配方，以解决与雪的配方冲突问题。
   *
   * @see ExtShapeSlabBlock#getCraftingRecipe()
   */
  public boolean specialSnowSlabCrafting = true;

  /**
   * 从配置文件中读取并保存配置文件。如果捕获到异常，将会在控制台中输出。读取到的文件中，所有其他的 NBT 标签都被保留。
   */
  public void writeFile(File file) {
    final NbtCompound nbtCompound;
    try {
      nbtCompound = NbtIo.read(file);
    } catch (IOException e) {
      LOGGER.error("Failed to read NBT config file {}.", file, e);
      return;
    }
    writeFile(nbtCompound, file);
  }

  /**
   * 将配置文件的内容存储在文件中。
   *
   * @param nbtCompound 文件已有的 NBT 复合标签。写入时，保留原有的标签，即使这些标签无法识别。
   */
  @Contract(mutates = "param1")
  public void writeFile(NbtCompound nbtCompound, File file) {
    try {
      if (file.createNewFile()) {
        LOGGER.info("Creating a new config file.");
      }
    } catch (IOException e) {
      LOGGER.error("Cannot write config file {}.", file, e);
    }
    try {
      NbtIo.write(writeNbt(nbtCompound), file);
    } catch (IOException e) {
      LOGGER.error("Failed to write config file {}.", file, e);
    }
    LOGGER.info("Successfully wrote config file {}.", file);
  }

  @Contract(mutates = "this")
  public void readNbt(NbtCompound nbtCompound) {
    if (nbtCompound.contains("addToVanillaGroups")) {
      addToVanillaGroups = nbtCompound.getBoolean("addToVanillaGroups");
    }
    if (nbtCompound.contains("showSpecificGroups")) {
      showSpecificGroups = nbtCompound.getBoolean("showSpecificGroups");
    }
    if (nbtCompound.contains("registerBlockFamilies")) {
      registerBlockFamilies = nbtCompound.getBoolean("registerBlockFamilies");
    }
    if (nbtCompound.contains("avoidSomeButtonRecipes")) {
      avoidSomeButtonRecipes = nbtCompound.getBoolean("avoidSomeButtonRecipes");
    }
    if (nbtCompound.contains("preventWoodenWallRecipes")) {
      preventWoodenWallRecipes = nbtCompound.getBoolean("preventWoodenWallRecipes");
    }
    if (nbtCompound.contains("specialSnowSlabCrafting")) {
      specialSnowSlabCrafting = nbtCompound.getBoolean("specialSnowSlabCrafting");
    }
  }

  /**
   * 读取配置文件并写入配置对象中。
   *
   * @param file 被读取的文件。
   * @return 读取到的配置文件中的 NBT。
   */
  @Contract(mutates = "this")
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

  @Contract(mutates = "param1", value = "_ -> param1")
  public NbtCompound writeNbt(NbtCompound nbt) {
    nbt.putBoolean("addToVanillaGroups", addToVanillaGroups);
    nbt.putBoolean("showSpecificGroups", showSpecificGroups);
    nbt.putBoolean("registerBlockFamilies", registerBlockFamilies);
    nbt.putBoolean("avoidSomeButtonRecipes", avoidSomeButtonRecipes);
    nbt.putBoolean("preventWoodenWallRecipes", preventWoodenWallRecipes);
    nbt.putBoolean("specialSnowSlabCrafting", specialSnowSlabCrafting);
    return nbt;
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
