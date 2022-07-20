package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import pers.solid.extshape.block.ExtShapeWallBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTags;

public class WallBuilder extends AbstractBlockBuilder<ExtShapeWallBlock> {
  public WallBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeWallBlock(baseBlock, builder.blockSettings));
    this.defaultTagToAdd = ExtShapeBlockTags.WALLS;
    this.mapping = BlockMappings.getMappingOf(BlockShape.WALL);
    itemSettings.group(ItemGroup.DECORATIONS);
  }

  @Override
  protected String getSuffix() {
    return "_wall";
  }
}
