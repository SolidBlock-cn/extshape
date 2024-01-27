package pers.solid.extshape.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.brrp.v1.generator.BRRPFenceGateBlock;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.util.FenceSettings;

public class ExtShapeFenceGateBlock extends BRRPFenceGateBlock implements ExtShapeVariantBlockInterface {

  private final Item secondIngredient;

  public ExtShapeFenceGateBlock(Block baseBlock, Settings settings, SoundEvent closeSound, SoundEvent openSound, Item secondIngredient) {
    super(baseBlock, settings, closeSound, openSound);
    this.secondIngredient = secondIngredient;
  }

  public ExtShapeFenceGateBlock(Block baseBlock, Settings settings, FenceSettings fenceSettings) {
    this(baseBlock, settings, fenceSettings.closeSound(), fenceSettings.openSound(), fenceSettings.secondIngredient());
  }

  @Override
  public MutableText getName() {
    return Text.translatable("block.extshape.?_fence_gate", this.getNamePrefix());
  }

  @NotNull
  @Override
  public Item getSecondIngredient() {
    return secondIngredient;
  }

  @Override
  public @Nullable CraftingRecipeJsonBuilder getCraftingRecipe() {
    final CraftingRecipeJsonBuilder craftingRecipe = super.getCraftingRecipe();
    return craftingRecipe != null ? craftingRecipe.group(getRecipeGroup()) : null;
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.FENCE_GATE;
  }


  public static class WithExtension extends ExtShapeFenceGateBlock {
    private final BlockExtension extension;

    public WithExtension(Block baseBlock, Settings settings, FenceSettings fenceSettings, BlockExtension extension) {
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
  }

  public static class WithOxidation extends ExtShapeFenceGateBlock implements Oxidizable {
    private final OxidationLevel oxidationLevel;

    public WithOxidation(Block baseBlock, Settings settings, FenceSettings fenceSettings, OxidationLevel oxidationLevel) {
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
