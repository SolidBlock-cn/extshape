package pers.solid.extshape.block;

import com.mojang.datafixers.util.Function4;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SingleItemRecipeJsonBuilder;
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
import pers.solid.extshape.util.ActivationSettings;

/**
 * 本模组中的压力板方块，方块的激活时长和激活类型可能是自定义的。
 */
public class ExtShapePressurePlateBlock extends PressurePlateBlock implements ExtShapeVariantBlockInterface {

  public final Block baseBlock;
  protected final int tickRate;

  public ExtShapePressurePlateBlock(@NotNull Block baseBlock, ActivationRule activationRule, Settings settings, BlockSetType blockSetType, int tickRate) {
    super(activationRule, settings, blockSetType);
    this.baseBlock = baseBlock;
    this.tickRate = tickRate;
  }

  public ExtShapePressurePlateBlock(@NotNull Block baseBlock, Settings settings, @NotNull ActivationSettings activationSettings) {
    this(baseBlock, activationSettings.activationRule(), settings, activationSettings.blockSetType(), activationSettings.plateTime());
  }

  @Override
  public @NotNull Block getBaseBlock() {
    return baseBlock;
  }

  @Override
  public MutableText getName() {
    return Text.translatable("block.extshape.?_pressure_plate", this.getNamePrefix());
  }

  @Override
  public @Nullable CraftingRecipeJsonBuilder getCraftingRecipe() {
    return ShapedRecipeJsonBuilder.create(getRecipeCategory(), this)
        .pattern("##")
        .input('#', baseBlock)
        .criterion(RecipeProvider.hasItem(baseBlock), RecipeProvider.conditionsFromItem(baseBlock))
        .group(getRecipeGroup());
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.PRESSURE_PLATE;
  }

  @Override
  public void registerModel(ExtShapeModelProvider modelProvider, BlockStateModelGenerator blockStateModelGenerator) {
    modelProvider.getBlockTexturePool(baseBlock, blockStateModelGenerator).pressurePlate(this);
  }

  @Override
  public int getTickRate() {
    return tickRate;
  }

  @Override
  public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
    super.onStateReplaced(state, world, pos, newState, moved);
    if (getRedstoneOutput(state) > 0 && newState.getBlock() instanceof ExtShapePressurePlateBlock && getRedstoneOutput(newState) > 0) {
      world.scheduleBlockTick(pos.toImmutable(), newState.getBlock(), getTickRate());
    }
  }

  public static class WithExtension extends ExtShapePressurePlateBlock {
    private final @NotNull BlockExtension extension;

    public WithExtension(@NotNull Block baseBlock, Settings settings, @NotNull ActivationSettings activationSettings, @NotNull BlockExtension extension) {
      super(baseBlock, settings, activationSettings);
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

    @Override
    public boolean emitsRedstonePower(BlockState state) {
      return super.emitsRedstonePower(state) || extension.emitsRedstonePower().emitsRedstonePower(state, super.emitsRedstonePower(state));
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
      return extension.weakRedstonePower().getWeakRedstonePower(state, world, pos, direction, super.getWeakRedstonePower(state, world, pos, direction));
    }
  }

  public static class WithOxidation extends ExtShapePressurePlateBlock implements Oxidizable {
    private final @NotNull OxidationLevel oxidationLevel;

    public WithOxidation(@NotNull Block baseBlock, Settings settings, @NotNull ActivationSettings activationSettings, @NotNull OxidationLevel oxidationLevel) {
      super(baseBlock, settings, activationSettings);
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
