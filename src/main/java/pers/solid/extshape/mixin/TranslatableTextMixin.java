package pers.solid.extshape.mixin;

import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Language;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import pers.solid.extshape.util.AttributiveBlockNameManager;

import java.util.function.Consumer;

@Mixin(TranslatableText.class)
public class TranslatableTextMixin {
  @Shadow
  @Final
  private String key;

  @Shadow
  @Final
  private Object[] args;

  @WrapWithCondition(method = "updateTranslations", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/TranslatableText;forEachPart(Ljava/lang/String;Ljava/util/function/Consumer;)V"))
  public boolean modify(TranslatableText instance, String translation, Consumer<StringVisitable> partsConsumer, @Local ImmutableList.Builder<StringVisitable> builder) {
    Language language = Language.getInstance();
    if (key.equals(AttributiveBlockNameManager.ATTRIBUTIVE_KEY)) {
      final String newKey = (String) args[0];
      final String converted = AttributiveBlockNameManager.convertToAttributive(language.get(newKey), language);
      final Object[] newArgs = ArrayUtils.remove(args, 0);
      final TranslatableText newText = new TranslatableText(newKey, newArgs);
      ((TranslatableTextAccessor) newText).invokeForEachPart(converted, builder::add);
      return false;
    } else {
      return true;
    }
  }
}
