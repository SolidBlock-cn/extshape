package pers.solid.extshape.blockus;

import com.brand.blockus.itemgroups.BlockusItemGroups;
import com.brand.blockus.registry.content.BlockusBlocks;
import com.brand.blockus.registry.content.bundles.BSSWBundle;
import com.google.common.collect.*;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
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

  private static final Identifier ADD_EXTRA_ITEMS = ExtShapeBlockus.id("add_extra_items");

  private ExtShapeBlockusItemGroup() {
  }

  private static final ImmutableSet<Block> SPECIAL_SORTED_RAINBOW_BLOCKS = Streams.concat(
      BlockusBlocks.STAINED_SHINGLES.colorMap().values().stream().map(BSSWBundle::block),
      BlockusBlocks.STAINED_STONE_BRICKS.colorMap().values().stream().map(BSSWBundle::block),
      Stream.of(BlockusBlocks.SHINGLES).map(BSSWBundle::block)).collect(ImmutableSet.toImmutableSet());

  public static void addVanillaGroupRules(Collection<BlockShape> shapes) {
    final Multimap<Item, Item> buildingAppendingRule = VanillaItemGroup.getAppendingRule(BlockusItemGroups.BLOCKUS_BUILDING_BLOCKS);
    new EntryVariantAppender(BlockusItemGroups.BLOCKUS_BUILDING_BLOCKS, shapes, ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS, ExtShapeBlockusBlocks.BLOCKUS_BLOCKS::contains).appendItems(buildingAppendingRule);
    final Multimap<Item, Item> coloredAppendingRule = VanillaItemGroup.getAppendingRule(BlockusItemGroups.BLOCKUS_COLORED_BLOCKS);
    final Multimap<Item, Item> coloredTilesAppendingRule = VanillaItemGroup.getAppendingRule(BlockusItemGroups.BLOCKUS_COLORED_TILES);
    new EntryVariantAppender(BlockusItemGroups.BLOCKUS_COLORED_BLOCKS, shapes, Iterables.filter(ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS, input -> !SPECIAL_SORTED_RAINBOW_BLOCKS.contains(input)), ExtShapeBlockusBlocks.BLOCKUS_BLOCKS::contains).appendItems(coloredAppendingRule);
    new EntryVariantAppender(BlockusItemGroups.BLOCKUS_COLORED_TILES, shapes, Iterables.filter(ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS, input -> !SPECIAL_SORTED_RAINBOW_BLOCKS.contains(input)), ExtShapeBlockusBlocks.BLOCKUS_BLOCKS::contains).appendItems(coloredTilesAppendingRule);
    final Item shingleAnchor = BlockusBlocks.STAINED_SHINGLES.colorMap().get(DyeColor.PINK).slab().asItem();
    final Item stainedStoneBrickAnchor = BlockusBlocks.STAINED_STONE_BRICKS.colorMap().get(DyeColor.PINK).wall().asItem();
    for (BlockShape blockShape : ExtShapeConfig.CURRENT_CONFIG.shapesToAddToVanilla) {
      BiMap<Block, Block> biMap = BlockBiMaps.of(blockShape);
      final Block block1 = biMap.get(BlockusBlocks.SHINGLES.block());
      if (block1 != null && ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(block1))
        coloredAppendingRule.put(shingleAnchor, block1.asItem());
      for (var bsswBundle : BlockusBlocks.STAINED_SHINGLES.colorMap().values()) {
        final Block block = biMap.get(bsswBundle.block());
        if (block != null && ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(block))
          coloredAppendingRule.put(shingleAnchor, block.asItem());
      }
      for (var bsswBundle : BlockusBlocks.STAINED_STONE_BRICKS.colorMap().values()) {
        final Block block = biMap.get(bsswBundle.block());
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

  private static void addModifyEntriesEvent(ResourceKey<CreativeModeTab> itemGroup) {
    final Event<CreativeModeTabEvents.ModifyOutput> event = CreativeModeTabEvents.modifyOutputEvent(itemGroup);
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
