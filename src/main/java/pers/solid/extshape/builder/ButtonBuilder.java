package pers.solid.extshape.builder;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import pers.solid.extshape.block.ExtShapeButtonBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class ButtonBuilder extends AbstractBlockBuilder<AbstractButtonBlock> {
    public final ExtShapeButtonBlock.ButtonType type;

    public ButtonBuilder(ExtShapeButtonBlock.ButtonType type, Block baseBlock) {
        super(baseBlock, FabricBlockSettings.copyOf(baseBlock).noCollision().strength(baseBlock.getHardness() / 4f),builder -> new ExtShapeButtonBlock(type,builder.blockSettings));
        this.type = type;
        this.defaultTag = ExtShapeBlockTag.BUTTONS;
        this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.BUTTON);
    }

    @Override
    protected String getSuffix() {
        return "_button";
    }
}
