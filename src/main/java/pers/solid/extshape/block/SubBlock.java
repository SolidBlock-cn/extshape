package pers.solid.extshape.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

import static pers.solid.extshape.Mappings.*;

public interface SubBlock {
    // 所有从其他方块生成的方块都称为SubBlock。]
    BlockState getBaseBlockState();

    default Identifier getBaseBlockIdentifier() {
        return Registry.BLOCK.getId(this.getBaseBlock());
    }

    Map<String, String> pathPrefixMappings = new HashMap<>();

    default Block getBaseBlock() {
        return this.getBaseBlockState().getBlock();
    }

    default String getPathPrefix() {
        return getPathPrefixOf(this.getBaseBlockIdentifier().getPath());
    }

    default MutableText getNamePrefix() {
        if (this.hasPathPrefixChanged())
            return new TranslatableText(String.format("block.extshape.prefix.%s", this.getPathPrefix()));
        else return this.getBaseBlock().getName();
    }

    default boolean hasPathPrefixChanged() {
        return pathPrefixMappings.containsKey(this.getBaseBlockIdentifier().getPath());
    }

    static String getPathPrefixOf(String path) {
        // @paras: path: 基础方块的id路径，如iron_block、stone_bricks。
        if (pathPrefixMappings.containsKey(path)) return pathPrefixMappings.get(path);
        String convertedPath = path.replaceAll("_planks$", "")
                .replaceAll("_block$", "")
                .replaceAll("^block_of_", "")
                .replaceAll("bricks$", "brick");
        if (!path.equals(convertedPath))
            pathPrefixMappings.put(path, convertedPath);
        return convertedPath;
    }

    static Identifier convertIdentifier(Identifier identifier, String suffix) {
        String path = identifier.getPath();
        String basePath = getPathPrefixOf(path);
        return new Identifier("extshape", basePath + suffix);
    }

    static Identifier getTextureOf(Block baseBlock) {
        // 输入一个baseBlock，获得其材质。
        Identifier baseIdentifier = Registry.BLOCK.getId(baseBlock);
        return mappingOfTexture.getOrDefault(baseBlock,
                new Identifier(baseIdentifier.getNamespace(), "block/" + baseIdentifier.getPath()));
    }

    default Identifier getBaseTexture() {
        return getTextureOf(this.getBaseBlock());
    }

    static Identifier getTopTextureOf(Block baseBlock) {
        return mappingOfTopTexture.getOrDefault(baseBlock, getTextureOf(baseBlock));
    }

    default Identifier getTopTexture() {
        return getTopTextureOf(this.getBaseBlock());
    }

    static Identifier getBottomTextureOf(Block baseBlock) {
        return mappingOfBottomTexture.getOrDefault(baseBlock, getTextureOf(baseBlock));
    }

    default Identifier getBottomTexture() {
        return getBottomTextureOf(this.getBaseBlock());
    }

    static Identifier getSideTextureOf(Block baseBlock) {
        return mappingOfSideTexture.getOrDefault(baseBlock, getTextureOf(baseBlock));
    }

    default Identifier getSideTexture() {
        return getSideTextureOf(this.getBaseBlock());
    }

}
