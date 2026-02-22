package pers.solid.extshape.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import pers.solid.extshape.tag.ExtShapeTags;

@Mixin(SnowyDirtBlock.class)
public class SnowyBlockMixin extends Block {

  @SuppressWarnings("unused")
  private SnowyBlockMixin(Properties settings) {
    super(settings);
  }

  @ModifyExpressionValue(method = "getStateForPlacement", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/SnowyDirtBlock;isSnowySetting(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
  private boolean getPlacementState_snow(boolean original, @Local(argsOnly = true) BlockPlaceContext ctx) {
    return original || isSnow2(ctx.getLevel().getBlockState(ctx.getClickedPos().above()), ctx.getLevel(), ctx.getClickedPos().above());
  }

  @ModifyExpressionValue(method = "updateShape", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/SnowyDirtBlock;isSnowySetting(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
  private boolean getStateForNeighborUpdate_snow(boolean original, @Local(argsOnly = true, ordinal = 1) BlockState neighborState, @Local(argsOnly = true) LevelReader world, @Local(argsOnly = true, ordinal = 1) BlockPos neighborPos) {
    return original || isSnow2(neighborState, world, neighborPos);
  }

  @Unique
  private static boolean isSnow2(BlockState state, LevelReader world, BlockPos pos) {
    return state.is(ExtShapeTags.SNOW, state0 -> isFaceFull(state0.getCollisionShape(world, pos), Direction.DOWN));
  }
}
