package pers.solid.extshape;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.config.ExtShapeConfig;

/**
 * 该模组的方块物品。不同之处在于，其获取名称会直接获取方块名称。
 */
public class ExtShapeBlockItem extends BlockItem {
  public ExtShapeBlockItem(Block block, Settings settings) {
    super(block, settings);
  }

  @Override
  public Text getName() {
    return this.getBlock().getName();
  }

  @Override
  public Text getName(ItemStack stack) {
    return getBlock().getName();
  }

  @Override
  public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
    // 如果启用了添加至原版物品组，当该方块不属于形状，或者允许添加的形状包含当前形状时，添加至原版物品组。
    final BlockShape shape;
    if (ExtShapeConfig.CURRENT_CONFIG.addToVanillaGroups && (!(getBlock() instanceof ExtShapeBlockInterface i) || (shape = i.getBlockShape()) == null || ExtShapeConfig.CURRENT_CONFIG.shapesToAddToVanilla.contains(shape)) || group == ItemGroup.SEARCH) {
      super.appendStacks(group, stacks);
    }
  }
}
