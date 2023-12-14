package pers.solid.extshape.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallBlock;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SingleItemRecipeJsonBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.brrp.v1.BRRPUtils;
import pers.solid.brrp.v1.generator.BRRPWallBlock;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.util.BlockCollections;

public class ExtShapeWallBlock extends BRRPWallBlock implements ExtShapeVariantBlockInterface {
  @SuppressWarnings("unchecked")
  public static final MapCodec<WallBlock> CODEC = (MapCodec<WallBlock>) (MapCodec<?>) BRRPUtils.createCodecWithBaseBlock(createSettingsCodec(), ExtShapeWallBlock::new);

  public ExtShapeWallBlock(@NotNull Block baseBlock, Settings settings) {
    super(baseBlock, settings);
  }

  @Override
  public MutableText getName() {
    return Text.translatable("block.extshape.?_wall", this.getNamePrefix());
  }

  @Override
  public @Nullable CraftingRecipeJsonBuilder getCraftingRecipe() {
    final CraftingRecipeJsonBuilder craftingRecipe = super.getCraftingRecipe();
    return craftingRecipe == null || (ExtShapeConfig.CURRENT_CONFIG.preventWoodenWallRecipes && BlockCollections.PLANKS.contains(baseBlock)) ? null : craftingRecipe.group(getRecipeGroup());
  }

  @Override
  public @Nullable SingleItemRecipeJsonBuilder getStonecuttingRecipe() {
    return simpleStoneCuttingRecipe(1);
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.WALL;
  }

  @Override
  public MapCodec<WallBlock> getCodec() {
    return CODEC;
  }

  public static class WithExtension extends ExtShapeWallBlock {
    private final BlockExtension extension;

    public WithExtension(Block baseBlock, Settings settings, BlockExtension extension) {
      super(baseBlock, settings);
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

    @SuppressWarnings("deprecation")
    @Override
    public boolean emitsRedstonePower(BlockState state) {
      return super.emitsRedstonePower(state) || extension.emitsRedstonePower().emitsRedstonePower(state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
      return extension.weakRedstonePower().getWeakRedstonePower(state, world, pos, direction);
    }
  }
}
