package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.tag.BlockTags;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.block.ExtShapeFenceGateBlock;
import pers.solid.extshape.util.FenceSettings;

public class FenceGateBuilder extends AbstractBlockBuilder<FenceGateBlock> {

  public final FenceSettings fenceSettings;

  public FenceGateBuilder(Block baseBlock, FenceSettings fenceSettings) {
    super(baseBlock, builder -> new ExtShapeFenceGateBlock(builder.baseBlock, builder.blockSettings, fenceSettings));
    this.shape = BlockShape.FENCE_GATE;
    this.fenceSettings = fenceSettings;
    primaryTagToAddTo = BlockTags.FENCE_GATES;
  }

  @Override
  protected String getSuffix() {
    return "_fence_gate";
  }

  @Override
  public AbstractBlockBuilder<FenceGateBlock> withExtension(BlockExtension blockExtension) {
    return setInstanceSupplier(builder -> new ExtShapeFenceGateBlock.WithExtension(baseBlock, builder.blockSettings, fenceSettings, blockExtension));
  }
}
