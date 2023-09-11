package pers.solid.extshape.blockus;

import com.brand.blockus.content.BlockusBlocks;
import com.brand.blockus.content.types.ConcreteTypes;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMultimap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import pers.solid.brrp.v1.api.RuntimeResourcePack;
import pers.solid.extshape.rrp.CrossShapeDataGeneration;
import pers.solid.extshape.util.BlockCollections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * @see pers.solid.extshape.rrp.VanillaStonecutting
 */
public class BlockusCrossShapeDataGeneration extends CrossShapeDataGeneration {
  public static final @Unmodifiable ImmutableMultimap<Block, Block> INSTANCE;


  static {
    final ImmutableMultimap.Builder<Block, Block> builder = new ImmutableMultimap.Builder<>();
    registerBlockusStonecutting(builder);
    INSTANCE = builder.build();
  }

  public BlockusCrossShapeDataGeneration(Block baseBlock, @Nullable String defaultNamespace, RuntimeResourcePack pack) {
    super(baseBlock, defaultNamespace, pack);
  }

  @Override
  protected @NotNull ImmutableCollection<Block> getUncutBaseBlocks() {
    return INSTANCE.get(baseBlock);
  }

  /**
   * @see com.brand.blockus.data.providers.BlockusRecipeProvider#generate(Consumer)
   */
  private static void registerBlockusStonecutting(ImmutableMultimap.Builder<Block, Block> builder) {
    for (ConcreteTypes concreteType : ConcreteTypes.values()) {
      builder.put(concreteType.block, concreteType.base);
      builder.put(concreteType.chiseled, concreteType.base);
      builder.put(concreteType.pillar, concreteType.base);
    }
    builder.put(BlockusBlocks.MUD_BRICK_PILLAR, Blocks.MUD_BRICKS);
    builder.put(BlockusBlocks.CHISELED_MUD_BRICKS, Blocks.MUD_BRICKS);
    builder.put(BlockusBlocks.STONE_TILES.block, Blocks.STONE_BRICKS);
    builder.put(BlockusBlocks.STONE_BRICK_PILLAR, Blocks.STONE);
    builder.put(BlockusBlocks.STONE_BRICK_PILLAR, Blocks.STONE_BRICKS);
    builder.put(BlockusBlocks.HERRINGBONE_STONE_BRICKS, Blocks.STONE);
    builder.put(BlockusBlocks.HERRINGBONE_STONE_BRICKS, Blocks.STONE_BRICKS);
    putMultipleWithMid(builder, BlockusBlocks.ANDESITE_BRICKS.block, Blocks.ANDESITE, Blocks.POLISHED_ANDESITE, BlockusBlocks.CHISELED_ANDESITE_BRICKS, BlockusBlocks.HERRINGBONE_ANDESITE_BRICKS, BlockusBlocks.ANDESITE_CIRCULAR_PAVING);
    putMultipleWithMid(builder, BlockusBlocks.DIORITE_BRICKS.block, Blocks.DIORITE, Blocks.POLISHED_DIORITE, BlockusBlocks.CHISELED_DIORITE_BRICKS, BlockusBlocks.HERRINGBONE_DIORITE_BRICKS, BlockusBlocks.DIORITE_CIRCULAR_PAVING);
    putMultipleWithMid(builder, BlockusBlocks.GRANITE_BRICKS.block, Blocks.GRANITE, Blocks.POLISHED_GRANITE, BlockusBlocks.CHISELED_GRANITE_BRICKS, BlockusBlocks.HERRINGBONE_GRANITE_BRICKS, BlockusBlocks.GRANITE_CIRCULAR_PAVING);

    builder.put(BlockusBlocks.POLISHED_DRIPSTONE.block, Blocks.DRIPSTONE_BLOCK);
    putMultipleWithMid(builder, BlockusBlocks.DRIPSTONE_BRICKS.block, Blocks.DRIPSTONE_BLOCK, BlockusBlocks.POLISHED_DRIPSTONE.block, BlockusBlocks.CHISELED_DRIPSTONE, BlockusBlocks.DRIPSTONE_PILLAR);

    builder.put(BlockusBlocks.POLISHED_TUFF.block, Blocks.TUFF);
    putMultipleWithMid(builder, BlockusBlocks.TUFF_BRICKS.block, Blocks.TUFF, BlockusBlocks.POLISHED_TUFF.block, BlockusBlocks.CHISELED_TUFF, BlockusBlocks.TUFF_PILLAR, BlockusBlocks.HERRINGBONE_TUFF_BRICKS, BlockusBlocks.TUFF_CIRCULAR_PAVING);

    builder.put(BlockusBlocks.POLISHED_AMETHYST.block, Blocks.AMETHYST_BLOCK);
    putMultipleWithMid(builder, BlockusBlocks.AMETHYST_BRICKS.block, Blocks.AMETHYST_BLOCK, BlockusBlocks.POLISHED_AMETHYST.block, BlockusBlocks.CHISELED_AMETHYST, BlockusBlocks.AMETHYST_PILLAR);

    builder.putAll(BlockusBlocks.DEEPSLATE_PILLAR, Blocks.COBBLED_DEEPSLATE, Blocks.POLISHED_DEEPSLATE, Blocks.DEEPSLATE_BRICKS);
    builder.putAll(BlockusBlocks.HERRINGBONE_DEEPSLATE_BRICKS, Blocks.COBBLED_DEEPSLATE, Blocks.POLISHED_DEEPSLATE, Blocks.DEEPSLATE_BRICKS);
    builder.putAll(BlockusBlocks.DEEPSLATE_CIRCULAR_PAVING, Blocks.COBBLED_DEEPSLATE, Blocks.POLISHED_DEEPSLATE, Blocks.DEEPSLATE_BRICKS);

    builder.put(BlockusBlocks.POLISHED_SCULK.block, Blocks.SCULK);
    putMultipleWithMid(builder, BlockusBlocks.SCULK_BRICKS.block, BlockusBlocks.POLISHED_SCULK.block, Blocks.SCULK, BlockusBlocks.CHISELED_SCULK_BRICKS, BlockusBlocks.SCULK_PILLAR);

    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.POLISHED_BLACKSTONE_TILES.block, BlockusBlocks.POLISHED_BLACKSTONE_PILLAR, BlockusBlocks.HERRINGBONE_POLISHED_BLACKSTONE_BRICKS, BlockusBlocks.POLISHED_BLACKSTONE_CIRCULAR_PAVING), Blocks.BLACKSTONE, Blocks.POLISHED_BLACKSTONE, Blocks.POLISHED_BLACKSTONE_BRICKS);

    putMultipleWithMid(builder, BlockusBlocks.POLISHED_BASALT_BRICKS.block, Blocks.BASALT, Blocks.POLISHED_BASALT, BlockusBlocks.CHISELED_POLISHED_BASALT, BlockusBlocks.POLISHED_BASALT_PILLAR, BlockusBlocks.HERRINGBONE_POLISHED_BASALT_BRICKS, BlockusBlocks.POLISHED_BASALT_CIRCULAR_PAVING);
    builder.put(BlockusBlocks.CRIMSON_WART_BRICKS.block, Blocks.NETHER_WART_BLOCK);
    builder.put(BlockusBlocks.WARPED_WART_BRICKS.block, Blocks.WARPED_WART_BLOCK);

    builder.put(BlockusBlocks.POLISHED_LIMESTONE.block, BlockusBlocks.LIMESTONE.block);
    putMultipleWithMid(builder, BlockusBlocks.LIMESTONE_BRICKS.block, BlockusBlocks.LIMESTONE.block, BlockusBlocks.POLISHED_LIMESTONE.block, BlockusBlocks.SMALL_LIMESTONE_BRICKS.block, BlockusBlocks.LIMESTONE_TILES.block, BlockusBlocks.CHISELED_LIMESTONE, BlockusBlocks.CHISELED_LIMESTONE_PILLAR, BlockusBlocks.LIMESTONE_PILLAR, BlockusBlocks.LIMESTONE_SQUARES, BlockusBlocks.LIMESTONE_CIRCULAR_PAVING, BlockusBlocks.LIMESTONE_LINES);
    builder.put(BlockusBlocks.CHISELED_LIMESTONE_PILLAR, BlockusBlocks.LIMESTONE_PILLAR);

    builder.put(BlockusBlocks.POLISHED_MARBLE.block, BlockusBlocks.MARBLE.block);
    putMultipleWithMid(builder, BlockusBlocks.MARBLE_BRICKS.block, BlockusBlocks.MARBLE.block, BlockusBlocks.POLISHED_MARBLE.block, BlockusBlocks.SMALL_MARBLE_BRICKS.block, BlockusBlocks.MARBLE_TILES.block, BlockusBlocks.CHISELED_MARBLE, BlockusBlocks.CHISELED_MARBLE_PILLAR, BlockusBlocks.MARBLE_PILLAR, BlockusBlocks.MARBLE_SQUARES, BlockusBlocks.MARBLE_CIRCULAR_PAVING, BlockusBlocks.MARBLE_LINES);
    builder.put(BlockusBlocks.CHISELED_MARBLE_PILLAR, BlockusBlocks.MARBLE_PILLAR);

    builder.put(BlockusBlocks.POLISHED_BLUESTONE.block, BlockusBlocks.BLUESTONE.block);
    putMultipleWithMid(builder, BlockusBlocks.BLUESTONE_BRICKS.block, BlockusBlocks.BLUESTONE.block, BlockusBlocks.POLISHED_BLUESTONE.block, BlockusBlocks.SMALL_BLUESTONE_BRICKS.block, BlockusBlocks.BLUESTONE_TILES.block, BlockusBlocks.CHISELED_BLUESTONE, BlockusBlocks.BLUESTONE_PILLAR, BlockusBlocks.CHISELED_BLUESTONE_PILLAR, BlockusBlocks.BLUESTONE_SQUARES, BlockusBlocks.BLUESTONE_CIRCULAR_PAVING, BlockusBlocks.BLUESTONE_LINES);
    builder.put(BlockusBlocks.CHISELED_BLUESTONE_PILLAR, BlockusBlocks.BLUESTONE_PILLAR);

    builder.put(BlockusBlocks.POLISHED_VIRIDITE.block, BlockusBlocks.VIRIDITE.block);
    putMultipleWithMid(builder, BlockusBlocks.VIRIDITE_BRICKS.block, BlockusBlocks.VIRIDITE.block, BlockusBlocks.POLISHED_VIRIDITE.block, BlockusBlocks.SMALL_VIRIDITE_BRICKS.block, BlockusBlocks.VIRIDITE_TILES.block, BlockusBlocks.CHISELED_VIRIDITE, BlockusBlocks.VIRIDITE_PILLAR, BlockusBlocks.CHISELED_VIRIDITE_PILLAR, BlockusBlocks.VIRIDITE_SQUARES, BlockusBlocks.VIRIDITE_CIRCULAR_PAVING, BlockusBlocks.VIRIDITE_LINES);
    builder.put(BlockusBlocks.CHISELED_VIRIDITE_PILLAR, BlockusBlocks.VIRIDITE_PILLAR);

    builder.put(BlockusBlocks.CHISELED_LAVA_BRICKS, BlockusBlocks.LAVA_BRICKS.block);
    builder.put(BlockusBlocks.CHISELED_WATER_BRICKS, BlockusBlocks.WATER_BRICKS.block);
    builder.put(BlockusBlocks.SNOW_PILLAR, BlockusBlocks.SNOW_BRICKS.block);

    builder.put(BlockusBlocks.MAGMA_BRICKS.block, Blocks.MAGMA_BLOCK);
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.SMALL_MAGMA_BRICKS.block, BlockusBlocks.CHISELED_MAGMA_BRICKS), Blocks.MAGMA_BLOCK, BlockusBlocks.MAGMA_BRICKS.block);

    builder.put(BlockusBlocks.BLAZE_PILLAR, BlockusBlocks.BLAZE_BRICKS.block);
    builder.put(BlockusBlocks.POLISHED_NETHERRACK.block, Blocks.NETHERRACK);
    putMultipleWithMid(builder, BlockusBlocks.NETHERRACK_BRICKS.block, Blocks.NETHERRACK, BlockusBlocks.POLISHED_NETHERRACK.block, BlockusBlocks.NETHERRACK_CIRCULAR_PAVING);

    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.POLISHED_NETHER_BRICKS, BlockusBlocks.NETHER_BRICK_PILLAR, BlockusBlocks.HERRINGBONE_NETHER_BRICKS), Blocks.NETHER_BRICKS);
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.POLISHED_RED_NETHER_BRICKS, BlockusBlocks.RED_NETHER_BRICK_PILLAR, BlockusBlocks.HERRINGBONE_RED_NETHER_BRICKS), Blocks.RED_NETHER_BRICKS);
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.POLISHED_CHARRED_NETHER_BRICKS, BlockusBlocks.CHARRED_NETHER_BRICK_PILLAR, BlockusBlocks.HERRINGBONE_CHARRED_NETHER_BRICKS), BlockusBlocks.CHARRED_NETHER_BRICKS.block);
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.POLISHED_TEAL_NETHER_BRICKS, BlockusBlocks.TEAL_NETHER_BRICK_PILLAR, BlockusBlocks.HERRINGBONE_TEAL_NETHER_BRICKS), BlockusBlocks.TEAL_NETHER_BRICKS.block);

    builder.put(BlockusBlocks.OBSIDIAN_BRICKS.block, Blocks.OBSIDIAN);
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.SMALL_OBSIDIAN_BRICKS.block, BlockusBlocks.OBSIDIAN_PILLAR, BlockusBlocks.OBSIDIAN_CIRCULAR_PAVING), Blocks.OBSIDIAN, BlockusBlocks.OBSIDIAN_BRICKS.block);

    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.QUARTZ_TILES.block, BlockusBlocks.QUARTZ_CIRCULAR_PAVING), Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_BRICKS);
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.CHISELED_PRISMARINE, BlockusBlocks.PRISMARINE_PILLAR, BlockusBlocks.PRISMARINE_CIRCULAR_PAVING), Blocks.PRISMARINE_BRICKS);
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.CHISELED_DARK_PRISMARINE, BlockusBlocks.DARK_PRISMARINE_PILLAR), Blocks.DARK_PRISMARINE);
    builder.put(BlockusBlocks.LARGE_BRICKS.block, Blocks.BRICKS);
    builder.put(BlockusBlocks.HERRINGBONE_BRICKS, Blocks.BRICKS);
    builder.put(BlockusBlocks.HERRINGBONE_SOAKED_BRICKS, BlockusBlocks.SOAKED_BRICKS.block);
    builder.put(BlockusBlocks.HERRINGBONE_SANDY_BRICKS, BlockusBlocks.SANDY_BRICKS.block);
    builder.put(BlockusBlocks.HERRINGBONE_CHARRED_BRICKS, BlockusBlocks.CHARRED_BRICKS.block);

    builder.put(BlockusBlocks.SANDSTONE_BRICKS.block, Blocks.SANDSTONE);
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.SMALL_SANDSTONE_BRICKS.block, BlockusBlocks.SANDSTONE_PILLAR), Blocks.SANDSTONE, BlockusBlocks.SANDSTONE_BRICKS.block);

    builder.put(BlockusBlocks.RED_SANDSTONE_BRICKS.block, Blocks.RED_SANDSTONE);
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.SMALL_RED_SANDSTONE_BRICKS.block, BlockusBlocks.RED_SANDSTONE_PILLAR), Blocks.RED_SANDSTONE, BlockusBlocks.RED_SANDSTONE_BRICKS.block);

    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.SOUL_SANDSTONE_BRICKS.block, BlockusBlocks.CUT_SOUL_SANDSTONE, BlockusBlocks.CHISELED_SOUL_SANDSTONE), BlockusBlocks.SOUL_SANDSTONE.block);
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.SMALL_SOUL_SANDSTONE_BRICKS.block, BlockusBlocks.SOUL_SANDSTONE_PILLAR), BlockusBlocks.SOUL_SANDSTONE.block, BlockusBlocks.SOUL_SANDSTONE_BRICKS.block);

    builder.put(BlockusBlocks.HONEYCOMB_BRICKS.block, Blocks.HONEYCOMB_BLOCK);
    builder.put(BlockusBlocks.POLISHED_PURPUR.block, Blocks.PURPUR_BLOCK);
    putMultipleWithMid(builder, BlockusBlocks.PURPUR_BRICKS.block, Blocks.PURPUR_BLOCK, BlockusBlocks.POLISHED_PURPUR.block, BlockusBlocks.SMALL_PURPUR_BRICKS.block, BlockusBlocks.CHISELED_PURPUR, Blocks.PURPUR_PILLAR, BlockusBlocks.PURPUR_SQUARES, BlockusBlocks.PURPUR_LINES);
    builder.put(BlockusBlocks.POLISHED_PHANTOM_PURPUR.block, BlockusBlocks.PHANTOM_PURPUR_BLOCK.block);
    putMultipleWithMid(builder, BlockusBlocks.PHANTOM_PURPUR_BRICKS.block, BlockusBlocks.PHANTOM_PURPUR_BLOCK.block, BlockusBlocks.POLISHED_PHANTOM_PURPUR.block, BlockusBlocks.SMALL_PHANTOM_PURPUR_BRICKS.block, BlockusBlocks.CHISELED_PHANTOM_PURPUR, BlockusBlocks.PHANTOM_PURPUR_PILLAR, BlockusBlocks.PHANTOM_PURPUR_SQUARES, BlockusBlocks.PHANTOM_PURPUR_LINES);

    builder.put(BlockusBlocks.POLISHED_END_STONE.block, Blocks.END_STONE);
    putMultipleWithMid(builder, Blocks.END_STONE_BRICKS, Blocks.END_STONE, BlockusBlocks.POLISHED_END_STONE.block, BlockusBlocks.SMALL_END_STONE_BRICKS.block, BlockusBlocks.CHISELED_END_STONE_BRICKS, BlockusBlocks.END_STONE_PILLAR, BlockusBlocks.HERRINGBONE_END_STONE_BRICKS);

    builder.put(BlockusBlocks.RAINBOW_BRICKS.block, BlockusBlocks.RAINBOW_BLOCK);
    builder.put(BlockusBlocks.CHOCOLATE_BRICKS.block, BlockusBlocks.CHOCOLATE_BLOCK.block);
    builder.put(BlockusBlocks.CHOCOLATE_SQUARES, BlockusBlocks.CHOCOLATE_BLOCK.block);

    // shingles and terracotta
    builder.put(BlockusBlocks.SHINGLES.block, Blocks.TERRACOTTA);
    final Iterator<Block> terracottaIterator = BlockCollections.STAINED_TERRACOTTA.iterator();
    for (var bssTypes : BlockusBlockCollections.TINTED_SHINGLES) {
      builder.put(bssTypes.block, terracottaIterator.next());
    }

    // glazed terracotta
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
  }

  private static void putMultipleWithMid(ImmutableMultimap.Builder<Block, Block> builder, Block midOutput, Block ingredient1, Block ingredient2, Block... outputs) {
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
