package pers.solid.extshape.data;

import com.google.common.collect.Iterables;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.mixin.BlockStateModelGeneratorAccessor;
import pers.solid.extshape.mixin.BlockTexturePoolAccessor;
import pers.solid.extshape.mixin.TextureMapAccessor;
import pers.solid.extshape.util.BlockCollections;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class ExtShapeModelProvider extends FabricModelProvider {
  protected final Map<Block, TextureMapping> textureMaps = new HashMap<>();
  protected final Map<Block, BlockModelGenerators.BlockFamilyProvider> poolMap = new HashMap<>();

  public ExtShapeModelProvider(FabricPackOutput output) {
    super(output);
    registerTextureMaps();
  }

  protected void registerTextureMaps() {
    final Iterator<Block> woods = Iterables.concat(BlockCollections.WOODS, BlockCollections.STRIPPED_WOODS, BlockCollections.HYPHAES, BlockCollections.STRIPPED_HYPHAES).iterator();
    final Iterator<Block> logs = Iterables.concat(BlockCollections.LOGS, BlockCollections.STRIPPED_LOGS, BlockCollections.STEMS, BlockCollections.STRIPPED_STEMS).iterator();
    while (woods.hasNext()) {
      final Block wood = woods.next();
      final Block log = logs.next();
      textureMaps.put(wood, TextureMapping.cube(log));
      textureMaps.put(log, TextureMapping.logColumn(log));
    }
    textureMaps.put(Blocks.BAMBOO_BLOCK, TextureMapping.logColumn(Blocks.BAMBOO_BLOCK));
    textureMaps.put(Blocks.STRIPPED_BAMBOO_BLOCK, TextureMapping.logColumn(Blocks.STRIPPED_BAMBOO_BLOCK));

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
      textureMaps.put(block, TextureMapping.column(block));
    }

    textureMaps.put(Blocks.SNOW_BLOCK, TextureMapping.cube(Blocks.SNOW));
  }

  @Override
  public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
    final ObjectSet<Block> blocks = ExtShapeBlocks.getBlocks();

    for (Block block : blocks) {
      if (block instanceof ExtShapeBlockInterface e) {
        e.registerModel(this, blockStateModelGenerator);
      } else if (block == ExtShapeBlocks.PETRIFIED_OAK_PLANKS) {
        blockStateModelGenerator.createNonTemplateModelBlock(block, Blocks.OAK_PLANKS);
        blockStateModelGenerator.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(Blocks.OAK_PLANKS));
      } else if (block == ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB) {
        blockStateModelGenerator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(Blocks.SMOOTH_STONE_SLAB, "_double"))));
        blockStateModelGenerator.registerSimpleItemModel(block, ModelLocationUtils.getModelLocation(Blocks.SMOOTH_STONE_SLAB, "_double"));
      } else {
        throw new IllegalStateException("Not provided model for block: " + block);
      }
    }
  }

  public TextureMapping getTextureMap(Block baseBlock, BlockModelGenerators blockStateModelGenerator) {
    if (textureMaps.containsKey(baseBlock)) {
      return textureMaps.get(baseBlock);
    }
    final Map<Block, TexturedModel> texturedModels = BlockStateModelGeneratorAccessor.getTEXTURED_MODELS();
    final TexturedModel texturedModel = texturedModels.containsKey(baseBlock) ? texturedModels.get(baseBlock) : TexturedModel.CUBE.get(baseBlock);
    final TextureMapping textures = texturedModel.getMapping();
    final Map<TextureSlot, Identifier> entries = ((TextureMapAccessor) textures).getSlots();
    if (entries.containsKey(TextureSlot.SIDE) && !entries.containsKey(TextureSlot.ALL)) {
      textures.put(TextureSlot.ALL, textures.get(TextureSlot.SIDE));
    }
    return textures;
  }

  public BlockModelGenerators.BlockFamilyProvider getBlockTexturePool(Block baseBlock, BlockModelGenerators blockStateModelGenerator) {
    if (HoneycombItem.WAX_OFF_BY_BLOCK.get().containsKey(baseBlock)) {
      baseBlock = HoneycombItem.WAX_OFF_BY_BLOCK.get().get(baseBlock);
    }
    return poolMap.computeIfAbsent(baseBlock, block -> {
      final BlockModelGenerators.BlockFamilyProvider blockTexturePool = blockStateModelGenerator.new BlockFamilyProvider(getTextureMap(block, blockStateModelGenerator));
      ((BlockTexturePoolAccessor) blockTexturePool).setFullBlock(BlockModelGenerators.plainModel(ModelLocationUtils.getModelLocation(block)));
      final TextureMapping textures = getTextureMap(block, blockStateModelGenerator);
      try {
        textures.get(TextureSlot.ALL);
      } catch (IllegalStateException e) {
        textures.put(TextureSlot.ALL, textures.get(TextureSlot.SIDE));
      }
      return blockTexturePool;
    });
  }

  @Override
  public void generateItemModels(ItemModelGenerators itemModelGenerator) {
  }
}
