package pers.solid.extshape.builder;

import net.minecraft.block.*;
import net.minecraft.item.Item;
import pers.solid.extshape.ExtShapeBlockItem;

public class ExtShapeBlockItemBuilder extends AbstractItemBuilder<ExtShapeBlockItem> {
  public final Block block;

  protected ExtShapeBlockItemBuilder(Block block, Item.Settings settings) {
    super(settings);
    this.block = block;
  }

  @Override
  public void createInstance() {
    this.item = new ExtShapeBlockItem(block, settings);
  }
}
