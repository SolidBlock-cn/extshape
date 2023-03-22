package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import pers.solid.extshape.block.ExtShapeVerticalSlabBlock;
import pers.solid.extshape.block.VerticalSlabBlock;

public class VerticalSlabBuilder extends AbstractBlockBuilder<VerticalSlabBlock> {
  public VerticalSlabBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeVerticalSlabBlock(baseBlock, builder.blockSettings));
    this.shape = BlockShape.VERTICAL_SLAB;
  }

  @Override
  protected String getSuffix() {
    return "_vertical_slab";
  }
}
