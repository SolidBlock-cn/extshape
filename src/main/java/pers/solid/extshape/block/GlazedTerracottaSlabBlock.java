package pers.solid.extshape.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class GlazedTerracottaSlabBlock extends ExtShapeSlabBlock {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public GlazedTerracottaSlabBlock(Settings settings) {
        super(settings);
        this.getDefaultState().with(FACING, Direction.NORTH);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = ctx.getWorld().getBlockState(blockPos);
        BlockState state = super.getPlacementState(ctx);
        if (!blockState.isOf(this) && state != null)
            return state.with(FACING, ctx.getPlayerFacing().getOpposite());
        else return state;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    @Override
    @SuppressWarnings("deprecation")
    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.PUSH_ONLY;
    }
}
