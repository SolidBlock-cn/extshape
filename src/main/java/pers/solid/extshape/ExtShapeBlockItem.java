package pers.solid.extshape;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.Nullable;
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
    return stack.getItem().getName();
  }

  @Override
  public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
    if (ExtShapeConfig.CURRENT_CONFIG.addToVanillaGroups || group == ItemGroup.SEARCH) {
      super.appendStacks(group, stacks);
    }
  }

  @Override
  public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
    if (getBlock() instanceof ExtShapeBlockInterface block && block.getBaseBlock() != null) {
      final Item item = block.getBaseBlock().asItem();
      final BlockShape shape = BlockShape.getShapeOf(getBlock());
      final float timeMultiplier = shape == null ? 1 : shape.logicalCompleteness;
      return (int) (ForgeHooks.getBurnTime(new ItemStack(item, itemStack.getCount(), itemStack.getNbt()), recipeType) * timeMultiplier);
    } else {
      return super.getBurnTime(itemStack, recipeType);
    }
  }
}
