package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.ItemGroup;
import pers.solid.extshape.block.ExtShapeSlabBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeTags;

public class SlabBuilder extends AbstractBlockBuilder<SlabBlock> {
  public SlabBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeSlabBlock(baseBlock, builder.blockSettings));
    this.defaultTagToAdd = ExtShapeTags.SLABS;
    this.mapping = BlockMappings.getMappingOf(BlockShape.SLAB);
    itemSettings.group(ItemGroup.BUILDING_BLOCKS);
  }

  @Override
  protected String getSuffix() {
    return "_slab";
  }
}
