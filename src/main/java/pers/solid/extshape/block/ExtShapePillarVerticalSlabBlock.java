package pers.solid.extshape.block;

import com.google.gson.JsonPrimitive;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.data.client.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;
import pers.solid.brrp.v1.BRRPUtils;
import pers.solid.brrp.v1.api.RuntimeResourcePack;
import pers.solid.brrp.v1.model.ModelJsonBuilder;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.util.BlockCollections;

public class ExtShapePillarVerticalSlabBlock extends ExtShapeVerticalSlabBlock {
  public static final MapCodec<ExtShapePillarVerticalSlabBlock> CODEC = BRRPUtils.createCodecWithBaseBlock(createSettingsCodec(), ExtShapePillarVerticalSlabBlock::new);
  public static final EnumProperty<Direction.Axis> AXIS = PillarBlock.AXIS;

  public ExtShapePillarVerticalSlabBlock(@NotNull Block baseBlock, Settings settings) {
    super(baseBlock, settings);
    setDefaultState(getDefaultState().with(AXIS, Direction.Axis.Y));
  }

  @Environment(EnvType.CLIENT)
  @Override
  public @UnknownNullability BlockStateSupplier getBlockStates() {
    final BlockStateVariantMap.DoubleProperty<Direction.Axis, Direction> variants = BlockStateVariantMap.create(AXIS, FACING);
    final Identifier modelId = getBlockModelId();
    final Identifier horizontalModelId = modelId.brrp_suffixed("_horizontal");
    final VariantSetting<Integer> yRotation = new VariantSetting<>("y", JsonPrimitive::new);

    for (Direction facing : Direction.Type.HORIZONTAL) {
      final int rotation = (int) facing.asRotation();
      variants.register(Direction.Axis.Y, facing, new BlockStateVariant().put(VariantSettings.MODEL, modelId).put(yRotation, rotation).put(VariantSettings.UVLOCK, true));
      final boolean usesTopModel = facing == Direction.NORTH || facing == Direction.EAST;
      variants.register(facing.getAxis(), facing, new BlockStateVariant().put(VariantSettings.MODEL, usesTopModel ? horizontalModelId.brrp_suffixed("_top") : horizontalModelId).put(yRotation, usesTopModel ? rotation - 180 : rotation).put(VariantSettings.X, VariantSettings.Rotation.R90));
      variants.register(facing.rotateYClockwise().getAxis(), facing, new BlockStateVariant().put(VariantSettings.MODEL, usesTopModel ? horizontalModelId.brrp_suffixed("_unordered_top") : horizontalModelId.brrp_suffixed("_unordered")).put(yRotation, usesTopModel ? rotation - 90 : rotation + 90).put(VariantSettings.X, VariantSettings.Rotation.R90));
    }

    return VariantsBlockStateSupplier.create(this).coordinate(variants);
  }

  @Environment(EnvType.CLIENT)
  @Override
  public void writeBlockModel(RuntimeResourcePack pack) {
    super.writeBlockModel(pack);
    final boolean isLog = BlockCollections.LOGS.contains(baseBlock) || BlockCollections.STRIPPED_LOGS.contains(baseBlock);
    final ModelJsonBuilder blockModel = getBlockModel();
    if (isLog) {
      pack.addModel(getBlockModelId().brrp_suffixed("_horizontal"), blockModel.withParent(Identifier.of(ExtShape.MOD_ID, "block/vertical_slab_column_horizontal")));
      pack.addModel(getBlockModelId().brrp_suffixed("_horizontal_top"), blockModel.withParent(Identifier.of(ExtShape.MOD_ID, "block/vertical_slab_column_horizontal_top")));
      pack.addModel(getBlockModelId().brrp_suffixed("_horizontal_unordered"), blockModel.withParent(Identifier.of(ExtShape.MOD_ID, "block/vertical_slab_column_horizontal_unordered")));
      pack.addModel(getBlockModelId().brrp_suffixed("_horizontal_unordered_top"), blockModel.withParent(Identifier.of(ExtShape.MOD_ID, "block/vertical_slab_column_horizontal_unordered_top")));
    } else {
      pack.addModel(getBlockModelId().brrp_suffixed("_horizontal"), blockModel.withParent(Identifier.of(ExtShape.MOD_ID, "block/vertical_slab_column")));
      pack.addModel(getBlockModelId().brrp_suffixed("_horizontal_top"), blockModel.withParent(Identifier.of(ExtShape.MOD_ID, "block/vertical_slab_column_top")));
      pack.addModel(getBlockModelId().brrp_suffixed("_horizontal_unordered"), blockModel.withParent(Identifier.of(ExtShape.MOD_ID, "block/vertical_slab_column_unordered")));
      pack.addModel(getBlockModelId().brrp_suffixed("_horizontal_unordered_top"), blockModel.withParent(Identifier.of(ExtShape.MOD_ID, "block/vertical_slab_column_unordered_top")));
    }
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    super.appendProperties(builder);
    builder.add(AXIS);
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    final BlockState placementState = super.getPlacementState(ctx);
    final BlockState oldState = ctx.getWorld().getBlockState(ctx.getBlockPos());
    if (oldState.isOf(this) && placementState != null) {
      return placementState.with(AXIS, oldState.get(AXIS));
    }
    return placementState != null ? placementState.with(AXIS, ctx.getSide().getAxis()) : null;
  }

  @Override
  public BlockState rotate(BlockState state, BlockRotation rotation) {
    return PillarBlock.changeRotation(super.rotate(state, rotation), rotation);
  }

  @Override
  protected MapCodec<? extends ExtShapePillarVerticalSlabBlock> getCodec() {
    return CODEC;
  }
}

