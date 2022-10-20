package pers.solid.extshape.block;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Contract;

public class BlockExtension implements Cloneable {
  public static final BlockExtension EMPTY = new BlockExtension(StacksDroppedCallback.EMPTY);

  private BlockExtension(StacksDroppedCallback stacksDroppedCallback) {
    this.stacksDroppedCallback = stacksDroppedCallback;
  }

  public static Builder builder() {
    return new Builder();
  }

  @FunctionalInterface
  public interface StacksDroppedCallback {
    StacksDroppedCallback EMPTY = (state, world, pos, stack, dropExperience) -> {
    };

    void onStackDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack, boolean dropExperience);
  }

  public final StacksDroppedCallback stacksDroppedCallback;

  @Override
  public BlockExtension clone() {
    try {
      return (BlockExtension) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }

  public static class Builder {
    private StacksDroppedCallback stacksDroppedCallback = StacksDroppedCallback.EMPTY;


    @Contract(value = "_ -> this", mutates = "this")
    public Builder setStacksDroppedCallback(StacksDroppedCallback stacksDroppedCallback) {
      this.stacksDroppedCallback = stacksDroppedCallback;
      return this;
    }

    public BlockExtension build() {
      return new BlockExtension(stacksDroppedCallback);
    }
  }
}
