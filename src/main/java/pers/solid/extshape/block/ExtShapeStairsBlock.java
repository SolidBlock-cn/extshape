package pers.solid.extshape.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SingleItemRecipeJsonBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import pers.solid.brrp.v1.generator.BRRPStairsBlock;
import pers.solid.extshape.builder.BlockShape;

import java.util.Random;

public class ExtShapeStairsBlock extends BRRPStairsBlock implements ExtShapeVariantBlockInterface {

  public ExtShapeStairsBlock(Block baseBlock, Settings settings) {
    super(baseBlock, settings);
  }

  @Override
  public MutableText getName() {
    return new TranslatableText("block.extshape.?_stairs", this.getNamePrefix());
  }

  @Override
  public @Nullable SingleItemRecipeJsonBuilder getStonecuttingRecipe() {
    return simpleStoneCuttingRecipe(1);
  }

  @Override
  public @Nullable CraftingRecipeJsonBuilder getCraftingRecipe() {
    if (NOT_TO_CRAFT_STAIRS_OR_SLABS.contains(baseBlock)) {
      return null;
    }
    final CraftingRecipeJsonBuilder craftingRecipe = super.getCraftingRecipe();
    return craftingRecipe == null ? null : craftingRecipe.group(getRecipeGroup());
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.STAIRS;
  }

  public static class WithExtension extends ExtShapeStairsBlock {
    private final BlockExtension extension;

    public WithExtension(Block baseBlock, Settings settings, BlockExtension extension) {
      super(baseBlock, settings);
      this.extension = extension;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
      super.onStacksDropped(state, world, pos, stack);
      extension.stacksDroppedCallback().onStackDropped(state, world, pos, stack);
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

  /**
   * @see net.minecraft.block.OxidizableStairsBlock
   */
  public static class WithOxidation extends ExtShapeStairsBlock implements Oxidizable {
    private final OxidationLevel oxidationLevel;

    public WithOxidation(Block baseBlock, Settings settings, OxidationLevel oxidationLevel) {
      super(baseBlock, settings);
      this.oxidationLevel = oxidationLevel;
    }

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
