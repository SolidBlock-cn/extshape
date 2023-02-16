package pers.solid.extshape.rrp;

import net.minecraft.block.Block;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus;
import pers.solid.brrp.v1.RRPEventHelper;
import pers.solid.brrp.v1.api.RuntimeResourcePack;
import pers.solid.brrp.v1.generator.BlockResourceGenerator;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.tag.ExtShapeTags;

/**
 * 本类包含所有有关运行时资源包（runtime resource pack）的代码。<p>
 * 从 1.5.0 开始，扩展方块形状模组开始依赖 BRRP（更好的运行时资源包）。
 */
@ApiStatus.Internal
public final class ExtShapeRRP {
  private ExtShapeRRP() {
  }

  /**
   * 适用于整个模组的运行时资源包，服务端和客户端都会运行。
   */
  public static final RuntimeResourcePack PACK = RuntimeResourcePack.create(new Identifier(ExtShape.MOD_ID, "pack"));

  private static final Logger LOGGER = LogManager.getLogger("Extended Block Shapes-Runtime resource pack");

  /**
   * 注册所有的运行时资源。
   */
  public static void registerRRP() {
    generateServerData(PACK);
    PACK.setSidedRegenerationCallback(ResourceType.SERVER_DATA, () -> {
      PACK.clearResources(ResourceType.SERVER_DATA);
      generateServerData(PACK);
    });
    if (FMLEnvironment.dist == Dist.CLIENT) {
      generateClientResources(PACK);
      PACK.setSidedRegenerationCallback(ResourceType.CLIENT_RESOURCES, () -> {
        PACK.clearResources(ResourceType.CLIENT_RESOURCES);
        generateClientResources(PACK);
      });
    }

    RRPEventHelper.BEFORE_VANILLA.registerPack(PACK);
  }

  /**
   * 为运行时资源包添加客户端资源。该方法执行时不会清除已生成的资源（如有），因此调用之前您可能需要先自行清除一次。
   *
   * @param pack 运行时资源包。
   */
  @OnlyIn(Dist.CLIENT)
  public static void generateClientResources(RuntimeResourcePack pack) {
    LOGGER.info("Generating client resources for Extended Block Shapes mod!");

    // 注册纹理变量。
    VanillaTextures.registerTextures();

    // 为方块添加资源。
    for (Block block : ExtShapeBlocks.getBlocks()) {
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
    LOGGER.info("Generating server data for Extended Block Shapes mod!");

    // 为方块添加数据。
    for (Block block : ExtShapeBlocks.getBlocks()) {
      if (!(block instanceof BlockResourceGenerator generator)) continue;
      generator.writeRecipes(pack);
      final Block baseBlock = generator.getBaseBlock();
      final UnusualLootTables.LootTableFunction lootTableFunction = UnusualLootTables.INSTANCE.get(baseBlock);
      if (lootTableFunction != null) {
        pack.addLootTable(generator.getLootTableId(), lootTableFunction.apply(baseBlock, BlockShape.getShapeOf(block), block));
      } else {
        generator.writeLootTable(pack);
      }
    }

    for (Block baseBlock : ExtShapeBlocks.getBaseBlocks()) {
      new CrossShapeDataGeneration(baseBlock, ExtShape.MOD_ID, pack).generateCrossShapeData();
    }

    // 添加方块标签。
    ExtShapeTags.TAG_PREPARATIONS.write(PACK);
  }
}
