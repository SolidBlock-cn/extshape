package pers.solid.extshape.blockus;

import com.brand.blockus.registry.content.BlockusBlocks;
import com.brand.blockus.utils.helper.BlockOrder;
import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.itemgroup.ExtShapeSpecificItemGroups;
import pers.solid.extshape.util.BlockBiMaps;

import java.util.ArrayList;
import java.util.Arrays;
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
      .icon(() -> new ItemStack(BlockBiMaps.getBlockOfOrThrow(BlockShape.WALL, BlockusBlocks.GINGHAM_WOOL.colorMap().get(DyeColor.YELLOW).block())))
      .entries((displayContext, entries) -> CONSTRUCTION_BASE_BLOCKS.forEach(block -> ExtShapeSpecificItemGroups.addBaseAndVariantsToEntries(block, entries)))
      .build());

  /**
   * 扩展方块形状 - Blockus 模组中的染色方块。
   */
  public static final ItemGroup COLORED_BLOCKS = register("extshape-blockus_colored_blocks", FabricItemGroup.builder()
      .displayName(wrapItemGroupName(Text.translatable("itemGroup.extshape_blockus.colored_blocks")))
      .icon(() -> new ItemStack(BlockBiMaps.getBlockOfOrThrow(BlockShape.VERTICAL_STAIRS, BlockusBlocks.WOODEN_MOSAIC.get("mangrove").block())))
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
    c.add(Arrays.stream(BlockOrder.COLOR).map(dyeColor -> BlockusBlocks.STAINED_STONE_BRICKS.colorMap().get(dyeColor).block()).toList());

    // 染色混凝土砖
    c.add(Arrays.stream(BlockOrder.COLOR).map(dyeColor -> BlockusBlocks.CONCRETE_BRICKS.colorMap().get(dyeColor).block()).toList());
    c.add(Arrays.stream(BlockOrder.COLOR).map(dyeColor -> BlockusBlocks.CONCRETE_BRICKS.colorMap().get(dyeColor).chiseled()).toList());

    // 瓦片
    c.add(ImmutableList.of(BlockusBlocks.SHINGLES.block()));

    // 染色瓦片
    c.add(Arrays.stream(BlockOrder.COLOR).map(dyeColor -> BlockusBlocks.STAINED_SHINGLES.colorMap().get(dyeColor).block()).toList());

    // 花纹羊毛
    c.add(Arrays.stream(BlockOrder.COLOR).map(dyeColor -> BlockusBlocks.PATTERNED_WOOL.colorMap().get(dyeColor).block()).toList());

    // 方格羊毛
    c.add(Arrays.stream(BlockOrder.COLOR).map(dyeColor -> BlockusBlocks.GINGHAM_WOOL.colorMap().get(dyeColor).block()).toList());

    // 带釉陶瓦柱
    c.add(Arrays.stream(BlockOrder.COLOR).map(dyeColor -> BlockusBlocks.GLAZED_TERRACOTTA_PILLAR.colorMap().get(dyeColor)).toList());

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
