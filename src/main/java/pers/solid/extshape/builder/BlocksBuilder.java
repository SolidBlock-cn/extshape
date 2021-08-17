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
    final Map<Shape, Boolean> build;
    @NotNull
    final Block baseBlock;
    final List<ExtShapeBlockTag> tagList = new ArrayList<>();
    boolean fireproof;
    private @Nullable Item fenceCraftingIngredient;
    private @Nullable ExtShapeButtonBlock.ButtonType buttonType;
    private @Nullable PressurePlateBlock.ActivationRule pressurePlateActivationRule;

    public BlocksBuilder(@NotNull Block baseBlock, @Nullable Item fenceCraftingIngredient, ExtShapeButtonBlock.@Nullable ButtonType buttonType,
                         PressurePlateBlock.@Nullable ActivationRule pressurePlateActivationRule) {
        super();
        this.fenceCraftingIngredient = fenceCraftingIngredient;
        this.buttonType = buttonType;
        this.pressurePlateActivationRule = pressurePlateActivationRule;
        this.baseBlock = baseBlock;
        this.build = new HashMap<>();
        for (Shape shape : Shape.values()) {
            build.put(shape, true);
        }
    }

    public BlocksBuilder(@NotNull Block baseBlock) {
        super();
        this.baseBlock = baseBlock;
        this.build = new HashMap<>();
        for (Shape shape : Shape.values()) {
            build.put(shape, false);
        }
    }

    public BlocksBuilder withIf(boolean condition, Shape... shapes) {
        for (Shape shape : shapes) build.put(shape, condition);
        return this;
    }

    public BlocksBuilder with(Shape... shapes) {
        return this.withIf(true, shapes);
    }

    public BlocksBuilder without(Shape... shapes) {
        return this.withIf(false, shapes);
    }

    public BlocksBuilder withoutFences() {
        build.put(Shape.fence, false);
        build.put(Shape.fenceGate, false);
        return this;
    }

    public BlocksBuilder withShapes() {
        return this.with(Shape.stairs, Shape.slab, Shape.verticalQuarterPiece, Shape.verticalStairs, Shape.verticalStairs
                , Shape.quarterPiece);
    }

    public BlocksBuilder withoutShapes() {
        return this.without(Shape.stairs, Shape.slab, Shape.verticalQuarterPiece, Shape.verticalSlab,
                Shape.verticalStairs, Shape.quarterPiece);
    }

    public BlocksBuilder withFences(@NotNull Item fenceCraftingIngredient) {
        build.put(Shape.fence, true);
        build.put(Shape.fenceGate, true);
        this.fenceCraftingIngredient = fenceCraftingIngredient;
        return this;
    }

    public BlocksBuilder withoutWall() {
        build.put(Shape.wall, false);
        return this;
    }

    public BlocksBuilder withWall() {
        build.put(Shape.wall, true);
        return this;
    }

    public BlocksBuilder withoutRedstone() {
        build.put(Shape.button, false);
        build.put(Shape.pressurePlate, false);
        return this;
    }

    public BlocksBuilder withoutButton() {
        build.put(Shape.button, false);
        return this;
    }

    public BlocksBuilder withButton(@NotNull ExtShapeButtonBlock.ButtonType type) {
        build.put(Shape.button, true);
        this.buttonType = type;
        return this;
    }

    public BlocksBuilder withoutPressurePlate() {
        build.put(Shape.pressurePlate, false);
        return this;
    }

    public BlocksBuilder withPressurePlate(@NotNull PressurePlateBlock.ActivationRule type) {
        build.put(Shape.pressurePlate, true);
        this.pressurePlateActivationRule = type;
        return this;
    }

    public BlocksBuilder withIf(Shape shape, boolean condition) {
        build.put(shape, condition);
        return this;
    }

    public BlocksBuilder fireproof() {
        this.fireproof = true;
        return this;
    }

    public BlocksBuilder setDefaultTagOf(@Nullable Shape shape, @Nullable ExtShapeBlockTag tag) {
        if (shape == null || tag == null) return this;
        defaultTags.put(shape, tag);
        return this;
    }

    public BlocksBuilder setDefaultTagOf(Map<@Nullable Shape, @Nullable ExtShapeBlockTag> map) {
        for (var entry : map.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) continue;
            defaultTags.put(entry.getKey(), entry.getValue());
        }
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
        for (Entry<Shape, Boolean> entry : build.entrySet()) {
            Shape shape = entry.getKey();
            Boolean build = entry.getValue();
            // 自动排除现成的。
            if (build && BlockMappings.getBlockOf(shape, baseBlock) == null)
                this.put(shape, BlockBuilder.create(shape, baseBlock, fenceCraftingIngredient, buttonType,
                        pressurePlateActivationRule));
        }

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
