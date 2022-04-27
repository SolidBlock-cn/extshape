package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.item.Item;
import pers.solid.extshape.block.ExtShapeFenceBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class FenceBuilder extends AbstractBlockBuilder<FenceBlock> {
  protected FenceBuilder(Block baseBlock, Item secondIngredient) {
    super(baseBlock, builder -> new ExtShapeFenceBlock(baseBlock, secondIngredient, builder.blockSettings));
    this.defaultTag = ExtShapeBlockTag.FENCES;
    this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.FENCE);
  }

  @Override
  protected String getSuffix() {
    return "_fence";
  }
}
