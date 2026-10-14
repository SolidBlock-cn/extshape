package pers.solid.extshape.itemgroup;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
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
  private static final ArrayList<List<Block>> COLORED_BASE_BLOCKS = new ArrayList<>();
  private static final ArrayList<Block> STONE_BASE_BLOCKS = new ArrayList<>();
  private static final ArrayList<Block> MINERAL_BASE_BLOCKS = new ArrayList<>();
  private static final LinkedHashSet<Block> OTHER_BASE_BLOCKS = new LinkedHashSet<>();

  public static final CreativeModeTab WOODEN_AND_BAMBOO_BLOCKS = register("extshape_wooden_and_bamboo_blocks", FabricItemGroup.builder()
      .title(wrapItemGroupName(Component.translatable("itemGroup.extshape.wooden_blocks")))
      .icon(() -> new ItemStack(BlockBiMaps.getBlockOfOrThrow(BlockShape.WALL, Blocks.CHERRY_PLANKS)))
      .displayItems((displayContext, entries) -> addWoodenBaseAndVariantsToEntries(WOODEN_AND_BAMBOO_BASE_BLOCKS, entries))
      .build());

  public static final CreativeModeTab COLORED_BLOCKS = register("extshape_colored_blocks", FabricItemGroup.builder()
      .title(wrapItemGroupName(Component.translatable("itemGroup.extshape.colored_blocks")))
      .icon(() -> new ItemStack(BlockBiMaps.getBlockOfOrThrow(BlockShape.WALL, Blocks.LIME_WOOL)))
      .displayItems((displayContext, entries) -> COLORED_BASE_BLOCKS.forEach(blocks -> addColoredBaseAndVariantsToEntries(blocks, entries)))
      .build());

  public static final CreativeModeTab STONE_BLOCKS = register("extshape_stone_blocks", FabricItemGroup.builder()
      .title(wrapItemGroupName(Component.translatable("itemGroup.extshape.stone_blocks")))
      .icon(() -> new ItemStack(BlockBiMaps.getBlockOfOrThrow(BlockShape.FENCE, Blocks.CALCITE)))
      .displayItems((displayContext, entries) -> STONE_BASE_BLOCKS.forEach(block -> addBaseAndVariantsToEntries(block, entries)))
      .build());

  public static final CreativeModeTab MINERAL_BLOCKS = register("extshape_mineral_blocks", FabricItemGroup.builder()
      .title(wrapItemGroupName(Component.translatable("itemGroup.extshape.mineral_blocks")))
      .icon(() -> new ItemStack(BlockBiMaps.getBlockOfOrThrow(BlockShape.VERTICAL_SLAB, Blocks.DIAMOND_BLOCK)))
      .displayItems((displayContext, entries) -> MINERAL_BASE_BLOCKS.forEach(block -> addBaseAndVariantsToEntries(block, entries)))
      .build());

  public static final CreativeModeTab OTHER_BLOCKS = register("extshape_other_blocks", FabricItemGroup.builder()
      .title(wrapItemGroupName(Component.translatable("itemGroup.extshape.other_blocks")))
      .icon(() -> new ItemStack(BlockBiMaps.getBlockOfOrThrow(BlockShape.SLAB, Blocks.HONEYCOMB_BLOCK)))
      .displayItems((displayContext, entries) -> OTHER_BASE_BLOCKS.forEach(block -> addBaseAndVariantsToEntries(block, entries)))
      .build());

  public static final ImmutableSet<CreativeModeTab> MOD_GROUPS = ImmutableSet.of(WOODEN_AND_BAMBOO_BLOCKS, COLORED_BLOCKS, STONE_BLOCKS, MINERAL_BLOCKS, OTHER_BLOCKS);

  private ExtShapeSpecificItemGroups() {
  }


  private static void prepareBlockList() {
    WOODEN_AND_BAMBOO_BASE_BLOCKS.clear();
    COLORED_BASE_BLOCKS.clear();
    STONE_BASE_BLOCKS.clear();
    OTHER_BASE_BLOCKS.clear();

    final Block air = Blocks.AIR;
    final List<Block> woodsAndHyphaes = ImmutableList.copyOf(Iterables.concat(BlockCollections.WOODS, Arrays.asList(air, air), BlockCollections.HYPHAES));
    final List<Block> strippedWoodsAndHyphaes = ImmutableList.copyOf(Iterables.concat(BlockCollections.STRIPPED_WOODS, Arrays.asList(air, air), BlockCollections.STRIPPED_HYPHAES));
    final List<Block> planks = ImmutableList.copyOf(BlockCollections.PLANKS);

    if (IntStream.of(woodsAndHyphaes.size(), strippedWoodsAndHyphaes.size(), planks.size()).distinct().count() > 1) {
      throw new IllegalStateException("Sizes mismatch! Sizes are: woods_and_hyphaes = " + woodsAndHyphaes.size() + ", stripped_woods_and_hyphaes = " + strippedWoodsAndHyphaes.size() + ", planks = " + planks.size());
    }

    WOODEN_AND_BAMBOO_BASE_BLOCKS.add(woodsAndHyphaes);
    WOODEN_AND_BAMBOO_BASE_BLOCKS.add(strippedWoodsAndHyphaes);
    WOODEN_AND_BAMBOO_BASE_BLOCKS.add(planks);

    // 彩色方块不使用 BlockCollections 中的颜色，这是为了确保顺序。
    var coloredBaseBlocks = COLORED_BASE_BLOCKS;
    final List<Block> wools = List.of(Blocks.WHITE_WOOL, Blocks.LIGHT_GRAY_WOOL, Blocks.GRAY_WOOL, Blocks.BLACK_WOOL, Blocks.BROWN_WOOL, Blocks.RED_WOOL, Blocks.ORANGE_WOOL, Blocks.YELLOW_WOOL, Blocks.LIME_WOOL, Blocks.GREEN_WOOL, Blocks.CYAN_WOOL, Blocks.LIGHT_BLUE_WOOL, Blocks.BLUE_WOOL, Blocks.PURPLE_WOOL, Blocks.MAGENTA_WOOL, Blocks.PINK_WOOL);

    coloredBaseBlocks.add(wools);
    coloredBaseBlocks.add(List.of(Blocks.TERRACOTTA));

    final List<Block> stainedTerracottas = List.of(Blocks.WHITE_TERRACOTTA, Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.GRAY_TERRACOTTA, Blocks.BLACK_TERRACOTTA, Blocks.BROWN_TERRACOTTA, Blocks.RED_TERRACOTTA, Blocks.ORANGE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA, Blocks.LIME_TERRACOTTA, Blocks.GREEN_TERRACOTTA, Blocks.CYAN_TERRACOTTA, Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.BLUE_TERRACOTTA, Blocks.PURPLE_TERRACOTTA, Blocks.MAGENTA_TERRACOTTA, Blocks.PINK_TERRACOTTA);
    coloredBaseBlocks.add(stainedTerracottas);

    final List<Block> concretes = List.of(Blocks.WHITE_CONCRETE, Blocks.LIGHT_GRAY_CONCRETE, Blocks.GRAY_CONCRETE, Blocks.BLACK_CONCRETE, Blocks.BROWN_CONCRETE, Blocks.RED_CONCRETE, Blocks.ORANGE_CONCRETE, Blocks.YELLOW_CONCRETE, Blocks.LIME_CONCRETE, Blocks.GREEN_CONCRETE, Blocks.CYAN_CONCRETE, Blocks.LIGHT_BLUE_CONCRETE, Blocks.BLUE_CONCRETE, Blocks.PURPLE_CONCRETE, Blocks.MAGENTA_CONCRETE, Blocks.PINK_CONCRETE);
    coloredBaseBlocks.add(concretes);

    final List<Block> glazedTerracottas = List.of(Blocks.WHITE_GLAZED_TERRACOTTA, Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA, Blocks.GRAY_GLAZED_TERRACOTTA, Blocks.BLACK_GLAZED_TERRACOTTA, Blocks.BROWN_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA, Blocks.ORANGE_GLAZED_TERRACOTTA, Blocks.YELLOW_GLAZED_TERRACOTTA, Blocks.LIME_GLAZED_TERRACOTTA, Blocks.GREEN_GLAZED_TERRACOTTA, Blocks.CYAN_GLAZED_TERRACOTTA, Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, Blocks.BLUE_GLAZED_TERRACOTTA, Blocks.PURPLE_GLAZED_TERRACOTTA, Blocks.MAGENTA_GLAZED_TERRACOTTA, Blocks.PINK_GLAZED_TERRACOTTA);
    coloredBaseBlocks.add(glazedTerracottas);

    STONE_BASE_BLOCKS.addAll(BlockCollections.STONES);
    STONE_BASE_BLOCKS.addAll(Arrays.asList(
        Blocks.SMOOTH_STONE,
        ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB,
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
    COLORED_BASE_BLOCKS.forEach(baseBlocks::removeAll);
    STONE_BASE_BLOCKS.forEach(baseBlocks::remove);
    MINERAL_BASE_BLOCKS.forEach(baseBlocks::remove);

    for (Block block : baseBlocks) {
      if (block == ExtShapeBlocks.PETRIFIED_OAK_PLANKS) {
        continue;
      }

      final SoundType soundGroup = block.defaultBlockState().getSoundType();
      if (soundGroup == SoundType.STONE) {
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
  public static void addBaseAndVariantsToEntries(Block baseBlock, CreativeModeTab.Output entries) {
    if (baseBlock == null) return;
    entries.accept(baseBlock);
    for (BlockShape shape : ExtShapeConfig.CURRENT_CONFIG.shapesInSpecificGroups) {
      final @Nullable Block shapeBlock = BlockBiMaps.getBlockOf(shape, baseBlock);
      if (shapeBlock != null) {
        entries.accept(shapeBlock);
      }
    }
  }

  public static void addWoodenBaseAndVariantsToEntries(List<List<Block>> baseBlockLists, CreativeModeTab.Output entries) {
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

  public static void addColoredBaseAndVariantsToEntries(List<Block> baseBlocks, CreativeModeTab.Output entries) {
    switch (ExtShapeConfig.CURRENT_CONFIG.coloredBlockSorting) {
      case SAME_COLOR_DIFFERENT_SHAPES_TOGETHER -> {
        for (Block baseBlock : baseBlocks) {
          addBaseAndVariantsToEntries(baseBlock, entries);
        }
      }
      case SAME_SHAPE_DIFFERENT_COLORS_TOGETHER -> {
        for (Block baseBlock : baseBlocks) {
          entries.accept(baseBlock);
        }
        for (BlockShape shape : ExtShapeConfig.CURRENT_CONFIG.shapesInSpecificGroups) {
          for (Block baseBlock : baseBlocks) {
            final @Nullable Block shapeBlock = BlockBiMaps.getBlockOf(shape, baseBlock);
            if (shapeBlock != null) {
              entries.accept(shapeBlock);
            }
          }
        }
      }
    }
  }

  private static <T extends CreativeModeTab> T register(String name, T group) {
    return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ExtShape.id(name), group);
  }

  private static Component wrapItemGroupName(Component text) {
    return Component.empty()
        .append(Component.translatable("itemGroup.extshape.prefix", Component.translatable("modmenu.nameTranslation.extshape")).withStyle(style -> style.withColor(0x1676C4)))
        .append(text);
  }

  public static void init() {
    prepareBlockList();

    Objects.requireNonNull(MOD_GROUPS);
  }
}