package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.block.ExtShapeWallBlock;

public class WallBuilder extends AbstractBlockBuilder<ExtShapeWallBlock> {
  public WallBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeWallBlock(builder.baseBlock, builder.blockSettings));
    this.shape = BlockShape.WALL;
  }

  @Override
  protected String getSuffix() {
    return "_wall";
  }

  @Override
  public AbstractBlockBuilder<ExtShapeWallBlock> withExtension(BlockExtension blockExtension) {
    return setInstanceSupplier(builder -> new ExtShapeWallBlock.WithExtension(builder.baseBlock, builder.blockSettings, blockExtension));
  }
}
