package pers.solid.extshape;

import com.google.common.base.Preconditions;
import com.google.common.base.Suppliers;
import com.google.common.collect.Streams;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemUsageContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.config.ExtShapeOptionsScreen;
import pers.solid.extshape.rrp.ExtShapeRRP;
import pers.solid.extshape.rs.ExtShapeBridgeImplementation;
import pers.solid.extshape.tag.ExtShapeTags;
import pers.solid.extshape.util.BlockBiMaps;
import pers.solid.extshape.util.BlockCollections;
import pers.solid.mod.forge.ExtShapeBridgeImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * <p>欢迎使用扩展方块形状模组。本模组为许多方块提供了各个形状的变种，包括原版不存在的形状。
 * <p>本模组中的所有方块是在 {@link ExtShapeBlocks} 中创建的，创建的同时将其注册，并创建和注册对应的方块物品。物品组由 {@link ExtShapeItemGroup} 提供。本模组还提供了一定的配置功能，参见 {@link ExtShapeConfig}。
 * <p>本模组还有一个内置的方块映射管理系统，由 {@link BlockBiMaps} 提供。方块映射是指的方块与方块之间的关系。本模组的方块被创建时，就会自动加入映射中。此外，原版的方块映射也会加入。可以利用 {@link BlockBiMaps#getBlockOf} 来获取特定方块的特定形状的变种。
 * <p>本模组的方块和物品所使用的资源，包括客户端资源和服务器数据，绝大多数都是在运行时生成的，并不会保存为本地文件，这是依赖的 Better Runtime Resource Pack（BRRP）模组。
 * <hr>
 * <p>Welcome to use Extended Block Shapes mod, which provides various variants in different shapes of many blocks, including shapes that do not exist in vanilla Minecraft.
 * <p>Blocks of this mod are created in {@link ExtShapeBlocks}; while created, they are also registered, and so as their corresponding block items. Item groups are provided in {@link ExtShapeItemGroup}. This mod also provides a simple configuration. See {@link ExtShapeConfig}.
 * <p>This mod contains an internal block mapping management, provided by {@link BlockBiMaps}。Block mapping means the relations between blocks. Block in this mod are added instantly to the mappings upon created. Besides, vanilla block mappings are also added. You may get the specified variant of a specified block by {@link BlockBiMaps#getBlockOf}.
 * <p>Resources used by this mod, including client assets and server data, are generated on runtime, without saving local files, which relies on Better Runtime Resource Pack (BRRP) mod.
 *
 * @author SolidBlock
 */
@Mod(ExtShape.MOD_ID)
public class ExtShape {
  public static final String MOD_ID = "extshape";
  public static final Logger LOGGER = LoggerFactory.getLogger("Extended Block Shapes");

  public ExtShape() {
    ExtShapeConfig.init();
    MinecraftForge.EVENT_BUS.addListener(ExtShape::registerCommand);
    FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, ExtShape::register);
    if (ModList.get().isLoaded("reasonable_sorting")) {
      try {
        ExtShapeBridgeImpl.setValue(ExtShapeBridgeImplementation::new);
      } catch (LinkageError e) {
        LOGGER.info("Unexpected expected error was thrown when loading between Extended Block Shapes and Reasonable Sorting mod", e);
      }
    }
    FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Block.class, ExtShape::initializeBridge);
    MinecraftForge.EVENT_BUS.addListener(ExtShape::registerFuels);
    DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ExtShape::registerConfig);
  }

  @OnlyIn(Dist.CLIENT)
  private static void registerConfig() {
    ModLoadingContext.get().getActiveContainer().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class, Suppliers.ofInstance(new ConfigGuiHandler.ConfigGuiFactory((client, screen) -> new ExtShapeOptionsScreen(screen))));
  }

  public static void register(RegistryEvent.Register<Block> event) {
    ExtShapeBlocks.init();
    ExtShapeItemGroup.init();
    ExtShapeTags.refillTags();
    registerComposingChances();
    registerStrippableBlocks();

    ExtShapeRRP.registerRRP();
  }

  public static void registerCommand(RegisterCommandsEvent event) {
    RecipeConflict.registerCommand(event.getDispatcher());
  }

  /**
   * 可通过斧去皮的方块。
   */
  public static final Map<Block, Block> EXTENDED_STRIPPABLE_BLOCKS = new HashMap<>();

  /**
   * 注册所有可去皮的方块。考虑到存在复杂的方块状态的情况，故使用 {@link pers.solid.extshape.block.ExtShapeBlockInterface#getToolModifiedState(BlockState, ItemUsageContext, ToolAction, boolean)}。
   */
  private static void registerStrippableBlocks() {
    Streams.concat(
        IntStream.range(0, BlockCollections.LOGS.size()).mapToObj(i -> Pair.of(BlockCollections.LOGS.get(i), BlockCollections.STRIPPED_LOGS.get(i))),
        IntStream.range(0, BlockCollections.WOODS.size()).mapToObj(i -> Pair.of(BlockCollections.WOODS.get(i), BlockCollections.STRIPPED_WOODS.get(i))),
        IntStream.range(0, BlockCollections.HYPHAES.size()).mapToObj(i -> Pair.of(BlockCollections.HYPHAES.get(i), BlockCollections.STRIPPED_HYPHAES.get(i))),
        IntStream.range(0, BlockCollections.STEMS.size()).mapToObj(i -> Pair.of(BlockCollections.STEMS.get(i), BlockCollections.STRIPPED_STEMS.get(i)))
    ).forEach(pair -> {
      final Block inputBase = pair.getFirst();
      final Block outputBase = pair.getSecond();
      for (BlockShape shape : BlockShape.values()) {
        final Block input = BlockBiMaps.getBlockOf(shape, inputBase);
        final Block output = BlockBiMaps.getBlockOf(shape, outputBase);
        if (input != null && output != null) {
          EXTENDED_STRIPPABLE_BLOCKS.put(input, output);
        }
      }
    });
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
      map.put(Preconditions.checkNotNull(BlockBiMaps.getBlockOf(BlockShape.STAIRS, compostableBlock)).asItem(), 0.65f);
      map.put(Preconditions.checkNotNull(BlockBiMaps.getBlockOf(BlockShape.SLAB, compostableBlock)).asItem(), 0.325f);
      map.put(Preconditions.checkNotNull(BlockBiMaps.getBlockOf(BlockShape.VERTICAL_STAIRS, compostableBlock)).asItem(), 0.65f);
      map.put(Preconditions.checkNotNull(BlockBiMaps.getBlockOf(BlockShape.VERTICAL_SLAB, compostableBlock)).asItem(), 0.325f);
      map.put(Preconditions.checkNotNull(BlockBiMaps.getBlockOf(BlockShape.QUARTER_PIECE, compostableBlock)).asItem(), 0.15625f);
      map.put(Preconditions.checkNotNull(BlockBiMaps.getBlockOf(BlockShape.VERTICAL_QUARTER_PIECE, compostableBlock)).asItem(), 0.15625f);
      map.put(Preconditions.checkNotNull(BlockBiMaps.getBlockOf(BlockShape.FENCE, compostableBlock)).asItem(), 0.65f);
      map.put(Preconditions.checkNotNull(BlockBiMaps.getBlockOf(BlockShape.FENCE_GATE, compostableBlock)).asItem(), 0.65f);
      map.put(Preconditions.checkNotNull(BlockBiMaps.getBlockOf(BlockShape.WALL, compostableBlock)).asItem(), 0.65f);
      map.put(Preconditions.checkNotNull(BlockBiMaps.getBlockOf(BlockShape.BUTTON, compostableBlock)).asItem(), 0.2f);
      map.put(Preconditions.checkNotNull(BlockBiMaps.getBlockOf(BlockShape.PRESSURE_PLATE, compostableBlock)).asItem(), 0.2f);
    }
    // 原版的下界疣和诡异疣的堆肥概率为 0.9。
    for (final Block compostableBlock : new Block[]{
        Blocks.WARPED_WART_BLOCK, Blocks.NETHER_WART_BLOCK
    }) {
      map.put(Preconditions.checkNotNull(BlockBiMaps.getBlockOf(BlockShape.STAIRS, compostableBlock)).asItem(), 0.8f);
      map.put(Preconditions.checkNotNull(BlockBiMaps.getBlockOf(BlockShape.SLAB, compostableBlock)).asItem(), 0.4f);
      map.put(Preconditions.checkNotNull(BlockBiMaps.getBlockOf(BlockShape.VERTICAL_STAIRS, compostableBlock)).asItem(), 0.8f);
      map.put(Preconditions.checkNotNull(BlockBiMaps.getBlockOf(BlockShape.VERTICAL_SLAB, compostableBlock)).asItem(), 0.4f);
      map.put(Preconditions.checkNotNull(BlockBiMaps.getBlockOf(BlockShape.QUARTER_PIECE, compostableBlock)).asItem(), 0.2f);
      map.put(Preconditions.checkNotNull(BlockBiMaps.getBlockOf(BlockShape.VERTICAL_QUARTER_PIECE, compostableBlock)).asItem(), 0.2f);
      map.put(Preconditions.checkNotNull(BlockBiMaps.getBlockOf(BlockShape.FENCE, compostableBlock)).asItem(), 0.8f);
      map.put(Preconditions.checkNotNull(BlockBiMaps.getBlockOf(BlockShape.FENCE_GATE, compostableBlock)).asItem(), 0.8f);
      map.put(Preconditions.checkNotNull(BlockBiMaps.getBlockOf(BlockShape.WALL, compostableBlock)).asItem(), 0.8f);
    }
  }

  /**
   * 在初始化时，注册所有的燃料。注意：对于 Forge 版本，物品的燃烧由 {@code IForgeItem} 的相关接口决定。
   *
   * @see ExtShapeBlocks
   * @see net.minecraft.block.entity.AbstractFurnaceBlockEntity#createFuelTimeMap()
   */
  @ApiStatus.AvailableSince("1.5.0")
  private static void registerFuels(FurnaceFuelBurnTimeEvent event) {
    // 参照原版木制（含下界木）楼梯和台阶，楼梯燃烧时间为 300 刻，台阶燃烧时间为 150 刻。
    // 但是，non_flammable_wood 标签的仍然不会被熔炉接受。
    if (event.getItemStack().isIn(ExtShapeTags.WOODEN_VERTICAL_STAIRS.toVanillaItemTag())) event.setBurnTime(300);
    if (event.getItemStack().isIn(ExtShapeTags.WOODEN_VERTICAL_SLABS.toVanillaItemTag())) event.setBurnTime(150);
    if (event.getItemStack().isIn(ExtShapeTags.WOODEN_QUARTER_PIECES.toVanillaItemTag())) event.setBurnTime(75);
    if (event.getItemStack().isIn(ExtShapeTags.WOODEN_VERTICAL_QUARTER_PIECES.toVanillaItemTag())) event.setBurnTime(75);
    if (event.getItemStack().isIn(ExtShapeTags.WOODEN_WALLS.toVanillaItemTag())) event.setBurnTime(300);
    if (event.getItemStack().isIn(ExtShapeTags.LOG_STAIRS.toVanillaItemTag())) event.setBurnTime(300);
    if (event.getItemStack().isIn(ExtShapeTags.LOG_SLABS.toVanillaItemTag())) event.setBurnTime(150);
    if (event.getItemStack().isIn(ExtShapeTags.LOG_VERTICAL_STAIRS.toVanillaItemTag())) event.setBurnTime(300);
    if (event.getItemStack().isIn(ExtShapeTags.LOG_VERTICAL_SLABS.toVanillaItemTag())) event.setBurnTime(150);
    if (event.getItemStack().isIn(ExtShapeTags.LOG_QUARTER_PIECES.toVanillaItemTag())) event.setBurnTime(75);
    if (event.getItemStack().isIn(ExtShapeTags.LOG_VERTICAL_QUARTER_PIECES.toVanillaItemTag())) event.setBurnTime(75);
    if (event.getItemStack().isIn(ExtShapeTags.LOG_WALLS.toVanillaItemTag())) event.setBurnTime(300);
    if (event.getItemStack().isIn(ExtShapeTags.LOG_FENCES.toVanillaItemTag())) event.setBurnTime(300);
    if (event.getItemStack().isIn(ExtShapeTags.LOG_FENCE_GATES.toVanillaItemTag())) event.setBurnTime(300);
    if (event.getItemStack().isIn(ExtShapeTags.LOG_BUTTONS.toVanillaItemTag())) event.setBurnTime(100);
    if (event.getItemStack().isIn(ExtShapeTags.LOG_PRESSURE_PLATES.toVanillaItemTag())) event.setBurnTime(300);

    // 参照原版羊毛燃烧时间为 100 刻，楼梯燃烧时间和基础方块相同，台阶燃烧时间为一半。
    if (event.getItemStack().isIn(ExtShapeTags.WOOLEN_STAIRS.toVanillaItemTag())) event.setBurnTime(100);
    if (event.getItemStack().isIn(ExtShapeTags.WOOLEN_SLABS.toVanillaItemTag())) event.setBurnTime(50);
    if (event.getItemStack().isIn(ExtShapeTags.WOOLEN_QUARTER_PIECES.toVanillaItemTag())) event.setBurnTime(25);
    if (event.getItemStack().isIn(ExtShapeTags.WOOLEN_VERTICAL_STAIRS.toVanillaItemTag())) event.setBurnTime(100);
    if (event.getItemStack().isIn(ExtShapeTags.WOOLEN_VERTICAL_SLABS.toVanillaItemTag())) event.setBurnTime(50);
    if (event.getItemStack().isIn(ExtShapeTags.WOOLEN_VERTICAL_QUARTER_PIECES.toVanillaItemTag())) event.setBurnTime(25);
    // 栅栏、栅栏门、压力板、燃烧时间和基础方块一致，门的燃烧时间为三分之二，按钮为三分之一。
    // 但考虑到羊毛压力板是与地毯相互合成的，故燃烧时间与地毯一致，为 67。
    if (event.getItemStack().isIn(ExtShapeTags.WOOLEN_FENCES.toVanillaItemTag())) event.setBurnTime(100);
    if (event.getItemStack().isIn(ExtShapeTags.WOOLEN_FENCE_GATES.toVanillaItemTag())) event.setBurnTime(100);
    if (event.getItemStack().isIn(ExtShapeTags.WOOLEN_PRESSURE_PLATES.toVanillaItemTag())) event.setBurnTime(67);
    if (event.getItemStack().isIn(ExtShapeTags.WOOLEN_BUTTONS.toVanillaItemTag())) event.setBurnTime(33);
    if (event.getItemStack().isIn(ExtShapeTags.WOOLEN_WALLS.toVanillaItemTag())) event.setBurnTime(100);
  }

  private static void initializeBridge(RegistryEvent.Register<Block> event) {
    if (ModList.get().isLoaded("reasonable_sorting")) {
      try {
        ExtShapeBridgeImplementation.initialize();
      } catch (LinkageError e) {
        LOGGER.info("An error ({}) was thrown when initializing Reasonable Sorting Mod with Extended Block Shapes mod. This is expected.", e.getClass().getSimpleName());
      } catch (Throwable e) {
        LOGGER.warn("Failed to call ExtShapeBridgeImpl.initialize():", e);
      }
    }
  }
}
