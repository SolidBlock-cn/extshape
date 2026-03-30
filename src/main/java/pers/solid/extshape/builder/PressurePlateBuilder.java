package pers.solid.extshape.builder;

import net.minecraft.util.Util;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.block.ExtShapePressurePlateBlock;
import pers.solid.extshape.mixin.AbstractBlockSettingsAccessor;
import pers.solid.extshape.util.ActivationSettings;

public class PressurePlateBuilder extends AbstractBlockBuilder<PressurePlateBlock> {

  public final ActivationSettings activationSettings;

  public PressurePlateBuilder(Block baseBlock, ActivationSettings activationSettings) {
    super(baseBlock, Util.make(BlockBehaviour.Properties.ofFullCopy(baseBlock)
        .noCollision()
        .strength(computeStrength(baseBlock.defaultDestroyTime()), computeStrength(baseBlock.getExplosionResistance()))
        .pushReaction(baseBlock.defaultBlockState().getPistonPushReaction() == PushReaction.BLOCK ? PushReaction.BLOCK : PushReaction.DESTROY), settings -> ((AbstractBlockSettingsAccessor) settings).setRequiresCorrectToolForDrops(false)), builder -> new ExtShapePressurePlateBlock(builder.baseBlock, builder.blockSettings, ((PressurePlateBuilder) builder).activationSettings));
    this.activationSettings = activationSettings;
    this.shape = BlockShape.PRESSURE_PLATE;
  }

  private static float computeStrength(float baseHardness) {
    return baseHardness == -1 ? -1 : Math.min(0.5f, baseHardness / 4f);
  }

  @Override
  protected String getSuffix() {
    return "_pressure_plate";
  }

  @Override
  public AbstractBlockBuilder<PressurePlateBlock> withExtension(BlockExtension blockExtension) {
    return setInstanceSupplier(builder -> new ExtShapePressurePlateBlock.WithExtension(builder.baseBlock, builder.blockSettings, ((PressurePlateBuilder) builder).activationSettings, blockExtension));
  }
}
