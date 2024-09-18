package pers.solid.extshape.blockus.data;

import com.brand.blockus.Blockus;
import com.brand.blockus.content.BlockusBlocks;
import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.client.TextureMap;
import net.minecraft.util.Identifier;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.blockus.BlockusBlockCollections;
import pers.solid.extshape.blockus.ExtShapeBlockusBlocks;
import pers.solid.extshape.data.ExtShapeModelProvider;

import java.util.function.Supplier;

public class ExtShapeBlockusModelProvider extends ExtShapeModelProvider {

  public ExtShapeBlockusModelProvider(FabricDataOutput output) {
    super(output);
  }

  @Override
  public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
    for (Block block : ExtShapeBlockusBlocks.BLOCKUS_BLOCKS) {
      if (block instanceof ExtShapeBlockInterface e) {
        e.registerModel(this, blockStateModelGenerator);
      } else {
        throw new IllegalStateException("Not provided model for block: " + block);
      }
    }
  }

  protected void registerTextureMaps() {
    register(BlockusBlocks.ROUGH_BASALT.block, new Identifier("block/basalt_top"));
    register(BlockusBlocks.ROUGH_SANDSTONE.block, new Identifier("block/sandstone_bottom"));
    register(BlockusBlocks.ROUGH_RED_SANDSTONE.block, new Identifier("block/red_sandstone_bottom"));
    register(BlockusBlocks.ROUGH_SOUL_SANDSTONE.block, new Identifier(Blockus.MOD_ID, "block/soul_sandstone_bottom"));

    registerSuffixed(BlockusBlocks.STRIPPED_WHITE_OAK_LOG, TextureKey.END, "_top");
    register(BlockusBlocks.STRIPPED_WHITE_OAK_WOOD, new Identifier(Blockus.MOD_ID, "block/stripped_white_oak_log"));
    registerSuffixed(BlockusBlocks.WHITE_OAK_LOG, TextureKey.END, "_top");
    register(BlockusBlocks.WHITE_OAK_WOOD, new Identifier(Blockus.MOD_ID, "block/white_oak_log"));

    registerSuffixed(BlockusBlocks.SOUL_SANDSTONE.block, TextureKey.TOP, "_top");
    register(BlockusBlocks.SMOOTH_SOUL_SANDSTONE.block, new Identifier(Blockus.MOD_ID, "block/soul_sandstone_top"));

    for (var block : BlockusBlockCollections.GLAZED_TERRACOTTA_PILLARS) {
      registerSuffixed(block, TextureKey.END, "_top");
    }

    for (final Supplier<Block> supplier : ImmutableList.<Supplier<Block>>of(
        () -> BlockusBlocks.OAK_SMALL_LOGS,
        () -> BlockusBlocks.SPRUCE_SMALL_LOGS,
        () -> BlockusBlocks.BIRCH_SMALL_LOGS,
        () -> BlockusBlocks.JUNGLE_SMALL_LOGS,
        () -> BlockusBlocks.ACACIA_SMALL_LOGS,
        () -> BlockusBlocks.DARK_OAK_SMALL_LOGS,
        () -> BlockusBlocks.MANGROVE_SMALL_LOGS,
        () -> BlockusBlocks.WARPED_SMALL_STEMS,
        () -> BlockusBlocks.CRIMSON_SMALL_STEMS,
        () -> BlockusBlocks.WHITE_OAK_SMALL_LOGS
    )) {
      Block block = supplier.get();
      registerSuffixed(block, TextureKey.END, "_top");
    }
  }

  private void registerSuffixed(Block block, TextureKey textureKey, String suffix) {
    textureMaps.computeIfAbsent(block, TextureMap::all).put(textureKey, TextureMap.getSubId(block, suffix));
  }

  private void register(Block block, Identifier identifier) {
    textureMaps.computeIfAbsent(block, TextureMap::all).put(TextureKey.ALL, identifier);
  }

  @Override
  public TextureMap getTextureMap(Block baseBlock, BlockStateModelGenerator blockStateModelGenerator) {
    return textureMaps.computeIfAbsent(baseBlock, TextureMap::all);
  }
}
