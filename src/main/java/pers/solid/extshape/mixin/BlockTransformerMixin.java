package pers.solid.extshape.mixin;

import com.google.common.collect.Iterators;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.world.item.component.BlockTransformerMappings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;
import pers.solid.extshape.ExtShape;

import java.util.Iterator;

@Mixin(BlockTransformer.class)
public class BlockTransformerMixin {
  @ModifyExpressionValue(method = "transformBlock", at = @At(value = "INVOKE", target = "Ljava/util/List;iterator()Ljava/util/Iterator;"), slice = @Slice(to = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/context/UseOnContext;getClickedFace()Lnet/minecraft/core/Direction;")))
  private Iterator<BlockTransformer.BlockTransformData> getStrippedExtendedState(Iterator<BlockTransformer.BlockTransformData> original) {
    final BlockTransformer self = (BlockTransformer) (Object) this;
    if (self == BlockTransformerMappings.AXE) {
      return Iterators.concat(original, ExtShape.EXTENDED_STRIPPABLE_BLOCKS.iterator());
    } else {
      return original;
    }
  }
}
