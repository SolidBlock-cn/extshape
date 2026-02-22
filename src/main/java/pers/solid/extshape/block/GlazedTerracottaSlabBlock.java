package pers.solid.extshape.block;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.data.ExtShapeBlockStateModelGenerator;
import pers.solid.extshape.data.ExtShapeModelProvider;

/**
 * 带釉陶瓦台阶方块。
 */
public class GlazedTerracottaSlabBlock extends ExtShapeSlabBlock {
  public static final MapCodec<GlazedTerracottaSlabBlock> CODEC = ExtShapeBlockInterface.createCodecWithBaseBlock(propertiesCodec(), GlazedTerracottaSlabBlock::new);
  public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

  public GlazedTerracottaSlabBlock(@NotNull Block baseBlock, Properties settings) {
    super(baseBlock, settings);
    registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
  }

  @Nullable
  @Override
  public BlockState getStateForPlacement(BlockPlaceContext ctx) {
    BlockPos blockPos = ctx.getClickedPos();
    BlockState blockState = ctx.getLevel().getBlockState(blockPos);
    BlockState state = super.getStateForPlacement(ctx);
    if (!blockState.is(this) && state != null) {
      return state.setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    } else {
      return state;
    }
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    super.createBlockStateDefinition(builder);
    builder.add(FACING);
  }

  @Override
  public MapCodec<? extends GlazedTerracottaSlabBlock> codec() {
    return CODEC;
  }

  @Environment(EnvType.CLIENT)
  @Override
  public void registerModel(ExtShapeModelProvider modelProvider, BlockModelGenerators blockStateModelGenerator) {
    ExtShapeBlockStateModelGenerator.registerGlazedTerracottaSlab(this, baseBlock, modelProvider.getTextureMap(baseBlock, blockStateModelGenerator), blockStateModelGenerator);
  }
}
