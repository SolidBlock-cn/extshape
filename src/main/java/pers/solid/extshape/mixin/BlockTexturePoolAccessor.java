package pers.solid.extshape.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.TextureMap;
import net.minecraft.client.render.model.json.ModelVariant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(BlockStateModelGenerator.BlockTexturePool.class)
public interface BlockTexturePoolAccessor {
  @Accessor
  void setBaseModelId(ModelVariant baseModelId);

  @Accessor
  TextureMap getTextures();
}
