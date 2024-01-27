package pers.solid.extshape.block;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.data.client.BlockStateSupplier;
import net.minecraft.data.client.BlockStateVariant;
import net.minecraft.data.client.BlockStateVariantMap;
import net.minecraft.data.client.VariantsBlockStateSupplier;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;
import pers.solid.brrp.v1.BRRPUtils;
import pers.solid.brrp.v1.api.RuntimeResourcePack;
import pers.solid.brrp.v1.model.ModelJsonBuilder;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.util.BlockCollections;

import java.util.Arrays;
import java.util.Set;

import static net.minecraft.data.client.VariantSettings.*;

/**
 * 类似于普通的台阶，但是像 {@link PillarBlock} 那样拥有摆放的方向。
 */
public class ExtShapePillarSlabBlock extends ExtShapeSlabBlock {
  public static final Set<Block> BASE_BLOCKS_WITH_HORIZONTAL_COLUMN = Sets.newHashSet(Iterables.concat(
      BlockCollections.LOGS,
      BlockCollections.STRIPPED_LOGS,
      Arrays.asList(Blocks.HAY_BLOCK, Blocks.PURPUR_PILLAR, Blocks.QUARTZ_PILLAR)
  ));
  public static final EnumProperty<Direction.Axis> AXIS = PillarBlock.AXIS;

  public ExtShapePillarSlabBlock(@NotNull Block baseBlock, Settings settings) {
    super(baseBlock, settings);
    setDefaultState(getDefaultState().with(AXIS, Direction.Axis.Y));
  }

  @Environment(EnvType.CLIENT)
  @Override
  public @UnknownNullability BlockStateSupplier getBlockStates() {
    final BlockStateVariantMap.DoubleProperty<SlabType, Direction.Axis> variants = BlockStateVariantMap.create(TYPE, AXIS);
    final Identifier modelId = getBlockModelId();
    final Identifier topModelId = modelId.brrp_suffixed("_top");
    final Identifier baseModelId = baseBlock == null ? modelId.brrp_suffixed("_double") : BRRPUtils.getBlockModelId(baseBlock);
    // axis = y
    variants.register(SlabType.DOUBLE, Direction.Axis.Y, BlockStateVariant.create().put(MODEL, baseModelId));
    variants.register(SlabType.TOP, Direction.Axis.Y, BlockStateVariant.create().put(MODEL, topModelId));
    variants.register(SlabType.BOTTOM, Direction.Axis.Y, BlockStateVariant.create().put(MODEL, modelId));
    // axis = x
    final boolean isLog = BlockCollections.LOGS.contains(baseBlock) || BlockCollections.STRIPPED_LOGS.contains(baseBlock);
    final Identifier horizontalBaseModelId = isLog ? baseModelId.brrp_suffixed("_horizontal") : baseModelId;
    final Identifier horizontalModelId = modelId.brrp_suffixed("_horizontal");
    variants.register(SlabType.DOUBLE, Direction.Axis.X, BlockStateVariant.create().put(MODEL, horizontalBaseModelId).put(X, Rotation.R90).put(Y, Rotation.R90));
    variants.register(SlabType.BOTTOM, Direction.Axis.X, BlockStateVariant.create().put(MODEL, horizontalModelId).put(X, Rotation.R90).put(Y, Rotation.R90));
    variants.register(SlabType.TOP, Direction.Axis.X, BlockStateVariant.create().put(MODEL, horizontalModelId.brrp_suffixed("_top")).put(X, Rotation.R90).put(Y, Rotation.R90));
    // axis = z
    variants.register(SlabType.DOUBLE, Direction.Axis.Z, BlockStateVariant.create().put(MODEL, horizontalBaseModelId).put(X, Rotation.R90));
    variants.register(SlabType.BOTTOM, Direction.Axis.Z, BlockStateVariant.create().put(MODEL, horizontalModelId).put(X, Rotation.R90));
    variants.register(SlabType.TOP, Direction.Axis.Z, BlockStateVariant.create().put(MODEL, horizontalModelId.brrp_suffixed("_top")).put(X, Rotation.R90));

    return VariantsBlockStateSupplier.create(this).coordinate(variants);
  }

  @Environment(EnvType.CLIENT)
  @Override
  public void writeBlockModel(RuntimeResourcePack pack) {
    super.writeBlockModel(pack);
    final boolean hasHorizontalColumn = BASE_BLOCKS_WITH_HORIZONTAL_COLUMN.contains(baseBlock);
    final ModelJsonBuilder blockModel = getBlockModel();
    if (hasHorizontalColumn) {
      pack.addModel(getBlockModelId().brrp_suffixed("_horizontal"), blockModel.withParent(new Identifier(ExtShape.MOD_ID, "block/slab_column_horizontal")));
      pack.addModel(getBlockModelId().brrp_suffixed("_horizontal_top"), blockModel.withParent(new Identifier(ExtShape.MOD_ID, "block/slab_column_horizontal_top")));
    } else {
      pack.addModel(getBlockModelId().brrp_suffixed("_horizontal"), blockModel.withParent(new Identifier(ExtShape.MOD_ID, "block/slab_column")));
      pack.addModel(getBlockModelId().brrp_suffixed("_horizontal_top"), blockModel.withParent(new Identifier(ExtShape.MOD_ID, "block/slab_column_top")));
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

  @SuppressWarnings("deprecation")
  @Override
  public BlockState rotate(BlockState state, BlockRotation rotation) {
    return PillarBlock.changeRotation(super.rotate(state, rotation), rotation);
  }
}
