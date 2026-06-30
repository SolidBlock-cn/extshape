package pers.solid.extshape.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.data.ExtShapeModelProvider;
import pers.solid.extshape.util.FenceSettings;

/**
 * 本模组中的栅栏门方块。
 */
public class ExtShapeFenceGateBlock extends FenceGateBlock implements ExtShapeVariantBlockInterface {
  public final Block baseBlock;
  /**
   * 合成栅栏门方块所需要的第二合成材料，通常和对应栅栏的一致。
   */
  private final Item secondIngredient;

  public ExtShapeFenceGateBlock(Block baseBlock, Properties settings, WoodType woodType, Item secondIngredient) {
    super(woodType, settings);
    this.baseBlock = baseBlock;
    this.secondIngredient = secondIngredient;
  }

  public ExtShapeFenceGateBlock(Block baseBlock, Properties settings, FenceSettings fenceSettings) {
    this(baseBlock, settings, fenceSettings.woodType(), fenceSettings.secondIngredient());
  }

  @Override
  public Block getBaseBlock() {
    return baseBlock;
  }

  @Override
  public MutableComponent getName() {
    return Component.translatable("block.extshape.?_fence_gate", this.getNamePrefix());
  }

  public Item getSecondIngredient() {
    return secondIngredient;
  }

  @Override
  public @Nullable RecipeBuilder getCraftingRecipe(RecipeProvider recipeGenerator) {
    final ShapedRecipeBuilder craftingRecipe = recipeGenerator.shaped(getRecipeCategory(), this, 3)
        .define('W', baseBlock)
        .define('#', secondIngredient)
        .pattern("#W#")
        .pattern("#W#")
        .unlockedBy(RecipeProvider.getHasName(baseBlock), recipeGenerator.has(baseBlock));
    return craftingRecipe.group(getRecipeGroup());
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.FENCE_GATE;
  }

  @Environment(EnvType.CLIENT)
  @Override
  public void registerModel(ExtShapeModelProvider modelProvider, BlockModelGenerators blockStateModelGenerator) {
    modelProvider.getBlockTexturePool(baseBlock, blockStateModelGenerator).fenceGate(this);
  }

  public static class WithExtension extends ExtShapeFenceGateBlock {
    private final BlockExtension extension;

    public WithExtension(Block baseBlock, Properties settings, FenceSettings fenceSettings, BlockExtension extension) {
      super(baseBlock, settings, fenceSettings);
      this.extension = extension;
    }

    @Override
    public void spawnAfterBreak(BlockState state, ServerLevel world, BlockPos pos, ItemStack stack, boolean dropExperience) {
      super.spawnAfterBreak(state, world, pos, stack, dropExperience);
      extension.stacksDroppedCallback().onStackDropped(state, world, pos, stack, dropExperience);
    }

    @Override
    public void onProjectileHit(Level world, BlockState state, BlockHitResult hit, Projectile projectile) {
      super.onProjectileHit(world, state, hit, projectile);
      extension.projectileHitCallback().onProjectileHit(world, state, hit, projectile);
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
      super.stepOn(world, pos, state, entity);
      extension.steppedOnCallback().onSteppedOn(world, pos, state, entity);
    }

    @Override
    public boolean isSignalSource(BlockState state) {
      return super.isSignalSource(state) || extension.emitsRedstonePower().emitsRedstonePower(state, super.isSignalSource(state));
    }

    @Override
    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
      return extension.weakRedstonePower().getWeakRedstonePower(state, world, pos, direction, super.getSignal(state, world, pos, direction));
    }
  }

  public static class WithOxidation extends ExtShapeFenceGateBlock implements WeatheringCopper {
    private final WeatherState oxidationLevel;

    public WithOxidation(Block baseBlock, Properties settings, WoodType woodType, Item secondIngredient, WeatherState oxidationLevel) {
      super(baseBlock, settings, woodType, secondIngredient);
      this.oxidationLevel = oxidationLevel;
    }

    public WithOxidation(Block baseBlock, Properties settings, FenceSettings fenceSettings, WeatherState oxidationLevel) {
      super(baseBlock, settings, fenceSettings);
      this.oxidationLevel = oxidationLevel;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
      this.changeOverTime(state, world, pos, random);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
      return WeatheringCopper.getNext(state.getBlock()).isPresent();
    }

    @Override
    public WeatherState getAge() {
      return oxidationLevel;
    }

  }
}
