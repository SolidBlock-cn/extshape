package pers.solid.extshape.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Contract;

public record BlockExtension(StacksDroppedCallback stacksDroppedCallback, ProjectileHitCallback projectileHitCallback, SteppedOnCallback steppedOnCallback, EmitsRedstonePower emitsRedstonePower, WeakRedstonePower weakRedstonePower) implements Cloneable {
  public static final BlockExtension EMPTY = new BlockExtension(StacksDroppedCallback.EMPTY, ProjectileHitCallback.EMPTY, SteppedOnCallback.EMPTY, EmitsRedstonePower.EMPTY, WeakRedstonePower.EMPTY);
  public static final BlockExtension AMETHYST = BlockExtension.builder().setProjectileHitCallback((world, state, hit, projectile) -> {
    if (!world.isClient) {
      BlockPos blockPos = hit.getBlockPos();
      world.playSound(null, blockPos, SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, SoundCategory.BLOCKS, 1.0F, 0.5F + world.random.nextFloat() * 1.2F);
      world.playSound(null, blockPos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 1.0F, 0.5F + world.random.nextFloat() * 1.2F);
    }
  }).build();

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public BlockExtension clone() {
    try {
      return (BlockExtension) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }

  @FunctionalInterface
  public interface StacksDroppedCallback {
    StacksDroppedCallback EMPTY = (state, world, pos, stack, dropExperience) -> {
    };

    void onStackDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack, boolean dropExperience);
  }

  @FunctionalInterface
  public interface ProjectileHitCallback {
    ProjectileHitCallback EMPTY = (world, state, hit, projectile) -> {
    };

    void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile);
  }

  @FunctionalInterface
  public interface SteppedOnCallback {
    SteppedOnCallback EMPTY = (world, pos, state, entity) -> {};

    void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity);
  }

  @FunctionalInterface
  public interface EmitsRedstonePower {
    EmitsRedstonePower EMPTY = (state, original) -> original;

    boolean emitsRedstonePower(BlockState state, boolean original);
  }

  @FunctionalInterface
  public interface WeakRedstonePower {
    WeakRedstonePower EMPTY = (state, world, pos, direction, original) -> original;

    int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction, int original);
  }

  public static class Builder {
    private StacksDroppedCallback stacksDroppedCallback = StacksDroppedCallback.EMPTY;
    private ProjectileHitCallback projectileHitCallback = ProjectileHitCallback.EMPTY;
    private SteppedOnCallback steppedOnCallback = SteppedOnCallback.EMPTY;
    private EmitsRedstonePower emitsRedstonePower = EmitsRedstonePower.EMPTY;
    private WeakRedstonePower weakRedstonePower = WeakRedstonePower.EMPTY;

    public Builder setProjectileHitCallback(ProjectileHitCallback projectileHitCallback) {
      this.projectileHitCallback = projectileHitCallback;
      return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public Builder setStacksDroppedCallback(StacksDroppedCallback stacksDroppedCallback) {
      this.stacksDroppedCallback = stacksDroppedCallback;
      return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public Builder setSteppedOnCallback(SteppedOnCallback steppedOnCallback) {
      this.steppedOnCallback = steppedOnCallback;
      return this;
    }

    public Builder setEmitsRedstonePower(EmitsRedstonePower emitsRedstonePower) {
      this.emitsRedstonePower = emitsRedstonePower;
      return this;
    }

    public Builder setWeakRedstonePower(WeakRedstonePower weakRedstonePower) {
      this.weakRedstonePower = weakRedstonePower;
      return this;
    }

    public BlockExtension build() {
      return new BlockExtension(stacksDroppedCallback, projectileHitCallback, steppedOnCallback, emitsRedstonePower, weakRedstonePower);
    }
  }
}
