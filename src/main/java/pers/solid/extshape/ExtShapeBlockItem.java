package pers.solid.extshape;

import net.minecraft.resources.DependantName;
import net.minecraft.util.Util;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import pers.solid.extshape.mixin.ItemSettingAccessor;
import pers.solid.extshape.util.SpecialDependantName;

/**
 * 该模组的方块物品。不同之处在于，其获取名称会直接获取方块名称。
 */
public class ExtShapeBlockItem extends BlockItem {
  public ExtShapeBlockItem(Block block, Properties settings) {
    super(block, Util.make(settings, properties -> {
      final ItemSettingAccessor accessor = (ItemSettingAccessor) properties;
      final DependantName<Item, String> original = accessor.getDescriptionId();
      SpecialDependantName<Item, String> special = original::get;
      accessor.setDescriptionId(special);
    }));
  }
}
