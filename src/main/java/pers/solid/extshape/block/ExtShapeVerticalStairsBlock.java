package pers.solid.extshape.block;

import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;

public class ExtShapeVerticalStairsBlock extends VerticalStairsBlock implements ExtShapeVariantBlockInterface {
    public ExtShapeVerticalStairsBlock(Settings settings) {
        super(settings);
    }

    @Override
    public MutableText getName() {
        return new TranslatableText("block.extshape.?_vertical_stairs", this.getNamePrefix());
    }
}
