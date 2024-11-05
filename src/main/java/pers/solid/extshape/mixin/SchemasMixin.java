package pers.solid.extshape.mixin;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.datafixer.Schemas;
import net.minecraft.datafixer.fix.BlockNameFix;
import net.minecraft.datafixer.fix.ItemNameFix;
import org.jetbrains.annotations.Contract;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pers.solid.extshape.builder.BlockShape;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

/**
 * 此 mixin 用于将之前版本的一些 id 同步更新为新版本的。
 */
@Mixin(Schemas.class)
public abstract class SchemasMixin {
  @Shadow
  @Final
  private static BiFunction<Integer, Schema, Schema> EMPTY_IDENTIFIER_NORMALIZE;

  @Contract
  @Shadow
  private static UnaryOperator<String> replacing(Map<String, String> replacements) {
    throw new AssertionError();
  }

  @Inject(method = "build", at = @At("TAIL"))
  private static void postBuild(DataFixerBuilder builder, CallbackInfo ci) {
    // in 24w18a (dataVersion = 3940), some blocks are not experimental, and we should convert them into vanilla ones.
    final Schema schema3939 = builder.addSchema(3939, EMPTY_IDENTIFIER_NORMALIZE);
    final UnaryOperator<String> unaryOperator = replacing(Map.of(
        "extshape:tuff_stairs", "minecraft:tuff_stairs",
        "extshape:tuff_slab", "minecraft:tuff_slab",
        "extshape:tuff_wall", "minecraft:tuff_wall"
    ));
    builder.addFixer(BlockNameFix.create(schema3939, "Rename tuff stairs and slab blocks from 'extshape' namespace to vanilla ones", unaryOperator));
    builder.addFixer(ItemNameFix.create(schema3939, "Rename tuff stairs and slab items from 'extshape' namespace to vanilla ones", unaryOperator));

    // in 24w44a (dataVersion = 4174), we have removed some blocks.
    final Schema schema4173 = builder.addSchema(4173, EMPTY_IDENTIFIER_NORMALIZE);
    final ImmutableMap.Builder<String, String> idMapBuilder = new ImmutableMap.Builder<>();
    final List<BlockShape> constructionShapes = List.of(BlockShape.QUARTER_PIECE, BlockShape.SLAB, BlockShape.STAIRS, BlockShape.VERTICAL_QUARTER_PIECE, BlockShape.VERTICAL_SLAB, BlockShape.VERTICAL_STAIRS);
    final List<String> logsAndStems = List.of("oak_log", "spruce_log", "birch_log", "jungle_log", "acacia_log", "cherry_log", "dark_oak_log", "pale_oak_log", "mangrove_log", "warped_stem", "crimson_stem");
    for (String path : Iterables.concat(logsAndStems, Lists.transform(logsAndStems, s -> "stripped_" + s))) {
      final String replacedPath = path.replace("_log", "_wood").replace("_stem", "_hyphae");
      Preconditions.checkState(!path.equals(replacedPath));
      for (BlockShape shape : constructionShapes) {
        idMapBuilder.put("extshape:" + path + "_" + shape.asString(), "extshape:" + replacedPath + "_" + shape.asString());
      }
    }

    idMapBuilder.put("extshape:cut_sandstone_wall", "extshape:smooth_sandstone_wall");
    idMapBuilder.put("extshape:cut_red_sandstone_wall", "extshape:smooth_red_sandstone_wall");
    idMapBuilder.put("extshape:chiseled_sandstone_wall", "minecraft:sandstone_wall");
    idMapBuilder.put("extshape:chiseled_red_sandstone_wall", "minecraft:sandstone_wall");

    for (BlockShape shape : constructionShapes) {
      idMapBuilder.put("extshape:chiseled_sandstone_" + shape.asString(), "extshape:sandstone_" + shape.asString());
      idMapBuilder.put("extshape:chiseled_red_sandstone_" + shape.asString(), "extshape:red_sandstone_" + shape.asString());
    }

    idMapBuilder.put("extshape:chiseled_quartz_button", "extshape:quartz_button");
    idMapBuilder.put("extshape:chiseled_quartz_fence", "extshape:quartz_fence");
    idMapBuilder.put("extshape:chiseled_quartz_fence_gate", "extshape:quartz_fence_gate");

    for (BlockShape shape : constructionShapes) {
      final boolean vanilla = shape == BlockShape.SLAB || shape == BlockShape.STAIRS;
      idMapBuilder.put("extshape:chiseled_polished_blackstone_" + shape.asString(), (vanilla ? "minecraft:" : "extshape:") + "polished_blackstone_" + shape.asString());
      idMapBuilder.put("extshape:chiseled_nether_brick_" + shape.asString(), (vanilla ? "minecraft:" : "extshape:") + "nether_brick_" + shape.asString());
    }

    for (BlockShape shape : BlockShape.values()) {
      final boolean vanilla = shape == BlockShape.SLAB || shape == BlockShape.STAIRS || shape == BlockShape.WALL;
      idMapBuilder.put("extshape:chiseled_tuff_" + shape.asString(), (vanilla ? "minecraft:" : "extshape:") + "tuff_" + shape.asString());
      idMapBuilder.put("extshape:chiseled_tuff_brick_" + shape.asString(), (vanilla ? "minecraft:" : "extshape:") + "tuff_brick_" + shape.asString());
      idMapBuilder.put("extshape:chiseled_deepslate_" + shape.asString(), (vanilla ? "minecraft:" : "extshape:") + "cobbled_deepslate_" + shape.asString());
      idMapBuilder.put("extshape:chiseled_stone_brick_" + shape.asString(), (vanilla ? "minecraft:" : "extshape:") + "stone_brick_" + shape.asString());
      idMapBuilder.put("extshape:polished_basalt_" + shape.asString(), "extshape:basalt_" + shape.asString());
    }

    final UnaryOperator<String> unaryOperator2 = replacing(idMapBuilder.build());
    builder.addFixer(BlockNameFix.create(schema4173, "Renamed some removed blocks of Extended Block Shapes mod", unaryOperator2));
    builder.addFixer(ItemNameFix.create(schema4173, "Renamed some removed items of Extended Block Shapes mod", unaryOperator2));
  }
}
