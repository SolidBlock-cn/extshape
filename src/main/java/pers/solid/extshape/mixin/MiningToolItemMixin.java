package pers.solid.extshape.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.*;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.tag.ExtShapeTags;

@Mixin(MiningToolItem.class)
public class MiningToolItemMixin extends ToolItem {
  public MiningToolItemMixin(ToolMaterial material, Settings settings) {
    super(material, settings);
  }

  /**
   * @see ExtShapeTags#PICKAXE_UNMINEABLE
   */
  private static final TagKey<Block> PICKAXE_UNMINEABLE = TagKey.of(Registry.BLOCK_KEY, new Identifier(ExtShape.MOD_ID, "pickaxe_unmineable"));

  /**
   * 如果方块属于 {@link #PICKAXE_UNMINEABLE} 这个标签中，那么即使属于 {@link BlockTags#PICKAXE_MINEABLE}，也不能认为可以开采。这是考虑到 Minecraft 将整个 {@link BlockTags#WALLS}（{@code minecraft:walls}）都加入了镐的可开采行列，但本模组添加的方块并非全都可以用镐开采，因此特地做出修改。
   */
  @SuppressWarnings("ConstantConditions")
  @Inject(at = @At("HEAD"), method = "getMiningSpeedMultiplier", cancellable = true)
  public void getMiningSpeedMultiplierInjected(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
    if (((MiningToolItem) (Object) this) instanceof PickaxeItem && state.isIn(PICKAXE_UNMINEABLE)) {
      cir.setReturnValue(1f);
    }
  }
}
