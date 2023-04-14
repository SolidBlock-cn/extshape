package pers.solid.extshape;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.text.Text;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;

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
  public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
    if (getBlock() instanceof ExtShapeBlockInterface itf) {
      final Block baseBlock = itf.getBaseBlock();
      final BlockShape blockShape = itf.getBlockShape();
      if (baseBlock != null && blockShape != null && ExtShapeBlocks.getBaseBlocks().contains(baseBlock)) {
        final int baseBurnTime = ForgeHooks.getBurnTime(baseBlock.asItem().getDefaultStack(), recipeType);
        if (baseBurnTime > 0) {
          return ((int) (baseBurnTime * blockShape.logicalCompleteness));
        }
      }
    }

    return super.getBurnTime(itemStack, recipeType);
  }
}
