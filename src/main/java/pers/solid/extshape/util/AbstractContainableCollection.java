package pers.solid.extshape.util;

import com.google.common.collect.AbstractIterator;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractContainableCollection<E,CE extends Collection<E>,CCE extends Collection<CE>> extends AbstractCollection<E> implements ContainableCollection<E, CE,CCE> {

    protected final CCE entries;

    protected AbstractContainableCollection(CCE entries) {
        this.entries = entries;
    }

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

    @Override
    public int size() {
        int size =0 ;
        for (Collection<E> entry : this.entries) {
            size += entry.size();
        }
        return size;
    }

    @Override
    public boolean contains(Object o) {
        return containsEntry(ObjectLists.singleton(o));
    }

    public boolean containsEntry(Object o) {
        if (entries.contains(o)) return true;
        for (Collection<E> entry : entries) {
            if (entry.contains(o)) return true;
        }
        return false;
    }

    @Override
    public boolean directlyContains(Object o) {
        return entries.contains(o);
    }

    public abstract CE singletonOf(E e);

    @Override
    public boolean add(E e) {
        return entries.add(singletonOf(e));
    }

    @Override
    public boolean addCollection(CE es) {
        return entries.add(es);
    }

    public boolean addAllCollections(Collection<CE> c) {
        return entries.addAll(c);
    }

    public boolean removeCollection(CE c) {
        return entries.remove(c);
    }

    public boolean removeAllCollections(Collection<CE> c) {
        return entries.removeAll(c);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof AbstractContainableCollection) {
            if (this.entries.equals(((AbstractContainableCollection<?,?,?>) obj).entries)) return true;
        }
        return this == obj;
    }

    @Override
    public boolean isEmpty() {
        return entries.isEmpty();
    }
}
