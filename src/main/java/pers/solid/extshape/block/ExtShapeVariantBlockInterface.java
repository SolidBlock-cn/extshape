package pers.solid.extshape.block;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.block.Block;
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

  default @NotNull MutableComponent getNamePrefix() {
    final Block baseBlock = this.getBaseBlock();
    return AttributiveBlockNameManager.getAttributiveBlockName(baseBlock.getName());
  }
}
