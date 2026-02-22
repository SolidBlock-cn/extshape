package pers.solid.extshape.block;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.data.ExtShapeBlockStateModelGenerator;
import pers.solid.extshape.data.ExtShapeModelProvider;

/**
 * 本模组中的竖直台阶方块。
 */
public class ExtShapeVerticalSlabBlock extends VerticalSlabBlock implements ExtShapeVariantBlockInterface {
  public static final MapCodec<ExtShapeVerticalSlabBlock> CODEC = ExtShapeBlockInterface.createCodecWithBaseBlock(propertiesCodec(), ExtShapeVerticalSlabBlock::new);
  public final Block baseBlock;

  public ExtShapeVerticalSlabBlock(@NotNull Block baseBlock, Properties settings) {
    super(settings);
    this.baseBlock = baseBlock;
  }

  @Override
  public MutableComponent getName() {
    return Component.translatable("block.extshape.?_vertical_slab", this.getNamePrefix());
  }

  @Override
  public @Nullable SingleItemRecipeBuilder getStonecuttingRecipe(RecipeProvider recipeGenerato) {
    return simpleStoneCuttingRecipe(2, recipeGenerato);
  }

  @Override
  public @NotNull Block getBaseBlock() {
    return baseBlock;
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.VERTICAL_SLAB;
  }

  @Environment(EnvType.CLIENT)
  @Override
  public void registerModel(ExtShapeModelProvider modelProvider, BlockModelGenerators blockStateModelGenerator) {
    ExtShapeBlockStateModelGenerator.registerVerticalSlab(this, modelProvider.getTextureMap(baseBlock, blockStateModelGenerator), blockStateModelGenerator);
  }

  @Override
  protected MapCodec<? extends ExtShapeVerticalSlabBlock> codec() {
    return CODEC;
  }

  public static class WithExtension extends ExtShapeVerticalSlabBlock {
    private final @NotNull BlockExtension extension;

    public WithExtension(@NotNull Block baseBlock, Properties settings, @NotNull BlockExtension extension) {
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

  public static class WithOxidation extends ExtShapeVerticalSlabBlock implements WeatheringCopper {
    public static final MapCodec<WithOxidation> CODEC = CopperManager.createCodec(propertiesCodec(), WithOxidation::new);
    private final @NotNull WeatherState oxidationLevel;

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
