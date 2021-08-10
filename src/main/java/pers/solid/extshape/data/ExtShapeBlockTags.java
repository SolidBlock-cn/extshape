package pers.solid.extshape.data;

import net.minecraft.block.Block;
import net.minecraft.tag.RequiredTagList;
import net.minecraft.tag.RequiredTagListRegistry;
import net.minecraft.util.registry.Registry;

@Deprecated
public class ExtShapeBlockTags {
    protected static final RequiredTagList<Block> REQUIRED_TAGS =
            RequiredTagListRegistry.register(Registry.BLOCK_KEY, "tags/blocks");
}
