package pers.solid.extshape.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/**
 * <p>方块的一些扩展行为组成的记录。这个记录在未来版本可能会增加新内容，因此建议使用 {@code BlockExtension.builder().build()}。不含有任何行为的对象可见 {@link #EMPTY}。
 * <p>这里面的方法都调用于模组中的各方块类的带有 {@code WithExtension} 名称的变种。
 */
public record BlockExtension(StacksDroppedCallback stacksDroppedCallback, ProjectileHitCallback projectileHitCallback, SteppedOnCallback steppedOnCallback, EmitsRedstonePower emitsRedstonePower, WeakRedstonePower weakRedstonePower) implements Cloneable {
  /**
   * 不含任何行为的空白 {@code BlockExtension} 对象。
   */
  public static final BlockExtension EMPTY = new BlockExtension(StacksDroppedCallback.EMPTY, ProjectileHitCallback.EMPTY, SteppedOnCallback.EMPTY, EmitsRedstonePower.EMPTY, WeakRedstonePower.EMPTY);
  public static final BlockExtension AMETHYST = BlockExtension.builder().setProjectileHitCallback((world, state, hit, projectile) -> {
    if (!world.isClientSide()) {
      BlockPos blockPos = hit.getBlockPos();
      world.playSound(null, blockPos, SoundEvents.AMETHYST_BLOCK_HIT, SoundSource.BLOCKS, 1.0F, 0.5F + world.getRandom().nextFloat() * 1.2F);
      world.playSound(null, blockPos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1.0F, 0.5F + world.getRandom().nextFloat() * 1.2F);
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

  /**
   * 方块被破坏后掉落物品的行为，例如幽匿块被破坏后掉落经验。
   *
   * @see BlockBehaviour#spawnAfterBreak
   */
  @FunctionalInterface
  public interface StacksDroppedCallback {
    StacksDroppedCallback EMPTY = (state, world, pos, stack, dropExperience) -> {
    };

    /**
     * 方块被破坏后的行为。
     */
    void onStackDropped(BlockState state, ServerLevel world, BlockPos pos, ItemStack stack, boolean dropExperience);
  }

  /**
   * 方块被弹射物击中后的行为，例如 紫水晶被击中后发出清脆响声。
   *
   * @see BlockBehaviour#onProjectileHit
   */
  @FunctionalInterface
  public interface ProjectileHitCallback {
    ProjectileHitCallback EMPTY = (world, state, hit, projectile) -> {
    };

    /**
     * 方块被弹射物击中后的行为。
     */
    void onProjectileHit(Level world, BlockState state, BlockHitResult hit, Projectile projectile);
  }

  /**
   * 玩家踩在方块上时的行为，例如沥青给予玩家加速效果。
   *
   * @see Block#stepOn
   */
  @FunctionalInterface
  public interface SteppedOnCallback {
    SteppedOnCallback EMPTY = (world, pos, state, entity) -> {};

    /**
     * 玩家踩在方块上时的行为。
     */
    void onSteppedOn(Level world, BlockPos pos, BlockState state, Entity entity);
  }

  /**
   * 方块是否产生红石信号，例如红石块会始终产生红石信号。
   *
   * @see BlockBehaviour#isSignalSource
   */
  @FunctionalInterface
  public interface EmitsRedstonePower {
    EmitsRedstonePower EMPTY = (state, original) -> original;

    /**
     * 方块是否产生红石信号。
     *
     * @return 如果产生红石信号，返回 {@code true}，否则返回 {@code original}。
     */
    boolean emitsRedstonePower(BlockState state, boolean original);
  }

  /**
   * 方块是否产生弱红石信号（即为方块弱充电）。例如红石块会始终为方块提供 15 的充能等级。
   *
   * @see BlockBehaviour#getSignal
   */
  @FunctionalInterface
  public interface WeakRedstonePower {
    WeakRedstonePower EMPTY = (state, world, pos, direction, original) -> original;

    /**
     * 方块是否产生弱红石信号。
     *
     * @return 方块的弱红石信号等级。
     */
    int getWeakRedstonePower(BlockState state, BlockGetter world, BlockPos pos, Direction direction, int original);
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

    public Builder setStacksDroppedCallback(StacksDroppedCallback stacksDroppedCallback) {
      this.stacksDroppedCallback = stacksDroppedCallback;
      return this;
    }

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
