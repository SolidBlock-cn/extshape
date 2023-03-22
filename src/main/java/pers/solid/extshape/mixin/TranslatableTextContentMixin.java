package pers.solid.extshape.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Language;
import org.apache.commons.lang3.ArrayUtils;
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

  @Inject(method = "updateTranslations", at = @At(value = "INVOKE", target = "Ljava/util/Objects;requireNonNull(Ljava/lang/Object;)Ljava/lang/Object;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
  public void modify(CallbackInfo ci, Language language, String string, ImmutableList.Builder<StringVisitable> builder) {
    if (key.equals(AttributiveBlockNameManager.ATTRIBUTIVE_KEY)) {
      final String newKey = (String) args[0];
      final String converted = AttributiveBlockNameManager.convertToAttributive(language.get(newKey), language);
      final Object[] newArgs = ArrayUtils.remove(args, 0);
      final TranslatableTextContent newContent = new TranslatableTextContent(newKey, newArgs);
      ((TranslatableTextContentAccessor) newContent).invokeForEachPart(converted, builder::add);
      translations = builder.build();
      ci.cancel();
    }
  }
}
