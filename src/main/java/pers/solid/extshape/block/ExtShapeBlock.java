package pers.solid.extshape.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.util.Identifier;
import pers.solid.extshape.ExtShapeBlockItem;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class ExtShapeBlock extends Block implements RegistrableBlock {
    public final Identifier identifier;
    public final BlockItem blockItem;

    public ExtShapeBlock(Identifier identifier, Settings settings) {
        super(settings);
        this.identifier = identifier;
        this.blockItem = new ExtShapeBlockItem(this, new FabricItemSettings());
    }

    @Override
    public Identifier getIdentifier() {
        return this.identifier;
    }

    @Override
    public BlockItem getBlockItem() {
        return this.blockItem;
    }

    @Override
    public RegistrableBlock addToTag() {
        this.addToTag(ExtShapeBlockTag.EXTSHAPE_BLOCKS);
        return this;
    }
}
