package pers.solid.extshape.block;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.Models;
import net.minecraft.client.data.VariantsBlockModelDefinitionCreator;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.data.ExtShapeModelProvider;
import pers.solid.extshape.mixin.BlockStateModelGeneratorAccessor;
import pers.solid.extshape.util.ActivationSettings;

public class ExtShapeHorizontalFacingPressurePlateBlock extends ExtShapePressurePlateBlock {
  public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
  public static final MapCodec<ExtShapeHorizontalFacingPressurePlateBlock> CODEC = createCodec(ExtShapeHorizontalFacingPressurePlateBlock::new);

  public ExtShapeHorizontalFacingPressurePlateBlock(@NotNull Block baseBlock, Settings settings, @NotNull BlockSetType blockSetType, int tickRate) {
    super(baseBlock, settings, blockSetType, tickRate);
    setDefaultState(getDefaultState().with(FACING, Direction.SOUTH));
  }

  public ExtShapeHorizontalFacingPressurePlateBlock(@NotNull Block baseBlock, Settings settings, @NotNull ActivationSettings activationSettings) {
    this(baseBlock, settings, activationSettings.blockSetType(), activationSettings.plateTime());
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    super.appendProperties(builder);
    builder.add(FACING);
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    final BlockState placementState = super.getPlacementState(ctx);
    return placementState != null ? placementState.with(FACING, ctx.getHorizontalPlayerFacing().getOpposite()) : null;
  }

  @Override
  public BlockState rotate(BlockState state, BlockRotation rotation) {
    return state.with(FACING, rotation.rotate(state.get(FACING)));
  }

  @Override
  public BlockState mirror(BlockState state, BlockMirror mirror) {
    return state.rotate(mirror.getRotation(state.get(FACING)));
  }

  @SuppressWarnings("unchecked")
  @Override
  public MapCodec<PressurePlateBlock> getCodec() {
    return (MapCodec<PressurePlateBlock>) (MapCodec<?>) CODEC;
  }

  @Environment(EnvType.CLIENT)
  @Override
  public void registerModel(ExtShapeModelProvider modelProvider, BlockStateModelGenerator blockStateModelGenerator) {
    final WeightedVariant upModel = BlockStateModelGenerator.createWeightedVariant(Models.PRESSURE_PLATE_UP.upload(this, modelProvider.getTextureMap(baseBlock, blockStateModelGenerator), blockStateModelGenerator.modelCollector));
    final WeightedVariant downModel = BlockStateModelGenerator.createWeightedVariant(Models.PRESSURE_PLATE_DOWN.upload(this, modelProvider.getTextureMap(baseBlock, blockStateModelGenerator), blockStateModelGenerator.modelCollector));
    blockStateModelGenerator.blockStateCollector.accept(((VariantsBlockModelDefinitionCreator) BlockStateModelGenerator.createPressurePlateBlockState(this, upModel, downModel)).coordinate(BlockStateModelGeneratorAccessor.getSOUTH_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS()));
  }
}
