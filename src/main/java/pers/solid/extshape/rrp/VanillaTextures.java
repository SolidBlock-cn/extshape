package pers.solid.extshape.rrp;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.TextureKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import pers.solid.brrp.v1.generator.TextureRegistry;
import pers.solid.extshape.block.ExtShapeBlocks;

@Environment(EnvType.CLIENT)
@ApiStatus.Internal
public class VanillaTextures {
  @Environment(EnvType.CLIENT)
  public static void registerTextures() {
    TextureRegistry.register(Blocks.SMOOTH_QUARTZ, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/quartz_block_bottom"));
    TextureRegistry.register(Blocks.SMOOTH_RED_SANDSTONE, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/red_sandstone_top"));
    TextureRegistry.register(Blocks.SMOOTH_SANDSTONE, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/sandstone_top"));
    TextureRegistry.register(Blocks.SNOW_BLOCK, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/snow"));
    TextureRegistry.register(ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB, new Identifier(Identifier.DEFAULT_NAMESPACE, "smooth_stone"));
    TextureRegistry.register(Blocks.QUARTZ_BLOCK, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/quartz_block_side"));
    TextureRegistry.register(Blocks.ANCIENT_DEBRIS, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/ancient_debris_side"));
    TextureRegistry.register(Blocks.MELON, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/melon_side"));
    TextureRegistry.register(Blocks.PUMPKIN, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/pumpkin_side"));
    TextureRegistry.register(Blocks.WAXED_COPPER_BLOCK, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/copper_block"));
    TextureRegistry.register(Blocks.WAXED_EXPOSED_COPPER, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/exposed_copper"));
    TextureRegistry.register(Blocks.WAXED_OXIDIZED_COPPER, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/oxidized_copper"));
    TextureRegistry.register(Blocks.WAXED_WEATHERED_COPPER, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/weathered_copper"));
    TextureRegistry.register(Blocks.WAXED_CUT_COPPER, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/cut_copper"));
    TextureRegistry.register(Blocks.WAXED_EXPOSED_CUT_COPPER, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/exposed_cut_copper"));
    TextureRegistry.register(Blocks.WAXED_OXIDIZED_CUT_COPPER, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/oxidized_cut_copper"));
    TextureRegistry.register(Blocks.WAXED_WEATHERED_CUT_COPPER, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/weathered_cut_copper"));

    TextureRegistry.register(Blocks.SANDSTONE, TextureKey.TOP, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/sandstone_top"));
    TextureRegistry.register(Blocks.SANDSTONE, TextureKey.BOTTOM, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/sandstone_bottom"));
    TextureRegistry.register(Blocks.RED_SANDSTONE, TextureKey.TOP, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/red_sandstone_top"));
    TextureRegistry.register(Blocks.RED_SANDSTONE, TextureKey.BOTTOM, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/red_sandstone_bottom"));
    TextureRegistry.register(ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB, new Identifier(Identifier.DEFAULT_NAMESPACE, "smooth_stone_slab_side"));
    TextureRegistry.register(Blocks.QUARTZ_BLOCK, TextureKey.END, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/quartz_block_top"));
    TextureRegistry.register(Blocks.ANCIENT_DEBRIS, TextureKey.END, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/ancient_debris_top"));
    TextureRegistry.register(Blocks.MELON, TextureKey.END, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/melon_top"));
    TextureRegistry.register(Blocks.PUMPKIN, TextureKey.END, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/pumpkin_top"));

    // 原木系列
    TextureRegistry.register(Blocks.OAK_LOG, TextureKey.END, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/oak_log_top"));
    TextureRegistry.register(Blocks.SPRUCE_LOG, TextureKey.END, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/spruce_log_top"));
    TextureRegistry.register(Blocks.BIRCH_LOG, TextureKey.END, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/birch_log_top"));
    TextureRegistry.register(Blocks.JUNGLE_LOG, TextureKey.END, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/jungle_log_top"));
    TextureRegistry.register(Blocks.ACACIA_LOG, TextureKey.END, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/acacia_log_top"));
    TextureRegistry.register(Blocks.DARK_OAK_LOG, TextureKey.END, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/dark_oak_log_top"));
    TextureRegistry.register(Blocks.MANGROVE_LOG, TextureKey.END, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/mangrove_log_top"));
    TextureRegistry.register(Blocks.WARPED_STEM, TextureKey.END, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/warped_stem_top"));
    TextureRegistry.register(Blocks.CRIMSON_STEM, TextureKey.END, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/crimson_stem_top"));
    TextureRegistry.register(Blocks.OAK_WOOD, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/oak_log"));
    TextureRegistry.register(Blocks.SPRUCE_WOOD, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/spruce_log"));
    TextureRegistry.register(Blocks.BIRCH_WOOD, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/birch_log"));
    TextureRegistry.register(Blocks.JUNGLE_WOOD, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/jungle_log"));
    TextureRegistry.register(Blocks.ACACIA_WOOD, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/acacia_log"));
    TextureRegistry.register(Blocks.DARK_OAK_WOOD, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/dark_oak_log"));
    TextureRegistry.register(Blocks.MANGROVE_WOOD, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/mangrove_log"));
    TextureRegistry.register(Blocks.WARPED_HYPHAE, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/warped_stem"));
    TextureRegistry.register(Blocks.CRIMSON_HYPHAE, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/crimson_stem"));
    TextureRegistry.register(Blocks.STRIPPED_OAK_LOG, TextureKey.END, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/stripped_oak_log_top"));
    TextureRegistry.register(Blocks.STRIPPED_SPRUCE_LOG, TextureKey.END, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/stripped_spruce_log_top"));
    TextureRegistry.register(Blocks.STRIPPED_BIRCH_LOG, TextureKey.END, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/stripped_birch_log_top"));
    TextureRegistry.register(Blocks.STRIPPED_JUNGLE_LOG, TextureKey.END, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/stripped_jungle_log_top"));
    TextureRegistry.register(Blocks.STRIPPED_ACACIA_LOG, TextureKey.END, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/stripped_acacia_log_top"));
    TextureRegistry.register(Blocks.STRIPPED_DARK_OAK_LOG, TextureKey.END, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/stripped_dark_oak_log_top"));
    TextureRegistry.register(Blocks.STRIPPED_MANGROVE_LOG, TextureKey.END, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/stripped_mangrove_log_top"));
    TextureRegistry.register(Blocks.STRIPPED_WARPED_STEM, TextureKey.END, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/stripped_warped_stem_top"));
    TextureRegistry.register(Blocks.STRIPPED_CRIMSON_STEM, TextureKey.END, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/stripped_crimson_stem_top"));
    TextureRegistry.register(Blocks.STRIPPED_OAK_WOOD, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/stripped_oak_log"));
    TextureRegistry.register(Blocks.STRIPPED_SPRUCE_WOOD, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/stripped_spruce_log"));
    TextureRegistry.register(Blocks.STRIPPED_BIRCH_WOOD, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/stripped_birch_log"));
    TextureRegistry.register(Blocks.STRIPPED_JUNGLE_WOOD, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/stripped_jungle_log"));
    TextureRegistry.register(Blocks.STRIPPED_ACACIA_WOOD, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/stripped_acacia_log"));
    TextureRegistry.register(Blocks.STRIPPED_DARK_OAK_WOOD, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/stripped_dark_oak_log"));
    TextureRegistry.register(Blocks.STRIPPED_MANGROVE_WOOD, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/stripped_mangrove_log"));
    TextureRegistry.register(Blocks.STRIPPED_WARPED_HYPHAE, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/stripped_warped_stem"));
    TextureRegistry.register(Blocks.STRIPPED_CRIMSON_HYPHAE, new Identifier(Identifier.DEFAULT_NAMESPACE, "block/stripped_crimson_stem"));

    TextureRegistry.registerSuffixed(Blocks.OCHRE_FROGLIGHT, TextureKey.ALL, "_side");
    TextureRegistry.registerSuffixed(Blocks.OCHRE_FROGLIGHT, TextureKey.END, "_top");
    TextureRegistry.registerSuffixed(Blocks.VERDANT_FROGLIGHT, TextureKey.ALL, "_side");
    TextureRegistry.registerSuffixed(Blocks.VERDANT_FROGLIGHT, TextureKey.END, "_top");
    TextureRegistry.registerSuffixed(Blocks.PEARLESCENT_FROGLIGHT, TextureKey.ALL, "_side");
    TextureRegistry.registerSuffixed(Blocks.PEARLESCENT_FROGLIGHT, TextureKey.END, "_top");

    TextureRegistry.registerSuffixed(Blocks.BASALT, TextureKey.ALL, "_side");
    TextureRegistry.registerSuffixed(Blocks.BASALT, TextureKey.END, "_top");
    TextureRegistry.registerSuffixed(Blocks.POLISHED_BASALT, TextureKey.ALL, "_side");
    TextureRegistry.registerSuffixed(Blocks.POLISHED_BASALT, TextureKey.END, "_top");
    TextureRegistry.registerSuffixed(Blocks.BLACKSTONE, TextureKey.END, "_top");
    TextureRegistry.registerSuffixed(Blocks.DEEPSLATE, TextureKey.END, "_top");

    TextureRegistry.registerSuffixed(Blocks.BAMBOO_BLOCK, TextureKey.END, "_top");
    TextureRegistry.registerSuffixed(Blocks.STRIPPED_BAMBOO_BLOCK, TextureKey.END, "_top");
  }
}
