package pers.solid.extshape.tag;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;

/**
 * 原版 Minecraft 的 Mineable 标签的内容，更新于 1.17。<br>
 * 若基础方块为这些方块，则会加入对应的标签。
 *
 * @since 1.5.0 降低了本类内容的可访问性。
 */
final class Mineable {
  /**
   * 不含木板、原木等。部分不可能作为基础方块的予以注释掉。
   *
   * @see net.minecraft.tag.BlockTags#AXE_MINEABLE
   */
  static final @Unmodifiable ImmutableSet<Block> VANILLA_AXE_MINEABLE = ImmutableSet.of(
//      Blocks.NOTE_BLOCK,
//      Blocks.ATTACHED_MELON_STEM,
//      Blocks.ATTACHED_PUMPKIN_STEM,
//      Blocks.AZALEA,
//      Blocks.BAMBOO,
//      Blocks.BARREL,
//      Blocks.BEE_NEST,
//      Blocks.BEEHIVE,
//      Blocks.BEETROOTS,
//      Blocks.BIG_DRIPLEAF_STEM,
//      Blocks.BIG_DRIPLEAF,
      Blocks.BOOKSHELF,
//      Blocks.BROWN_MUSHROOM_BLOCK,
//      Blocks.BROWN_MUSHROOM,
//      Blocks.CAMPFIRE,
//      Blocks.CARROTS,
//      Blocks.CARTOGRAPHY_TABLE,
//      Blocks.CARVED_PUMPKIN,
//      Blocks.CAVE_VINES_PLANT,
//      Blocks.CAVE_VINES,
//      Blocks.CHEST,
//      Blocks.CHORUS_FLOWER,
//      Blocks.CHORUS_PLANT,
//      Blocks.COCOA,
//      Blocks.COMPOSTER,
      Blocks.CRAFTING_TABLE,
      Blocks.CRIMSON_FUNGUS,
//      Blocks.DAYLIGHT_DETECTOR,
//      Blocks.DEAD_BUSH,
//      Blocks.FERN,
      Blocks.FLETCHING_TABLE,
//      Blocks.GLOW_LICHEN,
//      Blocks.GRASS,
//      Blocks.HANGING_ROOTS,
//      Blocks.JACK_O_LANTERN,
//      Blocks.JUKEBOX,
//      Blocks.LADDER,
//      Blocks.LARGE_FERN,
//      Blocks.LECTERN,
//      Blocks.LILY_PAD,
//      Blocks.LOOM,
//      Blocks.MELON_STEM,
      Blocks.MELON,
//      Blocks.MUSHROOM_STEM,
      Blocks.NETHER_WART,
//      Blocks.POTATOES,
//      Blocks.PUMPKIN_STEM,
      Blocks.PUMPKIN,
      Blocks.RED_MUSHROOM_BLOCK,
//      Blocks.RED_MUSHROOM,
//      Blocks.SCAFFOLDING,
//      Blocks.SMALL_DRIPLEAF,
//      Blocks.SMITHING_TABLE,
//      Blocks.SOUL_CAMPFIRE,
//      Blocks.SPORE_BLOSSOM,
//      Blocks.SUGAR_CANE,
//      Blocks.SWEET_BERRY_BUSH,
//      Blocks.TALL_GRASS,
//      Blocks.TRAPPED_CHEST,
//      Blocks.TWISTING_VINES_PLANT,
//      Blocks.TWISTING_VINES,
//      Blocks.VINE,
      Blocks.WARPED_FUNGUS
//      Blocks.WEEPING_VINES_PLANT,
//      Blocks.WEEPING_VINES,
//      Blocks.WHEAT
  );
  static final @Unmodifiable ImmutableSet<Block> VANILLA_HOE_MINEABLE = ImmutableSet.of(
      Blocks.NETHER_WART_BLOCK,
      Blocks.WARPED_WART_BLOCK,
      Blocks.HAY_BLOCK,
      Blocks.DRIED_KELP_BLOCK,
//      Blocks.TARGET,
      Blocks.SHROOMLIGHT,
      Blocks.SPONGE,
      Blocks.WET_SPONGE,
//      Blocks.JUNGLE_LEAVES,
//      Blocks.OAK_LEAVES,
//      Blocks.SPRUCE_LEAVES,
//      Blocks.DARK_OAK_LEAVES,
//      Blocks.ACACIA_LEAVES,
//      Blocks.BIRCH_LEAVES,
//      Blocks.AZALEA_LEAVES,
//      Blocks.FLOWERING_AZALEA_LEAVES,
//      Blocks.SCULK_SENSOR,
      Blocks.MOSS_BLOCK
//      Blocks.MOSS_CARPET
  );
  static final @Unmodifiable ImmutableSet<Block> VANILLA_PICKAXE_MINEABLE = ImmutableSet.of(
      Blocks.STONE,
      Blocks.GRANITE,
      Blocks.POLISHED_GRANITE,
      Blocks.DIORITE,
      Blocks.POLISHED_DIORITE,
      Blocks.ANDESITE,
      Blocks.POLISHED_ANDESITE,
      Blocks.COBBLESTONE,
      Blocks.GOLD_ORE,
      Blocks.DEEPSLATE_GOLD_ORE,
      Blocks.IRON_ORE,
      Blocks.DEEPSLATE_IRON_ORE,
      Blocks.COAL_ORE,
      Blocks.DEEPSLATE_COAL_ORE,
      Blocks.NETHER_GOLD_ORE,
      Blocks.LAPIS_ORE,
      Blocks.DEEPSLATE_LAPIS_ORE,
      Blocks.LAPIS_BLOCK,
//      Blocks.DISPENSER,
      Blocks.SANDSTONE,
      Blocks.CHISELED_SANDSTONE,
      Blocks.CUT_SANDSTONE,
      Blocks.GOLD_BLOCK,
      Blocks.IRON_BLOCK,
      Blocks.BRICKS,
      Blocks.MOSSY_COBBLESTONE,
      Blocks.OBSIDIAN,
//      Blocks.SPAWNER,
      Blocks.DIAMOND_ORE,
      Blocks.DEEPSLATE_DIAMOND_ORE,
      Blocks.DIAMOND_BLOCK,
//      Blocks.FURNACE,
//      Blocks.COBBLESTONE_STAIRS,
//      Blocks.STONE_PRESSURE_PLATE,
//      Blocks.IRON_DOOR,
      Blocks.REDSTONE_ORE,
      Blocks.DEEPSLATE_REDSTONE_ORE,
      Blocks.NETHERRACK,
      Blocks.BASALT,
      Blocks.POLISHED_BASALT,
      Blocks.STONE_BRICKS,
      Blocks.MOSSY_STONE_BRICKS,
      Blocks.CRACKED_STONE_BRICKS,
      Blocks.CHISELED_STONE_BRICKS,
//      Blocks.IRON_BARS,
//      Blocks.CHAIN,
//      Blocks.BRICK_STAIRS,
//      Blocks.STONE_BRICK_STAIRS,
      Blocks.NETHER_BRICKS,
//      Blocks.NETHER_BRICK_FENCE,
//      Blocks.NETHER_BRICK_STAIRS,
//      Blocks.ENCHANTING_TABLE,
//      Blocks.BREWING_STAND,
      Blocks.END_STONE,
//      Blocks.SANDSTONE_STAIRS,
      Blocks.EMERALD_ORE,
      Blocks.DEEPSLATE_EMERALD_ORE,
//      Blocks.ENDER_CHEST,
      Blocks.EMERALD_BLOCK,
//      Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE,
//      Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,
      Blocks.REDSTONE_BLOCK,
      Blocks.NETHER_QUARTZ_ORE,
      Blocks.HOPPER,
      Blocks.QUARTZ_BLOCK,
      Blocks.CHISELED_QUARTZ_BLOCK,
      Blocks.QUARTZ_PILLAR,
      Blocks.QUARTZ_STAIRS,
      Blocks.DROPPER,
      Blocks.WHITE_TERRACOTTA,
      Blocks.ORANGE_TERRACOTTA,
      Blocks.MAGENTA_TERRACOTTA,
      Blocks.LIGHT_BLUE_TERRACOTTA,
      Blocks.YELLOW_TERRACOTTA,
      Blocks.LIME_TERRACOTTA,
      Blocks.PINK_TERRACOTTA,
      Blocks.GRAY_TERRACOTTA,
      Blocks.LIGHT_GRAY_TERRACOTTA,
      Blocks.CYAN_TERRACOTTA,
      Blocks.PURPLE_TERRACOTTA,
      Blocks.BLUE_TERRACOTTA,
      Blocks.BROWN_TERRACOTTA,
      Blocks.GREEN_TERRACOTTA,
      Blocks.RED_TERRACOTTA,
      Blocks.BLACK_TERRACOTTA,
      Blocks.IRON_TRAPDOOR,
      Blocks.PRISMARINE,
      Blocks.PRISMARINE_BRICKS,
      Blocks.DARK_PRISMARINE,
//      Blocks.PRISMARINE_STAIRS,
//      Blocks.PRISMARINE_BRICK_STAIRS,
//      Blocks.DARK_PRISMARINE_STAIRS,
//      Blocks.PRISMARINE_SLAB,
//      Blocks.PRISMARINE_BRICK_SLAB,
//      Blocks.DARK_PRISMARINE_SLAB,
      Blocks.TERRACOTTA,
      Blocks.COAL_BLOCK,
      Blocks.RED_SANDSTONE,
      Blocks.CHISELED_RED_SANDSTONE,
      Blocks.CUT_RED_SANDSTONE,
//      Blocks.RED_SANDSTONE_STAIRS,
//      Blocks.STONE_SLAB,
//      Blocks.SMOOTH_STONE_SLAB,
//      Blocks.SANDSTONE_SLAB,
//      Blocks.CUT_SANDSTONE_SLAB,
//      Blocks.PETRIFIED_OAK_SLAB,
//      Blocks.COBBLESTONE_SLAB,
//      Blocks.BRICK_SLAB,
//      Blocks.STONE_BRICK_SLAB,
//      Blocks.NETHER_BRICK_SLAB,
//      Blocks.QUARTZ_SLAB,
//      Blocks.RED_SANDSTONE_SLAB,
//      Blocks.CUT_RED_SANDSTONE_SLAB,
//      Blocks.PURPUR_SLAB,
      Blocks.SMOOTH_STONE,
      Blocks.SMOOTH_SANDSTONE,
      Blocks.SMOOTH_QUARTZ,
      Blocks.SMOOTH_RED_SANDSTONE,
      Blocks.PURPUR_BLOCK,
      Blocks.PURPUR_PILLAR,
//      Blocks.PURPUR_STAIRS,
      Blocks.END_STONE_BRICKS,
      Blocks.MAGMA_BLOCK,
      Blocks.RED_NETHER_BRICKS,
      Blocks.BONE_BLOCK,
//      Blocks.OBSERVER,
      Blocks.WHITE_GLAZED_TERRACOTTA,
      Blocks.ORANGE_GLAZED_TERRACOTTA,
      Blocks.MAGENTA_GLAZED_TERRACOTTA,
      Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA,
      Blocks.YELLOW_GLAZED_TERRACOTTA,
      Blocks.LIME_GLAZED_TERRACOTTA,
      Blocks.PINK_GLAZED_TERRACOTTA,
      Blocks.GRAY_GLAZED_TERRACOTTA,
      Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA,
      Blocks.CYAN_GLAZED_TERRACOTTA,
      Blocks.PURPLE_GLAZED_TERRACOTTA,
      Blocks.BLUE_GLAZED_TERRACOTTA,
      Blocks.BROWN_GLAZED_TERRACOTTA,
      Blocks.GREEN_GLAZED_TERRACOTTA,
      Blocks.RED_GLAZED_TERRACOTTA,
      Blocks.BLACK_GLAZED_TERRACOTTA,
      Blocks.WHITE_CONCRETE,
      Blocks.ORANGE_CONCRETE,
      Blocks.MAGENTA_CONCRETE,
      Blocks.LIGHT_BLUE_CONCRETE,
      Blocks.YELLOW_CONCRETE,
      Blocks.LIME_CONCRETE,
      Blocks.PINK_CONCRETE,
      Blocks.GRAY_CONCRETE,
      Blocks.LIGHT_GRAY_CONCRETE,
      Blocks.CYAN_CONCRETE,
      Blocks.PURPLE_CONCRETE,
      Blocks.BLUE_CONCRETE,
      Blocks.BROWN_CONCRETE,
      Blocks.GREEN_CONCRETE,
      Blocks.RED_CONCRETE,
      Blocks.BLACK_CONCRETE,
//      Blocks.DEAD_TUBE_CORAL_BLOCK,
//      Blocks.DEAD_BRAIN_CORAL_BLOCK,
//      Blocks.DEAD_BUBBLE_CORAL_BLOCK,
//      Blocks.DEAD_FIRE_CORAL_BLOCK,
//      Blocks.DEAD_HORN_CORAL_BLOCK,
//      Blocks.TUBE_CORAL_BLOCK,
//      Blocks.BRAIN_CORAL_BLOCK,
//      Blocks.BUBBLE_CORAL_BLOCK,
//      Blocks.FIRE_CORAL_BLOCK,
//      Blocks.HORN_CORAL_BLOCK,
//      Blocks.DEAD_TUBE_CORAL,
//      Blocks.DEAD_BRAIN_CORAL,
//      Blocks.DEAD_BUBBLE_CORAL,
//      Blocks.DEAD_FIRE_CORAL,
//      Blocks.DEAD_HORN_CORAL,
//      Blocks.DEAD_TUBE_CORAL_FAN,
//      Blocks.DEAD_BRAIN_CORAL_FAN,
//      Blocks.DEAD_BUBBLE_CORAL_FAN,
//      Blocks.DEAD_FIRE_CORAL_FAN,
//      Blocks.DEAD_HORN_CORAL_FAN,
//      Blocks.DEAD_TUBE_CORAL_WALL_FAN,
//      Blocks.DEAD_BRAIN_CORAL_WALL_FAN,
//      Blocks.DEAD_BUBBLE_CORAL_WALL_FAN,
//      Blocks.DEAD_FIRE_CORAL_WALL_FAN,
//      Blocks.DEAD_HORN_CORAL_WALL_FAN,
//      Blocks.POLISHED_GRANITE_STAIRS,
//      Blocks.SMOOTH_RED_SANDSTONE_STAIRS,
//      Blocks.MOSSY_STONE_BRICK_STAIRS,
//      Blocks.POLISHED_DIORITE_STAIRS,
//      Blocks.MOSSY_COBBLESTONE_STAIRS,
//      Blocks.END_STONE_BRICK_STAIRS,
//      Blocks.STONE_STAIRS,
//      Blocks.SMOOTH_SANDSTONE_STAIRS,
//      Blocks.SMOOTH_QUARTZ_STAIRS,
//      Blocks.GRANITE_STAIRS,
//      Blocks.ANDESITE_STAIRS,
//      Blocks.RED_NETHER_BRICK_STAIRS,
//      Blocks.POLISHED_ANDESITE_STAIRS,
//      Blocks.DIORITE_STAIRS,
//      Blocks.POLISHED_GRANITE_SLAB,
//      Blocks.SMOOTH_RED_SANDSTONE_SLAB,
//      Blocks.MOSSY_STONE_BRICK_SLAB,
//      Blocks.POLISHED_DIORITE_SLAB,
//      Blocks.MOSSY_COBBLESTONE_SLAB,
//      Blocks.END_STONE_BRICK_SLAB,
//      Blocks.SMOOTH_SANDSTONE_SLAB,
//      Blocks.SMOOTH_QUARTZ_SLAB,
//      Blocks.GRANITE_SLAB,
//      Blocks.ANDESITE_SLAB,
//      Blocks.RED_NETHER_BRICK_SLAB,
//      Blocks.POLISHED_ANDESITE_SLAB,
//      Blocks.DIORITE_SLAB,
//      Blocks.SMOKER,
//      Blocks.BLAST_FURNACE,
      Blocks.GRINDSTONE,
//      Blocks.STONECUTTER,
//      Blocks.BELL,
//      Blocks.LANTERN,
//      Blocks.SOUL_LANTERN,
      Blocks.WARPED_NYLIUM,
      Blocks.CRIMSON_NYLIUM,
      Blocks.NETHERITE_BLOCK,
      Blocks.ANCIENT_DEBRIS,
      Blocks.CRYING_OBSIDIAN,
//      Blocks.RESPAWN_ANCHOR,
      Blocks.LODESTONE,
      Blocks.BLACKSTONE,
//      Blocks.BLACKSTONE_STAIRS,
//      Blocks.BLACKSTONE_SLAB,
      Blocks.POLISHED_BLACKSTONE,
//      Blocks.POLISHED_BLACKSTONE_BRICKS,
//      Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS,
      Blocks.CHISELED_POLISHED_BLACKSTONE,
//      Blocks.POLISHED_BLACKSTONE_BRICK_SLAB,
//      Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS,
      Blocks.GILDED_BLACKSTONE,
//      Blocks.POLISHED_BLACKSTONE_STAIRS,
//      Blocks.POLISHED_BLACKSTONE_SLAB,
//      Blocks.POLISHED_BLACKSTONE_PRESSURE_PLATE,
      Blocks.CHISELED_NETHER_BRICKS,
      Blocks.CRACKED_NETHER_BRICKS,
      Blocks.QUARTZ_BRICKS,
      Blocks.TUFF,
      Blocks.CALCITE,
      Blocks.OXIDIZED_COPPER,
      Blocks.WEATHERED_COPPER,
      Blocks.EXPOSED_COPPER,
      Blocks.COPPER_BLOCK,
      Blocks.COPPER_ORE,
      Blocks.DEEPSLATE_COPPER_ORE,
      Blocks.OXIDIZED_CUT_COPPER,
      Blocks.WEATHERED_CUT_COPPER,
      Blocks.EXPOSED_CUT_COPPER,
      Blocks.CUT_COPPER,
//      Blocks.OXIDIZED_CUT_COPPER_STAIRS,
//      Blocks.WEATHERED_CUT_COPPER_STAIRS,
//      Blocks.EXPOSED_CUT_COPPER_STAIRS,
//      Blocks.CUT_COPPER_STAIRS,
//      Blocks.OXIDIZED_CUT_COPPER_SLAB,
//      Blocks.WEATHERED_CUT_COPPER_SLAB,
//      Blocks.EXPOSED_CUT_COPPER_SLAB,
//      Blocks.CUT_COPPER_SLAB,
      Blocks.WAXED_COPPER_BLOCK,
      Blocks.WAXED_WEATHERED_COPPER,
      Blocks.WAXED_EXPOSED_COPPER,
      Blocks.WAXED_OXIDIZED_COPPER,
      Blocks.WAXED_OXIDIZED_CUT_COPPER,
      Blocks.WAXED_WEATHERED_CUT_COPPER,
      Blocks.WAXED_EXPOSED_CUT_COPPER,
      Blocks.WAXED_CUT_COPPER,
//      Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS,
//      Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS,
//      Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS,
//      Blocks.WAXED_CUT_COPPER_STAIRS,
//      Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB,
//      Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB,
//      Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB,
//      Blocks.WAXED_CUT_COPPER_SLAB,
      Blocks.LIGHTNING_ROD,
      Blocks.POINTED_DRIPSTONE,
      Blocks.DRIPSTONE_BLOCK,
      Blocks.DEEPSLATE,
      Blocks.COBBLED_DEEPSLATE,
//      Blocks.COBBLED_DEEPSLATE_STAIRS,
//      Blocks.COBBLED_DEEPSLATE_SLAB,
      Blocks.POLISHED_DEEPSLATE,
//      Blocks.POLISHED_DEEPSLATE_STAIRS,
//      Blocks.POLISHED_DEEPSLATE_SLAB,
      Blocks.DEEPSLATE_TILES,
//      Blocks.DEEPSLATE_TILE_STAIRS,
//      Blocks.DEEPSLATE_TILE_SLAB,
      Blocks.DEEPSLATE_BRICKS,
//      Blocks.DEEPSLATE_BRICK_STAIRS,
//      Blocks.DEEPSLATE_BRICK_SLAB,
      Blocks.CHISELED_DEEPSLATE,
      Blocks.CRACKED_DEEPSLATE_BRICKS,
      Blocks.CRACKED_DEEPSLATE_TILES,
      Blocks.SMOOTH_BASALT,
      Blocks.RAW_IRON_BLOCK,
      Blocks.RAW_COPPER_BLOCK,
      Blocks.RAW_GOLD_BLOCK,
      Blocks.ICE,
      Blocks.PACKED_ICE,
      Blocks.BLUE_ICE,
//      Blocks.STONE_BUTTON,
//      Blocks.PISTON,
//      Blocks.STICKY_PISTON,
//      Blocks.PISTON_HEAD,
//      Blocks.AMETHYST_CLUSTER,
//      Blocks.SMALL_AMETHYST_BUD,
//      Blocks.MEDIUM_AMETHYST_BUD,
//      Blocks.LARGE_AMETHYST_BUD,
      Blocks.AMETHYST_BLOCK,
      Blocks.BUDDING_AMETHYST,
//      Blocks.INFESTED_COBBLESTONE,
//      Blocks.INFESTED_CHISELED_STONE_BRICKS,
//      Blocks.INFESTED_CRACKED_STONE_BRICKS,
//      Blocks.INFESTED_DEEPSLATE,
//      Blocks.INFESTED_STONE,
//      Blocks.INFESTED_MOSSY_STONE_BRICKS,
//      Blocks.INFESTED_STONE_BRICKS
      Blocks.MUD_BRICKS,
      Blocks.PACKED_MUD
  );
  static final @Unmodifiable ImmutableSet<Block> VANILLA_SHOVEL_MINEABLE = ImmutableSet.of(Blocks.CLAY,
      Blocks.DIRT,
      Blocks.COARSE_DIRT,
      Blocks.PODZOL,
      Blocks.FARMLAND,
      Blocks.GRASS_BLOCK,
      Blocks.GRAVEL,
      Blocks.MYCELIUM,
      Blocks.SAND,
      Blocks.RED_SAND,
      Blocks.SNOW_BLOCK,
      Blocks.SNOW,
      Blocks.SOUL_SAND,
      Blocks.DIRT_PATH,
//      Blocks.WHITE_CONCRETE_POWDER,
//      Blocks.ORANGE_CONCRETE_POWDER,
//      Blocks.MAGENTA_CONCRETE_POWDER,
//      Blocks.LIGHT_BLUE_CONCRETE_POWDER,
//      Blocks.YELLOW_CONCRETE_POWDER,
//      Blocks.LIME_CONCRETE_POWDER,
//      Blocks.PINK_CONCRETE_POWDER,
//      Blocks.GRAY_CONCRETE_POWDER,
//      Blocks.LIGHT_GRAY_CONCRETE_POWDER,
//      Blocks.CYAN_CONCRETE_POWDER,
//      Blocks.PURPLE_CONCRETE_POWDER,
//      Blocks.BLUE_CONCRETE_POWDER,
//      Blocks.BROWN_CONCRETE_POWDER,
//      Blocks.GREEN_CONCRETE_POWDER,
//      Blocks.RED_CONCRETE_POWDER,
//      Blocks.BLACK_CONCRETE_POWDER,
      Blocks.SOUL_SOIL,
      Blocks.ROOTED_DIRT
  );

  /**
   * @see net.minecraft.tag.BlockTags#NEEDS_DIAMOND_TOOL
   */
  @ApiStatus.AvailableSince("1.5.0")
  static final @Unmodifiable ImmutableSet<Block> NEEDS_DIAMOND_TOOL = ImmutableSet.of(
      Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN, Blocks.NETHERITE_BLOCK, Blocks.RESPAWN_ANCHOR, Blocks.ANCIENT_DEBRIS
  );
  /**
   * @see net.minecraft.tag.BlockTags#NEEDS_IRON_TOOL
   */
  @ApiStatus.AvailableSince("1.5.0")
  static final @Unmodifiable ImmutableSet<Block> NEEDS_IRON_TOOL = ImmutableSet.of(
      Blocks.DIAMOND_BLOCK,
      Blocks.DIAMOND_ORE,
      Blocks.DEEPSLATE_DIAMOND_ORE,
      Blocks.EMERALD_ORE,
      Blocks.DEEPSLATE_EMERALD_ORE,
      Blocks.EMERALD_BLOCK,
      Blocks.GOLD_BLOCK,
      Blocks.RAW_GOLD_BLOCK,
      Blocks.GOLD_ORE,
      Blocks.DEEPSLATE_GOLD_ORE,
      Blocks.REDSTONE_ORE,
      Blocks.DEEPSLATE_REDSTONE_ORE
  );
  /**
   * @see net.minecraft.tag.BlockTags#NEEDS_STONE_TOOL
   */
  @ApiStatus.AvailableSince("1.5.0")
  static final @Unmodifiable ImmutableSet<Block> NEEDS_STONE_TOOL = ImmutableSet.of(
      Blocks.IRON_BLOCK,
      Blocks.RAW_IRON_BLOCK,
      Blocks.IRON_ORE,
      Blocks.DEEPSLATE_IRON_ORE,
      Blocks.LAPIS_BLOCK,
      Blocks.LAPIS_ORE,
      Blocks.DEEPSLATE_LAPIS_ORE,
      Blocks.COPPER_BLOCK,
      Blocks.RAW_COPPER_BLOCK,
      Blocks.COPPER_ORE,
      Blocks.DEEPSLATE_COPPER_ORE,
//      Blocks.CUT_COPPER_SLAB,
//      Blocks.CUT_COPPER_STAIRS,
      Blocks.CUT_COPPER,
      Blocks.WEATHERED_COPPER,
//      Blocks.WEATHERED_CUT_COPPER_SLAB,
//      Blocks.WEATHERED_CUT_COPPER_STAIRS,
      Blocks.WEATHERED_CUT_COPPER,
      Blocks.OXIDIZED_COPPER,
//      Blocks.OXIDIZED_CUT_COPPER_SLAB,
//      Blocks.OXIDIZED_CUT_COPPER_STAIRS,
      Blocks.OXIDIZED_CUT_COPPER,
      Blocks.EXPOSED_COPPER,
//      Blocks.EXPOSED_CUT_COPPER_SLAB,
//      Blocks.EXPOSED_CUT_COPPER_STAIRS,
      Blocks.EXPOSED_CUT_COPPER,
      Blocks.WAXED_COPPER_BLOCK,
//      Blocks.WAXED_CUT_COPPER_SLAB,
//      Blocks.WAXED_CUT_COPPER_STAIRS,
      Blocks.WAXED_CUT_COPPER,
      Blocks.WAXED_WEATHERED_COPPER,
//      Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB,
//      Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS,
      Blocks.WAXED_WEATHERED_CUT_COPPER,
      Blocks.WAXED_EXPOSED_COPPER,
//      Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB,
//      Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS,
      Blocks.WAXED_EXPOSED_CUT_COPPER,
      Blocks.WAXED_OXIDIZED_COPPER,
//      Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB,
//      Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS,
      Blocks.WAXED_OXIDIZED_CUT_COPPER/*,
      Blocks.LIGHTNING_ROD*/);

  private Mineable() {
  }
}
