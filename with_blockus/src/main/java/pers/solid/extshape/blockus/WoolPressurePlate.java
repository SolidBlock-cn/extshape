package pers.solid.extshape.blockus;

import net.minecraft.block.Block;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapePressurePlateBlock;
import pers.solid.extshape.util.ActivationSettings;

/**
 * 羊毛压力板，与普通的压力板不同的是，羊毛压力板的合成配方不是两个羊毛方块，而是一个对应的地毯。
 */
public class WoolPressurePlate extends ExtShapePressurePlateBlock {
  private final Block carpet;

  public WoolPressurePlate(Block baseBlock, Settings settings, @NotNull ActivationSettings activationSettings, Block carpet) {
    super(baseBlock, settings, activationSettings);
    this.carpet = carpet;
  }

  @Override
  public @Nullable CraftingRecipeJsonBuilder getCraftingRecipe() {
    return ShapedRecipeJsonBuilder.create(getRecipeCategory(), this)
        .pattern("###")
        .input('#', carpet)
        .criterion(RecipeProvider.hasItem(carpet), RecipeProvider.conditionsFromItem(carpet))
        .group(getRecipeGroup());
  }
}
