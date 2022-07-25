package pers.solid.extshape;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.FireBlock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.rs.ExtShapeBridgeImpl;
import pers.solid.extshape.tag.ExtShapeBlockTags;

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
public class ExtShape implements ModInitializer {
  public static final String MOD_ID = "extshape";
  public static final Logger LOGGER = LogManager.getLogger(ExtShape.class);

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
    CommandRegistrationCallback.EVENT.register(RecipeConflict::registerCommand);

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
   * 注册所有的可堆肥方块。注意：对于 Forge 版本，是直接修改的 {@link ComposterBlock#ITEM_TO_LEVEL_INCREASE_CHANCE}。
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
   * 在初始化时，注册所有的燃料。注意：对于 Forge 版本，物品的燃烧由 {@code IForgeItem} 的相关接口决定。
   *
   * @see ExtShapeBlocks
   * @see net.minecraft.block.entity.AbstractFurnaceBlockEntity#createFuelTimeMap()
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
   * 在初始化时，注册所有的可燃方块。注意：对于 Forge 版本，方块燃烧由 {@code IForgeBlock} 接口的相关方法决定。
   *
   * @see FireBlock#registerDefaultFlammables()
   */
  private void registerFlammableBlocks() {
    final FlammableBlockRegistry registry = FlammableBlockRegistry.getDefaultInstance();
    // 羊毛方块加入可燃方块
    for (final Block block : ExtShapeBlockTags.WOOLEN_BLOCKS) {
      registry.add(block, 30, 60);
    }

    // 木头加入可燃方块
    for (final Block block : ExtShapeBlockTags.OVERWORLD_WOODEN_BLOCKS) {
      registry.add(block, 5, 20);
    }
  }
}
