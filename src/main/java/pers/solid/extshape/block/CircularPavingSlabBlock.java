package pers.solid.extshape.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.data.client.model.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;
import pers.solid.brrp.v1.BRRPUtils;
import pers.solid.brrp.v1.api.RuntimeResourcePack;
import pers.solid.brrp.v1.model.ModelJsonBuilder;
import pers.solid.extshape.ExtShape;

public class CircularPavingSlabBlock extends ExtShapeSlabBlock {
  public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

  public CircularPavingSlabBlock(@NotNull Block baseBlock, Settings settings) {
    super(baseBlock, settings);
    setDefaultState(getDefaultState().with(FACING, Direction.SOUTH));
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    super.appendProperties(builder);
    builder.add(FACING);
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    final BlockState placementState = super.getPlacementState(ctx);
    if (placementState != null && placementState.get(TYPE) == SlabType.DOUBLE) {
      return placementState;
    }
    return placementState != null ? placementState.with(FACING, ctx.getPlayerFacing().getOpposite()) : null;
  }

  @SuppressWarnings("deprecation")
  @Override
  public BlockState rotate(BlockState state, BlockRotation rotation) {
    return state.with(FACING, rotation.rotate(state.get(FACING)));
  }

  @SuppressWarnings("deprecation")
  @Override
  public BlockState mirror(BlockState state, BlockMirror mirror) {
    return state.rotate(mirror.getRotation(state.get(FACING)));
  }

  @Environment(EnvType.CLIENT)
  @Override
  public @UnknownNullability ModelJsonBuilder getBlockModel() {
    return super.getBlockModel().withParent(new Identifier(ExtShape.MOD_ID, "block/glazed_terracotta_slab"));
  }

  @Environment(EnvType.CLIENT)
  @Override
  public void writeBlockModel(RuntimeResourcePack pack) {
    final ModelJsonBuilder model = getBlockModel();
    final Identifier id = getBlockModelId();
    pack.addModel(id, model);
    pack.addModel(id.brrp_suffixed("_top"), model.withParent(new Identifier(ExtShape.MOD_ID, "block/glazed_terracotta_slab_top")));
  }

  @Environment(EnvType.CLIENT)
  @Override
  public @UnknownNullability BlockStateSupplier getBlockStates() {
    final Identifier id = getBlockModelId();
    final BlockStateVariantMap.SingleProperty<SlabType> variants = BlockStateVariantMap.create(TYPE);
    Identifier baseId = baseBlock == null ? null : BRRPUtils.getBlockModelId(baseBlock);
    if (baseId != null && baseId.getPath().contains("waxed_") && baseId.getPath().contains("copper")) {
      baseId = new Identifier(baseId.getNamespace(), baseId.getPath().replace("waxed_", ""));
    }
    final BlockStateVariantMap variants2 = BlockStateModelGenerator.createSouthDefaultHorizontalRotationStates();
    variants.register(SlabType.BOTTOM, BlockStateVariant.create().put(VariantSettings.MODEL, id));
    variants.register(SlabType.TOP, BlockStateVariant.create().put(VariantSettings.MODEL, id.brrp_suffixed("_top")));
    variants.register(SlabType.DOUBLE, BlockStateVariant.create().put(VariantSettings.MODEL, baseBlock != null ? baseId : id.brrp_suffixed("_double")));
    return VariantsBlockStateSupplier.create(this).coordinate(variants).coordinate(variants2);
  }
}
