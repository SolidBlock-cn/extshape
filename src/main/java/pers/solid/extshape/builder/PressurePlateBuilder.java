package pers.solid.extshape.builder;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.item.ItemGroup;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.ExtShapePressurePlateBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTags;

public class PressurePlateBuilder extends AbstractBlockBuilder<PressurePlateBlock> {

  public PressurePlateBlock.ActivationRule type;

  public PressurePlateBuilder(@NotNull PressurePlateBlock.ActivationRule type, Block baseBlock) {
    super(baseBlock, FabricBlockSettings.copyOf(baseBlock).noCollision().strength(baseBlock.getHardness() / 4f), builder -> new ExtShapePressurePlateBlock(baseBlock, ((PressurePlateBuilder) builder).type, builder.blockSettings));
    this.type = type;
    this.defaultTagToAdd = ExtShapeBlockTags.PRESSURE_PLATES;
    this.mapping = BlockMappings.getMappingOf(BlockShape.PRESSURE_PLATE);
    itemSettings.group(ItemGroup.REDSTONE);
  }

  @Override
  protected String getSuffix() {
    return "_pressure_plate";
  }


}
