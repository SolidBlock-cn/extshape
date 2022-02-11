package pers.solid.extshape.block;

import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;

public class ExtShapeVerticalSlabBlock extends VerticalSlabBlock implements ExtShapeVariantBlockInterface {


  public ExtShapeVerticalSlabBlock(Settings settings) {
    super(settings);
  }

  @Override
  public MutableText getName() {
    return new TranslatableText("block.extshape.?_vertical_slab", this.getNamePrefix());
  }
}
