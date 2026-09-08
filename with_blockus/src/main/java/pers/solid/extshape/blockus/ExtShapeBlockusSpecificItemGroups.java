package pers.solid.extshape.blockus;

import com.brand.blockus.registry.content.BlockusBlocks;
import com.brand.blockus.utils.helper.BlockOrder;
import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
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
  public static final CreativeModeTab CONSTRUCTION_BLOCKS = register("extshape-blockus_construction_blocks", FabricCreativeModeTab.builder()
      .title(wrapItemGroupName(Component.translatable("itemGroup.extshape_blockus.construction_blocks")))
      .icon(() -> new ItemStack(BlockBiMaps.getBlockOfOrThrow(BlockShape.WALL, BlockusBlocks.GINGHAM_WOOL.block().blocks().yellow())))
      .displayItems((displayContext, entries) -> CONSTRUCTION_BASE_BLOCKS.forEach(block -> ExtShapeSpecificItemGroups.addBaseAndVariantsToEntries(block, entries)))
      .build());

  /**
   * 扩展方块形状 - Blockus 模组中的染色方块。
   */
  public static final CreativeModeTab COLORED_BLOCKS = register("extshape-blockus_colored_blocks", FabricCreativeModeTab.builder()
      .title(wrapItemGroupName(Component.translatable("itemGroup.extshape_blockus.colored_blocks")))
      .icon(() -> new ItemStack(BlockBiMaps.getBlockOfOrThrow(BlockShape.VERTICAL_STAIRS, BlockusBlocks.WOODEN_MOSAIC.get("mangrove").block())))
      .displayItems((displayContext, entries) -> COLORED_BASE_BLOCKS.forEach(blocks -> ExtShapeSpecificItemGroups.addColoredBaseAndVariantsToEntries(blocks, entries)))
      .build());

  private static <T extends CreativeModeTab> T register(String name, T group) {
    return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ExtShapeBlockus.id(name), group);
  }

  private static void prepareBlockusBlockList() {
    CONSTRUCTION_BASE_BLOCKS.clear();
    final var c = COLORED_BASE_BLOCKS;
    c.clear();

    // 染色石砖
    c.add(BlockOrder.COLOR.stream().map(BlockusBlocks.DYED_STONE_BRICKS.block().blocks()::pick).toList());

    // 染色混凝土砖
    c.add(BlockOrder.COLOR.stream().map(BlockusBlocks.CONCRETE_BRICKS.block().blocks()::pick).toList());
    c.add(BlockOrder.COLOR.stream().map(BlockusBlocks.CONCRETE_BRICKS.chiseled().blocks()::pick).toList());

    // 瓦片
    c.add(ImmutableList.of(BlockusBlocks.SHINGLES.block()));

    // 染色瓦片
    c.add(BlockOrder.COLOR.stream().map(BlockusBlocks.DYED_SHINGLES.block().blocks()::pick).toList());

    // 花纹羊毛
    c.add(BlockOrder.COLOR.stream().map(BlockusBlocks.PATTERNED_WOOL.block().blocks()::pick).toList());

    // 方格羊毛
    c.add(BlockOrder.COLOR.stream().map(BlockusBlocks.GINGHAM_WOOL.block().blocks()::pick).toList());

    // 带釉陶瓦柱
    c.add(BlockOrder.COLOR.stream().map(BlockusBlocks.GLAZED_TERRACOTTA_PILLAR.blocks()::pick).toList());

    // 剩下的方块加入建筑方块物品组

    ObjectSet<Block> baseBlocks = new ObjectLinkedOpenHashSet<>(ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS);
    COLORED_BASE_BLOCKS.forEach(baseBlocks::removeAll);
    CONSTRUCTION_BASE_BLOCKS.addAll(baseBlocks);
  }

  private static Component wrapItemGroupName(Component text) {
    return Component.empty()
        .append(Component.translatable("itemGroup.extshape.prefix", Component.translatable("modmenu.nameTranslation.extshape_blockus")).withStyle(style -> style.withColor(0x098F4A)))
        .append(text);
  }

  public static void init() {
    prepareBlockusBlockList();
    Objects.requireNonNull(CONSTRUCTION_BLOCKS);
    Objects.requireNonNull(COLORED_BLOCKS);
  }
}
