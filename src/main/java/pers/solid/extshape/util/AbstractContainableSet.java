package pers.solid.extshape.util;

import java.util.Set;

/**
 * Similar to {@link AbstractContainableCollection}, while {@code CE} and {@code CCE} must be a set, and this collection implements {@link Set}.
 *
 * @param <E>   The type of elements.
 * @param <CE>  The collection of {@code E}. Must extend {@code Set<E>}.
 * @param <CCE> The collection of {@code CE}. Must extend {@code Set<CE>}.
 */
public abstract class AbstractContainableSet<E, CE extends Set<E>, CCE extends Set<CE>> extends AbstractContainableCollection<E, CE, CCE> implements Set<E> {
    protected AbstractContainableSet(CCE entryCollection) {
        super(entryCollection);
    }

    @Override
    public abstract CE singletonOf(E o);
}
