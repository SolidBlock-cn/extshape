package pers.solid.extshape.builder;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.ExtShapePressurePlateBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class PressurePlateBuilder extends AbstractBlockBuilder<PressurePlateBlock> {
  public final @NotNull PressurePlateBlock.ActivationRule type;

  protected PressurePlateBuilder(@NotNull PressurePlateBlock.ActivationRule type, Block baseBlock) {
    super(baseBlock, FabricBlockSettings.copyOf(baseBlock).noCollision().strength(baseBlock.getHardness() / 4f), builder -> new ExtShapePressurePlateBlock(baseBlock, type, builder.blockSettings));
    this.type = type;
    this.defaultTag = ExtShapeBlockTag.PRESSURE_PLATES;
    this.mapping = BlockMappings.SHAPE_TO_MAPPING.get(Shape.PRESSURE_PLATE);
  }

  @Override
  protected String getSuffix() {
    return "_pressure_plate";
  }


}
