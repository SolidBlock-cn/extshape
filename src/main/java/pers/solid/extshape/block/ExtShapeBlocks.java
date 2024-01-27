package pers.solid.extshape.block;

import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.mixin.object.builder.AbstractBlockSettingsAccessor;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import pers.solid.brrp.v1.api.RuntimeResourcePack;
import pers.solid.brrp.v1.generator.BRRPCubeBlock;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.builder.*;
import pers.solid.extshape.mixin.BlockAccessor;
import pers.solid.extshape.rrp.RecipeGroupRegistry;
import pers.solid.extshape.tag.ExtShapeTags;
import pers.solid.extshape.util.ActivationSettings;
import pers.solid.extshape.util.BlockBiMaps;
import pers.solid.extshape.util.BlockCollections;
import pers.solid.extshape.util.FenceSettings;

import java.util.function.BiConsumer;
import java.util.function.Function;

import static net.minecraft.block.Blocks.*;

/**
 * <p>扩展方块形状模组所有的方块都是在此类定义的。类初始化时，就会实例化所有的方块对象。其中，{@linkplain  #PETRIFIED_OAK_PLANKS 石化橡木木板}和{@linkplain #SMOOTH_STONE_DOUBLE_SLAB 双层石台阶}直接以字段的形式储存，其他方块则需要通过 {@link BlockBiMaps#getBlockOf} 间接访问。
 * <p>在使用此类的内容时尤其需要注意是否完成了初始化。如果在还没有初始化的时候使用本类的内容导致过早初始化，可能会产生错误。例如，Forge 模组是在注册表事件中将此类初始化的，因为此时注册表未冻结，可以注册，如过早或过晚注册则会产生问题。
 * <hr>
 * <p>Blocks in Extended Block Shapes mod are defined in this class. When the class is initialized, all block objects are instantiated. {@linkplain #PETRIFIED_OAK_PLANKS Petrified oak planks} and {@linkplain  #SMOOTH_STONE_DOUBLE_SLAB Smooth stone double slab} are stored directly in fields, while other blocks can be accessed indirectly through {@link BlockBiMaps#getBlockOf}.
 * <p>It is important to notice whether the initialization is completed when using contents in this class. If you use content in this class before proper initialization and causes it initialized too early, some errors may be thrown. For example, Forge mod completes initialization of this class in registry events, as at that buttonTime the registry is unfrozen and allow registration; exceptions are thrown if registered too early or too late.
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
    // 将一些非 pickaxe_mineable 的方块的墙加入 pickaxe_unmineable 标签中
    final Function<BlockShape, @Nullable TagKey<? extends ItemConvertible>> addWallToUnmineableTag = blockShape -> blockShape == BlockShape.WALL ? ExtShapeTags.PICKAXE_UNMINEABLE : null;

    // 石头及其变种（含磨制变种），已存在其楼梯、台阶、墙，但是还没有栅栏和栅栏门。
    for (final Block block : BlockCollections.STONES) {
      FACTORY.createAllShapes(block)
          .markStoneCuttable()
          .addExtraTag(BlockTags.PICKAXE_MINEABLE)
          .setFenceSettings(FenceSettings.STONE)
          .setActivationSettings(ActivationSettings.STONE)
          .build();
    }

    // 泥土和砂土。其中砂土没有按钮和压力板。
    FACTORY.createAllShapes(DIRT)
        .addExtraTag(BlockTags.SHOVEL_MINEABLE)
        .addExtraTag(addWallToUnmineableTag)
        .addPreBuildConsumer((blockShape, blockBuilder) -> {
          if (blockShape.isConstruction) {
            blockBuilder.addExtraTag(BlockTags.ENDERMAN_HOLDABLE);
            blockBuilder.addExtraTag(BlockTags.BAMBOO_PLANTABLE_ON);
            blockBuilder.addExtraTag(BlockTags.DEAD_BUSH_MAY_PLACE_ON);
          }
        })
        .setActivationSettings(ActivationSettings.GRAVEL)
        .setFenceSettings(FenceSettings.DIRT)
        .build();
    FACTORY.createAllShapes(COARSE_DIRT)
        .addExtraTag(BlockTags.SHOVEL_MINEABLE)
        .addExtraTag(addWallToUnmineableTag)
        .setActivationSettings(ActivationSettings.SOFT)
        .setFenceSettings(FenceSettings.DIRT)
        .withoutRedstone()
        .build();

    // 圆石。
    FACTORY.createAllShapes(COBBLESTONE)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .setFenceSettings(FenceSettings.STONE)
        .build();

    // 原木、木头、菌柄、菌核及其去皮变种。
    // 下面两个标签均带有 axe_mineable
    final BiConsumer<BlockShape, AbstractBlockBuilder<? extends Block>> woodFlammable = (blockShape, blockBuilder) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 5, 5);

    // 原木和竹子。
    for (final Block block : BlockCollections.LOGS) {
      FACTORY.createConstructionOnly(block)
          .setPillar()
          .setPrimaryTagForShape(ExtShapeTags.SHAPE_TO_LOG_TAG)
          .addPostBuildConsumer(woodFlammable)
          .setRecipeGroup(blockShape -> "log_" + blockShape.asString())
          .build();
    }

    // 去皮的原木。
    for (final Block block : BlockCollections.STRIPPED_LOGS) {
      FACTORY.createConstructionOnly(block)
          .setPillar(false)
          .setPrimaryTagForShape(ExtShapeTags.SHAPE_TO_LOG_TAG)
          .addPostBuildConsumer(woodFlammable)
          .setRecipeGroup(blockShape -> "stripped_log_" + blockShape.asString())
          .build();
    }

    // an infinite cycling loop for wooden block set types, which each cycle should correspond to
    // BlockCollections.WOODS, which does not include bamboo and nether woods.
    // 木头
    for (final Block block : BlockCollections.WOODS) {
      FACTORY.createAllShapes(block)
          .setFenceSettings(FenceSettings.WOOD)
          .setActivationSettings(ActivationSettings.WOOD)
          .setPillar()
          .setPrimaryTagForShape(ExtShapeTags.SHAPE_TO_LOG_TAG)
          .addPostBuildConsumer(woodFlammable)
          .setRecipeGroup(blockShape -> "wood_" + blockShape.asString())
          .build();
    }
    for (final Block block : BlockCollections.STRIPPED_WOODS) {
      FACTORY.createAllShapes(block)
          .setFenceSettings(FenceSettings.NETHER_WOOD)
          .setActivationSettings(ActivationSettings.NETHER_WOOD)
          .setPillar()
          .setPrimaryTagForShape(ExtShapeTags.SHAPE_TO_LOG_TAG)
          .addPostBuildConsumer(woodFlammable)
          .setRecipeGroup(blockShape -> "stripped_wood_" + blockShape.asString())
          .build();
    }
    for (final Block block : BlockCollections.STEMS) {
      FACTORY.createConstructionOnly(block)
          .setPillar()
          .setPrimaryTagForShape(ExtShapeTags.SHAPE_TO_LOG_TAG)
          .addExtraTag(ItemTags.NON_FLAMMABLE_WOOD)
          .setRecipeGroup(blockShape -> "log_" + blockShape.asString())
          .build();
    }
    for (final Block block : BlockCollections.STRIPPED_STEMS) {
      FACTORY.createConstructionOnly(block)
          .setPillar()
          .setPrimaryTagForShape(ExtShapeTags.SHAPE_TO_LOG_TAG)
          .addExtraTag(ItemTags.NON_FLAMMABLE_WOOD)
          .setRecipeGroup(blockShape -> "stripped_log_" + blockShape.asString())
          .build();
    }
    for (final Block block : BlockCollections.HYPHAES) {
      FACTORY.createAllShapes(block)
          .setFenceSettings(FenceSettings.NETHER_WOOD)
          .setActivationSettings(ActivationSettings.NETHER_WOOD)
          .setPillar()
          .setPrimaryTagForShape(ExtShapeTags.SHAPE_TO_LOG_TAG)
          .addExtraTag(ItemTags.NON_FLAMMABLE_WOOD)
          .setRecipeGroup(blockShape -> "wood_" + blockShape.asString())
          .build();
    }
    for (final Block block : BlockCollections.STRIPPED_HYPHAES) {
      FACTORY.createAllShapes(block)
          .setFenceSettings(FenceSettings.NETHER_WOOD)
          .setActivationSettings(ActivationSettings.NETHER_WOOD)
          .setPillar()
          .setPrimaryTagForShape(ExtShapeTags.SHAPE_TO_LOG_TAG)
          .addExtraTag(ItemTags.NON_FLAMMABLE_WOOD)
          .setRecipeGroup(blockShape -> "stripped_wood_" + blockShape.asString())
          .build();
    }

    // 木板。
    for (final Block block : BlockCollections.PLANKS) {
      if (block == CRIMSON_PLANKS || block == WARPED_PLANKS) {
        FACTORY.createAllShapes(block)
            .setFenceSettings(null)
            .setActivationSettings(ActivationSettings.NETHER_WOOD)
            .setPrimaryTagForShape(ExtShapeTags.SHAPE_TO_WOODEN_TAG)
            .addExtraTag(ItemTags.NON_FLAMMABLE_WOOD)
            .setRecipeGroup(blockShape -> "wooden_" + blockShape.asString())
            .build();
      } else {
        FACTORY.createAllShapes(block)
            .setFenceSettings(null)
            .setActivationSettings(ActivationSettings.NETHER_WOOD)
            .setPrimaryTagForShape(ExtShapeTags.SHAPE_TO_WOODEN_TAG)
            .addPostBuildConsumer((blockShape, blockBuilder) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 5, 20))
            .setRecipeGroup(blockShape -> "wooden_" + blockShape.asString())
            .build();
      }
    }

    // 石化橡木木板。
    PETRIFIED_OAK_PLANKS = FACTORY.modify(new BlockBuilder())
        .setInstanceSupplier(builder -> {
          final BRRPCubeBlock block = BRRPCubeBlock.cubeAll(builder.blockSettings, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/oak_planks"));
          ExtShapeBlockInterface.STONECUTTABLE_BLOCKS.add(block);
          return block;
        })
        .setBlockSettings(AbstractBlock.Settings.copy(PETRIFIED_OAK_SLAB))
        .setIdentifier(new Identifier(ExtShape.MOD_ID, "petrified_oak_planks"))
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .group(ItemGroup.BUILDING_BLOCKS)
        .build();

    BASE_BLOCKS.add(PETRIFIED_OAK_PLANKS);
    BlockBiMaps.setBlockOf(BlockShape.SLAB, PETRIFIED_OAK_PLANKS, PETRIFIED_OAK_SLAB);

    // 基岩。
    FACTORY.createAllShapes(BEDROCK)
        .markStoneCuttable()
        .addExtraTag(BlockTags.INFINIBURN_END)
        .addExtraTag(BlockTags.WITHER_IMMUNE)
        .setFenceSettings(FenceSettings.STONE)
        .setActivationSettings(ActivationSettings.HARD)
        .addPreBuildConsumer((blockShape1, builder1) -> ((AbstractBlockSettingsAccessor) builder1.blockSettings.strength(-1.0F, 3600000.0F).allowsSpawning((state1, world1, pos1, type) -> false)).setLootTableId(null))
        .build();

    // 青金石块。
    FACTORY.createAllShapes(LAPIS_BLOCK)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .addExtraTag(BlockTags.NEEDS_STONE_TOOL)
        .setFenceSettings(new FenceSettings(Items.LAPIS_LAZULI))
        .setActivationSettings(ActivationSettings.ORE_BLOCK)
        .build();

    // 砂岩、红砂岩及其切制、錾制、平滑变种。其中，只有平滑砂岩有栅栏、压力板和按钮。
    for (final Block block : BlockCollections.SANDSTONES) {
      FACTORY.createConstructionOnly(block)
          .markStoneCuttable()
          .addExtraTag(BlockTags.PICKAXE_MINEABLE)
          .with(BlockShape.WALL)
          .build();
    }
    for (final Block block : new Block[]{SMOOTH_SANDSTONE, SMOOTH_RED_SANDSTONE}) {
      FACTORY.createEmpty(block)
          .markStoneCuttable()
          .addExtraTag(BlockTags.PICKAXE_MINEABLE)
          .withFences(new FenceSettings(Items.STICK))
          .withPressurePlate(ActivationSettings.STONE)
          .withButton(ActivationSettings.STONE)
          .build();
    }

    // 羊毛。
    for (final Block block : BlockCollections.WOOLS) {
      // 下面这些标签均属于 woolen_blocks
      FACTORY.createAllShapes(block)
          .setFenceSettings(FenceSettings.WOOL)
          .setActivationSettings(ActivationSettings.WOOL)
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
          .addPostBuildConsumer((blockShape, blockBuilder) -> FlammableBlockRegistry.getDefaultInstance().add(blockBuilder.instance, 30, 50))
          .setRecipeGroup(blockShape -> "wool_" + blockShape.asString())
          .build();
    }

    // 金块。
    FACTORY.createAllShapes(GOLD_BLOCK)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .addExtraTag(BlockTags.NEEDS_IRON_TOOL)
        .addExtraTag(ItemTags.PIGLIN_LOVED)
        .addExtraTag(BlockTags.GUARDED_BY_PIGLINS)
        .setFenceSettings(new FenceSettings(Items.GOLD_INGOT))
        .setActivationSettings(ActivationSettings.GOLD)
        .without(BlockShape.PRESSURE_PLATE)
        .build();
    // 铁块。
    FACTORY.createAllShapes(IRON_BLOCK)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .addExtraTag(BlockTags.NEEDS_STONE_TOOL)
        .setFenceSettings(new FenceSettings(Items.IRON_INGOT))
        .setActivationSettings(ActivationSettings.IRON)
        .without(BlockShape.PRESSURE_PLATE)
        .build();

    // 砖栅栏和栅栏门。
    FACTORY.createAllShapes(BRICKS)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .setFenceSettings(new FenceSettings(Items.BRICK))
        .setActivationSettings(ActivationSettings.STONE)
        .build();

    // 苔石栅栏和栅栏门。
    FACTORY.createAllShapes(MOSSY_COBBLESTONE)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .setFenceSettings(FenceSettings.STONE)
        .build();

    // 黑曜石。
    FACTORY.createAllShapes(OBSIDIAN)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .addExtraTag(BlockTags.NEEDS_DIAMOND_TOOL)
        .setFenceSettings(new FenceSettings(Items.FLINT))
        .addExtraTag(BlockTags.DRAGON_IMMUNE)
        .setActivationSettings(ActivationSettings.HARD)
        .build();

    // 钻石块。
    FACTORY.createAllShapes(DIAMOND_BLOCK)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .addExtraTag(BlockTags.NEEDS_IRON_TOOL)
        .setFenceSettings(new FenceSettings(Items.DIAMOND))
        .setActivationSettings(ActivationSettings.hard(ActivationSettings.Sounds.METAL))
        .build();

    // 紫水晶块。
    FACTORY.createAllShapes(AMETHYST_BLOCK)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .withExtension(BlockExtension.AMETHYST)
        .setFenceSettings(FenceSettings.AMETHYST)
        .withoutRedstone()
        .build();

    // 冰，由于技术原因，暂不产生。

    // 雪块。
    FACTORY.createAllShapes(SNOW_BLOCK)
        .addExtraTag(BlockTags.SHOVEL_MINEABLE)
        .addExtraTag(addWallToUnmineableTag)
        .addExtraTag(ExtShapeTags.SNOW)
        .setFenceSettings(FenceSettings.SNOW)
        .setActivationSettings(ActivationSettings.soft(new ActivationSettings.Sounds(SoundEvents.BLOCK_SNOW_HIT)))
        .build();

    // 黏土块。
    FACTORY.createAllShapes(CLAY)
        .addExtraTag(BlockTags.SHOVEL_MINEABLE)
        .addExtraTag(addWallToUnmineableTag)
        .setFenceSettings(new FenceSettings(Items.CLAY_BALL))
        .setActivationSettings(ActivationSettings.GRAVEL)
        .addExtraTag(shape -> shape.isConstruction ? BlockTags.SMALL_DRIPLEAF_PLACEABLE : null)
        .addExtraTag(shape -> shape.isConstruction ? BlockTags.ENDERMAN_HOLDABLE : null)
        .build();

    // 南瓜。
    FACTORY.createAllShapes(PUMPKIN)
        .addExtraTag(BlockTags.AXE_MINEABLE)
        .addExtraTag(addWallToUnmineableTag)
        .setFenceSettings(FenceSettings.WOOD)
        .setActivationSettings(ActivationSettings.CROPS)
        .addExtraTag(shape -> shape.isConstruction ? BlockTags.ENDERMAN_HOLDABLE : null)
        .compostingChance(0.65f)
        .build();

    // 下界岩。
    FACTORY.createAllShapes(NETHERRACK)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .addExtraTag(BlockTags.INFINIBURN_OVERWORLD)
        .setFenceSettings(FenceSettings.NETHERRACK)
        .setActivationSettings(ActivationSettings.NETHERRACK)
        .build();

    // 荧石。
    FACTORY.createAllShapes(GLOWSTONE)
        .addExtraTag(addWallToUnmineableTag)
        .setFenceSettings(new FenceSettings(Items.GLOWSTONE_DUST))
        .setActivationSettings(ActivationSettings.SOFT)
        .build();

    // 石砖、苔石砖、雕纹石砖。
    for (final Block block : new Block[]{STONE_BRICKS, MOSSY_STONE_BRICKS, CHISELED_STONE_BRICKS}) {
      FACTORY.createAllShapes(block)
          .markStoneCuttable()
          .addExtraTag(BlockTags.PICKAXE_MINEABLE)
          .setFenceSettings(FenceSettings.STONE)
          .setActivationSettings(ActivationSettings.STONE)
          .build();
    }

    // 泥（自 1.19）。
    FACTORY.createAllShapes(PACKED_MUD)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .setFenceSettings(new FenceSettings(Items.MUD))
        .setActivationSettings(ActivationSettings.STONE)
        .build();
    FACTORY.createAllShapes(MUD_BRICKS)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .setFenceSettings(new FenceSettings(Items.MUD))
        .setActivationSettings(ActivationSettings.STONE)
        .build();

    // 西瓜。
    FACTORY.createAllShapes(MELON)
        .addExtraTag(BlockTags.AXE_MINEABLE)
        .addExtraTag(addWallToUnmineableTag)
        .setFenceSettings(new FenceSettings(Items.MELON_SLICE))
        .setActivationSettings(ActivationSettings.CROPS)
        .addExtraTag(shape -> shape.isConstruction ? BlockTags.ENDERMAN_HOLDABLE : null)
        .compostingChance(0.65f)
        .build();

    // 下界砖块的栅栏门、按钮和压力板。
    FACTORY.createAllShapes(NETHER_BRICKS)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .setFenceSettings(new FenceSettings(Items.NETHER_BRICK))
        .setActivationSettings(ActivationSettings.STONE)
        .withoutRedstone()
        .build();

    // 末地石、末地石砖。
    FACTORY.createAllShapes(END_STONE)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .addExtraTag(BlockTags.DRAGON_IMMUNE)
        .setFenceSettings(FenceSettings.STONE)
        .setActivationSettings(ActivationSettings.STONE)
        .build();
    FACTORY.createAllShapes(END_STONE_BRICKS)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .addExtraTag(BlockTags.DRAGON_IMMUNE)
        .setFenceSettings(FenceSettings.STONE)
        .setActivationSettings(ActivationSettings.STONE)
        .build();

    // 绿宝石块。
    FACTORY.createAllShapes(EMERALD_BLOCK)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .addExtraTag(BlockTags.NEEDS_IRON_TOOL)
        .setFenceSettings(new FenceSettings(Items.EMERALD))
        .build();

    // 石英、石英砖、平滑石英块、錾制石英块均有按钮和压力板。
    for (final Block block : new Block[]{QUARTZ_BLOCK, CHISELED_QUARTZ_BLOCK, QUARTZ_BRICKS, SMOOTH_QUARTZ}) {
      FACTORY.createAllShapes(block)
          .markStoneCuttable()
          .addExtraTag(BlockTags.PICKAXE_MINEABLE)
          .setFenceSettings(FenceSettings.QUARTZ)
          .setActivationSettings(ActivationSettings.QUARTZ)
          .build();
    }

    // 红色下界砖。
    FACTORY.createAllShapes(RED_NETHER_BRICKS)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .setFenceSettings(new FenceSettings(Items.NETHER_BRICK))
        .build();

    // 陶瓦和彩色陶瓦。
    FACTORY.createAllShapes(TERRACOTTA)
        .markStoneCuttable()  // 下面这些标签已带有 pickaxe_mineable
        .setFenceSettings(new FenceSettings(Items.CLAY))
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
        .addExtraTag(blockShape -> blockShape.isConstruction ? BlockTags.DEAD_BUSH_MAY_PLACE_ON : null)
        .build();
    for (final Block block : BlockCollections.STAINED_TERRACOTTA) {
      FACTORY.createAllShapes(block)
          .markStoneCuttable()
          .addExtraTag(BlockTags.PICKAXE_MINEABLE)
          .setFenceSettings(new FenceSettings(Items.CLAY))
          .setActivationSettings(ActivationSettings.STONE)
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
          .addExtraTag(blockShape -> blockShape.isConstruction ? BlockTags.DEAD_BUSH_MAY_PLACE_ON : null)
          .setRecipeGroup(blockShape -> "stained_terracotta_" + blockShape.asString())
          .build();
    }

    // 煤炭块。
    FACTORY.createAllShapes(COAL_BLOCK)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .setFenceSettings(new FenceSettings(Items.COAL))
        .setActivationSettings(ActivationSettings.ORE_BLOCK)
        .fuelTime(16000)
        .build();

    // 浮冰。
    FACTORY.createAllShapes(PACKED_ICE)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .setFenceSettings(new FenceSettings(Items.ICE))
        .build();

    // 海晶石、海晶石砖、暗海晶石。
    for (final Block block : new Block[]{PRISMARINE, PRISMARINE_BRICKS, DARK_PRISMARINE}) {
      FACTORY.createAllShapes(block)
          .markStoneCuttable()
          .addExtraTag(BlockTags.PICKAXE_MINEABLE)
          .setFenceSettings(new FenceSettings(Items.PRISMARINE_SHARD))
          .setActivationSettings(ActivationSettings.STONE)
          .build();
    }

    // 海晶灯。
    FACTORY.createConstructionOnly(SEA_LANTERN)
        .build();

    // 平滑石头比较特殊，完整方块和台阶不同。
    SMOOTH_STONE_DOUBLE_SLAB = FACTORY.modify(new BlockBuilder())
        .setInstanceSupplier(builder -> BRRPCubeBlock.cubeBottomTop(builder.blockSettings, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/smooth_stone"), new Identifier(Identifier.DEFAULT_NAMESPACE, "block/smooth_stone_slab_side"), new Identifier(Identifier.DEFAULT_NAMESPACE, "block/smooth_stone")))
        .setBlockSettings(AbstractBlock.Settings.copy(SMOOTH_STONE))
        .setIdentifier(new Identifier(ExtShape.MOD_ID, "smooth_stone_slab_double"))
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .group(ItemGroup.BUILDING_BLOCKS)
        .build();

    FACTORY.createAllShapes(SMOOTH_STONE)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .setFenceSettings(new FenceSettings(Items.FLINT))
        .withoutConstructionShapes()
        .build();

    BASE_BLOCKS.add(SMOOTH_STONE_DOUBLE_SLAB);
    ExtShapeVariantBlockInterface.STONECUTTABLE_BASE_BLOCKS.add(SMOOTH_STONE_DOUBLE_SLAB);
    BlockBiMaps.setBlockOf(BlockShape.SLAB, SMOOTH_STONE_DOUBLE_SLAB, SMOOTH_STONE_SLAB);

    // 紫珀块。
    FACTORY.createAllShapes(PURPUR_BLOCK)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .setFenceSettings(new FenceSettings(Items.SHULKER_SHELL))
        .build();

    // 下界疣块、诡异疣块。
    FACTORY.createAllShapes(NETHER_WART_BLOCK)
        .addExtraTag(BlockTags.HOE_MINEABLE)
        .addExtraTag(addWallToUnmineableTag)
        .compostingChance(0.85f)
        .setFenceSettings(FenceSettings.WART)
        .setActivationSettings(ActivationSettings.WART)
        .build();
    FACTORY.createAllShapes(WARPED_WART_BLOCK)
        .addExtraTag(BlockTags.HOE_MINEABLE)
        .addExtraTag(addWallToUnmineableTag)
        .compostingChance(0.85f)
        .setFenceSettings(FenceSettings.WART)
        .setActivationSettings(ActivationSettings.WART)
        .build();

    // 带釉陶瓦只注册台阶。
    for (final Block block : BlockCollections.GLAZED_TERRACOTTA) {
      ExtShapeVariantBlockInterface.STONECUTTABLE_BASE_BLOCKS.add(block);
      final SlabBlock slabBlock = FACTORY.modify(new SlabBuilder(block))
          .setInstanceSupplier(builder -> new GlazedTerracottaSlabBlock(builder.baseBlock, AbstractBlock.Settings.copy(builder.baseBlock)))
          .setPrimaryTagToAddTo(ExtShapeTags.GLAZED_TERRACOTTA_SLABS)
          .build();
      RecipeGroupRegistry.setRecipeGroup(slabBlock, "glazed_terracotta_slab");
    }

    // 彩色混凝土。
    for (final Block block : BlockCollections.CONCRETES) {
      FACTORY.createAllShapes(block)
          .markStoneCuttable() // 下面这些标签已带有 pickaxe_mineable
          .setFenceSettings(new FenceSettings(Items.CLAY))
          .setActivationSettings(ActivationSettings.STONE)
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
        .addExtraTag(BlockTags.HOE_MINEABLE)
        .addExtraTag(addWallToUnmineableTag)
        .setFenceSettings(new FenceSettings(Items.GLOWSTONE_DUST))
        .setActivationSettings(ActivationSettings.SOFT)
        .compostingChance(0.65f)
        .build();

    // 蜜脾块。
    FACTORY.createAllShapes(HONEYCOMB_BLOCK)
        .addExtraTag(addWallToUnmineableTag)
        .setFenceSettings(new FenceSettings(Items.HONEYCOMB))
        .setActivationSettings(ActivationSettings.SOFT)
        .build();

    // 下界合金块。
    FACTORY.createAllShapes(NETHERITE_BLOCK)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .addExtraTag(BlockTags.NEEDS_DIAMOND_TOOL)
        .setFenceSettings(new FenceSettings(Items.NETHERITE_INGOT))
        .setActivationSettings(ActivationSettings.HARD)
        .build();

    // 远古残骸。
    FACTORY.createAllShapes(ANCIENT_DEBRIS)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .addExtraTag(BlockTags.NEEDS_DIAMOND_TOOL)
        .setFenceSettings(new FenceSettings(Items.NETHERITE_SCRAP))
        .setActivationSettings(ActivationSettings.HARD)
        .build();

    // 哭泣的黑曜石。
    FACTORY.createAllShapes(CRYING_OBSIDIAN)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .addExtraTag(BlockTags.NEEDS_DIAMOND_TOOL)
        .addExtraTag(BlockTags.DRAGON_IMMUNE)
        .setFenceSettings(new FenceSettings(Items.FLINT))
        .setActivationSettings(ActivationSettings.HARD)
        .build();

    // 黑石及其变种。
    FACTORY.createConstructionOnly(BLACKSTONE)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .build();
    FACTORY.createConstructionOnly(POLISHED_BLACKSTONE)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .build();
    FACTORY.createConstructionOnly(POLISHED_BLACKSTONE_BRICKS)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .build();
    FACTORY.createConstructionOnly(CHISELED_POLISHED_BLACKSTONE)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .build();
    FACTORY.createConstructionOnly(GILDED_BLACKSTONE)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .addExtraTag(BlockTags.GUARDED_BY_PIGLINS)
        .build();

    FACTORY.createConstructionOnly(CHISELED_NETHER_BRICKS)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .build();

    // 凝灰岩，方解石。
    FACTORY.createAllShapes(TUFF)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .setFenceSettings(FenceSettings.TUFF)
        .setActivationSettings(ActivationSettings.TUFF)
        .build();

    FACTORY.createAllShapes(CALCITE)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .setFenceSettings(new FenceSettings(Items.FLINT))
        .setActivationSettings(ActivationSettings.softerStone(ActivationSettings.Sounds.STONE))
        .build();

    // 幽匿块。
    FACTORY.createAllShapes(SCULK)
        .addExtraTag(BlockTags.HOE_MINEABLE)
        .addExtraTag(addWallToUnmineableTag)
        .setFenceSettings(new FenceSettings(Items.SCULK_VEIN, SoundEvents.BLOCK_SCULK_HIT))
        .setActivationSettings(ActivationSettings.wood(new ActivationSettings.Sounds(SoundEvents.BLOCK_SCULK_HIT)))
        .withExtension(BlockExtension.builder().setStacksDroppedCallback((state, world, pos, stack, dropExperience) -> ((BlockAccessor) state.getBlock()).callDropExperienceWhenMined(world, pos, stack, ConstantIntProvider.create(1))).build())
        .build();

    // 铜块。
    new CopperManager(FACTORY).registerBlocks();

    // 滴水石、苔藓。
    FACTORY.createAllShapes(DRIPSTONE_BLOCK)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .setFenceSettings(FenceSettings.DRIPSTONE)
        .setActivationSettings(ActivationSettings.DRIPSTONE)
        .build();
    FACTORY.createAllShapes(MOSS_BLOCK)
        .addExtraTag(BlockTags.HOE_MINEABLE)
        .addExtraTag(addWallToUnmineableTag)
        .addExtraTag(shape -> shape.isConstruction ? BlockTags.SMALL_DRIPLEAF_PLACEABLE : null)
        .setFenceSettings(new FenceSettings(Items.MOSS_CARPET, SoundEvents.BLOCK_MOSS_HIT))
        .compostingChance(0.65f)
        .setActivationSettings(ActivationSettings.soft(new ActivationSettings.Sounds(SoundEvents.BLOCK_MOSS_HIT)))
        .build();

    // 深板岩。
    FACTORY.createAllShapes(DEEPSLATE)
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.DEEPSLATE)
        .setActivationSettings(ActivationSettings.DEEPSLATE)
        .setPillar()
        .build();
    FACTORY.createAllShapes(COBBLED_DEEPSLATE)
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.DEEPSLATE)
        .setActivationSettings(ActivationSettings.DEEPSLATE)
        .build();
    FACTORY.createAllShapes(POLISHED_DEEPSLATE)
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .markStoneCuttable()
        .setFenceSettings(new FenceSettings(Items.FLINT))
        .setActivationSettings(ActivationSettings.STONE)
        .build();
    FACTORY.createAllShapes(DEEPSLATE_TILES)
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .markStoneCuttable()
        .setFenceSettings(new FenceSettings(Items.FLINT))
        .setActivationSettings(ActivationSettings.STONE)
        .build();
    FACTORY.createAllShapes(DEEPSLATE_BRICKS)
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.DEEPSLATE_BRICKS)
        .setActivationSettings(ActivationSettings.DEEPSLATE_BRICKS)
        .build();
    FACTORY.createAllShapes(CHISELED_DEEPSLATE)
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .markStoneCuttable()
        .setFenceSettings(FenceSettings.DEEPSLATE_BRICKS)
        .setActivationSettings(ActivationSettings.DEEPSLATE_BRICKS)
        .build();

    // 玄武岩及其变种。
    FACTORY.createAllShapes(BASALT)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .setFenceSettings(FenceSettings.BASALT)
        .setActivationSettings(ActivationSettings.BASALT)
        .setPillar()
        .build();
    FACTORY.createAllShapes(POLISHED_BASALT)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .setFenceSettings(FenceSettings.BASALT)
        .setActivationSettings(ActivationSettings.BASALT)
        .setPillar()
        .build();
    FACTORY.createAllShapes(SMOOTH_BASALT)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .setFenceSettings(FenceSettings.BASALT)
        .setActivationSettings(ActivationSettings.BASALT)
        .build();

    // 粗铁、粗铜、粗金。
    FACTORY.createAllShapes(RAW_IRON_BLOCK)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .addExtraTag(BlockTags.NEEDS_STONE_TOOL)
        .setFenceSettings(new FenceSettings(Items.RAW_IRON))
        .setActivationSettings(ActivationSettings.ORE_BLOCK)
        .build();
    FACTORY.createAllShapes(RAW_COPPER_BLOCK)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .addExtraTag(BlockTags.NEEDS_STONE_TOOL)
        .setFenceSettings(new FenceSettings(Items.RAW_COPPER))
        .setActivationSettings(ActivationSettings.ORE_BLOCK)
        .build();
    FACTORY.createAllShapes(RAW_GOLD_BLOCK)
        .markStoneCuttable()
        .addExtraTag(BlockTags.PICKAXE_MINEABLE)
        .addExtraTag(BlockTags.NEEDS_IRON_TOOL)
        .setFenceSettings(new FenceSettings(Items.RAW_GOLD))
        .setActivationSettings(ActivationSettings.ORE_BLOCK)
        .addExtraTag(ItemTags.PIGLIN_LOVED)
        .addExtraTag(BlockTags.GUARDED_BY_PIGLINS)
        .build();

    // 蛙明灯。
    for (Block block : ArrayUtils.toArray(OCHRE_FROGLIGHT, VERDANT_FROGLIGHT, PEARLESCENT_FROGLIGHT)) {
      FACTORY.createAllShapes(block)
          .addExtraTag(addWallToUnmineableTag)
          .setFenceSettings(new FenceSettings(Items.SLIME_BALL))
          .setActivationSettings(ActivationSettings.SOFT)
          .setPillar()
          .build();
    }

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
