package pers.solid.extshape.mixin;

import net.minecraft.data.server.recipe.SingleItemRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SingleItemRecipeJsonBuilder.class)
public interface SingleItemRecipeJsonBuilderAccessor {
  @Accessor
  RecipeCategory getCategory();

  @Accessor
  Item getOutput();

  @Accessor
  Ingredient getInput();

  @Accessor
  int getCount();
}
