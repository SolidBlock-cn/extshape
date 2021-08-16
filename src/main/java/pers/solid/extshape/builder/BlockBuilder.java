package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.item.Item;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapeBlock;
import pers.solid.extshape.block.ExtShapeButtonBlock;
import pers.solid.extshape.tag.ExtShapeBlockTag;

public class BlockBuilder extends AbstractBlockBuilder<Block> {
    @Nullable String suffix;

    protected BlockBuilder() {
        super(null, null);
        suffix = null;
    }

    public static BlocksBuilder createAllShapes(Block baseBlock) {
        return createAllShapes(baseBlock, null, null, null).withoutFences().withoutButton().withoutPressurePlate();
    }

    public static BlocksBuilder createAllShapes(Block baseBlock, Item fenceCraftingIngredient,
                                                ExtShapeButtonBlock.ButtonType buttonType,
                                                PressurePlateBlock.ActivationRule pressurePlateActivationRule) {
        return new BlocksBuilder(baseBlock, fenceCraftingIngredient, buttonType, pressurePlateActivationRule);
    }

    public static AbstractBlockBuilder<? extends Block> create(Shape shape,Block baseBlock,
                                                           @Nullable Item fenceCraftingIngredient,
                                      @Nullable ExtShapeButtonBlock.ButtonType buttonType,
                                      @Nullable PressurePlateBlock.ActivationRule pressurePlateActivationRule) {
        return switch (shape) {
            case stairs -> createStairs(baseBlock);
            case slab -> createSlab(baseBlock);
            case verticalSlab -> createVerticalSlab(baseBlock);
            case verticalStairs -> new VerticalStairsBuilder(baseBlock);
            case quarterPiece -> createQuarterPiece(baseBlock);
            case verticalQuarterPiece -> new VerticalQuarterPieceBuilder(baseBlock);
            case fence -> createFence(baseBlock, fenceCraftingIngredient);
            case fenceGate -> createFenceGate(baseBlock, fenceCraftingIngredient);
            case button -> createButton(buttonType, baseBlock);
            case pressurePlate -> createPressurePlate(pressurePlateActivationRule, baseBlock);
            case wall -> createWall(baseBlock);
        };
    }
    public static BlockBuilder createBlock() {
        return new BlockBuilder();
    }

    public static StairsBuilder createStairs(Block baseBlock) {
        return new StairsBuilder(baseBlock);
    }

    public static SlabBuilder createSlab(Block baseBlock) {
        return new SlabBuilder(baseBlock);
    }

    public static VerticalSlabBuilder createVerticalSlab(Block baseBlock) {
        return new VerticalSlabBuilder(baseBlock);
    }

    public static QuarterPieceBuilder createQuarterPiece(Block baseBlock) {
        return new QuarterPieceBuilder(baseBlock);
    }

    public static FenceBuilder createFence(Block baseBlock, Item craftingIngredient) {
        return new FenceBuilder(baseBlock, craftingIngredient);
    }

    public static FenceGateBuilder createFenceGate(Block baseBlock, Item craftingIngredient) {
        return new FenceGateBuilder(baseBlock, craftingIngredient);
    }

    public static ButtonBuilder createButton(ExtShapeButtonBlock.ButtonType type, Block baseBlock) {
        return new ButtonBuilder(type, baseBlock);
    }

    public static PressurePlateBuilder createPressurePlate(PressurePlateBlock.ActivationRule type, Block baseBlock) {
        return new PressurePlateBuilder(type, baseBlock);
    }

    public static WallBuilder createWall(Block baseBlock) {
        return new WallBuilder(baseBlock);
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
