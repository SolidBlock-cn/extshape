package pers.solid.extshape.blockus;

import com.brand.blockus.content.BlockusBlocks;
import com.brand.blockus.content.types.BSSTypes;
import com.brand.blockus.content.types.BSSWTypes;
import com.brand.blockus.itemgroups.BlockusItemGroups;
import com.google.common.base.Predicates;
import com.google.common.collect.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import pers.solid.extshape.VanillaItemGroup;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.util.BlockBiMaps;
import pers.solid.extshape.util.EntryVariantAppender;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @see pers.solid.extshape.VanillaItemGroup
 */
public final class ExtShapeBlockusItemGroup {

  private ExtShapeBlockusItemGroup() {
  }

  private static final ImmutableSet<Block> SPECIAL_SORTED_RAINBOW_BLOCKS = Streams.concat(
      plainStream(BlockusBlockCollections.TINTED_SHINGLES.stream()).map(t -> t.block),
      plainStream(BlockusBlockCollections.STAINED_STONE_BRICKS.stream()).map(bsswTypes -> bsswTypes.block),
      plainStream(Stream.<Supplier<BSSTypes>>of(() -> BlockusBlocks.SHINGLES)).map(bssTypes -> bssTypes.block)).collect(ImmutableSet.toImmutableSet());

  public static void addVanillaGroupRules(Collection<BlockShape> shapes) {
    new EntryVariantAppender(Registries.ITEM_GROUP.get(BlockusItemGroups.BLOCKUS_BUILDING_BLOCKS), shapes, ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS, ExtShapeBlockusBlocks.BLOCKUS_BLOCKS::contains).appendItems(VanillaItemGroup.getAppendingRule(Registries.ITEM_GROUP.getOrThrow(BlockusItemGroups.BLOCKUS_BUILDING_BLOCKS)));
    final Multimap<Item, Item> coloredAppendingRule = VanillaItemGroup.getAppendingRule(Registries.ITEM_GROUP.get(BlockusItemGroups.BLOCKUS_COLORED_BLOCKS));
    new EntryVariantAppender(Registries.ITEM_GROUP.get(BlockusItemGroups.BLOCKUS_COLORED_BLOCKS), shapes, Iterables.filter(ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS, input -> !SPECIAL_SORTED_RAINBOW_BLOCKS.contains(input)), ExtShapeBlockusBlocks.BLOCKUS_BLOCKS::contains).appendItems(coloredAppendingRule);
    final Item shingleAnchor = BlockusBlocks.PINK_SHINGLES.slab.asItem();
    final Item stainedStoneBrickAnchor = BlockusBlocks.PINK_STONE_BRICKS.wall.asItem();
    for (BlockShape blockShape : ExtShapeConfig.CURRENT_CONFIG.shapesToAddToVanilla) {
      BiMap<Block, Block> biMap = BlockBiMaps.of(blockShape);
      ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SHINGLES, bssTypes -> {
        final Block block = biMap.get(bssTypes.block);
        if (block != null && ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(block)) coloredAppendingRule.put(shingleAnchor, block.asItem());
      });
      for (Supplier<BSSTypes> supplier : BlockusBlockCollections.TINTED_SHINGLES) {
        ExtShapeBlockus.tryConsume(supplier, bssTypes -> {
          final Block block = biMap.get(bssTypes.block);
          if (block != null && ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(block)) coloredAppendingRule.put(shingleAnchor, block.asItem());
        });
      }
      for (Supplier<BSSWTypes> supplier : BlockusBlockCollections.STAINED_STONE_BRICKS) {
        ExtShapeBlockus.tryConsume(supplier, bsswTypes -> {
          final Block block = biMap.get(bsswTypes.block);
          if (block != null && ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(block)) coloredAppendingRule.put(stainedStoneBrickAnchor, block.asItem());
        });
      }
    }
  }


  private static <T> Stream<T> plainStream(Stream<? extends Supplier<T>> stream) {
    return stream.map(supplier -> {
      try {
        return supplier.get();
      } catch (Throwable throwable) {
        return null;
      }
    }).filter(Predicates.notNull());
  }

  public static void registerEvent() {
    VanillaItemGroup.UPDATE_SHAPES_EVENT.register(() -> ExtShapeBlockusItemGroup.addVanillaGroupRules(ExtShapeConfig.CURRENT_CONFIG.shapesToAddToVanilla));
  }
}
