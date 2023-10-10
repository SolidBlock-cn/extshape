package pers.solid.extshape.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.Instrument;
import net.minecraft.registry.tag.BlockTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.block.ExtShapeVariantBlockInterface;

@Mixin(Instrument.class)
public abstract class InstrumentMixin {
  @SuppressWarnings("deprecation")
  @Inject(method = "fromBelowState", at = @At("HEAD"), cancellable = true)
  private static void injectedFromBelowState(BlockState state, CallbackInfoReturnable<Instrument> cir) {
    final Block block = state.getBlock();
    if (block instanceof ExtShapeVariantBlockInterface variantBlockInterface && ExtShapeBlocks.getBlocks().contains(block)) {
      final Block baseBlock = variantBlockInterface.getBaseBlock();
      if (baseBlock == null) {
      } else if (baseBlock == Blocks.CLAY) {
        cir.setReturnValue(Instrument.FLUTE);
      } else if (baseBlock == Blocks.GOLD_BLOCK) {
        cir.setReturnValue(Instrument.BELL);
      } else if (baseBlock.getRegistryEntry().isIn(BlockTags.WOOL)) {
        cir.setReturnValue(Instrument.GUITAR);
      } else if (baseBlock == Blocks.PACKED_ICE) {
        cir.setReturnValue(Instrument.CHIME);
      } else if (baseBlock == Blocks.BONE_BLOCK) {
        cir.setReturnValue(Instrument.XYLOPHONE);
      } else if (baseBlock == Blocks.IRON_BLOCK) {
        cir.setReturnValue(Instrument.IRON_XYLOPHONE);
      } else if (baseBlock == Blocks.SOUL_SAND) {
        cir.setReturnValue(Instrument.COW_BELL);
      } else if (baseBlock == Blocks.PUMPKIN) {
        cir.setReturnValue(Instrument.DIDGERIDOO);
      } else if (baseBlock == Blocks.EMERALD_BLOCK) {
        cir.setReturnValue(Instrument.BIT);
      } else if (baseBlock == Blocks.HAY_BLOCK) {
        cir.setReturnValue(Instrument.BANJO);
      } else if (baseBlock == Blocks.GLOWSTONE) {
        cir.setReturnValue(Instrument.PLING);
      }
    }
  }
}
