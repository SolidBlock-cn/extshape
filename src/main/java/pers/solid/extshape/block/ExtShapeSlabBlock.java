package pers.solid.extshape.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Oxidizable;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.BlockStateSupplier;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SingleItemRecipeJsonBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import pers.solid.brrp.v1.BRRPUtils;
import pers.solid.brrp.v1.generator.BRRPSlabBlock;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;

import java.util.Random;

/**
 * 本模组中的台阶方块。
 */
public class ExtShapeSlabBlock extends BRRPSlabBlock implements ExtShapeVariantBlockInterface {
  public ExtShapeSlabBlock(@NotNull Block baseBlock, Settings settings) {
    super(baseBlock, settings);
  }

  @Override
  public @NotNull Block getBaseBlock() {
    assert baseBlock != null;
    return baseBlock;
  }

  @Override
  public MutableText getName() {
    return new TranslatableText("block.extshape.?_slab", this.getNamePrefix());
  }

  @Environment(EnvType.CLIENT)
  @Override
  public @UnknownNullability BlockStateSupplier getBlockStates() {
    final Identifier id = getBlockModelId();
    // 对于上蜡的铜，其自身的方块模型以及对应完整方块的模型均为未上蜡的方块模型，故在此处做出调整。
    Identifier baseId = baseBlock == null ? null : BRRPUtils.getBlockModelId(baseBlock);
    if (baseId != null) {
      final String basePath = baseId.getPath();

      // 基础方块为涂蜡铜方块时，使用未涂蜡铜的模型。
      if (basePath.contains("/waxed_") && basePath.contains("copper")) {
        baseId = baseId.withPath(basePath.replace("/waxed_", "/"));
      }
    }
    return BlockStateModelGenerator.createSlabBlockState(this, id, id.brrp_suffixed("_top"), baseBlock != null ? baseId : id.brrp_suffixed("_double"));
  }

  @Override
  public @Nullable CraftingRecipeJsonBuilder getCraftingRecipe() {
    if (ExtShapeStairsBlock.shouldNotCraftStairsOrSlabs(baseBlock)) {
      return null;
    }
    final CraftingRecipeJsonBuilder craftingRecipe = (baseBlock == Blocks.SNOW_BLOCK && ExtShapeConfig.CURRENT_CONFIG.specialSnowSlabCrafting) ? ShapelessRecipeJsonBuilder.create(this).input(Ingredient.ofItems(Blocks.SNOW)).criterion("has_snow", RecipeProvider.conditionsFromItem(Blocks.SNOW)) : super.getCraftingRecipe();
    return craftingRecipe != null ? craftingRecipe.group(getRecipeGroup()) : null;
  }

  @Override
  public @Nullable SingleItemRecipeJsonBuilder getStonecuttingRecipe() {
    return baseBlock == null ? null : simpleStoneCuttingRecipe(2);
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.SLAB;
  }


  public static class WithExtension extends ExtShapeSlabBlock {
    private final @NotNull BlockExtension extension;

    public WithExtension(@NotNull Block baseBlock, Settings settings, @NotNull BlockExtension extension) {
      super(baseBlock, settings);
      this.extension = extension;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
      super.onStacksDropped(state, world, pos, stack);
      extension.stacksDroppedCallback().onStackDropped(state, world, pos, stack);
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

  /**
   * @see net.minecraft.block.OxidizableSlabBlock
   */
  public static class WithOxidation extends ExtShapeSlabBlock implements Oxidizable {
    private final @NotNull OxidationLevel oxidationLevel;

    public WithOxidation(@NotNull Block baseBlock, Settings settings, @NotNull OxidationLevel oxidationLevel) {
      super(baseBlock, settings);
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
