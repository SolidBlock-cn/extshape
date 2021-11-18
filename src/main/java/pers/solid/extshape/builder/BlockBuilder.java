package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.item.Item;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapeButtonBlock;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class BlockBuilder extends AbstractBlockBuilder<Block> {

    public BlockBuilder() {
        super(null, null, builder -> new Block(builder.blockSettings));
    }

    public static BlocksBuilder createBasicShapes(Block baseBlock) {
        return createEmpty(baseBlock).withShapes();
    }

    @Contract("_, _, _, _ -> new")
    public static @NotNull BlocksBuilder createAllShapes(Block baseBlock, @Nullable Item fenceCraftingIngredient, @Nullable ExtShapeButtonBlock.ButtonType buttonType, @Nullable PressurePlateBlock.ActivationRule pressurePlateActivationRule) {
        return new BlocksBuilder(baseBlock, fenceCraftingIngredient, buttonType, pressurePlateActivationRule);
    }

    public static BlocksBuilder createEmpty(Block baseBlock) {
        return new BlocksBuilder(baseBlock);
    }

    public static @Nullable AbstractBlockBuilder<? extends Block> create(Shape shape, Block baseBlock, @Nullable Item fenceCraftingIngredient, @Nullable ExtShapeButtonBlock.ButtonType buttonType, @Nullable PressurePlateBlock.ActivationRule pressurePlateActivationRule) {
        switch (shape) {
            case STAIRS:
                return new StairsBuilder(baseBlock);
            case SLAB:
                return new SlabBuilder(baseBlock);
            case VERTICAL_SLAB:
                return new VerticalSlabBuilder(baseBlock);
            case VERTICAL_STAIRS:
                return new VerticalStairsBuilder(baseBlock);
            case QUARTER_PIECE:
                return new QuarterPieceBuilder(baseBlock);
            case VERTICAL_QUARTER_PIECE:
                return new VerticalQuarterPieceBuilder(baseBlock);
            case FENCE:
                return new FenceBuilder(baseBlock, fenceCraftingIngredient);
            case FENCE_GATE:
                return new FenceGateBuilder(baseBlock, fenceCraftingIngredient);
            case BUTTON:
                return buttonType != null ? new ButtonBuilder(buttonType, baseBlock) : null;
            case PRESSURE_PLATE:
                return pressurePlateActivationRule != null ? new PressurePlateBuilder(pressurePlateActivationRule, baseBlock) : null;
            case WALL:
                return new WallBuilder(baseBlock);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static BlockBuilder createBlock() {
        return new BlockBuilder();
    }

    @Override
    protected String getSuffix() {
        return null;
    }

    @Override
    protected @Nullable ExtShapeBlockTag getDefaultTag() {
        return null;
    }
}
