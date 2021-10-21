package pers.solid.extshape;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.builder.Shape;
import pers.solid.extshape.mappings.BlockMappings;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ShapesSorting implements Supplier<Collection<Map<Item, Collection<Item>>>> {
    public static final Collection<Shape> SHAPES = new LinkedHashSet<>();
    public static final Map<Block, Collection<Block>> ABSTRACT_BLOCK_SHAPE_SORTING_RULES = new AbstractMap<>() {
        @NotNull
        @Override
        public Set<Entry<Block, Collection<Block>>> entrySet() {
            return BlockMappings.BASE_BLOCKS.stream().map(block -> new SimpleImmutableEntry<>(block, get(block))).filter(entry -> entry.getValue() != null).collect(Collectors.toUnmodifiableSet());
        }

        @Override
        @Nullable
        public Collection<Block> get(Object key) {
            if (!(key instanceof Block)) return null;
            final List<Block> blocks = SHAPES.stream().map(shape -> BlockMappings.getBlockOf(shape, ((Block) key))).filter(Objects::nonNull).toList();
            return blocks.isEmpty() ? null : blocks;
        }
    };
    public static final Map<Item, Collection<Item>> ABSTRACT_ITEM_SHAPE_SORTING_RULES = new AbstractMap<>() {
        @NotNull
        @Override
        public Set<Entry<Item, Collection<Item>>> entrySet() {
            return ABSTRACT_BLOCK_SHAPE_SORTING_RULES.entrySet().stream().map(entry -> new SimpleImmutableEntry<Item, Collection<Item>>(entry.getKey().asItem(), entry.getValue().stream().map(Block::asItem).toList())).filter(entry -> entry.getKey() != null && entry.getValue() != null).collect(Collectors.toUnmodifiableSet());
        }

        @Override
        public Collection<Item> get(Object key) {
            if (!(key instanceof BlockItem)) return null;
            final Collection<Block> blocks = ABSTRACT_BLOCK_SHAPE_SORTING_RULES.get(((BlockItem) key).getBlock());
            return blocks == null ? null : blocks.stream().map(Block::asItem).filter(Objects::nonNull).toList();
        }
    };
    public static final Map<Item, Collection<Item>> COMPILED_ITEM_SHAPE_SORTING_RULES = new HashMap<>();

    public static boolean modLoaded() {
        return true;
    }

    public static void updateShapeList(String s) {
        SHAPES.clear();
        if (s.equals("*")) SHAPES.addAll(Arrays.asList(Shape.values()));
        else
            Arrays.stream(s.split("\\s+")).map(Shape.SHAPE_TO_STRING.inverse()::get).filter(Objects::nonNull).forEach(SHAPES::add);
        COMPILED_ITEM_SHAPE_SORTING_RULES.clear();
        COMPILED_ITEM_SHAPE_SORTING_RULES.putAll(ABSTRACT_ITEM_SHAPE_SORTING_RULES);
    }

    public static String getValidShapeNames() {
        return String.join(" ", Shape.SHAPE_TO_STRING.values());
    }

    public static boolean isValidShapeName(String s) {
        return Shape.SHAPE_TO_STRING.containsValue(s);
    }

    @Override
    public Collection<Map<Item, Collection<Item>>> get() {
        return Collections.singleton(COMPILED_ITEM_SHAPE_SORTING_RULES);
    }
}
