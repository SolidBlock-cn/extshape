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
import pers.solid.extshape.builder.Shape;
import pers.solid.extshape.config.ExtShapeConfig;

import java.util.Objects;

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
      final float timeMultiplier = switch (Objects.requireNonNullElse(Shape.getShapeOf(getBlock()), Shape.STAIRS)) {
        case STAIRS, VERTICAL_STAIRS, WALL, FENCE, FENCE_GATE -> 1f;
        case SLAB, VERTICAL_SLAB -> 0.5f;
        case QUARTER_PIECE, VERTICAL_QUARTER_PIECE -> 0.25f;
        case PRESSURE_PLATE -> 2f / 3;
        case BUTTON -> 1f / 3;
      };
      return (int) (ForgeHooks.getBurnTime(new ItemStack(item, itemStack.getCount(), itemStack.getNbt()), recipeType) * timeMultiplier);
    } else {
      return super.getBurnTime(itemStack, recipeType);
    }
  }
}
