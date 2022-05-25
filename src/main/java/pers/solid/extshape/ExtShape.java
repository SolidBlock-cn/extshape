package pers.solid.extshape;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.Shape;
import pers.solid.extshape.config.ExtShapeConfig;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTags;

public class ExtShape implements ModInitializer {
  public static final String MOD_ID = "extshape";
  public static final Logger LOGGER = LogManager.getLogger("EXTSHAPE");

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
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(Shape.STAIRS, compostableBlock), 0.65f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(Shape.SLAB, compostableBlock), 0.325f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(Shape.VERTICAL_STAIRS, compostableBlock), 0.65f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(Shape.VERTICAL_SLAB, compostableBlock), 0.325f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(Shape.QUARTER_PIECE, compostableBlock), 0.15625f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(Shape.VERTICAL_QUARTER_PIECE, compostableBlock), 0.15625f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(Shape.FENCE, compostableBlock), 0.65f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(Shape.FENCE_GATE, compostableBlock), 0.65f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(Shape.WALL, compostableBlock), 0.65f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(Shape.BUTTON, compostableBlock), 0.2f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(Shape.PRESSURE_PLATE, compostableBlock), 0.2f);
    }
    // 原版的下界疣和诡异疣的堆肥概率为 0.9。
    for (final Block compostableBlock : new Block[]{
        Blocks.WARPED_WART_BLOCK, Blocks.NETHER_WART_BLOCK
    }) {
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(Shape.STAIRS, compostableBlock), 0.8f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(Shape.SLAB, compostableBlock), 0.4f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(Shape.VERTICAL_STAIRS, compostableBlock), 0.8f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(Shape.VERTICAL_SLAB, compostableBlock), 0.4f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(Shape.QUARTER_PIECE, compostableBlock), 0.2f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(Shape.VERTICAL_QUARTER_PIECE, compostableBlock), 0.2f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(Shape.FENCE, compostableBlock), 0.8f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(Shape.FENCE_GATE, compostableBlock), 0.8f);
      CompostingChanceRegistry.INSTANCE.add(BlockMappings.getBlockOf(Shape.WALL, compostableBlock), 0.8f);
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
    FuelRegistry.INSTANCE.add(ExtShapeBlockTags.WOODEN_WALLS.toItemTag(), 100);

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
