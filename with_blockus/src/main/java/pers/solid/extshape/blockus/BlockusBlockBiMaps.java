package pers.solid.extshape.blockus;

import com.brand.blockus.content.types.BSSTypes;
import com.brand.blockus.content.types.BSSWTypes;
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
          if (variant != null && !BlockBiMaps.of(shape).containsKey(baseBlock)) {
            // 避免 Blockus 创建的原版方块的一些形状与本模组中的发生冲突
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
    } catch (Throwable throwable) {
      ExtShapeBlockus.LOGGER.error("Cannot load BSSWTypes and BSSTypes from Blockus mod. This may cause Extended Block Shapes mod to create duplicate block instances.", throwable);
    }
  }

}
