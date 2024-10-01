package pers.solid.extshape.block;

import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.data.ExtShapeBlockStateModelGenerator;
import pers.solid.extshape.data.ExtShapeModelProvider;

public class ExtShapePillarUvLockedSlabBlock extends ExtShapePillarSlabBlock {
  public ExtShapePillarUvLockedSlabBlock(@NotNull Block baseBlock, Settings settings) {
    super(baseBlock, settings);
  }

  @Override
  public void registerModel(ExtShapeModelProvider modelProvider, BlockStateModelGenerator blockStateModelGenerator) {
    ExtShapeBlockStateModelGenerator.registerPillarUvLockedSlab(this, baseBlock, modelProvider.getTextureMap(baseBlock, blockStateModelGenerator), blockStateModelGenerator);
  }
}
