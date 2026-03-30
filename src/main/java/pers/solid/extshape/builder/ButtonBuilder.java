package pers.solid.extshape.builder;

import net.minecraft.util.Util;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.block.ExtShapeButtonBlock;
import pers.solid.extshape.mixin.AbstractBlockSettingsAccessor;
import pers.solid.extshape.util.ActivationSettings;

public class ButtonBuilder extends AbstractBlockBuilder<ButtonBlock> {

  public final ActivationSettings activationSettings;

  public ButtonBuilder(Block baseBlock, ActivationSettings activationSettings) {
    super(baseBlock, Util.make(BlockBehaviour.Properties.ofFullCopy(baseBlock)
        .noCollision()
        .strength(computeStrength(baseBlock.defaultDestroyTime()), computeStrength(baseBlock.getExplosionResistance()))
        .pushReaction(baseBlock.defaultBlockState().getPistonPushReaction() == PushReaction.BLOCK ? PushReaction.BLOCK : PushReaction.DESTROY)
        .instrument(NoteBlockInstrument.HARP)
        .mapColor(MapColor.NONE), settings -> ((AbstractBlockSettingsAccessor) settings).setRequiresCorrectToolForDrops(false)), builder -> new ExtShapeButtonBlock(builder.baseBlock, builder.blockSettings, ((ButtonBuilder) builder).activationSettings));
    this.shape = BlockShape.BUTTON;
    this.activationSettings = activationSettings;
  }

  private static float computeStrength(float baseHardness) {
    return baseHardness == -1 ? -1 : Math.min(0.5f, baseHardness / 4f);
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
