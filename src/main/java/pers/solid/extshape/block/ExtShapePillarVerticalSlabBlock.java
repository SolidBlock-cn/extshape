package pers.solid.extshape.block;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JBlockStates;
import net.devtech.arrp.json.blockstate.JVariants;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.tag.ExtShapeTags;

public class ExtShapePillarVerticalSlabBlock extends ExtShapeVerticalSlabBlock {
  public static final EnumProperty<Direction.Axis> AXIS = PillarBlock.AXIS;

  public ExtShapePillarVerticalSlabBlock(@NotNull Block baseBlock, Settings settings) {
    super(baseBlock, settings);
    setDefaultState(getDefaultState().with(AXIS, Direction.Axis.Y));
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public @NotNull JBlockStates getBlockStates() {
    final JVariants variants = new JVariants();
    final Identifier modelId = getBlockModelId();
    final Identifier horizontalModelId = modelId.brrp_append("_horizontal");

    for (Direction facing : Direction.Type.HORIZONTAL) {
      final int rotation = (int) facing.asRotation();
      variants.addVariant("axis=y,facing", facing, new JBlockModel(modelId).y(rotation).uvlock());
      final boolean usesTopModel = facing == Direction.NORTH || facing == Direction.EAST;
      variants.addVariant("axis=" + facing.getAxis().asString() + ",facing", facing, new JBlockModel(usesTopModel ? horizontalModelId.brrp_append("_top") : horizontalModelId).x(90).y(usesTopModel ? rotation - 180 : rotation));
      variants.addVariant("axis=" + facing.rotateYClockwise().getAxis().asString() + ",facing", facing, new JBlockModel(usesTopModel ? horizontalModelId.brrp_append("_unordered_top") : horizontalModelId.brrp_append("_unordered")).x(90).y(usesTopModel ? rotation - 90 : rotation + 90));
    }

    return JBlockStates.ofVariants(variants);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void writeBlockModel(RuntimeResourcePack pack) {
    super.writeBlockModel(pack);
    final boolean isLog = ExtShapeTags.LOGS.contains(baseBlock) || ExtShapeTags.STRIPPED_LOGS.contains(baseBlock);
    final JModel blockModel = getBlockModel();
    if (isLog) {
      pack.addModel(blockModel.clone().parent(new Identifier(ExtShape.MOD_ID, "block/vertical_slab_column_horizontal")), getBlockModelId().brrp_append("_horizontal"));
      pack.addModel(blockModel.clone().parent(new Identifier(ExtShape.MOD_ID, "block/vertical_slab_column_horizontal_top")), getBlockModelId().brrp_append("_horizontal_top"));
      pack.addModel(blockModel.clone().parent(new Identifier(ExtShape.MOD_ID, "block/vertical_slab_column_horizontal_unordered")), getBlockModelId().brrp_append("_horizontal_unordered"));
      pack.addModel(blockModel.clone().parent(new Identifier(ExtShape.MOD_ID, "block/vertical_slab_column_horizontal_unordered_top")), getBlockModelId().brrp_append("_horizontal_unordered_top"));
    } else {
      pack.addModel(blockModel.clone().parent(new Identifier(ExtShape.MOD_ID, "block/vertical_slab_column")), getBlockModelId().brrp_append("_horizontal"));
      pack.addModel(blockModel.clone().parent(new Identifier(ExtShape.MOD_ID, "block/vertical_slab_column_top")), getBlockModelId().brrp_append("_horizontal_top"));
      pack.addModel(blockModel.clone().parent(new Identifier(ExtShape.MOD_ID, "block/vertical_slab_column_unordered")), getBlockModelId().brrp_append("_horizontal_unordered"));
      pack.addModel(blockModel.clone().parent(new Identifier(ExtShape.MOD_ID, "block/vertical_slab_column_unordered_top")), getBlockModelId().brrp_append("_horizontal_unordered_top"));
    }
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
}

