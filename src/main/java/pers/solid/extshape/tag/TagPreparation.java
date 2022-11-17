package pers.solid.extshape.tag;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.tags.IdentifiedTag;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * 本模组使用的标签，可以存储对象并进行数据生成。其特点是，自身拥有标识符（可能为 null），同时也可以查询元素的标识符。需要注意的是，这些对象不会从数据包中加载，而是将其数据写到运行时资源包中。可以使用 {@link #toVanillaTag()} 以在需要原版标签对象时使用，或者使用 {@link #generateTag()} 转换成运行时资源包使用的数据。
 */
@ApiStatus.AvailableSince("1.5.0")
public abstract class TagPreparation<E> extends AbstractCollection<E> {
  /**
   * 用于存储 {@link TagPreparation} 对象的常量池，参见 {@link TagPreparationFactory#of(Identifier, Collection, Collection)}。当存在相同运行时资源包和 id 的对象时，就会直接从里面取用它。，不再创建新的
   */
  static final Interner<TagPreparation<?>> INTERNER = Interners.newWeakInterner();
  protected final RuntimeResourcePack pack;
  public final Identifier identifier;
  public final Collection<E> entryList;
  public final Collection<TagPreparation<E>> tagList;

  protected TagPreparation(RuntimeResourcePack pack, Identifier identifier, Collection<E> entryList, Collection<TagPreparation<E>> tagList) {
    this.pack = pack;
    this.identifier = identifier;
    this.entryList = entryList;
    this.tagList = tagList;
  }

  /**
   * 获取标签内元素的标识符。通常是使用注册表中的方法。
   *
   * @param e 一个元素。不一定是标签内元素的类型，但必须与符合类参 {@code E}。
   * @return 标签内元素所拥有的标识符。
   */
  @Contract(pure = true)
  public abstract Identifier getIdentifierOf(E e);

  @Override
  public boolean add(E e) {
    if (!FMLEnvironment.production && entryList.contains(e)) {
      throw new IllegalArgumentException("Duplicated element " + e + " in tag " + this);
    }
    return entryList.add(e);
  }

  @Override
  public boolean addAll(@NotNull Collection<? extends E> c) {
    return entryList.addAll(c);
  }

  @CanIgnoreReturnValue
  @Contract(mutates = "this")
  public boolean addTag(TagPreparation<E> es) {
    if (this.identifier != null && es.identifier == null) {
      // 不允许将没有标识符的标签加入拥有标识符的标签中。
      throw new IllegalArgumentException("Cannot add a tag without an identifier to an identified tag!");
    }
    if (es == this) {
      // 不允许将标签加入它自己（以免无限循环）。
      throw new IllegalArgumentException("Cannot add a tag that is identical to the tag to be added to!");
    }
    if (!FMLEnvironment.production && tagList.contains(es)) {
      throw new IllegalArgumentException("Duplicate tag " + es + " in the tag " + this);
    }
    return tagList.add(es);
  }

  /**
   * 将自身添加到另一个标签中，并允许串联。
   */
  @Contract(value = "_ -> this", mutates = "param1")
  public TagPreparation<E> addSelfToTag(TagPreparation<E> anotherTag) {
    anotherTag.addTag(this);
    return this;
  }

  public abstract TagKey<E> toVanillaTag();

  /**
   * 转化为 ARRP 中的标签对象。
   *
   * @return ARRP 中的标签对象。
   */
  public IdentifiedTag generateTag() {
    final IdentifiedTag jTag = new IdentifiedTag(toVanillaTag());
    for (E e : entryList) {
      jTag.add(getIdentifierOf(e));
    }
    for (TagPreparation<E> es : tagList) {
      jTag.addTag(es.identifier);
    }
    return jTag;
  }

  @CanIgnoreReturnValue
  public byte[] writeIntoPack() {
    if (pack == null) {
      throw new IllegalStateException("Cannot invoke writeIntoPack because the pack is null");
    }
    return generateTag().write(pack);
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
    return entryList.size() + tagList.stream().mapToInt(TagPreparation::size).sum();
  }

  @Override
  public void clear() {
    entryList.clear();
    tagList.clear();
  }

  @Override
  public boolean equals(Object o) {
    // 当 pack 字段不为 null 时，只比较 pack 和 identifier，不比较 entryList 和 tagList，因为此时对象已经被 intern 了。
    if (this == o) return true;
    if (o.getClass() != getClass()) return false;
    final TagPreparation<?> that = (TagPreparation<?>) o;
    if (pack == null && that.pack == null && (!entryList.equals(that.entryList) || !tagList.equals(that.tagList))) {
      return false;
    }
    return Objects.equals(identifier, that.identifier) && Objects.equals(pack, that.pack);
  }

  @Override
  public int hashCode() {
    // 当 pack 字段不为 null 时，只考虑 pack 和 identifier，不考虑 entryList 和 tagList，因为此时对象已经被 intern 了。
    int result = pack != null ? pack.hashCode() : 0;
    result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
    if (pack == null) {
      result = 31 * result + (entryList != null ? entryList.hashCode() : 0);
      result = 31 * result + (tagList != null ? tagList.hashCode() : 0);
    }
    return result;
  }
}
