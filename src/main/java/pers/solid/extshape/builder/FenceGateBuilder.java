package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.item.Item;
import pers.solid.extshape.block.ExtShapeFenceGateBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeTags;
import pers.solid.extshape.util.FenceSettings;

public class FenceGateBuilder extends AbstractBlockBuilder<FenceGateBlock> {

  public Item secondIngredient;

  public FenceGateBuilder(Block baseBlock, FenceSettings fenceSettings) {
    super(baseBlock, builder -> new ExtShapeFenceGateBlock(baseBlock, fenceSettings, builder.blockSettings));
    this.defaultTagToAdd = ExtShapeTags.FENCE_GATES;
    this.mapping = BlockMappings.getMappingOf(BlockShape.FENCE_GATE);
    this.secondIngredient = fenceSettings.secondIngredient();
  }

  @Override
  protected String getSuffix() {
    return "_fence_gate";
  }
}
