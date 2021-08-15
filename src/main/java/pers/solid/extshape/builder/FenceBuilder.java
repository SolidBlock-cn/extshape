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
        super(baseBlock);
        this.craftingIngredient = craftingIngredient;
        this.defaultTag = ExtShapeBlockTag.FENCES;
        this.mapping = BlockMappings.mappingOfFences;
    }

    @Override
    protected String getSuffix() {
        return "_fence";
    }

    @Override
    public void createInstance() {
        this.block = new ExtShapeFenceBlock(this.blockSettings);
    }

    public FenceBuilder setCraftingIngredient(Item craftingIngredient) {
        this.craftingIngredient = craftingIngredient;
        return this;
    }

    @Override
    public FenceBlock build() {
        super.build();
        IngredientMappings.mappingOfFenceIngredients.put(this.block, this.craftingIngredient);
        return this.block;
    }
}
