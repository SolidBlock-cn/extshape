package pers.solid.extshape.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.VariantsBlockStateSupplier;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.data.ExtShapeModelProvider;
import pers.solid.extshape.util.ActivationSettings;

public class ExtShapeHorizontalFacingPressurePlateBlock extends ExtShapePressurePlateBlock {
  public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

  public ExtShapeHorizontalFacingPressurePlateBlock(@NotNull Block baseBlock, Settings settings, @NotNull ActivationSettings activationSettings) {
    super(baseBlock, settings, activationSettings);
    setDefaultState(getDefaultState().with(FACING, Direction.SOUTH));
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

  @SuppressWarnings("deprecation")
  @Override
  public BlockState rotate(BlockState state, BlockRotation rotation) {
    return state.with(FACING, rotation.rotate(state.get(FACING)));
  }

  @SuppressWarnings("deprecation")
  @Override
  public BlockState mirror(BlockState state, BlockMirror mirror) {
    return state.rotate(mirror.getRotation(state.get(FACING)));
  }

  @Override
  public void registerModel(ExtShapeModelProvider modelProvider, BlockStateModelGenerator blockStateModelGenerator) {
    final Identifier upModelId = Models.PRESSURE_PLATE_UP.upload(this, modelProvider.getTextureMap(baseBlock, blockStateModelGenerator), blockStateModelGenerator.modelCollector);
    final Identifier downModelId = Models.PRESSURE_PLATE_DOWN.upload(this, modelProvider.getTextureMap(baseBlock, blockStateModelGenerator), blockStateModelGenerator.modelCollector);
    blockStateModelGenerator.blockStateCollector.accept(((VariantsBlockStateSupplier) BlockStateModelGenerator.createPressurePlateBlockState(this, upModelId, downModelId)).coordinate(BlockStateModelGenerator.createSouthDefaultHorizontalRotationStates()));
  }
}
