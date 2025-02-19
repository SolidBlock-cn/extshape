package pers.solid.extshape.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.BlockStateVariantMap;
import net.minecraft.client.data.TexturedModel;
import net.minecraft.client.render.model.json.ModelVariantOperator;
import org.jetbrains.annotations.Contract;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(BlockStateModelGenerator.class)
public interface BlockStateModelGeneratorAccessor {
  @Contract
  @Accessor
  static BlockStateVariantMap<ModelVariantOperator> getSOUTH_DEFAULT_HORIZONTAL_ROTATION_OPERATIONS() {
    throw new AssertionError();
  }

  @Contract
  @Accessor
  static Map<Block, TexturedModel> getTEXTURED_MODELS() {
    throw new AssertionError();
  }
}
