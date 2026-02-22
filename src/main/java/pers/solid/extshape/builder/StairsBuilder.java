package pers.solid.extshape.builder;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.block.ExtShapeStairsBlock;

public class StairsBuilder extends AbstractBlockBuilder<StairBlock> {
  public StairsBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeStairsBlock(builder.baseBlock, builder.blockSettings));
    this.shape = BlockShape.STAIRS;
  }

  @Override
  protected String getSuffix() {
    return "_stairs";
  }

  @Override
  public AbstractBlockBuilder<StairBlock> withExtension(BlockExtension blockExtension) {
    return setInstanceSupplier(builder -> new ExtShapeStairsBlock.WithExtension(builder.baseBlock, builder.blockSettings, blockExtension));
  }
}
