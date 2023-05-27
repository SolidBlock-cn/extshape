package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
import net.minecraft.item.Item;
import net.minecraft.tag.BlockTags;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.block.ExtShapeFenceBlock;

public class FenceBuilder extends AbstractBlockBuilder<FenceBlock> {
  public final Item secondIngredient;

  public FenceBuilder(Block baseBlock, Item secondIngredient) {
    super(baseBlock, builder -> new ExtShapeFenceBlock(baseBlock, ((FenceBuilder) builder).secondIngredient, builder.blockSettings));
    this.secondIngredient = secondIngredient;
    this.shape = BlockShape.FENCE;
    primaryTagToAddTo = BlockTags.FENCES;
  }

  @Override
  protected String getSuffix() {
    return "_fence";
  }

  @Override
  public AbstractBlockBuilder<FenceBlock> withExtension(BlockExtension blockExtension) {
    return setInstanceSupplier(builder -> new ExtShapeFenceBlock.WithExtension(baseBlock, ((FenceBuilder) builder).secondIngredient, builder.blockSettings, blockExtension));
  }
}
