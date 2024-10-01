package pers.solid.extshape.builder;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.block.ExtShapePressurePlateBlock;
import pers.solid.extshape.util.ActivationSettings;

public class PressurePlateBuilder extends AbstractBlockBuilder<PressurePlateBlock> {

  public final ActivationSettings activationSettings;

  public PressurePlateBuilder(Block baseBlock, @NotNull ActivationSettings activationSettings) {
    super(baseBlock, AbstractBlock.Settings.copy(baseBlock)
        .noCollision()
        .strength(computeStrength(baseBlock.getHardness()), computeStrength(baseBlock.getBlastResistance())), builder -> new ExtShapePressurePlateBlock(builder.baseBlock, builder.blockSettings, ((PressurePlateBuilder) builder).activationSettings));
    this.activationSettings = activationSettings;
    this.shape = BlockShape.PRESSURE_PLATE;
  }

  private static float computeStrength(float baseHardness) {
    return baseHardness == -1 ? -1 : baseHardness / 4f;
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
