package pers.solid.extshape.data;

import com.google.common.base.Preconditions;
import com.google.common.collect.*;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;
import pers.solid.extshape.block.CopperManager;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.tag.ExtShapeTags;
import pers.solid.extshape.util.BlockBiMaps;
import pers.solid.extshape.util.BlockCollections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class ExtShapeBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
  /**
   * 各类型方块的标签，不包括染色陶瓦的，因为染色陶瓦的标签已经被添加到了陶瓦的标签中。
   */
  public static final ImmutableList<ImmutableMap<BlockShape, TagKey<Block>>> TYPE_SHAPE_TAGS = ImmutableList.of(ExtShapeTags.SHAPE_TO_WOODEN_TAG, ExtShapeTags.SHAPE_TO_LOG_TAG, ExtShapeTags.SHAPE_TO_WOOLEN_TAG, ExtShapeTags.SHAPE_TO_CONCRETE_TAG, ExtShapeTags.SHAPE_TO_TERRACOTTA_TAG);

  /**
   * 此集内的方块会被加入 {#code stone_pressure_plates} 和 {@code stone_buttons}。
   */
  public static final ImmutableSet<Block> STONE_BASE_BLOCKS = ImmutableSet.of(Blocks.STONE, Blocks.SMOOTH_STONE, Blocks.BLACKSTONE, Blocks.POLISHED_BLACKSTONE, Blocks.CHISELED_POLISHED_BLACKSTONE, Blocks.GILDED_BLACKSTONE);

  public ExtShapeBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
    super(output, registriesFuture);
  }

  @Override
  protected void addTags(HolderLookup.Provider wrapperLookup) {

    // region 基础形状部分

    valueLookupBuilder(BlockTags.OCCLUDES_VIBRATION_SIGNALS).addTag(ExtShapeTags.WOOLEN_BLOCKS);

    // 将原木的所有标签加入 log_blocks
    for (TagKey<Block> tag : ExtShapeTags.SHAPE_TO_LOG_TAG.values()) {
      valueLookupBuilder(ExtShapeTags.LOG_BLOCKS).addTag(tag);
    }

    // 将各类染色陶瓦标签添加到非染色陶瓦标签
    ExtShapeTags.SHAPE_TO_TERRACOTTA_TAG.forEach((blockShape, tag) -> {
      final TagKey<Block> stained = ExtShapeTags.SHAPE_TO_STAINED_TERRACOTTA_TAG.get(blockShape);
      Objects.requireNonNull(stained);
      valueLookupBuilder(tag).addTag(stained);
    });

    // 将各类具体形状的标签加入形状的总标签，但是 log 的标签加入对应的 wooden 标签
    for (Map.Entry<BlockShape, TagKey<Block>> entry : ExtShapeTags.SHAPE_TO_TAG.entrySet()) {
      final BlockShape shape = entry.getKey();
      final TagKey<Block> shapeTag = entry.getValue();
      final var builder = valueLookupBuilder(shapeTag);
      for (ImmutableMap<BlockShape, TagKey<Block>> map : TYPE_SHAPE_TAGS) {
        final TagKey<Block> typeShapeTag = map.get(shape);
        if (typeShapeTag != null && ExtShapeTags.SHAPE_TO_WOODEN_TAG.containsKey(shape) && map == ExtShapeTags.SHAPE_TO_LOG_TAG) {
          valueLookupBuilder(ExtShapeTags.SHAPE_TO_WOODEN_TAG.get(shape)).addTag(typeShapeTag);
          continue;
        }
        if (typeShapeTag != null && !typeShapeTag.location().getNamespace().equals(Identifier.DEFAULT_NAMESPACE)) {
          builder.addTag(typeShapeTag);
        }
      }
    }
    valueLookupBuilder(BlockTags.SLABS).addTag(ExtShapeTags.GLAZED_TERRACOTTA_SLABS);

    for (TagKey<Block> tag : ExtShapeTags.SHAPE_TO_WOOLEN_TAG.values()) {
      valueLookupBuilder(ExtShapeTags.WOOLEN_BLOCKS).addTag(tag);
    }
    valueLookupBuilder(BlockTags.SHEARS_MAJOR_BREAKING_SPEED).addTag(ExtShapeTags.WOOLEN_BLOCKS);
    valueLookupBuilder(ExtShapeTags.WOODEN_BLOCKS)
        .addTag(ExtShapeTags.LOG_BLOCKS);
    for (TagKey<Block> tag : ExtShapeTags.SHAPE_TO_WOODEN_TAG.values()) {
      valueLookupBuilder(ExtShapeTags.WOODEN_BLOCKS).addTag(tag);
    }

    // 基础形状部分

    // mineable 部分

    valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE).add(
        ExtShapeBlocks.PETRIFIED_OAK_PLANKS,
        ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB
    );
    addForShapes(BlockTags.MINEABLE_WITH_PICKAXE, Iterables.concat(
        BlockCollections.STONES,
        BlockCollections.UNCOLORED_SANDSTONES,
        BlockCollections.RED_SANDSTONES,
        CopperManager.COPPER_BLOCKS,
        CopperManager.CUT_COPPER_BLOCKS,
        CopperManager.WAXED_COPPER_BLOCKS,
        CopperManager.WAXED_CUT_COPPER_BLOCKS,
        ImmutableSet.of(
            Blocks.COBBLESTONE,
            Blocks.LAPIS_BLOCK,
            Blocks.GOLD_BLOCK,
            Blocks.IRON_BLOCK,
            Blocks.BRICKS,
            Blocks.MOSSY_COBBLESTONE,
            Blocks.OBSIDIAN,
            Blocks.DIAMOND_BLOCK,
            Blocks.AMETHYST_BLOCK,
            Blocks.NETHERRACK,
            Blocks.STONE_BRICKS,
            Blocks.MOSSY_STONE_BRICKS,
            Blocks.PACKED_MUD,
            Blocks.MUD_BRICKS,
            Blocks.RESIN_BRICKS,
            Blocks.NETHER_BRICKS,
            Blocks.END_STONE,
            Blocks.END_STONE_BRICKS,
            Blocks.EMERALD_BLOCK,
            Blocks.QUARTZ_BLOCK,
            Blocks.CHISELED_QUARTZ_BLOCK,
            Blocks.QUARTZ_BRICKS,
            Blocks.SMOOTH_QUARTZ,
            Blocks.RED_NETHER_BRICKS,
            Blocks.COAL_BLOCK,
            Blocks.PACKED_ICE,
            Blocks.PRISMARINE,
            Blocks.PRISMARINE_BRICKS,
            Blocks.DARK_PRISMARINE,
            Blocks.SMOOTH_STONE,
            Blocks.PURPUR_BLOCK,
            Blocks.NETHERITE_BLOCK,
            Blocks.ANCIENT_DEBRIS,
            Blocks.CRYING_OBSIDIAN,
            Blocks.BLACKSTONE,
            Blocks.POLISHED_BLACKSTONE,
            Blocks.POLISHED_BLACKSTONE_BRICKS,
            Blocks.GILDED_BLACKSTONE,
            Blocks.TUFF,
            Blocks.POLISHED_TUFF,
            Blocks.TUFF_BRICKS,
            Blocks.CALCITE,
            Blocks.DRIPSTONE_BLOCK,
            Blocks.DEEPSLATE,
            Blocks.COBBLED_DEEPSLATE,
            Blocks.POLISHED_DEEPSLATE,
            Blocks.DEEPSLATE_TILES,
            Blocks.DEEPSLATE_BRICKS,
            Blocks.BASALT,
            Blocks.SMOOTH_BASALT,
            Blocks.RAW_IRON_BLOCK,
            Blocks.RAW_COPPER_BLOCK,
            Blocks.RAW_GOLD_BLOCK,
            Blocks.SULFUR,
            Blocks.SULFUR_BRICKS,
            Blocks.CINNABAR,
            Blocks.CINNABAR_BRICKS
        )
    ));

    // 所有的混凝土和陶瓦加入 pickaxe_mineable
    for (TagKey<Block> tag : Iterables.concat(ExtShapeTags.SHAPE_TO_CONCRETE_TAG.values(), ExtShapeTags.SHAPE_TO_TERRACOTTA_TAG.values())) {
      valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE).addTag(tag);
    }
    valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE).addTag(ExtShapeTags.GLAZED_TERRACOTTA_SLABS);

    valueLookupBuilder(BlockTags.MINEABLE_WITH_AXE)
        .addTag(ExtShapeTags.WOODEN_BLOCKS);

    addForShapes(BlockTags.MINEABLE_WITH_AXE,
        Blocks.PUMPKIN,
        Blocks.MELON);

    addForShapes(BlockTags.MINEABLE_WITH_SHOVEL,
        Blocks.DIRT,
        Blocks.COARSE_DIRT,
        Blocks.SNOW_BLOCK,
        Blocks.CLAY);

    addForShapes(BlockTags.MINEABLE_WITH_HOE,
        Blocks.NETHER_WART_BLOCK,
        Blocks.WARPED_WART_BLOCK,
        Blocks.SHROOMLIGHT,
        Blocks.SCULK,
        Blocks.MOSS_BLOCK,
        Blocks.PALE_MOSS_BLOCK);

    addForShapes(BlockTags.NEEDS_STONE_TOOL,
        Blocks.LAPIS_BLOCK,
        Blocks.IRON_BLOCK,
        Blocks.RAW_IRON_BLOCK,
        Blocks.RAW_COPPER_BLOCK
    );
    addForShapes(BlockTags.NEEDS_STONE_TOOL, Iterables.concat(CopperManager.COPPER_BLOCKS, CopperManager.CUT_COPPER_BLOCKS, CopperManager.CUT_COPPER_BLOCKS, CopperManager.WAXED_CUT_COPPER_BLOCKS));
    addForShapes(BlockTags.NEEDS_IRON_TOOL,
        Blocks.GOLD_BLOCK,
        Blocks.DIAMOND_BLOCK,
        Blocks.EMERALD_BLOCK,
        Blocks.RAW_GOLD_BLOCK
    );
    addForShapes(BlockTags.NEEDS_DIAMOND_TOOL,
        Blocks.OBSIDIAN,
        Blocks.NETHERITE_BLOCK,
        Blocks.ANCIENT_DEBRIS,
        Blocks.CRYING_OBSIDIAN
    );

    // endregion mineable 部分

    // region 方块类型形状部分

    for (Block baseBlock : ExtShapeBlocks.getBaseBlocks()) {
      if (Stream.of(BlockCollections.LOGS, BlockCollections.STRIPPED_LOGS, BlockCollections.WOODS, BlockCollections.STRIPPED_WOODS, BlockCollections.STEMS, BlockCollections.STRIPPED_STEMS, BlockCollections.HYPHAES, BlockCollections.STRIPPED_HYPHAES).anyMatch(blocks -> blocks.contains(baseBlock))) {
        addShapesToCorrespondingTags(baseBlock, ExtShapeTags.SHAPE_TO_LOG_TAG);
      } else if (BlockCollections.PLANKS.contains(baseBlock) || baseBlock == Blocks.BAMBOO_BLOCK || baseBlock == Blocks.STRIPPED_BAMBOO_BLOCK) {
        addShapesToCorrespondingTags(baseBlock, ExtShapeTags.SHAPE_TO_WOODEN_TAG);
      } else if (BlockCollections.CONCRETES.contains(baseBlock)) {
        addShapesToCorrespondingTags(baseBlock, ExtShapeTags.SHAPE_TO_CONCRETE_TAG);
      } else if (BlockCollections.STAINED_TERRACOTTA.contains(baseBlock)) {
        addShapesToCorrespondingTags(baseBlock, ExtShapeTags.SHAPE_TO_STAINED_TERRACOTTA_TAG);
      } else if (baseBlock == Blocks.TERRACOTTA) {
        addShapesToCorrespondingTags(baseBlock, ExtShapeTags.SHAPE_TO_TERRACOTTA_TAG);
      } else if (BlockCollections.WOOLS.contains(baseBlock)) {
        addShapesToCorrespondingTags(baseBlock, ExtShapeTags.SHAPE_TO_WOOLEN_TAG);
      } else if (BlockCollections.GLAZED_TERRACOTTA.contains(baseBlock)) {
        addShapesToCorrespondingTags(baseBlock, Map.of(BlockShape.SLAB, ExtShapeTags.GLAZED_TERRACOTTA_SLABS));
      } else {
        addShapesToCorrespondingTags(baseBlock, ExtShapeTags.SHAPE_TO_TAG);
      }
    }

    // endregion 方块类型形状部分

    // region 特殊标签部分

    addForConstructionShapes(BlockTags.ENDERMAN_HOLDABLE, Blocks.DIRT, Blocks.PUMPKIN, Blocks.MELON);
    addForConstructionShapes(BlockTags.SUPPORTS_BAMBOO, Blocks.DIRT);
    addForConstructionShapes(BlockTags.SUPPORTS_DRY_VEGETATION, Blocks.DIRT);
    ExtShapeTags.SHAPE_TO_TERRACOTTA_TAG.forEach((shape, tag) -> {
      if (shape.isConstruction) {
        valueLookupBuilder(BlockTags.SUPPORTS_DRY_VEGETATION).addTag(tag);
      }
    });

    addForShapes(BlockTags.INFINIBURN_END, Blocks.BEDROCK);
    addForShapes(BlockTags.WITHER_IMMUNE, Blocks.BEDROCK);

    for (Block block : BlockCollections.UNCOLORED_SANDSTONES) {
      final Block stairs = BlockBiMaps.getBlockOf(BlockShape.STAIRS, block);
      if (isValidBlock(stairs)) {
        valueLookupBuilder(ConventionalBlockTags.UNCOLORED_SANDSTONE_STAIRS).add(stairs);
      }
      final Block slab = BlockBiMaps.getBlockOf(BlockShape.SLAB, block);
      if (isValidBlock(slab)) {
        valueLookupBuilder(ConventionalBlockTags.UNCOLORED_SANDSTONE_SLABS).add(slab);
      }
    }
    for (Block block : BlockCollections.RED_SANDSTONES) {
      final Block stairs = BlockBiMaps.getBlockOf(BlockShape.STAIRS, block);
      if (isValidBlock(stairs)) {
        valueLookupBuilder(ConventionalBlockTags.RED_SANDSTONE_STAIRS).add(stairs);
      }
      final Block slab = BlockBiMaps.getBlockOf(BlockShape.SLAB, block);
      if (isValidBlock(slab)) {
        valueLookupBuilder(ConventionalBlockTags.RED_SANDSTONE_SLABS).add(slab);
      }
    }

    addForShapes(BlockTags.GUARDED_BY_PIGLINS,
        Blocks.GOLD_BLOCK,
        Blocks.GILDED_BLACKSTONE,
        Blocks.RAW_GOLD_BLOCK
    );
    addForShapes(BlockTags.DRAGON_IMMUNE, Blocks.OBSIDIAN);
    addForShapes(ExtShapeTags.SNOW, Blocks.SNOW_BLOCK);
    addForConstructionShapes(BlockTags.SUPPORTS_SMALL_DRIPLEAF, Blocks.CLAY, Blocks.MOSS_BLOCK);
    addForConstructionShapes(BlockTags.ENDERMAN_HOLDABLE, Blocks.CLAY);
    addForShapes(BlockTags.INFINIBURN_OVERWORLD, Blocks.NETHERRACK);
    addForShapes(BlockTags.DRAGON_IMMUNE, Blocks.OBSIDIAN, Blocks.END_STONE, Blocks.END_STONE_BRICKS, Blocks.CRYING_OBSIDIAN);

    // endregion 特殊标签部分

    // region 染色标签部分

    final Iterator<TagKey<Block>> dyedTags = Iterators.cycle(BlockCollections.DYED_TAGS);
    for (Block baseBlock : Iterables.concat(BlockCollections.WOOLS, BlockCollections.CONCRETES, BlockCollections.STAINED_TERRACOTTA, BlockCollections.GLAZED_TERRACOTTA)) {
      final TagKey<Block> dyedTag = dyedTags.next();
      addForShapes(dyedTag, baseBlock);
    }

    Validate.validState(dyedTags.next() == BlockCollections.DYED_TAGS.getFirst(), "BlockCollections.DYED_TAGS is not correctly iterated");

    // endregion 染色标签部分
  }

  protected void addForShapes(TagKey<Block> tag, Block baseBlock) {
    checkValidBaseBlock(baseBlock);
    final var builder = valueLookupBuilder(tag);
    for (BlockShape shape : BlockShape.values()) {
      final Block block = BlockBiMaps.getBlockOf(shape, baseBlock);
      if (isValidBlock(block)) {
        builder.add(block);
      }
    }
  }

  protected void addForShapes(TagKey<Block> tag, Block... baseBlocks) {
    addForShapes(tag, Arrays.asList(baseBlocks));
  }

  protected void addForShapes(TagKey<Block> tag, Iterable<Block> baseBlocks) {
    final var builder = valueLookupBuilder(tag);
    for (Block baseBlock : baseBlocks) {
      checkValidBaseBlock(baseBlock);
      for (BlockShape shape : BlockShape.values()) {
        final Block block = BlockBiMaps.getBlockOf(shape, baseBlock);
        if (isValidBlock(block)) {
          builder.add(block);
        }
      }
    }
  }

  protected void addForConstructionShapes(TagKey<Block> tag, Block baseBlock) {
    checkValidBaseBlock(baseBlock);
    final var builder = valueLookupBuilder(tag);
    for (BlockShape shape : BlockShape.values()) {
      if (shape.isConstruction) {
        final Block block = BlockBiMaps.getBlockOf(shape, baseBlock);
        if (isValidBlock(block)) {
          builder.add(block);
        }
      }
    }
  }

  protected void addForConstructionShapes(TagKey<Block> tag, Block... baseBlocks) {
    addForConstructionShapes(tag, Arrays.asList(baseBlocks));
  }

  protected void addForConstructionShapes(TagKey<Block> tag, Iterable<Block> baseBlocks) {
    final var builder = valueLookupBuilder(tag);
    for (Block baseBlock : baseBlocks) {
      checkValidBaseBlock(baseBlock);
      for (BlockShape shape : BlockShape.values()) {
        if (shape.isConstruction) {
          final Block block = BlockBiMaps.getBlockOf(shape, baseBlock);
          if (isValidBlock(block)) {
            builder.add(block);
          }
        }
      }
    }
  }

  protected void addShapesToCorrespondingTags(Block baseBlock, Map<BlockShape, ? extends TagKey<Block>> tags) {
    checkValidBaseBlock(baseBlock);
    for (final BlockShape shape : BlockShape.values()) {
      TagKey<Block> tag = tags.containsKey(shape) ? tags.get(shape) : ExtShapeTags.SHAPE_TO_TAG.get(shape);

      // 对石质压力板的特殊处理
      if (STONE_BASE_BLOCKS.contains(baseBlock)) {
        if (BlockTags.PRESSURE_PLATES.equals(tag)) {
          tag = BlockTags.STONE_PRESSURE_PLATES;
        } else if (BlockTags.BUTTONS.equals(tag)) {
          tag = BlockTags.STONE_BUTTONS;
        }
      }

      Preconditions.checkNotNull(tag, "tag of shape %s", shape);
      final Block block = BlockBiMaps.getBlockOf(shape, baseBlock);
      if (isValidBlock(block)) {
        valueLookupBuilder(tag).add(block);
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
  protected void checkValidBaseBlock(@Nullable Block baseBlock) {
    Preconditions.checkArgument(ExtShapeBlocks.containsBaseBlock(baseBlock), "%s is not a base block", baseBlock);
  }

  /**
   * 检测方块是否为模组中的方块，并返回 true 或 false。
   *
   * @implNote 其他模组继承此类时需要重写此方法。
   */
  @Contract("null -> false")
  protected boolean isValidBlock(@Nullable Block block) {
    return ExtShapeBlocks.contains(block);
  }
}
