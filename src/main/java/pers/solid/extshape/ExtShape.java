package pers.solid.extshape;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.rs.ExtShapeBridgeImpl;
import pers.solid.extshape.tag.ExtShapeBlockTags;

public class ExtShape implements ModInitializer {
  public static final String MOD_ID = "extshape";
  public static final Logger LOGGER = LoggerFactory.getLogger(ExtShape.class);

  @Override
  public void onInitialize() {
    ExtShapeConfig.init();
    ExtShapeBlocks.init();
    ExtShapeItemGroup.init();
    ExtShapeBlockTags.refillTags();

    registerFlammableBlocks();
    registerComposingChances();
    registerFuels();

    ExtShapeRRP.registerRRP();
    CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(LiteralArgumentBuilder.<ServerCommandSource>literal("extshape:check-conflict")
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

    if (FabricLoader.getInstance().isModLoaded("reasonable-sorting")) {
      try {
        ExtShapeBridgeImpl.initialize();
      } catch (LinkageError e) {
        LOGGER.info("An error ({}) was thrown when initializing Reasonable Sorting Mod with Extended Block Shapes mod. This is expected.", e.getClass().getSimpleName());
      } catch (Throwable e) {
        LOGGER.warn("Failed to call ExtShapeBridgeImpl.initialize():", e);
      }
    }
  }

  /**
   * 注册所有的可堆肥方块。
   *
   * @see net.fabricmc.fabric.api.registry.CompostingChanceRegistry
   * @see ComposterBlock#registerDefaultCompostableItems()
   */
  private void registerComposingChances() {
    // 原版这些方块的堆肥概率为 0.65。
    for (final Block compostableBlock : new Block[]{
        Blocks.PUMPKIN, Blocks.MELON, Blocks.MOSS_BLOCK, Blocks.SHROOMLIGHT
    }) {
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(BlockShape.STAIRS, compostableBlock), 0.65f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(BlockShape.SLAB, compostableBlock), 0.325f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(BlockShape.VERTICAL_STAIRS, compostableBlock), 0.65f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(BlockShape.VERTICAL_SLAB, compostableBlock), 0.325f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(BlockShape.QUARTER_PIECE, compostableBlock), 0.15625f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(BlockShape.VERTICAL_QUARTER_PIECE, compostableBlock), 0.15625f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(BlockShape.FENCE, compostableBlock), 0.65f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(BlockShape.FENCE_GATE, compostableBlock), 0.65f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(BlockShape.WALL, compostableBlock), 0.65f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(BlockShape.BUTTON, compostableBlock), 0.2f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(BlockShape.PRESSURE_PLATE, compostableBlock), 0.2f);
    }
    // 原版的下界疣和诡异疣的堆肥概率为 0.9。
    for (final Block compostableBlock : new Block[]{
        Blocks.WARPED_WART_BLOCK, Blocks.NETHER_WART_BLOCK
    }) {
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(BlockShape.STAIRS, compostableBlock), 0.8f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(BlockShape.SLAB, compostableBlock), 0.4f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(BlockShape.VERTICAL_STAIRS, compostableBlock), 0.8f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(BlockShape.VERTICAL_SLAB, compostableBlock), 0.4f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(BlockShape.QUARTER_PIECE, compostableBlock), 0.2f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(BlockShape.VERTICAL_QUARTER_PIECE, compostableBlock), 0.2f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(BlockShape.FENCE, compostableBlock), 0.8f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(BlockShape.FENCE_GATE, compostableBlock), 0.8f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(BlockShape.WALL, compostableBlock), 0.8f);
    }
  }

  /**
   * 在初始化时，注册所有的染料。
   *
   * @see ExtShapeBlocks
   * @see AbstractFurnaceBlockEntity#createFuelTimeMap()
   */
  @ApiStatus.AvailableSince("1.5.0")
  private void registerFuels() {
    // 参照原版木制（含下界木）楼梯和台阶，楼梯燃烧时间为 300 刻，台阶燃烧时间为 150 刻。
    // 但是，non_flammable_wood 标签的仍然不会被熔炉接受。
    FuelRegistry.INSTANCE.add(ExtShapeBlockTags.WOODEN_VERTICAL_STAIRS.toItemTag(), 300);
    FuelRegistry.INSTANCE.add(ExtShapeBlockTags.WOODEN_VERTICAL_SLABS.toItemTag(), 150);
    FuelRegistry.INSTANCE.add(ExtShapeBlockTags.WOODEN_QUARTER_PIECES.toItemTag(), 75);
    FuelRegistry.INSTANCE.add(ExtShapeBlockTags.WOODEN_VERTICAL_QUARTER_PIECES.toItemTag(), 75);
    FuelRegistry.INSTANCE.add(ExtShapeBlockTags.WOODEN_WALLS.toItemTag(), 300);

    // 参照原版羊毛燃烧时间为 100 刻，楼梯燃烧时间和基础方块相同，台阶燃烧时间为一半。
    FuelRegistry.INSTANCE.add(ExtShapeBlockTags.WOOLEN_STAIRS.toItemTag(), 100);
    FuelRegistry.INSTANCE.add(ExtShapeBlockTags.WOOLEN_SLABS.toItemTag(), 50);
    FuelRegistry.INSTANCE.add(ExtShapeBlockTags.WOOLEN_QUARTER_PIECES.toItemTag(), 25);
    FuelRegistry.INSTANCE.add(ExtShapeBlockTags.WOOLEN_VERTICAL_STAIRS.toItemTag(), 100);
    FuelRegistry.INSTANCE.add(ExtShapeBlockTags.WOOLEN_VERTICAL_SLABS.toItemTag(), 50);
    FuelRegistry.INSTANCE.add(ExtShapeBlockTags.WOOLEN_VERTICAL_QUARTER_PIECES.toItemTag(), 25);
    // 栅栏、栅栏门、压力板、燃烧时间和基础方块一致，门的燃烧时间为三分之二，按钮为三分之一。
    // 但考虑到羊毛压力板是与地毯相互合成的，故燃烧时间与地毯一致，为 67。
    FuelRegistry.INSTANCE.add(ExtShapeBlockTags.WOOLEN_FENCES.toItemTag(), 100);
    FuelRegistry.INSTANCE.add(ExtShapeBlockTags.WOOLEN_FENCE_GATES.toItemTag(), 100);
    FuelRegistry.INSTANCE.add(ExtShapeBlockTags.WOOLEN_PRESSURE_PLATES.toItemTag(), 67);
    FuelRegistry.INSTANCE.add(ExtShapeBlockTags.WOOLEN_BUTTONS.toItemTag(), 33);
    FuelRegistry.INSTANCE.add(ExtShapeBlockTags.WOOLEN_WALLS.toItemTag(), 100);
  }

  /**
   * 在初始化时，注册所有的可燃方块。
   *
   * @see FireBlock#registerDefaultFlammables()
   */
  private void registerFlammableBlocks() {
    // 羊毛方块加入可燃方块
    for (final Block block : ExtShapeBlockTags.WOOLEN_BLOCKS) {
      FlammableBlockRegistry.getDefaultInstance().add(block, 30, 60);
    }

    // 木头加入可燃方块
    for (final Block block : ExtShapeBlockTags.OVERWORLD_WOODEN_BLOCKS) {
      FlammableBlockRegistry.getDefaultInstance().add(block, 5, 20);
    }
  }
}
