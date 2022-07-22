package pers.solid.extshape;

import com.google.common.base.Preconditions;
import com.google.common.base.Suppliers;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.config.ExtShapeOptionsScreen;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.rs.ExtShapeBridgeImplementation;
import pers.solid.extshape.tag.ExtShapeBlockTags;
import pers.solid.mod.forge.ExtShapeBridgeImpl;

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
    ModLoadingContext.get().getActiveContainer().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, Suppliers.ofInstance(new ConfigScreenHandler.ConfigScreenFactory((client, screen) -> new ExtShapeOptionsScreen(screen))));
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
    event.getDispatcher().register(LiteralArgumentBuilder.<ServerCommandSource>literal("extshape:check-conflict")
        .requires(source -> source.hasPermissionLevel(2))
        .executes(context -> {
          final ServerCommandSource source = context.getSource();
          final ServerWorld world = source.getWorld();
          final ServerPlayerEntity player = source.getPlayer();
          if (player == null) {
            source.sendFeedback(Text.translatable("argument.entity.notfound.player"), false);
            return 0;
          }
          return RecipeConflict.checkConflict(world.getRecipeManager(), world, player, text -> source.sendFeedback(text, true));
        }));

  }

  /**
   * 注册所有的可堆肥方块。
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
