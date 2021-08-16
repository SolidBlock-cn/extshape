package pers.solid.extshape.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock.ActivationRule;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import pers.solid.extshape.block.ExtShapeButtonBlock.ButtonType;
import pers.solid.extshape.builder.BlockBuilder;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import static net.minecraft.block.Blocks.*;
import static pers.solid.extshape.tag.ExtShapeBlockTag.*;

public class ExtShapeBlocks {
    public static final Block SMOOTH_STONE_DOUBLE_SLAB;

    static {
        // 石头及其变种（含磨制变种），已存在其楼梯、台阶、墙，但是还没有栅栏和栅栏门。
        for (final Block block : STONES) {
            BlockBuilder.createAllShapes(block, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).build();
        }

        // 平滑石头比较特殊，完整方块和台阶不同。
        SMOOTH_STONE_DOUBLE_SLAB =
                BlockBuilder.createBlock().setBlockSettings(FabricBlockSettings.copyOf(SMOOTH_STONE)).setIdentifier(new Identifier(
                        "extshape", "smooth_stone_slab_double")).putTag(PICKAXE_MINEABLE).setDefaultTag(FULL_BLOCKS).build();
        BlockBuilder.createAllShapes(SMOOTH_STONE, Items.FLINT, ButtonType.STONE, ActivationRule.MOBS).build();

        // 深板岩圆石、磨制深板岩
        for (final Block BLOCK : new Block[]{COBBLED_DEEPSLATE, POLISHED_DEEPSLATE}) {
            BlockBuilder.createVerticalSlab(BLOCK).build();
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
            BlockBuilder.createVerticalSlab(BLOCK).setDefaultTag(WOODEN_VERTICAL_SLABS).putTag(isOverworld ?
                    OVERWORLD_WOODEN_BLOCKS : null).build();
        }
        WOODEN_VERTICAL_SLABS.addToTag(VERTICAL_SLABS);

        // 基岩
        BlockBuilder.createAllShapes(BEDROCK, Items.STICK, ButtonType.HARD, ActivationRule.MOBS).putTag(BEDROCK_BLOCKS).build();

        // 紫水晶块。
        BlockBuilder.createAllShapes(AMETHYST_BLOCK, Items.AMETHYST_SHARD, ButtonType.STONE, ActivationRule.MOBS).build();

        // 铁块。
        BlockBuilder.createAllShapes(IRON_BLOCK, Items.IRON_INGOT, ButtonType.STONE, null).withoutPressurePlate().build();

        // 金块，钻石块，下界合金块，青金石块
        for (final Block BLOCK : new Block[]{GOLD_BLOCK, DIAMOND_BLOCK, NETHERITE_BLOCK, LAPIS_BLOCK}) {
            Item item = BLOCK == GOLD_BLOCK ? Items.GOLD_INGOT : BLOCK == DIAMOND_BLOCK ? Items.DIAMOND :
                    BLOCK == NETHERITE_BLOCK ? Items.NETHERITE_INGOT : BLOCK == LAPIS_BLOCK ? Items.LAPIS_LAZULI : null;
            BlockBuilder.createAllShapes(BLOCK, item, ButtonType.HARD, null).withoutPressurePlate().build();
        }

        // 砂岩、红砂岩及其切制、錾制、平滑变种。栅栏合成材料统一改为stick。
        for (final Block block : SANDSTONES) {
            BlockBuilder.createAllShapes(block, Items.STICK, ButtonType.STONE, ActivationRule.MOBS).build();
        }

        // 羊毛不注册墙。
        for (final Block block : WOOLS) {
            BlockBuilder.createAllShapes(block, Items.STRING, ButtonType.SOFT, ActivationRule.EVERYTHING).putTag(WOOLEN_BLOCKS).withoutWall().setDefaultTags(WOOLEN_STAIRS, WOOLEN_SLABS, WOODEN_VERTICAL_SLABS, WOOLEN_FENCES, WOOLEN_FENCE_GATES, null, WOOLEN_BUTTONS, WOOLEN_PRESSURE_PLATES).build();
        }

        WOOLEN_STAIRS.addToTag(STAIRS);
        WOOLEN_SLABS.addToTag(SLABS);
        WOOLEN_FENCES.addToTag(FENCES);
        WOOLEN_FENCE_GATES.addToTag(FENCE_GATES);
        WOOLEN_BUTTONS.addToTag(BUTTONS);
        WOOLEN_PRESSURE_PLATES.addToTag(PRESSURE_PLATES);
        WOOLEN_VERTICAL_SLABS.addToTag(VERTICAL_SLABS);

        // 砖栅栏和栅栏门。
        BlockBuilder.createAllShapes(BRICKS, Items.BRICK, null, null).withoutRedstone().build();

        // 苔石栅栏和栅栏门。
        BlockBuilder.createAllShapes(MOSSY_COBBLESTONE, Items.FLINT, null, null).withoutRedstone().build();

        // 黑曜石。
        BlockBuilder.createAllShapes(OBSIDIAN, Items.STONE, ButtonType.HARD, ActivationRule.MOBS).build();

        // 紫珀块。
        BlockBuilder.createAllShapes(PURPUR_BLOCK, null, null, null).withoutFences().withoutRedstone().build();

        // 冰，由于技术原因，暂不产生。

        // 雪块
        BlockBuilder.createAllShapes(SNOW_BLOCK, Items.SNOW_BLOCK, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

        // 下界岩。
        BlockBuilder.createAllShapes(NETHERRACK, Items.NETHER_BRICK, ButtonType.STONE, ActivationRule.MOBS).putTag(INFINIBURN_OVERWORLD).build();

        // 荧石可以发光。
        BlockBuilder.createAllShapes(GLOWSTONE, Items.GLOWSTONE_DUST, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

        // 石砖、苔石砖、深板岩砖、深板岩瓦
        for (final Block block : new Block[]{STONE_BRICKS, MOSSY_STONE_BRICKS, DEEPSLATE_BRICKS, DEEPSLATE_TILES}) {
            BlockBuilder.createAllShapes(block).build();
        }

        // 下界砖块的栅栏门、按钮和压力板。
        BlockBuilder.createAllShapes(NETHER_BRICKS, Items.NETHER_BRICK, ButtonType.HARD, ActivationRule.MOBS).build();

        // 末地石砖。
        BlockBuilder.createAllShapes(END_STONE_BRICKS).build();

        // 绿宝石块。
        BlockBuilder.createAllShapes(EMERALD_BLOCK, Items.EMERALD, ButtonType.HARD, ActivationRule.MOBS).build();

        // 石英、石英砖、平滑石英块、錾制石英块，其中石英砖和錾制石英块还需要楼梯台阶。
        for (final Block block : new Block[]{QUARTZ_BLOCK, QUARTZ_BRICKS, SMOOTH_QUARTZ, CHISELED_QUARTZ_BLOCK}) {
            BlockBuilder.createAllShapes(block, Items.QUARTZ, ButtonType.STONE, ActivationRule.MOBS).build();
        }

        // 陶瓦和彩色陶瓦。
        for (final Block block : new Block[]{TERRACOTTA}) {
            BlockBuilder.createAllShapes(block, Items.CLAY, ButtonType.STONE, ActivationRule.MOBS).setDefaultTags(TERRACOTTA_STAIRS, TERRACOTTA_SLABS,
                    TERRACOTTA_VERTICAL_SLABS, TERRACOTTA_FENCE_GATES, TERRACOTTA_FENCE_GATES, TERRACOTTA_WALLS,
                    TERRACOTTA_BUTTONS, TERRACOTTA_PRESSURE_PLATES).build();
        }

        TERRACOTTA_STAIRS.addToTag(STAIRS);
        TERRACOTTA_SLABS.addToTag(SLABS);
        TERRACOTTA_FENCES.addToTag(FENCES);
        TERRACOTTA_FENCE_GATES.addToTag(FENCE_GATES);
        TERRACOTTA_WALLS.addToTag(WALLS);
        TERRACOTTA_BUTTONS.addToTag(BUTTONS);
        TERRACOTTA_PRESSURE_PLATES.addToTag(PRESSURE_PLATES);
        TERRACOTTA_VERTICAL_SLABS.addToTag(VERTICAL_SLABS);

        for (final Block block : STAINED_TERRACOTTAS) {
            BlockBuilder.createAllShapes(block, Items.CLAY, ButtonType.STONE, ActivationRule.MOBS).setDefaultTags(STAINED_TERRACOTTA_STAIRS,
                    STAINED_TERRACOTTA_SLABS, STAINED_TERRACOTTA_VERTICAL_SLABS, STAINED_TERRACOTTA_FENCES,
                    STAINED_TERRACOTTA_FENCE_GATES, STAINED_TERRACOTTA_WALLS, STAINED_TERRACOTTA_BUTTONS, STAINED_TERRACOTTA_PRESSURE_PLATES).build();
        }

        STAINED_TERRACOTTA_STAIRS.addToTag(TERRACOTTA_STAIRS);
        STAINED_TERRACOTTA_SLABS.addToTag(TERRACOTTA_SLABS);
        STAINED_TERRACOTTA_FENCES.addToTag(TERRACOTTA_FENCES);
        STAINED_TERRACOTTA_FENCE_GATES.addToTag(TERRACOTTA_FENCE_GATES);
        STAINED_TERRACOTTA_WALLS.addToTag(TERRACOTTA_WALLS);
        STAINED_TERRACOTTA_BUTTONS.addToTag(TERRACOTTA_BUTTONS);
        STAINED_TERRACOTTA_PRESSURE_PLATES.addToTag(TERRACOTTA_PRESSURE_PLATES);
        STAINED_TERRACOTTA_VERTICAL_SLABS.addToTag(TERRACOTTA_VERTICAL_SLABS);

        // 浮冰。
        BlockBuilder.createAllShapes(PACKED_ICE, Items.ICE, ButtonType.STONE, ActivationRule.MOBS).build();

        // 海晶石、海晶石砖、暗海晶石。
        for (final Block block : new Block[]{PRISMARINE, PRISMARINE_BRICKS, DARK_PRISMARINE}) {
            BlockBuilder.createAllShapes(block, Items.PRISMARINE_SHARD, ButtonType.STONE, ActivationRule.MOBS).build();
        }

        // 下界疣块、诡异疣块。
        BlockBuilder.createAllShapes(NETHER_WART_BLOCK, Items.NETHER_WART, ButtonType.SOFT, ActivationRule.EVERYTHING).build();
        BlockBuilder.createAllShapes(WARPED_WART_BLOCK, Items.WARPED_WART_BLOCK, ButtonType.SOFT, ActivationRule.EVERYTHING).withoutRedstone().build();

        // 红色下界砖。
        BlockBuilder.createAllShapes(RED_NETHER_BRICKS, Items.NETHER_BRICK, ButtonType.SOFT, ActivationRule.EVERYTHING).build();

        // 彩色混凝土。
        for (final Block block : CONCRETES) {
            BlockBuilder.createAllShapes(block, Items.GRAVEL, ButtonType.STONE, ActivationRule.MOBS).setDefaultTags(CONCRETE_STAIRS, CONCRETE_SLABS, CONCRETE_VERTICAL_SLABS,
                    CONCRETE_FENCES, CONCRETE_FENCE_GATES, CONCRETE_WALLS, CONCRETE_BUTTONS, CONCRETE_PRESSURE_PLATES).build();
        }

        CONCRETE_STAIRS.addToTag(STAIRS);
        CONCRETE_SLABS.addToTag(SLABS);
        CONCRETE_FENCES.addToTag(FENCES);
        CONCRETE_FENCE_GATES.addToTag(FENCE_GATES);
        CONCRETE_WALLS.addToTag(WALLS);
        CONCRETE_BUTTONS.addToTag(BUTTONS);
        CONCRETE_PRESSURE_PLATES.addToTag(PRESSURE_PLATES);
        CONCRETE_VERTICAL_SLABS.addToTag(VERTICAL_SLABS);

        // 哭泣的黑曜石。
        BlockBuilder.createAllShapes(CRYING_OBSIDIAN, Items.STONE, ButtonType.HARD, ActivationRule.MOBS).build();

        // 带釉陶瓦只注册台阶。
        for (final Block BLOCK : GLAZED_TERRACOTTAS) {
            BlockBuilder.createSlab(BLOCK).setInstance(new GlazedTerracottaSlabBlock(FabricBlockSettings.copyOf(BLOCK))).setDefaultTag(GLAZED_TERRACOTTA_SLABS).build();
        }

        GLAZED_TERRACOTTA_SLABS.addToTag(SLABS);

        ExtShapeBlockTag.completeMineableTags();
    }

    public static void init() {
    }
}
