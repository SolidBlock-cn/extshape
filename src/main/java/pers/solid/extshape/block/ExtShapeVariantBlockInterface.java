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

/**
 * 用于此模组中的所有变种方块的接口。
 */
public interface ExtShapeVariantBlockInterface extends ExtShapeBlockInterface {
    /**
     * 从基础方块命名空间id路径到其对应变种方块的命名空间id路径前缀的映射。
     * 例如石砖是stone_bricks，但其变种方块的命名空间id路径前缀是stone_brick，因此其变种方块的id路径会形如stone_brick_stairs而非stone_bricks_stairs。
     */
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

    /**
     * 根据基础方块的命名空间id以及指定的后缀，利用{@link #pathPrefixMappings}，组合一个extshape命名空间下的新的id。
     * @param identifier 基础方块的id，如<code>minecraft:quartz_bricks</code>。
     * @param suffix 后缀，例如<code>"_stairs"</code>或<code>"_fence"</code>。
     * @return 组合后的id，例如 <code>minecraft:quartz_bricks</code> 和 <code>"_stairs"</code> 组合形成
     * <code>extshape:quartz_stairs</code>。
     */
    static Identifier convertIdentifier(Identifier identifier, String suffix) {
        if (identifier == Registry.BLOCK.getDefaultId())
            throw new RuntimeException(String.format("Attempt to convert a default block" +
                    " ID %s with suffix %s!", identifier, suffix));
        String path = identifier.getPath();
        String basePath = getPathPrefixOf(path);
        return new Identifier("extshape", basePath + suffix);
    }

    /**
     * 获取基础方块的id。
     * @return 基础方块的id。
     */
    default Identifier getBaseBlockIdentifier() {
        return Registry.BLOCK.getId(this.getBaseBlock());
    }

    /**
     * @return 该方块的基础方块。
     */
    default Block getBaseBlock() {
        return BlockMappings.getBaseBlockOf((Block) this);
    }

    /**
     * @return 该方块的id路径前缀。
     */
    default String getPathPrefix() {
        return getPathPrefixOf(this.getBaseBlockIdentifier().getPath());
    }

    default MutableText getNamePrefix() {
        if (this.getBaseBlock() == null) return new TranslatableText("block.extshape.prefix.unknown");
        if (this.hasPathPrefixChanged())
            return new TranslatableText(String.format("block.extshape.prefix.%s", this.getPathPrefix()));
        else return this.getBaseBlock().getName();
    }

    /**
     * @return id路径前缀与基础方块的id路径是否不同。
     * 例如，石砖楼梯的id路径前缀为 <code>stone_brick</code>，而其基础方块石砖的id路径为 <code>stone_bricks</code>，不同，所以返回 <code>true</code>。
     */
    default boolean hasPathPrefixChanged() {
        return pathPrefixMappings.containsKey(this.getBaseBlockIdentifier().getPath());
    }
}
