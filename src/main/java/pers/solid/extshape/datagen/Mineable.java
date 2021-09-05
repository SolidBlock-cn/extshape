package pers.solid.extshape.datagen;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import net.minecraft.util.registry.Registry;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public final class Mineable {

    // 从原版数据包获取Mineable方块标签。
    public static final String axe = """
            {
              "replace": false,
              "values": [
                "minecraft:note_block",
                "minecraft:attached_melon_stem",
                "minecraft:attached_pumpkin_stem",
                "minecraft:azalea",
                "minecraft:bamboo",
                "minecraft:barrel",
                "minecraft:bee_nest",
                "minecraft:beehive",
                "minecraft:beetroots",
                "minecraft:big_dripleaf_stem",
                "minecraft:big_dripleaf",
                "minecraft:bookshelf",
                "minecraft:brown_mushroom_block",
                "minecraft:brown_mushroom",
                "minecraft:campfire",
                "minecraft:carrots",
                "minecraft:cartography_table",
                "minecraft:carved_pumpkin",
                "minecraft:cave_vines_plant",
                "minecraft:cave_vines",
                "minecraft:chest",
                "minecraft:chorus_flower",
                "minecraft:chorus_plant",
                "minecraft:cocoa",
                "minecraft:composter",
                "minecraft:crafting_table",
                "minecraft:crimson_fungus",
                "minecraft:daylight_detector",
                "minecraft:dead_bush",
                "minecraft:fern",
                "minecraft:fletching_table",
                "minecraft:glow_lichen",
                "minecraft:grass",
                "minecraft:hanging_roots",
                "minecraft:jack_o_lantern",
                "minecraft:jukebox",
                "minecraft:ladder",
                "minecraft:large_fern",
                "minecraft:lectern",
                "minecraft:lily_pad",
                "minecraft:loom",
                "minecraft:melon_stem",
                "minecraft:melon",
                "minecraft:mushroom_stem",
                "minecraft:nether_wart",
                "minecraft:potatoes",
                "minecraft:pumpkin_stem",
                "minecraft:pumpkin",
                "minecraft:red_mushroom_block",
                "minecraft:red_mushroom",
                "minecraft:scaffolding",
                "minecraft:small_dripleaf",
                "minecraft:smithing_table",
                "minecraft:soul_campfire",
                "minecraft:spore_blossom",
                "minecraft:sugar_cane",
                "minecraft:sweet_berry_bush",
                "minecraft:tall_grass",
                "minecraft:trapped_chest",
                "minecraft:twisting_vines_plant",
                "minecraft:twisting_vines",
                "minecraft:vine",
                "minecraft:warped_fungus",
                "minecraft:weeping_vines_plant",
                "minecraft:weeping_vines",
                "minecraft:wheat",
                "#minecraft:banners",
                "#minecraft:fence_gates",
                "#minecraft:flowers",
                "#minecraft:logs",
                "#minecraft:planks",
                "#minecraft:saplings",
                "#minecraft:signs",
                "#minecraft:wooden_buttons",
                "#minecraft:wooden_doors",
                "#minecraft:wooden_fences",
                "#minecraft:wooden_pressure_plates",
                "#minecraft:wooden_slabs",
                "#minecraft:wooden_stairs",
                "#minecraft:wooden_trapdoors"
              ]
            }""";
    public static final String hoe = """
            {
              "replace": false,
              "values": [
                "minecraft:nether_wart_block",
                "minecraft:warped_wart_block",
                "minecraft:hay_block",
                "minecraft:dried_kelp_block",
                "minecraft:target",
                "minecraft:shroomlight",
                "minecraft:sponge",
                "minecraft:wet_sponge",
                "minecraft:jungle_leaves",
                "minecraft:oak_leaves",
                "minecraft:spruce_leaves",
                "minecraft:dark_oak_leaves",
                "minecraft:acacia_leaves",
                "minecraft:birch_leaves",
                "minecraft:azalea_leaves",
                "minecraft:flowering_azalea_leaves",
                "minecraft:sculk_sensor",
                "minecraft:moss_block",
                "minecraft:moss_carpet"
              ]
            }""";
    public static final String pickaxe = """
            {
              "replace": false,
              "values": [
                "minecraft:stone",
                "minecraft:granite",
                "minecraft:polished_granite",
                "minecraft:diorite",
                "minecraft:polished_diorite",
                "minecraft:andesite",
                "minecraft:polished_andesite",
                "minecraft:cobblestone",
                "minecraft:gold_ore",
                "minecraft:deepslate_gold_ore",
                "minecraft:iron_ore",
                "minecraft:deepslate_iron_ore",
                "minecraft:coal_ore",
                "minecraft:deepslate_coal_ore",
                "minecraft:nether_gold_ore",
                "minecraft:lapis_ore",
                "minecraft:deepslate_lapis_ore",
                "minecraft:lapis_block",
                "minecraft:dispenser",
                "minecraft:sandstone",
                "minecraft:chiseled_sandstone",
                "minecraft:cut_sandstone",
                "minecraft:gold_block",
                "minecraft:iron_block",
                "minecraft:bricks",
                "minecraft:mossy_cobblestone",
                "minecraft:obsidian",
                "minecraft:spawner",
                "minecraft:diamond_ore",
                "minecraft:deepslate_diamond_ore",
                "minecraft:diamond_block",
                "minecraft:furnace",
                "minecraft:cobblestone_stairs",
                "minecraft:stone_pressure_plate",
                "minecraft:iron_door",
                "minecraft:redstone_ore",
                "minecraft:deepslate_redstone_ore",
                "minecraft:netherrack",
                "minecraft:basalt",
                "minecraft:polished_basalt",
                "minecraft:stone_bricks",
                "minecraft:mossy_stone_bricks",
                "minecraft:cracked_stone_bricks",
                "minecraft:chiseled_stone_bricks",
                "minecraft:iron_bars",
                "minecraft:chain",
                "minecraft:brick_stairs",
                "minecraft:stone_brick_stairs",
                "minecraft:nether_bricks",
                "minecraft:nether_brick_fence",
                "minecraft:nether_brick_stairs",
                "minecraft:enchanting_table",
                "minecraft:brewing_stand",
                "minecraft:end_stone",
                "minecraft:sandstone_stairs",
                "minecraft:emerald_ore",
                "minecraft:deepslate_emerald_ore",
                "minecraft:ender_chest",
                "minecraft:emerald_block",
                "minecraft:light_weighted_pressure_plate",
                "minecraft:heavy_weighted_pressure_plate",
                "minecraft:redstone_block",
                "minecraft:nether_quartz_ore",
                "minecraft:hopper",
                "minecraft:quartz_block",
                "minecraft:chiseled_quartz_block",
                "minecraft:quartz_pillar",
                "minecraft:quartz_stairs",
                "minecraft:dropper",
                "minecraft:white_terracotta",
                "minecraft:orange_terracotta",
                "minecraft:magenta_terracotta",
                "minecraft:light_blue_terracotta",
                "minecraft:yellow_terracotta",
                "minecraft:lime_terracotta",
                "minecraft:pink_terracotta",
                "minecraft:gray_terracotta",
                "minecraft:light_gray_terracotta",
                "minecraft:cyan_terracotta",
                "minecraft:purple_terracotta",
                "minecraft:blue_terracotta",
                "minecraft:brown_terracotta",
                "minecraft:green_terracotta",
                "minecraft:red_terracotta",
                "minecraft:black_terracotta",
                "minecraft:iron_trapdoor",
                "minecraft:prismarine",
                "minecraft:prismarine_bricks",
                "minecraft:dark_prismarine",
                "minecraft:prismarine_stairs",
                "minecraft:prismarine_brick_stairs",
                "minecraft:dark_prismarine_stairs",
                "minecraft:prismarine_slab",
                "minecraft:prismarine_brick_slab",
                "minecraft:dark_prismarine_slab",
                "minecraft:terracotta",
                "minecraft:coal_block",
                "minecraft:red_sandstone",
                "minecraft:chiseled_red_sandstone",
                "minecraft:cut_red_sandstone",
                "minecraft:red_sandstone_stairs",
                "minecraft:stone_slab",
                "minecraft:smooth_stone_slab",
                "minecraft:sandstone_slab",
                "minecraft:cut_sandstone_slab",
                "minecraft:petrified_oak_slab",
                "minecraft:cobblestone_slab",
                "minecraft:brick_slab",
                "minecraft:stone_brick_slab",
                "minecraft:nether_brick_slab",
                "minecraft:quartz_slab",
                "minecraft:red_sandstone_slab",
                "minecraft:cut_red_sandstone_slab",
                "minecraft:purpur_slab",
                "minecraft:smooth_stone",
                "minecraft:smooth_sandstone",
                "minecraft:smooth_quartz",
                "minecraft:smooth_red_sandstone",
                "minecraft:purpur_block",
                "minecraft:purpur_pillar",
                "minecraft:purpur_stairs",
                "minecraft:end_stone_bricks",
                "minecraft:magma_block",
                "minecraft:red_nether_bricks",
                "minecraft:bone_block",
                "minecraft:observer",
                "minecraft:white_glazed_terracotta",
                "minecraft:orange_glazed_terracotta",
                "minecraft:magenta_glazed_terracotta",
                "minecraft:light_blue_glazed_terracotta",
                "minecraft:yellow_glazed_terracotta",
                "minecraft:lime_glazed_terracotta",
                "minecraft:pink_glazed_terracotta",
                "minecraft:gray_glazed_terracotta",
                "minecraft:light_gray_glazed_terracotta",
                "minecraft:cyan_glazed_terracotta",
                "minecraft:purple_glazed_terracotta",
                "minecraft:blue_glazed_terracotta",
                "minecraft:brown_glazed_terracotta",
                "minecraft:green_glazed_terracotta",
                "minecraft:red_glazed_terracotta",
                "minecraft:black_glazed_terracotta",
                "minecraft:white_concrete",
                "minecraft:orange_concrete",
                "minecraft:magenta_concrete",
                "minecraft:light_blue_concrete",
                "minecraft:yellow_concrete",
                "minecraft:lime_concrete",
                "minecraft:pink_concrete",
                "minecraft:gray_concrete",
                "minecraft:light_gray_concrete",
                "minecraft:cyan_concrete",
                "minecraft:purple_concrete",
                "minecraft:blue_concrete",
                "minecraft:brown_concrete",
                "minecraft:green_concrete",
                "minecraft:red_concrete",
                "minecraft:black_concrete",
                "minecraft:dead_tube_coral_block",
                "minecraft:dead_brain_coral_block",
                "minecraft:dead_bubble_coral_block",
                "minecraft:dead_fire_coral_block",
                "minecraft:dead_horn_coral_block",
                "minecraft:tube_coral_block",
                "minecraft:brain_coral_block",
                "minecraft:bubble_coral_block",
                "minecraft:fire_coral_block",
                "minecraft:horn_coral_block",
                "minecraft:dead_tube_coral",
                "minecraft:dead_brain_coral",
                "minecraft:dead_bubble_coral",
                "minecraft:dead_fire_coral",
                "minecraft:dead_horn_coral",
                "minecraft:dead_tube_coral_fan",
                "minecraft:dead_brain_coral_fan",
                "minecraft:dead_bubble_coral_fan",
                "minecraft:dead_fire_coral_fan",
                "minecraft:dead_horn_coral_fan",
                "minecraft:dead_tube_coral_wall_fan",
                "minecraft:dead_brain_coral_wall_fan",
                "minecraft:dead_bubble_coral_wall_fan",
                "minecraft:dead_fire_coral_wall_fan",
                "minecraft:dead_horn_coral_wall_fan",
                "minecraft:polished_granite_stairs",
                "minecraft:smooth_red_sandstone_stairs",
                "minecraft:mossy_stone_brick_stairs",
                "minecraft:polished_diorite_stairs",
                "minecraft:mossy_cobblestone_stairs",
                "minecraft:end_stone_brick_stairs",
                "minecraft:stone_stairs",
                "minecraft:smooth_sandstone_stairs",
                "minecraft:smooth_quartz_stairs",
                "minecraft:granite_stairs",
                "minecraft:andesite_stairs",
                "minecraft:red_nether_brick_stairs",
                "minecraft:polished_andesite_stairs",
                "minecraft:diorite_stairs",
                "minecraft:polished_granite_slab",
                "minecraft:smooth_red_sandstone_slab",
                "minecraft:mossy_stone_brick_slab",
                "minecraft:polished_diorite_slab",
                "minecraft:mossy_cobblestone_slab",
                "minecraft:end_stone_brick_slab",
                "minecraft:smooth_sandstone_slab",
                "minecraft:smooth_quartz_slab",
                "minecraft:granite_slab",
                "minecraft:andesite_slab",
                "minecraft:red_nether_brick_slab",
                "minecraft:polished_andesite_slab",
                "minecraft:diorite_slab",
                "minecraft:smoker",
                "minecraft:blast_furnace",
                "minecraft:grindstone",
                "minecraft:stonecutter",
                "minecraft:bell",
                "minecraft:lantern",
                "minecraft:soul_lantern",
                "minecraft:warped_nylium",
                "minecraft:crimson_nylium",
                "minecraft:netherite_block",
                "minecraft:ancient_debris",
                "minecraft:crying_obsidian",
                "minecraft:respawn_anchor",
                "minecraft:lodestone",
                "minecraft:blackstone",
                "minecraft:blackstone_stairs",
                "minecraft:blackstone_slab",
                "minecraft:polished_blackstone",
                "minecraft:polished_blackstone_bricks",
                "minecraft:cracked_polished_blackstone_bricks",
                "minecraft:chiseled_polished_blackstone",
                "minecraft:polished_blackstone_brick_slab",
                "minecraft:polished_blackstone_brick_stairs",
                "minecraft:gilded_blackstone",
                "minecraft:polished_blackstone_stairs",
                "minecraft:polished_blackstone_slab",
                "minecraft:polished_blackstone_pressure_plate",
                "minecraft:chiseled_nether_bricks",
                "minecraft:cracked_nether_bricks",
                "minecraft:quartz_bricks",
                "minecraft:tuff",
                "minecraft:calcite",
                "minecraft:oxidized_copper",
                "minecraft:weathered_copper",
                "minecraft:exposed_copper",
                "minecraft:copper_block",
                "minecraft:copper_ore",
                "minecraft:deepslate_copper_ore",
                "minecraft:oxidized_cut_copper",
                "minecraft:weathered_cut_copper",
                "minecraft:exposed_cut_copper",
                "minecraft:cut_copper",
                "minecraft:oxidized_cut_copper_stairs",
                "minecraft:weathered_cut_copper_stairs",
                "minecraft:exposed_cut_copper_stairs",
                "minecraft:cut_copper_stairs",
                "minecraft:oxidized_cut_copper_slab",
                "minecraft:weathered_cut_copper_slab",
                "minecraft:exposed_cut_copper_slab",
                "minecraft:cut_copper_slab",
                "minecraft:waxed_copper_block",
                "minecraft:waxed_weathered_copper",
                "minecraft:waxed_exposed_copper",
                "minecraft:waxed_oxidized_copper",
                "minecraft:waxed_oxidized_cut_copper",
                "minecraft:waxed_weathered_cut_copper",
                "minecraft:waxed_exposed_cut_copper",
                "minecraft:waxed_cut_copper",
                "minecraft:waxed_oxidized_cut_copper_stairs",
                "minecraft:waxed_weathered_cut_copper_stairs",
                "minecraft:waxed_exposed_cut_copper_stairs",
                "minecraft:waxed_cut_copper_stairs",
                "minecraft:waxed_oxidized_cut_copper_slab",
                "minecraft:waxed_weathered_cut_copper_slab",
                "minecraft:waxed_exposed_cut_copper_slab",
                "minecraft:waxed_cut_copper_slab",
                "minecraft:lightning_rod",
                "minecraft:pointed_dripstone",
                "minecraft:dripstone_block",
                "minecraft:deepslate",
                "minecraft:cobbled_deepslate",
                "minecraft:cobbled_deepslate_stairs",
                "minecraft:cobbled_deepslate_slab",
                "minecraft:polished_deepslate",
                "minecraft:polished_deepslate_stairs",
                "minecraft:polished_deepslate_slab",
                "minecraft:deepslate_tiles",
                "minecraft:deepslate_tile_stairs",
                "minecraft:deepslate_tile_slab",
                "minecraft:deepslate_bricks",
                "minecraft:deepslate_brick_stairs",
                "minecraft:deepslate_brick_slab",
                "minecraft:chiseled_deepslate",
                "minecraft:cracked_deepslate_bricks",
                "minecraft:cracked_deepslate_tiles",
                "minecraft:smooth_basalt",
                "minecraft:raw_iron_block",
                "minecraft:raw_copper_block",
                "minecraft:raw_gold_block",
                "minecraft:ice",
                "minecraft:packed_ice",
                "minecraft:blue_ice",
                "minecraft:stone_button",
                "minecraft:piston",
                "minecraft:sticky_piston",
                "minecraft:piston_head",
                "minecraft:amethyst_cluster",
                "minecraft:small_amethyst_bud",
                "minecraft:medium_amethyst_bud",
                "minecraft:large_amethyst_bud",
                "minecraft:amethyst_block",
                "minecraft:budding_amethyst",
                "minecraft:infested_cobblestone",
                "minecraft:infested_chiseled_stone_bricks",
                "minecraft:infested_cracked_stone_bricks",
                "minecraft:infested_deepslate",
                "minecraft:infested_stone",
                "minecraft:infested_mossy_stone_bricks",
                "minecraft:infested_stone_bricks",
                "#minecraft:walls",
                "#minecraft:shulker_boxes",
                "#minecraft:anvil",
                "#minecraft:cauldrons",
                "#minecraft:rails"
              ]
            }""";
    public static final String shovel = """
            {
              "replace": false,
              "values": [
                "minecraft:clay",
                "minecraft:dirt",
                "minecraft:coarse_dirt",
                "minecraft:podzol",
                "minecraft:farmland",
                "minecraft:grass_block",
                "minecraft:gravel",
                "minecraft:mycelium",
                "minecraft:sand",
                "minecraft:red_sand",
                "minecraft:snow_block",
                "minecraft:snow",
                "minecraft:soul_sand",
                "minecraft:dirt_path",
                "minecraft:white_concrete_powder",
                "minecraft:orange_concrete_powder",
                "minecraft:magenta_concrete_powder",
                "minecraft:light_blue_concrete_powder",
                "minecraft:yellow_concrete_powder",
                "minecraft:lime_concrete_powder",
                "minecraft:pink_concrete_powder",
                "minecraft:gray_concrete_powder",
                "minecraft:light_gray_concrete_powder",
                "minecraft:cyan_concrete_powder",
                "minecraft:purple_concrete_powder",
                "minecraft:blue_concrete_powder",
                "minecraft:brown_concrete_powder",
                "minecraft:green_concrete_powder",
                "minecraft:red_concrete_powder",
                "minecraft:black_concrete_powder",
                "minecraft:soul_soil",
                "minecraft:rooted_dirt"
              ]
            }""";
    public static final ExtShapeBlockTag VANILLA_AXE_MINEABLE = new ExtShapeBlockTag();
    public static final ExtShapeBlockTag VANILLA_HOE_MINEABLE = new ExtShapeBlockTag();
    public static final ExtShapeBlockTag VANILLA_PICKAXE_MINEABLE = new ExtShapeBlockTag();
    public static final ExtShapeBlockTag VANILLA_SHOVEL_MINEABLE = new ExtShapeBlockTag();

    static {
        parse(VANILLA_AXE_MINEABLE, axe);
        parse(VANILLA_HOE_MINEABLE, hoe);
        parse(VANILLA_PICKAXE_MINEABLE, pickaxe);
        parse(VANILLA_SHOVEL_MINEABLE, shovel);
    }

    public static void parse(ExtShapeBlockTag tag, String jsonString) {
        JsonObject object = new JsonParser().parse(jsonString).getAsJsonObject();
        JsonArray list = object.get("values").getAsJsonArray();
        for (JsonElement idElement : list) {
            String idString = idElement.getAsString();
            try {
                // *暂时* 忽略掉井号开头的标签
                if ("#minecraft:planks".equals(idString)) {
                    tag.addTag(ExtShapeBlockTag.PLANKS);
                }
                if (idString.startsWith("#")) continue;

                Identifier identifier = new Identifier(idString);
                if (!Registry.BLOCK.containsId(identifier)) {
                    ExtShape.EXTSHAPE_LOGGER.warn(String.format("When parsing mineable tags %s, the block " +
                                    "registry does not contain this tag: %s", tag.toString(),
                            identifier));
                }
                Block block = Registry.BLOCK.get(identifier);
                tag.add(block);
            } catch (InvalidIdentifierException e) {
                ExtShape.EXTSHAPE_LOGGER.error(String.format("Internal error: failed to resolve id %s.", idString));
            }
        }
    }
}
