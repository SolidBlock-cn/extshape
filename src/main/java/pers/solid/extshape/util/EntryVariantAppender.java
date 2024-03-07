package pers.solid.extshape.util;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;

import java.util.function.Predicate;

/**
 * 用于在原版物品组中添加本模组中的方块。
 *
 * @param itemGroup      需要适用的物品组。
 * @param shapes         需要添加指定的形状的方块。
 * @param baseBlocks     需要添加指定的这些基础方块。
 * @param blockPredicate 判断方块是否是需要被添加的（返回 true），还是已在物品栏中存在、需要作为锚点的（返回 false）。
 */
public record EntryVariantAppender(RegistryKey<ItemGroup> itemGroup, Iterable<BlockShape> shapes, Iterable<Block> baseBlocks, Predicate<Block> blockPredicate) {
  public static final Multimap<Block, Block> ADJACENT_BASE_BLOCKS = ImmutableSetMultimap.<Block, Block>builder()
      .put(Blocks.BAMBOO_PLANKS, Blocks.BAMBOO_MOSAIC)
      .put(Blocks.BLACKSTONE, Blocks.GILDED_BLACKSTONE)
      .build();

  public void appendItems(Multimap<Item, Item> appendingRule) {
    if (!ExtShapeConfig.CURRENT_CONFIG.addToVanillaGroups) return;
    final long prevTime = System.currentTimeMillis();
    for (final Block baseBlock : baseBlocks) {
      if (ADJACENT_BASE_BLOCKS.containsValue(baseBlock)) continue;
      // 当已有物品中有基础方块时，才会将其各种形状的方块加到其后。
      @NotNull Block anchor = baseBlock;
      for (BlockShape shape : shapes) {
        final Block block = BlockBiMaps.getBlockOf(shape, baseBlock);
        if (block == null) continue;
        // 考虑到有些情况下，自然方块收录了建筑方块的基础方块，但没有收入原版已有的形状，故这种情况下，其模组中的各种形状不会加入。
        if (blockPredicate.test(block)) {
          appendingRule.put(anchor.asItem(), block.asItem());
        } else {
          anchor = block;
        }
        for (Block adjacentBaseBlock : ADJACENT_BASE_BLOCKS.get(baseBlock)) {
          final Block adjacentBlock = BlockBiMaps.getBlockOf(shape, adjacentBaseBlock);
          if (adjacentBlock == null) continue;
          // 考虑到有些情况下，自然方块收录了建筑方块的基础方块，但没有收入原版已有的形状，故这种情况下，其模组中的各种形状不会加入。
          if (blockPredicate.test(adjacentBlock)) {
            appendingRule.put(anchor.asItem(), adjacentBlock.asItem());
          } else {
            anchor = adjacentBlock;
          }
        }
      }
    }
    final double afterTime = System.currentTimeMillis();
    ExtShape.LOGGER.info("Time spent on adding items to group {}: {}", itemGroup.getValue(), (afterTime - prevTime) / 1000d);
  }
}
