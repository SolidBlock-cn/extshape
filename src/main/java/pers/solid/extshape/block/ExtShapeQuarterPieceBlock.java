package pers.solid.extshape.block;

import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;

public class ExtShapeQuarterPieceBlock extends QuarterPieceBlock implements ExtShapeVariantBlockInterface{
    public ExtShapeQuarterPieceBlock(Settings settings) {
        super(settings);
    }

    @Override
    public MutableText getName() {
        return new TranslatableText("block.extshape.?_quarter_piece", this.getNamePrefix());
    }
}
