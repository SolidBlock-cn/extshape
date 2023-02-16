package pers.solid.extshape.block;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.generator.ResourceGeneratorHelper;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JBlockStates;
import net.devtech.arrp.json.blockstate.JVariants;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.minecraft.block.Block;
import net.minecraft.data.client.TextureKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.ExtShape;

public class ExtShapePillarUvLockedSlabBlock extends ExtShapePillarSlabBlock {
  public ExtShapePillarUvLockedSlabBlock(@NotNull Block baseBlock, Settings settings) {
    super(baseBlock, settings);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public @NotNull JBlockStates getBlockStates() {
    final JVariants variants = new JVariants();
    final Identifier modelId = getBlockModelId();
    final Identifier topModelId = modelId.brrp_append("_top");
    final Identifier baseModelId = baseBlock == null ? modelId.brrp_append("_double") : ResourceGeneratorHelper.getBlockModelId(baseBlock);

    for (Direction.Axis axis : Direction.Axis.values()) {
      variants.addVariant("type=bottom,axis", axis, new JBlockModel(modelId.brrp_append("_" + axis.asString())));
      variants.addVariant("type=top,axis", axis, new JBlockModel(topModelId.brrp_append("_" + axis.asString())));
      variants.addVariant("type=double,axis", axis, new JBlockModel(baseModelId.brrp_append("_" + axis.asString())));
    }

    return JBlockStates.ofVariants(variants);
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  public void writeBlockModel(RuntimeResourcePack pack) {
    final JModel blockModel = getBlockModel();
    final Identifier blockModelId = getBlockModelId();
    pack.addModel(blockModel, blockModelId);
    final Identifier topModelId = blockModelId.brrp_append("_top");

    final JModel blockModelUvLocked = getBlockModel().clone().textures(JTextures.of("side", getTextureId(TextureKey.SIDE)).var("end", getTextureId(TextureKey.END)));
    for (Direction.Axis axis : Direction.Axis.values()) {
      pack.addModel(blockModelUvLocked.clone().parent(new Identifier(ExtShape.MOD_ID, "block/slab_column_uv_locked_" + axis.asString())), blockModelId.brrp_append("_" + axis.asString()));
      pack.addModel(blockModelUvLocked.clone().parent(new Identifier(ExtShape.MOD_ID, "block/slab_column_uv_locked_" + axis.asString() + "_top")), topModelId.brrp_append("_" + axis.asString()));
    }
  }
}
