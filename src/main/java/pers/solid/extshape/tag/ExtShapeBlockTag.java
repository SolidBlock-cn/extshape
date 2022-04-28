package pers.solid.extshape.tag;

import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.compress.utils.Sets;
import pers.solid.extshape.util.AbstractBiPartRecursiveCollection;
import pers.solid.extshape.util.AbstractLinkedRecursiveCollection;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * 本模组使用的方块标签。需要注意的是，这些标签是内置的会直接存储内容，不会从数据包中加载。不应与 {@link net.minecraft.tag.BlockTags} 混淆。
 */
public interface ExtShapeBlockTag extends UsableTag<Block> {
  static ExtShapeBlockTag createLinked(Block... elements) {
    return createLinked(null, elements);
  }

  static ExtShapeBlockTag createLinked(Identifier identifier, Block... elements) {
    return createLinked(identifier, Arrays.asList(elements));
  }

  static ExtShapeBlockTag createLinked(Identifier identifier, Collection<Block> list) {
    final Linked o = new Linked(list.stream().map(TagEntry::of).collect(Collectors.toSet()), identifier);
    ExtShapeBlockTags.TAG_LIST.add(o);
    return o;
  }

  static ExtShapeBlockTag createBiPart(Identifier identifier, Block... elements) {
    return createBiPart(identifier, Sets.newHashSet(elements));
  }

  static ExtShapeBlockTag createBiPart(Identifier identifier, Collection<Block> list) {
    final BiPart o = new BiPart(list, new HashSet<>(), identifier);
    ExtShapeBlockTags.TAG_LIST.add(o);
    return o;
  }

  /**
   * 将自身<b>添加到</b>另一个方块标签中。返回标签自身，可以递归调用。
   *
   * @param anotherTag 需要将此标签添加到的标签。
   */
  @Override
  default ExtShapeBlockTag addToTag(UsableTag<Block> anotherTag) {
    return (ExtShapeBlockTag) UsableTag.super.addToTag(anotherTag);
  }

  /**
   * 获取某个方块的命名空间id。
   *
   * @param element 方块。
   * @return 方块的命名空间id。
   */
  @Override
  default Identifier getIdentifierOf(Block element) {
    return Registry.BLOCK.getId(element);
  }

  final class Linked extends AbstractLinkedRecursiveCollection.TagImpl<Block> implements ExtShapeBlockTag {
    private Linked(Collection<TagEntry<Block>> elements, Identifier identifier) {
      super(elements, identifier);
    }
  }

  final class BiPart extends AbstractBiPartRecursiveCollection.TagImpl<Block> implements ExtShapeBlockTag {
    private BiPart(Collection<Block> directCollection, Collection<TagEntry<Block>> collectionCollection, Identifier identifier) {
      super(directCollection, collectionCollection, identifier);
    }
  }
}
