package pers.solid.extshape.rs;

import com.google.common.base.Predicates;
import com.google.common.collect.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.ObjectUtils;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class ExtShapeBridgeImpl /*extends ExtShapeBridge*/ {
  public static final LinkedHashSet<BlockShape> SHAPES_FOLLOWING_BASE_BLOCKS = new LinkedHashSet<>();
  public static final Multimap<BlockShape, ItemGroup> SHAPE_TRANSFER_RULES = HashMultimap.create();
/*  public static final SortingRule<Block> SHAPE_FOLLOWING_BASE_BLOCKS_RULE = new ShapeSortingRule(BlockMappings.BASE_BLOCKS::contains, SHAPES_FOLLOWING_BASE_BLOCKS);
  public static final SortingRule<Item> SHAPE_FOLLOWING_BASE_BLOCKS_ITEM_RULE = new BlockItemRule(SHAPE_FOLLOWING_BASE_BLOCKS_RULE);
  public static final TransferRule BASE_BLOCKS_IN_BUILDING_RULE = item -> item instanceof BlockItem blockItem && BlockMappings.BASE_BLOCKS.contains(blockItem.getBlock()) && item.getGroup() != ItemGroup.BUILDING_BLOCKS ? Collections.singleton(ItemGroup.BUILDING_BLOCKS) : null;
  public static final TransferRule SHAPE_TRANSFER_RULE = new ShapeTransferRule(SHAPE_TRANSFER_RULES);*/

  //  @Override
  public boolean modHasLoaded() {
    return true;
  }

  //  @Override
  public boolean isValidShapeName(String s) {
    return BlockShape.byName(s) != null;
  }

  //  @Override
  public Stream<String> getValidShapeNames() {
    return BlockShape.values().stream().map(BlockShape::asString);
  }


  //  @Override
  public void updateShapeList(String s) {
    SHAPES_FOLLOWING_BASE_BLOCKS.clear();
    if (s.equals("*")) {
      SHAPES_FOLLOWING_BASE_BLOCKS.addAll(BlockShape.values());
    } else {
      Arrays.stream(s.split("\\s+"))
          .map(BlockShape::byName)
          .filter(Objects::nonNull)
          .forEach(SHAPES_FOLLOWING_BASE_BLOCKS::add);
    }
  }


  //  @Override
  public void updateShapeTransferRules(List<String> list) {
    SHAPE_TRANSFER_RULES.clear();/*
    for (String s : list) {
      final String[] split1 = s.split("\\s+");
      if (split1.length < 2) continue;
      final BlockShape shape = BlockShape.byName(split1[0]);
      final String[] split2 = split1[1].split("\\s+");
      Arrays.stream(split2)
          .map(groupName -> Arrays.stream(ItemGroup.GROUPS)
              .filter(group -> group.getName().equals(groupName))
              .findAny())
          .filter(Optional::isPresent)
          .map(Optional::get)
          .forEach(group -> SHAPE_TRANSFER_RULES.put(shape, group));
    }*/
  }

  public static void initialize() {/*
    final ImmutableMultimap<Block, Block> defaultRules = ImmutableMultimap.of(Blocks.OAK_PLANKS, ExtShapeBlocks.PETRIFIED_OAK_PLANKS, Blocks.SMOOTH_STONE, ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB);
    SortingRule.addConditionalSortingRule(Registry.BLOCK_KEY, () -> Configs.instance.enableDefaultItemSortingRules && !Configs.instance.blockItemsOnly, new MultimapSortingRule<>(defaultRules), "default sorting rules of extshape");
    SortingRule.addConditionalSortingRule(Registry.ITEM_KEY, () -> Configs.instance.enableDefaultItemSortingRules, new MultimapSortingRule<>(ImmutableMultimap.copyOf((Iterable<? extends Map.Entry<Item, Item>>) defaultRules.entries().stream().map(entry -> Maps.immutableEntry(entry.getKey().asItem(), entry.getValue().asItem()))::iterator)), "default sorting rules of extshape");
    SortingRule.addConditionalSortingRule(Registry.BLOCK_KEY, () -> !Configs.instance.blockItemsOnly, SHAPE_FOLLOWING_BASE_BLOCKS_RULE, 8, "shape_following_base");
    SortingRule.addSortingRule(Registry.ITEM_KEY, SHAPE_FOLLOWING_BASE_BLOCKS_ITEM_RULE, 8, "shape_following_base");
    TransferRule.addTransferRule(SHAPE_TRANSFER_RULE);
    TransferRule.addConditionalTransferRule(() -> Configs.instance.baseBlocksInBuildingBlocks, BASE_BLOCKS_IN_BUILDING_RULE);

    // 如果不添加以下规则的话，那么与颜色排序规则作用时会存在错误，例如白色羊毛之后会是各种颜色的羊毛变种，然后再是其他颜色的羊毛完整方块。因此这里添加一个优先级更高的规则，仅对基础方块进行颜色排序，以解决这个问题。

    final SortingRule<Block> variantAndShapeSortingRule = block -> {
      final Iterable<Block> iterable1 = !Configs.VARIANTS_FOLLOWING_BASE_BLOCKS.isEmpty() ? SortingRules.VARIANT_FOLLOWS_BASE.getFollowers(block) : null;
      final Iterable<Block> iterable2 = SHAPE_FOLLOWING_BASE_BLOCKS_RULE.getFollowers(block);

      if (iterable1 != null && iterable2 != null) {
        return Iterables.concat(iterable1, iterable2);
      } else if (iterable1 != null) {
        return iterable1;
      } else {
        return iterable2; // 可能为 null
      }
    };
    final SortingRule<Block> colorBaseBlockSortingRule = block -> {
      if (BlockMappings.BASE_BLOCKS.contains(block)) {
        final Iterable<Block> colorFollowers = SortingRules.COLOR_SORTING_RULE.getFollowers(block);
        if (colorFollowers != null) {
          return Iterables.concat(
              ObjectUtils.defaultIfNull(variantAndShapeSortingRule.getFollowers(block), Collections.emptyList()),
              Iterables.concat(Iterables.filter(
                  Iterables.transform(colorFollowers, leadingObj -> {
                    final Iterable<Block> followers = variantAndShapeSortingRule.getFollowers(leadingObj);
                    return followers == null ? Collections.singleton(leadingObj) : Iterables.concat(Collections.singleton(leadingObj), followers);
                  }),
                  Predicates.notNull())));
        }
      }
      return null;
    };
    SortingRule.addConditionalSortingRule(Registry.BLOCK_KEY, () -> Configs.instance.fancyColorsSorting && !Configs.instance.blockItemsOnly, colorBaseBlockSortingRule, 11, "color sorting rule override");
    SortingRule.addConditionalSortingRule(Registry.ITEM_KEY, () -> Configs.instance.fancyColorsSorting, new BlockItemRule(colorBaseBlockSortingRule), 11, "color sorting rule override");*/
  }
}
