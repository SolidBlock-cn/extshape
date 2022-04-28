package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import pers.solid.extshape.block.ExtShapeSlabBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTags;

public class SlabBuilder extends AbstractBlockBuilder<SlabBlock> {
  public SlabBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeSlabBlock(baseBlock, builder.blockSettings));
    this.defaultTag = ExtShapeBlockTags.SLABS;
    this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.SLAB);
  }

  @Override
  protected String getSuffix() {
    return "_slab";
  }
}
