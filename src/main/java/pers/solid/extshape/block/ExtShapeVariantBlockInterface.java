package pers.solid.extshape.block;

import net.minecraft.block.Block;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

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
  Map<String, String> PATH_PREFIX_MAPPINGS = Util.make(new HashMap<>(), map -> {
    map.put("chiseled_quartz", "chiseled_quartz");
    map.put("clay", "clay");
    map.put("cut_copper", "cut_copper");
    map.put("exposed_copper", "exposed_copper");
    map.put("exposed_cut_copper", "exposed_cut_copper");
    map.put("oxidized_copper", "oxidized_copper");
    map.put("oxidized_cut_copper", "oxidized_cut_copper");
    map.put("smooth_quartz", "smooth_quartz");
    map.put("waxed_cut_copper", "waxed_cut_copper");
    map.put("waxed_exposed_copper", "waxed_exposed_copper");
    map.put("waxed_exposed_cut_copper", "waxed_exposed_cut_copper");
    map.put("waxed_oxidized_copper", "waxed_oxidized_copper");
    map.put("waxed_oxidized_cut_copper", "waxed_oxidized_cut_copper");
    map.put("waxed_weathered_copper", "waxed_weathered_copper");
    map.put("waxed_weathered_cut_copper", "waxed_weathered_cut_copper");
    map.put("weathered_cut_copper", "weathered_cut_copper");
  });

  /**
   * 获得 {@code path} 对应的名称前缀。
   *
   * @param path 命名空间id中的路径。如 {@code iron_block}、{@code stone_bricks}。
   * @return 转换得到的路径前缀。
   */
  static String getPathPrefixOf(@NotNull String path) {
    return PATH_PREFIX_MAPPINGS.getOrDefault(path, Util.make(path
        .replaceAll("_planks$", "")
        .replaceAll("_block$", "")
        .replaceAll("^block_of_", "")
        .replaceAll("tiles$", "tile")
        .replaceAll("bricks$", "brick"), convertedPath -> {
      if (!path.equals(convertedPath))
        PATH_PREFIX_MAPPINGS.put(path, convertedPath);
    }));
  }

  /**
   * 根据基础方块的命名空间id以及指定的后缀，利用{@link #PATH_PREFIX_MAPPINGS}，组合一个extshape命名空间下的新的id。
   *
   * @param identifier 基础方块的id，如<code>minecraft:quartz_bricks</code>。
   * @param suffix     后缀，例如<code>"_stairs"</code>或<code>"_fence"</code>。
   * @return 组合后的id，例如 <code>minecraft:quartz_bricks</code> 和 <code>"_stairs"</code> 组合形成
   * <code>extshape:quartz_stairs</code>。
   */
  static Identifier convertIdentifier(Identifier identifier, String suffix) {
    if (identifier == Registry.BLOCK.getDefaultId())
      throw new RuntimeException(String.format("Attempt to convert a default block ID %s with suffix %s!", identifier, suffix));
    String path = identifier.getPath();
    String basePath = getPathPrefixOf(path);
    return new Identifier("extshape", basePath + suffix);
  }

  /**
   * 将标识符转化为方块标识符并添加后缀。<br>
   * <b>例如：</b>{@code minecraft:oak_slab} 和 {@code _top} 转化为 {@code minecraft:block/oak_slab_top}。
   *
   * @param identifier 要转化的标识符。
   * @param suffix     标识符后缀。
   * @return 转化后的标识符。
   */
  static Identifier blockIdentifier(Identifier identifier, String suffix) {
    return new Identifier(identifier.getNamespace(), "block/" + identifier.getPath().replaceFirst("^waxed_", "") + suffix);
  }

  /**
   * 获取基础方块的id。
   *
   * @return 基础方块的id。
   */
  default Identifier getBaseBlockId() {
    return Registry.BLOCK.getId(this.getBaseBlock());
  }

  /**
   * @return 该方块的基础方块。
   */
  @Override
  Block getBaseBlock();

  /**
   * @return 该方块的id路径前缀。
   */
  default String getPathPrefix() {
    return getPathPrefixOf(this.getBaseBlockId().getPath());
  }

  default MutableText getNamePrefix() {
    if (this.getBaseBlock() == null) return new TranslatableText("block.extshape.prefix.unknown");
    if (this.hasPathPrefixChanged())
      return new TranslatableText("block.extshape.prefix." + this.getPathPrefix());
    else return this.getBaseBlock().getName();
  }

  /**
   * @return id路径前缀与基础方块的id路径是否不同。
   * 例如，石砖楼梯的id路径前缀为 <code>stone_brick</code>，而其基础方块石砖的id路径为 <code>stone_bricks</code>，不同，所以返回 <code>true</code>。
   */
  default boolean hasPathPrefixChanged() {
    return PATH_PREFIX_MAPPINGS.containsKey(this.getBaseBlockId().getPath());
  }
}
