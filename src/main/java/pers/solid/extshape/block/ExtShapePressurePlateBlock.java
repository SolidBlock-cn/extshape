package pers.solid.extshape.block;

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
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SingleItemRecipeJsonBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import pers.solid.brrp.v1.api.RuntimeResourcePack;
import pers.solid.brrp.v1.model.ModelJsonBuilder;
import pers.solid.brrp.v1.model.ModelUtils;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.util.ActivationSettings;
import pers.solid.extshape.util.BlockCollections;

/**
 * 本模组中的压力板方块，方块的激活时长和激活类型可能是自定义的。
 */
public class ExtShapePressurePlateBlock extends PressurePlateBlock implements ExtShapeVariantBlockInterface {

  public final Block baseBlock;
  private final SoundEvent depressSound;
  private final SoundEvent pressSound;
  protected final int tickRate;

  public ExtShapePressurePlateBlock(@NotNull Block baseBlock, ActivationRule activationRule, Settings settings, SoundEvent depressSound, SoundEvent pressSound, int tickRate) {
    super(activationRule, settings);
    this.baseBlock = baseBlock;
    this.depressSound = depressSound;
    this.pressSound = pressSound;
    this.tickRate = tickRate;
  }

  public ExtShapePressurePlateBlock(@NotNull Block baseBlock, Settings settings, @NotNull ActivationSettings activationSettings) {
    this(baseBlock, activationSettings.activationRule(), settings, activationSettings.sounds().depressSound(), activationSettings.sounds().pressSound(), activationSettings.plateTime());
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
  public PistonBehavior getPistonBehavior(BlockState state) {
    return baseBlock.getHardness() == -1 || baseBlock == Blocks.OBSIDIAN || baseBlock == Blocks.CRYING_OBSIDIAN || baseBlock.getDefaultState().getPistonBehavior() == PistonBehavior.BLOCK ? PistonBehavior.BLOCK : super.getPistonBehavior(state);
  }

  @Environment(EnvType.CLIENT)
  @Override
  public @UnknownNullability BlockStateSupplier getBlockStates() {
    final Identifier blockModelId = getBlockModelId();
    return BlockStateModelGenerator.createPressurePlateBlockState(
        this,
        blockModelId,
        blockModelId.brrp_suffixed("_down")
    );
  }

  @Environment(EnvType.CLIENT)
  @Override
  public @UnknownNullability ModelJsonBuilder getBlockModel() {
    return ModelJsonBuilder.create(Models.PRESSURE_PLATE_UP)
        .addTexture("texture", getTextureId(TextureKey.TEXTURE));
  }

  protected void playPressSound(WorldAccess world, BlockPos pos) {
    world.playSound(null, pos, pressSound, SoundCategory.BLOCKS, 0.3F, 0.8F);
  }

  protected void playDepressSound(WorldAccess world, BlockPos pos) {
    world.playSound(null, pos, depressSound, SoundCategory.BLOCKS, 0.3F, 0.7F);
  }

  @Override
  @Environment(EnvType.CLIENT)
  public void writeBlockModel(RuntimeResourcePack pack) {
    final Identifier blockModelId = getBlockModelId();
    final ModelJsonBuilder blockModel = getBlockModel();
    ModelUtils.writeModelsWithVariants(pack, blockModelId, blockModel, Models.PRESSURE_PLATE_UP, Models.PRESSURE_PLATE_DOWN);
  }

  @Override
  public @Nullable CraftingRecipeJsonBuilder getCraftingRecipe() {
    if (BlockCollections.WOOLS.contains(baseBlock)) {
      final Identifier woolId = Registry.BLOCK.getId(baseBlock);
      final Identifier carpetId = new Identifier(woolId.getNamespace(), woolId.getPath().replaceAll("_wool$", "_carpet"));
      final Item carpet = Registry.ITEM.get(carpetId);
      // 一个羊毛压力板由 3 个地毯合成。
      return ShapedRecipeJsonBuilder.create(this)
          .pattern("###")
          .input('#', carpet)
          .criterionFromItem(this)
          .group(getRecipeGroup());
    } else if (baseBlock == Blocks.MOSS_BLOCK) {
      // 一个苔藓压力板由 3 个覆地苔藓合成。
      return ShapedRecipeJsonBuilder.create(this)
          .pattern("###")
          .input('#', Items.MOSS_CARPET)
          .criterionFromItem(Items.MOSS_CARPET)
          .group(getRecipeGroup());
    } else {
      return ShapedRecipeJsonBuilder.create(this)
          .pattern("##")
          .input('#', baseBlock)
          .criterionFromItem(baseBlock)
          .group(getRecipeGroup());
    }
  }

  @Override
  public void writeRecipes(RuntimeResourcePack pack) {
    ExtShapeVariantBlockInterface.super.writeRecipes(pack);

    // 反向合成配方。
    if (BlockCollections.WOOLS.contains(baseBlock)) {
      final Identifier woolId = Registry.BLOCK.getId(baseBlock);
      final Identifier carpetId = new Identifier(woolId.getNamespace(), woolId.getPath().replaceAll("_wool$", "_carpet"));
      final Item carpet = Registry.ITEM.get(carpetId);
      final SingleItemRecipeJsonBuilder recipe = SingleItemRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(this), carpet).criterion("has_pressure_plate", RecipeProvider.conditionsFromItem(this));
      final Identifier recipeId = new Identifier(ExtShape.MOD_ID, carpetId.getPath() + "_from_pressure_plate");
      pack.addRecipeAndAdvancement(recipeId, recipe);
    } else if (baseBlock == Blocks.MOSS_BLOCK) {
      final SingleItemRecipeJsonBuilder recipe = SingleItemRecipeJsonBuilder.createStonecutting(Ingredient.ofItems(this), Blocks.MOSS_CARPET).criterion("has_pressure_plate", RecipeProvider.conditionsFromItem(this));
      final Identifier recipeId = new Identifier(ExtShape.MOD_ID, "moss_carpet_from_pressure_plate");
      pack.addRecipeAndAdvancement(recipeId, recipe);
    }
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.PRESSURE_PLATE;
  }


  @Override
  public int getTickRate() {
    return tickRate;
  }

  @Override
  public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
    super.onStateReplaced(state, world, pos, newState, moved);
    if (getRedstoneOutput(state) > 0 && newState.getBlock() instanceof ExtShapePressurePlateBlock && getRedstoneOutput(newState) > 0) {
      world.createAndScheduleBlockTick(pos.toImmutable(), newState.getBlock(), getTickRate());
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
