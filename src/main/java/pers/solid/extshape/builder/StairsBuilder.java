package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import pers.solid.extshape.block.ExtShapeStairsBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class StairsBuilder extends AbstractBlockBuilder<StairsBlock> {
  protected StairsBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeStairsBlock(builder.baseBlock, builder.blockSettings));
    this.defaultTag = ExtShapeBlockTag.STAIRS;
    this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.STAIRS);
  }

  @Override
  protected String getSuffix() {
    return "_stairs";
  }
}
