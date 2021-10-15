package pers.solid.extshape.util;

import java.util.Collection;

interface ContainableCollection<E,CE extends Collection<E>,CCE extends Collection<CE>> extends Collection<E> {

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
