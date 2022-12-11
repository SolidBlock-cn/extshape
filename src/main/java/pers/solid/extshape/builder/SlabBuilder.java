package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import pers.solid.extshape.block.ExtShapeSlabBlock;
import pers.solid.extshape.tag.ExtShapeTags;

public class SlabBuilder extends AbstractBlockBuilder<SlabBlock> {
  public SlabBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeSlabBlock(baseBlock, builder.blockSettings));
    this.defaultTagToAdd = ExtShapeTags.SLABS;
    this.shape = BlockShape.SLAB;
  }

  @Override
  protected String getSuffix() {
    return "_slab";
  }
}
