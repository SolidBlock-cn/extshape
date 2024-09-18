package pers.solid.extshape.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.data.ExtShapeBlockStateModelGenerator;
import pers.solid.extshape.data.ExtShapeModelProvider;

public class ExtShapePillarUvLockedSlabBlock extends ExtShapePillarSlabBlock {
  public static final MapCodec<ExtShapePillarUvLockedSlabBlock> CODEC = ExtShapeBlockInterface.createCodecWithBaseBlock(createSettingsCodec(), ExtShapePillarUvLockedSlabBlock::new);

  public ExtShapePillarUvLockedSlabBlock(@NotNull Block baseBlock, Settings settings) {
    super(baseBlock, settings);
  }

  @Override
  public MapCodec<? extends ExtShapePillarUvLockedSlabBlock> getCodec() {
    return CODEC;
  }

  @Override
  public void registerModel(ExtShapeModelProvider modelProvider, BlockStateModelGenerator blockStateModelGenerator) {
    ExtShapeBlockStateModelGenerator.registerPillarUvLockedSlab(this, baseBlock, modelProvider.getTextureMap(baseBlock, blockStateModelGenerator), blockStateModelGenerator);
  }
}
