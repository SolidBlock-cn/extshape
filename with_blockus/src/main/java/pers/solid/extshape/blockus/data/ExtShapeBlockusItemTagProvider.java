package pers.solid.extshape.blockus.data;

import com.brand.blockus.registry.content.BlockusBlocks;
import com.brand.blockus.registry.tag.BlockusBlockTags;
import com.brand.blockus.registry.tag.BlockusItemTags;
import com.brand.blockus.utils.helper.WoodMaps;
import com.google.common.collect.Iterables;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.blockus.ExtShapeBlockusTags;
import pers.solid.extshape.data.ExtShapeBlockTagProvider;
import pers.solid.extshape.data.ExtShapeItemTagProvider;
import pers.solid.extshape.tag.ExtShapeTags;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class ExtShapeBlockusItemTagProvider extends ExtShapeItemTagProvider {
  public ExtShapeBlockusItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, @NotNull ExtShapeBlockTagProvider blockTagProvider) {
    super(output, completableFuture, blockTagProvider);
  }

  @Override
  protected void addTags(HolderLookup.Provider wrapperLookup) {
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
    addForShapes(ItemTags.NON_FLAMMABLE_WOOD, () -> Stream.of(WoodMaps.CRIMSON, WoodMaps.WARPED, WoodMaps.CHARRED).map(WoodMaps::getId).flatMap(string -> Stream.of(BlockusBlocks.HERRINGBONE_PLANKS.get(string), BlockusBlocks.WOODEN_MOSAIC.get(string).block(), BlockusBlocks.MOSSY_PLANKS.get(string).block())).iterator());
    addForShapes(ItemTags.PIGLIN_LOVED, BlockusBlocks.GOLD_PLATING.block(), BlockusBlocks.GOLD_BRICKS.block());
  }
}
