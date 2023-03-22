package pers.solid.extshape.blockus.mixin;

import net.minecraft.advancement.Advancement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder$ShapedRecipeJsonProvider")
public
interface ShapedRecipeJsonProviderAccessor {
  @Accessor
  Advancement.Builder getAdvancementBuilder();
}
