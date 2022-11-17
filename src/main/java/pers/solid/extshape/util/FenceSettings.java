package pers.solid.extshape.util;

import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public record FenceSettings(Item secondIngredient, SoundEvent closeSoundEvent, SoundEvent openSoundEvent) {
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
