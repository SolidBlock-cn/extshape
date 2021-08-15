package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import pers.solid.extshape.block.ExtShapeSlabBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class SlabBuilder extends AbstractBlockBuilder<SlabBlock> {
    protected SlabBuilder(Block baseBlock) {
        super(baseBlock);
        this.defaultTag = ExtShapeBlockTag.SLABS;
        this.mapping = BlockMappings.mappingOfSlabs;
    }

    @Override
    protected String getSuffix() {
        return "_slab";
    }

    @Override
    public void createInstance() {
        this.block = new ExtShapeSlabBlock(this.blockSettings);
    }
}
