package pers.solid.extshape.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Unmodifiable;

import static net.minecraft.block.Blocks.*;

/**
 * 此类收到了一些常用的方块集合。
 */
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
  public static final @Unmodifiable ImmutableList<Block> STONE_VARIANTS = ImmutableList.of(
      GRANITE,
      POLISHED_GRANITE,
      DIORITE,
      POLISHED_DIORITE,
      ANDESITE,
      POLISHED_ANDESITE
  );
  public static final @Unmodifiable ImmutableList<Block> STONES = new ImmutableList.Builder<Block>().add(STONE).addAll(STONE_VARIANTS).build();
  public static final @Unmodifiable ImmutableSet<Block> UNCOLORED_SANDSTONES = ImmutableSet.of(
      SANDSTONE,
      CUT_SANDSTONE,
      CHISELED_SANDSTONE,
      SMOOTH_SANDSTONE
  );
  public static final @Unmodifiable ImmutableSet<Block> RED_SANDSTONES = ImmutableSet.of(
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
  public static final @Unmodifiable ImmutableList<TagKey<Block>> DYED_TAGS = ImmutableList.of(
      ConventionalBlockTags.WHITE_DYED,
      ConventionalBlockTags.ORANGE_DYED,
      ConventionalBlockTags.MAGENTA_DYED,
      ConventionalBlockTags.LIGHT_BLUE_DYED,
      ConventionalBlockTags.YELLOW_DYED,
      ConventionalBlockTags.LIME_DYED,
      ConventionalBlockTags.PINK_DYED,
      ConventionalBlockTags.GRAY_DYED,
      ConventionalBlockTags.LIGHT_GRAY_DYED,
      ConventionalBlockTags.CYAN_DYED,
      ConventionalBlockTags.PURPLE_DYED,
      ConventionalBlockTags.BLUE_DYED,
      ConventionalBlockTags.BROWN_DYED,
      ConventionalBlockTags.GREEN_DYED,
      ConventionalBlockTags.RED_DYED,
      ConventionalBlockTags.BLACK_DYED
  );
  public static final @Unmodifiable ImmutableList<TagKey<Item>> DYED_ITEM_TAGS = ImmutableList.of(
      ConventionalItemTags.WHITE_DYED,
      ConventionalItemTags.ORANGE_DYED,
      ConventionalItemTags.MAGENTA_DYED,
      ConventionalItemTags.LIGHT_BLUE_DYED,
      ConventionalItemTags.YELLOW_DYED,
      ConventionalItemTags.LIME_DYED,
      ConventionalItemTags.PINK_DYED,
      ConventionalItemTags.GRAY_DYED,
      ConventionalItemTags.LIGHT_GRAY_DYED,
      ConventionalItemTags.CYAN_DYED,
      ConventionalItemTags.PURPLE_DYED,
      ConventionalItemTags.BLUE_DYED,
      ConventionalItemTags.BROWN_DYED,
      ConventionalItemTags.GREEN_DYED,
      ConventionalItemTags.RED_DYED,
      ConventionalItemTags.BLACK_DYED
  );

  private BlockCollections() {
  }
}
