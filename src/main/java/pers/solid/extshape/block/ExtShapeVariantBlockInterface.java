package pers.solid.extshape.block;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.util.AttributiveBlockNameManager;

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
  Map<String, String> PATH_PREFIXES = new HashMap<>();

  /**
   * 此集合内的所有方块不能合成楼梯和台阶。此举是为了避免合成表冲突。
   *
   * @since 1.5.2
   */
  @ApiStatus.AvailableSince("1.5.2")
  ImmutableCollection<Block> NOT_TO_CRAFT_STAIRS_OR_SLABS = ImmutableSet.of(
      Blocks.CHISELED_SANDSTONE,
      Blocks.CHISELED_RED_SANDSTONE,
      Blocks.CHISELED_QUARTZ_BLOCK,
      Blocks.CUT_SANDSTONE,
      Blocks.CUT_RED_SANDSTONE
  );

  /**
   * 获得 {@code path} 对应的名称前缀。
   *
   * @param path 命名空间id中的路径。如 {@code iron_block}、{@code stone_bricks}。
   * @return 转换得到的路径前缀。
   */
  static @NotNull String getPathPrefixOf(@NotNull String path) {
    return PATH_PREFIXES.computeIfAbsent(path, s -> s.endsWith("bamboo_block") ? s : s
        .replaceAll("_planks$", "")
        .replaceAll("_block$", "")
        .replaceAll("^block_of_", "")
        .replaceAll("tiles$", "tile")
        .replaceAll("bricks$", "brick"));
  }

  /**
   * 根据基础方块的命名空间id以及指定的后缀，组合一个extshape命名空间下的新的id。
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

  default BlockExtension getBlockExtension() {
    return BlockExtension.EMPTY;
  }

  default MutableText getNamePrefix() {
    final Block baseBlock = this.getBaseBlock();
    if (baseBlock == null) return Text.translatable("block.extshape.prefix.unknown");
    return AttributiveBlockNameManager.getAttributiveBlockName(baseBlock.getName());
  }
}
