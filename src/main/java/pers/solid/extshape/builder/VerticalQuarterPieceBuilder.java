package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import pers.solid.extshape.block.ExtShapeVerticalQuarterPieceBlock;
import pers.solid.extshape.block.VerticalQuarterPieceBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTags;

public class VerticalQuarterPieceBuilder extends AbstractBlockBuilder<VerticalQuarterPieceBlock> {
  protected VerticalQuarterPieceBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeVerticalQuarterPieceBlock(baseBlock, builder.blockSettings));
    this.defaultTag = ExtShapeBlockTags.VERTICAL_QUARTER_PIECES;
    this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.VERTICAL_QUARTER_PIECE);
  }

  @Override
  protected String getSuffix() {
    return "_vertical_quarter_piece";
  }
}
