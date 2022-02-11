package pers.solid.extshape.util;

import java.util.Collection;

/**
 * The collection that contains two layers of collection. It judges whether an element is contained the collection by judging the "collection-in-collection" contents.
 * <br>
 * For the implementation of this interface, see {@link AbstractContainableCollection}.
 *
 * @param <E>   The type of elements.
 * @param <CE>  The collection of {@code E}. Must extend {@code Collection<E>}.
 * @param <CCE> The collection of {@code CE}. Must extend {@code Collection<CE>}.
 */
interface ContainableCollection<E, CE extends Collection<E>, CCE extends Collection<CE>> extends Collection<E> {

  @Override
  boolean add(E e);

  @Override
  boolean remove(Object o);

  @Override
  boolean contains(Object o);

  boolean directlyContains(Object o);

  boolean containsEntry(Object o);

  boolean addCollection(CE ce);

  boolean addAllCollections(Collection<CE> ces);

  boolean removeCollection(CE ce);

  boolean removeAllCollections(Collection<CE> ces);
}
