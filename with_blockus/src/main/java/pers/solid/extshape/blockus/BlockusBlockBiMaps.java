package pers.solid.extshape.blockus;

import com.brand.blockus.datagen.family.BlockusFamilies;
import com.brand.blockus.registry.content.BlockusBlocks;
import com.brand.blockus.registry.content.bundles.BSSWBundle;
import com.brand.blockus.registry.content.bundles.ConcreteBundle;
import com.brand.blockus.registry.content.bundles.WoolBundle;
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
    final Stream<BlockFamily> families = BlockusFamilies.getFamilies();
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
    BSSWBundle.values().forEach(bsswTypes -> {
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
    WoolBundle.values().forEach(woolTypes -> {
      if (woolTypes.stairs != null) {
        BlockBiMaps.setBlockOf(BlockShape.STAIRS, woolTypes.block, woolTypes.stairs);
      }
      if (woolTypes.slab != null) {
        BlockBiMaps.setBlockOf(BlockShape.SLAB, woolTypes.block, woolTypes.slab);
      }
    });
    ConcreteBundle.values().forEach(concreteTypes -> {
      if (concreteTypes.stairs != null) {
        BlockBiMaps.setBlockOf(BlockShape.STAIRS, concreteTypes.block, concreteTypes.stairs);
      }
      if (concreteTypes.slab != null) {
        BlockBiMaps.setBlockOf(BlockShape.SLAB, concreteTypes.block, concreteTypes.slab);
      }
      if (concreteTypes.wall != null) {
        BlockBiMaps.setBlockOf(BlockShape.WALL, concreteTypes.block, concreteTypes.wall);
      }
    });

    BlockBiMaps.setBlockOf(BlockShape.PRESSURE_PLATE, BlockusBlocks.LIMESTONE.block, BlockusBlocks.LIMESTONE_PRESSURE_PLATE);
    BlockBiMaps.setBlockOf(BlockShape.PRESSURE_PLATE, BlockusBlocks.MARBLE.block, BlockusBlocks.MARBLE_PRESSURE_PLATE);
    BlockBiMaps.setBlockOf(BlockShape.PRESSURE_PLATE, BlockusBlocks.BLUESTONE.block, BlockusBlocks.BLUESTONE_PRESSURE_PLATE);
    BlockBiMaps.setBlockOf(BlockShape.PRESSURE_PLATE, BlockusBlocks.VIRIDITE.block, BlockusBlocks.VIRIDITE_PRESSURE_PLATE);
    BlockBiMaps.setBlockOf(BlockShape.PRESSURE_PLATE, BlockusBlocks.POLISHED_NETHERRACK.block, BlockusBlocks.POLISHED_NETHERRACK_PRESSURE_PLATE);
    BlockBiMaps.setBlockOf(BlockShape.PRESSURE_PLATE, BlockusBlocks.POLISHED_END_STONE.block, BlockusBlocks.POLISHED_END_STONE_PRESSURE_PLATE);
    BlockBiMaps.setBlockOf(BlockShape.BUTTON, BlockusBlocks.LIMESTONE.block, BlockusBlocks.LIMESTONE_BUTTON);
    BlockBiMaps.setBlockOf(BlockShape.BUTTON, BlockusBlocks.MARBLE.block, BlockusBlocks.MARBLE_BUTTON);
    BlockBiMaps.setBlockOf(BlockShape.BUTTON, BlockusBlocks.BLUESTONE.block, BlockusBlocks.BLUESTONE_BUTTON);
    BlockBiMaps.setBlockOf(BlockShape.BUTTON, BlockusBlocks.VIRIDITE.block, BlockusBlocks.VIRIDITE_BUTTON);
    BlockBiMaps.setBlockOf(BlockShape.BUTTON, BlockusBlocks.POLISHED_NETHERRACK.block, BlockusBlocks.POLISHED_NETHERRACK_BUTTON);
    BlockBiMaps.setBlockOf(BlockShape.BUTTON, BlockusBlocks.POLISHED_END_STONE.block, BlockusBlocks.POLISHED_END_STONE_BUTTON);
  }
}
