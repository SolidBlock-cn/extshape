package pers.solid.extshape;

import com.google.common.collect.Streams;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.component.BlockTransformers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.CopyPropertiesProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedStateProvider;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.mixin.BlockTransformerMixin;
import pers.solid.extshape.util.BlockBiMaps;
import pers.solid.extshape.util.BlockCollections;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class ExtShapeBlockTransformers {
  public static final ResourceKey<BlockTransformer> AXE = ResourceKey.create(Registries.BLOCK_TRANSFORMER, ExtShape.id("axe"));

  private ExtShapeBlockTransformers() {
  }

  /**
   * 创建用于本模组中的去皮方块的 {@link BlockTransformer.BlockTransformData}。本模组不修改原版的 {@link BlockTransformers#axeStrippables()}，而是通过 {@link BlockTransformerMixin} 让斧在给树去皮时，识别本模组中的可去皮方块。
   *
   * @see BlockTransformerMixin
   * @see BlockTransformers#axeStrippables()
   */
  public static BlockTransformer.BlockTransformData createEnhancedBlockTransformData() {
    final RuleBasedStateProvider.Builder ruleBasedStateProviderBuilder = RuleBasedStateProvider.builder();
    Streams.concat(
        IntStream.range(0, BlockCollections.LOGS.size()).mapToObj(i -> Pair.of(BlockCollections.LOGS.get(i), BlockCollections.STRIPPED_LOGS.get(i))),
        IntStream.range(0, BlockCollections.WOODS.size()).mapToObj(i -> Pair.of(BlockCollections.WOODS.get(i), BlockCollections.STRIPPED_WOODS.get(i))),
        IntStream.range(0, BlockCollections.HYPHAES.size()).mapToObj(i -> Pair.of(BlockCollections.HYPHAES.get(i), BlockCollections.STRIPPED_HYPHAES.get(i))),
        IntStream.range(0, BlockCollections.STEMS.size()).mapToObj(i -> Pair.of(BlockCollections.STEMS.get(i), BlockCollections.STRIPPED_STEMS.get(i))),
        Stream.of(Pair.of(Blocks.BAMBOO_BLOCK, Blocks.STRIPPED_BAMBOO_BLOCK))
    ).forEach(pair -> {
      final Block inputBase = pair.getFirst();
      final Block outputBase = pair.getSecond();
      for (BlockShape shape : BlockShape.values()) {
        final Block input = BlockBiMaps.getBlockOf(shape, inputBase);
        final Block output = BlockBiMaps.getBlockOf(shape, outputBase);
        if (input != null && output != null) {
          ruleBasedStateProviderBuilder.ifTrueThenProvide(BlockPredicate.matchesBlocks(input), new CopyPropertiesProvider(output));
        }
      }
    });

    return BlockTransformer.BlockTransformData.builder(ruleBasedStateProviderBuilder.build()).sound(SoundEvents.AXE_STRIP).build();
  }
}
