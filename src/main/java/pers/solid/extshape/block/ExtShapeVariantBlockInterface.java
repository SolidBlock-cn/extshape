package pers.solid.extshape.block;

import net.minecraft.block.Block;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.ExtShape;

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
    map.put("weathered_copper", "weathered_copper");
    map.put("weathered_cut_copper", "weathered_cut_copper");
  });

  /**
   * 获得 {@code path} 对应的名称前缀。
   *
   * @param path 命名空间id中的路径。如 {@code iron_block}、{@code stone_bricks}。
   * @return 转换得到的路径前缀。
   */
  static @NotNull String getPathPrefixOf(@NotNull String path) {
    if (PATH_PREFIX_MAPPINGS.containsKey(path)) {
      return PATH_PREFIX_MAPPINGS.get(path);
    }
    final String newPath = path
        .replaceAll("_planks$", "")
        .replaceAll("_block$", "")
        .replaceAll("^block_of_", "")
        .replaceAll("tiles$", "tile")
        .replaceAll("bricks$", "brick");
    if (!path.equals(newPath)) {
      PATH_PREFIX_MAPPINGS.putIfAbsent(path, newPath);
    }
    return newPath;
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
    String path = identifier.getPath();
    String basePath = getPathPrefixOf(path);
    return new Identifier(ExtShape.MOD_ID, basePath + suffix);
  }

  /**
   * @return 该方块的基础方块。
   */
  @Override
  Block getBaseBlock();

  default MutableText getNamePrefix() {
    final Block baseBlock = this.getBaseBlock();
    if (baseBlock == null) return new TranslatableText("block.extshape.prefix.unknown");
    final String path = Registry.BLOCK.getId(baseBlock).getPath();
    if (PATH_PREFIX_MAPPINGS.containsKey(path)) {
      return new TranslatableText("block.extshape.prefix." + getPathPrefixOf(path));
    } else {
      return baseBlock.getName();
    }
  }
}
