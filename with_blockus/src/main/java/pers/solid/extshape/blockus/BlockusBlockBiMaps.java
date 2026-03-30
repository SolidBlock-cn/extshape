package pers.solid.extshape.blockus;

import com.brand.blockus.datagen.family.BlockusFamilies;
import com.brand.blockus.registry.content.BlockusBlocks;
import com.brand.blockus.registry.content.bundles.BSSWBundle;
import com.brand.blockus.registry.content.bundles.ConcreteBundle;
import com.brand.blockus.registry.content.bundles.WoolBundle;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.util.BlockBiMaps;

import java.util.Map;
import java.util.stream.Stream;

public final class BlockusBlockBiMaps {
  private BlockusBlockBiMaps() {
  }

  static void importFromBlockus() {
    final Stream<BlockFamily> families = BlockusFamilies.getAllFamilies();
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
    BSSWBundle.values().forEach(bsswBundle -> {
      if (bsswBundle.stairs() != null) {
        BlockBiMaps.setBlockOf(BlockShape.STAIRS, bsswBundle.block(), bsswBundle.stairs());
      }
      if (bsswBundle.slab() != null) {
        BlockBiMaps.setBlockOf(BlockShape.SLAB, bsswBundle.block(), bsswBundle.slab());
      }
      if (bsswBundle.wall() != null) {
        BlockBiMaps.setBlockOf(BlockShape.WALL, bsswBundle.block(), bsswBundle.wall());
      }
    });
    WoolBundle.values().forEach(woolBundle -> woolBundle.colorMap().values().forEach(woolVariants -> {
      if (woolVariants.stairs() != null) {
        BlockBiMaps.setBlockOf(BlockShape.STAIRS, woolVariants.block(), woolVariants.stairs());
      }
      if (woolVariants.slab() != null) {
        BlockBiMaps.setBlockOf(BlockShape.SLAB, woolVariants.block(), woolVariants.slab());
      }
    }));
    ConcreteBundle.values().forEach(concreteBundle -> concreteBundle.colorMap().values().forEach(concreteVariants -> {
      if (concreteVariants.stairs() != null) {
        BlockBiMaps.setBlockOf(BlockShape.STAIRS, concreteVariants.block(), concreteVariants.stairs());
      }
      if (concreteVariants.slab() != null) {
        BlockBiMaps.setBlockOf(BlockShape.SLAB, concreteVariants.block(), concreteVariants.slab());
      }
      if (concreteVariants.wall() != null) {
        BlockBiMaps.setBlockOf(BlockShape.WALL, concreteVariants.block(), concreteVariants.wall());
      }
    }));

    BlockBiMaps.setBlockOf(BlockShape.PRESSURE_PLATE, BlockusBlocks.LIMESTONE.block(), BlockusBlocks.LIMESTONE_PRESSURE_PLATE);
    BlockBiMaps.setBlockOf(BlockShape.PRESSURE_PLATE, BlockusBlocks.MARBLE.block(), BlockusBlocks.MARBLE_PRESSURE_PLATE);
    BlockBiMaps.setBlockOf(BlockShape.PRESSURE_PLATE, BlockusBlocks.BLUESTONE.block(), BlockusBlocks.BLUESTONE_PRESSURE_PLATE);
    BlockBiMaps.setBlockOf(BlockShape.PRESSURE_PLATE, BlockusBlocks.VIRIDITE.block(), BlockusBlocks.VIRIDITE_PRESSURE_PLATE);
    BlockBiMaps.setBlockOf(BlockShape.PRESSURE_PLATE, BlockusBlocks.POLISHED_NETHERRACK.block(), BlockusBlocks.POLISHED_NETHERRACK_PRESSURE_PLATE);
    BlockBiMaps.setBlockOf(BlockShape.PRESSURE_PLATE, BlockusBlocks.POLISHED_END_STONE.block(), BlockusBlocks.POLISHED_END_STONE_PRESSURE_PLATE);
    BlockBiMaps.setBlockOf(BlockShape.PRESSURE_PLATE, BlockusBlocks.POLISHED_SCULK.block(), BlockusBlocks.POLISHED_SCULK_PRESSURE_PLATE);
    BlockBiMaps.setBlockOf(BlockShape.BUTTON, BlockusBlocks.LIMESTONE.block(), BlockusBlocks.LIMESTONE_BUTTON);
    BlockBiMaps.setBlockOf(BlockShape.BUTTON, BlockusBlocks.MARBLE.block(), BlockusBlocks.MARBLE_BUTTON);
    BlockBiMaps.setBlockOf(BlockShape.BUTTON, BlockusBlocks.BLUESTONE.block(), BlockusBlocks.BLUESTONE_BUTTON);
    BlockBiMaps.setBlockOf(BlockShape.BUTTON, BlockusBlocks.VIRIDITE.block(), BlockusBlocks.VIRIDITE_BUTTON);
    BlockBiMaps.setBlockOf(BlockShape.BUTTON, BlockusBlocks.POLISHED_NETHERRACK.block(), BlockusBlocks.POLISHED_NETHERRACK_BUTTON);
    BlockBiMaps.setBlockOf(BlockShape.BUTTON, BlockusBlocks.POLISHED_END_STONE.block(), BlockusBlocks.POLISHED_END_STONE_BUTTON);
    BlockBiMaps.setBlockOf(BlockShape.BUTTON, BlockusBlocks.POLISHED_SCULK.block(), BlockusBlocks.POLISHED_SCULK_BUTTON);

    BlockBiMaps.setBlockOf(BlockShape.SLAB, BlockusBlocks.CUT_SOUL_SANDSTONE, BlockusBlocks.CUT_SOUL_SANDSTONE_SLAB);
    BlockBiMaps.setBlockOf(BlockShape.WALL, BlockusBlocks.ICE_BRICKS, BlockusBlocks.ICE_BRICK_WALL);
  }
}
