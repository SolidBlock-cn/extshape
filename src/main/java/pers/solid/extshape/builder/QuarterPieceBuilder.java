package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import pers.solid.extshape.block.ExtShapeQuarterPieceBlock;
import pers.solid.extshape.block.QuarterPieceBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTags;

public class QuarterPieceBuilder extends AbstractBlockBuilder<QuarterPieceBlock> {
  protected QuarterPieceBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeQuarterPieceBlock(baseBlock, builder.blockSettings));
    this.defaultTag = ExtShapeBlockTags.QUARTER_PIECES;
    this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.QUARTER_PIECE);
  }

  @Override
  protected String getSuffix() {
    return "_quarter_piece";
  }
}
