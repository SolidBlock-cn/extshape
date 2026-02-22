package pers.solid.extshape;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

/**
 * 该模组的方块物品。不同之处在于，其获取名称会直接获取方块名称。
 */
public class ExtShapeBlockItem extends BlockItem {
  public ExtShapeBlockItem(Block block, Properties settings) {
    super(block, settings);
  }
}
