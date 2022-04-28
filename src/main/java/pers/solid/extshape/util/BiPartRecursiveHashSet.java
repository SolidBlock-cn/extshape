package pers.solid.extshape.util;

import java.util.HashSet;

public class BiPartRecursiveHashSet<E> extends AbstractBiPartRecursiveCollection<
    E, HashSet<E>, HashSet<E>, HashSet<HashSet<E>>
    > {
  protected BiPartRecursiveHashSet(HashSet<E> directCollection, HashSet<HashSet<E>> collectionCollection) {
    super(directCollection, collectionCollection);
  }
}
