package pers.solid.extshape.util;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public record FenceSettings(Item secondIngredient, SoundEvent closeSoundEvent, SoundEvent openSoundEvent) {
  public static final FenceSettings DEFAULT = common(Items.STICK);
  public static final FenceSettings AMETHYST = new FenceSettings(Items.AMETHYST_SHARD, SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, SoundEvents.BLOCK_AMETHYST_BLOCK_HIT);

  public static FenceSettings common(Item secondIngredient) {
    return new FenceSettings(secondIngredient, SoundEvents.BLOCK_FENCE_GATE_CLOSE, SoundEvents.BLOCK_FENCE_GATE_OPEN);
  }

  public static FenceSettings netherWood(Item secondIngredient) {
    return new FenceSettings(secondIngredient, SoundEvents.BLOCK_NETHER_WOOD_FENCE_GATE_CLOSE, SoundEvents.BLOCK_NETHER_WOOD_FENCE_GATE_OPEN);
  }

  public static FenceSettings bambooWood(Item secondIngredient) {
    return new FenceSettings(secondIngredient, SoundEvents.BLOCK_BAMBOO_WOOD_FENCE_GATE_CLOSE, SoundEvents.BLOCK_BAMBOO_WOOD_FENCE_GATE_OPEN);
  }
}
