package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import pers.solid.extshape.block.ExtShapeVerticalQuarterPieceBlock;
import pers.solid.extshape.block.VerticalQuarterPieceBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTags;

public class VerticalQuarterPieceBuilder extends AbstractBlockBuilder<VerticalQuarterPieceBlock> {
  public VerticalQuarterPieceBuilder(Block baseBlock) {
    super(baseBlock, builder -> new ExtShapeVerticalQuarterPieceBlock(baseBlock, builder.blockSettings));
    this.defaultTagToAdd = ExtShapeBlockTags.VERTICAL_QUARTER_PIECES;
    this.mapping = BlockMappings.getMappingOf(BlockShape.VERTICAL_QUARTER_PIECE);
    itemSettings.group(ItemGroup.BUILDING_BLOCKS);
  }

  @Override
  protected String getSuffix() {
    return "_vertical_quarter_piece";
  }
}
