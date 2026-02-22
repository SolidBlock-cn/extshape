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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.data.ExtShapeModelProvider;
import pers.solid.extshape.mixin.FenceGateAccessor;
import pers.solid.extshape.util.FenceSettings;

/**
 * 本模组中的栅栏门方块。
 */
public class ExtShapeFenceGateBlock extends FenceGateBlock implements ExtShapeVariantBlockInterface {
  public static final MapCodec<ExtShapeFenceGateBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("base_block").forGetter(ExtShapeBlockInterface::getBaseBlock), propertiesCodec(), WoodType.CODEC.fieldOf("wood_type").forGetter(block -> ((FenceGateAccessor) block).getType()), BuiltInRegistries.ITEM.byNameCodec().fieldOf("second_ingredient").forGetter(block -> block.secondIngredient)).apply(instance, ExtShapeFenceGateBlock::new));
  public final @NotNull Block baseBlock;
  /**
   * 合成栅栏门方块所需要的第二合成材料，通常和对应栅栏的一致。
   */
  private final Item secondIngredient;

  public ExtShapeFenceGateBlock(@NotNull Block baseBlock, Properties settings, @NotNull WoodType woodType, @Nullable Item secondIngredient) {
    super(woodType, settings);
    this.baseBlock = baseBlock;
    this.secondIngredient = secondIngredient;
  }

  public ExtShapeFenceGateBlock(@NotNull Block baseBlock, Properties settings, @NotNull FenceSettings fenceSettings) {
    this(baseBlock, settings, fenceSettings.woodType(), fenceSettings.secondIngredient());
  }

  @Override
  public @NotNull Block getBaseBlock() {
    return baseBlock;
  }

  @Override
  public MutableComponent getName() {
    return Component.translatable("block.extshape.?_fence_gate", this.getNamePrefix());
  }

  public @Nullable Item getSecondIngredient() {
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
    return craftingRecipe != null ? craftingRecipe.group(getRecipeGroup()) : null;
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

  @SuppressWarnings("unchecked")
  @Override
  public MapCodec<FenceGateBlock> codec() {
    return (MapCodec<FenceGateBlock>) (MapCodec<?>) CODEC;
  }

  public static class WithExtension extends ExtShapeFenceGateBlock {
    private final @NotNull BlockExtension extension;

    public WithExtension(@NotNull Block baseBlock, Properties settings, @NotNull FenceSettings fenceSettings, @NotNull BlockExtension extension) {
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
    private final @NotNull WeatherState oxidationLevel;
    public static final MapCodec<WithOxidation> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("base_block").forGetter(ExtShapeBlockInterface::getBaseBlock), propertiesCodec(), WoodType.CODEC.fieldOf("wood_type").forGetter(block -> ((FenceGateAccessor) block).getType()), BuiltInRegistries.ITEM.byNameCodec().fieldOf("second_ingredient").forGetter(ExtShapeFenceGateBlock::getSecondIngredient), CopperManager.weatheringStateField()).apply(instance, WithOxidation::new));

    public WithOxidation(@NotNull Block baseBlock, Properties settings, @NotNull WoodType woodType, @Nullable Item secondIngredient, @NotNull WeatherState oxidationLevel) {
      super(baseBlock, settings, woodType, secondIngredient);
      this.oxidationLevel = oxidationLevel;
    }

    public WithOxidation(@NotNull Block baseBlock, Properties settings, @NotNull FenceSettings fenceSettings, @NotNull WeatherState oxidationLevel) {
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

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec<FenceGateBlock> codec() {
      return (MapCodec<FenceGateBlock>) (MapCodec<?>) CODEC;
    }
  }
}
