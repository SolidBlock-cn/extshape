package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import pers.solid.extshape.block.ExtShapeVerticalStairsBlock;
import pers.solid.extshape.block.VerticalStairsBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class VerticalStairsBuilder extends AbstractBlockBuilder<VerticalStairsBlock> {
    protected VerticalStairsBuilder(Block baseBlock) {
        super(baseBlock,builder -> new ExtShapeVerticalStairsBlock(builder.blockSettings));
        this.defaultTag = ExtShapeBlockTag.VERTICAL_STAIRS;
        this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.VERTICAL_STAIRS);
    }

    @Override
    protected String getSuffix() {
        return "_vertical_stairs";
    }
}
