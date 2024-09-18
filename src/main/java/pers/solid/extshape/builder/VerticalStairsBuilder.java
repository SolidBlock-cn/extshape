package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.block.ExtShapeVerticalStairsBlock;
import pers.solid.extshape.block.VerticalStairsBlock;

public class VerticalStairsBuilder extends AbstractBlockBuilder<VerticalStairsBlock> {
  public VerticalStairsBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeVerticalStairsBlock(builder.baseBlock, builder.blockSettings));
    this.shape = BlockShape.VERTICAL_STAIRS;
  }

  @Override
  protected String getSuffix() {
    return "_vertical_stairs";
  }

  @Override
  public AbstractBlockBuilder<VerticalStairsBlock> withExtension(BlockExtension blockExtension) {
    return setInstanceSupplier(builder -> new ExtShapeVerticalStairsBlock.WithExtension(baseBlock, builder.blockSettings, blockExtension));
  }
}
