package pers.solid.extshape.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * 竖直台阶。
 */
public class VerticalSlabBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
  public static final EnumProperty<Direction> HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
  protected static final VoxelShape NORTH_SHAPE = Block.box(0, 0, 0, 16, 16, 8);
  protected static final VoxelShape SOUTH_SHAPE = Block.box(0, 0, 8, 16, 16, 16);
  protected static final VoxelShape EAST_SHAPE = Block.box(8, 0, 0, 16, 16, 16);
  protected static final VoxelShape WEST_SHAPE = Block.box(0, 0, 0, 8, 16, 16);
  public static final MapCodec<VerticalSlabBlock> CODEC = simpleCodec(VerticalSlabBlock::new);

  public VerticalSlabBlock(Properties settings) {
    super(settings);
    registerDefaultState(defaultBlockState().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(WATERLOGGED, false));
  }

  @Override
  protected MapCodec<? extends VerticalSlabBlock> codec() {
    return CODEC;
  }


  @Override
  protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
    if (state.getValue(WATERLOGGED)) {
      tickView.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
    }
    return super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateManager) {
    stateManager.add(HORIZONTAL_FACING).add(WATERLOGGED);
  }

  @Override
  public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
    Direction dir = state.getValue(HORIZONTAL_FACING);
    return switch (dir) {
      case NORTH -> NORTH_SHAPE;
      case SOUTH -> SOUTH_SHAPE;
      case EAST -> EAST_SHAPE;
      case WEST -> WEST_SHAPE;
      default -> Shapes.block();
    };
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext ctx) {
    BlockPos blockPos = ctx.getClickedPos();
    FluidState fluidState = ctx.getLevel().getFluidState(blockPos);
    final Direction.Axis axis = ctx.getHorizontalDirection().getAxis();
    final double d = ctx.getClickLocation().get(axis) - blockPos.get(axis);
    final Direction facing = switch (axis) {
      case X -> d < 0.5 ? Direction.WEST : Direction.EAST;
      case Z -> d < 0.5 ? Direction.NORTH : Direction.SOUTH;
      default -> Direction.NORTH;
    };
    return this.defaultBlockState().setValue(FACING, facing).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
  }

  @Override
  public FluidState getFluidState(BlockState state) {
    return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
  }

  @Override
  protected boolean isPathfindable(BlockState state, PathComputationType type) {
    return false;
  }
}
