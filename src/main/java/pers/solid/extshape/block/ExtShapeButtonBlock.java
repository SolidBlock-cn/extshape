package pers.solid.extshape.block;

import com.mojang.serialization.Codec;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.data.ExtShapeModelProvider;
import pers.solid.extshape.mixin.ButtonBlockAccessor;
import pers.solid.extshape.util.ActivationSettings;

/**
 * 本模组中的按钮方块。按钮的激活时长可能会是特制的。
 */
public class ExtShapeButtonBlock extends ButtonBlock implements ExtShapeVariantBlockInterface {
  public static final MapCodec<ExtShapeButtonBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("base_block").forGetter(ExtShapeBlockInterface::getBaseBlock), propertiesCodec(), BlockSetType.CODEC.fieldOf("block_set_type").forGetter(b -> ((ButtonBlockAccessor) b).getType()), Codec.INT.fieldOf("press_ticks").forGetter(b -> ((ButtonBlockAccessor) b).getTicksToStayPressed())).apply(instance, ExtShapeButtonBlock::new));

  public final @NotNull Block baseBlock;

  public ExtShapeButtonBlock(@NotNull Block baseBlock, Properties settings, BlockSetType blockSetType, int pressTicks) {
    super(blockSetType, pressTicks, settings);
    this.baseBlock = baseBlock;
  }

  public ExtShapeButtonBlock(@NotNull Block baseBlock, Properties blockSettings, @NotNull ActivationSettings activationSettings) {
    super(activationSettings.blockSetType(), activationSettings.buttonTime(), blockSettings);
    this.baseBlock = baseBlock;
  }

  @Override
  public @NotNull Block getBaseBlock() {
    return baseBlock;
  }

  @Override
  public MutableComponent getName() {
    return Component.translatable("block.extshape.?_button", this.getNamePrefix());
  }

  @Override
  public @Nullable RecipeBuilder getCraftingRecipe(RecipeProvider recipeGenerator) {
    return recipeGenerator.shapeless(getRecipeCategory(), this)
        .requires(baseBlock)
        .unlockedBy(RecipeProvider.getHasName(baseBlock), recipeGenerator.has(baseBlock))
        .group(getRecipeGroup());
  }

  @SuppressWarnings("unchecked")
  @Override
  public MapCodec<ButtonBlock> codec() {
    return (MapCodec<ButtonBlock>) (MapCodec<?>) CODEC;
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.BUTTON;
  }

  @Environment(EnvType.CLIENT)
  @Override
  public void registerModel(ExtShapeModelProvider modelProvider, BlockModelGenerators blockStateModelGenerator) {
    modelProvider.getBlockTexturePool(baseBlock, blockStateModelGenerator).button(this);
  }


  @Override
  protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel world, BlockPos pos, boolean moved) {
    super.affectNeighborsAfterRemoval(state, world, pos, moved);
    if (state.getValue(POWERED) && state.getBlock() instanceof ExtShapeButtonBlock && state.getValue(POWERED)) {
      world.scheduleTick(pos.immutable(), state.getBlock(), ((ButtonBlockAccessor) this).getTicksToStayPressed());
    }
  }

  public static class WithExtension extends ExtShapeButtonBlock {
    private final @NotNull BlockExtension extension;

    public WithExtension(@NotNull Block baseBlock, Properties settings, @NotNull ActivationSettings activationSettings, @NotNull BlockExtension extension) {
      super(baseBlock, settings, activationSettings);
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

  public static class WithOxidation extends ExtShapeButtonBlock implements WeatheringCopper {
    private final @NotNull WeatherState oxidationLevel;
    public static final MapCodec<WithOxidation> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("base_block").forGetter(ExtShapeBlockInterface::getBaseBlock), propertiesCodec(), BlockSetType.CODEC.fieldOf("block_set_type").forGetter(b -> ((ButtonBlockAccessor) b).getType()), Codec.INT.fieldOf("press_ticks").forGetter(b -> ((ButtonBlockAccessor) b).getTicksToStayPressed()), CopperManager.weatheringStateField()).apply(instance, WithOxidation::new));

    public WithOxidation(@NotNull Block baseBlock, Properties settings, BlockSetType blockSetType, int pressTicks, @NotNull WeatherState oxidationLevel) {
      super(baseBlock, settings, blockSetType, pressTicks);
      this.oxidationLevel = oxidationLevel;
    }

    public WithOxidation(@NotNull Block baseBlock, Properties settings, @NotNull ActivationSettings activationSettings, @NotNull WeatherState oxidationLevel) {
      this(baseBlock, settings, activationSettings.blockSetType(), activationSettings.buttonTime(), oxidationLevel);
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
    public MapCodec<ButtonBlock> codec() {
      return (MapCodec<ButtonBlock>) (MapCodec<?>) CODEC;
    }
  }
}
