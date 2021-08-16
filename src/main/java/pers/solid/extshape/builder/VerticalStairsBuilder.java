package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import pers.solid.extshape.block.ExtShapeVerticalStairsBlock;
import pers.solid.extshape.block.VerticalStairsBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class VerticalStairsBuilder extends AbstractBlockBuilder<VerticalStairsBlock> {
    protected VerticalStairsBuilder(Block baseBlock) {
        super(baseBlock);
        this.defaultTag = ExtShapeBlockTag.VERTICAL_STAIRS;
        this.mapping = BlockMappings.shapeToMapping.get(Shape.verticalStairs);
    }

    @Override
    protected String getSuffix() {
        return "_vertical_stairs";
    }

    @Override
    public void createInstance() {
        this.block = new ExtShapeVerticalStairsBlock(blockSettings);
    }
}
