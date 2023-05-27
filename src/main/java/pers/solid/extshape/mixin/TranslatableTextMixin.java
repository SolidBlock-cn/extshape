package pers.solid.extshape.mixin;

import net.minecraft.text.StringVisitable;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Language;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import pers.solid.extshape.util.AttributiveBlockNameManager;

import java.util.List;

@Mixin(TranslatableText.class)
public class TranslatableTextMixin {
  @Shadow
  @Final
  private String key;

  @Shadow
  @Final
  private Object[] args;

  @Final
  @Mutable
  @Shadow
  private List<StringVisitable> translations;

  @Inject(method = "updateTranslations", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/TranslatableText;setTranslation(Ljava/lang/String;)V", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
  public void modify(CallbackInfo ci, Language language, String string) {
    if (key.equals(AttributiveBlockNameManager.ATTRIBUTIVE_KEY)) {
      final String newKey = (String) args[0];
      final String converted = AttributiveBlockNameManager.convertToAttributive(language.get(newKey), language);
      final Object[] newArgs = ArrayUtils.remove(args, 0);
      final TranslatableText newText = new TranslatableText(newKey, newArgs);
      ((TranslatableTextAccessor) newText).invokeSetTranslation(converted);
      translations = ((TranslatableTextAccessor) newText).getTranslations();
      ci.cancel();
    }
  }
}
