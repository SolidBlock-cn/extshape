package pers.solid.extshape.blockus;

import net.minecraft.item.ItemGroups;
import net.minecraft.util.Identifier;
import pers.solid.extshape.VanillaItemGroup;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.util.EntryVariantAppender;

import java.util.Collection;

/**
 * @see pers.solid.extshape.VanillaItemGroup
 */
public final class ExtShapeBlockusItemGroup {

  private ExtShapeBlockusItemGroup() {
  }

  public static void addVanillaGroupRules(Collection<BlockShape> shapes) {
    ItemGroups.getGroups().forEach(group -> {
      if (group.getId().equals(new Identifier("blockus", "blockus_building_blocks"))) {
        new EntryVariantAppender(group, shapes, ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS, ExtShapeBlockusBlocks.BLOCKUS_BLOCKS::contains).appendItems(VanillaItemGroup.getAppendingRule(group));
      } else if (group.getId().equals(new Identifier("blockus", "blockus_colored"))) {
        new EntryVariantAppender(group, shapes, ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS, ExtShapeBlockusBlocks.BLOCKUS_BLOCKS::contains).appendItems(VanillaItemGroup.getAppendingRule(group));
      } else if (group.getId().equals(new Identifier("blockus", "blockus_natural"))) {
        new EntryVariantAppender(group, shapes, ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS, ExtShapeBlockusBlocks.BLOCKUS_BLOCKS::contains).appendItems(VanillaItemGroup.getAppendingRule(group));
      }
    });
  }

  public static void registerEvent() {
    VanillaItemGroup.UPDATE_SHAPES_EVENT.register(() -> ExtShapeBlockusItemGroup.addVanillaGroupRules(ExtShapeConfig.CURRENT_CONFIG.shapesToAddToVanilla));
  }
}
