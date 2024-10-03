package pers.solid.extshape.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.data.ExtShapeBlockStateModelGenerator;
import pers.solid.extshape.data.ExtShapeModelProvider;

/**
 * 带釉陶瓦台阶方块。
 */
public class GlazedTerracottaSlabBlock extends ExtShapeSlabBlock {
  public static final MapCodec<GlazedTerracottaSlabBlock> CODEC = ExtShapeBlockInterface.createCodecWithBaseBlock(createSettingsCodec(), GlazedTerracottaSlabBlock::new);
  public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;

  public GlazedTerracottaSlabBlock(@NotNull Block baseBlock, Settings settings) {
    super(baseBlock, settings);
    setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
  }

  @Nullable
  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    BlockPos blockPos = ctx.getBlockPos();
    BlockState blockState = ctx.getWorld().getBlockState(blockPos);
    BlockState state = super.getPlacementState(ctx);
    if (!blockState.isOf(this) && state != null) {
      return state.with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    } else {
      return state;
    }
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    super.appendProperties(builder);
    builder.add(FACING);
  }

  @Override
  public MapCodec<? extends GlazedTerracottaSlabBlock> getCodec() {
    return CODEC;
  }

  @Override
  public void registerModel(ExtShapeModelProvider modelProvider, BlockStateModelGenerator blockStateModelGenerator) {
    ExtShapeBlockStateModelGenerator.registerGlazedTerracottaSlab(this, baseBlock, modelProvider.getTextureMap(baseBlock, blockStateModelGenerator), blockStateModelGenerator);
  }
}
