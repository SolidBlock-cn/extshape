package pers.solid.extshape.builder;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.tag.BlockTags;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.block.ExtShapePressurePlateBlock;

public class PressurePlateBuilder extends AbstractBlockBuilder<PressurePlateBlock> {

  protected final PressurePlateBlock.ActivationRule type;

  public PressurePlateBuilder(@NotNull PressurePlateBlock.ActivationRule type, Block baseBlock) {
    super(baseBlock, AbstractBlock.Settings.copy(baseBlock)
        .noCollision()
        .strength(computeStrength(baseBlock.getHardness()), computeStrength(baseBlock.getBlastResistance())), builder -> new ExtShapePressurePlateBlock(builder.baseBlock, ((PressurePlateBuilder) builder).type, builder.blockSettings));
    this.shape = BlockShape.PRESSURE_PLATE;
    this.type = type;
    final Material material = baseBlock.getDefaultState().getMaterial();
    primaryTagToAddTo = material == Material.STONE ? BlockTags.STONE_PRESSURE_PLATES : material == Material.WOOD || material == Material.NETHER_WOOD ? BlockTags.WOODEN_PRESSURE_PLATES : BlockTags.PRESSURE_PLATES;
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
    return setInstanceSupplier(builder -> new ExtShapePressurePlateBlock.WithExtension(builder.baseBlock, ((PressurePlateBuilder) builder).type, builder.blockSettings, blockExtension));
  }
}
