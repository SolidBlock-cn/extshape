package pers.solid.extshape.blockus.data;

import com.brand.blockus.registry.content.BlockusBlocks;
import com.brand.blockus.registry.tag.BlockusBlockTags;
import com.brand.blockus.registry.tag.BlockusItemTags;
import com.google.common.collect.Iterables;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.blockus.ExtShapeBlockusTags;
import pers.solid.extshape.data.ExtShapeBlockTagProvider;
import pers.solid.extshape.data.ExtShapeItemTagProvider;
import pers.solid.extshape.tag.ExtShapeTags;

import java.util.concurrent.CompletableFuture;

public class ExtShapeBlockusItemTagProvider extends ExtShapeItemTagProvider {
  public ExtShapeBlockusItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture, @NotNull ExtShapeBlockTagProvider blockTagProvider) {
    super(output, completableFuture, blockTagProvider);
  }

  @Override
  protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
    for (TagKey<Block> tag : Iterables.concat(ExtShapeBlockusTags.GLAZED_TERRACOTTA_PILLAR_TAGS.values(), ExtShapeTags.SHAPE_TO_TAG.values())) {
      copyWithSameId(tag);
    }

    this.copy(BlockusBlockTags.ALL_PATTERNED_WOOLS, BlockusItemTags.ALL_PATTERNED_WOOLS);
    copyWithSameId(ExtShapeTags.LOG_BLOCKS);
    copyWithSameId(ExtShapeTags.WOOLEN_BLOCKS);

    addForShapes(ItemTags.NON_FLAMMABLE_WOOD,
        BlockusBlocks.CHARRED.planks,
        BlockusBlocks.HERRINGBONE_CRIMSON_PLANKS,
        BlockusBlocks.HERRINGBONE_WARPED_PLANKS,
        BlockusBlocks.HERRINGBONE_CHARRED_PLANKS);
    addForShapes(ItemTags.PIGLIN_LOVED, BlockusBlocks.GOLD_PLATING.block, BlockusBlocks.GOLD_BRICKS.block);
  }
}
