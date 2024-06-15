package pers.solid.extshape.mixin;

import net.minecraft.data.server.recipe.StonecuttingRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.recipe.book.RecipeCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StonecuttingRecipeJsonBuilder.class)
public interface StonecuttingRecipeJsonBuilderAccessor {
  @Accessor
  RecipeCategory getCategory();

  @Accessor
  Item getOutput();

  @Accessor
  int getCount();
}
