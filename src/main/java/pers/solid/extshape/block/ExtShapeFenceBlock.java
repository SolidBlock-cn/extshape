package pers.solid.extshape.block;

import net.devtech.arrp.generator.BRRPFenceBlock;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.recipe.JShapedRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.util.BlockCollections;

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
    return Text.translatable("block.extshape.?_fence", this.getNamePrefix());
  }

  @Override
  public @Nullable JRecipe getCraftingRecipe() {
    return new JShapedRecipe(this).resultCount(3)
        .recipeCategory(getRecipeCategory())
        .pattern("W#W", "W#W")
        .group(getRecipeGroup())
        .addKey("W", baseBlock)
        .addKey("#", getSecondIngredient())
        .addInventoryChangedCriterion("has_ingredient", baseBlock);
  }

  @Nullable
  @Override
  public Item getSecondIngredient() {
    return secondIngredient;
  }

  @Override
  public String getRecipeGroup() {
    if ((BlockCollections.WOOLS).contains(baseBlock)) return "wool_fence";
    if ((BlockCollections.CONCRETES).contains(baseBlock)) return "concrete_fence";
    if ((BlockCollections.STAINED_TERRACOTTA).contains(baseBlock)) return "stained_terracotta_fence";
    if ((BlockCollections.GLAZED_TERRACOTTA).contains(baseBlock)) return "glazed_terracotta_fence";
    return "";
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
}
