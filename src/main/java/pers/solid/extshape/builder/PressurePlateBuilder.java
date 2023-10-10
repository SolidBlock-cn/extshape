package pers.solid.extshape.builder;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.Material;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.tag.BlockTags;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.block.ExtShapePressurePlateBlock;

public class PressurePlateBuilder extends AbstractBlockBuilder<PressurePlateBlock> {

  protected final PressurePlateBlock.ActivationRule type;
  protected final BlockSetType blockSetType;

  public PressurePlateBuilder(@NotNull PressurePlateBlock.ActivationRule type, Block baseBlock, @NotNull BlockSetType blockSetType) {
    super(baseBlock, AbstractBlock.Settings.copy(baseBlock)
        .noCollision()
        .strength(baseBlock.getHardness() / 4f)
        .pistonBehavior(baseBlock.getDefaultState().getPistonBehavior() == PistonBehavior.BLOCK ? PistonBehavior.BLOCK : PistonBehavior.DESTROY), builder -> new ExtShapePressurePlateBlock(builder.baseBlock, ((PressurePlateBuilder) builder).type, builder.blockSettings, ((PressurePlateBuilder) builder).blockSetType));
    this.shape = BlockShape.PRESSURE_PLATE;
    this.type = type;
    this.blockSetType = blockSetType;
    final Material material = baseBlock.getDefaultState().getMaterial();
    primaryTagToAddTo = material == Material.STONE ? BlockTags.STONE_PRESSURE_PLATES : material == Material.WOOD || material == Material.NETHER_WOOD ? BlockTags.WOODEN_PRESSURE_PLATES : BlockTags.PRESSURE_PLATES;
  }

  @Override
  protected String getSuffix() {
    return "_pressure_plate";
  }

  @Override
  public AbstractBlockBuilder<PressurePlateBlock> withExtension(BlockExtension blockExtension) {
    return setInstanceSupplier(builder -> new ExtShapePressurePlateBlock.WithExtension(builder.baseBlock, ((PressurePlateBuilder) builder).type, builder.blockSettings, ((PressurePlateBuilder) builder).blockSetType, blockExtension));
  }
}
