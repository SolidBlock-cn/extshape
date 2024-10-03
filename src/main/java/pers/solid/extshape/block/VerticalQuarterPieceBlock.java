package pers.solid.extshape.block;

import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.util.HorizontalCornerDirection;

import java.util.Arrays;
import java.util.Map;

/**
 * 纵条方块，只占 1/4 体积的方块。
 */
public class VerticalQuarterPieceBlock extends Block implements Waterloggable {
  public static final Map<HorizontalCornerDirection, VoxelShape> VOXELS = Maps.toMap(Arrays.asList(HorizontalCornerDirection.values()), dir -> {
    Vec3i vec = dir.getVector();
    return (VoxelShapes.cuboid(Math.min(vec.getX() + 1, 1) * 0.5, 0,
        Math.min(vec.getZ() + 1, 1) * 0.5, Math.max(vec.getX() + 1, 1) * 0.5, 1,
        Math.max(vec.getZ() + 1, 1) * 0.5));

  });
  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
  public static final EnumProperty<HorizontalCornerDirection> FACING = EnumProperty.of("facing",
      HorizontalCornerDirection.class);
  public static final MapCodec<VerticalQuarterPieceBlock> CODEC = createCodec(VerticalQuarterPieceBlock::new);

  public VerticalQuarterPieceBlock(Settings settings) {
    super(settings);
    this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(FACING, HorizontalCornerDirection.SOUTH_WEST));
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(WATERLOGGED).add(FACING);
  }

  @Nullable
  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    BlockPos blockPos = ctx.getBlockPos();
    FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
    double x_diff = ctx.getHitPos().x - ctx.getBlockPos().getX();
    double z_diff = ctx.getHitPos().z - ctx.getBlockPos().getZ();
    final HorizontalCornerDirection facing;
    if (x_diff < 0.5) {
      facing = z_diff < 0.5 ? HorizontalCornerDirection.NORTH_WEST : HorizontalCornerDirection.SOUTH_WEST;
    } else {
      facing = z_diff < 0.5 ? HorizontalCornerDirection.NORTH_EAST : HorizontalCornerDirection.SOUTH_EAST;
    }
    return this.getDefaultState()
        .with(FACING, facing)
        .with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
  }

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    HorizontalCornerDirection dir = state.get(FACING);
    return VOXELS.get(dir);
  }

  @Override
  public BlockState rotate(BlockState state, BlockRotation rotation) {
    return super.rotate(state, rotation).with(FACING, state.get(FACING).rotate(rotation));
  }

  @Override
  public BlockState mirror(BlockState state, BlockMirror mirror) {
    return super.mirror(state, mirror).with(FACING, state.get(FACING).mirror(mirror));
  }

  @Override
  public FluidState getFluidState(BlockState state) {
    return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
  }


  @Override
  protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
    if (state.get(WATERLOGGED)) {
      tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
    }
    return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
  }

  @Override
  protected MapCodec<? extends VerticalQuarterPieceBlock> getCodec() {
    return CODEC;
  }
}
