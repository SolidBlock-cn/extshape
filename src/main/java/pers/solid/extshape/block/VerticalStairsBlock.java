package pers.solid.extshape.block;

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
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.HorizontalCornerDirection;

import java.util.Map;

public class VerticalStairsBlock extends Block implements Waterloggable {
    public VerticalStairsBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(FACING, HorizontalCornerDirection.SOUTH_WEST));
    }

    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final EnumProperty<HorizontalCornerDirection> FACING = VerticalQuarterPieceBlock.FACING;
    public static final Map<HorizontalCornerDirection, VoxelShape> VOXELS = VerticalQuarterPieceBlock.VOXELS;

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED).add(FACING);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        return this.getDefaultState().with(FACING, HorizontalCornerDirection.fromRotation(ctx.getPlayerYaw())).with(WATERLOGGED,
                fluidState.getFluid() == Fluids.WATER);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        var dir = state.get(FACING).getOpposite();
        var voxel = VOXELS.get(dir);
        return VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(),voxel, BooleanBiFunction.ONLY_FIRST);
    }
}
