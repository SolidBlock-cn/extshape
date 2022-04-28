package pers.solid.extshape.tag;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.devtech.arrp.json.tags.JTag;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.util.RecursiveCollection;

/**
 * 可以在本模组代码中使用的标签。其特点是，自身拥有标识符（可能为 null），同时也可以查询元素的标识符。
 */
public interface UsableTag<E> extends RecursiveCollection<E, TagEntry<E>>, TagEntry<E> {
  /**
   * 获取自身的标识符。注意这里一般认为一个标签可以没有标识符。且这个标识符应该是常量。
   *
   * @return 标签自身的标识符。
   */
  @Nullable Identifier getIdentifier();

  /**
   * 获取标签内元素的标识符。通常是使用注册表中的方法。
   *
   * @param e 一个元素。不一定是标签内元素的类型，但必须与符合类参 {@code E}。
   * @return 标签内元素所拥有的标识符。
   */
  Identifier getIdentifierOf(E e);

  @CanIgnoreReturnValue
  @SuppressWarnings("UnusedReturnValue")
  default boolean addTag(UsableTag<E> es) {
    if (getIdentifier() != null && es.getIdentifier() == null) {
      // 不允许将没有标识符的标签加入拥有标识符的标签中。
      throw new IllegalArgumentException("Cannot add a tag without an identifier to an identified tag!");
    }
    if (es == this) {
      // 不允许将标签加入它自己（以免无限循环）。
      throw new IllegalArgumentException("Cannot add a tag that is identical to the tag to be added to!");
    }
    return addCollection(es);
  }

  /**
   * 将自身添加到另一个标签中，并允许串联。
   */
  @Contract(value = "_ -> this", mutates = "param1")
  default UsableTag<E> addToTag(UsableTag<E> anotherTag) {
    anotherTag.addTag(this);
    return this;
  }

  /**
   * 转化为 ARRP 中的标签对象。
   *
   * @return ARRP 中的标签对象。
   */
  default JTag toARRP() {
    final JTag jTag = new JTag();
    components().forEach(either -> either.map(
        e -> jTag.add(getIdentifierOf(e)),
        es -> jTag.addTag(((UsableTag<?>) es).getIdentifier())
    ));
    return jTag;
  }
}
