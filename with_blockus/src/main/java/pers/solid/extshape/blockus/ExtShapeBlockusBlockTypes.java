package pers.solid.extshape.blockus;

import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import pers.solid.extshape.util.ExtShapeBlockTypes;

public final class ExtShapeBlockusBlockTypes {
  public static final BlockSetType GRASS_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(ExtShapeBlockTypes.SOFT_BLOCK_SET_TYPE).soundGroup(BlockSoundGroup.GRASS).build(new Identifier(ExtShapeBlockus.NAMESPACE, "grass_block"));
  public static final WoodType GRASS_BLOCK_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK).soundGroup(BlockSoundGroup.GRASS).build(new Identifier(ExtShapeBlockus.NAMESPACE, "grass_block"), GRASS_BLOCK_SET_TYPE);
  public static final BlockSetType ICE_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE).soundGroup(BlockSoundGroup.GLASS).build(new Identifier(ExtShapeBlockus.NAMESPACE, "ice"));
  public static final WoodType ICE_WOOD_TYPE = WoodTypeBuilder.copyOf(ExtShapeBlockTypes.STONE_WOOD_TYPE).soundGroup(BlockSoundGroup.GLASS).build(new Identifier(ExtShapeBlockus.NAMESPACE, "ice"), ICE_BLOCK_SET_TYPE);

  public static final BlockSetType SLIME_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(ExtShapeBlockTypes.SOFT_BLOCK_SET_TYPE).soundGroup(BlockSoundGroup.SLIME).buttonClickOnSound(SoundEvents.BLOCK_SLIME_BLOCK_HIT).buttonClickOffSound(SoundEvents.BLOCK_SLIME_BLOCK_HIT).pressurePlateClickOnSound(SoundEvents.BLOCK_SLIME_BLOCK_HIT).pressurePlateClickOffSound(SoundEvents.BLOCK_SLIME_BLOCK_HIT).build(new Identifier(ExtShapeBlockus.NAMESPACE, "slime"));
  public static final WoodType SLIME_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK).soundGroup(BlockSoundGroup.SLIME).fenceGateOpenSound(SoundEvents.BLOCK_SLIME_BLOCK_HIT).fenceGateCloseSound(SoundEvents.BLOCK_SLIME_BLOCK_HIT).build(new Identifier(ExtShapeBlockus.NAMESPACE, "slime"), SLIME_BLOCK_SET_TYPE);

  private ExtShapeBlockusBlockTypes() {
  }
}
