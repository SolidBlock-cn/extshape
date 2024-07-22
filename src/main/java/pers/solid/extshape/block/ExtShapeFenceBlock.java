package pers.solid.extshape.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
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
import pers.solid.brrp.v1.generator.BRRPFenceBlock;
import pers.solid.extshape.builder.BlockShape;

/**
 * 本模组中的栅栏方块。
 */
public class ExtShapeFenceBlock extends BRRPFenceBlock implements ExtShapeVariantBlockInterface {

  /**
   * 合成栅栏方块需要使用的第二个材料。
   */
  private final @Nullable Item secondIngredient;

  public ExtShapeFenceBlock(@NotNull Block baseBlock, @Nullable Item secondIngredient, Settings settings) {
    super(baseBlock, settings);
    this.secondIngredient = secondIngredient;
  }

  @Override
  public @NotNull Block getBaseBlock() {
    assert baseBlock != null;
    return baseBlock;
  }

  @Override
  public MutableText getName() {
    return Text.translatable("block.extshape.?_fence", this.getNamePrefix());
  }

  @Override
  public @Nullable CraftingRecipeJsonBuilder getCraftingRecipe() {
    return (baseBlock == null || secondIngredient == null) ? null : ShapedRecipeJsonBuilder.create(this, 2)
        .pattern("W#W").pattern("W#W")
        .group(getRecipeGroup())
        .input('W', baseBlock)
        .input('#', getSecondIngredient())
        .criterion(RecipeProvider.hasItem(baseBlock), RecipeProvider.conditionsFromItem(baseBlock));
  }

  @Nullable
  @Override
  public Item getSecondIngredient() {
    return secondIngredient;
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.FENCE;
  }


  public static class WithExtension extends ExtShapeFenceBlock {
    private final @NotNull BlockExtension extension;

    public WithExtension(@NotNull Block baseBlock, @Nullable Item secondIngredient, Settings settings, @NotNull BlockExtension extension) {
      super(baseBlock, secondIngredient, settings);
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

  public static class WithOxidation extends ExtShapeFenceBlock implements Oxidizable {
    private final @NotNull OxidationLevel oxidationLevel;

    public WithOxidation(@NotNull Block baseBlock, @Nullable Item secondIngredient, Settings settings, @NotNull OxidationLevel oxidationLevel) {
      super(baseBlock, secondIngredient, settings);
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
