package pers.solid.extshape.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.MapColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractBlock.AbstractBlockState.class)
public interface AbstractBlockStateAccessor {
  @Accessor
  MapColor getMapColor();
}
