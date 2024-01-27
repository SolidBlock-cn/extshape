package pers.solid.extshape.util;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.Oxidizable;
import pers.solid.extshape.block.CopperManager;

import java.util.Arrays;

public record ActivationSettings(BlockSetType blockSetType, int buttonTime, int plateTime) {
  public static ActivationSettings stone(BlockSetType blockSetType) {
    return new ActivationSettings(blockSetType, 20, 20);
  }

  public static ActivationSettings soft(BlockSetType blockSetType) {
    return new ActivationSettings(blockSetType, 60, 60);
  }

  public static ActivationSettings wood(BlockSetType blockSetType) {
    return new ActivationSettings(blockSetType, 30, 20);
  }

  public static ActivationSettings hard(BlockSetType blockSetType) {
    return new ActivationSettings(blockSetType, 5, 5);
  }

  public static final ActivationSettings PSUDO_WOODEN = wood(ExtShapeBlockTypes.SOFT);
  public static final ActivationSettings STONE = stone(BlockSetType.STONE);
  public static final ImmutableMap<Oxidizable.OxidationLevel, ActivationSettings> COPPER = Arrays.stream(Oxidizable.OxidationLevel.values()).collect(ImmutableMap.toImmutableMap(Functions.identity(), oxidationLevel -> {
    final int activationRate = CopperManager.getActivationRate(oxidationLevel);
    return new ActivationSettings(BlockSetType.COPPER, activationRate, activationRate);
  }));
  public static final ActivationSettings HARD = hard(BlockSetType.STONE);
  public static final ActivationSettings SOFT = soft(ExtShapeBlockTypes.SOFT);
  public static final ActivationSettings WOOL = new ActivationSettings(ExtShapeBlockTypes.WOOL, 50, 50);
  public static final ActivationSettings BAMBOO = wood(BlockSetType.BAMBOO);
  public static final ActivationSettings DIRT = soft(ExtShapeBlockTypes.DIRT_BLOCK_SET_TYPE);
  public static final ActivationSettings ORE_BLOCK = new ActivationSettings(BlockSetType.STONE, 15, 10);
  public static final ActivationSettings GOLD = new ActivationSettings(BlockSetType.GOLD, 15, 10);
  public static final ActivationSettings IRON = new ActivationSettings(BlockSetType.IRON, 15, 10);
  public static final ActivationSettings CROPS = new ActivationSettings(ExtShapeBlockTypes.SOFT, 50, 40);
  public static final ActivationSettings QUARTZ = new ActivationSettings(ExtShapeBlockTypes.QUARTZ, 25, 25);
  public static final ActivationSettings SOFTER_STONE = new ActivationSettings(BlockSetType.STONE, 40, 30);
}
