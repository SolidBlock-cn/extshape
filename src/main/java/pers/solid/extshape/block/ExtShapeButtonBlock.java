package pers.solid.extshape.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.*;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
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
import pers.solid.extshape.mixin.ButtonBlockAccessor;
import pers.solid.extshape.util.ActivationSettings;

/**
 * 本模组中的按钮方块。按钮的激活时长可能会是特制的。
 */
public class ExtShapeButtonBlock extends ButtonBlock implements ExtShapeVariantBlockInterface {
  public static final MapCodec<ExtShapeButtonBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Registries.BLOCK.getCodec().fieldOf("base_block").forGetter(ExtShapeBlockInterface::getBaseBlock), createSettingsCodec(), BlockSetType.CODEC.fieldOf("block_set_type").forGetter(b -> ((ButtonBlockAccessor) b).getBlockSetType()), Codec.INT.fieldOf("press_ticks").forGetter(b -> ((ButtonBlockAccessor) b).getPressTicks())).apply(instance, ExtShapeButtonBlock::new));

  public final @NotNull Block baseBlock;

  public ExtShapeButtonBlock(@NotNull Block baseBlock, Settings settings, BlockSetType blockSetType, int pressTicks) {
    super(blockSetType, pressTicks, settings);
    this.baseBlock = baseBlock;
  }

  public ExtShapeButtonBlock(@NotNull Block baseBlock, Settings blockSettings, @NotNull ActivationSettings activationSettings) {
    super(activationSettings.blockSetType(), activationSettings.buttonTime(), blockSettings);
    this.baseBlock = baseBlock;
  }

  @Override
  public @NotNull Block getBaseBlock() {
    return baseBlock;
  }

  @Override
  public MutableText getName() {
    return Text.translatable("block.extshape.?_button", this.getNamePrefix());
  }

  @Override
  public @Nullable CraftingRecipeJsonBuilder getCraftingRecipe() {
    return ShapelessRecipeJsonBuilder.create(getRecipeCategory(), this)
        .input(baseBlock)
        .criterion(RecipeProvider.hasItem(baseBlock), RecipeProvider.conditionsFromItem(baseBlock))
        .group(getRecipeGroup());
  }

  @SuppressWarnings("unchecked")
  @Override
  public MapCodec<ButtonBlock> getCodec() {
    return (MapCodec<ButtonBlock>) (MapCodec<?>) CODEC;
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.BUTTON;
  }

  @Override
  public void registerModel(ExtShapeModelProvider modelProvider, BlockStateModelGenerator blockStateModelGenerator) {
    modelProvider.getBlockTexturePool(baseBlock, blockStateModelGenerator).button(this);
  }

  @Override
  public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
    super.onStateReplaced(state, world, pos, newState, moved);
    if (state.get(POWERED) && newState.getBlock() instanceof ExtShapeButtonBlock && newState.get(POWERED)) {
      world.scheduleBlockTick(pos.toImmutable(), newState.getBlock(), ((ButtonBlockAccessor) this).getPressTicks());
    }
  }

  public static class WithExtension extends ExtShapeButtonBlock {
    private final @NotNull BlockExtension extension;

    public WithExtension(@NotNull Block baseBlock, Settings settings, @NotNull ActivationSettings activationSettings, @NotNull BlockExtension extension) {
      super(baseBlock, settings, activationSettings);
      this.extension = extension;
    }

    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack, boolean dropExperience) {
      super.onStacksDropped(state, world, pos, stack, dropExperience);
      extension.stacksDroppedCallback().onStackDropped(state, world, pos, stack, dropExperience);
    }

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

    @Override
    public boolean emitsRedstonePower(BlockState state) {
      return super.emitsRedstonePower(state) || extension.emitsRedstonePower().emitsRedstonePower(state, super.emitsRedstonePower(state));
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
      return extension.weakRedstonePower().getWeakRedstonePower(state, world, pos, direction, super.getWeakRedstonePower(state, world, pos, direction));
    }
  }

  public static class WithOxidation extends ExtShapeButtonBlock implements Oxidizable {
    private final @NotNull OxidationLevel oxidationLevel;
    public static final MapCodec<WithOxidation> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Registries.BLOCK.getCodec().fieldOf("base_block").forGetter(ExtShapeBlockInterface::getBaseBlock), createSettingsCodec(), BlockSetType.CODEC.fieldOf("block_set_type").forGetter(b -> ((ButtonBlockAccessor) b).getBlockSetType()), Codec.INT.fieldOf("press_ticks").forGetter(b -> ((ButtonBlockAccessor) b).getPressTicks()), CopperManager.weatheringStateField()).apply(instance, WithOxidation::new));

    public WithOxidation(@NotNull Block baseBlock, Settings settings, BlockSetType blockSetType, int pressTicks, @NotNull OxidationLevel oxidationLevel) {
      super(baseBlock, settings, blockSetType, pressTicks);
      this.oxidationLevel = oxidationLevel;
    }

    public WithOxidation(@NotNull Block baseBlock, Settings settings, @NotNull ActivationSettings activationSettings, @NotNull OxidationLevel oxidationLevel) {
      this(baseBlock, settings, activationSettings.blockSetType(), activationSettings.buttonTime(), oxidationLevel);
    }

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

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec<ButtonBlock> getCodec() {
      return (MapCodec<ButtonBlock>) (MapCodec<?>) CODEC;
    }
  }
}
