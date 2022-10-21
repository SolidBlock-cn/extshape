package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.item.Item;
import pers.solid.extshape.block.ExtShapeFenceGateBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeTags;

public class FenceGateBuilder extends AbstractBlockBuilder<FenceGateBlock> {

  public Item secondIngredient;

  public FenceGateBuilder(Block baseBlock, Item secondIngredient) {
    super(baseBlock, builder -> new ExtShapeFenceGateBlock(baseBlock, ((FenceGateBuilder) builder).secondIngredient, builder.blockSettings));
    this.defaultTagToAdd = ExtShapeTags.FENCE_GATES;
    this.mapping = BlockMappings.getMappingOf(BlockShape.FENCE_GATE);
    this.secondIngredient = secondIngredient;
  }

  @Override
  protected String getSuffix() {
    return "_fence_gate";
  }
}
