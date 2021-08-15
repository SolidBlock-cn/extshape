package pers.solid.extshape.block;

import net.minecraft.block.Block;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.mappings.BlockMappings;

import java.util.HashMap;
import java.util.Map;

public interface ExtShapeVariantBlockInterface extends ExtShapeBlockInterface {
    Map<String, String> pathPrefixMappings = new HashMap<>();

    static String getPathPrefixOf(@NotNull String path) {
        // @paras: path: 基础方块的id路径，如iron_block、stone_bricks。
        if (pathPrefixMappings.containsKey(path)) return pathPrefixMappings.get(path);
        String convertedPath = path.replaceAll("_planks$", "")
                .replaceAll("_block$", "")
                .replaceAll("^block_of_", "")
                .replaceAll("tiles$", "tile")
                .replaceAll("bricks$", "brick");
        if (!path.equals(convertedPath))
            pathPrefixMappings.put(path, convertedPath);
        return convertedPath;
    }

    static Identifier convertIdentifier(Identifier identifier, String suffix) {
        if (identifier == Registry.BLOCK.getDefaultId())
            throw new RuntimeException(String.format("Attempt to convert a default block" +
                    " ID %s with suffix %s!", identifier, suffix));
        String path = identifier.getPath();
        String basePath = getPathPrefixOf(path);
        return new Identifier("extshape", basePath + suffix);
    }

    default Identifier getBaseBlockIdentifier() {
        return Registry.BLOCK.getId(this.getBaseBlock());
    }

    default Block getBaseBlock() {
        return BlockMappings.getBaseBlockOf((Block) this);
    }

    default String getPathPrefix() {
        return getPathPrefixOf(this.getBaseBlockIdentifier().getPath());
    }

    default MutableText getNamePrefix() {
        if (this.getBaseBlock() == null) return new TranslatableText("block.extshape.prefix.unknown");
        if (this.hasPathPrefixChanged())
            return new TranslatableText(String.format("block.extshape.prefix.%s", this.getPathPrefix()));
        else return this.getBaseBlock().getName();
    }

    default boolean hasPathPrefixChanged() {
        return pathPrefixMappings.containsKey(this.getBaseBlockIdentifier().getPath());
    }

}
