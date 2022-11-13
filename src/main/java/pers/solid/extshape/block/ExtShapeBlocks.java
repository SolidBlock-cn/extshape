package pers.solid.extshape.block;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.devtech.arrp.generator.BRRPCubeBlock;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock.ActivationRule;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.block.ExtShapeButtonBlock.ButtonType;
import pers.solid.extshape.builder.*;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.mixin.BlockAccessor;
import pers.solid.extshape.tag.ExtShapeBlockTag;
import pers.solid.extshape.tag.ExtShapeTags;

import static net.minecraft.block.Blocks.*;

/**
 * <p>扩展方块形状模组所有的方块都是在此类定义的。类初始化时，就会实例化所有的方块对象。其中，{@linkplain  #PETRIFIED_OAK_PLANKS 石化橡木木板}和{@linkplain #SMOOTH_STONE_DOUBLE_SLAB 双层石台阶}直接以字段的形式储存，其他方块则需要通过 {@link BlockMappings#getBlockOf} 间接访问。
 * <p>在使用此类的内容时尤其需要注意是否完成了初始化。如果在还没有初始化的时候使用本类的内容导致过早初始化，可能会产生错误。例如，Forge 模组是在注册表事件中将此类初始化的，因为此时注册表未冻结，可以注册，如过早或过晚注册则会产生问题。
 * <hr>
 * <p>Blocks in Extended Block Shapes mod are defined in this class. When the class is initialized, all block objects are instantiated. {@linkplain #PETRIFIED_OAK_PLANKS Petrified oak planks} and {@linkplain  #SMOOTH_STONE_DOUBLE_SLAB Smooth stone double slab} are stored directly in fields, while other blocks can be accessed indirectly through {@link BlockMappings#getBlockOf}.
 * <p>It is important to notice whether the initialization is completed when using contents in this class. If you use content in this class before proper initialization and causes it initialized too early, some errors may be thrown. For example, Forge mod completes initialization of this class in registry events, as at that time the registry is unfrozen and allow registration; exceptions are thrown if registered too early or too late.
 */
public final class ExtShapeBlocks {
  /**
   * 存储本模组所有方块的列表。该列表的内容是在 {@link AbstractBlockBuilder#build()} 中添加的。
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
    使用 {@link BlocksBuilder} 并利用迭代器来批量注册多个方块及其对应方块物品，提高效率。
    只有极少数方块会以静态常量成员变量的形式存储。
   */
  static {
    // 石头及其变种（含磨制变种），已存在其楼梯、台阶、墙，但是还没有栅栏和栅栏门。
    for (final Block block : ExtShapeTags.STONES) {
      BlocksBuilder.createAllShapes(block, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).build();
    }

    // 泥土和砂土。其中砂土没有按钮和压力板。
    BlocksBuilder.createAllShapes(DIRT, Items.STICK, ButtonType.SOFT, ActivationRule.EVERYTHING).build();
    BlocksBuilder.createAllShapes(COARSE_DIRT, Items.STICK, null, null).build();

    // 圆石。
    BlocksBuilder.createAllShapes(COBBLESTONE, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).build();

    // 原木、木头、菌柄、菌核及其去皮变种。
    final ImmutableMap<BlockShape, ExtShapeBlockTag> woodenTags = new ImmutableMap.Builder<BlockShape, ExtShapeBlockTag>()
        .put(BlockShape.STAIRS, ExtShapeTags.LOG_STAIRS)
        .put(BlockShape.SLAB, ExtShapeTags.LOG_SLABS)
        .put(BlockShape.VERTICAL_SLAB, ExtShapeTags.LOG_VERTICAL_SLABS)
        .put(BlockShape.VERTICAL_STAIRS, ExtShapeTags.LOG_VERTICAL_STAIRS)
        .put(BlockShape.QUARTER_PIECE, ExtShapeTags.LOG_QUARTER_PIECES)
        .put(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeTags.LOG_VERTICAL_QUARTER_PIECES)
        .put(BlockShape.FENCE, ExtShapeTags.LOG_FENCES)
        .put(BlockShape.FENCE_GATE, ExtShapeTags.LOG_FENCE_GATES)
        .put(BlockShape.BUTTON, ExtShapeTags.LOG_BUTTONS)
        .put(BlockShape.PRESSURE_PLATE, ExtShapeTags.LOG_PRESSURE_PLATES)
        .build();

    ExtShapeTags.LOGS.forEach(block -> BlocksBuilder.createConstructionOnly(block).setPillar().setTagToAddForShape(woodenTags).build());
    ExtShapeTags.STRIPPED_LOGS.forEach(block -> BlocksBuilder.createConstructionOnly(block).setPillar().setTagToAddForShape(woodenTags).build());
    ExtShapeTags.WOODS.forEach(block -> BlocksBuilder.createAllShapes(block, Items.STICK, ButtonType.WOODEN, ActivationRule.EVERYTHING).setPillar().setTagToAddForShape(woodenTags).build());
    ExtShapeTags.STRIPPED_WOODS.forEach(block -> BlocksBuilder.createAllShapes(block, Items.STICK, ButtonType.WOODEN, ActivationRule.EVERYTHING).setPillar().setTagToAddForShape(woodenTags).build());
    ExtShapeTags.STEMS.forEach(block -> BlocksBuilder.createConstructionOnly(block).setPillar().setTagToAddForShape(woodenTags).build());
    ExtShapeTags.STRIPPED_STEMS.forEach(block -> BlocksBuilder.createConstructionOnly(block).setPillar().setTagToAddForShape(woodenTags).build());
    ExtShapeTags.HYPHAES.forEach(block -> BlocksBuilder.createAllShapes(block, Items.STICK, ButtonType.WOODEN, ActivationRule.EVERYTHING).setPillar().setTagToAddForShape(woodenTags).build());
    ExtShapeTags.STRIPPED_HYPHAES.forEach(block -> BlocksBuilder.createAllShapes(block, Items.STICK, ButtonType.WOODEN, ActivationRule.EVERYTHING).setPillar().setTagToAddForShape(woodenTags).build());

    // 木板。
    for (final Block block : ExtShapeTags.PLANKS) {
      BlocksBuilder.createAllShapes(block, null, ButtonType.WOODEN, ActivationRule.EVERYTHING)
          .setTagToAddForShape(BlockShape.VERTICAL_SLAB, ExtShapeTags.WOODEN_VERTICAL_SLABS)
          .setTagToAddForShape(BlockShape.VERTICAL_STAIRS, ExtShapeTags.WOODEN_VERTICAL_STAIRS)
          .setTagToAddForShape(BlockShape.QUARTER_PIECE, ExtShapeTags.WOODEN_QUARTER_PIECES)
          .setTagToAddForShape(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeTags.WOODEN_VERTICAL_QUARTER_PIECES)
          .setTagToAddForShape(BlockShape.WALL, ExtShapeTags.WOODEN_WALLS)
          .build();
    }

    // 石化橡木木板。
    PETRIFIED_OAK_PLANKS = new BlockBuilder()
        .setInstanceSupplier(builder -> BRRPCubeBlock.cubeAll(builder.blockSettings, "block/oak_planks"))
        .setBlockSettings(Block.Settings.copy(PETRIFIED_OAK_SLAB))
        .setIdentifier(new Identifier(ExtShape.MOD_ID, "petrified_oak_planks"))
        .addTagToAdd(ExtShapeTags.PICKAXE_MINEABLE)
        .group(ItemGroup.BUILDING_BLOCKS).build();

    BlockMappings.getMappingOf(BlockShape.SLAB).put(PETRIFIED_OAK_PLANKS, PETRIFIED_OAK_SLAB);

    // 基岩。
    BlocksBuilder.createAllShapes(BEDROCK, Items.STICK, ButtonType.HARD, ActivationRule.MOBS)
        .consumeEachSettings((shape, settings) -> settings.strength(-1.0F, 3600000.0F).allowsSpawning((state, world, pos, type) -> false))
        .build();

    // 青金石块。
    BlocksBuilder.createAllShapes(LAPIS_BLOCK, Items.LAPIS_LAZULI, ButtonType.STONE, ActivationRule.MOBS).build();

    // 砂岩、红砂岩及其切制、錾制、平滑变种。其中，只有平滑砂岩有栅栏、压力板和按钮。
    for (final Block block : ExtShapeTags.SANDSTONES) {
      BlocksBuilder.createConstructionOnly(block).with(BlockShape.WALL).build();
    }
    for (final Block block : new Block[]{SMOOTH_SANDSTONE, SMOOTH_RED_SANDSTONE}) {
      BlocksBuilder.createEmpty(block).withFences(Items.STICK).withPressurePlate(ActivationRule.MOBS).withButton(ButtonType.STONE).build();
    }

    // 羊毛。
    for (final Block block : ExtShapeTags.WOOLS) {
      BlocksBuilder.createAllShapes(block, Items.STRING, ButtonType.SOFT, ActivationRule.EVERYTHING)
          .addTagToAddEach(ExtShapeTags.WOOLEN_BLOCKS)
          .setTagToAddForShape(BlockShape.STAIRS, ExtShapeTags.WOOLEN_STAIRS)
          .setTagToAddForShape(BlockShape.VERTICAL_STAIRS, ExtShapeTags.WOOLEN_VERTICAL_STAIRS)
          .setTagToAddForShape(BlockShape.SLAB, ExtShapeTags.WOOLEN_SLABS)
          .setTagToAddForShape(BlockShape.VERTICAL_SLAB, ExtShapeTags.WOOLEN_VERTICAL_SLABS)
          .setTagToAddForShape(BlockShape.QUARTER_PIECE, ExtShapeTags.WOOLEN_QUARTER_PIECES)
          .setTagToAddForShape(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeTags.WOOLEN_VERTICAL_QUARTER_PIECES)
          .setTagToAddForShape(BlockShape.FENCE, ExtShapeTags.WOOLEN_FENCES)
          .setTagToAddForShape(BlockShape.FENCE_GATE, ExtShapeTags.WOOLEN_FENCE_GATES)
          .setTagToAddForShape(BlockShape.BUTTON, ExtShapeTags.WOOLEN_BUTTONS)
          .setTagToAddForShape(BlockShape.PRESSURE_PLATE, ExtShapeTags.WOOLEN_PRESSURE_PLATES)
          .setTagToAddForShape(BlockShape.WALL, ExtShapeTags.WOOLEN_WALLS)
          .build();
    }

    // 金块。
    BlocksBuilder.createAllShapes(GOLD_BLOCK, Items.GOLD_INGOT, ButtonType.STONE, null).build();
    // 铁块。
    BlocksBuilder.createAllShapes(IRON_BLOCK, Items.IRON_INGOT, ButtonType.STONE, null).build();

    // 砖栅栏和栅栏门。
    BlocksBuilder.createConstructionOnly(BRICKS).withFences(Items.BRICK).with(BlockShape.WALL).build();

    // 苔石栅栏和栅栏门。
    BlocksBuilder.createAllShapes(MOSSY_COBBLESTONE, Items.STICK, ButtonType.STONE, ActivationRule.MOBS).build();

    // 黑曜石。
    BlocksBuilder.createAllShapes(OBSIDIAN, Items.STONE, ButtonType.HARD, ActivationRule.MOBS).build();

    // 钻石块。
    BlocksBuilder.createAllShapes(DIAMOND_BLOCK, Items.DIAMOND, ButtonType.HARD, ActivationRule.MOBS).build();

    // 紫水晶块。
    BlocksBuilder.createAllShapes(AMETHYST_BLOCK, Items.AMETHYST_SHARD, ButtonType.STONE, ActivationRule.MOBS).withoutRedstone().build();

    // 冰，由于技术原因，暂不产生。

    // 雪块。
    BlocksBuilder.createAllShapes(SNOW_BLOCK, Items.SNOW, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

    // 黏土块。
    BlocksBuilder.createAllShapes(CLAY, Items.CLAY_BALL, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

    // 南瓜。
    BlocksBuilder.createAllShapes(PUMPKIN, Items.PUMPKIN_SEEDS, ButtonType.WOODEN, ActivationRule.EVERYTHING).build();

    // 下界岩。
    BlocksBuilder.createAllShapes(NETHERRACK, Items.NETHER_BRICK, ButtonType.STONE, ActivationRule.MOBS).build();

    // 荧石可以发光。
    BlocksBuilder.createAllShapes(GLOWSTONE, Items.GLOWSTONE_DUST, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

    // 石砖、苔石砖。
    for (final Block block : new Block[]{STONE_BRICKS, MOSSY_STONE_BRICKS, CHISELED_STONE_BRICKS}) {
      BlocksBuilder.createConstructionOnly(block).withFences(Items.FLINT).with(BlockShape.WALL).build();
    }

    // 泥（自 1.19）。
    for (final Block block : new Block[]{PACKED_MUD, MUD_BRICKS}) {
      BlocksBuilder.createAllShapes(block, Items.MUD, ButtonType.STONE, ActivationRule.MOBS).build();
    }

    // 西瓜。
    BlocksBuilder.createAllShapes(MELON, Items.MELON_SLICE, ButtonType.WOODEN, ActivationRule.EVERYTHING).build();

    // 下界砖块的栅栏门、按钮和压力板。
    BlocksBuilder.createAllShapes(NETHER_BRICKS, Items.NETHER_BRICK, ButtonType.STONE, ActivationRule.MOBS).withoutRedstone().build();

    // 末地石、末地石砖。
    BlocksBuilder.createAllShapes(END_STONE, Items.END_STONE_BRICKS, ButtonType.STONE, ActivationRule.MOBS).build();

    // 绿宝石块。
    BlocksBuilder.createAllShapes(EMERALD_BLOCK, Items.EMERALD, ButtonType.STONE, ActivationRule.MOBS).build();

    // 石英、石英砖、平滑石英块、錾制石英块均有按钮和压力板。
    for (final Block block : new Block[]{QUARTZ_BLOCK, CHISELED_QUARTZ_BLOCK, QUARTZ_BRICKS, SMOOTH_QUARTZ}) {
      BlocksBuilder.createAllShapes(block, Items.QUARTZ, ButtonType.STONE, ActivationRule.MOBS).build();
    }

    // 红色下界砖。
    BlocksBuilder.createAllShapes(RED_NETHER_BRICKS, Items.NETHER_BRICK, ButtonType.STONE, ActivationRule.MOBS).build();

    // 陶瓦和彩色陶瓦。
    BlocksBuilder.createAllShapes(TERRACOTTA, Items.CLAY, ButtonType.STONE, ActivationRule.MOBS)
        .setTagToAddForShape(BlockShape.STAIRS, ExtShapeTags.TERRACOTTA_STAIRS)
        .setTagToAddForShape(BlockShape.VERTICAL_STAIRS, ExtShapeTags.TERRACOTTA_VERTICAL_STAIRS)
        .setTagToAddForShape(BlockShape.SLAB, ExtShapeTags.TERRACOTTA_SLABS)
        .setTagToAddForShape(BlockShape.VERTICAL_SLAB, ExtShapeTags.TERRACOTTA_VERTICAL_SLABS)
        .setTagToAddForShape(BlockShape.QUARTER_PIECE, ExtShapeTags.TERRACOTTA_QUARTER_PIECES)
        .setTagToAddForShape(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeTags.TERRACOTTA_VERTICAL_QUARTER_PIECES)
        .setTagToAddForShape(BlockShape.FENCE, ExtShapeTags.TERRACOTTA_FENCES)
        .setTagToAddForShape(BlockShape.FENCE_GATE, ExtShapeTags.TERRACOTTA_FENCE_GATES)
        .setTagToAddForShape(BlockShape.WALL, ExtShapeTags.TERRACOTTA_WALLS)
        .setTagToAddForShape(BlockShape.BUTTON, ExtShapeTags.TERRACOTTA_BUTTONS)
        .setTagToAddForShape(BlockShape.PRESSURE_PLATE, ExtShapeTags.TERRACOTTA_PRESSURE_PLATES)
        .build();
    for (final Block block : ExtShapeTags.STAINED_TERRACOTTA) {
      BlocksBuilder.createAllShapes(block, Items.CLAY, ButtonType.STONE, ActivationRule.MOBS)
          .setTagToAddForShape(BlockShape.STAIRS, ExtShapeTags.STAINED_TERRACOTTA_STAIRS)
          .setTagToAddForShape(BlockShape.VERTICAL_STAIRS, ExtShapeTags.STAINED_TERRACOTTA_VERTICAL_STAIRS)
          .setTagToAddForShape(BlockShape.SLAB, ExtShapeTags.STAINED_TERRACOTTA_SLABS)
          .setTagToAddForShape(BlockShape.VERTICAL_SLAB, ExtShapeTags.STAINED_TERRACOTTA_VERTICAL_SLABS)
          .setTagToAddForShape(BlockShape.QUARTER_PIECE, ExtShapeTags.STAINED_TERRACOTTA_QUARTER_PIECES)
          .setTagToAddForShape(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeTags.STAINED_TERRACOTTA_VERTICAL_QUARTER_PIECES)
          .setTagToAddForShape(BlockShape.FENCE, ExtShapeTags.STAINED_TERRACOTTA_FENCES)
          .setTagToAddForShape(BlockShape.FENCE_GATE, ExtShapeTags.STAINED_TERRACOTTA_FENCE_GATES)
          .setTagToAddForShape(BlockShape.WALL, ExtShapeTags.STAINED_TERRACOTTA_WALLS)
          .setTagToAddForShape(BlockShape.BUTTON, ExtShapeTags.STAINED_TERRACOTTA_BUTTONS)
          .setTagToAddForShape(BlockShape.PRESSURE_PLATE, ExtShapeTags.STAINED_TERRACOTTA_PRESSURE_PLATES)
          .build();
    }

    // 煤炭块。
    BlocksBuilder.createAllShapes(COAL_BLOCK, Items.COAL, ButtonType.STONE, ActivationRule.MOBS).build();

    // 浮冰。
    BlocksBuilder.createAllShapes(PACKED_ICE, Items.ICE, ButtonType.STONE, ActivationRule.MOBS).build();

    // 海晶石、海晶石砖、暗海晶石。
    for (final Block block : new Block[]{PRISMARINE, PRISMARINE_BRICKS, DARK_PRISMARINE}) {
      BlocksBuilder.createAllShapes(block, Items.PRISMARINE_SHARD, ButtonType.STONE, ActivationRule.MOBS).build();
    }

    // 海晶灯。
    BlocksBuilder.createConstructionOnly(SEA_LANTERN).build();

    // 平滑石头比较特殊，完整方块和台阶不同。
    SMOOTH_STONE_DOUBLE_SLAB = new BlockBuilder()
        .setInstanceSupplier(builder -> BRRPCubeBlock.cubeBottomTop(builder.blockSettings, "block/smooth_stone", "block/smooth_stone_slab_side", "block/smooth_stone"))
        .setBlockSettings(Block.Settings.copy(SMOOTH_STONE))
        .setIdentifier(new Identifier(ExtShape.MOD_ID, "smooth_stone_slab_double"))
        .addTagToAdd(ExtShapeTags.PICKAXE_MINEABLE)
        .group(ItemGroup.BUILDING_BLOCKS).build();

    BlocksBuilder.createAllShapes(SMOOTH_STONE, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).withoutConstructionShapes().build();

    BlockMappings.getMappingOf(BlockShape.SLAB).put(SMOOTH_STONE_DOUBLE_SLAB, SMOOTH_STONE_SLAB);

    // 紫珀块。
    BlocksBuilder.createAllShapes(PURPUR_BLOCK, Items.SHULKER_SHELL, ButtonType.STONE, ActivationRule.MOBS).build();

    // 下界疣块、诡异疣块。
    BlocksBuilder.createAllShapes(NETHER_WART_BLOCK, Items.NETHER_WART, null, null).withoutRedstone().build();
    BlocksBuilder.createAllShapes(WARPED_WART_BLOCK, Items.NETHER_WART, null, null).withoutRedstone().build();

    // 带釉陶瓦只注册台阶。
    for (final Block block : ExtShapeTags.GLAZED_TERRACOTTA) {
      new SlabBuilder(block).setInstanceSupplier(builder -> new GlazedTerracottaSlabBlock(builder.baseBlock, Block.Settings.copy(builder.baseBlock))).setDefaultTagToAdd(ExtShapeTags.GLAZED_TERRACOTTA_SLABS).build();
    }

    // 彩色混凝土。
    for (final Block block : ExtShapeTags.CONCRETES) {
      BlocksBuilder.createAllShapes(block, Items.GRAVEL, ButtonType.STONE, ActivationRule.MOBS)
          .setTagToAddForShape(BlockShape.STAIRS, ExtShapeTags.CONCRETE_STAIRS)
          .setTagToAddForShape(BlockShape.VERTICAL_STAIRS, ExtShapeTags.CONCRETE_VERTICAL_STAIRS)
          .setTagToAddForShape(BlockShape.SLAB, ExtShapeTags.CONCRETE_SLABS)
          .setTagToAddForShape(BlockShape.VERTICAL_SLAB, ExtShapeTags.CONCRETE_VERTICAL_SLABS)
          .setTagToAddForShape(BlockShape.QUARTER_PIECE, ExtShapeTags.CONCRETE_QUARTER_PIECES)
          .setTagToAddForShape(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeTags.CONCRETE_VERTICAL_QUARTER_PIECES)
          .setTagToAddForShape(BlockShape.FENCE, ExtShapeTags.CONCRETE_FENCES)
          .setTagToAddForShape(BlockShape.FENCE_GATE, ExtShapeTags.CONCRETE_FENCE_GATES)
          .setTagToAddForShape(BlockShape.WALL, ExtShapeTags.CONCRETE_WALLS)
          .setTagToAddForShape(BlockShape.BUTTON, ExtShapeTags.CONCRETE_BUTTONS)
          .setTagToAddForShape(BlockShape.PRESSURE_PLATE, ExtShapeTags.CONCRETE_PRESSURE_PLATES)
          .build();
    }

    // 菌光体。
    BlocksBuilder.createAllShapes(SHROOMLIGHT, Items.GLOWSTONE_DUST, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

    // 蜜脾块。
    BlocksBuilder.createAllShapes(HONEYCOMB_BLOCK, Items.HONEYCOMB, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

    // 下界合金方块。
    BlocksBuilder.createAllShapes(NETHERITE_BLOCK, Items.NETHERITE_INGOT, ButtonType.HARD, ActivationRule.MOBS).build();

    // 远古残骸。
    BlocksBuilder.createAllShapes(ANCIENT_DEBRIS, Items.NETHERITE_SCRAP, ButtonType.HARD, ActivationRule.MOBS).build();

    // 哭泣的黑曜石。
    BlocksBuilder.createAllShapes(CRYING_OBSIDIAN, Items.STONE, ButtonType.HARD, ActivationRule.MOBS).build();

    // 黑石及其变种。
    BlocksBuilder.createConstructionOnly(BLACKSTONE).build();
    BlocksBuilder.createConstructionOnly(POLISHED_BLACKSTONE).build();
    BlocksBuilder.createConstructionOnly(POLISHED_BLACKSTONE_BRICKS).build();
    BlocksBuilder.createConstructionOnly(CHISELED_POLISHED_BLACKSTONE).build();
    BlocksBuilder.createConstructionOnly(GILDED_BLACKSTONE).build();

    BlocksBuilder.createConstructionOnly(CHISELED_NETHER_BRICKS).build();

    // 凝灰岩，方解石。
    BlocksBuilder.createAllShapes(TUFF, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).build();
    BlocksBuilder.createAllShapes(CALCITE, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).build();

    // 幽匿块。
    BlocksBuilder.createConstructionOnly(SCULK).withExtension(BlockExtension.builder().setStacksDroppedCallback((state, world, pos, stack, dropExperience) -> ((BlockAccessor) state.getBlock()).callDropExperienceWhenMined(world, pos, stack, ConstantIntProvider.create(1))).build()).build();

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
      BlocksBuilder.createAllShapes(block, Items.COPPER_INGOT, ButtonType.STONE, ActivationRule.MOBS).build();
    }

    // 滴水石、苔藓。
    BlocksBuilder.createAllShapes(DRIPSTONE_BLOCK, Items.POINTED_DRIPSTONE, ButtonType.STONE, ActivationRule.MOBS).build();
    BlocksBuilder.createAllShapes(MOSS_BLOCK, Items.MOSS_CARPET, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

    // 深板岩。
    BlocksBuilder.createAllShapes(DEEPSLATE, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).setPillar().build();
    for (final Block block : new Block[]{COBBLED_DEEPSLATE, POLISHED_DEEPSLATE, DEEPSLATE_TILES, DEEPSLATE_BRICKS, CHISELED_DEEPSLATE}) {
      BlocksBuilder.createAllShapes(block, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).build();
    }

    // 玄武岩及其变种。
    BlocksBuilder.createAllShapes(BASALT, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).setPillar().build();
    BlocksBuilder.createAllShapes(POLISHED_BASALT, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).setPillar().build();
    BlocksBuilder.createAllShapes(SMOOTH_BASALT, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).build();

    // 粗铁、粗铜、粗金。
    BlocksBuilder.createAllShapes(RAW_IRON_BLOCK, Items.RAW_IRON, ButtonType.STONE, ActivationRule.MOBS).build();
    BlocksBuilder.createAllShapes(RAW_COPPER_BLOCK, Items.RAW_COPPER, ButtonType.STONE, ActivationRule.MOBS).build();
    BlocksBuilder.createAllShapes(RAW_GOLD_BLOCK, Items.RAW_GOLD, ButtonType.STONE, ActivationRule.MOBS).build();

    // 蛙明灯。
    BlocksBuilder.createAllShapes(OCHRE_FROGLIGHT, Items.SLIME_BALL, ButtonType.WOODEN, ActivationRule.EVERYTHING).setPillar().build();
    BlocksBuilder.createAllShapes(VERDANT_FROGLIGHT, Items.SLIME_BALL, ButtonType.WOODEN, ActivationRule.EVERYTHING).setPillar().build();
    BlocksBuilder.createAllShapes(PEARLESCENT_FROGLIGHT, Items.SLIME_BALL, ButtonType.WOODEN, ActivationRule.EVERYTHING).setPillar().build();
  }

  private ExtShapeBlocks() {
  }

  /**
   * 虽然此函数不执行操作，但是执行此函数会确保此类中的静态部分都遍历一遍。
   */
  @SuppressWarnings("EmptyMethod")
  public static void init() {
  }
}
