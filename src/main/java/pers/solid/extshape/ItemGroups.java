package pers.solid.extshape;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class ItemGroups {
    // 创建物品组
    public static final ItemGroup EXT_BUILDING_BLOCKS_GROUP = FabricItemGroupBuilder.create(
                    new Identifier("extshape", "building_blocks"))
            .icon(() -> new ItemStack(ExtShapeBlockTag.WOOLEN_STAIRS.getEntry(2)))
            .appendItems(itemStacks -> {
                ExtShapeBlockTag.FULL_BLOCKS.forEach((Block BLOCK) -> itemStacks.add(new ItemStack(BLOCK)));
                ExtShapeBlockTag.STAIRS.forEach((Block BLOCK) -> itemStacks.add(new ItemStack(BLOCK)));
                ExtShapeBlockTag.SLABS.forEach((Block BLOCK) -> itemStacks.add(new ItemStack(BLOCK)));
                ExtShapeBlockTag.VERTICAL_SLABS.forEach((Block BLOCK) -> itemStacks.add(new ItemStack(BLOCK)));
            })
            .build();
    public static final ItemGroup EXT_DECORATION_BLOCKS_GROUP = FabricItemGroupBuilder.create(
                    new Identifier("extshape", "decoration_blocks"))
            .icon(() -> new ItemStack(ExtShapeBlockTag.WOOLEN_FENCES.getEntry(3)))
            .appendItems(itemStacks -> {
                ExtShapeBlockTag.FENCES.forEach((Block BLOCK) -> itemStacks.add(new ItemStack(BLOCK)));
                ExtShapeBlockTag.WALLS.forEach((Block BLOCK) -> itemStacks.add(new ItemStack(BLOCK)));
            })
            .build();
    public static final ItemGroup EXT_REDSTONE_GROUP = FabricItemGroupBuilder.create(
                    new Identifier("extshape", "redstone"))
            .icon(() -> new ItemStack(ExtShapeBlockTag.WOOLEN_PRESSURE_PLATES.getEntry(4)))
            .appendItems(itemStacks -> {
                ExtShapeBlockTag.BUTTONS.forEach((Block BLOCK) -> itemStacks.add(new ItemStack(BLOCK)));
                ExtShapeBlockTag.PRESSURE_PLATES.forEach((Block BLOCK) -> itemStacks.add(new ItemStack(BLOCK)));
                ExtShapeBlockTag.FENCE_GATES.forEach((Block BLOCK) -> itemStacks.add(new ItemStack(BLOCK)));
            })
            .build();

    public static void init() {

    }
}