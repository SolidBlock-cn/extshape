package pers.solid.extshape.block;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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

import java.util.Arrays;
import java.util.Set;

/**
 * 类似于普通的台阶，但是像 {@link PillarBlock} 那样拥有摆放的方向。
 */
public class ExtShapePillarSlabBlock extends ExtShapeSlabBlock {
  public static final Set<Block> BASE_BLOCKS_WITH_HORIZONTAL_COLUMN = Sets.newHashSet(Iterables.concat(
      BlockCollections.LOGS,
      BlockCollections.STRIPPED_LOGS,
      Arrays.asList(Blocks.HAY_BLOCK, Blocks.PURPUR_PILLAR, Blocks.QUARTZ_PILLAR, Blocks.OCHRE_FROGLIGHT, Blocks.VERDANT_FROGLIGHT, Blocks.PEARLESCENT_FROGLIGHT)
  ));
  public static final EnumProperty<Direction.Axis> AXIS = PillarBlock.AXIS;

  public ExtShapePillarSlabBlock(@NotNull Block baseBlock, Settings settings) {
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

  @SuppressWarnings("deprecation")
  @Override
  public BlockState rotate(BlockState state, BlockRotation rotation) {
    return PillarBlock.changeRotation(super.rotate(state, rotation), rotation);
  }

  @Override
  public void registerModel(ExtShapeModelProvider modelProvider, BlockStateModelGenerator blockStateModelGenerator) {
    ExtShapeBlockStateModelGenerator.registerPillarSlab(this, baseBlock, modelProvider.getTextureMap(baseBlock, blockStateModelGenerator), blockStateModelGenerator, BASE_BLOCKS_WITH_HORIZONTAL_COLUMN.contains(baseBlock));
  }
}
