package pers.solid.extshape.blockus;

import com.brand.blockus.Blockus;
import com.brand.blockus.registry.content.BlockusBlocks;
import com.brand.blockus.registry.content.bundles.BSSWBundle;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.util.BlockBiMaps;

import java.util.List;
import java.util.Map;

public class ExtShapeBlockusAliases {
  public static final Logger LOGGER = LoggerFactory.getLogger(ExtShapeBlockusAliases.class);
  private static boolean initialized = false;

  public static void init() {
    if (initialized) {
      throw new IllegalStateException("ExtShapeBlockusAliases already initialized!");
    }

    ExtShapeBlockusAliases.initWallChanges();
    ExtShapeBlockusAliases.initPackedIceBricksChanges();
    ExtShapeBlockusAliases.initSquaresChanges();
    ExtShapeBlockusAliases.initTilesAndHerringboneChanges();

    initialized = true;
  }

  /**
   * 自 Blockus 更新后，加入了一些磨制方块的墙，导致这些方块在 Extended Block Shapes Blockus 中不再创建。因此创建 alias，以兼容此前版本。
   */
  public static void initWallChanges() {
    convertWallsToBlockus("polished_dripstone");
    convertWallsToBlockus("polished_sculk");
    convertWallsToBlockus("polished_amethyst");
    convertWallsToBlockus("polished_limestone");
    convertWallsToBlockus("polished_marble");
    convertWallsToBlockus("polished_bluestone");
    convertWallsToBlockus("polished_viridite");
    convertWallsToBlockus("polished_netherrack");
    convertWallsToBlockus("polished_nether_brick");
    convertWallsToBlockus("polished_red_nether_brick");
    convertWallsToBlockus("polished_charred_nether_brick");
    convertWallsToBlockus("polished_teal_nether_brick");
    convertWallsToBlockus("polished_purpur");
    convertWallsToBlockus("polished_phantom_purpur");
    convertWallsToBlockus("polished_end_stone");
  }

  /**
   * 自 Blockus 2.17.3 和 2.16.6 开始，Blockus 为一些方纹方块加入了楼梯、台阶和墙。因此本模组的相关方块将自动重定向至 Blockus 的相关方块。
   */
  public static void initSquaresChanges() {
    convertSquareBlockBundle(BlockusBlocks.LIMESTONE_SQUARES);
    convertSquareBlockBundle(BlockusBlocks.MARBLE_SQUARES);
    convertSquareBlockBundle(BlockusBlocks.BLUESTONE_SQUARES);
    convertSquareBlockBundle(BlockusBlocks.VIRIDITE_SQUARES);
    convertSquareBlockBundle(BlockusBlocks.PURPUR_SQUARES);
    convertSquareBlockBundle(BlockusBlocks.PHANTOM_PURPUR_SQUARES);
    convertSquareBlockBundle(BlockusBlocks.CHOCOLATE_SQUARES);
  }

  /**
   * 自 2.16.7/2.17.4/2.18.2 起，Blockus 模组更改了冰砖的 ID，并加入了一些形状变种。
   */
  public static void initPackedIceBricksChanges() {
    for (BlockShape shape : BlockShape.values()) {
      if (shape == BlockShape.WALL || shape == BlockShape.BUTTON) {
        continue;
      }
      final Block newBlock = BlockBiMaps.getBlockOfOrThrow(shape, BlockusBlocks.PACKED_ICE_BRICKS.block());

      final Identifier newId = BuiltInRegistries.BLOCK.getKey(newBlock);
      final Identifier oldId = ExtShapeBlockus.id("ice_brick_" + shape.getSerializedName());

      BuiltInRegistries.BLOCK.addAlias(oldId, newId);
      BuiltInRegistries.ITEM.addAlias(oldId, newId);
    }
  }

  /**
   * 自扩展方块形状模组 3.2.0 开始，硫黄和朱砂的瓦以及人字形纹砖的按钮和栅栏被移除，替换为原版的相应方块。
   */
  public static void initTilesAndHerringboneChanges() {
    final Map<Identifier, Block> map = Map.of(
        ExtShapeBlockus.id("sulfur_tile_"), Blocks.SULFUR_BRICKS,
        ExtShapeBlockus.id("herringbone_sulfur_brick_"), Blocks.SULFUR_BRICKS,
        ExtShapeBlockus.id("cinnabar_tile_"), Blocks.CINNABAR_BRICKS,
        ExtShapeBlockus.id("herringbone_cinnabar_brick_"), Blocks.CINNABAR_BRICKS
    );
    final List<BlockShape> shapesToReplace = List.of(BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE);

    map.forEach((prefix, newBase) -> {
      for (BlockShape blockShape : shapesToReplace) {
        final Identifier oldId = prefix.withSuffix(blockShape.getSerializedName());
        if (BuiltInRegistries.BLOCK.containsKey(oldId)) {
          throw new IllegalArgumentException(oldId + " is still present in the block registry!");
        }
        final Block newBlock = BlockBiMaps.getBlockOfOrThrow(blockShape, newBase);
        final Identifier newId = BuiltInRegistries.BLOCK.getKey(newBlock);

        BuiltInRegistries.BLOCK.addAlias(oldId, newId);
        BuiltInRegistries.ITEM.addAlias(oldId, newId);
      }
    });
  }

  private static void convertWallsToBlockus(String baseName) {
    final Identifier from = ExtShapeBlockus.id(baseName + "_wall");
    final Identifier to = Blockus.id(baseName + "_wall");

    boolean blockValid = true;
    boolean itemValid = true;
    if (BuiltInRegistries.BLOCK.containsKey(from)) {
      LOGGER.warn("Block id {} is supposed to be replaced from, but it actually exists. It might be caused by incompatible updates", from);
      blockValid = false;
    }
    if (!BuiltInRegistries.BLOCK.containsKey(to)) {
      LOGGER.warn("Block id {} is supposed to be replaced with, but it actually does not exist. It might be caused by incompatible updates", to);
      blockValid = false;
    }
    if (BuiltInRegistries.ITEM.containsKey(from)) {
      LOGGER.warn("Item id {} is supposed to be replaced from, but it actually exists. It might be caused by incompatible updates", from);
      itemValid = false;
    }
    if (!BuiltInRegistries.ITEM.containsKey(to)) {
      LOGGER.warn("Item id {} is supposed to be replaced with, but it actually does not exist. It might be caused by incompatible updates", to);
      itemValid = false;
    }
    if (blockValid) {
      BuiltInRegistries.BLOCK.addAlias(from, to);
    }
    if (itemValid) {
      BuiltInRegistries.ITEM.addAlias(from, to);
    }
  }

  private static void convertSquareBlockBundle(BSSWBundle bundle) {
    final Block[] blocks = {bundle.stairs(), bundle.slab(), bundle.wall()};

    for (Block block : blocks) {
      final Identifier blockusId = BuiltInRegistries.BLOCK.getKey(block);
      final Identifier extshapeId = ExtShapeBlockus.id(blockusId.getPath());

      if (BuiltInRegistries.BLOCK.containsKey(extshapeId)) {
        LOGGER.warn("Block id {} is supposed to be replaced with, but it actual exists along with {}", extshapeId, blockusId);
      }

      BuiltInRegistries.BLOCK.addAlias(extshapeId, blockusId);

      final Item item = block.asItem();
      if (item != Items.AIR) {
        final Identifier blockusItemId = BuiltInRegistries.ITEM.getKey(item);
        final Identifier extshapeItemId = ExtShapeBlockus.id(blockusItemId.getPath());
        if (BuiltInRegistries.ITEM.containsKey(extshapeItemId)) {
          LOGGER.warn("Item id {} is supposed to be replaced with, but it actual exists along with {}", extshapeItemId, blockusItemId);
        }
        BuiltInRegistries.ITEM.addAlias(extshapeItemId, blockusItemId);
      }
    }
  }
}
