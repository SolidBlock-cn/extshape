package pers.solid.extshape.block;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.BlockStateSupplier;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.*;
import pers.solid.brrp.v1.api.RuntimeResourcePack;
import pers.solid.brrp.v1.model.ModelJsonBuilder;
import pers.solid.brrp.v1.model.ModelUtils;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.mixin.AbstractButtonBlockAccessor;
import pers.solid.extshape.util.ActivationSettings;
import pers.solid.extshape.util.BlockCollections;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * 本模组中的按钮方块。按钮的激活时长可能会是特制的。
 */
public class ExtShapeButtonBlock extends AbstractButtonBlock implements ExtShapeVariantBlockInterface {
  /**
   * 该集合内的方块将不会生成按钮配方，以解决合成配方的冲突。
   *
   * @see pers.solid.extshape.config.ExtShapeConfig#avoidSomeButtonRecipes
   */
  private static final Supplier<@Unmodifiable Collection<Block>> REFUSE_RECIPES = Suppliers.memoize(() -> ImmutableSet.<Block>builder().add(Blocks.EMERALD_BLOCK, Blocks.IRON_BLOCK, Blocks.GOLD_BLOCK, Blocks.DIAMOND_BLOCK, Blocks.COAL_BLOCK, Blocks.LAPIS_BLOCK, Blocks.NETHERITE_BLOCK, Blocks.PUMPKIN, Blocks.COPPER_BLOCK, Blocks.RAW_GOLD_BLOCK, Blocks.RAW_COPPER_BLOCK, Blocks.RAW_IRON_BLOCK, Blocks.WAXED_COPPER_BLOCK).addAll(BlockCollections.LOGS).addAll(BlockCollections.WOODS).addAll(BlockCollections.HYPHAES).addAll(BlockCollections.STEMS).addAll(BlockCollections.STRIPPED_LOGS).addAll(BlockCollections.STRIPPED_WOODS).addAll(BlockCollections.STRIPPED_HYPHAES).addAll(BlockCollections.STRIPPED_STEMS).build());

  /**
   * 该基础方块的方块是否应该避免生成按钮配方，以避免合成表冲突。
   *
   * @param baseBlock 基础方块。
   * @return 如果为 {@code true}，则表示该基础方块的按钮不应该有合成配方。
   */
  @Contract(pure = true)
  public static boolean shouldRefuseRecipe(@NotNull Block baseBlock) {
    return REFUSE_RECIPES.get().contains(baseBlock);
  }

  public final @NotNull Block baseBlock;

  public ExtShapeButtonBlock(@NotNull Block baseBlock, Settings blockSettings, @NotNull ActivationSettings activationSettings) {
    super(activationSettings.buttonActivatedByProjectile(), blockSettings);
    this.baseBlock = baseBlock;
    this.clickOffSound = activationSettings.sounds().clickOffSound();
    this.clickOnSound = activationSettings.sounds().clickOnSound();
    this.pressTicks = activationSettings.buttonTime();
  }

  @Override
  public @NotNull Block getBaseBlock() {
    return baseBlock;
  }

  @Override
  protected SoundEvent getClickSound(boolean powered) {
    return powered ? clickOnSound : clickOffSound;
  }

  @Override
  public MutableText getName() {
    return Text.translatable("block.extshape.?_button", this.getNamePrefix());
  }

  @SuppressWarnings("deprecation")
  @Override
  public PistonBehavior getPistonBehavior(BlockState state) {
    return baseBlock.getHardness() == -1 || baseBlock == Blocks.OBSIDIAN || baseBlock == Blocks.CRYING_OBSIDIAN || baseBlock.getDefaultState().getPistonBehavior() == PistonBehavior.BLOCK ? PistonBehavior.BLOCK : super.getPistonBehavior(state);
  }

  @Override
  @Environment(EnvType.CLIENT)
  public @UnknownNullability BlockStateSupplier getBlockStates() {
    final Identifier blockModelId = getBlockModelId();
    return BlockStateModelGenerator.createButtonBlockState(
        this,
        blockModelId,
        blockModelId.brrp_suffixed("_pressed")
    );
  }

  @Environment(EnvType.CLIENT)
  @Override
  public @UnknownNullability ModelJsonBuilder getBlockModel() {
    return ModelJsonBuilder.create(Models.BUTTON).addTexture(TextureKey.TEXTURE, getTextureId(TextureKey.TEXTURE));
  }

  @Environment(EnvType.CLIENT)
  @Override
  public void writeBlockModel(RuntimeResourcePack pack) {
    final Identifier blockModelId = getBlockModelId();
    final ModelJsonBuilder blockModel = getBlockModel();
    ModelUtils.writeModelsWithVariants(pack, blockModelId, blockModel, Models.BUTTON, Models.BUTTON_INVENTORY, Models.BUTTON_PRESSED);
  }


  @Environment(EnvType.CLIENT)
  @Override
  public @UnknownNullability ModelJsonBuilder getItemModel() {
    return ModelJsonBuilder.create(ModelUtils.appendVariant(getBlockModelId(), Models.BUTTON_INVENTORY));
  }

  @Override
  public @Nullable CraftingRecipeJsonBuilder getCraftingRecipe() {
    final Block baseBlock = getBaseBlock();
    if (shouldRefuseRecipe(baseBlock) && ExtShapeConfig.CURRENT_CONFIG.avoidSomeButtonRecipes) {
      return null;
    }
    return ShapelessRecipeJsonBuilder.create(this)
        .input(baseBlock)
        .criterion(RecipeProvider.hasItem(baseBlock), RecipeProvider.conditionsFromItem(baseBlock))
        .group(getRecipeGroup());
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.BUTTON;
  }

  @Override
  public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
    super.onStateReplaced(state, world, pos, newState, moved);
    if (state.get(POWERED) && newState.getBlock() instanceof ExtShapeButtonBlock && newState.get(POWERED)) {
      world.createAndScheduleBlockTick(pos.toImmutable(), newState.getBlock(), ((AbstractButtonBlockAccessor) this).invokeGetPressTicks());
    }
  }

  public static class WithExtension extends ExtShapeButtonBlock {
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
  }

  public static class WithOxidation extends ExtShapeButtonBlock implements Oxidizable {
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
