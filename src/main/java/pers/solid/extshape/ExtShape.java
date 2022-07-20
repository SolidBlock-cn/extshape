package pers.solid.extshape;

import com.google.common.base.Preconditions;
import com.google.common.base.Suppliers;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.Shape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.config.ExtShapeOptionsScreen;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.rs.ExtShapeBridgeImpl;
import pers.solid.extshape.tag.ExtShapeBlockTags;

@Mod(ExtShape.MOD_ID)
public class ExtShape {
  public static final String MOD_ID = "extshape";
  public static final Logger LOGGER = LoggerFactory.getLogger(ExtShape.class);

  public ExtShape() {
    ExtShapeConfig.init();
    MinecraftForge.EVENT_BUS.addListener(ExtShape::registerCommand);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(ExtShape::register);
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
        })));

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
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(Shape.STAIRS, compostableBlock)).asItem(), 0.65f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(Shape.SLAB, compostableBlock)).asItem(), 0.325f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(Shape.VERTICAL_STAIRS, compostableBlock)).asItem(), 0.65f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(Shape.VERTICAL_SLAB, compostableBlock)).asItem(), 0.325f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(Shape.QUARTER_PIECE, compostableBlock)).asItem(), 0.15625f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(Shape.VERTICAL_QUARTER_PIECE, compostableBlock)).asItem(), 0.15625f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(Shape.FENCE, compostableBlock)).asItem(), 0.65f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(Shape.FENCE_GATE, compostableBlock)).asItem(), 0.65f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(Shape.WALL, compostableBlock)).asItem(), 0.65f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(Shape.BUTTON, compostableBlock)).asItem(), 0.2f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(Shape.PRESSURE_PLATE, compostableBlock)).asItem(), 0.2f);
    }
    // 原版的下界疣和诡异疣的堆肥概率为 0.9。
    for (final Block compostableBlock : new Block[]{
        Blocks.WARPED_WART_BLOCK, Blocks.NETHER_WART_BLOCK
    }) {
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(Shape.STAIRS, compostableBlock)).asItem(), 0.8f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(Shape.SLAB, compostableBlock)).asItem(), 0.4f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(Shape.VERTICAL_STAIRS, compostableBlock)).asItem(), 0.8f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(Shape.VERTICAL_SLAB, compostableBlock)).asItem(), 0.4f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(Shape.QUARTER_PIECE, compostableBlock)).asItem(), 0.2f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(Shape.VERTICAL_QUARTER_PIECE, compostableBlock)).asItem(), 0.2f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(Shape.FENCE, compostableBlock)).asItem(), 0.8f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(Shape.FENCE_GATE, compostableBlock)).asItem(), 0.8f);
      map.put(Preconditions.checkNotNull(BlockMappings.getBlockOf(Shape.WALL, compostableBlock)).asItem(), 0.8f);
    }
  }
}
