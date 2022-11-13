package pers.solid.extshape.builder;

import com.google.common.collect.ImmutableSet;
import net.devtech.arrp.util.CanIgnoreReturnValue;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.*;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.mixin.AbstractBlockStateAccessor;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * <p>此类相当于将一个基础方块的多个形状的构建器整合到一起，其本质为从方块形状到对应方块构建器的映射。
 * <p>调用其方法时，会修改构造器参数，但不会进行实际构建，而调用 {@link #build()} 之后，就会正式执行构建，将会调用这些构建器的 {@link AbstractBlockBuilder#build()} 方法，这时候才产生方块对象，并根据参数进行一系列操作，如加入注册表、标签等。
 */
public class BlocksBuilder extends TreeMap<BlockShape, AbstractBlockBuilder<? extends Block>> {
  /**
   * 本模组内置的所有方块形状。注意这并不包括其他模组添加的方块形状。因此，其他模组如需使用 BlocksBuilder，需自行通过 {@link #with(BlockShape...)} 添加。
   */
  private static final ImmutableSet<BlockShape> SHAPES = ImmutableSet.of(BlockShape.STAIRS, BlockShape.SLAB, BlockShape.VERTICAL_SLAB, BlockShape.VERTICAL_STAIRS, BlockShape.QUARTER_PIECE, BlockShape.VERTICAL_QUARTER_PIECE, BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.WALL, BlockShape.BUTTON, BlockShape.PRESSURE_PLATE);
  /**
   * 为指定形状的 {@link AbstractBlockBuilder} 调用 {@link AbstractBlockBuilder#setDefaultTagToAdd(ExtShapeBlockTag) #setDefaultTagToAdd}。
   */
  private Map<@NotNull BlockShape, @Nullable ExtShapeBlockTag> tagToAddForShape = new HashMap<>();
  /**
   * 该基础方块需要构建哪些形状的变种。可以通过 {@link #with} 和 {@link #without} 进行增减。
   */
  protected final SortedSet<BlockShape> shapesToBuild;
  /**
   * 基础方块。对于 BlocksBuilder 而言，基础方块不能是 {@code null}。
   */
  public final @NotNull Block baseBlock;
  /**
   * 构建器需要构建的所有对象都需要添加到的标签的列表。也就是说，构建的时候，这里面的标签会添加此构建器构建的所有方块。
   */
  protected final List<@NotNull ExtShapeBlockTag> tagsToAddEach = new ArrayList<>();
  protected @Nullable Item fenceCraftingIngredient;
  protected @Nullable ExtShapeButtonBlock.ButtonType buttonType;
  protected @Nullable PressurePlateBlock.ActivationRule pressurePlateActivationRule;
  /**
   * 在执行 {@link #build()} 之前会为每个值执行。
   */
  protected @Nullable BiConsumer<BlockShape, AbstractBlockBuilder<?>> blockBuilderConsumer;

  /**
   * 根据一个基础方块，构造其多个变种方块。需要提供其中部分变种方块的参数。
   *
   * @param baseBlock                   基础方块。
   * @param fenceCraftingIngredient     合成栅栏和栅栏门时，需要使用的第二合成材料。
   * @param buttonType                  按钮类型。
   * @param pressurePlateActivationRule 压力板激活类型。
   * @param shapesToBuild               需要构建哪些方块形状。
   */
  public BlocksBuilder(@NotNull Block baseBlock, @Nullable Item fenceCraftingIngredient, ExtShapeButtonBlock.@Nullable ButtonType buttonType, PressurePlateBlock.@Nullable ActivationRule pressurePlateActivationRule, SortedSet<BlockShape> shapesToBuild) {
    this.fenceCraftingIngredient = fenceCraftingIngredient;
    this.buttonType = buttonType;
    this.pressurePlateActivationRule = pressurePlateActivationRule;
    this.baseBlock = baseBlock;
    this.shapesToBuild = shapesToBuild;
  }

  /**
   * 创建一个 BlocksBuilder，将会创建所有形状的，但不包括第三方模组新增加的形状。
   *
   * @param baseBlock                   基础方块。
   * @param fenceCraftingIngredient     栅栏的第二合成材料。若为 {@code null}，则意味着不产生栅栏和栅栏门。
   * @param buttonType                  按钮的类型。若为 {@code null}，则意味着不产生按钮。
   * @param pressurePlateActivationRule 压力板的类型。若为 {@code null}，则意味着不产生压力板。
   * @return 新的 BlocksBuilder。
   */
  @Contract("_,_,_,_ -> new")
  public static BlocksBuilder createAllShapes(@NotNull Block baseBlock, @Nullable Item fenceCraftingIngredient, ExtShapeButtonBlock.@Nullable ButtonType buttonType, PressurePlateBlock.@Nullable ActivationRule pressurePlateActivationRule) {
    return new BlocksBuilder(baseBlock, fenceCraftingIngredient, buttonType, pressurePlateActivationRule, new TreeSet<>(SHAPES));
  }

  /**
   * 创建一个 BlocksBuilder，但暂时不会创建任何形状的方块，可以在后续通过 {@link #with} 等方法添加。
   *
   * @param baseBlock 基础方块。
   * @return 新的 BlocksBuilder。
   */
  @Contract("_ -> new")
  public static BlocksBuilder createEmpty(@NotNull Block baseBlock) {
    return new BlocksBuilder(baseBlock, null, null, null, new TreeSet<>());
  }

  /**
   * 创建一个 BlocksBuilder，但是只包含建筑方块，可以在后续通过 {@link #with}、{@link #without} 等方法增加或删除需要创建的形状。
   *
   * @param baseBlock 基础方块。
   * @return 新的 BlocksBuilder。
   */
  @Contract("_ -> new")
  public static BlocksBuilder createConstructionOnly(@NotNull Block baseBlock) {
    return createEmpty(baseBlock).withConstructionShapes();
  }

  @SuppressWarnings({"unchecked", "RedundantCast"})
  @Contract(value = "_ -> this", mutates = "this")
  public BlocksBuilder withExtension(@NotNull BlockExtension blockExtension) {
    blockBuilderConsumer = (blockShape, abstractBlockBuilder) -> {
      if (blockShape == BlockShape.STAIRS) ((AbstractBlockBuilder<StairsBlock>) abstractBlockBuilder).instanceSupplier = builder -> new ExtShapeStairsBlock.WithExtension(builder.baseBlock, builder.blockSettings, blockExtension);
      if (blockShape == BlockShape.SLAB) ((AbstractBlockBuilder<SlabBlock>) abstractBlockBuilder).instanceSupplier = builder -> new ExtShapeSlabBlock.WithExtension(builder.baseBlock, builder.blockSettings, blockExtension);
      if (blockShape == BlockShape.QUARTER_PIECE) ((AbstractBlockBuilder<QuarterPieceBlock>) abstractBlockBuilder).instanceSupplier = builder -> new ExtShapeQuarterPieceBlock.WithExtension(builder.baseBlock, builder.blockSettings, blockExtension);
      if (blockShape == BlockShape.VERTICAL_STAIRS) ((AbstractBlockBuilder<VerticalStairsBlock>) abstractBlockBuilder).instanceSupplier = builder -> new ExtShapeVerticalStairsBlock.WithExtension(builder.baseBlock, builder.blockSettings, blockExtension);
      if (blockShape == BlockShape.VERTICAL_SLAB) ((AbstractBlockBuilder<VerticalSlabBlock>) abstractBlockBuilder).instanceSupplier = builder -> new ExtShapeVerticalSlabBlock.WithExtension(builder.baseBlock, builder.blockSettings, blockExtension);
      if (blockShape == BlockShape.VERTICAL_QUARTER_PIECE) ((AbstractBlockBuilder<VerticalQuarterPieceBlock>) abstractBlockBuilder).instanceSupplier = builder -> new ExtShapeVerticalQuarterPieceBlock.WithExtension(builder.baseBlock, builder.blockSettings, blockExtension);
    };
    return this;
  }

  @SuppressWarnings("unchecked")
  @Contract(value = "-> this", mutates = "this")
  public BlocksBuilder setPillar() {
    blockBuilderConsumer = (blockShape, abstractBlockBuilder) -> {
      if (blockShape == BlockShape.SLAB) {
        ((AbstractBlockBuilder<SlabBlock>) abstractBlockBuilder).instanceSupplier = builder -> new ExtShapePillarSlabBlock(builder.baseBlock, builder.blockSettings);
      } else if (blockShape == BlockShape.VERTICAL_SLAB) {
        ((AbstractBlockBuilder<VerticalSlabBlock>) abstractBlockBuilder).instanceSupplier = builder -> new ExtShapePillarVerticalSlabBlock(builder.baseBlock, builder.blockSettings);
      } else if (baseBlock.getStateManager().getProperties().contains(Properties.AXIS)) {
        abstractBlockBuilder.blockSettings.mapColor(((AbstractBlockStateAccessor) baseBlock.getDefaultState().with(Properties.AXIS, Direction.Axis.X)).getMapColor());
      }
    };
    return this;
  }

  /**
   * 构造此形状的变种。
   *
   * @param shape 形状。
   */
  @Contract(value = "_ -> this", mutates = "this")
  public BlocksBuilder with(BlockShape shape) {
    shapesToBuild.add(shape);
    return this;
  }

  /**
   * 构造这些形状的变种。
   *
   * @param shapes 形状。
   */
  @Contract(value = "_ -> this", mutates = "this")
  public BlocksBuilder with(BlockShape... shapes) {
    Collections.addAll(shapesToBuild, shapes);
    return this;
  }

  /**
   * 不构造此形状的变种。
   *
   * @param shape 形状。
   */
  @Contract(value = "_ -> this", mutates = "this")
  public BlocksBuilder without(BlockShape shape) {
    shapesToBuild.remove(shape);
    return this;
  }

  /**
   * 不构造这些形状的变种。
   *
   * @param shapes 形状。
   */
  @Contract(value = "_ -> this", mutates = "this")
  public BlocksBuilder without(BlockShape... shapes) {
    for (BlockShape shape : shapes) {
      shapesToBuild.remove(shape);
    }
    return this;
  }

  /**
   * 构造基础形状。
   * 基础形状包括楼梯、台阶、垂直楼梯等。
   */
  @Contract(value = "-> this", mutates = "this")
  public BlocksBuilder withConstructionShapes() {
    return this.with(BlockShape.STAIRS, BlockShape.SLAB, BlockShape.VERTICAL_QUARTER_PIECE, BlockShape.VERTICAL_STAIRS, BlockShape.VERTICAL_SLAB, BlockShape.QUARTER_PIECE);
  }

  /**
   * 不构造基础形状。
   */
  @Contract(value = "-> this", mutates = "this")
  public BlocksBuilder withoutConstructionShapes() {
    return this.without(BlockShape.STAIRS, BlockShape.SLAB, BlockShape.VERTICAL_QUARTER_PIECE, BlockShape.VERTICAL_SLAB, BlockShape.VERTICAL_STAIRS, BlockShape.QUARTER_PIECE);
  }

  /**
   * 构造栅栏和栅栏门，并指定合成材料。
   *
   * @param fenceCraftingIngredient 合成栅栏或栅栏门需要使用的第二合成材料。
   */
  @Contract(value = "_ -> this", mutates = "this")
  public BlocksBuilder withFences(@NotNull Item fenceCraftingIngredient) {
    shapesToBuild.add(BlockShape.FENCE);
    shapesToBuild.add(BlockShape.FENCE_GATE);
    this.fenceCraftingIngredient = fenceCraftingIngredient;
    return this;
  }

  /**
   * 不构造红石机关。按钮、压力板都将不会构造，栅栏门虽也属于红石机关但不受影响。
   */
  @Contract(value = "-> this", mutates = "this")
  public BlocksBuilder withoutRedstone() {
    shapesToBuild.add(BlockShape.BUTTON);
    shapesToBuild.add(BlockShape.PRESSURE_PLATE);
    return this;
  }


  /**
   * 构造按钮，并指定按钮类型。
   *
   * @param type 按钮类型。
   */
  @Contract(value = "_ -> this", mutates = "this")
  public BlocksBuilder withButton(@NotNull ExtShapeButtonBlock.ButtonType type) {
    shapesToBuild.add(BlockShape.BUTTON);
    this.buttonType = type;
    return this;
  }

  /**
   * 构造压力板，并指定压力板类型。
   *
   * @param type 压力板类型。
   */
  @Contract(value = "_, -> this", mutates = "this")
  public BlocksBuilder withPressurePlate(@NotNull PressurePlateBlock.ActivationRule type) {
    shapesToBuild.add(BlockShape.PRESSURE_PLATE);
    this.pressurePlateActivationRule = type;
    return this;
  }

  /**
   * 设置指定形状的方块的默认方块标签。
   *
   * @param shape 形状。
   * @param tag   默认方块标签。
   */
  @Contract(value = "_, _, -> this", mutates = "this")
  public BlocksBuilder setTagToAddForShape(@Nullable BlockShape shape, @Nullable ExtShapeBlockTag tag) {
    if (shape == null || tag == null) return this;
    tagToAddForShape.put(shape, tag);
    return this;
  }

  /**
   * 设置各个形状的方块需要添加的方块标签。会覆盖已有的值。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_, -> this", mutates = "this")
  public BlocksBuilder setTagToAddForShape(Map<BlockShape, ExtShapeBlockTag> map) {
    tagToAddForShape = map;
    return this;
  }

  /**
   * 将构造后的所有方块都放入同一个标签中。
   *
   * @param tag 构造后所有的方块都需要放入的标签。
   */
  @Contract(value = "_ -> this", mutates = "this")
  public BlocksBuilder addTagToAddEach(@NotNull ExtShapeBlockTag tag) {
    this.tagsToAddEach.add(tag);
    return this;
  }

  /**
   * 和 {@link #forEach} 类似，但是会返回以允许串联。
   */
  @Contract(value = "_-> this", mutates = "this")
  public BlocksBuilder setConsumesEach(BiConsumer<BlockShape, AbstractBlockBuilder<? extends Block>> biConsumer) {
    if (blockBuilderConsumer == null) {
      blockBuilderConsumer = biConsumer;
    } else {
      blockBuilderConsumer = blockBuilderConsumer.andThen(biConsumer);
    }
    return this;
  }

  /**
   * 对各个构建器的方块设置应用 BiConsumer。可用于一次性修改所有将要构建的方块的方块设置，然后串联。
   */
  @Contract(value = "_-> this", mutates = "this")
  public BlocksBuilder consumeEachSettings(BiConsumer<BlockShape, AbstractBlock.Settings> biConsumer) {
    setConsumesEach((blockShape, builder) -> biConsumer.accept(blockShape, builder.blockSettings));
    return this;
  }

  public <T extends Block> BlocksBuilder setBuilderOf(BlockShape shape, AbstractBlockBuilder<T> builder) {
    put(shape, builder);
    return this;
  }

  /**
   * 进行构建。构建后不会返回。
   */
  public void build() {
    for (final BlockShape shape : shapesToBuild) {
      // 自动排除现成的。
      if (BlockMappings.getBlockOf(shape, baseBlock) == null && !this.containsKey(shape)) {
        final @Nullable AbstractBlockBuilder<? extends Block> blockBuilder;
        blockBuilder = createBlockBuilderFor(shape);
        if (blockBuilder != null) {
          this.put(shape, blockBuilder);
        }
      }
    }

    final Collection<AbstractBlockBuilder<? extends Block>> values = this.values();
    for (Map.Entry<BlockShape, ExtShapeBlockTag> entry : this.tagToAddForShape.entrySet()) {
      AbstractBlockBuilder<?> builder = this.get(entry.getKey());
      if (builder != null && entry.getValue() != null) builder.setDefaultTagToAdd(entry.getValue());
    }
    for (AbstractBlockBuilder<? extends Block> builder : values) {
      if (this.baseBlock.asItem().isFireproof()) builder.itemSettings.fireproof();
      tagsToAddEach.forEach(builder::addTagToAdd);
    }
    if (blockBuilderConsumer != null) {
      forEach(blockBuilderConsumer);
    }
    values.forEach(AbstractBlockBuilder::build);
  }

  /**
   * 根据指定的形状，为这个 BlocksBuilder 对象创建该方块的 BlockBuilder。注意这个 {@code shape} 只能是本模组内置的 11 个方块形状，不能是自行添加的，但是你可以创建子类修改此方法的行为。
   *
   * @param shape 方块形状。仅限 {@link BlockShape} 中预置的 11 种。
   * @return 此对象的 {@link #baseBlock} 的此形状的变种的 {@link BlocksBuilder}。
   * @throws IllegalArgumentException 如果提供的 {@code shape} 参数不是本模组预置的 11 种之一（子类覆盖了此方法的除外）。
   */
  @Contract(pure = true)
  @Nullable
  protected AbstractBlockBuilder<? extends Block> createBlockBuilderFor(@NotNull BlockShape shape) {
    final int id = shape.id;
    return switch (id) {
      case 0 -> new StairsBuilder(baseBlock);
      case 1 -> new SlabBuilder(baseBlock);
      case 2 -> new VerticalSlabBuilder(baseBlock);
      case 3 -> new VerticalStairsBuilder(baseBlock);
      case 4 -> new QuarterPieceBuilder(baseBlock);
      case 5 -> new VerticalQuarterPieceBuilder(baseBlock);
      case 6 -> new FenceBuilder(baseBlock, fenceCraftingIngredient);
      case 7 -> new FenceGateBuilder(baseBlock, fenceCraftingIngredient);
      case 8 -> new WallBuilder(baseBlock);
      case 9 -> buttonType != null ? new ButtonBuilder(buttonType, baseBlock) : null;
      case 10 -> pressurePlateActivationRule != null ? new PressurePlateBuilder(pressurePlateActivationRule, baseBlock) : null;
      default -> throw new IllegalArgumentException("The Shape object " + shape.asString() + " is not supported, which may be provided by other mod. You may extend BlocksBuilder class and define your own 'createBlockBuilderFor' with support for your Shape object.");
    };
  }
}
