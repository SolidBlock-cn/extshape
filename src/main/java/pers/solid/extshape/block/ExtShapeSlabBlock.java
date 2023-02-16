package pers.solid.extshape.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.model.BlockStateModelGenerator;
import net.minecraft.data.client.model.BlockStateSupplier;
import net.minecraft.data.server.RecipesProvider;
import net.minecraft.data.server.recipe.CraftingRecipeJsonFactory;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonFactory;
import net.minecraft.data.server.recipe.SingleItemRecipeJsonFactory;
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

public class ExtShapeSlabBlock extends BRRPSlabBlock implements ExtShapeVariantBlockInterface {
  public ExtShapeSlabBlock(@NotNull Block baseBlock, Settings settings) {
    super(baseBlock, settings);
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
    if (baseId != null && baseId.getPath().contains("waxed_") && baseId.getPath().contains("copper")) {
      baseId = new Identifier(baseId.getNamespace(), baseId.getPath().replace("waxed_", ""));
    }
    return BlockStateModelGenerator.createSlabBlockState(this, id, id.brrp_suffixed("_top"), baseBlock != null ? baseId : id.brrp_suffixed("_double"));
  }

  @Override
  public @Nullable CraftingRecipeJsonFactory getCraftingRecipe() {
    final CraftingRecipeJsonFactory craftingRecipe = (baseBlock == Blocks.SNOW_BLOCK && ExtShapeConfig.CURRENT_CONFIG.specialSnowSlabCrafting) ? ShapelessRecipeJsonFactory.create(this).input(Ingredient.ofItems(Blocks.SNOW)).criterion("has_snow", RecipesProvider.conditionsFromItem(Blocks.SNOW)) : super.getCraftingRecipe();
    return craftingRecipe != null && !NOT_TO_CRAFT_STAIRS_OR_SLABS.contains(baseBlock) ? craftingRecipe.group(getRecipeGroup()) : null;
  }

  @Override
  public @Nullable SingleItemRecipeJsonFactory getStonecuttingRecipe() {
    return baseBlock == null ? null : simpleStoneCuttingRecipe(2);
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.SLAB;
  }


  public static class WithExtension extends ExtShapeSlabBlock {
    private final BlockExtension extension;

    public WithExtension(Block baseBlock, Settings settings, BlockExtension extension) {
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
}
