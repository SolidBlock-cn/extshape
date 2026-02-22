package pers.solid.extshape.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeProvider;
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
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.data.ExtShapeModelProvider;

/**
 * 本模组中的栅栏方块。
 */
public class ExtShapeFenceBlock extends FenceBlock implements ExtShapeVariantBlockInterface {
  public static final MapCodec<ExtShapeFenceBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("base_block").forGetter(ExtShapeBlockInterface::getBaseBlock), BuiltInRegistries.ITEM.byNameCodec().fieldOf("second_ingredient").forGetter(block -> block.secondIngredient), propertiesCodec()).apply(instance, ExtShapeFenceBlock::new));

  public final @NotNull Block baseBlock;
  /**
   * 合成栅栏方块需要使用的第二个材料。
   */
  private final @Nullable Item secondIngredient;

  public ExtShapeFenceBlock(@NotNull Block baseBlock, @Nullable Item secondIngredient, Properties settings) {
    super(settings);
    this.baseBlock = baseBlock;
    this.secondIngredient = secondIngredient;
  }

  @Override
  public @NotNull Block getBaseBlock() {
    return baseBlock;
  }

  @Override
  public MutableComponent getName() {
    return Component.translatable("block.extshape.?_fence", this.getNamePrefix());
  }

  @Override
  public @Nullable RecipeBuilder getCraftingRecipe(RecipeProvider recipeGenerator) {
    return secondIngredient == null ? null : recipeGenerator.shaped(getRecipeCategory(), this, 2)
        .pattern("W#W").pattern("W#W")
        .group(getRecipeGroup())
        .define('W', baseBlock)
        .define('#', getSecondIngredient())
        .unlockedBy(RecipeProvider.getHasName(baseBlock), recipeGenerator.has(baseBlock));
  }

  @Nullable
  public Item getSecondIngredient() {
    return secondIngredient;
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.FENCE;
  }

  @Environment(EnvType.CLIENT)
  @Override
  public void registerModel(ExtShapeModelProvider modelProvider, BlockModelGenerators blockStateModelGenerator) {
    modelProvider.getBlockTexturePool(baseBlock, blockStateModelGenerator).fence(this);
  }

  @SuppressWarnings("unchecked")
  @Override
  public MapCodec<FenceBlock> codec() {
    return (MapCodec<FenceBlock>) (MapCodec<?>) CODEC;
  }

  public static class WithExtension extends ExtShapeFenceBlock {
    private final @NotNull BlockExtension extension;

    public WithExtension(@NotNull Block baseBlock, @Nullable Item secondIngredient, Properties settings, @NotNull BlockExtension extension) {
      super(baseBlock, secondIngredient, settings);
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

  public static class WithOxidation extends ExtShapeFenceBlock implements WeatheringCopper {
    private final @NotNull WeatherState oxidationLevel;
    public static final MapCodec<WithOxidation> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("base_block").forGetter(ExtShapeBlockInterface::getBaseBlock), BuiltInRegistries.ITEM.byNameCodec().fieldOf("second_ingredient").forGetter(ExtShapeFenceBlock::getSecondIngredient), propertiesCodec(), CopperManager.weatheringStateField()).apply(instance, WithOxidation::new));

    public WithOxidation(@NotNull Block baseBlock, @Nullable Item secondIngredient, Properties settings, @NotNull WeatherState oxidationLevel) {
      super(baseBlock, secondIngredient, settings);
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

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec<FenceBlock> codec() {
      return (MapCodec<FenceBlock>) (MapCodec<?>) CODEC;
    }
  }
}
