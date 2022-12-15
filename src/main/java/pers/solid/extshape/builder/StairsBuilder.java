package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraft.item.ItemGroup;
import pers.solid.extshape.block.ExtShapeStairsBlock;
import pers.solid.extshape.tag.ExtShapeTags;

public class StairsBuilder extends AbstractBlockBuilder<StairsBlock> {
  public StairsBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeStairsBlock(builder.baseBlock, builder.blockSettings));
    this.defaultTagToAdd = ExtShapeTags.STAIRS;
    this.shape = BlockShape.STAIRS;
    itemSettings.group(ItemGroup.BUILDING_BLOCKS);
  }

  @Override
  protected String getSuffix() {
    return "_stairs";
  }
}
