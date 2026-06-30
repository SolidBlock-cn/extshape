package pers.solid.extshape.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.data.ExtShapeModelProvider;
import pers.solid.extshape.mixin.BlockStateModelGeneratorAccessor;
import pers.solid.extshape.util.ActivationSettings;

public class ExtShapeHorizontalFacingPressurePlateBlock extends ExtShapePressurePlateBlock {
  public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

  public ExtShapeHorizontalFacingPressurePlateBlock(Block baseBlock, Properties settings, BlockSetType blockSetType, int tickRate) {
    super(baseBlock, settings, blockSetType, tickRate);
    registerDefaultState(defaultBlockState().setValue(FACING, Direction.SOUTH));
  }

  public ExtShapeHorizontalFacingPressurePlateBlock(Block baseBlock, Properties settings, ActivationSettings activationSettings) {
    this(baseBlock, settings, activationSettings.blockSetType(), activationSettings.plateTime());
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    super.createBlockStateDefinition(builder);
    builder.add(FACING);
  }

  @Override
  public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
    final BlockState placementState = super.getStateForPlacement(ctx);
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
    final MultiVariant upModel = BlockModelGenerators.plainVariant(ModelTemplates.PRESSURE_PLATE_UP.create(this, modelProvider.getTextureMap(baseBlock, blockStateModelGenerator), blockStateModelGenerator.modelOutput));
    final MultiVariant downModel = BlockModelGenerators.plainVariant(ModelTemplates.PRESSURE_PLATE_DOWN.create(this, modelProvider.getTextureMap(baseBlock, blockStateModelGenerator), blockStateModelGenerator.modelOutput));
    blockStateModelGenerator.blockStateOutput.accept(((MultiVariantGenerator) BlockModelGenerators.createPressurePlate(this, upModel, downModel)).with(BlockStateModelGeneratorAccessor.getROTATION_HORIZONTAL_FACING_ALT()));
  }
}
