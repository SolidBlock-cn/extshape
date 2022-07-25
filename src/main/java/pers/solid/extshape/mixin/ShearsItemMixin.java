package pers.solid.extshape.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pers.solid.extshape.ExtShape;

@Mixin(ShearsItem.class)
public class ShearsItemMixin {
  private static final TagKey<Block> WOOLEN_BLOCKS = TagKey.of(Registry.BLOCK_KEY, new Identifier(ExtShape.MOD_ID, "woolen_blocks"));

  @Inject(method = "getMiningSpeedMultiplier", at = @At("HEAD"), cancellable = true)
  protected void injectedGetMiningSpeedMultiplier(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
    if (state.isIn(WOOLEN_BLOCKS)) cir.setReturnValue(5f);
  }
}
