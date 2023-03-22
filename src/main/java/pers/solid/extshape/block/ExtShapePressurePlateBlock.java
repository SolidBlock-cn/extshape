package pers.solid.extshape.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.BlockStateSupplier;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SingleItemRecipeJsonBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;
import pers.solid.brrp.v1.api.RuntimeResourcePack;
import pers.solid.brrp.v1.model.ModelJsonBuilder;
import pers.solid.brrp.v1.model.ModelUtils;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.util.BlockCollections;

public class ExtShapePressurePlateBlock extends PressurePlateBlock implements ExtShapeVariantBlockInterface {

  public final Block baseBlock;

  public ExtShapePressurePlateBlock(Block baseBlock, ActivationRule type, Settings settings) {
    super(type, settings);
    this.baseBlock = baseBlock;
  }

  @Override
  public Block getBaseBlock() {
    return baseBlock;
  }

  @Override
  public MutableText getName() {
    return Text.translatable("block.extshape.?_pressure_plate", this.getNamePrefix());
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
      final ShapelessRecipeJsonBuilder recipe = ShapelessRecipeJsonBuilder.create(this).input(carpet).criterion("has_carpet", RecipeProvider.conditionsFromItem(carpet));
      return recipe;
    } else if (baseBlock == Blocks.MOSS_BLOCK) {
      return ShapelessRecipeJsonBuilder.create(this).input(Blocks.MOSS_CARPET)
          .criterion("has_carpet", RecipeProvider.conditionsFromItem(Blocks.MOSS_CARPET));
    } else {
      return ShapedRecipeJsonBuilder.create(this)
          .pattern("##")
          .input('#', baseBlock)
          .criterion(RecipeProvider.hasItem(baseBlock), RecipeProvider.conditionsFromItem(baseBlock))
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


  public static class WithExtension extends ExtShapePressurePlateBlock {
    private final BlockExtension extension;

    public WithExtension(Block baseBlock, ActivationRule type, Settings settings, BlockExtension extension) {
      super(baseBlock, type, settings);
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
}
