package pers.solid.extshape.block;

import com.google.gson.JsonPrimitive;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.SlabType;
import net.minecraft.data.client.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import pers.solid.brrp.v1.BRRPUtils;
import pers.solid.brrp.v1.api.RuntimeResourcePack;
import pers.solid.brrp.v1.model.ModelJsonBuilder;
import pers.solid.extshape.ExtShape;

/**
 * 带釉陶瓦台阶方块。
 */
public class GlazedTerracottaSlabBlock extends ExtShapeSlabBlock {
  public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

  public GlazedTerracottaSlabBlock(@NotNull Block baseBlock, Settings settings) {
    super(baseBlock, settings);
    setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
  }

  @Nullable
  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    BlockPos blockPos = ctx.getBlockPos();
    BlockState blockState = ctx.getWorld().getBlockState(blockPos);
    BlockState state = super.getPlacementState(ctx);
    if (!blockState.isOf(this) && state != null) {
      return state.with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    } else {
      return state;
    }
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    super.appendProperties(builder);
    builder.add(FACING);
  }

  @Environment(EnvType.CLIENT)
  @Override
  public @UnknownNullability BlockStateSupplier getBlockStates() {
    final VariantsBlockStateSupplier state = VariantsBlockStateSupplier.create(this);
    final VariantSetting<Integer> variantSetting = new VariantSetting<>("y", JsonPrimitive::new);
    assert baseBlock != null; // 带釉陶瓦楼梯的基础方块肯定是非 null 的。
    final Identifier baseBlockModelId = BRRPUtils.getBlockModelId(baseBlock);
    final Identifier blockModelId = getBlockModelId();
    final BlockStateVariantMap.DoubleProperty<SlabType, Direction> map = BlockStateVariantMap.create(TYPE, FACING);
    for (Direction direction : Direction.Type.HORIZONTAL) {
      final int rotation = (int) direction.asRotation();
      map.register(SlabType.BOTTOM, direction, BlockStateVariant.create().put(VariantSettings.MODEL, blockModelId).put(variantSetting, rotation));
      map.register(SlabType.TOP, direction, BlockStateVariant.create().put(VariantSettings.MODEL, blockModelId.brrp_suffixed("_top")).put(variantSetting, rotation));
      map.register(SlabType.DOUBLE, direction, BlockStateVariant.create().put(VariantSettings.MODEL, baseBlockModelId).put(variantSetting, rotation));
    }
    return state.coordinate(map);
  }

  @Environment(EnvType.CLIENT)
  @Override
  public @UnknownNullability ModelJsonBuilder getBlockModel() {
    return super.getBlockModel().parent(ExtShape.id("block/glazed_terracotta_slab"));
  }

  @Environment(EnvType.CLIENT)
  @Override
  public void writeBlockModel(RuntimeResourcePack pack) {
    final ModelJsonBuilder model = getBlockModel();
    final Identifier id = getBlockModelId();
    pack.addModel(id, model);
    pack.addModel(id.brrp_suffixed("_top"), model.withParent(ExtShape.id("block/glazed_terracotta_slab_top")));
  }
}
