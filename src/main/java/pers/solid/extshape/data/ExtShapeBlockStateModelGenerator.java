package pers.solid.extshape.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.block.Block;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.data.client.*;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import pers.solid.extshape.block.*;
import pers.solid.extshape.mixin.ModelAccessor;
import pers.solid.extshape.util.BlockCollections;
import pers.solid.extshape.util.HorizontalCornerDirection;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static net.minecraft.data.client.VariantSettings.*;

public final class ExtShapeBlockStateModelGenerator {
  // region create models

  public static BlockStateSupplier createCircularPavingSlabBlockState(Block block, Identifier bottomModelId, Identifier topModelId, Identifier fullModelId) {
    final BlockStateVariantMap.SingleProperty<SlabType> variants = BlockStateVariantMap.create(CircularPavingSlabBlock.TYPE);
    final BlockStateVariantMap variants2 = BlockStateModelGenerator.createSouthDefaultHorizontalRotationStates();
    variants.register(SlabType.BOTTOM, BlockStateVariant.create().put(VariantSettings.MODEL, bottomModelId));
    variants.register(SlabType.TOP, BlockStateVariant.create().put(VariantSettings.MODEL, topModelId));
    variants.register(SlabType.DOUBLE, BlockStateVariant.create().put(VariantSettings.MODEL, fullModelId));
    return VariantsBlockStateSupplier.create(block).coordinate(variants).coordinate(variants2);
  }

  public static BlockStateSupplier createGlazedTerracottaSlabBlockState(Block block, Identifier bottomModelId, Identifier topModelId, Identifier fullModelId) {
    final VariantsBlockStateSupplier state = VariantsBlockStateSupplier.create(block);
    final VariantSetting<Integer> variantSetting = new VariantSetting<>("y", JsonPrimitive::new);
    final BlockStateVariantMap.DoubleProperty<SlabType, Direction> map = BlockStateVariantMap.create(GlazedTerracottaSlabBlock.TYPE, GlazedTerracottaSlabBlock.FACING);
    for (Direction direction : Direction.Type.HORIZONTAL) {
      final int rotation = (int) direction.asRotation();
      map.register(SlabType.BOTTOM, direction, BlockStateVariant.create().put(VariantSettings.MODEL, bottomModelId).put(variantSetting, rotation));
      map.register(SlabType.TOP, direction, BlockStateVariant.create().put(VariantSettings.MODEL, topModelId).put(variantSetting, rotation));
      map.register(SlabType.DOUBLE, direction, BlockStateVariant.create().put(VariantSettings.MODEL, fullModelId).put(variantSetting, rotation));
    }
    return state.coordinate(map);
  }

  /**
   * @see BlockStateModelGenerator#createSlabBlockState
   */
  public static BlockStateSupplier createPillarSlabBlockState(Block block, Identifier bottomModelId, Identifier topModelId, Identifier fullModelId, Identifier bottomHorizontalModelId, Identifier topHorizontalModelId, Identifier fullHorizontalModelId) {
    final BlockStateVariantMap.DoubleProperty<SlabType, Direction.Axis> variants = BlockStateVariantMap.create(ExtShapePillarSlabBlock.TYPE, ExtShapePillarSlabBlock.AXIS);
    // axis = y
    variants.register(SlabType.DOUBLE, Direction.Axis.Y, BlockStateVariant.create().put(MODEL, fullModelId));
    variants.register(SlabType.TOP, Direction.Axis.Y, BlockStateVariant.create().put(MODEL, topModelId));
    variants.register(SlabType.BOTTOM, Direction.Axis.Y, BlockStateVariant.create().put(MODEL, bottomModelId));
    // axis = x
    variants.register(SlabType.DOUBLE, Direction.Axis.X, BlockStateVariant.create().put(MODEL, fullHorizontalModelId).put(X, VariantSettings.Rotation.R90).put(Y, Rotation.R90));
    variants.register(SlabType.BOTTOM, Direction.Axis.X, BlockStateVariant.create().put(MODEL, bottomHorizontalModelId).put(X, Rotation.R90).put(Y, Rotation.R90));
    variants.register(SlabType.TOP, Direction.Axis.X, BlockStateVariant.create().put(MODEL, topHorizontalModelId).put(X, Rotation.R90).put(Y, Rotation.R90));
    // axis = z
    variants.register(SlabType.DOUBLE, Direction.Axis.Z, BlockStateVariant.create().put(MODEL, fullHorizontalModelId).put(X, Rotation.R90));
    variants.register(SlabType.BOTTOM, Direction.Axis.Z, BlockStateVariant.create().put(MODEL, bottomHorizontalModelId).put(X, Rotation.R90));
    variants.register(SlabType.TOP, Direction.Axis.Z, BlockStateVariant.create().put(MODEL, topHorizontalModelId).put(X, Rotation.R90));

    return VariantsBlockStateSupplier.create(block).coordinate(variants);
  }

  /**
   * @see BlockStateModelGenerator#createSlabBlockState
   * @see BlockStateModelGenerator#createUvLockedColumnBlockState
   */
  public static BlockStateSupplier createPillarUvLockedSlabBlockState(Block block, Identifier fullModelId) {
    final BlockStateVariantMap.DoubleProperty<SlabType, Direction.Axis> variants = BlockStateVariantMap.create(ExtShapePillarUvLockedSlabBlock.TYPE, ExtShapePillarUvLockedSlabBlock.AXIS);
    for (Direction.Axis axis : Direction.Axis.values()) {
      variants.register(SlabType.BOTTOM, axis, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(block, ((ModelAccessor) ExtShapeModels.SLAB_COLUMN_UV_LOCKED.get(axis)).getVariant().orElse(""))));
      variants.register(SlabType.TOP, axis, BlockStateVariant.create().put(VariantSettings.MODEL, ModelIds.getBlockSubModelId(block, ((ModelAccessor) ExtShapeModels.SLAB_COLUMN_UV_LOCKED_TOP.get(axis)).getVariant().orElse(""))));
      variants.register(SlabType.DOUBLE, axis, BlockStateVariant.create().put(VariantSettings.MODEL, fullModelId.withSuffixedPath("_" + axis.asString())));
    }
    return VariantsBlockStateSupplier.create(block).coordinate(variants);
  }

  public static BlockStateSupplier createPillarVerticalSlabBlockState(Block block, Identifier modelId, Identifier horizontalModelId, Identifier horizontalUnorderedModelId, Identifier horizontalTopModelId, Identifier horizontalUnorderedTopModelId) {
    final BlockStateVariantMap.DoubleProperty<Direction.Axis, Direction> variants = BlockStateVariantMap.create(ExtShapePillarVerticalSlabBlock.AXIS, ExtShapePillarVerticalSlabBlock.FACING);
    final VariantSetting<Integer> yRotation = new VariantSetting<>("y", JsonPrimitive::new);

    for (Direction facing : Direction.Type.HORIZONTAL) {
      final int rotation = (int) facing.asRotation();
      variants.register(Direction.Axis.Y, facing, new BlockStateVariant()
          .put(VariantSettings.MODEL, modelId)
          .put(yRotation, rotation)
          .put(VariantSettings.UVLOCK, true));
      final boolean usesTopModel = facing == Direction.NORTH || facing == Direction.EAST;
      variants.register(facing.getAxis(), facing, new BlockStateVariant()
          .put(VariantSettings.MODEL, usesTopModel ? horizontalTopModelId : horizontalModelId)
          .put(yRotation, usesTopModel ? rotation - 180 : rotation)
          .put(VariantSettings.X, VariantSettings.Rotation.R90));
      variants.register(facing.rotateYClockwise().getAxis(), facing, new BlockStateVariant()
          .put(VariantSettings.MODEL, usesTopModel ? horizontalUnorderedTopModelId : horizontalUnorderedModelId)
          .put(yRotation, usesTopModel ? rotation - 90 : rotation + 90)
          .put(VariantSettings.X, VariantSettings.Rotation.R90));
    }
    return VariantsBlockStateSupplier.create(block).coordinate(variants);
  }

  public static BlockStateSupplier createQuarterPieceBlockState(Block block, Identifier bottomModelId, Identifier topModelId) {
    return VariantsBlockStateSupplier.create(block).coordinate(
        BlockStateVariantMap.create(QuarterPieceBlock.HALF)
            .register(BlockHalf.TOP, BlockStateVariant.create().put(VariantSettings.MODEL, topModelId).put(VariantSettings.UVLOCK, true))
            .register(BlockHalf.BOTTOM, BlockStateVariant.create().put(VariantSettings.MODEL, bottomModelId).put(VariantSettings.UVLOCK, true))
    ).coordinate(BlockStateModelGenerator.createSouthDefaultHorizontalRotationStates());
  }

  public static BlockStateSupplier createVerticalQuarterPieceBlockState(Block block, Identifier modelId) {
    return VariantsBlockStateSupplier.create(block, BlockStateVariant.create()
            .put(VariantSettings.MODEL, modelId)
            .put(VariantSettings.UVLOCK, true))
        .coordinate(BlockStateVariantMap.create(VerticalQuarterPieceBlock.FACING)
            .register(HorizontalCornerDirection.SOUTH_EAST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R0))
            .register(HorizontalCornerDirection.SOUTH_WEST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R90))
            .register(HorizontalCornerDirection.NORTH_WEST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R180))
            .register(HorizontalCornerDirection.NORTH_EAST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R270)));
  }

  public static BlockStateSupplier createVerticalSlabBlockState(Block block, Identifier modelId) {
    return VariantsBlockStateSupplier.create(block, new BlockStateVariant()
        .put(VariantSettings.MODEL, modelId)
        .put(VariantSettings.UVLOCK, true)
    ).coordinate(BlockStateModelGenerator.createSouthDefaultHorizontalRotationStates());
  }

  public static BlockStateSupplier createVerticalStairsBlockState(Block block, Identifier modelId) {
    return VariantsBlockStateSupplier.create(block, new BlockStateVariant()
        .put(VariantSettings.MODEL, modelId)
        .put(VariantSettings.UVLOCK, true)
    ).coordinate(BlockStateVariantMap.create(ExtShapeVerticalStairsBlock.FACING)
        .register(HorizontalCornerDirection.SOUTH_WEST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R0))
        .register(HorizontalCornerDirection.NORTH_WEST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R90))
        .register(HorizontalCornerDirection.NORTH_EAST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R180))
        .register(HorizontalCornerDirection.SOUTH_EAST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R270)));
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
  private static Identifier getUnwaxedModelId(Model model, Block block, TextureMap textures, BiConsumer<Identifier, Supplier<JsonElement>> modelCollector) {
    final Identifier id = Registries.BLOCK.getId(block);
    if (id.getPath().startsWith("waxed_")) {
      return ModelIds.getBlockSubModelId(Registries.BLOCK.getOrEmpty(id.withPath(s -> s.replace("waxed_", ""))).orElseThrow(), ((ModelAccessor) model).getVariant().orElse(""));
    }
    return model.upload(block, textures, modelCollector);
  }
}
