package pers.solid.extshape.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.MutableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import pers.solid.extshape.ExtShapeBlockItem;

@Mixin(Item.class)
public abstract class ItemMixin {
  @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/Text;translatable(Ljava/lang/String;)Lnet/minecraft/text/MutableText;"))
  private MutableText tweakName(String key, Operation<MutableText> original, @Local(argsOnly = true) Item.Settings settings) {
    final Item thisItem = (Item) (Object) this;
    if (thisItem instanceof ExtShapeBlockItem) {
      final RegistryKey<Item> registryKey = ((ItemSettingAccessor) settings).getRegistryKey();
      return Registries.BLOCK.getOptionalValue(registryKey.getValue()).orElseThrow(() -> new IllegalStateException("Block with registry id " + registryKey.getValue() + " does not exist or is not registered.")).getName();
    } else {
      return original.call(key);
    }
  }
}
