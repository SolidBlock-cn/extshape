package pers.solid.extshape.block;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.enums.SlabType;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;
import pers.solid.brrp.v1.BRRPUtils;
import pers.solid.brrp.v1.api.RuntimeResourcePack;
import pers.solid.brrp.v1.model.ModelJsonBuilder;
import pers.solid.brrp.v1.model.ModelUtils;
import pers.solid.extshape.ExtShape;

public class ExtShapePillarUvLockedSlabBlock extends ExtShapePillarSlabBlock {
  public static final MapCodec<ExtShapePillarUvLockedSlabBlock> CODEC = BRRPUtils.createCodecWithBaseBlock(createSettingsCodec(), ExtShapePillarUvLockedSlabBlock::new);

  public ExtShapePillarUvLockedSlabBlock(@NotNull Block baseBlock, Settings settings) {
    super(baseBlock, settings);
  }

  @Environment(EnvType.CLIENT)
  @Override
  public @UnknownNullability BlockStateSupplier getBlockStates() {
    final BlockStateVariantMap.DoubleProperty<SlabType, Direction.Axis> variants = BlockStateVariantMap.create(TYPE, AXIS);
    final Identifier modelId = getBlockModelId();
    final Identifier topModelId = modelId.brrp_suffixed("_top");
    final Identifier baseModelId = baseBlock == null ? modelId.brrp_suffixed("_double") : BRRPUtils.getBlockModelId(baseBlock);

    for (Direction.Axis axis : Direction.Axis.values()) {
      variants.register(SlabType.BOTTOM, axis, BlockStateVariant.create().put(VariantSettings.MODEL, modelId.brrp_suffixed("_" + axis.asString())));
      variants.register(SlabType.TOP, axis, BlockStateVariant.create().put(VariantSettings.MODEL, topModelId.brrp_suffixed("_" + axis.asString())));
      variants.register(SlabType.DOUBLE, axis, BlockStateVariant.create().put(VariantSettings.MODEL, baseModelId.brrp_suffixed("_" + axis.asString())));
    }

    return VariantsBlockStateSupplier.create(this).coordinate(variants);
  }

  @Environment(EnvType.CLIENT)
  @Override
  public void writeBlockModel(RuntimeResourcePack pack) {
    final ModelJsonBuilder blockModel = getBlockModel();
    final Identifier blockModelId = getBlockModelId();
    pack.addModel(blockModelId, blockModel);
    final Identifier topModelId = blockModelId.brrp_suffixed("_top");

    final ModelJsonBuilder blockModelUvLocked = getBlockModel().clone().setTextures(ModelUtils.getTextureMap(this, TextureKey.SIDE, TextureKey.END));
    for (Direction.Axis axis : Direction.Axis.values()) {
      pack.addModel(blockModelId.brrp_suffixed("_" + axis.asString()), blockModelUvLocked.clone().parent(new Identifier(ExtShape.MOD_ID, "block/slab_column_uv_locked_" + axis.asString())));
      pack.addModel(topModelId.brrp_suffixed("_" + axis.asString()), blockModelUvLocked.clone().parent(new Identifier(ExtShape.MOD_ID, "block/slab_column_uv_locked_" + axis.asString() + "_top")));
    }
  }

  @Override
  public MapCodec<? extends ExtShapePillarUvLockedSlabBlock> getCodec() {
    return CODEC;
  }
}
