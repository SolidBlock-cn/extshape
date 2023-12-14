package pers.solid.extshape.mixin;

import net.minecraft.block.Block;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(BlockFamilies.class)
public interface BlockFamiliesAccessor {
  @Accessor("BASE_BLOCKS_TO_FAMILIES")
  static Map<Block, BlockFamily> getBaseBlocksToFamilies() {
    throw new AssertionError("Cannot access: mixin is not loaded");
  }
}
