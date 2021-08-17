package pers.solid.extshape.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class QuarterPieceBlock extends HorizontalFacingBlock implements Waterloggable {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final EnumProperty<BlockHalf> HALF = Properties.BLOCK_HALF;

    protected static final VoxelShape NORTH_BOTTOM_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 8, 8);
    protected static final VoxelShape SOUTH_BOTTOM_SHAPE = Block.createCuboidShape(0, 0, 8, 16, 8, 16);
    protected static final VoxelShape EAST_BOTTOM_SHAPE = Block.createCuboidShape(8, 0, 0, 16, 8, 16);
    protected static final VoxelShape WEST_BOTTOM_SHAPE = Block.createCuboidShape(0, 0, 0, 8, 8, 16);

    protected static final VoxelShape NORTH_TOP_SHAPE = Block.createCuboidShape(0, 8, 0, 16, 16, 8);
    protected static final VoxelShape SOUTH_TOP_SHAPE = Block.createCuboidShape(0, 8, 8, 16, 16, 16);
    protected static final VoxelShape EAST_TOP_SHAPE = Block.createCuboidShape(8, 8, 0, 16, 16, 16);
    protected static final VoxelShape WEST_TOP_SHAPE = Block.createCuboidShape(0, 8, 0, 8, 16, 16);

    public QuarterPieceBlock(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED
                , false).with(HALF, BlockHalf.TOP));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> stateManager) {
        stateManager.add(FACING).add(HALF).add(WATERLOGGED);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction dir = state.get(FACING);
        BlockHalf half = state.get(HALF);
        return switch (half) {
            case TOP -> switch (dir) {
                case NORTH -> NORTH_TOP_SHAPE;
                case SOUTH -> SOUTH_TOP_SHAPE;
                case EAST -> EAST_TOP_SHAPE;
                case WEST -> WEST_TOP_SHAPE;
                default -> VoxelShapes.empty();
            };
            case BOTTOM -> switch (dir) {
                case NORTH -> NORTH_BOTTOM_SHAPE;
                case SOUTH -> SOUTH_BOTTOM_SHAPE;
                case EAST -> EAST_BOTTOM_SHAPE;
                case WEST -> WEST_BOTTOM_SHAPE;
                default -> VoxelShapes.empty();
            };
        };
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getSide();
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing()).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER).with(HALF, direction != Direction.DOWN && (direction == Direction.UP || !(ctx.getHitPos().y - (double) blockPos.getY() > 0.5D)) ? BlockHalf.BOTTOM : BlockHalf.TOP);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }
}
