package pers.solid.extshape.blockus;

import com.brand.blockus.content.BlockusBlocks;
import com.brand.blockus.content.types.BSSTypes;
import com.brand.blockus.content.types.BSSWTypes;
import com.google.common.collect.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.util.Identifier;
import pers.solid.extshape.VanillaItemGroup;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.util.BlockBiMaps;
import pers.solid.extshape.util.EntryVariantAppender;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * @see pers.solid.extshape.VanillaItemGroup
 */
public final class ExtShapeBlockusItemGroup {

  private ExtShapeBlockusItemGroup() {
  }

  private static final ImmutableSet<Block> SPECIAL_SORTED_RAINBOW_BLOCKS = Streams.concat(
      BlockusBlockCollections.TINTED_SHINGLES.stream().map(t -> t.block),
      BlockusBlockCollections.STAINED_STONE_BRICKS.stream().map(bsswTypes -> bsswTypes.block),
      Stream.of(BlockusBlocks.SHINGLES).map(bssTypes -> bssTypes.block)).collect(ImmutableSet.toImmutableSet());

  public static void addVanillaGroupRules(Collection<BlockShape> shapes) {
    ItemGroups.getGroups().forEach(group -> {
      if (group.getId().equals(new Identifier("blockus", "blockus_building_blocks"))) {
        new EntryVariantAppender(group, shapes, ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS, ExtShapeBlockusBlocks.BLOCKUS_BLOCKS::contains).appendItems(VanillaItemGroup.getAppendingRule(group));
      } else if (group.getId().equals(new Identifier("blockus", "blockus_colored"))) {
        final Multimap<Item, Item> coloredAppendingRule = VanillaItemGroup.getAppendingRule(group);
        new EntryVariantAppender(group, shapes, Iterables.filter(ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS, input -> !SPECIAL_SORTED_RAINBOW_BLOCKS.contains(input)), ExtShapeBlockusBlocks.BLOCKUS_BLOCKS::contains).appendItems(coloredAppendingRule);
        final Item shingleAnchor = BlockusBlocks.PINK_SHINGLES.slab.asItem();
        final Item stainedStoneBrickAnchor = BlockusBlocks.PINK_STONE_BRICKS.wall.asItem();
        for (BlockShape blockShape : ExtShapeConfig.CURRENT_CONFIG.shapesToAddToVanilla) {
          BiMap<Block, Block> biMap = BlockBiMaps.of(blockShape);
          final Block block1 = biMap.get(BlockusBlocks.SHINGLES.block);
          if (block1 != null && ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(block1))
            coloredAppendingRule.put(shingleAnchor, block1.asItem());
          for (BSSTypes bssTypes : BlockusBlockCollections.TINTED_SHINGLES) {
            final Block block = biMap.get(bssTypes.block);
            if (block != null && ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(block))
              coloredAppendingRule.put(shingleAnchor, block.asItem());
          }
          for (BSSWTypes bsswTypes : BlockusBlockCollections.STAINED_STONE_BRICKS) {
            final Block block = biMap.get(bsswTypes.block);
            if (block != null && ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(block))
              coloredAppendingRule.put(stainedStoneBrickAnchor, block.asItem());
          }
        }
      }
    });
  }

  public static void registerEvent() {
    VanillaItemGroup.UPDATE_SHAPES_EVENT.register(() -> ExtShapeBlockusItemGroup.addVanillaGroupRules(ExtShapeConfig.CURRENT_CONFIG.shapesToAddToVanilla));
  }
}
