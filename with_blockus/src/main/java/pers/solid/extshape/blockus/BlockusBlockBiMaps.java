package pers.solid.extshape.blockus;

import com.brand.blockus.content.BlockusBlocks;
import com.brand.blockus.content.types.BSSTypes;
import com.brand.blockus.content.types.BSSWTypes;
import com.brand.blockus.content.types.PatternWoolTypes;
import com.brand.blockus.data.family.BlockusBlockFamilies;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.family.BlockFamily;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.util.BlockBiMaps;

import java.util.Map;
import java.util.stream.Stream;

public final class BlockusBlockBiMaps {
  private BlockusBlockBiMaps() {
  }

  static void importFromBlockus() {
    try {
      final Stream<BlockFamily> families = BlockusBlockFamilies.getFamilies();
      families.forEach(blockFamily -> {
        Block baseBlock = blockFamily.getBaseBlock();
        // considering blockus also make variants for netherite blocks, to avoid conflict with
        // Extended Block Shapes, we do not add it to BlockBiMaps.
        if (baseBlock == Blocks.NETHERITE_BLOCK) return;
        Map<BlockFamily.Variant, Block> variants = blockFamily.getVariants();
        for (BlockShape shape : BlockShape.values()) {
          if (shape.vanillaVariant == null) continue;
          Block variant = variants.get(shape.vanillaVariant);
          if (variant != null) {
            BlockBiMaps.of(shape).put(baseBlock, variant);
            BlockBiMaps.BASE_BLOCKS.add(baseBlock);
          }
        }
      });
    } catch (Throwable throwable) {
      ExtShapeBlockus.LOGGER.error("Cannot load BlockFamilies from Blockus mod. This may cause Extended Block Shapes mod to create duplicate block instances.", throwable);
    }
    try {
      BSSWTypes.values().forEach(bsswTypes -> {
        if (bsswTypes.stairs != null) {
          BlockBiMaps.of(BlockShape.STAIRS).put(bsswTypes.block, bsswTypes.stairs);
        }
        if (bsswTypes.slab != null) {
          BlockBiMaps.of(BlockShape.SLAB).put(bsswTypes.block, bsswTypes.slab);
        }
        if (bsswTypes.wall != null) {
          BlockBiMaps.of(BlockShape.WALL).put(bsswTypes.block, bsswTypes.wall);
        }
      });
      BSSTypes.values().forEach(bssTypes -> {
        if (bssTypes.stairs != null) {
          BlockBiMaps.of(BlockShape.STAIRS).put(bssTypes.block, bssTypes.stairs);
        }
        if (bssTypes.slab != null) {
          BlockBiMaps.of(BlockShape.SLAB).put(bssTypes.block, bssTypes.slab);
        }
      });
      PatternWoolTypes.values().forEach(patternWoolTypes -> {
        if (patternWoolTypes.stairs != null) {
          BlockBiMaps.of(BlockShape.STAIRS).put(patternWoolTypes.block, patternWoolTypes.slab);
        }
        if (patternWoolTypes.slab != null) {
          BlockBiMaps.of(BlockShape.SLAB).put(patternWoolTypes.block, patternWoolTypes.slab);
        }
      });
    } catch (Throwable throwable) {
      ExtShapeBlockus.LOGGER.error("Cannot load BSSWTypes and BSSTypes from Blockus mod. This may cause Extended Block Shapes mod to create duplicate block instances.", throwable);
    }

    tryRun(() -> BlockBiMaps.of(BlockShape.PRESSURE_PLATE).put(BlockusBlocks.LIMESTONE.block, BlockusBlocks.LIMESTONE_PRESSURE_PLATE));
    tryRun(() -> BlockBiMaps.of(BlockShape.PRESSURE_PLATE).put(BlockusBlocks.MARBLE.block, BlockusBlocks.MARBLE_PRESSURE_PLATE));
    tryRun(() -> BlockBiMaps.of(BlockShape.PRESSURE_PLATE).put(BlockusBlocks.BLUESTONE.block, BlockusBlocks.BLUESTONE_PRESSURE_PLATE));
    tryRun(() -> BlockBiMaps.of(BlockShape.PRESSURE_PLATE).put(BlockusBlocks.VIRIDITE.block, BlockusBlocks.VIRIDITE_PRESSURE_PLATE));
    tryRun(() -> BlockBiMaps.of(BlockShape.PRESSURE_PLATE).put(BlockusBlocks.POLISHED_NETHERRACK.block, BlockusBlocks.POLISHED_NETHERRACK_PRESSURE_PLATE));
    tryRun(() -> BlockBiMaps.of(BlockShape.PRESSURE_PLATE).put(BlockusBlocks.POLISHED_END_STONE.block, BlockusBlocks.POLISHED_END_STONE_PRESSURE_PLATE));
    tryRun(() -> BlockBiMaps.of(BlockShape.PRESSURE_PLATE).put(BlockusBlocks.POLISHED_TUFF.block, BlockusBlocks.POLISHED_TUFF_PRESSURE_PLATE));
    tryRun(() -> BlockBiMaps.of(BlockShape.PRESSURE_PLATE).put(BlockusBlocks.POLISHED_SCULK.block, BlockusBlocks.POLISHED_SCULK_PRESSURE_PLATE));
    tryRun(() -> BlockBiMaps.of(BlockShape.BUTTON).put(BlockusBlocks.LIMESTONE.block, BlockusBlocks.LIMESTONE_BUTTON));
    tryRun(() -> BlockBiMaps.of(BlockShape.BUTTON).put(BlockusBlocks.MARBLE.block, BlockusBlocks.MARBLE_BUTTON));
    tryRun(() -> BlockBiMaps.of(BlockShape.BUTTON).put(BlockusBlocks.BLUESTONE.block, BlockusBlocks.BLUESTONE_BUTTON));
    tryRun(() -> BlockBiMaps.of(BlockShape.BUTTON).put(BlockusBlocks.VIRIDITE.block, BlockusBlocks.VIRIDITE_BUTTON));
    tryRun(() -> BlockBiMaps.of(BlockShape.BUTTON).put(BlockusBlocks.POLISHED_NETHERRACK.block, BlockusBlocks.POLISHED_NETHERRACK_BUTTON));
    tryRun(() -> BlockBiMaps.of(BlockShape.BUTTON).put(BlockusBlocks.POLISHED_END_STONE.block, BlockusBlocks.POLISHED_END_STONE_BUTTON));
    tryRun(() -> BlockBiMaps.of(BlockShape.BUTTON).put(BlockusBlocks.POLISHED_TUFF.block, BlockusBlocks.POLISHED_TUFF_BUTTON));
    tryRun(() -> BlockBiMaps.of(BlockShape.BUTTON).put(BlockusBlocks.POLISHED_SCULK.block, BlockusBlocks.POLISHED_SCULK_BUTTON));
  }

  private static void tryRun(Runnable runnable) {
    try {
      runnable.run();
    } catch (Throwable ignored) {
    }
  }
}
