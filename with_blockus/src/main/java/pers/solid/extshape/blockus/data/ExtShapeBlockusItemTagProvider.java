package pers.solid.extshape.blockus.data;

import com.brand.blockus.registry.content.BlockusBlocks;
import com.brand.blockus.registry.tag.BlockusBlockTags;
import com.brand.blockus.registry.tag.BlockusItemTags;
import com.brand.blockus.utils.helper.WoodMaps;
import com.google.common.collect.Iterables;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import pers.solid.extshape.blockus.ExtShapeBlockusTags;
import pers.solid.extshape.data.ExtShapeBlockTagProvider;
import pers.solid.extshape.data.ExtShapeItemTagProvider;
import pers.solid.extshape.tag.ExtShapeTags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ExtShapeBlockusItemTagProvider extends ExtShapeItemTagProvider {
  public ExtShapeBlockusItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture, ExtShapeBlockTagProvider blockTagProvider) {
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
        BlockusBlocks.CHARRED.planks(),
        BlockusBlocks.SMALL_LOGS.get("crimson"),
        BlockusBlocks.SMALL_LOGS.get("warped")
    );
    addForShapes(ItemTags.NON_FLAMMABLE_WOOD, Iterables.concat(Iterables.transform(List.of(WoodMaps.CRIMSON, WoodMaps.WARPED, WoodMaps.CHARRED), woodMaps -> List.of(BlockusBlocks.HERRINGBONE_PLANKS.get(woodMaps.getId()), BlockusBlocks.WOODEN_MOSAIC.get(woodMaps.getId()).block(), BlockusBlocks.MOSSY_PLANKS.get(woodMaps.getId()).block()))));

    addForShapes(ItemTags.PIGLIN_LOVED, BlockusBlocks.GOLD_PLATING.block(), BlockusBlocks.GOLD_BRICKS.block());
  }
}
