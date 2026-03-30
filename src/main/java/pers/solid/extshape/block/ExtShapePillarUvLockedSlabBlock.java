package pers.solid.extshape.block;

import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.world.level.block.Block;
import pers.solid.extshape.data.ExtShapeBlockStateModelGenerator;
import pers.solid.extshape.data.ExtShapeModelProvider;

public class ExtShapePillarUvLockedSlabBlock extends ExtShapePillarSlabBlock {
  public static final MapCodec<ExtShapePillarUvLockedSlabBlock> CODEC = ExtShapeBlockInterface.createCodecWithBaseBlock(propertiesCodec(), ExtShapePillarUvLockedSlabBlock::new);

  public ExtShapePillarUvLockedSlabBlock(Block baseBlock, Properties settings) {
    super(baseBlock, settings);
  }

  @Override
  public MapCodec<? extends ExtShapePillarUvLockedSlabBlock> codec() {
    return CODEC;
  }

  @Environment(EnvType.CLIENT)
  @Override
  public void registerModel(ExtShapeModelProvider modelProvider, BlockModelGenerators blockStateModelGenerator) {
    ExtShapeBlockStateModelGenerator.registerPillarUvLockedSlab(this, baseBlock, modelProvider.getTextureMap(baseBlock, blockStateModelGenerator), blockStateModelGenerator);
  }
}
