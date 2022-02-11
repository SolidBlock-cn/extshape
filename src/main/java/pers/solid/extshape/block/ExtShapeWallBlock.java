package pers.solid.extshape.block;

import net.minecraft.block.WallBlock;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;

public class ExtShapeWallBlock extends WallBlock implements ExtShapeVariantBlockInterface {
  public ExtShapeWallBlock(Settings settings) {
    super(settings);
  }
  // 特别注意：目前只要是有#walls标签的方块都会在#mineable/pickaxe标签内。
  // 为修复此问题，我们不注册羊毛墙！！！

  @Override
  public MutableText getName() {
    return new TranslatableText("block.extshape.?_wall", this.getNamePrefix());
  }

}
