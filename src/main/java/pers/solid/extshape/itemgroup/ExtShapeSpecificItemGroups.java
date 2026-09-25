package pers.solid.extshape.itemgroup;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.GourdBlock;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;

/**
 * <p>本模组中的专用物品组。只有当 {@link ExtShapeConfig#showSpecificGroups} 为 true 时，此类才会被初始化，否则该类一直不会被初始化。
 */
public final class ExtShapeSpecificItemGroups {
  private static final ArrayList<Block> WOODEN_BASE_BLOCKS = new ArrayList<>();
  private static final ArrayList<Block> COLORFUL_BASE_BLOCKS = new ArrayList<>();
  private static final ArrayList<Block> STONE_BASE_BLOCKS = new ArrayList<>();
  private static final ArrayList<Block> MINERAL_BASE_BLOCKS = new ArrayList<>();
  private static final LinkedHashSet<Block> OTHER_BASE_BLOCKS = new LinkedHashSet<>();

  public static final ItemGroup WOODEN_BLOCKS = register("wooden_blocks", FabricItemGroup.builder()
      .displayName(Text.translatable("itemGroup.extshape.wooden_blocks"))
      .icon(() -> new ItemStack(BlockBiMaps.getBlockOfOrThrow(BlockShape.WALL, Blocks.CHERRY_PLANKS)))
      .entries((displayContext, entries) -> WOODEN_BASE_BLOCKS.forEach((block -> addBaseAndVariantsToEntries(block, entries))))
      .build());

  public static final ItemGroup COLORFUL_BLOCKS = register("color_blocks", FabricItemGroup.builder()
      .displayName(Text.translatable("itemGroup.extshape.colorful_blocks"))
      .icon(() -> new ItemStack(BlockBiMaps.getBlockOfOrThrow(BlockShape.STAIRS, Blocks.LIME_WOOL)))
      .entries((displayContext, entries) -> COLORFUL_BASE_BLOCKS.forEach(block -> addBaseAndVariantsToEntries(block, entries)))
      .build());

  public static final ItemGroup STONE_BLOCKS = register("stone_blocks", FabricItemGroup.builder()
      .displayName(Text.translatable("itemGroup.extshape.stone_blocks"))
      .icon(() -> new ItemStack(BlockBiMaps.getBlockOfOrThrow(BlockShape.FENCE, Blocks.CALCITE)))
      .entries((displayContext, entries) -> STONE_BASE_BLOCKS.forEach(block -> addBaseAndVariantsToEntries(block, entries)))
      .build());

  public static final ItemGroup MINERAL_BLOCKS = register("mineral_blocks", FabricItemGroup.builder()
      .displayName(Text.translatable("itemGroup.extshape.mineral_blocks"))
      .icon(() -> new ItemStack(BlockBiMaps.getBlockOfOrThrow(BlockShape.SLAB, Blocks.DIAMOND_BLOCK)))
      .entries((displayContext, entries) -> MINERAL_BASE_BLOCKS.forEach(block -> addBaseAndVariantsToEntries(block, entries)))
      .build());

  public static final ItemGroup OTHER_BLOCKS = register("other_blocks", FabricItemGroup.builder()
      .displayName(Text.translatable("itemGroup.extshape.other_blocks"))
      .icon(() -> new ItemStack(BlockBiMaps.getBlockOfOrThrow(BlockShape.VERTICAL_SLAB, Blocks.WAXED_OXIDIZED_COPPER)))
      .entries((displayContext, entries) -> OTHER_BASE_BLOCKS.forEach(block -> addBaseAndVariantsToEntries(block, entries)))
      .build());

  public static final ImmutableSet<ItemGroup> MOD_GROUPS = ImmutableSet.of(WOODEN_BLOCKS, COLORFUL_BLOCKS, STONE_BLOCKS, MINERAL_BLOCKS, OTHER_BLOCKS);

  private ExtShapeSpecificItemGroups() {
  }


  private static void prepareBlockList() {
    WOODEN_BASE_BLOCKS.clear();
    COLORFUL_BASE_BLOCKS.clear();
    STONE_BASE_BLOCKS.clear();
    OTHER_BASE_BLOCKS.clear();

    COLORFUL_BASE_BLOCKS.addAll(BlockCollections.WOOLS);
    COLORFUL_BASE_BLOCKS.addAll(BlockCollections.CONCRETES);
    COLORFUL_BASE_BLOCKS.add(Blocks.TERRACOTTA);
    COLORFUL_BASE_BLOCKS.addAll(BlockCollections.STAINED_TERRACOTTA);
    COLORFUL_BASE_BLOCKS.addAll(BlockCollections.GLAZED_TERRACOTTA);
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
    WOODEN_BASE_BLOCKS.forEach(baseBlocks::remove);
    COLORFUL_BASE_BLOCKS.forEach(baseBlocks::remove);
    STONE_BASE_BLOCKS.forEach(baseBlocks::remove);
    MINERAL_BASE_BLOCKS.forEach(baseBlocks::remove);

    for (Block block : baseBlocks) {
      final BlockSoundGroup soundGroup = block.getDefaultState().getSoundGroup();
      if (soundGroup == BlockSoundGroup.STONE) {
        STONE_BASE_BLOCKS.add(block);
      } else if (soundGroup == BlockSoundGroup.WOOD || soundGroup == BlockSoundGroup.NETHER_WOOD || soundGroup == BlockSoundGroup.NETHER_STEM || soundGroup == BlockSoundGroup.BAMBOO_WOOD || soundGroup == BlockSoundGroup.CHERRY_WOOD) {
        if (!(block instanceof GourdBlock)) {
          // 南瓜和西瓜属于木质音效，但不加入木制方块
          WOODEN_BASE_BLOCKS.add(block);
        }
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

  private static <T extends ItemGroup> T register(String name, T group) {
    return Registry.register(Registries.ITEM_GROUP, ExtShape.id(name), group);
  }

  public static void init() {
    prepareBlockList();

    Objects.requireNonNull(MOD_GROUPS);
  }
}