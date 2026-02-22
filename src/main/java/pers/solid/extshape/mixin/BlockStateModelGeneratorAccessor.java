package pers.solid.extshape.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Contract;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(BlockModelGenerators.class)
public interface BlockStateModelGeneratorAccessor {
  @Contract
  @Accessor
  static PropertyDispatch<VariantMutator> getROTATION_HORIZONTAL_FACING_ALT() {
    throw new AssertionError();
  }

  @Contract
  @Accessor
  static Map<Block, TexturedModel> getTEXTURED_MODELS() {
    throw new AssertionError();
  }
}
