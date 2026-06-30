package pers.solid.extshape.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * 横条方块是指只占 1/4 的体积（其中只有 1/2 的高度）的方块。
 */
public class QuarterPieceBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
  public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
  public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

  protected static final VoxelShape NORTH_BOTTOM_SHAPE = Block.box(0, 0, 0, 16, 8, 8);
  protected static final VoxelShape SOUTH_BOTTOM_SHAPE = Block.box(0, 0, 8, 16, 8, 16);
  protected static final VoxelShape EAST_BOTTOM_SHAPE = Block.box(8, 0, 0, 16, 8, 16);
  protected static final VoxelShape WEST_BOTTOM_SHAPE = Block.box(0, 0, 0, 8, 8, 16);

  protected static final VoxelShape NORTH_TOP_SHAPE = Block.box(0, 8, 0, 16, 16, 8);
  protected static final VoxelShape SOUTH_TOP_SHAPE = Block.box(0, 8, 8, 16, 16, 16);
  protected static final VoxelShape EAST_TOP_SHAPE = Block.box(8, 8, 0, 16, 16, 16);
  protected static final VoxelShape WEST_TOP_SHAPE = Block.box(0, 8, 0, 8, 16, 16);

  public QuarterPieceBlock(Properties settings) {
    super(settings);
    registerDefaultState(defaultBlockState()
        .setValue(FACING, Direction.NORTH)
        .setValue(WATERLOGGED, false)
        .setValue(HALF, Half.BOTTOM));
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
    stateManager.add(FACING).add(HALF).add(WATERLOGGED);
  }

  @Override
  public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
    Direction dir = state.getValue(FACING);
    Half half = state.getValue(HALF);
    return switch (half) {
      case TOP -> switch (dir) {
        case NORTH -> NORTH_TOP_SHAPE;
        case SOUTH -> SOUTH_TOP_SHAPE;
        case EAST -> EAST_TOP_SHAPE;
        case WEST -> WEST_TOP_SHAPE;
        default -> Shapes.empty();
      };
      case BOTTOM -> switch (dir) {
        case NORTH -> NORTH_BOTTOM_SHAPE;
        case SOUTH -> SOUTH_BOTTOM_SHAPE;
        case EAST -> EAST_BOTTOM_SHAPE;
        case WEST -> WEST_BOTTOM_SHAPE;
        default -> Shapes.empty();
      };
    };
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext ctx) {
    Direction direction = ctx.getClickedFace();
    BlockPos blockPos = ctx.getClickedPos();
    FluidState fluidState = ctx.getLevel().getFluidState(blockPos);
    final Direction.Axis axis = ctx.getHorizontalDirection().getAxis();
    final double d = ctx.getClickLocation().get(axis) - blockPos.get(axis);
    final Direction facing = switch (axis) {
      case X -> d < 0.5 ? Direction.WEST : Direction.EAST;
      case Z -> d < 0.5 ? Direction.NORTH : Direction.SOUTH;
      default -> Direction.NORTH;
    };
    return this.defaultBlockState()
        .setValue(FACING, facing)
        .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER)
        .setValue(HALF, direction != Direction.DOWN && (direction == Direction.UP || !(ctx.getClickLocation().y - (double) blockPos.getY() > 0.5D)) ? Half.BOTTOM : Half.TOP);
  }

  @Override
  public FluidState getFluidState(BlockState state) {
    return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
  }

  @Override
  protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
    if (state.getValue(WATERLOGGED)) {
      tickView.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
    }
    return super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
  }

  @Override
  protected boolean isPathfindable(BlockState state, PathComputationType type) {
    return type == PathComputationType.WATER && state.getFluidState().is(FluidTags.WATER);
  }
}
