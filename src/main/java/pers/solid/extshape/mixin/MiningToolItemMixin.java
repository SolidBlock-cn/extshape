package pers.solid.extshape.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import pers.solid.extshape.block.ExtShapeFenceGateBlock;
import pers.solid.extshape.block.ExtShapeWallBlock;

@Mixin(MiningToolItem.class)
public abstract class MiningToolItemMixin {

  @Shadow
  @Final
  private TagKey<Block> effectiveBlocks;

  /**
   * 原版将所有的墙都加入了 {@code minecraft:mineable/pickaxe} 标签，将所有的栅栏门都加入了 {@code minecraft:mineable/axe} 标签，因此这里作出修改，本模组的墙和栅栏方块需要考虑其基础方块才能够高效采集。
   */
  @SuppressWarnings("ConstantConditions")
  @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"), method = "getMiningSpeedMultiplier")
  public boolean getMiningSpeedMultiplierInjected(boolean original, @Local(argsOnly = true) BlockState state) {
    if (((MiningToolItem) (Object) this) instanceof PickaxeItem) {
      if (state.getBlock() instanceof ExtShapeWallBlock wall) {
        return original && wall.baseBlock.getDefaultState().isIn(effectiveBlocks);
      }
    } else if (((MiningToolItem) (Object) this) instanceof AxeItem) {
      if (state.getBlock() instanceof ExtShapeFenceGateBlock fenceGate) {
        return original && fenceGate.baseBlock.getDefaultState().isIn(effectiveBlocks);
      }
    }
    return original;
  }
}
