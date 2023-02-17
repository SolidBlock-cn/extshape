package pers.solid.extshape.util;

import net.minecraft.block.WoodType;
import net.minecraft.item.Item;

public record FenceSettings(Item secondIngredient, WoodType woodType) {
  public static FenceSettings common(Item secondIngredient) {
    return new FenceSettings(secondIngredient, WoodType.OAK);
  }
}
