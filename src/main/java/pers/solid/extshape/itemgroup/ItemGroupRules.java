package pers.solid.extshape.itemgroup;

import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.util.BlockBiMaps;
import pers.solid.extshape.util.BlockCollections;
import pers.solid.extshape.util.EntryVariantAppender;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * <p>此类用于管理扩展方块形状中需要添加到原版物品组的物品。所有的物品组都可以指定一个 prepending 规则和 appending 规则，分别指定在一个原版物品的前面和后面添加哪些物品。
 * <p>本模组没有使用 Fabric API 的 {@link FabricItemGroupEntries#addBefore(ItemConvertible, ItemConvertible...)} 和 {@link FabricItemGroupEntries#addAfter(ItemConvertible, ItemConvertible...)}，是考虑到该方法在寻找锚点时会迭代所有物品，如果反复调用，将影响性能。因此此模组是直接将 mixin 加到原版的 add 中，在调用原版的 {@link ItemGroup.Entries#add(ItemStack, ItemGroup.StackVisibility)} 时，会根据已有的规则一并添加需要 prepend 或 append 的物品。
 * <p>物品规则可通过 {@link UpdateItemGroupRulesEvent#EVENT} 注册。游戏第一次访问物品规则时，以及配置有改变时，都会重行生成一次规则。
 */
public final class ItemGroupRules {

  private static @Nullable ItemGroupRulesAccess current;

  private ItemGroupRules() {
  }

  @ApiStatus.Internal
  public static void registerForMod() {
    UpdateItemGroupRulesEvent.EVENT.register(rulesAccess -> ItemGroupRules.recreateVanillaGroupRules(rulesAccess, ExtShapeConfig.CURRENT_CONFIG.shapesToAddToVanilla));
  }

  private static void recreateVanillaGroupRules(ItemGroupRulesAccess rulesAccess, Collection<BlockShape> shapes) {
    ExtShape.LOGGER.info("Recreating vanilla item group rules for Extended Block Shapes.");
    final Multimap<Item, Item> apRedstone = rulesAccess.getAppendingRule(ItemGroups.REDSTONE);
    apRedstone.put(Items.STONE_BUTTON, BlockBiMaps.getBlockOfOrThrow(BlockShape.BUTTON, Blocks.OBSIDIAN).asItem());
    final Multimap<Item, Item> preRedstone = rulesAccess.getPrependingRule(ItemGroups.REDSTONE);
    preRedstone.put(Items.OAK_BUTTON, BlockBiMaps.getBlockOfOrThrow(BlockShape.BUTTON, Blocks.WHITE_WOOL).asItem());

    final Multimap<Item, Item> apBuilding = rulesAccess.getAppendingRule(ItemGroups.BUILDING_BLOCKS);
    final Multimap<Item, Item> preBuilding = rulesAccess.getPrependingRule(ItemGroups.BUILDING_BLOCKS);
    preBuilding.put(Items.SMOOTH_STONE_SLAB, ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB.asItem());
    apBuilding.put(Items.OAK_PLANKS, ExtShapeBlocks.PETRIFIED_OAK_PLANKS.asItem());
    new EntryVariantAppender(ItemGroups.BUILDING_BLOCKS, shapes, Iterables.filter(BlockBiMaps.BASE_BLOCKS, block -> !(BlockCollections.WOOLS.contains(block) || BlockCollections.STAINED_TERRACOTTA.contains(block) || BlockCollections.CONCRETES.contains(block) || BlockCollections.GLAZED_TERRACOTTA.contains(block) || block == Blocks.TERRACOTTA)), ExtShapeBlocks.getBlocks()::contains).appendItems(apBuilding);
    new EntryVariantAppender(ItemGroups.COLORED_BLOCKS, shapes, Iterables.concat(BlockCollections.WOOLS, Collections.singleton(Blocks.TERRACOTTA), BlockCollections.STAINED_TERRACOTTA, BlockCollections.CONCRETES, BlockCollections.GLAZED_TERRACOTTA), ExtShapeBlocks::contains).appendItems(rulesAccess.getAppendingRule(ItemGroups.COLORED_BLOCKS));
    // natural 物品组应该排除变种的方块（这些方块已出现在了建筑方块物品组中）。
    final Set<Block> excludedInNatural = Set.of(Blocks.DEEPSLATE, Blocks.NETHERRACK, Blocks.BASALT, Blocks.SMOOTH_BASALT, Blocks.END_STONE, Blocks.AMETHYST_BLOCK);
    new EntryVariantAppender(ItemGroups.NATURAL, shapes, Iterables.filter(BlockBiMaps.BASE_BLOCKS, block -> !(BlockCollections.LOGS.contains(block) || BlockCollections.STEMS.contains(block) || excludedInNatural.contains(block))), ExtShapeBlocks::contains).appendItems(rulesAccess.getAppendingRule(ItemGroups.NATURAL));
  }

  /**
   * 获取当前的规则，如果当前的规则不存在，则会根据已在 {@link UpdateItemGroupRulesEvent#EVENT} 中注册的事件回调生成规则。
   */
  public static ItemGroupRulesAccess currentRules() {
    if (current == null) {
      return current = createAndUpdateRules();
    }
    return current;
  }

  /**
   * 重新构建规则，该过程会调用 {@link UpdateItemGroupRulesEvent#EVENT} 中注册的事件回调。此方法通常发生在游戏配置有改变时。
   */
  public static void rebuildRules() {
    current = createAndUpdateRules();
  }

  private static ItemGroupRulesAccess createAndUpdateRules() {
    final ItemGroupRulesAccess rulesAccess = ItemGroupRulesAccess.create();
    ItemGroupRules.current = rulesAccess;
    UpdateItemGroupRulesEvent.EVENT.invoker().updateItemGroupEntries(rulesAccess);
    rulesAccess.logRulesInfo();
    return rulesAccess;
  }
}
