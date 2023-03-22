package pers.solid.extshape.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import pers.solid.brrp.v1.model.ModelJsonBuilder;
import pers.solid.brrp.v1.model.ModelUtils;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.builder.BlocksBuilder;
import pers.solid.extshape.util.HorizontalCornerDirection;

public class ExtShapeVerticalStairsBlock extends VerticalStairsBlock implements ExtShapeVariantBlockInterface {
  public final Block baseBlock;

  public ExtShapeVerticalStairsBlock(Block baseBlock, Settings settings) {
    super(settings);
    this.baseBlock = baseBlock;
  }

  @Override
  public Block getBaseBlock() {
    return baseBlock;
  }

  @Override
  public MutableText getName() {
    return new TranslatableText("block.extshape.?_vertical_stairs", this.getNamePrefix());
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public @UnknownNullability BlockStateSupplier getBlockStates() {
    final Identifier identifier = getBlockModelId();
    return VariantsBlockStateSupplier.create(this, new BlockStateVariant().put(VariantSettings.MODEL, identifier).put(VariantSettings.UVLOCK, true)).coordinate(BlockStateVariantMap.create(FACING)
        .register(HorizontalCornerDirection.SOUTH_WEST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R0))
        .register(HorizontalCornerDirection.NORTH_WEST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R90))
        .register(HorizontalCornerDirection.NORTH_EAST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R180))
        .register(HorizontalCornerDirection.SOUTH_EAST, BlockStateVariant.create().put(VariantSettings.Y, VariantSettings.Rotation.R270)));
  }


  @Override
  @OnlyIn(Dist.CLIENT)
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
