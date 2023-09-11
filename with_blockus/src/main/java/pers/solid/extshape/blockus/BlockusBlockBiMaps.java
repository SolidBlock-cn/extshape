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
        // ignore netherite block because it belongs to vanilla
        if (baseBlock == Blocks.NETHERITE_BLOCK) return;
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
          if (variant != null) {
            BlockBiMaps.setBlockOf(shape, baseBlock, variant);
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
          BlockBiMaps.setBlockOf(BlockShape.STAIRS, bsswTypes.block, bsswTypes.stairs);
        }
        if (bsswTypes.slab != null) {
          BlockBiMaps.setBlockOf(BlockShape.SLAB, bsswTypes.block, bsswTypes.slab);
        }
        if (bsswTypes.wall != null) {
          BlockBiMaps.setBlockOf(BlockShape.WALL, bsswTypes.block, bsswTypes.wall);
        }
      });
      BSSTypes.values().forEach(bssTypes -> {
        if (bssTypes.stairs != null) {
          BlockBiMaps.setBlockOf(BlockShape.STAIRS, bssTypes.block, bssTypes.stairs);
        }
        if (bssTypes.slab != null) {
          BlockBiMaps.setBlockOf(BlockShape.SLAB, bssTypes.block, bssTypes.slab);
        }
      });
      PatternWoolTypes.values().forEach(patternWoolTypes -> {
        if (patternWoolTypes.stairs != null) {
          BlockBiMaps.setBlockOf(BlockShape.STAIRS, patternWoolTypes.block, patternWoolTypes.stairs);
        }
        if (patternWoolTypes.slab != null) {
          BlockBiMaps.setBlockOf(BlockShape.SLAB, patternWoolTypes.block, patternWoolTypes.slab);
        }
      });
    } catch (Throwable throwable) {
      ExtShapeBlockus.LOGGER.error("Cannot load BSSWTypes and BSSTypes from Blockus mod. This may cause Extended Block Shapes mod to create duplicate block instances.", throwable);
    }

    tryRun(() -> BlockBiMaps.setBlockOf(BlockShape.PRESSURE_PLATE, BlockusBlocks.LIMESTONE.block, BlockusBlocks.LIMESTONE_PRESSURE_PLATE));
    tryRun(() -> BlockBiMaps.setBlockOf(BlockShape.PRESSURE_PLATE, BlockusBlocks.MARBLE.block, BlockusBlocks.MARBLE_PRESSURE_PLATE));
    tryRun(() -> BlockBiMaps.setBlockOf(BlockShape.PRESSURE_PLATE, BlockusBlocks.BLUESTONE.block, BlockusBlocks.BLUESTONE_PRESSURE_PLATE));
    tryRun(() -> BlockBiMaps.setBlockOf(BlockShape.PRESSURE_PLATE, BlockusBlocks.VIRIDITE.block, BlockusBlocks.VIRIDITE_PRESSURE_PLATE));
    tryRun(() -> BlockBiMaps.setBlockOf(BlockShape.PRESSURE_PLATE, BlockusBlocks.POLISHED_NETHERRACK.block, BlockusBlocks.POLISHED_NETHERRACK_PRESSURE_PLATE));
    tryRun(() -> BlockBiMaps.setBlockOf(BlockShape.PRESSURE_PLATE, BlockusBlocks.POLISHED_END_STONE.block, BlockusBlocks.POLISHED_END_STONE_PRESSURE_PLATE));
    tryRun(() -> BlockBiMaps.setBlockOf(BlockShape.PRESSURE_PLATE, BlockusBlocks.POLISHED_TUFF.block, BlockusBlocks.POLISHED_TUFF_PRESSURE_PLATE));
    tryRun(() -> BlockBiMaps.setBlockOf(BlockShape.PRESSURE_PLATE, BlockusBlocks.POLISHED_SCULK.block, BlockusBlocks.POLISHED_SCULK_PRESSURE_PLATE));
    tryRun(() -> BlockBiMaps.setBlockOf(BlockShape.BUTTON, BlockusBlocks.LIMESTONE.block, BlockusBlocks.LIMESTONE_BUTTON));
    tryRun(() -> BlockBiMaps.setBlockOf(BlockShape.BUTTON, BlockusBlocks.MARBLE.block, BlockusBlocks.MARBLE_BUTTON));
    tryRun(() -> BlockBiMaps.setBlockOf(BlockShape.BUTTON, BlockusBlocks.BLUESTONE.block, BlockusBlocks.BLUESTONE_BUTTON));
    tryRun(() -> BlockBiMaps.setBlockOf(BlockShape.BUTTON, BlockusBlocks.VIRIDITE.block, BlockusBlocks.VIRIDITE_BUTTON));
    tryRun(() -> BlockBiMaps.setBlockOf(BlockShape.BUTTON, BlockusBlocks.POLISHED_NETHERRACK.block, BlockusBlocks.POLISHED_NETHERRACK_BUTTON));
    tryRun(() -> BlockBiMaps.setBlockOf(BlockShape.BUTTON, BlockusBlocks.POLISHED_END_STONE.block, BlockusBlocks.POLISHED_END_STONE_BUTTON));
    tryRun(() -> BlockBiMaps.setBlockOf(BlockShape.BUTTON, BlockusBlocks.POLISHED_TUFF.block, BlockusBlocks.POLISHED_TUFF_BUTTON));
    tryRun(() -> BlockBiMaps.setBlockOf(BlockShape.BUTTON, BlockusBlocks.POLISHED_SCULK.block, BlockusBlocks.POLISHED_SCULK_BUTTON));
  }

  private static void tryRun(Runnable runnable) {
    try {
      runnable.run();
    } catch (Throwable ignored) {
    }
  }
}
