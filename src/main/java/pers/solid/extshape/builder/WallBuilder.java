package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import pers.solid.extshape.block.ExtShapeWallBlock;

public class WallBuilder extends AbstractBlockBuilder<ExtShapeWallBlock> {
  public WallBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeWallBlock(baseBlock, builder.blockSettings));
    this.shape = BlockShape.WALL;
  }

  @Override
  protected String getSuffix() {
    return "_wall";
  }
}
