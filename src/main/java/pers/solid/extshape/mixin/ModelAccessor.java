package pers.solid.extshape.mixin;

import net.minecraft.data.client.Model;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Optional;

@Mixin(Model.class)
public interface ModelAccessor {
  @Accessor
  Optional<String> getVariant();
}
