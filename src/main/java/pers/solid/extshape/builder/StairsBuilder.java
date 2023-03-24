package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraft.registry.tag.BlockTags;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.block.ExtShapeStairsBlock;

public class StairsBuilder extends AbstractBlockBuilder<StairsBlock> {
  public StairsBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeStairsBlock(builder.baseBlock, builder.blockSettings));
    this.shape = BlockShape.STAIRS;
    primaryTagToAddTo = BlockTags.STAIRS;
  }

  @Override
  protected String getSuffix() {
    return "_stairs";
  }

  @Override
  public AbstractBlockBuilder<StairsBlock> withExtension(BlockExtension blockExtension) {
    return setInstanceSupplier(builder -> new ExtShapeStairsBlock.WithExtension(builder.baseBlock, builder.blockSettings, blockExtension));
  }
}
