package pers.solid.extshape.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExtShapeGlazedTerracottaSlabBlock extends ExtShapeSlabBlock{
    public static final DirectionProperty FACING =Properties.HORIZONTAL_FACING;

    public ExtShapeGlazedTerracottaSlabBlock(@NotNull Block baseBlock, @Nullable Settings settings) {
        super(baseBlock, settings);
        this.getDefaultState().with(FACING, Direction.NORTH);
    }

    public ExtShapeGlazedTerracottaSlabBlock(@NotNull Block baseBlock) {
        this(baseBlock,null);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = ctx.getWorld().getBlockState(blockPos);
        BlockState state = super.getPlacementState(ctx);
        if (!blockState.isOf(this) && state!=null)
        return state.with(FACING, ctx.getPlayerFacing().getOpposite());
        else return state;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }

    @Override
    public String getTopBlockModelString() {
        return String.format("""
                {
                  "parent": "extshape:block/glazed_terracotta_slab_top",
                  "textures": {
                    "bottom": "%s",
                    "top": "%s",
                    "side": "%s"
                  }
                }
                """, this.getBottomTexture(), this.getTopTexture(), this.getSideTexture());
    }

    @Override
    public String getBlockModelString() {
        return String.format("""
                {
                  "parent": "extshape:block/glazed_terracotta_slab",
                  "textures": {
                    "bottom": "%s",
                    "top": "%s",
                    "side": "%s"
                  }
                }
                """, this.getBottomTexture(), this.getTopTexture(), this.getSideTexture());
    }

    @Override
    public String getBlockStatesString() {
        Identifier baseIdentifier = this.getBaseBlockIdentifier();
        return String.format("""
                {
                  "variants": {
                    "type=bottom,facing=south": {
                      "model": "%1$s"
                    },
                    "type=double,facing=south": {
                      "model": "%2$s"
                    },
                    "type=top,facing=south": {
                      "model": "%1$s_top"
                    },
                    "type=bottom,facing=west": {
                      "model": "%1$s",
                      "y": 90
                    },
                    "type=double,facing=west": {
                      "model": "%2$s",
                      "y": 90
                    },
                    "type=top,facing=west": {
                      "model": "%1$s_top",
                      "y": 90
                    },
                    "type=bottom,facing=north": {
                      "model": "%1$s",
                      "y": 180
                    },
                    "type=double,facing=north": {
                      "model": "%2$s",
                      "y": 180
                    },
                    "type=top,facing=north": {
                      "model": "%1$s_top",
                      "y": 180
                    },
                    "type=bottom,facing=east": {
                      "model": "%1$s",
                      "y": 270
                    },
                    "type=double,facing=east": {
                      "model": "%2$s",
                      "y": 270
                    },
                    "type=top,facing=east": {
                      "model": "%1$s_top",
                      "y": 270
                    }
                  }
                }""", this.getBlockModelIdentifier(),new Identifier(baseIdentifier.getNamespace(),
                "block/"+baseIdentifier.getPath()));
    }

    public PistonBehavior getPistonBehavior(BlockState state) {
        return PistonBehavior.PUSH_ONLY;
    }
}
