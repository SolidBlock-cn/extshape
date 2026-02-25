package pers.solid.extshape.mixin;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.world.item.ItemStackTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SingleItemRecipeBuilder.class)
public interface StonecuttingRecipeJsonBuilderAccessor {
  @Accessor
  RecipeCategory getCategory();

  @Accessor
  ItemStackTemplate getResult();
}
