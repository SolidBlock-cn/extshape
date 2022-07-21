package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import pers.solid.extshape.block.ExtShapeFenceBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTags;

public class FenceBuilder extends AbstractBlockBuilder<FenceBlock> {
  public Item secondIngredient;

  public FenceBuilder(Block baseBlock, Item secondIngredient) {
    super(baseBlock, builder -> new ExtShapeFenceBlock(baseBlock, ((FenceBuilder) builder).secondIngredient, builder.blockSettings));
    this.secondIngredient = secondIngredient;
    this.defaultTagToAdd = ExtShapeBlockTags.FENCES;
    this.mapping = BlockMappings.getMappingOf(BlockShape.FENCE);
    itemSettings.group(ItemGroup.DECORATIONS);
  }

  @Override
  protected String getSuffix() {
    return "_fence";
  }
}
