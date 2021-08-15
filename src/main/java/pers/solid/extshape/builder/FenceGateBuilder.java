package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.item.Item;
import pers.solid.extshape.block.ExtShapeFenceGateBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class FenceGateBuilder extends AbstractBlockBuilder<FenceGateBlock> {
    protected final Item craftingIngredient;

    protected FenceGateBuilder(Block baseBlock, Item craftingIngredient) {
        super(baseBlock);
        this.craftingIngredient = craftingIngredient;
        this.defaultTag = ExtShapeBlockTag.FENCE_GATES;
        this.mapping = BlockMappings.mappingOfFenceGates;
    }

    @Override
    protected String getSuffix() {
        return "_fence_gate";
    }

    @Override
    public void createInstance() {
        this.block = new ExtShapeFenceGateBlock(this.baseBlock,craftingIngredient,this.blockSettings);
    }
}
