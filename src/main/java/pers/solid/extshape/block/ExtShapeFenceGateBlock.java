package pers.solid.extshape.block;

import net.minecraft.block.*;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeProvider;
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
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.data.ExtShapeModelProvider;
import pers.solid.extshape.util.FenceSettings;

/**
 * 本模组中的栅栏门方块。
 */
public class ExtShapeFenceGateBlock extends FenceGateBlock implements ExtShapeVariantBlockInterface {

  public final @NotNull Block baseBlock;
  /**
   * 合成栅栏门方块所需要的第二合成材料，通常和对应栅栏的一致。
   */
  private final Item secondIngredient;

  public ExtShapeFenceGateBlock(@NotNull Block baseBlock, Settings settings, @NotNull WoodType woodType, @Nullable Item secondIngredient) {
    super(settings, woodType);
    this.baseBlock = baseBlock;
    this.secondIngredient = secondIngredient;
  }

  public ExtShapeFenceGateBlock(@NotNull Block baseBlock, Settings settings, @NotNull FenceSettings fenceSettings) {
    this(baseBlock, settings, fenceSettings.woodType(), fenceSettings.secondIngredient());
  }

  @Override
  public @NotNull Block getBaseBlock() {
    return baseBlock;
  }

  @Override
  public MutableText getName() {
    return Text.translatable("block.extshape.?_fence_gate", this.getNamePrefix());
  }

  public @Nullable Item getSecondIngredient() {
    return secondIngredient;
  }

  @Override
  public @Nullable CraftingRecipeJsonBuilder getCraftingRecipe() {
    final ShapedRecipeJsonBuilder craftingRecipe = ShapedRecipeJsonBuilder.create(getRecipeCategory(), this, 3)
        .input('W', baseBlock)
        .input('#', secondIngredient)
        .pattern("#W#")
        .pattern("#W#")
        .criterion(RecipeProvider.hasItem(baseBlock), RecipeProvider.conditionsFromItem(baseBlock));
    return craftingRecipe != null ? craftingRecipe.group(getRecipeGroup()) : null;
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.FENCE_GATE;
  }

  @Override
  public void registerModel(ExtShapeModelProvider modelProvider, BlockStateModelGenerator blockStateModelGenerator) {
    modelProvider.getBlockTexturePool(baseBlock, blockStateModelGenerator).fenceGate(this);
  }

  public static class WithExtension extends ExtShapeFenceGateBlock {
    private final @NotNull BlockExtension extension;

    public WithExtension(@NotNull Block baseBlock, Settings settings, @NotNull FenceSettings fenceSettings, @NotNull BlockExtension extension) {
      super(baseBlock, settings, fenceSettings);
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

    @SuppressWarnings("deprecation")
    @Override
    public boolean emitsRedstonePower(BlockState state) {
      return super.emitsRedstonePower(state) || extension.emitsRedstonePower().emitsRedstonePower(state, super.emitsRedstonePower(state));
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
      return extension.weakRedstonePower().getWeakRedstonePower(state, world, pos, direction, super.getWeakRedstonePower(state, world, pos, direction));
    }
  }

  public static class WithOxidation extends ExtShapeFenceGateBlock implements Oxidizable {
    private final @NotNull OxidationLevel oxidationLevel;

    public WithOxidation(@NotNull Block baseBlock, Settings settings, @NotNull FenceSettings fenceSettings, @NotNull OxidationLevel oxidationLevel) {
      super(baseBlock, settings, fenceSettings);
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
