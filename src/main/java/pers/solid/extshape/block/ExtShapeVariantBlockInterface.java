package pers.solid.extshape.block;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.util.AttributiveBlockNameManager;

/**
 * 用于此模组中的所有变种方块的接口。
 */
public interface ExtShapeVariantBlockInterface extends ExtShapeBlockInterface {

  /**
   * @return 该方块的基础方块。
   */
  @Override
  @NotNull
  Block getBaseBlock();

  default @NotNull MutableText getNamePrefix() {
    final Block baseBlock = this.getBaseBlock();
    return AttributiveBlockNameManager.getAttributiveBlockName(baseBlock.getName());
  }
}
