package pers.solid.extshape.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.DependantName;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import pers.solid.extshape.util.SpecialDependantName;

@Mixin(Item.Properties.class)
public abstract class ItemPropertiesMixin {
  @Shadow
  private DependantName<Item, String> descriptionId;

  @WrapOperation(method = "lambda$finalizeInitializer$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;"))
  private MutableComponent tweakName(String key, Operation<MutableComponent> original, @Local(argsOnly = true) ResourceKey<Item> resourceKey) {
    if (descriptionId instanceof SpecialDependantName<Item, String>) {
      return BuiltInRegistries.BLOCK.getOptional(resourceKey.identifier()).orElseThrow(() -> new IllegalStateException("Block with registry id " + resourceKey.identifier() + " does not exist or is not registered.")).getName();
    } else {
      return original.call(key);
    }
  }
}
