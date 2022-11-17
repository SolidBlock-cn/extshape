package pers.solid.extshape.rrp;

import com.google.common.collect.ImmutableList;
import net.devtech.arrp.api.RRPEventHelper;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.generator.BlockResourceGenerator;
import net.minecraft.block.Block;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
   * 仅适用于客户端的资源包。
   *
   * @see ExtShapeRRP#STANDARD_PACK
   */
  @OnlyIn(Dist.CLIENT)
  public static final RuntimeResourcePack CLIENT_PACK = RuntimeResourcePack.create(new Identifier(ExtShape.MOD_ID, "client"));
  /**
   * 适用于整个模组的运行时资源包，服务端和客户端都会运行。之所以称为“standard”而非“server”，是因为即使在客户端启动时，该资源包也会存在。
   *
   * @see ExtShapeRRP#CLIENT_PACK
   */
  public static final RuntimeResourcePack STANDARD_PACK = RuntimeResourcePack.create(new Identifier(ExtShape.MOD_ID, "standard"));

  /**
   * 是否在每次重新加载资源时都生成一次资源。<ul>
   * <li>若为 <code>false</code>，则游戏初始化后就会将所有的资源生成好。</li>
   * <li>若为 <code>true</code>，则每次加载资源包时（例如 F3 + T）生成一次资源包，每次加载数据包时（例如 {@code /reload}）重新生成一次数据包。</li>
   * </ul>
   * 通常建议此项在开发环境时为 <code>true</code>，在发布时为 <code>false</code>。
   */
  private static final boolean GENERATE_EACH_RELOAD = !FMLEnvironment.production;

  private static final Logger LOGGER = LoggerFactory.getLogger("Extended Block Shapes-Runtime resource pack");

  /**
   * 注册所有的运行时资源。
   */
  public static void registerRRP() {
    if (!GENERATE_EACH_RELOAD) {
      generateServerData(STANDARD_PACK);
      if (FMLEnvironment.dist == Dist.CLIENT) {
        generateClientResources(CLIENT_PACK);
      }
    }
    RRPEventHelper.BEFORE_VANILLA.registerPacks((resourceType) -> {
      final ImmutableList.Builder<RuntimeResourcePack> builder = ImmutableList.builder();
      if (resourceType == ResourceType.SERVER_DATA || resourceType == null) {
        if (GENERATE_EACH_RELOAD) {
          STANDARD_PACK.clearResources(ResourceType.SERVER_DATA);
          generateServerData(STANDARD_PACK);
        }
        builder.add(STANDARD_PACK);
      }
      if (resourceType == ResourceType.CLIENT_RESOURCES || resourceType == null) {
        try {
          if (GENERATE_EACH_RELOAD) {
            CLIENT_PACK.clearResources(ResourceType.CLIENT_RESOURCES);
            generateClientResources(CLIENT_PACK);
          }
          builder.add(CLIENT_PACK);
        } catch (NoSuchFieldError throwable) {
          throw new AssertionError(throwable);
        }
      }
      return builder.build();
    });
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
      new CrossShapeDataGeneration(baseBlock, ExtShape.MOD_ID, STANDARD_PACK).generateCrossShapeData();
    }

    // 添加方块标签。
    ExtShapeTags.writeAllTags();
  }
}
