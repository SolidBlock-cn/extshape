package pers.solid.extshape;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.util.BlockBiMaps;
import pers.solid.extshape.util.BlockCollections;
import pers.solid.extshape.util.EntryVariantAppender;

import java.util.Collections;
import java.util.Map;

public class ExtShapeNewItemGroup {
  private static final Map<ItemGroup, Multimap<Item, Item>> APPENDING_RULES = new Object2ObjectLinkedOpenHashMap<>();
  private static final Map<ItemGroup, Multimap<Item, Item>> PREPENDING_RULES = new Object2ObjectLinkedOpenHashMap<>();

  public static Multimap<Item, Item> getAppendingRule(ItemGroup group) {
    return APPENDING_RULES.computeIfAbsent(group, itemGroup -> LinkedHashMultimap.create());
  }

  public static Multimap<Item, Item> getPrependingRule(ItemGroup group) {
    return PREPENDING_RULES.computeIfAbsent(group, itemGroup -> LinkedHashMultimap.create());
  }

  public static void registerForMod() {
    final ImmutableList<BlockShape> shapes = ImmutableList.of(
        BlockShape.STAIRS, BlockShape.SLAB, BlockShape.QUARTER_PIECE, BlockShape.VERTICAL_STAIRS, BlockShape.VERTICAL_SLAB, BlockShape.VERTICAL_QUARTER_PIECE, BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.WALL, BlockShape.PRESSURE_PLATE, BlockShape.BUTTON
    );
    final Multimap<Item, Item> apBuilding = getAppendingRule(ItemGroups.BUILDING_BLOCKS);
    final Multimap<Item, Item> preBuilding = getPrependingRule(ItemGroups.BUILDING_BLOCKS);
    preBuilding.put(Items.SMOOTH_STONE_SLAB, ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB.asItem());
    apBuilding.put(Items.OAK_PLANKS, ExtShapeBlocks.PETRIFIED_OAK_PLANKS.asItem());
    new EntryVariantAppender(ItemGroups.BUILDING_BLOCKS, shapes, Iterables.filter(BlockBiMaps.BASE_BLOCKS, block -> !(BlockCollections.WOOLS.contains(block) || BlockCollections.STAINED_TERRACOTTA.contains(block) || BlockCollections.CONCRETES.contains(block) || BlockCollections.GLAZED_TERRACOTTA.contains(block) || block == Blocks.TERRACOTTA)), ExtShapeBlocks.getBlocks()::contains).appendItems(apBuilding);
    new EntryVariantAppender(ItemGroups.COLORED_BLOCKS, shapes, Iterables.concat(BlockCollections.WOOLS, Collections.singleton(Blocks.TERRACOTTA), BlockCollections.STAINED_TERRACOTTA, BlockCollections.CONCRETES, BlockCollections.GLAZED_TERRACOTTA), ExtShapeBlocks.getBlocks()::contains).appendItems(getAppendingRule(ItemGroups.COLORED_BLOCKS));
    new EntryVariantAppender(ItemGroups.NATURAL, shapes, Iterables.filter(BlockBiMaps.BASE_BLOCKS, block -> !(BlockCollections.LOGS.contains(block) || BlockCollections.STEMS.contains(block))), ExtShapeBlocks.getBlocks()::contains).appendItems(getAppendingRule(ItemGroups.NATURAL));
    final Multimap<Item, Item> apRedstone = getAppendingRule(ItemGroups.REDSTONE);
    apRedstone.put(Items.STONE_BUTTON, BlockBiMaps.getBlockOf(BlockShape.BUTTON, Blocks.OBSIDIAN).asItem());
    final Multimap<Item, Item> preRedstone = getPrependingRule(ItemGroups.REDSTONE);
    preRedstone.put(Items.OAK_BUTTON, BlockBiMaps.getBlockOf(BlockShape.BUTTON, Blocks.WHITE_WOOL).asItem());
  }
}
