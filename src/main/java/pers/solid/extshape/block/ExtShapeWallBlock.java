package pers.solid.extshape.block;

import net.devtech.arrp.generator.BRRPWallBlock;
import net.devtech.arrp.json.recipe.JRecipe;
import net.minecraft.block.Block;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.util.BlockCollections;

public class ExtShapeWallBlock extends BRRPWallBlock implements ExtShapeVariantBlockInterface {
  public ExtShapeWallBlock(@NotNull Block baseBlock, Settings settings) {
    super(baseBlock, settings);
  }

  @Override
  public MutableText getName() {
    return Text.translatable("block.extshape.?_wall", this.getNamePrefix());
  }

  @Override
  public @Nullable JRecipe getCraftingRecipe() {
    final JRecipe craftingRecipe = super.getCraftingRecipe();
    return craftingRecipe == null || (ExtShapeConfig.CURRENT_CONFIG.preventWoodenWallRecipes && BlockCollections.PLANKS.contains(baseBlock)) ? null : craftingRecipe.group(getRecipeGroup());
  }

  @Override
  public @Nullable JRecipe getStonecuttingRecipe() {
    return simpleStoneCuttingRecipe(1).recipeCategory(getRecipeCategory());
  }

  @Override
  public String getRecipeGroup() {
    if (BlockCollections.WOOLS.contains(baseBlock)) return "wool_wall";
    if (BlockCollections.CONCRETES.contains(baseBlock)) return "concrete_wall";
    if (BlockCollections.STAINED_TERRACOTTA.contains(baseBlock)) return "stained_terracotta_wall";
    if (BlockCollections.GLAZED_TERRACOTTA.contains(baseBlock)) return "glazed_terracotta_wall";
    if (BlockCollections.PLANKS.contains(baseBlock)) return "wooden_wall";
    return "";
  }

  @Override
  public BlockShape getBlockShape() {
    return BlockShape.WALL;
  }
}
