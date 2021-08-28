package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import pers.solid.extshape.block.ExtShapeVerticalQuarterPieceBlock;
import pers.solid.extshape.block.VerticalQuarterPieceBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class VerticalQuarterPieceBuilder extends AbstractBlockBuilder<VerticalQuarterPieceBlock> {
    protected VerticalQuarterPieceBuilder(Block baseBlock) {
        super(baseBlock);
        this.defaultTag = ExtShapeBlockTag.VERTICAL_QUARTER_PIECES;
        this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.verticalQuarterPiece);
    }

    @Override
    protected String getSuffix() {
        return "_vertical_quarter_piece";
    }

    @Override
    public void createInstance() {
        this.block = new ExtShapeVerticalQuarterPieceBlock(blockSettings);
    }
}
