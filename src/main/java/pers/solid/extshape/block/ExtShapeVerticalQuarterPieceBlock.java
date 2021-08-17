package pers.solid.extshape.block;

import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;

public class ExtShapeVerticalQuarterPieceBlock extends VerticalQuarterPieceBlock implements ExtShapeVariantBlockInterface {
    public ExtShapeVerticalQuarterPieceBlock(Settings settings) {
        super(settings);
    }

    @Override
    public MutableText getName() {
        return new TranslatableText("block.extshape.?_vertical_quarter_piece", this.getNamePrefix());
    }
}
