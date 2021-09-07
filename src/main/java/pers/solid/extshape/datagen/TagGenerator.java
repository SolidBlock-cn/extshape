package pers.solid.extshape.datagen;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.util.HashSet;
import java.util.Set;

/**
 * 此类中定义的所有标签均<b>只在数据生成过程中使用</b>，平时运行游戏时，不会运行此处的代码。
 */
public class TagGenerator extends Generator {
//    public static final ExtShapeBlockTag OCCLUDES_VIBRATION_SIGNALS = new ExtShapeBlockTag(new Identifier("minecraft"
//            , "occludes_vibration_signals"));
    public static final ExtShapeBlockTag DRAGON_IMMUNE = new ExtShapeBlockTag(new Identifier("minecraft",
            "dragon_immune"));
    public static final ExtShapeBlockTag INFINIBURN_OVERWORLD = new ExtShapeBlockTag(new Identifier("minecraft",
            "infiniburn_overworld"));
    public static final ExtShapeBlockTag INFINIBURN_END = new ExtShapeBlockTag(new Identifier("minecraft",
            "infiniburn_end"));
    public static final ExtShapeBlockTag GEODE_INVALID_BLOCKS = new ExtShapeBlockTag(new Identifier("minecraft",
            "geode_invalid_blocks"));
    public static final ExtShapeBlockTag WITHER_IMMUNE = new ExtShapeBlockTag(new Identifier("minecraft",
            "wither_immune"));
//    public static final ExtShapeBlockTag NEEDS_DIAMOND_TOOL = new ExtShapeBlockTag(new Identifier("minecraft",
//            "needs_diamond_tool"));
//    public static final ExtShapeBlockTag NEEDS_IRON_TOOL = new ExtShapeBlockTag(new Identifier("minecraft",
//            "needs_iron_tool"));
//    public static final ExtShapeBlockTag NEEDS_STONE_TOOL = new ExtShapeBlockTag(new Identifier("minecraft",
//            "needs_stone_tool"));
    public static final ExtShapeBlockTag NON_FLAMMABLE_WOOD = new ExtShapeBlockTag(new Identifier("minecraft",
            "non_flammable_wood"));
    private static final Set<ExtShapeBlockTag> NO_ITEM_TAGS = new HashSet<>();

    static {
        NO_ITEM_TAGS.add(DRAGON_IMMUNE);
        NO_ITEM_TAGS.add(INFINIBURN_END);
        NO_ITEM_TAGS.add(INFINIBURN_OVERWORLD);
        NO_ITEM_TAGS.add(GEODE_INVALID_BLOCKS);
        NO_ITEM_TAGS.add(WITHER_IMMUNE);
//        NO_ITEM_TAGS.add(NEEDS_IRON_TOOL);
//        NO_ITEM_TAGS.add(NEEDS_STONE_TOOL);
//        NO_ITEM_TAGS.add(NEEDS_DIAMOND_TOOL);
    }

    static {
        ExtShape.EXTSHAPE_LOGGER.info("Preparing for block tags. Make sure you are in the development environment!");
        for (Block block : ExtShapeBlockTag.EXTSHAPE_BLOCKS) {
            Block baseBlock = BlockMappings.getBaseBlockOf(block);
            if (baseBlock == null) continue;
            if (baseBlock == Blocks.END_STONE || baseBlock == Blocks.OBSIDIAN || baseBlock == Blocks.CRYING_OBSIDIAN)
                DRAGON_IMMUNE.add(block);
            if (baseBlock == Blocks.NETHERRACK) INFINIBURN_OVERWORLD.add(block);
            if (baseBlock == Blocks.BEDROCK) INFINIBURN_END.add(block);
//            if (ExtShapeBlockTag.WOOLEN_BLOCKS.contains(baseBlock)) OCCLUDES_VIBRATION_SIGNALS.add(block);
            if (baseBlock == Blocks.BEDROCK || baseBlock == Blocks.PACKED_ICE || baseBlock == Blocks.BLUE_ICE || baseBlock == Blocks.ICE)
                GEODE_INVALID_BLOCKS.add(block);
            if (baseBlock == Blocks.BEDROCK) WITHER_IMMUNE.add(block);
//            if (baseBlock == Blocks.OBSIDIAN || baseBlock == Blocks.CRYING_OBSIDIAN || baseBlock == Blocks.NETHERITE_BLOCK || baseBlock == Blocks.ANCIENT_DEBRIS)
//                NEEDS_DIAMOND_TOOL.add(block);
//            if (baseBlock == Blocks.DIAMOND_BLOCK || baseBlock == Blocks.EMERALD_BLOCK || baseBlock == Blocks.GOLD_BLOCK)
//                NEEDS_IRON_TOOL.add(block);
//            if (baseBlock == Blocks.IRON_BLOCK || baseBlock == Blocks.LAPIS_BLOCK) NEEDS_STONE_TOOL.add(block);
            if (baseBlock == Blocks.CRIMSON_PLANKS || baseBlock == Blocks.WARPED_PLANKS) NON_FLAMMABLE_WOOD.add(block);
        }
        ExtShape.EXTSHAPE_LOGGER.info("Tags have been completed.");
    }

    /**
     * 写入所有的方块标签文件。
     *
     * @param generator 生成器。
     */
    public static void writeAllBlockTagFiles(Generator generator) {
        for (ExtShapeBlockTag tag : ExtShapeBlockTag.ALL_EXTSHAPE_BLOCK_TAGS) {
            generator.writeBlockTagFile(tag);
            if (!NO_ITEM_TAGS.contains(tag)) {
                generator.writeItemTagFile(tag);
            }
        }
    }
}
