package pers.solid.extshape.builder;

import net.minecraft.world.level.block.Block;

public class BlockBuilder extends AbstractBlockBuilder<Block> {

  public BlockBuilder() {
    super(null, null, builder -> new Block(builder.blockSettings));
  }

  @Override
  protected String getSuffix() {
    return null;
  }
}
