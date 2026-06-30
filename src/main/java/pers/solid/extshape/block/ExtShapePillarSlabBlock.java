package pers.solid.extshape.block;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jspecify.annotations.Nullable;
import pers.solid.extshape.data.ExtShapeBlockStateModelGenerator;
import pers.solid.extshape.data.ExtShapeModelProvider;
import pers.solid.extshape.util.BlockCollections;

import java.util.Arrays;
import java.util.Set;

/**
 * 类似于普通的台阶，但是像 {@link RotatedPillarBlock} 那样拥有摆放的方向。
 */
public class ExtShapePillarSlabBlock extends ExtShapeSlabBlock {
  public static final Set<Block> BASE_BLOCKS_WITH_HORIZONTAL_COLUMN = Sets.newHashSet(Iterables.concat(
      BlockCollections.LOGS,
      BlockCollections.STRIPPED_LOGS,
      Arrays.asList(Blocks.HAY_BLOCK, Blocks.PURPUR_PILLAR, Blocks.QUARTZ_PILLAR, Blocks.OCHRE_FROGLIGHT, Blocks.VERDANT_FROGLIGHT, Blocks.PEARLESCENT_FROGLIGHT)
  ));
  public static final EnumProperty<Direction.Axis> AXIS = RotatedPillarBlock.AXIS;

  public ExtShapePillarSlabBlock(Block baseBlock, Properties settings) {
    super(baseBlock, settings);
    registerDefaultState(defaultBlockState().setValue(AXIS, Direction.Axis.Y));
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    super.createBlockStateDefinition(builder);
    builder.add(AXIS);
  }

  @Override
  public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
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

  @Environment(EnvType.CLIENT)
  @Override
  public void registerModel(ExtShapeModelProvider modelProvider, BlockModelGenerators blockStateModelGenerator) {
    ExtShapeBlockStateModelGenerator.registerPillarSlab(this, baseBlock, modelProvider.getTextureMap(baseBlock, blockStateModelGenerator), blockStateModelGenerator, BASE_BLOCKS_WITH_HORIZONTAL_COLUMN.contains(baseBlock));
  }
}
