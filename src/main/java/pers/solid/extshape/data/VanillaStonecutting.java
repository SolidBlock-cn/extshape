package pers.solid.extshape.data;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMultimap;
import it.unimi.dsi.fastutil.objects.ObjectIntPair;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;
import pers.solid.extshape.block.CopperManager;
import pers.solid.extshape.block.ExtShapeBlocks;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 这里记录了 Minecraft 原版的可切石方块。在生成切石配方的过程中，需要考虑间接切石的情形，因此需要使用到这里的例子。
 * <p>
 * 例如，石头可以切石成石砖，因此也应该能够切石成以石砖为基础的方块，类似地，石台阶也应该切石成石砖横条，等等。
 *
 * @author SolidBlock
 * @since 1.5.1
 */
@ApiStatus.AvailableSince("1.5.1")
public final class VanillaStonecutting {
  /**
   * 键为切石<i>之后</i>的方块，值为切石<i>之前</i>的方块。
   */
  public static final @Unmodifiable ImmutableMultimap<Block, Block> INSTANCE;
  public static final @Unmodifiable ImmutableMultimap<Block, ObjectIntPair<Block>> INSTANCE_WITH_WEIGHT;

  private VanillaStonecutting() {
  }

  static {
    final ImmutableMultimap.Builder<Block, Block> builder = new ImmutableMultimap.Builder<>();
    registerVanillaStonecutting(builder);
    final ImmutableMultimap.Builder<Block, ObjectIntPair<Block>> weightedBuilder = new ImmutableMultimap.Builder<>();
    registerCutCopperBlocks(weightedBuilder);
    INSTANCE = builder.build();

    if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
      final List<Block> unrecognizedBaseBlocks = INSTANCE.keySet().stream().distinct()
          .filter(block -> !ExtShapeBlocks.containsBaseBlock(block))
          .toList();

      if (!unrecognizedBaseBlocks.isEmpty()) {
        throw new IllegalStateException("These blocks exist in the results of VanillaStonecutting.INSTANCE, but are not base blocks: " + unrecognizedBaseBlocks.stream().map(Registries.BLOCK::getKey).map(Objects::toString).collect(Collectors.joining(", ")));
      }
    }

    INSTANCE_WITH_WEIGHT = weightedBuilder.build();
  }

  private static void registerVanillaStonecutting(ImmutableMultimap.Builder<Block, Block> builder) {
    builder.put(Blocks.STONE_BRICKS, Blocks.STONE);
    builder.put(Blocks.CUT_SANDSTONE, Blocks.SANDSTONE);
    builder.put(Blocks.CUT_RED_SANDSTONE, Blocks.RED_SANDSTONE);
    builder.put(Blocks.CHISELED_QUARTZ_BLOCK, Blocks.QUARTZ_BLOCK);
    builder.put(Blocks.QUARTZ_BRICKS, Blocks.QUARTZ_BLOCK);
    builder.put(Blocks.POLISHED_ANDESITE, Blocks.ANDESITE);
    builder.put(Blocks.POLISHED_GRANITE, Blocks.GRANITE);
    builder.put(Blocks.POLISHED_DIORITE, Blocks.DIORITE);
    builder.put(Blocks.END_STONE_BRICKS, Blocks.END_STONE);
    builder.put(Blocks.POLISHED_BLACKSTONE, Blocks.BLACKSTONE);
    builder.put(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.BLACKSTONE);
    builder.put(Blocks.POLISHED_BLACKSTONE_BRICKS, Blocks.POLISHED_BLACKSTONE);
    builder.put(Blocks.POLISHED_DEEPSLATE, Blocks.COBBLED_DEEPSLATE);
    builder.put(Blocks.DEEPSLATE_BRICKS, Blocks.COBBLED_DEEPSLATE);
    builder.put(Blocks.DEEPSLATE_TILES, Blocks.COBBLED_DEEPSLATE);
    builder.put(Blocks.DEEPSLATE_BRICKS, Blocks.POLISHED_DEEPSLATE);
    builder.put(Blocks.DEEPSLATE_TILES, Blocks.POLISHED_DEEPSLATE);
    builder.put(Blocks.DEEPSLATE_TILES, Blocks.DEEPSLATE_BRICKS);

    builder.put(Blocks.POLISHED_TUFF, Blocks.TUFF);
    builder.put(Blocks.TUFF_BRICKS, Blocks.TUFF);
    builder.put(Blocks.TUFF_BRICKS, Blocks.POLISHED_TUFF);

    // 于 26.1 加入
    builder.put(Blocks.COBBLESTONE, Blocks.STONE);
    builder.put(Blocks.COBBLED_DEEPSLATE, Blocks.DEEPSLATE);
    builder.put(Blocks.POLISHED_DEEPSLATE, Blocks.DEEPSLATE);
    builder.put(Blocks.DEEPSLATE_BRICKS, Blocks.DEEPSLATE);
    builder.put(Blocks.DEEPSLATE_TILES, Blocks.DEEPSLATE);
  }

  private static void registerCutCopperBlocks(ImmutableMultimap.Builder<Block, ObjectIntPair<Block>> builder) {
    registerCutCopperBlocks(builder, CopperManager.CUT_COPPER_BLOCKS, CopperManager.COPPER_BLOCKS);
    registerCutCopperBlocks(builder, CopperManager.WAXED_CUT_COPPER_BLOCKS, CopperManager.WAXED_COPPER_BLOCKS);
  }

  private static void registerCutCopperBlocks(ImmutableMultimap.Builder<Block, ObjectIntPair<Block>> builder, List<Block> cutCoppers, List<Block> uncutCoppers) {
    Preconditions.checkArgument(cutCoppers.size() == uncutCoppers.size(), "cutCoppers and uncutCoppers must be of same size!");
    for (int i = 0; i < cutCoppers.size(); i++) {
      builder.put(cutCoppers.get(i), ObjectIntPair.of(uncutCoppers.get(i), 4));
    }
  }
}
