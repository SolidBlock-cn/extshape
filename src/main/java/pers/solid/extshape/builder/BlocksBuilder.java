package pers.solid.extshape.builder;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.item.Item;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapeButtonBlock;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * 　　由多个方块构造器组成的一个从形状到构造器的映射。<br>
 * 　　用其方法时，会修改构造器参数，但不会进行实际构造，而调用 {@link #build()} 之后，就会正式执行构造，将会调用这些构造器的 <code>build</code>方法，这时候才产生方块对象，并根据参数进行一系列操作，如加入注册表、标签等。<br>
 * 　　调用这些 {@code build} 方法时，会自动往运行时资源包（RRP）中添加内容，同时 {@link #build()} 会添加一些方块之间的转换配方，如台阶与纵台阶的合成配方。
 */
public class BlocksBuilder extends HashMap<BlockShape, AbstractBlockBuilder<? extends Block>> {
  public final Map<@NotNull BlockShape, @Nullable ExtShapeBlockTag> defaultTags = new HashMap<>();
  private final Set<BlockShape> shapesToBuild;

  public final @NotNull Block baseBlock;
  private final List<ExtShapeBlockTag> tagsToAddEach = new ArrayList<>();
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
  public BlocksBuilder(@NotNull Block baseBlock, @Nullable Item fenceCraftingIngredient, ExtShapeButtonBlock.@Nullable ButtonType buttonType, PressurePlateBlock.@Nullable ActivationRule pressurePlateActivationRule, Set<BlockShape> shapesToBuild) {
    super(BlockShape.values().size());
    this.fenceCraftingIngredient = fenceCraftingIngredient;
    this.buttonType = buttonType;
    this.pressurePlateActivationRule = pressurePlateActivationRule;
    this.baseBlock = baseBlock;
    this.shapesToBuild = shapesToBuild;
  }

  public static BlocksBuilder createComprehensive(@NotNull Block baseBlock, @Nullable Item fenceCraftingIngredient, ExtShapeButtonBlock.@Nullable ButtonType buttonType, PressurePlateBlock.@Nullable ActivationRule pressurePlateActivationRule) {
    return new BlocksBuilder(baseBlock, fenceCraftingIngredient, buttonType, pressurePlateActivationRule, new HashSet<>(BlockShape.values()));
  }

  public static BlocksBuilder createEmpty(@NotNull Block baseBlock) {
    return new BlocksBuilder(baseBlock, null, null, null, new HashSet<>());
  }

  public static BlocksBuilder createConstructionOnly(@NotNull Block baseBlock) {
    return createEmpty(baseBlock).withConstructionShapes();
  }

  /**
   * 构造这些形状的变种。
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
   * 不构造这些形状的变种。
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
   * 构造基础形状。
   * 基础形状包括楼梯、台阶、垂直楼梯等。
   */
  @CanIgnoreReturnValue
  @Contract(value = "-> this", mutates = "this")
  public BlocksBuilder withConstructionShapes() {
    return this.with(BlockShape.STAIRS, BlockShape.SLAB, BlockShape.VERTICAL_QUARTER_PIECE, BlockShape.VERTICAL_STAIRS, BlockShape.VERTICAL_SLAB, BlockShape.QUARTER_PIECE);
  }

  /**
   * 不构造基础形状。
   */
  @CanIgnoreReturnValue
  @Contract(value = "-> this", mutates = "this")
  public BlocksBuilder withoutConstructionShapes() {
    return this.without(BlockShape.STAIRS, BlockShape.SLAB, BlockShape.VERTICAL_QUARTER_PIECE, BlockShape.VERTICAL_SLAB, BlockShape.VERTICAL_STAIRS, BlockShape.QUARTER_PIECE);
  }

  /**
   * 构造栅栏和栅栏门，并指定合成材料。
   *
   * @param fenceCraftingIngredient 合成栅栏或栅栏门需要使用的第二合成材料。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_ -> this", mutates = "this")
  public BlocksBuilder withFences(@NotNull Item fenceCraftingIngredient) {
    shapesToBuild.add(BlockShape.FENCE);
    shapesToBuild.add(BlockShape.FENCE_GATE);
    this.fenceCraftingIngredient = fenceCraftingIngredient;
    return this;
  }

  /**
   * 不构造墙。
   */
  @CanIgnoreReturnValue
  @Contract(value = "-> this", mutates = "this")
  public BlocksBuilder withoutWall() {
    shapesToBuild.add(BlockShape.WALL);
    return this;
  }

  /**
   * 构造墙。
   */
  @CanIgnoreReturnValue
  @Contract(value = "-> this", mutates = "this")
  public BlocksBuilder withWall() {
    shapesToBuild.add(BlockShape.WALL);
    return this;
  }

  /**
   * 不构造红石机关。按钮、压力板都将不会构造，栅栏门虽也属于红石机关但不受影响。
   */
  @CanIgnoreReturnValue
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
  @CanIgnoreReturnValue
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
  @CanIgnoreReturnValue
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
  @CanIgnoreReturnValue
  @Contract(value = "_, _, -> this", mutates = "this")
  public BlocksBuilder setDefaultTagOf(@Nullable BlockShape shape, @Nullable ExtShapeBlockTag tag) {
    if (shape == null || tag == null) return this;
    defaultTags.put(shape, tag);
    return this;
  }

  /**
   * 将构造后的所有方块都放入同一个标签中。
   *
   * @param tag 构造后所有的方块都需要放入的标签。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_ -> this", mutates = "this")
  public BlocksBuilder addTagToAddEach(ExtShapeBlockTag tag) {
    this.tagsToAddEach.add(tag);
    return this;
  }

  @CanIgnoreReturnValue
  @Contract(value = "_-> this", mutates = "this")
  public BlocksBuilder consumeEach(BiConsumer<BlockShape, AbstractBlockBuilder<? extends Block>> biConsumer) {
    forEach(biConsumer);
    return this;
  }

  @CanIgnoreReturnValue
  @Contract(value = "_-> this", mutates = "this")
  public BlocksBuilder consumeEachSettings(BiConsumer<BlockShape, AbstractBlock.Settings> consumer) {
    forEach((blockShape, builder) -> consumer.accept(blockShape, builder.blockSettings));
    return this;
  }

  /**
   * 进行构造。构造后不会返回。
   */
  public void build() {
    for (final BlockShape shape : shapesToBuild) {
      // 自动排除现成的。
      if (BlockMappings.getBlockOf(shape, baseBlock) == null) {
        final @Nullable AbstractBlockBuilder<? extends Block> blockBuilder;
        if (shape == BlockShape.FENCE) {
          blockBuilder = fenceCraftingIngredient == null ? null : new FenceBuilder(baseBlock, fenceCraftingIngredient);
        } else if (shape == BlockShape.FENCE_GATE) {
          blockBuilder = fenceCraftingIngredient == null ? null : new FenceGateBuilder(baseBlock, fenceCraftingIngredient);
        } else if (shape == BlockShape.BUTTON) {
          blockBuilder = buttonType != null ? new ButtonBuilder(buttonType, baseBlock) : null;
        } else if (shape == BlockShape.PRESSURE_PLATE) {
          blockBuilder = pressurePlateActivationRule != null ? new PressurePlateBuilder(pressurePlateActivationRule, baseBlock) : null;
        } else {
          blockBuilder = shape.createBuilder(baseBlock);
        }
        if (blockBuilder != null) {
          this.put(shape, blockBuilder);
        }
      }
    }

    final Collection<AbstractBlockBuilder<? extends Block>> values = this.values();
    for (Entry<BlockShape, ExtShapeBlockTag> entry : this.defaultTags.entrySet()) {
      AbstractBlockBuilder<?> builder = this.get(entry.getKey());
      if (builder != null && entry.getValue() != null) builder.setDefaultTagToAdd(entry.getValue());
    }
    for (AbstractBlockBuilder<? extends Block> builder : values) {
      if (this.baseBlock.asItem().isFireproof()) builder.itemSettings.fireproof();
      tagsToAddEach.forEach(builder::addTagToAdd);
    }
    values.forEach(AbstractBlockBuilder::build);
  }
}
