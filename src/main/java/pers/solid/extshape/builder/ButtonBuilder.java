package pers.solid.extshape.builder;

import net.fabricmc.fabric.mixin.object.builder.AbstractBlockSettingsAccessor;
import net.minecraft.block.*;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Util;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.ExtShapeButtonBlock;

public class ButtonBuilder extends AbstractBlockBuilder<AbstractButtonBlock> {

  public ButtonBuilder(@NotNull ExtShapeButtonBlock.ButtonType type, Block baseBlock) {
    super(baseBlock, Util.make(AbstractBlock.Settings.copy(baseBlock)
        .noCollision()
        .strength(computeStrength(baseBlock.getHardness()), computeStrength(baseBlock.getBlastResistance()))
        .mapColor(MapColor.CLEAR), settings -> ((AbstractBlockSettingsAccessor) settings).setMaterial(Material.DECORATION)), builder -> new ExtShapeButtonBlock(baseBlock, type, builder.blockSettings));
    this.shape = BlockShape.BUTTON;
    final Material material = baseBlock.getDefaultState().getMaterial();
    primaryTagToAddTo = material == Material.WOOD || material == Material.NETHER_WOOD ? BlockTags.WOODEN_BUTTONS : BlockTags.BUTTONS;
  }

  private static float computeStrength(float baseHardness) {
    return baseHardness == -1 ? -1 : baseHardness / 4f;
  }

  @Override
  protected String getSuffix() {
    return "_button";
  }
}
