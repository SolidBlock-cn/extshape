package pers.solid.extshape.blockus;

import com.brand.blockus.Blockus;
import com.brand.blockus.registry.content.BlockusBlocks;
import com.brand.blockus.registry.content.bundles.BSSWBundle;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtShapeBlockusAliases {
  public static final Logger LOGGER = LoggerFactory.getLogger(ExtShapeBlockusAliases.class);

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
