package pers.solid.extshape.builder;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.ExtShapePressurePlateBlock;
import pers.solid.extshape.tag.ExtShapeTags;

public class PressurePlateBuilder extends AbstractBlockBuilder<PressurePlateBlock> {

  public PressurePlateBuilder(@NotNull PressurePlateBlock.ActivationRule type, Block baseBlock) {
    super(baseBlock, AbstractBlock.Settings.copy(baseBlock).noCollision().strength(baseBlock.getHardness() / 4f), builder -> new ExtShapePressurePlateBlock(baseBlock, type, builder.blockSettings));
    this.defaultTagToAdd = ExtShapeTags.PRESSURE_PLATES;
    this.shape = BlockShape.PRESSURE_PLATE;
  }

  @Override
  protected String getSuffix() {
    return "_pressure_plate";
  }


}
