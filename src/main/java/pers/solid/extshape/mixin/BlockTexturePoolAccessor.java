package pers.solid.extshape.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.block.dispatch.Variant;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(BlockModelGenerators.BlockFamilyProvider.class)
public interface BlockTexturePoolAccessor {
  @Accessor
  void setFullBlock(Variant baseModelId);

  @Accessor
  TextureMapping getMapping();
}
