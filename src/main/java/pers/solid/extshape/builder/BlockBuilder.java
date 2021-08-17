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

    public BlockBuilder() {
        super(null, null);
        suffix = null;
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

    public static AbstractBlockBuilder<? extends Block> create(Shape shape,Block baseBlock,
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
//
//    public static StairsBuilder createStairs(Block baseBlock) {
//        return new StairsBuilder(baseBlock);
//    }
//
//    public static SlabBuilder createSlab(Block baseBlock) {
//        return new SlabBuilder(baseBlock);
//    }
//
//    public static VerticalSlabBuilder createVerticalSlab(Block baseBlock) {
//        return new VerticalSlabBuilder(baseBlock);
//    }
//
//    public static QuarterPieceBuilder createQuarterPiece(Block baseBlock) {
//        return new QuarterPieceBuilder(baseBlock);
//    }
//
//    public static FenceBuilder createFence(Block baseBlock, Item craftingIngredient) {
//        return new FenceBuilder(baseBlock, craftingIngredient);
//    }
//
//    public static FenceGateBuilder createFenceGate(Block baseBlock, Item craftingIngredient) {
//        return new FenceGateBuilder(baseBlock, craftingIngredient);
//    }
//
//    public static ButtonBuilder createButton(ExtShapeButtonBlock.ButtonType type, Block baseBlock) {
//        return new ButtonBuilder(type, baseBlock);
//    }
//
//    public static PressurePlateBuilder createPressurePlate(PressurePlateBlock.ActivationRule type, Block baseBlock) {
//        return new PressurePlateBuilder(type, baseBlock);
//    }
//
//    public static WallBuilder createWall(Block baseBlock) {
//        return new WallBuilder(baseBlock);
//    }


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
