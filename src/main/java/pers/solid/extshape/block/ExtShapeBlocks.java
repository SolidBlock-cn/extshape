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

public class ExtShapeBlocks {
    public static final SmoothStoneDoubleSlabBlock SMOOTH_STONE_DOUBLE_SLAB;

    static {
        // 石头及其变种（含磨制变种），已存在其楼梯、台阶、墙，但是还没有栅栏和栅栏门。
        for (final Block BLOCK : STONES) {
            new ExtShapeFenceBlock(BLOCK, Items.FLINT).register().addToTag();
            new ExtShapeFenceGateBlock(BLOCK, Items.FLINT).register().addToTag();
            new ExtShapeVerticalSlabBlock(BLOCK).register().addToTag();
        }
        for (final Block BLOCK : STONE_VARIANTS) {
            new ExtShapeButtonBlock(stone, BLOCK).register().addToTag();
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, BLOCK).register().addToTag();
        }

        // 平滑石头比较特殊，完整方块和台阶不同。
        SMOOTH_STONE_DOUBLE_SLAB =
                (SmoothStoneDoubleSlabBlock) new SmoothStoneDoubleSlabBlock(FabricBlockSettings.copyOf(SMOOTH_STONE)).register(new Identifier("extshape",
                        "smooth_stone_slab_double")).addToTag().addToTag(PICKAXE_MINEABLE);

        new ExtShapeFenceBlock(SMOOTH_STONE, Items.FLINT).register().addToTag();
        new ExtShapeFenceGateBlock(SMOOTH_STONE, Items.FLINT).register().addToTag();
        new ExtShapeWallBlock(SMOOTH_STONE).register().addToTag();
        new ExtShapeButtonBlock(stone, SMOOTH_STONE).register().addToTag();

        // 深板岩圆石、磨制深板岩
        for (final Block BLOCK:new Block[]{COBBLED_DEEPSLATE,POLISHED_DEEPSLATE}) {
            new ExtShapeVerticalSlabBlock(BLOCK).register().addToTag();
        }

        // 凝灰岩，方解石。
        for (final Block BLOCK : new Block[]{TUFF, CALCITE}) {
            new ExtShapeStairsBlock(BLOCK).register().addToTag();
            new ExtShapeSlabBlock(BLOCK).register().addToTag();
            new ExtShapeFenceBlock(BLOCK, Items.FLINT).register().addToTag();
            new ExtShapeFenceGateBlock(BLOCK, Items.FLINT).register().addToTag();
            new ExtShapeWallBlock(BLOCK).register().addToTag();
            new ExtShapeButtonBlock(stone, BLOCK).register().addToTag();
            new ExtShapeVerticalSlabBlock(BLOCK).register().addToTag();
        }

        // 圆石。
        new ExtShapeFenceBlock(COBBLESTONE, Items.FLINT).register().addToTag();
        new ExtShapeFenceGateBlock(COBBLESTONE, Items.FLINT).register().addToTag();
        new ExtShapeVerticalSlabBlock(COBBLESTONE).register().addToTag();

        // 木板。
        for (final Block BLOCK : PLANKS) {
            final boolean isOverworld = OVERWORLD_PLANKS.contains(BLOCK);
            new ExtShapeVerticalSlabBlock(BLOCK).register().addToTag(WOODEN_VERTICAL_SLABS).addToTag(isOverworld ?
                    OVERWORLD_WOODEN_BLOCKS:null);
        }
        WOODEN_VERTICAL_SLABS.addToTag(VERTICAL_SLABS);

        // 基岩
        for (final Block BLOCK : new Block[]{BEDROCK}) {
            new ExtShapeStairsBlock(BLOCK).register().addToTag().addToTag(BEDROCK_BLOCKS);
            new ExtShapeSlabBlock(BLOCK).register().addToTag().addToTag(BEDROCK_BLOCKS);
            new ExtShapeFenceBlock(BLOCK, Items.FLINT).register().addToTag().addToTag(BEDROCK_BLOCKS);
            new ExtShapeFenceGateBlock(BLOCK, Items.FLINT).register().addToTag().addToTag(BEDROCK_BLOCKS);
            new ExtShapeWallBlock(BLOCK).register().addToTag().addToTag(BEDROCK_BLOCKS);
            new ExtShapeButtonBlock(hard, BLOCK).register().addToTag().addToTag(BEDROCK_BLOCKS);
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, BLOCK).register().addToTag().addToTag(BEDROCK_BLOCKS);
            new ExtShapeVerticalSlabBlock(BEDROCK).register().addToTag().addToTag(BEDROCK_BLOCKS);
        }

        // 紫水晶块。
        new ExtShapeStairsBlock(AMETHYST_BLOCK).register().addToTag();
        new ExtShapeSlabBlock(AMETHYST_BLOCK).register().addToTag();
        new ExtShapeFenceBlock(AMETHYST_BLOCK, Items.AMETHYST_SHARD).register().addToTag();
        new ExtShapeFenceGateBlock(AMETHYST_BLOCK, Items.AMETHYST_SHARD).register().addToTag();
        new ExtShapeWallBlock(AMETHYST_BLOCK).register().addToTag();
        new ExtShapeButtonBlock(stone, AMETHYST_BLOCK).register().addToTag();
        new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, AMETHYST_BLOCK).register().addToTag();
        new ExtShapeVerticalSlabBlock(AMETHYST_BLOCK).register().addToTag();

        // 铁块。
        new ExtShapeStairsBlock(IRON_BLOCK).register().addToTag();
        new ExtShapeSlabBlock(IRON_BLOCK).register().addToTag();
        new ExtShapeFenceBlock(IRON_BLOCK, Items.IRON_INGOT).register().addToTag();
        new ExtShapeFenceGateBlock(IRON_BLOCK, Items.IRON_INGOT).register().addToTag();
        new ExtShapeWallBlock(IRON_BLOCK).register().addToTag();
        new ExtShapeButtonBlock(stone, IRON_BLOCK).register().addToTag();
        new ExtShapeVerticalSlabBlock(IRON_BLOCK).register().addToTag();

        // 金块，钻石块，下界合金块，青金石块
        for (final Block BLOCK : new Block[]{GOLD_BLOCK, DIAMOND_BLOCK, NETHERITE_BLOCK, LAPIS_BLOCK}) {
            @Nullable FabricItemSettings settings = BLOCK == NETHERITE_BLOCK ? new FabricItemSettings().fireproof() :
                    null;
            Item item = BLOCK == GOLD_BLOCK ? Items.GOLD_INGOT : BLOCK == DIAMOND_BLOCK ? Items.DIAMOND :
                    BLOCK == NETHERITE_BLOCK ? Items.NETHERITE_INGOT : BLOCK == LAPIS_BLOCK ? Items.LAPIS_LAZULI : null;
            new ExtShapeStairsBlock(BLOCK).register(settings).addToTag();
            new ExtShapeSlabBlock(BLOCK).register(settings).addToTag();
            new ExtShapeFenceBlock(BLOCK, item).register(settings).addToTag();
            new ExtShapeFenceGateBlock(BLOCK, item).register(settings).addToTag();
            new ExtShapeWallBlock(BLOCK).register(settings).addToTag();
            new ExtShapeButtonBlock(hard, BLOCK).register(settings).addToTag();
            new ExtShapeVerticalSlabBlock(BLOCK).register(settings).addToTag();
        }

        // 砂岩、红砂岩。
        new ExtShapeFenceBlock(SANDSTONE, Items.SAND).register().addToTag();
        new ExtShapeFenceGateBlock(SANDSTONE, Items.SAND).register().addToTag();
        new ExtShapeFenceBlock(RED_SANDSTONE, Items.RED_SAND).register().addToTag();
        new ExtShapeFenceGateBlock(RED_SANDSTONE, Items.RED_SAND).register().addToTag();
        new ExtShapeButtonBlock(stone, SANDSTONE).register().addToTag();
        new ExtShapeButtonBlock(stone, RED_SANDSTONE).register().addToTag();
        new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, SANDSTONE).register().addToTag();
        new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, RED_SANDSTONE).register().addToTag();
        new ExtShapeVerticalSlabBlock(SANDSTONE).register().addToTag();
        new ExtShapeVerticalSlabBlock(RED_SANDSTONE).register().addToTag();

        // 切制砂岩、切制红砂岩，注册楼梯、栅栏、墙。
        for (final Block BLOCK : new Block[]{CUT_SANDSTONE, CUT_RED_SANDSTONE}) {
            new ExtShapeStairsBlock(BLOCK).register().addToTag();
            new ExtShapeFenceBlock(BLOCK, BLOCK == CUT_SANDSTONE ? Items.SAND : Items.RED_SAND).register().addToTag();
            new ExtShapeFenceGateBlock(BLOCK, BLOCK == CUT_SANDSTONE ? Items.SAND : Items.RED_SAND).register().addToTag();
            new ExtShapeWallBlock(BLOCK).register().addToTag();
            new ExtShapeButtonBlock(stone, BLOCK).register().addToTag();
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, BLOCK).register().addToTag();
            new ExtShapeVerticalSlabBlock(BLOCK).register().addToTag();
        }

        // 錾制砂岩、切制红砂岩，注册楼梯、台阶、栅栏、墙。
        for (final Block BLOCK : new Block[]{CHISELED_SANDSTONE, CHISELED_RED_SANDSTONE}) {
            new ExtShapeStairsBlock(BLOCK).register().addToTag();
            new ExtShapeSlabBlock(BLOCK).register().addToTag();
            new ExtShapeFenceBlock(BLOCK, BLOCK == CHISELED_SANDSTONE ? Items.SAND : Items.RED_SAND).register().addToTag();
            new ExtShapeFenceGateBlock(BLOCK, BLOCK == CHISELED_SANDSTONE ? Items.SAND : Items.RED_SAND).register().addToTag();
            new ExtShapeWallBlock(BLOCK).register().addToTag();
            new ExtShapeButtonBlock(stone, BLOCK).register().addToTag();
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, BLOCK).register().addToTag();
            new ExtShapeVerticalSlabBlock(BLOCK).register().addToTag();
        }

        // 平滑砂岩、平滑红砂岩，注册栅栏、墙、按钮和压力板。
        for (final Block BLOCK : new Block[]{SMOOTH_SANDSTONE, SMOOTH_RED_SANDSTONE}) {
            new ExtShapeFenceBlock(BLOCK, BLOCK == SMOOTH_SANDSTONE ? Items.SAND : Items.RED_SAND).register().addToTag();
            new ExtShapeFenceGateBlock(BLOCK, BLOCK == SMOOTH_SANDSTONE ? Items.SAND : Items.RED_SAND).register().addToTag();
            new ExtShapeWallBlock(BLOCK).register().addToTag();
            new ExtShapeButtonBlock(stone, BLOCK).register().addToTag();
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, BLOCK).register().addToTag();
            new ExtShapeVerticalSlabBlock(BLOCK).register().addToTag();
        }

        // 羊毛不注册墙。
        for (final Block BLOCK : WOOLS) {
            new ExtShapeStairsBlock(BLOCK).register().addToTag(WOOLEN_STAIRS);
            new ExtShapeSlabBlock(BLOCK).register().addToTag(WOOLEN_SLABS);
            new ExtShapeFenceBlock(BLOCK, Items.STRING).register().addToTag(WOOLEN_FENCES);
            new ExtShapeFenceGateBlock(BLOCK, Items.STRING).register().addToTag(WOOLEN_FENCE_GATES);
            new ExtShapeButtonBlock(soft, BLOCK).register().addToTag(WOOLEN_BUTTONS);
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, BLOCK).register().addToTag(WOOLEN_PRESSURE_PLATES);
            new ExtShapeVerticalSlabBlock(BLOCK).register().addToTag(WOOLEN_VERTICAL_SLABS);
        }

        WOOLEN_STAIRS.addToTag(STAIRS);
        WOOLEN_SLABS.addToTag(SLABS);
        WOOLEN_FENCES.addToTag(FENCES);
        WOOLEN_FENCE_GATES.addToTag(FENCE_GATES);
        WOOLEN_BUTTONS.addToTag(BUTTONS);
        WOOLEN_PRESSURE_PLATES.addToTag(PRESSURE_PLATES);
        WOOLEN_VERTICAL_SLABS.addToTag(VERTICAL_SLABS);

        // 砖栅栏和栅栏门。
        new ExtShapeFenceBlock(BRICKS, Items.BRICK).register().addToTag();
        new ExtShapeFenceGateBlock(BRICKS, Items.BRICK).register().addToTag();
        new ExtShapeVerticalSlabBlock(BRICKS).register().addToTag();

        // 苔石栅栏和栅栏门。
        new ExtShapeFenceBlock(MOSSY_COBBLESTONE, Items.FLINT).register().addToTag();
        new ExtShapeFenceGateBlock(MOSSY_COBBLESTONE, Items.FLINT).register().addToTag();
        new ExtShapeVerticalSlabBlock(MOSSY_COBBLESTONE).register().addToTag();

        // 黑曜石。
        new ExtShapeStairsBlock(OBSIDIAN).register().addToTag();
        new ExtShapeSlabBlock(OBSIDIAN).register().addToTag();
        new ExtShapeFenceBlock(OBSIDIAN, Items.STONE).register().addToTag();
        new ExtShapeFenceGateBlock(OBSIDIAN, Items.STONE).register().addToTag();
        new ExtShapeWallBlock(OBSIDIAN).register().addToTag();
        new ExtShapeButtonBlock(hard, OBSIDIAN).register().addToTag();
        new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, OBSIDIAN).register().addToTag();
        new ExtShapeVerticalSlabBlock(OBSIDIAN).register().addToTag();

        // 紫珀块。
        new ExtShapeVerticalSlabBlock(PURPUR_BLOCK).register().addToTag();

        // 冰，由于技术原因，暂不产生。

        // 雪块
        new ExtShapeStairsBlock(SNOW_BLOCK).register().addToTag();
        new ExtShapeSlabBlock(SNOW_BLOCK).register().addToTag();
        new ExtShapeFenceBlock(SNOW_BLOCK, Items.SNOWBALL).register().addToTag();
        new ExtShapeFenceGateBlock(SNOW_BLOCK, Items.SNOWBALL).register().addToTag();
        new ExtShapeWallBlock(SNOW_BLOCK).register().addToTag();
        new ExtShapeButtonBlock(soft, SNOW_BLOCK).register().addToTag();
        new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, SNOW_BLOCK).register().addToTag();
        new ExtShapeVerticalSlabBlock(SNOW_BLOCK).register().addToTag();

        // 下界岩。
        new ExtShapeStairsBlock(NETHERRACK).register().addToTag().addToTag(INFINIBURN_OVERWORLD);
        new ExtShapeSlabBlock(NETHERRACK).register().addToTag().addToTag(INFINIBURN_OVERWORLD);
        new ExtShapeFenceBlock(NETHERRACK, Items.NETHER_BRICK).register().addToTag().addToTag(INFINIBURN_OVERWORLD);
        new ExtShapeFenceGateBlock(NETHERRACK, Items.NETHER_BRICK).register().addToTag().addToTag(INFINIBURN_OVERWORLD);
        new ExtShapeWallBlock(NETHERRACK).register().addToTag().addToTag(INFINIBURN_OVERWORLD);
        new ExtShapeButtonBlock(hard, NETHERRACK).register().addToTag().addToTag(INFINIBURN_OVERWORLD);
        new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, NETHERRACK).register().addToTag().addToTag(INFINIBURN_OVERWORLD);
        new ExtShapeVerticalSlabBlock(NETHERRACK).register().addToTag().addToTag(INFINIBURN_OVERWORLD);

        // 荧石可以发光。
        new ExtShapeStairsBlock(GLOWSTONE).register().addToTag();
        new ExtShapeSlabBlock(GLOWSTONE).register().addToTag();
        new ExtShapeFenceBlock(GLOWSTONE, Items.GLOWSTONE_DUST).register().addToTag();
        new ExtShapeFenceGateBlock(GLOWSTONE, Items.GLOWSTONE_DUST).register().addToTag();
        new ExtShapeWallBlock(GLOWSTONE).register().addToTag();
        new ExtShapeButtonBlock(soft, GLOWSTONE).register().addToTag();
        new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, GLOWSTONE).register().addToTag();
        new ExtShapeVerticalSlabBlock(GLOWSTONE).register().addToTag();

        // 石砖、苔石砖、深板岩砖、深板岩瓦
        for (final Block BLOCK : new Block[]{STONE_BRICKS, MOSSY_STONE_BRICKS, DEEPSLATE_BRICKS,DEEPSLATE_TILES}) {
            new ExtShapeVerticalSlabBlock(BLOCK).register().addToTag();
        }

        // 下界砖块的栅栏门、按钮和压力板。
        for (final Block BLOCK : new Block[]{NETHER_BRICKS}) {
            new ExtShapeFenceGateBlock(BLOCK, Items.NETHER_BRICK).register().addToTag();
            new ExtShapeWallBlock(BLOCK).register().addToTag();
            new ExtShapeButtonBlock(hard, BLOCK).register().addToTag();
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, BLOCK).register().addToTag();
            new ExtShapeVerticalSlabBlock(BLOCK).register().addToTag();
        }

        // 末地石砖。
        new ExtShapeVerticalSlabBlock(END_STONE_BRICKS).register().addToTag();

        // 绿宝石块。
        new ExtShapeStairsBlock(EMERALD_BLOCK).register().addToTag();
        new ExtShapeSlabBlock(EMERALD_BLOCK).register().addToTag();
        new ExtShapeFenceBlock(EMERALD_BLOCK, Items.EMERALD).register().addToTag();
        new ExtShapeFenceGateBlock(EMERALD_BLOCK, Items.EMERALD).register().addToTag();
        new ExtShapeWallBlock(EMERALD_BLOCK).register().addToTag();
        new ExtShapeButtonBlock(hard, EMERALD_BLOCK).register().addToTag();
        new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, EMERALD_BLOCK).register().addToTag();
        new ExtShapeVerticalSlabBlock(EMERALD_BLOCK).register().addToTag();

        // 石英、石英砖、平滑石英块、錾制石英块，其中石英砖和錾制石英块还需要楼梯台阶。
        for (final Block BLOCK : new Block[]{QUARTZ_BLOCK, QUARTZ_BRICKS, SMOOTH_QUARTZ, CHISELED_QUARTZ_BLOCK}) {
            new ExtShapeFenceBlock(BLOCK, Items.QUARTZ).register().addToTag();
            new ExtShapeFenceGateBlock(BLOCK, Items.QUARTZ).register().addToTag();
            new ExtShapeWallBlock(BLOCK).register().addToTag();
            new ExtShapeButtonBlock(stone, BLOCK).register().addToTag();
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, BLOCK).register().addToTag();
            new ExtShapeVerticalSlabBlock(BLOCK).register().addToTag();
        }

        for (final Block BLOCK : new Block[]{QUARTZ_BRICKS, CHISELED_QUARTZ_BLOCK}) {
            new ExtShapeStairsBlock(BLOCK).register().addToTag();
            new ExtShapeSlabBlock(BLOCK).register().addToTag();
        }

        // 陶瓦和彩色陶瓦。
        for (final Block BLOCK : new Block[]{TERRACOTTA}) {
            new ExtShapeStairsBlock(BLOCK).register().addToTag(TERRACOTTA_STAIRS);
            new ExtShapeSlabBlock(BLOCK).register().addToTag(TERRACOTTA_SLABS);
            new ExtShapeFenceBlock(BLOCK, Items.CLAY).register().addToTag(TERRACOTTA_FENCES);
            new ExtShapeFenceGateBlock(BLOCK, Items.CLAY).register().addToTag(TERRACOTTA_FENCE_GATES);
            new ExtShapeWallBlock(BLOCK).register().addToTag(TERRACOTTA_WALLS);
            new ExtShapeButtonBlock(stone, BLOCK).register().addToTag(TERRACOTTA_BUTTONS);
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, BLOCK).register().addToTag(TERRACOTTA_PRESSURE_PLATES);
            new ExtShapeVerticalSlabBlock(BLOCK).register().addToTag(TERRACOTTA_VERTICAL_SLABS);
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
            new ExtShapeStairsBlock(BLOCK).register().addToTag(STAINED_TERRACOTTA_STAIRS);
            new ExtShapeSlabBlock(BLOCK).register().addToTag(STAINED_TERRACOTTA_SLABS);
            new ExtShapeFenceBlock(BLOCK, Items.CLAY).register().addToTag(STAINED_TERRACOTTA_FENCES);
            new ExtShapeFenceGateBlock(BLOCK, Items.CLAY).register().addToTag(STAINED_TERRACOTTA_FENCE_GATES);
            new ExtShapeWallBlock(BLOCK).register().addToTag(STAINED_TERRACOTTA_WALLS);
            new ExtShapeButtonBlock(stone, BLOCK).register().addToTag(STAINED_TERRACOTTA_BUTTONS);
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, BLOCK).register().addToTag(STAINED_TERRACOTTA_PRESSURE_PLATES);
            new ExtShapeVerticalSlabBlock(BLOCK).register().addToTag(STAINED_TERRACOTTA_VERTICAL_SLABS);
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
        new ExtShapeStairsBlock(PACKED_ICE).register().addToTag();
        new ExtShapeSlabBlock(PACKED_ICE).register().addToTag();
        new ExtShapeFenceBlock(PACKED_ICE, Items.ICE).register().addToTag();
        new ExtShapeFenceGateBlock(PACKED_ICE, Items.ICE).register().addToTag();
        new ExtShapeWallBlock(PACKED_ICE).register().addToTag();
        new ExtShapeButtonBlock(stone, PACKED_ICE).register().addToTag();
        new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, PACKED_ICE).register().addToTag();
        new ExtShapeVerticalSlabBlock(PACKED_ICE).register().addToTag();

        // 海晶石栅栏、按钮和压力板。
        new ExtShapeFenceBlock(PRISMARINE, Items.PRISMARINE_SHARD).register().addToTag();
        new ExtShapeFenceGateBlock(PRISMARINE, Items.PRISMARINE_SHARD).register().addToTag();
        new ExtShapeButtonBlock(stone, PRISMARINE).register().addToTag();
        new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, PRISMARINE).register().addToTag();
        new ExtShapeVerticalSlabBlock(PRISMARINE).register().addToTag();

        // 海晶石砖、暗海晶石。
        for (final Block BLOCK : new Block[]{PRISMARINE_BRICKS, DARK_PRISMARINE}) {
            new ExtShapeFenceBlock(BLOCK, Items.PRISMARINE_SHARD).register().addToTag();
            new ExtShapeFenceGateBlock(BLOCK, Items.PRISMARINE_SHARD).register().addToTag();
            new ExtShapeWallBlock(BLOCK).register().addToTag();
            new ExtShapeButtonBlock(stone, BLOCK).register().addToTag();
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, BLOCK).register().addToTag();
            new ExtShapeVerticalSlabBlock(BLOCK).register().addToTag();
        }

        // 下界疣块、诡异疣块。
        for (final Block BLOCK : new Block[]{NETHER_WART_BLOCK, WARPED_WART_BLOCK}) {
            new ExtShapeStairsBlock(BLOCK).register().addToTag();
            new ExtShapeSlabBlock(BLOCK).register().addToTag();
            new ExtShapeFenceBlock(BLOCK, BLOCK == NETHER_WART_BLOCK ? Items.NETHER_WART : Items.WARPED_FUNGUS).register().addToTag();
            new ExtShapeFenceGateBlock(BLOCK, BLOCK == NETHER_WART_BLOCK ? Items.NETHER_WART : Items.WARPED_FUNGUS).register().addToTag();
            new ExtShapeWallBlock(BLOCK).register().addToTag();
            new ExtShapeButtonBlock(soft, BLOCK).register().addToTag();
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, BLOCK).register().addToTag();
            new ExtShapeVerticalSlabBlock(BLOCK).register().addToTag();
        }

        // 红色下界砖。
        for (final Block BLOCK : new Block[]{RED_NETHER_BRICKS}) {
            new ExtShapeFenceBlock(BLOCK, Items.NETHER_BRICK).register().addToTag();
            new ExtShapeFenceGateBlock(BLOCK, Items.NETHER_BRICK).register().addToTag();
            new ExtShapeWallBlock(BLOCK).register().addToTag();
            new ExtShapeButtonBlock(soft, BLOCK).register().addToTag();
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, BLOCK).register().addToTag();
            new ExtShapeVerticalSlabBlock(BLOCK).register().addToTag();
        }

        // 彩色混凝土。
        for (final Block BLOCK : CONCRETES) {
            new ExtShapeStairsBlock(BLOCK).register().addToTag(CONCRETE_STAIRS);
            new ExtShapeSlabBlock(BLOCK).register().addToTag(CONCRETE_SLABS);
            new ExtShapeFenceBlock(BLOCK, Items.GRAVEL).register().addToTag(CONCRETE_FENCES);
            new ExtShapeFenceGateBlock(BLOCK, Items.GRAVEL).register().addToTag(CONCRETE_FENCE_GATES);
            new ExtShapeWallBlock(BLOCK).register().addToTag(CONCRETE_WALLS);
            new ExtShapeButtonBlock(stone, BLOCK).register().addToTag(CONCRETE_BUTTONS);
            new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, BLOCK).register().addToTag(CONCRETE_PRESSURE_PLATES);
            new ExtShapeVerticalSlabBlock(BLOCK).register().addToTag(CONCRETE_VERTICAL_SLABS);
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
        new ExtShapeStairsBlock(CRYING_OBSIDIAN).register().addToTag();
        new ExtShapeSlabBlock(CRYING_OBSIDIAN).register().addToTag();
        new ExtShapeFenceBlock(CRYING_OBSIDIAN, Items.STONE).register().addToTag();
        new ExtShapeFenceGateBlock(CRYING_OBSIDIAN, Items.STONE).register().addToTag();
        new ExtShapeWallBlock(CRYING_OBSIDIAN).register().addToTag();
        new ExtShapeButtonBlock(hard, CRYING_OBSIDIAN).register().addToTag();
        new ExtShapePressurePlateBlock(PressurePlateBlock.ActivationRule.MOBS, CRYING_OBSIDIAN).register().addToTag();
        new ExtShapeVerticalSlabBlock(CRYING_OBSIDIAN).register().addToTag();

        // 带釉陶瓦只注册台阶。
        for (final Block BLOCK : GLAZED_TERRACOTTAS) {
            new ExtShapeGlazedTerracottaSlabBlock(BLOCK).register().addToTag(GLAZED_TERRACOTTA_SLABS);
        }

        GLAZED_TERRACOTTA_SLABS.addToTag(SLABS);
    }

    public static void init() {
    }
}
