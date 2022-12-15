package pers.solid.extshape.builder;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.item.Item;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapeButtonBlock;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * BlocksBuilderFactory 是用来生成 BlocksBuilder 的对象。通常来说，如果其他模组要使用本模组的接口的话，可以单独设置一个 BlocksBuilderFactory 对象。每个对象都可以指定一个命名空间，以及构建出来的方块自动加入到哪些集合中
 */
public class BlocksBuilderFactory {
  /**
   * 本模组内置的所有方块形状。注意这并不包括其他模组添加的方块形状。因此，其他模组如需使用 BlocksBuilder，需自行通过 {@link BlocksBuilder#with(BlockShape)} 添加。
   */
  private static final ImmutableSet<BlockShape> SHAPES = ImmutableSet.of(BlockShape.STAIRS, BlockShape.SLAB, BlockShape.VERTICAL_SLAB, BlockShape.VERTICAL_STAIRS, BlockShape.QUARTER_PIECE, BlockShape.VERTICAL_QUARTER_PIECE, BlockShape.FENCE, BlockShape.FENCE_GATE, BlockShape.WALL, BlockShape.BUTTON, BlockShape.PRESSURE_PLATE);
  /**
   * 生成的方块的默认命名空间。例如，在本模组中，即使石头的 id 为 {@code minecraft:stone}，但由于给 BlocksBuilderFactory 设置了 {@code extshape} 命名空间，因此其通过本模组产生的衍生方块都是 {@code extshape} 命名空间的，如 {@code extshape:stone_vertical_slab}。如果为 {@code null}，那么生成的方块都会直接使用其基础方块的命名空间。
   */
  protected @Nullable String defaultNamespace;
  /**
   * 将构建产生的方块都添加到这个集合中。
   */
  protected @Nullable Collection<Block> instanceCollection;
  /**
   * 将使用的基础方块添加到这个集合中。注意：在利用本对象来创建 {@link BlocksBuilder} 的时候，就会将其使用的基础方块添加到这个集合。
   */
  protected @Nullable Collection<Block> baseBlockCollection;

  private BlocksBuilderFactory() {
  }

  public static BlocksBuilderFactory create() {
    return new BlocksBuilderFactory();
  }

  public static BlocksBuilderFactory create(@Nullable String defaultNamespace, @Nullable Collection<Block> instanceCollection, Collection<Block> baseBlockCollection) {
    final BlocksBuilderFactory blocksBuilderFactory = new BlocksBuilderFactory();
    blocksBuilderFactory.defaultNamespace = defaultNamespace;
    blocksBuilderFactory.instanceCollection = instanceCollection;
    blocksBuilderFactory.baseBlockCollection = baseBlockCollection;
    return blocksBuilderFactory;
  }

  protected BlocksBuilder createInternal(@NotNull Block baseBlock, @Nullable Item fenceCraftingIngredient, ExtShapeButtonBlock.@Nullable ButtonType buttonType, PressurePlateBlock.@Nullable ActivationRule pressurePlateActivationRule, SortedSet<BlockShape> shapesToBuild) {
    final BlocksBuilder blocksBuilder = new BlocksBuilder(baseBlock, fenceCraftingIngredient, buttonType, pressurePlateActivationRule, shapesToBuild);
    blocksBuilder.defaultNamespace = defaultNamespace;
    blocksBuilder.instanceCollection = instanceCollection;
    if (baseBlockCollection != null) {
      baseBlockCollection.add(baseBlock);
    }
    return blocksBuilder;
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
  public BlocksBuilder createAllShapes(@NotNull Block baseBlock, @Nullable Item fenceCraftingIngredient, ExtShapeButtonBlock.@Nullable ButtonType buttonType, PressurePlateBlock.@Nullable ActivationRule pressurePlateActivationRule) {
    return createInternal(baseBlock, fenceCraftingIngredient, buttonType, pressurePlateActivationRule, new TreeSet<>(SHAPES));
  }

  /**
   * 创建一个 BlocksBuilder，但暂时不会创建任何形状的方块，可以在后续通过 {@link BlocksBuilder#with} 等方法添加。
   *
   * @param baseBlock 基础方块。
   * @return 新的 BlocksBuilder。
   */
  @Contract("_ -> new")
  public BlocksBuilder createEmpty(@NotNull Block baseBlock) {
    return createInternal(baseBlock, null, null, null, new TreeSet<>());
  }

  /**
   * 创建一个 BlocksBuilder，但是只包含建筑方块，可以在后续通过 {@link BlocksBuilder#with}、{@link BlocksBuilder#without} 等方法增加或删除需要创建的形状。
   *
   * @param baseBlock 基础方块。
   * @return 新的 BlocksBuilder。
   */
  @Contract("_ -> new")
  public BlocksBuilder createConstructionOnly(@NotNull Block baseBlock) {
    return createEmpty(baseBlock).withConstructionShapes();
  }

  /**
   * 修改一个 AbstractBlockBuilder 对象，使其部分字段适应当前 BlocksBuilderFactory 的设置。会将这个参数修改并直接返回。
   */
  @Contract(value = "_ -> param1", mutates = "param1")
  public <T extends AbstractBlockBuilder<?>> T modify(T blockBuilder) {
    blockBuilder.defaultNamespace = defaultNamespace;
    blockBuilder.instanceCollection = instanceCollection;
    return blockBuilder;
  }
}
