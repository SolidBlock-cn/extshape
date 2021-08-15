package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import pers.solid.extshape.block.ExtShapeVerticalSlabBlock;
import pers.solid.extshape.block.VerticalSlabBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class VerticalSlabBuilder extends AbstractBlockBuilder<VerticalSlabBlock> {
    protected VerticalSlabBuilder(Block baseBlock) {
        super(baseBlock);
        this.defaultTag = ExtShapeBlockTag.VERTICAL_SLABS;
        this.mapping = BlockMappings.mappingOfVerticalSlabs;
    }

    @Override
    protected String getSuffix() {
        return "_vertical_slab";
    }

    @Override
    public void createInstance() {
        this.block = new ExtShapeVerticalSlabBlock(this.blockSettings);
    }
}
