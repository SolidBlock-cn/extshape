package pers.solid.extshape.data;

import com.google.common.collect.Iterables;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.item.HoneycombItem;
import net.minecraft.util.Identifier;
import pers.solid.brrp.v1.mixin.TextureMapAccessor;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.mixin.BlockStateModelGeneratorAccessor;
import pers.solid.extshape.mixin.BlockTexturePoolAccessor;
import pers.solid.extshape.util.BlockCollections;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExtShapeModelProvider extends FabricModelProvider {
  protected final Map<Block, TextureMap> textureMaps = new HashMap<>();
  protected final Map<Block, BlockStateModelGenerator.BlockTexturePool> poolMap = new HashMap<>();

  public ExtShapeModelProvider(FabricDataOutput output) {
    super(output);
    registerTextureMaps();
  }

  protected void registerTextureMaps() {
    final Iterator<Block> woods = Iterables.concat(BlockCollections.WOODS, BlockCollections.STRIPPED_WOODS, BlockCollections.HYPHAES, BlockCollections.STRIPPED_HYPHAES).iterator();
    final Iterator<Block> logs = Iterables.concat(BlockCollections.LOGS, BlockCollections.STRIPPED_LOGS, BlockCollections.STEMS, BlockCollections.STRIPPED_STEMS).iterator();
    while (woods.hasNext()) {
      final Block wood = woods.next();
      final Block log = logs.next();
      textureMaps.put(wood, TextureMap.all(log));
      textureMaps.put(log, TextureMap.sideAndEndForTop(log));
    }
    textureMaps.put(Blocks.BAMBOO_BLOCK, TextureMap.sideAndEndForTop(Blocks.BAMBOO_BLOCK));
    textureMaps.put(Blocks.STRIPPED_BAMBOO_BLOCK, TextureMap.sideAndEndForTop(Blocks.STRIPPED_BAMBOO_BLOCK));

    // cube column blocks
    for (Block block : Arrays.asList(
        Blocks.ANCIENT_DEBRIS,
        Blocks.MELON,
        Blocks.BASALT,
        Blocks.POLISHED_BASALT,
        Blocks.BONE_BLOCK,
        Blocks.PURPUR_PILLAR,
        Blocks.QUARTZ_PILLAR,
        Blocks.OCHRE_FROGLIGHT,
        Blocks.VERDANT_FROGLIGHT,
        Blocks.PEARLESCENT_FROGLIGHT,
        Blocks.PUMPKIN
    )) {
      textureMaps.put(block, TextureMap.sideEnd(block));
    }

    textureMaps.put(Blocks.SNOW_BLOCK, TextureMap.all(Blocks.SNOW));
  }

  @Override
  public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
    final ObjectSet<Block> blocks = ExtShapeBlocks.getBlocks();

    for (Block block : blocks) {
      if (block instanceof ExtShapeBlockInterface e) {
        e.registerModel(this, blockStateModelGenerator);
      } else if (block == ExtShapeBlocks.PETRIFIED_OAK_PLANKS) {
        blockStateModelGenerator.registerStateWithModelReference(block, Blocks.OAK_PLANKS);
        blockStateModelGenerator.registerParentedItemModel(block, ModelIds.getBlockModelId(Blocks.OAK_PLANKS));
      } else if (block == ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB) {
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, ModelIds.getBlockSubModelId(Blocks.SMOOTH_STONE_SLAB, "_double")));
        blockStateModelGenerator.registerParentedItemModel(block, ModelIds.getBlockSubModelId(Blocks.SMOOTH_STONE_SLAB, "_double"));
      } else {
        throw new IllegalStateException("Not provided model for block: " + block);
      }
    }
  }

  public TextureMap getTextureMap(Block baseBlock, BlockStateModelGenerator blockStateModelGenerator) {
    if (textureMaps.containsKey(baseBlock)) {
      return textureMaps.get(baseBlock);
    }
    final Map<Block, TexturedModel> texturedModels = ((BlockStateModelGeneratorAccessor) blockStateModelGenerator).getTexturedModels();
    final TexturedModel texturedModel = texturedModels.containsKey(baseBlock) ? texturedModels.get(baseBlock) : TexturedModel.CUBE_ALL.get(baseBlock);
    final TextureMap textures = texturedModel.getTextures();
    final Map<TextureKey, Identifier> entries = ((TextureMapAccessor) textures).getEntries();
    if (entries.containsKey(TextureKey.SIDE) && !entries.containsKey(TextureKey.ALL)) {
      textures.put(TextureKey.ALL, textures.getTexture(TextureKey.SIDE));
    }
    return textures;
  }

  public BlockStateModelGenerator.BlockTexturePool getBlockTexturePool(Block baseBlock, BlockStateModelGenerator blockStateModelGenerator) {
    if (HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get().containsKey(baseBlock)) {
      baseBlock = HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get().get(baseBlock);
    }
    return poolMap.computeIfAbsent(baseBlock, block -> {
      final BlockStateModelGenerator.BlockTexturePool blockTexturePool = blockStateModelGenerator.new BlockTexturePool(getTextureMap(block, blockStateModelGenerator));
      ((BlockTexturePoolAccessor) blockTexturePool).setBaseModelId(ModelIds.getBlockModelId(block));
      final TextureMap textures = getTextureMap(block, blockStateModelGenerator);
      try {
        textures.getTexture(TextureKey.ALL);
      } catch (IllegalStateException e) {
        textures.put(TextureKey.ALL, textures.getTexture(TextureKey.SIDE));
      }
      return blockTexturePool;
    });
  }

  @Override
  public void generateItemModels(ItemModelGenerator itemModelGenerator) {
  }
}
