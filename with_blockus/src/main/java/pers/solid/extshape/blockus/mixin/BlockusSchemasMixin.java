package pers.solid.extshape.blockus.mixin;

import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.BlockRenameFix;
import net.minecraft.util.datafix.fixes.ItemRenameFix;
import net.minecraft.world.item.DyeColor;
import org.apache.commons.lang3.Validate;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pers.solid.extshape.blockus.ExtShapeBlockus;
import pers.solid.extshape.builder.BlockShape;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

@Mixin(DataFixers.class)
public abstract class BlockusSchemasMixin {
  @Shadow
  private static UnaryOperator<String> createRenamer(Map<String, String> replacements) {
    return null;
  }

  @Shadow
  @Final
  private static BiFunction<Integer, Schema, Schema> SAME_NAMESPACED;

  @Inject(method = "addFixers", at = @At("TAIL"))
  private static void postBuild(DataFixerBuilder builder, CallbackInfo ci) {
    final Map<String, String> idMap = new HashMap<>();
    removeFenceAndGate(idMap, "stone_tiles", "extshape:stone");
    removeFenceAndGate(idMap, "herringbone_stone_brick", "extshape:stone_brick");
    removeFenceAndGate(idMap, "andesite_brick", "extshape:andesite");
    removeShapesExcept(idMap, "chiseled_andesite", "extshape:andesite", BlockShape.BUTTON, BlockShape.WALL, BlockShape.STAIRS, BlockShape.SLAB);
    removeShapesFor(idMap, "chiseled_andesite", "minecraft:andesite", BlockShape.WALL, BlockShape.STAIRS, BlockShape.SLAB);
    removeFenceAndGate(idMap, "herringbone_andesite_brick", "extshape:andesite");
    removeFenceAndGate(idMap, "diorite_brick", "extshape:diorite");
    removeShapesExcept(idMap, "chiseled_diorite", "extshape:diorite", BlockShape.BUTTON, BlockShape.WALL, BlockShape.STAIRS, BlockShape.SLAB);
    removeShapesFor(idMap, "chiseled_diorite", "minecraft:diorite", BlockShape.WALL, BlockShape.STAIRS, BlockShape.SLAB);
    removeFenceAndGate(idMap, "herringbone_diorite_brick", "extshape:diorite");
    removeFenceAndGate(idMap, "granite_brick", "extshape:granite");
    removeShapesExcept(idMap, "chiseled_granite", "extshape:granite", BlockShape.BUTTON, BlockShape.WALL, BlockShape.STAIRS, BlockShape.SLAB);
    removeShapesFor(idMap, "chiseled_granite", "minecraft:granite", BlockShape.WALL, BlockShape.STAIRS, BlockShape.SLAB);
    removeFenceAndGate(idMap, "herringbone_granite_brick", "extshape:granite");
    removeShapesExcept(idMap, "chiseled_mud_brick", "extshape:mud_brick", BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL);
    removeShapesFor(idMap, "chiseled_mud_brick", "minecraft:mud_brick", BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL);
    removeFenceAndGate(idMap, "dripstone_brick", "extshape:dripstone");
    removeShapesExcept(idMap, "chiseled_dripstone", "extshape:dripstone", BlockShape.BUTTON);
    removeShapesExcept(idMap, "cracked_tuff_brick", "extshape:tuff_brick", BlockShape.BUTTON, BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL);
    removeShapesFor(idMap, "cracked_tuff_brick", "minecraft:tuff_brick", BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL);
    removeShapesExcept(idMap, "carved_tuff_brick", "extshape:tuff_brick", BlockShape.BUTTON, BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL);
    removeShapesFor(idMap, "carved_tuff_brick", "minecraft:tuff_brick", BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL);
    removeFenceAndGate(idMap, "herringbone_tuff_brick", "extshape:tuff_brick");
    removeFenceAndGate(idMap, "herringbone_deepslate_brick", "extshape:deepslate_brick");
    removeFenceAndGate(idMap, "sculk_brick", "extshape_blockus:polished_sculk");
    removeShapesExcept(idMap, "chiseled_sculk_brick", "extshape_blockus:polished_sculk", BlockShape.BUTTON, BlockShape.STAIRS, BlockShape.SLAB, BlockShape.PRESSURE_PLATE);
    removeShapesFor(idMap, "chiseled_sculk_brick", "blockus:polished_sculk", BlockShape.STAIRS, BlockShape.SLAB, BlockShape.PRESSURE_PLATE, BlockShape.BUTTON);
    removeFenceAndGate(idMap, "amethyst_brick", "extshape_blockus:polished_amethyst");
    removeShapesExcept(idMap, "chiseled_amethyst", "extshape_blockus:polished_amethyst", BlockShape.PRESSURE_PLATE, BlockShape.BUTTON, BlockShape.STAIRS, BlockShape.SLAB);
    removeShapesFor(idMap, "chiseled_amethyst", "blockus:polished_amethyst", BlockShape.STAIRS, BlockShape.SLAB);
    removeFenceAndGate(idMap, "polished_blackstone_tiles", "extshape:polished_blackstone");
    removeFenceAndGate(idMap, "herringbone_polished_blackstone_brick", "extshape:polished_blackstone");
    removeFenceAndGate(idMap, "polished_basalt_brick", "extshape:smooth_basalt");
    removeFenceAndGate(idMap, "herringbone_polished_basalt_brick", "extshape:smooth_basalt");
    removeShapesExcept(idMap, "polished_basalt_circular_paving", "extshape:smooth_basalt", BlockShape.SLAB, BlockShape.PRESSURE_PLATE);
    removeFenceAndGate(idMap, "limestone_brick", "extshape_blockus:small_limestone_brick");
    removeFenceAndGate(idMap, "limestone_tiles", "extshape_blockus:limestone");
    removeShapesExcept(idMap, "chiseled_limestone", "extshape_blockus:limestone", BlockShape.BUTTON, BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL, BlockShape.PRESSURE_PLATE);
    removeShapesFor(idMap, "chiseled_limestone", "blockus:limestone", BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL, BlockShape.PRESSURE_PLATE);
    removeFenceAndGate(idMap, "limestone_squares", "extshape_blockus:limestone");
    removeFenceAndGate(idMap, "marble_brick", "extshape_blockus:small_marble_brick");
    removeFenceAndGate(idMap, "marble_tiles", "extshape_blockus:marble");
    removeShapesExcept(idMap, "chiseled_marble", "extshape_blockus:marble", BlockShape.BUTTON, BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL, BlockShape.PRESSURE_PLATE);
    removeShapesFor(idMap, "chiseled_marble", "blockus:marble", BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL, BlockShape.PRESSURE_PLATE);
    removeFenceAndGate(idMap, "marble_squares", "extshape_blockus:marble");
    removeFenceAndGate(idMap, "bluestone_brick", "extshape_blockus:small_bluestone_brick");
    removeFenceAndGate(idMap, "bluestone_tiles", "extshape_blockus:bluestone");
    removeShapesExcept(idMap, "chiseled_bluestone", "extshape_blockus:bluestone", BlockShape.BUTTON, BlockShape.PRESSURE_PLATE, BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL);
    removeShapesFor(idMap, "chiseled_bluestone", "blockus:bluestone", BlockShape.PRESSURE_PLATE, BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL);
    removeFenceAndGate(idMap, "bluestone_squares", "extshape_blockus:bluestone");
    removeFenceAndGate(idMap, "viridite_brick", "extshape_blockus:small_viridite_brick");
    removeFenceAndGate(idMap, "viridite_tiles", "extshape_blockus:viridite");
    removeShapesExcept(idMap, "chiseled_viridite", "extshape_blockus:viridite", BlockShape.BUTTON, BlockShape.PRESSURE_PLATE, BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL);
    removeShapesFor(idMap, "chiseled_viridite", "blockus:viridite", BlockShape.PRESSURE_PLATE, BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL);
    removeFenceAndGate(idMap, "viridite_squares", "extshape_blockus:viridite");
    removeShapesExcept(idMap, "chiseled_lava_brick", "extshape_blockus:lava_brick", BlockShape.BUTTON, BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL);
    removeShapesFor(idMap, "chiseled_lava_brick", "blockus:lava_brick", BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL);
    removeShapesExcept(idMap, "chiseled_lava_polished_blackstone_brick", "extshape_blockus:lava_polished_blackstone_brick", BlockShape.BUTTON, BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL);
    removeShapesFor(idMap, "chiseled_lava_polished_blackstone_brick", "blockus:lava_polished_blackstone_brick", BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL);
    idMap.put("extshape_blockus:ice_brick_wall", "blockus:ice_brick_wall");
    removeFenceAndGate(idMap, "obsidian_brick", "extshape_blockus:small_obsidian_brick");
    removeFenceAndGate(idMap, "netherrack_brick", "extshape_blockus:polished_netherrack");
    removeFenceAndGate(idMap, "quartz_tiles", "extshape:smooth_quartz");
    removeFenceAndGate(idMap, "magma_brick", "extshape_blockus:small_magma_brick");
    idMap.put("extshape_blockus:magma_brick_button", "extshape_blockus:small_magma_brick_button");
    removeShapesExcept(idMap, "chiseled_magma_brick", "extshape_blockus:small_magma_brick", BlockShape.BUTTON, BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL);
    removeShapesFor(idMap, "chiseled_magma_brick", "blockus:small_magma_brick", BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL);
    removeShapesFor(idMap, "herringbone_nether_brick", "extshape:nether_brick", BlockShape.FENCE_GATE);
    removeShapesFor(idMap, "herringbone_nether_brick", "minecraft:nether_brick", BlockShape.FENCE);
    removeFenceAndGate(idMap, "herringbone_red_nether_brick", "extshape:red_nether_brick");
    removeFenceAndGate(idMap, "herringbone_teal_nether_brick", "extshape_blockus:teal_nether_brick");
    removeShapesExcept(idMap, "chiseled_prismarine", "extshape:prismarine", BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.WALL, BlockShape.STAIRS, BlockShape.SLAB);
    removeShapesFor(idMap, "chiseled_prismarine", "minecraft:prismarine", BlockShape.WALL, BlockShape.STAIRS, BlockShape.SLAB);
    removeShapesExcept(idMap, "chiseled_dark_prismarine", "extshape:dark_prismarine", BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.STAIRS, BlockShape.SLAB);
    removeShapesFor(idMap, "chiseled_dark_prismarine", "minecraft:dark_prismarine", BlockShape.STAIRS, BlockShape.SLAB);
    removeShapesExcept(idMap, "prismarine_circular_paving", "extshape:prismarine", BlockShape.SLAB, BlockShape.PRESSURE_PLATE, BlockShape.WALL, BlockShape.STAIRS);
    removeShapesFor(idMap, "prismarine_circular_paving", "minecraft:prismarine", BlockShape.WALL, BlockShape.STAIRS);
    removeFenceAndGate(idMap, "large_brick", "extshape:brick");
    removeFenceAndGate(idMap, "herringbone_brick", "extshape:brick");
    removeFenceAndGate(idMap, "herringbone_soaked_brick", "extshape_blockus:soaked_brick");
    removeFenceAndGate(idMap, "herringbone_charred_brick", "extshape_blockus:charred_brick");
    removeFenceAndGate(idMap, "herringbone_sandy_brick", "extshape_blockus:sandy_brick");
    removeFenceAndGate(idMap, "sandstone_brick", "extshape_blockus:small_sandstone_brick");
    removeFenceAndGate(idMap, "red_sandstone_brick", "extshape_blockus:small_red_sandstone_brick");
    removeFenceAndGate(idMap, "soul_sandstone_brick", "extshape_blockus:small_soul_sandstone_brick");
    removeShapesFor(idMap, "cut_soul_sandstone", "extshape_blockus:smooth_soul_sandstone", BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.WALL, BlockShape.PRESSURE_PLATE);
    idMap.put("extshape_blockus:cut_soul_sandstone", "blockus:cut_soul_sandstone");
    removeShapesExcept(idMap, "chiseled_soul_sandstone", "extshape_blockus:smooth_soul_sandstone", BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.STAIRS, BlockShape.SLAB);
    removeShapesFor(idMap, "chiseled_soul_sandstone", "blockus:smooth_soul_sandstone", BlockShape.STAIRS, BlockShape.SLAB);
    removeFenceAndGate(idMap, "purpur_brick", "extshape_blockus:small_purpur_brick");
    removeShapesExcept(idMap, "chiseled_purpur", "extshape_blockus:polished_purpur", BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.STAIRS, BlockShape.SLAB);
    removeShapesFor(idMap, "chiseled_purpur", "blockus:polished_purpur", BlockShape.STAIRS, BlockShape.SLAB);
    removeFenceAndGate(idMap, "purpur_squares", "extshape_blockus:polished_purpur");
    removeFenceAndGate(idMap, "phantom_purpur_brick", "extshape_blockus:small_phantom_purpur_brick");
    removeShapesExcept(idMap, "chiseled_phantom_purpur", "extshape_blockus:polished_phantom_purpur", BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.STAIRS, BlockShape.SLAB);
    removeShapesFor(idMap, "chiseled_phantom_purpur", "blockus:polished_phantom_purpur", BlockShape.STAIRS, BlockShape.SLAB);
    removeFenceAndGate(idMap, "phantom_purpur_squares", "extshape_blockus:polished_phantom_purpur");
    removeShapesExcept(idMap, "chiseled_end_stone_brick", "extshape_blockus:small_end_stone_brick", BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL);
    removeShapesFor(idMap, "chiseled_end_stone_brick", "blockus:small_end_stone_brick", BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL);
    removeFenceAndGate(idMap, "herringbone_end_stone_brick", "extshape_blockus:small_end_stone_brick");

    removeShapesFor(idMap, "white_oak_log", "extshape_blockus:white_oak_wood", BlockShape.STAIRS, BlockShape.SLAB, BlockShape.VERTICAL_SLAB, BlockShape.VERTICAL_STAIRS, BlockShape.QUARTER_PIECE, BlockShape.VERTICAL_QUARTER_PIECE);
    removeShapesFor(idMap, "stripped_white_oak_log", "extshape_blockus:stripped_white_oak_wood", BlockShape.STAIRS, BlockShape.SLAB, BlockShape.VERTICAL_SLAB, BlockShape.VERTICAL_STAIRS, BlockShape.QUARTER_PIECE, BlockShape.VERTICAL_QUARTER_PIECE);

    for (DyeColor color : DyeColor.values()) {
      removeShapesExcept(idMap, "chiseled_" + color.getSerializedName() + "_concrete_brick", "extshape_blockus:" + color.getSerializedName() + "_concrete_brick", BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL, BlockShape.BUTTON, BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.PRESSURE_PLATE);
      removeShapesFor(idMap, "chiseled_" + color.getSerializedName() + "_concrete_brick", "blockus:" + color.getSerializedName() + "_concrete_brick", BlockShape.STAIRS, BlockShape.SLAB, BlockShape.WALL);
    }

    if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
      idMap.forEach((k, v) -> {
        Validate.validState(!k.contains("__"), "%s contains duplicate underscore!", k);
        Validate.validState(!v.contains("__"), "%s contains duplicate underscore!", v);
        Validate.validState(k.contains(":"), "%s misses namespace!", k);
        Validate.validState(v.contains(":"), "%s misses namespace!", v);
      });
    }

    final Schema schema = builder.addSchema(4081, SAME_NAMESPACED);
    final UnaryOperator<String> unaryOperator = createRenamer(idMap);
    builder.addFixer(BlockRenameFix.create(schema, "Rename removed blocks in Extended Block Shapes - Blockus mod", unaryOperator));
    builder.addFixer(ItemRenameFix.create(schema, "Rename removed items in Extended Block Shapes - Blockus mod", unaryOperator));

    if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
      ExtShapeBlockus.replacing_id_map = idMap;
    }
  }

  @Unique
  private static void removeFenceAndGate(Map<String, String> idMap, String from, String to) {
    idMap.put("extshape_blockus:" + from + "_fence", to + "_fence");
    idMap.put("extshape_blockus:" + from + "_fence_gate", to + "_fence_gate");
  }

  @Unique
  private static void removeShapesFor(Map<String, String> idMap, String from, String to, BlockShape... shapes) {
    for (BlockShape shape : shapes) {
      idMap.put("extshape_blockus:" + from + "_" + shape.getSerializedName(), to + "_" + shape.getSerializedName());
    }
  }

  @Unique
  private static void removeShapesExcept(Map<String, String> idMap, String from, String to, BlockShape... except) {
    final Set<BlockShape> list = Set.of(except);
    for (BlockShape shape : BlockShape.values()) {
      if (!list.contains(shape)) {
        idMap.put("extshape_blockus:" + from + "_" + shape.getSerializedName(), to + "_" + shape.getSerializedName());
      }
    }
  }
}
