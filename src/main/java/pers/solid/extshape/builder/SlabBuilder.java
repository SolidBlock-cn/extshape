package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.registry.tag.BlockTags;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.block.ExtShapeSlabBlock;

public class SlabBuilder extends AbstractBlockBuilder<SlabBlock> {
  public SlabBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeSlabBlock(baseBlock, builder.blockSettings));
    this.shape = BlockShape.SLAB;
    primaryTagToAddTo = BlockTags.SLABS;
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
