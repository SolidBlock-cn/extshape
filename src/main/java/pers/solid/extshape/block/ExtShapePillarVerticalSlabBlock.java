package pers.solid.extshape.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.data.ExtShapeBlockStateModelGenerator;
import pers.solid.extshape.data.ExtShapeModelProvider;
import pers.solid.extshape.util.BlockCollections;

public class ExtShapePillarVerticalSlabBlock extends ExtShapeVerticalSlabBlock {
  public static final EnumProperty<Direction.Axis> AXIS = PillarBlock.AXIS;

  public ExtShapePillarVerticalSlabBlock(@NotNull Block baseBlock, Settings settings) {
    super(baseBlock, settings);
    setDefaultState(getDefaultState().with(AXIS, Direction.Axis.Y));
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    super.appendProperties(builder);
    builder.add(AXIS);
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    final BlockState placementState = super.getPlacementState(ctx);
    final BlockState oldState = ctx.getWorld().getBlockState(ctx.getBlockPos());
    if (oldState.isOf(this) && placementState != null) {
      return placementState.with(AXIS, oldState.get(AXIS));
    }
    return placementState != null ? placementState.with(AXIS, ctx.getSide().getAxis()) : null;
  }

  @Override
  public BlockState rotate(BlockState state, BlockRotation rotation) {
    return PillarBlock.changeRotation(super.rotate(state, rotation), rotation);
  }

  @Override
  public void registerModel(ExtShapeModelProvider modelProvider, BlockStateModelGenerator blockStateModelGenerator) {
    ExtShapeBlockStateModelGenerator.registerPillarVerticalSlab(this, modelProvider.getTextureMap(baseBlock, blockStateModelGenerator), blockStateModelGenerator, BlockCollections.LOGS.contains(baseBlock) || BlockCollections.STRIPPED_LOGS.contains(baseBlock));
  }
}

