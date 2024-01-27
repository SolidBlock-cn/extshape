package pers.solid.extshape.block;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Oxidizable;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.BlockStateSupplier;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SingleItemRecipeJsonBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import pers.solid.brrp.v1.BRRPUtils;
import pers.solid.brrp.v1.generator.BRRPSlabBlock;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;

public class ExtShapeSlabBlock extends BRRPSlabBlock implements ExtShapeVariantBlockInterface {
  public static final MapCodec<ExtShapeSlabBlock> CODEC = BRRPUtils.createCodecWithBaseBlock(createSettingsCodec(), ExtShapeSlabBlock::new);

  public ExtShapeSlabBlock(@NotNull Block baseBlock, Settings settings) {
    super(baseBlock, settings);
  }

  @Override
  public MutableText getName() {
    return Text.translatable("block.extshape.?_slab", this.getNamePrefix());
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
  public @Nullable CraftingRecipeJsonBuilder getCraftingRecipe() {
    final CraftingRecipeJsonBuilder craftingRecipe = (baseBlock == Blocks.SNOW_BLOCK && ExtShapeConfig.CURRENT_CONFIG.specialSnowSlabCrafting) ? ShapelessRecipeJsonBuilder.create(getRecipeCategory(), this).input(Ingredient.ofItems(Blocks.SNOW)).criterion("has_snow", RecipeProvider.conditionsFromItem(Blocks.SNOW)) : super.getCraftingRecipe();
    return craftingRecipe != null && !NOT_TO_CRAFT_STAIRS_OR_SLABS.contains(baseBlock) ? craftingRecipe.group(getRecipeGroup()) : null;
  }

  @Override
  public @Nullable SingleItemRecipeJsonBuilder getStonecuttingRecipe() {
    return baseBlock == null ? null : simpleStoneCuttingRecipe(2);
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.SLAB;
  }

  @Override
  public MapCodec<? extends ExtShapeSlabBlock> getCodec() {
    return CODEC;
  }

  public static class WithExtension extends ExtShapeSlabBlock {
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

  /**
   * @see net.minecraft.block.OxidizableSlabBlock
   */
  public static class WithOxidation extends ExtShapeSlabBlock implements Oxidizable {
    private final OxidationLevel oxidationLevel;
    public static final MapCodec<WithOxidation> CODEC = CopperManager.createCodec(createSettingsCodec(), WithOxidation::new);

    public WithOxidation(@NotNull Block baseBlock, Settings settings, OxidationLevel oxidationLevel) {
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

    @Override
    public MapCodec<? extends WithOxidation> getCodec() {
      return CODEC;
    }
  }
}
