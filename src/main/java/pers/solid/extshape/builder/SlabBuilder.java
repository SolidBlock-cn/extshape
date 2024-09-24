package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.block.ExtShapeSlabBlock;

public class SlabBuilder extends AbstractBlockBuilder<SlabBlock> {
  public SlabBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeSlabBlock(builder.baseBlock, builder.blockSettings));
    this.shape = BlockShape.SLAB;
  }

  @Override
  protected String getSuffix() {
    return "_slab";
  }

  @Override
  public AbstractBlockBuilder<SlabBlock> withExtension(BlockExtension blockExtension) {
    return setInstanceSupplier(builder -> new ExtShapeSlabBlock.WithExtension(baseBlock, builder.blockSettings, blockExtension));
  }
}
