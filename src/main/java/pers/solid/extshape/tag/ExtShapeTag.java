package pers.solid.extshape.tag;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ExtShapeTag<T> implements Iterable<T> {
    // 数据包内的标签

    public final Identifier identifier;
    //    public final List<T> list;
//    public final List<ExtShapeTag<T>> listOfTags;
    public final List<TagEntry<T>> entryList;
    public boolean replace;

    protected static class TagEntry<T> implements Iterable<T> {
        final T element;
        final ExtShapeTag<T> elementTag;
        final boolean isTag;

        TagEntry(T element) {
            this.element = element;
            this.elementTag = null;
            this.isTag = false;
        }

        TagEntry(ExtShapeTag<T> elementTag) {
            this.elementTag = elementTag;
            this.element = null;
            this.isTag = true;
        }

        boolean contains(T element) {
            if (this.isTag) {
                assert this.elementTag != null;
                return this.elementTag.contains(element);
            } else {
                return this.element == element;
            }
        }

        public int size() {
            if (this.isTag) {
                assert this.elementTag != null;
                return this.elementTag.size();
            } else return 1;
        }

        public T get(int index) {
            if (this.isTag) {
                assert this.elementTag != null;
                return this.elementTag.entryList.get(index).get(0);
            } else if (index == 0) return this.element;
            else throw new IndexOutOfBoundsException("The single element entry has only index 0.");
        }

        @NotNull
        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {

                int cursor = -1;
                @Nullable
                final Iterator<T> elementTagIterator = elementTag == null ? null : elementTag.iterator();

                @Override
                public boolean hasNext() {
                    if (!isTag) return cursor == -1;
                    else {
                        assert elementTagIterator != null;
                        return elementTagIterator.hasNext();
                    }
                }

                @Override
                public T next() {
                    cursor++;
                    if (!isTag) {
                        return element;
                    } else {
                        assert elementTagIterator != null;
                        return elementTagIterator.next();
                    }
                }
            };
        }
    }

    public ExtShapeTag(List<T> list) {
        this(null, list);
    }

    public ExtShapeTag(Identifier identifier) {
        this(identifier, new ArrayList<>());
    }

    public ExtShapeTag(Identifier identifier, List<T> list) {
        this.identifier = identifier;
//        this.list = new ArrayList<>();
//        this.list.addAll(list);
//        this.listOfTags = new ArrayList<>();
        this.entryList = new ArrayList<>();
        list.forEach(e -> this.entryList.add(new TagEntry<T>(e)));
    }

    public Identifier getIdentifier() {
        return this.identifier;
    }

    public void add(T element) {
        if (this.directlyContains(element)) throw new RuntimeException(String.format("Cannot add a duplicate element " +
                "to a tag! %s already contains %s.", this, element));
        this.entryList.add(new TagEntry<T>(element));
    }

    public void addTag(ExtShapeTag<T> element) {
        if (this.identifier != null && element.identifier == null)
            throw new IllegalStateException("Cannot add a tag without " +
                    "identifier" +
                    " to a tag with " +
                    "an identifier");
        if (element == this) throw new RuntimeException("Cannot add to a tag itself!");
        if (element.containsTag(this))
            throw new RuntimeException(String.format("Cannot add into this tag another tag that contains" +
                    " it! %s already contains %s.", element, this));
        if (this.directlyContainsTag(element))
            throw new RuntimeException(String.format("Cannot add a duplicate tag to a tag! %s " +
                    "already contains %s.", this, element));
        this.entryList.add(new TagEntry<T>(element));
    }

    public void addAll(Collection<T> elements) {
        elements.forEach(this::add);
    }

    public void addAllTags(Collection<ExtShapeTag<T>> elements) {
        elements.forEach(this::addTag);
    }

    public ExtShapeTag<T> addToTag(ExtShapeTag<T> tag) {
        tag.addTag(this);
        return this;
    }

    public boolean contains(T element) {
        for (TagEntry<T> entry : entryList) if (entry.contains(element)) return true;
        return false;
    }

    public boolean containsTag(ExtShapeTag<T> tag) {
        for (TagEntry<T> entry :
                entryList)
            if (entry.elementTag != null && (entry.elementTag == tag || entry.elementTag.containsTag(tag))) return true;
        return false;
    }

    public boolean directlyContains(T element) {
        for (TagEntry<T> entry : entryList) if (entry.element == element) return true;
        return false;
    }

    public boolean directlyContainsTag(ExtShapeTag<T> tag) {
        for (TagEntry<T> entry : entryList) if (entry.elementTag == tag) return true;
        return false;
    }

    public Identifier getIdentifierOf(T element) {
        return null;
    }

    public JsonObject generateJson() {
        JsonObject main = new JsonObject();
        main.addProperty("replace", replace);

        JsonArray values = new JsonArray();
//        for (T element : list) values.add(getIdentifierOf(element).toString());
//        for (ExtShapeTag<T> element : listOfTags) {
//            if (element.getIdentifier() == null) {
//                System.out.println(element.identifier);
//                System.out.println(element.getIdentifier());
//            }
//            values.add("#" + element.getIdentifier().toString());
//        }

        for (TagEntry<T> entry : this.entryList) {
            if (entry.isTag) values.add("#" + entry.elementTag.getIdentifier());
            else values.add(getIdentifierOf(entry.element).toString());
        }

        main.add("values", values);
        return main;
    }

    public String generateString() {
        return this.generateJson().toString();
    }
//
//    @Override
//    public void forEach(Consumer<? super T> action) {
//        Objects.requireNonNull(action);
//        for (T t : this.list) {
//            action.accept(t);
//        }
//        for (ExtShapeTag<T> t : this.listOfTags) {
//            t.forEach(action);
//        }
//    }

    public List<T> asList() {
        List<T> list = new ArrayList<>();
        for (final T element : this) list.add(element);
        return list;
    }

//    public List<T> asListRaw(List<T> list) {
//        list.addAll(this.list);
//        for (final ExtShapeTag<T> element : this.listOfTags) element.asListRaw(list);
//        return list;
//    }

    public int size() {
        int size = 0;
        for (TagEntry<T> entry : this.entryList) size += entry.size();
        return size;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int cursor = 0;
            //            private final Iterator<T> listIterator = list.iterator();
//            private final Iterator<ExtShapeTag<T>> listOfTagsIterator = listOfTags.iterator();
            private Iterator<T> iteratingTagIterator = null;
            private final int rawSize = entryList.size();

            @Override
            public boolean hasNext() {
                return cursor != rawSize || iteratingTagIterator == null || iteratingTagIterator.hasNext();
            }

            @Override
            public T next() {
                int i = cursor;
                if (iteratingTagIterator == null || !iteratingTagIterator.hasNext()) {
                    iteratingTagIterator = entryList.get(i).iterator();
                    cursor++;
                }
                return iteratingTagIterator.next();
            }
        };
    }

    @Override
    public String toString() {
        return "ExtShapeTag{" + identifier +
                '}';
    }

    public T getEntry(int index) {
        // 获取EntryList里面的项目。如果这个Entry为Tag，则返回这个Tag的第一个元素。
        return this.entryList.get(index).get(0);
    }

}
