package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import pers.solid.extshape.block.ExtShapeWallBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class WallBuilder extends AbstractBlockBuilder<ExtShapeWallBlock> {
    protected WallBuilder(Block baseBlock) {
        super(baseBlock);
        this.defaultTag = ExtShapeBlockTag.WALLS;
        this.mapping = BlockMappings.mappingOfWalls;
    }

    @Override
    protected String getSuffix() {
        return "_wall";
    }

    @Override
    public void createInstance() {
        this.block = new ExtShapeWallBlock(blockSettings);
    }
}
