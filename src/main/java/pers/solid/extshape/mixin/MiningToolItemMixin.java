package pers.solid.extshape.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.tag.BlockTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import pers.solid.extshape.tag.ExtShapeTags;

@Mixin(MiningToolItem.class)
public abstract class MiningToolItemMixin {

  /**
   * 如果方块属于 {@link ExtShapeTags#PICKAXE_UNMINEABLE} 这个标签中，那么即使属于 {@link BlockTags#PICKAXE_MINEABLE}，也不能认为可以开采。这是考虑到 Minecraft 将整个 {@link BlockTags#WALLS}（{@code minecraft:walls}）都加入了镐的可开采行列，但本模组添加的方块并非全都可以用镐开采，因此特地做出修改。
   */
  @SuppressWarnings("ConstantConditions")
  @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isIn(Lnet/minecraft/tag/TagKey;)Z"), method = "getMiningSpeedMultiplier")
  public boolean getMiningSpeedMultiplierInjected(boolean original, @Local(argsOnly = true) BlockState state) {
    return original && !(((MiningToolItem) (Object) this) instanceof PickaxeItem && state.isIn(ExtShapeTags.PICKAXE_UNMINEABLE));
  }
}
