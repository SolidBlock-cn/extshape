package pers.solid.extshape.builder;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import pers.solid.extshape.ExtShapeBlockItem;

public class ExtShapeBlockItemBuilder extends AbstractItemBuilder<ExtShapeBlockItem> {
    Block block;

    protected ExtShapeBlockItemBuilder(Block block, FabricItemSettings settings) {
        super(settings);
        this.block = block;
    }

    @Override
    public void createInstance() {
        this.item = new ExtShapeBlockItem(block, settings);
    }
}
