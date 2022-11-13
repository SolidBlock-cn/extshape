package pers.solid.extshape.tag;

import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Contract;
import pers.solid.extshape.ExtShape;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 本模组使用的方块标签。需要注意的是，这些标签是内置的会直接存储内容，不会从数据包中加载。不应与 {@link net.minecraft.tag.BlockTags} 混淆。但是，可以使用 {@link #toBlockTag()} 以在需要原版标签对象时使用，或者使用 {@link #toARRP()} 转换成运行时资源包使用的数据。
 */
public class ExtShapeBlockTag extends UsableTag<Block> {
  /**
   * 创建一个基础的用于本模组的方块标签。
   *
   * @param identifier 方块标签的 id。将会用于数据生成。
   * @param entryList  方块自身使用的集合。
   * @param usableTags 包含多个方块标签的集合。
   */
  protected ExtShapeBlockTag(Identifier identifier, Collection<Block> entryList, Collection<UsableTag<Block>> usableTags) {
    super(identifier, entryList, usableTags);
  }

  /**
   * 创建一个没有 id 的方块标签。
   *
   * @param blocks 方块标签内的初始元素。
   * @return 新的没有 id 的方块标签。
   */
  @Contract(value = "_ -> new", pure = true)
  public static ExtShapeBlockTag create(Block... blocks) {
    return new ExtShapeBlockTag(null, Lists.newArrayList(blocks), new ArrayList<>());
  }

  /**
   * 创建一个指定 id 的方块标签。这里为了方便，直接使用拆开的 id 字符串和路径，因为主要用于本模组。
   *
   * @param namespace 标识符的命名空间。
   * @param path      标识符的路径。
   * @return 新的具有指定 id 的方块标签。
   */
  @Contract(value = "_, _ -> new", pure = true)
  public static ExtShapeBlockTag create(String namespace, String path) {
    return new ExtShapeBlockTag(new Identifier(namespace, path), new ArrayList<>(), new ArrayList<>());
  }

  @Contract(value = "_ -> new", pure = true)
  static ExtShapeBlockTag create(String path) {
    return create(ExtShape.MOD_ID, path);
  }

  /**
   * 根据 Minecraft 原版代码中的方块标签（即 {@link net.minecraft.tag.BlockTags} 中的）创建一个新的元素。这里的标识符将直接使用那个标签拥有的标识符。
   *
   * @param vanillaBlockTag 原版的方块标签对象。
   * @return 新的使用已有参数的标识符的方块标签。
   */
  @Contract(value = "_ -> new", pure = true)
  public static ExtShapeBlockTag create(TagKey<Block> vanillaBlockTag) {
    return new ExtShapeBlockTag(vanillaBlockTag.id(), new ArrayList<>(), new ArrayList<>());
  }

  /**
   * 将自身<b>添加到</b>另一个方块标签中。返回标签自身，可以递归调用。
   *
   * @param anotherTag 需要将此标签添加到的标签。
   */
  @Override
  public ExtShapeBlockTag addSelfToTag(UsableTag<Block> anotherTag) {
    return (ExtShapeBlockTag) super.addSelfToTag(anotherTag);
  }

  /**
   * 获取某个方块的命名空间id。
   *
   * @param element 方块。
   * @return 方块的命名空间id。
   */
  @Override
  public Identifier getIdentifierOf(Block element) {
    return ForgeRegistries.BLOCKS.getKey(element);
  }

  /**
   * 将该标签转化为游戏内使用的方块标签对象。对于 1.18.2 以上的版本，应当使用 {@code TagKey.of}。
   *
   * @return 方块标签对象。
   */
  @Contract(value = "-> new", pure = true)
  public TagKey<Block> toBlockTag() {
    return TagKey.of(Registry.BLOCK_KEY, identifier);
  }

  /**
   * 将该标签转化为游戏内使用的物品标签对象。对于 1.18.2 以上的版本，应当使用 {@code TagKey.of}。
   *
   * @return 物品标签对象。
   */
  @Contract(value = "-> new", pure = true)
  public TagKey<Item> toItemTag() {
    return TagKey.of(Registry.ITEM_KEY, identifier);
  }
}
