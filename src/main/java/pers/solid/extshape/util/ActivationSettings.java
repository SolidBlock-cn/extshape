package pers.solid.extshape.util;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import pers.solid.extshape.ExtShape;
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
    return new ActivationSettings(50, 40, true, PressurePlateBlock.ActivationRule.EVERYTHING, sounds);
  }

  public static ActivationSettings softerStone(Sounds sounds) {
    return new ActivationSettings(25, 20, false, PressurePlateBlock.ActivationRule.MOBS, sounds);
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
  public static final ActivationSettings WOOL = new ActivationSettings(50, 40, true, PressurePlateBlock.ActivationRule.EVERYTHING, Sounds.of("wool"));
  public static final ActivationSettings SNOW = new ActivationSettings(55, 55, true, PressurePlateBlock.ActivationRule.EVERYTHING, Sounds.of("snow"));
  public static final ActivationSettings MOSS = new ActivationSettings(60, 60, true, PressurePlateBlock.ActivationRule.EVERYTHING, Sounds.of("moss"));
  public static final ActivationSettings BAMBOO = wood(Sounds.BAMBOO);
  public static final ActivationSettings GRAVEL = soft(Sounds.STONE);
  public static final ActivationSettings ORE_BLOCK = new ActivationSettings(15, 10, false, PressurePlateBlock.ActivationRule.MOBS, Sounds.STONE);
  public static final ActivationSettings GOLD = new ActivationSettings(15, 10, false, PressurePlateBlock.ActivationRule.MOBS, Sounds.METAL);
  public static final ActivationSettings IRON = new ActivationSettings(15, 10, false, PressurePlateBlock.ActivationRule.MOBS, Sounds.METAL);
  public static final ActivationSettings CROPS = new ActivationSettings(35, 25, false, PressurePlateBlock.ActivationRule.MOBS, Sounds.METAL);
  public static final ActivationSettings QUARTZ = new ActivationSettings(25, 20, false, PressurePlateBlock.ActivationRule.MOBS, Sounds.STONE);
  public static final ActivationSettings BASALT = new ActivationSettings(15, 15, false, PressurePlateBlock.ActivationRule.MOBS, Sounds.STONE);
  public static final ActivationSettings DRIPSTONE = STONE;
  public static final ActivationSettings TUFF = softerStone(Sounds.STONE);
  public static final ActivationSettings DEEPSLATE = new ActivationSettings(10, 10, false, PressurePlateBlock.ActivationRule.MOBS, Sounds.STONE);
  public static final ActivationSettings DEEPSLATE_BRICKS = DEEPSLATE;
  public static final ActivationSettings NETHERRACK = softerStone(Sounds.STONE);
  public static final ActivationSettings GLOWSTONE = new ActivationSettings(30, 30, true, PressurePlateBlock.ActivationRule.EVERYTHING, Sounds.WOOD);
  public static final ActivationSettings WART = new ActivationSettings(45, 45, true, PressurePlateBlock.ActivationRule.EVERYTHING, Sounds.of("wart"));

  public record Sounds(SoundEvent clickOffSound, SoundEvent clickOnSound, SoundEvent depressSound, SoundEvent pressSound) {
    private static SoundEvent sound(String name) {
      return SoundEvent.of(new Identifier(ExtShape.MOD_ID, name));
    }

    public Sounds(String clickOffSound, String clickOnSound, String depressSound, String pressSound) {
      this(sound(clickOffSound), sound(clickOnSound), sound(depressSound), sound(pressSound));
    }

    public static Sounds of(String name) {
      return new Sounds(
          "block." + name + "_button.click_on",
          "block." + name + "_button.click_off",
          "block." + name + "_pressure_plate.click_on",
          "block." + name + "_pressure_plate.click_off"
      );
    }

    public static final Sounds STONE = new Sounds(SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON);
    public static final Sounds WOOD = new Sounds(SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_OFF, SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_ON, SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON);
    public static final Sounds NETHER_WOOD = new Sounds(SoundEvents.BLOCK_NETHER_WOOD_BUTTON_CLICK_OFF, SoundEvents.BLOCK_NETHER_WOOD_BUTTON_CLICK_ON, SoundEvents.BLOCK_NETHER_WOOD_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_NETHER_WOOD_PRESSURE_PLATE_CLICK_ON);
    public static final Sounds BAMBOO = new Sounds(SoundEvents.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_OFF, SoundEvents.BLOCK_BAMBOO_WOOD_BUTTON_CLICK_ON, SoundEvents.BLOCK_BAMBOO_WOOD_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_BAMBOO_WOOD_PRESSURE_PLATE_CLICK_ON);
    public static final Sounds METAL = new Sounds(SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON);
    public static final Sounds SCULK = of("sculk");
    public static final Sounds SLIME = of("slime");
  }
}
