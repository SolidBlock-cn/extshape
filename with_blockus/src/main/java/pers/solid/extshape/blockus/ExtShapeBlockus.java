package pers.solid.extshape.blockus;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ExtShapeBlockus implements ModInitializer {
  public static final String NAMESPACE = "extshape_blockus";
  public static final Logger LOGGER = LoggerFactory.getLogger("Extended Block Shapes for Blockus");

  public static <T> void tryConsume(Supplier<T> supplier, Consumer<T> consumer) {
    final T t;
    try {
      t = supplier.get();
    } catch (Throwable throwable) {
      return;
    }
    consumer.accept(t);
  }

  public static <L, R> R tryTransform(Supplier<L> supplier, Function<L, R> transformer, Supplier<R> defaultSupplier) {
    final L left;
    try {
      left = supplier.get();
    } catch (Throwable throwable) {
      return defaultSupplier.get();
    }
    return transformer.apply(left);
  }

  @Override
  public void onInitialize() {
    if (FabricLoader.getInstance().isModLoaded("blockus")) {
      LOGGER.info("Blockus mod loaded. Extended Block Shapes mod is trying to apply it.");
      ExtShapeBlockusBlocks.init();
      ExtShapeBlockusRRP.registerRRP();
      ExtShapeBlockusItemGroup.registerEvent();
    }
  }
}
