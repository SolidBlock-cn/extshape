package pers.solid.extshape.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.data.client.*;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SingleItemRecipeJsonBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
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
import pers.solid.brrp.v1.model.ModelJsonBuilder;
import pers.solid.brrp.v1.model.ModelUtils;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.builder.BlocksBuilder;
import pers.solid.extshape.util.HorizontalCornerDirection;

import java.util.Random;

/**
 * 本模组中的竖直楼梯方块。
 */
public class ExtShapeVerticalStairsBlock extends VerticalStairsBlock implements ExtShapeVariantBlockInterface {
  public final @NotNull Block baseBlock;

  public ExtShapeVerticalStairsBlock(@NotNull Block baseBlock, Settings settings) {
    super(settings);
    this.baseBlock = baseBlock;
  }

  @Override
  public @NotNull Block getBaseBlock() {
    return baseBlock;
  }

  @Override
  public MutableText getName() {
    return new TranslatableText("block.extshape.?_vertical_stairs", this.getNamePrefix());
  }

  @Override
  @Environment(EnvType.CLIENT)
  public @UnknownNullability BlockStateSupplier getBlockStates() {
    final Identifier identifier = getBlockModelId();
    return VariantsBlockStateSupplier.create(this, new BlockStateVariant()
        .put(VariantSettings.MODEL, identifier)
        .put(VariantSettings.UVLOCK, true)
    ).coordinate(BlockStateVariantMap.create(FACING)
        .register(HorizontalCornerDirection.SOUTH_WEST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R0))
        .register(HorizontalCornerDirection.NORTH_WEST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R90))
        .register(HorizontalCornerDirection.NORTH_EAST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R180))
        .register(HorizontalCornerDirection.SOUTH_EAST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R270)));
  }


  @Override
  @Environment(EnvType.CLIENT)
  public @UnknownNullability ModelJsonBuilder getBlockModel() {
    return ModelJsonBuilder.create(new Identifier(ExtShape.MOD_ID, "block/vertical_stairs")).setTextures(ModelUtils.getTextureMap(this, TextureKey.TOP, TextureKey.SIDE, TextureKey.BOTTOM));
  }

  /**
   * 注意：跨方块类型的合成表由 {@link BlocksBuilder} 定义。
   */
  @Override
  public @Nullable CraftingRecipeJsonBuilder getCraftingRecipe() {
    return null;
  }


  @Override
  public @Nullable SingleItemRecipeJsonBuilder getStonecuttingRecipe() {
    return simpleStoneCuttingRecipe(1);
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.VERTICAL_STAIRS;
  }


  public static class WithExtension extends ExtShapeVerticalStairsBlock {
    private final @NotNull BlockExtension extension;

    public WithExtension(Block baseBlock, Settings settings, @NotNull BlockExtension extension) {
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

  public static class WithOxidation extends ExtShapeVerticalStairsBlock implements Oxidizable {
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
