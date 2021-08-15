package pers.solid.extshape.datagen;

import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public class SmoothStoneDoubleSlabGenerator extends AbstractBlockGenerator<Block> {
    protected SmoothStoneDoubleSlabGenerator(Path path, @NotNull Block block) {
        super(path, block);
    }

    @Override
    public Identifier getBlockModelIdentifier() {
        return new Identifier("minecraft", "block/smooth_stone_slab_double");
    }

    @Override
    public String getBlockModelString() {
        return null;
    }
}
