package pers.solid.extshape.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import pers.solid.extshape.builder.BlockBuilder;

import static net.minecraft.block.Blocks.*;
import static net.minecraft.block.PressurePlateBlock.ActivationRule.EVERYTHING;
import static net.minecraft.block.PressurePlateBlock.ActivationRule.MOBS;
import static pers.solid.extshape.block.ExtShapeButtonBlock.ButtonType.*;
import static pers.solid.extshape.tag.ExtShapeBlockTag.*;

public class ExtShapeBlocks {
    public static final SmoothStoneDoubleSlabBlock SMOOTH_STONE_DOUBLE_SLAB;

    static {
        // 石头及其变种（含磨制变种），已存在其楼梯、台阶、墙，但是还没有栅栏和栅栏门。
        for (final Block block : STONES) {
            BlockBuilder.create(block,Items.FLINT,stone,MOBS).build();
        }

        // 平滑石头比较特殊，完整方块和台阶不同。
        SMOOTH_STONE_DOUBLE_SLAB =
                (SmoothStoneDoubleSlabBlock) new SmoothStoneDoubleSlabBlock(FabricBlockSettings.copyOf(SMOOTH_STONE)).register(new Identifier("extshape",
                        "smooth_stone_slab_double")).addToTag().addToTag(PICKAXE_MINEABLE);
        BlockBuilder.create(SMOOTH_STONE,Items.FLINT,stone,MOBS).build();

        // 深板岩圆石、磨制深板岩
        for (final Block BLOCK:new Block[]{COBBLED_DEEPSLATE,POLISHED_DEEPSLATE}) {
            BlockBuilder.createVerticalSlab(BLOCK).build();
        }

        // 凝灰岩，方解石。
        for (final Block BLOCK : new Block[]{TUFF, CALCITE}) {
            BlockBuilder.create(BLOCK,Items.FLINT,stone,MOBS).build();
        }

        // 圆石。
        BlockBuilder.create(COBBLESTONE,Items.FLINT,null,null).noRedstone().build();

        // 木板。
        for (final Block BLOCK : PLANKS) {
            final boolean isOverworld = OVERWORLD_PLANKS.contains(BLOCK);
            BlockBuilder.createVerticalSlab(BLOCK).setDefaultTag(WOODEN_VERTICAL_SLABS).putTag(isOverworld ?
                    OVERWORLD_WOODEN_BLOCKS:null).build();
        }
        WOODEN_VERTICAL_SLABS.addToTag(VERTICAL_SLABS);

        // 基岩
            BlockBuilder.create(BEDROCK,Items.STICK,hard,MOBS).putTag(BEDROCK_BLOCKS).build();

        // 紫水晶块。
        BlockBuilder.create(AMETHYST_BLOCK,Items.AMETHYST_SHARD,stone,MOBS).build();

        // 铁块。
        BlockBuilder.create(IRON_BLOCK,Items.IRON_INGOT,stone,null).noPressurePlate().build();

        // 金块，钻石块，下界合金块，青金石块
        for (final Block BLOCK : new Block[]{GOLD_BLOCK, DIAMOND_BLOCK, NETHERITE_BLOCK, LAPIS_BLOCK}) {
            Item item = BLOCK == GOLD_BLOCK ? Items.GOLD_INGOT : BLOCK == DIAMOND_BLOCK ? Items.DIAMOND :
                    BLOCK == NETHERITE_BLOCK ? Items.NETHERITE_INGOT : BLOCK == LAPIS_BLOCK ? Items.LAPIS_LAZULI : null;
            BlockBuilder.create(BLOCK,item,hard,null).noPressurePlate().build();
        }

        // 砂岩、红砂岩及其切制、錾制、平滑变种。栅栏合成材料统一改为stick。
        for (final Block block : SANDSTONES) {
            BlockBuilder.create(block,Items.STICK,stone,MOBS).build();
        }

        // 羊毛不注册墙。
        for (final Block block : WOOLS) {
            BlockBuilder.create(block,Items.STRING,soft,EVERYTHING).putTag(WOOLEN_BLOCKS).noWall().setDefaultTags(WOOLEN_STAIRS,WOOLEN_SLABS,WOODEN_VERTICAL_SLABS,WOOLEN_FENCES,WOOLEN_FENCE_GATES,null,WOOLEN_BUTTONS,WOOLEN_PRESSURE_PLATES).build();
        }

        WOOLEN_STAIRS.addToTag(STAIRS);
        WOOLEN_SLABS.addToTag(SLABS);
        WOOLEN_FENCES.addToTag(FENCES);
        WOOLEN_FENCE_GATES.addToTag(FENCE_GATES);
        WOOLEN_BUTTONS.addToTag(BUTTONS);
        WOOLEN_PRESSURE_PLATES.addToTag(PRESSURE_PLATES);
        WOOLEN_VERTICAL_SLABS.addToTag(VERTICAL_SLABS);

        // 砖栅栏和栅栏门。
        BlockBuilder.create(BRICKS,Items.BRICK,null,null).noRedstone().build();

        // 苔石栅栏和栅栏门。
        BlockBuilder.create(MOSSY_COBBLESTONE,Items.FLINT,null,null).noRedstone().build();

        // 黑曜石。
        BlockBuilder.create(OBSIDIAN,Items.STONE,hard,MOBS).build();

        // 紫珀块。
        BlockBuilder.create(PURPUR_BLOCK,null,null,null).noFences().noRedstone().build();

        // 冰，由于技术原因，暂不产生。

        // 雪块
        BlockBuilder.create(SNOW_BLOCK,Items.SNOW_BLOCK,soft,EVERYTHING).build();

        // 下界岩。
        BlockBuilder.create(NETHERRACK,Items.NETHER_BRICK,stone,MOBS).putTag(INFINIBURN_OVERWORLD).build();

        // 荧石可以发光。
        BlockBuilder.create(GLOWSTONE,Items.GLOWSTONE_DUST,soft,EVERYTHING).build();

        // 石砖、苔石砖、深板岩砖、深板岩瓦
        for (final Block block : new Block[]{STONE_BRICKS, MOSSY_STONE_BRICKS, DEEPSLATE_BRICKS,DEEPSLATE_TILES}) {
            BlockBuilder.create(block).build();
        }

        // 下界砖块的栅栏门、按钮和压力板。
        BlockBuilder.create(NETHER_BRICKS,Items.NETHER_BRICK,hard,MOBS).build();

        // 末地石砖。
        BlockBuilder.create(END_STONE_BRICKS).build();

        // 绿宝石块。
        BlockBuilder.create(EMERALD_BLOCK,Items.EMERALD,hard,MOBS).build();

        // 石英、石英砖、平滑石英块、錾制石英块，其中石英砖和錾制石英块还需要楼梯台阶。
        for (final Block block : new Block[]{QUARTZ_BLOCK, QUARTZ_BRICKS, SMOOTH_QUARTZ, CHISELED_QUARTZ_BLOCK}) {
            BlockBuilder.create(block,Items.QUARTZ,stone,MOBS).build();
        }

        // 陶瓦和彩色陶瓦。
        for (final Block block : new Block[]{TERRACOTTA}) {
            BlockBuilder.create(block,Items.CLAY,stone,MOBS).setDefaultTags(TERRACOTTA_STAIRS,TERRACOTTA_SLABS,
                    TERRACOTTA_VERTICAL_SLABS,TERRACOTTA_FENCE_GATES,TERRACOTTA_FENCE_GATES,TERRACOTTA_WALLS,
                    TERRACOTTA_BUTTONS,TERRACOTTA_PRESSURE_PLATES).build();
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
            BlockBuilder.create(block,Items.CLAY,stone,MOBS).setDefaultTags(STAINED_TERRACOTTA_STAIRS,
                    STAINED_TERRACOTTA_SLABS,STAINED_TERRACOTTA_VERTICAL_SLABS,STAINED_TERRACOTTA_FENCES,
                    STAINED_TERRACOTTA_FENCE_GATES,STAINED_TERRACOTTA_WALLS, STAINED_TERRACOTTA_BUTTONS,STAINED_TERRACOTTA_PRESSURE_PLATES).build();
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
        BlockBuilder.create(PACKED_ICE,Items.ICE,stone,MOBS).build();

        // 海晶石、海晶石砖、暗海晶石。
        for (final Block block : new Block[]{PRISMARINE,PRISMARINE_BRICKS, DARK_PRISMARINE}) {
            BlockBuilder.create(block,Items.PRISMARINE_SHARD,stone,MOBS).build();
        }

        // 下界疣块、诡异疣块。
        BlockBuilder.create(NETHER_WART_BLOCK,Items.NETHER_WART,soft,EVERYTHING).build();
        BlockBuilder.create(WARPED_WART_BLOCK,Items.WARPED_WART_BLOCK,soft,EVERYTHING).noRedstone().build();

        // 红色下界砖。
        BlockBuilder.create(RED_NETHER_BRICKS,Items.NETHER_BRICK,soft,EVERYTHING).build();

        // 彩色混凝土。
        for (final Block block : CONCRETES) {
            BlockBuilder.create(block,Items.GRAVEL,stone,MOBS).setDefaultTags(CONCRETE_STAIRS,CONCRETE_SLABS,CONCRETE_VERTICAL_SLABS,
                    CONCRETE_FENCES,CONCRETE_FENCE_GATES,CONCRETE_WALLS,CONCRETE_BUTTONS,CONCRETE_PRESSURE_PLATES).build();
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
        BlockBuilder.create(CRYING_OBSIDIAN,Items.STONE,hard,MOBS).build();

        // 带釉陶瓦只注册台阶。
        for (final Block BLOCK : GLAZED_TERRACOTTAS) {
            BlockBuilder.createSlab(BLOCK).setInstance(new ExtShapeGlazedTerracottaSlabBlock(FabricBlockSettings.copyOf(BLOCK))).setDefaultTag(GLAZED_TERRACOTTA_SLABS).build();
        }

        GLAZED_TERRACOTTA_SLABS.addToTag(SLABS);
    }

    public static void init() {
    }
}
