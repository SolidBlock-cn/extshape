package pers.solid.extshape.mixin;

import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Consumer;

@Mixin(TranslatableContents.class)
public interface TranslatableTextContentAccessor {
  @Invoker
  void invokeDecomposeTemplate(String translation, Consumer<FormattedText> partsConsumer);
}
