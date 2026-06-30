package pers.solid.extshape.blockus;

import com.brand.blockus.itemgroups.BlockusItemGroups;
import com.brand.blockus.registry.content.BlockusBlocks;
import com.brand.blockus.registry.content.bundles.CopperBSSWBundle;
import com.brand.blockus.registry.content.bundles.DyedBSSWBundle;
import com.brand.blockus.registry.content.bundles.WoolBundle;
import com.brand.blockus.utils.blocks.ColorBlockItemCollection;
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
import net.minecraft.world.level.block.WeatheringCopperCollection;
import org.jetbrains.annotations.Nullable;
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

  private static final ImmutableSet<Block> SPECIAL_SORTED_BUILDING_BLOCKS = Streams.concat(
      BlockusBlocks.COPPER_BRICKS.block().blocks().asList().stream(),
      BlockusBlocks.COPPER_TUFF_BRICKS.block().blocks().asList().stream()
  ).collect(ImmutableSet.toImmutableSet());

  private static final ImmutableSet<Block> SPECIAL_SORTED_RAINBOW_BLOCKS = Streams.concat(
      BlockusBlocks.DYED_STONE_BRICKS.block().blocks().asList().stream(),
      BlockusBlocks.DYED_SHINGLES.block().blocks().asList().stream(),
      BlockusBlocks.CONCRETE_BRICKS.block().blocks().asList().stream(),
      BlockusBlocks.PATTERNED_WOOL.block().blocks().asList().stream(),
      BlockusBlocks.GINGHAM_WOOL.block().blocks().asList().stream()).collect(ImmutableSet.toImmutableSet()
  );

  public static void addVanillaGroupRules(Collection<BlockShape> shapes) {
    final Multimap<Item, Item> buildingAppendingRule = VanillaItemGroup.getAppendingRule(BlockusItemGroups.BLOCKUS_BUILDING_BLOCKS);
    new EntryVariantAppender(BlockusItemGroups.BLOCKUS_BUILDING_BLOCKS, shapes, Iterables.filter(ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS, input -> !SPECIAL_SORTED_BUILDING_BLOCKS.contains(input)), ExtShapeBlockusBlocks.BLOCKUS_BLOCKS::contains).appendItems(buildingAppendingRule);
    final Multimap<Item, Item> coloredAppendingRule = VanillaItemGroup.getAppendingRule(BlockusItemGroups.BLOCKUS_COLORED_BLOCKS);
    final Multimap<Item, Item> coloredTilesAppendingRule = VanillaItemGroup.getAppendingRule(BlockusItemGroups.BLOCKUS_COLORED_TILES);
    new EntryVariantAppender(BlockusItemGroups.BLOCKUS_COLORED_BLOCKS, shapes, Iterables.filter(ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS, input -> !SPECIAL_SORTED_RAINBOW_BLOCKS.contains(input)), ExtShapeBlockusBlocks.BLOCKUS_BLOCKS::contains).appendItems(coloredAppendingRule);
    new EntryVariantAppender(BlockusItemGroups.BLOCKUS_COLORED_TILES, shapes, Iterables.filter(ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS, input -> !SPECIAL_SORTED_RAINBOW_BLOCKS.contains(input)), ExtShapeBlockusBlocks.BLOCKUS_BLOCKS::contains).appendItems(coloredTilesAppendingRule);

    // 添加特殊排序的方块
    // 染色石砖
    putColoredBlocks(BlockusBlocks.DYED_STONE_BRICKS, coloredAppendingRule);

    // 瓦片
    putColoredBlocks(BlockusBlocks.DYED_SHINGLES, coloredAppendingRule);

    // 混凝土砖
    putColoredBlocks(BlockusBlocks.CONCRETE_BRICKS.block().blocks(), BlockusBlocks.CONCRETE_BRICKS.slab().items(), BlockusBlocks.CONCRETE_BRICKS.wall().items(), coloredAppendingRule);

    // 花纹羊毛
    putWoolBundle(BlockusBlocks.PATTERNED_WOOL, coloredAppendingRule);

    // 方格羊毛
    putWoolBundle(BlockusBlocks.GINGHAM_WOOL, coloredAppendingRule);

    putCopperBSSW(BlockusBlocks.COPPER_BRICKS, buildingAppendingRule);
    putCopperBSSW(BlockusBlocks.COPPER_TUFF_BRICKS, buildingAppendingRule);
  }

  private static void putColoredBlocks(DyedBSSWBundle dyedBSSWBundle, Multimap<Item, Item> appendingRule) {
    final ColorCollection<Block> blockColorCollection = dyedBSSWBundle.block().blocks();
    final ColorCollection<Item> slabItemCollection = dyedBSSWBundle.slab().items();
    final ColorBlockItemCollection wall = dyedBSSWBundle.wall();
    final @Nullable ColorCollection<Item> wallItemCollection = wall == null ? null : wall.items();
    putColoredBlocks(blockColorCollection, slabItemCollection, wallItemCollection, appendingRule);
  }

  private static void putColoredBlocks(ColorCollection<Block> baseCollection, ColorCollection<Item> slabItemCollection, @Nullable ColorCollection<Item> wallItemCollection, Multimap<Item, Item> appendingRule) {
    final Item slabAnchor = slabItemCollection.pick(BlockOrder.COLOR.getLast());
    final Item wallAnchor = wallItemCollection == null ? slabAnchor : wallItemCollection.pick(BlockOrder.COLOR.getLast());

    for (BlockShape blockShape : ExtShapeConfig.CURRENT_CONFIG.shapesToAddToVanilla) {
      if (blockShape == BlockShape.STAIRS || blockShape == BlockShape.SLAB || (wallItemCollection != null && blockShape == BlockShape.WALL)) continue;
      final BiMap<Block, Block> biMap = BlockBiMaps.of(blockShape);
      final Item anchor = blockShape.compareTo(BlockShape.WALL) > 0 ? wallAnchor : slabAnchor;
      baseCollection.forEach(block -> {
        final Block shaped = biMap.get(block);
        if (shaped != null && ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(shaped)) {
          appendingRule.put(anchor, shaped.asItem());
        }
      });
    }
  }

  private static void putWoolBundle(WoolBundle woolBundle, Multimap<Item, Item> appendingRule) {
    putColoredBlocks(woolBundle.block().blocks(), woolBundle.slab().items(), null, appendingRule);
  }

  private static void putCopperBSSW(CopperBSSWBundle copperBSSWBundle, Multimap<Item, Item> appendingRule) {
    final WeatheringCopperCollection<Block> baseCollection = copperBSSWBundle.block().blocks();
    final Item slabAnchor = copperBSSWBundle.slab().items().waxed().oxidized();
    final Item wallAnchor = copperBSSWBundle.wall().items().waxed().oxidized();

    for (BlockShape blockShape : ExtShapeConfig.CURRENT_CONFIG.shapesToAddToVanilla) {
      if (blockShape == BlockShape.STAIRS || blockShape == BlockShape.SLAB || blockShape == BlockShape.WALL) continue;
      final BiMap<Block, Block> biMap = BlockBiMaps.of(blockShape);
      final Item anchor = blockShape.compareTo(BlockShape.WALL) > 0 ? wallAnchor : slabAnchor;
      baseCollection.forEach(block -> {
        final Block shaped = biMap.get(block);
        if (shaped != null && ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(shaped)) {
          appendingRule.put(anchor, shaped.asItem());
        }
      });
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
