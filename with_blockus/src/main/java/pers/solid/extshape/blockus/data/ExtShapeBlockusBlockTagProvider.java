package pers.solid.extshape.blockus.data;

import com.brand.blockus.content.BlockusBlocks;
import com.brand.blockus.tags.BlockusBlockTags;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Streams;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import pers.solid.extshape.blockus.BlockusBlockCollections;
import pers.solid.extshape.blockus.ExtShapeBlockusBlocks;
import pers.solid.extshape.blockus.ExtShapeBlockusTags;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.data.ExtShapeBlockTagProvider;
import pers.solid.extshape.tag.ExtShapeTags;
import pers.solid.extshape.util.BlockBiMaps;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class ExtShapeBlockusBlockTagProvider extends ExtShapeBlockTagProvider {
  public ExtShapeBlockusBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
    // region 形状标签

    for (Block baseBlock : ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS) {
      if (BlockusBlockCollections.GLAZED_TERRACOTTA_PILLARS.contains(baseBlock)) {
        addShapesToCorrespondingTags(baseBlock, ExtShapeBlockusTags.GLAZED_TERRACOTTA_PILLAR_TAGS);
      } else if (BlockusBlockCollections.SMALL_LOGS.contains(baseBlock)) {
        addShapesToCorrespondingTags(baseBlock, ExtShapeTags.SHAPE_TO_LOG_TAG);
      } else if (BlockusBlockCollections.HERRINGBONE_PLANKS.contains(baseBlock) || baseBlock == BlockusBlocks.WHITE_OAK.planks || baseBlock == BlockusBlocks.CHARRED.planks) {
        addShapesToCorrespondingTags(baseBlock, ExtShapeTags.SHAPE_TO_WOODEN_TAG);
      } else {
        addShapesToCorrespondingTags(baseBlock, ExtShapeTags.SHAPE_TO_TAG);
      }
    }
    ExtShapeBlockusTags.GLAZED_TERRACOTTA_PILLAR_TAGS.forEach((blockShape, tag) -> {
      getOrCreateTagBuilder(ExtShapeTags.SHAPE_TO_TAG.get(blockShape)).addTag(tag);
      getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).addTag(tag);
    });

    // endregion

    // region mineable 方块标签

    final Block[] pickaxeMineableBaseBlocks = {
        BlockusBlocks.CHISELED_MUD_BRICKS,
        BlockusBlocks.CRIMSON_WART_BRICKS.block,
        BlockusBlocks.WARPED_WART_BRICKS.block,
        BlockusBlocks.NETHER_TILES.block,
        BlockusBlocks.END_TILES.block,
        BlockusBlocks.CHARCOAL_BLOCK,
        BlockusBlocks.ENDER_BLOCK,
        BlockusBlocks.NETHER_STAR_BLOCK
    };
    addForShapes(BlockTags.PICKAXE_MINEABLE, pickaxeMineableBaseBlocks);

    final Block[] hoeMineableBaseBlocks = {
        BlockusBlocks.CHORUS_BLOCK,
        BlockusBlocks.THATCH.block,
        BlockusBlocks.ROTTEN_FLESH_BLOCK
    };
    addForShapes(BlockTags.HOE_MINEABLE, hoeMineableBaseBlocks);

    getOrCreateTagBuilder(ExtShapeTags.PICKAXE_UNMINEABLE)
        .add(Stream.of(hoeMineableBaseBlocks)
            .map(block -> BlockBiMaps.getBlockOf(BlockShape.WALL, block))
            .filter(Objects::nonNull)
            .toArray(Block[]::new));
    getOrCreateTagBuilder(ExtShapeTags.AXE_UNMINEABLE)
        .add(Streams.concat(
                Arrays.stream(pickaxeMineableBaseBlocks),
                Arrays.stream(hoeMineableBaseBlocks)
            )
            .map(block -> BlockBiMaps.getBlockOf(BlockShape.FENCE_GATE, block))
            .filter(Objects::nonNull)
            .toArray(Block[]::new));

    addForShapes(BlockTags.NEEDS_STONE_TOOL,
        BlockusBlocks.ENDER_BLOCK
    );
    addForShapes(BlockTags.NEEDS_IRON_TOOL,
        BlockusBlocks.NETHER_STAR_BLOCK
    );
//    addForShapes(BlockTags.NEEDS_DIAMOND_TOOL);


    // endregion mineable 方块标签

    // region 特殊方块标签
    addForShapes(BlockusBlockTags.STONE_BLOCKS,
        BlockusBlocks.STONE_TILES.block,
        BlockusBlocks.HERRINGBONE_STONE_BRICKS,
        BlockusBlocks.STONE_CIRCULAR_PAVING
    );

    addForShapes(BlockusBlockTags.ANDESITE_BLOCKS,
        BlockusBlocks.ANDESITE_BRICKS.block,
        BlockusBlocks.CHISELED_ANDESITE_BRICKS,
        BlockusBlocks.HERRINGBONE_ANDESITE_BRICKS,
        BlockusBlocks.ANDESITE_CIRCULAR_PAVING
    );

    addForShapes(BlockusBlockTags.DIORITE_BLOCKS,
        BlockusBlocks.DIORITE_BRICKS.block,
        BlockusBlocks.CHISELED_DIORITE_BRICKS,
        BlockusBlocks.HERRINGBONE_DIORITE_BRICKS,
        BlockusBlocks.DIORITE_CIRCULAR_PAVING
    );

    addForShapes(BlockusBlockTags.GRANITE_BLOCKS,
        BlockusBlocks.GRANITE_BRICKS.block,
        BlockusBlocks.CHISELED_GRANITE_BRICKS,
        BlockusBlocks.HERRINGBONE_GRANITE_BRICKS,
        BlockusBlocks.GRANITE_CIRCULAR_PAVING);

    addForShapes(BlockusBlockTags.DRIPSTONE_BLOCKS,
        BlockusBlocks.POLISHED_DRIPSTONE.block,
        BlockusBlocks.DRIPSTONE_BRICKS.block,
        BlockusBlocks.CHISELED_DRIPSTONE);

    addForShapes(BlockusBlockTags.TUFF_BLOCKS,
        BlockusBlocks.POLISHED_TUFF.block,
        BlockusBlocks.TUFF_BRICKS.block,
        BlockusBlocks.CRACKED_TUFF_BRICKS,
        BlockusBlocks.HERRINGBONE_TUFF_BRICKS,
        BlockusBlocks.TUFF_CIRCULAR_PAVING);

    addForShapes(BlockusBlockTags.DEEPSLATE_BLOCKS,
        BlockusBlocks.MOSSY_DEEPSLATE_BRICKS.block,
        BlockusBlocks.HERRINGBONE_DEEPSLATE_BRICKS,
        BlockusBlocks.DEEPSLATE_CIRCULAR_PAVING);

    addForShapes(BlockusBlockTags.SCULK_BLOCKS,
        BlockusBlocks.POLISHED_SCULK.block,
        BlockusBlocks.SCULK_BRICKS.block,
        BlockusBlocks.CHISELED_SCULK_BRICKS);

    addForShapes(BlockusBlockTags.AMETHYST_BLOCKS,
        BlockusBlocks.POLISHED_AMETHYST.block,
        BlockusBlocks.AMETHYST_BRICKS.block,
        BlockusBlocks.CHISELED_AMETHYST);

    addForShapes(BlockusBlockTags.BLACKSTONE_BLOCKS,
        BlockusBlocks.POLISHED_BLACKSTONE_TILES.block,
        BlockusBlocks.CRIMSON_WARTY_BLACKSTONE_BRICKS.block,
        BlockusBlocks.WARPED_WARTY_BLACKSTONE_BRICKS.block,
        BlockusBlocks.HERRINGBONE_POLISHED_BLACKSTONE_BRICKS,
        BlockusBlocks.POLISHED_BLACKSTONE_CIRCULAR_PAVING
    );

    addForShapes(BlockusBlockTags.BASALT_BLOCKS,
        BlockusBlocks.ROUGH_BASALT.block,
        BlockusBlocks.POLISHED_BASALT_BRICKS.block,
        BlockusBlocks.HERRINGBONE_POLISHED_BASALT_BRICKS,
        BlockusBlocks.POLISHED_BASALT_CIRCULAR_PAVING
    );

    addForShapes(BlockusBlockTags.LIMESTONE,
        BlockusBlocks.LIMESTONE.block,
        BlockusBlocks.POLISHED_LIMESTONE.block,
        BlockusBlocks.LIMESTONE_BRICKS.block,
        BlockusBlocks.SMALL_LIMESTONE_BRICKS.block,
        BlockusBlocks.LIMESTONE_TILES.block,
        BlockusBlocks.CHISELED_LIMESTONE,
        BlockusBlocks.LIMESTONE_SQUARES,
        BlockusBlocks.LIMESTONE_CIRCULAR_PAVING
    );

    addForShapes(BlockusBlockTags.MARBLE,
        BlockusBlocks.MARBLE.block,
        BlockusBlocks.POLISHED_MARBLE.block,
        BlockusBlocks.MARBLE_BRICKS.block,
        BlockusBlocks.SMALL_MARBLE_BRICKS.block,
        BlockusBlocks.MARBLE_TILES.block,
        BlockusBlocks.CHISELED_MARBLE,
        BlockusBlocks.MARBLE_SQUARES,
        BlockusBlocks.MARBLE_CIRCULAR_PAVING
    );

    addForShapes(BlockusBlockTags.BLUESTONE,
        BlockusBlocks.BLUESTONE.block,
        BlockusBlocks.POLISHED_BLUESTONE.block,
        BlockusBlocks.BLUESTONE_BRICKS.block,
        BlockusBlocks.SMALL_BLUESTONE_BRICKS.block,
        BlockusBlocks.BLUESTONE_TILES.block,
        BlockusBlocks.CHISELED_BLUESTONE,
        BlockusBlocks.BLUESTONE_SQUARES,
        BlockusBlocks.BLUESTONE_CIRCULAR_PAVING
    );

    addForShapes(BlockusBlockTags.VIRIDITE,
        BlockusBlocks.VIRIDITE.block,
        BlockusBlocks.POLISHED_VIRIDITE.block,
        BlockusBlocks.VIRIDITE_BRICKS.block,
        BlockusBlocks.SMALL_VIRIDITE_BRICKS.block,
        BlockusBlocks.VIRIDITE_TILES.block,
        BlockusBlocks.CHISELED_VIRIDITE,
        BlockusBlocks.VIRIDITE_SQUARES,
        BlockusBlocks.VIRIDITE_CIRCULAR_PAVING
    );

    addForShapes(BlockusBlockTags.LAVA_BRICKS,
        BlockusBlocks.LAVA_BRICKS.block,
        BlockusBlocks.CHISELED_LAVA_BRICKS
    );
    addForShapes(BlockusBlockTags.LAVA_POLISHED_BLACKSTONE_BRICKS,
        BlockusBlocks.LAVA_POLISHED_BLACKSTONE_BRICKS.block,
        BlockusBlocks.CHISELED_LAVA_POLISHED_BLACKSTONE
    );

    addForShapes(BlockusBlockTags.SNOW_BRICKS, BlockusBlocks.SNOW_BRICKS.block);
    addForShapes(BlockusBlockTags.ICE_BRICKS, BlockusBlocks.ICE_BRICKS);

    addForShapes(BlockusBlockTags.OBSIDIAN,
        BlockusBlocks.OBSIDIAN_BRICKS.block,
        BlockusBlocks.SMALL_OBSIDIAN_BRICKS.block,
        BlockusBlocks.OBSIDIAN_CIRCULAR_PAVING,
        BlockusBlocks.GLOWING_OBSIDIAN
    );

    addForShapes(BlockusBlockTags.NETHERRACK_BLOCKS,
        BlockusBlocks.POLISHED_NETHERRACK.block,
        BlockusBlocks.NETHERRACK_BRICKS.block,
        BlockusBlocks.NETHERRACK_CIRCULAR_PAVING
    );

    addForShapes(BlockusBlockTags.QUARTZ_BLOCKS,
        BlockusBlocks.QUARTZ_TILES.block,
        BlockusBlocks.QUARTZ_CIRCULAR_PAVING);

    addForShapes(BlockusBlockTags.MAGMA_BRICKS,
        BlockusBlocks.MAGMA_BRICKS.block,
        BlockusBlocks.SMALL_MAGMA_BRICKS.block,
        BlockusBlocks.CHISELED_MAGMA_BRICKS
    );

    addForShapes(BlockusBlockTags.BLAZE_BRICKS,
        BlockusBlocks.BLAZE_BRICKS.block,
        BlockusBlocks.BLAZE_LANTERN);

    addForShapes(BlockusBlockTags.NETHER_BRICKS,
        BlockusBlocks.POLISHED_NETHER_BRICKS,
        BlockusBlocks.POLISHED_RED_NETHER_BRICKS,
        BlockusBlocks.HERRINGBONE_NETHER_BRICKS,
        BlockusBlocks.HERRINGBONE_RED_NETHER_BRICKS,
        BlockusBlocks.CHARRED_NETHER_BRICKS.block,
        BlockusBlocks.POLISHED_CHARRED_NETHER_BRICKS,
        BlockusBlocks.TEAL_NETHER_BRICKS.block,
        BlockusBlocks.POLISHED_TEAL_NETHER_BRICKS,
        BlockusBlocks.HERRINGBONE_TEAL_NETHER_BRICKS
    );

    addForShapes(BlockusBlockTags.PRISMARINE_BLOCKS,
        BlockusBlocks.CHISELED_PRISMARINE,
        BlockusBlocks.PRISMARINE_CIRCULAR_PAVING,
        BlockusBlocks.CHISELED_DARK_PRISMARINE
    );

    addForShapes(BlockusBlockTags.BRICKS_BLOCKS,
        BlockusBlocks.LARGE_BRICKS.block,
        BlockusBlocks.HERRINGBONE_BRICKS,
        BlockusBlocks.SOAKED_BRICKS.block,
        BlockusBlocks.HERRINGBONE_SOAKED_BRICKS,
        BlockusBlocks.CHARRED_BRICKS.block,
        BlockusBlocks.HERRINGBONE_CHARRED_BRICKS,
        BlockusBlocks.SANDY_BRICKS.block,
        BlockusBlocks.HERRINGBONE_SANDY_BRICKS
    );

    addForShapes(BlockusBlockTags.SANDSTONE,
        BlockusBlocks.ROUGH_SANDSTONE.block,
        BlockusBlocks.SANDSTONE_BRICKS.block,
        BlockusBlocks.SMALL_SANDSTONE_BRICKS.block
    );

    addForShapes(BlockusBlockTags.RED_SANDSTONE,
        BlockusBlocks.ROUGH_RED_SANDSTONE.block,
        BlockusBlocks.RED_SANDSTONE_BRICKS.block,
        BlockusBlocks.SMALL_RED_SANDSTONE_BRICKS.block
    );

    addForShapes(BlockusBlockTags.SOUL_SANDSTONE,
        BlockusBlocks.ROUGH_SOUL_SANDSTONE.block,
        BlockusBlocks.SOUL_SANDSTONE_BRICKS.block,
        BlockusBlocks.SMALL_SOUL_SANDSTONE_BRICKS.block,
        BlockusBlocks.SMOOTH_SOUL_SANDSTONE.block,
        BlockusBlocks.CUT_SOUL_SANDSTONE,
        BlockusBlocks.CHISELED_SOUL_SANDSTONE
    );

    addForShapes(BlockusBlockTags.RAINBOW_BLOCKS,
        BlockusBlocks.RAINBOW_BLOCK,
        BlockusBlocks.RAINBOW_BRICKS.block);

    addForShapes(BlockusBlockTags.HONEYCOMB_BLOCKS, BlockusBlocks.HONEYCOMB_BRICKS.block);

    addForShapes(BlockusBlockTags.PURPUR_BLOCKS,
        BlockusBlocks.PURPUR_BRICKS.block,
        BlockusBlocks.SMALL_PURPUR_BRICKS.block,
        BlockusBlocks.POLISHED_PURPUR.block,
        BlockusBlocks.CHISELED_PURPUR,
        BlockusBlocks.PURPUR_SQUARES
    );

    addForShapes(BlockusBlockTags.PHANTOM_PURPUR_BLOCKS,
        BlockusBlocks.PHANTOM_PURPUR_BRICKS.block,
        BlockusBlocks.SMALL_PHANTOM_PURPUR_BRICKS.block,
        BlockusBlocks.PHANTOM_PURPUR_BLOCK.block,
        BlockusBlocks.POLISHED_PHANTOM_PURPUR.block,
        BlockusBlocks.CHISELED_PHANTOM_PURPUR,
        BlockusBlocks.PHANTOM_PURPUR_SQUARES
    );

    addForShapes(BlockusBlockTags.END_STONE_BLOCKS,
        BlockusBlocks.POLISHED_END_STONE.block,
        BlockusBlocks.SMALL_END_STONE_BRICKS.block,
        BlockusBlocks.CHISELED_END_STONE_BRICKS,
        BlockusBlocks.HERRINGBONE_END_STONE_BRICKS
    );

    addForShapes(ExtShapeTags.LOG_BLOCKS,
        BlockusBlocks.WHITE_OAK_LOG,
        BlockusBlocks.STRIPPED_WHITE_OAK_LOG,
        BlockusBlocks.WHITE_OAK_WOOD,
        BlockusBlocks.STRIPPED_WHITE_OAK_WOOD
    );

    addForShapes(BlockusBlockTags.CHOCOLATE_BLOCKS,
        BlockusBlocks.CHOCOLATE_BLOCK.block,
        BlockusBlocks.CHOCOLATE_BRICKS.block,
        BlockusBlocks.CHOCOLATE_SQUARES
    );


    addForShapes(BlockusBlockTags.STAINED_STONE_BRICKS, Iterables.transform(BlockusBlockCollections.STAINED_STONE_BRICKS, input -> input.block));

    addForShapes(BlockusBlockTags.CONCRETE_BLOCKS, Iterables.transform(BlockusBlockCollections.CONCRETE_BRICKS, input -> input.block));
    addForShapes(BlockusBlockTags.CONCRETE_BLOCKS, Iterables.transform(BlockusBlockCollections.CONCRETE_BRICKS, input -> input.chiseled));

    addForShapes(BlockusBlockTags.SHINGLES, BlockusBlocks.SHINGLES.block);
    addForShapes(BlockusBlockTags.SHINGLES, Iterables.transform(BlockusBlockCollections.TINTED_SHINGLES, input -> input.block));

    addForShapes(BlockusBlockTags.ALL_PATTERNED_WOOLS, Iterables.transform(BlockusBlockCollections.PATTERNED_WOOLS, input -> input.block));
    addForShapes(ExtShapeTags.WOOLEN_BLOCKS, Iterables.transform(BlockusBlockCollections.PATTERNED_WOOLS, input -> input.block));

    addForShapes(BlockusBlockTags.THATCH, BlockusBlocks.THATCH.block);

    addForShapes(BlockusBlockTags.PLATINGS,
        BlockusBlocks.IRON_PLATING.block,
        BlockusBlocks.GOLD_PLATING.block);

    addForShapes(BlockTags.INFINIBURN_OVERWORLD, BlockusBlocks.CHARCOAL_BLOCK);
    addForShapes(BlockTags.DRAGON_IMMUNE, BlockusBlocks.NETHER_STAR_BLOCK);

    // endregion 特殊方块标签

    // region 其他通用方块标签
    addForShapes(BlockTags.GUARDED_BY_PIGLINS,
        BlockusBlocks.GOLD_PLATING.block
    );

    // endregion 其他通用方块标签
  }

  @Override
  protected void checkValidBaseBlock(Block baseBlock) {
    Preconditions.checkArgument(ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS.contains(baseBlock), "%s is not a base block for Extended Block Shapes - Blockus", baseBlock);
  }

  @Override
  protected boolean isValidBlock(Block block) {
    return ExtShapeBlockusBlocks.BLOCKUS_BLOCKS.contains(block);
  }
}
