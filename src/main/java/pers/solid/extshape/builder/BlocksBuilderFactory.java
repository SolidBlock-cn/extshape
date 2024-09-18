package pers.solid.extshape.builder;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
  public @Nullable String defaultNamespace;
  /**
   * 将构建产生的方块都添加到这个集合中。
   */
  public @Nullable Collection<Block> instanceCollection;
  /**
   * 将使用的基础方块添加到这个集合中。注意：在利用本对象来创建 {@link BlocksBuilder} 的时候，就会将其使用的基础方块添加到这个集合。
   */
  public @Nullable Collection<Block> baseBlockCollection;

  public BlocksBuilderFactory() {
  }

  protected BlocksBuilder createInternal(@NotNull Block baseBlock, SortedSet<BlockShape> shapesToBuild) {
    return new BlocksBuilder(baseBlock, shapesToBuild);
  }

  protected BlocksBuilder create(@NotNull Block baseBlock, SortedSet<BlockShape> shapesToBuild) {
    final BlocksBuilder blocksBuilder = createInternal(baseBlock, shapesToBuild);
    blocksBuilder.defaultNamespace = defaultNamespace;
    blocksBuilder.instanceCollection = instanceCollection;
    if (baseBlockCollection != null) {
      baseBlockCollection.add(baseBlock);
    }
    return blocksBuilder;
  }

  public BlocksBuilder createAllShapes(@NotNull Block baseBlock) {
    return create(baseBlock, new TreeSet<>(SHAPES));
  }

  /**
   * 创建一个 BlocksBuilder，但暂时不会创建任何形状的方块，可以在后续通过 {@link BlocksBuilder#with} 等方法添加。
   *
   * @param baseBlock 基础方块。
   * @return 新的 BlocksBuilder。
   */
  @Contract("_ -> new")
  public BlocksBuilder createEmpty(@NotNull Block baseBlock) {
    return create(baseBlock, new TreeSet<>());
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
