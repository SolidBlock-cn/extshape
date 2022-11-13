package pers.solid.extshape.block;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.generator.ResourceGeneratorHelper;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JBlockStates;
import net.devtech.arrp.json.blockstate.JVariants;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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

import java.util.Arrays;
import java.util.Set;

/**
 * 类似于普通的台阶，但是像 {@link PillarBlock} 那样拥有摆放的方向。
 */
public class ExtShapePillarSlabBlock extends ExtShapeSlabBlock {
  public static final Set<Block> BASE_BLOCKS_WITH_HORIZONTAL_COLUMN = Sets.newHashSet(Iterables.concat(
      ExtShapeTags.LOGS,
      ExtShapeTags.STRIPPED_LOGS,
      Arrays.asList(Blocks.HAY_BLOCK, Blocks.PURPUR_PILLAR, Blocks.QUARTZ_PILLAR, Blocks.OCHRE_FROGLIGHT, Blocks.VERDANT_FROGLIGHT, Blocks.PEARLESCENT_FROGLIGHT)
  ));
  public static final EnumProperty<Direction.Axis> AXIS = PillarBlock.AXIS;

  public ExtShapePillarSlabBlock(@NotNull Block baseBlock, Settings settings) {
    super(baseBlock, settings);
    setDefaultState(getDefaultState().with(AXIS, Direction.Axis.Y));
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public @NotNull JBlockStates getBlockStates() {
    final JVariants variants = new JVariants();
    final Identifier modelId = getBlockModelId();
    final Identifier topModelId = modelId.brrp_append("_top");
    final Identifier baseModelId = baseBlock == null ? modelId.brrp_append("_double") : ResourceGeneratorHelper.getBlockModelId(baseBlock);
    // axis = y
    variants.addVariant("type=double,axis=y", new JBlockModel(baseModelId));
    variants.addVariant("type=top,axis=y", new JBlockModel(topModelId));
    variants.addVariant("type=bottom,axis=y", new JBlockModel(modelId));


    // axis = x
    final boolean isLog = ExtShapeTags.LOGS.contains(baseBlock) || ExtShapeTags.STRIPPED_LOGS.contains(baseBlock);
    final Identifier horizontalBaseModelId = isLog ? baseModelId.brrp_append("_horizontal") : baseModelId;
    final Identifier horizontalModelId = modelId.brrp_append("_horizontal");
    variants.addVariant("type=double,axis=x", new JBlockModel(horizontalBaseModelId).x(90).y(90));
    variants.addVariant("type=bottom,axis=x", new JBlockModel(horizontalModelId).x(90).y(90));
    variants.addVariant("type=top,axis=x", new JBlockModel(horizontalModelId.brrp_append("_top")).x(90).y(90));
    // axis = z
    variants.addVariant("type=double,axis=z", new JBlockModel(horizontalBaseModelId).x(90));
    variants.addVariant("type=bottom,axis=z", new JBlockModel(horizontalModelId).x(90));
    variants.addVariant("type=top,axis=z", new JBlockModel(horizontalModelId.brrp_append("_top")).x(90));

    return JBlockStates.ofVariants(variants);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void writeBlockModel(RuntimeResourcePack pack) {
    super.writeBlockModel(pack);
    final boolean hasHorizontalColumn = BASE_BLOCKS_WITH_HORIZONTAL_COLUMN.contains(baseBlock);
    final JModel blockModel = getBlockModel();
    if (hasHorizontalColumn) {
      pack.addModel(blockModel.clone().parent(new Identifier(ExtShape.MOD_ID, "block/slab_column_horizontal")), getBlockModelId().brrp_append("_horizontal"));
      pack.addModel(blockModel.clone().parent(new Identifier(ExtShape.MOD_ID, "block/slab_column_horizontal_top")), getBlockModelId().brrp_append("_horizontal_top"));
    } else {
      pack.addModel(blockModel.clone().parent(new Identifier(ExtShape.MOD_ID, "block/slab_column")), getBlockModelId().brrp_append("_horizontal"));
      pack.addModel(blockModel.clone().parent(new Identifier(ExtShape.MOD_ID, "block/slab_column_top")), getBlockModelId().brrp_append("_horizontal_top"));
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
