package pers.solid.extshape.builder;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.*;
import pers.solid.extshape.mixin.AbstractBlockStateAccessor;
import pers.solid.extshape.rrp.RecipeGroupRegistry;
import pers.solid.extshape.tag.TagPreparations;
import pers.solid.extshape.util.BlockBiMaps;
import pers.solid.extshape.util.FenceSettings;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>此类相当于将一个基础方块的多个形状的构建器整合到一起，其本质为从方块形状到对应方块构建器的映射。
 * <p>调用其方法时，修改此对象自身的字段，但不会进行实际构建，也不会创建各 {@link AbstractBlockBuilder} 对象。调用 {@link #build()} 之后，才会创建各 {@link AbstractBlockBuilder} 对象，并调用 {@link AbstractBlockBuilder#build()} 方法，这时候才产生方块对象，并根据参数进行一系列操作，如加入注册表、标签等。
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
   * 为指定形状的 {@link AbstractBlockBuilder} 调用 {@link AbstractBlockBuilder#setPrimaryTagToAddTo}。
   */
  private @Nullable Map<@NotNull BlockShape, @Nullable TagKey<? extends ItemConvertible>> primaryTagForShape = null;
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
  protected final List<@NotNull TagKey<? extends ItemConvertible>> extraTags = new ArrayList<>();
  protected TagPreparations tagPreparations;

  public BlocksBuilder setFenceSettings(FenceSettings fenceSettings) {
    this.fenceSettings = fenceSettings;
    return this;
  }

  public BlocksBuilder setCommonFenceSettings(Item secondIngredient) {
    return this.setFenceSettings(FenceSettings.common(secondIngredient));
  }

  public BlocksBuilder setButtonType(ExtShapeButtonBlock.ButtonType buttonType) {
    this.buttonType = buttonType;
    return this;
  }

  public BlocksBuilder setPressurePlateActivationRule(PressurePlateBlock.ActivationRule pressurePlateActivationRule) {
    this.pressurePlateActivationRule = pressurePlateActivationRule;
    return this;
  }

  protected @Nullable FenceSettings fenceSettings;
  protected @Nullable ExtShapeButtonBlock.ButtonType buttonType;
  protected @Nullable PressurePlateBlock.ActivationRule pressurePlateActivationRule;
  /**
   * 在执行 {@link #build()} 之前会为每个值执行。
   */
  protected @Nullable BiConsumer<BlockShape, AbstractBlockBuilder<?>> preBuildConsumer;
  /**
   * 在执行 {@link #build()} 之后会为每个值执行。
   */
  protected @Nullable BiConsumer<BlockShape, AbstractBlockBuilder<?>> postBuildConsumer;

  public BlocksBuilder(@NotNull Block baseBlock, SortedSet<BlockShape> shapesToBuild) {
    this.baseBlock = baseBlock;
    this.shapesToBuild = shapesToBuild;
    this.fenceSettings = FenceSettings.DEFAULT;
    this.buttonType = ExtShapeButtonBlock.ButtonType.STONE;
    this.pressurePlateActivationRule = PressurePlateBlock.ActivationRule.MOBS;
  }

  /**
   * 设置其构建的方块可能需要实现的扩展功能。
   */
  @Contract(value = "_ -> this", mutates = "this")
  public BlocksBuilder withExtension(@NotNull BlockExtension blockExtension) {
    return addPreBuildConsumer((blockShape, abstractBlockBuilder) -> abstractBlockBuilder.withExtension(blockExtension));
  }

  @SuppressWarnings({"unchecked", "RedundantCast"})
  @Contract(value = "-> this", mutates = "this")
  public BlocksBuilder setPillar() {
    return addPreBuildConsumer((blockShape, abstractBlockBuilder) -> {
      if (blockShape == BlockShape.SLAB) {
        ((AbstractBlockBuilder<SlabBlock>) abstractBlockBuilder).instanceSupplier = builder -> new ExtShapePillarSlabBlock(builder.baseBlock, builder.blockSettings);
      } else if (blockShape == BlockShape.VERTICAL_SLAB) {
        ((AbstractBlockBuilder<VerticalSlabBlock>) abstractBlockBuilder).instanceSupplier = builder -> new ExtShapePillarVerticalSlabBlock(builder.baseBlock, builder.blockSettings);
      } else if (baseBlock.getStateManager().getProperties().contains(Properties.AXIS)) {
        abstractBlockBuilder.blockSettings.mapColor(((AbstractBlockStateAccessor) baseBlock.getDefaultState().with(Properties.AXIS, Direction.Axis.X)).getMapColor());
      }
    });
  }

  @SuppressWarnings({"unchecked", "RedundantCast"})
  @Contract(value = "-> this", mutates = "this")
  public BlocksBuilder setPillarUvLocked() {
    return addPreBuildConsumer((blockShape, abstractBlockBuilder) -> {
      if (blockShape == BlockShape.SLAB) {
        ((AbstractBlockBuilder<SlabBlock>) abstractBlockBuilder).instanceSupplier = builder -> new ExtShapePillarUvLockedSlabBlock(builder.baseBlock, builder.blockSettings);
      } /*else if (blockShape == BlockShape.VERTICAL_SLAB) {
        ((AbstractBlockBuilder<VerticalSlabBlock>) abstractBlockBuilder).instanceSupplier = builder -> new ExtShapePillarVerticalSlabBlock(builder.baseBlock, builder.blockSettings);
      } */ else if (baseBlock.getStateManager().getProperties().contains(Properties.AXIS)) {
        abstractBlockBuilder.blockSettings.mapColor(((AbstractBlockStateAccessor) baseBlock.getDefaultState().with(Properties.AXIS, Direction.Axis.X)).getMapColor());
      }
    });
  }

  @Contract(value = "_ -> this", mutates = "this")
  public BlocksBuilder setPillar(boolean uvLocked) {
    return uvLocked ? setPillarUvLocked() : setPillar();
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
  public BlocksBuilder withButton(@NotNull ExtShapeButtonBlock.ButtonType type) {
    with(BlockShape.BUTTON);
    this.buttonType = type;
    return this;
  }

  /**
   * 设置需要构建压力板，并指定压力板类型。
   *
   * @param type 压力板类型。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_, -> this", mutates = "this")
  public BlocksBuilder withPressurePlate(@NotNull PressurePlateBlock.ActivationRule type) {
    with(BlockShape.PRESSURE_PLATE);
    this.pressurePlateActivationRule = type;
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
  public BlocksBuilder setPrimaryTagForShape(@Nullable BlockShape shape, @Nullable TagKey<? extends ItemConvertible> tag) {
    if (shape == null || tag == null) return this;
    if (primaryTagForShape == null) {
      primaryTagForShape = new HashMap<>();
    }
    primaryTagForShape.put(shape, tag);
    return this;
  }

  /**
   * 设置各个形状的方块需要添加的方块标签。会覆盖已有的值。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_, -> this", mutates = "this")
  public BlocksBuilder setPrimaryTagForShape(Map<BlockShape, TagKey<? extends ItemConvertible>> map) {
    primaryTagForShape = map;
    return this;
  }

  /**
   * 将构造后的所有方块都放入同一个标签中。
   *
   * @param tag 构造后所有的方块都需要放入的标签。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_ -> this", mutates = "this")
  public BlocksBuilder addExtraTag(@NotNull TagKey<? extends ItemConvertible> tag) {
    this.extraTags.add(tag);
    return this;
  }

  /**
   * 将构造后的所有方块添加到依照函数指定的标签中。
   *
   * @param function 根据形状来判断需要将方块添加到的标签。该函数将在构造前执行。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_ -> this", mutates = "this")
  public BlocksBuilder addExtraTag(Function<BlockShape, @Nullable TagKey<? extends ItemConvertible>> function) {
    return addPreBuildConsumer((blockShape, builder) -> {
      final TagKey<? extends ItemConvertible> tag = function.apply(blockShape);
      if (tag != null) builder.addExtraTag(tag);
    });
  }

  /**
   * 添加一个将会在构建各方块对象前执行的 consumer。注意，此时方块还没有被创建，也没有被注册，可以执行 {@link AbstractBlockBuilder#setBlockSettings}、{@link AbstractBlockBuilder#addExtraTag} 等需要在之前创建对象之间执行的操作。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_-> this", mutates = "this")
  public BlocksBuilder addPreBuildConsumer(BiConsumer<BlockShape, AbstractBlockBuilder<? extends Block>> biConsumer) {
    if (preBuildConsumer == null) {
      preBuildConsumer = biConsumer;
    } else if (biConsumer != null) {
      preBuildConsumer = preBuildConsumer.andThen(biConsumer);
    }
    return this;
  }

  /**
   * 添加一个将会在构建各方块对象之后执行的 consumer。此时 {@link AbstractBlockBuilder#build()} 已经完成，可以通过 {@link AbstractBlockBuilder#instance} 获取到已经创建好的对象。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_-> this", mutates = "this")
  public BlocksBuilder addPostBuildConsumer(BiConsumer<BlockShape, AbstractBlockBuilder<? extends Block>> biConsumer) {
    if (postBuildConsumer == null) {
      postBuildConsumer = biConsumer;
    } else if (biConsumer != null) {
      postBuildConsumer = postBuildConsumer.andThen(biConsumer);
    }
    return this;
  }

  @CanIgnoreReturnValue
  @Contract(value = "_-> this", mutates = "this")
  public BlocksBuilder setRecipeGroup(Function<BlockShape, String> function) {
    addPostBuildConsumer((blockShape, blockBuilder) -> RecipeGroupRegistry.setRecipeGroup(blockBuilder.instance, function.apply(blockShape)));
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
    // 设置需要将构建后的方块都添加到指定的标签中。
    for (AbstractBlockBuilder<? extends Block> builder : values) {
      builder.extraTags.addAll(extraTags);
    }
    if (preBuildConsumer != null) {
      forEach(preBuildConsumer);
    }
    values.forEach(AbstractBlockBuilder::build);
    if (postBuildConsumer != null) {
      forEach(postBuildConsumer);
    }
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
      case 9 -> buttonType != null ? new ButtonBuilder(buttonType, baseBlock) : null;
      case 10 -> pressurePlateActivationRule != null ? new PressurePlateBuilder(pressurePlateActivationRule, baseBlock) : null;
      default -> throw new IllegalArgumentException("The Shape object " + shape.asString() + " is not supported, which may be provided by other mod. You may extend BlocksBuilder class and define your own 'createBlockBuilderFor' with support for your Shape object.");
    };
    if (builder != null) {
      builder.defaultNamespace = this.defaultNamespace;
      builder.instanceCollection = this.instanceCollection;
      builder.tagPreparations = this.tagPreparations;
      if (primaryTagForShape != null) {
        builder.primaryTagToAddTo = primaryTagForShape.get(shape);
      }
    }
    return builder;
  }
}
