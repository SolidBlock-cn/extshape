package pers.solid.extshape.util;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The concrete class of {@link AbstractContainableSet}, while {@code CE} and {@code CCE} are both {@link Set}, instead of type parameters that extend {@code Set}.
 * It's temperately not used. The {@link pers.solid.extshape.tag.ExtShapeTag} extends {@link AbstractContainableSet} instead of this.
 *
 * @param <E> The type of the elements.
 */
public class ContainableSet<E> extends AbstractContainableSet<E, Set<E>, Set<Set<E>>> {
    /**
     * Please use {@link #create()} to create a new instance.
     */
    protected ContainableSet(Set<Set<E>> entryCollection) {
        super(entryCollection);
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
    public static <E> ContainableSet<E> ofSets(ContainableSet<E>... elements) {
        ImmutableSet.Builder<Set<E>> builder = new ImmutableSet.Builder<>();
        for (ContainableSet<E> element : elements) {
            builder.add(element);
        }
        return new ContainableSet<>(builder.build());
    }

    @Override
    public Set<E> singletonOf(E o) {
        return ObjectSets.singleton(o);
    }
}
