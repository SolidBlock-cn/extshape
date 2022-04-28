package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.item.Item;
import pers.solid.extshape.block.ExtShapeFenceGateBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTags;

public class FenceGateBuilder extends AbstractBlockBuilder<FenceGateBlock> {
  protected final Item craftingIngredient;

  protected FenceGateBuilder(Block baseBlock, Item craftingIngredient) {
    super(baseBlock, builder -> new ExtShapeFenceGateBlock(baseBlock, craftingIngredient, builder.blockSettings));
    this.craftingIngredient = craftingIngredient;
    this.defaultTag = ExtShapeBlockTags.FENCE_GATES;
    this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.FENCE_GATE);
  }

  @Override
  protected String getSuffix() {
    return "_fence_gate";
  }
}
