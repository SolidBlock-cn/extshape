package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import pers.solid.extshape.block.ExtShapeStairsBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeTags;

public class StairsBuilder extends AbstractBlockBuilder<StairsBlock> {
  public StairsBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeStairsBlock(builder.baseBlock, builder.blockSettings));
    this.defaultTagToAdd = ExtShapeTags.STAIRS;
    this.mapping = BlockMappings.getMappingOf(BlockShape.STAIRS);
  }

  @Override
  protected String getSuffix() {
    return "_stairs";
  }
}
