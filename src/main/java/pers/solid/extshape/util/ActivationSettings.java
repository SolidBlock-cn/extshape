package pers.solid.extshape.util;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import pers.solid.extshape.block.CopperManager;

import java.util.Arrays;

/**
 * 用于按钮、压力板的一些设置。
 *
 * @param buttonTime 按钮的触发持续时间。
 * @param plateTime  压力板的触发持续时间。
 */
public record ActivationSettings(int buttonTime, int plateTime, boolean buttonActivatedByProjectile, PressurePlateBlock.ActivationRule activationRule, Sounds sounds) {

  public static ActivationSettings stone(Sounds sounds) {
    return new ActivationSettings(20, 20, false, PressurePlateBlock.ActivationRule.MOBS, sounds);
  }

  public static ActivationSettings soft(Sounds sounds) {
    return new ActivationSettings(60, 60, true, PressurePlateBlock.ActivationRule.EVERYTHING, sounds);
  }

  public static ActivationSettings softerStone(Sounds sounds) {
    return new ActivationSettings(40, 30, false, PressurePlateBlock.ActivationRule.MOBS, sounds);
  }

  public static ActivationSettings wood(Sounds sounds) {
    return new ActivationSettings(30, 20, true, PressurePlateBlock.ActivationRule.EVERYTHING, sounds);
  }

  public static ActivationSettings hard(Sounds sounds) {
    return new ActivationSettings(5, 5, false, PressurePlateBlock.ActivationRule.MOBS, sounds);
  }

  public static final ActivationSettings WOOD = wood(Sounds.WOOD);
  public static final ActivationSettings NETHER_WOOD = wood(Sounds.NETHER_WOOD);
  public static final ActivationSettings STONE = stone(Sounds.STONE);
  public static final ImmutableMap<Oxidizable.OxidationLevel, ActivationSettings> COPPER = Arrays.stream(Oxidizable.OxidationLevel.values()).collect(ImmutableMap.toImmutableMap(Functions.identity(), oxidationLevel -> {
    final int activationRate = CopperManager.getActivationRate(oxidationLevel);
    return new ActivationSettings(activationRate, activationRate, false, PressurePlateBlock.ActivationRule.MOBS, Sounds.STONE);
  }));
  public static final ActivationSettings HARD = hard(Sounds.STONE);
  public static final ActivationSettings SOFT = soft(Sounds.STONE);
  public static final ActivationSettings WOOL = new ActivationSettings(50, 50, true, PressurePlateBlock.ActivationRule.EVERYTHING, new Sounds(SoundEvents.BLOCK_WOOL_HIT));
  public static final ActivationSettings BAMBOO = wood(Sounds.BAMBOO);
  public static final ActivationSettings GRAVEL = soft(Sounds.STONE);
  public static final ActivationSettings ORE_BLOCK = new ActivationSettings(15, 10, false, PressurePlateBlock.ActivationRule.MOBS, Sounds.STONE);
  public static final ActivationSettings GOLD = new ActivationSettings(15, 10, false, PressurePlateBlock.ActivationRule.MOBS, Sounds.METAL);
  public static final ActivationSettings IRON = new ActivationSettings(15, 10, false, PressurePlateBlock.ActivationRule.MOBS, Sounds.METAL);
  public static final ActivationSettings CROPS = new ActivationSettings(50, 40, false, PressurePlateBlock.ActivationRule.MOBS, Sounds.METAL);
  public static final ActivationSettings QUARTZ = new ActivationSettings(25, 25, false, PressurePlateBlock.ActivationRule.MOBS, Sounds.STONE);
  public static final ActivationSettings BASALT = STONE;
  public static final ActivationSettings DRIPSTONE = STONE;
  public static final ActivationSettings TUFF = softerStone(Sounds.STONE);
  public static final ActivationSettings DEEPSLATE = STONE;
  public static final ActivationSettings DEEPSLATE_BRICKS = STONE;
  public static final ActivationSettings NETHERRACK = STONE;
  public static final ActivationSettings WART = soft(new Sounds(SoundEvents.BLOCK_WART_BLOCK_HIT));

  public record Sounds(SoundEvent clickOffSound, SoundEvent clickOnSound, SoundEvent depressSound, SoundEvent pressSound) {
    public static final Sounds STONE = new Sounds(SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON);
    public static final Sounds WOOD = new Sounds(SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_OFF, SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_ON, SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON);
    public static final Sounds NETHER_WOOD = new Sounds(SoundEvents.BLOCK_NETHER_WOOD_BUTTON_CLICK_OFF, SoundEvents.BLOCK_NETHER_WOOD_BUTTON_CLICK_ON, SoundEvents.BLOCK_NETHER_WOOD_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_NETHER_WOOD_PRESSURE_PLATE_CLICK_ON);
    public static final Sounds BAMBOO = new Sounds(SoundEvents.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_OFF, SoundEvents.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_ON, SoundEvents.BLOCK_BAMBOO_WOOD_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_BAMBOO_WOOD_PRESSURE_PLATE_CLICK_ON);
    public static final Sounds METAL = new Sounds(SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON);

    public Sounds(SoundEvent constSound) {
      this(constSound, constSound, constSound, constSound);
    }
  }
}
