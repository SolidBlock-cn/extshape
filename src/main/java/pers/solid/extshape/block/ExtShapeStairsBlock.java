package pers.solid.extshape.block;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.data.ExtShapeModelProvider;

/**
 * 本模组中的楼梯方块。受原版限制，基础方块（{@link #baseBlock}）不能为 {@code null}。
 */
public class ExtShapeStairsBlock extends StairBlock implements ExtShapeVariantBlockInterface {
  public static final MapCodec<ExtShapeStairsBlock> CODEC = ExtShapeBlockInterface.createCodecWithBaseBlock(propertiesCodec(), ExtShapeStairsBlock::new);

  public final @NotNull Block baseBlock;

  public ExtShapeStairsBlock(@NotNull Block baseBlock, Properties settings) {
    super(baseBlock.defaultBlockState(), settings);
    this.baseBlock = baseBlock;
  }

  @Override
  public MutableComponent getName() {
    return Component.translatable("block.extshape.?_stairs", this.getNamePrefix());
  }

  @Override
  public @Nullable SingleItemRecipeBuilder getStonecuttingRecipe(RecipeProvider recipeGenerator) {
    return simpleStoneCuttingRecipe(1, recipeGenerator);
  }

  @Override
  public @Nullable RecipeBuilder getCraftingRecipe(RecipeProvider recipeGenerator) {
    // recipeCategory 一定量 building_blocks，所以没有问题
    return recipeGenerator.stairBuilder(this, Ingredient.of(baseBlock))
        .unlockedBy(RecipeProvider.getHasName(baseBlock), recipeGenerator.has(baseBlock))
        .group(getRecipeGroup());
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.STAIRS;
  }

  @Environment(EnvType.CLIENT)
  @Override
  public void registerModel(ExtShapeModelProvider modelProvider, BlockModelGenerators blockStateModelGenerator) {
    modelProvider.getBlockTexturePool(baseBlock, blockStateModelGenerator).stairs(this);
  }

  @Override
  public MapCodec<? extends ExtShapeStairsBlock> codec() {
    return CODEC;
  }

  @Override
  public @NotNull Block getBaseBlock() {
    return baseBlock;
  }

  public static class WithExtension extends ExtShapeStairsBlock {
    private final @NotNull BlockExtension extension;

    public WithExtension(Block baseBlock, Properties settings, @NotNull BlockExtension extension) {
      super(baseBlock, settings);
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

  /**
   * @see net.minecraft.world.level.block.WeatheringCopperStairBlock
   */
  public static class WithOxidation extends ExtShapeStairsBlock implements WeatheringCopper {
    private final @NotNull WeatherState oxidationLevel;
    public static final MapCodec<WithOxidation> CODEC = CopperManager.createCodec(propertiesCodec(), WithOxidation::new);

    public WithOxidation(@NotNull Block baseBlock, Properties settings, @NotNull WeatherState oxidationLevel) {
      super(baseBlock, settings);
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

    @Override
    public MapCodec<? extends WithOxidation> codec() {
      return CODEC;
    }
  }
}
