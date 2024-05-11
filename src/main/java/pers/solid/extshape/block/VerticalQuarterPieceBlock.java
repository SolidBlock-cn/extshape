package pers.solid.extshape.block;

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
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.util.HorizontalCornerDirection;

import java.util.EnumMap;
import java.util.Map;

public class VerticalQuarterPieceBlock extends Block implements Waterloggable {
  public static final Map<HorizontalCornerDirection, VoxelShape> VOXELS = new EnumMap<>(HorizontalCornerDirection.class);
  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
  public static final EnumProperty<HorizontalCornerDirection> FACING = EnumProperty.of("facing",
      HorizontalCornerDirection.class);
  public static final MapCodec<VerticalQuarterPieceBlock> CODEC = createCodec(VerticalQuarterPieceBlock::new);

  static {
    for (HorizontalCornerDirection dir : HorizontalCornerDirection.values()) {
      Vec3i vec = dir.getVector();
      VOXELS.put(dir, VoxelShapes.cuboid(Math.min(vec.getX() + 1, 1) * 0.5, 0,
          Math.min(vec.getZ() + 1, 1) * 0.5, Math.max(vec.getX() + 1, 1) * 0.5, 1,
          Math.max(vec.getZ() + 1, 1) * 0.5));
    }
  }

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
  public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
    if (state.get(WATERLOGGED)) {
      world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
    }
    return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
  }

  @Override
  protected MapCodec<? extends VerticalQuarterPieceBlock> getCodec() {
    return CODEC;
  }
}
