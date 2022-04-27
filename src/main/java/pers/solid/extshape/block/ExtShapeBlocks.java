package pers.solid.extshape.block;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.devtech.arrp.generator.BRRPCubeBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock.ActivationRule;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import pers.solid.extshape.block.ExtShapeButtonBlock.ButtonType;
import pers.solid.extshape.builder.*;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.util.List;

import static net.minecraft.block.Blocks.*;
import static pers.solid.extshape.tag.ExtShapeBlockTag.*;

/**
 * @see net.minecraft.block.Blocks
 */
public final class ExtShapeBlocks {
  /**
   * 存储本模组所有方块的列表。其功能类似于 {@link ExtShapeBlockTag#EXTSHAPE_BLOCKS}，但是使用该列表进行迭代显然更加迅速。该列表的内容是在 {@link AbstractBlockBuilder#build()} 中添加的。
   */
  public static final List<Block> BLOCKS = new ObjectArrayList<>();
  /**
   * 石化橡木方块。
   */
  public static final Block PETRIFIED_OAK_PLANKS;
  /**
   * 双层石台阶。
   */
  public static final Block SMOOTH_STONE_DOUBLE_SLAB;

  /*
    使用BlockBuilder并利用迭代器来批量注册多个方块及其对应方块物品，提高效率。
    只有极少数方块会以静态常量成员变量的形式存储。
   */
  static {
    // 石头及其变种（含磨制变种），已存在其楼梯、台阶、墙，但是还没有栅栏和栅栏门。
    for (final Block block : STONES) {
      new BlocksBuilder(block, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).build();
    }

    // 圆石。
    new BlocksBuilder(COBBLESTONE, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).withoutRedstone().build();

    // 木板。
    for (final Block block : PLANKS) {
      final boolean isOverworld = OVERWORLD_PLANKS.contains(block);
      new BlocksBuilder(block, null, ButtonType.WOODEN, ActivationRule.EVERYTHING)
          .setDefaultTagOf(Shape.VERTICAL_SLAB, WOODEN_VERTICAL_SLABS)
          .setDefaultTagOf(Shape.VERTICAL_STAIRS, WOODEN_VERTICAL_STAIRS)
          .setDefaultTagOf(Shape.QUARTER_PIECE, WOODEN_QUARTER_PIECES)
          .setDefaultTagOf(Shape.VERTICAL_QUARTER_PIECE, WOODEN_VERTICAL_QUARTER_PIECES)
          .putTag(isOverworld ? OVERWORLD_WOODEN_BLOCKS : null)
          .withoutWall().build();
    }

    // 石化橡木木板
    PETRIFIED_OAK_PLANKS = new BlockBuilder()
        .setInstanceSupplier(builder -> BRRPCubeBlock.cubeAll(builder.blockSettings, "block/oak_planks"))
        .setBlockSettings(FabricBlockSettings.copyOf(PETRIFIED_OAK_SLAB))
        .setIdentifier(new Identifier("extshape", "petrified_oak_planks"))
        .group(ItemGroup.BUILDING_BLOCKS).setDefaultTag(FULL_BLOCKS).build();

    BlockMappings.SHAPE_TO_MAPPING.get(Shape.SLAB).put(PETRIFIED_OAK_PLANKS, PETRIFIED_OAK_SLAB);

    // 基岩。
    new BlocksBuilder(BEDROCK, Items.STICK, ButtonType.HARD, ActivationRule.MOBS).setPreparationConsumer(((shape, abstractBlockBuilder) -> abstractBlockBuilder.setPreparationConsumer(builder -> builder.blockSettings.strength(-1.0F, 3600000.0F).allowsSpawning((state, world, pos, type) -> false)))).build();

    // 青金石块。
    new BlocksBuilder(LAPIS_BLOCK, Items.LAPIS_LAZULI, ButtonType.STONE, ActivationRule.MOBS).build();

    // 砂岩、红砂岩及其切制、錾制、平滑变种。其中，只有平滑砂岩有栅栏、压力板和按钮。
    for (final Block block : SANDSTONES) {
      new BlocksBuilder(block).withShapes().withWall().build();
    }
    for (final Block block : new Block[]{SMOOTH_SANDSTONE, SMOOTH_RED_SANDSTONE}) {
      new BlocksBuilder(block).withFences(Items.STICK).withPressurePlate(ActivationRule.MOBS).withButton(ButtonType.STONE).build();
    }

    // 羊毛不注册墙。
    for (final Block block : WOOLS) {
      new BlocksBuilder(block, Items.STRING, ButtonType.SOFT, ActivationRule.EVERYTHING).putTag(WOOLEN_BLOCKS).withoutWall()
          .setDefaultTagOf(Shape.STAIRS, WOOLEN_STAIRS)
          .setDefaultTagOf(Shape.VERTICAL_STAIRS, WOOLEN_VERTICAL_STAIRS)
          .setDefaultTagOf(Shape.SLAB, WOOLEN_SLABS)
          .setDefaultTagOf(Shape.VERTICAL_SLAB, WOOLEN_VERTICAL_SLABS)
          .setDefaultTagOf(Shape.QUARTER_PIECE, WOOLEN_QUARTER_PIECES)
          .setDefaultTagOf(Shape.VERTICAL_QUARTER_PIECE, WOOLEN_VERTICAL_QUARTER_PIECES)
          .setDefaultTagOf(Shape.FENCE, WOOLEN_FENCES)
          .setDefaultTagOf(Shape.FENCE_GATE, WOOLEN_FENCE_GATES)
          .setDefaultTagOf(Shape.BUTTON, WOOLEN_BUTTONS)
          .setDefaultTagOf(Shape.PRESSURE_PLATE, WOOLEN_PRESSURE_PLATES)
          .build();
    }

    // 金块。
    new BlocksBuilder(GOLD_BLOCK, Items.GOLD_INGOT, ButtonType.STONE, null).build();
    // 铁块。
    new BlocksBuilder(IRON_BLOCK, Items.IRON_INGOT, ButtonType.STONE, null).build();

    // 砖栅栏和栅栏门。
    new BlocksBuilder(BRICKS).withShapes().withFences(Items.BRICK).withWall().build();

    // 苔石栅栏和栅栏门。
    new BlocksBuilder(MOSSY_COBBLESTONE, Items.STICK, ButtonType.STONE, ActivationRule.MOBS).build();

    // 黑曜石。
    new BlocksBuilder(OBSIDIAN, Items.STONE, ButtonType.HARD, ActivationRule.MOBS).build();

    // 钻石块。
    new BlocksBuilder(DIAMOND_BLOCK, Items.DIAMOND, ButtonType.HARD, ActivationRule.MOBS).build();

    // 紫水晶块。
    new BlocksBuilder(AMETHYST_BLOCK, Items.AMETHYST_SHARD, ButtonType.STONE, ActivationRule.MOBS).withoutRedstone().build();

    // 冰，由于技术原因，暂不产生。

    // 雪块。
    new BlocksBuilder(SNOW_BLOCK, Items.SNOW_BLOCK, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

    // 黏土块。
    new BlocksBuilder(CLAY).withShapes().build();

    // 南瓜。
    new BlocksBuilder(PUMPKIN, Items.PUMPKIN_SEEDS, ButtonType.WOODEN, ActivationRule.EVERYTHING).build();

    // 下界岩。
    new BlocksBuilder(NETHERRACK, Items.NETHER_BRICK, ButtonType.STONE, ActivationRule.MOBS).build();

    // 荧石可以发光。
    new BlocksBuilder(GLOWSTONE, Items.GLOWSTONE_DUST, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

    // 石砖、苔石砖。
    for (final Block block : new Block[]{STONE_BRICKS, MOSSY_STONE_BRICKS, CHISELED_STONE_BRICKS}) {
      new BlocksBuilder(block).withShapes().withFences(Items.FLINT).withWall().build();
    }

    // 西瓜。
    new BlocksBuilder(MELON, Items.MELON_SLICE, ButtonType.WOODEN, ActivationRule.EVERYTHING).build();

    // 下界砖块的栅栏门、按钮和压力板。
    new BlocksBuilder(NETHER_BRICKS, Items.NETHER_BRICK, ButtonType.STONE, ActivationRule.MOBS).withoutRedstone().build();

    // 末地石、末地石砖。
    new BlocksBuilder(END_STONE, Items.END_STONE_BRICKS, ButtonType.STONE, ActivationRule.MOBS).build();

    // 绿宝石块。
    new BlocksBuilder(EMERALD_BLOCK, Items.EMERALD, ButtonType.STONE, ActivationRule.MOBS).build();

    // 石英、石英砖、平滑石英块、錾制石英块均有按钮和压力板。
    for (final Block block : new Block[]{QUARTZ_BLOCK, CHISELED_QUARTZ_BLOCK, QUARTZ_BRICKS, SMOOTH_QUARTZ}) {
      new BlocksBuilder(block, Items.QUARTZ, ButtonType.STONE, ActivationRule.MOBS).build();
    }

    // 红色下界砖。
    new BlocksBuilder(RED_NETHER_BRICKS, Items.NETHER_BRICK, ButtonType.STONE, ActivationRule.MOBS).build();

    // 陶瓦和彩色陶瓦。
    new BlocksBuilder(TERRACOTTA, Items.CLAY, ButtonType.STONE, ActivationRule.MOBS)
        .setDefaultTagOf(Shape.STAIRS, TERRACOTTA_STAIRS)
        .setDefaultTagOf(Shape.VERTICAL_STAIRS, TERRACOTTA_VERTICAL_STAIRS)
        .setDefaultTagOf(Shape.SLAB, TERRACOTTA_SLABS)
        .setDefaultTagOf(Shape.VERTICAL_SLAB, TERRACOTTA_VERTICAL_SLABS)
        .setDefaultTagOf(Shape.QUARTER_PIECE, TERRACOTTA_QUARTER_PIECES)
        .setDefaultTagOf(Shape.VERTICAL_QUARTER_PIECE, TERRACOTTA_VERTICAL_QUARTER_PIECES)
        .setDefaultTagOf(Shape.FENCE, TERRACOTTA_FENCES)
        .setDefaultTagOf(Shape.FENCE_GATE, TERRACOTTA_FENCE_GATES)
        .setDefaultTagOf(Shape.WALL, TERRACOTTA_WALLS)
        .setDefaultTagOf(Shape.BUTTON, TERRACOTTA_BUTTONS)
        .setDefaultTagOf(Shape.PRESSURE_PLATE, TERRACOTTA_PRESSURE_PLATES)
        .build();
    for (final Block block : STAINED_TERRACOTTAS) {
      new BlocksBuilder(block, Items.CLAY, ButtonType.STONE, ActivationRule.MOBS)
          .setDefaultTagOf(Shape.STAIRS, STAINED_TERRACOTTA_STAIRS)
          .setDefaultTagOf(Shape.VERTICAL_STAIRS, STAINED_TERRACOTTA_VERTICAL_STAIRS)
          .setDefaultTagOf(Shape.SLAB, STAINED_TERRACOTTA_SLABS)
          .setDefaultTagOf(Shape.VERTICAL_SLAB, STAINED_TERRACOTTA_VERTICAL_SLABS)
          .setDefaultTagOf(Shape.QUARTER_PIECE, STAINED_TERRACOTTA_QUARTER_PIECES)
          .setDefaultTagOf(Shape.VERTICAL_QUARTER_PIECE, STAINED_TERRACOTTA_VERTICAL_QUARTER_PIECES)
          .setDefaultTagOf(Shape.FENCE, STAINED_TERRACOTTA_FENCES)
          .setDefaultTagOf(Shape.FENCE_GATE, STAINED_TERRACOTTA_FENCE_GATES)
          .setDefaultTagOf(Shape.WALL, STAINED_TERRACOTTA_WALLS)
          .setDefaultTagOf(Shape.BUTTON, STAINED_TERRACOTTA_BUTTONS)
          .setDefaultTagOf(Shape.PRESSURE_PLATE, STAINED_TERRACOTTA_PRESSURE_PLATES)
          .build();
    }

    // 浮冰。
    new BlocksBuilder(PACKED_ICE, Items.ICE, ButtonType.STONE, ActivationRule.MOBS).build();

    // 海晶石、海晶石砖、暗海晶石。
    for (final Block block : new Block[]{PRISMARINE, PRISMARINE_BRICKS, DARK_PRISMARINE}) {
      new BlocksBuilder(block, Items.PRISMARINE_SHARD, ButtonType.STONE, ActivationRule.MOBS).build();
    }

    // 海晶灯。
    new BlocksBuilder(SEA_LANTERN).withShapes().build();

    // 平滑石头比较特殊，完整方块和台阶不同。
    SMOOTH_STONE_DOUBLE_SLAB = new BlockBuilder()
        .setInstanceSupplier(builder -> BRRPCubeBlock.cubeBottomTop(builder.blockSettings, "block/smooth_stone_slab_top", "block/smooth_stone_slab", "block/smooth_stone_slab_top"))
        .setBlockSettings(FabricBlockSettings.copyOf(SMOOTH_STONE))
        .setIdentifier(new Identifier("extshape", "smooth_stone_slab_double"))
        .group(ItemGroup.BUILDING_BLOCKS).setDefaultTag(FULL_BLOCKS).build();

    new BlocksBuilder(SMOOTH_STONE, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).withoutShapes().build();

    BlockMappings.SHAPE_TO_MAPPING.get(Shape.SLAB).put(SMOOTH_STONE_DOUBLE_SLAB, SMOOTH_STONE_SLAB);

    // 紫珀块。
    new BlocksBuilder(PURPUR_BLOCK, Items.SHULKER_SHELL, ButtonType.STONE, ActivationRule.MOBS).build();

    // 下界疣块、诡异疣块。
    new BlocksBuilder(NETHER_WART_BLOCK, Items.NETHER_WART, null, null).withoutRedstone().withoutWall().build();
    new BlocksBuilder(WARPED_WART_BLOCK, Items.WARPED_WART_BLOCK, null, null).withoutRedstone().withoutWall().build();

    // 带釉陶瓦只注册台阶。
    for (final Block block : GLAZED_TERRACOTTAS) {
      new SlabBuilder(block).setInstanceSupplier(builder -> new GlazedTerracottaSlabBlock(builder.baseBlock, FabricBlockSettings.copyOf(builder.baseBlock))).setDefaultTag(GLAZED_TERRACOTTA_SLABS).build();
    }

    // 彩色混凝土。
    for (final Block block : CONCRETES) {
      new BlocksBuilder(block, Items.GRAVEL, ButtonType.STONE, ActivationRule.MOBS)
          .setDefaultTagOf(Shape.STAIRS, CONCRETE_STAIRS)
          .setDefaultTagOf(Shape.VERTICAL_STAIRS, CONCRETE_VERTICAL_STAIRS)
          .setDefaultTagOf(Shape.SLAB, CONCRETE_SLABS)
          .setDefaultTagOf(Shape.VERTICAL_SLAB, CONCRETE_VERTICAL_SLABS)
          .setDefaultTagOf(Shape.QUARTER_PIECE, CONCRETE_QUARTER_PIECES)
          .setDefaultTagOf(Shape.VERTICAL_QUARTER_PIECE, CONCRETE_VERTICAL_QUARTER_PIECES)
          .setDefaultTagOf(Shape.FENCE, CONCRETE_FENCES)
          .setDefaultTagOf(Shape.FENCE_GATE, CONCRETE_FENCE_GATES)
          .setDefaultTagOf(Shape.WALL, CONCRETE_WALLS)
          .setDefaultTagOf(Shape.BUTTON, CONCRETE_BUTTONS)
          .setDefaultTagOf(Shape.PRESSURE_PLATE, CONCRETE_PRESSURE_PLATES).build();
    }

    // 菌光体。
    new BlocksBuilder(SHROOMLIGHT, Items.GLOWSTONE_DUST, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

    // 蜜脾块。
    new BlocksBuilder(HONEYCOMB_BLOCK, Items.HONEYCOMB, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

    // 下界合金方块。
    new BlocksBuilder(NETHERITE_BLOCK, Items.NETHERITE_INGOT, ButtonType.HARD, ActivationRule.MOBS).build();

    // 远古残骸。
    new BlocksBuilder(ANCIENT_DEBRIS, Items.NETHERITE_SCRAP, ButtonType.HARD, ActivationRule.MOBS).build();

    // 哭泣的黑曜石。
    new BlocksBuilder(CRYING_OBSIDIAN, Items.STONE, ButtonType.HARD, ActivationRule.MOBS).build();

    // 黑石及其变种。
    new BlocksBuilder(BLACKSTONE).withShapes().build();
    new BlocksBuilder(POLISHED_BLACKSTONE).withShapes().build();
    new BlocksBuilder(POLISHED_BLACKSTONE_BRICKS).withShapes().build();
    new BlocksBuilder(CHISELED_POLISHED_BLACKSTONE).withShapes().build();
    new BlocksBuilder(GILDED_BLACKSTONE).withShapes().build();

    new BlocksBuilder(CHISELED_NETHER_BRICKS).withShapes().build();

    // 凝灰岩，方解石。
    new BlocksBuilder(TUFF, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).build();
    new BlocksBuilder(CALCITE, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).build();

    // 涂蜡的铜块。
    for (final Block block : new Block[]{
        WAXED_COPPER_BLOCK,
        WAXED_WEATHERED_COPPER,
        WAXED_EXPOSED_COPPER,
        WAXED_OXIDIZED_COPPER,
        WAXED_OXIDIZED_CUT_COPPER,
        WAXED_WEATHERED_CUT_COPPER,
        WAXED_EXPOSED_CUT_COPPER,
        WAXED_CUT_COPPER
    }) {
      new BlocksBuilder(block).withShapes().build();
    }

    // 滴水石、苔藓。
    new BlocksBuilder(DRIPSTONE_BLOCK, Items.POINTED_DRIPSTONE, ButtonType.STONE, ActivationRule.MOBS).build();
    new BlocksBuilder(MOSS_BLOCK, Items.MOSS_CARPET, ButtonType.SOFT, ActivationRule.EVERYTHING).withoutWall().build();

    // 深板岩变种。深板岩自身属于 PillarBlock，不适合创建形状。
    for (final Block block : new Block[]{COBBLED_DEEPSLATE, POLISHED_DEEPSLATE, DEEPSLATE_TILES, DEEPSLATE_BRICKS, CHISELED_DEEPSLATE}) {
      new BlocksBuilder(block, Items.DEEPSLATE, ButtonType.STONE, ActivationRule.MOBS).build();
    }

    // 平滑玄武岩。
    new BlocksBuilder(SMOOTH_BASALT, Items.FLINT, ButtonType.STONE, ActivationRule.EVERYTHING).build();

    // 粗铁、粗铜、粗金。
    for (Block block : new Block[]{RAW_IRON_BLOCK, RAW_COPPER_BLOCK, RAW_GOLD_BLOCK}) {
      new BlocksBuilder(block).withShapes().build();
    }
  }

  /**
   * 虽然此函数不执行操作，但是执行此函数会确保此类中的静态部分都遍历一遍。
   */
  public static void init() {
  }
}
