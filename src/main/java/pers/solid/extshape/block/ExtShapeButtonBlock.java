package pers.solid.extshape.block;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.data.ExtShapeModelProvider;
import pers.solid.extshape.mixin.ButtonBlockAccessor;
import pers.solid.extshape.util.ActivationSettings;

/**
 * 本模组中的按钮方块。按钮的激活时长可能会是特制的。
 */
public class ExtShapeButtonBlock extends ButtonBlock implements ExtShapeVariantBlockInterface {
  public final @NotNull Block baseBlock;

  public ExtShapeButtonBlock(@NotNull Block baseBlock, Settings blockSettings, @NotNull ActivationSettings activationSettings) {
    super(blockSettings, activationSettings.blockSetType(), activationSettings.buttonTime(), activationSettings.buttonActivatedByProjectile());
    this.baseBlock = baseBlock;
  }

  @Override
  public @NotNull Block getBaseBlock() {
    return baseBlock;
  }

  @Override
  public MutableText getName() {
    return Text.translatable("block.extshape.?_button", this.getNamePrefix());
  }

  @SuppressWarnings("deprecation")
  @Override
  public PistonBehavior getPistonBehavior(BlockState state) {
    return baseBlock.getHardness() == -1 || baseBlock == Blocks.OBSIDIAN || baseBlock == Blocks.CRYING_OBSIDIAN || baseBlock.getDefaultState().getPistonBehavior() == PistonBehavior.BLOCK ? PistonBehavior.BLOCK : super.getPistonBehavior(state);
  }


  @Override
  public @Nullable CraftingRecipeJsonBuilder getCraftingRecipe() {
    return ShapelessRecipeJsonBuilder.create(getRecipeCategory(), this)
        .input(baseBlock)
        .criterion(RecipeProvider.hasItem(baseBlock), RecipeProvider.conditionsFromItem(baseBlock))
        .group(getRecipeGroup());
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.BUTTON;
  }

  @Override
  public void registerModel(ExtShapeModelProvider modelProvider, BlockStateModelGenerator blockStateModelGenerator) {
    modelProvider.getBlockTexturePool(baseBlock, blockStateModelGenerator).button(this);
  }

  @Override
  public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
    super.onStateReplaced(state, world, pos, newState, moved);
    if (state.get(POWERED) && newState.getBlock() instanceof ExtShapeButtonBlock && newState.get(POWERED)) {
      world.scheduleBlockTick(pos.toImmutable(), newState.getBlock(), ((ButtonBlockAccessor) this).getPressTicks());
    }
  }

  public static class WithExtension extends ExtShapeButtonBlock {
    private final @NotNull BlockExtension extension;

    public WithExtension(@NotNull Block baseBlock, Settings settings, @NotNull ActivationSettings activationSettings, @NotNull BlockExtension extension) {
      super(baseBlock, settings, activationSettings);
      this.extension = extension;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack, boolean dropExperience) {
      super.onStacksDropped(state, world, pos, stack, dropExperience);
      extension.stacksDroppedCallback().onStackDropped(state, world, pos, stack, dropExperience);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
      super.onProjectileHit(world, state, hit, projectile);
      extension.projectileHitCallback().onProjectileHit(world, state, hit, projectile);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
      super.onSteppedOn(world, pos, state, entity);
      extension.steppedOnCallback().onSteppedOn(world, pos, state, entity);
    }
  }

  public static class WithOxidation extends ExtShapeButtonBlock implements Oxidizable {
    private final @NotNull OxidationLevel oxidationLevel;

    public WithOxidation(@NotNull Block baseBlock, Settings settings, @NotNull ActivationSettings activationSettings, @NotNull OxidationLevel oxidationLevel) {
      super(baseBlock, settings, activationSettings);
      this.oxidationLevel = oxidationLevel;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      this.tickDegradation(state, world, pos, random);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
      return Oxidizable.getIncreasedOxidationBlock(state.getBlock()).isPresent();
    }

    @Override
    public OxidationLevel getDegradationLevel() {
      return oxidationLevel;
    }
  }
}
