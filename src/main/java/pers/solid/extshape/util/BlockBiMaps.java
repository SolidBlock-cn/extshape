package pers.solid.extshape.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.block.Block;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.builder.BlockShape;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.stream.Stream;

/**
 * <p>本类用来原版和本模组中的所有方块之间的关系。方块映射的逻辑是，每个方块形状（{@link BlockShape}）都有一个由基础方块到对应形状方块的双向映射（BiMap）。可以使用 {@link #getBlockOf(BlockShape, Block)} 获取基础方块对应形状的方块。如需获取一个特定方块所属的形状，可以使用 {@link BlockShape#getShapeOf(Block)}。
 * <hr>
 * <p>This mod handles relations of blocks in vanilla and this mod. The logic of block mapping is, each {@linkplain BlockShape} has a bi-map between base blocks and blocks in that shape. You can use {@link #getBlockOf(BlockShape, Block)} to get the corresponding block in that shape of a base block. To get the shape of a block, you can use {@link BlockShape#getShapeOf(Block)}.
 */
public final class BlockBiMaps {
  private BlockBiMaps() {
  }

  /**
   * 由方块形状到 BiMap 的映射，这个 BiMap 则是由基础方块到对应方块的映射。
   */
  private static final Map<BlockShape, BiMap<Block, Block>> SHAPE_TO_BI_MAP = new HashMap<>();
  /**
   * 基础方块集合。当某个方块被产生变种方块（楼梯、台阶等）后，该方块就会视为基础方块，加到此集合中。
   */
  public static final LinkedHashSet<Block> BASE_BLOCKS = new LinkedHashSet<>();

  static {
    importFromVanilla();
  }

  /**
   * 从原版的 {@link BlockFamilies} 导入数据至 BlockMappings 中。
   */
  private static void importFromVanilla() {
    Stream<BlockFamily> vanillaBlockFamilies = BlockFamilies.getFamilies();
    vanillaBlockFamilies.forEach(blockFamily -> {
      Block baseBlock = blockFamily.getBaseBlock();
      Map<BlockFamily.Variant, Block> variants = blockFamily.getVariants();
      for (BlockShape shape : BlockShape.values()) {
        if (shape.vanillaVariant == null) continue;
        Block variant = variants.get(shape.vanillaVariant);
        if (variant == null) {
          if (shape == BlockShape.FENCE) {
            variant = variants.get(BlockFamily.Variant.CUSTOM_FENCE);
          } else if (shape == BlockShape.FENCE_GATE) {
            variant = variants.get(BlockFamily.Variant.CUSTOM_FENCE_GATE);
          }
        }
        if (variant != null && !(variant.getRequiredFeatures().contains(FeatureFlags.UPDATE_1_21) && !baseBlock.getRequiredFeatures().contains(FeatureFlags.UPDATE_1_21))) {
          // 考虑到凝灰岩基础方块没有功能标签，但是凝灰岩变种方块有功能标签，故在此进行修改，以使用本模组中的凝灰岩楼梯和凝灰岩台阶
          setBlockOf(shape, baseBlock, variant);
          BASE_BLOCKS.add(baseBlock);
        }
      }
    });
  }

  /**
   * 获取指定形状的方块映射。这个映射的键为基础方块，值为其对应的方块。如果这个映射不存在，则会创建新的。
   *
   * @param shape 方块形状。
   * @return 方块映射。
   */
  public static @NotNull BiMap<Block, Block> of(@NotNull BlockShape shape) {
    return SHAPE_TO_BI_MAP.computeIfAbsent(shape, shape1 -> HashBiMap.create());
  }

  /**
   * 根据指定的形状和基础方块，获取变种方块。
   *
   * @param shape     形状。
   * @param baseBlock 基础方块。
   * @return 变种方块。
   */
  @Nullable
  public static Block getBlockOf(@NotNull BlockShape shape, @NotNull Block baseBlock) {
    return of(shape).get(baseBlock);
  }

  /**
   * 设置指定基础方块的指定形状的方块。如果遇到重复，则会产生警告。
   *
   * @param shape     形状。
   * @param baseBlock 基础方块。
   * @param block     需要被设置的方块，该方块不是基础方块。
   */
  public static void setBlockOf(@NotNull BlockShape shape, @NotNull Block baseBlock, @NotNull Block block) {
    final BiMap<Block, Block> biMap = of(shape);
    if (biMap.containsKey(baseBlock)) {
      ExtShape.LOGGER.warn("Duplicate block mapping found: the shape {} of base block {} is {}, but will also be {}.", shape.asString(), baseBlock, biMap.get(baseBlock), block);
    }
    biMap.put(baseBlock, block);
    BASE_BLOCKS.add(baseBlock);
  }
}
