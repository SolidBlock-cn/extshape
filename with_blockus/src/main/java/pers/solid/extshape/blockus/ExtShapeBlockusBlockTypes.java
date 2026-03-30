package pers.solid.extshape.blockus;

import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import pers.solid.extshape.util.ExtShapeBlockTypes;

public final class ExtShapeBlockusBlockTypes {
  public static final BlockSetType GRASS_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(ExtShapeBlockTypes.SOFT_BLOCK_SET_TYPE).soundType(SoundType.GRASS).build(ExtShapeBlockus.id("grass_block"));
  public static final WoodType GRASS_BLOCK_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK).soundType(SoundType.GRASS).build(ExtShapeBlockus.id("grass_block"), GRASS_BLOCK_SET_TYPE);
  public static final BlockSetType ICE_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE).soundType(SoundType.GLASS).build(ExtShapeBlockus.id("ice"));
  public static final WoodType ICE_WOOD_TYPE = WoodTypeBuilder.copyOf(ExtShapeBlockTypes.STONE_WOOD_TYPE).soundType(SoundType.GLASS).build(ExtShapeBlockus.id("ice"), ICE_BLOCK_SET_TYPE);

  private ExtShapeBlockusBlockTypes() {
  }
}
