package pers.solid.extshape.builder;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.ExtShapeButtonBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTags;

public class ButtonBuilder extends AbstractBlockBuilder<AbstractButtonBlock> {

  private ExtShapeButtonBlock.ButtonType type;

  public ButtonBuilder(@NotNull ExtShapeButtonBlock.ButtonType type, Block baseBlock) {
    super(baseBlock, AbstractBlock.Settings.copy(baseBlock).noCollision().strength(baseBlock.getHardness() / 4f), builder -> new ExtShapeButtonBlock(baseBlock, ((ButtonBuilder) builder).type, builder.blockSettings));
    this.type = type;
    this.defaultTagToAdd = ExtShapeBlockTags.BUTTONS;
    this.mapping = BlockMappings.getMappingOf(BlockShape.BUTTON);
    itemSettings.group(ItemGroup.REDSTONE);
  }

  protected ButtonBuilder(Block baseBlock) {
    this(ExtShapeButtonBlock.ButtonType.STONE, baseBlock);
  }

  @Override
  protected String getSuffix() {
    return "_button";
  }
}
