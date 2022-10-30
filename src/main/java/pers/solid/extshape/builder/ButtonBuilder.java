package pers.solid.extshape.builder;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.ButtonBlock;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.ExtShapeButtonBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeTags;

public class ButtonBuilder extends AbstractBlockBuilder<ButtonBlock> {

  public ButtonBuilder(@NotNull ExtShapeButtonBlock.ButtonType type, Block baseBlock) {
    super(baseBlock, FabricBlockSettings.copyOf(baseBlock).noCollision().strength(baseBlock.getHardness() / 4f), builder -> new ExtShapeButtonBlock(baseBlock, type, builder.blockSettings));
    this.defaultTagToAdd = ExtShapeTags.BUTTONS;
    this.mapping = BlockMappings.getMappingOf(BlockShape.BUTTON);
  }

  @Override
  protected String getSuffix() {
    return "_button";
  }
}
