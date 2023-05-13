package pers.solid.extshape.builder;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.BlockSoundGroup;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.block.ExtShapePressurePlateBlock;
import pers.solid.extshape.util.BlockCollections;

public class PressurePlateBuilder extends AbstractBlockBuilder<PressurePlateBlock> {

  protected final PressurePlateBlock.ActivationRule type;
  protected final BlockSetType blockSetType;

  public PressurePlateBuilder(@NotNull PressurePlateBlock.ActivationRule type, Block baseBlock, @NotNull BlockSetType blockSetType) {
    super(baseBlock, FabricBlockSettings.copyOf(baseBlock).noCollision().strength(baseBlock.getHardness() / 4f), builder -> new ExtShapePressurePlateBlock(builder.baseBlock, ((PressurePlateBuilder) builder).type, builder.blockSettings, ((PressurePlateBuilder) builder).blockSetType));
    this.shape = BlockShape.PRESSURE_PLATE;
    this.type = type;
    this.blockSetType = blockSetType;
    final BlockSoundGroup soundGroup = baseBlock.getDefaultState().getSoundGroup();
    primaryTagToAddTo = soundGroup == BlockSoundGroup.STONE ? BlockTags.STONE_PRESSURE_PLATES : BlockCollections.VanillaMineable.AXE.contains(baseBlock) ? BlockTags.WOODEN_PRESSURE_PLATES : BlockTags.PRESSURE_PLATES;
  }

  @Override
  protected String getSuffix() {
    return "_pressure_plate";
  }

  @Override
  public AbstractBlockBuilder<PressurePlateBlock> withExtension(BlockExtension blockExtension) {
    return setInstanceSupplier(builder -> new ExtShapePressurePlateBlock.WithExtension(builder.baseBlock, ((PressurePlateBuilder) builder).type, builder.blockSettings, ((PressurePlateBuilder) builder).blockSetType, blockExtension));
  }
}
