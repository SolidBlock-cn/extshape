package pers.solid.extshape.blockus;

import com.brand.blockus.Blockus;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
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
}
