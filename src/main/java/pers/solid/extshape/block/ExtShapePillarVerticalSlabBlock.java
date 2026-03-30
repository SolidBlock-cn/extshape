package pers.solid.extshape.block;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import pers.solid.extshape.data.ExtShapeBlockStateModelGenerator;
import pers.solid.extshape.data.ExtShapeModelProvider;
import pers.solid.extshape.util.BlockCollections;

public class ExtShapePillarVerticalSlabBlock extends ExtShapeVerticalSlabBlock {
  public static final MapCodec<ExtShapePillarVerticalSlabBlock> CODEC = ExtShapeBlockInterface.createCodecWithBaseBlock(propertiesCodec(), ExtShapePillarVerticalSlabBlock::new);
  public static final EnumProperty<Direction.Axis> AXIS = RotatedPillarBlock.AXIS;

  public ExtShapePillarVerticalSlabBlock(Block baseBlock, Properties settings) {
    super(baseBlock, settings);
    registerDefaultState(defaultBlockState().setValue(AXIS, Direction.Axis.Y));
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    super.createBlockStateDefinition(builder);
    builder.add(AXIS);
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext ctx) {
    final BlockState placementState = super.getStateForPlacement(ctx);
    final BlockState oldState = ctx.getLevel().getBlockState(ctx.getClickedPos());
    if (oldState.is(this) && placementState != null) {
      return placementState.setValue(AXIS, oldState.getValue(AXIS));
    }
    return placementState != null ? placementState.setValue(AXIS, ctx.getClickedFace().getAxis()) : null;
  }

  @Override
  public BlockState rotate(BlockState state, Rotation rotation) {
    return RotatedPillarBlock.rotatePillar(super.rotate(state, rotation), rotation);
  }

  @Override
  protected MapCodec<? extends ExtShapePillarVerticalSlabBlock> codec() {
    return CODEC;
  }

  @Environment(EnvType.CLIENT)
  @Override
  public void registerModel(ExtShapeModelProvider modelProvider, BlockModelGenerators blockStateModelGenerator) {
    ExtShapeBlockStateModelGenerator.registerPillarVerticalSlab(this, modelProvider.getTextureMap(baseBlock, blockStateModelGenerator), blockStateModelGenerator, BlockCollections.LOGS.contains(baseBlock) || BlockCollections.STRIPPED_LOGS.contains(baseBlock));
  }
}

