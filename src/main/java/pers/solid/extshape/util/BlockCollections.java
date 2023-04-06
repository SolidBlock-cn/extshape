package pers.solid.extshape.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import org.jetbrains.annotations.Unmodifiable;

import static net.minecraft.block.Blocks.*;

public final class BlockCollections {
  public static final ImmutableList<Block> LOGS = ImmutableList.of(
      OAK_LOG,
      SPRUCE_LOG,
      BIRCH_LOG,
      JUNGLE_LOG,
      ACACIA_LOG,
      CHERRY_LOG,
      DARK_OAK_LOG,
      MANGROVE_LOG
  );
  public static final ImmutableList<Block> STRIPPED_LOGS = ImmutableList.of(
      STRIPPED_OAK_LOG,
      STRIPPED_SPRUCE_LOG,
      STRIPPED_BIRCH_LOG,
      STRIPPED_JUNGLE_LOG,
      STRIPPED_ACACIA_LOG,
      STRIPPED_CHERRY_LOG,
      STRIPPED_DARK_OAK_LOG,
      STRIPPED_OAK_LOG,
      STRIPPED_MANGROVE_LOG
  );
  public static final ImmutableList<Block> WOODS = ImmutableList.of(
      OAK_WOOD,
      SPRUCE_WOOD,
      BIRCH_WOOD,
      JUNGLE_WOOD,
      ACACIA_WOOD,
      CHERRY_WOOD,
      DARK_OAK_WOOD,
      MANGROVE_WOOD
  );
  public static final ImmutableList<Block> STRIPPED_WOODS = ImmutableList.of(
      STRIPPED_OAK_WOOD,
      STRIPPED_SPRUCE_WOOD,
      STRIPPED_BIRCH_WOOD,
      STRIPPED_JUNGLE_WOOD,
      STRIPPED_ACACIA_WOOD,
      STRIPPED_CHERRY_WOOD,
      STRIPPED_DARK_OAK_WOOD,
      STRIPPED_MANGROVE_WOOD
  );
  public static final ImmutableList<Block> STEMS = ImmutableList.of(
      WARPED_STEM,
      CRIMSON_STEM
  );
  public static final ImmutableList<Block> STRIPPED_STEMS = ImmutableList.of(
      STRIPPED_WARPED_STEM,
      STRIPPED_CRIMSON_STEM
  );
  public static final ImmutableList<Block> HYPHAES = ImmutableList.of(
      WARPED_HYPHAE,
      CRIMSON_HYPHAE
  );
  public static final ImmutableList<Block> STRIPPED_HYPHAES = ImmutableList.of(
      STRIPPED_WARPED_HYPHAE,
      STRIPPED_CRIMSON_HYPHAE
  );
  public static final @Unmodifiable ImmutableSet<Block> PLANKS = ImmutableSet.of(
      OAK_PLANKS,
      SPRUCE_PLANKS,
      BIRCH_PLANKS,
      JUNGLE_PLANKS,
      ACACIA_PLANKS,
      CHERRY_PLANKS,
      DARK_OAK_PLANKS,
      MANGROVE_PLANKS,
      BAMBOO_PLANKS,
      BAMBOO_MOSAIC,
      CRIMSON_PLANKS,
      WARPED_PLANKS
  );
  public static final @Unmodifiable ImmutableSet<Block> OVERWORLD_PLANKS = ImmutableSet.of(
      OAK_PLANKS,
      SPRUCE_PLANKS,
      BIRCH_PLANKS,
      JUNGLE_PLANKS,
      ACACIA_PLANKS,
      CHERRY_PLANKS,
      DARK_OAK_PLANKS,
      MANGROVE_PLANKS,
      BAMBOO_PLANKS,
      BAMBOO_MOSAIC
  );
  public static final @Unmodifiable ImmutableList<Block> STONE_VARIANTS = ImmutableList.of(
      GRANITE,
      POLISHED_GRANITE,
      DIORITE,
      POLISHED_DIORITE,
      ANDESITE,
      POLISHED_ANDESITE
  );
  public static final @Unmodifiable ImmutableList<Block> STONES = new ImmutableList.Builder<Block>().add(STONE).addAll(STONE_VARIANTS).build();
  public static final @Unmodifiable ImmutableSet<Block> SANDSTONES = ImmutableSet.of(
      SANDSTONE,
      CUT_SANDSTONE,
      CHISELED_SANDSTONE,
      SMOOTH_SANDSTONE,
      RED_SANDSTONE,
      CUT_RED_SANDSTONE,
      CHISELED_RED_SANDSTONE,
      SMOOTH_RED_SANDSTONE
  );
  public static final @Unmodifiable ImmutableSet<Block> WOOLS = ImmutableSet.of(WHITE_WOOL,
      ORANGE_WOOL,
      MAGENTA_WOOL,
      LIGHT_BLUE_WOOL,
      YELLOW_WOOL,
      LIME_WOOL,
      PINK_WOOL,
      GRAY_WOOL,
      LIGHT_GRAY_WOOL,
      CYAN_WOOL,
      PURPLE_WOOL,
      BLUE_WOOL,
      BROWN_WOOL,
      GREEN_WOOL,
      RED_WOOL,
      BLACK_WOOL);
  public static final @Unmodifiable ImmutableSet<Block> GLAZED_TERRACOTTA = ImmutableSet.of(
      WHITE_GLAZED_TERRACOTTA,
      ORANGE_GLAZED_TERRACOTTA,
      MAGENTA_GLAZED_TERRACOTTA,
      LIGHT_BLUE_GLAZED_TERRACOTTA,
      YELLOW_GLAZED_TERRACOTTA,
      LIME_GLAZED_TERRACOTTA,
      PINK_GLAZED_TERRACOTTA,
      GRAY_GLAZED_TERRACOTTA,
      LIGHT_GRAY_GLAZED_TERRACOTTA,
      CYAN_GLAZED_TERRACOTTA,
      PURPLE_GLAZED_TERRACOTTA,
      BLUE_GLAZED_TERRACOTTA,
      BROWN_GLAZED_TERRACOTTA,
      GREEN_GLAZED_TERRACOTTA,
      RED_GLAZED_TERRACOTTA,
      BLACK_GLAZED_TERRACOTTA
  );
  public static final @Unmodifiable ImmutableSet<Block> CONCRETES = ImmutableSet.of(WHITE_CONCRETE,
      ORANGE_CONCRETE,
      MAGENTA_CONCRETE,
      LIGHT_BLUE_CONCRETE,
      YELLOW_CONCRETE,
      LIME_CONCRETE,
      PINK_CONCRETE,
      GRAY_CONCRETE,
      LIGHT_GRAY_CONCRETE,
      CYAN_CONCRETE,
      PURPLE_CONCRETE,
      BLUE_CONCRETE,
      BROWN_CONCRETE,
      GREEN_CONCRETE,
      RED_CONCRETE,
      BLACK_CONCRETE);
  public static final @Unmodifiable ImmutableSet<Block> STAINED_TERRACOTTA = ImmutableSet.of(
      WHITE_TERRACOTTA,
      ORANGE_TERRACOTTA,
      MAGENTA_TERRACOTTA,
      LIGHT_BLUE_TERRACOTTA,
      YELLOW_TERRACOTTA,
      LIME_TERRACOTTA,
      PINK_TERRACOTTA,
      GRAY_TERRACOTTA,
      LIGHT_GRAY_TERRACOTTA,
      CYAN_TERRACOTTA,
      PURPLE_TERRACOTTA,
      BLUE_TERRACOTTA,
      BROWN_TERRACOTTA,
      GREEN_TERRACOTTA,
      RED_TERRACOTTA,
      BLACK_TERRACOTTA
  );

  private BlockCollections() {
  }

  /**
   * 原版 Minecraft 的 Mineable 标签的内容，若基础方块为这些方块，则会加入对应的标签。
   */
  public static final class VanillaMineable {
    /**
     * 不含木板、原木等。部分不可能作为基础方块的予以注释掉。
     *
     * @see net.minecraft.registry.tag.BlockTags#AXE_MINEABLE
     */
    public static final @Unmodifiable ImmutableSet<Block> AXE = ImmutableSet.of(
        BOOKSHELF,
        CRAFTING_TABLE,
        CRIMSON_FUNGUS,
        FLETCHING_TABLE,
        MELON,
        NETHER_WART,
        PUMPKIN,
        RED_MUSHROOM_BLOCK,
        WARPED_FUNGUS,

        // 1.20
        BAMBOO_BLOCK,
        STRIPPED_BAMBOO_BLOCK,
        BAMBOO_PLANKS,
        BAMBOO_MOSAIC
    );
    public static final @Unmodifiable ImmutableSet<Block> HOE = ImmutableSet.of(
        NETHER_WART_BLOCK,
        WARPED_WART_BLOCK,
        HAY_BLOCK,
        DRIED_KELP_BLOCK,
        SHROOMLIGHT,
        SPONGE,
        WET_SPONGE,
        MOSS_BLOCK
    );
    public static final @Unmodifiable ImmutableSet<Block> PICKAXE = ImmutableSet.of(
        STONE,
        GRANITE,
        POLISHED_GRANITE,
        DIORITE,
        POLISHED_DIORITE,
        ANDESITE,
        POLISHED_ANDESITE,
        COBBLESTONE,
        GOLD_ORE,
        DEEPSLATE_GOLD_ORE,
        IRON_ORE,
        DEEPSLATE_IRON_ORE,
        COAL_ORE,
        DEEPSLATE_COAL_ORE,
        NETHER_GOLD_ORE,
        LAPIS_ORE,
        DEEPSLATE_LAPIS_ORE,
        LAPIS_BLOCK,
        SANDSTONE,
        CHISELED_SANDSTONE,
        CUT_SANDSTONE,
        GOLD_BLOCK,
        IRON_BLOCK,
        BRICKS,
        MOSSY_COBBLESTONE,
        OBSIDIAN,
        DIAMOND_ORE,
        DEEPSLATE_DIAMOND_ORE,
        DIAMOND_BLOCK,
        REDSTONE_ORE,
        DEEPSLATE_REDSTONE_ORE,
        NETHERRACK,
        BASALT,
        POLISHED_BASALT,
        STONE_BRICKS,
        MOSSY_STONE_BRICKS,
        CRACKED_STONE_BRICKS,
        CHISELED_STONE_BRICKS,
        NETHER_BRICKS,
        END_STONE,
        EMERALD_ORE,
        DEEPSLATE_EMERALD_ORE,
        EMERALD_BLOCK,
        REDSTONE_BLOCK,
        NETHER_QUARTZ_ORE,
        HOPPER,
        QUARTZ_BLOCK,
        CHISELED_QUARTZ_BLOCK,
        QUARTZ_PILLAR,
        QUARTZ_STAIRS,
        DROPPER,
        IRON_TRAPDOOR,
        PRISMARINE,
        PRISMARINE_BRICKS,
        DARK_PRISMARINE,
        TERRACOTTA,
        COAL_BLOCK,
        RED_SANDSTONE,
        CHISELED_RED_SANDSTONE,
        CUT_RED_SANDSTONE,
        SMOOTH_STONE,
        SMOOTH_SANDSTONE,
        SMOOTH_QUARTZ,
        SMOOTH_RED_SANDSTONE,
        PURPUR_BLOCK,
        PURPUR_PILLAR,
        END_STONE_BRICKS,
        MAGMA_BLOCK,
        RED_NETHER_BRICKS,
        BONE_BLOCK,
        GRINDSTONE,
        WARPED_NYLIUM,
        CRIMSON_NYLIUM,
        NETHERITE_BLOCK,
        ANCIENT_DEBRIS,
        CRYING_OBSIDIAN,
        LODESTONE,
        BLACKSTONE,
        POLISHED_BLACKSTONE,
        CHISELED_POLISHED_BLACKSTONE,
        GILDED_BLACKSTONE,
        CHISELED_NETHER_BRICKS,
        CRACKED_NETHER_BRICKS,
        QUARTZ_BRICKS,
        TUFF,
        CALCITE,
        OXIDIZED_COPPER,
        WEATHERED_COPPER,
        EXPOSED_COPPER,
        COPPER_BLOCK,
        COPPER_ORE,
        DEEPSLATE_COPPER_ORE,
        OXIDIZED_CUT_COPPER,
        WEATHERED_CUT_COPPER,
        EXPOSED_CUT_COPPER,
        CUT_COPPER,
        WAXED_COPPER_BLOCK,
        WAXED_WEATHERED_COPPER,
        WAXED_EXPOSED_COPPER,
        WAXED_OXIDIZED_COPPER,
        WAXED_OXIDIZED_CUT_COPPER,
        WAXED_WEATHERED_CUT_COPPER,
        WAXED_EXPOSED_CUT_COPPER,
        WAXED_CUT_COPPER,
        LIGHTNING_ROD,
        POINTED_DRIPSTONE,
        DRIPSTONE_BLOCK,
        DEEPSLATE,
        COBBLED_DEEPSLATE,
        POLISHED_DEEPSLATE,
        DEEPSLATE_TILES,
        DEEPSLATE_BRICKS,
        CHISELED_DEEPSLATE,
        CRACKED_DEEPSLATE_BRICKS,
        CRACKED_DEEPSLATE_TILES,
        SMOOTH_BASALT,
        RAW_IRON_BLOCK,
        RAW_COPPER_BLOCK,
        RAW_GOLD_BLOCK,
        ICE,
        PACKED_ICE,
        BLUE_ICE,
        AMETHYST_BLOCK,
        BUDDING_AMETHYST,
        MUD_BRICKS,
        PACKED_MUD
    );
    public static final @Unmodifiable ImmutableSet<Block> SHOVEL = ImmutableSet.of(CLAY,
        DIRT,
        COARSE_DIRT,
        PODZOL,
        FARMLAND,
        GRASS_BLOCK,
        GRAVEL,
        MYCELIUM,
        SAND,
        RED_SAND,
        SNOW_BLOCK,
        SNOW,
        SOUL_SAND,
        DIRT_PATH,
        SOUL_SOIL,
        ROOTED_DIRT
    );

    /**
     * @see net.minecraft.registry.tag.BlockTags#NEEDS_DIAMOND_TOOL
     */
    public static final @Unmodifiable ImmutableSet<Block> NEEDS_DIAMOND_TOOL = ImmutableSet.of(
        OBSIDIAN, CRYING_OBSIDIAN, NETHERITE_BLOCK, RESPAWN_ANCHOR, ANCIENT_DEBRIS
    );
    /**
     * @see net.minecraft.registry.tag.BlockTags#NEEDS_IRON_TOOL
     */
    public static final @Unmodifiable ImmutableSet<Block> NEEDS_IRON_TOOL = ImmutableSet.of(
        DIAMOND_BLOCK,
        DIAMOND_ORE,
        DEEPSLATE_DIAMOND_ORE,
        EMERALD_ORE,
        DEEPSLATE_EMERALD_ORE,
        EMERALD_BLOCK,
        GOLD_BLOCK,
        RAW_GOLD_BLOCK,
        GOLD_ORE,
        DEEPSLATE_GOLD_ORE,
        REDSTONE_ORE,
        DEEPSLATE_REDSTONE_ORE
    );
    /**
     * @see net.minecraft.registry.tag.BlockTags#NEEDS_STONE_TOOL
     */
    public static final @Unmodifiable ImmutableSet<Block> NEEDS_STONE_TOOL = ImmutableSet.of(
        IRON_BLOCK,
        RAW_IRON_BLOCK,
        IRON_ORE,
        DEEPSLATE_IRON_ORE,
        LAPIS_BLOCK,
        LAPIS_ORE,
        DEEPSLATE_LAPIS_ORE,
        COPPER_BLOCK,
        RAW_COPPER_BLOCK,
        COPPER_ORE,
        DEEPSLATE_COPPER_ORE,
        CUT_COPPER,
        WEATHERED_COPPER,
        WEATHERED_CUT_COPPER,
        OXIDIZED_COPPER,
        OXIDIZED_CUT_COPPER,
        EXPOSED_COPPER,
        EXPOSED_CUT_COPPER,
        WAXED_COPPER_BLOCK,
        WAXED_CUT_COPPER,
        WAXED_WEATHERED_COPPER,
        WAXED_WEATHERED_CUT_COPPER,
        WAXED_EXPOSED_COPPER,
        WAXED_EXPOSED_CUT_COPPER,
        WAXED_OXIDIZED_COPPER,
        WAXED_OXIDIZED_CUT_COPPER
    );

    private VanillaMineable() {
    }
  }
}
