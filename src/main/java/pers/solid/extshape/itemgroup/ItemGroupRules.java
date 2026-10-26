package pers.solid.extshape.itemgroup;

import com.google.common.collect.BiMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.common.collect.Streams;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTabOutput;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ColorCollection;
import net.minecraft.world.level.block.WeatheringCopperCollection;
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
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>此类用于管理扩展方块形状中需要添加到原版物品组的物品。所有的物品组都可以指定一个 prepending 规则和 appending 规则，分别指定在一个原版物品的前面和后面添加哪些物品。
 * <p>本模组没有使用 Fabric API 的 {@link FabricCreativeModeTabOutput#insertBefore(ItemLike, ItemLike...)} 和 {@link FabricCreativeModeTabOutput#insertAfter(ItemLike, ItemLike...)}，是考虑到该方法在寻找锚点时会迭代所有物品，如果反复调用，将影响性能。因此此模组是直接将 mixin 加到原版的 add 中，在调用原版的 {@link CreativeModeTab.Output#accept(ItemStack, CreativeModeTab.TabVisibility)} 时，会根据已有的规则一并添加需要 prepend 或 append 的物品。
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
    final Multimap<Item, Item> apRedstone = rulesAccess.getAppendingRule(CreativeModeTabs.REDSTONE_BLOCKS);
    apRedstone.put(Items.STONE_BUTTON, BlockBiMaps.getBlockOfOrThrow(BlockShape.BUTTON, Blocks.OBSIDIAN).asItem());
    final Multimap<Item, Item> preRedstone = rulesAccess.getPrependingRule(CreativeModeTabs.REDSTONE_BLOCKS);
    preRedstone.put(Items.OAK_BUTTON, BlockBiMaps.getBlockOfOrThrow(BlockShape.BUTTON, Blocks.WOOL.white()).asItem());

    final Multimap<Item, Item> apBuilding = rulesAccess.getAppendingRule(CreativeModeTabs.BUILDING_BLOCKS);
    final Multimap<Item, Item> preBuilding = rulesAccess.getPrependingRule(CreativeModeTabs.BUILDING_BLOCKS);
    preBuilding.put(Items.SMOOTH_STONE_SLAB, ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB.asItem());

    // 需要特殊处理的基础方块，在添加方块时，将不按常规方法添加，通常适用于铜和染色方块。
    final Set<Block> specialSortedBase = Streams.concat(Blocks.COPPER_BLOCK.asList().stream(), Blocks.CUT_COPPER.asList().stream()).collect(Collectors.toUnmodifiableSet());

    new EntryVariantAppender(CreativeModeTabs.BUILDING_BLOCKS, shapes, Iterables.filter(BlockBiMaps.BASE_BLOCKS, block -> !(BlockCollections.WOOLS.contains(block) || BlockCollections.STAINED_TERRACOTTA.contains(block) || BlockCollections.CONCRETES.contains(block) || BlockCollections.GLAZED_TERRACOTTA.contains(block) || block == Blocks.TERRACOTTA || specialSortedBase.contains(block))), ExtShapeBlocks.getBlocks()::contains).appendItems(apBuilding);
    final Multimap<Item, Item> coloredAppendingRule = rulesAccess.getAppendingRule(CreativeModeTabs.COLORED_BLOCKS);
    new EntryVariantAppender(CreativeModeTabs.COLORED_BLOCKS, shapes, Collections.singleton(Blocks.TERRACOTTA), ExtShapeBlocks::contains).appendItems(coloredAppendingRule);
    // natural 物品组应该排除变种的方块（这些方块已出现在了建筑方块物品组中）。
    final Set<Block> excludedInNatural = Set.of(Blocks.DEEPSLATE, Blocks.NETHERRACK, Blocks.BASALT, Blocks.SMOOTH_BASALT, Blocks.END_STONE, Blocks.AMETHYST_BLOCK);
    new EntryVariantAppender(CreativeModeTabs.NATURAL_BLOCKS, shapes, Iterables.filter(BlockBiMaps.BASE_BLOCKS, block -> !(BlockCollections.LOGS.contains(block) || BlockCollections.STEMS.contains(block) || excludedInNatural.contains(block))), ExtShapeBlocks::contains).appendItems(rulesAccess.getAppendingRule(CreativeModeTabs.NATURAL_BLOCKS));


    // 对原版切制铜块（含涂蜡变种）的特殊处理
    final Item unwaxedCopperAnchor = Items.COPPER_BLOCK.weathering().oxidized();
    final Item waxedCopperAnchor = Items.COPPER_BLOCK.waxed().oxidized();
    final Item unwaxedCutCopperAnchor = Items.CUT_COPPER_SLAB.weathering().oxidized();
    final Item waxedCutCopperAnchor = Items.CUT_COPPER_SLAB.waxed().oxidized();

    final Item woolAnchor = Items.WOOL.pink();
    final Item dyedTerracottaAnchor = Items.DYED_TERRACOTTA.pink();
    final Item concreteAnchor = Items.CONCRETE.pink();
    final Item glazedTerracottaAnchor = Items.GLAZED_TERRACOTTA.pink();

    // 铜类方块的特殊排序
    addSpecialCopperSortingRules(apBuilding, unwaxedCopperAnchor, Blocks.COPPER_BLOCK.weathering(), ExtShapeBlocks::contains);
    addSpecialCopperSortingRules(apBuilding, waxedCopperAnchor, Blocks.COPPER_BLOCK.waxed(), ExtShapeBlocks::contains);
    addSpecialCopperSortingRules(apBuilding, unwaxedCutCopperAnchor, Blocks.CUT_COPPER.weathering(), ExtShapeBlocks::contains);
    addSpecialCopperSortingRules(apBuilding, waxedCutCopperAnchor, Blocks.CUT_COPPER.waxed(), ExtShapeBlocks::contains);

    List<DyeColor> gameplayColorOrder = List.of(DyeColor.WHITE, DyeColor.LIGHT_GRAY, DyeColor.GRAY, DyeColor.BLACK, DyeColor.BROWN, DyeColor.RED, DyeColor.ORANGE, DyeColor.YELLOW, DyeColor.LIME, DyeColor.GREEN, DyeColor.CYAN, DyeColor.LIGHT_BLUE, DyeColor.BLUE, DyeColor.PURPLE, DyeColor.MAGENTA, DyeColor.PINK);

    // 染色方块的特殊排序
    addSpecialColoredSortingRules(coloredAppendingRule, woolAnchor, Blocks.WOOL, gameplayColorOrder, ExtShapeBlocks::contains);
    addSpecialColoredSortingRules(coloredAppendingRule, dyedTerracottaAnchor, Blocks.DYED_TERRACOTTA, gameplayColorOrder, ExtShapeBlocks::contains);
    addSpecialColoredSortingRules(coloredAppendingRule, concreteAnchor, Blocks.CONCRETE, gameplayColorOrder, ExtShapeBlocks::contains);
    addSpecialColoredSortingRules(coloredAppendingRule, glazedTerracottaAnchor, Blocks.GLAZED_TERRACOTTA, gameplayColorOrder, ExtShapeBlocks::contains);
  }

  public static void addSpecialCopperSortingRules(Multimap<Item, Item> appendingRule, Item anchor, WeatheringCopperCollection.ByState<? extends Block> coppersByState, Predicate<Block> predicate) {
    for (BlockShape blockShape : ExtShapeConfig.CURRENT_CONFIG.shapesToAddToVanilla) {
      final BiMap<Block, Block> biMap = BlockBiMaps.of(blockShape);
      coppersByState.forEach(block -> {
        final Block variant = biMap.get(block);
        if (variant != null && predicate.test(variant)) {
          appendingRule.put(anchor, variant.asItem());
        }
      });
    }
  }

  public static void addSpecialColoredSortingRules(Multimap<Item, Item> appendingRule, Item anchor, ColorCollection<? extends Block> colorCollection, List<DyeColor> colorOrder, Predicate<Block> predicate) {

    for (BlockShape blockShape : ExtShapeConfig.CURRENT_CONFIG.shapesToAddToVanilla) {
      final BiMap<Block, Block> biMap = BlockBiMaps.of(blockShape);
      for (DyeColor dyeColor : colorOrder) {
        final Block block = colorCollection.pick(dyeColor);
        final Block variant = biMap.get(block);
        if (variant != null && predicate.test(variant)) {
          appendingRule.put(anchor, variant.asItem());
        }
      }
    }
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
