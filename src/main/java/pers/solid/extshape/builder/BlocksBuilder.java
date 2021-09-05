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

/**
 * 由多个方块构造器组成的一个从形状到构造器的映射。
 * 调用其方法时，会修改构造器参数，但不会进行实际构造，而调用 {@link #build()} 之后，就会正式执行构造，将会调用这些构造器的 <code>build</code>
 * 方法，这时候才产生方块对象，并根据参数进行一系列操作，如加入注册表、标签等。
 */
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

    /**
     * 根据一个基础方块，构造其多个变种方块。需要提供其中部分变种方块的参数。
     *
     * @param baseBlock                   基础方块。
     * @param fenceCraftingIngredient     合成栅栏和栅栏门时，需要使用的第二合成材料。
     * @param buttonType                  按钮类型。
     * @param pressurePlateActivationRule 压力板激活类型。
     */
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

    /**
     * 根据基础方块创建一个空的 BlocksBuilder，但不会计划创建任何构造器，除非调用启用构造器的有关方法。
     *
     * @param baseBlock 基础方块。
     */
    public BlocksBuilder(@NotNull Block baseBlock) {
        super();
        this.baseBlock = baseBlock;
        this.build = new HashMap<>();
        for (Shape shape : Shape.values()) {
            build.put(shape, false);
        }
    }

    /**
     * 指定条件成立时，构造这些形状的变种，否则不构造。
     *
     * @param condition 条件。
     * @param shapes    条件成立时，构造这些形状，否则不构造这些形状。
     */
    public BlocksBuilder withIf(boolean condition, Shape... shapes) {
        for (Shape shape : shapes) build.put(shape, condition);
        return this;
    }

    /**
     * 构造这些形状的变种。
     *
     * @param shapes 形状。
     */
    public BlocksBuilder with(Shape... shapes) {
        return this.withIf(true, shapes);
    }

    /**
     * 不构造这些形状的变种。
     *
     * @param shapes 形状。
     */
    public BlocksBuilder without(Shape... shapes) {
        return this.withIf(false, shapes);
    }

    /**
     * 不构造栅栏。
     */
    public BlocksBuilder withoutFences() {
        build.put(Shape.fence, false);
        build.put(Shape.fenceGate, false);
        return this;
    }

    /**
     * 构造基础形状。
     * 基础形状包括楼梯、台阶、垂直楼梯等。
     */
    public BlocksBuilder withShapes() {
        return this.with(Shape.stairs, Shape.slab, Shape.verticalQuarterPiece, Shape.verticalStairs, Shape.verticalStairs
                , Shape.quarterPiece);
    }

    /**
     * 不构造基础形状。
     */
    public BlocksBuilder withoutShapes() {
        return this.without(Shape.stairs, Shape.slab, Shape.verticalQuarterPiece, Shape.verticalSlab,
                Shape.verticalStairs, Shape.quarterPiece);
    }

    /**
     * 构造栅栏和栅栏门，并指定合成材料。
     *
     * @param fenceCraftingIngredient 合成栅栏或栅栏门需要使用的第二合成材料。
     */
    public BlocksBuilder withFences(@NotNull Item fenceCraftingIngredient) {
        build.put(Shape.fence, true);
        build.put(Shape.fenceGate, true);
        this.fenceCraftingIngredient = fenceCraftingIngredient;
        return this;
    }

    /**
     * 不构造墙。
     */
    public BlocksBuilder withoutWall() {
        build.put(Shape.wall, false);
        return this;
    }

    /**
     * 构造墙。
     */
    public BlocksBuilder withWall() {
        build.put(Shape.wall, true);
        return this;
    }

    /**
     * 不构造红石机关。按钮、压力板都将不会构造，栅栏门虽也属于红石机关但不受影响。
     */
    public BlocksBuilder withoutRedstone() {
        build.put(Shape.button, false);
        build.put(Shape.pressurePlate, false);
        return this;
    }

    /**
     * 不构造按钮。
     */
    public BlocksBuilder withoutButton() {
        build.put(Shape.button, false);
        return this;
    }

    /**
     * 构造按钮，并指定按钮类型。
     *
     * @param type 按钮类型。
     */
    public BlocksBuilder withButton(@NotNull ExtShapeButtonBlock.ButtonType type) {
        build.put(Shape.button, true);
        this.buttonType = type;
        return this;
    }

    /**
     * 不构造压力板。
     */
    public BlocksBuilder withoutPressurePlate() {
        build.put(Shape.pressurePlate, false);
        return this;
    }

    /**
     * 构造压力板，并指定压力板类型。
     *
     * @param type 压力板类型。
     */
    public BlocksBuilder withPressurePlate(@NotNull PressurePlateBlock.ActivationRule type) {
        build.put(Shape.pressurePlate, true);
        this.pressurePlateActivationRule = type;
        return this;
    }

    /**
     * 如果需要构造的方块都会构造对应的方块物品，则这些方块物品将会是防火的。
     */
    public BlocksBuilder fireproof() {
        this.fireproof = true;
        return this;
    }

    /**
     * 设置指定形状的方块的默认方块标签。
     *
     * @param shape 形状。
     * @param tag   默认方块标签。
     */
    public BlocksBuilder setDefaultTagOf(@Nullable Shape shape, @Nullable ExtShapeBlockTag tag) {
        if (shape == null || tag == null) return this;
        defaultTags.put(shape, tag);
        return this;
    }

    /**
     * 分别设置多个指定形状的方块的方块标签。
     *
     * @param map 由形状到方块标签的映射。
     */
    public BlocksBuilder setDefaultTagOf(Map<@Nullable Shape, @Nullable ExtShapeBlockTag> map) {
        for (Entry<@Nullable Shape, @Nullable ExtShapeBlockTag> entry : map.entrySet()) {
            if (entry.getKey() == null || entry.getValue() == null) continue;
            defaultTags.put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    /**
     * 将构造后的所有方块都放入同一个标签中。
     *
     * @param tag 构造后所有的方块都需要放入的标签。
     */
    public BlocksBuilder putTag(ExtShapeBlockTag tag) {
        this.tagList.add(tag);
        return this;
    }

    /**
     * 进行构造。构造后不会返回。
     */
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
