package pers.solid.extshape;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import pers.solid.extshape.builder.Shape;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 本模组中的物品组。
 * 物品将会添加到原版物品组，然后再添加四个额外的物品组，用于分类存放物品，且这些额外物品组包括原版方块。
 */
public class ItemGroups {
    private static final ExtShapeBlockTag WOODEN_BLOCKS = new ExtShapeBlockTag();
    public static final ItemGroup WOODEN_BLOCK_GROUP = FabricItemGroupBuilder.create(new Identifier("extshape",
            "wooden_blocks")).appendItems(itemStacks -> WOODEN_BLOCKS.forEach((block -> importTo(block,
            itemStacks)))).icon(() -> new ItemStack(Blocks.BIRCH_PLANKS)).build();
    private static final ExtShapeBlockTag COLORFUL_BLOCKS = new ExtShapeBlockTag();
    public static final ItemGroup COLORFUL_BLOCK_GROUP = FabricItemGroupBuilder.create(new Identifier("extshape",
            "colorful_blocks")).icon(() -> new ItemStack(Blocks.YELLOW_WOOL)).appendItems(itemStacks -> COLORFUL_BLOCKS.forEach((block -> importTo(block,
            itemStacks)))).build();
    private static final ExtShapeBlockTag STONE_BLOCKS = new ExtShapeBlockTag();
    public static final ItemGroup STONE_BLOCK_GROUP = FabricItemGroupBuilder.create(new Identifier("extshape",
            "stone_blocks")).icon(() -> new ItemStack(Blocks.STONE)).appendItems(itemStacks -> STONE_BLOCKS.forEach((block -> importTo(block,
            itemStacks)))).build();
    public static final ItemGroup OTHER_BLOCK_GROUP = FabricItemGroupBuilder.create(new Identifier("extshape",
            "other_blocks")).icon(() -> new ItemStack(Blocks.CHISELED_QUARTZ_BLOCK)).appendItems(itemStacks -> {
        Set<Block> baseBlockList = new LinkedHashSet<>(BlockMappings.BASE_BLOCKS);
        WOODEN_BLOCKS.forEach(baseBlockList::remove);
        COLORFUL_BLOCKS.forEach(baseBlockList::remove);
        STONE_BLOCKS.forEach(baseBlockList::remove);
        baseBlockList.forEach(block -> importTo(block, itemStacks));
    }).build();

    static {
        WOODEN_BLOCKS.addTag(ExtShapeBlockTag.PLANKS);
        COLORFUL_BLOCKS.addTag(ExtShapeBlockTag.WOOLS);
        COLORFUL_BLOCKS.addTag(ExtShapeBlockTag.CONCRETES);
        COLORFUL_BLOCKS.add(Blocks.TERRACOTTA);
        COLORFUL_BLOCKS.addTag(ExtShapeBlockTag.STAINED_TERRACOTTAS);
        COLORFUL_BLOCKS.addTag(ExtShapeBlockTag.GLAZED_TERRACOTTAS);
        STONE_BLOCKS.addTag(ExtShapeBlockTag.STONES);
        STONE_BLOCKS.addAll(ImmutableList.of(
                Blocks.SMOOTH_STONE,
                Blocks.STONE_BRICKS,
                Blocks.MOSSY_STONE_BRICKS,
//                Blocks.COBBLED_DEEPSLATE,
//                Blocks.POLISHED_DEEPSLATE,
//                Blocks.DEEPSLATE_BRICKS,
//                Blocks.DEEPSLATE_TILES,
//                Blocks.TUFF,
//                Blocks.CALCITE,
                Blocks.COBBLESTONE,
                Blocks.MOSSY_COBBLESTONE,
                Blocks.SANDSTONE,
                Blocks.RED_SANDSTONE,
                Blocks.CUT_SANDSTONE,
                Blocks.CUT_RED_SANDSTONE,
                Blocks.CHISELED_SANDSTONE,
                Blocks.CHISELED_RED_SANDSTONE,
                Blocks.SMOOTH_SANDSTONE,
                Blocks.SMOOTH_RED_SANDSTONE,
                Blocks.NETHERRACK,
                Blocks.NETHER_BRICKS,
//                Blocks.SMOOTH_BASALT,
                Blocks.RED_NETHER_BRICKS,
                Blocks.PRISMARINE,
                Blocks.PRISMARINE_BRICKS,
                Blocks.DARK_PRISMARINE,
                Blocks.BRICKS,
                Blocks.BLACKSTONE,
                Blocks.POLISHED_BLACKSTONE,
                Blocks.POLISHED_BLACKSTONE_BRICKS,
                Blocks.OBSIDIAN,
                Blocks.CRYING_OBSIDIAN,
                Blocks.BEDROCK,
                Blocks.END_STONE,
                Blocks.END_STONE_BRICKS));
    }

    // 以下为按方块排序的列表。
    protected static void importTo(Block baseBlock, List<ItemStack> itemStacks) {
        Block t;
        List<ItemStack> is = new ArrayList<>();
        if (baseBlock == null) return;
        for (Shape shape : Shape.values()) {
            if ((t = BlockMappings.getBlockOf(shape, baseBlock)) != null) is.add(new ItemStack(t));
        }
        if (is.size() > 0) {
            itemStacks.add(new ItemStack(baseBlock));
            itemStacks.addAll(is);
        }
    }

    public static void init() {

    }
}