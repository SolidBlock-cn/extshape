package pers.solid.extshape.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.block.Blocks.*;
import static pers.solid.extshape.block.ExtShapeButtonBlock.ButtonType.*;
import static pers.solid.extshape.tag.ExtShapeBlockTag.*;

public class Blocks {
    public static final SmoothStoneDoubleSlabBlock SMOOTH_STONE_DOUBLE_SLAB;

    static {
        // 石头及其变种（含磨制变种），已存在其楼梯、台阶、墙，但是还没有栅栏和栅栏门。
        for (final Block BLOCK : STONES) {
            new ExtShapeFenceBlock(BLOCK, Items.FLINT).addToTag();
            new ExtShapeFenceGateBlock(BLOCK, Items.FLINT).addToTag();
            new ExtShapeVerticalSlabBlock(BLOCK).addToTag();
        }
        for (final Block BLOCK : STONE_VARIANTS) {
            new ExtShapeButtonBlock(stone, BLOCK).addToTag();
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, BLOCK).addToTag();
        }

        // 平滑石头比较特殊，完整方块和台阶不同。
        SMOOTH_STONE_DOUBLE_SLAB =
                (SmoothStoneDoubleSlabBlock) new SmoothStoneDoubleSlabBlock(new Identifier("extshape",
                        "smooth_stone_slab_double"), FabricBlockSettings.copyOf(SMOOTH_STONE)).addToTag().addToTag(PICKAXE_MINEABLE);

        new ExtShapeFenceBlock(SMOOTH_STONE, Items.FLINT).addToTag();
        new ExtShapeFenceGateBlock(SMOOTH_STONE, Items.FLINT).addToTag();
        new ExtShapeWallBlock(SMOOTH_STONE).addToTag();
        new ExtShapeButtonBlock(stone, SMOOTH_STONE).addToTag();

        // 凝灰岩，方解石。
        for (final Block BLOCK : new Block[]{TUFF, CALCITE}) {
            new ExtShapeStairsBlock(BLOCK).addToTag();
            new ExtShapeSlabBlock(BLOCK).addToTag();
            new ExtShapeFenceBlock(BLOCK, Items.FLINT).addToTag();
            new ExtShapeFenceGateBlock(BLOCK, Items.FLINT).addToTag();
            new ExtShapeWallBlock(BLOCK).addToTag();
            new ExtShapeButtonBlock(stone, BLOCK).addToTag();
            new ExtShapeVerticalSlabBlock(BLOCK).addToTag();
        }

        // 圆石栅栏和栅栏门。
        new ExtShapeFenceBlock(COBBLESTONE, Items.FLINT).addToTag();
        new ExtShapeFenceGateBlock(COBBLESTONE, Items.FLINT).addToTag();
        new ExtShapeVerticalSlabBlock(COBBLESTONE).addToTag();

        // 基岩
        for (final Block BLOCK : new Block[]{BEDROCK}) {
            new ExtShapeStairsBlock(BLOCK).addToTag().addToTag(BEDROCK_BLOCKS);
            new ExtShapeSlabBlock(BLOCK).addToTag().addToTag(BEDROCK_BLOCKS);
            new ExtShapeFenceBlock(BLOCK, Items.FLINT).addToTag().addToTag(BEDROCK_BLOCKS);
            new ExtShapeFenceGateBlock(BLOCK, Items.FLINT).addToTag().addToTag(BEDROCK_BLOCKS);
            new ExtShapeWallBlock(BLOCK).addToTag().addToTag(BEDROCK_BLOCKS);
            new ExtShapeButtonBlock(hard, BLOCK).addToTag().addToTag(BEDROCK_BLOCKS);
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, BLOCK).addToTag().addToTag(BEDROCK_BLOCKS);
            new ExtShapeVerticalSlabBlock(BEDROCK).addToTag().addToTag(BEDROCK_BLOCKS);
        }

        // 紫水晶块。
        new ExtShapeStairsBlock(AMETHYST_BLOCK).addToTag();
        new ExtShapeSlabBlock(AMETHYST_BLOCK).addToTag();
        new ExtShapeFenceBlock(AMETHYST_BLOCK, Items.AMETHYST_SHARD).addToTag();
        new ExtShapeFenceGateBlock(AMETHYST_BLOCK, Items.AMETHYST_SHARD).addToTag();
        new ExtShapeWallBlock(AMETHYST_BLOCK).addToTag();
        new ExtShapeButtonBlock(stone, AMETHYST_BLOCK).addToTag();
        new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, AMETHYST_BLOCK).addToTag();
        new ExtShapeVerticalSlabBlock(AMETHYST_BLOCK).addToTag();

        // 铁块。
        new ExtShapeStairsBlock(IRON_BLOCK).addToTag();
        new ExtShapeSlabBlock(IRON_BLOCK).addToTag();
        new ExtShapeFenceBlock(IRON_BLOCK, Items.IRON_INGOT).addToTag();
        new ExtShapeFenceGateBlock(IRON_BLOCK, Items.IRON_INGOT).addToTag();
        new ExtShapeWallBlock(IRON_BLOCK).addToTag();
        new ExtShapeButtonBlock(stone, IRON_BLOCK).addToTag();
        new ExtShapeVerticalSlabBlock(IRON_BLOCK).addToTag();

        // 金块，钻石块，下界合金块，青金石块
        for (final Block BLOCK : new Block[]{GOLD_BLOCK, DIAMOND_BLOCK, NETHERITE_BLOCK, LAPIS_BLOCK}) {
            @Nullable FabricItemSettings settings = BLOCK == NETHERITE_BLOCK ? new FabricItemSettings().fireproof() :
                    null;
            Item item = BLOCK == GOLD_BLOCK ? Items.GOLD_INGOT : BLOCK == DIAMOND_BLOCK ? Items.DIAMOND :
                    BLOCK == NETHERITE_BLOCK ? Items.NETHERITE_INGOT : BLOCK == LAPIS_BLOCK ? Items.LAPIS_LAZULI : null;
            new ExtShapeStairsBlock(BLOCK, null, null, settings).addToTag();
            new ExtShapeSlabBlock(BLOCK, null, null, settings).addToTag();
            new ExtShapeFenceBlock(BLOCK, item, null, null, settings).addToTag();
            new ExtShapeFenceGateBlock(BLOCK, item, null, null, settings).addToTag();
            new ExtShapeWallBlock(BLOCK, null, null, settings).addToTag();
            new ExtShapeButtonBlock(hard, BLOCK, null, null, settings).addToTag();
            new ExtShapeVerticalSlabBlock(BLOCK,null,null,settings).addToTag();
        }

        // 砂岩、红砂岩，注册栅栏、按钮和压力板。
        new ExtShapeFenceBlock(SANDSTONE, Items.SAND).addToTag();
        new ExtShapeFenceGateBlock(SANDSTONE, Items.SAND).addToTag();
        new ExtShapeFenceBlock(RED_SANDSTONE, Items.RED_SAND).addToTag();
        new ExtShapeFenceGateBlock(RED_SANDSTONE, Items.RED_SAND).addToTag();
        new ExtShapeButtonBlock(stone, SANDSTONE).addToTag();
        new ExtShapeButtonBlock(stone, RED_SANDSTONE).addToTag();
        new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, SANDSTONE).addToTag();
        new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, RED_SANDSTONE).addToTag();
        new ExtShapeVerticalSlabBlock(SANDSTONE).addToTag();
        new ExtShapeVerticalSlabBlock(RED_SANDSTONE).addToTag();

        // 切制砂岩、切制红砂岩，注册楼梯、栅栏、墙。
        for (final Block BLOCK : new Block[]{CUT_SANDSTONE, CUT_RED_SANDSTONE}) {
            new ExtShapeStairsBlock(BLOCK).addToTag();
            new ExtShapeFenceBlock(BLOCK, BLOCK == CUT_SANDSTONE ? Items.SAND : Items.RED_SAND).addToTag();
            new ExtShapeFenceGateBlock(BLOCK, BLOCK == CUT_SANDSTONE ? Items.SAND : Items.RED_SAND).addToTag();
            new ExtShapeWallBlock(BLOCK).addToTag();
            new ExtShapeButtonBlock(stone, BLOCK).addToTag();
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, BLOCK);
            new ExtShapeVerticalSlabBlock(BLOCK).addToTag();
        }

        // 錾制砂岩、切制红砂岩，注册楼梯、台阶、栅栏、墙。
        for (final Block BLOCK : new Block[]{CHISELED_SANDSTONE, CHISELED_RED_SANDSTONE}) {
            new ExtShapeStairsBlock(BLOCK).addToTag();
            new ExtShapeSlabBlock(BLOCK).addToTag();
            new ExtShapeFenceBlock(BLOCK, BLOCK == CHISELED_SANDSTONE ? Items.SAND : Items.RED_SAND).addToTag();
            new ExtShapeFenceGateBlock(BLOCK, BLOCK == CHISELED_SANDSTONE ? Items.SAND : Items.RED_SAND).addToTag();
            new ExtShapeWallBlock(BLOCK).addToTag();
            new ExtShapeButtonBlock(stone, BLOCK).addToTag();
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, BLOCK);
            new ExtShapeVerticalSlabBlock(BLOCK).addToTag();
        }

        // 平滑砂岩、平滑红砂岩，注册栅栏、墙、按钮和压力板。
        for (final Block BLOCK : new Block[]{SMOOTH_SANDSTONE, SMOOTH_RED_SANDSTONE}) {
            new ExtShapeFenceBlock(BLOCK, BLOCK == SMOOTH_SANDSTONE ? Items.SAND : Items.RED_SAND).addToTag();
            new ExtShapeFenceGateBlock(BLOCK, BLOCK == SMOOTH_SANDSTONE ? Items.SAND : Items.RED_SAND).addToTag();
            new ExtShapeWallBlock(BLOCK).addToTag();
            new ExtShapeButtonBlock(stone, BLOCK).addToTag();
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, BLOCK);
            new ExtShapeVerticalSlabBlock(BLOCK).addToTag();
        }

        // 羊毛不注册墙。
        for (final Block BLOCK : WOOLS) {
            new ExtShapeStairsBlock(BLOCK).addToTag(WOOLEN_STAIRS);
            new ExtShapeSlabBlock(BLOCK).addToTag(WOOLEN_SLABS);
            new ExtShapeFenceBlock(BLOCK, Items.STRING).addToTag(WOOLEN_FENCES);
            new ExtShapeFenceGateBlock(BLOCK, Items.STRING).addToTag(WOOLEN_FENCE_GATES);
            new ExtShapeButtonBlock(soft, BLOCK).addToTag(WOOLEN_BUTTONS);
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, BLOCK).addToTag(WOOLEN_PRESSURE_PLATES);
            new ExtShapeVerticalSlabBlock(BLOCK).addToTag(WOOLEN_VERTICAL_SLABS);
        }

        WOOLEN_STAIRS.addToTag(STAIRS);
        WOOLEN_SLABS.addToTag(SLABS);
        WOOLEN_FENCES.addToTag(FENCES);
        WOOLEN_FENCE_GATES.addToTag(FENCE_GATES);
        WOOLEN_BUTTONS.addToTag(BUTTONS);
        WOOLEN_PRESSURE_PLATES.addToTag(PRESSURE_PLATES);
        WOOLEN_VERTICAL_SLABS.addToTag(VERTICAL_SLABS);

        // 砖栅栏和栅栏门。
        new ExtShapeFenceBlock(BRICKS, Items.BRICK).addToTag();
        new ExtShapeFenceGateBlock(BRICKS, Items.BRICK).addToTag();
        new ExtShapeVerticalSlabBlock(BRICKS).addToTag();

        // 苔石栅栏和栅栏门。
        new ExtShapeFenceBlock(MOSSY_COBBLESTONE, Items.FLINT).addToTag();
        new ExtShapeFenceGateBlock(MOSSY_COBBLESTONE, Items.FLINT).addToTag();
        new ExtShapeVerticalSlabBlock(MOSSY_COBBLESTONE).addToTag();


        // 黑曜石。
        new ExtShapeStairsBlock(OBSIDIAN).addToTag();
        new ExtShapeSlabBlock(OBSIDIAN).addToTag();
        new ExtShapeFenceBlock(OBSIDIAN, Items.STONE).addToTag();
        new ExtShapeFenceGateBlock(OBSIDIAN, Items.STONE).addToTag();
        new ExtShapeWallBlock(OBSIDIAN).addToTag();
        new ExtShapeButtonBlock(hard, OBSIDIAN).addToTag();
        new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, OBSIDIAN).addToTag();
        new ExtShapeVerticalSlabBlock(OBSIDIAN).addToTag();

        // 雪块
        new ExtShapeStairsBlock(SNOW_BLOCK).addToTag();
        new ExtShapeSlabBlock(SNOW_BLOCK).addToTag();
        new ExtShapeFenceBlock(SNOW_BLOCK, Items.SNOWBALL).addToTag();
        new ExtShapeFenceGateBlock(SNOW_BLOCK, Items.SNOWBALL).addToTag();
        new ExtShapeWallBlock(SNOW_BLOCK).addToTag();
        new ExtShapeButtonBlock(soft, SNOW_BLOCK).addToTag();
        new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, SNOW_BLOCK).addToTag();
        new ExtShapeVerticalSlabBlock(SNOW_BLOCK).addToTag();

        // 下界岩。
        new ExtShapeStairsBlock(NETHERRACK).addToTag().addToTag(INFINIBURN_OVERWORLD);
        new ExtShapeSlabBlock(NETHERRACK).addToTag().addToTag(INFINIBURN_OVERWORLD);
        new ExtShapeFenceBlock(NETHERRACK, Items.NETHER_BRICK).addToTag().addToTag(INFINIBURN_OVERWORLD);
        new ExtShapeFenceGateBlock(NETHERRACK, Items.NETHER_BRICK).addToTag().addToTag(INFINIBURN_OVERWORLD);
        new ExtShapeWallBlock(NETHERRACK).addToTag().addToTag(INFINIBURN_OVERWORLD);
        new ExtShapeButtonBlock(hard, NETHERRACK).addToTag().addToTag(INFINIBURN_OVERWORLD);
        new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, NETHERRACK).addToTag().addToTag(INFINIBURN_OVERWORLD);
        new ExtShapeVerticalSlabBlock(NETHERRACK).addToTag().addToTag(INFINIBURN_OVERWORLD);

        // 荧石可以发光。
        new ExtShapeStairsBlock(GLOWSTONE).addToTag();
        new ExtShapeSlabBlock(GLOWSTONE).addToTag();
        new ExtShapeFenceBlock(GLOWSTONE, Items.GLOWSTONE_DUST).addToTag();
        new ExtShapeFenceGateBlock(GLOWSTONE, Items.GLOWSTONE_DUST).addToTag();
        new ExtShapeWallBlock(GLOWSTONE).addToTag();
        new ExtShapeButtonBlock(soft, GLOWSTONE).addToTag();
        new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, GLOWSTONE).addToTag();
        new ExtShapeVerticalSlabBlock(GLOWSTONE).addToTag();

        // 下界砖块的栅栏门、按钮和压力板。
        for (final Block BLOCK : new Block[]{NETHER_BRICKS}) {
            new ExtShapeFenceGateBlock(BLOCK, Items.NETHER_BRICK).addToTag();
            new ExtShapeWallBlock(BLOCK).addToTag();
            new ExtShapeButtonBlock(hard, BLOCK).addToTag();
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, BLOCK).addToTag();
            new ExtShapeVerticalSlabBlock(BLOCK).addToTag();
        }

        // 绿宝石。
        new ExtShapeStairsBlock(EMERALD_BLOCK).addToTag();
        new ExtShapeSlabBlock(EMERALD_BLOCK).addToTag();
        new ExtShapeFenceBlock(EMERALD_BLOCK, Items.EMERALD).addToTag();
        new ExtShapeFenceGateBlock(EMERALD_BLOCK, Items.EMERALD).addToTag();
        new ExtShapeWallBlock(EMERALD_BLOCK).addToTag();
        new ExtShapeButtonBlock(hard, EMERALD_BLOCK).addToTag();
        new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, EMERALD_BLOCK).addToTag();
        new ExtShapeVerticalSlabBlock(EMERALD_BLOCK).addToTag();

        // 石英、石英砖、平滑石英块、錾制石英块，其中石英砖和錾制石英块还需要楼梯台阶。
        for (final Block BLOCK : new Block[]{QUARTZ_BLOCK, QUARTZ_BRICKS, SMOOTH_QUARTZ, CHISELED_QUARTZ_BLOCK}) {
            new ExtShapeFenceBlock(BLOCK, Items.QUARTZ).addToTag();
            new ExtShapeFenceGateBlock(BLOCK, Items.QUARTZ).addToTag();
            new ExtShapeWallBlock(BLOCK).addToTag();
            new ExtShapeButtonBlock(stone, BLOCK).addToTag();
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, BLOCK);
        }

        for (final Block BLOCK : new Block[]{QUARTZ_BRICKS, CHISELED_QUARTZ_BLOCK}) {
            new ExtShapeStairsBlock(BLOCK).addToTag();
            new ExtShapeSlabBlock(BLOCK).addToTag();
            new ExtShapeVerticalSlabBlock(BLOCK).addToTag();
        }

        // 陶瓦和彩色陶瓦。
        for (final Block BLOCK : new Block[]{TERRACOTTA}) {
            new ExtShapeStairsBlock(BLOCK).addToTag(TERRACOTTA_STAIRS);
            new ExtShapeSlabBlock(BLOCK).addToTag(TERRACOTTA_SLABS);
            new ExtShapeFenceBlock(BLOCK, Items.CLAY).addToTag(TERRACOTTA_FENCES);
            new ExtShapeFenceGateBlock(BLOCK, Items.CLAY).addToTag(TERRACOTTA_FENCE_GATES);
            new ExtShapeWallBlock(BLOCK).addToTag(TERRACOTTA_WALLS);
            new ExtShapeButtonBlock(stone, BLOCK).addToTag(TERRACOTTA_BUTTONS);
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, BLOCK).addToTag(TERRACOTTA_PRESSURE_PLATES);
            new ExtShapeVerticalSlabBlock(BLOCK).addToTag(TERRACOTTA_VERTICAL_SLABS);
        }

        TERRACOTTA_STAIRS.addToTag(STAIRS);
        TERRACOTTA_SLABS.addToTag(SLABS);
        TERRACOTTA_FENCES.addToTag(FENCES);
        TERRACOTTA_FENCE_GATES.addToTag(FENCE_GATES);
        TERRACOTTA_WALLS.addToTag(WALLS);
        TERRACOTTA_BUTTONS.addToTag(BUTTONS);
        TERRACOTTA_PRESSURE_PLATES.addToTag(PRESSURE_PLATES);
        TERRACOTTA_VERTICAL_SLABS.addToTag(VERTICAL_SLABS);

        for (final Block BLOCK : STAINED_TERRACOTTAS) {
            new ExtShapeStairsBlock(BLOCK).addToTag(STAINED_TERRACOTTA_STAIRS);
            new ExtShapeSlabBlock(BLOCK).addToTag(STAINED_TERRACOTTA_SLABS);
            new ExtShapeFenceBlock(BLOCK, Items.CLAY).addToTag(STAINED_TERRACOTTA_FENCES);
            new ExtShapeFenceGateBlock(BLOCK, Items.CLAY).addToTag(STAINED_TERRACOTTA_FENCE_GATES);
            new ExtShapeWallBlock(BLOCK).addToTag(STAINED_TERRACOTTA_WALLS);
            new ExtShapeButtonBlock(stone, BLOCK).addToTag(STAINED_TERRACOTTA_BUTTONS);
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, BLOCK).addToTag(STAINED_TERRACOTTA_PRESSURE_PLATES);
            new ExtShapeVerticalSlabBlock(BLOCK).addToTag(STAINED_TERRACOTTA_VERTICAL_SLABS);
        }

        STAINED_TERRACOTTA_STAIRS.addToTag(TERRACOTTA_STAIRS);
        STAINED_TERRACOTTA_SLABS.addToTag(TERRACOTTA_SLABS);
        STAINED_TERRACOTTA_FENCES.addToTag(TERRACOTTA_FENCES);
        STAINED_TERRACOTTA_FENCE_GATES.addToTag(TERRACOTTA_FENCE_GATES);
        STAINED_TERRACOTTA_WALLS.addToTag(TERRACOTTA_WALLS);
        STAINED_TERRACOTTA_BUTTONS.addToTag(TERRACOTTA_BUTTONS);
        STAINED_TERRACOTTA_PRESSURE_PLATES.addToTag(TERRACOTTA_PRESSURE_PLATES);
        STAINED_TERRACOTTA_VERTICAL_SLABS.addToTag(TERRACOTTA_VERTICAL_SLABS);

        // 海晶石栅栏、按钮和压力板。
        new ExtShapeFenceBlock(PRISMARINE, Items.PRISMARINE_SHARD).addToTag();
        new ExtShapeFenceGateBlock(PRISMARINE, Items.PRISMARINE_SHARD).addToTag();
        new ExtShapeButtonBlock(stone, PRISMARINE).addToTag();
        new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, PRISMARINE).addToTag();
        new ExtShapeVerticalSlabBlock(PRISMARINE).addToTag();

        // 海晶石砖、暗海晶石。
        for (final Block BLOCK : new Block[]{PRISMARINE_BRICKS, DARK_PRISMARINE}) {
            new ExtShapeFenceBlock(BLOCK, Items.PRISMARINE_SHARD).addToTag();
            new ExtShapeFenceGateBlock(BLOCK, Items.PRISMARINE_SHARD).addToTag();
            new ExtShapeWallBlock(BLOCK).addToTag();
            new ExtShapeButtonBlock(stone, BLOCK).addToTag();
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, BLOCK).addToTag();
            new ExtShapeVerticalSlabBlock(BLOCK).addToTag();
        }

        // 下界疣块、诡异疣块。
        for (final Block BLOCK : new Block[]{NETHER_WART_BLOCK, WARPED_WART_BLOCK}) {
            new ExtShapeStairsBlock(BLOCK).addToTag();
            new ExtShapeSlabBlock(BLOCK).addToTag();
            new ExtShapeFenceBlock(BLOCK, BLOCK == NETHER_WART_BLOCK ? Items.NETHER_WART : Items.WARPED_FUNGUS).addToTag();
            new ExtShapeFenceGateBlock(BLOCK, BLOCK == NETHER_WART_BLOCK ? Items.NETHER_WART : Items.WARPED_FUNGUS).addToTag();
            new ExtShapeWallBlock(BLOCK).addToTag();
            new ExtShapeButtonBlock(soft, BLOCK).addToTag();
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, BLOCK);
            new ExtShapeVerticalSlabBlock(BLOCK).addToTag();
        }

        // 红色下界砖。
        for (final Block BLOCK : new Block[]{RED_NETHER_BRICKS}) {
            new ExtShapeFenceBlock(BLOCK, Items.NETHER_BRICK).addToTag();
            new ExtShapeFenceGateBlock(BLOCK, Items.NETHER_BRICK).addToTag();
            new ExtShapeWallBlock(BLOCK).addToTag();
            new ExtShapeButtonBlock(soft, BLOCK).addToTag();
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, BLOCK);
            new ExtShapeVerticalSlabBlock(BLOCK).addToTag();
        }

        // 彩色混凝土。
        for (final Block BLOCK : CONCRETES) {
            new ExtShapeStairsBlock(BLOCK).addToTag(CONCRETE_STAIRS);
            new ExtShapeSlabBlock(BLOCK).addToTag(CONCRETE_SLABS);
            new ExtShapeFenceBlock(BLOCK, Items.GRAVEL).addToTag(CONCRETE_FENCES);
            new ExtShapeFenceGateBlock(BLOCK, Items.GRAVEL).addToTag(CONCRETE_FENCE_GATES);
            new ExtShapeWallBlock(BLOCK).addToTag(CONCRETE_WALLS);
            new ExtShapeButtonBlock(stone, BLOCK).addToTag(CONCRETE_BUTTONS);
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, BLOCK).addToTag(CONCRETE_PRESSURE_PLATES);
            new ExtShapeVerticalSlabBlock(BLOCK).addToTag(CONCRETE_VERTICAL_SLABS);
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
        new ExtShapeStairsBlock(CRYING_OBSIDIAN).addToTag();
        new ExtShapeSlabBlock(CRYING_OBSIDIAN).addToTag();
        new ExtShapeFenceBlock(CRYING_OBSIDIAN, Items.STONE).addToTag();
        new ExtShapeFenceGateBlock(CRYING_OBSIDIAN, Items.STONE).addToTag();
        new ExtShapeWallBlock(CRYING_OBSIDIAN).addToTag();
        new ExtShapeButtonBlock(hard, CRYING_OBSIDIAN).addToTag();
        new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, CRYING_OBSIDIAN).addToTag();
        new ExtShapeVerticalSlabBlock(CRYING_OBSIDIAN).addToTag();

        // 带釉陶瓦只注册台阶。
        for (final Block BLOCK : GLAZED_TERRACOTTAS) {
            new ExtShapeSlabBlock(BLOCK).addToTag();
        }
    }

    public static void init() {
    }
}
