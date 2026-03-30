package pers.solid.extshape.builder;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import org.jetbrains.annotations.Nullable;

public class BlockBuilder extends AbstractBlockBuilder<Block> {

  public BlockBuilder() {
    super(null, AbstractBlock.Settings.create(), builder -> new Block(builder.blockSettings));
  }

  @Override
  protected @Nullable String getSuffix() {
    return null;
  }
}
