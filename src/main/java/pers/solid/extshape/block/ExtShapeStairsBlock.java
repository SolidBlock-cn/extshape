package pers.solid.extshape.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;

public class ExtShapeStairsBlock extends StairsBlock implements Waterloggable, ExtShapeVariantBlockInterface {

    public ExtShapeStairsBlock(BlockState baseBlockState, Settings settings) {
        super(baseBlockState, settings);
    }

    @Override
    public MutableText getName() {
        return new TranslatableText("block.extshape.?_stairs", this.getNamePrefix());
    }
}
