package pers.solid.extshape.util;

import com.google.common.collect.AbstractIterator;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

/**
 * The abstract implementation for {@link ContainableCollection}.<br>
 * It judges whether elements are contained in this collection by whether it's contained in any collection entry in {@link #entries}. Usually, adding an element equals to adding a singleton into {@link #entries}.
 *
 * @param <E>   The type of elements.
 * @param <CE>  The collection of {@code E}. Must extend {@code Collection<E>}.
 * @param <CCE> The collection of {@code CE}. Must extend {@code Collection<CE>}.
 */
public abstract class AbstractContainableCollection<E, CE extends Collection<E>, CCE extends Collection<CE>> extends AbstractCollection<E> implements ContainableCollection<E, CE, CCE> {

    /**
     * The collection that stores collections of elements. The <code>CCE</code> implements {@code Collection<CE>}, and <code>CE</code> extends or implements {@code Collection<E>}, which means {@code CCE} is a "collection-of-collection" of {@code E}, the type of the element.
     */
    protected final CCE entries;

    /**
     * Construct a new instance by providing directly the entries.
     *
     * @param entries The entries that will be used directly in this instancce.
     */
    protected AbstractContainableCollection(CCE entries) {
        this.entries = entries;
    }

    /**
     * Iterating elements in "collections-in-collections". To be clearer, for example, if the {@link #entries} is something like <code>{{1,2,3},{4,5,6},{7}}</code>, then the iterator yields respectively {@code 1,2,3,4,5,6,7}.
     *
     * @return The iterator of this instance.
     */
    @Override
    public Iterator<E> iterator() {
        return new AbstractIterator<E>() {
            final CCE entries = AbstractContainableCollection.this.entries;
            private final Iterator<CE> entriesIterator = entries.iterator();
            private @Nullable Iterator<E> iteratingEntryIterator = null;

            @Override
            protected E computeNext() {
                while (iteratingEntryIterator == null || !iteratingEntryIterator.hasNext()) {
                    if (!entriesIterator.hasNext()) return endOfData();
                    iteratingEntryIterator = entriesIterator.next().iterator();
                }
                return iteratingEntryIterator.next();
            }
        };
    }

    /**
     * Summing the sizes of collections in entries.
     *
     * @return The number of elements. It theoretically equals to the sum of size of collections in {@link #entries}.
     */
    @Override
    public int size() {
        int size = 0;
        for (Collection<E> entry : this.entries) {
            size += entry.size();
        }
        return size;
    }

    /**
     * @return <code>true</code> if any of the {@link #entries} contains the element <code>o</code>.
     */
    @Override
    public boolean contains(Object o) {
        for (CE entry : entries) {
            if (entry.contains(o)) return true;
        }
        return false;
    }

    /**
     * @return <code>true</code> if the {@link #entries} contains the entry <code>o</code>.
     */
    @Override
    public boolean containsEntry(Object o) {
        if (entries.contains(o)) return true;
        for (CE entry : entries) {
            if (entry.contains(o)) return true;
        }
        return false;
    }

    /**
     * @return <code>true</code> if {@link #entries} directly contains a singleton of <code>o</code>.
     * @see #singletonOf(Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean directlyContains(Object o) {
        E element;
        try {
            element = ((E) o);
        } catch (ClassCastException e) {
            return false;
        }
        return entries.contains(singletonOf(element));
    }

    /**
     * Create a singleton of <code>e</code> to be added to or removed from {@link #entries}.
     *
     * @param e The element in the singleton.
     * @return The singleton that can be in {@link #entries}.
     */
    public abstract CE singletonOf(E e);

    /**
     * Add a singleton of <code>e</code> to {@link #entries}.
     */
    @Override
    public boolean add(E e) {
        return entries.add(singletonOf(e));
    }

    /**
     * Add a collection directly in to {@link #entries}.
     */
    @Override
    public boolean addCollection(CE es) {
        return entries.add(es);
    }

    @Override
    public boolean addAllCollections(Collection<CE> c) {
        return entries.addAll(c);
    }

    @Override
    public boolean removeCollection(CE c) {
        return entries.remove(c);
    }

    @Override
    public boolean removeAllCollections(Collection<CE> c) {
        return entries.removeAll(c);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof AbstractContainableCollection) {
            if (this.entries.equals(((AbstractContainableCollection<?, ?, ?>) obj).entries)) return true;
        }
        return this == obj;
    }

    @Override
    public boolean isEmpty() {
        return entries.isEmpty();
    }
}
