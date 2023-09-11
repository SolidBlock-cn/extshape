package pers.solid.extshape.mixin;

import net.minecraft.data.server.recipe.SingleItemRecipeJsonBuilder;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SingleItemRecipeJsonBuilder.class)
public interface SingleItemRecipeJsonBuilderAccessor {

  @Accessor
  Item getOutput();

  @Accessor
  int getCount();
}
