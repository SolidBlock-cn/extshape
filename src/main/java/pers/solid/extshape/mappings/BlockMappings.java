package pers.solid.extshape.mappings;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.block.Block;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.Shape;
import pers.solid.extshape.mixin.BlockFamiliesAccessor;

import java.util.EnumMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class BlockMappings {
  public static final EnumMap<Shape, BiMap<Block, Block>> SHAPE_TO_MAPPING;
  /**
   * 基础方块集合。当某个方块被产生变种方块（楼梯、台阶等）后，该方块就会视为基础方块，加到此集合中。
   */
  public static final Set<Block> BASE_BLOCKS = new LinkedHashSet<>();

  static {
    SHAPE_TO_MAPPING = new EnumMap<>(Shape.class);
    for (Shape shape : Shape.values()) {
      SHAPE_TO_MAPPING.put(shape, HashBiMap.create());
    }
    // 从原版的BlockFamilies导入数据至BlockMappings。
    Stream<BlockFamily> vanillaBlockFamilies = BlockFamilies.getFamilies();
    vanillaBlockFamilies.forEach(blockFamily -> {
      Block baseBlock = blockFamily.getBaseBlock();
      Map<BlockFamily.Variant, Block> variants = blockFamily.getVariants();
      for (Shape shape : Shape.values()) {
        if (shape.vanillaVariant == null) continue;
        Block variant = variants.get(shape.vanillaVariant);
        if (variant != null) {
          SHAPE_TO_MAPPING.get(shape).put(baseBlock, variant);
          BASE_BLOCKS.add(baseBlock);
        }
      }
    });
  }

  /**
   * @param block 方块。
   * @return 某个方块所在类地方块映射表。
   */
  @Nullable
  public static BiMap<Block, ? extends Block> getBlockMappingOf(Block block) {
    return SHAPE_TO_MAPPING.get(Shape.getShapeOf(block));
  }

  /**
   * @param block 变种方块。
   * @return 方块所属类的映射表。如果不存在，则为 null。
   */
  public static @Nullable Block getBaseBlockOf(Block block) {
    BiMap<Block, ? extends Block> mapping = getBlockMappingOf(block);
    if (mapping == null) return null;
    for (Map.Entry<Block, ? extends Block> entry : mapping.entrySet()) {
      if (entry.getValue() == block) return entry.getKey();
    }
    return mapping.inverse().get(block);
  }

  /**
   * 根据指定的形状和基础方块，获取变种方块。
   *
   * @param shape     形状。
   * @param baseBlock 基础方块。
   * @return 变种方块。
   */
  @Nullable
  public static Block getBlockOf(Shape shape, Block baseBlock) {
    BiMap<Block, Block> mapping = SHAPE_TO_MAPPING.get(shape);
    if (mapping == null) return null;
    return mapping.get(baseBlock);
  }

  public static void completeBlockFamilies() {
    final Map<Block, BlockFamily> baseBlocksToFamilies = BlockFamiliesAccessor.getBaseBlocksToFamilies();
    for (Block baseBlock : BASE_BLOCKS) {
      final BlockFamily blockFamily = baseBlocksToFamilies.computeIfAbsent(baseBlock, x -> new BlockFamily.Builder(baseBlock).build());
      final Map<BlockFamily.Variant, Block> variants = blockFamily.getVariants();
      SHAPE_TO_MAPPING.forEach((shape, map) -> {
        if (map.containsKey(baseBlock)) {
          map.putIfAbsent(baseBlock, map.get(baseBlock));
        }
      });
    }
  }
}
