package pers.solid.extshape.util;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeTags;

/**
 * 用于在原版物品组中添加本模组中的方块。
 */
public record EntryVariantAppender(ItemGroup itemgroup, BlockShape[] shapes) implements ItemGroupEvents.ModifyEntries {

  @Override
  public void modifyEntries(FabricItemGroupEntries entries) {
    if (!ExtShapeConfig.CURRENT_CONFIG.addToVanillaGroups) return;
    final long prevTime = System.currentTimeMillis();
    for (final Block baseBlock : BlockMappings.BASE_BLOCKS) {
      // 当已有物品中有基础方块时，才会将其各种形状的方块加到其后。
      if (itemgroup == ItemGroups.NATURAL && (ExtShapeTags.LOGS.contains(baseBlock) || ExtShapeTags.STEMS.contains(baseBlock))) continue;
      final boolean displays = entries.getDisplayStacks().stream().anyMatch(stack -> baseBlock != null && stack.isOf(baseBlock.asItem()));
      if (!displays) continue;
      @NotNull Block anchor = baseBlock;
      for (BlockShape shape : shapes) {
        final Block block = BlockMappings.getBlockOf(shape, baseBlock);
        if (block == null) continue;
        // 考虑到有些情况下，自然方块收录了建筑方块的基础方块，但没有收入原版已有的形状，故这种情况下，其模组中的各种形状不会加入。
        final Block finalAnchor = anchor;
        if (ExtShapeBlocks.BLOCKS.contains(block) && (anchor == baseBlock || entries.getDisplayStacks().stream().anyMatch(stack -> stack.isOf(finalAnchor.asItem())))) {
          entries.addAfter(anchor, block);
        }
        anchor = block;
      }
    }
    final double afterTime = System.currentTimeMillis();
    ExtShape.LOGGER.info("Time spent on adding items to group {}: {}", itemgroup.getId(), (afterTime - prevTime) / 1000d);
  }
}
