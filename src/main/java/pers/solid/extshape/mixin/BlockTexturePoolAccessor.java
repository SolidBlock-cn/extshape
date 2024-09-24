package pers.solid.extshape.mixin;

import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.TextureMap;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockStateModelGenerator.BlockTexturePool.class)
public interface BlockTexturePoolAccessor {
  @Accessor
  void setBaseModelId(Identifier baseModelId);

  @Accessor
  TextureMap getTextures();
}
