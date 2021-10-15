package pers.solid.extshape.tag;

import it.unimi.dsi.fastutil.objects.ObjectSets;

import java.util.Set;

interface TagEntry<E> extends Set<E> {
    final class TagEntrySingleton<E> extends ObjectSets.Singleton<E> implements TagEntry<E> {
        /**
         * Please use {@link TagEntry#of} instead.
         */
        private TagEntrySingleton(E element) {
            super(element);
        }

        public E get() {
            return element;
        }
    }

    static <E> TagEntrySingleton<E> of(E element) {
        return new TagEntrySingleton<>(element);
    }
}
