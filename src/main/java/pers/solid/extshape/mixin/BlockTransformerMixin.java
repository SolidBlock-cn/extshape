package pers.solid.extshape.mixin;

import com.google.common.collect.Iterators;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.core.component.BlockTransformer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.BlockTransformers;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;
import pers.solid.extshape.ExtShapeBlockTransformers;

import java.util.Iterator;
import java.util.Optional;

@Mixin(BlockTransformer.class)
public class BlockTransformerMixin {
  @ModifyExpressionValue(method = "transformBlock", at = @At(value = "INVOKE", target = "Ljava/util/List;iterator()Ljava/util/Iterator;"), slice = @Slice(to = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/context/UseOnContext;getClickedFace()Lnet/minecraft/core/Direction;")))
  private Iterator<BlockTransformer.BlockTransformData> getStrippedExtendedState(Iterator<BlockTransformer.BlockTransformData> original, @Local(argsOnly = true) final UseOnContext context) {
    final Holder<BlockTransformer> blockTransformer = context.getItemInHand().get(DataComponents.BLOCK_TRANSFORMER);
    if (blockTransformer != null && blockTransformer.is(BlockTransformers.AXE)) {
      final Optional<Holder.Reference<BlockTransformer>> ref = context.getLevel().registryAccess().get(ExtShapeBlockTransformers.AXE);
      if (ref.isPresent()) {
        return Iterators.concat(original, ref.get().value().transforms().iterator());
      }
    }
    return original;
  }
}
