package pers.solid.extshape.mixin;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.BlockRenameFix;
import net.minecraft.util.datafix.fixes.ItemRenameFix;
import net.minecraft.util.filefix.FileFixerUpper;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.Contract;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.builder.BlockShape;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

/**
 * 此 mixin 用于将之前版本的一些 id 同步更新为新版本的。
 */
@Mixin(DataFixers.class)
public abstract class SchemasMixin {
  @Shadow
  @Final
  private static BiFunction<Integer, Schema, Schema> SAME_NAMESPACED;

  @Contract
  @Shadow
  private static UnaryOperator<String> createRenamer(Map<String, String> replacements) {
    throw new AssertionError();
  }

  @Inject(method = "addFixers", at = @At("TAIL"))
  private static void postBuild(DataFixerBuilder builder, FileFixerUpper.Builder fileFixerUpper, CallbackInfo ci) {
    // in 24w18a (dataVersion = 3940), some blocks are not experimental, and we should convert them into vanilla ones.
    final Schema schema3939 = builder.addSchema(3939, SAME_NAMESPACED);
    final UnaryOperator<String> unaryOperator = createRenamer(Map.of(
        "extshape:tuff_stairs", "minecraft:tuff_stairs",
        "extshape:tuff_slab", "minecraft:tuff_slab",
        "extshape:tuff_wall", "minecraft:tuff_wall"
    ));
    builder.addFixer(BlockRenameFix.create(schema3939, "Rename tuff stairs and slab blocks from 'extshape' namespace to vanilla ones", unaryOperator));
    builder.addFixer(ItemRenameFix.create(schema3939, "Rename tuff stairs and slab items from 'extshape' namespace to vanilla ones", unaryOperator));

    // in 24w44a (dataVersion = 4174), we have removed some blocks.
    final Schema schema4173 = builder.addSchema(4173, SAME_NAMESPACED);
    final ImmutableMap.Builder<String, String> idMapBuilder = new ImmutableMap.Builder<>();
    final List<BlockShape> constructionShapes = List.of(BlockShape.QUARTER_PIECE, BlockShape.SLAB, BlockShape.STAIRS, BlockShape.VERTICAL_QUARTER_PIECE, BlockShape.VERTICAL_SLAB, BlockShape.VERTICAL_STAIRS);
    final List<String> logsAndStems = List.of("oak_log", "spruce_log", "birch_log", "jungle_log", "acacia_log", "cherry_log", "dark_oak_log", "pale_oak_log", "mangrove_log", "warped_stem", "crimson_stem");
    for (String path : Iterables.concat(logsAndStems, Lists.transform(logsAndStems, s -> "stripped_" + s))) {
      final String replacedPath = path.replace("_log", "_wood").replace("_stem", "_hyphae");
      Preconditions.checkState(!path.equals(replacedPath));
      for (BlockShape shape : constructionShapes) {
        idMapBuilder.put("extshape:" + path + "_" + shape.getSerializedName(), "extshape:" + replacedPath + "_" + shape.getSerializedName());
      }
    }

    idMapBuilder.put("extshape:cut_sandstone_wall", "extshape:smooth_sandstone_wall");
    idMapBuilder.put("extshape:cut_red_sandstone_wall", "extshape:smooth_red_sandstone_wall");
    idMapBuilder.put("extshape:chiseled_sandstone_wall", "minecraft:sandstone_wall");
    idMapBuilder.put("extshape:chiseled_red_sandstone_wall", "minecraft:sandstone_wall");

    for (BlockShape shape : constructionShapes) {
      final boolean vanilla = shape == BlockShape.SLAB || shape == BlockShape.STAIRS || shape == BlockShape.WALL;
      idMapBuilder.put("extshape:chiseled_sandstone_" + shape.getSerializedName(), (vanilla ? "minecraft:" : "extshape:") + "sandstone_" + shape.getSerializedName());
      idMapBuilder.put("extshape:chiseled_red_sandstone_" + shape.getSerializedName(), (vanilla ? "minecraft:" : "extshape:") + "red_sandstone_" + shape.getSerializedName());
    }

    idMapBuilder.put("extshape:chiseled_quartz_button", "extshape:smooth_quartz_button");
    idMapBuilder.put("extshape:chiseled_quartz_fence", "extshape:smooth_quartz_fence");
    idMapBuilder.put("extshape:chiseled_quartz_fence_gate", "extshape:smooth_quartz_fence_gate");
    idMapBuilder.put("extshape:quartz_brick_button", "extshape:smooth_quartz_button");
    idMapBuilder.put("extshape:quartz_brick_fence", "extshape:smooth_quartz_fence");
    idMapBuilder.put("extshape:quartz_brick_fence_gate", "extshape:smooth_quartz_fence_gate");
    idMapBuilder.put("extshape:quartz_button", "extshape:smooth_quartz_button");
    idMapBuilder.put("extshape:quartz_fence", "extshape:smooth_quartz_fence");
    idMapBuilder.put("extshape:quartz_fence_gate", "extshape:smooth_quartz_fence_gate");
    idMapBuilder.put("extshape:quartz_pressure_plate", "extshape:smooth_quartz_pressure_plate");

    for (BlockShape shape : constructionShapes) {
      final boolean vanilla = shape == BlockShape.SLAB || shape == BlockShape.STAIRS;
      idMapBuilder.put("extshape:chiseled_polished_blackstone_" + shape.getSerializedName(), (vanilla ? "minecraft:" : "extshape:") + "polished_blackstone_" + shape.getSerializedName());
      idMapBuilder.put("extshape:chiseled_nether_brick_" + shape.getSerializedName(), (vanilla ? "minecraft:" : "extshape:") + "nether_brick_" + shape.getSerializedName());
    }

    for (BlockShape shape : BlockShape.values()) {
      final boolean vanilla = shape == BlockShape.SLAB || shape == BlockShape.STAIRS || shape == BlockShape.WALL;
      idMapBuilder.put("extshape:chiseled_tuff_" + shape.getSerializedName(), (vanilla ? "minecraft:" : "extshape:") + "tuff_" + shape.getSerializedName());
      idMapBuilder.put("extshape:chiseled_tuff_brick_" + shape.getSerializedName(), (vanilla ? "minecraft:" : "extshape:") + "tuff_brick_" + shape.getSerializedName());
      idMapBuilder.put("extshape:chiseled_deepslate_" + shape.getSerializedName(), (vanilla ? "minecraft:" : "extshape:") + "cobbled_deepslate_" + shape.getSerializedName());
      idMapBuilder.put("extshape:chiseled_stone_brick_" + shape.getSerializedName(), (vanilla ? "minecraft:" : "extshape:") + "stone_brick_" + shape.getSerializedName());
      idMapBuilder.put("extshape:polished_basalt_" + shape.getSerializedName(), "extshape:basalt_" + shape.getSerializedName());
    }

    final ImmutableMap<String, String> idMap = idMapBuilder.build();
    if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
      idMap.forEach((k, v) -> {
        Validate.validState(!k.contains("__"));
        Validate.validState(!v.contains("__"));
        Validate.validState(k.contains(":"));
        Validate.validState(v.contains(":"));
      });
      ExtShape.idMapToVerify = idMap;
    }
    final UnaryOperator<String> unaryOperator2 = createRenamer(idMap);
    builder.addFixer(BlockRenameFix.create(schema4173, "Rename some removed blocks of Extended Block Shapes mod", unaryOperator2));
    builder.addFixer(ItemRenameFix.create(schema4173, "Rename some removed items of Extended Block Shapes mod", unaryOperator2));
  }
}
