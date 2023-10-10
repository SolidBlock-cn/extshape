package pers.solid.extshape.builder;

import net.fabricmc.fabric.mixin.object.builder.AbstractBlockSettingsAccessor;
import net.minecraft.block.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Util;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.ExtShapeButtonBlock;
import pers.solid.extshape.util.ButtonSettings;

public class ButtonBuilder extends AbstractBlockBuilder<ButtonBlock> {

  public ButtonBuilder(@NotNull ButtonSettings buttonSettings, Block baseBlock) {
    super(baseBlock, Util.make(AbstractBlock.Settings.copy(baseBlock)
        .noCollision()
        .strength(computeStrength(baseBlock.getHardness()), computeStrength(baseBlock.getBlastResistance()))
        .mapColor(MapColor.CLEAR), settings -> ((AbstractBlockSettingsAccessor) settings).setMaterial(Material.DECORATION)), builder -> new ExtShapeButtonBlock(baseBlock, buttonSettings, builder.blockSettings));
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
