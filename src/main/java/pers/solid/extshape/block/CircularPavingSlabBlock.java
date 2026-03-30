package pers.solid.extshape.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.data.ExtShapeBlockStateModelGenerator;
import pers.solid.extshape.data.ExtShapeModelProvider;

public class CircularPavingSlabBlock extends ExtShapeSlabBlock {
  public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

  public CircularPavingSlabBlock(Block baseBlock, Properties settings) {
    super(baseBlock, settings);
    registerDefaultState(defaultBlockState().setValue(FACING, Direction.SOUTH));
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    super.createBlockStateDefinition(builder);
    builder.add(FACING);
  }

  @Override
  public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
    final BlockState placementState = super.getStateForPlacement(ctx);
    if (placementState != null && placementState.getValue(TYPE) == SlabType.DOUBLE) {
      return placementState;
    }
    return placementState != null ? placementState.setValue(FACING, ctx.getHorizontalDirection().getOpposite()) : null;
  }

  @Override
  public BlockState rotate(BlockState state, Rotation rotation) {
    return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
  }

  @Override
  public BlockState mirror(BlockState state, Mirror mirror) {
    return state.rotate(mirror.getRotation(state.getValue(FACING)));
  }

  @Environment(EnvType.CLIENT)
  @Override
  public void registerModel(ExtShapeModelProvider modelProvider, BlockModelGenerators blockStateModelGenerator) {
    ExtShapeBlockStateModelGenerator.registerCircularPavingSlab(this, baseBlock, modelProvider.getTextureMap(baseBlock, blockStateModelGenerator), blockStateModelGenerator);
  }
}
