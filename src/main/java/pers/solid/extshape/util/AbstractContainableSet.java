package pers.solid.extshape.util;

import java.util.Set;

public abstract class AbstractContainableSet<E,CE extends Set<E>,CCE extends Set<CE>> extends AbstractContainableCollection<E,CE,CCE> implements Set<E> {
    protected AbstractContainableSet(CCE entryCollection) {
        super(entryCollection);
    }


    public boolean contains(AbstractContainableSet<E,CE,CCE> c) {
        return containsEntry(c);
    }

    @Override
    public abstract CE singletonOf(E o);
}
