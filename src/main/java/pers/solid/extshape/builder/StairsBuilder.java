package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import pers.solid.extshape.block.ExtShapeStairsBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class StairsBuilder extends AbstractBlockBuilder<StairsBlock> {
    protected StairsBuilder(Block baseBlock) {
        super(baseBlock);
        this.defaultTag = ExtShapeBlockTag.STAIRS;
        this.mapping = BlockMappings.mappingOfStairs;
    }


    @Override
    protected String getSuffix() {
        return "_stairs";
    }

    @Override
    public void createInstance() {
        this.block = new ExtShapeStairsBlock(this.baseBlock.getDefaultState(), this.blockSettings);
    }
}
