package pers.solid.extshape.builder;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;

public class ItemBuilder extends AbstractItemBuilder<Item> {
  protected ItemBuilder(FabricItemSettings settings) {
    super(settings);
  }

  @Override
  public void createInstance() {
    this.item = new Item(this.settings);
  }
}
