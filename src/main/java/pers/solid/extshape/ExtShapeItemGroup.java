package pers.solid.extshape;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.Contract;
import pers.solid.extshape.builder.Shape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.mixin.CreativeInventoryScreenAccessor;
import pers.solid.extshape.mixin.ItemGroupAccessor;
import pers.solid.extshape.tag.ExtShapeBlockTags;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * 本模组中的物品组。
 * 物品将会添加到原版物品组，然后再添加四个额外的物品组，用于分类存放物品，且这些额外物品组包括原版方块。
 */
public class ExtShapeItemGroup extends ItemGroup {
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
    final ItemGroup[] groups = ItemGroup.GROUPS;
    final ItemGroup[] newGroups = new ItemGroup[groups.length + 4];

    System.arraycopy(groups, 0, newGroups, 0, groups.length);

    ItemGroupAccessor.setGroups(newGroups);
    WOODEN_BLOCK_GROUP = new ExtShapeItemGroup(
        groups.length,
        new Identifier(ExtShape.MOD_ID, "wooden_blocks"),
        Suppliers.ofInstance(new ItemStack(BlockMappings.getBlockOf(Shape.WALL, Blocks.BIRCH_PLANKS))),
        (stacks, group) -> WOODEN_BLOCKS.forEach((block -> importTo(block,
            stacks)))
    );

    COLORFUL_BLOCK_GROUP = new ExtShapeItemGroup(
        groups.length + 1,
        new Identifier(ExtShape.MOD_ID, "colorful_blocks"),
        Suppliers.ofInstance(new ItemStack(BlockMappings.getBlockOf(Shape.STAIRS, Blocks.LIME_WOOL))),
        (stacks, group) -> COLORFUL_BLOCKS.forEach(block -> importTo(block, stacks))
    );

    STONE_BLOCK_GROUP = new ExtShapeItemGroup(
        groups.length + 2,
        new Identifier(ExtShape.MOD_ID, "stone_blocks"),
        Suppliers.ofInstance(new ItemStack(BlockMappings.getBlockOf(Shape.FENCE, Blocks.CALCITE))),
        (stacks, group) -> STONE_BLOCKS.forEach(block -> importTo(block, stacks))
    );

    OTHER_BLOCK_GROUP = new ExtShapeItemGroup(
        groups.length + 3,
        new Identifier(ExtShape.MOD_ID, "other_blocks"),
        Suppliers.ofInstance(new ItemStack(BlockMappings.getBlockOf(Shape.VERTICAL_SLAB, Blocks.WAXED_OXIDIZED_COPPER))),
        (stacks, group) -> OTHER_BLOCKS.forEach(block -> importTo(block, stacks)));

    MOD_GROUPS = ImmutableSet.of(WOODEN_BLOCK_GROUP, COLORFUL_BLOCK_GROUP, STONE_BLOCK_GROUP, OTHER_BLOCK_GROUP);

    if (!ExtShapeConfig.CURRENT_CONFIG.showSpecificGroups) {
      ItemGroupAccessor.setGroups(groups);
    }
  }

  static {
    WOODEN_BLOCKS.addAll(ExtShapeBlockTags.PLANKS);
    COLORFUL_BLOCKS.addAll(ExtShapeBlockTags.WOOLS);
    COLORFUL_BLOCKS.addAll(ExtShapeBlockTags.CONCRETES);
    COLORFUL_BLOCKS.add(Blocks.TERRACOTTA);
    COLORFUL_BLOCKS.addAll(ExtShapeBlockTags.STAINED_TERRACOTTA);
    COLORFUL_BLOCKS.addAll(ExtShapeBlockTags.GLAZED_TERRACOTTA);
    STONE_BLOCKS.addAll(ExtShapeBlockTags.STONES);
    STONE_BLOCKS.addAll(Arrays.asList(
        Blocks.SMOOTH_STONE,
        Blocks.STONE_BRICKS,
        Blocks.MOSSY_STONE_BRICKS,
        Blocks.CHISELED_STONE_BRICKS,
        Blocks.COBBLED_DEEPSLATE,
        Blocks.POLISHED_DEEPSLATE,
        Blocks.DEEPSLATE_BRICKS,
        Blocks.DEEPSLATE_TILES,
        Blocks.CHISELED_DEEPSLATE,
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
        Blocks.SMOOTH_BASALT,
        Blocks.RED_NETHER_BRICKS,
        Blocks.PRISMARINE,
        Blocks.PRISMARINE_BRICKS,
        Blocks.DARK_PRISMARINE,
        Blocks.BRICKS,
        Blocks.BLACKSTONE,
        Blocks.POLISHED_BLACKSTONE,
        Blocks.POLISHED_BLACKSTONE_BRICKS,
        Blocks.OBSIDIAN,
        Blocks.CRYING_OBSIDIAN,
        Blocks.BEDROCK,
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

    Set<Block> baseBlockList = new LinkedHashSet<>(BlockMappings.BASE_BLOCKS);
    WOODEN_BLOCKS.forEach(baseBlockList::remove);
    COLORFUL_BLOCKS.forEach(baseBlockList::remove);
    STONE_BLOCKS.forEach(baseBlockList::remove);
    OTHER_BLOCKS.addAll(baseBlockList);
  }

  private final Supplier<ItemStack> stackSupplier;
  private final BiConsumer<List<ItemStack>, ItemGroup> stacksForDisplay;

  public ExtShapeItemGroup(int index, Identifier id, Supplier<ItemStack> stackSupplier, BiConsumer<List<ItemStack>, ItemGroup> stacksForDisplay) {
    super(index, id.getNamespace() + "." + id.getPath());
    this.stackSupplier = stackSupplier;
    this.stacksForDisplay = stacksForDisplay;
  }

  /**
   * 在游戏运行中途将本模组的物品组加到 {@link ItemGroup#GROUPS} 中。一开始运行时不会执行此方法，只有在模组中途通过配置屏幕将已关闭的 {@link ExtShapeConfig#showSpecificGroups} 设为 {@code true} 时才会执行本方法。
   */
  public static void implementGroups() {
    final ItemGroup[] groups = ItemGroup.GROUPS;
    final ItemGroup[] newGroups = new ItemGroup[groups.length + 4];
    System.arraycopy(groups, 0, newGroups, 0, groups.length);
    ((ItemGroupAccessor) WOODEN_BLOCK_GROUP).setIndex(groups.length);
    ((ItemGroupAccessor) COLORFUL_BLOCK_GROUP).setIndex(groups.length + 1);
    ((ItemGroupAccessor) STONE_BLOCK_GROUP).setIndex(groups.length + 2);
    ((ItemGroupAccessor) OTHER_BLOCK_GROUP).setIndex(groups.length + 3);
    newGroups[groups.length] = WOODEN_BLOCK_GROUP;
    newGroups[groups.length + 1] = COLORFUL_BLOCK_GROUP;
    newGroups[groups.length + 2] = STONE_BLOCK_GROUP;
    newGroups[groups.length + 3] = OTHER_BLOCK_GROUP;
    ItemGroupAccessor.setGroups(newGroups);
  }

  public static void removeGroups() {
    final ItemGroup[] newGroups = new ItemGroup[ItemGroup.GROUPS.length - 4];
    int i = 0;
    for (ItemGroup group : ItemGroup.GROUPS) {
      if (!MOD_GROUPS.contains(group)) {
        newGroups[i] = group;
        ((ItemGroupAccessor) group).setIndex(i);
        i += 1;
      } else {
        ((ItemGroupAccessor) group).setIndex(-1);
      }
    }
    ItemGroupAccessor.setGroups(newGroups);
    final int selectedTab = CreativeInventoryScreenAccessor.getSelectedTab();
    if (selectedTab >= newGroups.length) {
      CreativeInventoryScreenAccessor.setSelectedTab(newGroups.length - 1);
    }
  }

  /**
   * 将方块及其变种都添加到物品堆的列表中。
   *
   * @param baseBlock  其基础方块。
   * @param itemStacks 需要被添加至的物品堆的列表。
   */
  @Contract(mutates = "param2")
  protected static void importTo(Block baseBlock, List<ItemStack> itemStacks) {
    if (baseBlock == null) return;
    itemStacks.add(new ItemStack(baseBlock));
    for (Shape shape : Shape.values()) {
      final Block shapeBlock = BlockMappings.getBlockOf(shape, baseBlock);
      if (shapeBlock != null) itemStacks.add(new ItemStack(shapeBlock));
    }
  }

  @SuppressWarnings("EmptyMethod")
  public static void init() {
  }

  @Override
  public void appendStacks(DefaultedList<ItemStack> stacks) {
    stacksForDisplay.accept(stacks, this);
  }

  @Override
  public ItemStack createIcon() {
    return stackSupplier.get();
  }

  @Override
  public int getIndex() {
    final int index = super.getIndex();
    if (index == -1) ExtShape.LOGGER.warn("Attempt to find an unactivated group!");
    return index;
  }
}