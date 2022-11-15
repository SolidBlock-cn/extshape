package pers.solid.extshape;

import pers.solid.extshape.config.ExtShapeConfig;

/**
 * 本模组中的物品组。
 * 物品将会添加到原版物品组，然后再添加四个额外的物品组，用于分类存放物品，且这些额外物品组包括原版方块。
 */
public class ExtShapeItemGroup /*extends ItemGroup*/ {/*
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
    final List<ItemGroup> groups = ItemGroups.getGroups();
    final ImmutableList.Builder<ItemGroup> newGroups = ImmutableList.builderWithExpectedSize(groups.size() + 4);
    newGroups.addAll(groups);

    ItemGroupsAccessor.setGroups(newGroups.build());
    WOODEN_BLOCK_GROUP = new ExtShapeItemGroup(
        groups.length,
        new Identifier(ExtShape.MOD_ID, "wooden_blocks"),
        Suppliers.ofInstance(new ItemStack(BlockMappings.getBlockOf(BlockShape.WALL, Blocks.BAMBOO_MOSAIC))),
        (featureSet, entries) -> WOODEN_BLOCKS.forEach((block -> importTo(block,
            featureSet, entries)))
    );

    COLORFUL_BLOCK_GROUP = new ExtShapeItemGroup(
        groups.length + 1,
        new Identifier(ExtShape.MOD_ID, "colorful_blocks"),
        Suppliers.ofInstance(new ItemStack(BlockMappings.getBlockOf(BlockShape.STAIRS, Blocks.LIME_WOOL))),
        (featureSet, entries) -> COLORFUL_BLOCKS.forEach(block -> importTo(block, featureSet, entries))
    );

    STONE_BLOCK_GROUP = new ExtShapeItemGroup(
        groups.length + 2,
        new Identifier(ExtShape.MOD_ID, "stone_blocks"),
        Suppliers.ofInstance(new ItemStack(BlockMappings.getBlockOf(BlockShape.FENCE, Blocks.CALCITE))),
        (featureSet, entries) -> STONE_BLOCKS.forEach(block -> importTo(block, featureSet, entries))
    );

    OTHER_BLOCK_GROUP = new ExtShapeItemGroup(
        groups.length + 3,
        new Identifier(ExtShape.MOD_ID, "other_blocks"),
        Suppliers.ofInstance(new ItemStack(BlockMappings.getBlockOf(BlockShape.VERTICAL_SLAB, Blocks.WAXED_OXIDIZED_COPPER))),
        (featureSet, entries) -> OTHER_BLOCKS.forEach(block -> importTo(block, featureSet, entries)));

    MOD_GROUPS = ImmutableSet.of(WOODEN_BLOCK_GROUP, COLORFUL_BLOCK_GROUP, STONE_BLOCK_GROUP, OTHER_BLOCK_GROUP);

    if (!ExtShapeConfig.CURRENT_CONFIG.showSpecificGroups) {
      ItemGroupsAccessor.setGroups(ItemGroupsAccessor.invokeAsArray(newGroups));
    } else {
      for (ItemGroup group : MOD_GROUPS) {
        newGroups[group.getIndex()] = group;
      }
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
    STONE_BLOCKS.addAll(ExtShapeTags.STONES);
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

    Set<Block> baseBlockList = new LinkedHashSet<>(BlockMappings.BASE_BLOCKS);
    WOODEN_BLOCKS.forEach(baseBlockList::remove);
    COLORFUL_BLOCKS.forEach(baseBlockList::remove);
    STONE_BLOCKS.forEach(baseBlockList::remove);
    for (Block block : baseBlockList) {
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

  private final Supplier<ItemStack> stackSupplier;
  private final BiConsumer<FeatureSet, Entries> stacksForDisplay;

  public ExtShapeItemGroup(Row row, int column, Type type, Identifier id, Supplier<ItemStack> iconSupplier, EntryCollector entryCollector) {
    super(row, column, type, Text.translatable("itemGroup." + id.getNamespace() + "." + id.getPath()), iconSupplier, entryCollector);
  }

  *//**
 * 在游戏运行中途将本模组的物品组加到 {@link net.minecraft.item.ItemGroups#GROUPS} 中。一开始运行时不会执行此方法，只有在模组中途通过配置屏幕将已关闭的 {@link ExtShapeConfig#showSpecificGroups} 设为 {@code true} 时才会执行本方法。
 *//*
  public static void implementGroups() {
    final ItemGroup[] groups = ItemGroups.GROUPS;
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
    ItemGroupsAccessor.setGroups(ItemGroupsAccessor.invokeAsArray(newGroups));
  }

  public static void removeGroups() {
    final ItemGroup[] newGroups = new ItemGroup[ItemGroups.GROUPS.length - 4];
    int i = 0;
    for (ItemGroup group : ItemGroups.GROUPS) {
      if (!MOD_GROUPS.contains(group)) {
        newGroups[i] = group;
        ((ItemGroupAccessor) group).setIndex(i);
        i += 1;
      } else {
        ((ItemGroupAccessor) group).setIndex(-1);
      }
    }
    ItemGroupsAccessor.setGroups(ItemGroupsAccessor.invokeAsArray(newGroups));
    final int selectedTab = CreativeInventoryScreenAccessor.getSelectedTab();
    if (selectedTab >= newGroups.length) {
      CreativeInventoryScreenAccessor.setSelectedTab(newGroups.length - 1);
    }
  }

  *//**
 * 将方块及其变种都添加到物品堆的列表中。
 *
 * @param baseBlock 其基础方块。
 * @param entries   需要被添加至的物品堆的列表。
 *//*
  @Contract(mutates = "param2")
  protected static void importTo(Block baseBlock, FeatureSet featureSet, Entries entries) {
    if (baseBlock == null) return;
    entries.add(baseBlock);
    for (BlockShape shape : BlockShape.values()) {
      final Block shapeBlock = BlockMappings.getBlockOf(shape, baseBlock);
      if (shapeBlock != null) entries.add(shapeBlock);
    }
  }

  @SuppressWarnings("EmptyMethod")
  public static void init() {
  }

  @Override
  public ItemStack createIcon() {
    return stackSupplier.get();
  }

  @Override
  protected void addItems(FeatureSet enabledFeatures, Entries entries) {
    stacksForDisplay.accept(enabledFeatures, entries);
  }

  @Override
  public int getIndex() {
    final int index = super.getIndex();
    if (index == -1) ExtShape.LOGGER.warn("Attempt to find an unactivated group!");
    return index;
  }*/
}