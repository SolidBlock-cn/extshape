package pers.solid.extshape.blockus;

import com.brand.blockus.itemgroups.BlockusItemGroups;
import com.brand.blockus.registry.content.BlockusBlocks;
import com.brand.blockus.utils.helper.BlockOrder;
import com.google.common.collect.*;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.event.Event;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ColorCollection;
import pers.solid.extshape.VanillaItemGroup;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.util.BlockBiMaps;
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

  private static final ImmutableSet<Block> SPECIAL_SORTED_RAINBOW_BLOCKS = Streams.concat(
      BlockusBlocks.DYED_STONE_BRICKS.block().blocks().asList().stream(),
      BlockusBlocks.DYED_SHINGLES.block().blocks().asList().stream(),
      BlockusBlocks.CONCRETE_BRICKS.block().blocks().asList().stream(),
      BlockusBlocks.PATTERNED_WOOL.block().blocks().asList().stream(),
      BlockusBlocks.GINGHAM_WOOL.block().blocks().asList().stream()).collect(ImmutableSet.toImmutableSet()
  );

  public static void addVanillaGroupRules(Collection<BlockShape> shapes) {
    final Multimap<Item, Item> buildingAppendingRule = VanillaItemGroup.getAppendingRule(BlockusItemGroups.BLOCKUS_BUILDING_BLOCKS);
    new EntryVariantAppender(BlockusItemGroups.BLOCKUS_BUILDING_BLOCKS, shapes, ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS, ExtShapeBlockusBlocks.BLOCKUS_BLOCKS::contains).appendItems(buildingAppendingRule);
    final Multimap<Item, Item> coloredAppendingRule = VanillaItemGroup.getAppendingRule(BlockusItemGroups.BLOCKUS_COLORED_BLOCKS);
    final Multimap<Item, Item> coloredTilesAppendingRule = VanillaItemGroup.getAppendingRule(BlockusItemGroups.BLOCKUS_COLORED_TILES);
    new EntryVariantAppender(BlockusItemGroups.BLOCKUS_COLORED_BLOCKS, shapes, Iterables.filter(ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS, input -> !SPECIAL_SORTED_RAINBOW_BLOCKS.contains(input)), ExtShapeBlockusBlocks.BLOCKUS_BLOCKS::contains).appendItems(coloredAppendingRule);
    new EntryVariantAppender(BlockusItemGroups.BLOCKUS_COLORED_TILES, shapes, Iterables.filter(ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS, input -> !SPECIAL_SORTED_RAINBOW_BLOCKS.contains(input)), ExtShapeBlockusBlocks.BLOCKUS_BLOCKS::contains).appendItems(coloredTilesAppendingRule);

    final Item stainedStoneBrickAnchor = BlockusBlocks.DYED_STONE_BRICKS.wall().items().pick(BlockOrder.COLOR.getLast());
    final Item stainedShinglesAnchor = BlockusBlocks.DYED_SHINGLES.slab().items().pick(BlockOrder.COLOR.getLast());
    final Item concreteBricksAnchor = BlockusBlocks.CONCRETE_BRICKS.wall().items().pick(BlockOrder.COLOR.getLast());
    final Item patternedWoolAnchor = BlockusBlocks.PATTERNED_WOOL.slab().items().pick(BlockOrder.COLOR.getLast());
    final Item ginghamWoolAnchor = BlockusBlocks.GINGHAM_WOOL.slab().items().pick(BlockOrder.COLOR.getLast());

    final ColorCollection<Block> stainedStoneBrickBlocks = BlockusBlocks.DYED_STONE_BRICKS.block().blocks();
    final ColorCollection<Block> stainedShinglesBrickBlocks = BlockusBlocks.DYED_SHINGLES.block().blocks();
    final ColorCollection<Block> concreteBrickBlocks = BlockusBlocks.CONCRETE_BRICKS.block().blocks();
    final ColorCollection<Block> patternedWoolBlocks = BlockusBlocks.PATTERNED_WOOL.block().blocks();
    final ColorCollection<Block> ginghamWoolBlocks = BlockusBlocks.GINGHAM_WOOL.block().blocks();

    // 添加特殊排序的方块
    for (BlockShape blockShape : ExtShapeConfig.CURRENT_CONFIG.shapesToAddToVanilla) {
      BiMap<Block, Block> biMap = BlockBiMaps.of(blockShape);

      // 染色石砖
      for (var baseBlock : stainedStoneBrickBlocks.asList()) {
        final Block block = biMap.get(baseBlock);
        if (block != null && ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(block))
          coloredAppendingRule.put(stainedStoneBrickAnchor, block.asItem());
      }

      // 瓦片
      for (var baseBlock : stainedShinglesBrickBlocks.asList()) {
        final Block block = biMap.get(baseBlock);
        if (block != null && ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(block)) {
          coloredAppendingRule.put(stainedShinglesAnchor, block.asItem());
        }
      }

      // 混凝土砖
      for (var baseBlock : concreteBrickBlocks.asList()) {
        final Block block = biMap.get(baseBlock);
        if (block != null && ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(block)) {
          coloredAppendingRule.put(concreteBricksAnchor, block.asItem());
        }
      }

      // 花纹羊毛
      for (var baseBlock : patternedWoolBlocks.asList()) {
        final Block block = biMap.get(baseBlock);
        if (block != null && ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(block)) {
          coloredAppendingRule.put(patternedWoolAnchor, block.asItem());
        }
      }

      // 方格羊毛
      for (var baseBlock : ginghamWoolBlocks.asList()) {
        final Block block = biMap.get(baseBlock);
        if (block != null && ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(block)) {
          coloredAppendingRule.put(ginghamWoolAnchor, block.asItem());
        }
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
