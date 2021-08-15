package pers.solid.extshape.mappings;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import pers.solid.extshape.block.ExtShapeBlocks;

import java.util.HashMap;
import java.util.Map;

public class TextureMappings {
    public static final Map<Block, Identifier> mappingOfTexture = new HashMap<>();
    public static final Map<Block, Identifier> mappingOfTopTexture = new HashMap<>();
    public static final Map<Block, Identifier> mappingOfBottomTexture = new HashMap<>();
    public static final Map<Block, Identifier> mappingOfSideTexture = new HashMap<>();

    static {
        mappingOfTexture.put(Blocks.SMOOTH_QUARTZ, new Identifier("minecraft", "block/quartz_block_bottom"));
        mappingOfTexture.put(Blocks.SMOOTH_RED_SANDSTONE, new Identifier("minecraft", "block/red_sandstone_top"));
        mappingOfTexture.put(Blocks.SMOOTH_SANDSTONE, new Identifier("minecraft", "block/sandstone_top"));
        mappingOfTexture.put(Blocks.SNOW_BLOCK, new Identifier("minecraft", "block/snow"));
        mappingOfTexture.put(ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB, new Identifier("minecraft",
                "smooth_stone"));
        mappingOfTexture.put(Blocks.QUARTZ_BLOCK, new Identifier("minecraft", "block/quartz_block_side"));

        mappingOfTopTexture.put(Blocks.SANDSTONE, new Identifier("minecraft", "block/sandstone_top"));
        mappingOfBottomTexture.put(Blocks.SANDSTONE, new Identifier("minecraft", "block/sandstone_bottom"));
        mappingOfTopTexture.put(Blocks.RED_SANDSTONE, new Identifier("minecraft", "block/red_sandstone_top"));
        mappingOfBottomTexture.put(Blocks.RED_SANDSTONE, new Identifier("minecraft", "block/red_sandstone_bottom"));
        mappingOfSideTexture.put(ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB, new Identifier("minecraft",
                "smooth_stone_slab_side"));
        mappingOfTopTexture.put(Blocks.QUARTZ_BLOCK, new Identifier("minecraft", "block/quartz_block_top"));
        mappingOfBottomTexture.put(Blocks.QUARTZ_BLOCK, new Identifier("minecraft", "block/quartz_block_top"));
    }

    public static Identifier getTopTextureOf(Block baseBlock) {
        return mappingOfTopTexture.getOrDefault(baseBlock, getTextureOf(baseBlock));
    }

    public static Identifier getBottomTextureOf(Block baseBlock) {
        return mappingOfBottomTexture.getOrDefault(baseBlock, getTextureOf(baseBlock));
    }

    public static Identifier getSideTextureOf(Block baseBlock) {
        return mappingOfSideTexture.getOrDefault(baseBlock, getTextureOf(baseBlock));
    }

    public static Identifier getTextureOf(Block baseBlock) {
        // 输入一个baseBlock，获得其材质。
        Identifier baseIdentifier = Registry.BLOCK.getId(baseBlock);
        return mappingOfTexture.getOrDefault(baseBlock,
                new Identifier(baseIdentifier.getNamespace(), "block/" + baseIdentifier.getPath()));
    }
}
