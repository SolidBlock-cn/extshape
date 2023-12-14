package pers.solid.extshape.util;

import net.minecraft.block.WoodType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public record FenceSettings(Item secondIngredient, WoodType woodType) {
  public static final FenceSettings DEFAULT = common(Items.STICK);
  public static final FenceSettings AMETHYST = new FenceSettings(Items.AMETHYST_SHARD, ExtShapeBlockTypes.AMETHYST_WOOD_TYPE);

  public static FenceSettings common(Item secondIngredient) {
    return new FenceSettings(secondIngredient, WoodType.OAK);
  }
}
