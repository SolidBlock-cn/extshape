package pers.solid.extshape.blockus;

import com.brand.blockus.Blockus;
import com.brand.blockus.content.BlockusBlocks;
import net.devtech.arrp.api.RRPEventHelper;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.generator.BlockResourceGenerator;
import net.devtech.arrp.generator.TextureRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.data.client.TextureKey;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.rrp.CrossShapeDataGeneration;
import pers.solid.extshape.rrp.ExtShapeRRP;
import pers.solid.extshape.rrp.UnusualLootTables;

/**
 * @see ExtShapeRRP
 */
public final class ExtShapeBlockusRRP {
  public static final Logger LOGGER = LoggerFactory.getLogger(ExtShapeBlockusRRP.class);
  @Environment(EnvType.CLIENT)
  public static final RuntimeResourcePack EXTSHAPE_CLIENT_PACK = RuntimeResourcePack.create(new Identifier(ExtShapeBlockus.NAMESPACE, "client"));
  public static final RuntimeResourcePack EXTSHAPE_STANDARD_PACK = RuntimeResourcePack.create(new Identifier(ExtShapeBlockus.NAMESPACE, "standard"));

  private ExtShapeBlockusRRP() {
  }

  public static void registerRRP() {
    generateServerData(EXTSHAPE_STANDARD_PACK);
    RRPEventHelper.BEFORE_VANILLA.registerSidedPack(ResourceType.SERVER_DATA, EXTSHAPE_STANDARD_PACK);
    if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
      generateClientResources(EXTSHAPE_CLIENT_PACK);
      RRPEventHelper.BEFORE_VANILLA.registerSidedPack(ResourceType.CLIENT_RESOURCES, EXTSHAPE_CLIENT_PACK);
    }
  }

  /**
   * 为运行时资源包添加客户端资源。该方法执行时不会清除已生成的资源（如有），因此调用之前您可能需要先自行清除一次。
   *
   * @param pack 运行时资源包。
   */
  @Environment(EnvType.CLIENT)
  public static void generateClientResources(RuntimeResourcePack pack) {
    LOGGER.info("Generating client resources for Extended Block Shapes mod!");

    // 注册纹理变量。
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ROUGH_BASALT.block, block -> TextureRegistry.register(block, new Identifier("block/basalt_top")));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ROUGH_SANDSTONE.block, block -> TextureRegistry.register(block, new Identifier("block/sandstone_bottom")));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ROUGH_RED_SANDSTONE.block, block -> TextureRegistry.register(block, new Identifier("block/red_sandstone_bottom")));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.ROUGH_SOUL_SANDSTONE.block, block -> TextureRegistry.register(block, new Identifier(Blockus.MOD_ID, "block/soul_sandstone_bottom")));

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.STRIPPED_WHITE_OAK_LOG, block -> TextureRegistry.registerAppended(block, TextureKey.END, "_top"));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.STRIPPED_WHITE_OAK_WOOD, block -> TextureRegistry.register(block, new Identifier(Blockus.MOD_ID, "block/stripped_white_oak_log")));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.WHITE_OAK_LOG, block -> TextureRegistry.registerAppended(block, TextureKey.END, "_top"));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.WHITE_OAK_WOOD, block -> TextureRegistry.register(block, new Identifier(Blockus.MOD_ID, "block/white_oak_log")));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMOOTH_SOUL_SANDSTONE, bssTypes -> TextureRegistry.register(bssTypes.block, new Identifier(Blockus.MOD_ID, "block/soul_sandstone_top")));

    // 为方块添加资源。
    for (Block block : ExtShapeBlockusBlocks.BLOCKUS_BLOCKS) {
      if (!(block instanceof BlockResourceGenerator generator)) continue;
      generator.writeAssets(pack);
    }
  }

  /**
   * 为运行时资源包添加服务器数据。该方法执行时不会清除已经生成的数据（若有），因此调用之前您可能需要先自行清除一次。
   *
   * @param pack 运行时数据包。
   */
  public static void generateServerData(RuntimeResourcePack pack) {
    LOGGER.info("Generating server data for Extended Block Shapes - Blockus mod!");

    // 为方块添加数据。
    for (Block block : ExtShapeBlockusBlocks.BLOCKUS_BLOCKS) {
      if (!(block instanceof BlockResourceGenerator generator)) continue;
      generator.writeRecipes(pack);
      final Block baseBlock = generator.getBaseBlock();
      final UnusualLootTables.LootTableFunction lootTableFunction = ExtShapeUnusualLootTables.INSTANCE.get(baseBlock);
      if (lootTableFunction != null) {
        pack.addLootTable(generator.getLootTableId(), lootTableFunction.apply(baseBlock, BlockShape.getShapeOf(block), block));
      } else {
        generator.writeLootTable(pack);
      }
    }

    for (Block baseBlock : ExtShapeBlockusBlocks.BLOCKUS_BASE_BLOCKS) {
      new CrossShapeDataGeneration(baseBlock, ExtShapeBlockus.NAMESPACE, pack).generateCrossShapeData();
    }

    ExtShapeBlockusTags.writeAllTags();
  }
}
