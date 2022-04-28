package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import pers.solid.extshape.block.ExtShapeStairsBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTags;

public class StairsBuilder extends AbstractBlockBuilder<StairsBlock> {
  protected StairsBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeStairsBlock(builder.baseBlock, builder.blockSettings));
    this.defaultTag = ExtShapeBlockTags.STAIRS;
    this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.STAIRS);
  }

  @Override
  protected String getSuffix() {
    return "_stairs";
  }
}
