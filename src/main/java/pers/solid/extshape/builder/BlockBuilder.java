package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.item.Item;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapeBlock;
import pers.solid.extshape.block.ExtShapeButtonBlock;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class BlockBuilder extends AbstractBlockBuilder<Block> {

    public BlockBuilder() {
        super(null, null);
    }

    public static BlocksBuilder createBasicShapes(Block baseBlock) {
        return createAllShapes(baseBlock, null, null, null).withoutFences().withoutButton().withoutPressurePlate();
    }

    public static BlocksBuilder createAllShapes(Block baseBlock, Item fenceCraftingIngredient,
                                                ExtShapeButtonBlock.ButtonType buttonType,
                                                PressurePlateBlock.ActivationRule pressurePlateActivationRule) {
        return new BlocksBuilder(baseBlock, fenceCraftingIngredient, buttonType, pressurePlateActivationRule);
    }

    public static BlocksBuilder createEmpty(Block baseBlock) {
        return new BlocksBuilder(baseBlock);
    }

    public static AbstractBlockBuilder<? extends Block> create(Shape shape, Block baseBlock,
                                                               @Nullable Item fenceCraftingIngredient,
                                                               @Nullable ExtShapeButtonBlock.ButtonType buttonType,
                                                               @Nullable PressurePlateBlock.ActivationRule pressurePlateActivationRule) {
        return switch (shape) {
            case stairs -> new StairsBuilder(baseBlock);
            case slab -> new SlabBuilder(baseBlock);
            case verticalSlab -> new VerticalSlabBuilder(baseBlock);
            case verticalStairs -> new VerticalStairsBuilder(baseBlock);
            case quarterPiece -> new QuarterPieceBuilder(baseBlock);
            case verticalQuarterPiece -> new VerticalQuarterPieceBuilder(baseBlock);
            case fence -> new FenceBuilder(baseBlock, fenceCraftingIngredient);
            case fenceGate -> new FenceGateBuilder(baseBlock, fenceCraftingIngredient);
            case button -> new ButtonBuilder(buttonType, baseBlock);
            case pressurePlate -> new PressurePlateBuilder(pressurePlateActivationRule, baseBlock);
            case wall -> new WallBuilder(baseBlock);
        };
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

    @Override
    public void createInstance() {
        this.block = new ExtShapeBlock(this.blockSettings);
    }
}
