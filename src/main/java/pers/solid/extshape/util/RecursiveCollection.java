package pers.solid.extshape.util;

import com.mojang.datafixers.util.Either;
import org.jetbrains.annotations.Contract;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * <p>可以递归的集。这种集的特点是，可以添加单个元素，也可以一次添加另一个集。递归集有两种形式：</p>
 * <p>这样的集有两种实现形式：</p>
 * <ul>
 *   <li>不考虑顺序的集合。这种集自身将会由两个普通的散列集（HashSet）组成，这种集判断元素是否存在时比较方便，但是在进行迭代时，先迭代元素集，然后再迭代集合之集。</li>
 *   <li>考虑顺序的集合。这种集合自身内部由一个“集合之集”组成的链集（LinkedHashSet），当添加一个元素是否存在时，添加一个该元素的单元素集进去。</li>
 * </ul>
 *
 * @param <E>  集合元素的类型。
 * @param <CE> {@code E} 的集合。必须继承 {@code Collection<E>}。
 */
public interface RecursiveCollection<E, CE extends Collection<E>> extends Collection<E> {
  @Override
  boolean add(E e);

  @Override
  boolean remove(Object o);

  @Contract(pure = true)
  @Override
  boolean contains(Object o);

  @Contract(pure = true)
  boolean containsDirectly(Object o);

  @Contract(pure = true)
  boolean containsCollection(Object o);

  @Contract(mutates = "this")
  boolean addCollection(CE ce);

  @Contract(mutates = "this")
  boolean addAllCollections(Collection<CE> ces);

  @Contract(mutates = "this")
  boolean removeCollection(Object ce);

  @Contract(mutates = "this")
  boolean removeAllCollections(Collection<?> ces);

  @Contract(pure = true)
  Stream<Either<E, CE>> components();
}
