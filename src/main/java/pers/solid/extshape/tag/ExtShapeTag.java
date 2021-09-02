package pers.solid.extshape.tag;

import com.google.common.collect.UnmodifiableIterator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.function.Consumer;

/**
 * 一类标签，实质上是可以递归的列表。但是不能与 Minecraft 的原版标签混淆。
 * 它即可以包含一些某类元素，又可以包含另一个由此类元素组成的标签。
 * 标签的内容都是内置的，并且可以修改，不受数据包影响。
 * 数据生成器会利用这些标签内容生成数据。
 *
 * @param <T> 标签内的元素的类型。
 */
public class ExtShapeTag<T> extends AbstractCollection<T> {
    public final Identifier identifier; // 标签的命名空间id。
    public final List<TagEntry<T>> entryList;
    public boolean replace;

    @SafeVarargs
    public ExtShapeTag(T... elements) {
        this(null, elements);
    }

    public ExtShapeTag(Collection<T> elements) {
        this(null, elements);
    }

    /**
     * 创建一个用于本模组的标签。
     *
     * @param identifier 标签的命名空间id。
     * @param elements   标签的初始内容。
     */
    public ExtShapeTag(Identifier identifier, Collection<T> elements) {
        this.identifier = identifier;
        this.entryList = new ArrayList<>();
        for (T element : elements) {
            this.entryList.add(new TagEntrySingleElement<>(element));
        }
        replace = false;
    }

    @SafeVarargs
    public ExtShapeTag(Identifier identifier, T... elements) {
        this(identifier, Arrays.asList(elements));
    }

    /**
     * 获取该标签的命名空间id。
     *
     * @return 该标签的命名空间id。
     */
    public Identifier getIdentifier() {
        return this.identifier;
    }

    /**
     * 向该标签内添加一个元素。元素不能重复。
     *
     * @param element 需要添加进此标签的元素。
     */
    public boolean add(T element) {
        if (this.directlyContains(element)) throw new RuntimeException(String.format("Cannot add a duplicate element " +
                "to a tag! %s already contains %s.", this, element));
        return this.entryList.add(new TagEntrySingleElement<>(element));
    }

    /**
     * 向该标签内添加一个由同类元素组成的标签。标签不能重复。
     * 不能将没有命名空间的id的标签添加到有命名空间id的标签中。
     * 不能将任何标签添加到它自己中。
     * 若标签内已包含此标签，则会报错。
     *
     * @param element 需要添加进此标签的标签，其元素类型必须一致。
     */
    public boolean addTag(ExtShapeTag<T> element) {
        if (this.identifier != null && element.identifier == null)
            throw new UnsupportedOperationException("Cannot add a tag without identifier to a tag with an identifier");
        if (element == this) throw new UnsupportedOperationException("Cannot add to a tag itself!");
        if (element.containsTag(this))
            throw new UnsupportedOperationException(String.format("Cannot add into this tag another tag that contains it! %s already contains %s.", element, this));
        if (this.directlyContainsTag(element))
            throw new UnsupportedOperationException(String.format("Cannot add a duplicate tag to a tag! %s already contains %s.", this,
                    element));
        return this.entryList.add(new TagEntryTag<>(element));
    }

    /**
     * 向标签内添加多个元素。
     *
     * @see #add
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        return super.addAll(c);
    }

    /**
     * 向标签内添加多个标签。
     *
     * @param elements 需要添加到该标签内的标签。
     * @see #addTag
     */
    @SafeVarargs
    public final void addAllTags(ExtShapeTag<T>... elements) {
        for (ExtShapeTag<T> element : elements) {
            addTag(element);
        }
    }

    /**
     * 将该标签添加到其他标签中。
     *
     * @param tag 需要将此标签添加到的标签。
     * @return 标签自身。
     * @see #add
     */
    public ExtShapeTag<T> addToTag(ExtShapeTag<T> tag) {
        tag.addTag(this);
        return this;
    }

    /**
     * @return 标签是否包含元素。会递归检查，即检查标签内的标签是否含有此元素。
     */
    public boolean contains(Object element) {
        for (TagEntry<T> entry : entryList) if (entry.contains(element)) return true;
        return false;
    }

    /**
     * @return 标签内是否包含标签。会递归检查，即检查标签内的标签是否含有此标签。
     */
    public boolean containsTag(ExtShapeTag<T> tag) {
        for (TagEntry<T> entry :
                entryList)
            if (entry instanceof TagEntryTag<T>) {
                ExtShapeTag<T> elementTag = ((TagEntryTag<T>) entry).elementTag;
                if (elementTag == tag || elementTag.containsTag(tag)) return true;
            }
        return false;
    }

    /**
     * @return 标签是否直接包含元素。不会递归检查，即不会检查标签内的标签是否含有此元素。
     */
    public boolean directlyContains(T element) {
        for (TagEntry<T> entry : entryList)
            if (entry instanceof TagEntrySingleElement && ((TagEntrySingleElement<T>) entry).element == element)
                return true;
        return false;
    }

    /**
     * @return 标签是否直接包含标签。不会递归检查，即不会检查标签内的标签是否含有此标签。
     */
    public boolean directlyContainsTag(ExtShapeTag<T> tag) {
        for (TagEntry<T> entry : entryList)
            if (entry instanceof TagEntryTag && ((TagEntryTag<T>) entry).elementTag == tag) return true;
        return false;
    }

    /**
     * 获取某个元素的命名空间id。
     *
     * @param element 元素。
     * @return 命名空间id。
     */
    public Identifier getIdentifierOf(T element) {
        return null;
    }

    /**
     * 将标签转换为json对象。
     *
     * @return json对象。
     */
    public JsonObject generateJson() {
        JsonObject main = new JsonObject();
        main.addProperty("replace", replace);

        JsonArray values = new JsonArray();

        for (TagEntry<T> entry : this.entryList) {
            if (entry instanceof TagEntryTag) values.add("#" + ((TagEntryTag<T>) entry).elementTag.getIdentifier());
            else if (entry instanceof TagEntrySingleElement)
                values.add(getIdentifierOf(((TagEntrySingleElement<T>) entry).element).toString());
        }

        main.add("values", values);
        return main;
    }

    /**
     * 将标签转换为json字符串。{@see #generateJson}
     *
     * @return json字符串。
     */
    public String generateString() {
        StringWriter stringWriter = new StringWriter();
        JsonWriter jsonWriter = new JsonWriter(stringWriter);
        jsonWriter.setIndent("  ");
        try {
            Streams.write(this.generateJson(), jsonWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }

    /**
     * 将标签转换为普通的列表。标签内的标签会被完全展开。
     *
     * @return 转换后的列表。
     */
    public List<T> asList() {
        return new ArrayList<>(this);
    }

    /**
     * 类似于{@link Iterable#forEach}，迭代标签内的元素，但不使用迭代器。
     *
     * @param action 同<code>forEach</code>中的<code>action</code>。
     */
    @Deprecated
    public void rawForEach(Consumer<? super T> action) {
        for (TagEntry<T> entry : entryList) {
            if (entry instanceof TagEntryTag) {
                ((TagEntryTag<T>) entry).elementTag.rawForEach(action);
            } else if (entry instanceof TagEntrySingleElement) {
                action.accept(((TagEntrySingleElement<T>) entry).element);
            }
        }
    }

    /**
     * 类似于{@link #asList()}，但不使用迭代器，将标签转换为列表。
     *
     * @return 转换后的列表。
     */
    @Deprecated
    public List<T> rawToList() {
        List<T> list = new ArrayList<>();
        this.rawForEach(list::add);
        return list;
    }

    /**
     * 标签元素个数。
     * 虽然标签元素不允许重复，但是有可能标签内元素与标签内的标签中的元素重复。这种重复是仍会重复计数的。
     *
     * @return 标签内的元素个数。
     */
    public int size() {
        int size = 0;
        for (TagEntry<T> entry : this.entryList) size += entry.size();
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private final int entryListSize = entryList.size();
            private int cursor = -1; // 迭代器在<code>entryList</code>中的指针。
            private @Nullable Iterator<T> iteratingEntryIterator = null;

            @Override
            public boolean hasNext() {
                if (cursor == entryListSize) return false;
                while (iteratingEntryIterator == null || !iteratingEntryIterator.hasNext()) {
                    cursor++;
                    if (cursor == entryListSize) return false;
                    iteratingEntryIterator = entryList.get(cursor).iterator();
                }
                return true;
            }

            @Override
            public T next() {
                while (iteratingEntryIterator == null || !iteratingEntryIterator.hasNext()) {
                    cursor++;
                    iteratingEntryIterator = entryList.get(cursor).iterator();
                }
                return iteratingEntryIterator.next();
            }
        };
    }

    @Override
    public String toString() {
        return "ExtShapeTag{" + identifier + '}';
    }

    /**
     * 标签内的一个项。这个项可以是<b>元素</b>，也可以是<b>由此类元素组成的标签</b>。
     *
     * @param <T> 与标签的元素类相同。
     */
    protected abstract static class TagEntry<T> implements Iterable<T> {

        public abstract boolean contains(Object element);

        public abstract int size();
    }

    /**
     * 标签内的一个项，且这个项为单元素。
     *
     * @param <T> 与标签的元素相同。
     */
    protected static class TagEntrySingleElement<T> extends TagEntry<T> {
        final T element;

        TagEntrySingleElement(T element) {
            this.element = element;
        }

        /**
         * 单元素项仍然可以视为列表，其尺寸为1。
         *
         * @return 对于单元素项，其大小一律为1。
         */
        @Override
        public int size() {
            return 1;
        }

        /**
         * 单元素项内的元素是否为此元素。
         *
         * @param element 需要检测是否包含的元素。
         * @return 对于单元素项，“包含”某元素的充要条件是其内的单元素“等于”此元素。
         */
        @Override
        public boolean contains(Object element) {
            return Objects.equals(element, this.element);
        }

        @Override
        public UnmodifiableIterator<T> iterator() {
            return new UnmodifiableIterator<>() {
                int cursor = -1;

                @Override
                public boolean hasNext() {
                    return cursor == -1;
                }

                @Override
                public T next() {
                    cursor++;
                    return element;
                }
            };
        }
    }

    /**
     * 标签内的一个项，其中这个项为一个标签。
     *
     * @param <T> 与标签的元素相同。
     */
    protected static class TagEntryTag<T> extends TagEntry<T> {
        final ExtShapeTag<T> elementTag;

        TagEntryTag(ExtShapeTag<T> elementTag) {
            this.elementTag = elementTag;
        }

        @Override
        public UnmodifiableIterator<T> iterator() {
            return new UnmodifiableIterator<>() {
                final Iterator<T> elementTagIterator = elementTag.iterator();
                int cursor = -1;

                @Override
                public boolean hasNext() {
                    return elementTagIterator.hasNext();
                }

                @Override
                public T next() {
                    cursor++;
                    return elementTagIterator.next();
                }
            };
        }

        /**
         * 该项的尺寸，即为该项内的标签的尺寸。
         *
         * @return 该项内的标签的尺寸。
         */
        @Override
        public int size() {
            return elementTag.size();
        }

        /**
         * @param element 需要检测是否包含的元素。
         * @return 该项内的标签是否包含此元素。
         */
        @Override
        public boolean contains(Object element) {
            return elementTag.contains(element);
        }
    }

}
