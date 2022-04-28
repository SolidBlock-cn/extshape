package pers.solid.extshape.tag;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.devtech.arrp.json.tags.JTag;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Stream;

/**
 * 可以在本模组代码中使用的标签。其特点是，自身拥有标识符（可能为 null），同时也可以查询元素的标识符。
 */
@ApiStatus.AvailableSince("1.5.0")
public abstract class UsableTag<E> extends AbstractCollection<E> {
  /**
   * 包含所有已创建的标签。
   **/
  public static final List<UsableTag<?>> TAG_LIST = new ArrayList<>();
  public final Identifier identifier;
  public final Collection<E> entryList;
  public final Collection<UsableTag<E>> tagList;

  protected UsableTag(Identifier identifier, Collection<E> entryList, Collection<UsableTag<E>> tagList) {
    this.identifier = identifier;
    this.entryList = entryList;
    this.tagList = tagList;
    TAG_LIST.add(this);
  }

  /**
   * 获取标签内元素的标识符。通常是使用注册表中的方法。
   *
   * @param e 一个元素。不一定是标签内元素的类型，但必须与符合类参 {@code E}。
   * @return 标签内元素所拥有的标识符。
   */
  public abstract Identifier getIdentifierOf(E e);

  @Override
  public boolean add(E e) {
    return entryList.add(e);
  }

  @Override
  public boolean addAll(@NotNull Collection<? extends E> c) {
    return entryList.addAll(c);
  }

  @CanIgnoreReturnValue
  @SuppressWarnings("UnusedReturnValue")
  public boolean addTag(UsableTag<E> es) {
    if (this.identifier != null && es.identifier == null) {
      // 不允许将没有标识符的标签加入拥有标识符的标签中。
      throw new IllegalArgumentException("Cannot add a tag without an identifier to an identified tag!");
    }
    if (es == this) {
      // 不允许将标签加入它自己（以免无限循环）。
      throw new IllegalArgumentException("Cannot add a tag that is identical to the tag to be added to!");
    }
    return tagList.add(es);
  }

  /**
   * 将自身添加到另一个标签中，并允许串联。
   */
  @Contract(value = "_ -> this", mutates = "param1")
  public UsableTag<E> addToTag(UsableTag<E> anotherTag) {
    anotherTag.addTag(this);
    return this;
  }

  /**
   * 转化为 ARRP 中的标签对象。
   *
   * @return ARRP 中的标签对象。
   */
  public JTag toARRP() {
    final JTag jTag = new JTag();
    for (E e : entryList) {
      jTag.add(getIdentifierOf(e));
    }
    for (UsableTag<E> es : tagList) {
      jTag.addTag(es.identifier);
    }
    return jTag;
  }

  @Override
  public Iterator<E> iterator() {
    return stream().iterator();
  }

  @Override
  public Stream<E> stream() {
    return Stream.concat(entryList.stream(), tagList.stream().flatMap(Collection::stream));
  }

  @Override
  public int size() {
    return entryList.size() + tagList.stream().mapToInt(UsableTag::size).sum();
  }
}
