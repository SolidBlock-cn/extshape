package pers.solid.extshape.util;

import it.unimi.dsi.fastutil.objects.ObjectSets;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class LinkedRecursiveHashSet<E> extends AbstractLinkedRecursiveCollection<E, Set<E>, Set<Set<E>>> {
  public LinkedRecursiveHashSet(Collection<E> directElements) {
    super(directElements.stream().map(Collections::singleton).collect(Collectors.toSet()));
  }

  @SafeVarargs
  public LinkedRecursiveHashSet(E... directElements) {
    this(Arrays.asList(directElements));
  }

  protected LinkedRecursiveHashSet(Set<Set<E>> internalEntries) {
    super(internalEntries);
  }

  @Override
  public Set<E> singletonOf(E e) {
    return ObjectSets.singleton(e);
  }

  @Override
  public boolean isSingleton(Set<E> mayBeSingleton) {
    return mayBeSingleton instanceof ObjectSets.Singleton<E>;
  }
}
