package pers.solid.extshape.tag;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.rrp.ExtShapeRRP;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 用于生成 {@link TagPreparation} 对象的实用类。
 *
 * @param <T>
 * @param <R>
 */
public abstract class TagPreparationFactory<T, R extends TagPreparation<T>> {
  /**
   * 创建的 {@link TagPreparation} 对象会默认写入到这个运行时资源包中。
   */
  public final RuntimeResourcePack pack;
  /**
   * 默认命名空间，在调用 {@link #ofEmpty(String)} 而不指定命名空间时会使用。
   */
  public final String defaultNamespace;

  protected TagPreparationFactory(RuntimeResourcePack pack, String defaultNamespace) {
    this.pack = pack;
    this.defaultNamespace = defaultNamespace;
  }

  protected abstract R createDirectly(@Nullable RuntimeResourcePack pack, Identifier identifier, Collection<T> entryList, Collection<TagPreparation<T>> tagList);

  /**
   * 创建一个实例，但是会尝试从 {@link TagPreparation#INTERNER} 中取出现成的。
   */
  @SuppressWarnings("unchecked")
  public R of(Identifier identifier, Collection<T> entryList, Collection<TagPreparation<T>> tagList) {
    final R instance = createDirectly(pack, identifier, entryList, tagList);
    if (pack == null) {
      return instance;
    } else {
      return (R) TagPreparation.INTERNER.intern(instance);
    }
  }

  public R ofEmpty(@NotNull Identifier identifier) {
    return of(identifier, new ArrayList<>(), new ArrayList<>());
  }

  public R ofEmpty(@NotNull String path) {
    return ofEmpty(new Identifier(defaultNamespace, path));
  }

  public R ofEmpty(@NotNull TagKey<T> tag) {
    return ofEmpty(tag.id());
  }

  /**
   * 仅用于本模组的方块标签，不公开。
   */
  static final TagPreparationFactory<Block, BlockTagPreparation> BLOCK = new TagPreparationFactory<>(ExtShapeRRP.STANDARD_PACK, ExtShape.MOD_ID) {
    @Override
    protected BlockTagPreparation createDirectly(@Nullable RuntimeResourcePack pack, Identifier identifier, Collection<Block> entryList, Collection<TagPreparation<Block>> tagList) {
      return new BlockTagPreparation(pack, identifier, entryList, tagList);
    }
  };

  /**
   * 仅用于本模组的物品标签，不公开。
   */
  static final TagPreparationFactory<Item, ItemTagPreparation> ITEM = new TagPreparationFactory<>(ExtShapeRRP.STANDARD_PACK, ExtShape.MOD_ID) {
    @Override
    protected ItemTagPreparation createDirectly(@Nullable RuntimeResourcePack pack, Identifier identifier, Collection<Item> entryList, Collection<TagPreparation<Item>> tagList) {
      return new ItemTagPreparation(pack, identifier, entryList, tagList);
    }
  };
}
