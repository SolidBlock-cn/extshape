package pers.solid.extshape.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import pers.solid.extshape.block.ExtShapeFenceGateBlock;
import pers.solid.extshape.block.ExtShapeWallBlock;

import java.util.Optional;

@Mixin(ToolComponent.class)
public abstract class ToolComponentMixin {
  @ModifyExpressionValue(method = {"getSpeed", "isCorrectForDrops"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isIn(Lnet/minecraft/registry/entry/RegistryEntryList;)Z"))
  private boolean modifyIsIn(boolean original, @Local ToolComponent.Rule rule, @Local(argsOnly = true) BlockState instance) {
    final RegistryEntryList<Block> registryEntryList = rule.blocks();
    final Optional<TagKey<Block>> tagKey = registryEntryList.getTagKey();
    if (instance.getBlock() instanceof ExtShapeWallBlock wall && tagKey.isPresent() && tagKey.get().equals(BlockTags.PICKAXE_MINEABLE)) {
      return original && wall.baseBlock.getDefaultState().isIn(registryEntryList);
    } else if (instance.getBlock() instanceof ExtShapeFenceGateBlock fenceGate && tagKey.isPresent() && tagKey.get().equals(BlockTags.AXE_MINEABLE)) {
      return original && fenceGate.baseBlock.getDefaultState().isIn(registryEntryList);
    }
    return original;
  }
}
