package pers.solid.extshape.builder;

import net.minecraft.block.*;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.QuarterPieceBlock;
import pers.solid.extshape.block.VerticalQuarterPieceBlock;
import pers.solid.extshape.block.VerticalSlabBlock;
import pers.solid.extshape.block.VerticalStairsBlock;
import pers.solid.extshape.family.BlockFamily;

public enum Shape {
    stairs(StairsBlock.class, BlockFamily.Variant.STAIRS),
    slab(SlabBlock.class, BlockFamily.Variant.SLAB),
    verticalSlab(VerticalSlabBlock.class, null),
    verticalStairs(VerticalStairsBlock.class, null),
    quarterPiece(QuarterPieceBlock.class, null),
    verticalQuarterPiece(VerticalQuarterPieceBlock.class, null),
    fence(FenceBlock.class, BlockFamily.Variant.FENCE),
    fenceGate(FenceGateBlock.class, BlockFamily.Variant.FENCE_GATE),
    wall(WallBlock.class, BlockFamily.Variant.WALL),
    button(AbstractButtonBlock.class, BlockFamily.Variant.BUTTON),
    pressurePlate(PressurePlateBlock.class, BlockFamily.Variant.PRESSURE_PLATE);

    public final Class<? extends Block> withClass;
    public final @Nullable BlockFamily.Variant vanillaVariant;

    Shape(Class<? extends Block> withClass, @Nullable BlockFamily.Variant vanillaVariant) {
        this.withClass = withClass;
        this.vanillaVariant = vanillaVariant;
    }

    @Nullable
    public static Shape getShapeOf(Block block) {
        for (Shape shape : Shape.values()) {
            if (shape.withClass.isInstance(block)) return shape;
        }
        return null;
    }
}
