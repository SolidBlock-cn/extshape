package pers.solid.extshape.blockus;

import com.brand.blockus.Blockus;
import com.brand.blockus.registry.content.BlockusBlocks;
import com.google.common.collect.Iterables;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.util.BlockBiMaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtShapeBlockus implements ModInitializer {
  public static final String NAMESPACE = "extshape_blockus";
  public static final Logger LOGGER = LoggerFactory.getLogger("Extended Block Shapes for Blockus");

  private static final Identifier defaultId = Identifier.fromNamespaceAndPath(NAMESPACE, "default");

  /**
   * 此字段仅在开发环境下生效，将在初始化 data fixer 时设置值，从而在完成注册后验证其中的 id 是否有效。
   */
  public static @Nullable Map<String, String> datafixer_replacing_id_map = null;

  public static Identifier id(String path) {
    return defaultId.withPath(path);
  }

  @Override
  public void onInitialize() {
    if (FabricLoader.getInstance().isModLoaded("blockus")) {
      LOGGER.info("Blockus mod loaded. Extended Block Shapes - Blockus mod is trying to apply it.");
      ExtShapeBlockusBlocks.init();
      ExtShapeBlockusAliases.initWallChanges();
      ExtShapeBlockusItemGroup.registerEvent();
      registerStrippableBlocks();
      if (ExtShapeConfig.CURRENT_CONFIG.showSpecificGroups) {
        ExtShapeBlockusSpecificItemGroups.init();
      }

      if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
        validateBlockIds();
        if (datafixer_replacing_id_map == null) {
          LOGGER.warn("ExtShapeBlockus.replacing_id_map is null");
        } else {
          validateReplacingIds(Iterables.transform(datafixer_replacing_id_map.entrySet(), input -> Map.entry(Identifier.parse(input.getKey()), Identifier.parse(input.getValue()))));
        }

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
          LOGGER.info("Validating Extended Block Shapes Blockus mod content");
          ExtShape.validateTagsForBlocks(server, ExtShapeBlockusBlocks.BLOCKUS_BLOCKS);
          LOGGER.info("Extended Block Shapes Blockus mod content is successfully validated");

          LOGGER.info("Validating Extended Block Shapes Blockus recipes");
          ExtShape.validateStonecuttingForBlocks(server, ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS);
          LOGGER.info("Extended Block Shapes Blockus recipes are successfully validated");
        });
      }
      prepareAndValidateRegistryAliases();
    }
  }

  private static void registerStrippableBlocks() {
    for (BlockShape shape : BlockShape.values()) {
      @Nullable var block1 = BlockBiMaps.getBlockOf(shape, BlockusBlocks.WHITE_OAK_LOG);
      @Nullable var block2 = BlockBiMaps.getBlockOf(shape, BlockusBlocks.STRIPPED_WHITE_OAK_LOG);
      if (block1 != null && block2 != null) {
        ExtShape.EXTENDED_STRIPPABLE_BLOCKS.put(block1, block2);
      }
      @Nullable var block3 = BlockBiMaps.getBlockOf(shape, BlockusBlocks.WHITE_OAK_WOOD);
      @Nullable var block4 = BlockBiMaps.getBlockOf(shape, BlockusBlocks.STRIPPED_WHITE_OAK_WOOD);
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

  /**
   * 检查需要替换的 ID 是否存在错误。只有当需要被替换的 ID（entry.key）均不存在对应方块或物品，且替换后的 ID 均存在对应方块或物品时存在时，才能通过。
   */
  protected static void validateReplacingIds(Iterable<Map.Entry<Identifier, Identifier>> entries) {
    final List<RuntimeException> exceptions = new ArrayList<>();
    entries.forEach((entry) -> {
      final Identifier key = entry.getKey();
      final Identifier value = entry.getValue();
      try {
        Validate.validState(!BuiltInRegistries.BLOCK.containsKey(key), "The id %s is to be replaced, but still exists in the block registry!", key);
        Validate.validState(!BuiltInRegistries.ITEM.containsKey(key), "The id %s is to be replaced, but still exists in the item registry!", key);
      } catch (RuntimeException e) {
        LOGGER.error("Data fixer:", e);
        exceptions.add(e);
      }
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

  private static void prepareAndValidateRegistryAliases() {
    final Map<Identifier, Identifier> replacingIds = new HashMap<>();
    final Map<Block, Block> baseBlockForWallReplacements = Map.of(
        BlockusBlocks.ROUGH_SANDSTONE.block(), Blocks.SANDSTONE,
        BlockusBlocks.ROUGH_RED_SANDSTONE.block(), Blocks.RED_SANDSTONE,
        BlockusBlocks.ROUGH_SOUL_SANDSTONE.block(), BlockusBlocks.SOUL_SANDSTONE.block()
    );

    baseBlockForWallReplacements.forEach((fromBase, toBase) -> {
      replacingIds.put(ExtShapeBlockus.id(BuiltInRegistries.BLOCK.getKey(fromBase).getPath() + "_wall"), BuiltInRegistries.BLOCK.getKey(BlockBiMaps.getBlockOfOrThrow(BlockShape.WALL, toBase)));
    });

    final String[] smallLogs = new String[]{"oak", "spruce", "birch", "jungle", "acacia", "dark_oak", "mangrove", "cherry", "pale_oak", "white_oak"};
    final BlockShape[] constructionShapes = {BlockShape.STAIRS, BlockShape.SLAB, BlockShape.VERTICAL_QUARTER_PIECE, BlockShape.VERTICAL_SLAB, BlockShape.VERTICAL_STAIRS, BlockShape.QUARTER_PIECE};

    // region 小型原木的更名
    // 适用于 1.21.1 和 >=1.21.8
    for (String type : smallLogs) {
      for (BlockShape shape : constructionShapes) {
        replacingIds.put(ExtShapeBlockus.id(type + "_small_logs_" + shape.getSerializedName()), ExtShapeBlockus.id("small_" + type + "_logs_" + shape.getSerializedName()));
      }
    }
    // endregion 小型原木的更名

    if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
      validateReplacingIds(replacingIds.entrySet());
    }
    replacingIds.forEach((fromId, toId) -> {
      BuiltInRegistries.BLOCK.addAlias(fromId, toId);
      BuiltInRegistries.ITEM.addAlias(fromId, toId);
    });
  }
}
