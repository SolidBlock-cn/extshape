package pers.solid.extshape.tag;

import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.compress.utils.Sets;
import pers.solid.extshape.util.AbstractBiPartRecursiveCollection;

import java.util.Collection;
import java.util.HashSet;

public interface ExtShapeItemTag extends UsableTag<ItemConvertible> {
  static ExtShapeItemTag createBiPart(Identifier identifier, ItemConvertible... itemConvertibles) {
    return createBiPart(identifier, Sets.newHashSet(itemConvertibles));
  }

  static ExtShapeItemTag createBiPart(Identifier identifier, Collection<ItemConvertible> itemConvertibles) {
    return new BiPart(itemConvertibles, new HashSet<>(), identifier);
  }

  @Override
  default Identifier getIdentifierOf(ItemConvertible item) {
    return Registry.ITEM.getId(item.asItem());
  }

  final class BiPart extends AbstractBiPartRecursiveCollection.TagImpl<ItemConvertible> implements ExtShapeItemTag {
    private BiPart(Collection<ItemConvertible> directCollection, Collection<TagEntry<ItemConvertible>> collectionCollection, Identifier identifier) {
      super(directCollection, collectionCollection, identifier);
    }
  }
}
