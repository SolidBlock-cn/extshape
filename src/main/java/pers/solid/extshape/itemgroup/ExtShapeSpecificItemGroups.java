package pers.solid.extshape.itemgroup;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.util.BlockBiMaps;
import pers.solid.extshape.util.BlockCollections;

import java.util.*;
import java.util.stream.IntStream;

/**
 * <p>本模组中的专用物品组。只有当 {@link ExtShapeConfig#showSpecificGroups} 为 true 时，此类才会被初始化，否则该类一直不会被初始化。
 */
public final class ExtShapeSpecificItemGroups {
  private static final ArrayList<List<Block>> WOODEN_AND_BAMBOO_BASE_BLOCKS = new ArrayList<>();
  private static final ArrayList<List<Block>> COLORFUL_BASE_BLOCKS = new ArrayList<>();
  private static final ArrayList<Block> STONE_BASE_BLOCKS = new ArrayList<>();
  private static final ArrayList<Block> MINERAL_BASE_BLOCKS = new ArrayList<>();
  private static final LinkedHashSet<Block> OTHER_BASE_BLOCKS = new LinkedHashSet<>();

  public static final ItemGroup WOODEN_AND_BAMBOO_BLOCKS = register("wooden_and_bamboo_blocks", FabricItemGroup.builder()
      .displayName(Text.translatable("itemGroup.extshape.wooden_blocks"))
      .icon(() -> new ItemStack(BlockBiMaps.getBlockOfOrThrow(BlockShape.WALL, Blocks.CHERRY_PLANKS)))
      .entries((displayContext, entries) -> addWoodenBaseAndVariantsToEntries(WOODEN_AND_BAMBOO_BASE_BLOCKS, entries))
      .build());

  public static final ItemGroup COLORFUL_BLOCKS = register("color_blocks", FabricItemGroup.builder()
      .displayName(Text.translatable("itemGroup.extshape.colorful_blocks"))
      .icon(() -> new ItemStack(BlockBiMaps.getBlockOfOrThrow(BlockShape.WALL, Blocks.LIME_WOOL)))
      .entries((displayContext, entries) -> COLORFUL_BASE_BLOCKS.forEach(blocks -> addColorfulBaseAndVariantsToEntries(blocks, entries)))
      .build());

  public static final ItemGroup STONE_BLOCKS = register("stone_blocks", FabricItemGroup.builder()
      .displayName(Text.translatable("itemGroup.extshape.stone_blocks"))
      .icon(() -> new ItemStack(BlockBiMaps.getBlockOfOrThrow(BlockShape.FENCE, Blocks.CALCITE)))
      .entries((displayContext, entries) -> STONE_BASE_BLOCKS.forEach(block -> addBaseAndVariantsToEntries(block, entries)))
      .build());

  public static final ItemGroup MINERAL_BLOCKS = register("mineral_blocks", FabricItemGroup.builder()
      .displayName(Text.translatable("itemGroup.extshape.mineral_blocks"))
      .icon(() -> new ItemStack(BlockBiMaps.getBlockOfOrThrow(BlockShape.VERTICAL_SLAB, Blocks.DIAMOND_BLOCK)))
      .entries((displayContext, entries) -> MINERAL_BASE_BLOCKS.forEach(block -> addBaseAndVariantsToEntries(block, entries)))
      .build());

  public static final ItemGroup OTHER_BLOCKS = register("other_blocks", FabricItemGroup.builder()
      .displayName(Text.translatable("itemGroup.extshape.other_blocks"))
      .icon(() -> new ItemStack(BlockBiMaps.getBlockOfOrThrow(BlockShape.SLAB, Blocks.HONEYCOMB_BLOCK)))
      .entries((displayContext, entries) -> OTHER_BASE_BLOCKS.forEach(block -> addBaseAndVariantsToEntries(block, entries)))
      .build());

  public static final ImmutableSet<ItemGroup> MOD_GROUPS = ImmutableSet.of(WOODEN_AND_BAMBOO_BLOCKS, COLORFUL_BLOCKS, STONE_BLOCKS, MINERAL_BLOCKS, OTHER_BLOCKS);

  private ExtShapeSpecificItemGroups() {
  }


  private static void prepareBlockList() {
    WOODEN_AND_BAMBOO_BASE_BLOCKS.clear();
    COLORFUL_BASE_BLOCKS.clear();
    STONE_BASE_BLOCKS.clear();
    OTHER_BASE_BLOCKS.clear();

    final Block air = Blocks.AIR;
    final List<Block> logsAndStems = ImmutableList.copyOf(Iterables.concat(BlockCollections.LOGS, Arrays.asList(Blocks.BAMBOO_BLOCK, air), BlockCollections.STEMS));
    final List<Block> woodsAndHyphaes = ImmutableList.copyOf(Iterables.concat(BlockCollections.WOODS, Arrays.asList(air, air), BlockCollections.HYPHAES));
    final List<Block> strippedLogsAndStems = ImmutableList.copyOf(Iterables.concat(BlockCollections.STRIPPED_LOGS, Arrays.asList(Blocks.STRIPPED_BAMBOO_BLOCK, air), BlockCollections.STRIPPED_STEMS));
    final List<Block> strippedWoodsAndHyphaes = ImmutableList.copyOf(Iterables.concat(BlockCollections.STRIPPED_WOODS, Arrays.asList(air, air), BlockCollections.STRIPPED_HYPHAES));
    final List<Block> planks = ImmutableList.copyOf(BlockCollections.PLANKS);

    if (IntStream.of(logsAndStems.size(), woodsAndHyphaes.size(), strippedLogsAndStems.size(), strippedLogsAndStems.size(), planks.size()).distinct().count() > 1) {
      throw new IllegalStateException("Sizes mismatch! Sizes are: logs_and_stems = " + logsAndStems.size() + ", woods_and_hyphaes = " + woodsAndHyphaes.size() + ", stripped_logs_and_steps = " + strippedLogsAndStems.size() + ", stripped_woods_and_hyphaes = " + strippedWoodsAndHyphaes.size() + ", planks = " + planks.size());
    }

    WOODEN_AND_BAMBOO_BASE_BLOCKS.add(logsAndStems);
    WOODEN_AND_BAMBOO_BASE_BLOCKS.add(woodsAndHyphaes);
    WOODEN_AND_BAMBOO_BASE_BLOCKS.add(strippedLogsAndStems);
    WOODEN_AND_BAMBOO_BASE_BLOCKS.add(strippedWoodsAndHyphaes);
    WOODEN_AND_BAMBOO_BASE_BLOCKS.add(planks);

    // 彩色方块不使用 BlockCollections 中的颜色，这是为了确保顺序。
    var colorfulBaseBlocks = COLORFUL_BASE_BLOCKS;
    final List<Block> wools = List.of(Blocks.WHITE_WOOL, Blocks.LIGHT_GRAY_WOOL, Blocks.GRAY_WOOL, Blocks.BLACK_WOOL, Blocks.BROWN_WOOL, Blocks.RED_WOOL, Blocks.ORANGE_WOOL, Blocks.YELLOW_WOOL, Blocks.LIME_WOOL, Blocks.GREEN_WOOL, Blocks.CYAN_WOOL, Blocks.LIGHT_BLUE_WOOL, Blocks.BLUE_WOOL, Blocks.PURPLE_WOOL, Blocks.MAGENTA_WOOL, Blocks.PINK_WOOL);

    colorfulBaseBlocks.add(wools);
    colorfulBaseBlocks.add(List.of(Blocks.TERRACOTTA));

    final List<Block> stainedTerracottas = List.of(Blocks.WHITE_TERRACOTTA, Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.GRAY_TERRACOTTA, Blocks.BLACK_TERRACOTTA, Blocks.BROWN_TERRACOTTA, Blocks.RED_TERRACOTTA, Blocks.ORANGE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA, Blocks.LIME_TERRACOTTA, Blocks.GREEN_TERRACOTTA, Blocks.CYAN_TERRACOTTA, Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.BLUE_TERRACOTTA, Blocks.PURPLE_TERRACOTTA, Blocks.MAGENTA_TERRACOTTA, Blocks.PINK_TERRACOTTA);
    colorfulBaseBlocks.add(stainedTerracottas);

    final List<Block> concretes = List.of(Blocks.WHITE_CONCRETE, Blocks.LIGHT_GRAY_CONCRETE, Blocks.GRAY_CONCRETE, Blocks.BLACK_CONCRETE, Blocks.BROWN_CONCRETE, Blocks.RED_CONCRETE, Blocks.ORANGE_CONCRETE, Blocks.YELLOW_CONCRETE, Blocks.LIME_CONCRETE, Blocks.GREEN_CONCRETE, Blocks.CYAN_CONCRETE, Blocks.LIGHT_BLUE_CONCRETE, Blocks.BLUE_CONCRETE, Blocks.PURPLE_CONCRETE, Blocks.MAGENTA_CONCRETE, Blocks.PINK_CONCRETE);
    colorfulBaseBlocks.add(concretes);

    final List<Block> glazedTerracottas = List.of(Blocks.WHITE_GLAZED_TERRACOTTA, Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA, Blocks.GRAY_GLAZED_TERRACOTTA, Blocks.BLACK_GLAZED_TERRACOTTA, Blocks.BROWN_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA, Blocks.ORANGE_GLAZED_TERRACOTTA, Blocks.YELLOW_GLAZED_TERRACOTTA, Blocks.LIME_GLAZED_TERRACOTTA, Blocks.GREEN_GLAZED_TERRACOTTA, Blocks.CYAN_GLAZED_TERRACOTTA, Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, Blocks.BLUE_GLAZED_TERRACOTTA, Blocks.PURPLE_GLAZED_TERRACOTTA, Blocks.MAGENTA_GLAZED_TERRACOTTA, Blocks.PINK_GLAZED_TERRACOTTA);
    colorfulBaseBlocks.add(glazedTerracottas);

    STONE_BASE_BLOCKS.addAll(BlockCollections.STONES);
    STONE_BASE_BLOCKS.addAll(Arrays.asList(
        Blocks.SMOOTH_STONE,
        Blocks.STONE_BRICKS,
        Blocks.MOSSY_STONE_BRICKS,
        Blocks.CHISELED_STONE_BRICKS,
        Blocks.DEEPSLATE,
        Blocks.COBBLED_DEEPSLATE,
        Blocks.POLISHED_DEEPSLATE,
        Blocks.DEEPSLATE_BRICKS,
        Blocks.DEEPSLATE_TILES,
        Blocks.CHISELED_DEEPSLATE,
        Blocks.BEDROCK,
        Blocks.TUFF,
        Blocks.CALCITE,
        Blocks.COBBLESTONE,
        Blocks.MOSSY_COBBLESTONE,
        Blocks.SANDSTONE,
        Blocks.RED_SANDSTONE,
        Blocks.CUT_SANDSTONE,
        Blocks.CUT_RED_SANDSTONE,
        Blocks.CHISELED_SANDSTONE,
        Blocks.CHISELED_RED_SANDSTONE,
        Blocks.SMOOTH_SANDSTONE,
        Blocks.SMOOTH_RED_SANDSTONE,
        Blocks.NETHERRACK,
        Blocks.NETHER_BRICKS,
        Blocks.BASALT,
        Blocks.SMOOTH_BASALT,
        Blocks.POLISHED_BASALT,
        Blocks.RED_NETHER_BRICKS,
        Blocks.BLACKSTONE,
        Blocks.POLISHED_BLACKSTONE,
        Blocks.POLISHED_BLACKSTONE_BRICKS,
        Blocks.OBSIDIAN,
        Blocks.CRYING_OBSIDIAN,
        Blocks.END_STONE,
        Blocks.END_STONE_BRICKS));

    MINERAL_BASE_BLOCKS.addAll(Arrays.asList(
        Blocks.RAW_IRON_BLOCK,
        Blocks.RAW_GOLD_BLOCK,
        Blocks.RAW_COPPER_BLOCK,
        Blocks.IRON_BLOCK,
        Blocks.GOLD_BLOCK,
        Blocks.DIAMOND_BLOCK,
        Blocks.NETHERITE_BLOCK,
        Blocks.EMERALD_BLOCK
    ));

    MINERAL_BASE_BLOCKS.addAll(Arrays.asList(
        // 未氧化的
        Blocks.COPPER_BLOCK,
        Blocks.WAXED_COPPER_BLOCK,
        Blocks.CUT_COPPER,
        Blocks.WAXED_CUT_COPPER,

        // 斑驳的
        Blocks.EXPOSED_COPPER,
        Blocks.WAXED_EXPOSED_COPPER,
        Blocks.EXPOSED_CUT_COPPER,
        Blocks.WAXED_EXPOSED_CUT_COPPER,

        // 锈蚀的
        Blocks.WEATHERED_COPPER,
        Blocks.WAXED_WEATHERED_COPPER,
        Blocks.WEATHERED_CUT_COPPER,
        Blocks.WAXED_WEATHERED_CUT_COPPER,

        // 氧化的
        Blocks.OXIDIZED_COPPER,
        Blocks.WAXED_OXIDIZED_COPPER,
        Blocks.OXIDIZED_CUT_COPPER,
        Blocks.WAXED_OXIDIZED_CUT_COPPER
    ));

    OTHER_BASE_BLOCKS.addAll(Arrays.asList(
        // 石英部分
        Blocks.QUARTZ_BLOCK,
        Blocks.CHISELED_QUARTZ_BLOCK,
        Blocks.QUARTZ_BRICKS,
        Blocks.SMOOTH_QUARTZ,

        // 海晶
        Blocks.PRISMARINE,
        Blocks.PRISMARINE_BRICKS,
        Blocks.DARK_PRISMARINE,
        Blocks.SEA_LANTERN
    ));

    ObjectSet<Block> baseBlocks = new ObjectLinkedOpenHashSet<>(ExtShapeBlocks.getBaseBlocks());
    WOODEN_AND_BAMBOO_BASE_BLOCKS.forEach(baseBlocks::removeAll);
    COLORFUL_BASE_BLOCKS.forEach(baseBlocks::removeAll);
    STONE_BASE_BLOCKS.forEach(baseBlocks::remove);
    MINERAL_BASE_BLOCKS.forEach(baseBlocks::remove);

    for (Block block : baseBlocks) {
      final BlockSoundGroup soundGroup = block.getDefaultState().getSoundGroup();
      if (soundGroup == BlockSoundGroup.STONE) {
        STONE_BASE_BLOCKS.add(block);
      } else {
        OTHER_BASE_BLOCKS.add(block);
      }
    }
  }

  /**
   * 将方块及其变种都添加到物品组的项中。
   *
   * @param baseBlock 基础方块。
   */
  private static void addBaseAndVariantsToEntries(Block baseBlock, ItemGroup.Entries entries) {
    if (baseBlock == null) return;
    entries.add(baseBlock);
    for (BlockShape shape : ExtShapeConfig.CURRENT_CONFIG.shapesInSpecificGroups) {
      final @Nullable Block shapeBlock = BlockBiMaps.getBlockOf(shape, baseBlock);
      if (shapeBlock != null) {
        entries.add(shapeBlock);
      }
    }
  }

  private static void addWoodenBaseAndVariantsToEntries(List<List<Block>> baseBlockLists, ItemGroup.Entries entries) {
    switch (ExtShapeConfig.CURRENT_CONFIG.woodenAndBambooBlockSorting) {
      case SAME_SPECIES_DIFFERENT_FORMS_TOGETHER -> {
        if (baseBlockLists.isEmpty()) {
          throw new IllegalArgumentException("baseBlockLists must not be empty");
        }
        final int refSize = baseBlockLists.iterator().next().size();

        for (List<Block> baseBlockList : baseBlockLists) {
          if (baseBlockList.size() != refSize) {
            throw new IllegalArgumentException("baseBlockLists must have same size, but the list " + baseBlockList + " has the size of " + baseBlockList.size() + " instead of " + refSize);
          }
        }

        for (int i = 0; i < refSize; i++) {
          for (List<Block> baseBlockList : baseBlockLists) {
            final Block block = baseBlockList.get(i);
            if (block != Blocks.AIR) {
              addBaseAndVariantsToEntries(block, entries);
            }
          }
        }
      }
      case SAME_FORM_DIFFERENT_SPECIES_TOGETHER -> {
        for (List<Block> baseBlockList : baseBlockLists) {
          for (Block block : baseBlockList) {
            if (block != Blocks.AIR) {
              addBaseAndVariantsToEntries(block, entries);
            }
          }
        }
      }
    }
  }

  private static void addColorfulBaseAndVariantsToEntries(List<Block> baseBlocks, ItemGroup.Entries entries) {
    switch (ExtShapeConfig.CURRENT_CONFIG.colorfulBlockSorting) {
      case SAME_COLOR_DIFFERENT_SHAPES_TOGETHER -> {
        for (Block baseBlock : baseBlocks) {
          addBaseAndVariantsToEntries(baseBlock, entries);
        }
      }
      case SAME_SHAPE_DIFFERENT_COLORS_TOGETHER -> {
        for (Block baseBlock : baseBlocks) {
          entries.add(baseBlock);
        }
        for (BlockShape shape : ExtShapeConfig.CURRENT_CONFIG.shapesInSpecificGroups) {
          for (Block baseBlock : baseBlocks) {
            final @Nullable Block shapeBlock = BlockBiMaps.getBlockOf(shape, baseBlock);
            if (shapeBlock != null) {
              entries.add(shapeBlock);
            }
          }
        }
      }
    }
  }

  private static <T extends ItemGroup> T register(String name, T group) {
    return Registry.register(Registries.ITEM_GROUP, ExtShape.id(name), group);
  }

  public static void init() {
    prepareBlockList();

    Objects.requireNonNull(MOD_GROUPS);
  }
}