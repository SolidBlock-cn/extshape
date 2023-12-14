package pers.solid.extshape.builder;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.BlockSoundGroup;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.block.ExtShapePressurePlateBlock;

public class PressurePlateBuilder extends AbstractBlockBuilder<PressurePlateBlock> {

  protected final BlockSetType blockSetType;

  public PressurePlateBuilder(Block baseBlock, @NotNull BlockSetType blockSetType) {
    super(baseBlock, AbstractBlock.Settings.copy(baseBlock)
        .noCollision()
        .strength(computeStrength(baseBlock.getHardness()), computeStrength(baseBlock.getBlastResistance()))
        .pistonBehavior(baseBlock.getDefaultState().getPistonBehavior() == PistonBehavior.BLOCK ? PistonBehavior.BLOCK : PistonBehavior.DESTROY), builder -> new ExtShapePressurePlateBlock(builder.baseBlock, builder.blockSettings, ((PressurePlateBuilder) builder).blockSetType));
    this.shape = BlockShape.PRESSURE_PLATE;
    this.blockSetType = blockSetType;
    final BlockSoundGroup soundGroup = baseBlock.getDefaultState().getSoundGroup();
    primaryTagToAddTo = soundGroup == BlockSoundGroup.STONE ? BlockTags.STONE_PRESSURE_PLATES : (soundGroup == BlockSoundGroup.WOOD || soundGroup == BlockSoundGroup.BAMBOO_WOOD || soundGroup == BlockSoundGroup.NETHER_WOOD || soundGroup == BlockSoundGroup.CHERRY_WOOD) ? BlockTags.WOODEN_PRESSURE_PLATES : BlockTags.PRESSURE_PLATES;
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
    return setInstanceSupplier(builder -> new ExtShapePressurePlateBlock.WithExtension(builder.baseBlock, builder.blockSettings, ((PressurePlateBuilder) builder).blockSetType, blockExtension));
  }
}
