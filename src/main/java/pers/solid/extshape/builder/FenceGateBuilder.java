package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.item.Item;
import pers.solid.extshape.block.ExtShapeFenceGateBlock;
import pers.solid.extshape.util.FenceSettings;

public class FenceGateBuilder extends AbstractBlockBuilder<FenceGateBlock> {

  public final Item secondIngredient;

  public FenceGateBuilder(Block baseBlock, FenceSettings fenceSettings) {
    super(baseBlock, builder -> new ExtShapeFenceGateBlock(baseBlock, fenceSettings, builder.blockSettings));
    this.shape = BlockShape.FENCE_GATE;
    this.secondIngredient = fenceSettings.secondIngredient();
  }

  @Override
  protected String getSuffix() {
    return "_fence_gate";
  }
}
