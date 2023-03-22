package pers.solid.extshape.tag;

import com.google.common.base.Preconditions;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Contract;
import pers.solid.brrp.v1.api.RuntimeResourcePack;
import pers.solid.brrp.v1.tag.IdentifiedTagBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>此类用于记录一个模组需要的方块和物品标签的内容，从而在后续进行数据生成。你可以通过此类的各种 {@code put} 方法来添加，也可以在构建方块对象时，通过 {@link pers.solid.extshape.builder.AbstractBlockBuilder#addExtraTag(TagKey)} 等方式添加。
 * <p>这些标签在添加之后，并不会直接被游戏识别。因此，此类只是在记录，你可以通过 {@link #write(RuntimeResourcePack)} 方法将其写入到运行时资源包中。在写入完成后，如果不再需要使用此对象的内容，可以调用 {@link #clear()} 方法来释放内存。
 */
public class TagPreparations {
  private final Multimap<TagKey<Block>, Object> blockTags = LinkedHashMultimap.create();
  private final Multimap<TagKey<Item>, Object> itemTags = LinkedHashMultimap.create();
  private final Map<TagKey<Block>, TagKey<Item>> blockTagsToItemTags = new HashMap<>();

  @Contract(mutates = "this")
  @CanIgnoreReturnValue
  public boolean put(TagKey<? extends ItemConvertible> tagKey, ItemConvertible object) {
    if (tagKey.registry() == RegistryKeys.BLOCK) {
      Preconditions.checkArgument(object instanceof Block);
      return blockTags.put((TagKey<Block>) tagKey, object);
    } else if (tagKey.registry() == RegistryKeys.ITEM) {
      return itemTags.put((TagKey<Item>) tagKey, object.asItem());
    }
    throw new IllegalArgumentException("Only blocks and items are accepted.");
  }

  @Contract(mutates = "this")
  @CanIgnoreReturnValue
  public boolean put(TagKey<?> to, TagKey<?> tagKeyInIt) {
    if (to.registry() == RegistryKeys.BLOCK) {
      return blockTags.put((TagKey<Block>) to, tagKeyInIt);
    } else if (to.registry() == RegistryKeys.ITEM) {
      return itemTags.put((TagKey<Item>) to, tagKeyInIt);
    }
    throw new IllegalArgumentException("Only block or item tags are accepted");
  }

  @Contract(mutates = "this")
  @CanIgnoreReturnValue
  public boolean putAll(TagKey<?> tagKey, Iterable<?> objects) {
    if (tagKey.registry() == RegistryKeys.BLOCK) {
      return blockTags.putAll((TagKey<Block>) tagKey, objects);
    } else if (tagKey.registry() == RegistryKeys.ITEM) {
      return itemTags.putAll((TagKey<Item>) tagKey, objects);
    }
    throw new IllegalArgumentException("Only blocks and items are accepted.");
  }

  @Contract(mutates = "this")
  @CanIgnoreReturnValue
  public boolean putAll(TagKey<Block> tagKey, Block... blocks) {
    return blockTags.put(tagKey, Arrays.asList(blocks));
  }

  @Contract(mutates = "this")
  @CanIgnoreReturnValue
  public boolean putAll(TagKey<Item> tagKey, Item... items) {
    return itemTags.put(tagKey, Arrays.asList(items));
  }

  @Contract(mutates = "this")
  @CanIgnoreReturnValue
  public boolean putAllTags(TagKey<?> tagKey, Iterable<TagKey<?>> tagKeys) {
    if (tagKey.registry() == RegistryKeys.BLOCK) {
      return blockTags.putAll((TagKey<Block>) tagKey, tagKeys);
    } else if (tagKey.registry() == RegistryKeys.ITEM) {
      return itemTags.putAll((TagKey<Item>) tagKey, tagKeys);
    }
    throw new IllegalArgumentException("Only block or item tags are accepted.");
  }

  @Contract(mutates = "this")
  @CanIgnoreReturnValue
  public boolean putAllTags(TagKey<?> tagKey, TagKey<?>... tagKeys) {
    return putAll(tagKey, Arrays.asList(tagKeys));
  }

  @Contract(mutates = "this")
  public void setBlockTagWithItem(TagKey<Block> blockTagKey, TagKey<Item> itemTagKey) {
    blockTagsToItemTags.put(blockTagKey, itemTagKey);
  }

  @Contract(pure = true)
  public TagKey<Item> getItemTagOf(TagKey<Block> blockTagKey) {
    return blockTagsToItemTags.get(blockTagKey);
  }

  @Contract(mutates = "this")
  public void forceSetBlockTagWithItem(TagKey<Block> blockTagKey) {
    blockTagsToItemTags.put(blockTagKey, TagKey.of(RegistryKeys.ITEM, blockTagKey.id()));
  }

  @Contract(mutates = "this")
  public void setBlockTagWithoutItem(TagKey<Block> tagKey) {
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
        } else if (object instanceof TagKey<?> tagKey1) {
          identifiedTag.addTag(tagKey1.id());
        }
      }
      pack.addTag(identifiedTag);
      if (blockTagsToItemTags.containsKey(tagKey)) {
        final TagKey<Item> itemTag = blockTagsToItemTags.get(tagKey);
        final IdentifiedTagBuilder<Item> itemIdentifiedTag = IdentifiedTagBuilder.createItem(itemTag);
        for (Object object : objects) {
          if (itemTags.containsKey(itemTag)) {
            throw new IllegalStateException("Duplicated tag ids of independent item tag and block-tag-affiliated item tag.");
          }
          if (object instanceof Block block) {
            itemIdentifiedTag.add(block.asItem());
          } else if (object instanceof TagKey<?> tagKey1) {
            itemIdentifiedTag.addTag(tagKey1.id());
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
        } else if (object instanceof TagKey<?> tagKey1) {
          identifiedTag.addTag(tagKey1.id());
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
