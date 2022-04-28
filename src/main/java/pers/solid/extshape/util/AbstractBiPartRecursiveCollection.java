package pers.solid.extshape.util;

import com.mojang.datafixers.util.Either;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.tag.TagEntry;
import pers.solid.extshape.tag.UsableTag;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

public abstract class AbstractBiPartRecursiveCollection<E, CE extends Collection<E>, C1 extends Collection<E>, C2 extends Collection<CE>> extends AbstractCollection<E> implements RecursiveCollection<E, CE> {
  /**
   * 直接存放元素的集合，其扩展了 {@code Collection<E>}。
   */
  protected final C1 directCollection;
  /**
   * 存放元素集合的集合，其扩展了 {@code Collection<C2 extends Collection<CE extends Collection>>}，其中 {@code CE} 就是 {@link #addCollection} 等方法使用的 CE
   */
  protected final C2 collectionCollection;

  protected AbstractBiPartRecursiveCollection(C1 directCollection, C2 collectionCollection) {
    this.directCollection = directCollection;
    this.collectionCollection = collectionCollection;
  }

  @Override
  public boolean add(E e) {
    return directCollection.add(e);
  }

  @Override
  public boolean addAll(@NotNull Collection<? extends E> c) {
    return directCollection.addAll(c);
  }

  @Override
  public boolean addCollection(CE es) {
    return collectionCollection.add(es);
  }

  @Override
  public boolean addAllCollections(Collection<CE> ces) {
    return collectionCollection.addAll(ces);
  }

  @SuppressWarnings("SuspiciousMethodCalls")
  @Override
  public boolean removeCollection(Object ce) {
    return collectionCollection.remove(ce);
  }

  @SuppressWarnings("SuspiciousMethodCalls")
  @Override
  public boolean removeAllCollections(Collection<?> ces) {
    return collectionCollection.removeAll(ces);
  }

  @Override
  public boolean remove(Object o) {
    if (directCollection.remove(o)) {
      return true;
    }
    for (CE es : collectionCollection) {
      if (es.remove(o)) return true;
    }
    return false;
  }

  @NotNull
  @Override
  public Iterator<E> iterator() {
    return Stream.concat(directCollection.stream(), collectionCollection.stream().flatMap(Collection::stream)).iterator();
  }

  @Override
  public int size() {
    return directCollection.size() + collectionCollection.stream().mapToInt(Collection::size).sum();
  }

  @Override
  public boolean contains(Object o) {
    if (directCollection.contains(o)) return true;
    for (CE es : collectionCollection) {
      if (es.contains(o)) return true;
    }
    return false;
  }

  @SuppressWarnings("SuspiciousMethodCalls")
  @Override
  public boolean containsDirectly(Object o) {
    return directCollection.contains(o);
  }

  @SuppressWarnings("SuspiciousMethodCalls")
  @Override
  public boolean containsCollection(Object o) {
    return collectionCollection.contains(o);
  }

  @Override
  public Stream<Either<E, CE>> components() {
    return Stream.concat(
        directCollection.stream().map(Either::left),
        collectionCollection.stream().map(Either::right)
    );
  }

  public abstract static class TagImpl<E> extends AbstractBiPartRecursiveCollection<E, TagEntry<E>, Collection<E>, Collection<TagEntry<E>>> implements UsableTag<E> {
    public final Identifier identifier;

    protected TagImpl(Collection<E> directCollection, Collection<TagEntry<E>> collectionCollection, Identifier identifier) {
      super(directCollection, collectionCollection);
      this.identifier = identifier;
    }

    @Override
    public @Nullable Identifier getIdentifier() {
      return identifier;
    }
  }
}
