package pers.solid.extshape.mixin;

import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import pers.solid.extshape.util.AttributiveBlockNameManager;

import java.util.function.Consumer;

@Mixin(TranslatableContents.class)
public class TranslatableTextContentMixin {
  @Shadow
  @Final
  private String key;

  @Shadow
  @Final
  private Object[] args;

  @Shadow
  @Final
  @Nullable
  private String fallback;

  @WrapWithCondition(method = "decompose", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/contents/TranslatableContents;decomposeTemplate(Ljava/lang/String;Ljava/util/function/Consumer;)V"))
  public boolean modify(TranslatableContents instance, String translation, Consumer<FormattedText> partsConsumer, @Local ImmutableList.Builder<FormattedText> builder) {
    Language language = Language.getInstance();
    if (key.equals(AttributiveBlockNameManager.ATTRIBUTIVE_KEY)) {
      final String newKey = (String) args[0];
      final String converted = AttributiveBlockNameManager.convertToAttributive(language.getOrDefault(newKey), language);
      final Object[] newArgs = ArrayUtils.remove(args, 0);
      final TranslatableContents newContent = new TranslatableContents(newKey, fallback, newArgs);
      ((TranslatableTextContentAccessor) newContent).invokeDecomposeTemplate(converted, builder::add);
      return false;
    } else {
      return true;
    }
  }
}
