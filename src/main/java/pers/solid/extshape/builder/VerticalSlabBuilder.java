package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import pers.solid.extshape.block.ExtShapeVerticalSlabBlock;
import pers.solid.extshape.block.VerticalSlabBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeTags;

public class VerticalSlabBuilder extends AbstractBlockBuilder<VerticalSlabBlock> {
  public VerticalSlabBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeVerticalSlabBlock(baseBlock, builder.blockSettings));
    this.defaultTagToAdd = ExtShapeTags.VERTICAL_SLABS;
    this.mapping = BlockMappings.getMappingOf(BlockShape.VERTICAL_SLAB);
  }

  @Override
  protected String getSuffix() {
    return "_vertical_slab";
  }
}
