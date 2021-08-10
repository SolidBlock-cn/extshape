package pers.solid.extshape;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class ExtShapeBlockItem extends BlockItem {

    public ExtShapeBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    public Text getName() {
        return this.getBlock().getName();
    }

    public Text getName(ItemStack stack) {
        return stack.getItem().getName();
    }
}
