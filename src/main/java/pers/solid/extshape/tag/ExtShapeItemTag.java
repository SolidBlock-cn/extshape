package pers.solid.extshape.tag;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Collection;
import java.util.HashSet;

public class ExtShapeItemTag extends UsableTag<ItemConvertible> {
  protected ExtShapeItemTag(Identifier identifier, Collection<ItemConvertible> entryList, Collection<UsableTag<ItemConvertible>> tagList) {
    super(identifier, entryList, tagList);
  }

  public static ExtShapeItemTag create(String namespace, String path, ItemConvertible... itemConvertibles) {
    return new ExtShapeItemTag(new Identifier(namespace, path), Lists.newArrayList(itemConvertibles), new HashSet<>());
  }

  @Override
  public Identifier getIdentifierOf(ItemConvertible item) {
    return Registry.ITEM.getId(item.asItem());
  }
}
