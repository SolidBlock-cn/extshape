package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.block.ExtShapeVerticalQuarterPieceBlock;
import pers.solid.extshape.block.VerticalQuarterPieceBlock;

public class VerticalQuarterPieceBuilder extends AbstractBlockBuilder<VerticalQuarterPieceBlock> {
  public VerticalQuarterPieceBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeVerticalQuarterPieceBlock(builder.baseBlock, builder.blockSettings));
    this.shape = BlockShape.VERTICAL_QUARTER_PIECE;
  }

  @Override
  protected String getSuffix() {
    return "_vertical_quarter_piece";
  }

  @Override
  public AbstractBlockBuilder<VerticalQuarterPieceBlock> withExtension(BlockExtension blockExtension) {
    return setInstanceSupplier(builder -> new ExtShapeVerticalQuarterPieceBlock.WithExtension(builder.baseBlock, builder.blockSettings, blockExtension));
  }
}
