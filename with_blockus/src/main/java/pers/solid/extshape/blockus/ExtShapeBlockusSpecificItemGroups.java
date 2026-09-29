package pers.solid.extshape.blockus;

import com.brand.blockus.registry.content.BlockusBlocks;
import com.brand.blockus.registry.content.bundles.ConcreteBundle;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.itemgroup.ExtShapeSpecificItemGroups;
import pers.solid.extshape.util.BlockBiMaps;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 扩展方块形状 - Blockus 中的专用物品组。只有当 {@link ExtShapeConfig#showSpecificGroups} 为 true 时，此类才会被初始化，否则该类一直不会被初始化。
 */
public final class ExtShapeBlockusSpecificItemGroups {
  private static final ArrayList<Block> CONSTRUCTION_BASE_BLOCKS = new ArrayList<>();
  private static final ArrayList<List<Block>> COLORED_BASE_BLOCKS = new ArrayList<>();

  /**
   * 扩展方块形状 - Blockus 模组中的建筑方块。
   */
  public static final ItemGroup CONSTRUCTION_BLOCKS = register("extshape-blockus_construction_blocks", FabricItemGroup.builder()
      .displayName(wrapItemGroupName(Text.translatable("itemGroup.extshape_blockus.construction_blocks")))
      .icon(() -> new ItemStack(BlockBiMaps.getBlockOfOrThrow(BlockShape.WALL, BlockusBlocks.YELLOW_GINGHAM_WOOL.block)))
      .entries((displayContext, entries) -> CONSTRUCTION_BASE_BLOCKS.forEach(block -> ExtShapeSpecificItemGroups.addBaseAndVariantsToEntries(block, entries)))
      .build());

  /**
   * 扩展方块形状 - Blockus 模组中的染色方块。
   */
  public static final ItemGroup COLORED_BLOCKS = register("extshape-blockus_colored_blocks", FabricItemGroup.builder()
      .displayName(wrapItemGroupName(Text.translatable("itemGroup.extshape_blockus.colored_blocks")))
      .icon(() -> new ItemStack(BlockBiMaps.getBlockOfOrThrow(BlockShape.VERTICAL_STAIRS, BlockusBlocks.MANGROVE_MOSAIC.block)))
      .entries((displayContext, entries) -> COLORED_BASE_BLOCKS.forEach(blocks -> ExtShapeSpecificItemGroups.addColoredBaseAndVariantsToEntries(blocks, entries)))
      .build());

  private static <T extends ItemGroup> T register(String name, T group) {
    return Registry.register(Registries.ITEM_GROUP, ExtShapeBlockus.id(name), group);
  }

  private static void prepareBlockusBlockList() {
    CONSTRUCTION_BASE_BLOCKS.clear();
    final var c = COLORED_BASE_BLOCKS;
    c.clear();

    // 染色石砖
    c.add(List.of(BlockusBlocks.WHITE_STONE_BRICKS.block,
        BlockusBlocks.GRAY_STONE_BRICKS.block,
        BlockusBlocks.BLACK_STONE_BRICKS.block,
        BlockusBlocks.BROWN_STONE_BRICKS.block,
        BlockusBlocks.RED_STONE_BRICKS.block,
        BlockusBlocks.ORANGE_STONE_BRICKS.block,
        BlockusBlocks.YELLOW_STONE_BRICKS.block,
        BlockusBlocks.LIME_STONE_BRICKS.block,
        BlockusBlocks.GREEN_STONE_BRICKS.block,
        BlockusBlocks.CYAN_STONE_BRICKS.block,
        BlockusBlocks.LIGHT_BLUE_STONE_BRICKS.block,
        BlockusBlocks.BLUE_STONE_BRICKS.block,
        BlockusBlocks.PURPLE_STONE_BRICKS.block,
        BlockusBlocks.MAGENTA_STONE_BRICKS.block,
        BlockusBlocks.PINK_STONE_BRICKS.block));

    // 染色混凝土砖
    c.add(ImmutableList.copyOf(Lists.transform(ConcreteBundle.values(), input -> input.block)));
    c.add(ImmutableList.copyOf(Lists.transform(ConcreteBundle.values(), input -> input.chiseled)));

    // 瓦片
    c.add(ImmutableList.of(BlockusBlocks.SHINGLES.block));

    // 染色瓦片
    c.add(List.of(BlockusBlocks.WHITE_SHINGLES.block,
        BlockusBlocks.LIGHT_GRAY_SHINGLES.block,
        BlockusBlocks.GRAY_SHINGLES.block,
        BlockusBlocks.BLACK_SHINGLES.block,
        BlockusBlocks.BROWN_SHINGLES.block,
        BlockusBlocks.RED_SHINGLES.block,
        BlockusBlocks.ORANGE_SHINGLES.block,
        BlockusBlocks.YELLOW_SHINGLES.block,
        BlockusBlocks.LIME_SHINGLES.block,
        BlockusBlocks.GREEN_SHINGLES.block,
        BlockusBlocks.CYAN_SHINGLES.block,
        BlockusBlocks.LIGHT_BLUE_SHINGLES.block,
        BlockusBlocks.BLUE_SHINGLES.block,
        BlockusBlocks.PURPLE_SHINGLES.block,
        BlockusBlocks.MAGENTA_SHINGLES.block,
        BlockusBlocks.PINK_SHINGLES.block));

    // 花纹羊毛
    c.add(List.of(BlockusBlocks.WHITE_PATTERNED_WOOL.block,
        BlockusBlocks.LIGHT_GRAY_PATTERNED_WOOL.block,
        BlockusBlocks.GRAY_PATTERNED_WOOL.block,
        BlockusBlocks.BLACK_PATTERNED_WOOL.block,
        BlockusBlocks.BROWN_PATTERNED_WOOL.block,
        BlockusBlocks.RED_PATTERNED_WOOL.block,
        BlockusBlocks.ORANGE_PATTERNED_WOOL.block,
        BlockusBlocks.YELLOW_PATTERNED_WOOL.block,
        BlockusBlocks.LIME_PATTERNED_WOOL.block,
        BlockusBlocks.GREEN_PATTERNED_WOOL.block,
        BlockusBlocks.CYAN_PATTERNED_WOOL.block,
        BlockusBlocks.LIGHT_BLUE_PATTERNED_WOOL.block,
        BlockusBlocks.BLUE_PATTERNED_WOOL.block,
        BlockusBlocks.PURPLE_PATTERNED_WOOL.block,
        BlockusBlocks.MAGENTA_PATTERNED_WOOL.block,
        BlockusBlocks.PINK_PATTERNED_WOOL.block));

    // 方格羊毛
    c.add(List.of(BlockusBlocks.WHITE_GINGHAM_WOOL.block,
        BlockusBlocks.LIGHT_GRAY_GINGHAM_WOOL.block,
        BlockusBlocks.GRAY_GINGHAM_WOOL.block,
        BlockusBlocks.BLACK_GINGHAM_WOOL.block,
        BlockusBlocks.BROWN_GINGHAM_WOOL.block,
        BlockusBlocks.RED_GINGHAM_WOOL.block,
        BlockusBlocks.ORANGE_GINGHAM_WOOL.block,
        BlockusBlocks.YELLOW_GINGHAM_WOOL.block,
        BlockusBlocks.LIME_GINGHAM_WOOL.block,
        BlockusBlocks.GREEN_GINGHAM_WOOL.block,
        BlockusBlocks.CYAN_GINGHAM_WOOL.block,
        BlockusBlocks.LIGHT_BLUE_GINGHAM_WOOL.block,
        BlockusBlocks.BLUE_GINGHAM_WOOL.block,
        BlockusBlocks.PURPLE_GINGHAM_WOOL.block,
        BlockusBlocks.MAGENTA_GINGHAM_WOOL.block,
        BlockusBlocks.PINK_GINGHAM_WOOL.block));

    // 带釉陶瓦柱
    c.add(List.of(BlockusBlocks.WHITE_GLAZED_TERRACOTTA_PILLAR,
        BlockusBlocks.LIGHT_GRAY_GLAZED_TERRACOTTA_PILLAR,
        BlockusBlocks.GRAY_GLAZED_TERRACOTTA_PILLAR,
        BlockusBlocks.BLACK_GLAZED_TERRACOTTA_PILLAR,
        BlockusBlocks.BROWN_GLAZED_TERRACOTTA_PILLAR,
        BlockusBlocks.RED_GLAZED_TERRACOTTA_PILLAR,
        BlockusBlocks.ORANGE_GLAZED_TERRACOTTA_PILLAR,
        BlockusBlocks.YELLOW_GLAZED_TERRACOTTA_PILLAR,
        BlockusBlocks.LIME_GLAZED_TERRACOTTA_PILLAR,
        BlockusBlocks.GREEN_GLAZED_TERRACOTTA_PILLAR,
        BlockusBlocks.CYAN_GLAZED_TERRACOTTA_PILLAR,
        BlockusBlocks.LIGHT_BLUE_GLAZED_TERRACOTTA_PILLAR,
        BlockusBlocks.BLUE_GLAZED_TERRACOTTA_PILLAR,
        BlockusBlocks.PURPLE_GLAZED_TERRACOTTA_PILLAR,
        BlockusBlocks.MAGENTA_GLAZED_TERRACOTTA_PILLAR,
        BlockusBlocks.PINK_GLAZED_TERRACOTTA_PILLAR));

    // 剩下的方块加入建筑方块物品组

    ObjectSet<Block> baseBlocks = new ObjectLinkedOpenHashSet<>(ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS);
    COLORED_BASE_BLOCKS.forEach(baseBlocks::removeAll);
    CONSTRUCTION_BASE_BLOCKS.addAll(baseBlocks);
  }

  private static Text wrapItemGroupName(Text text) {
    return Text.empty()
        .append(Text.translatable("itemGroup.extshape.prefix", Text.translatable("modmenu.nameTranslation.extshape_blockus")).styled(style -> style.withColor(0x098F4A)))
        .append(text);
  }

  public static void init() {
    prepareBlockusBlockList();
    Objects.requireNonNull(CONSTRUCTION_BLOCKS);
    Objects.requireNonNull(COLORED_BLOCKS);
  }
}
