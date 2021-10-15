package pers.solid.extshape.util;

import java.util.Collection;

public interface ContainableCollection<E> extends Collection<E> {

    @Override
    boolean add(E e);

    @Override
    boolean remove(Object o);

    @Override
    boolean contains(Object o);

    boolean directlyContains(Object o);
}
