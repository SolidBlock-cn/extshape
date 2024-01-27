package pers.solid.extshape.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SnowyBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import pers.solid.extshape.tag.ExtShapeTags;

@Mixin(SnowyBlock.class)
public class SnowyBlockMixin extends Block {

  @SuppressWarnings("unused")
  private SnowyBlockMixin(Settings settings) {
    super(settings);
  }

  @ModifyExpressionValue(method = "getPlacementState", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/SnowyBlock;isSnow(Lnet/minecraft/block/BlockState;)Z"))
  private boolean getPlacementState_snow(boolean original, @Local(argsOnly = true) ItemPlacementContext ctx) {
    return original || isSnow2(ctx.getWorld().getBlockState(ctx.getBlockPos().up()), ctx.getWorld(), ctx.getBlockPos().up());
  }

  @ModifyExpressionValue(method = "getStateForNeighborUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/SnowyBlock;isSnow(Lnet/minecraft/block/BlockState;)Z"))
  private boolean getStateForNeighborUpdate_snow(boolean original, @Local(argsOnly = true, ordinal = 1) BlockState neighborState, @Local(argsOnly = true) WorldAccess world, @Local(argsOnly = true, ordinal = 1) BlockPos neighborPos) {
    return original || isSnow2(neighborState, world, neighborPos);
  }

  @Unique
  private static boolean isSnow2(BlockState state, WorldView world, BlockPos pos) {
    return state.isIn(ExtShapeTags.SNOW, state0 -> isFaceFullSquare(state0.getCollisionShape(world, pos), Direction.DOWN));
  }
}
