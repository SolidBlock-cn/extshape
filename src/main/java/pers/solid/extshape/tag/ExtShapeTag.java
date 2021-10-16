package pers.solid.extshape.tag;

import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonWriter;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.util.AbstractContainableSet;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 一类标签，实质上是可以递归的列表。但是不能与 Minecraft 的原版标签混淆。它既可以包含一些某类元素，又可以包含另一个由此类元素组成的标签。标签的内容都是在运行时就设置的，并且可以在代码内部修改，不是从数据包中读取的，不受数据包影响。数据生成器会利用这些标签内容生成数据。<br>
 * “标签”实际上是一个“可递归包含的集合”，因此继承了 {@link AbstractContainableSet}。其中，{@link #entries} 是一个{@link Set 集合}（在构造器函数中，这个集合实际上是一个 {@link java.util.LinkedHashSet}，集合的元素是{@link TagEntry 标签项}。它可能是任意一个元素的单元素集，也有可能是另一个这种标签，因为这种标签自身也实现了 {@link TagEntry}。
 *
 * @param <E> 标签内的元素的类型。
 */
public class ExtShapeTag<E> extends AbstractContainableSet<E,TagEntry<E>,Set<TagEntry<E>>> implements TagEntry<E> {
    /**
     * 标签的命名空间id，有可能不存在。
     */
    public final @Nullable Identifier identifier;

    /**
     * 类似于数据包中的标签的“replace”参数。该参数目前仅用于生成JSON格式的数据，除此之外其值并无意义。
     */
    public boolean replace;

    @SafeVarargs
    public ExtShapeTag(E... elements) {
        this(null, elements);
    }

    /**
     * 创建一个用于本模组的标签。
     *
     * @param identifier 标签的命名空间id。
     * @param elements   标签的初始内容的集合。
     */
    public ExtShapeTag(@Nullable Identifier identifier, Collection<E> elements) {
        super(Util.make(Sets.newLinkedHashSet(), set -> {
            for (E element : elements) {
                set.add(TagEntry.of(element));
            }
        }));
        this.identifier = identifier;
        this.replace = false;
    }

    /**
     * 创建一个用于本模组的标签。
     *
     * @param identifier 标签的命名空间id。
     * @param elements   标签的初始内容的数组。
     */
    @SafeVarargs
    public ExtShapeTag(@Nullable Identifier identifier, E... elements) {
        super(Util.make(Sets.newLinkedHashSet(), set -> {
            for (E element : elements) {
                set.add(TagEntry.of(element));
            }
        }));
        this.identifier = identifier;
        this.replace = false;
    }

    /**
     * 获取该标签的命名空间id。
     *
     * @return 该标签的命名空间id。
     */
    public @Nullable Identifier getIdentifier() {
        return this.identifier;
    }

    /**
     * 向该标签内添加一个由同类元素组成的标签。有以下限制：<ul>
     *     <li>标签不能重复。</li>
     *     <li>不能将没有命名空间的id的标签添加到有命名空间id的标签中。</li>
     *     <li>不能将任何标签添加到它自己中（否则会造成无限递归）。</li>
     *     <li>若标签内已包含此标签，则会报错。</li></ul>
     * 此外，元素如果为 <code>null</code>，可以正常添加进去，但是日志记录中会输出错误，不会抛出错误。
     * <b>注意：</b>有些限制在未来版本会直接移植到 {@link pers.solid.extshape.util.AbstractContainableCollection} 中去。
     *
     * @param element 需要添加进此标签的标签，其元素类型必须一致。
     */
    public boolean addTag(ExtShapeTag<E> element) {
        if (element == null) {
            ExtShape.EXTSHAPE_LOGGER.error(String.format("Trying to add a null tag to tag %s.", this));
            return false;
        }
        if (this.identifier != null && element.identifier == null)
            throw new UnsupportedOperationException("Cannot add a tag without identifier to a tag with an identifier");
        if (element == this) throw new UnsupportedOperationException("Cannot add to a tag itself!");
        if (element.containsTag(this))
            throw new UnsupportedOperationException(String.format("Cannot add into this tag another tag that contains it! %s already contains %s.", element, this));
        if (this.directlyContainsTag(element))
            throw new UnsupportedOperationException(String.format("Cannot add a duplicate tag to a tag! %s already contains %s.", this,
                    element));
        return super.addCollection(element);
    }

    /**
     * @deprecated
     * @see #addTag(ExtShapeTag)
     */
    @Deprecated
    @Override
    public boolean addCollection(TagEntry<E> es) {
        throw new UnsupportedOperationException();
    }

    /**
     * @deprecated
     * @see #addAllTags(ExtShapeTag[])
     */
    @Deprecated
    @Override
    public boolean addAllCollections(Collection<TagEntry<E>> c) {
        throw new UnsupportedOperationException();
    }

    /**
     * 向标签内添加多个标签。
     *
     * @param elements 需要添加到该标签内的标签。
     * @see #addTag
     */
    @SafeVarargs
    public final void addAllTags(ExtShapeTag<E>... elements) {
        for (ExtShapeTag<E> element : elements) {
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
    public ExtShapeTag<E> addToTag(ExtShapeTag<E> tag) {
        tag.addTag(this);
        return this;
    }

    /**
     * @return 标签内是否包含标签。会递归检查，即检查标签内的标签是否含有此标签。
     */
    public boolean containsTag(ExtShapeTag<E> tag) {
        return containsEntry(tag);
    }

    /**
     * @return 标签是否直接包含标签。不会递归检查，即不会检查标签内的标签是否含有此标签。
     */
    public boolean directlyContainsTag(Object tag) {
        return this.directlyContains(tag);
    }

    /**
     * 获取某个元素的命名空间id。
     *
     * @param element 元素。
     * @return 命名空间id。
     */
    public Identifier getIdentifierOf(E element) {
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

        for (Collection<E> entry : this.entries) {
            if (entry instanceof ExtShapeTag) values.add("#" + ((ExtShapeTag<Object>) entry).getIdentifier());
            else if (entry instanceof ObjectLists.Singleton)
                values.add(getIdentifierOf((entry.iterator().next())).toString());
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
     * 将标签转换为普通列表。标签内的标签会被完全展开。列表元素有可能会重复。
     *
     * @return 转换后的列表。其大小应当于标签 {@link #size()} 返回的结果一致。
     */
    public List<E> asList() {
        return new ArrayList<>(this);
    }

    @Override
    public String toString() {
        return "ExtShapeTag{" + identifier + '}';
    }

    @Override
    public TagEntry<E> singletonOf(E o) {
        return TagEntry.of(o);
    }
}
