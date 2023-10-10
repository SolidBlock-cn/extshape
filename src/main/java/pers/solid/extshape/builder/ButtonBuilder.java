package pers.solid.extshape.builder;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.Material;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.tag.BlockTags;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.ExtShapeButtonBlock;
import pers.solid.extshape.util.ButtonSettings;

public class ButtonBuilder extends AbstractBlockBuilder<ButtonBlock> {

  public ButtonBuilder(@NotNull ButtonSettings buttonSettings, Block baseBlock) {
    super(baseBlock, AbstractBlock.Settings.copy(baseBlock)
        .noCollision()
        .strength(baseBlock.getHardness() / 4f)
        .pistonBehavior(baseBlock.getDefaultState().getPistonBehavior() == PistonBehavior.BLOCK ? PistonBehavior.BLOCK : PistonBehavior.DESTROY)
        .instrument(Instrument.HARP), builder -> new ExtShapeButtonBlock(baseBlock, buttonSettings, builder.blockSettings));
    this.shape = BlockShape.BUTTON;
    final Material material = baseBlock.getDefaultState().getMaterial();
    primaryTagToAddTo = material == Material.WOOD || material == Material.NETHER_WOOD ? BlockTags.WOODEN_BUTTONS : BlockTags.BUTTONS;
  }

  @Override
  protected String getSuffix() {
    return "_button";
  }
}
