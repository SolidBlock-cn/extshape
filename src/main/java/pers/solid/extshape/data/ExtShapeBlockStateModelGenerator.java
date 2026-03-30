package pers.solid.extshape.data;

import com.mojang.math.Quadrant;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockModelDefinitionGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import pers.solid.extshape.block.*;
import pers.solid.extshape.mixin.BlockStateModelGeneratorAccessor;
import pers.solid.extshape.util.BlockCollections;
import pers.solid.extshape.util.HorizontalCornerDirection;

import java.util.function.BiConsumer;


@Environment(EnvType.CLIENT)
public final class ExtShapeBlockStateModelGenerator {
  // region create models

  public static BlockModelDefinitionGenerator createCircularPavingSlabBlockState(Block block, Identifier bottomModelId, Identifier topModelId, Identifier fullModelId) {
    final PropertyDispatch.C1<MultiVariant, SlabType> variants = PropertyDispatch.initial(CircularPavingSlabBlock.TYPE);
    final PropertyDispatch<VariantMutator> variants2 = BlockStateModelGeneratorAccessor.getROTATION_HORIZONTAL_FACING_ALT();
    variants.select(SlabType.BOTTOM, BlockModelGenerators.plainVariant(bottomModelId));
    variants.select(SlabType.TOP, BlockModelGenerators.plainVariant(topModelId));
    variants.select(SlabType.DOUBLE, BlockModelGenerators.plainVariant(fullModelId));
    return MultiVariantGenerator.dispatch(block).with(variants).with(variants2);
  }

  public static BlockModelDefinitionGenerator createGlazedTerracottaSlabBlockState(Block block, Identifier bottomModelId, Identifier topModelId, Identifier fullModelId) {
    final MultiVariantGenerator.Empty state = MultiVariantGenerator.dispatch(block);
    final PropertyDispatch.C1<MultiVariant, SlabType> map = PropertyDispatch.initial(GlazedTerracottaSlabBlock.TYPE);
    map.select(SlabType.BOTTOM, BlockModelGenerators.plainVariant(bottomModelId));
    map.select(SlabType.TOP, BlockModelGenerators.plainVariant(topModelId));
    map.select(SlabType.DOUBLE, BlockModelGenerators.plainVariant(fullModelId));
    return state.with(map).with(BlockStateModelGeneratorAccessor.getROTATION_HORIZONTAL_FACING_ALT());
  }

  /**
   * @see BlockModelGenerators#createSlab
   */
  public static BlockModelDefinitionGenerator createPillarSlabBlockState(Block block, Identifier bottomModelId, Identifier topModelId, Identifier fullModelId, Identifier bottomHorizontalModelId, Identifier topHorizontalModelId, Identifier fullHorizontalModelId) {
    final PropertyDispatch.C2<MultiVariant, SlabType, Direction.Axis> variants = PropertyDispatch.initial(ExtShapePillarSlabBlock.TYPE, ExtShapePillarSlabBlock.AXIS);
    // axis = y
    variants.select(SlabType.DOUBLE, Direction.Axis.Y, BlockModelGenerators.plainVariant(fullModelId));
    variants.select(SlabType.TOP, Direction.Axis.Y, BlockModelGenerators.plainVariant(topModelId));
    variants.select(SlabType.BOTTOM, Direction.Axis.Y, BlockModelGenerators.plainVariant(bottomModelId));
    // axis = x
    variants.select(SlabType.DOUBLE, Direction.Axis.X, BlockModelGenerators.plainVariant(fullHorizontalModelId).with(BlockModelGenerators.X_ROT_90).with(BlockModelGenerators.Y_ROT_90));
    variants.select(SlabType.BOTTOM, Direction.Axis.X, BlockModelGenerators.plainVariant(bottomHorizontalModelId).with(BlockModelGenerators.X_ROT_90).with(BlockModelGenerators.Y_ROT_90));
    variants.select(SlabType.TOP, Direction.Axis.X, BlockModelGenerators.plainVariant(topHorizontalModelId).with(BlockModelGenerators.X_ROT_90).with(BlockModelGenerators.Y_ROT_90));
    // axis = z
    variants.select(SlabType.DOUBLE, Direction.Axis.Z, BlockModelGenerators.plainVariant(fullHorizontalModelId).with(BlockModelGenerators.X_ROT_90));
    variants.select(SlabType.BOTTOM, Direction.Axis.Z, BlockModelGenerators.plainVariant(bottomHorizontalModelId).with(BlockModelGenerators.X_ROT_90));
    variants.select(SlabType.TOP, Direction.Axis.Z, BlockModelGenerators.plainVariant(topHorizontalModelId).with(BlockModelGenerators.X_ROT_90));

    return MultiVariantGenerator.dispatch(block).with(variants);
  }

  /**
   * @see BlockModelGenerators#createSlab
   * @see BlockModelGenerators#createPillarBlockUVLocked
   */
  public static BlockModelDefinitionGenerator createPillarUvLockedSlabBlockState(Block block, Identifier fullModelId) {
    final PropertyDispatch.C2<MultiVariant, SlabType, Direction.Axis> variants = PropertyDispatch.initial(ExtShapePillarUvLockedSlabBlock.TYPE, ExtShapePillarUvLockedSlabBlock.AXIS);
    for (Direction.Axis axis : Direction.Axis.values()) {
      variants.select(SlabType.BOTTOM, axis, BlockModelGenerators.plainVariant(ExtShapeModels.SLAB_COLUMN_UV_LOCKED.get(axis).getDefaultModelLocation(block)));
      variants.select(SlabType.TOP, axis, BlockModelGenerators.plainVariant(ExtShapeModels.SLAB_COLUMN_UV_LOCKED_TOP.get(axis).getDefaultModelLocation(block)));
      variants.select(SlabType.DOUBLE, axis, BlockModelGenerators.plainVariant(fullModelId.withSuffix("_" + axis.getSerializedName())));
    }
    return MultiVariantGenerator.dispatch(block).with(variants);
  }

  @SuppressWarnings("deprecation")
  public static MultiVariantGenerator createPillarVerticalSlabBlockState(Block block, Identifier modelId, Identifier horizontalModelId, Identifier horizontalUnorderedModelId, Identifier horizontalTopModelId, Identifier horizontalUnorderedTopModelId) {
    final PropertyDispatch.C2<MultiVariant, Direction.Axis, Direction> variants = PropertyDispatch.initial(ExtShapePillarVerticalSlabBlock.AXIS, ExtShapePillarVerticalSlabBlock.FACING);

    for (Direction facing : Direction.Plane.HORIZONTAL) {
      final int rotation = (int) facing.toYRot();
      variants.select(Direction.Axis.Y, facing, BlockModelGenerators.plainVariant(modelId)
          .with(VariantMutator.Y_ROT.withValue(Quadrant.parseJson(rotation)))
          .with(BlockModelGenerators.UV_LOCK));
      final boolean usesTopModel = facing == Direction.NORTH || facing == Direction.EAST;
      variants.select(facing.getAxis(), facing, BlockModelGenerators.plainVariant(usesTopModel ? horizontalTopModelId : horizontalModelId)
          .with(VariantMutator.Y_ROT.withValue(Quadrant.parseJson(usesTopModel ? rotation - 180 : rotation)))
          .with(BlockModelGenerators.X_ROT_90));
      variants.select(facing.getClockWise().getAxis(), facing, BlockModelGenerators.plainVariant(usesTopModel ? horizontalUnorderedTopModelId : horizontalUnorderedModelId)
          .with(VariantMutator.Y_ROT.withValue(Quadrant.parseJson(usesTopModel ? rotation - 90 : rotation + 90)))
          .with(BlockModelGenerators.X_ROT_90));
    }
    return MultiVariantGenerator.dispatch(block).with(variants);
  }

  public static MultiVariantGenerator createQuarterPieceBlockState(Block block, Identifier bottomModelId, Identifier topModelId) {
    return MultiVariantGenerator.dispatch(block).with(
        PropertyDispatch.initial(QuarterPieceBlock.HALF)
            .select(Half.TOP, BlockModelGenerators.plainVariant(topModelId).with(BlockModelGenerators.UV_LOCK))
            .select(Half.BOTTOM, BlockModelGenerators.plainVariant(bottomModelId).with(BlockModelGenerators.UV_LOCK))
    ).with(BlockStateModelGeneratorAccessor.getROTATION_HORIZONTAL_FACING_ALT());
  }

  public static MultiVariantGenerator createVerticalQuarterPieceBlockState(Block block, Identifier modelId) {
    return MultiVariantGenerator.dispatch(block, BlockModelGenerators.plainVariant(modelId)
            .with(BlockModelGenerators.UV_LOCK))
        .with(PropertyDispatch.modify(VerticalQuarterPieceBlock.FACING)
            .select(HorizontalCornerDirection.SOUTH_EAST, BlockModelGenerators.NOP)
            .select(HorizontalCornerDirection.SOUTH_WEST, BlockModelGenerators.Y_ROT_90)
            .select(HorizontalCornerDirection.NORTH_WEST, BlockModelGenerators.Y_ROT_180)
            .select(HorizontalCornerDirection.NORTH_EAST, BlockModelGenerators.Y_ROT_270));
  }

  public static MultiVariantGenerator createVerticalSlabBlockState(Block block, Identifier modelId) {
    return MultiVariantGenerator.dispatch(block, BlockModelGenerators.plainVariant(modelId)
        .with(BlockModelGenerators.UV_LOCK)
    ).with(BlockStateModelGeneratorAccessor.getROTATION_HORIZONTAL_FACING_ALT());
  }

  public static MultiVariantGenerator createVerticalStairsBlockState(Block block, Identifier modelId) {
    return MultiVariantGenerator.dispatch(block, BlockModelGenerators.plainVariant(modelId)
        .with(BlockModelGenerators.UV_LOCK)
    ).with(PropertyDispatch.modify(ExtShapeVerticalStairsBlock.FACING)
        .select(HorizontalCornerDirection.SOUTH_WEST, BlockModelGenerators.NOP)
        .select(HorizontalCornerDirection.NORTH_WEST, BlockModelGenerators.Y_ROT_90)
        .select(HorizontalCornerDirection.NORTH_EAST, BlockModelGenerators.Y_ROT_180)
        .select(HorizontalCornerDirection.SOUTH_EAST, BlockModelGenerators.Y_ROT_270));
  }

  // endregion create models

  // region register

  public static void registerCircularPavingSlab(Block block, Block baseBlock, TextureMapping textureMap, BlockModelGenerators blockStateModelGenerator) {
    final Identifier bottomModelId = ExtShapeModels.CIRCULAR_PAVING_SLAB.create(block, textureMap, blockStateModelGenerator.modelOutput);
    final Identifier topModelId = ExtShapeModels.CIRCULAR_PAVING_SLAB_TOP.create(block, textureMap, blockStateModelGenerator.modelOutput);
    final Identifier fullModelId = ModelLocationUtils.getModelLocation(baseBlock);
    blockStateModelGenerator.blockStateOutput.accept(createCircularPavingSlabBlockState(block, bottomModelId, topModelId, fullModelId));
    blockStateModelGenerator.registerSimpleItemModel(block, bottomModelId);
  }

  public static void registerGlazedTerracottaSlab(Block block, Block baseBlock, TextureMapping textureMap, BlockModelGenerators blockStateModelGenerator) {
    final Identifier bottomModelId = ExtShapeModels.GLAZED_TERRACOTTA_SLAB.create(block, textureMap, blockStateModelGenerator.modelOutput);
    final Identifier topModelId = ExtShapeModels.GLAZED_TERRACOTTA_SLAB_TOP.create(block, textureMap, blockStateModelGenerator.modelOutput);
    final Identifier fullModelId = ModelLocationUtils.getModelLocation(baseBlock);
    blockStateModelGenerator.blockStateOutput.accept(createGlazedTerracottaSlabBlockState(block, bottomModelId, topModelId, fullModelId));
    blockStateModelGenerator.registerSimpleItemModel(block, bottomModelId);
  }

  public static void registerPillarSlab(Block block, Block baseBlock, TextureMapping textureMap, BlockModelGenerators blockStateModelGenerator, boolean hasHorizontalColumn) {
    final Identifier bottomModelId = ModelTemplates.SLAB_BOTTOM.create(block, textureMap, blockStateModelGenerator.modelOutput);
    final Identifier topModelId = ModelTemplates.SLAB_TOP.create(block, textureMap, blockStateModelGenerator.modelOutput);
    final Identifier fullModelId = ModelLocationUtils.getModelLocation(baseBlock);
    final boolean isLog = BlockCollections.LOGS.contains(baseBlock) || BlockCollections.STRIPPED_LOGS.contains(baseBlock);
    final Identifier fullHorizontalModelId = isLog ? fullModelId.withSuffix("_horizontal") : fullModelId;
    final Identifier bottomHorizontalModelId, topHorizontalModelId;
    if (hasHorizontalColumn) {
      bottomHorizontalModelId = ExtShapeModels.SLAB_COLUMN_HORIZONTAL.create(block, textureMap, blockStateModelGenerator.modelOutput);
      topHorizontalModelId = ExtShapeModels.SLAB_COLUMN_HORIZONTAL_TOP.create(block, textureMap, blockStateModelGenerator.modelOutput);
    } else {
      bottomHorizontalModelId = ExtShapeModels.SLAB_COLUMN.create(block, textureMap, blockStateModelGenerator.modelOutput);
      topHorizontalModelId = ExtShapeModels.SLAB_COLUMN_TOP.create(block, textureMap, blockStateModelGenerator.modelOutput);
    }
    blockStateModelGenerator.blockStateOutput.accept(createPillarSlabBlockState(block, bottomModelId, topModelId, fullModelId, bottomHorizontalModelId, topHorizontalModelId, fullHorizontalModelId));
    blockStateModelGenerator.registerSimpleItemModel(block, bottomModelId);
  }

  public static void registerPillarUvLockedSlab(Block block, Block baseBlock, TextureMapping textureMap, BlockModelGenerators blockStateModelGenerator) {
    final Identifier slabModelId = ModelTemplates.SLAB_BOTTOM.create(block, textureMap, blockStateModelGenerator.modelOutput);
    final Identifier fullModelId = ModelLocationUtils.getModelLocation(baseBlock);
    for (Direction.Axis axis : Direction.Axis.values()) {
      ExtShapeModels.SLAB_COLUMN_UV_LOCKED.get(axis).create(block, textureMap, blockStateModelGenerator.modelOutput);
      ExtShapeModels.SLAB_COLUMN_UV_LOCKED_TOP.get(axis).create(block, textureMap, blockStateModelGenerator.modelOutput);
    }
    blockStateModelGenerator.blockStateOutput.accept(createPillarUvLockedSlabBlockState(block, fullModelId));
    blockStateModelGenerator.registerSimpleItemModel(block, slabModelId);
  }

  public static void registerPillarVerticalSlab(Block block, TextureMapping textureMap, BlockModelGenerators blockStateModelGenerator, boolean isLog) {
    final Identifier modelId = ExtShapeModels.VERTICAL_SLAB.create(block, textureMap, blockStateModelGenerator.modelOutput);
    final Identifier horizontalModelId;
    final Identifier horizontalUnorderedModelId;
    final Identifier horizontalTopModelId;
    final Identifier horizontalUnorderedTopModelId;
    if (isLog) {
      horizontalModelId = ExtShapeModels.VERTICAL_SLAB_COLUMN_HORIZONTAL.create(block, textureMap, blockStateModelGenerator.modelOutput);
      horizontalUnorderedModelId = ExtShapeModels.VERTICAL_SLAB_COLUMN_HORIZONTAL_UNORDERED.create(block, textureMap, blockStateModelGenerator.modelOutput);
      horizontalTopModelId = ExtShapeModels.VERTICAL_SLAB_COLUMN_HORIZONTAL_TOP.create(block, textureMap, blockStateModelGenerator.modelOutput);
      horizontalUnorderedTopModelId = ExtShapeModels.VERTICAL_SLAB_COLUMN_HORIZONTAL_UNORDERED_TOP.create(block, textureMap, blockStateModelGenerator.modelOutput);
    } else {
      horizontalModelId = ExtShapeModels.VERTICAL_SLAB_COLUMN.create(block, textureMap, blockStateModelGenerator.modelOutput);
      horizontalUnorderedModelId = ExtShapeModels.VERTICAL_SLAB_COLUMN_UNORDERED.create(block, textureMap, blockStateModelGenerator.modelOutput);
      horizontalTopModelId = ExtShapeModels.VERTICAL_SLAB_COLUMN_TOP.create(block, textureMap, blockStateModelGenerator.modelOutput);
      horizontalUnorderedTopModelId = ExtShapeModels.VERTICAL_SLAB_COLUMN_UNORDERED_TOP.create(block, textureMap, blockStateModelGenerator.modelOutput);
    }
    blockStateModelGenerator.blockStateOutput.accept(createPillarVerticalSlabBlockState(block, modelId, horizontalModelId, horizontalUnorderedModelId, horizontalTopModelId, horizontalUnorderedTopModelId));
    blockStateModelGenerator.registerSimpleItemModel(block, modelId);
  }

  public static void registerQuarterPiece(Block block, TextureMapping textureMap, BlockModelGenerators blockStateModelGenerator) {
    final Identifier bottomModelId = getUnwaxedModelId(ExtShapeModels.QUARTER_PIECE, block, textureMap, blockStateModelGenerator.modelOutput);
    final Identifier topModelId = getUnwaxedModelId(ExtShapeModels.QUARTER_PIECE_TOP, block, textureMap, blockStateModelGenerator.modelOutput);
    blockStateModelGenerator.blockStateOutput.accept(createQuarterPieceBlockState(block, bottomModelId, topModelId));
    blockStateModelGenerator.registerSimpleItemModel(block, bottomModelId);
  }

  public static void registerVerticalQuarterPiece(Block block, TextureMapping textureMap, BlockModelGenerators blockStateModelGenerator) {
    final Identifier modelId = getUnwaxedModelId(ExtShapeModels.VERTICAL_QUARTER_PIECE, block, textureMap, blockStateModelGenerator.modelOutput);
    blockStateModelGenerator.blockStateOutput.accept(createVerticalQuarterPieceBlockState(block, modelId));
    blockStateModelGenerator.registerSimpleItemModel(block, modelId);
  }

  public static void registerVerticalSlab(Block block, TextureMapping textureMap, BlockModelGenerators blockStateModelGenerator) {
    final Identifier modelId = getUnwaxedModelId(ExtShapeModels.VERTICAL_SLAB, block, textureMap, blockStateModelGenerator.modelOutput);
    blockStateModelGenerator.blockStateOutput.accept(createVerticalSlabBlockState(block, modelId));
    blockStateModelGenerator.registerSimpleItemModel(block, modelId);
  }

  public static void registerVerticalStairs(Block block, TextureMapping textureMap, BlockModelGenerators blockStateModelGenerator) {
    final Identifier modelId = getUnwaxedModelId(ExtShapeModels.VERTICAL_STAIRS, block, textureMap, blockStateModelGenerator.modelOutput);
    blockStateModelGenerator.blockStateOutput.accept(createVerticalStairsBlockState(block, modelId));
    blockStateModelGenerator.registerSimpleItemModel(block, modelId);
  }

  // endregion register

  /**
   * 对于普通方块，正常生成并获取其模型。对于特殊的涂蜡的方块，由于此时不需要生成涂蜡方块模型，只需要使用未涂蜡模型的 id 即可。
   */
  private static Identifier getUnwaxedModelId(ModelTemplate model, Block block, TextureMapping textures, BiConsumer<Identifier, ModelInstance> modelCollector) {
    final Identifier id = BuiltInRegistries.BLOCK.getKey(block);
    if (id.getPath().startsWith("waxed_")) {
      return model.getDefaultModelLocation(BuiltInRegistries.BLOCK.getOptional(id.withPath(s -> s.replace("waxed_", ""))).orElseThrow());
    }
    return model.create(block, textures, modelCollector);
  }
}
