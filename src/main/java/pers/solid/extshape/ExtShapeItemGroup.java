package pers.solid.extshape;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.fabricmc.fabric.impl.itemgroup.ItemGroupHelper;
import net.fabricmc.fabric.mixin.itemgroup.ItemGroupsAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.mixin.AbstractBlockAccessor;
import pers.solid.extshape.util.BlockBiMaps;
import pers.solid.extshape.util.BlockCollections;

import java.util.*;
import java.util.function.Supplier;

/**
 * 本模组中的物品组。
 * 物品将会添加到原版物品组，然后再添加四个额外的物品组，用于分类存放物品，且这些额外物品组包括原版方块。
 *
 * @see VanillaItemGroup
 */
public class ExtShapeItemGroup {
  public static final ItemGroup WOODEN_BLOCK_GROUP;
  public static final ItemGroup COLORFUL_BLOCK_GROUP;
  public static final ItemGroup STONE_BLOCK_GROUP;
  public static final ItemGroup OTHER_BLOCK_GROUP;
  public static final ImmutableSet<ItemGroup> MOD_GROUPS;
  private static final ArrayList<Block> WOODEN_BLOCKS = new ArrayList<>();
  private static final ArrayList<Block> COLORFUL_BLOCKS = new ArrayList<>();
  private static final ArrayList<Block> STONE_BLOCKS = new ArrayList<>();
  private static final LinkedHashSet<Block> OTHER_BLOCKS = new LinkedHashSet<>();

  static {
    WOODEN_BLOCK_GROUP = create(
        new Identifier(ExtShape.MOD_ID, "wooden_blocks"),
        Suppliers.ofInstance(new ItemStack(BlockBiMaps.getBlockOf(BlockShape.WALL, Blocks.BAMBOO_MOSAIC))),
        (featureSet, entries, operatorsEnabled) -> WOODEN_BLOCKS.forEach((block -> importTo(block,
            featureSet, entries, operatorsEnabled)))
    );

    COLORFUL_BLOCK_GROUP = create(
        new Identifier(ExtShape.MOD_ID, "colorful_blocks"),
        Suppliers.ofInstance(new ItemStack(BlockBiMaps.getBlockOf(BlockShape.STAIRS, Blocks.LIME_WOOL))),
        (featureSet, entries, operatorsEnabled) -> COLORFUL_BLOCKS.forEach(block -> importTo(block, featureSet, entries, operatorsEnabled))
    );

    STONE_BLOCK_GROUP = create(
        new Identifier(ExtShape.MOD_ID, "stone_blocks"),
        Suppliers.ofInstance(new ItemStack(BlockBiMaps.getBlockOf(BlockShape.FENCE, Blocks.CALCITE))),
        (featureSet, entries, operatorsEnabled) -> STONE_BLOCKS.forEach(block -> importTo(block, featureSet, entries, operatorsEnabled))
    );

    OTHER_BLOCK_GROUP = create(
        new Identifier(ExtShape.MOD_ID, "other_blocks"),
        Suppliers.ofInstance(new ItemStack(BlockBiMaps.getBlockOf(BlockShape.VERTICAL_SLAB, Blocks.WAXED_OXIDIZED_COPPER))),
        (featureSet, entries, operatorsEnabled) -> OTHER_BLOCKS.forEach(block -> importTo(block, featureSet, entries, operatorsEnabled)));

    MOD_GROUPS = ImmutableSet.of(WOODEN_BLOCK_GROUP, COLORFUL_BLOCK_GROUP, STONE_BLOCK_GROUP, OTHER_BLOCK_GROUP);

    if (ExtShapeConfig.CURRENT_CONFIG.showSpecificGroups) {
      implementGroups();
    }
  }

  static {
    Collections.addAll(COLORFUL_BLOCKS,
        Blocks.WHITE_WOOL,
        Blocks.LIGHT_GRAY_WOOL,
        Blocks.GRAY_WOOL,
        Blocks.BLACK_WOOL,
        Blocks.BROWN_WOOL,
        Blocks.RED_WOOL,
        Blocks.ORANGE_WOOL,
        Blocks.YELLOW_WOOL,
        Blocks.LIME_WOOL,
        Blocks.GREEN_WOOL,
        Blocks.CYAN_WOOL,
        Blocks.LIGHT_BLUE_WOOL,
        Blocks.BLUE_WOOL,
        Blocks.PURPLE_WOOL,
        Blocks.MAGENTA_WOOL,
        Blocks.PINK_WOOL,
        Blocks.WHITE_CONCRETE,
        Blocks.LIGHT_GRAY_CONCRETE,
        Blocks.GRAY_CONCRETE,
        Blocks.BLACK_CONCRETE,
        Blocks.BROWN_CONCRETE,
        Blocks.RED_CONCRETE,
        Blocks.ORANGE_CONCRETE,
        Blocks.YELLOW_CONCRETE,
        Blocks.LIME_CONCRETE,
        Blocks.GREEN_CONCRETE,
        Blocks.CYAN_CONCRETE,
        Blocks.LIGHT_BLUE_CONCRETE,
        Blocks.BLUE_CONCRETE,
        Blocks.PURPLE_CONCRETE,
        Blocks.MAGENTA_CONCRETE,
        Blocks.PINK_CONCRETE,
        Blocks.TERRACOTTA,
        Blocks.WHITE_TERRACOTTA,
        Blocks.LIGHT_GRAY_TERRACOTTA,
        Blocks.GRAY_TERRACOTTA,
        Blocks.BLACK_TERRACOTTA,
        Blocks.BROWN_TERRACOTTA,
        Blocks.RED_TERRACOTTA,
        Blocks.ORANGE_TERRACOTTA,
        Blocks.YELLOW_TERRACOTTA,
        Blocks.LIME_TERRACOTTA,
        Blocks.GREEN_TERRACOTTA,
        Blocks.CYAN_TERRACOTTA,
        Blocks.LIGHT_BLUE_TERRACOTTA,
        Blocks.BLUE_TERRACOTTA,
        Blocks.PURPLE_TERRACOTTA,
        Blocks.MAGENTA_TERRACOTTA,
        Blocks.PINK_TERRACOTTA,
        Blocks.WHITE_GLAZED_TERRACOTTA,
        Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA,
        Blocks.GRAY_GLAZED_TERRACOTTA,
        Blocks.BLACK_GLAZED_TERRACOTTA,
        Blocks.BROWN_GLAZED_TERRACOTTA,
        Blocks.RED_GLAZED_TERRACOTTA,
        Blocks.ORANGE_GLAZED_TERRACOTTA,
        Blocks.YELLOW_GLAZED_TERRACOTTA,
        Blocks.LIME_GLAZED_TERRACOTTA,
        Blocks.GREEN_GLAZED_TERRACOTTA,
        Blocks.CYAN_GLAZED_TERRACOTTA,
        Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA,
        Blocks.BLUE_GLAZED_TERRACOTTA,
        Blocks.PURPLE_GLAZED_TERRACOTTA,
        Blocks.MAGENTA_GLAZED_TERRACOTTA,
        Blocks.PINK_GLAZED_TERRACOTTA
    );
    STONE_BLOCKS.addAll(BlockCollections.STONES);
    STONE_BLOCKS.addAll(Arrays.asList(
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
        Blocks.RED_NETHER_BRICKS,
        Blocks.BLACKSTONE,
        Blocks.POLISHED_BLACKSTONE,
        Blocks.POLISHED_BLACKSTONE_BRICKS,
        Blocks.OBSIDIAN,
        Blocks.CRYING_OBSIDIAN,
        Blocks.END_STONE,
        Blocks.END_STONE_BRICKS));
    OTHER_BLOCKS.addAll(Arrays.asList(
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
        Blocks.WAXED_OXIDIZED_CUT_COPPER,


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
    WOODEN_BLOCKS.forEach(baseBlocks::remove);
    COLORFUL_BLOCKS.forEach(baseBlocks::remove);
    STONE_BLOCKS.forEach(baseBlocks::remove);
    for (Block block : baseBlocks) {
      final Material material = ((AbstractBlockAccessor) block).getMaterial();
      if (material == Material.STONE) {
        STONE_BLOCKS.add(block);
      } else if (material == Material.WOOD || material == Material.NETHER_WOOD) {
        WOODEN_BLOCKS.add(block);
      } else {
        OTHER_BLOCKS.add(block);
      }
    }
  }

  public static ItemGroup create(Identifier id, Supplier<ItemStack> iconSupplier, ItemGroup.EntryCollector entryCollector) {
    final ItemGroup group = new ItemGroup.Builder(null, -1)
        .entries((enabledFeatures, entries, operatorEnabled) -> {
          if (ExtShapeConfig.CURRENT_CONFIG.showSpecificGroups) {
            entryCollector.accept(enabledFeatures, entries, operatorEnabled);
          }
        })
        .displayName(Text.translatable("itemGroup.%s.%s".formatted(id.getNamespace(), id.getPath())))
        .icon(iconSupplier)
        .build();
    ((net.fabricmc.fabric.impl.itemgroup.FabricItemGroup) group).setId(id);
    return group;
  }

  public static void implementGroups() {
    MOD_GROUPS.forEach(ItemGroupHelper::appendItemGroup);
  }

  public static void removeGroups() {
    MOD_GROUPS.forEach(ExtShapeItemGroup::removeItemGroup);
  }

  /**
   * @see ItemGroupHelper#appendItemGroup(ItemGroup)
   */
  public static void removeItemGroup(ItemGroup itemGroup) {
    if (ItemGroups.getGroups().stream().noneMatch(group -> group.getId().equals(itemGroup.getId()))) {
      ExtShape.LOGGER.warn("Seems trying to remove a group with id {} that does not exist.", itemGroup.getId());
    }

    var itemGroups = new ArrayList<>(ItemGroups.getGroups());
    itemGroups.remove(itemGroup);

    List<ItemGroup> validated = ItemGroupsAccessor.invokeCollect(itemGroups.toArray(ItemGroup[]::new));
    ItemGroupsAccessor.setGroups(validated);
    ItemGroupHelper.sortedGroups = validated.stream().sorted((a, b) -> {
      if (a.isSpecial() && !b.isSpecial()) return 1;
      if (!a.isSpecial() && b.isSpecial()) return -1;
      return 0;
    }).toList();
  }


  /**
   * 将方块及其变种都添加到物品堆的列表中。
   *
   * @param baseBlock 其基础方块。
   * @param entries   需要被添加至的物品堆的列表。
   */
  @Contract(mutates = "param2")
  protected static void importTo(Block baseBlock, FeatureSet featureSet, ItemGroup.Entries entries, boolean operatorsEnabled) {
    if (baseBlock == null) return;
    entries.add(baseBlock);
    for (BlockShape shape : BlockShape.values()) {
      final Block shapeBlock = BlockBiMaps.getBlockOf(shape, baseBlock);
      if (shapeBlock != null) {
        entries.add(shapeBlock);
      }
    }
  }

  public static void init() {
    Objects.requireNonNull(MOD_GROUPS);
  }
}