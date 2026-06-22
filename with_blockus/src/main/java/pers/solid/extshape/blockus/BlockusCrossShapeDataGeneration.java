package pers.solid.extshape.blockus;

import com.brand.blockus.datagen.providers.BlockusRecipeProvider;
import com.brand.blockus.registry.content.BlockusBlocks;
import com.brand.blockus.registry.content.bundles.ConcreteBundle;
import com.brand.blockus.utils.helper.BlockMaps;
import com.brand.blockus.utils.helper.BlockOrder;
import com.google.common.collect.ImmutableMultimap;
import it.unimi.dsi.fastutil.objects.ObjectIntPair;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.registry.Registries;
import net.minecraft.util.DyeColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.data.CrossShapeDataGeneration;
import pers.solid.extshape.data.VanillaStonecutting;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @see VanillaStonecutting
 */
public class BlockusCrossShapeDataGeneration extends CrossShapeDataGeneration {
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
        throw new IllegalStateException("These blocks exist in the results of BlockusCrossShapeDataGeneration.INSTANCE, but are not base blocks: " + unrecognizedBaseBlocks.stream().map(Registries.BLOCK::getKey).map(Objects::toString).collect(Collectors.joining(", ")));
      }
    }
  }

  public BlockusCrossShapeDataGeneration(Block baseBlock, @Nullable String defaultNamespace, RecipeGenerator recipeGenerator, @NotNull RecipeExporter exporter) {
    super(baseBlock, defaultNamespace, recipeGenerator, exporter);
  }

  @Override
  protected @NotNull Iterable<ObjectIntPair<Block>> getUncutBaseBlocks() {
    return INSTANCE.get(baseBlock).stream().map(block -> ObjectIntPair.of(block, 1)).collect(Collectors.toList());
  }

  /**
   * @see BlockusRecipeProvider#getRecipeGenerator
   */
  private static void registerBlockusStonecutting(ImmutableMultimap.Builder<Block, Block> builder) {
    for (ConcreteBundle concreteBundle : ConcreteBundle.values()) {
      final Map<DyeColor, ConcreteBundle.ConcreteVariants> concreteBundleMap = concreteBundle.colorMap();
      for (DyeColor dyeColor : BlockOrder.COLOR) {
        final ConcreteBundle.ConcreteVariants concreteVariants = concreteBundleMap.get(dyeColor);
        if (concreteVariants == null) {
          continue;
        }
        Block base = BlockMaps.CONCRETE_MAP.get(dyeColor);
        builder.put(concreteVariants.block(), base);
      }
    }
    builder.putAll(BlockusBlocks.STONE_TILES.block(), Blocks.STONE, Blocks.STONE_BRICKS);
    builder.put(BlockusBlocks.HERRINGBONE_STONE_BRICKS, Blocks.STONE);
    builder.put(BlockusBlocks.HERRINGBONE_STONE_BRICKS, Blocks.STONE_BRICKS);
    putMultipleWithMid(builder, BlockusBlocks.ANDESITE_BRICKS.block(), Blocks.ANDESITE, Blocks.POLISHED_ANDESITE, BlockusBlocks.HERRINGBONE_ANDESITE_BRICKS, BlockusBlocks.ANDESITE_CIRCULAR_PAVING);
    putMultipleWithMid(builder, BlockusBlocks.DIORITE_BRICKS.block(), Blocks.DIORITE, Blocks.POLISHED_DIORITE, BlockusBlocks.HERRINGBONE_DIORITE_BRICKS, BlockusBlocks.DIORITE_CIRCULAR_PAVING);
    putMultipleWithMid(builder, BlockusBlocks.GRANITE_BRICKS.block(), Blocks.GRANITE, Blocks.POLISHED_GRANITE, BlockusBlocks.HERRINGBONE_GRANITE_BRICKS, BlockusBlocks.GRANITE_CIRCULAR_PAVING);

    builder.put(BlockusBlocks.POLISHED_DRIPSTONE.block(), Blocks.DRIPSTONE_BLOCK);
    builder.putAll(BlockusBlocks.DRIPSTONE_BRICKS.block(), Blocks.DRIPSTONE_BLOCK, BlockusBlocks.POLISHED_DRIPSTONE.block());

    builder.putAll(BlockusBlocks.TUFF_TILES.block(), Blocks.TUFF, Blocks.POLISHED_TUFF, Blocks.TUFF_BRICKS);
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.HERRINGBONE_TUFF_BRICKS, BlockusBlocks.TUFF_CIRCULAR_PAVING), Blocks.TUFF, Blocks.POLISHED_TUFF, Blocks.TUFF_BRICKS);

    builder.put(BlockusBlocks.POLISHED_AMETHYST.block(), Blocks.AMETHYST_BLOCK);
    builder.putAll(BlockusBlocks.AMETHYST_BRICKS.block(), Blocks.AMETHYST_BLOCK, BlockusBlocks.POLISHED_AMETHYST.block());

    builder.putAll(BlockusBlocks.HERRINGBONE_DEEPSLATE_BRICKS, Blocks.COBBLED_DEEPSLATE, Blocks.POLISHED_DEEPSLATE, Blocks.DEEPSLATE_BRICKS);
    builder.putAll(BlockusBlocks.DEEPSLATE_CIRCULAR_PAVING, Blocks.COBBLED_DEEPSLATE, Blocks.POLISHED_DEEPSLATE, Blocks.DEEPSLATE_BRICKS);

    builder.put(BlockusBlocks.POLISHED_SCULK.block(), Blocks.SCULK);
    builder.putAll(BlockusBlocks.SCULK_BRICKS.block(), BlockusBlocks.POLISHED_SCULK.block(), Blocks.SCULK);

    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.POLISHED_BLACKSTONE_TILES.block(), BlockusBlocks.HERRINGBONE_POLISHED_BLACKSTONE_BRICKS, BlockusBlocks.POLISHED_BLACKSTONE_CIRCULAR_PAVING), Blocks.BLACKSTONE, Blocks.POLISHED_BLACKSTONE, Blocks.POLISHED_BLACKSTONE_BRICKS);

    putMultipleWithMid(builder, BlockusBlocks.POLISHED_BASALT_BRICKS.block(), Blocks.BASALT, Blocks.POLISHED_BASALT, BlockusBlocks.HERRINGBONE_POLISHED_BASALT_BRICKS, BlockusBlocks.POLISHED_BASALT_CIRCULAR_PAVING);
    builder.put(BlockusBlocks.CRIMSON_WART_BRICKS.block(), Blocks.NETHER_WART_BLOCK);
    builder.put(BlockusBlocks.WARPED_WART_BRICKS.block(), Blocks.WARPED_WART_BLOCK);

    builder.put(BlockusBlocks.POLISHED_LIMESTONE.block(), BlockusBlocks.LIMESTONE.block());
    putMultipleWithMid(builder, BlockusBlocks.LIMESTONE_BRICKS.block(), BlockusBlocks.LIMESTONE.block(), BlockusBlocks.POLISHED_LIMESTONE.block(), BlockusBlocks.SMALL_LIMESTONE_BRICKS.block(), BlockusBlocks.LIMESTONE_TILES.block(), BlockusBlocks.LIMESTONE_SQUARES, BlockusBlocks.LIMESTONE_CIRCULAR_PAVING);

    builder.put(BlockusBlocks.POLISHED_MARBLE.block(), BlockusBlocks.MARBLE.block());
    putMultipleWithMid(builder, BlockusBlocks.MARBLE_BRICKS.block(), BlockusBlocks.MARBLE.block(), BlockusBlocks.POLISHED_MARBLE.block(), BlockusBlocks.SMALL_MARBLE_BRICKS.block(), BlockusBlocks.MARBLE_TILES.block(), BlockusBlocks.MARBLE_SQUARES, BlockusBlocks.MARBLE_CIRCULAR_PAVING);

    builder.put(BlockusBlocks.POLISHED_BLUESTONE.block(), BlockusBlocks.BLUESTONE.block());
    putMultipleWithMid(builder, BlockusBlocks.BLUESTONE_BRICKS.block(), BlockusBlocks.BLUESTONE.block(), BlockusBlocks.POLISHED_BLUESTONE.block(), BlockusBlocks.SMALL_BLUESTONE_BRICKS.block(), BlockusBlocks.BLUESTONE_TILES.block(), BlockusBlocks.BLUESTONE_SQUARES, BlockusBlocks.BLUESTONE_CIRCULAR_PAVING);

    builder.put(BlockusBlocks.POLISHED_VIRIDITE.block(), BlockusBlocks.VIRIDITE.block());
    putMultipleWithMid(builder, BlockusBlocks.VIRIDITE_BRICKS.block(), BlockusBlocks.VIRIDITE.block(), BlockusBlocks.POLISHED_VIRIDITE.block(), BlockusBlocks.SMALL_VIRIDITE_BRICKS.block(), BlockusBlocks.VIRIDITE_TILES.block(), BlockusBlocks.VIRIDITE_SQUARES, BlockusBlocks.VIRIDITE_CIRCULAR_PAVING);

    builder.put(BlockusBlocks.MAGMA_BRICKS.block(), Blocks.MAGMA_BLOCK);
    builder.putAll(BlockusBlocks.SMALL_MAGMA_BRICKS.block(), Blocks.MAGMA_BLOCK, BlockusBlocks.MAGMA_BRICKS.block());

    builder.put(BlockusBlocks.POLISHED_NETHERRACK.block(), Blocks.NETHERRACK);
    putMultipleWithMid(builder, BlockusBlocks.NETHERRACK_BRICKS.block(), Blocks.NETHERRACK, BlockusBlocks.POLISHED_NETHERRACK.block(), BlockusBlocks.NETHERRACK_CIRCULAR_PAVING);

    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.POLISHED_NETHER_BRICKS.block(), BlockusBlocks.HERRINGBONE_NETHER_BRICKS), Blocks.NETHER_BRICKS);
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.POLISHED_RED_NETHER_BRICKS.block(), BlockusBlocks.HERRINGBONE_RED_NETHER_BRICKS), Blocks.RED_NETHER_BRICKS);
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.POLISHED_CHARRED_NETHER_BRICKS.block(), BlockusBlocks.HERRINGBONE_CHARRED_NETHER_BRICKS), BlockusBlocks.CHARRED_NETHER_BRICKS.block());
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.POLISHED_TEAL_NETHER_BRICKS.block(), BlockusBlocks.HERRINGBONE_TEAL_NETHER_BRICKS), BlockusBlocks.TEAL_NETHER_BRICKS.block());

    builder.put(BlockusBlocks.OBSIDIAN_BRICKS.block(), Blocks.OBSIDIAN);
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.SMALL_OBSIDIAN_BRICKS.block(), BlockusBlocks.OBSIDIAN_CIRCULAR_PAVING), Blocks.OBSIDIAN, BlockusBlocks.OBSIDIAN_BRICKS.block());

    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.QUARTZ_TILES.block(), BlockusBlocks.QUARTZ_CIRCULAR_PAVING), Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_BRICKS);
    builder.put(BlockusBlocks.PRISMARINE_CIRCULAR_PAVING, Blocks.PRISMARINE_BRICKS);
    builder.put(BlockusBlocks.LARGE_BRICKS.block(), Blocks.BRICKS);
    builder.put(BlockusBlocks.HERRINGBONE_BRICKS, Blocks.BRICKS);
    builder.put(BlockusBlocks.HERRINGBONE_SOAKED_BRICKS, BlockusBlocks.SOAKED_BRICKS.block());
    builder.put(BlockusBlocks.HERRINGBONE_SANDY_BRICKS, BlockusBlocks.SANDY_BRICKS.block());
    builder.put(BlockusBlocks.HERRINGBONE_CHARRED_BRICKS, BlockusBlocks.CHARRED_BRICKS.block());


    builder.put(BlockusBlocks.LARGE_RESIN_BRICKS.block(), Blocks.RESIN_BRICKS);
    builder.put(BlockusBlocks.HERRINGBONE_RESIN_BRICKS, Blocks.RESIN_BRICKS);

    builder.put(BlockusBlocks.SANDSTONE_BRICKS.block(), Blocks.SANDSTONE);
    builder.putAll(BlockusBlocks.SMALL_SANDSTONE_BRICKS.block(), Blocks.SANDSTONE, BlockusBlocks.SANDSTONE_BRICKS.block());

    builder.put(BlockusBlocks.RED_SANDSTONE_BRICKS.block(), Blocks.RED_SANDSTONE);
    builder.putAll(BlockusBlocks.SMALL_RED_SANDSTONE_BRICKS.block(), Blocks.RED_SANDSTONE, BlockusBlocks.RED_SANDSTONE_BRICKS.block());

    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.SOUL_SANDSTONE_BRICKS.block(), BlockusBlocks.CUT_SOUL_SANDSTONE), BlockusBlocks.SOUL_SANDSTONE.block());
    builder.putAll(BlockusBlocks.SMALL_SOUL_SANDSTONE_BRICKS.block(), BlockusBlocks.SOUL_SANDSTONE.block(), BlockusBlocks.SOUL_SANDSTONE_BRICKS.block());

    builder.put(BlockusBlocks.HONEYCOMB_BRICKS.block(), Blocks.HONEYCOMB_BLOCK);
    builder.put(BlockusBlocks.POLISHED_PURPUR.block(), Blocks.PURPUR_BLOCK);
    putMultipleWithMid(builder, BlockusBlocks.PURPUR_BRICKS.block(), Blocks.PURPUR_BLOCK, BlockusBlocks.POLISHED_PURPUR.block(), BlockusBlocks.SMALL_PURPUR_BRICKS.block(), BlockusBlocks.PURPUR_SQUARES);
    builder.put(BlockusBlocks.POLISHED_PHANTOM_PURPUR.block(), BlockusBlocks.PHANTOM_PURPUR_BLOCK.block());
    putMultipleWithMid(builder, BlockusBlocks.PHANTOM_PURPUR_BRICKS.block(), BlockusBlocks.PHANTOM_PURPUR_BLOCK.block(), BlockusBlocks.POLISHED_PHANTOM_PURPUR.block(), BlockusBlocks.SMALL_PHANTOM_PURPUR_BRICKS.block(), BlockusBlocks.PHANTOM_PURPUR_SQUARES);

    builder.put(BlockusBlocks.POLISHED_END_STONE.block(), Blocks.END_STONE);
    putMultipleOutputs(builder, Arrays.asList(BlockusBlocks.SMALL_END_STONE_BRICKS.block(), BlockusBlocks.HERRINGBONE_END_STONE_BRICKS), Blocks.END_STONE, BlockusBlocks.POLISHED_END_STONE.block(), Blocks.END_STONE_BRICKS);
    // 在 Blockus 中，磨制末地石可合成原版的末地石砖
    builder.put(Blocks.END_STONE_BRICKS, BlockusBlocks.POLISHED_END_STONE.block());

    builder.put(BlockusBlocks.RAINBOW_BRICKS.block(), BlockusBlocks.RAINBOW_BLOCK);
    builder.put(BlockusBlocks.CHOCOLATE_BRICKS.block(), BlockusBlocks.CHOCOLATE_BLOCK.block());
    builder.put(BlockusBlocks.CHOCOLATE_SQUARES, BlockusBlocks.CHOCOLATE_BLOCK.block());

    // shingles and terracotta
    builder.put(BlockusBlocks.SHINGLES.block(), Blocks.TERRACOTTA);
    BlockusBlocks.STAINED_SHINGLES.colorMap().forEach((dyeColor, bsswBundle) -> builder.put(bsswBundle.base(), BlockMaps.TERRACOTTA_MAP.get(dyeColor)));

    // glazed terracotta
    BlockusBlocks.GLAZED_TERRACOTTA_PILLAR.colorMap().forEach((dyeColor, block) -> builder.put(block, BlockMaps.GLAZED_TERRACOTTA_MAP.get(dyeColor)));

    // 1.20 新增：矿物方块与矿物砖的转换
    builder.put(BlockusBlocks.IRON_BRICKS.block(), Blocks.IRON_BLOCK);
    builder.put(BlockusBlocks.GOLD_BRICKS.block(), Blocks.GOLD_BLOCK);
    builder.put(BlockusBlocks.LAPIS_BRICKS.block(), Blocks.LAPIS_BLOCK);
    builder.put(BlockusBlocks.REDSTONE_BRICKS.block(), Blocks.REDSTONE_BLOCK);
    builder.put(BlockusBlocks.EMERALD_BRICKS.block(), Blocks.EMERALD_BLOCK);
    builder.put(BlockusBlocks.DIAMOND_BRICKS.block(), Blocks.DIAMOND_BLOCK);
    builder.put(BlockusBlocks.NETHERITE_BRICKS.block(), Blocks.NETHERITE_BLOCK);
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
