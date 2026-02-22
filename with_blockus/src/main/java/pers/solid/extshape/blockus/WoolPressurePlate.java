package pers.solid.extshape.blockus;

import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapePressurePlateBlock;
import pers.solid.extshape.util.ActivationSettings;

/**
 * 羊毛压力板，与普通的压力板不同的是，羊毛压力板的合成配方不是两个羊毛方块，而是一个对应的地毯。
 */
public class WoolPressurePlate extends ExtShapePressurePlateBlock {
  private final Block carpet;

  public WoolPressurePlate(Block baseBlock, Properties settings, @NotNull ActivationSettings activationSettings, Block carpet) {
    super(baseBlock, settings, activationSettings);
    this.carpet = carpet;
  }

  @Override
  public @Nullable RecipeBuilder getCraftingRecipe(RecipeProvider recipeGenerator) {
    return recipeGenerator.shaped(getRecipeCategory(), this)
        .pattern("###")
        .define('#', carpet)
        .unlockedBy(RecipeProvider.getHasName(carpet), recipeGenerator.has(carpet))
        .group(getRecipeGroup());
  }
}
