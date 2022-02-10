package pers.solid.extshape.builder;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.recipe.*;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.block.ExtShapeButtonBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.mixin.AbstractBlockMixin;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * 　　由多个方块构造器组成的一个从形状到构造器的映射。<br>
 * 　　用其方法时，会修改构造器参数，但不会进行实际构造，而调用 {@link #build()} 之后，就会正式执行构造，将会调用这些构造器的 <code>build</code>方法，这时候才产生方块对象，并根据参数进行一系列操作，如加入注册表、标签等。<br>
 * 　　调用这些 {@code build} 方法时，会自动往运行时资源包（RRP）中添加内容，同时 {@link #build()} 会添加一些方块之间的转换配方，如台阶与纵台阶的合成配方。
 */
public class BlocksBuilder extends EnumMap<Shape, AbstractBlockBuilder<? extends Block>> {
    final Map<@NotNull Shape, @Nullable ExtShapeBlockTag> defaultTags = new HashMap<>();
    final Object2BooleanMap<Shape> shapeToWhetherBuild;
    @NotNull
    final Block baseBlock;
    final List<ExtShapeBlockTag> tagList = new ArrayList<>();
    public @Nullable BiConsumer<Shape, AbstractBlockBuilder<? extends Block>> preparationConsumer;
    boolean fireproof;
    private @Nullable Item fenceCraftingIngredient;
    private @Nullable ExtShapeButtonBlock.ButtonType buttonType;
    private @Nullable PressurePlateBlock.ActivationRule pressurePlateActivationRule;
    private Tag<Item> breakByToolTag;
    private int miningLevel;

    /**
     * 根据一个基础方块，构造其多个变种方块。需要提供其中部分变种方块的参数。
     *
     * @param baseBlock                   基础方块。
     * @param fenceCraftingIngredient     合成栅栏和栅栏门时，需要使用的第二合成材料。
     * @param buttonType                  按钮类型。
     * @param pressurePlateActivationRule 压力板激活类型。
     */
    public BlocksBuilder(@NotNull Block baseBlock, @Nullable Item fenceCraftingIngredient, ExtShapeButtonBlock.@Nullable ButtonType buttonType, PressurePlateBlock.@Nullable ActivationRule pressurePlateActivationRule) {
        super(Shape.class);
        this.fenceCraftingIngredient = fenceCraftingIngredient;
        this.buttonType = buttonType;
        this.pressurePlateActivationRule = pressurePlateActivationRule;
        this.baseBlock = baseBlock;
        this.shapeToWhetherBuild = new Object2BooleanOpenHashMap<>();
        for (Shape shape : Shape.values()) {
            shapeToWhetherBuild.put(shape, true);
        }
    }

    /**
     * 根据基础方块创建一个空的 BlocksBuilder，但不会计划创建任何构造器，除非调用启用构造器的有关方法。
     *
     * @param baseBlock 基础方块。
     */
    public BlocksBuilder(@NotNull Block baseBlock) {
        super(Shape.class);
        this.baseBlock = baseBlock;
        this.shapeToWhetherBuild = new Object2BooleanOpenHashMap<>();
        for (Shape shape : Shape.values()) {
            shapeToWhetherBuild.put(shape, false);
        }
    }

    public BlocksBuilder setPreparationConsumer(@Nullable BiConsumer<Shape, AbstractBlockBuilder<? extends Block>> preparationConsumer) {
        this.preparationConsumer = preparationConsumer;
        return this;
    }

    /**
     * 指定条件成立时，构造这些形状的变种，否则不构造。
     *
     * @param condition 条件。
     * @param shapes    条件成立时，构造这些形状，否则不构造这些形状。
     */
    public BlocksBuilder withIf(boolean condition, Shape... shapes) {
        for (Shape shape : shapes) shapeToWhetherBuild.put(shape, condition);
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
        shapeToWhetherBuild.put(Shape.FENCE, false);
        shapeToWhetherBuild.put(Shape.FENCE_GATE, false);
        return this;
    }

    /**
     * 构造基础形状。
     * 基础形状包括楼梯、台阶、垂直楼梯等。
     */
    public BlocksBuilder withShapes() {
        return this.with(Shape.STAIRS, Shape.SLAB, Shape.VERTICAL_QUARTER_PIECE, Shape.VERTICAL_STAIRS, Shape.VERTICAL_SLAB, Shape.QUARTER_PIECE);
    }

    /**
     * 不构造基础形状。
     */
    public BlocksBuilder withoutShapes() {
        return this.without(Shape.STAIRS, Shape.SLAB, Shape.VERTICAL_QUARTER_PIECE, Shape.VERTICAL_SLAB, Shape.VERTICAL_STAIRS, Shape.QUARTER_PIECE);
    }

    /**
     * 构造栅栏和栅栏门，并指定合成材料。
     *
     * @param fenceCraftingIngredient 合成栅栏或栅栏门需要使用的第二合成材料。
     */
    public BlocksBuilder withFences(@NotNull Item fenceCraftingIngredient) {
        shapeToWhetherBuild.put(Shape.FENCE, true);
        shapeToWhetherBuild.put(Shape.FENCE_GATE, true);
        this.fenceCraftingIngredient = fenceCraftingIngredient;
        return this;
    }

    /**
     * 不构造墙。
     */
    public BlocksBuilder withoutWall() {
        shapeToWhetherBuild.put(Shape.WALL, false);
        return this;
    }

    /**
     * 构造墙。
     */
    public BlocksBuilder withWall() {
        shapeToWhetherBuild.put(Shape.WALL, true);
        return this;
    }

    /**
     * 不构造红石机关。按钮、压力板都将不会构造，栅栏门虽也属于红石机关但不受影响。
     */
    public BlocksBuilder withoutRedstone() {
        shapeToWhetherBuild.put(Shape.BUTTON, false);
        shapeToWhetherBuild.put(Shape.PRESSURE_PLATE, false);
        return this;
    }

    /**
     * 不构造按钮。
     */
    public BlocksBuilder withoutButton() {
        shapeToWhetherBuild.put(Shape.BUTTON, false);
        return this;
    }

    /**
     * 构造按钮，并指定按钮类型。
     *
     * @param type 按钮类型。
     */
    public BlocksBuilder withButton(@NotNull ExtShapeButtonBlock.ButtonType type) {
        shapeToWhetherBuild.put(Shape.BUTTON, true);
        this.buttonType = type;
        return this;
    }

    /**
     * 不构造压力板。
     */
    public BlocksBuilder withoutPressurePlate() {
        shapeToWhetherBuild.put(Shape.PRESSURE_PLATE, false);
        return this;
    }

    /**
     * 构造压力板，并指定压力板类型。
     *
     * @param type 压力板类型。
     */
    public BlocksBuilder withPressurePlate(@NotNull PressurePlateBlock.ActivationRule type) {
        shapeToWhetherBuild.put(Shape.PRESSURE_PLATE, true);
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

    public BlocksBuilder breakByTool(Tag<Item> tag, int miningLevel) {
        this.breakByToolTag = tag;
        this.miningLevel = miningLevel;
        return this;
    }

    public BlocksBuilder breakByTool(Tag<Item> tag) {
        return this.breakByTool(tag, 0);
    }

    /**
     * 台阶到垂直台阶的配方。参考格式：
     * <pre>
     * {
     *   "type": "minecraft:crafting_shaped",
     *   "group": "%s",
     *   "pattern": ["#"],
     *   "key": {
     *     "#": {"item": "%s"}
     *   },
     *   "result": {
     *     "item": "%s",
     *     "count": 1
     *   }
     * }
     * </pre>
     * 注意运行此方法时，建议先检测对应方块是否存在。
     */
    public void writeSlab2VerticalRecipe(RuntimeResourcePack pack) {
        final Identifier verticalSlabIdentifier = getBlockIdentifierOf(Shape.VERTICAL_SLAB);
        final Identifier slabIdentifier = getBlockIdentifierOf(Shape.SLAB);
        pack.addRecipe(verticalSlabIdentifier, simpleShapelessRecipe(slabIdentifier.toString(), verticalSlabIdentifier.toString()));
        pack.addRecipe(new Identifier("extshape", slabIdentifier.getPath() + "_from_vertical_slab"), simpleShapelessRecipe(verticalSlabIdentifier.toString(), slabIdentifier.toString()));
    }

    /**
     * 楼梯到垂直楼梯以及垂直楼梯到楼梯的配方。
     */
    public void writeStairs2VerticalRecipe(RuntimeResourcePack pack) {
        final Identifier verticalStairsIdentifier = getBlockIdentifierOf(Shape.VERTICAL_STAIRS);
        final Identifier stairsIdentifier = getBlockIdentifierOf(Shape.STAIRS);
        pack.addRecipe(verticalStairsIdentifier, simpleShapelessRecipe(stairsIdentifier.toString(), verticalStairsIdentifier.toString()));
        pack.addRecipe(new Identifier("extshape", stairsIdentifier.getPath() + "_from_vertical_stairs"), simpleShapelessRecipe(verticalStairsIdentifier.toString(), stairsIdentifier.toString()));
    }

    /**
     * 横条与纵条之间的配方。
     */
    public void writeQuarterPiece2VerticalRecipe(RuntimeResourcePack pack) {
        final Identifier verticalIdentifier = getBlockIdentifierOf(Shape.VERTICAL_QUARTER_PIECE);
        final Identifier identifier = getBlockIdentifierOf(Shape.QUARTER_PIECE);
        pack.addRecipe(verticalIdentifier, simpleShapelessRecipe(identifier.toString(), verticalIdentifier.toString()));
        pack.addRecipe(new Identifier("extshape", identifier.getPath() + "_from_vertical_quarter_piece"), simpleShapelessRecipe(verticalIdentifier.toString(), identifier.toString()));
    }

    @NotNull
    private JShapelessRecipe simpleShapelessRecipe(String ingredient, String result) {
        return JRecipe.shapeless(JIngredients.ingredients().add(JIngredient.ingredient().item(ingredient)), JResult.result(result));
    }

    /**
     * 获取该 BlocksBuilder 中指定形状的方块。可能有以下情况：
     * <ol>
     *   <li>若该方块变种是需要构造且已完成构造的，则直接将其实例返回，但前提是已经构造好了实例。</li>
     *   <li>若不构造此方块变种（拒绝构造或者无需构造），则尝试从已有的 {@link BlockMappings} 中获取。</li>
     * </ol>
     *
     * @param shape 形状。
     * @return 指定形状的方块。
     * @see #getBlockIdentifierOf(Shape)
     */
    protected @Nullable Block getBlockOf(Shape shape) {
        if (containsKey(shape)) {
            return get(shape).instance;
        } else {
            return BlockMappings.getBlockOf(shape, baseBlock);
        }
    }

    protected boolean hasBlockItemOf(Shape shape) {
        if (containsKey(shape) && get(shape).buildItem) {
            // 如果正好存在需要构造的方块，且该方块不拒绝构造物品。
            return true;
        } else {
            final Block block = BlockMappings.SHAPE_TO_MAPPING.get(shape).get(baseBlock);
            return block != null && BlockItem.BLOCK_ITEMS.containsKey(block);
        }
    }

    /**
     * 获取该 BlocksBuilder 中指定形状的方块id。可能有以下情况：
     * <ol>
     *   <li>若该方块变种是需要构造且已完成构造的，则直接调用其 {@link Builder#getIdentifier()}。</li>
     *   <li>若不构造此方块变种（拒绝构造或者无需构造），则尝试从已有的 {@link BlockMappings} 中获取方块然后从 {@link Registry#BLOCK} 中获取其 id。</li>
     * </ol>
     *
     * @param shape 形状。
     * @return 指定形状的方块。
     * @see #getBlockIdentifierOf(Shape)
     */
    protected Identifier getBlockIdentifierOf(Shape shape) {
        if (containsKey(shape)) {
            return get(shape).getIdentifier();
        } else {
            return Registry.BLOCK.getId(BlockMappings.getBlockOf(shape, baseBlock));
        }
    }

    /**
     * 是否允许切石。
     *
     * @see AbstractBlockBuilder#isStoneCut()
     */
    private boolean isStoneCut() {
        return ((AbstractBlockMixin) baseBlock).getMaterial() == Material.STONE;
    }

    /**
     * 写入不同形状之间的资源包。<br>
     * 注意：调用此方法前，应当先检查对应的方块实例是否已经存在或者构造好（{@link #getBlockOf} 不返回 {@code null}）。
     */
    public void writeInterShapeResources(RuntimeResourcePack pack) {
        if (hasBlockItemOf(Shape.SLAB) && hasBlockItemOf(Shape.VERTICAL_SLAB)) {
            writeSlab2VerticalRecipe(pack);
        }
        if (hasBlockItemOf(Shape.STAIRS) && hasBlockItemOf(Shape.VERTICAL_STAIRS)) {
            writeStairs2VerticalRecipe(pack);
        }
        if (hasBlockItemOf(Shape.QUARTER_PIECE) && hasBlockItemOf(Shape.VERTICAL_QUARTER_PIECE)) {
            writeQuarterPiece2VerticalRecipe(pack);
        }
        final Identifier slabIdentifier = getBlockIdentifierOf(Shape.SLAB);
        final Identifier verticalSlabIdentifier = getBlockIdentifierOf(Shape.VERTICAL_SLAB);
        final Identifier quarterPieceIdentifier = getBlockIdentifierOf(Shape.QUARTER_PIECE);
        final Identifier verticalQuarterPieceIdentifier = getBlockIdentifierOf(Shape.VERTICAL_QUARTER_PIECE);
        final Identifier stairsIdentifier = getBlockIdentifierOf(Shape.STAIRS);
        final boolean stoneCut = isStoneCut();

        if (hasBlockItemOf(Shape.QUARTER_PIECE)) {
            final String result = quarterPieceIdentifier.toString();
            // 1x楼梯 -> 3x横条
            if (hasBlockItemOf(Shape.STAIRS) && stoneCut) {
                pack.addRecipe(new Identifier(quarterPieceIdentifier.getNamespace(), quarterPieceIdentifier.getPath() + "_from_stairs_stonecutting"), simpleStonecuttingRecipe(stairsIdentifier.toString(), result, 3));
            }

            // 1x台阶 -> 2x横条
            if (hasBlockItemOf(Shape.SLAB)) {
                pack.addRecipe(new Identifier(quarterPieceIdentifier.getNamespace(), quarterPieceIdentifier.getPath() + "_from_slab"), simpleShapedRecipe(slabIdentifier.toString(), result, 6, "###"));
                if (stoneCut)
                    pack.addRecipe(new Identifier(quarterPieceIdentifier.getNamespace(), quarterPieceIdentifier.getPath() + "_from_slab_stonecutting"), simpleStonecuttingRecipe(slabIdentifier.toString(), result, 2));
            }

            // 1x纵台阶 -> 2x横条
            if (hasBlockItemOf(Shape.VERTICAL_SLAB)) {
                pack.addRecipe(new Identifier(quarterPieceIdentifier.getNamespace(), quarterPieceIdentifier.getPath() + "_from_vertical_slab"), simpleShapedRecipe(verticalSlabIdentifier.toString(), result, 6, "###"));
                if (stoneCut)
                    pack.addRecipe(new Identifier(quarterPieceIdentifier.getNamespace(), quarterPieceIdentifier.getPath() + "_from_vertical_slab_stonecutting"), simpleStonecuttingRecipe(verticalSlabIdentifier.toString(), result, 2));
            }
        }

        if (hasBlockItemOf(Shape.VERTICAL_QUARTER_PIECE)) {
            String result = verticalQuarterPieceIdentifier.toString();
            // 1x纵台阶 -> 2x纵条
            if (hasBlockItemOf(Shape.VERTICAL_SLAB)) {
                pack.addRecipe(new Identifier(verticalQuarterPieceIdentifier.getNamespace(), verticalQuarterPieceIdentifier.getPath() + "_from_vertical_slab"), simpleShapedRecipe(verticalSlabIdentifier.toString(), result, 2, "#", "#", "#"));
                if (stoneCut)
                    pack.addRecipe(new Identifier(verticalQuarterPieceIdentifier.getNamespace(), verticalQuarterPieceIdentifier.getPath() + "_from_vertical_slab_stonecutting"), simpleStonecuttingRecipe(verticalSlabIdentifier.toString(), result, 2));
            }

            // 1x纵楼梯 -> 3x纵条
            if (hasBlockItemOf(Shape.VERTICAL_STAIRS) && stoneCut) {
                pack.addRecipe(new Identifier(verticalQuarterPieceIdentifier.getNamespace(), verticalQuarterPieceIdentifier.getPath() + "_from_vertical_stairs_stonecutting"), simpleStonecuttingRecipe(getBlockIdentifierOf(Shape.VERTICAL_STAIRS).toString(), result, 3));
            }
        }
    }

    protected JShapedRecipe simpleShapedRecipe(String item, String result, int count, String... patterns) {
        return JRecipe.shaped(JPattern.pattern(patterns), JKeys.keys().key("#", JIngredient.ingredient().item(item)), JResult.stackedResult(result, count));
    }

    protected JStonecuttingRecipe simpleStonecuttingRecipe(String ingredient, String result, int count) {
        return JRecipe.stonecutting(JIngredient.ingredient().item(ingredient), JResult.stackedResult(result, count));
    }

    /**
     * 进行构造。构造后不会返回。
     */
    public void build() {
        for (Object2BooleanMap.Entry<Shape> entry : shapeToWhetherBuild.object2BooleanEntrySet()) {
            Shape shape = entry.getKey();
            boolean whetherBuild = entry.getBooleanValue();
            // 自动排除现成的。
            if (whetherBuild && BlockMappings.getBlockOf(shape, baseBlock) == null) {
                final @Nullable AbstractBlockBuilder<? extends Block> blockBuilder = BlockBuilder.create(shape, baseBlock, fenceCraftingIngredient, buttonType, pressurePlateActivationRule);
                if (blockBuilder != null) {
                    this.put(shape, blockBuilder);
                }
                if (this.preparationConsumer != null) {
                    this.preparationConsumer.accept(shape, blockBuilder);
                }
            }
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
            if (this.breakByToolTag != null) {
                if (builder instanceof ButtonBuilder || builder instanceof PressurePlateBuilder) {
                    // 对于按钮、压力板，不应用挖掘等级。
                    builder.breakByTool(breakByToolTag);
                } else {
                    builder.breakByTool(breakByToolTag, miningLevel);
                }
            }
        }
        values.forEach(AbstractBlockBuilder::build);
        writeInterShapeResources(ExtShape.EXTSHAPE_PACK);
    }
}
