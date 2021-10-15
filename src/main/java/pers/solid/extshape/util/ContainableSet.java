package pers.solid.extshape.util;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.objects.ObjectLists;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public class ContainableSet<E> extends AbstractContainableCollection<E> implements Set<E> {
    /**
     * Please use {@link #create()} to create a new instance.
     */
    protected ContainableSet(Set<Collection<E>> entryCollection) {
        super(entryCollection);
    }

    public static <E> ContainableSet<E> create() {
        return new ContainableSet<>(new LinkedHashSet<>());
    }

    @SafeVarargs
    public static <E> ContainableSet<E> of(E... elements) {
        ImmutableSet.Builder<Collection<E>> builder = new ImmutableSet.Builder<>();
        for (E element : elements) {
            builder.add(ObjectLists.singleton(element));
        }
        final ImmutableSet<Collection<E>> set = builder.build();
        return new ContainableSet<>(set);
    }

    @SafeVarargs
    public static <E> ContainableSet<E> ofSets(ContainableSet<E>... elements) {
        ImmutableSet.Builder<Collection<E>> builder = new ImmutableSet.Builder<>();
        for (ContainableSet<E> element : elements) {
            builder.add(element);
        }
        return new ContainableSet<>(builder.build());
    }

    public boolean addSet(ContainableSet<E> set) {
        return entries.add(set);
    }

    public boolean removeSet(ContainableSet<E> set) {
        return entries.remove(set);
    }

    public boolean addAllSet(Collection<? extends ContainableSet<E>> c) {
        return entries.addAll(c);
    }

    public boolean removeAllSet(Collection<? extends ContainableSet<E>> c) {
        return entries.removeAll(c);
    }

    public boolean contains(ContainableSet<E> c) {
        return containsEntry(c);
    }
}
