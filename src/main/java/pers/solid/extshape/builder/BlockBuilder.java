package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class BlockBuilder extends AbstractBlockBuilder<Block> {

  public BlockBuilder() {
    super(null, null, builder -> new Block(builder.blockSettings));
  }

  @Override
  protected String getSuffix() {
    return null;
  }

  protected @Nullable ExtShapeBlockTag getDefaultTagToAdd() {
    return null;
  }
}
