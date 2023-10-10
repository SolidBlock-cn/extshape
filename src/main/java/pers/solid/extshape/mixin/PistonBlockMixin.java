package pers.solid.extshape.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.block.ExtShapeVariantBlockInterface;

@Mixin(PistonBlock.class)
public abstract class PistonBlockMixin {
  /**
   * 此方法会使黑曜石和哭泣的黑曜石的相关方块无法被移动。
   */
  @Inject(method = "isMovable", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 0), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;OBSIDIAN:Lnet/minecraft/block/Block;")), cancellable = true)
  private static void injectedIsMovable(BlockState state, World world, BlockPos pos, Direction direction, boolean canBreak, Direction pistonDir, CallbackInfoReturnable<Boolean> cir) {
    if (state.getBlock() instanceof ExtShapeVariantBlockInterface variantBlockInterface && ExtShapeBlocks.getBlocks().contains(state.getBlock())) {
      final Block baseBlock = variantBlockInterface.getBaseBlock();
      if (baseBlock == Blocks.OBSIDIAN || baseBlock == Blocks.CRYING_OBSIDIAN) {
        cir.setReturnValue(false);
      }
    }
  }
}
