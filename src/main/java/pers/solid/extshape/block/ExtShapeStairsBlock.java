package pers.solid.extshape.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.StonecuttingRecipeJsonBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import pers.solid.brrp.v1.BRRPUtils;
import pers.solid.brrp.v1.generator.BRRPStairsBlock;
import pers.solid.extshape.builder.BlockShape;

public class ExtShapeStairsBlock extends BRRPStairsBlock implements ExtShapeVariantBlockInterface {
  public static final MapCodec<ExtShapeStairsBlock> CODEC = BRRPUtils.createCodecWithBaseBlock(createSettingsCodec(), ExtShapeStairsBlock::new);

  public ExtShapeStairsBlock(Block baseBlock, Settings settings) {
    super(baseBlock, settings);
  }

  @Override
  public MutableText getName() {
    return Text.translatable("block.extshape.?_stairs", this.getNamePrefix());
  }

  @Override
  public @Nullable StonecuttingRecipeJsonBuilder getStonecuttingRecipe() {
    return simpleStoneCuttingRecipe(1);
  }

  @Override
  public @Nullable CraftingRecipeJsonBuilder getCraftingRecipe() {
    if (NOT_TO_CRAFT_STAIRS_OR_SLABS.contains(baseBlock)) {
      return null;
    }
    final CraftingRecipeJsonBuilder craftingRecipe = super.getCraftingRecipe();
    return craftingRecipe == null ? null : craftingRecipe.group(getRecipeGroup());
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.STAIRS;
  }

  @Override
  public MapCodec<? extends ExtShapeStairsBlock> getCodec() {
    return CODEC;
  }

  public static class WithExtension extends ExtShapeStairsBlock {
    private final BlockExtension extension;

    public WithExtension(Block baseBlock, Settings settings, BlockExtension extension) {
      super(baseBlock, settings);
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

  /**
   * @see net.minecraft.block.OxidizableStairsBlock
   */
  public static class WithOxidation extends ExtShapeStairsBlock implements Oxidizable {
    private final OxidationLevel oxidationLevel;
    public static final MapCodec<WithOxidation> CODEC = CopperManager.createCodec(createSettingsCodec(), WithOxidation::new);

    public WithOxidation(Block baseBlock, Settings settings, OxidationLevel oxidationLevel) {
      super(baseBlock, settings);
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

    @Override
    public MapCodec<? extends WithOxidation> getCodec() {
      return CODEC;
    }
  }
}
