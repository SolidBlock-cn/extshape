package pers.solid.extshape.builder;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.block.*;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.util.StringIdentifiable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.QuarterPieceBlock;
import pers.solid.extshape.block.VerticalQuarterPieceBlock;
import pers.solid.extshape.block.VerticalSlabBlock;
import pers.solid.extshape.block.VerticalStairsBlock;

import java.util.Arrays;

public enum Shape implements StringIdentifiable {
    STAIRS(StairsBlock.class, BlockFamily.Variant.STAIRS, "stairs"),
    SLAB(SlabBlock.class, BlockFamily.Variant.SLAB, "slab"),
    VERTICAL_SLAB(VerticalSlabBlock.class, null, "vertical_slab"),
    VERTICAL_STAIRS(VerticalStairsBlock.class, null, "vertical_stairs"),
    QUARTER_PIECE(QuarterPieceBlock.class, null, "quarter_piece"),
    VERTICAL_QUARTER_PIECE(VerticalQuarterPieceBlock.class, null, "vertical_quarter_piece"),
    FENCE(FenceBlock.class, BlockFamily.Variant.FENCE, "fence"),
    FENCE_GATE(FenceGateBlock.class, BlockFamily.Variant.FENCE_GATE, "fence_gate"),
    WALL(WallBlock.class, BlockFamily.Variant.WALL, "wall"),
    BUTTON(AbstractButtonBlock.class, BlockFamily.Variant.BUTTON, "button"),
    PRESSURE_PLATE(PressurePlateBlock.class, BlockFamily.Variant.PRESSURE_PLATE, "pressure_place");

    public static final BiMap<Shape, String> SHAPE_TO_STRING = Arrays.stream(values()).collect(ImmutableBiMap.toImmutableBiMap(value -> value, Shape::asString));
    /**
     * 该形状对应的方块类。
     */
    public final Class<? extends Block> withClass;
    /**
     * 该形状对应的原版的 {@link BlockFamily.Variant}。
     */
    public final @Nullable BlockFamily.Variant vanillaVariant;
    private final @NotNull String name;

    Shape(Class<? extends Block> withClass, @Nullable BlockFamily.Variant vanillaVariant, @NotNull String name) {
        this.withClass = withClass;
        this.vanillaVariant = vanillaVariant;
        this.name = name;
    }

    @Nullable
    public static Shape getShapeOf(Block block) {
        for (Shape shape : Shape.values()) {
            if (shape.withClass.isInstance(block)) return shape;
        }
        return null;
    }

    @Override
    @NotNull
    public String asString() {
        return name;
    }
}
