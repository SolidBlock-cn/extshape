package pers.solid.extshape.block;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.generator.ResourceGeneratorHelper;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JBlockStates;
import net.devtech.arrp.json.blockstate.JVariants;
import net.devtech.arrp.json.models.JModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
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
    return placementState != null ? placementState.with(FACING, ctx.getHorizontalPlayerFacing().getOpposite()) : null;
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
  public @NotNull JModel getBlockModel() {
    return super.getBlockModel().parent(new Identifier(ExtShape.MOD_ID, "block/glazed_terracotta_slab"));
  }

  @Environment(EnvType.CLIENT)
  @Override
  public void writeBlockModel(RuntimeResourcePack pack) {
    final JModel model = getBlockModel();
    final Identifier id = getBlockModelId();
    pack.addModel(model, id);
    pack.addModel(model.clone().parent(new Identifier(ExtShape.MOD_ID, "block/glazed_terracotta_slab_top")), id.brrp_append("_top"));
  }

  @Environment(EnvType.CLIENT)
  @Override
  public @NotNull JBlockStates getBlockStates() {
    final Identifier id = getBlockModelId();
    final JVariants JVariants = new JVariants();
    Identifier baseId = baseBlock == null ? null : ResourceGeneratorHelper.getBlockModelId(baseBlock);
    if (baseId != null && baseId.getPath().contains("waxed_") && baseId.getPath().contains("copper")) {
      baseId = new Identifier(baseId.getNamespace(), baseId.getPath().replace("waxed_", ""));
    }
    for (Direction direction : Direction.Type.HORIZONTAL) {
      JVariants.addVariant("type=bottom,facing", direction, new JBlockModel(id).y(((int) direction.asRotation())));
      JVariants.addVariant("type=top,facing", direction, new JBlockModel(id.brrp_append("_top")).y(((int) direction.asRotation())));
      JVariants.addVariant("type=double,facing", direction, new JBlockModel(baseBlock != null ? baseId : id.brrp_append("_double")).y(((int) direction.asRotation())));
    }
    return JBlockStates.ofVariants(JVariants);
  }
}
