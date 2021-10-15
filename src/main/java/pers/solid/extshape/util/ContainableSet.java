package pers.solid.extshape.util;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;

import java.util.LinkedHashSet;
import java.util.Set;

public class ContainableSet<E> extends AbstractContainableSet<E, Set<E>,Set<Set<E>>> {
    /**
     * Please use {@link #create()} to create a new instance.
     *
     */
    protected ContainableSet(Set<Set<E>> entryCollection) {
        super(entryCollection);
    }

    @Override
    public Set<E> singletonOf(E o) {
        return ObjectSets.singleton(o);
    }

    public static <E> ContainableSet<E> create() {
        return new ContainableSet<>(new LinkedHashSet<>());
    }


    @SafeVarargs
    public static <E> ContainableSet<E> of(E... elements) {
        ImmutableSet.Builder<Set<E>> builder = new ImmutableSet.Builder<>();
        for (E element : elements) {
            final Set<E> singleton = ObjectSets.singleton(element);
            builder.add(singleton);
        }
        final ImmutableSet<Set<E>> set = builder.build();
        return new ContainableSet<>(set);
    }


    @SafeVarargs
    public static <E,CE extends Set<E>,CCE extends Set<CE>> ContainableSet<E> ofSets(ContainableSet<E>... elements) {
        ImmutableSet.Builder<Set<E>> builder = new ImmutableSet.Builder<>();
        for (ContainableSet<E> element : elements) {
            builder.add(element);
        }
        return new ContainableSet<E>(builder.build());
    }
}
