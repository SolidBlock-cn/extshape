package pers.solid.extshape.tag;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 本模组使用的物品标签。通常来说，方块标签已经可以顶替物品标签。
 */
@ApiStatus.AvailableSince("1.5.0")
public class ExtShapeItemTag extends UsableTag<ItemConvertible> {
  protected ExtShapeItemTag(Identifier identifier, Collection<ItemConvertible> entryList, Collection<UsableTag<ItemConvertible>> tagList) {
    super(identifier, entryList, tagList);
  }

  public static ExtShapeItemTag create(String namespace, String path) {
    return new ExtShapeItemTag(new Identifier(namespace, path), new ArrayList<>(), new ArrayList<>());
  }

  public static ExtShapeItemTag create(TagKey<Item> vanillaItemTag) {
    return new ExtShapeItemTag(vanillaItemTag.id(), new ArrayList<>(), new ArrayList<>());
  }

  @Override
  public Identifier getIdentifierOf(ItemConvertible item) {
    return ForgeRegistries.ITEMS.getKey(item.asItem());
  }
}
