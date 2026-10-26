package pers.solid.extshape.blockus;

import com.brand.blockus.registry.content.BlockusBlocks;
import pers.solid.extshape.block.CopperManager;

/**
 * 处理 Blockus 模组中的铜相关方块。
 *
 * @see CopperManager
 */
public final class BlockusCopperManager {
  public static final CopperManager COPPER_BRICKS = new CopperManager(BlockusBlocks.COPPER_BRICKS.block().blocks());
  public static final CopperManager COPPER_TUFF_BRICKS = new CopperManager(BlockusBlocks.COPPER_TUFF_BRICKS.block().blocks());
}
