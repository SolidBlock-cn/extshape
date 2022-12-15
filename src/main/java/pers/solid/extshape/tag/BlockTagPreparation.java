package pers.solid.extshape.tag;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.tags.IdentifiedTag;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraftforge.common.ForgeTagHandler;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;

/**
 * 方块标签的数据生成。
 */
public class BlockTagPreparation extends TagPreparation<Block> {
  /**
   * 创建一个基础的用于本模组的方块标签。
   *
   * @param identifier      方块标签的 id。将会用于数据生成。
   * @param entryList       方块自身使用的集合。
   * @param tagPreparations 包含多个方块标签的集合。
   */
  protected BlockTagPreparation(RuntimeResourcePack pack, Identifier identifier, Collection<Block> entryList, Collection<TagPreparation<Block>> tagPreparations) {
    super(pack, identifier, entryList, tagPreparations);
  }

  /**
   * 将自身<b>添加到</b>另一个方块标签中。返回标签自身，可以递归调用。
   *
   * @param anotherTag 需要将此标签添加到的标签。
   */
  @Override
  public BlockTagPreparation addSelfToTag(TagPreparation<Block> anotherTag) {
    return (BlockTagPreparation) super.addSelfToTag(anotherTag);
  }

  @Override
  public Tag<Block> toVanillaTag() {
    return ForgeTagHandler.makeWrapperTag(ForgeRegistries.BLOCKS, identifier);
  }

  @Override
  public IdentifiedTag toBRRPDataGenerationTag() {
    return new IdentifiedTag("blocks", identifier);
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
   * 将该标签转化为游戏内使用的物品标签对象。对于 1.18.2 以上的版本，应当使用 {@code TagKey.of}。
   *
   * @return 物品标签对象。
   */
  public Tag<Item> toVanillaItemTag() {
    return ForgeTagHandler.makeWrapperTag(ForgeRegistries.ITEMS, identifier);
  }
}
