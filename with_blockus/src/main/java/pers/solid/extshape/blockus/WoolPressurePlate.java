package pers.solid.extshape.blockus;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapePressurePlateBlock;

/**
 * 羊毛压力板，与普通的压力板不同的是，羊毛压力板的合成配方不是两个羊毛方块，而是一个对应的地毯。
 */
public class WoolPressurePlate extends ExtShapePressurePlateBlock {
  private final Block carpet;

  public WoolPressurePlate(Block baseBlock, Settings settings, @NotNull BlockSetType blockSetType, int tickRate, Block carpet) {
    super(baseBlock, settings, blockSetType, tickRate);
    this.carpet = carpet;
  }

  @Override
  public @Nullable CraftingRecipeJsonBuilder getCraftingRecipe() {
    return ShapedRecipeJsonBuilder.create(getRecipeCategory(), this).pattern("###").input('#', carpet).criterionFromItem(carpet).group(getRecipeGroup());
  }
}
