package pers.solid.extshape;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.ApiStatus;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.util.BlockBiMaps;
import pers.solid.extshape.util.BlockCollections;
import pers.solid.extshape.util.EntryVariantAppender;

import java.util.*;

/**
 * 本类用于将物品添加到原版物品组。
 */
public final class VanillaItemGroup {
  private static final Map<ResourceKey<CreativeModeTab>, Multimap<Item, Item>> APPENDING_RULES = new Object2ObjectLinkedOpenHashMap<>();
  private static final Map<ResourceKey<CreativeModeTab>, Multimap<Item, Item>> PREPENDING_RULES = new Object2ObjectLinkedOpenHashMap<>();

  /**
   * 更新创造模式物品栏时的事件。
   */
  public static final Event<Runnable> UPDATE_SHAPES_EVENT = EventFactory.createArrayBacked(Runnable.class, runnables -> () -> {
    for (Runnable runnable : runnables) {
      runnable.run();
    }
  });

  private VanillaItemGroup() {
  }

  @ApiStatus.Internal
  public static Multimap<Item, Item> getAppendingRule(ResourceKey<CreativeModeTab> group) {
    return APPENDING_RULES.computeIfAbsent(group, itemGroup -> ArrayListMultimap.create());
  }

  @ApiStatus.Internal
  public static Multimap<Item, Item> getPrependingRule(ResourceKey<CreativeModeTab> group) {
    return PREPENDING_RULES.computeIfAbsent(group, itemGroup -> ArrayListMultimap.create());
  }

  @ApiStatus.Internal
  public static void registerForMod() {
    final Multimap<Item, Item> apRedstone = getAppendingRule(CreativeModeTabs.REDSTONE_BLOCKS);
    apRedstone.put(Items.STONE_BUTTON, Objects.requireNonNull(BlockBiMaps.getBlockOf(BlockShape.BUTTON, Blocks.OBSIDIAN)).asItem());
    final Multimap<Item, Item> preRedstone = getPrependingRule(CreativeModeTabs.REDSTONE_BLOCKS);
    preRedstone.put(Items.OAK_BUTTON, Objects.requireNonNull(BlockBiMaps.getBlockOf(BlockShape.BUTTON, Blocks.WOOL.white())).asItem());

    UPDATE_SHAPES_EVENT.register(() -> {
      PREPENDING_RULES.clear();
      APPENDING_RULES.clear();
    });
    UPDATE_SHAPES_EVENT.register(() -> VanillaItemGroup.recreateVanillaGroupRules(ExtShapeConfig.CURRENT_CONFIG.shapesToAddToVanilla));
  }

  @ApiStatus.Internal
  public static void recreateVanillaGroupRules(Collection<BlockShape> shapes) {
    final Multimap<Item, Item> apBuilding = getAppendingRule(CreativeModeTabs.BUILDING_BLOCKS);
    final Multimap<Item, Item> preBuilding = getPrependingRule(CreativeModeTabs.BUILDING_BLOCKS);
    preBuilding.put(Items.SMOOTH_STONE_SLAB, ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB.asItem());
    apBuilding.put(Items.OAK_PLANKS, ExtShapeBlocks.PETRIFIED_OAK_PLANKS.asItem());
    new EntryVariantAppender(CreativeModeTabs.BUILDING_BLOCKS, shapes, Iterables.filter(BlockBiMaps.BASE_BLOCKS, block -> !(BlockCollections.WOOLS.contains(block) || BlockCollections.STAINED_TERRACOTTA.contains(block) || BlockCollections.CONCRETES.contains(block) || BlockCollections.GLAZED_TERRACOTTA.contains(block) || block == Blocks.TERRACOTTA)), ExtShapeBlocks.getBlocks()::contains).appendItems(apBuilding);
    new EntryVariantAppender(CreativeModeTabs.COLORED_BLOCKS, shapes, Iterables.concat(BlockCollections.WOOLS, Collections.singleton(Blocks.TERRACOTTA), BlockCollections.STAINED_TERRACOTTA, BlockCollections.CONCRETES, BlockCollections.GLAZED_TERRACOTTA), ExtShapeBlocks::contains).appendItems(getAppendingRule(CreativeModeTabs.COLORED_BLOCKS));
    // natural 物品组应该排除变种的方块（这些方块已出现在了建筑方块物品组中）。
    final Set<Block> excludedInNatural = Set.of(Blocks.DEEPSLATE, Blocks.NETHERRACK, Blocks.BASALT, Blocks.SMOOTH_BASALT, Blocks.END_STONE, Blocks.AMETHYST_BLOCK);
    new EntryVariantAppender(CreativeModeTabs.NATURAL_BLOCKS, shapes, Iterables.filter(BlockBiMaps.BASE_BLOCKS, block -> !(BlockCollections.LOGS.contains(block) || BlockCollections.STEMS.contains(block) || excludedInNatural.contains(block))), ExtShapeBlocks::contains).appendItems(getAppendingRule(CreativeModeTabs.NATURAL_BLOCKS));
  }
}
