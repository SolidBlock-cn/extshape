package pers.solid.extshape.mixin;

import net.minecraft.core.component.BlockTransformer;
import net.minecraft.world.item.component.BlockTransformerMappings;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockTransformerMappings.class)
public interface BlockTransformerMappingsAccessor {
  @Invoker
  static BlockTransformer.BlockTransformData callGetStrippableBlockData(final Block fromBlock, final Block toBlock) {
    throw new UnsupportedOperationException();
  }
}
