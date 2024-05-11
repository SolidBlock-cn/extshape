package pers.solid.extshape.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.*;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
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
import pers.solid.brrp.v1.generator.BRRPFenceGateBlock;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.mixin.FenceGateAccessor;
import pers.solid.extshape.util.FenceSettings;

public class ExtShapeFenceGateBlock extends BRRPFenceGateBlock implements ExtShapeVariantBlockInterface {
  public static final MapCodec<ExtShapeFenceGateBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Registries.BLOCK.getCodec().fieldOf("base_block").forGetter(BRRPFenceGateBlock::getBaseBlock), createSettingsCodec(), WoodType.CODEC.fieldOf("wood_type").forGetter(block -> ((FenceGateAccessor) block).getType()), Registries.ITEM.getCodec().fieldOf("second_ingredient").forGetter(block -> block.secondIngredient)).apply(instance, ExtShapeFenceGateBlock::new));
  private final Item secondIngredient;

  public ExtShapeFenceGateBlock(Block baseBlock, Settings settings, @NotNull WoodType woodType, Item secondIngredient) {
    super(baseBlock, settings, woodType);
    this.secondIngredient = secondIngredient;
  }

  public ExtShapeFenceGateBlock(Block baseBlock, Settings settings, FenceSettings fenceSettings) {
    this(baseBlock, settings, fenceSettings.woodType(), fenceSettings.secondIngredient());
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

  @SuppressWarnings("unchecked")
  @Override
  public MapCodec<FenceGateBlock> getCodec() {
    return (MapCodec<FenceGateBlock>) (MapCodec<?>) CODEC;
  }

  public static class WithExtension extends ExtShapeFenceGateBlock {
    private final BlockExtension extension;

    public WithExtension(Block baseBlock, Settings settings, FenceSettings fenceSettings, BlockExtension extension) {
      super(baseBlock, settings, fenceSettings);
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

  public static class WithOxidation extends ExtShapeFenceGateBlock implements Oxidizable {
    private final OxidationLevel oxidationLevel;
    public static final MapCodec<WithOxidation> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(Registries.BLOCK.getCodec().fieldOf("base_block").forGetter(BRRPFenceGateBlock::getBaseBlock), createSettingsCodec(), WoodType.CODEC.fieldOf("wood_type").forGetter(block -> ((FenceGateAccessor) block).getType()), Registries.ITEM.getCodec().fieldOf("second_ingredient").forGetter(ExtShapeFenceGateBlock::getSecondIngredient), CopperManager.weatheringStateField()).apply(instance, WithOxidation::new));

    public WithOxidation(Block baseBlock, Settings settings, WoodType woodType, Item secondIngredient, OxidationLevel oxidationLevel) {
      super(baseBlock, settings, woodType, secondIngredient);
      this.oxidationLevel = oxidationLevel;
    }

    public WithOxidation(Block baseBlock, Settings settings, FenceSettings fenceSettings, OxidationLevel oxidationLevel) {
      super(baseBlock, settings, fenceSettings);
      this.oxidationLevel = oxidationLevel;
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
    public MapCodec<FenceGateBlock> getCodec() {
      return (MapCodec<FenceGateBlock>) (MapCodec<?>) CODEC;
    }
  }
}
