package pers.solid.extshape.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import pers.solid.extshape.ExtShapeBlockItem;

@Mixin(Item.class)
public abstract class ItemMixin {
  @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;"))
  private MutableComponent tweakName(String key, Operation<MutableComponent> original, @Local(argsOnly = true) Item.Properties settings) {
    final Item thisItem = (Item) (Object) this;
    if (thisItem instanceof ExtShapeBlockItem) {
      final ResourceKey<Item> registryKey = ((ItemSettingAccessor) settings).getId();
      return BuiltInRegistries.BLOCK.getOptional(registryKey.identifier()).orElseThrow(() -> new IllegalStateException("Block with registry id " + registryKey.identifier() + " does not exist or is not registered.")).getName();
    } else {
      return original.call(key);
    }
  }
}
