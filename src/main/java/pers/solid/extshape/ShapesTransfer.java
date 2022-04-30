package pers.solid.extshape;

import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Util;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.builder.Shape;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTags;

import java.util.*;
import java.util.function.Supplier;

public class ShapesTransfer implements Supplier<Collection<Map<Item, ItemGroup>>> {
  public static final Map<Item, ItemGroup> COMPILED_SHAPE_TRANSFER_RULES = new HashMap<>();
  public static final Map<Shape, ItemGroup> SHAPE_TRANSFER_RULES = new HashMap<>();
  public static final Map<Item, ItemGroup> BASE_BLOCKS_IN_BUILDING_BLOCKS = Util.make(() -> {
    final ImmutableMap.Builder<Item, ItemGroup> builder = new ImmutableMap.Builder<>();
    builder
        .put(Items.SHROOMLIGHT, ItemGroup.BUILDING_BLOCKS)
        .put(Items.MOSS_BLOCK, ItemGroup.BUILDING_BLOCKS)
        .put(Items.HONEYCOMB_BLOCK, ItemGroup.BUILDING_BLOCKS);
    for (Block block : ExtShapeBlockTags.GLAZED_TERRACOTTA) {
      builder.put(block.asItem(), ItemGroup.BUILDING_BLOCKS);
    }
    return builder.build();
  });
  public static boolean baseBlocksInBuildingBlocks = false;

  public static void updateShapeTransferRules(List<String> list) {
    SHAPE_TRANSFER_RULES.clear();
    for (String s : list) {
      final String[] split = s.split("\\s+");
      if (split.length < 2) continue;
      final Shape shape = Shape.SHAPE_TO_STRING.inverse().get(split[0]);
      final ItemGroup group = Util.make(() -> {
        final String groupName = split[1];
        for (ItemGroup group1 : ItemGroup.GROUPS) {
          if (groupName.equals(group1.getName())) return group1;
        }
        return null;
      });
      if (shape == null || group == null) return;
      SHAPE_TRANSFER_RULES.put(shape, group);
    }
    COMPILED_SHAPE_TRANSFER_RULES.clear();
    SHAPE_TRANSFER_RULES.forEach((shape, group) -> BlockMappings.SHAPE_TO_MAPPING.get(shape).values().stream().map(Block::asItem).filter(Objects::nonNull).forEach(item -> COMPILED_SHAPE_TRANSFER_RULES.put(item, group)));
  }

  public static void setBaseBlocksInBuildingBlocks(boolean b) {
    baseBlocksInBuildingBlocks = b;
  }

  @Override
  public Collection<Map<Item, ItemGroup>> get() {
    return ImmutableList.of(COMPILED_SHAPE_TRANSFER_RULES, new ForwardingMap<>() {
      @Override
      protected @NotNull Map<Item, ItemGroup> delegate() {
        return baseBlocksInBuildingBlocks ? BASE_BLOCKS_IN_BUILDING_BLOCKS : ImmutableMap.of();
      }
    });
  }
}
