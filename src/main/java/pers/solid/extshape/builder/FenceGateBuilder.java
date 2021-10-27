package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.item.Item;
import pers.solid.extshape.block.ExtShapeFenceGateBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.mappings.IngredientMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class FenceGateBuilder extends AbstractBlockBuilder<FenceGateBlock> {
    protected Item craftingIngredient;

    protected FenceGateBuilder(Block baseBlock, Item craftingIngredient) {
        super(baseBlock, builder -> new ExtShapeFenceGateBlock(builder.blockSettings));
        this.craftingIngredient = craftingIngredient;
        this.defaultTag = ExtShapeBlockTag.FENCE_GATES;
        this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.FENCE_GATE);
    }

    @Override
    protected String getSuffix() {
        return "_fence_gate";
    }

    public FenceGateBuilder setCraftingIngredient(Item craftingIngredient) {
        this.craftingIngredient = craftingIngredient;
        return this;
    }

    @Override
    public FenceGateBlock build() {
        super.build();
        IngredientMappings.MAPPING_OF_FENCE_GATE_INGREDIENTS.put(this.instance, this.craftingIngredient);
        return this.instance;
    }
}
