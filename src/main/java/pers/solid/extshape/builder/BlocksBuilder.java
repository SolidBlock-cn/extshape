package pers.solid.extshape.builder;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.minecraft.block.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.*;
import pers.solid.extshape.mixin.AbstractBlockStateAccessor;
import pers.solid.extshape.tag.BlockTagPreparation;
import pers.solid.extshape.util.BlockBiMaps;
import pers.solid.extshape.util.ButtonSettings;
import pers.solid.extshape.util.FenceSettings;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * <p>此类相当于将一个基础方块的多个形状的构建器整合到一起，其本质为从方块形状到对应方块构建器的映射。
 * <p>调用其方法时，会修改构造器参数，但不会进行实际构建，而调用 {@link #build()} 之后，就会正式执行构建，将会调用这些构建器的 {@link AbstractBlockBuilder#build()} 方法，这时候才产生方块对象，并根据参数进行一系列操作，如加入注册表、标签等。
 */
public class BlocksBuilder extends TreeMap<BlockShape, AbstractBlockBuilder<? extends Block>> {
  private static final BlockShape[] CONSTRUCTION_SHAPES = {BlockShape.STAIRS, BlockShape.SLAB, BlockShape.VERTICAL_QUARTER_PIECE, BlockShape.VERTICAL_SLAB, BlockShape.VERTICAL_STAIRS, BlockShape.QUARTER_PIECE};
  /**
   * 构建方块时使用的命名空间。其引用会传递到 {@link AbstractBlockBuilder#defaultNamespace} 中。
   */
  protected @Nullable String defaultNamespace;
  /**
   * 构建方块后将方块加到这个集合中。其引用会传递到 {@link AbstractBlockBuilder#instanceCollection}。
   */
  protected @Nullable Collection<Block> instanceCollection;

  /**
   * 为指定形状的 {@link AbstractBlockBuilder} 调用 {@link AbstractBlockBuilder#setDefaultTagToAdd(BlockTagPreparation) #setDefaultTagToAdd}。
   */
  private Map<@NotNull BlockShape, @Nullable BlockTagPreparation> tagToAddForShape = new HashMap<>();
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
  protected final List<@NotNull BlockTagPreparation> tagsToAddEach = new ArrayList<>();
  protected @Nullable FenceSettings fenceSettings;
  protected @Nullable ButtonSettings buttonSettings;
  protected @Nullable PressurePlateBlock.ActivationRule pressurePlateActivationRule;
  /**
   * 在执行 {@link #build()} 之前会为每个值执行。
   */
  protected @Nullable BiConsumer<BlockShape, AbstractBlockBuilder<?>> blockBuilderConsumer;
  protected @Nullable BlockSetType blockSetType;

  /**
   * 根据一个基础方块，构造其多个变种方块。需要提供其中部分变种方块的参数。
   *
   * @param baseBlock                   基础方块。
   * @param fenceSettings               合成栅栏和栅栏门时，需要使用的第二合成材料以及栅栏门的声音。
   * @param buttonSettings              按钮类型。
   * @param pressurePlateActivationRule 压力板激活类型。
   * @param shapesToBuild               需要构建哪些方块形状。
   */
  public BlocksBuilder(@NotNull Block baseBlock, @Nullable FenceSettings fenceSettings, @Nullable ButtonSettings buttonSettings, PressurePlateBlock.@Nullable ActivationRule pressurePlateActivationRule, @Nullable BlockSetType blockSetType, SortedSet<BlockShape> shapesToBuild) {
    this.fenceSettings = fenceSettings;
    this.buttonSettings = buttonSettings;
    this.pressurePlateActivationRule = pressurePlateActivationRule;
    this.blockSetType = blockSetType;
    this.baseBlock = baseBlock;
    this.shapesToBuild = shapesToBuild;
  }

  /**
   * 设置其构建的方块可能需要实现的扩展功能。
   */
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

  @SuppressWarnings({"unchecked", "RedundantCast"})
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

  public BlocksBuilder setPillar(boolean uvLocked) {
    return uvLocked ? setPillarUvLocked() : setPillar();
  }

  @SuppressWarnings({"unchecked", "RedundantCast"})
  @Contract(value = "-> this", mutates = "this")
  public BlocksBuilder setPillarUvLocked() {
    blockBuilderConsumer = (blockShape, abstractBlockBuilder) -> {
      if (blockShape == BlockShape.SLAB) {
        ((AbstractBlockBuilder<SlabBlock>) abstractBlockBuilder).instanceSupplier = builder -> new ExtShapePillarUvLockedSlabBlock(builder.baseBlock, builder.blockSettings);
      } /*else if (blockShape == BlockShape.VERTICAL_SLAB) {
        ((AbstractBlockBuilder<VerticalSlabBlock>) abstractBlockBuilder).instanceSupplier = builder -> new ExtShapePillarVerticalSlabBlock(builder.baseBlock, builder.blockSettings);
      } */ else if (baseBlock.getStateManager().getProperties().contains(Properties.AXIS)) {
        abstractBlockBuilder.blockSettings.mapColor(((AbstractBlockStateAccessor) baseBlock.getDefaultState().with(Properties.AXIS, Direction.Axis.X)).getMapColor());
      }
    };
    return this;
  }

  /**
   * 设置需要构建此形状的变种。
   *
   * @param shape 形状。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_ -> this", mutates = "this")
  public BlocksBuilder with(BlockShape shape) {
    shapesToBuild.add(shape);
    return this;
  }

  /**
   * 设置需要构建这些形状的变种。
   *
   * @param shapes 形状。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_ -> this", mutates = "this")
  public BlocksBuilder with(BlockShape... shapes) {
    Collections.addAll(shapesToBuild, shapes);
    return this;
  }

  /**
   * 设置不需要构建此形状的变种。
   *
   * @param shape 形状。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_ -> this", mutates = "this")
  public BlocksBuilder without(BlockShape shape) {
    shapesToBuild.remove(shape);
    return this;
  }

  /**
   * 设置不需要构建这些形状的变种。
   *
   * @param shapes 形状。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_ -> this", mutates = "this")
  public BlocksBuilder without(BlockShape... shapes) {
    for (BlockShape shape : shapes) {
      shapesToBuild.remove(shape);
    }
    return this;
  }

  /**
   * 设置需要构建其建筑形状。建筑包括楼梯、台阶、垂直楼梯等。
   */
  @CanIgnoreReturnValue
  @Contract(value = "-> this", mutates = "this")
  public BlocksBuilder withConstructionShapes() {
    return this.with(CONSTRUCTION_SHAPES);
  }

  /**
   * 设置不需要构建其建筑形状。
   */
  @CanIgnoreReturnValue
  @Contract(value = "-> this", mutates = "this")
  public BlocksBuilder withoutConstructionShapes() {
    return this.without(CONSTRUCTION_SHAPES);
  }

  /**
   * 设置需要构建栅栏和栅栏门，并指定合成材料。
   *
   * @param fenceSettings 合成栅栏或栅栏门需要使用的第二合成材料以及声音。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_ -> this", mutates = "this")
  public BlocksBuilder withFences(@NotNull FenceSettings fenceSettings) {
    with(BlockShape.FENCE, BlockShape.FENCE_GATE);
    this.fenceSettings = fenceSettings;
    return this;
  }

  /**
   * 设置不要构建红石机关。按钮、压力板都将不会构造，栅栏门虽也属于红石机关但不受此方法影响。
   */
  @CanIgnoreReturnValue
  @Contract(value = "-> this", mutates = "this")
  public BlocksBuilder withoutRedstone() {
    without(BlockShape.BUTTON, BlockShape.PRESSURE_PLATE);
    return this;
  }


  /**
   * 设置需要构建按钮，并指定按钮类型。
   *
   * @param type 按钮类型。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_ -> this", mutates = "this")
  public BlocksBuilder withButton(@NotNull ButtonSettings type) {
    with(BlockShape.BUTTON);
    this.buttonSettings = type;
    return this;
  }

  /**
   * 设置需要构建压力板，并指定压力板类型。
   *
   * @param type 压力板类型。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_, _ -> this", mutates = "this")
  public BlocksBuilder withPressurePlate(@NotNull PressurePlateBlock.ActivationRule type, @NotNull BlockSetType blockSetType) {
    with(BlockShape.PRESSURE_PLATE);
    this.pressurePlateActivationRule = type;
    this.blockSetType = blockSetType;
    return this;
  }

  /**
   * 设置指定形状的方块的默认方块标签。
   *
   * @param shape 形状。
   * @param tag   默认方块标签。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_, _, -> this", mutates = "this")
  public BlocksBuilder setTagToAddForShape(@Nullable BlockShape shape, @Nullable BlockTagPreparation tag) {
    if (shape == null || tag == null) return this;
    tagToAddForShape.put(shape, tag);
    return this;
  }

  /**
   * 设置各个形状的方块需要添加的方块标签。会覆盖已有的值。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_, -> this", mutates = "this")
  public BlocksBuilder setTagToAddForShape(Map<BlockShape, BlockTagPreparation> map) {
    tagToAddForShape = map;
    return this;
  }

  /**
   * 将构造后的所有方块都放入同一个标签中。
   *
   * @param tag 构造后所有的方块都需要放入的标签。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_ -> this", mutates = "this")
  public BlocksBuilder addTagToAddEach(@NotNull BlockTagPreparation tag) {
    this.tagsToAddEach.add(tag);
    return this;
  }

  /**
   * 和 {@link #forEach} 类似，但是会返回以允许串联。
   */
  @CanIgnoreReturnValue
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
  @CanIgnoreReturnValue
  @Contract(value = "_-> this", mutates = "this")
  public BlocksBuilder consumeEachSettings(BiConsumer<BlockShape, AbstractBlock.Settings> biConsumer) {
    setConsumesEach((blockShape, builder) -> biConsumer.accept(blockShape, builder.blockSettings));
    return this;
  }

  /**
   * 进行构建。构建后不会返回。
   */
  public void build() {
    // 只有 shapesToBuild 中的形状会被使用。
    for (final BlockShape shape : shapesToBuild) {
      // 如果已经存在指定基础方块对应的形状的方块，那么不会重复构建。
      if (BlockBiMaps.getBlockOf(shape, baseBlock) == null && !this.containsKey(shape)) {
        final @Nullable AbstractBlockBuilder<? extends Block> blockBuilder;
        blockBuilder = createBlockBuilderFor(shape);
        if (blockBuilder != null) {
          this.put(shape, blockBuilder);
        }
      }
    }

    final Collection<AbstractBlockBuilder<? extends Block>> values = this.values();

    // 为各个形状的构建器设置对应的默认标签。
    for (Map.Entry<BlockShape, BlockTagPreparation> entry : this.tagToAddForShape.entrySet()) {
      AbstractBlockBuilder<?> builder = this.get(entry.getKey());
      if (builder != null && entry.getValue() != null) builder.setDefaultTagToAdd(entry.getValue());
    }

    // 如果基础方块对应的物品能够防火，那么其构建后产生的方块对应的物品也应该防火。
    if (this.baseBlock.asItem().isFireproof()) {
      values.forEach(builder -> builder.itemSettings.fireproof());
    }

    // 设置需要将构建后的方块都添加到指定的标签中。
    for (AbstractBlockBuilder<? extends Block> builder : values) {
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
    final AbstractBlockBuilder<? extends Block> builder = switch (id) {
      case 0 -> new StairsBuilder(baseBlock);
      case 1 -> new SlabBuilder(baseBlock);
      case 2 -> new VerticalSlabBuilder(baseBlock);
      case 3 -> new VerticalStairsBuilder(baseBlock);
      case 4 -> new QuarterPieceBuilder(baseBlock);
      case 5 -> new VerticalQuarterPieceBuilder(baseBlock);
      case 6 -> fenceSettings == null ? null : new FenceBuilder(baseBlock, fenceSettings.secondIngredient());
      case 7 -> fenceSettings == null ? null : new FenceGateBuilder(baseBlock, fenceSettings);
      case 8 -> new WallBuilder(baseBlock);
      case 9 -> buttonSettings != null ? new ButtonBuilder(buttonSettings, baseBlock) : null;
      case 10 -> pressurePlateActivationRule != null ? new PressurePlateBuilder(pressurePlateActivationRule, baseBlock, blockSetType) : null;
      default -> throw new IllegalArgumentException("The Shape object " + shape.asString() + " is not supported, which may be provided by other mod. You may extend BlocksBuilder class and define your own 'createBlockBuilderFor' with support for your Shape object.");
    };
    if (builder != null) {
      builder.defaultNamespace = this.defaultNamespace;
      builder.instanceCollection = this.instanceCollection;
    }
    return builder;
  }
}
