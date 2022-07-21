package pers.solid.extshape.block;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.devtech.arrp.generator.BRRPCubeBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock.ActivationRule;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.block.ExtShapeButtonBlock.ButtonType;
import pers.solid.extshape.builder.*;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTags;

import static net.minecraft.block.Blocks.*;

/**
 * @see net.minecraft.block.Blocks
 */
public final class ExtShapeBlocks {
  /**
   * 存储本模组所有方块的列表。其功能类似于 {@link ExtShapeBlockTags#EXTSHAPE_BLOCKS}，但是使用该列表进行迭代显然更加迅速。该列表的内容是在 {@link AbstractBlockBuilder#build()} 中添加的。
   */
  public static final ObjectList<Block> BLOCKS = new ObjectArrayList<>();
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
    for (final Block block : ExtShapeBlockTags.STONES) {
      BlocksBuilder.createComprehensive(block, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).build();
    }

    // 圆石。
    BlocksBuilder.createComprehensive(COBBLESTONE, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).withoutRedstone().build();

    // 木板。
    for (final Block block : ExtShapeBlockTags.PLANKS) {
      final boolean isOverworld = ExtShapeBlockTags.OVERWORLD_PLANKS.contains(block);
      BlocksBuilder.createComprehensive(block, null, ButtonType.WOODEN, ActivationRule.EVERYTHING)
          .setDefaultTagOf(BlockShape.VERTICAL_SLAB, ExtShapeBlockTags.WOODEN_VERTICAL_SLABS)
          .setDefaultTagOf(BlockShape.VERTICAL_STAIRS, ExtShapeBlockTags.WOODEN_VERTICAL_STAIRS)
          .setDefaultTagOf(BlockShape.QUARTER_PIECE, ExtShapeBlockTags.WOODEN_QUARTER_PIECES)
          .setDefaultTagOf(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeBlockTags.WOODEN_VERTICAL_QUARTER_PIECES)
          .setDefaultTagOf(BlockShape.WALL, ExtShapeBlockTags.WOODEN_WALLS)
          .addTagToAddEach(isOverworld ? ExtShapeBlockTags.OVERWORLD_WOODEN_BLOCKS : null)
          .build();
    }

    // 石化橡木木板
    PETRIFIED_OAK_PLANKS = new BlockBuilder()
        .setInstanceSupplier(builder -> BRRPCubeBlock.cubeAll(builder.blockSettings, "block/oak_planks"))
        .setBlockSettings(FabricBlockSettings.copyOf(PETRIFIED_OAK_SLAB))
        .setIdentifier(new Identifier(ExtShape.MOD_ID, "petrified_oak_planks"))
        .group(ItemGroup.BUILDING_BLOCKS).setDefaultTagToAdd(ExtShapeBlockTags.FULL_BLOCKS).build();

    BlockMappings.getMappingOf(BlockShape.SLAB).put(PETRIFIED_OAK_PLANKS, PETRIFIED_OAK_SLAB);

    // 基岩。
    BlocksBuilder.createComprehensive(BEDROCK, Items.STICK, ButtonType.HARD, ActivationRule.MOBS)
        .consumeEachSettings((shape, settings) -> settings.strength(-1.0F, 3600000.0F).allowsSpawning((state, world, pos, type) -> false))
        .build();

    // 青金石块。
    BlocksBuilder.createComprehensive(LAPIS_BLOCK, Items.LAPIS_LAZULI, ButtonType.STONE, ActivationRule.MOBS).build();

    // 砂岩、红砂岩及其切制、錾制、平滑变种。其中，只有平滑砂岩有栅栏、压力板和按钮。
    for (final Block block : ExtShapeBlockTags.SANDSTONES) {
      BlocksBuilder.createConstructionOnly(block).withWall().build();
    }
    for (final Block block : new Block[]{SMOOTH_SANDSTONE, SMOOTH_RED_SANDSTONE}) {
      BlocksBuilder.createEmpty(block).withFences(Items.STICK).withPressurePlate(ActivationRule.MOBS).withButton(ButtonType.STONE).build();
    }

    // 羊毛。
    for (final Block block : ExtShapeBlockTags.WOOLS) {
      BlocksBuilder.createComprehensive(block, Items.STRING, ButtonType.SOFT, ActivationRule.EVERYTHING)
          .addTagToAddEach(ExtShapeBlockTags.WOOLEN_BLOCKS)
          .setDefaultTagOf(BlockShape.STAIRS, ExtShapeBlockTags.WOOLEN_STAIRS)
          .setDefaultTagOf(BlockShape.VERTICAL_STAIRS, ExtShapeBlockTags.WOOLEN_VERTICAL_STAIRS)
          .setDefaultTagOf(BlockShape.SLAB, ExtShapeBlockTags.WOOLEN_SLABS)
          .setDefaultTagOf(BlockShape.VERTICAL_SLAB, ExtShapeBlockTags.WOOLEN_VERTICAL_SLABS)
          .setDefaultTagOf(BlockShape.QUARTER_PIECE, ExtShapeBlockTags.WOOLEN_QUARTER_PIECES)
          .setDefaultTagOf(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeBlockTags.WOOLEN_VERTICAL_QUARTER_PIECES)
          .setDefaultTagOf(BlockShape.FENCE, ExtShapeBlockTags.WOOLEN_FENCES)
          .setDefaultTagOf(BlockShape.FENCE_GATE, ExtShapeBlockTags.WOOLEN_FENCE_GATES)
          .setDefaultTagOf(BlockShape.BUTTON, ExtShapeBlockTags.WOOLEN_BUTTONS)
          .setDefaultTagOf(BlockShape.PRESSURE_PLATE, ExtShapeBlockTags.WOOLEN_PRESSURE_PLATES)
          .setDefaultTagOf(BlockShape.WALL, ExtShapeBlockTags.WOOLEN_WALLS)
          .build();
    }

    // 金块。
    BlocksBuilder.createComprehensive(GOLD_BLOCK, Items.GOLD_INGOT, ButtonType.STONE, null).build();
    // 铁块。
    BlocksBuilder.createComprehensive(IRON_BLOCK, Items.IRON_INGOT, ButtonType.STONE, null).build();

    // 砖栅栏和栅栏门。
    BlocksBuilder.createConstructionOnly(BRICKS).withFences(Items.BRICK).withWall().build();

    // 苔石栅栏和栅栏门。
    BlocksBuilder.createComprehensive(MOSSY_COBBLESTONE, Items.STICK, ButtonType.STONE, ActivationRule.MOBS).build();

    // 黑曜石。
    BlocksBuilder.createComprehensive(OBSIDIAN, Items.STONE, ButtonType.HARD, ActivationRule.MOBS).build();

    // 钻石块。
    BlocksBuilder.createComprehensive(DIAMOND_BLOCK, Items.DIAMOND, ButtonType.HARD, ActivationRule.MOBS).build();

    // 紫水晶块。
    BlocksBuilder.createComprehensive(AMETHYST_BLOCK, Items.AMETHYST_SHARD, ButtonType.STONE, ActivationRule.MOBS).withoutRedstone().build();

    // 冰，由于技术原因，暂不产生。

    // 雪块。
    BlocksBuilder.createComprehensive(SNOW_BLOCK, Items.SNOW, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

    // 黏土块。
    BlocksBuilder.createComprehensive(CLAY, Items.CLAY_BALL, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

    // 南瓜。
    BlocksBuilder.createComprehensive(PUMPKIN, Items.PUMPKIN_SEEDS, ButtonType.WOODEN, ActivationRule.EVERYTHING).build();

    // 下界岩。
    BlocksBuilder.createComprehensive(NETHERRACK, Items.NETHER_BRICK, ButtonType.STONE, ActivationRule.MOBS).build();

    // 荧石可以发光。
    BlocksBuilder.createComprehensive(GLOWSTONE, Items.GLOWSTONE_DUST, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

    // 石砖、苔石砖。
    for (final Block block : new Block[]{STONE_BRICKS, MOSSY_STONE_BRICKS, CHISELED_STONE_BRICKS}) {
      BlocksBuilder.createConstructionOnly(block).withFences(Items.FLINT).withWall().build();
    }

    // 西瓜。
    BlocksBuilder.createComprehensive(MELON, Items.MELON_SLICE, ButtonType.WOODEN, ActivationRule.EVERYTHING).build();

    // 下界砖块的栅栏门、按钮和压力板。
    BlocksBuilder.createComprehensive(NETHER_BRICKS, Items.NETHER_BRICK, ButtonType.STONE, ActivationRule.MOBS).withoutRedstone().build();

    // 末地石、末地石砖。
    BlocksBuilder.createComprehensive(END_STONE, Items.END_STONE_BRICKS, ButtonType.STONE, ActivationRule.MOBS).build();

    // 绿宝石块。
    BlocksBuilder.createComprehensive(EMERALD_BLOCK, Items.EMERALD, ButtonType.STONE, ActivationRule.MOBS).build();

    // 石英、石英砖、平滑石英块、錾制石英块均有按钮和压力板。
    for (final Block block : new Block[]{QUARTZ_BLOCK, CHISELED_QUARTZ_BLOCK, QUARTZ_BRICKS, SMOOTH_QUARTZ}) {
      BlocksBuilder.createComprehensive(block, Items.QUARTZ, ButtonType.STONE, ActivationRule.MOBS).build();
    }

    // 红色下界砖。
    BlocksBuilder.createComprehensive(RED_NETHER_BRICKS, Items.NETHER_BRICK, ButtonType.STONE, ActivationRule.MOBS).build();

    // 陶瓦和彩色陶瓦。
    BlocksBuilder.createComprehensive(TERRACOTTA, Items.CLAY, ButtonType.STONE, ActivationRule.MOBS)
        .setDefaultTagOf(BlockShape.STAIRS, ExtShapeBlockTags.TERRACOTTA_STAIRS)
        .setDefaultTagOf(BlockShape.VERTICAL_STAIRS, ExtShapeBlockTags.TERRACOTTA_VERTICAL_STAIRS)
        .setDefaultTagOf(BlockShape.SLAB, ExtShapeBlockTags.TERRACOTTA_SLABS)
        .setDefaultTagOf(BlockShape.VERTICAL_SLAB, ExtShapeBlockTags.TERRACOTTA_VERTICAL_SLABS)
        .setDefaultTagOf(BlockShape.QUARTER_PIECE, ExtShapeBlockTags.TERRACOTTA_QUARTER_PIECES)
        .setDefaultTagOf(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeBlockTags.TERRACOTTA_VERTICAL_QUARTER_PIECES)
        .setDefaultTagOf(BlockShape.FENCE, ExtShapeBlockTags.TERRACOTTA_FENCES)
        .setDefaultTagOf(BlockShape.FENCE_GATE, ExtShapeBlockTags.TERRACOTTA_FENCE_GATES)
        .setDefaultTagOf(BlockShape.WALL, ExtShapeBlockTags.TERRACOTTA_WALLS)
        .setDefaultTagOf(BlockShape.BUTTON, ExtShapeBlockTags.TERRACOTTA_BUTTONS)
        .setDefaultTagOf(BlockShape.PRESSURE_PLATE, ExtShapeBlockTags.TERRACOTTA_PRESSURE_PLATES)
        .build();
    for (final Block block : ExtShapeBlockTags.STAINED_TERRACOTTA) {
      BlocksBuilder.createComprehensive(block, Items.CLAY, ButtonType.STONE, ActivationRule.MOBS)
          .setDefaultTagOf(BlockShape.STAIRS, ExtShapeBlockTags.STAINED_TERRACOTTA_STAIRS)
          .setDefaultTagOf(BlockShape.VERTICAL_STAIRS, ExtShapeBlockTags.STAINED_TERRACOTTA_VERTICAL_STAIRS)
          .setDefaultTagOf(BlockShape.SLAB, ExtShapeBlockTags.STAINED_TERRACOTTA_SLABS)
          .setDefaultTagOf(BlockShape.VERTICAL_SLAB, ExtShapeBlockTags.STAINED_TERRACOTTA_VERTICAL_SLABS)
          .setDefaultTagOf(BlockShape.QUARTER_PIECE, ExtShapeBlockTags.STAINED_TERRACOTTA_QUARTER_PIECES)
          .setDefaultTagOf(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeBlockTags.STAINED_TERRACOTTA_VERTICAL_QUARTER_PIECES)
          .setDefaultTagOf(BlockShape.FENCE, ExtShapeBlockTags.STAINED_TERRACOTTA_FENCES)
          .setDefaultTagOf(BlockShape.FENCE_GATE, ExtShapeBlockTags.STAINED_TERRACOTTA_FENCE_GATES)
          .setDefaultTagOf(BlockShape.WALL, ExtShapeBlockTags.STAINED_TERRACOTTA_WALLS)
          .setDefaultTagOf(BlockShape.BUTTON, ExtShapeBlockTags.STAINED_TERRACOTTA_BUTTONS)
          .setDefaultTagOf(BlockShape.PRESSURE_PLATE, ExtShapeBlockTags.STAINED_TERRACOTTA_PRESSURE_PLATES)
          .build();
    }

    // 浮冰。
    BlocksBuilder.createComprehensive(PACKED_ICE, Items.ICE, ButtonType.STONE, ActivationRule.MOBS).build();

    // 海晶石、海晶石砖、暗海晶石。
    for (final Block block : new Block[]{PRISMARINE, PRISMARINE_BRICKS, DARK_PRISMARINE}) {
      BlocksBuilder.createComprehensive(block, Items.PRISMARINE_SHARD, ButtonType.STONE, ActivationRule.MOBS).build();
    }

    // 海晶灯。
    BlocksBuilder.createConstructionOnly(SEA_LANTERN).build();

    // 平滑石头比较特殊，完整方块和台阶不同。
    SMOOTH_STONE_DOUBLE_SLAB = new BlockBuilder()
        .setInstanceSupplier(builder -> BRRPCubeBlock.cubeBottomTop(builder.blockSettings, "block/smooth_stone_slab_top", "block/smooth_stone_slab", "block/smooth_stone_slab_top"))
        .setBlockSettings(FabricBlockSettings.copyOf(SMOOTH_STONE))
        .setIdentifier(new Identifier(ExtShape.MOD_ID, "smooth_stone_slab_double"))
        .group(ItemGroup.BUILDING_BLOCKS).setDefaultTagToAdd(ExtShapeBlockTags.FULL_BLOCKS).build();

    BlocksBuilder.createComprehensive(SMOOTH_STONE, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).withoutConstructionShapes().build();

    BlockMappings.getMappingOf(BlockShape.SLAB).put(SMOOTH_STONE_DOUBLE_SLAB, SMOOTH_STONE_SLAB);

    // 紫珀块。
    BlocksBuilder.createComprehensive(PURPUR_BLOCK, Items.SHULKER_SHELL, ButtonType.STONE, ActivationRule.MOBS).build();

    // 下界疣块、诡异疣块。
    BlocksBuilder.createComprehensive(NETHER_WART_BLOCK, Items.NETHER_WART, null, null).withoutRedstone().build();
    BlocksBuilder.createComprehensive(WARPED_WART_BLOCK, Items.NETHER_WART, null, null).withoutRedstone().build();

    // 带釉陶瓦只注册台阶。
    for (final Block block : ExtShapeBlockTags.GLAZED_TERRACOTTA) {
      new SlabBuilder(block).setInstanceSupplier(builder -> new GlazedTerracottaSlabBlock(builder.baseBlock, FabricBlockSettings.copyOf(builder.baseBlock))).setDefaultTagToAdd(ExtShapeBlockTags.GLAZED_TERRACOTTA_SLABS).build();
    }

    // 彩色混凝土。
    for (final Block block : ExtShapeBlockTags.CONCRETES) {
      BlocksBuilder.createComprehensive(block, Items.GRAVEL, ButtonType.STONE, ActivationRule.MOBS)
          .setDefaultTagOf(BlockShape.STAIRS, ExtShapeBlockTags.CONCRETE_STAIRS)
          .setDefaultTagOf(BlockShape.VERTICAL_STAIRS, ExtShapeBlockTags.CONCRETE_VERTICAL_STAIRS)
          .setDefaultTagOf(BlockShape.SLAB, ExtShapeBlockTags.CONCRETE_SLABS)
          .setDefaultTagOf(BlockShape.VERTICAL_SLAB, ExtShapeBlockTags.CONCRETE_VERTICAL_SLABS)
          .setDefaultTagOf(BlockShape.QUARTER_PIECE, ExtShapeBlockTags.CONCRETE_QUARTER_PIECES)
          .setDefaultTagOf(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeBlockTags.CONCRETE_VERTICAL_QUARTER_PIECES)
          .setDefaultTagOf(BlockShape.FENCE, ExtShapeBlockTags.CONCRETE_FENCES)
          .setDefaultTagOf(BlockShape.FENCE_GATE, ExtShapeBlockTags.CONCRETE_FENCE_GATES)
          .setDefaultTagOf(BlockShape.WALL, ExtShapeBlockTags.CONCRETE_WALLS)
          .setDefaultTagOf(BlockShape.BUTTON, ExtShapeBlockTags.CONCRETE_BUTTONS)
          .setDefaultTagOf(BlockShape.PRESSURE_PLATE, ExtShapeBlockTags.CONCRETE_PRESSURE_PLATES)
          .build();
    }

    // 菌光体。
    BlocksBuilder.createComprehensive(SHROOMLIGHT, Items.GLOWSTONE_DUST, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

    // 蜜脾块。
    BlocksBuilder.createComprehensive(HONEYCOMB_BLOCK, Items.HONEYCOMB, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

    // 下界合金方块。
    BlocksBuilder.createComprehensive(NETHERITE_BLOCK, Items.NETHERITE_INGOT, ButtonType.HARD, ActivationRule.MOBS).build();

    // 远古残骸。
    BlocksBuilder.createComprehensive(ANCIENT_DEBRIS, Items.NETHERITE_SCRAP, ButtonType.HARD, ActivationRule.MOBS).build();

    // 哭泣的黑曜石。
    BlocksBuilder.createComprehensive(CRYING_OBSIDIAN, Items.STONE, ButtonType.HARD, ActivationRule.MOBS).build();

    // 黑石及其变种。
    BlocksBuilder.createConstructionOnly(BLACKSTONE).build();
    BlocksBuilder.createConstructionOnly(POLISHED_BLACKSTONE).build();
    BlocksBuilder.createConstructionOnly(POLISHED_BLACKSTONE_BRICKS).build();
    BlocksBuilder.createConstructionOnly(CHISELED_POLISHED_BLACKSTONE).build();
    BlocksBuilder.createConstructionOnly(GILDED_BLACKSTONE).build();

    BlocksBuilder.createConstructionOnly(CHISELED_NETHER_BRICKS).build();

    // 凝灰岩，方解石。
    BlocksBuilder.createComprehensive(TUFF, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).build();
    BlocksBuilder.createComprehensive(CALCITE, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).build();

    // 涂蜡的铜块。
    for (final Block block : new Block[]{
        WAXED_COPPER_BLOCK,
        WAXED_CUT_COPPER,
        WAXED_EXPOSED_COPPER,
        WAXED_EXPOSED_CUT_COPPER,
        WAXED_WEATHERED_COPPER,
        WAXED_WEATHERED_CUT_COPPER,
        WAXED_OXIDIZED_COPPER,
        WAXED_OXIDIZED_CUT_COPPER
    }) {
      BlocksBuilder.createComprehensive(block, Items.COPPER_INGOT, ButtonType.STONE, ActivationRule.MOBS).build();
    }

    // 滴水石、苔藓。
    BlocksBuilder.createComprehensive(DRIPSTONE_BLOCK, Items.POINTED_DRIPSTONE, ButtonType.STONE, ActivationRule.MOBS).build();
    BlocksBuilder.createComprehensive(MOSS_BLOCK, Items.MOSS_CARPET, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

    // 深板岩变种。深板岩自身属于 PillarBlock，不适合创建形状。
    for (final Block block : new Block[]{COBBLED_DEEPSLATE, POLISHED_DEEPSLATE, DEEPSLATE_TILES, DEEPSLATE_BRICKS, CHISELED_DEEPSLATE}) {
      BlocksBuilder.createComprehensive(block, Items.DEEPSLATE, ButtonType.STONE, ActivationRule.MOBS).build();
    }

    // 平滑玄武岩。
    BlocksBuilder.createComprehensive(SMOOTH_BASALT, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).build();

    // 粗铁、粗铜、粗金。
    BlocksBuilder.createComprehensive(RAW_IRON_BLOCK, Items.RAW_IRON, ButtonType.STONE, ActivationRule.MOBS).build();
    BlocksBuilder.createComprehensive(RAW_COPPER_BLOCK, Items.RAW_COPPER, ButtonType.STONE, ActivationRule.MOBS).build();
    BlocksBuilder.createComprehensive(RAW_GOLD_BLOCK, Items.RAW_GOLD, ButtonType.STONE, ActivationRule.MOBS).build();
  }

  /**
   * 虽然此函数不执行操作，但是执行此函数会确保此类中的静态部分都遍历一遍。
   */
  @SuppressWarnings("EmptyMethod")
  public static void init() {
  }
}
