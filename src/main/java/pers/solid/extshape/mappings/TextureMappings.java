package pers.solid.extshape.mappings;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import pers.solid.extshape.block.ExtShapeBlocks;

import java.util.HashMap;
import java.util.Map;

public class TextureMappings {
    /**
     * 方块到基础材质id的映射。
     */
    public static final Map<Block, Identifier> BLOCK_TO_TEXTURE = new HashMap<>();
    /**
     * 方块到顶部材质id的映射。
     */
    public static final Map<Block, Identifier> BLOCK_TO_TOP_TEXTURE = new HashMap<>();
    /**
     * 方块到底部材质id的映射。
     */
    public static final Map<Block, Identifier> BLOCK_TO_BOTTOM_TEXTURE = new HashMap<>();
    /**
     * 方块到侧面材质id的映射。
     */
    public static final Map<Block, Identifier> BLOCK_TO_SIDE_TEXTURE = new HashMap<>();

    static {
        BLOCK_TO_TEXTURE.put(Blocks.SMOOTH_QUARTZ, new Identifier("minecraft", "block/quartz_block_bottom"));
        BLOCK_TO_TEXTURE.put(Blocks.SMOOTH_RED_SANDSTONE, new Identifier("minecraft", "block/red_sandstone_top"));
        BLOCK_TO_TEXTURE.put(Blocks.SMOOTH_SANDSTONE, new Identifier("minecraft", "block/sandstone_top"));
        BLOCK_TO_TEXTURE.put(Blocks.SNOW_BLOCK, new Identifier("minecraft", "block/snow"));
        BLOCK_TO_TEXTURE.put(ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB, new Identifier("minecraft",
                "smooth_stone"));
        BLOCK_TO_TEXTURE.put(Blocks.QUARTZ_BLOCK, new Identifier("minecraft", "block/quartz_block_side"));

        BLOCK_TO_TOP_TEXTURE.put(Blocks.SANDSTONE, new Identifier("minecraft", "block/sandstone_top"));
        BLOCK_TO_BOTTOM_TEXTURE.put(Blocks.SANDSTONE, new Identifier("minecraft", "block/sandstone_bottom"));
        BLOCK_TO_TOP_TEXTURE.put(Blocks.RED_SANDSTONE, new Identifier("minecraft", "block/red_sandstone_top"));
        BLOCK_TO_BOTTOM_TEXTURE.put(Blocks.RED_SANDSTONE, new Identifier("minecraft", "block/red_sandstone_bottom"));
        BLOCK_TO_SIDE_TEXTURE.put(ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB, new Identifier("minecraft",
                "smooth_stone_slab_side"));
        BLOCK_TO_TOP_TEXTURE.put(Blocks.QUARTZ_BLOCK, new Identifier("minecraft", "block/quartz_block_top"));
        BLOCK_TO_BOTTOM_TEXTURE.put(Blocks.QUARTZ_BLOCK, new Identifier("minecraft", "block/quartz_block_top"));
    }

    /**
     * @param baseBlock 基础方块。
     * @return 基础方块的顶部材质id。
     */
    public static Identifier getTopTextureOf(Block baseBlock) {
        return BLOCK_TO_TOP_TEXTURE.getOrDefault(baseBlock, getTextureOf(baseBlock));
    }

    /**
     * @param baseBlock 基础方块。
     * @return 基础方块的底部材质id。
     */
    public static Identifier getBottomTextureOf(Block baseBlock) {
        return BLOCK_TO_BOTTOM_TEXTURE.getOrDefault(baseBlock, getTextureOf(baseBlock));
    }

    /**
     * @param baseBlock 基础方块。
     * @return 基础方块的侧面材质id。
     */
    public static Identifier getSideTextureOf(Block baseBlock) {
        return BLOCK_TO_SIDE_TEXTURE.getOrDefault(baseBlock, getTextureOf(baseBlock));
    }

    /**
     * @param baseBlock 基础方块。
     * @return 基础方块的材质id。
     */
    public static Identifier getTextureOf(Block baseBlock) {
        Identifier baseIdentifier = Registry.BLOCK.getId(baseBlock);
        return BLOCK_TO_TEXTURE.getOrDefault(baseBlock,
                new Identifier(baseIdentifier.getNamespace(), "block/" + baseIdentifier.getPath()));
    }
}
