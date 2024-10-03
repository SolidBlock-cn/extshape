package pers.solid.extshape.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.data.ExtShapeBlockStateModelGenerator;
import pers.solid.extshape.data.ExtShapeModelProvider;

public class CircularPavingSlabBlock extends ExtShapeSlabBlock {
  public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;

  public CircularPavingSlabBlock(@NotNull Block baseBlock, Settings settings) {
    super(baseBlock, settings);
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
    if (placementState != null && placementState.get(TYPE) == SlabType.DOUBLE) {
      return placementState;
    }
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

  @Override
  public void registerModel(ExtShapeModelProvider modelProvider, BlockStateModelGenerator blockStateModelGenerator) {
    ExtShapeBlockStateModelGenerator.registerCircularPavingSlab(this, baseBlock, modelProvider.getTextureMap(baseBlock, blockStateModelGenerator), blockStateModelGenerator);
  }
}
