package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.block.ExtShapeQuarterPieceBlock;
import pers.solid.extshape.block.QuarterPieceBlock;
import pers.solid.extshape.tag.ExtShapeTags;

public class QuarterPieceBuilder extends AbstractBlockBuilder<QuarterPieceBlock> {
  public QuarterPieceBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeQuarterPieceBlock(builder.baseBlock, builder.blockSettings));
    this.shape = BlockShape.QUARTER_PIECE;
    primaryTagToAddTo = ExtShapeTags.QUARTER_PIECES;
  }

  @Override
  protected String getSuffix() {
    return "_quarter_piece";
  }

  @Override
  public AbstractBlockBuilder<QuarterPieceBlock> withExtension(BlockExtension blockExtension) {
    return setInstanceSupplier(builder -> new ExtShapeQuarterPieceBlock.WithExtension(baseBlock, builder.blockSettings, blockExtension));
  }
}
