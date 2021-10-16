package pers.solid.extshape.builder;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import pers.solid.extshape.block.ExtShapePressurePlateBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class PressurePlateBuilder extends AbstractBlockBuilder<PressurePlateBlock> {
    public final PressurePlateBlock.ActivationRule type;

    protected PressurePlateBuilder(PressurePlateBlock.ActivationRule type, Block baseBlock) {
        super(baseBlock, FabricBlockSettings.copyOf(baseBlock).noCollision().strength(baseBlock.getHardness() / 4f), builder -> new ExtShapePressurePlateBlock(type, builder.blockSettings));
        this.type = type;
        this.defaultTag = ExtShapeBlockTag.PRESSURE_PLATES;
        this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.PRESSURE_PLATE);
    }

    @Override
    protected String getSuffix() {
        return "_pressure_plate";
    }
}
