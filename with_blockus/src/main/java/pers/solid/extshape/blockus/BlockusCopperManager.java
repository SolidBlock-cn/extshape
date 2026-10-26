package pers.solid.extshape.blockus;

import com.brand.blockus.registry.content.BlockusBlocks;
import com.brand.blockus.registry.content.bundles.CopperBSSWBundle;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import pers.solid.extshape.block.CopperManager;

import java.util.List;

/**
 * 处理 Blockus 模组中的铜相关方块。
 *
 * @see CopperManager
 */
public final class BlockusCopperManager {
  public static final List<CopperBSSWBundle> COPPER_BRICK_BUNDLES = List.of(BlockusBlocks.COPPER_BRICKS, BlockusBlocks.EXPOSED_COPPER_BRICKS, BlockusBlocks.WEATHERED_COPPER_BRICKS, BlockusBlocks.OXIDIZED_COPPER_BRICKS);
  public static final List<CopperBSSWBundle> COPPER_TUFF_BUNDLES = List.of(BlockusBlocks.COPPER_TUFF_BRICKS, BlockusBlocks.EXPOSED_COPPER_TUFF_BRICKS, BlockusBlocks.WEATHERED_COPPER_TUFF_BRICKS, BlockusBlocks.OXIDIZED_COPPER_TUFF_BRICKS);

  public static final CopperManager COPPER_BRICKS = of(COPPER_BRICK_BUNDLES);
  public static final CopperManager COPPER_TUFF = of(COPPER_TUFF_BUNDLES);

  public static CopperManager of(List<CopperBSSWBundle> bundles) {
    return new CopperManager(
        ImmutableList.copyOf(Lists.transform(bundles, CopperBSSWBundle::block)),
        ImmutableList.copyOf(Lists.transform(bundles, CopperBSSWBundle::blockWaxed))
    );
  }
}
