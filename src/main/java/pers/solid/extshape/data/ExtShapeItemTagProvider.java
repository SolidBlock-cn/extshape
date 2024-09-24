package pers.solid.extshape.data;

import com.google.common.collect.Iterables;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.tag.ExtShapeTags;
import pers.solid.extshape.util.BlockBiMaps;
import pers.solid.extshape.util.BlockCollections;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class ExtShapeItemTagProvider extends FabricTagProvider.ItemTagProvider {

  protected final @NotNull ExtShapeBlockTagProvider blockTagProvider;

  public ExtShapeItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture, @NotNull ExtShapeBlockTagProvider blockTagProvider) {
    super(output, completableFuture, blockTagProvider);
    this.blockTagProvider = blockTagProvider;
  }

  @Override
  protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
    copy(BlockTags.DAMPENS_VIBRATIONS, ItemTags.DAMPENS_VIBRATIONS);
    copyWithSameId(ExtShapeTags.WOOLEN_BLOCKS);
    copyWithSameId(ExtShapeTags.WOODEN_BLOCKS);
    copyWithSameId(ExtShapeTags.LOG_BLOCKS);

    for (TagKey<Block> tag : Iterables.concat(
        ExtShapeTags.SHAPE_TO_TAG.values(),
        ExtShapeTags.SHAPE_TO_WOODEN_TAG.values(),
        ExtShapeTags.SHAPE_TO_LOG_TAG.values(),
        ExtShapeTags.SHAPE_TO_WOOLEN_TAG.values(),
        ExtShapeTags.SHAPE_TO_CONCRETE_TAG.values(),
        ExtShapeTags.SHAPE_TO_TERRACOTTA_TAG.values(),
        ExtShapeTags.SHAPE_TO_STAINED_TERRACOTTA_TAG.values()
    )) {
      copyWithSameId(tag);
    }
    copyWithSameId(ExtShapeTags.GLAZED_TERRACOTTA_SLABS);
    copy(ConventionalBlockTags.UNCOLORED_SANDSTONE_STAIRS, ConventionalItemTags.UNCOLORED_SANDSTONE_STAIRS);
    copy(ConventionalBlockTags.UNCOLORED_SANDSTONE_SLABS, ConventionalItemTags.UNCOLORED_SANDSTONE_SLABS);
    copy(ConventionalBlockTags.RED_SANDSTONE_STAIRS, ConventionalItemTags.RED_SANDSTONE_STAIRS);
    copy(ConventionalBlockTags.RED_SANDSTONE_SLABS, ConventionalItemTags.RED_SANDSTONE_SLABS);

    for (int i = 0; i < BlockCollections.DYED_TAGS.size(); i++) {
      copy(BlockCollections.DYED_TAGS.get(i), BlockCollections.DYED_ITEM_TAGS.get(i));
    }

    addForShapes(ItemTags.NON_FLAMMABLE_WOOD, Iterables.concat(BlockCollections.STEMS,
        BlockCollections.STRIPPED_STEMS,
        BlockCollections.HYPHAES,
        BlockCollections.STRIPPED_HYPHAES,
        Arrays.asList(Blocks.CRIMSON_PLANKS, Blocks.WARPED_PLANKS)
    ));
    addForShapes(ItemTags.PIGLIN_LOVED, Blocks.GOLD_BLOCK, Blocks.RAW_GOLD_BLOCK);
  }

  protected void copyWithSameId(TagKey<Block> blockTag) {
    copy(blockTag, TagKey.of(RegistryKeys.ITEM, blockTag.id()));
  }

  protected void addForShapes(TagKey<Item> tag, Block baseBlock) {
    checkValidBaseBlock(baseBlock);
    final var builder = getOrCreateTagBuilder(tag);
    for (BlockShape shape : BlockShape.values()) {
      final Block block = BlockBiMaps.getBlockOf(shape, baseBlock);
      if (isValidBlock(block)) {
        builder.add(block.asItem());
      }
    }
  }

  protected void addForShapes(TagKey<Item> tag, Block... baseBlocks) {
    addForShapes(tag, Arrays.asList(baseBlocks));
  }

  protected void addForShapes(TagKey<Item> tag, Iterable<Block> baseBlocks) {
    final var builder = getOrCreateTagBuilder(tag);
    for (Block baseBlock : baseBlocks) {
      checkValidBaseBlock(baseBlock);
      for (BlockShape shape : BlockShape.values()) {
        final Block block = BlockBiMaps.getBlockOf(shape, baseBlock);
        if (isValidBlock(block)) {
          builder.add(block.asItem());
        }
      }
    }
  }


  /**
   * 检测方块是否为模组中的有效基础方块，如果不是本模组中的基础方块，则抛出错误。
   *
   * @throws IllegalArgumentException 模组不是本模组中使用的基础方块。
   * @implNote 其他模组继承此类时需要重写此方法。
   */
  @Contract("null -> fail")
  protected void checkValidBaseBlock(Block baseBlock) {
    blockTagProvider.checkValidBaseBlock(baseBlock);
  }

  /**
   * 检测方块是否为模组中的方块，并返回 true 或 false。
   *
   * @implNote 其他模组继承此类时需要重写此方法。
   */
  @Contract("null -> false")
  protected boolean isValidBlock(Block block) {
    return blockTagProvider.isValidBlock(block);
  }
}
