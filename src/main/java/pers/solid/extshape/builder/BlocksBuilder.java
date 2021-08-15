package pers.solid.extshape.builder;

import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapeButtonBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.util.*;

public class BlocksBuilder extends HashMap<Shape, AbstractBlockBuilder<? extends Block>> {
    final Map<@NotNull Shape, @Nullable ExtShapeBlockTag> defaultTags = new HashMap<>();
    Block baseBlock;
    boolean buildStairs, buildSlab, buildVerticalSlab;
    boolean buildFence, buildFenceGate, buildWall;
    boolean buildButton, buildPressurePlate;
    boolean fireproof;
    List<ExtShapeBlockTag> tagList = new ArrayList<>();
    private @Nullable Item fenceCraftingIngredient;
    private @Nullable ExtShapeButtonBlock.ButtonType buttonType;
    private @Nullable PressurePlateBlock.ActivationRule pressurePlateActivationRule;

    public BlocksBuilder(Block baseBlock, @Nullable Item fenceCraftingIngredient, ExtShapeButtonBlock.@Nullable ButtonType buttonType,
                         PressurePlateBlock.@Nullable ActivationRule pressurePlateActivationRule) {
        super();
        this.fenceCraftingIngredient = fenceCraftingIngredient;
        this.buttonType = buttonType;
        this.pressurePlateActivationRule = pressurePlateActivationRule;
        this.baseBlock = baseBlock;
        buildStairs = BlockMappings.getStairsBlockOf(baseBlock) == null;
        buildSlab = BlockMappings.getSlabBlockOf(baseBlock) == null;
        buildVerticalSlab = BlockMappings.getVerticalSlabBlockOf(baseBlock) == null;
        buildFence = BlockMappings.getFenceBlockOf(baseBlock) == null;
        buildFenceGate = BlockMappings.getFenceGateBlockOf(baseBlock) == null;
        buildWall = BlockMappings.getWallBlockOf(baseBlock) == null;
        buildButton = BlockMappings.getButtonBlockOf(baseBlock) == null;
        buildPressurePlate = BlockMappings.getPressurePlateBlockOf(baseBlock) == null;
    }

    public BlocksBuilder noFences() {
        this.buildFence = false;
        this.buildFenceGate = false;
        return this;
    }

    public BlocksBuilder withFences(@NotNull Item fenceCraftingIngredient) {
        this.buildFence = true;
        this.buildFenceGate = true;
        this.fenceCraftingIngredient = fenceCraftingIngredient;
        return this;
    }

    public BlocksBuilder noWall() {
        this.buildWall = false;
        return this;
    }

    public BlocksBuilder withWall() {
        this.buildWall = true;
        return this;
    }

    public BlocksBuilder noRedstone() {
        this.buildButton = false;
        this.buildPressurePlate = false;
        return this;
    }

    public BlocksBuilder noButton() {
        this.buildButton = false;
        return this;
    }

    public BlocksBuilder withButton(@NotNull ExtShapeButtonBlock.ButtonType type) {
        this.buildButton = true;
        this.buttonType = type;
        return this;
    }

    public BlocksBuilder noPressurePlate() {
        this.buildPressurePlate = false;
        return this;
    }

    public BlocksBuilder withPressurePlate(@NotNull PressurePlateBlock.ActivationRule type) {
        this.buildPressurePlate = true;
        this.pressurePlateActivationRule = type;
        return this;
    }

    public BlocksBuilder fireproof() {
        this.fireproof = true;
        return this;
    }

    public BlocksBuilder setDefaultTags(@Nullable ExtShapeBlockTag stairs, @Nullable ExtShapeBlockTag slab,
                                        @Nullable ExtShapeBlockTag verticalSlab, @Nullable ExtShapeBlockTag fence,
                                        @Nullable ExtShapeBlockTag fenceGate, @Nullable ExtShapeBlockTag wall,
                                        @Nullable ExtShapeBlockTag button, @Nullable ExtShapeBlockTag pressurePlate) {
        defaultTags.put(Shape.stairs, stairs);
        defaultTags.put(Shape.slab, slab);
        defaultTags.put(Shape.verticalSlab, verticalSlab);
        defaultTags.put(Shape.fence, fence);
        defaultTags.put(Shape.fenceGate, fenceGate);
        defaultTags.put(Shape.wall, wall);
        defaultTags.put(Shape.button, button);
        defaultTags.put(Shape.pressurePlate, pressurePlate);
        return this;
    }

    public BlocksBuilder putDefaultTag(Shape shape, @Nullable ExtShapeBlockTag tag) {
        defaultTags.put(shape, tag);
        return this;
    }

    public BlocksBuilder putTag(ExtShapeBlockTag tag) {
        this.tagList.add(tag);
        return this;
    }

    public void build() {
        if (buildStairs) this.put(Shape.stairs, BlockBuilder.createStairs(baseBlock));
        if (buildSlab) this.put(Shape.slab, BlockBuilder.createSlab(baseBlock));
        if (buildVerticalSlab) this.put(Shape.verticalSlab, BlockBuilder.createVerticalSlab(baseBlock));
        if (buildFence) this.put(Shape.fence, BlockBuilder.createFence(baseBlock, fenceCraftingIngredient));
        if (buildFenceGate) this.put(Shape.fenceGate, BlockBuilder.createFenceGate(baseBlock, fenceCraftingIngredient));
        if (buildWall) this.put(Shape.wall, BlockBuilder.createWall(baseBlock));
        if (buildButton) this.put(Shape.button, BlockBuilder.createButton(buttonType, baseBlock));
        if (buildPressurePlate)
            this.put(Shape.pressurePlate, BlockBuilder.createPressurePlate(pressurePlateActivationRule, baseBlock));

        if (this.baseBlock.asItem().isFireproof() || this.fireproof) this.fireproof();
        final Collection<AbstractBlockBuilder<? extends Block>> values = this.values();
        for (Entry<Shape, ExtShapeBlockTag> entry : this.defaultTags.entrySet()) {
            AbstractBlockBuilder<?> builder = this.get(entry.getKey());
            if (builder != null && entry.getValue() != null) builder.setDefaultTag(entry.getValue());
        }
        for (AbstractBlockBuilder<? extends Block> builder : values) {
            if (this.fireproof) builder.fireproof();
            tagList.forEach(builder::putTag);
        }
        values.forEach(AbstractBlockBuilder::build);
    }
}
