package pers.solid.extshape.config;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.solid.extshape.block.ExtShapeSlabBlock;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.util.BlockBiMaps;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;

/**
 * 本模组使用的配置文件。
 */
public class ExtShapeConfig implements Cloneable {
  /**
   * 本模组的默认配置，即为实例初始化时使用的值。用于屏幕显示。
   */
  public static final @Unmodifiable ExtShapeConfig DEFAULT_CONFIG = new ExtShapeConfig();
  public static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("extshape.json").toFile();
  public static final File LEGACY_CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "extshape.nbt");
  static final Logger LOGGER = LoggerFactory.getLogger(ExtShapeConfig.class);
  private static final Gson GSON = new GsonBuilder()
      .setPrettyPrinting()
      .registerTypeAdapter(BlockShape.class, BlockShape.Serializer.INSTANCE)
      .create();
  /**
   * 本模组当前的配置。
   */
  public static ExtShapeConfig CURRENT_CONFIG;
  /**
   * 当配置更新后，这个值就会是 {@code true}，参见 {@link pers.solid.extshape.mixin.ItemGroupsMixin}。
   */
  public static boolean requireUpdateDisplay = false;
  public static boolean requireUpdateShapesToAddVanilla = true;

  public static void init() {
    try {
      CURRENT_CONFIG = readFile(CONFIG_FILE);
    } catch (IOException e) {
      try {
        final ExtShapeConfig newConfig = new ExtShapeConfig();
        try {
          final NbtCompound nbtCompound = newConfig.readLegacyFile(LEGACY_CONFIG_FILE);
          if (nbtCompound != null) {
            LOGGER.info("Reading the legacy NBT-format file of Extended Block Shapes mod.");
          }
        } catch (IOException ignore) {
          // 读取旧版 NBT 格式的配置文件如果出错，不抛出。
        }
        CURRENT_CONFIG = newConfig;
        if (LEGACY_CONFIG_FILE.delete()) {
          LOGGER.info("Deleted the legacy NBT-format file of Extended Block Shapes mod.");
        }
      } catch (Throwable throwable) {
        LOGGER.warn("Failed to read config file of Extended Block Shapes mod:", e);
        CURRENT_CONFIG = new ExtShapeConfig();
      }
      CURRENT_CONFIG.tryWriteFile(CONFIG_FILE);
    }
    if (CURRENT_CONFIG.registerBlockFamilies) {
      BlockBiMaps.completeBlockFamilies();
    }
  }

  /**
   * 是否将本模组的物品添加到创造模式物品栏中的原版的物品组中。
   */
  public boolean addToVanillaGroups = true;
  /**
   * 需要添加到原版物品组的方块形状的列表。不应该含有重复元素。
   */
  public Collection<BlockShape> shapesToAddToVanilla = ImmutableList.of(
      BlockShape.STAIRS, BlockShape.SLAB, BlockShape.QUARTER_PIECE, BlockShape.VERTICAL_STAIRS, BlockShape.VERTICAL_SLAB, BlockShape.VERTICAL_QUARTER_PIECE, BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.WALL, BlockShape.PRESSURE_PLATE, BlockShape.BUTTON
  );
  /**
   * 是否为与本模组有关的方块创建专门的物品组。默认为 {@code false}。如果不创建，则建议将 {@link #addToVanillaGroups} 设为 {@code true} 以免这些物品不再出现在创造模式物品栏中。
   */
  public boolean showSpecificGroups = false;
  /**
   * 需要添加到专用物品组中的方块形状的列表。不应该含有重复元素。
   */
  public Collection<BlockShape> shapesInSpecificGroups = shapesToAddToVanilla;
  /**
   * 是否将本模组的物品关系注册到 Minecraft 原版的 {@link net.minecraft.data.family.BlockFamilies} 中。<span style=color:maroon>仅支持 Minecraft 1.17 以上版本。</span>
   */
  public boolean registerBlockFamilies = false;
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
  public void tryWriteFile(File file) {
    try (final FileWriter writer = new FileWriter(file)) {
      GSON.toJson(this, writer);
    } catch (IOException e) {
      LOGGER.warn("Failed to write Extended Block Shapes config file:", e);
    }
  }

  /**
   * 读取配置文件并写入配置对象中。
   *
   * @param file 被读取的文件。
   */
  @Contract(mutates = "this")
  public NbtCompound readLegacyFile(File file) throws IOException {
    final NbtCompound nbtCompound = NbtIo.read(file);
    if (nbtCompound != null) {
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
    return nbtCompound;
  }

  public static ExtShapeConfig readFile(File file) throws IOException {
    try (final FileReader fileReader = new FileReader(file)) {
      final ExtShapeConfig config = GSON.fromJson(fileReader, ExtShapeConfig.class);
      if (config == null) {
        final ExtShapeConfig newConfig = new ExtShapeConfig();
        newConfig.tryWriteFile(file);
        return newConfig;
      }
      config.shapesToAddToVanilla = new LinkedHashSet<>(config.shapesToAddToVanilla);
      config.shapesInSpecificGroups = new LinkedHashSet<>(config.shapesInSpecificGroups);
      return config;
    }
  }

  @Override
  public ExtShapeConfig clone() {
    try {
      return (ExtShapeConfig) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ExtShapeConfig that)) return false;
    return addToVanillaGroups == that.addToVanillaGroups && showSpecificGroups == that.showSpecificGroups && registerBlockFamilies == that.registerBlockFamilies && avoidSomeButtonRecipes == that.avoidSomeButtonRecipes && preventWoodenWallRecipes == that.preventWoodenWallRecipes && specialSnowSlabCrafting == that.specialSnowSlabCrafting && Objects.equals(shapesToAddToVanilla, that.shapesToAddToVanilla) && Objects.equals(shapesInSpecificGroups, that.shapesInSpecificGroups);
  }

  @Override
  public int hashCode() {
    return Objects.hash(addToVanillaGroups, shapesToAddToVanilla, showSpecificGroups, shapesInSpecificGroups, registerBlockFamilies, avoidSomeButtonRecipes, preventWoodenWallRecipes, specialSnowSlabCrafting);
  }
}
