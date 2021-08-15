package pers.solid.extshape.block;

import net.minecraft.block.FenceGateBlock;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;

public class ExtShapeFenceGateBlock extends FenceGateBlock implements ExtShapeVariantBlockInterface {

    public ExtShapeFenceGateBlock(Settings settings) {
        super(settings);
    }

    @Override
    public MutableText getName() {
        return new TranslatableText("block.extshape.?_fence_gate", this.getNamePrefix());
    }

}
