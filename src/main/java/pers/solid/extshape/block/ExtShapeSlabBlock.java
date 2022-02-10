package pers.solid.extshape.block;

import net.minecraft.block.SlabBlock;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;

public class ExtShapeSlabBlock extends SlabBlock implements ExtShapeVariantBlockInterface {


    public ExtShapeSlabBlock(Settings settings) {
        super(settings);
    }

    @Override
    public MutableText getName() {
        return new TranslatableText("block.extshape.?_slab", this.getNamePrefix());
    }
}
