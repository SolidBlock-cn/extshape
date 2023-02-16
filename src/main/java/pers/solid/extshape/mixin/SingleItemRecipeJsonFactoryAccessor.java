package pers.solid.extshape.mixin;

import net.minecraft.data.server.recipe.SingleItemRecipeJsonFactory;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SingleItemRecipeJsonFactory.class)
public interface SingleItemRecipeJsonFactoryAccessor {

  @Accessor
  Item getOutput();

  @Accessor
  Ingredient getInput();

  @Accessor
  int getCount();
}
