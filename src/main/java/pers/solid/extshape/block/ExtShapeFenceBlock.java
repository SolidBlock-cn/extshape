package pers.solid.extshape.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import pers.solid.brrp.v1.generator.BRRPFenceBlock;
import pers.solid.extshape.builder.BlockShape;

public class ExtShapeFenceBlock extends BRRPFenceBlock implements ExtShapeVariantBlockInterface {

  /**
   * 合成栅栏方块需要使用的第二个材料。
   */
  private final Item secondIngredient;

  public ExtShapeFenceBlock(Block baseBlock, Item secondIngredient, Settings settings) {
    super(baseBlock, settings);
    this.secondIngredient = secondIngredient;
  }

  @Override
  public MutableText getName() {
    return new TranslatableText("block.extshape.?_fence", this.getNamePrefix());
  }

  @Override
  public @Nullable CraftingRecipeJsonBuilder getCraftingRecipe() {
    return baseBlock == null ? null : ShapedRecipeJsonBuilder.create(this, 2)
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
    private final BlockExtension extension;

    public WithExtension(Block baseBlock, Item secondIngredient, Settings settings, BlockExtension extension) {
      super(baseBlock, secondIngredient, settings);
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
}
