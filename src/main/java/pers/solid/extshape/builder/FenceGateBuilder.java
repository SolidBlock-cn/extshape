package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.item.Item;
import net.minecraft.tag.BlockTags;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.block.ExtShapeFenceGateBlock;

public class FenceGateBuilder extends AbstractBlockBuilder<FenceGateBlock> {

  public final Item secondIngredient;

  public FenceGateBuilder(Block baseBlock, Item secondIngredient) {
    super(baseBlock, builder -> new ExtShapeFenceGateBlock(baseBlock, ((FenceGateBuilder) builder).secondIngredient, builder.blockSettings));
    this.shape = BlockShape.FENCE_GATE;
    this.secondIngredient = secondIngredient;
    this.primaryTagToAddTo = BlockTags.FENCE_GATES;
  }

  @Override
  protected String getSuffix() {
    return "_fence_gate";
  }

  @Override
  public AbstractBlockBuilder<FenceGateBlock> withExtension(BlockExtension blockExtension) {
    return setInstanceSupplier(builder -> new ExtShapeFenceGateBlock.WithExtension(baseBlock, secondIngredient, builder.blockSettings, blockExtension));
  }
}
