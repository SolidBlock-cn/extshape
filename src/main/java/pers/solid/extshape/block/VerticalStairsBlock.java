package pers.solid.extshape.block;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.util.HorizontalCornerDirection;

public class VerticalStairsBlock extends Block implements SimpleWaterloggedBlock {
  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
  public static final EnumProperty<HorizontalCornerDirection> FACING = VerticalQuarterPieceBlock.FACING;
  public static final ImmutableMap<HorizontalCornerDirection, VoxelShape> VOXELS;
  public static final MapCodec<VerticalStairsBlock> CODEC = simpleCodec(VerticalStairsBlock::new);

  static {
    ImmutableMap.Builder<HorizontalCornerDirection, VoxelShape> builder = new ImmutableMap.Builder<>();
    VerticalQuarterPieceBlock.VOXELS.forEach((horizontalCornerDirection, voxelShape) -> builder.put(horizontalCornerDirection.getOpposite(), Shapes.join(Shapes.block(), voxelShape, BooleanOp.ONLY_FIRST)));
    VOXELS = builder.build();
  }

  public VerticalStairsBlock(Properties settings) {
    super(settings);
    this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false).setValue(FACING, HorizontalCornerDirection.SOUTH_WEST));
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    builder.add(WATERLOGGED).add(FACING);
  }

  @Nullable
  @Override
  public BlockState getStateForPlacement(BlockPlaceContext ctx) {
    BlockPos blockPos = ctx.getClickedPos();
    FluidState fluidState = ctx.getLevel().getFluidState(blockPos);
    double x_diff = ctx.getClickLocation().x - ctx.getClickedPos().getX();
    double z_diff = ctx.getClickLocation().z - ctx.getClickedPos().getZ();
    final HorizontalCornerDirection facing;
    if (x_diff < 0.5) {
      facing = z_diff < 0.5 ? HorizontalCornerDirection.NORTH_WEST : HorizontalCornerDirection.SOUTH_WEST;
    } else {
      facing = z_diff < 0.5 ? HorizontalCornerDirection.NORTH_EAST : HorizontalCornerDirection.SOUTH_EAST;
    }
    return this.defaultBlockState()
        .setValue(FACING, facing)
        .setValue(WATERLOGGED,
            fluidState.getType() == Fluids.WATER);
  }

  @Override
  public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
    return VOXELS.get(state.getValue(FACING));
  }

  @Override
  public BlockState rotate(BlockState state, Rotation rotation) {
    return super.rotate(state, rotation).setValue(FACING, state.getValue(FACING).rotate(rotation));
  }

  @Override
  public BlockState mirror(BlockState state, Mirror mirror) {
    return super.mirror(state, mirror).setValue(FACING, state.getValue(FACING).mirror(mirror));
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
  protected MapCodec<? extends VerticalStairsBlock> codec() {
    return CODEC;
  }

  @Override
  protected boolean isPathfindable(BlockState state, PathComputationType type) {
    return false;
  }
}
