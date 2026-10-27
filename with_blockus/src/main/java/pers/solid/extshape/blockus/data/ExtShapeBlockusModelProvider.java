package pers.solid.extshape.blockus.data;

import com.brand.blockus.Blockus;
import com.brand.blockus.registry.content.BlockusBlocks;
import com.brand.blockus.utils.helper.BlockOrder;
import com.brand.blockus.utils.helper.WoodMaps;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.blockus.ExtShapeBlockusBlocks;
import pers.solid.extshape.data.ExtShapeModelProvider;

@Environment(EnvType.CLIENT)
public class ExtShapeBlockusModelProvider extends ExtShapeModelProvider {

  public ExtShapeBlockusModelProvider(FabricPackOutput output) {
    super(output);
  }

  @Override
  public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
    for (Block block : ExtShapeBlockusBlocks.BLOCKUS_BLOCKS) {
      if (block instanceof ExtShapeBlockInterface e) {
        e.registerModel(this, blockStateModelGenerator);
      } else {
        throw new IllegalStateException("Not provided model for block: " + block);
      }
    }
  }

  protected void registerTextureMaps() {
    register(BlockusBlocks.ROUGH_BASALT.block(), Identifier.withDefaultNamespace("block/basalt_top"));
    register(BlockusBlocks.ROUGH_SANDSTONE.block(), Identifier.withDefaultNamespace("block/sandstone_bottom"));
    register(BlockusBlocks.ROUGH_RED_SANDSTONE.block(), Identifier.withDefaultNamespace("block/red_sandstone_bottom"));
    register(BlockusBlocks.ROUGH_SOUL_SANDSTONE.block(), Identifier.fromNamespaceAndPath(Blockus.MOD_ID, "block/soul_sandstone_bottom"));

    // 切制灵魂砂岩
    textureMaps.put(BlockusBlocks.CUT_SOUL_SANDSTONE, TextureMapping.column(TextureMapping.getBlockTexture(BlockusBlocks.CUT_SOUL_SANDSTONE), TextureMapping.getBlockTexture(BlockusBlocks.SOUL_SANDSTONE.block(), "_top")));

    registerSuffixed(BlockusBlocks.STRIPPED_WHITE_OAK_LOG, TextureSlot.END, "_top");
    register(BlockusBlocks.STRIPPED_WHITE_OAK_WOOD, Identifier.fromNamespaceAndPath(Blockus.MOD_ID, "block/stripped_white_oak_log"));
    registerSuffixed(BlockusBlocks.WHITE_OAK_LOG, TextureSlot.END, "_top");
    register(BlockusBlocks.WHITE_OAK_WOOD, Identifier.fromNamespaceAndPath(Blockus.MOD_ID, "block/white_oak_log"));

    registerSuffixed(BlockusBlocks.SOUL_SANDSTONE.block(), TextureSlot.TOP, "_top");
    register(BlockusBlocks.SMOOTH_SOUL_SANDSTONE.block(), Identifier.fromNamespaceAndPath(Blockus.MOD_ID, "block/soul_sandstone_top"));

    for (DyeColor dyeColor : BlockOrder.COLOR) {
      final Block block = BlockusBlocks.GLAZED_TERRACOTTA_PILLAR.blocks().pick(dyeColor);
      registerSuffixed(block, TextureSlot.END, "_top");
      registerSuffixed(block, TextureSlot.SIDE, "_side");
      registerSuffixed(block, TextureSlot.WALL, "_side");
    }

    for (WoodMaps woodMaps : BlockOrder.WOOD) {
      final Block block = BlockusBlocks.SMALL_LOGS.bundle().get(woodMaps.getId());
      registerSuffixed(block, TextureSlot.END, "_top");
    }
  }

  private void registerSuffixed(Block block, TextureSlot textureKey, String suffix) {
    textureMaps.computeIfAbsent(block, TextureMapping::cube).put(textureKey, TextureMapping.getBlockTexture(block, suffix));
  }

  private void register(Block block, Identifier identifier) {
    textureMaps.computeIfAbsent(block, TextureMapping::cube).put(TextureSlot.ALL, new Material(identifier, false));
  }

  @Override
  public TextureMapping getTextureMap(Block baseBlock, BlockModelGenerators blockStateModelGenerator) {
    return textureMaps.computeIfAbsent(baseBlock, TextureMapping::cube);
  }
}
