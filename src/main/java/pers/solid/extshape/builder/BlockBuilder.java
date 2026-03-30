package pers.solid.extshape.builder;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.Nullable;

public class BlockBuilder extends AbstractBlockBuilder<Block> {

  public BlockBuilder() {
    super(null, BlockBehaviour.Properties.of(), builder -> new Block(builder.blockSettings));
  }

  @Override
  protected @Nullable String getSuffix() {
    return null;
  }
}
