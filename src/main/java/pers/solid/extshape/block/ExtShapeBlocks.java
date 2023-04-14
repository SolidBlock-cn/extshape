package pers.solid.extshape.block;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock.ActivationRule;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.UnmodifiableView;
import pers.solid.brrp.v1.api.RuntimeResourcePack;
import pers.solid.brrp.v1.generator.BRRPCubeBlock;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.block.ExtShapeButtonBlock.ButtonType;
import pers.solid.extshape.builder.*;
import pers.solid.extshape.mixin.AbstractBlockSettingsAccessor;
import pers.solid.extshape.rrp.RecipeGroupRegistry;
import pers.solid.extshape.tag.ExtShapeTags;
import pers.solid.extshape.util.BlockBiMaps;
import pers.solid.extshape.util.BlockCollections;

import static net.minecraft.block.Blocks.*;

/**
 * <p>扩展方块形状模组所有的方块都是在此类定义的。类初始化时，就会实例化所有的方块对象。其中，{@linkplain  #PETRIFIED_OAK_PLANKS 石化橡木木板}和{@linkplain #SMOOTH_STONE_DOUBLE_SLAB 双层石台阶}直接以字段的形式储存，其他方块则需要通过 {@link BlockBiMaps#getBlockOf} 间接访问。
 * <p>在使用此类的内容时尤其需要注意是否完成了初始化。如果在还没有初始化的时候使用本类的内容导致过早初始化，可能会产生错误。例如，Forge 模组是在注册表事件中将此类初始化的，因为此时注册表未冻结，可以注册，如过早或过晚注册则会产生问题。
 * <hr>
 * <p>Blocks in Extended Block Shapes mod are defined in this class. When the class is initialized, all block objects are instantiated. {@linkplain #PETRIFIED_OAK_PLANKS Petrified oak planks} and {@linkplain  #SMOOTH_STONE_DOUBLE_SLAB Smooth stone double slab} are stored directly in fields, while other blocks can be accessed indirectly through {@link BlockBiMaps#getBlockOf}.
 * <p>It is important to notice whether the initialization is completed when using contents in this class. If you use content in this class before proper initialization and causes it initialized too early, some errors may be thrown. For example, Forge mod completes initialization of this class in registry events, as at that time the registry is unfrozen and allow registration; exceptions are thrown if registered too early or too late.
 */
public final class ExtShapeBlocks {
  /**
   * 存储本模组所有方块的列表。该列表的内容是在 {@link AbstractBlockBuilder#build()} 中添加的。其他模组添加的方块（即使使用了本模组的接口）不应该添加到这个集合中，而应该自行建立集合。
   */
  private static final ObjectSet<Block> BLOCKS = new ObjectLinkedOpenHashSet<>();

  /**
   * 获取本模组中的所有方块，返回的集合是不可变集合。
   */
  public static @UnmodifiableView ObjectSet<Block> getBlocks() {
    return ObjectSets.unmodifiable(BLOCKS);
  }

  /**
   * 存储本模组生成的方块的基础方块（包含原版方块）。该集合的内容是在 {@link BlocksBuilderFactory} 中添加的，其他模组使用的基础方块不应该添加到这个集合中。{@link pers.solid.extshape.rrp.ExtShapeRRP#generateServerData(RuntimeResourcePack)} 会使用这个集合，因为它不应该为使用了本模组接口的其他模组生成数据。
   */
  private static final ObjectSet<Block> BASE_BLOCKS = new ObjectLinkedOpenHashSet<>();

  /**
   * 获取本模组使用的所有基础方块（含原版方块），返回的集合是不可变集合。
   */
  public static @UnmodifiableView ObjectSet<Block> getBaseBlocks() {
    return ObjectSets.unmodifiable(BASE_BLOCKS);
  }

  /**
   * 用于生成本模组的方块。由于仅限本模组，故不对外公开。
   */
  private static final BlocksBuilderFactory FACTORY = Util.make(new BlocksBuilderFactory(), blocksBuilderFactory -> {
    blocksBuilderFactory.defaultNamespace = ExtShape.MOD_ID;
    blocksBuilderFactory.instanceCollection = BLOCKS;
    blocksBuilderFactory.baseBlockCollection = BASE_BLOCKS;
    blocksBuilderFactory.tagPreparations = ExtShapeTags.TAG_PREPARATIONS;
  });
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
    for (final Block block : BlockCollections.STONES) {
      FACTORY.createAllShapes(block)
          .setFenceCraftingIngredient(Items.FLINT)
          .setButtonType(ButtonType.STONE)
          .setPressurePlateActivationRule(ActivationRule.MOBS).build();
    }

    // 泥土和砂土。其中砂土没有按钮和压力板。
    FACTORY.createAllShapes(DIRT)
        .setFenceCraftingIngredient(Items.STICK)
        .addPreBuildConsumer((blockShape, blockBuilder) -> {
          if (blockShape.isConstruction) {
            blockBuilder.addExtraTag(BlockTags.ENDERMAN_HOLDABLE);
            blockBuilder.addExtraTag(BlockTags.BAMBOO_PLANTABLE_ON);
          }
        })
        .setButtonType(ButtonType.SOFT)
        .setPressurePlateActivationRule(ActivationRule.EVERYTHING).build();
    FACTORY.createAllShapes(COARSE_DIRT)
        .setFenceCraftingIngredient(Items.STICK)
        .setButtonType(null)
        .setPressurePlateActivationRule(null).build();

    // 圆石。
    FACTORY.createAllShapes(COBBLESTONE)
        .setFenceCraftingIngredient(Items.FLINT)
        .setPressurePlateActivationRule(ActivationRule.MOBS).build();

    // 原木、木头、菌柄、菌核及其去皮变种。
    final ImmutableMap<BlockShape, Tag.Identified<? extends ItemConvertible>> logTags = ImmutableMap.copyOf(ExtShapeTags.SHAPE_TO_LOG_TAG);
    final ImmutableMap<BlockShape, Tag.Identified<? extends ItemConvertible>> woodenTags = ImmutableMap.copyOf(ExtShapeTags.SHAPE_TO_WOODEN_TAG);

    // 原木和竹子。
    for (final Block block : BlockCollections.LOGS) {
      FACTORY.createConstructionOnly(block)
          .setPillar()
          .setPrimaryTagForShape(logTags)
          .setRecipeGroup(blockShape -> "log_" + blockShape.asString())
          .build();
    }

    // 去皮的原木。
    for (final Block block : BlockCollections.STRIPPED_LOGS) {
      FACTORY.createConstructionOnly(block)
          .setPillar(false)
          .setPrimaryTagForShape(logTags)
          .setRecipeGroup(blockShape -> "stripped_log_" + blockShape.asString())
          .build();
    }

    for (final Block block : BlockCollections.WOODS) {
      FACTORY.createAllShapes(block)
          .setButtonType(ButtonType.WOODEN)
          .setPressurePlateActivationRule(ActivationRule.EVERYTHING).setPillar()
          .setPrimaryTagForShape(logTags)
          .setRecipeGroup(blockShape -> "wood_" + blockShape.asString())
          .build();
    }
    for (final Block block : BlockCollections.STRIPPED_WOODS) {
      FACTORY.createAllShapes(block)
          .setButtonType(ButtonType.WOODEN)
          .setPressurePlateActivationRule(ActivationRule.EVERYTHING).setPillar()
          .setPrimaryTagForShape(logTags)
          .setRecipeGroup(blockShape -> "stripped_wood_" + blockShape.asString())
          .build();
    }
    for (final Block block : BlockCollections.STEMS) {
      FACTORY.createConstructionOnly(block).setPillar().setPrimaryTagForShape(logTags).addExtraTag(ItemTags.NON_FLAMMABLE_WOOD).setRecipeGroup(blockShape -> "log_" + blockShape.asString()).build();
    }
    for (final Block block : BlockCollections.STRIPPED_STEMS) {
      FACTORY.createConstructionOnly(block).setPillar().setPrimaryTagForShape(logTags).addExtraTag(ItemTags.NON_FLAMMABLE_WOOD).setRecipeGroup(blockShape -> "stripped_log_" + blockShape.asString()).build();
    }
    for (final Block block : BlockCollections.HYPHAES) {
      ButtonType buttonSettings = ButtonType.WOODEN;
      FACTORY.createAllShapes(block)
          .setButtonType(buttonSettings)
          .setPressurePlateActivationRule(ActivationRule.EVERYTHING).setPillar().setPrimaryTagForShape(logTags).addExtraTag(ItemTags.NON_FLAMMABLE_WOOD).setRecipeGroup(blockShape -> "wood_" + blockShape.asString()).build();
    }
    for (final Block block : BlockCollections.STRIPPED_HYPHAES) {
      FACTORY.createAllShapes(block)
          .setButtonType(ButtonType.WOODEN)
          .setPressurePlateActivationRule(ActivationRule.EVERYTHING).setPillar().setPrimaryTagForShape(logTags).addExtraTag(ItemTags.NON_FLAMMABLE_WOOD).setRecipeGroup(blockShape -> "stripped_wood_" + blockShape.asString()).build();
    }

    // 木板。
    for (final Block block : BlockCollections.PLANKS) {
      if (block == CRIMSON_PLANKS || block == WARPED_PLANKS) {
        FACTORY.createAllShapes(block)
            .setFenceCraftingIngredient(null)
            .setButtonType(ButtonType.WOODEN)
            .setPressurePlateActivationRule(ActivationRule.EVERYTHING)
            .setPrimaryTagForShape(woodenTags)
            .addExtraTag(ItemTags.NON_FLAMMABLE_WOOD)
            .setRecipeGroup(blockShape -> "wooden_" + blockShape.asString())
            .build();
      } else {
        FACTORY.createAllShapes(block)
            .setFenceCraftingIngredient(null)
            .setButtonType(ButtonType.WOODEN)
            .setPressurePlateActivationRule(ActivationRule.EVERYTHING)
            .setPrimaryTagForShape(woodenTags)
            .setRecipeGroup(blockShape -> "wooden_" + blockShape.asString())
            .build();
      }
    }

    // 石化橡木木板。
    PETRIFIED_OAK_PLANKS = FACTORY.modify(new BlockBuilder())
        .setInstanceSupplier(builder -> BRRPCubeBlock.cubeAll(builder.blockSettings, new Identifier("minecraft", "block/oak_planks")))
        .setBlockSettings(AbstractBlock.Settings.copy(PETRIFIED_OAK_SLAB))
        .setIdentifier(new Identifier(ExtShape.MOD_ID, "petrified_oak_planks"))
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .group(ItemGroup.BUILDING_BLOCKS).build();

    BlockBiMaps.of(BlockShape.SLAB).put(PETRIFIED_OAK_PLANKS, PETRIFIED_OAK_SLAB);

    // 基岩。
    FACTORY.createAllShapes(BEDROCK)
        .addExtraTag(BlockTags.INFINIBURN_END)
        .addExtraTag(BlockTags.WITHER_IMMUNE)
        .setFenceCraftingIngredient(Items.STICK)
        .setButtonType(ButtonType.HARD)
        .setPressurePlateActivationRule(ActivationRule.MOBS)
        .addPreBuildConsumer((blockShape1, builder1) -> {
          builder1.blockSettings.strength(-1.0F, 3600000.0F).allowsSpawning((state1, world1, pos1, type) -> false);
          ((AbstractBlockSettingsAccessor) builder1.blockSettings).setLootTableId(null);
          ((AbstractBlockSettingsAccessor) builder1.blockSettings).setLootTableSupplier(null);
        })
        .build();

    // 青金石块。
    FACTORY.createAllShapes(LAPIS_BLOCK)
        .setFenceCraftingIngredient(Items.LAPIS_LAZULI)
        .setPressurePlateActivationRule(ActivationRule.MOBS).build();

    // 砂岩、红砂岩及其切制、錾制、平滑变种。其中，只有平滑砂岩有栅栏、压力板和按钮。
    for (final Block block : BlockCollections.SANDSTONES) {
      FACTORY.createConstructionOnly(block).with(BlockShape.WALL).build();
    }
    for (final Block block : new Block[]{SMOOTH_SANDSTONE, SMOOTH_RED_SANDSTONE}) {
      FACTORY.createEmpty(block).withFences(Items.STICK).withPressurePlate(ActivationRule.MOBS).withButton(ButtonType.STONE).build();
    }

    // 羊毛。
    for (final Block block : BlockCollections.WOOLS) {
      FACTORY.createAllShapes(block)
          .setFenceCraftingIngredient(Items.STRING)
          .setButtonType(ButtonType.SOFT)
          .setPressurePlateActivationRule(ActivationRule.EVERYTHING)
          .setPrimaryTagForShape(BlockShape.STAIRS, ExtShapeTags.WOOLEN_STAIRS)
          .setPrimaryTagForShape(BlockShape.VERTICAL_STAIRS, ExtShapeTags.WOOLEN_VERTICAL_STAIRS)
          .setPrimaryTagForShape(BlockShape.SLAB, ExtShapeTags.WOOLEN_SLABS)
          .setPrimaryTagForShape(BlockShape.VERTICAL_SLAB, ExtShapeTags.WOOLEN_VERTICAL_SLABS)
          .setPrimaryTagForShape(BlockShape.QUARTER_PIECE, ExtShapeTags.WOOLEN_QUARTER_PIECES)
          .setPrimaryTagForShape(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeTags.WOOLEN_VERTICAL_QUARTER_PIECES)
          .setPrimaryTagForShape(BlockShape.FENCE, ExtShapeTags.WOOLEN_FENCES)
          .setPrimaryTagForShape(BlockShape.FENCE_GATE, ExtShapeTags.WOOLEN_FENCE_GATES)
          .setPrimaryTagForShape(BlockShape.BUTTON, ExtShapeTags.WOOLEN_BUTTONS)
          .setPrimaryTagForShape(BlockShape.PRESSURE_PLATE, ExtShapeTags.WOOLEN_PRESSURE_PLATES)
          .setPrimaryTagForShape(BlockShape.WALL, ExtShapeTags.WOOLEN_WALLS)
          .setRecipeGroup(blockShape -> "wool_" + blockShape.asString())
          .build();
    }

    // 金块。
    FACTORY.createAllShapes(GOLD_BLOCK)
        .setFenceCraftingIngredient(Items.GOLD_INGOT)
        .addExtraTag(ItemTags.PIGLIN_LOVED)
        .addExtraTag(BlockTags.GUARDED_BY_PIGLINS)
        .setButtonType(ButtonType.STONE)
        .setPressurePlateActivationRule(null).build();
    // 铁块。
    FACTORY.createAllShapes(IRON_BLOCK)
        .setFenceCraftingIngredient(Items.IRON_INGOT)
        .setButtonType(ButtonType.STONE)
        .setPressurePlateActivationRule(null).build();

    // 砖栅栏和栅栏门。
    FACTORY.createConstructionOnly(BRICKS).withFences(Items.BRICK).with(BlockShape.WALL).build();

    // 苔石栅栏和栅栏门。
    FACTORY.createAllShapes(MOSSY_COBBLESTONE)
        .setFenceCraftingIngredient(Items.STICK)
        .setPressurePlateActivationRule(ActivationRule.MOBS).build();

    // 黑曜石。
    FACTORY.createAllShapes(OBSIDIAN)
        .setFenceCraftingIngredient(Items.STONE)
        .addExtraTag(BlockTags.DRAGON_IMMUNE)
        .setButtonType(ButtonType.HARD)
        .setPressurePlateActivationRule(ActivationRule.MOBS).build();

    // 钻石块。
    FACTORY.createAllShapes(DIAMOND_BLOCK)
        .setFenceCraftingIngredient(Items.DIAMOND)
        .setButtonType(ButtonType.HARD)
        .setPressurePlateActivationRule(ActivationRule.MOBS).build();

    // 紫水晶块。
    FACTORY.createAllShapes(AMETHYST_BLOCK)
        .setFenceCraftingIngredient(Items.AMETHYST_SHARD)
        .withExtension(BlockExtension.AMETHYST)
        .setPressurePlateActivationRule(ActivationRule.MOBS).withoutRedstone().build();

    // 冰，由于技术原因，暂不产生。

    // 雪块。
    FACTORY.createAllShapes(SNOW_BLOCK)
        .setFenceCraftingIngredient(Items.SNOW)
        .addExtraTag(ExtShapeTags.SNOW)
        .setButtonType(ButtonType.SOFT)
        .setPressurePlateActivationRule(ActivationRule.EVERYTHING).build();

    // 黏土块。
    FACTORY.createAllShapes(CLAY)
        .setFenceCraftingIngredient(Items.CLAY_BALL)
        .setButtonType(ButtonType.SOFT)
        .addExtraTag(shape -> shape.isConstruction ? BlockTags.SMALL_DRIPLEAF_PLACEABLE : null)
        .addExtraTag(shape -> shape.isConstruction ? BlockTags.ENDERMAN_HOLDABLE : null)
        .setPressurePlateActivationRule(ActivationRule.EVERYTHING).build();

    // 南瓜。
    FACTORY.createAllShapes(PUMPKIN)
        .setFenceCraftingIngredient(Items.PUMPKIN_SEEDS)
        .setButtonType(ButtonType.WOODEN)
        .addExtraTag(shape -> shape.isConstruction ? BlockTags.ENDERMAN_HOLDABLE : null)
        .compostingChance(0.65f)
        .setPressurePlateActivationRule(ActivationRule.EVERYTHING).build();

    // 下界岩。
    FACTORY.createAllShapes(NETHERRACK)
        .setFenceCraftingIngredient(Items.NETHER_BRICK)
        .addExtraTag(BlockTags.INFINIBURN_OVERWORLD)
        .setPressurePlateActivationRule(ActivationRule.MOBS).build();

    // 荧石可以发光。
    FACTORY.createAllShapes(GLOWSTONE)
        .setFenceCraftingIngredient(Items.GLOWSTONE_DUST)
        .setButtonType(ButtonType.SOFT)
        .setPressurePlateActivationRule(ActivationRule.EVERYTHING).build();

    // 石砖、苔石砖。
    for (final Block block : new Block[]{STONE_BRICKS, MOSSY_STONE_BRICKS, CHISELED_STONE_BRICKS}) {
      FACTORY.createConstructionOnly(block).withFences(Items.FLINT).with(BlockShape.WALL).build();
    }

    // 西瓜。
    FACTORY.createAllShapes(MELON)
        .setFenceCraftingIngredient(Items.MELON_SLICE)
        .setButtonType(ButtonType.WOODEN)
        .addExtraTag(shape -> shape.isConstruction ? BlockTags.ENDERMAN_HOLDABLE : null)
        .compostingChance(0.65f)
        .setPressurePlateActivationRule(ActivationRule.EVERYTHING).build();

    // 下界砖块的栅栏门、按钮和压力板。
    FACTORY.createAllShapes(NETHER_BRICKS)
        .setFenceCraftingIngredient(Items.NETHER_BRICK)
        .setPressurePlateActivationRule(ActivationRule.MOBS).withoutRedstone().build();

    // 末地石、末地石砖。
    FACTORY.createAllShapes(END_STONE)
        .setFenceCraftingIngredient(Items.END_STONE_BRICKS)
        .addExtraTag(BlockTags.DRAGON_IMMUNE)
        .setPressurePlateActivationRule(ActivationRule.MOBS).build();

    // 绿宝石块。
    FACTORY.createAllShapes(EMERALD_BLOCK)
        .setFenceCraftingIngredient(Items.EMERALD)
        .setPressurePlateActivationRule(ActivationRule.MOBS).build();

    // 石英、石英砖、平滑石英块、錾制石英块均有按钮和压力板。
    for (final Block block : new Block[]{QUARTZ_BLOCK, CHISELED_QUARTZ_BLOCK, QUARTZ_BRICKS, SMOOTH_QUARTZ}) {
      FACTORY.createAllShapes(block)
          .setFenceCraftingIngredient(Items.QUARTZ)
          .setButtonType(ButtonType.STONE)
          .setPressurePlateActivationRule(ActivationRule.MOBS).build();
    }

    // 红色下界砖。
    FACTORY.createAllShapes(RED_NETHER_BRICKS)
        .setFenceCraftingIngredient(Items.NETHER_BRICK)
        .setPressurePlateActivationRule(ActivationRule.MOBS).build();

    // 陶瓦和彩色陶瓦。
    FACTORY.createAllShapes(TERRACOTTA)
        .setFenceCraftingIngredient(Items.CLAY)
        .setPressurePlateActivationRule(ActivationRule.MOBS)
        .setPrimaryTagForShape(BlockShape.STAIRS, ExtShapeTags.TERRACOTTA_STAIRS)
        .setPrimaryTagForShape(BlockShape.VERTICAL_STAIRS, ExtShapeTags.TERRACOTTA_VERTICAL_STAIRS)
        .setPrimaryTagForShape(BlockShape.SLAB, ExtShapeTags.TERRACOTTA_SLABS)
        .setPrimaryTagForShape(BlockShape.VERTICAL_SLAB, ExtShapeTags.TERRACOTTA_VERTICAL_SLABS)
        .setPrimaryTagForShape(BlockShape.QUARTER_PIECE, ExtShapeTags.TERRACOTTA_QUARTER_PIECES)
        .setPrimaryTagForShape(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeTags.TERRACOTTA_VERTICAL_QUARTER_PIECES)
        .setPrimaryTagForShape(BlockShape.FENCE, ExtShapeTags.TERRACOTTA_FENCES)
        .setPrimaryTagForShape(BlockShape.FENCE_GATE, ExtShapeTags.TERRACOTTA_FENCE_GATES)
        .setPrimaryTagForShape(BlockShape.WALL, ExtShapeTags.TERRACOTTA_WALLS)
        .setPrimaryTagForShape(BlockShape.BUTTON, ExtShapeTags.TERRACOTTA_BUTTONS)
        .setPrimaryTagForShape(BlockShape.PRESSURE_PLATE, ExtShapeTags.TERRACOTTA_PRESSURE_PLATES)
        .build();
    for (final Block block : BlockCollections.STAINED_TERRACOTTA) {
      FACTORY.createAllShapes(block)
          .setFenceCraftingIngredient(Items.CLAY)
          .setButtonType(ButtonType.STONE)
          .setPressurePlateActivationRule(ActivationRule.MOBS)
          .setPrimaryTagForShape(BlockShape.STAIRS, ExtShapeTags.STAINED_TERRACOTTA_STAIRS)
          .setPrimaryTagForShape(BlockShape.VERTICAL_STAIRS, ExtShapeTags.STAINED_TERRACOTTA_VERTICAL_STAIRS)
          .setPrimaryTagForShape(BlockShape.SLAB, ExtShapeTags.STAINED_TERRACOTTA_SLABS)
          .setPrimaryTagForShape(BlockShape.VERTICAL_SLAB, ExtShapeTags.STAINED_TERRACOTTA_VERTICAL_SLABS)
          .setPrimaryTagForShape(BlockShape.QUARTER_PIECE, ExtShapeTags.STAINED_TERRACOTTA_QUARTER_PIECES)
          .setPrimaryTagForShape(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeTags.STAINED_TERRACOTTA_VERTICAL_QUARTER_PIECES)
          .setPrimaryTagForShape(BlockShape.FENCE, ExtShapeTags.STAINED_TERRACOTTA_FENCES)
          .setPrimaryTagForShape(BlockShape.FENCE_GATE, ExtShapeTags.STAINED_TERRACOTTA_FENCE_GATES)
          .setPrimaryTagForShape(BlockShape.WALL, ExtShapeTags.STAINED_TERRACOTTA_WALLS)
          .setPrimaryTagForShape(BlockShape.BUTTON, ExtShapeTags.STAINED_TERRACOTTA_BUTTONS)
          .setPrimaryTagForShape(BlockShape.PRESSURE_PLATE, ExtShapeTags.STAINED_TERRACOTTA_PRESSURE_PLATES)
          .setRecipeGroup(blockShape -> "stained_terracotta_" + blockShape.asString())
          .build();
    }

    // 煤炭块。
    FACTORY.createAllShapes(COAL_BLOCK)
        .setFenceCraftingIngredient(Items.COAL)
        .setPressurePlateActivationRule(ActivationRule.MOBS)
        .build();

    // 浮冰。
    FACTORY.createAllShapes(PACKED_ICE)
        .setFenceCraftingIngredient(Items.ICE)
        .setPressurePlateActivationRule(ActivationRule.MOBS).build();

    // 海晶石、海晶石砖、暗海晶石。
    for (final Block block : new Block[]{PRISMARINE, PRISMARINE_BRICKS, DARK_PRISMARINE}) {
      FACTORY.createAllShapes(block)
          .setFenceCraftingIngredient(Items.PRISMARINE_SHARD)
          .setButtonType(ButtonType.STONE)
          .setPressurePlateActivationRule(ActivationRule.MOBS).build();
    }

    // 海晶灯。
    FACTORY.createConstructionOnly(SEA_LANTERN).build();

    // 平滑石头比较特殊，完整方块和台阶不同。
    SMOOTH_STONE_DOUBLE_SLAB = FACTORY.modify(new BlockBuilder())
        .setInstanceSupplier(builder -> BRRPCubeBlock.cubeBottomTop(builder.blockSettings, new Identifier("minecraft", "block/smooth_stone"), new Identifier("minecraft", "block/smooth_stone_slab_side"), new Identifier("minecraft", "block/smooth_stone")))
        .setBlockSettings(AbstractBlock.Settings.copy(SMOOTH_STONE))
        .setIdentifier(new Identifier(ExtShape.MOD_ID, "smooth_stone_slab_double"))
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .group(ItemGroup.BUILDING_BLOCKS).build();

    FACTORY.createAllShapes(SMOOTH_STONE)
        .setFenceCraftingIngredient(Items.FLINT)
        .setPressurePlateActivationRule(ActivationRule.MOBS).withoutConstructionShapes().build();

    BASE_BLOCKS.add(SMOOTH_STONE_DOUBLE_SLAB);
    BlockBiMaps.setBlockOf(BlockShape.SLAB, SMOOTH_STONE_DOUBLE_SLAB, SMOOTH_STONE_SLAB);

    // 紫珀块。
    FACTORY.createAllShapes(PURPUR_BLOCK)
        .setFenceCraftingIngredient(Items.SHULKER_SHELL)
        .setPressurePlateActivationRule(ActivationRule.MOBS).build();

    // 下界疣块、诡异疣块。
    FACTORY.createAllShapes(NETHER_WART_BLOCK)
        .compostingChance(0.85f)
        .setFenceCraftingIngredient(Items.NETHER_WART)
        .setButtonType(null)
        .setPressurePlateActivationRule(null).withoutRedstone().build();
    FACTORY.createAllShapes(WARPED_WART_BLOCK)
        .compostingChance(0.85f)
        .setFenceCraftingIngredient(Items.NETHER_WART)
        .setButtonType(null)
        .setPressurePlateActivationRule(null).withoutRedstone().build();

    // 带釉陶瓦只注册台阶。
    for (final Block block : BlockCollections.GLAZED_TERRACOTTA) {
      final SlabBlock slabBlock = FACTORY.modify(new SlabBuilder(block)).setInstanceSupplier(builder -> new GlazedTerracottaSlabBlock(builder.baseBlock, AbstractBlock.Settings.copy(builder.baseBlock))).setPrimaryTagToAddTo(ExtShapeTags.GLAZED_TERRACOTTA_SLABS).build();
      RecipeGroupRegistry.setRecipeGroup(slabBlock, "glazed_terracotta_slab");
    }

    // 彩色混凝土。
    for (final Block block : BlockCollections.CONCRETES) {
      FACTORY.createAllShapes(block)
          .setFenceCraftingIngredient(Items.GRAVEL)
          .setButtonType(ButtonType.STONE)
          .setPressurePlateActivationRule(ActivationRule.MOBS)
          .setPrimaryTagForShape(BlockShape.STAIRS, ExtShapeTags.CONCRETE_STAIRS)
          .setPrimaryTagForShape(BlockShape.VERTICAL_STAIRS, ExtShapeTags.CONCRETE_VERTICAL_STAIRS)
          .setPrimaryTagForShape(BlockShape.SLAB, ExtShapeTags.CONCRETE_SLABS)
          .setPrimaryTagForShape(BlockShape.VERTICAL_SLAB, ExtShapeTags.CONCRETE_VERTICAL_SLABS)
          .setPrimaryTagForShape(BlockShape.QUARTER_PIECE, ExtShapeTags.CONCRETE_QUARTER_PIECES)
          .setPrimaryTagForShape(BlockShape.VERTICAL_QUARTER_PIECE, ExtShapeTags.CONCRETE_VERTICAL_QUARTER_PIECES)
          .setPrimaryTagForShape(BlockShape.FENCE, ExtShapeTags.CONCRETE_FENCES)
          .setPrimaryTagForShape(BlockShape.FENCE_GATE, ExtShapeTags.CONCRETE_FENCE_GATES)
          .setPrimaryTagForShape(BlockShape.WALL, ExtShapeTags.CONCRETE_WALLS)
          .setPrimaryTagForShape(BlockShape.BUTTON, ExtShapeTags.CONCRETE_BUTTONS)
          .setPrimaryTagForShape(BlockShape.PRESSURE_PLATE, ExtShapeTags.CONCRETE_PRESSURE_PLATES)
          .setRecipeGroup(blockShape -> "concrete_" + blockShape.asString())
          .build();
    }

    // 菌光体。
    FACTORY.createAllShapes(SHROOMLIGHT)
        .setFenceCraftingIngredient(Items.GLOWSTONE_DUST)
        .setButtonType(ButtonType.SOFT)
        .compostingChance(0.65f)
        .setPressurePlateActivationRule(ActivationRule.EVERYTHING).build();

    // 蜜脾块。
    FACTORY.createAllShapes(HONEYCOMB_BLOCK)
        .setFenceCraftingIngredient(Items.HONEYCOMB)
        .setButtonType(ButtonType.SOFT)
        .setPressurePlateActivationRule(ActivationRule.EVERYTHING).build();

    // 下界合金方块。
    FACTORY.createAllShapes(NETHERITE_BLOCK)
        .setFenceCraftingIngredient(Items.NETHERITE_INGOT)
        .setButtonType(ButtonType.HARD)
        .setPressurePlateActivationRule(ActivationRule.MOBS).build();

    // 远古残骸。
    FACTORY.createAllShapes(ANCIENT_DEBRIS)
        .setFenceCraftingIngredient(Items.NETHERITE_SCRAP)
        .setButtonType(ButtonType.HARD)
        .setPressurePlateActivationRule(ActivationRule.MOBS).build();

    // 哭泣的黑曜石。
    FACTORY.createAllShapes(CRYING_OBSIDIAN)
        .setFenceCraftingIngredient(Items.STONE)
        .addExtraTag(BlockTags.DRAGON_IMMUNE)
        .setButtonType(ButtonType.HARD)
        .setPressurePlateActivationRule(ActivationRule.MOBS).build();

    // 黑石及其变种。
    FACTORY.createConstructionOnly(BLACKSTONE).build();
    FACTORY.createConstructionOnly(POLISHED_BLACKSTONE).build();
    FACTORY.createConstructionOnly(POLISHED_BLACKSTONE_BRICKS).build();
    FACTORY.createConstructionOnly(CHISELED_POLISHED_BLACKSTONE).build();
    FACTORY.createConstructionOnly(GILDED_BLACKSTONE)
        .addExtraTag(BlockTags.GUARDED_BY_PIGLINS).build();

    FACTORY.createConstructionOnly(CHISELED_NETHER_BRICKS).build();

    // 凝灰岩，方解石。
    FACTORY.createAllShapes(TUFF)
        .setFenceCraftingIngredient(Items.FLINT)
        .setPressurePlateActivationRule(ActivationRule.MOBS).build();
    FACTORY.createAllShapes(CALCITE)
        .setFenceCraftingIngredient(Items.FLINT)
        .setPressurePlateActivationRule(ActivationRule.MOBS).build();

    // 涂蜡的铜块。
    for (final Block block : new Block[]{
        WAXED_COPPER_BLOCK,
        WAXED_CUT_COPPER
    }) {
      FACTORY.createAllShapes(block)
          .setFenceCraftingIngredient(Items.COPPER_INGOT)
          .setRecipeGroup(blockShape -> "waxed_cut_copper_" + blockShape.asString()).build();
    }
    for (final Block block : new Block[]{
        WAXED_EXPOSED_COPPER,
        WAXED_EXPOSED_CUT_COPPER
    }) {
      FACTORY.createAllShapes(block)
          .setFenceCraftingIngredient(Items.COPPER_INGOT)
          .setRecipeGroup(blockShape -> "waxed_exposed_cut_copper_" + blockShape.asString()).build();
    }
    for (final Block block : new Block[]{
        WAXED_WEATHERED_COPPER,
        WAXED_WEATHERED_CUT_COPPER
    }) {
      FACTORY.createAllShapes(block)
          .setFenceCraftingIngredient(Items.COPPER_INGOT)
          .setRecipeGroup(blockShape -> "waxed_weathered_cut_copper_" + blockShape.asString()).build();
    }
    for (final Block block : new Block[]{
        WAXED_OXIDIZED_COPPER,
        WAXED_OXIDIZED_CUT_COPPER
    }) {
      FACTORY.createAllShapes(block)
          .setFenceCraftingIngredient(Items.COPPER_INGOT)
          .setButtonType(ButtonType.STONE)
          .setRecipeGroup(blockShape -> "waxed_oxidized_cut_copper_")
          .setPressurePlateActivationRule(ActivationRule.MOBS).build();
    }

    // 滴水石、苔藓。
    FACTORY.createAllShapes(DRIPSTONE_BLOCK)
        .setFenceCraftingIngredient(Items.POINTED_DRIPSTONE)
        .setPressurePlateActivationRule(ActivationRule.MOBS).build();
    FACTORY.createAllShapes(MOSS_BLOCK)
        .setFenceCraftingIngredient(Items.MOSS_CARPET)
        .compostingChance(0.65f)
        .addExtraTag(shape -> shape.isConstruction ? BlockTags.SMALL_DRIPLEAF_PLACEABLE : null)
        .setButtonType(ButtonType.SOFT)
        .setPressurePlateActivationRule(ActivationRule.EVERYTHING).build();

    // 深板岩。
    FACTORY.createAllShapes(DEEPSLATE)
        .setFenceCraftingIngredient(Items.FLINT)
        .setPressurePlateActivationRule(ActivationRule.MOBS).setPillar().build();
    for (final Block block : new Block[]{COBBLED_DEEPSLATE, POLISHED_DEEPSLATE, DEEPSLATE_TILES, DEEPSLATE_BRICKS, CHISELED_DEEPSLATE}) {
      FACTORY.createAllShapes(block)
          .setFenceCraftingIngredient(Items.FLINT)
          .setButtonType(ButtonType.STONE)
          .setPressurePlateActivationRule(ActivationRule.MOBS).build();
    }

    // 玄武岩及其变种。
    FACTORY.createAllShapes(BASALT)
        .setFenceCraftingIngredient(Items.FLINT)
        .setPressurePlateActivationRule(ActivationRule.MOBS).setPillar().build();
    FACTORY.createAllShapes(POLISHED_BASALT)
        .setFenceCraftingIngredient(Items.FLINT)
        .setPressurePlateActivationRule(ActivationRule.MOBS).setPillar().build();
    FACTORY.createAllShapes(SMOOTH_BASALT)
        .setFenceCraftingIngredient(Items.FLINT)
        .setPressurePlateActivationRule(ActivationRule.MOBS).build();

    // 粗铁、粗铜、粗金。
    FACTORY.createAllShapes(RAW_IRON_BLOCK)
        .setFenceCraftingIngredient(Items.RAW_IRON)
        .setPressurePlateActivationRule(ActivationRule.MOBS).build();
    FACTORY.createAllShapes(RAW_COPPER_BLOCK)
        .setFenceCraftingIngredient(Items.RAW_COPPER)
        .setPressurePlateActivationRule(ActivationRule.MOBS).build();
    FACTORY.createAllShapes(RAW_GOLD_BLOCK)
        .setFenceCraftingIngredient(Items.RAW_GOLD)
        .addExtraTag(ItemTags.PIGLIN_LOVED)
        .addExtraTag(BlockTags.GUARDED_BY_PIGLINS)
        .setPressurePlateActivationRule(ActivationRule.MOBS).build();

    ExtShape.LOGGER.info("Extended Block Shapes mod created {} blocks for {} base blocks. So swift!", BLOCKS.size(), BASE_BLOCKS.size());
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
