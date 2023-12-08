package pers.solid.extshape.util;

import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import pers.solid.extshape.ExtShape;

public final class ExtShapeBlockTypes {
  public static final BlockSetType AMETHYST_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.OAK)
      .soundGroup(BlockSoundGroup.AMETHYST_BLOCK)
      .register(new Identifier(ExtShape.MOD_ID, "amethyst"));
  public static final WoodType AMETHYST_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK)
      .fenceGateOpenSound(SoundEvents.BLOCK_AMETHYST_BLOCK_HIT)
      .fenceGateCloseSound(SoundEvents.BLOCK_AMETHYST_BLOCK_HIT)
      .hangingSignSoundGroup(BlockSoundGroup.AMETHYST_BLOCK)
      .soundGroup(BlockSoundGroup.AMETHYST_BLOCK)
      .register(new Identifier(ExtShape.MOD_ID, "amethyst"), AMETHYST_BLOCK_SET_TYPE);
  public static final BlockSetType SOFT = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .pressurePlateActivationRule(BlockSetType.ActivationRule.EVERYTHING)
      .register(new Identifier(ExtShape.MOD_ID, "soft"));
  public static final BlockSetType DIRT_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.OAK)
      .soundGroup(BlockSoundGroup.GRAVEL)
      .pressurePlateActivationRule(BlockSetType.ActivationRule.EVERYTHING)
      .register(new Identifier(ExtShape.MOD_ID, "dirt"));

  private ExtShapeBlockTypes() {}
}
