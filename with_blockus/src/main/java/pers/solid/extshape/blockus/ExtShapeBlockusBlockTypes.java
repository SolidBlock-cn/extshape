package pers.solid.extshape.blockus;

import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;
import net.minecraft.sound.BlockSoundGroup;
import pers.solid.extshape.util.ExtShapeBlockTypes;

public final class ExtShapeBlockusBlockTypes {
  public static final BlockSetType GRASS_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(ExtShapeBlockTypes.SOFT_BLOCK_SET_TYPE).soundGroup(BlockSoundGroup.GRASS).build(ExtShapeBlockus.id("grass_block"));
  public static final WoodType GRASS_BLOCK_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK).soundGroup(BlockSoundGroup.GRASS).build(ExtShapeBlockus.id("grass_block"), GRASS_BLOCK_SET_TYPE);
  public static final BlockSetType ICE_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE).soundGroup(BlockSoundGroup.GLASS).build(ExtShapeBlockus.id("ice"));
  public static final WoodType ICE_WOOD_TYPE = WoodTypeBuilder.copyOf(ExtShapeBlockTypes.STONE_WOOD_TYPE).soundGroup(BlockSoundGroup.GLASS).build(ExtShapeBlockus.id("ice"), ICE_BLOCK_SET_TYPE);

  private ExtShapeBlockusBlockTypes() {
  }
}
