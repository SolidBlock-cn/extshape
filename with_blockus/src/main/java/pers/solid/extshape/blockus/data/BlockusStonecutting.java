package pers.solid.extshape.blockus.data;

import com.brand.blockus.datagen.providers.BlockusRecipeProvider;
import com.brand.blockus.registry.content.BlockusBlocks;
import com.brand.blockus.registry.content.bundles.ConcreteBundle;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import org.jetbrains.annotations.Unmodifiable;
import pers.solid.extshape.block.CopperManager;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.blockus.BlockusBlockCollections;
import pers.solid.extshape.blockus.BlockusCopperManager;
import pers.solid.extshape.blockus.ExtShapeBlockusBlocks;
import pers.solid.extshape.data.VanillaStonecutting;
import pers.solid.extshape.util.BlockCollections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @see VanillaStonecutting
 */
public final class BlockusStonecutting {
  public static final @Unmodifiable ImmutableMultimap<Block, Block> INSTANCE;

  static {
    final ImmutableMultimap.Builder<Block, Block> builder = new ImmutableMultimap.Builder<>();
    registerBlockusStonecutting(builder);
    INSTANCE = builder.build();
    if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
      final List<Block> unrecognizedBaseBlocks = INSTANCE.keySet().stream().distinct()
          .filter(block -> !(ExtShapeBlocks.containsBaseBlock(block) || ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS.contains(block)))
          .toList();

      if (!unrecognizedBaseBlocks.isEmpty()) {
        throw new IllegalStateException("These blocks exist in the results of BlockusStonecutting.INSTANCE, but are not base blocks: " + unrecognizedBaseBlocks.stream().map(Registries.BLOCK::getId).map(Objects::toString).collect(Collectors.joining(", ")));
      }
    }
  }

  private BlockusStonecutting() {
  }

  /**
   * @see BlockusRecipeProvider#getRecipeGenerator
   */
  private static void registerBlockusStonecutting(ImmutableMultimap.Builder<Block, Block> builder) {
    // 混凝土砖
    for (ConcreteBundle concreteBundle : ConcreteBundle.values()) {
      builder.put(concreteBundle.block, concreteBundle.base);
    }

    // 石头
    builder.putAll(BlockusBlocks.STONE_TILES.block, Blocks.STONE, Blocks.STONE_BRICKS);
    builder.putAll(BlockusBlocks.STONE_CIRCULAR_PAVING, Blocks.STONE, Blocks.STONE_BRICKS);
    builder.putAll(BlockusBlocks.HERRINGBONE_STONE_BRICKS, Blocks.STONE, Blocks.STONE_BRICKS);

    // 三废石
    putMultipleWithMid(builder, BlockusBlocks.ANDESITE_BRICKS.block, Blocks.ANDESITE, Blocks.POLISHED_ANDESITE, BlockusBlocks.HERRINGBONE_ANDESITE_BRICKS, BlockusBlocks.ANDESITE_CIRCULAR_PAVING);
    putMultipleWithMid(builder, BlockusBlocks.DIORITE_BRICKS.block, Blocks.DIORITE, Blocks.POLISHED_DIORITE, BlockusBlocks.HERRINGBONE_DIORITE_BRICKS, BlockusBlocks.DIORITE_CIRCULAR_PAVING);
    putMultipleWithMid(builder, BlockusBlocks.GRANITE_BRICKS.block, Blocks.GRANITE, Blocks.POLISHED_GRANITE, BlockusBlocks.HERRINGBONE_GRANITE_BRICKS, BlockusBlocks.GRANITE_CIRCULAR_PAVING);

    // 滴水石
    builder.put(BlockusBlocks.POLISHED_DRIPSTONE.block, Blocks.DRIPSTONE_BLOCK);
    builder.putAll(BlockusBlocks.DRIPSTONE_BRICKS.block, Blocks.DRIPSTONE_BLOCK, BlockusBlocks.POLISHED_DRIPSTONE.block);

    // 凝灰岩
    builder.putAll(BlockusBlocks.TUFF_TILES.block, Blocks.TUFF, Blocks.POLISHED_TUFF, Blocks.TUFF_BRICKS);
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.HERRINGBONE_TUFF_BRICKS, BlockusBlocks.TUFF_CIRCULAR_PAVING), Blocks.TUFF, Blocks.POLISHED_TUFF, Blocks.TUFF_BRICKS);

    // 紫水晶
    builder.put(BlockusBlocks.POLISHED_AMETHYST.block, Blocks.AMETHYST_BLOCK);
    builder.putAll(BlockusBlocks.AMETHYST_BRICKS.block, Blocks.AMETHYST_BLOCK, BlockusBlocks.POLISHED_AMETHYST.block);

    // 深板岩
    builder.putAll(BlockusBlocks.HERRINGBONE_DEEPSLATE_BRICKS, Blocks.COBBLED_DEEPSLATE, Blocks.POLISHED_DEEPSLATE, Blocks.DEEPSLATE_BRICKS);
    builder.putAll(BlockusBlocks.DEEPSLATE_CIRCULAR_PAVING, Blocks.COBBLED_DEEPSLATE, Blocks.POLISHED_DEEPSLATE, Blocks.DEEPSLATE_BRICKS);

    // 幽匿
    builder.put(BlockusBlocks.POLISHED_SCULK.block, Blocks.SCULK);
    builder.putAll(BlockusBlocks.SCULK_BRICKS.block, BlockusBlocks.POLISHED_SCULK.block, Blocks.SCULK);

    // 黑石
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.POLISHED_BLACKSTONE_TILES.block, BlockusBlocks.HERRINGBONE_POLISHED_BLACKSTONE_BRICKS, BlockusBlocks.POLISHED_BLACKSTONE_CIRCULAR_PAVING), Blocks.BLACKSTONE, Blocks.POLISHED_BLACKSTONE, Blocks.POLISHED_BLACKSTONE_BRICKS);

    // 玄武岩
    putMultipleWithMid(builder, BlockusBlocks.POLISHED_BASALT_BRICKS.block, Blocks.BASALT, Blocks.POLISHED_BASALT, BlockusBlocks.HERRINGBONE_POLISHED_BASALT_BRICKS, BlockusBlocks.POLISHED_BASALT_CIRCULAR_PAVING);
    builder.put(BlockusBlocks.CRIMSON_WART_BRICKS.block, Blocks.NETHER_WART_BLOCK);
    builder.put(BlockusBlocks.WARPED_WART_BRICKS.block, Blocks.WARPED_WART_BLOCK);

    // 石灰岩
    builder.put(BlockusBlocks.POLISHED_LIMESTONE.block, BlockusBlocks.LIMESTONE.block);
    putMultipleWithMid(builder, BlockusBlocks.LIMESTONE_BRICKS.block, BlockusBlocks.LIMESTONE.block, BlockusBlocks.POLISHED_LIMESTONE.block, BlockusBlocks.SMALL_LIMESTONE_BRICKS.block, BlockusBlocks.LIMESTONE_TILES.block, BlockusBlocks.LIMESTONE_SQUARES, BlockusBlocks.LIMESTONE_CIRCULAR_PAVING);

    // 大理石
    builder.put(BlockusBlocks.POLISHED_MARBLE.block, BlockusBlocks.MARBLE.block);
    putMultipleWithMid(builder, BlockusBlocks.MARBLE_BRICKS.block, BlockusBlocks.MARBLE.block, BlockusBlocks.POLISHED_MARBLE.block, BlockusBlocks.SMALL_MARBLE_BRICKS.block, BlockusBlocks.MARBLE_TILES.block, BlockusBlocks.MARBLE_SQUARES, BlockusBlocks.MARBLE_CIRCULAR_PAVING);

    // 蓝石
    builder.put(BlockusBlocks.POLISHED_BLUESTONE.block, BlockusBlocks.BLUESTONE.block);
    putMultipleWithMid(builder, BlockusBlocks.BLUESTONE_BRICKS.block, BlockusBlocks.BLUESTONE.block, BlockusBlocks.POLISHED_BLUESTONE.block, BlockusBlocks.SMALL_BLUESTONE_BRICKS.block, BlockusBlocks.BLUESTONE_TILES.block, BlockusBlocks.BLUESTONE_SQUARES, BlockusBlocks.BLUESTONE_CIRCULAR_PAVING);

    // 青绿石
    builder.put(BlockusBlocks.POLISHED_VIRIDITE.block, BlockusBlocks.VIRIDITE.block);
    putMultipleWithMid(builder, BlockusBlocks.VIRIDITE_BRICKS.block, BlockusBlocks.VIRIDITE.block, BlockusBlocks.POLISHED_VIRIDITE.block, BlockusBlocks.SMALL_VIRIDITE_BRICKS.block, BlockusBlocks.VIRIDITE_TILES.block, BlockusBlocks.VIRIDITE_SQUARES, BlockusBlocks.VIRIDITE_CIRCULAR_PAVING);

    // 岩浆砖
    builder.put(BlockusBlocks.MAGMA_BRICKS.block, Blocks.MAGMA_BLOCK);
    builder.putAll(BlockusBlocks.SMALL_MAGMA_BRICKS.block, Blocks.MAGMA_BLOCK, BlockusBlocks.MAGMA_BRICKS.block);

    // 下界岩
    builder.put(BlockusBlocks.POLISHED_NETHERRACK.block, Blocks.NETHERRACK);
    putMultipleWithMid(builder, BlockusBlocks.NETHERRACK_BRICKS.block, Blocks.NETHERRACK, BlockusBlocks.POLISHED_NETHERRACK.block, BlockusBlocks.NETHERRACK_CIRCULAR_PAVING);

    // 下界砖
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.POLISHED_NETHER_BRICKS.block, BlockusBlocks.HERRINGBONE_NETHER_BRICKS), Blocks.NETHER_BRICKS);
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.POLISHED_RED_NETHER_BRICKS.block, BlockusBlocks.HERRINGBONE_RED_NETHER_BRICKS), Blocks.RED_NETHER_BRICKS);
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.POLISHED_CHARRED_NETHER_BRICKS.block, BlockusBlocks.HERRINGBONE_CHARRED_NETHER_BRICKS), BlockusBlocks.CHARRED_NETHER_BRICKS.block);
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.POLISHED_TEAL_NETHER_BRICKS.block, BlockusBlocks.HERRINGBONE_TEAL_NETHER_BRICKS), BlockusBlocks.TEAL_NETHER_BRICKS.block);

    // 黑曜石
    builder.put(BlockusBlocks.OBSIDIAN_BRICKS.block, Blocks.OBSIDIAN);
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.SMALL_OBSIDIAN_BRICKS.block, BlockusBlocks.OBSIDIAN_CIRCULAR_PAVING), Blocks.OBSIDIAN, BlockusBlocks.OBSIDIAN_BRICKS.block);

    // 石英
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.QUARTZ_TILES.block, BlockusBlocks.QUARTZ_CIRCULAR_PAVING), Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_BRICKS);

    // 海晶
    builder.put(BlockusBlocks.PRISMARINE_CIRCULAR_PAVING, Blocks.PRISMARINE_BRICKS);

    // 红砖及各类变种
    builder.put(BlockusBlocks.LARGE_BRICKS.block, Blocks.BRICKS);
    builder.put(BlockusBlocks.HERRINGBONE_BRICKS, Blocks.BRICKS);
    builder.put(BlockusBlocks.HERRINGBONE_SOAKED_BRICKS, BlockusBlocks.SOAKED_BRICKS.block);
    builder.put(BlockusBlocks.HERRINGBONE_SANDY_BRICKS, BlockusBlocks.SANDY_BRICKS.block);
    builder.put(BlockusBlocks.HERRINGBONE_CHARRED_BRICKS, BlockusBlocks.CHARRED_BRICKS.block);

    // 砂岩砖
    builder.put(BlockusBlocks.SANDSTONE_BRICKS.block, Blocks.SANDSTONE);
    builder.putAll(BlockusBlocks.SMALL_SANDSTONE_BRICKS.block, Blocks.SANDSTONE, BlockusBlocks.SANDSTONE_BRICKS.block);

    builder.put(BlockusBlocks.RED_SANDSTONE_BRICKS.block, Blocks.RED_SANDSTONE);
    builder.putAll(BlockusBlocks.SMALL_RED_SANDSTONE_BRICKS.block, Blocks.RED_SANDSTONE, BlockusBlocks.RED_SANDSTONE_BRICKS.block);

    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.SOUL_SANDSTONE_BRICKS.block, BlockusBlocks.CUT_SOUL_SANDSTONE), BlockusBlocks.SOUL_SANDSTONE.block);
    builder.putAll(BlockusBlocks.SMALL_SOUL_SANDSTONE_BRICKS.block, BlockusBlocks.SOUL_SANDSTONE.block, BlockusBlocks.SOUL_SANDSTONE_BRICKS.block);

    // 蜜脾
    builder.put(BlockusBlocks.HONEYCOMB_BRICKS.block, Blocks.HONEYCOMB_BLOCK);

    // 紫珀
    builder.put(BlockusBlocks.POLISHED_PURPUR.block, Blocks.PURPUR_BLOCK);
    putMultipleWithMid(builder, BlockusBlocks.PURPUR_BRICKS.block, Blocks.PURPUR_BLOCK, BlockusBlocks.POLISHED_PURPUR.block, BlockusBlocks.SMALL_PURPUR_BRICKS.block, BlockusBlocks.PURPUR_SQUARES);
    builder.put(BlockusBlocks.POLISHED_PHANTOM_PURPUR.block, BlockusBlocks.PHANTOM_PURPUR_BLOCK.block);
    putMultipleWithMid(builder, BlockusBlocks.PHANTOM_PURPUR_BRICKS.block, BlockusBlocks.PHANTOM_PURPUR_BLOCK.block, BlockusBlocks.POLISHED_PHANTOM_PURPUR.block, BlockusBlocks.SMALL_PHANTOM_PURPUR_BRICKS.block, BlockusBlocks.PHANTOM_PURPUR_SQUARES);

    // 末地石
    builder.put(BlockusBlocks.POLISHED_END_STONE.block, Blocks.END_STONE);
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.SMALL_END_STONE_BRICKS.block, BlockusBlocks.HERRINGBONE_END_STONE_BRICKS), Blocks.END_STONE, BlockusBlocks.POLISHED_END_STONE.block, Blocks.END_STONE_BRICKS);
    // 在 Blockus 中，磨制末地石可合成原版的末地石砖
    builder.put(Blocks.END_STONE_BRICKS, BlockusBlocks.POLISHED_END_STONE.block);

    // 彩虹砖
    builder.put(BlockusBlocks.RAINBOW_BRICKS.block, BlockusBlocks.RAINBOW_BLOCK);

    // 巧克力
    builder.put(BlockusBlocks.CHOCOLATE_BRICKS.block, BlockusBlocks.CHOCOLATE_BLOCK.block);
    builder.putAll(BlockusBlocks.CHOCOLATE_SQUARES, BlockusBlocks.CHOCOLATE_BLOCK.block, BlockusBlocks.CHOCOLATE_BRICKS.block);

    // 瓦片
    builder.put(BlockusBlocks.SHINGLES.block, Blocks.TERRACOTTA);

    // 彩色瓦片
    final Iterator<Block> terracottaIterator = BlockCollections.STAINED_TERRACOTTA.iterator();
    for (var bsswBundle : BlockusBlockCollections.TINTED_SHINGLES) {
      builder.put(bsswBundle.block, terracottaIterator.next());
    }

    // 带釉陶瓦柱
    final Iterator<Block> glazedTerracottaIterator = BlockCollections.GLAZED_TERRACOTTA.iterator();
    for (var block : BlockusBlockCollections.GLAZED_TERRACOTTA_PILLARS) {
      builder.put(block, glazedTerracottaIterator.next());
    }

    // 1.20 新增：矿物方块与矿物砖的转换
    builder.put(BlockusBlocks.IRON_BRICKS.block, Blocks.IRON_BLOCK);
    builder.put(BlockusBlocks.GOLD_BRICKS.block, Blocks.GOLD_BLOCK);
    builder.put(BlockusBlocks.LAPIS_BRICKS.block, Blocks.LAPIS_BLOCK);
    builder.put(BlockusBlocks.REDSTONE_BRICKS.block, Blocks.REDSTONE_BLOCK);
    builder.put(BlockusBlocks.EMERALD_BRICKS.block, Blocks.EMERALD_BLOCK);
    builder.put(BlockusBlocks.DIAMOND_BRICKS.block, Blocks.DIAMOND_BLOCK);
    builder.put(BlockusBlocks.NETHERITE_BRICKS.block, Blocks.NETHERITE_BLOCK);

    // region 铜砖
    final List<Block> unwaxedCopperBricksList = BlockusCopperManager.COPPER_BRICKS.unwaxed();
    final ImmutableList<Block> unwaxedCopperBlockList = CopperManager.COPPER_BLOCKS;
    final ImmutableList<Block> unwaxedCutCopperBlockList = CopperManager.CUT_COPPER_BLOCKS;
    for (int i = 0; i < unwaxedCopperBricksList.size(); i++) {
      final Block unwaxedCopperBricks = unwaxedCopperBricksList.get(i);
      builder.put(unwaxedCopperBricks, unwaxedCopperBlockList.get(i));
      builder.put(unwaxedCopperBricks, unwaxedCutCopperBlockList.get(i));
    }

    final List<Block> waxedCopperBricksList = BlockusCopperManager.COPPER_BRICKS.waxed();
    final ImmutableList<Block> waxedCopperBlockList = CopperManager.WAXED_COPPER_BLOCKS;
    final ImmutableList<Block> waxedCutCopperBlockList = CopperManager.WAXED_CUT_COPPER_BLOCKS;
    for (int i = 0; i < waxedCopperBricksList.size(); i++) {
      final Block waxedCopperBricks = waxedCopperBricksList.get(i);
      builder.put(waxedCopperBricks, waxedCopperBlockList.get(i));
      builder.put(waxedCopperBricks, waxedCutCopperBlockList.get(i));
    }
    // endregion 铜砖
  }

  private static void putMultipleWithMid(ImmutableMultimap.Builder<Block, Block> builder, Block midOutput, Block ingredient1, Block ingredient2, Block... outputs) {
    if (outputs.length == 0) {
      throw new IllegalArgumentException("Parameter 'outputs' must contain at least one element");
    }

    builder.putAll(midOutput, ingredient1, ingredient2);
    for (Block output : outputs) {
      builder.putAll(output, ingredient1, ingredient2, midOutput);
    }
  }

  private static void putMultipleOutputs(ImmutableMultimap.Builder<Block, Block> builder, Iterable<Block> outputs, Block ingredient) {
    for (Block output : outputs) {
      builder.put(output, ingredient);
    }
  }

  private static void putMultipleOutputs(ImmutableMultimap.Builder<Block, Block> builder, Iterable<Block> outputs, Block... ingredients) {
    for (Block output : outputs) {
      builder.putAll(output, ingredients);
    }
  }
}
