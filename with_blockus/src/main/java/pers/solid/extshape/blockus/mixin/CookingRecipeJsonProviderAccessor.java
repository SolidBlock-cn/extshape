package pers.solid.extshape.blockus.mixin;

import net.minecraft.advancement.Advancement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.minecraft.data.server.recipe.CookingRecipeJsonBuilder$CookingRecipeJsonProvider")
public
interface CookingRecipeJsonProviderAccessor {
  @Accessor
  Advancement.Builder getAdvancementBuilder();
}
