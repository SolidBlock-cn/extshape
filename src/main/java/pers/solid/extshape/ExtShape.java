package pers.solid.extshape;

import com.google.common.base.Preconditions;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.rs.ExtShapeBridgeImplementation;
import pers.solid.extshape.tag.ExtShapeBlockTags;
import pers.solid.mod.forge.ExtShapeBridgeImpl;

/**
 * <p>欢迎使用扩展方块形状模组。本模组为许多方块提供了各个形状的变种，包括原版不存在的形状。
 * <p>本模组中的所有方块是在 {@link ExtShapeBlocks} 中创建的，创建的同时将其注册，并创建和注册对应的方块物品。物品组由 {@link ExtShapeItemGroup} 提供。本模组还提供了一定的配置功能，参见 {@link ExtShapeConfig}。
 * <p>本模组还有一个内置的方块映射管理系统，由 {@link BlockMappings} 提供。方块映射是指的方块与方块之间的关系。本模组的方块被创建时，就会自动加入映射中。此外，原版的方块映射也会加入。可以利用 {@link BlockMappings#getBlockOf} 来获取特定方块的特定形状的变种。
 * <p>本模组的方块和物品所使用的资源，包括客户端资源和服务器数据，绝大多数都是在运行时生成的，并不会保存为本地文件，这是依赖的 Better Runtime Resource Pack（BRRP）模组。
 * <hr>
 * <p>Welcome to use Extended Block Shapes mod, which provides various variants in different shapes of many blocks, including shapes that do not exist in vanilla Minecraft.
 * <p>Blocks of this mod are created in {@link ExtShapeBlocks}; while created, they are also registered, and so as their corresponding block items. Item groups are provided in {@link ExtShapeItemGroup}. This mod also provides a simple configuration. See {@link ExtShapeConfig}.
 * <p>This mod contains an internal block mapping management, provided by {@link BlockMappings}。Block mapping means the relations between blocks. Block in this mod are added instantly to the mappings upon created. Besides, vanilla block mappings are also added. You may get the specified variant of a specified block by {@link BlockMappings#getBlockOf}.
 * <p>Resources used by this mod, including client assets and server data, are generated on runtime, without saving local files, which relies on Better Runtime Resource Pack (BRRP) mod.
 *
 * @author SolidBlock
 */
@Mod(ExtShape.MOD_ID)
public class ExtShape {
  public static final String MOD_ID = "extshape";
  public static final Logger LOGGER = LoggerFactory.getLogger(ExtShape.class);

  public ExtShape() {
    ExtShapeConfig.init();
    MinecraftForge.EVENT_BUS.addListener(ExtShape::registerCommand);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(ExtShape::register);
    if (ModList.get().isLoaded("reasonable_sorting")) {
      try {
        ExtShapeBridgeImpl.setValue(ExtShapeBridgeImplementation::new);
      } catch (LinkageError e) {
        LOGGER.info("Unexpected expected error was thrown when loading between Extended Block Shapes and Reasonable Sorting mod", e);
      }
    }
    FMLJavaModLoadingContext.get().getModEventBus().addListener(ExtShape::initializeBridge);
  }

  public static void register(RegisterEvent event) {
    event.register(ForgeRegistries.Keys.BLOCKS, helper -> {
      ExtShapeBlocks.init();
      ExtShapeItemGroup.init();
      ExtShapeBlockTags.refillTags();
      registerComposingChances();
      ExtShapeRRP.registerRRP();
    });
  }

  public static void registerCommand(RegisterCommandsEvent event) {
    RecipeConflict.registerCommand(event.getDispatcher());
  }

  /**
   * 注册所有的可堆肥方块。注意：对于 Forge 版本，是直接修改的 {@link ComposterBlock#ITEM_TO_LEVEL_INCREASE_CHANCE}。
   *
   * @see ComposterBlock#ITEM_TO_LEVEL_INCREASE_CHANCE
   * @see ComposterBlock#registerDefaultCompostableItems()
   */
  private static void registerComposingChances() {
    // 原版这些方块的堆肥概率为 0.65。
    final Object2FloatMap<ItemConvertible> map = ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE;
    for (final Block compostableBlock : new Block[]{
        Blocks.PUMPKIN, Blocks.MELON, Blocks.MOSS_BLOCK, Blocks.SHROOMLIGHT
    }) {
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(BlockShape.STAIRS, compostableBlock)).asItem(), 0.65f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(BlockShape.SLAB, compostableBlock)).asItem(), 0.325f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(BlockShape.VERTICAL_STAIRS, compostableBlock)).asItem(), 0.65f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(BlockShape.VERTICAL_SLAB, compostableBlock)).asItem(), 0.325f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(BlockShape.QUARTER_PIECE, compostableBlock)).asItem(), 0.15625f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(BlockShape.VERTICAL_QUARTER_PIECE, compostableBlock)).asItem(), 0.15625f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(BlockShape.FENCE, compostableBlock)).asItem(), 0.65f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(BlockShape.FENCE_GATE, compostableBlock)).asItem(), 0.65f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(BlockShape.WALL, compostableBlock)).asItem(), 0.65f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(BlockShape.BUTTON, compostableBlock)).asItem(), 0.2f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(BlockShape.PRESSURE_PLATE, compostableBlock)).asItem(), 0.2f);
    }
    // 原版的下界疣和诡异疣的堆肥概率为 0.9。
    for (final Block compostableBlock : new Block[]{
        Blocks.WARPED_WART_BLOCK, Blocks.NETHER_WART_BLOCK
    }) {
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(BlockShape.STAIRS, compostableBlock)).asItem(), 0.8f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(BlockShape.SLAB, compostableBlock)).asItem(), 0.4f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(BlockShape.VERTICAL_STAIRS, compostableBlock)).asItem(), 0.8f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(BlockShape.VERTICAL_SLAB, compostableBlock)).asItem(), 0.4f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(BlockShape.QUARTER_PIECE, compostableBlock)).asItem(), 0.2f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(BlockShape.VERTICAL_QUARTER_PIECE, compostableBlock)).asItem(), 0.2f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(BlockShape.FENCE, compostableBlock)).asItem(), 0.8f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(BlockShape.FENCE_GATE, compostableBlock)).asItem(), 0.8f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(BlockShape.WALL, compostableBlock)).asItem(), 0.8f);
    }
  }

  private static void initializeBridge(RegisterEvent event) {
    event.register(ForgeRegistries.Keys.ITEMS, helper -> {
      if (ModList.get().isLoaded("reasonable_sorting")) {
        try {
          ExtShapeBridgeImplementation.initialize();
        } catch (LinkageError e) {
          LOGGER.info("An error ({}) was thrown when initializing Reasonable Sorting Mod with Extended Block Shapes mod. This is expected.", e.getClass().getSimpleName());
        } catch (Throwable e) {
          LOGGER.warn("Failed to call ExtShapeBridgeImpl.initialize():", e);
        }
      }
    });
  }
}
