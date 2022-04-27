package pers.solid.extshape;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import pers.solid.extshape.builder.Shape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.mixin.CreativeInventoryScreenAccessor;
import pers.solid.extshape.mixin.ItemGroupAccessor;
import pers.solid.extshape.tag.ExtShapeBlockTag;

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

  static {
    final ItemGroup[] groups = ItemGroup.GROUPS;
    final ItemGroup[] newGroups = new ItemGroup[groups.length + 4];

    System.arraycopy(groups, 0, newGroups, 0, groups.length);

    ItemGroupAccessor.setGroups(newGroups);
    WOODEN_BLOCK_GROUP = new ExtShapeItemGroup(
        groups.length,
        new Identifier("extshape", "wooden_blocks"),
        Suppliers.ofInstance(new ItemStack(Blocks.BIRCH_PLANKS)),
        (stacks, group) -> WOODEN_BLOCKS.forEach((block -> importTo(block,
            stacks)))
    );

    COLORFUL_BLOCK_GROUP = new ExtShapeItemGroup(
        groups.length + 1,
        new Identifier("extshape", "colorful_blocks"),
        Suppliers.ofInstance(new ItemStack(Blocks.YELLOW_WOOL)),
        (stacks, group) -> COLORFUL_BLOCKS.forEach(block -> importTo(block, stacks))
    );

    STONE_BLOCK_GROUP = new ExtShapeItemGroup(
        groups.length + 2,
        new Identifier("extshape", "stone_blocks"),
        Suppliers.ofInstance(new ItemStack(Blocks.STONE)),
        (stacks, group) -> STONE_BLOCKS.forEach(block -> importTo(block, stacks))
    );

    OTHER_BLOCK_GROUP = new ExtShapeItemGroup(
        groups.length + 3,
        new Identifier("extshape", "other_blocks"),
        Suppliers.ofInstance(new ItemStack(Blocks.CHISELED_QUARTZ_BLOCK)),
        (stacks, group) -> {
          Set<Block> baseBlockList = new LinkedHashSet<>(BlockMappings.BASE_BLOCKS);
          WOODEN_BLOCKS.forEach(baseBlockList::remove);
          COLORFUL_BLOCKS.forEach(baseBlockList::remove);
          STONE_BLOCKS.forEach(baseBlockList::remove);
          baseBlockList.forEach(block -> importTo(block, stacks));
        });

    MOD_GROUPS = ImmutableSet.of(WOODEN_BLOCK_GROUP, COLORFUL_BLOCK_GROUP, STONE_BLOCK_GROUP, OTHER_BLOCK_GROUP);

    if (!ExtShapeConfig.CURRENT_CONFIG.hasSpecificGroup) {
      ItemGroupAccessor.setGroups(groups);
    }
  }

  static {
    WOODEN_BLOCKS.addAll(ExtShapeBlockTag.PLANKS);
    COLORFUL_BLOCKS.addAll(ExtShapeBlockTag.WOOLS);
    COLORFUL_BLOCKS.addAll(ExtShapeBlockTag.CONCRETES);
    COLORFUL_BLOCKS.add(Blocks.TERRACOTTA);
    COLORFUL_BLOCKS.addAll(ExtShapeBlockTag.STAINED_TERRACOTTAS);
    COLORFUL_BLOCKS.addAll(ExtShapeBlockTag.GLAZED_TERRACOTTAS);
    STONE_BLOCKS.addAll(ExtShapeBlockTag.STONES);
    STONE_BLOCKS.addAll(List.of(
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
  }

  private final Supplier<ItemStack> stackSupplier;
  private final BiConsumer<List<ItemStack>, ItemGroup> stacksForDisplay;

  public ExtShapeItemGroup(int index, Identifier id, Supplier<ItemStack> stackSupplier, BiConsumer<List<ItemStack>, ItemGroup> stacksForDisplay) {
    super(index, id.getNamespace() + "." + id.getPath());
    this.stackSupplier = stackSupplier;
    this.stacksForDisplay = stacksForDisplay;
  }

  /**
   * 在游戏运行中途将本模组的物品组加到 {@link ItemGroup#GROUPS} 中。一开始运行时不会执行此方法，只有在模组中途通过配置屏幕将已关闭的 {@link ExtShapeConfig#hasSpecificGroup} 设为 {@code true} 时才会执行本方法。
   */
  public static void implementGroups() {
    final ItemGroup[] groups = ItemGroup.GROUPS;
    final ItemGroup[] newGroups = new ItemGroup[groups.length + 4];
    System.arraycopy(groups, 0, newGroups, 0, groups.length);
    newGroups[groups.length] = WOODEN_BLOCK_GROUP;
    newGroups[groups.length + 1] = COLORFUL_BLOCK_GROUP;
    newGroups[groups.length + 2] = STONE_BLOCK_GROUP;
    newGroups[groups.length + 3] = OTHER_BLOCK_GROUP;
    ItemGroupAccessor.setGroups(newGroups);
  }

  public static void removeGroups() {
    final ItemGroup[] newGroups = Arrays.stream(ItemGroup.GROUPS).filter(group -> !MOD_GROUPS.contains(group)).toArray(ItemGroup[]::new);
    ItemGroupAccessor.setGroups(newGroups);
    final int selectedTab = CreativeInventoryScreenAccessor.getSelectedTab();
    if (selectedTab >= newGroups.length) {
      CreativeInventoryScreenAccessor.setSelectedTab(newGroups.length - 1);
    }
  }

  // 以下为按方块排序的列表。
  protected static void importTo(Block baseBlock, List<ItemStack> itemStacks) {
    Block t;
    List<ItemStack> is = new ArrayList<>();
    if (baseBlock == null) return;
    for (Shape shape : Shape.values()) {
      if ((t = BlockMappings.getBlockOf(shape, baseBlock)) != null) is.add(new ItemStack(t));
    }
    if (is.size() > 0) {
      itemStacks.add(new ItemStack(baseBlock));
      itemStacks.addAll(is);
    }
  }

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