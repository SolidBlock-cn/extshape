package pers.solid.extshape.block;

import net.minecraft.block.FenceBlock;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;

public class ExtShapeFenceBlock extends FenceBlock implements ExtShapeVariantBlockInterface {

  public ExtShapeFenceBlock(Settings settings) {
    super(settings);
  }

  @Override
  public MutableText getName() {
    return new TranslatableText("block.extshape.?_fence", this.getNamePrefix());
  }
}
