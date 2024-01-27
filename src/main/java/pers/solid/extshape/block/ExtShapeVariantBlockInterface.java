package pers.solid.extshape.block;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;
import pers.solid.extshape.util.AttributiveBlockNameManager;

/**
 * 用于此模组中的所有变种方块的接口。
 */
public interface ExtShapeVariantBlockInterface extends ExtShapeBlockInterface {

  /**
   * 此集合内的所有方块不能合成楼梯和台阶。此举是为了避免合成表冲突。
   *
   * @since 1.5.2
   */
  @ApiStatus.AvailableSince("1.5.2")
  ImmutableCollection<Block> NOT_TO_CRAFT_STAIRS_OR_SLABS = ImmutableSet.of(
      Blocks.CHISELED_SANDSTONE,
      Blocks.CHISELED_RED_SANDSTONE,
      Blocks.CHISELED_QUARTZ_BLOCK,
      Blocks.CUT_SANDSTONE,
      Blocks.CUT_RED_SANDSTONE
  );

  /**
   * @return 该方块的基础方块。
   */
  @Override
  Block getBaseBlock();

  default MutableText getNamePrefix() {
    final Block baseBlock = this.getBaseBlock();
    if (baseBlock == null) return Text.empty();
    return AttributiveBlockNameManager.getAttributiveBlockName(baseBlock.getName());
  }
}
