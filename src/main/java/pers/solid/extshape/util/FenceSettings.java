package pers.solid.extshape.util;

import net.minecraft.block.WoodType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

/**
 * 存储用于栅栏和栅栏门的一些设置，在构建方块时会使用到。
 *
 * @param secondIngredient 栅栏或栅栏门的除了其基础方块之外的第二合成材料。在本模组中，不同材质的栅栏或栅栏门有不同的第二合成材料。
 * @param woodType         控制栅栏或栅栏门的声音。
 */
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
  public static final FenceSettings DRIPSTONE = new FenceSettings(Items.POINTED_DRIPSTONE, ExtShapeBlockTypes.DRIPSTONE_WOOD_TYPE);
  public static final FenceSettings TUFF = new FenceSettings(Items.FLINT, ExtShapeBlockTypes.TUFF_WOOD_TYPE);
  public static final FenceSettings DEEPSLATE = new FenceSettings(Items.FLINT, ExtShapeBlockTypes.DEEPSLATE_WOOD_TYPE);
  public static final FenceSettings DEEPSLATE_BRICKS = new FenceSettings(Items.FLINT, ExtShapeBlockTypes.DEEPSLATE_BRICKS_WOOD_TYPE);
  public static final FenceSettings NETHERRACK = new FenceSettings(Items.NETHER_BRICK, ExtShapeBlockTypes.NETHERRACK_WOOD_TYPE);
}
