package pers.solid.extshape.tag;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 本模组使用的方块标签。需要注意的是，这些标签是内置的会直接存储内容，不会从数据包中加载。不应与 {@link net.minecraft.tag.BlockTags} 混淆。
 */
public class ExtShapeBlockTag extends UsableTag<Block> {
  protected ExtShapeBlockTag(Identifier identifier, Collection<Block> entryList, Collection<UsableTag<Block>> usableTags) {
    super(identifier, entryList, usableTags);
  }

  public static ExtShapeBlockTag create(Block... elements) {
    return new ExtShapeBlockTag(null, Lists.newArrayList(elements), new ArrayList<>());
  }

  public static ExtShapeBlockTag create(String namespace, String path, Block... elements) {
    return new ExtShapeBlockTag(new Identifier(namespace, path), Lists.newArrayList(elements), new ArrayList<>());
  }

  /**
   * 将自身<b>添加到</b>另一个方块标签中。返回标签自身，可以递归调用。
   *
   * @param anotherTag 需要将此标签添加到的标签。
   */
  @Override
  public ExtShapeBlockTag addToTag(UsableTag<Block> anotherTag) {
    return (ExtShapeBlockTag) super.addToTag(anotherTag);
  }

  /**
   * 获取某个方块的命名空间id。
   *
   * @param element 方块。
   * @return 方块的命名空间id。
   */
  @Override
  public Identifier getIdentifierOf(Block element) {
    return Registry.BLOCK.getId(element);
  }
}
