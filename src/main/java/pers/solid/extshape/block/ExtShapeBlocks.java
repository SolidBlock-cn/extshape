package pers.solid.extshape.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock.ActivationRule;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import pers.solid.extshape.block.ExtShapeButtonBlock.ButtonType;
import pers.solid.extshape.builder.BlockBuilder;
import pers.solid.extshape.builder.Shape;
import pers.solid.extshape.builder.SlabBuilder;
import pers.solid.extshape.mappings.BlockMappings;

import java.util.Map;

import static net.minecraft.block.Blocks.*;
import static pers.solid.extshape.tag.ExtShapeBlockTag.*;

/**
 * @see net.minecraft.block.Blocks
 */
public class ExtShapeBlocks {
    public static final Block PETRIFIED_OAK_PLANKS, SMOOTH_STONE_DOUBLE_SLAB;

    /*
      使用BlockBuilder并利用迭代器来批量注册多个方块及其对应方块物品，提高效率。
      只有极少数方块会以静态常量成员变量的形式存储。
     */
    static {
        // 石头及其变种（含磨制变种），已存在其楼梯、台阶、墙，但是还没有栅栏和栅栏门。
        for (final Block block : STONES) {
            BlockBuilder.createAllShapes(block, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).build();
        }

        // 圆石。
        BlockBuilder.createAllShapes(COBBLESTONE, Items.FLINT, null, null).withoutRedstone().build();

        // 木板。
        for (final Block block : PLANKS) {
            final boolean isOverworld = OVERWORLD_PLANKS.contains(block);
            BlockBuilder.createBasicShapes(block)
                    .setDefaultTagOf(Shape.VERTICAL_SLAB, WOODEN_VERTICAL_SLABS)
                    .setDefaultTagOf(Shape.VERTICAL_STAIRS, WOODEN_VERTICAL_STAIRS)
                    .setDefaultTagOf(Shape.QUARTER_PIECE, WOODEN_QUARTER_PIECES)
                    .setDefaultTagOf(Shape.VERTICAL_QUARTER_PIECE, WOODEN_VERTICAL_QUARTER_PIECES)
                    .putTag(isOverworld ? OVERWORLD_WOODEN_BLOCKS : null).build();
        }

        // 石化橡木。
        PETRIFIED_OAK_PLANKS =
                BlockBuilder.createBlock().setBlockSettings(FabricBlockSettings.copyOf(PETRIFIED_OAK_SLAB)).setIdentifier(new Identifier("extshape", "petrified_oak_planks")).group(ItemGroup.BUILDING_BLOCKS).setDefaultTag(FULL_BLOCKS).build();
        BlockMappings.SHAPE_TO_MAPPING.get(Shape.SLAB).put(PETRIFIED_OAK_PLANKS, PETRIFIED_OAK_SLAB);

        // 基岩。
        BlockBuilder.createAllShapes(BEDROCK, Items.STICK, ButtonType.HARD, ActivationRule.MOBS).setPreparationConsumer(((shape, abstractBlockBuilder) -> abstractBlockBuilder.setPreparationConsumer(builder -> builder.blockSettings.strength(-1.0F, 3600000.0F).allowsSpawning((state, world, pos, type) -> false)))).build();

        // 青金石块。
        BlockBuilder.createAllShapes(LAPIS_BLOCK, Items.LAPIS_LAZULI, null, ActivationRule.MOBS).build();

        // 砂岩、红砂岩及其切制、錾制、平滑变种。其中，只有平滑砂岩有栅栏、压力板和按钮。
        for (final Block block : SANDSTONES) {
            BlockBuilder.createEmpty(block).withShapes().withWall().build();
        }
        for (final Block block : new Block[]{SMOOTH_SANDSTONE, SMOOTH_RED_SANDSTONE}) {
            BlockBuilder.createEmpty(block).withFences(Items.STICK).withPressurePlate(ActivationRule.MOBS).withButton(ButtonType.STONE).build();
        }

        // 羊毛不注册墙。
        for (final Block block : WOOLS) {
            BlockBuilder.createAllShapes(block, Items.STRING, ButtonType.SOFT, ActivationRule.EVERYTHING).putTag(WOOLEN_BLOCKS).withoutWall().setDefaultTagOf(Map.of(Shape.STAIRS, WOOLEN_STAIRS, Shape.SLAB, WOOLEN_SLABS, Shape.VERTICAL_SLAB, WOOLEN_VERTICAL_SLABS, Shape.FENCE, WOOLEN_FENCES, Shape.FENCE_GATE, WOOLEN_FENCE_GATES, Shape.BUTTON, WOOLEN_BUTTONS, Shape.PRESSURE_PLATE, WOOLEN_PRESSURE_PLATES)).build();
        }

        // 金块。
        BlockBuilder.createAllShapes(GOLD_BLOCK, Items.GOLD_INGOT, null, null).withoutRedstone().build();
        // 铁块。
        BlockBuilder.createAllShapes(IRON_BLOCK, Items.IRON_INGOT, null, null).withoutRedstone().build();

        // 砖栅栏和栅栏门。
        BlockBuilder.createBasicShapes(BRICKS).withFences(Items.BRICK).withWall().build();

        // 苔石栅栏和栅栏门。
        BlockBuilder.createBasicShapes(MOSSY_COBBLESTONE).withFences(Items.STICK).withWall().build();

        // 黑曜石。
        BlockBuilder.createAllShapes(OBSIDIAN, Items.STONE, ButtonType.HARD, ActivationRule.MOBS).build();

        // 钻石块。
        BlockBuilder.createAllShapes(DIAMOND_BLOCK, Items.DIAMOND, null, ActivationRule.MOBS).build();

        // 紫水晶块。
        BlockBuilder.createAllShapes(AMETHYST_BLOCK, Items.AMETHYST_SHARD, null, null).withoutRedstone().build();

        // 冰，由于技术原因，暂不产生。

        // 雪块。
        BlockBuilder.createAllShapes(SNOW_BLOCK, Items.SNOW_BLOCK, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

        // 黏土块。
        BlockBuilder.createBasicShapes(CLAY).build();

        // 南瓜。
        BlockBuilder.createAllShapes(PUMPKIN, Items.PUMPKIN_SEEDS, ButtonType.WOODEN, ActivationRule.EVERYTHING).build();

        // 下界岩。
        BlockBuilder.createAllShapes(NETHERRACK, Items.NETHER_BRICK, ButtonType.STONE, ActivationRule.MOBS).build();

        // 磨制玄武岩。
        BlockBuilder.createAllShapes(POLISHED_BLACKSTONE, Items.NETHERRACK, null, null).build();

        // 荧石可以发光。
        BlockBuilder.createAllShapes(GLOWSTONE, Items.GLOWSTONE_DUST, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

        // 石砖、苔石砖。
        for (final Block block : new Block[]{STONE_BRICKS, MOSSY_STONE_BRICKS, CHISELED_STONE_BRICKS}) {
            BlockBuilder.createBasicShapes(block).withFences(Items.FLINT).withWall().build();
        }

        // 西瓜。
        BlockBuilder.createAllShapes(MELON, Items.MELON_SLICE, ButtonType.WOODEN, ActivationRule.EVERYTHING).build();

        // 下界砖块的栅栏门、按钮和压力板。
        BlockBuilder.createAllShapes(NETHER_BRICKS, Items.NETHER_BRICK, ButtonType.STONE, ActivationRule.MOBS).withoutRedstone().build();

        // 末地石、末地石砖。
        BlockBuilder.createAllShapes(END_STONE, Items.END_STONE_BRICKS, null, null).build();

        // 绿宝石块。
        BlockBuilder.createAllShapes(EMERALD_BLOCK, Items.EMERALD, null, ActivationRule.MOBS).build();

        // 石英、石英砖、平滑石英块、錾制石英块，只有平滑石英块有按钮和压力板。
        for (final Block block : new Block[]{QUARTZ_BLOCK, CHISELED_QUARTZ_BLOCK, QUARTZ_BRICKS, SMOOTH_QUARTZ}) {
            BlockBuilder.createAllShapes(block, Items.QUARTZ, null, null).build();
        }
        BlockBuilder.createEmpty(SMOOTH_QUARTZ).withButton(ButtonType.STONE).withPressurePlate(ActivationRule.MOBS).build();

        // 红色下界砖。
        BlockBuilder.createAllShapes(RED_NETHER_BRICKS, Items.NETHER_BRICK, ButtonType.STONE, ActivationRule.MOBS).build();

        // 陶瓦和彩色陶瓦。
        BlockBuilder.createAllShapes(TERRACOTTA, Items.CLAY, ButtonType.STONE, ActivationRule.MOBS).setDefaultTagOf(Map.of(Shape.STAIRS, TERRACOTTA_STAIRS, Shape.SLAB, TERRACOTTA_SLABS, Shape.VERTICAL_SLAB, TERRACOTTA_VERTICAL_SLABS, Shape.FENCE, TERRACOTTA_FENCES, Shape.FENCE_GATE, TERRACOTTA_FENCE_GATES, Shape.WALL, TERRACOTTA_WALLS, Shape.BUTTON, TERRACOTTA_BUTTONS, Shape.PRESSURE_PLATE, TERRACOTTA_PRESSURE_PLATES)).build();
        for (final Block block : STAINED_TERRACOTTAS) {
            BlockBuilder.createAllShapes(block, Items.CLAY, ButtonType.STONE, ActivationRule.MOBS).setDefaultTagOf(Map.of(Shape.STAIRS, STAINED_TERRACOTTA_STAIRS, Shape.SLAB, STAINED_TERRACOTTA_SLABS, Shape.VERTICAL_SLAB, STAINED_TERRACOTTA_VERTICAL_SLABS, Shape.FENCE, STAINED_TERRACOTTA_FENCES, Shape.FENCE_GATE, STAINED_TERRACOTTA_FENCE_GATES, Shape.WALL, STAINED_TERRACOTTA_WALLS, Shape.BUTTON, STAINED_TERRACOTTA_BUTTONS, Shape.PRESSURE_PLATE, STAINED_TERRACOTTA_PRESSURE_PLATES)).build();
        }

        // 浮冰。
        BlockBuilder.createAllShapes(PACKED_ICE, Items.ICE, ButtonType.STONE, ActivationRule.MOBS).build();

        // 海晶石、海晶石砖、暗海晶石。
        for (final Block block : new Block[]{PRISMARINE, PRISMARINE_BRICKS, DARK_PRISMARINE}) {
            BlockBuilder.createAllShapes(block, Items.PRISMARINE_SHARD, ButtonType.STONE, ActivationRule.MOBS).build();
        }

        // 海晶灯。
        BlockBuilder.createBasicShapes(SEA_LANTERN).build();

        // 平滑石头比较特殊，完整方块和台阶不同。
        SMOOTH_STONE_DOUBLE_SLAB = BlockBuilder.createBlock().setBlockSettings(FabricBlockSettings.copyOf(SMOOTH_STONE)).setIdentifier(new Identifier("extshape", "smooth_stone_slab_double")).group(ItemGroup.BUILDING_BLOCKS).setDefaultTag(FULL_BLOCKS).build();
        BlockBuilder.createAllShapes(SMOOTH_STONE, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).withoutShapes().build();
        BlockMappings.SHAPE_TO_MAPPING.get(Shape.SLAB).put(SMOOTH_STONE_DOUBLE_SLAB, SMOOTH_STONE_SLAB);

        // 紫珀块。
        BlockBuilder.createAllShapes(PURPUR_BLOCK, Items.SHULKER_SHELL, null, null).build();

        // 下界疣块、诡异疣块。
        BlockBuilder.createAllShapes(NETHER_WART_BLOCK, Items.NETHER_WART, null, null).withoutRedstone().build();
        BlockBuilder.createAllShapes(WARPED_WART_BLOCK, Items.WARPED_WART_BLOCK, null, null).withoutRedstone().build();

        // 带釉陶瓦只注册台阶。
        for (final Block block : GLAZED_TERRACOTTAS) {
            new SlabBuilder(block).setInstanceSupplier(builder -> new GlazedTerracottaSlabBlock(FabricBlockSettings.copyOf(builder.baseBlock))).setDefaultTag(GLAZED_TERRACOTTA_SLABS).build();
        }

        // 彩色混凝土。
        for (final Block block : CONCRETES) {
            BlockBuilder.createAllShapes(block, Items.GRAVEL, ButtonType.STONE, ActivationRule.MOBS).setDefaultTagOf(Map.of(Shape.STAIRS, CONCRETE_STAIRS, Shape.SLAB, CONCRETE_SLABS, Shape.VERTICAL_SLAB, CONCRETE_VERTICAL_SLABS, Shape.FENCE, CONCRETE_FENCES, Shape.FENCE_GATE, CONCRETE_FENCE_GATES, Shape.WALL, CONCRETE_WALLS, Shape.BUTTON, CONCRETE_BUTTONS, Shape.PRESSURE_PLATE, CONCRETE_PRESSURE_PLATES)).build();
        }

        // 菌光体。
        BlockBuilder.createAllShapes(SHROOMLIGHT, Items.GLOWSTONE_DUST, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

        // 蜜脾块。
        BlockBuilder.createAllShapes(HONEYCOMB_BLOCK, Items.HONEYCOMB, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

        // 下界合金方块。
        BlockBuilder.createAllShapes(NETHERITE_BLOCK, Items.NETHERITE_INGOT, null, ActivationRule.MOBS).withoutButton().build();

        // 远古残骸。
        BlockBuilder.createAllShapes(ANCIENT_DEBRIS, Items.NETHERITE_SCRAP, null, ActivationRule.MOBS).withoutButton().build();

        // 哭泣的黑曜石。
        BlockBuilder.createAllShapes(CRYING_OBSIDIAN, Items.STONE, ButtonType.HARD, ActivationRule.MOBS).build();

        // 黑石及其变种。
        BlockBuilder.createBasicShapes(BLACKSTONE).build();
        BlockBuilder.createBasicShapes(POLISHED_BLACKSTONE).build();
        BlockBuilder.createBasicShapes(POLISHED_BLACKSTONE_BRICKS).build();
        BlockBuilder.createBasicShapes(CHISELED_POLISHED_BLACKSTONE).build();
        BlockBuilder.createBasicShapes(GILDED_BLACKSTONE).build();

        BlockBuilder.createBasicShapes(CHISELED_NETHER_BRICKS).build();

        // 凝灰岩，方解石。
        BlockBuilder.createAllShapes(TUFF, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).build();
        BlockBuilder.createAllShapes(CALCITE, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).build();

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
            BlockBuilder.createBasicShapes(block).build();
        }

        // 滴水石、苔藓。
        BlockBuilder.createAllShapes(DRIPSTONE_BLOCK, Items.POINTED_DRIPSTONE, ButtonType.STONE, ActivationRule.MOBS).build();
        BlockBuilder.createAllShapes(MOSS_BLOCK, Items.MOSS_CARPET, ButtonType.SOFT, ActivationRule.EVERYTHING).withoutWall().build();

        // 深板岩及其变种。深板岩自身属于 PillarBlock，不适合创建形状。
        for (final Block block : new Block[]{COBBLED_DEEPSLATE, POLISHED_DEEPSLATE, DEEPSLATE_TILES, DEEPSLATE_BRICKS, CHISELED_DEEPSLATE}) {
            BlockBuilder.createAllShapes(block, Items.DEEPSLATE, null, ActivationRule.MOBS).build();
        }

        // 平滑玄武岩。
        BlockBuilder.createAllShapes(SMOOTH_BASALT, Items.FLINT, ButtonType.STONE, ActivationRule.EVERYTHING).build();

        // 粗铁、粗铜、粗金。
        for (Block block : new Block[]{RAW_IRON_BLOCK, RAW_COPPER_BLOCK, RAW_GOLD_BLOCK}) {
            BlockBuilder.createBasicShapes(block).build();
        }
    }

    /**
     * 虽然此函数不执行操作，但是执行此函数会确保此类中的静态部分都遍历一遍。
     */
    public static void init() {
    }
}
