package pers.solid.extshape.block;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.generator.ResourceGeneratorHelper;
import net.devtech.arrp.json.blockstate.JBlockStates;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.recipe.JShapedRecipe;
import net.devtech.arrp.json.recipe.JShapelessRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.TextureKey;
import net.minecraft.item.Item;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.util.BlockCollections;

public class ExtShapePressurePlateBlock extends PressurePlateBlock implements ExtShapeVariantBlockInterface {

  public final Block baseBlock;

  public ExtShapePressurePlateBlock(Block baseBlock, ActivationRule type, Settings settings) {
    super(type, settings, SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON);
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


  @OnlyIn(Dist.CLIENT)
  @Override
  public @NotNull JBlockStates getBlockStates() {
    final Identifier blockModelId = getBlockModelId();
    return JBlockStates.delegate(BlockStateModelGenerator.createPressurePlateBlockState(
        this,
        blockModelId,
        blockModelId.brrp_append("_down")
    ));
  }

  @OnlyIn(Dist.CLIENT)
  @Override
  @NotNull
  public JModel getBlockModel() {
    return new JModel("block/pressure_plate_up")
        .addTexture("texture", getTextureId(TextureKey.TEXTURE));
  }


  @Override
  @OnlyIn(Dist.CLIENT)
  public void writeBlockModel(RuntimeResourcePack pack) {
    final Identifier blockModelId = getBlockModelId();
    final JModel blockModel = getBlockModel();
    pack.addModel(blockModel, blockModelId);
    pack.addModel(blockModel.parent("block/pressure_plate_down"), blockModelId.brrp_append("_down"));
  }


  @Override
  public @NotNull JRecipe getCraftingRecipe() {
    if (BlockCollections.WOOLS.contains(baseBlock)) {
      final Identifier woolId = ForgeRegistries.BLOCKS.getKey(baseBlock);
      final Identifier carpetId = new Identifier(woolId.getNamespace(), woolId.getPath().replaceAll("_wool$", "_carpet"));
      final JShapelessRecipe recipe = new JShapelessRecipe(this.getItemId(), carpetId).recipeCategory(getRecipeCategory());
      recipe.addInventoryChangedCriterion("has_carpet", ForgeRegistries.ITEMS.getValue(carpetId));
      return recipe;
    } else if (baseBlock == Blocks.MOSS_BLOCK) {
      return new JShapelessRecipe(this, Blocks.MOSS_CARPET)
          .recipeCategory(getRecipeCategory()).addInventoryChangedCriterion("has_carpet", Blocks.MOSS_CARPET);
    } else {
      return new JShapedRecipe(this)
          .recipeCategory(getRecipeCategory())
          .pattern("##")
          .addKey("#", baseBlock)
          .addInventoryChangedCriterion("has_ingredient", baseBlock)
          .group(getRecipeGroup());
    }
  }

  @Override
  public void writeRecipes(RuntimeResourcePack pack) {
    ExtShapeVariantBlockInterface.super.writeRecipes(pack);

    // 反向合成配方。
    if (BlockCollections.WOOLS.contains(baseBlock)) {
      final Identifier woolId = ForgeRegistries.BLOCKS.getKey(baseBlock);
      final Identifier carpetId = new Identifier(woolId.getNamespace(), woolId.getPath().replaceAll("_wool$", "_carpet"));
      final JShapelessRecipe recipe = new JShapelessRecipe(carpetId, this.getItemId()).recipeCategory(RecipeCategory.DECORATIONS);
      final Item carpet = ForgeRegistries.ITEMS.getValue(carpetId);
      recipe.addInventoryChangedCriterion("has_pressure_plate", this);
      final Identifier recipeId = new Identifier(ExtShape.MOD_ID, carpetId.getPath() + "_from_pressure_plate");
      pack.addRecipe(recipeId, recipe);
      pack.addRecipeAdvancement(recipeId, ResourceGeneratorHelper.getAdvancementIdForRecipe(carpet, recipeId, recipe), recipe);
    } else if (baseBlock == Blocks.MOSS_BLOCK) {
      final JShapelessRecipe recipe = (JShapelessRecipe) new JShapelessRecipe(Blocks.MOSS_CARPET, this).addInventoryChangedCriterion("has_pressure_plate", this).recipeCategory(RecipeCategory.DECORATIONS);
      final Identifier recipeId = new Identifier(ExtShape.MOD_ID, "moss_carpet_from_pressure_plate");
      pack.addRecipe(recipeId, recipe);
      pack.addRecipeAdvancement(recipeId, ResourceGeneratorHelper.getAdvancementIdForRecipe(Blocks.MOSS_CARPET, recipeId, recipe), recipe);
    }
  }

  @Override
  public String getRecipeGroup() {
    if (BlockCollections.WOOLS.contains(baseBlock)) return "wool_pressure_plate";
    if (BlockCollections.CONCRETES.contains(baseBlock)) return "concrete_pressure_plate";
    if (BlockCollections.STAINED_TERRACOTTA.contains(baseBlock)) return "stained_terracotta_pressure_plate";
    if (BlockCollections.GLAZED_TERRACOTTA.contains(baseBlock)) return "glazed_terracotta_pressure_plate";
    if (BlockCollections.PLANKS.contains(baseBlock)) return "wooden_pressure_plate";
    return "";
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.PRESSURE_PLATE;
  }
}
