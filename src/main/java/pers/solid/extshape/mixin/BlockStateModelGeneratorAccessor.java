package pers.solid.extshape.mixin;

import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.TexturedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(BlockStateModelGenerator.class)
public interface BlockStateModelGeneratorAccessor {
  @Accessor
  Map<Block, TexturedModel> getTexturedModels();
}
