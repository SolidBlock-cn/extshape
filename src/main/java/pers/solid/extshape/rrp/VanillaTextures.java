package pers.solid.extshape.rrp;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.TextureKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import pers.solid.brrp.v1.generator.TextureRegistry;

@Environment(EnvType.CLIENT)
@ApiStatus.Internal
public class VanillaTextures {
  @Environment(EnvType.CLIENT)
  public static void registerTextures() {
    TextureRegistry.register(Blocks.QUARTZ_BLOCK, TextureKey.END, Identifier.ofVanilla("block/quartz_block_top"));
    TextureRegistry.register(Blocks.QUARTZ_BLOCK, Identifier.ofVanilla("block/quartz_block_side"));
    TextureRegistry.register(Blocks.SMOOTH_QUARTZ, Identifier.ofVanilla("block/quartz_block_bottom"));
    TextureRegistry.registerSuffixed(Blocks.CHISELED_QUARTZ_BLOCK, TextureKey.END, "_top");

    TextureRegistry.register(Blocks.SNOW_BLOCK, Identifier.ofVanilla("block/snow"));
    TextureRegistry.registerSuffixed(Blocks.ANCIENT_DEBRIS, TextureKey.ALL, "_side");
    TextureRegistry.registerSuffixed(Blocks.ANCIENT_DEBRIS, TextureKey.END, "_top");
    TextureRegistry.registerSuffixed(Blocks.MELON, TextureKey.ALL, "_side");
    TextureRegistry.registerSuffixed(Blocks.MELON, TextureKey.END, "_top");
    TextureRegistry.registerSuffixed(Blocks.PUMPKIN, TextureKey.ALL, "_side");
    TextureRegistry.registerSuffixed(Blocks.PUMPKIN, TextureKey.END, "_top");

    TextureRegistry.register(Blocks.WAXED_COPPER_BLOCK, Identifier.ofVanilla("block/copper_block"));
    TextureRegistry.register(Blocks.WAXED_EXPOSED_COPPER, Identifier.ofVanilla("block/exposed_copper"));
    TextureRegistry.register(Blocks.WAXED_OXIDIZED_COPPER, Identifier.ofVanilla("block/oxidized_copper"));
    TextureRegistry.register(Blocks.WAXED_WEATHERED_COPPER, Identifier.ofVanilla("block/weathered_copper"));
    TextureRegistry.register(Blocks.WAXED_CUT_COPPER, Identifier.ofVanilla("block/cut_copper"));
    TextureRegistry.register(Blocks.WAXED_EXPOSED_CUT_COPPER, Identifier.ofVanilla("block/exposed_cut_copper"));
    TextureRegistry.register(Blocks.WAXED_OXIDIZED_CUT_COPPER, Identifier.ofVanilla("block/oxidized_cut_copper"));
    TextureRegistry.register(Blocks.WAXED_WEATHERED_CUT_COPPER, Identifier.ofVanilla("block/weathered_cut_copper"));

    TextureRegistry.register(Blocks.SANDSTONE, TextureKey.TOP, Identifier.ofVanilla("block/sandstone_top"));
    TextureRegistry.register(Blocks.SANDSTONE, TextureKey.BOTTOM, Identifier.ofVanilla("block/sandstone_bottom"));
    TextureRegistry.register(Blocks.CHISELED_SANDSTONE, TextureKey.END, Identifier.ofVanilla("block/sandstone_top"));
    TextureRegistry.register(Blocks.CUT_SANDSTONE, TextureKey.END, Identifier.ofVanilla("block/sandstone_top"));
    TextureRegistry.register(Blocks.SMOOTH_SANDSTONE, Identifier.ofVanilla("block/sandstone_top"));

    TextureRegistry.register(Blocks.RED_SANDSTONE, TextureKey.TOP, Identifier.ofVanilla("block/red_sandstone_top"));
    TextureRegistry.register(Blocks.RED_SANDSTONE, TextureKey.BOTTOM, Identifier.ofVanilla("block/red_sandstone_bottom"));
    TextureRegistry.register(Blocks.CHISELED_RED_SANDSTONE, TextureKey.END, Identifier.ofVanilla("block/red_sandstone_top"));
    TextureRegistry.register(Blocks.CUT_RED_SANDSTONE, TextureKey.END, Identifier.ofVanilla("block/red_sandstone_top"));
    TextureRegistry.register(Blocks.SMOOTH_RED_SANDSTONE, Identifier.ofVanilla("block/red_sandstone_top"));

    TextureRegistry.register(Blocks.OAK_LOG, TextureKey.END, Identifier.ofVanilla("block/oak_log_top"));
    TextureRegistry.register(Blocks.SPRUCE_LOG, TextureKey.END, Identifier.ofVanilla("block/spruce_log_top"));
    TextureRegistry.register(Blocks.BIRCH_LOG, TextureKey.END, Identifier.ofVanilla("block/birch_log_top"));
    TextureRegistry.register(Blocks.JUNGLE_LOG, TextureKey.END, Identifier.ofVanilla("block/jungle_log_top"));
    TextureRegistry.register(Blocks.ACACIA_LOG, TextureKey.END, Identifier.ofVanilla("block/acacia_log_top"));
    TextureRegistry.register(Blocks.CHERRY_LOG, TextureKey.END, Identifier.ofVanilla("block/cherry_log_top"));
    TextureRegistry.register(Blocks.DARK_OAK_LOG, TextureKey.END, Identifier.ofVanilla("block/dark_oak_log_top"));
    TextureRegistry.register(Blocks.MANGROVE_LOG, TextureKey.END, Identifier.ofVanilla("block/mangrove_log_top"));
    TextureRegistry.register(Blocks.WARPED_STEM, TextureKey.END, Identifier.ofVanilla("block/warped_stem_top"));
    TextureRegistry.register(Blocks.CRIMSON_STEM, TextureKey.END, Identifier.ofVanilla("block/crimson_stem_top"));
    TextureRegistry.register(Blocks.OAK_WOOD, Identifier.ofVanilla("block/oak_log"));
    TextureRegistry.register(Blocks.SPRUCE_WOOD, Identifier.ofVanilla("block/spruce_log"));
    TextureRegistry.register(Blocks.BIRCH_WOOD, Identifier.ofVanilla("block/birch_log"));
    TextureRegistry.register(Blocks.JUNGLE_WOOD, Identifier.ofVanilla("block/jungle_log"));
    TextureRegistry.register(Blocks.ACACIA_WOOD, Identifier.ofVanilla("block/acacia_log"));
    TextureRegistry.register(Blocks.CHERRY_WOOD, Identifier.ofVanilla("block/cherry_log"));
    TextureRegistry.register(Blocks.DARK_OAK_WOOD, Identifier.ofVanilla("block/dark_oak_log"));
    TextureRegistry.register(Blocks.MANGROVE_WOOD, Identifier.ofVanilla("block/mangrove_log"));
    TextureRegistry.register(Blocks.WARPED_HYPHAE, Identifier.ofVanilla("block/warped_stem"));
    TextureRegistry.register(Blocks.CRIMSON_HYPHAE, Identifier.ofVanilla("block/crimson_stem"));
    TextureRegistry.register(Blocks.STRIPPED_OAK_LOG, TextureKey.END, Identifier.ofVanilla("block/stripped_oak_log_top"));
    TextureRegistry.register(Blocks.STRIPPED_SPRUCE_LOG, TextureKey.END, Identifier.ofVanilla("block/stripped_spruce_log_top"));
    TextureRegistry.register(Blocks.STRIPPED_BIRCH_LOG, TextureKey.END, Identifier.ofVanilla("block/stripped_birch_log_top"));
    TextureRegistry.register(Blocks.STRIPPED_JUNGLE_LOG, TextureKey.END, Identifier.ofVanilla("block/stripped_jungle_log_top"));
    TextureRegistry.register(Blocks.STRIPPED_ACACIA_LOG, TextureKey.END, Identifier.ofVanilla("block/stripped_acacia_log_top"));
    TextureRegistry.register(Blocks.STRIPPED_CHERRY_LOG, TextureKey.END, Identifier.ofVanilla("block/stripped_cherry_log_top"));
    TextureRegistry.register(Blocks.STRIPPED_DARK_OAK_LOG, TextureKey.END, Identifier.ofVanilla("block/stripped_dark_oak_log_top"));
    TextureRegistry.register(Blocks.STRIPPED_MANGROVE_LOG, TextureKey.END, Identifier.ofVanilla("block/stripped_mangrove_log_top"));
    TextureRegistry.register(Blocks.STRIPPED_WARPED_STEM, TextureKey.END, Identifier.ofVanilla("block/stripped_warped_stem_top"));
    TextureRegistry.register(Blocks.STRIPPED_CRIMSON_STEM, TextureKey.END, Identifier.ofVanilla("block/stripped_crimson_stem_top"));
    TextureRegistry.register(Blocks.STRIPPED_OAK_WOOD, Identifier.ofVanilla("block/stripped_oak_log"));
    TextureRegistry.register(Blocks.STRIPPED_SPRUCE_WOOD, Identifier.ofVanilla("block/stripped_spruce_log"));
    TextureRegistry.register(Blocks.STRIPPED_BIRCH_WOOD, Identifier.ofVanilla("block/stripped_birch_log"));
    TextureRegistry.register(Blocks.STRIPPED_JUNGLE_WOOD, Identifier.ofVanilla("block/stripped_jungle_log"));
    TextureRegistry.register(Blocks.STRIPPED_ACACIA_WOOD, Identifier.ofVanilla("block/stripped_acacia_log"));
    TextureRegistry.register(Blocks.STRIPPED_CHERRY_WOOD, Identifier.ofVanilla("block/stripped_cherry_log"));
    TextureRegistry.register(Blocks.STRIPPED_DARK_OAK_WOOD, Identifier.ofVanilla("block/stripped_dark_oak_log"));
    TextureRegistry.register(Blocks.STRIPPED_MANGROVE_WOOD, Identifier.ofVanilla("block/stripped_mangrove_log"));
    TextureRegistry.register(Blocks.STRIPPED_WARPED_HYPHAE, Identifier.ofVanilla("block/stripped_warped_stem"));
    TextureRegistry.register(Blocks.STRIPPED_CRIMSON_HYPHAE, Identifier.ofVanilla("block/stripped_crimson_stem"));

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

    TextureRegistry.registerSuffixed(Blocks.CHISELED_TUFF, TextureKey.END, "_top");
    TextureRegistry.registerSuffixed(Blocks.CHISELED_TUFF_BRICKS, TextureKey.END, "_top");
  }
}
