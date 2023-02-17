package pers.solid.extshape.builder;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.PressurePlateBlock;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.ExtShapePressurePlateBlock;
import pers.solid.extshape.tag.ExtShapeTags;

public class PressurePlateBuilder extends AbstractBlockBuilder<PressurePlateBlock> {

  public PressurePlateBuilder(@NotNull PressurePlateBlock.ActivationRule type, Block baseBlock, BlockSetType blockSetType) {
    super(baseBlock, FabricBlockSettings.copyOf(baseBlock).noCollision().strength(baseBlock.getHardness() / 4f), builder -> new ExtShapePressurePlateBlock(baseBlock, type, builder.blockSettings, blockSetType));
    this.defaultTagToAdd = ExtShapeTags.PRESSURE_PLATES;
    this.shape = BlockShape.PRESSURE_PLATE;
  }

  @Override
  protected String getSuffix() {
    return "_pressure_plate";
  }


}
