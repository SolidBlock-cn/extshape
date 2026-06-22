package pers.solid.extshape.blockus;

import com.brand.blockus.itemgroups.BlockusItemGroups;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import pers.solid.extshape.VanillaItemGroup;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.util.EntryVariantAppender;

import java.util.Collection;
import java.util.List;

/**
 * @see pers.solid.extshape.VanillaItemGroup
 */
public final class ExtShapeBlockusItemGroup {

  private static final Identifier ADD_EXTRA_ITEMS = ExtShapeBlockus.id("add_extra_items");

  private ExtShapeBlockusItemGroup() {
  }

  private static final ImmutableSet<Block> SPECIAL_SORTED_RAINBOW_BLOCKS = ImmutableSet.of();

  public static void addVanillaGroupRules(Collection<BlockShape> shapes) {
    final Multimap<Item, Item> buildingAppendingRule = VanillaItemGroup.getAppendingRule(BlockusItemGroups.BLOCKUS_BUILDING_BLOCKS);
    new EntryVariantAppender(BlockusItemGroups.BLOCKUS_BUILDING_BLOCKS, shapes, ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS, ExtShapeBlockusBlocks.BLOCKUS_BLOCKS::contains).appendItems(buildingAppendingRule);
    final Multimap<Item, Item> coloredAppendingRule = VanillaItemGroup.getAppendingRule(BlockusItemGroups.BLOCKUS_COLORED_BLOCKS);
    final Multimap<Item, Item> coloredTilesAppendingRule = VanillaItemGroup.getAppendingRule(BlockusItemGroups.BLOCKUS_COLORED_TILES);
    new EntryVariantAppender(BlockusItemGroups.BLOCKUS_COLORED_BLOCKS, shapes, Iterables.filter(ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS, input -> !SPECIAL_SORTED_RAINBOW_BLOCKS.contains(input)), ExtShapeBlockusBlocks.BLOCKUS_BLOCKS::contains).appendItems(coloredAppendingRule);
    new EntryVariantAppender(BlockusItemGroups.BLOCKUS_COLORED_TILES, shapes, Iterables.filter(ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS, input -> !SPECIAL_SORTED_RAINBOW_BLOCKS.contains(input)), ExtShapeBlockusBlocks.BLOCKUS_BLOCKS::contains).appendItems(coloredTilesAppendingRule);
  }


  public static void registerEvent() {
    VanillaItemGroup.UPDATE_SHAPES_EVENT.register(() -> ExtShapeBlockusItemGroup.addVanillaGroupRules(ExtShapeConfig.CURRENT_CONFIG.shapesToAddToVanilla));
    addModifyEntriesEvent(BlockusItemGroups.BLOCKUS_BUILDING_BLOCKS);
    addModifyEntriesEvent(BlockusItemGroups.BLOCKUS_COLORED_BLOCKS);
    addModifyEntriesEvent(BlockusItemGroups.BLOCKUS_COLORED_TILES);
  }

  private static void addModifyEntriesEvent(ResourceKey<CreativeModeTab> itemGroup) {
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
