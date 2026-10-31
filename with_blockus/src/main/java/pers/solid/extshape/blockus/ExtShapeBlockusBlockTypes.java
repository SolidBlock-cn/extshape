package pers.solid.extshape.blockus;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import pers.solid.extshape.block.CopperManager;
import pers.solid.extshape.util.ActivationSettings;
import pers.solid.extshape.util.ExtShapeBlockTypes;
import pers.solid.extshape.util.FenceSettings;

import java.util.Arrays;

public final class ExtShapeBlockusBlockTypes {
  public static final BlockSetType GRASS_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(ExtShapeBlockTypes.SOFT_BLOCK_SET_TYPE).soundType(SoundType.GRASS).build(ExtShapeBlockus.id("grass_block"));
  public static final WoodType GRASS_BLOCK_WOOD_TYPE = WoodTypeBuilder.copyOf(WoodType.OAK).soundType(SoundType.GRASS).build(ExtShapeBlockus.id("grass_block"), GRASS_BLOCK_SET_TYPE);
  public static final BlockSetType ICE_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE).soundType(SoundType.GLASS).build(ExtShapeBlockus.id("ice"));
  public static final WoodType ICE_WOOD_TYPE = WoodTypeBuilder.copyOf(ExtShapeBlockTypes.STONE_WOOD_TYPE).soundType(SoundType.GLASS).build(ExtShapeBlockus.id("ice"), ICE_BLOCK_SET_TYPE);

  public static final BlockSetType CHORUS_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE).soundType(SoundType.GRASS).build(ExtShapeBlockus.id("chorus"));
  public static final WoodType CHORUS_WOOD_TYPE = WoodTypeBuilder.copyOf(ExtShapeBlockTypes.STONE_WOOD_TYPE).soundType(SoundType.GRASS).build(ExtShapeBlockus.id("chorus"), CHORUS_BLOCK_SET_TYPE);

  public static final BlockSetType SHINGLES_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(ExtShapeBlockTypes.NETHER_BRICKS_BLOCK_SET_TYPE)
      .soundType(SoundType.NETHER_BRICKS)
      .build(ExtShapeBlockus.id("shingles"));
  public static final WoodType SHINGLES_WOOD_TYPE = WoodTypeBuilder.copyOf(ExtShapeBlockTypes.NETHER_BRICKS_WOOD_TYPE)
      .soundType(SoundType.NETHER_BRICKS)
      .build(ExtShapeBlockus.id("shingles"), SHINGLES_BLOCK_SET_TYPE);
  public static final ActivationSettings SHINGLES_ACTIVATION_SETTINGS = ActivationSettings.stone(SHINGLES_BLOCK_SET_TYPE);
  public static final FenceSettings SHINGLES_FENCE_SETTINGS = new FenceSettings(Items.FLINT, SHINGLES_WOOD_TYPE);

  public static final BlockSetType SCULK_BRICKS_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .soundType(SoundType.POLISHED_DEEPSLATE)
      .build(ExtShapeBlockus.id("sculk_bricks"));
  public static final WoodType SCULK_BRICKS_WOOD_TYPE = WoodTypeBuilder.copyOf(ExtShapeBlockTypes.STONE_WOOD_TYPE)
      .soundType(SoundType.POLISHED_DEEPSLATE)
      .build(ExtShapeBlockus.id("sculk_bricks"), SCULK_BRICKS_BLOCK_SET_TYPE);
  public static final FenceSettings SCULK_BRICKS_FENCE_SETTINGS = new FenceSettings(Items.SCULK_VEIN, ExtShapeBlockTypes.POLISHED_DEEPSLATE_WOOD_TYPE);
  public static final ActivationSettings SCULK_BRICKS_ACTIVATION_SETTINGS = ActivationSettings.stone(SCULK_BRICKS_BLOCK_SET_TYPE);

  public static final ImmutableMap<WeatheringCopper.WeatherState, ActivationSettings> COPPER_TUFF_ACTIVATION_SETTINGS = Arrays.stream(WeatheringCopper.WeatherState.values()).collect(ImmutableMap.toImmutableMap(Functions.identity(), oxidationLevel -> {
    final int activationRate = CopperManager.getActivationRate(oxidationLevel);
    return new ActivationSettings(ExtShapeBlockTypes.TUFF_BRICKS_BLOCK_SET_TYPE, activationRate, activationRate);
  }));
  public static final FenceSettings COPPER_TUFF_FENCE_SETTINGS = new FenceSettings(Items.COPPER_INGOT, ExtShapeBlockTypes.TUFF_BRICKS_WOOD_TYPE);

  public static final BlockSetType BLAZE_LANTERN_BLOCK_SET_TYPE = BlockSetTypeBuilder.copyOf(BlockSetType.STONE)
      .soundType(SoundType.GLASS)
      .build(ExtShapeBlockus.id("blaze_lantern"));
  public static final WoodType BLAZE_LANTERN_WOOD_TYPE = WoodTypeBuilder.copyOf(ExtShapeBlockTypes.STONE_WOOD_TYPE)
      .soundType(SoundType.GLASS)
      .build(ExtShapeBlockus.id("blaze_lantern"), BLAZE_LANTERN_BLOCK_SET_TYPE);

  private ExtShapeBlockusBlockTypes() {
  }
}
