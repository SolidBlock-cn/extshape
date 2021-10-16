package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import pers.solid.extshape.block.ExtShapeVerticalSlabBlock;
import pers.solid.extshape.block.VerticalSlabBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class VerticalSlabBuilder extends AbstractBlockBuilder<VerticalSlabBlock> {
    protected VerticalSlabBuilder(Block baseBlock) {
        super(baseBlock,builder -> new ExtShapeVerticalSlabBlock(builder.blockSettings));
        this.defaultTag = ExtShapeBlockTag.VERTICAL_SLABS;
        this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.VERTICAL_SLAB);
    }

    @Override
    protected String getSuffix() {
        return "_vertical_slab";
    }
}
