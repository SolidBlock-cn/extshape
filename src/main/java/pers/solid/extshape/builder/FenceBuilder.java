package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.item.Item;
import pers.solid.extshape.block.ExtShapeFenceBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.mappings.IngredientMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class FenceBuilder extends AbstractBlockBuilder<FenceBlock> {
    protected Item craftingIngredient;

    protected FenceBuilder(Block baseBlock, Item craftingIngredient) {
        super(baseBlock, builder -> new ExtShapeFenceBlock(builder.blockSettings));
        this.craftingIngredient = craftingIngredient;
        this.defaultTag = ExtShapeBlockTag.FENCES;
        this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.FENCE);
    }

    @Override
    protected String getSuffix() {
        return "_fence";
    }

    public FenceBuilder setCraftingIngredient(Item craftingIngredient) {
        this.craftingIngredient = craftingIngredient;
        return this;
    }

    @Override
    public FenceBlock build() {
        super.build();
        IngredientMappings.MAPPING_OF_FENCE_INGREDIENTS.put(this.instance, this.craftingIngredient);
        return this.instance;
    }
}
