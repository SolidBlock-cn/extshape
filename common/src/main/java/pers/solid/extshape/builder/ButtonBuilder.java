package pers.solid.extshape.builder;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.BlockSoundGroup;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.ExtShapeButtonBlock;
import pers.solid.extshape.util.ButtonSettings;

public class ButtonBuilder extends AbstractBlockBuilder<ButtonBlock> {

  public ButtonBuilder(@NotNull ButtonSettings buttonSettings, Block baseBlock) {
    super(baseBlock, AbstractBlock.Settings.copy(baseBlock)
        .noCollision()
        .strength(computeStrength(baseBlock.getHardness()), computeStrength(baseBlock.getBlastResistance()))
        .pistonBehavior(baseBlock.getDefaultState().getPistonBehavior() == PistonBehavior.BLOCK ? PistonBehavior.BLOCK : PistonBehavior.DESTROY)
        .instrument(Instrument.HARP)
        .mapColor(MapColor.CLEAR), builder -> new ExtShapeButtonBlock(baseBlock, buttonSettings, builder.blockSettings));
    this.shape = BlockShape.BUTTON;
    final BlockSoundGroup soundGroup = baseBlock.getSoundGroup(baseBlock.getDefaultState());
    primaryTagToAddTo = soundGroup == BlockSoundGroup.WOOD || soundGroup == BlockSoundGroup.NETHER_WOOD || soundGroup == BlockSoundGroup.BAMBOO_WOOD || soundGroup == BlockSoundGroup.CHERRY_WOOD ? BlockTags.WOODEN_BUTTONS : BlockTags.BUTTONS;
  }

  private static float computeStrength(float baseHardness) {
    return baseHardness == -1 ? -1 : baseHardness / 4f;
  }

  @Override
  protected String getSuffix() {
    return "_button";
  }
}