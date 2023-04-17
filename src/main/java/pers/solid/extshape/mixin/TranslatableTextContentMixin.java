package pers.solid.extshape.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Language;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import pers.solid.extshape.util.AttributiveBlockNameManager;

import java.util.List;

@Mixin(TranslatableTextContent.class)
public class TranslatableTextContentMixin {
  @Shadow
  @Final
  private String key;

  @Shadow
  @Final
  private Object[] args;

  @Shadow
  private List<StringVisitable> translations;

  @Shadow
  @Final
  @Nullable
  private String fallback;

  @Inject(method = "updateTranslations", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/TranslatableTextContent;forEachPart(Ljava/lang/String;Ljava/util/function/Consumer;)V", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
  public void modify(CallbackInfo ci, String string, ImmutableList.Builder<StringVisitable> builder) {
    Language language = Language.getInstance();
    if (key.equals(AttributiveBlockNameManager.ATTRIBUTIVE_KEY)) {
      final String newKey = (String) args[0];
      final String converted = AttributiveBlockNameManager.convertToAttributive(language.get(newKey), language);
      final Object[] newArgs = ArrayUtils.remove(args, 0);
      final TranslatableTextContent newContent = new TranslatableTextContent(newKey, fallback, newArgs);
      ((TranslatableTextContentAccessor) newContent).invokeForEachPart(converted, builder::add);
      translations = builder.build();
      ci.cancel();
    }
  }
}
