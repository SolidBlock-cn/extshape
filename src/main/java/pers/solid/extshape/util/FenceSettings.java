package pers.solid.extshape.util;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

/**
 * 存储用于栅栏和栅栏门的一些设置，在构建方块时会使用到。
 *
 * @param secondIngredient 栅栏或栅栏门的除了其基础方块之外的第二合成材料。在本模组中，不同材质的栅栏或栅栏门有不同的第二合成材料。
 * @param woodType         控制栅栏或栅栏门的声音。
 */
public record FenceSettings(Item secondIngredient, SoundEvent closeSound, SoundEvent openSound) {
  public FenceSettings(Item secondIngredient) {
    this(secondIngredient, SoundEvents.BLOCK_FENCE_GATE_CLOSE, SoundEvents.BLOCK_FENCE_GATE_OPEN);
  }

  public FenceSettings(Item secondIngredient, SoundEvent constSound) {
    this(secondIngredient, constSound, constSound);
  }

  public static final FenceSettings DEFAULT = new FenceSettings(Items.STICK, SoundEvents.BLOCK_FENCE_GATE_CLOSE, SoundEvents.BLOCK_FENCE_GATE_OPEN);
  public static final FenceSettings WOOD = DEFAULT;
  public static final FenceSettings NETHER_WOOD = new FenceSettings(Items.STICK, SoundEvents.BLOCK_NETHER_WOOD_FENCE_GATE_CLOSE, SoundEvents.BLOCK_NETHER_WOOD_FENCE_GATE_OPEN);
  public static final FenceSettings AMETHYST = new FenceSettings(Items.AMETHYST_SHARD, SoundEvents.BLOCK_AMETHYST_BLOCK_HIT);
  public static final FenceSettings BAMBOO_BLOCK = new FenceSettings(Items.BAMBOO, SoundEvents.BLOCK_BAMBOO_WOOD_FENCE_GATE_CLOSE, SoundEvents.BLOCK_BAMBOO_WOOD_FENCE_GATE_OPEN);
  public static final FenceSettings BAMBOO_PLANKS = new FenceSettings(Items.STICK, SoundEvents.BLOCK_BAMBOO_WOOD_FENCE_GATE_CLOSE, SoundEvents.BLOCK_BAMBOO_WOOD_FENCE_GATE_OPEN);
  public static final FenceSettings STONE = new FenceSettings(Items.FLINT);
  public static final FenceSettings DIRT = DEFAULT;
  public static final FenceSettings WOOL = new FenceSettings(Items.STRING, SoundEvents.BLOCK_WOOL_HIT);
  public static final FenceSettings SNOW = new FenceSettings(Items.SNOW, SoundEvents.BLOCK_SNOW_HIT);
  public static final FenceSettings QUARTZ = new FenceSettings(Items.QUARTZ);
  public static final FenceSettings BASALT = STONE;
  public static final FenceSettings DRIPSTONE = new FenceSettings(Items.POINTED_DRIPSTONE);
  public static final FenceSettings TUFF = STONE;
  public static final FenceSettings DEEPSLATE = STONE;
  public static final FenceSettings DEEPSLATE_BRICKS = STONE;
  public static final FenceSettings NETHERRACK = new FenceSettings(Items.NETHER_BRICK);
  public static final FenceSettings WART = new FenceSettings(Items.NETHER_WART, SoundEvents.BLOCK_WART_BLOCK_HIT);
}
