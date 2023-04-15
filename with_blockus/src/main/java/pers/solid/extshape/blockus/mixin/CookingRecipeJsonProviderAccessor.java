package pers.solid.extshape.blockus.mixin;

import net.minecraft.advancement.Advancement;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CookingRecipeJsonBuilder.CookingRecipeJsonProvider.class)
public
interface CookingRecipeJsonProviderAccessor {
  @Accessor
  Advancement.Builder getAdvancementBuilder();
}
