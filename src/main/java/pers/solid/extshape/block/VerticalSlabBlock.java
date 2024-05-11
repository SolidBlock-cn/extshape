package pers.solid.extshape.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

/**
 * 竖直台阶。
 */
public class VerticalSlabBlock extends HorizontalFacingBlock implements Waterloggable {
  public static final DirectionProperty HORIZONTAL_FACING = Properties.HORIZONTAL_FACING;
  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
  protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 16, 8);
  protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0, 0, 8, 16, 16, 16);
  protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(8, 0, 0, 16, 16, 16);
  protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0, 0, 0, 8, 16, 16);
  public static final MapCodec<VerticalSlabBlock> CODEC = createCodec(VerticalSlabBlock::new);

  public VerticalSlabBlock(Settings settings) {
    super(settings);
    setDefaultState(getDefaultState().with(HORIZONTAL_FACING, Direction.NORTH).with(WATERLOGGED, false));
  }

  @Override
  protected MapCodec<? extends VerticalSlabBlock> getCodec() {
    return CODEC;
  }


  @Override
  public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
    if (state.get(WATERLOGGED)) {
      world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
    }
    return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
    stateManager.add(HORIZONTAL_FACING).add(WATERLOGGED);
  }

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    Direction dir = state.get(HORIZONTAL_FACING);
    return switch (dir) {
      case NORTH -> NORTH_SHAPE;
      case SOUTH -> SOUTH_SHAPE;
      case EAST -> EAST_SHAPE;
      case WEST -> WEST_SHAPE;
      default -> VoxelShapes.fullCube();
    };
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    BlockPos blockPos = ctx.getBlockPos();
    FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
    final Direction.Axis axis = ctx.getHorizontalPlayerFacing().getAxis();
    final double d = ctx.getHitPos().getComponentAlongAxis(axis) - blockPos.getComponentAlongAxis(axis);
    final Direction facing = switch (axis) {
      case X -> d < 0.5 ? Direction.WEST : Direction.EAST;
      case Z -> d < 0.5 ? Direction.NORTH : Direction.SOUTH;
      default -> Direction.NORTH;
    };
    return this.getDefaultState().with(FACING, facing).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
  }

  @Override
  public FluidState getFluidState(BlockState state) {
    return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
  }
}
