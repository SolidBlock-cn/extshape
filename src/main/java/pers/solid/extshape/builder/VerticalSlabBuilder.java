package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.block.ExtShapeVerticalSlabBlock;
import pers.solid.extshape.block.VerticalSlabBlock;
import pers.solid.extshape.tag.ExtShapeTags;

public class VerticalSlabBuilder extends AbstractBlockBuilder<VerticalSlabBlock> {
  public VerticalSlabBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeVerticalSlabBlock(builder.baseBlock, builder.blockSettings));
    this.shape = BlockShape.VERTICAL_SLAB;
    primaryTagToAddTo = ExtShapeTags.VERTICAL_SLABS;
  }

  @Override
  protected String getSuffix() {
    return "_vertical_slab";
  }

  @Override
  public AbstractBlockBuilder<VerticalSlabBlock> withExtension(BlockExtension blockExtension) {
    return setInstanceSupplier(builder -> new ExtShapeVerticalSlabBlock.WithExtension(baseBlock, builder.blockSettings, blockExtension));
  }
}
