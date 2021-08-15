package pers.solid.extshape.block;

import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import org.jetbrains.annotations.NotNull;

public class ExtShapeButtonBlock extends AbstractButtonBlock implements ExtShapeVariantBlockInterface {

    public final ButtonType type;

    public ExtShapeButtonBlock(@NotNull ButtonType type, Settings settings) {
        super(type == ButtonType.WOODEN, settings);
        this.type = type;
    }

    @Override
    protected SoundEvent getClickSound(boolean powered) {
        if (this.type == ButtonType.WOODEN)
            return powered ? SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_ON : SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_OFF;
        else return powered ? SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON : SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF;
    }

    @Override
    public MutableText getName() {
        return new TranslatableText("block.extshape.?_button", this.getNamePrefix());
    }

    public enum ButtonType {
        WOODEN,
        STONE,
        HARD,
        SOFT
    }

}
