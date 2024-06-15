package pers.solid.extshape.blockus;

import com.brand.blockus.content.BlockusBlocks;
import com.brand.blockus.itemgroups.BlockusItemGroups;
import com.google.common.collect.*;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import pers.solid.extshape.VanillaItemGroup;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.util.BlockBiMaps;
import pers.solid.extshape.util.EntryVariantAppender;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * @see pers.solid.extshape.VanillaItemGroup
 */
public final class ExtShapeBlockusItemGroup {

  private static final Identifier ADD_EXTRA_ITEMS = Identifier.of(ExtShapeBlockus.NAMESPACE, "add_extra_items");

  private ExtShapeBlockusItemGroup() {
  }

  private static final ImmutableSet<Block> SPECIAL_SORTED_RAINBOW_BLOCKS = Streams.concat(
      BlockusBlockCollections.TINTED_SHINGLES.stream().map(t -> t.block),
      BlockusBlockCollections.STAINED_STONE_BRICKS.stream().map(bsswTypes -> bsswTypes.block),
      Stream.of(BlockusBlocks.SHINGLES).map(bssTypes -> bssTypes.block)).collect(ImmutableSet.toImmutableSet());

  public static void addVanillaGroupRules(Collection<BlockShape> shapes) {
    final Multimap<Item, Item> buildingAppendingRule = VanillaItemGroup.getAppendingRule(BlockusItemGroups.BLOCKUS_BUILDING_BLOCKS);
    new EntryVariantAppender(BlockusItemGroups.BLOCKUS_BUILDING_BLOCKS, shapes, Iterables.filter(ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS, block -> block != BlockusBlocks.SUGAR_BLOCK), ExtShapeBlockusBlocks.BLOCKUS_BLOCKS::contains).appendItems(buildingAppendingRule);
    final Multimap<Item, Item> coloredAppendingRule = VanillaItemGroup.getAppendingRule(BlockusItemGroups.BLOCKUS_COLORED_BLOCKS);
    final Multimap<Item, Item> coloredTilesAppendingRule = VanillaItemGroup.getAppendingRule(BlockusItemGroups.BLOCKUS_COLORED_TILES);
    new EntryVariantAppender(BlockusItemGroups.BLOCKUS_COLORED_BLOCKS, shapes, Iterables.filter(ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS, input -> !SPECIAL_SORTED_RAINBOW_BLOCKS.contains(input)), ExtShapeBlockusBlocks.BLOCKUS_BLOCKS::contains).appendItems(coloredAppendingRule);
    new EntryVariantAppender(BlockusItemGroups.BLOCKUS_COLORED_TILES, shapes, Iterables.filter(ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS, input -> !SPECIAL_SORTED_RAINBOW_BLOCKS.contains(input)), ExtShapeBlockusBlocks.BLOCKUS_BLOCKS::contains).appendItems(coloredTilesAppendingRule);
    final Item shingleAnchor = BlockusBlocks.PINK_SHINGLES.slab.asItem();
    final Item stainedStoneBrickAnchor = BlockusBlocks.PINK_STONE_BRICKS.wall.asItem();
    for (BlockShape blockShape : ExtShapeConfig.CURRENT_CONFIG.shapesToAddToVanilla) {
      BiMap<Block, Block> biMap = BlockBiMaps.of(blockShape);
      final Block block1 = biMap.get(BlockusBlocks.SHINGLES.block);
      if (block1 != null && ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(block1))
        coloredAppendingRule.put(shingleAnchor, block1.asItem());
      for (var bssTypes : BlockusBlockCollections.TINTED_SHINGLES) {
        final Block block = biMap.get(bssTypes.block);
        if (block != null && ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(block))
          coloredAppendingRule.put(shingleAnchor, block.asItem());
      }
      for (var bsswTypes : BlockusBlockCollections.STAINED_STONE_BRICKS) {
        final Block block = biMap.get(bsswTypes.block);
        if (block != null && ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(block))
          coloredAppendingRule.put(stainedStoneBrickAnchor, block.asItem());
      }
    }
  }


  public static void registerEvent() {
    VanillaItemGroup.UPDATE_SHAPES_EVENT.register(() -> ExtShapeBlockusItemGroup.addVanillaGroupRules(ExtShapeConfig.CURRENT_CONFIG.shapesToAddToVanilla));
    addModifyEntriesEvent(BlockusItemGroups.BLOCKUS_BUILDING_BLOCKS);
    addModifyEntriesEvent(BlockusItemGroups.BLOCKUS_COLORED_BLOCKS);
    addModifyEntriesEvent(BlockusItemGroups.BLOCKUS_COLORED_TILES);
  }

  private static void addModifyEntriesEvent(RegistryKey<ItemGroup> itemGroup) {
    final Event<ItemGroupEvents.ModifyEntries> event = ItemGroupEvents.modifyEntriesEvent(itemGroup);
    event.addPhaseOrdering(Event.DEFAULT_PHASE, ADD_EXTRA_ITEMS);
    event.register(ADD_EXTRA_ITEMS, entries -> {
      if (!ExtShapeConfig.CURRENT_CONFIG.addToVanillaGroups) {
        return;
      }
      final List<ItemStack> displayStacks = entries.getDisplayStacks();
      final List<ItemStack> searchTabStacks = entries.getSearchTabStacks();
      final Multimap<Item, Item> prependingRule = VanillaItemGroup.getPrependingRule(itemGroup);
      final Multimap<Item, Item> appendingRule = VanillaItemGroup.getAppendingRule(itemGroup);
      final List<ItemStack> immutableDisplayStacks = List.copyOf(displayStacks);
      final List<ItemStack> immutableSearchTabStacks = List.copyOf(searchTabStacks);
      displayStacks.clear();
      searchTabStacks.clear();
      for (ItemStack stack : immutableDisplayStacks) {
        final Item item = stack.getItem();
        displayStacks.addAll(Collections2.transform(prependingRule.get(item), ItemStack::new));
        displayStacks.add(stack);
        displayStacks.addAll(Collections2.transform(appendingRule.get(item), ItemStack::new));
      }
      for (ItemStack stack : immutableSearchTabStacks) {
        final Item item = stack.getItem();
        searchTabStacks.addAll(Collections2.transform(prependingRule.get(item), ItemStack::new));
        searchTabStacks.add(stack);
        searchTabStacks.addAll(Collections2.transform(appendingRule.get(item), ItemStack::new));
      }
    });
  }
}
