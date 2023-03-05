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
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.ExtShape;

public class GlazedTerracottaSlabBlock extends ExtShapeSlabBlock {
  public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

  public GlazedTerracottaSlabBlock(@NotNull Block baseBlock, Settings settings) {
    super(baseBlock, settings);
    // 注意这里不使用 stateManager.getDefaultState，因为 super 中设置了 WATERLOGGED = false。
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

  @Override
  @SuppressWarnings("deprecation")
  public PistonBehavior getPistonBehavior(BlockState state) {
    return PistonBehavior.PUSH_ONLY;
  }

  @Environment(EnvType.CLIENT)
  @Override
  public @NotNull JBlockStates getBlockStates() {
    final JVariants variant = new JVariants();
    final Identifier blockModelId = getBlockModelId();
    assert baseBlock != null; // 带釉陶瓦楼梯的基础方块肯定是非 null 的。
    final Identifier baseBlockModelId = ResourceGeneratorHelper.getBlockModelId(baseBlock);
    for (Direction direction : Direction.Type.HORIZONTAL) {
      final int rotation = (int) direction.asRotation();
      variant.addVariant("type=bottom,facing", direction, new JBlockModel(blockModelId).y(rotation));
      variant.addVariant("type=top,facing", direction, new JBlockModel(blockModelId.brrp_append("_top")).y(rotation));
      variant.addVariant("type=double,facing", direction, new JBlockModel(baseBlockModelId).y(rotation));
    }
    return JBlockStates.ofVariants(variant);
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
}
