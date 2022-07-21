package pers.solid.extshape.mappings;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.block.Block;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.mixin.BlockFamiliesAccessor;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/**
 * 本类包含所有有关方块映射关系的数据。<p>
 * 本模组的逻辑时，每个形状（{@link BlockShape}）都有一个由基础方块到对应形状方块的双射，这与 Minecraft 的 BlockFamily 的逻辑略有不同。
 */
public final class BlockMappings {
  private BlockMappings() {
  }

  /**
   * 由方块形状到 BiMap 的映射，这个 BiMap 则是由基础方块到对应方块的映射。
   */
  private static final Map<BlockShape, BiMap<Block, Block>> SHAPE_TO_MAPPING;
  /**
   * 基础方块集合。当某个方块被产生变种方块（楼梯、台阶等）后，该方块就会视为基础方块，加到此集合中。
   */
  public static final Set<Block> BASE_BLOCKS = new LinkedHashSet<>();

  static {
    SHAPE_TO_MAPPING = new HashMap<>();
    // 从原版的BlockFamilies导入数据至BlockMappings。
    Stream<BlockFamily> vanillaBlockFamilies = BlockFamilies.getFamilies();
    vanillaBlockFamilies.forEach(blockFamily -> {
      Block baseBlock = blockFamily.getBaseBlock();
      Map<BlockFamily.Variant, Block> variants = blockFamily.getVariants();
      for (BlockShape shape : BlockShape.values()) {
        if (shape.vanillaVariant == null) continue;
        Block variant = variants.get(shape.vanillaVariant);
        if (variant != null) {
          getMappingOf(shape).put(baseBlock, variant);
          BASE_BLOCKS.add(baseBlock);
        }
      }
    });
  }

  public static @NotNull BiMap<Block, Block> getMappingOf(BlockShape shape) {
    return SHAPE_TO_MAPPING.computeIfAbsent(shape, shape1 -> HashBiMap.create());
  }

  /**
   * 根据指定的形状和基础方块，获取变种方块。
   *
   * @param shape     形状。
   * @param baseBlock 基础方块。
   * @return 变种方块。
   */
  @Nullable
  public static Block getBlockOf(BlockShape shape, Block baseBlock) {
    BiMap<Block, Block> mapping = getMappingOf(shape);
    return mapping.get(baseBlock);
  }

  /**
   * 将本模组定义的方块关系添加到原版的 {@link BlockFamilies} 中。
   *
   * @see pers.solid.extshape.config.ExtShapeConfig#registerBlockFamilies
   */
  public static void completeBlockFamilies() {
    final Map<Block, BlockFamily> baseBlocksToFamilies = BlockFamiliesAccessor.getBaseBlocksToFamilies();
    for (Block baseBlock : BASE_BLOCKS) {
      final BlockFamily blockFamily = baseBlocksToFamilies.computeIfAbsent(baseBlock, x -> new BlockFamily.Builder(baseBlock).build());
      final Map<BlockFamily.Variant, Block> variants = blockFamily.getVariants();
      SHAPE_TO_MAPPING.forEach((shape, map) -> {
        if (map.containsKey(baseBlock) && shape.vanillaVariant != null) {
          variants.put(shape.vanillaVariant, map.get(baseBlock));
        }
      });
    }
  }
}
