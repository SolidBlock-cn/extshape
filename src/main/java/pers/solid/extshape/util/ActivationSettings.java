package pers.solid.extshape.util;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.Oxidizable;
import pers.solid.extshape.block.CopperManager;

import java.util.Arrays;

/**
 * 用于按钮、压力板的一些设置。
 *
 * @param blockSetType 按钮、压力板的 {@link BlockSetType}，会影响这些方块的声音以及按钮、压力板的一些具体设置。
 * @param buttonTime   按钮的触发持续时间。
 * @param plateTime    压力板的触发持续时间。
 */
public record ActivationSettings(BlockSetType blockSetType, int buttonTime, int plateTime) {
  public static ActivationSettings stone(BlockSetType blockSetType) {
    return new ActivationSettings(blockSetType, 20, 20);
  }

  public static ActivationSettings soft(BlockSetType blockSetType) {
    return new ActivationSettings(blockSetType, 50, 40);
  }

  public static ActivationSettings softerStone(BlockSetType blockSetType) {
    return new ActivationSettings(blockSetType, 25, 20);
  }

  public static ActivationSettings wood(BlockSetType blockSetType) {
    return new ActivationSettings(blockSetType, 30, 20);
  }

  public static ActivationSettings hard(BlockSetType blockSetType) {
    return new ActivationSettings(blockSetType, 5, 5);
  }

  public static final ActivationSettings STONE = stone(BlockSetType.STONE);
  public static final ImmutableMap<Oxidizable.OxidationLevel, ActivationSettings> COPPER = Arrays.stream(Oxidizable.OxidationLevel.values()).collect(ImmutableMap.toImmutableMap(Functions.identity(), oxidationLevel -> {
    final int activationRate = CopperManager.getActivationRate(oxidationLevel);
    return new ActivationSettings(BlockSetType.COPPER, activationRate, activationRate);
  }));
  public static final ActivationSettings HARD = hard(ExtShapeBlockTypes.HARD_BLOCK_SET_TYPE);
  public static final ActivationSettings WOOL = new ActivationSettings(ExtShapeBlockTypes.WOOL_BLOCK_SET_TYPE, 50, 40);
  public static final ActivationSettings SNOW = new ActivationSettings(ExtShapeBlockTypes.SNOW_BLOCK_SET_TYPE, 55, 55);
  public static final ActivationSettings MOSS = new ActivationSettings(ExtShapeBlockTypes.MOSS_BLOCK_SET_TYPE, 60, 60);
  public static final ActivationSettings BAMBOO = wood(BlockSetType.BAMBOO);
  public static final ActivationSettings GRAVEL = new ActivationSettings(ExtShapeBlockTypes.GRAVEL_BLOCK_SET_TYPE, 45, 45);
  public static final ActivationSettings ORE_BLOCK = new ActivationSettings(BlockSetType.STONE, 15, 10);
  public static final ActivationSettings GOLD = new ActivationSettings(BlockSetType.GOLD, 15, 10);
  public static final ActivationSettings IRON = new ActivationSettings(BlockSetType.IRON, 15, 10);
  public static final ActivationSettings CROPS = new ActivationSettings(BlockSetType.OAK, 35, 25);
  public static final ActivationSettings QUARTZ = new ActivationSettings(ExtShapeBlockTypes.QUARTZ_BLOCK_SET_TYPE, 25, 20);
  public static final ActivationSettings BASALT = new ActivationSettings(ExtShapeBlockTypes.BASALT_BLOCK_SET_TYPE, 15, 15);
  public static final ActivationSettings DRIPSTONE = stone(ExtShapeBlockTypes.DRIPSTONE_BLOCK_SET_TYPE);
  public static final ActivationSettings TUFF = softerStone(ExtShapeBlockTypes.TUFF_BLOCK_SET_TYPE);
  public static final ActivationSettings DEEPSLATE = new ActivationSettings(ExtShapeBlockTypes.DEEPSLATE_BLOCK_SET_TYPE, 10, 10);
  public static final ActivationSettings DEEPSLATE_BRICKS = new ActivationSettings(ExtShapeBlockTypes.DEEPSLATE_BRICKS_BLOCK_SET_TYPE, 10, 10);
  public static final ActivationSettings NETHERRACK = softerStone(ExtShapeBlockTypes.NETHERRACK_BLOCK_SET_TYPE);
  public static final ActivationSettings GLOWSTONE = new ActivationSettings(ExtShapeBlockTypes.GLOWSTONE_BLOCK_SET_TYPE, 30, 30);
  public static final ActivationSettings WART = new ActivationSettings(ExtShapeBlockTypes.WART_BLOCK_SET_TYPE, 45, 45);
}
