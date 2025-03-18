package pers.solid.extshape.data;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.ModelVariantOperator;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.AxisRotation;
import net.minecraft.util.math.Direction;
import pers.solid.extshape.block.*;
import pers.solid.extshape.mixin.BlockStateModelGeneratorAccessor;
import pers.solid.extshape.util.BlockCollections;
import pers.solid.extshape.util.HorizontalCornerDirection;

import java.util.function.BiConsumer;


@Environment(EnvType.CLIENT)
public final class ExtShapeBlockStateModelGenerator {
  // region create models

  public static BlockModelDefinitionCreator createCircularPavingSlabBlockState(Block block, Identifier bottomModelId, Identifier topModelId, Identifier fullModelId) {
    final BlockStateVariantMap.SingleProperty<WeightedVariant, SlabType> variants = BlockStateVariantMap.models(CircularPavingSlabBlock.TYPE);
    final BlockStateVariantMap<ModelVariantOperator> variants2 = BlockStateModelGeneratorAccessor.getSOUTH_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS();
    variants.register(SlabType.BOTTOM, BlockStateModelGenerator.createWeightedVariant(bottomModelId));
    variants.register(SlabType.TOP, BlockStateModelGenerator.createWeightedVariant(topModelId));
    variants.register(SlabType.DOUBLE, BlockStateModelGenerator.createWeightedVariant(fullModelId));
    return VariantsBlockModelDefinitionCreator.of(block).with(variants).coordinate(variants2);
  }

  public static BlockModelDefinitionCreator createGlazedTerracottaSlabBlockState(Block block, Identifier bottomModelId, Identifier topModelId, Identifier fullModelId) {
    final VariantsBlockModelDefinitionCreator.Empty state = VariantsBlockModelDefinitionCreator.of(block);
    final BlockStateVariantMap.SingleProperty<WeightedVariant, SlabType> map = BlockStateVariantMap.models(GlazedTerracottaSlabBlock.TYPE);
    map.register(SlabType.BOTTOM, BlockStateModelGenerator.createWeightedVariant(bottomModelId));
    map.register(SlabType.TOP, BlockStateModelGenerator.createWeightedVariant(topModelId));
    map.register(SlabType.DOUBLE, BlockStateModelGenerator.createWeightedVariant(fullModelId));
    return state.with(map).coordinate(BlockStateModelGeneratorAccessor.getSOUTH_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS());
  }

  /**
   * @see BlockStateModelGenerator#createSlabBlockState
   */
  public static BlockModelDefinitionCreator createPillarSlabBlockState(Block block, Identifier bottomModelId, Identifier topModelId, Identifier fullModelId, Identifier bottomHorizontalModelId, Identifier topHorizontalModelId, Identifier fullHorizontalModelId) {
    final BlockStateVariantMap.DoubleProperty<WeightedVariant, SlabType, Direction.Axis> variants = BlockStateVariantMap.models(ExtShapePillarSlabBlock.TYPE, ExtShapePillarSlabBlock.AXIS);
    // axis = y
    variants.register(SlabType.DOUBLE, Direction.Axis.Y, BlockStateModelGenerator.createWeightedVariant(fullModelId));
    variants.register(SlabType.TOP, Direction.Axis.Y, BlockStateModelGenerator.createWeightedVariant(topModelId));
    variants.register(SlabType.BOTTOM, Direction.Axis.Y, BlockStateModelGenerator.createWeightedVariant(bottomModelId));
    // axis = x
    variants.register(SlabType.DOUBLE, Direction.Axis.X, BlockStateModelGenerator.createWeightedVariant(fullHorizontalModelId).apply(BlockStateModelGenerator.ROTATE_X_90).apply(BlockStateModelGenerator.ROTATE_Y_90));
    variants.register(SlabType.BOTTOM, Direction.Axis.X, BlockStateModelGenerator.createWeightedVariant(bottomHorizontalModelId).apply(BlockStateModelGenerator.ROTATE_X_90).apply(BlockStateModelGenerator.ROTATE_Y_90));
    variants.register(SlabType.TOP, Direction.Axis.X, BlockStateModelGenerator.createWeightedVariant(topHorizontalModelId).apply(BlockStateModelGenerator.ROTATE_X_90).apply(BlockStateModelGenerator.ROTATE_Y_90));
    // axis = z
    variants.register(SlabType.DOUBLE, Direction.Axis.Z, BlockStateModelGenerator.createWeightedVariant(fullHorizontalModelId).apply(BlockStateModelGenerator.ROTATE_X_90));
    variants.register(SlabType.BOTTOM, Direction.Axis.Z, BlockStateModelGenerator.createWeightedVariant(bottomHorizontalModelId).apply(BlockStateModelGenerator.ROTATE_X_90));
    variants.register(SlabType.TOP, Direction.Axis.Z, BlockStateModelGenerator.createWeightedVariant(topHorizontalModelId).apply(BlockStateModelGenerator.ROTATE_X_90));

    return VariantsBlockModelDefinitionCreator.of(block).with(variants);
  }

  /**
   * @see BlockStateModelGenerator#createSlabBlockState
   * @see BlockStateModelGenerator#createUvLockedColumnBlockState
   */
  public static BlockModelDefinitionCreator createPillarUvLockedSlabBlockState(Block block, Identifier fullModelId) {
    final BlockStateVariantMap.DoubleProperty<WeightedVariant, SlabType, Direction.Axis> variants = BlockStateVariantMap.models(ExtShapePillarUvLockedSlabBlock.TYPE, ExtShapePillarUvLockedSlabBlock.AXIS);
    for (Direction.Axis axis : Direction.Axis.values()) {
      variants.register(SlabType.BOTTOM, axis, BlockStateModelGenerator.createWeightedVariant(ExtShapeModels.SLAB_COLUMN_UV_LOCKED.get(axis).getBlockSubModelId(block)));
      variants.register(SlabType.TOP, axis, BlockStateModelGenerator.createWeightedVariant(ExtShapeModels.SLAB_COLUMN_UV_LOCKED_TOP.get(axis).getBlockSubModelId(block)));
      variants.register(SlabType.DOUBLE, axis, BlockStateModelGenerator.createWeightedVariant(fullModelId.withSuffixedPath("_" + axis.asString())));
    }
    return VariantsBlockModelDefinitionCreator.of(block).with(variants);
  }

  public static VariantsBlockModelDefinitionCreator createPillarVerticalSlabBlockState(Block block, Identifier modelId, Identifier horizontalModelId, Identifier horizontalUnorderedModelId, Identifier horizontalTopModelId, Identifier horizontalUnorderedTopModelId) {
    final BlockStateVariantMap.DoubleProperty<WeightedVariant, Direction.Axis, Direction> variants = BlockStateVariantMap.models(ExtShapePillarVerticalSlabBlock.AXIS, ExtShapePillarVerticalSlabBlock.FACING);

    for (Direction facing : Direction.Type.HORIZONTAL) {
      final int rotation = (int) facing.getPositiveHorizontalDegrees();
      variants.register(Direction.Axis.Y, facing, BlockStateModelGenerator.createWeightedVariant(modelId)
          .apply(ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.fromDegrees(rotation)))
          .apply(BlockStateModelGenerator.UV_LOCK));
      final boolean usesTopModel = facing == Direction.NORTH || facing == Direction.EAST;
      variants.register(facing.getAxis(), facing, BlockStateModelGenerator.createWeightedVariant(usesTopModel ? horizontalTopModelId : horizontalModelId)
          .apply(ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.fromDegrees(usesTopModel ? rotation - 180 : rotation)))
          .apply(BlockStateModelGenerator.ROTATE_X_90));
      variants.register(facing.rotateYClockwise().getAxis(), facing, BlockStateModelGenerator.createWeightedVariant(usesTopModel ? horizontalUnorderedTopModelId : horizontalUnorderedModelId)
          .apply(ModelVariantOperator.ROTATION_Y.withValue(AxisRotation.fromDegrees(usesTopModel ? rotation - 90 : rotation + 90)))
          .apply(BlockStateModelGenerator.ROTATE_X_90));
    }
    return VariantsBlockModelDefinitionCreator.of(block).with(variants);
  }

  public static VariantsBlockModelDefinitionCreator createQuarterPieceBlockState(Block block, Identifier bottomModelId, Identifier topModelId) {
    return VariantsBlockModelDefinitionCreator.of(block).with(
        BlockStateVariantMap.models(QuarterPieceBlock.HALF)
            .register(BlockHalf.TOP, BlockStateModelGenerator.createWeightedVariant(topModelId).apply(BlockStateModelGenerator.UV_LOCK))
            .register(BlockHalf.BOTTOM, BlockStateModelGenerator.createWeightedVariant(bottomModelId).apply(BlockStateModelGenerator.UV_LOCK))
    ).coordinate(BlockStateModelGeneratorAccessor.getSOUTH_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS());
  }

  public static VariantsBlockModelDefinitionCreator createVerticalQuarterPieceBlockState(Block block, Identifier modelId) {
    return VariantsBlockModelDefinitionCreator.of(block, BlockStateModelGenerator.createWeightedVariant(modelId)
            .apply(BlockStateModelGenerator.UV_LOCK))
        .coordinate(BlockStateVariantMap.operations(VerticalQuarterPieceBlock.FACING)
            .register(HorizontalCornerDirection.SOUTH_EAST, BlockStateModelGenerator.NO_OP)
            .register(HorizontalCornerDirection.SOUTH_WEST, BlockStateModelGenerator.ROTATE_Y_90)
            .register(HorizontalCornerDirection.NORTH_WEST, BlockStateModelGenerator.ROTATE_Y_180)
            .register(HorizontalCornerDirection.NORTH_EAST, BlockStateModelGenerator.ROTATE_Y_270));
  }

  public static VariantsBlockModelDefinitionCreator createVerticalSlabBlockState(Block block, Identifier modelId) {
    return VariantsBlockModelDefinitionCreator.of(block, BlockStateModelGenerator.createWeightedVariant(modelId)
        .apply(BlockStateModelGenerator.UV_LOCK)
    ).coordinate(BlockStateModelGeneratorAccessor.getSOUTH_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS());
  }

  public static VariantsBlockModelDefinitionCreator createVerticalStairsBlockState(Block block, Identifier modelId) {
    return VariantsBlockModelDefinitionCreator.of(block, BlockStateModelGenerator.createWeightedVariant(modelId)
        .apply(BlockStateModelGenerator.UV_LOCK)
    ).coordinate(BlockStateVariantMap.operations(ExtShapeVerticalStairsBlock.FACING)
        .register(HorizontalCornerDirection.SOUTH_WEST, BlockStateModelGenerator.NO_OP)
        .register(HorizontalCornerDirection.NORTH_WEST, BlockStateModelGenerator.ROTATE_Y_90)
        .register(HorizontalCornerDirection.NORTH_EAST, BlockStateModelGenerator.ROTATE_Y_180)
        .register(HorizontalCornerDirection.SOUTH_EAST, BlockStateModelGenerator.ROTATE_Y_270));
  }

  // endregion create models

  // region register

  public static void registerCircularPavingSlab(Block block, Block baseBlock, TextureMap textureMap, BlockStateModelGenerator blockStateModelGenerator) {
    final Identifier bottomModelId = ExtShapeModels.CIRCULAR_PAVING_SLAB.upload(block, textureMap, blockStateModelGenerator.modelCollector);
    final Identifier topModelId = ExtShapeModels.CIRCULAR_PAVING_SLAB_TOP.upload(block, textureMap, blockStateModelGenerator.modelCollector);
    final Identifier fullModelId = ModelIds.getBlockModelId(baseBlock);
    blockStateModelGenerator.blockStateCollector.accept(createCircularPavingSlabBlockState(block, bottomModelId, topModelId, fullModelId));
    blockStateModelGenerator.registerParentedItemModel(block, bottomModelId);
  }

  public static void registerGlazedTerracottaSlab(Block block, Block baseBlock, TextureMap textureMap, BlockStateModelGenerator blockStateModelGenerator) {
    final Identifier bottomModelId = ExtShapeModels.GLAZED_TERRACOTTA_SLAB.upload(block, textureMap, blockStateModelGenerator.modelCollector);
    final Identifier topModelId = ExtShapeModels.GLAZED_TERRACOTTA_SLAB_TOP.upload(block, textureMap, blockStateModelGenerator.modelCollector);
    final Identifier fullModelId = ModelIds.getBlockModelId(baseBlock);
    blockStateModelGenerator.blockStateCollector.accept(createGlazedTerracottaSlabBlockState(block, bottomModelId, topModelId, fullModelId));
    blockStateModelGenerator.registerParentedItemModel(block, bottomModelId);
  }

  public static void registerPillarSlab(Block block, Block baseBlock, TextureMap textureMap, BlockStateModelGenerator blockStateModelGenerator, boolean hasHorizontalColumn) {
    final Identifier bottomModelId = Models.SLAB.upload(block, textureMap, blockStateModelGenerator.modelCollector);
    final Identifier topModelId = Models.SLAB_TOP.upload(block, textureMap, blockStateModelGenerator.modelCollector);
    final Identifier fullModelId = ModelIds.getBlockModelId(baseBlock);
    final boolean isLog = BlockCollections.LOGS.contains(baseBlock) || BlockCollections.STRIPPED_LOGS.contains(baseBlock);
    final Identifier fullHorizontalModelId = isLog ? fullModelId.withSuffixedPath("_horizontal") : fullModelId;
    final Identifier bottomHorizontalModelId, topHorizontalModelId;
    if (hasHorizontalColumn) {
      bottomHorizontalModelId = ExtShapeModels.SLAB_COLUMN_HORIZONTAL.upload(block, textureMap, blockStateModelGenerator.modelCollector);
      topHorizontalModelId = ExtShapeModels.SLAB_COLUMN_HORIZONTAL_TOP.upload(block, textureMap, blockStateModelGenerator.modelCollector);
    } else {
      bottomHorizontalModelId = ExtShapeModels.SLAB_COLUMN.upload(block, textureMap, blockStateModelGenerator.modelCollector);
      topHorizontalModelId = ExtShapeModels.SLAB_COLUMN_TOP.upload(block, textureMap, blockStateModelGenerator.modelCollector);
    }
    blockStateModelGenerator.blockStateCollector.accept(createPillarSlabBlockState(block, bottomModelId, topModelId, fullModelId, bottomHorizontalModelId, topHorizontalModelId, fullHorizontalModelId));
    blockStateModelGenerator.registerParentedItemModel(block, bottomModelId);
  }

  public static void registerPillarUvLockedSlab(Block block, Block baseBlock, TextureMap textureMap, BlockStateModelGenerator blockStateModelGenerator) {
    final Identifier slabModelId = Models.SLAB.upload(block, textureMap, blockStateModelGenerator.modelCollector);
    final Identifier fullModelId = ModelIds.getBlockModelId(baseBlock);
    for (Direction.Axis axis : Direction.Axis.values()) {
      ExtShapeModels.SLAB_COLUMN_UV_LOCKED.get(axis).upload(block, textureMap, blockStateModelGenerator.modelCollector);
      ExtShapeModels.SLAB_COLUMN_UV_LOCKED_TOP.get(axis).upload(block, textureMap, blockStateModelGenerator.modelCollector);
    }
    blockStateModelGenerator.blockStateCollector.accept(createPillarUvLockedSlabBlockState(block, fullModelId));
    blockStateModelGenerator.registerParentedItemModel(block, slabModelId);
  }

  public static void registerPillarVerticalSlab(Block block, TextureMap textureMap, BlockStateModelGenerator blockStateModelGenerator, boolean isLog) {
    final Identifier modelId = ExtShapeModels.VERTICAL_SLAB.upload(block, textureMap, blockStateModelGenerator.modelCollector);
    final Identifier horizontalModelId;
    final Identifier horizontalUnorderedModelId;
    final Identifier horizontalTopModelId;
    final Identifier horizontalUnorderedTopModelId;
    if (isLog) {
      horizontalModelId = ExtShapeModels.VERTICAL_SLAB_COLUMN_HORIZONTAL.upload(block, textureMap, blockStateModelGenerator.modelCollector);
      horizontalUnorderedModelId = ExtShapeModels.VERTICAL_SLAB_COLUMN_HORIZONTAL_UNORDERED.upload(block, textureMap, blockStateModelGenerator.modelCollector);
      horizontalTopModelId = ExtShapeModels.VERTICAL_SLAB_COLUMN_HORIZONTAL_TOP.upload(block, textureMap, blockStateModelGenerator.modelCollector);
      horizontalUnorderedTopModelId = ExtShapeModels.VERTICAL_SLAB_COLUMN_HORIZONTAL_UNORDERED_TOP.upload(block, textureMap, blockStateModelGenerator.modelCollector);
    } else {
      horizontalModelId = ExtShapeModels.VERTICAL_SLAB_COLUMN.upload(block, textureMap, blockStateModelGenerator.modelCollector);
      horizontalUnorderedModelId = ExtShapeModels.VERTICAL_SLAB_COLUMN_UNORDERED.upload(block, textureMap, blockStateModelGenerator.modelCollector);
      horizontalTopModelId = ExtShapeModels.VERTICAL_SLAB_COLUMN_TOP.upload(block, textureMap, blockStateModelGenerator.modelCollector);
      horizontalUnorderedTopModelId = ExtShapeModels.VERTICAL_SLAB_COLUMN_UNORDERED_TOP.upload(block, textureMap, blockStateModelGenerator.modelCollector);
    }
    blockStateModelGenerator.blockStateCollector.accept(createPillarVerticalSlabBlockState(block, modelId, horizontalModelId, horizontalUnorderedModelId, horizontalTopModelId, horizontalUnorderedTopModelId));
    blockStateModelGenerator.registerParentedItemModel(block, modelId);
  }

  public static void registerQuarterPiece(Block block, TextureMap textureMap, BlockStateModelGenerator blockStateModelGenerator) {
    final Identifier bottomModelId = getUnwaxedModelId(ExtShapeModels.QUARTER_PIECE, block, textureMap, blockStateModelGenerator.modelCollector);
    final Identifier topModelId = getUnwaxedModelId(ExtShapeModels.QUARTER_PIECE_TOP, block, textureMap, blockStateModelGenerator.modelCollector);
    blockStateModelGenerator.blockStateCollector.accept(createQuarterPieceBlockState(block, bottomModelId, topModelId));
    blockStateModelGenerator.registerParentedItemModel(block, bottomModelId);
  }

  public static void registerVerticalQuarterPiece(Block block, TextureMap textureMap, BlockStateModelGenerator blockStateModelGenerator) {
    final Identifier modelId = getUnwaxedModelId(ExtShapeModels.VERTICAL_QUARTER_PIECE, block, textureMap, blockStateModelGenerator.modelCollector);
    blockStateModelGenerator.blockStateCollector.accept(createVerticalQuarterPieceBlockState(block, modelId));
    blockStateModelGenerator.registerParentedItemModel(block, modelId);
  }

  public static void registerVerticalSlab(Block block, TextureMap textureMap, BlockStateModelGenerator blockStateModelGenerator) {
    final Identifier modelId = getUnwaxedModelId(ExtShapeModels.VERTICAL_SLAB, block, textureMap, blockStateModelGenerator.modelCollector);
    blockStateModelGenerator.blockStateCollector.accept(createVerticalSlabBlockState(block, modelId));
    blockStateModelGenerator.registerParentedItemModel(block, modelId);
  }

  public static void registerVerticalStairs(Block block, TextureMap textureMap, BlockStateModelGenerator blockStateModelGenerator) {
    final Identifier modelId = getUnwaxedModelId(ExtShapeModels.VERTICAL_STAIRS, block, textureMap, blockStateModelGenerator.modelCollector);
    blockStateModelGenerator.blockStateCollector.accept(createVerticalStairsBlockState(block, modelId));
    blockStateModelGenerator.registerParentedItemModel(block, modelId);
  }

  // endregion register

  /**
   * 对于普通方块，正常生成并获取其模型。对于特殊的涂蜡的方块，由于此时不需要生成涂蜡方块模型，只需要使用未涂蜡模型的 id 即可。
   */
  private static Identifier getUnwaxedModelId(Model model, Block block, TextureMap textures, BiConsumer<Identifier, ModelSupplier> modelCollector) {
    final Identifier id = Registries.BLOCK.getId(block);
    if (id.getPath().startsWith("waxed_")) {
      return model.getBlockSubModelId(Registries.BLOCK.getOptionalValue(id.withPath(s -> s.replace("waxed_", ""))).orElseThrow());
    }
    return model.upload(block, textures, modelCollector);
  }
}
