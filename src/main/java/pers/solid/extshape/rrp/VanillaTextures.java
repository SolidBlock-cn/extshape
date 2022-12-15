package pers.solid.extshape.rrp;

import net.devtech.arrp.generator.TextureRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.model.TextureKey;
import net.minecraft.util.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import pers.solid.extshape.block.ExtShapeBlocks;

@OnlyIn(Dist.CLIENT)
public class VanillaTextures {
  @OnlyIn(Dist.CLIENT)
  public static void registerTextures() {
    TextureRegistry.register(Blocks.SMOOTH_QUARTZ, new Identifier("minecraft", "block/quartz_block_bottom"));
    TextureRegistry.register(Blocks.SMOOTH_RED_SANDSTONE, new Identifier("minecraft", "block/red_sandstone_top"));
    TextureRegistry.register(Blocks.SMOOTH_SANDSTONE, new Identifier("minecraft", "block/sandstone_top"));
    TextureRegistry.register(Blocks.SNOW_BLOCK, new Identifier("minecraft", "block/snow"));
    TextureRegistry.register(ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB, new Identifier("minecraft", "smooth_stone"));
    TextureRegistry.register(Blocks.QUARTZ_BLOCK, new Identifier("minecraft", "block/quartz_block_side"));
    TextureRegistry.register(Blocks.ANCIENT_DEBRIS, new Identifier("minecraft", "block/ancient_debris_side"));
    TextureRegistry.register(Blocks.MELON, new Identifier("minecraft", "block/melon_side"));
    TextureRegistry.register(Blocks.PUMPKIN, new Identifier("minecraft", "block/pumpkin_side"));
    TextureRegistry.register(Blocks.WAXED_COPPER_BLOCK, new Identifier("minecraft", "block/copper_block"));
    TextureRegistry.register(Blocks.WAXED_EXPOSED_COPPER, new Identifier("minecraft", "block/exposed_copper"));
    TextureRegistry.register(Blocks.WAXED_OXIDIZED_COPPER, new Identifier("minecraft", "block/oxidized_copper"));
    TextureRegistry.register(Blocks.WAXED_WEATHERED_COPPER, new Identifier("minecraft", "block/weathered_copper"));
    TextureRegistry.register(Blocks.WAXED_CUT_COPPER, new Identifier("minecraft", "block/cut_copper"));
    TextureRegistry.register(Blocks.WAXED_EXPOSED_CUT_COPPER, new Identifier("minecraft", "block/exposed_cut_copper"));
    TextureRegistry.register(Blocks.WAXED_OXIDIZED_CUT_COPPER, new Identifier("minecraft", "block/oxidized_cut_copper"));
    TextureRegistry.register(Blocks.WAXED_WEATHERED_CUT_COPPER, new Identifier("minecraft", "block/weathered_cut_copper"));

    TextureRegistry.register(Blocks.SANDSTONE, TextureKey.TOP, new Identifier("minecraft", "block/sandstone_top"));
    TextureRegistry.register(Blocks.SANDSTONE, TextureKey.BOTTOM, new Identifier("minecraft", "block/sandstone_bottom"));
    TextureRegistry.register(Blocks.RED_SANDSTONE, TextureKey.TOP, new Identifier("minecraft", "block/red_sandstone_top"));
    TextureRegistry.register(Blocks.RED_SANDSTONE, TextureKey.BOTTOM, new Identifier("minecraft", "block/red_sandstone_bottom"));
    TextureRegistry.register(ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB, new Identifier("minecraft", "smooth_stone_slab_side"));
    TextureRegistry.register(Blocks.QUARTZ_BLOCK, TextureKey.END, new Identifier("minecraft", "block/quartz_block_top"));
    TextureRegistry.register(Blocks.ANCIENT_DEBRIS, TextureKey.END, new Identifier("minecraft", "block/ancient_debris_top"));
    TextureRegistry.register(Blocks.MELON, TextureKey.END, new Identifier("minecraft", "block/melon_top"));
    TextureRegistry.register(Blocks.PUMPKIN, TextureKey.END, new Identifier("minecraft", "block/pumpkin_top"));

    // 原木系列
    TextureRegistry.register(Blocks.OAK_LOG, TextureKey.END, new Identifier("minecraft", "block/oak_log_top"));
    TextureRegistry.register(Blocks.SPRUCE_LOG, TextureKey.END, new Identifier("minecraft", "block/spruce_log_top"));
    TextureRegistry.register(Blocks.BIRCH_LOG, TextureKey.END, new Identifier("minecraft", "block/birch_log_top"));
    TextureRegistry.register(Blocks.JUNGLE_LOG, TextureKey.END, new Identifier("minecraft", "block/jungle_log_top"));
    TextureRegistry.register(Blocks.ACACIA_LOG, TextureKey.END, new Identifier("minecraft", "block/acacia_log_top"));
    TextureRegistry.register(Blocks.DARK_OAK_LOG, TextureKey.END, new Identifier("minecraft", "block/dark_oak_log_top"));
    TextureRegistry.register(Blocks.WARPED_STEM, TextureKey.END, new Identifier("minecraft", "block/warped_stem_top"));
    TextureRegistry.register(Blocks.CRIMSON_STEM, TextureKey.END, new Identifier("minecraft", "block/crimson_stem_top"));
    TextureRegistry.register(Blocks.OAK_WOOD, new Identifier("minecraft", "block/oak_log"));
    TextureRegistry.register(Blocks.SPRUCE_WOOD, new Identifier("minecraft", "block/spruce_log"));
    TextureRegistry.register(Blocks.BIRCH_WOOD, new Identifier("minecraft", "block/birch_log"));
    TextureRegistry.register(Blocks.JUNGLE_WOOD, new Identifier("minecraft", "block/jungle_log"));
    TextureRegistry.register(Blocks.ACACIA_WOOD, new Identifier("minecraft", "block/acacia_log"));
    TextureRegistry.register(Blocks.DARK_OAK_WOOD, new Identifier("minecraft", "block/dark_oak_log"));
    TextureRegistry.register(Blocks.WARPED_HYPHAE, new Identifier("minecraft", "block/warped_stem"));
    TextureRegistry.register(Blocks.CRIMSON_HYPHAE, new Identifier("minecraft", "block/crimson_stem"));
    TextureRegistry.register(Blocks.STRIPPED_OAK_LOG, TextureKey.END, new Identifier("minecraft", "block/stripped_oak_log_top"));
    TextureRegistry.register(Blocks.STRIPPED_SPRUCE_LOG, TextureKey.END, new Identifier("minecraft", "block/stripped_spruce_log_top"));
    TextureRegistry.register(Blocks.STRIPPED_BIRCH_LOG, TextureKey.END, new Identifier("minecraft", "block/stripped_birch_log_top"));
    TextureRegistry.register(Blocks.STRIPPED_JUNGLE_LOG, TextureKey.END, new Identifier("minecraft", "block/stripped_jungle_log_top"));
    TextureRegistry.register(Blocks.STRIPPED_ACACIA_LOG, TextureKey.END, new Identifier("minecraft", "block/stripped_acacia_log_top"));
    TextureRegistry.register(Blocks.STRIPPED_DARK_OAK_LOG, TextureKey.END, new Identifier("minecraft", "block/stripped_dark_oak_log_top"));
    TextureRegistry.register(Blocks.STRIPPED_WARPED_STEM, TextureKey.END, new Identifier("minecraft", "block/stripped_warped_stem_top"));
    TextureRegistry.register(Blocks.STRIPPED_CRIMSON_STEM, TextureKey.END, new Identifier("minecraft", "block/stripped_crimson_stem_top"));
    TextureRegistry.register(Blocks.STRIPPED_OAK_WOOD, new Identifier("minecraft", "block/stripped_oak_log"));
    TextureRegistry.register(Blocks.STRIPPED_SPRUCE_WOOD, new Identifier("minecraft", "block/stripped_spruce_log"));
    TextureRegistry.register(Blocks.STRIPPED_BIRCH_WOOD, new Identifier("minecraft", "block/stripped_birch_log"));
    TextureRegistry.register(Blocks.STRIPPED_JUNGLE_WOOD, new Identifier("minecraft", "block/stripped_jungle_log"));
    TextureRegistry.register(Blocks.STRIPPED_ACACIA_WOOD, new Identifier("minecraft", "block/stripped_acacia_log"));
    TextureRegistry.register(Blocks.STRIPPED_DARK_OAK_WOOD, new Identifier("minecraft", "block/stripped_dark_oak_log"));
    TextureRegistry.register(Blocks.STRIPPED_WARPED_HYPHAE, new Identifier("minecraft", "block/stripped_warped_stem"));
    TextureRegistry.register(Blocks.STRIPPED_CRIMSON_HYPHAE, new Identifier("minecraft", "block/stripped_crimson_stem"));

    TextureRegistry.registerAppended(Blocks.BASALT, TextureKey.ALL, "_side");
    TextureRegistry.registerAppended(Blocks.BASALT, TextureKey.END, "_top");
    TextureRegistry.registerAppended(Blocks.POLISHED_BASALT, TextureKey.ALL, "_side");
    TextureRegistry.registerAppended(Blocks.POLISHED_BASALT, TextureKey.END, "_top");
    TextureRegistry.registerAppended(Blocks.DEEPSLATE, TextureKey.END, "_top");
  }
}
