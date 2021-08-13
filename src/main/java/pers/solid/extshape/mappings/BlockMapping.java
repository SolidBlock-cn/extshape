package pers.solid.extshape.mappings;

import net.minecraft.block.Block;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public class BlockMapping<T extends Block> extends HashMap<Block,T> {
    public static final Set<Block> baseBlocks = new LinkedHashSet<>();

    @Override
    public T put(Block key, T value) {
        baseBlocks.add(key);
        if (value!=null&&this.containsValue(value)) throw new UnsupportedOperationException(String.format("Cannot add" +
                " a " +
                "duplicate value %s for this " +
                "mapping!", value));
        return super.put(key, value);
    }
}
