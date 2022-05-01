package pers.solid.extshape.builder;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.ExtShapeButtonBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTags;

public class ButtonBuilder extends AbstractBlockBuilder<AbstractButtonBlock> {

  public ButtonBuilder(@NotNull ExtShapeButtonBlock.ButtonType type, Block baseBlock) {
    super(baseBlock, FabricBlockSettings.copyOf(baseBlock).noCollision().strength(baseBlock.getHardness() / 4f), builder -> new ExtShapeButtonBlock(baseBlock, type, builder.blockSettings));
    this.defaultTag = ExtShapeBlockTags.BUTTONS;
    this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.BUTTON);
  }

  @Override
  protected String getSuffix() {
    return "_button";
  }
}
