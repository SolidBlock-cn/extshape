package pers.solid.extshape.builder;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.ButtonBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.BlockSoundGroup;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.ExtShapeButtonBlock;
import pers.solid.extshape.util.ButtonSettings;

public class ButtonBuilder extends AbstractBlockBuilder<ButtonBlock> {

  public ButtonBuilder(@NotNull ButtonSettings buttonSettings, Block baseBlock) {
    super(baseBlock, FabricBlockSettings.copyOf(baseBlock).noCollision().strength(baseBlock.getHardness() / 4f), builder -> new ExtShapeButtonBlock(baseBlock, buttonSettings, builder.blockSettings));
    this.shape = BlockShape.BUTTON;
    final BlockSoundGroup soundGroup = baseBlock.getSoundGroup(baseBlock.getDefaultState());
    primaryTagToAddTo = soundGroup == BlockSoundGroup.WOOD || soundGroup == BlockSoundGroup.NETHER_WOOD || soundGroup == BlockSoundGroup.BAMBOO_WOOD || soundGroup == BlockSoundGroup.CHERRY_WOOD ? BlockTags.WOODEN_BUTTONS : BlockTags.BUTTONS;
  }

  @Override
  protected String getSuffix() {
    return "_button";
  }
}
