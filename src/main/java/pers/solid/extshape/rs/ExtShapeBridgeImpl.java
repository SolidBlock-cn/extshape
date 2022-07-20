package pers.solid.extshape.rs;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.mod.*;

import java.util.*;
import java.util.stream.Stream;

public class ExtShapeBridgeImpl extends ExtShapeBridge {
  public static final LinkedHashSet<BlockShape> SHAPES_FOLLOWING_BASE_BLOCKS = new LinkedHashSet<>();
  public static final Multimap<BlockShape, ItemGroup> SHAPE_TRANSFER_RULES = HashMultimap.create();
  public static final SortingRule<Block> SHAPE_FOLLOWING_BASE_BLOCKS_RULE = new ShapeSortingRule(BlockMappings.BASE_BLOCKS::contains, SHAPES_FOLLOWING_BASE_BLOCKS);
  public static final SortingRule<Item> SHAPE_FOLLOWING_BASE_BLOCKS_ITEM_RULE = new BlockItemRule(SHAPE_FOLLOWING_BASE_BLOCKS_RULE);
  public static final TransferRule BASE_BLOCKS_IN_BUILDING_RULE = item -> item instanceof BlockItem blockItem && BlockMappings.BASE_BLOCKS.contains(blockItem.getBlock()) ? Collections.singleton(ItemGroup.BUILDING_BLOCKS) : null;
  public static final TransferRule SHAPE_TRANSFER_RULE = new ShapeTransferRule(SHAPE_TRANSFER_RULES);

  @Override
  public boolean modHasLoaded() {
    return true;
  }

  @Override
  public boolean isValidShapeName(String s) {
    return BlockShape.byName(s) != null;
  }

  @Override
  public Stream<String> getValidShapeNames() {
    return BlockShape.values().stream().map(BlockShape::asString);
  }


  @Override
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


  @Override
  public void updateShapeTransferRules(List<String> list) {
    SHAPE_TRANSFER_RULES.clear();
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
    }
  }

  public static void initialize() {
    SortingRule.addConditionalSortingRule(Registry.BLOCK_KEY, () -> Configs.instance.enableDefaultItemSortingRules && !Configs.instance.blockItemsOnly, ImmutableMultimap.of(Blocks.OAK_PLANKS, ExtShapeBlocks.PETRIFIED_OAK_PLANKS, Blocks.SMOOTH_STONE, ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB)::get);
    SortingRule.addConditionalSortingRule(Registry.ITEM_KEY, () -> Configs.instance.enableDefaultItemSortingRules, ImmutableMultimap.of(Items.OAK_PLANKS, ExtShapeBlocks.PETRIFIED_OAK_PLANKS.asItem(), Items.SMOOTH_STONE, ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB.asItem())::get);
    SortingRule.addConditionalSortingRule(Registry.BLOCK_KEY, () -> !Configs.instance.blockItemsOnly, SHAPE_FOLLOWING_BASE_BLOCKS_RULE);
    SortingRule.addSortingRule(Registry.ITEM_KEY, SHAPE_FOLLOWING_BASE_BLOCKS_ITEM_RULE);
    TransferRule.addTransferRule(SHAPE_TRANSFER_RULE);
    TransferRule.addConditionalTransferRule(() -> Configs.instance.baseBlocksInBuildingBlocks, BASE_BLOCKS_IN_BUILDING_RULE);
  }
}
