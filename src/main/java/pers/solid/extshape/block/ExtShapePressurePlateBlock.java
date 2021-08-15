package pers.solid.extshape.block;

import net.minecraft.block.PressurePlateBlock;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;

public class ExtShapePressurePlateBlock extends PressurePlateBlock implements ExtShapeVariantBlockInterface {

    public ExtShapePressurePlateBlock(ActivationRule type, Settings settings) {
        super(type, settings);
    }

    @Override
    public MutableText getName() {
        return new TranslatableText("block.extshape.?_pressure_plate", this.getNamePrefix());
    }
}
