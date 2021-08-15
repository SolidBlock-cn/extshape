package pers.solid.extshape.block;

import net.minecraft.block.Block;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class ExtShapeBlock extends Block implements ExtShapeBlockInterface {

    public ExtShapeBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ExtShapeBlockInterface addToTag() {
        this.addToTag(ExtShapeBlockTag.EXTSHAPE_BLOCKS);
        return this;
    }
}
