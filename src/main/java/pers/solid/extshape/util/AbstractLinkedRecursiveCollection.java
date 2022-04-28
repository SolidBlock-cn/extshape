package pers.solid.extshape.util;

import com.mojang.datafixers.util.Either;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.tag.TagEntry;
import pers.solid.extshape.tag.UsableTag;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * 链式的递归集合。其实现方法为，其内部的 {@link #internalEntries} 是存储其集合的集合。当添加单个元素进去时，实际上是添加的一个单元素集进去。
 */
public abstract class AbstractLinkedRecursiveCollection<E, CE extends Collection<E>, CCE extends Collection<CE>>
    extends AbstractCollection<E>
    implements RecursiveCollection<E, CE> {
  protected final CCE internalEntries;

  protected AbstractLinkedRecursiveCollection(CCE internalEntries) {
    super();
    this.internalEntries = internalEntries;
  }

  /**
   * 在“集中集”中迭代元素。举个例子，如果说 {@link #internalEntries} 是类似于 <code>{{1,2,3},{4,5,6},{7}}</code>，那么这个迭代器就会分别生成 {@code 1,2,3,4,5,6,7}。
   *
   * @return 该实例的迭代器。
   */
  @Override
  public Iterator<E> iterator() {
    return internalEntries.stream().flatMap(Collection::stream).iterator();
  }

  /**
   * 将内部元素的大小累加起来。
   *
   * @return 该元素的大小。理论上等于 {@link #internalEntries} 中的元素大小。
   */
  @Override
  public int size() {
    return internalEntries.stream().mapToInt(Collection::size).sum();
  }

  /**
   * 判断该集合中是否存在某个元素。首先判断是否存在一个单元素集，然后再判断各集合中是否存在该元素。
   *
   * @return <code>true</code> 如果 {@link #internalEntries} 的任意一个集合包含 <code>o</code>。
   */
  @Override
  public boolean contains(Object o) {
    trySingleton:
    {
      final CE singleton;
      try {
        singleton = singletonOfObject(o);
      } catch (ClassCastException e) {
        break trySingleton;
      }
      if (internalEntries.contains(singleton)) {
        return true;
      }
    }
    for (CE entry : internalEntries) {
      if (entry.contains(o)) return true;
    }
    return false;
  }

  /**
   * @return <code>true</code> if the {@link #internalEntries} contains the entry <code>o</code>.
   */
  @SuppressWarnings("SuspiciousMethodCalls")
  @Override
  public boolean containsCollection(Object o) {
    if (internalEntries.contains(o)) return true;
    for (CE entry : internalEntries) {
      if (entry.contains(o)) return true;
    }
    return false;
  }

  /**
   * @return <code>true</code> if {@link #internalEntries} directly contains a singleton of <code>o</code>.
   * @see #singletonOf(Object)
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean containsDirectly(Object o) {
    E element;
    try {
      element = ((E) o);
    } catch (ClassCastException e) {
      return false;
    }
    return internalEntries.contains(singletonOf(element));
  }

  /**
   * Create a singleton of <code>e</code> to be added to or removed from {@link #internalEntries}.
   *
   * @param e The element in the singleton.
   * @return The singleton that can be in {@link #internalEntries}.
   */
  @Contract(value = "_ -> new", pure = true)
  public abstract CE singletonOf(E e);

  @SuppressWarnings("unchecked")
  public final CE singletonOfObject(Object o) throws ClassCastException {
    return singletonOf((E) o);
  }

  /**
   * Add a singleton of <code>e</code> to {@link #internalEntries}.
   */
  @Override
  public boolean add(E e) {
    return internalEntries.add(singletonOf(e));
  }

  /**
   * 尝试直接移除单元素集，如果这样的单元素集不存在，则尝试从里面的每个元素中移除元素，直到移除成功为止。<br>
   * <b>注意：</b>这样的操作有可能导致里面的集合的内容被移除，但不可变元素会被绕过。如果您只希望移除通过 {@link #add} 直接添加入的元素，请使用 {@link #removeSingleton(Object)}。
   *
   * @param o {@inheritDoc}
   * @return {@inheritDoc}
   */
  @Override
  public boolean remove(Object o) {
    if (removeSingleton(o)) {
      return true;
    }
    for (CE entry : internalEntries) {
      try {
        if (entry.remove(o)) return true;
      } catch (UnsupportedOperationException ignore) {
      }
    }
    return false;
  }

  /**
   * 从 {@link #internalEntries} 中直接移除元素，但并不会从集合的集合中移除元素。
   *
   * @param o 被移除的元素。
   * @return 是否成功移除。
   */
  public boolean removeSingleton(Object o) {
    final CE singleton;
    try {
      singleton = singletonOfObject(o);
    } catch (ClassCastException e) {
      return false;
    }
    return internalEntries.remove(singleton);
  }

  /**
   * Add a collection directly in to {@link #internalEntries}.
   */
  @Override
  @Contract(mutates = "this")
  public boolean addCollection(CE es) {
    return internalEntries.add(es);
  }

  @Override
  @Contract(mutates = "this")
  public boolean addAllCollections(Collection<CE> c) {
    return internalEntries.addAll(c);
  }

  @SuppressWarnings("SuspiciousMethodCalls")
  @Override
  @Contract(mutates = "this")
  public boolean removeCollection(Object c) {
    return internalEntries.remove(c);
  }

  @SuppressWarnings("SuspiciousMethodCalls")
  @Override
  @Contract(mutates = "this")
  public boolean removeAllCollections(Collection<?> c) {
    return internalEntries.removeAll(c);
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof AbstractLinkedRecursiveCollection<?, ?, ?> set && set.internalEntries.equals(this.internalEntries);
  }

  /**
   * 判断集合是否为空，方法就是内部集合不为空，或内部集合的所有集合均为空（也就是说，内部集合不含非空集合）。
   *
   * @return {@inheritDoc}
   */
  @Override
  public boolean isEmpty() {
    return internalEntries.isEmpty() || !internalEntries.stream().map(es -> !es.isEmpty()).toList().isEmpty();
  }

  public abstract boolean isSingleton(CE mayBeSingleton);

  @Override
  public Stream<Either<E, CE>> components() {
    return internalEntries.stream().map(es -> {
      if (isSingleton(es)) {
        return Either.left(es.iterator().next());
      } else {
        return Either.right(es);
      }
    });
  }

  public static abstract class TagImpl<E> extends AbstractLinkedRecursiveCollection<E, TagEntry<E>, Collection<TagEntry<E>>> implements UsableTag<E> {
    public final Identifier identifier;

    public TagImpl(Collection<TagEntry<E>> internalEntries, Identifier identifier) {
      super(internalEntries);
      this.identifier = identifier;
    }

    @Override
    public @Nullable Identifier getIdentifier() {
      return identifier;
    }

    @Override
    public TagEntry<E> singletonOf(E block) {
      return TagEntry.of(block);
    }

    @Override
    public boolean isSingleton(TagEntry<E> mayBeSingleton) {
      return false;
    }
  }
}
