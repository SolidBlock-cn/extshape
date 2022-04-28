package pers.solid.extshape.tag;

import it.unimi.dsi.fastutil.objects.ObjectSets;

import java.util.Collection;

public interface TagEntry<E> extends Collection<E> {
  static <E> TagEntrySingleton<E> of(E element) {
    return new TagEntrySingleton<>(element);
  }

  static <E> boolean isSingleton(TagEntry<E> e) {
    return e instanceof TagEntry.TagEntrySingleton<E>;
  }

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
}
