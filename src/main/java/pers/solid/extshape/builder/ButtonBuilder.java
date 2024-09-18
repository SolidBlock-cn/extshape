package pers.solid.extshape.builder;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.block.ExtShapeButtonBlock;
import pers.solid.extshape.util.ActivationSettings;

public class ButtonBuilder extends AbstractBlockBuilder<ButtonBlock> {

  public final ActivationSettings activationSettings;

  public ButtonBuilder(Block baseBlock, @NotNull ActivationSettings activationSettings) {
    super(baseBlock, AbstractBlock.Settings.copy(baseBlock)
        .noCollision()
        .strength(computeStrength(baseBlock.getHardness()), computeStrength(baseBlock.getBlastResistance()))
        .pistonBehavior(baseBlock.getDefaultState().getPistonBehavior() == PistonBehavior.BLOCK ? PistonBehavior.BLOCK : PistonBehavior.DESTROY)
        .instrument(Instrument.HARP)
        .mapColor(MapColor.CLEAR), builder -> new ExtShapeButtonBlock(builder.baseBlock, builder.blockSettings, ((ButtonBuilder) builder).activationSettings));
    this.shape = BlockShape.BUTTON;
    final BlockSoundGroup soundGroup = baseBlock.getSoundGroup(baseBlock.getDefaultState());
    this.activationSettings = activationSettings;
  }

  private static float computeStrength(float baseHardness) {
    return baseHardness == -1 ? -1 : baseHardness / 4f;
  }

  @Override
  protected String getSuffix() {
    return "_button";
  }

  @Override
  public AbstractBlockBuilder<ButtonBlock> withExtension(BlockExtension blockExtension) {
    return setInstanceSupplier(builder -> new ExtShapeButtonBlock.WithExtension(builder.baseBlock, builder.blockSettings, ((ButtonBuilder) builder).activationSettings, blockExtension));
  }
}
