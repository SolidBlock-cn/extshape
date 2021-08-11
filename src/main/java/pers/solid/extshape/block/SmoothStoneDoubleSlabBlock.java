package pers.solid.extshape.block;

import net.minecraft.util.Identifier;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class SmoothStoneDoubleSlabBlock extends ExtShapeBlock {
    public SmoothStoneDoubleSlabBlock(Settings settings) {
        super(settings);
    }

    @Override
    public Identifier getBlockModelIdentifier() {
        return new Identifier("minecraft", "block/smooth_stone_slab_double");
    }

    @Override
    public String getBlockModelString() {
        return null; // 不生成方块模型。在blockstates中即使用已有模型。
    }

    @Override
    public ExtShapeBlockInterface addToTag() {
        this.addToTag(ExtShapeBlockTag.FULL_BLOCKS);
        return this;
    }
}
