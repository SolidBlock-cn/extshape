package pers.solid.extshape.blockus;

import com.brand.blockus.content.BlockusBlocks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.util.BlockBiMaps;

public class ExtShapeBlockus implements ModInitializer {
  public static final String NAMESPACE = "extshape_blockus";
  public static final Logger LOGGER = LoggerFactory.getLogger("Extended Block Shapes for Blockus");

  @Override
  public void onInitialize() {
    if (FabricLoader.getInstance().isModLoaded("blockus")) {
      LOGGER.info("Blockus mod loaded. Extended Block Shapes mod is trying to apply it.");
      ExtShapeBlockusBlocks.init();
      ExtShapeBlockusRRP.registerRRP();
      registerStrippableBlocks();
    }
  }

  private static void registerStrippableBlocks() {
    for (BlockShape shape : BlockShape.values()) {
      var block1 = BlockBiMaps.getBlockOf(shape, BlockusBlocks.WHITE_OAK_LOG);
      var block2 = BlockBiMaps.getBlockOf(shape, BlockusBlocks.STRIPPED_WHITE_OAK_LOG);
      if (block1 != null && block2 != null) {
        ExtShape.EXTENDED_STRIPPABLE_BLOCKS.put(block1, block2);
      }
      var block3 = BlockBiMaps.getBlockOf(shape, BlockusBlocks.WHITE_OAK_WOOD);
      var block4 = BlockBiMaps.getBlockOf(shape, BlockusBlocks.STRIPPED_WHITE_OAK_WOOD);
      if (block3 != null && block4 != null) {
        ExtShape.EXTENDED_STRIPPABLE_BLOCKS.put(block3, block4);
      }
    }
  }
}
