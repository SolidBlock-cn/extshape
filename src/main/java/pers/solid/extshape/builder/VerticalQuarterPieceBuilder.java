package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import pers.solid.extshape.block.ExtShapeVerticalQuarterPieceBlock;
import pers.solid.extshape.block.VerticalQuarterPieceBlock;
import pers.solid.extshape.tag.ExtShapeTags;

public class VerticalQuarterPieceBuilder extends AbstractBlockBuilder<VerticalQuarterPieceBlock> {
  public VerticalQuarterPieceBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeVerticalQuarterPieceBlock(baseBlock, builder.blockSettings));
    this.defaultTagToAdd = ExtShapeTags.VERTICAL_QUARTER_PIECES;
    this.shape = BlockShape.VERTICAL_QUARTER_PIECE;
  }

  @Override
  protected String getSuffix() {
    return "_vertical_quarter_piece";
  }
}
