package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import pers.solid.extshape.block.ExtShapeQuarterPieceBlock;
import pers.solid.extshape.block.QuarterPieceBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeTags;

public class QuarterPieceBuilder extends AbstractBlockBuilder<QuarterPieceBlock> {
  public QuarterPieceBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeQuarterPieceBlock(baseBlock, builder.blockSettings));
    this.defaultTagToAdd = ExtShapeTags.QUARTER_PIECES;
    this.mapping = BlockMappings.getMappingOf(BlockShape.QUARTER_PIECE);
  }

  @Override
  protected String getSuffix() {
    return "_quarter_piece";
  }
}
