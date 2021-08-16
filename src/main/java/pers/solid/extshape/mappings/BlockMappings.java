package pers.solid.extshape.mappings;

import net.minecraft.block.Block;
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.Shape;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class BlockMappings {
    public static final Map<Shape,BlockMapping<Block>> shapeToMapping;

    static {
        shapeToMapping = new HashMap<>();
        for (Shape shape : Shape.values()) {
            shapeToMapping.put(shape, new BlockMapping<>());
        }
        // 从原版的BlockFamilies导入数据至BlockMappings。
        Stream<BlockFamily> vanillaBlockFamilies = BlockFamilies.getFamilies();
        vanillaBlockFamilies.forEach((blockFamily -> {
            Block baseBlock = blockFamily.getBaseBlock();
            Map<BlockFamily.Variant, Block> variants = blockFamily.getVariants();
            for (Shape shape : Shape.values()) {
                if (shape.vanillaVariant==null) continue;
                shapeToMapping.get(shape).put(baseBlock,variants.get(shape.vanillaVariant));
            }
        }));
    }

    @Nullable
    public static Map<Block, ? extends Block> getBlockMappingOf(Block block) {
        return shapeToMapping.get(Shape.getShapeOf(block));
    }

    public static Block getBaseBlockOf(Block block) {
        Map<Block, ? extends Block> mapping = getBlockMappingOf(block);
        if (mapping == null) return null;
        for (Map.Entry<Block, ? extends Block> entry : mapping.entrySet()) {
            if (entry.getValue() == block) return entry.getKey();
        }
        return null;
    }

    @Nullable
    public static Block getBlockOf(Shape shape, Block block) {
        var mapping = shapeToMapping.get(shape);
        if (mapping==null) return null;
        return mapping.get(block);
    }
}
