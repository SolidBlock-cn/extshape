package pers.solid.extshape.blockus;

import com.brand.blockus.Blockus;
import com.brand.blockus.content.BlockusBlocks;
import com.brand.blockus.tags.BlockusBlockTags;
import com.brand.blockus.tags.BlockusItemTags;
import com.google.common.collect.ImmutableList;
import net.devtech.arrp.api.RRPEventHelper;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.generator.BlockResourceGenerator;
import net.devtech.arrp.generator.TextureRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.data.client.TextureKey;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.rrp.CrossShapeDataGeneration;
import pers.solid.extshape.rrp.ExtShapeRRP;
import pers.solid.extshape.rrp.UnusualLootTables;
import pers.solid.extshape.tag.ExtShapeTags;

import java.util.function.Supplier;

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

    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SOUL_SANDSTONE, bsswTypes -> TextureRegistry.registerAppended(bsswTypes.block, TextureKey.TOP, "_top"));
    ExtShapeBlockus.tryConsume(() -> BlockusBlocks.SMOOTH_SOUL_SANDSTONE, bssTypes -> TextureRegistry.register(bssTypes.block, new Identifier(Blockus.MOD_ID, "block/soul_sandstone_top")));

    for (Supplier<Block> supplier : BlockusBlockCollections.GLAZED_TERRACOTTA_PILLARS) {
      ExtShapeBlockus.tryConsume(supplier, block -> TextureRegistry.registerAppended(block, TextureKey.END, "_top"));
    }

    for (final Supplier<Block> supplier : ImmutableList.<Supplier<Block>>of(
        () -> BlockusBlocks.OAK_SMALL_LOGS,
        () -> BlockusBlocks.SPRUCE_SMALL_LOGS,
        () -> BlockusBlocks.BIRCH_SMALL_LOGS,
        () -> BlockusBlocks.JUNGLE_SMALL_LOGS,
        () -> BlockusBlocks.ACACIA_SMALL_LOGS,
        () -> BlockusBlocks.DARK_OAK_SMALL_LOGS,
        () -> BlockusBlocks.MANGROVE_SMALL_LOGS,
        () -> BlockusBlocks.WARPED_SMALL_STEMS,
        () -> BlockusBlocks.CRIMSON_SMALL_STEMS,
        () -> BlockusBlocks.WHITE_OAK_SMALL_LOGS
    )) {
      ExtShapeBlockus.tryConsume(supplier, block -> TextureRegistry.registerAppended(block, TextureKey.END, "_top"));
    }

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

    // 方块和物品标签
    ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS.setBlockTagWithItem(BlockTags.STAIRS, ItemTags.STAIRS);
    ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS.setBlockTagWithItem(BlockTags.SLABS, ItemTags.SLABS);
    ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS.setBlockTagWithItem(BlockTags.FENCES, ItemTags.FENCES);
    ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS.setBlockTagWithItem(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
    ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS.setBlockTagWithItem(BlockTags.WALLS, ItemTags.WALLS);
    ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS.setBlockTagWithItem(BlockTags.BUTTONS, ItemTags.BUTTONS);
    ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS.forceSetBlockTagWithItem(ExtShapeTags.VERTICAL_SLABS);
    ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS.forceSetBlockTagWithItem(ExtShapeTags.QUARTER_PIECES);
    ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS.forceSetBlockTagWithItem(ExtShapeTags.VERTICAL_STAIRS);
    ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS.forceSetBlockTagWithItem(ExtShapeTags.VERTICAL_QUARTER_PIECES);

    ExtShapeTags.SHAPE_TO_LOG_TAG.values().forEach(ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS::forceSetBlockTagWithItem);
    ExtShapeTags.SHAPE_TO_WOODEN_TAG.values().forEach(ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS::forceSetBlockTagWithItem);

    ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS.setBlockTagWithItem(BlockusBlockTags.ALL_PATTERNED_WOOLS, BlockusItemTags.ALL_PATTERNED_WOOLS);
    ExtShapeBlockusTags.EXTSHAPE_TAG_PREPARATIONS.write(EXTSHAPE_STANDARD_PACK);
  }
}
