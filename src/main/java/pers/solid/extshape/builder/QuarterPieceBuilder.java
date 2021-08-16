package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import pers.solid.extshape.block.ExtShapeQuarterPieceBlock;
import pers.solid.extshape.block.QuarterPieceBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class QuarterPieceBuilder extends AbstractBlockBuilder<QuarterPieceBlock> {
    protected QuarterPieceBuilder(Block baseBlock) {
        super(baseBlock);
        this.defaultTag = ExtShapeBlockTag.QUARTER_PIECES;
        this.mapping = BlockMappings.shapeToMapping.get(Shape.quarterPiece);
    }

    @Override
    protected String getSuffix() {
        return "_quarter_piece";
    }

    @Override
    public void createInstance() {
        this.block = new ExtShapeQuarterPieceBlock(this.blockSettings);
    }
}
