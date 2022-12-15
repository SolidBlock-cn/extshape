package pers.solid.extshape.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SnowyBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pers.solid.extshape.ExtShape;

@Mixin(SnowyBlock.class)
public class SnowyBlockMixin extends Block {
  @Shadow
  @Final
  public static BooleanProperty SNOWY;
  private static final TagKey<Block> SNOW = TagKey.of(ForgeRegistries.Keys.BLOCKS, new Identifier(ExtShape.MOD_ID, "snow"));

  @SuppressWarnings("unused")
  private SnowyBlockMixin(Settings settings) {
    super(settings);
  }

  @Inject(method = "getPlacementState", at = @At(value = "RETURN"), cancellable = true)
  private void getPlacementState_snow(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
    final BlockState state = cir.getReturnValue();
    cir.setReturnValue(state.with(SNOWY, state.get(SNOWY) || isSnow2(ctx.getWorld().getBlockState(ctx.getBlockPos().up()), ctx.getWorld(), ctx.getBlockPos().up())));
  }

  @Inject(method = "getStateForNeighborUpdate", at = @At(value = "RETURN"), cancellable = true, slice = @Slice(
      from = @At(value = "FIELD", target = "Lnet/minecraft/util/math/Direction;UP:Lnet/minecraft/util/math/Direction;"),
      to = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getStateForNeighborUpdate(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;Lnet/minecraft/block/BlockState;Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;", shift = At.Shift.BEFORE)
  ))
  private void getStateForNeighborUpdate_snow(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
    final BlockState returnValue = cir.getReturnValue();
    cir.setReturnValue(returnValue.with(SNOWY, returnValue.get(SNOWY) || isSnow2(neighborState, world, neighborPos)));
  }

  private static boolean isSnow2(BlockState state, WorldView world, BlockPos pos) {
    return state.isIn(SNOW, state0 -> isFaceFullSquare(state0.getCollisionShape(world, pos), Direction.DOWN));
  }
}
