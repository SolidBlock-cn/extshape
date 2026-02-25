package pers.solid.extshape.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.HolderSet;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import pers.solid.extshape.block.ExtShapeFenceGateBlock;
import pers.solid.extshape.block.ExtShapeWallBlock;

import java.util.Optional;

@Mixin(Tool.class)
public abstract class ToolComponentMixin {
  @ModifyExpressionValue(method = {"getMiningSpeed", "isCorrectForDrops"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/core/HolderSet;)Z"))
  private boolean modifyIsIn(boolean original, @Local(name = "rule") Tool.Rule rule, @Local(argsOnly = true) BlockState instance) {
    final HolderSet<Block> registryEntryList = rule.blocks();
    final Optional<TagKey<Block>> tagKey = registryEntryList.unwrapKey();
    if (instance.getBlock() instanceof ExtShapeWallBlock wall && tagKey.isPresent() && tagKey.get().equals(BlockTags.MINEABLE_WITH_PICKAXE)) {
      return original && wall.baseBlock.defaultBlockState().is(registryEntryList);
    } else if (instance.getBlock() instanceof ExtShapeFenceGateBlock fenceGate && tagKey.isPresent() && tagKey.get().equals(BlockTags.MINEABLE_WITH_AXE)) {
      return original && fenceGate.baseBlock.defaultBlockState().is(registryEntryList);
    }
    return original;
  }
}
