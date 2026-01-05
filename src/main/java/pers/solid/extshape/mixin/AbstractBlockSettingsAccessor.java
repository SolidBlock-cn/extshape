package pers.solid.extshape.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.ToIntFunction;

@Mixin(AbstractBlock.Settings.class)
public interface AbstractBlockSettingsAccessor {
  @Accessor
  ToIntFunction<BlockState> getLuminance();
}
