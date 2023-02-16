package pers.solid.extshape.tag;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.tag.Tag;
import net.minecraftforge.common.ForgeTagHandler;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Contract;
import pers.solid.brrp.v1.api.RuntimeResourcePack;
import pers.solid.brrp.v1.tag.IdentifiedTagBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>此类用于记录一个模组需要的方块和物品标签的内容，从而在后续进行数据生成。你可以通过此类的各种 {@code put} 方法来添加，也可以在构建方块对象时，通过 {@link pers.solid.extshape.builder.AbstractBlockBuilder#addExtraTag(Tag.Identified)} 等方式添加。
 * <p>这些标签在添加之后，并不会直接被游戏识别。因此，此类只是在记录，你可以通过 {@link #write(RuntimeResourcePack)} 方法将其写入到运行时资源包中。在写入完成后，如果不再需要使用此对象的内容，可以调用 {@link #clear()} 方法来释放内存。
 */
public class TagPreparations {
  private final Multimap<Tag.Identified<Block>, Object> blockTags = LinkedHashMultimap.create();
  private final Multimap<Tag.Identified<Item>, Object> itemTags = LinkedHashMultimap.create();
  private final Map<Tag.Identified<Block>, Tag.Identified<Item>> blockTagsToItemTags = new HashMap<>();

  @SuppressWarnings("unchecked")
  @Contract(mutates = "this")
  @CanIgnoreReturnValue
  public boolean put(Tag.Identified<? extends ItemConvertible> tagKey, ItemConvertible object) {
    if (object instanceof Block) {
      return blockTags.put((Tag.Identified<Block>) tagKey, object);
    } else if (object instanceof Item) {
      return itemTags.put((Tag.Identified<Item>) tagKey, object.asItem());
    }
    throw new IllegalArgumentException("Only blocks and items are accepted.");
  }

  @SuppressWarnings("unchecked")
  @Contract(mutates = "this")
  @CanIgnoreReturnValue
  public <T> boolean put(Tag.Identified<T> to, Tag.Identified<?> tagKeyInIt, Class<T> classFilter) {
    if (classFilter == Block.class) {
      return blockTags.put((Tag.Identified<Block>) to, tagKeyInIt);
    } else if (classFilter == Item.class) {
      return itemTags.put((Tag.Identified<Item>) to, tagKeyInIt);
    }
    throw new IllegalArgumentException("Only block or item tags are accepted");
  }

  public boolean put(Tag.Identified<Block> to, Tag.Identified<Block> tagKeyInIt) {
    return put(to, tagKeyInIt, Block.class);
  }

  @SuppressWarnings("unchecked")
  @Contract(mutates = "this")
  @CanIgnoreReturnValue
  public <T> boolean putAll(Tag.Identified<?> tagKey, Iterable<?> objects, Class<T> classFilter) {
    if (classFilter == Block.class) {
      return blockTags.putAll((Tag.Identified<Block>) tagKey, objects);
    } else if (classFilter == Item.class) {
      return itemTags.putAll((Tag.Identified<Item>) tagKey, objects);
    }
    throw new IllegalArgumentException("Only blocks and items are accepted.");
  }

  @Contract(mutates = "this")
  @CanIgnoreReturnValue
  public boolean putAll(Tag.Identified<Block> tagKey, Block... blocks) {
    return blockTags.put(tagKey, Arrays.asList(blocks));
  }

  @Contract(mutates = "this")
  @CanIgnoreReturnValue
  public boolean putAll(Tag.Identified<Item> tagKey, Item... items) {
    return itemTags.put(tagKey, Arrays.asList(items));
  }

  @SuppressWarnings("unchecked")
  @Contract(mutates = "this")
  @CanIgnoreReturnValue
  public <T> boolean putAllTags(Tag.Identified<T> tagKey, Iterable<Tag.Identified<?>> tagKeys, Class<T> classFilter) {
    if (classFilter == Block.class) {
      return blockTags.putAll((Tag.Identified<Block>) tagKey, tagKeys);
    } else if (classFilter == Item.class) {
      return itemTags.putAll((Tag.Identified<Item>) tagKey, tagKeys);
    }
    throw new IllegalArgumentException("Only block or item tags are accepted.");
  }

  @Contract(mutates = "this")
  @CanIgnoreReturnValue
  public <T> boolean putAllTags(Tag.Identified<T> tagKey, Class<T> classFilter, Tag.Identified<?>... tagKeys) {
    return putAll(tagKey, Arrays.asList(tagKeys), classFilter);
  }

  @Contract(mutates = "this")
  public void setBlockTagWithItem(Tag.Identified<Block> blockTagKey, Tag.Identified<Item> itemTagKey) {
    blockTagsToItemTags.put(blockTagKey, itemTagKey);
  }

  @Contract(pure = true)
  public Tag.Identified<Item> getItemTagOf(Tag.Identified<Block> blockTagKey) {
    return blockTagsToItemTags.get(blockTagKey);
  }

  @Contract(mutates = "this")
  public void forceSetBlockTagWithItem(Tag.Identified<Block> blockTagKey) {
    final Tag.Identified<Item> itemTagKey = ForgeTagHandler.makeWrapperTag(ForgeRegistries.ITEMS, blockTagKey.getId());
    blockTagsToItemTags.put(blockTagKey, itemTagKey);
  }

  @Contract(mutates = "this")
  public void setBlockTagWithoutItem(Tag.Identified<Block> tagKey) {
    blockTagsToItemTags.remove(tagKey);
  }

  /**
   * 将本对象记录的所有标签写入到运行时资源包中。
   */
  @Contract(mutates = "param1")
  public void write(RuntimeResourcePack pack) {
    blockTags.asMap().forEach((tagKey, objects) -> {
      if (objects.isEmpty()) return;
      final IdentifiedTagBuilder<Block> identifiedTag = IdentifiedTagBuilder.createBlock(tagKey);
      for (Object object : objects) {
        if (object instanceof Block block) {
          identifiedTag.add(block);
        } else if (object instanceof Tag.Identified<?> tagKey1) {
          identifiedTag.addTag(tagKey1.getId());
        }
      }
      pack.addTag(identifiedTag);
      if (blockTagsToItemTags.containsKey(tagKey)) {
        final Tag.Identified<Item> itemTag = blockTagsToItemTags.get(tagKey);
        final IdentifiedTagBuilder<Item> itemIdentifiedTag = IdentifiedTagBuilder.createItem(itemTag);
        for (Object object : objects) {
          if (itemTags.containsKey(itemTag)) {
            throw new IllegalStateException("Duplicated tag ids of independent item tag and block-tag-affiliated item tag.");
          }
          if (object instanceof Block block) {
            itemIdentifiedTag.add(block.asItem());
          } else if (object instanceof Tag.Identified<?> tagKey1) {
            itemIdentifiedTag.addTag(tagKey1.getId());
          }
        }
        pack.addTag(itemIdentifiedTag);
      }
    });
    itemTags.asMap().forEach((tagKey, objects) -> {
      if (objects.isEmpty()) return;
      final IdentifiedTagBuilder<Item> identifiedTag = IdentifiedTagBuilder.createItem(tagKey);
      for (Object object : objects) {
        if (object instanceof Item item) {
          identifiedTag.add(item);
        } else if (object instanceof Tag.Identified<?> tagKey1) {
          identifiedTag.addTag(tagKey1.getId());
        }
      }
      pack.addTag(identifiedTag);
    });
  }

  /**
   * 清除本对象中记录的所有内容。
   */
  @Contract(mutates = "this")
  public void clear() {
    blockTags.clear();
    itemTags.clear();
    blockTagsToItemTags.clear();
  }
}
