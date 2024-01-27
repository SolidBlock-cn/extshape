package pers.solid.extshape.util;

import net.minecraft.block.WoodType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public record FenceSettings(Item secondIngredient, WoodType woodType) {
  public static final FenceSettings DEFAULT = new FenceSettings(Items.STICK, WoodType.OAK);
  public static final FenceSettings AMETHYST = new FenceSettings(Items.AMETHYST_SHARD, ExtShapeBlockTypes.AMETHYST_WOOD_TYPE);
  public static final FenceSettings BAMBOO_BLOCK = new FenceSettings(Items.BAMBOO, WoodType.BAMBOO);
  public static final FenceSettings BAMBOO_PLANKS = new FenceSettings(Items.STICK, WoodType.BAMBOO);
  public static final FenceSettings STONE = new FenceSettings(Items.FLINT, ExtShapeBlockTypes.STONE_WOOD_TYPE);
  public static final FenceSettings DIRT = new FenceSettings(Items.STICK, ExtShapeBlockTypes.GRAVEL_WOOD_TYPE);
  public static final FenceSettings WOOL = new FenceSettings(Items.STRING, ExtShapeBlockTypes.WOOL_WOOD_TYPE);
  public static final FenceSettings SNOW = new FenceSettings(Items.SNOW, ExtShapeBlockTypes.SNOW_WOOD_TYPE);
  public static final FenceSettings QUARTZ = new FenceSettings(Items.QUARTZ, ExtShapeBlockTypes.QUARTZ_WOOD_TYPE);
  public static final FenceSettings BASALT = new FenceSettings(Items.FLINT, ExtShapeBlockTypes.BASALT_WOOD_TYPE);
}
