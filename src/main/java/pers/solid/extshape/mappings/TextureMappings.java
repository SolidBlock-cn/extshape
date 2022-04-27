package pers.solid.extshape.mappings;

import net.devtech.arrp.generator.TextureRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.model.TextureKey;
import net.minecraft.util.Identifier;
import pers.solid.extshape.block.ExtShapeBlocks;

public class TextureMappings {
  @Environment(EnvType.CLIENT)
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
  }
}
