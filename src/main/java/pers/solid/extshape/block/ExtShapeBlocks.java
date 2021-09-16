package pers.solid.extshape.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock.ActivationRule;
import net.minecraft.item.Item;
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

        // 石化橡木。
        PETRIFIED_OAK_PLANKS =
                BlockBuilder.createBlock().setBlockSettings(FabricBlockSettings.copyOf(PETRIFIED_OAK_SLAB)).setIdentifier(new Identifier("extshape", "petrified_oak_planks")).group(ItemGroup.BUILDING_BLOCKS).setDefaultTag(FULL_BLOCKS).build();
        BlockMappings.SHAPE_TO_MAPPING.get(Shape.slab).put(PETRIFIED_OAK_PLANKS, PETRIFIED_OAK_SLAB);

        // 平滑石头比较特殊，完整方块和台阶不同。
        SMOOTH_STONE_DOUBLE_SLAB =
                BlockBuilder.createBlock().setBlockSettings(FabricBlockSettings.copyOf(SMOOTH_STONE)).setIdentifier(new Identifier(
                        "extshape", "smooth_stone_slab_double")).group(ItemGroup.BUILDING_BLOCKS).setDefaultTag(FULL_BLOCKS).build();
        BlockBuilder.createAllShapes(SMOOTH_STONE, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).withoutShapes().build();
        BlockMappings.SHAPE_TO_MAPPING.get(Shape.slab).put(SMOOTH_STONE_DOUBLE_SLAB, SMOOTH_STONE_SLAB);

        // 深板岩圆石、磨制深板岩。
        for (final Block BLOCK : new Block[]{COBBLED_DEEPSLATE, POLISHED_DEEPSLATE}) {
            BlockBuilder.createEmpty(BLOCK).withShapes().build();
        }

        // 凝灰岩，方解石。
        for (final Block BLOCK : new Block[]{TUFF, CALCITE}) {
            BlockBuilder.createAllShapes(BLOCK, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).build();
        }

        // 圆石。
        BlockBuilder.createAllShapes(COBBLESTONE, Items.FLINT, null, null).withoutRedstone().build();

        // 木板。
        for (final Block BLOCK : PLANKS) {
            final boolean isOverworld = OVERWORLD_PLANKS.contains(BLOCK);
            BlockBuilder.createBasicShapes(BLOCK).setDefaultTagOf(Shape.verticalSlab, WOODEN_VERTICAL_SLABS).setDefaultTagOf(Shape.verticalStairs, WOODEN_VERTICAL_STAIRS).setDefaultTagOf(Shape.quarterPiece, WOODEN_QUARTER_PIECES).setDefaultTagOf(Shape.verticalQuarterPiece, WOODEN_VERTICAL_QUARTER_PIECES).putTag(isOverworld ?
                    OVERWORLD_WOODEN_BLOCKS : null).build();
        }

        // 基岩
        BlockBuilder.createAllShapes(BEDROCK, Items.STICK, ButtonType.HARD, ActivationRule.MOBS).build();

        // 紫水晶块。
        BlockBuilder.createAllShapes(AMETHYST_BLOCK, Items.AMETHYST_SHARD, null, null).withoutRedstone().build();

        // 铁块。
        BlockBuilder.createAllShapes(IRON_BLOCK, Items.IRON_INGOT, ButtonType.STONE, null).withoutRedstone().build();

        // 金块，钻石块，下界合金块，青金石块
        for (final Block BLOCK : new Block[]{GOLD_BLOCK, DIAMOND_BLOCK, NETHERITE_BLOCK, LAPIS_BLOCK}) {
            Item item = BLOCK == GOLD_BLOCK ? Items.GOLD_INGOT : BLOCK == DIAMOND_BLOCK ? Items.DIAMOND :
                    BLOCK == NETHERITE_BLOCK ? Items.NETHERITE_INGOT : BLOCK == LAPIS_BLOCK ? Items.LAPIS_LAZULI : null;
            BlockBuilder.createAllShapes(BLOCK, item, null, null).withoutRedstone().build();
        }

        // 砂岩、红砂岩及其切制、錾制、平滑变种。其中，只有平滑砂岩有栅栏、压力板和按钮。
        for (final Block block : SANDSTONES) {
            BlockBuilder.createEmpty(block).withShapes().withWall().build();
        }
        for (final Block block : new Block[]{SMOOTH_SANDSTONE, SMOOTH_RED_SANDSTONE}) {
            BlockBuilder.createEmpty(block).withFences(Items.STICK).withPressurePlate(ActivationRule.MOBS).withButton(ButtonType.STONE).build();
        }

        // 羊毛不注册墙。
        for (final Block block : WOOLS) {
            BlockBuilder.createAllShapes(block, Items.STRING, ButtonType.SOFT, ActivationRule.EVERYTHING).putTag(WOOLEN_BLOCKS).withoutWall().setDefaultTagOf(Map.of(Shape.stairs, WOOLEN_STAIRS, Shape.slab, WOOLEN_SLABS, Shape.verticalSlab, WOOLEN_VERTICAL_SLABS, Shape.fence, WOOLEN_FENCES, Shape.fenceGate, WOOLEN_FENCE_GATES, Shape.button, WOOLEN_BUTTONS, Shape.pressurePlate, WOOLEN_PRESSURE_PLATES)).build();
        }

        // 砖栅栏和栅栏门。
        BlockBuilder.createBasicShapes(BRICKS).withFences(Items.BRICK).withWall().build();

        // 苔石栅栏和栅栏门。
        BlockBuilder.createBasicShapes(MOSSY_COBBLESTONE).withFences(Items.STICK).withWall().build();

        // 黑曜石。
        BlockBuilder.createAllShapes(OBSIDIAN, Items.STONE, ButtonType.HARD, ActivationRule.MOBS).build();

        // 紫珀块。
        BlockBuilder.createBasicShapes(PURPUR_BLOCK).withWall().build();

        // 冰，由于技术原因，暂不产生。

        // 雪块
        BlockBuilder.createAllShapes(SNOW_BLOCK, Items.SNOW_BLOCK, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

        // 黏土块
        BlockBuilder.createBasicShapes(CLAY).build();

        // 下界岩。
        BlockBuilder.createAllShapes(NETHERRACK, Items.NETHER_BRICK, ButtonType.STONE, ActivationRule.MOBS).build();

        // 平滑玄武岩
        BlockBuilder.createAllShapes(SMOOTH_BASALT,Items.FLINT,ButtonType.STONE, ActivationRule.EVERYTHING).build();

        // 荧石可以发光。
        BlockBuilder.createAllShapes(GLOWSTONE, Items.GLOWSTONE_DUST, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

        // 石砖、苔石砖、深板岩砖、深板岩瓦
        for (final Block block : new Block[]{STONE_BRICKS, MOSSY_STONE_BRICKS, DEEPSLATE_BRICKS, DEEPSLATE_TILES}) {
            BlockBuilder.createBasicShapes(block).withoutRedstone().build();
        }

        // 下界砖块的栅栏门、按钮和压力板。
        BlockBuilder.createAllShapes(NETHER_BRICKS, Items.NETHER_BRICK, null, null).withoutRedstone().build();

        // 末地石、末地石砖。
        BlockBuilder.createBasicShapes(END_STONE).withWall().withoutRedstone().build();
        BlockBuilder.createBasicShapes(END_STONE_BRICKS).withoutRedstone().build();

        // 绿宝石块。
        BlockBuilder.createAllShapes(EMERALD_BLOCK, Items.EMERALD, ButtonType.HARD, ActivationRule.MOBS).withoutRedstone().build();

        // 石英、石英砖、平滑石英块、錾制石英块，只有平滑石英块有按钮和压力板。
        for (final Block block : new Block[]{QUARTZ_BLOCK, QUARTZ_BRICKS, SMOOTH_QUARTZ, CHISELED_QUARTZ_BLOCK}) {
            BlockBuilder.createAllShapes(block, Items.QUARTZ, ButtonType.STONE, ActivationRule.MOBS).withoutRedstone().build();
        }
        BlockBuilder.createEmpty(SMOOTH_QUARTZ).withButton(ButtonType.STONE).withPressurePlate(ActivationRule.MOBS).build();

        // 陶瓦和彩色陶瓦。
        for (final Block block : new Block[]{TERRACOTTA}) {
            BlockBuilder.createAllShapes(block, Items.CLAY, ButtonType.STONE, ActivationRule.MOBS).setDefaultTagOf(Map.of(Shape.stairs, TERRACOTTA_STAIRS, Shape.slab, TERRACOTTA_SLABS, Shape.verticalSlab,
                    TERRACOTTA_VERTICAL_SLABS, Shape.fence, TERRACOTTA_FENCES, Shape.fenceGate, TERRACOTTA_FENCE_GATES,
                    Shape.wall,
                    TERRACOTTA_WALLS, Shape.button,
                    TERRACOTTA_BUTTONS, Shape.pressurePlate, TERRACOTTA_PRESSURE_PLATES)).build();
        }

        for (final Block block : STAINED_TERRACOTTAS) {
            BlockBuilder.createAllShapes(block, Items.CLAY, ButtonType.STONE, ActivationRule.MOBS).setDefaultTagOf(Map.of(Shape.stairs, STAINED_TERRACOTTA_STAIRS, Shape.slab,
                    STAINED_TERRACOTTA_SLABS, Shape.verticalSlab, STAINED_TERRACOTTA_VERTICAL_SLABS, Shape.fence,
                    STAINED_TERRACOTTA_FENCES, Shape.fenceGate,
                    STAINED_TERRACOTTA_FENCE_GATES, Shape.wall, STAINED_TERRACOTTA_WALLS, Shape.button,
                    STAINED_TERRACOTTA_BUTTONS, Shape.pressurePlate,
                    STAINED_TERRACOTTA_PRESSURE_PLATES)).build();
        }

        // 浮冰。
        BlockBuilder.createAllShapes(PACKED_ICE, Items.ICE, ButtonType.STONE, ActivationRule.MOBS).build();

        // 海晶石、海晶石砖、暗海晶石。
        for (final Block block : new Block[]{PRISMARINE, PRISMARINE_BRICKS, DARK_PRISMARINE}) {
            BlockBuilder.createAllShapes(block, Items.PRISMARINE_SHARD, ButtonType.STONE, ActivationRule.MOBS).build();
        }

        // 下界疣块、诡异疣块。
        BlockBuilder.createAllShapes(NETHER_WART_BLOCK, Items.NETHER_WART, null, null).withoutRedstone().build();
        BlockBuilder.createAllShapes(WARPED_WART_BLOCK, Items.WARPED_WART_BLOCK, null, null).withoutRedstone().build();

        // 红色下界砖。
        BlockBuilder.createAllShapes(RED_NETHER_BRICKS, Items.NETHER_BRICK, ButtonType.SOFT,
                ActivationRule.EVERYTHING).withoutRedstone().build();

        // 彩色混凝土。
        for (final Block block : CONCRETES) {
            BlockBuilder.createAllShapes(block, Items.GRAVEL, ButtonType.STONE, ActivationRule.MOBS).setDefaultTagOf(Map.of(Shape.stairs, CONCRETE_STAIRS, Shape.slab, CONCRETE_SLABS, Shape.verticalSlab, CONCRETE_VERTICAL_SLABS, Shape.fence,
                    CONCRETE_FENCES, Shape.fenceGate, CONCRETE_FENCE_GATES, Shape.wall, CONCRETE_WALLS,
                    Shape.button, CONCRETE_BUTTONS, Shape.pressurePlate,
                    CONCRETE_PRESSURE_PLATES)).build();
        }

        // 哭泣的黑曜石。
        BlockBuilder.createAllShapes(CRYING_OBSIDIAN, Items.STONE, ButtonType.HARD, ActivationRule.MOBS).build();

        // 带釉陶瓦只注册台阶。
        for (final Block BLOCK : GLAZED_TERRACOTTAS) {
            new SlabBuilder(BLOCK).setInstance(new GlazedTerracottaSlabBlock(FabricBlockSettings.copyOf(BLOCK))).setDefaultTag(GLAZED_TERRACOTTA_SLABS).build();
        }
    }

    /**
     * 虽然此函数不执行操作，但是执行此函数会确保此类中的静态部分都遍历一遍。
     */
    public static void init() {
    }
}
