package pers.solid.extshape.blockus;

import com.brand.blockus.Blockus;
import com.brand.blockus.registry.content.BlockusBlocks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.util.BlockBiMaps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtShapeBlockus implements ModInitializer {
  public static final String NAMESPACE = "extshape_blockus";
  public static final Logger LOGGER = LoggerFactory.getLogger("Extended Block Shapes for Blockus");

  private static final Identifier defaultId = Identifier.fromNamespaceAndPath(NAMESPACE, "default");

  /**
   * 此字段仅在开发环境下生效，将在初始化 data fixer 时设置值，从而在完成注册后验证其中的 id 是否有效。
   */
  public static @Nullable Map<String, String> replacing_id_map = null;

  public static Identifier id(String path) {
    return defaultId.withPath(path);
  }

  @Override
  public void onInitialize() {
    if (FabricLoader.getInstance().isModLoaded("blockus")) {
      LOGGER.info("Blockus mod loaded. Extended Block Shapes mod is trying to apply it.");
      ExtShapeBlockusBlocks.init();
      ExtShapeBlockusAliases.initWallChanges();
      ExtShapeBlockusItemGroup.registerEvent();
      registerStrippableBlocks();

      if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
        validateBlockIds();
        validateReplacingIds();
      }
    }
  }

  private static void registerStrippableBlocks() {
    for (BlockShape shape : BlockShape.values()) {
      var block1 = BlockBiMaps.getBlockOf(shape, BlockusBlocks.WHITE_OAK_LOG);
      var block2 = BlockBiMaps.getBlockOf(shape, BlockusBlocks.STRIPPED_WHITE_OAK_LOG);
      if (block1 != null && block2 != null) {
        ExtShape.EXTENDED_STRIPPABLE_BLOCKS.put(block1, block2);
      }
      var block3 = BlockBiMaps.getBlockOf(shape, BlockusBlocks.WHITE_OAK_WOOD);
      var block4 = BlockBiMaps.getBlockOf(shape, BlockusBlocks.STRIPPED_WHITE_OAK_WOOD);
      if (block3 != null && block4 != null) {
        ExtShape.EXTENDED_STRIPPABLE_BLOCKS.put(block3, block4);
      }
    }
  }

  /**
   * 检查本模组内的方块的 id 有无与 Blockus 模组中的重复的。
   */
  private static void validateBlockIds() {
    Validate.notEmpty(ExtShapeBlockusBlocks.BLOCKUS_BLOCKS);

    for (Block block : ExtShapeBlockusBlocks.BLOCKUS_BLOCKS) {
      final Identifier blockId = BuiltInRegistries.BLOCK.getKey(block);
      final Identifier blockusId = Identifier.fromNamespaceAndPath(Blockus.MOD_ID, blockId.getPath());
      if (BuiltInRegistries.BLOCK.containsKey(blockusId)) {
        final Block blockusBlock = BuiltInRegistries.BLOCK.getValue(blockusId);
        if (blockusBlock == BlockusBlocks.PAPER_WALL) {
          // 纸墙不属于墙方块，予以豁免。
          continue;
        }
        throw new IllegalStateException("Block with id " + blockId + " is registered in the mod, but block " + blockusId + " already exists in Blockus mod!");
      }
    }
  }

  private static void validateReplacingIds() {
    if (replacing_id_map == null) return;
    final List<RuntimeException> exceptions = new ArrayList<>();
    replacing_id_map.forEach((k, v) -> {
      final Identifier key = Identifier.parse(k);
      try {
        Validate.validState(!BuiltInRegistries.BLOCK.containsKey(key), "The id %s is to be replaced, but still exists in the block registry!", key);
        Validate.validState(!BuiltInRegistries.ITEM.containsKey(key), "The id %s is to be replaced, but still exists in the item registry!", key);
      } catch (RuntimeException e) {
        LOGGER.error("Data fixer:", e);
        exceptions.add(e);
      }
      final Identifier value = Identifier.parse(v);
      try {
        Validate.validState(BuiltInRegistries.BLOCK.containsKey(value), "The id %s is to be replace with, but does not exist in the block registry!", value);
        Validate.validState(BuiltInRegistries.ITEM.containsKey(value), "The id %s is to be replace with, but does not exist in the item registry!", value);
      } catch (RuntimeException e) {
        LOGGER.error("Data fixer:", e);
        exceptions.add(e);
      }
    });
    if (!exceptions.isEmpty()) {
      throw exceptions.getLast();
    }
  }
}
