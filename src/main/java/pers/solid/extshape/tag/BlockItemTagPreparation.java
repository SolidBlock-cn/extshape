package pers.solid.extshape.tag;

import net.devtech.arrp.json.tags.IdentifiedTag;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * 用于方块标签对应的等价物品标签。这个对象自身不能添加东西，可以视为“不可变的视图”。在生成运行时资源包的数据时，会直接使用其方块标签的数据。
 */
public class BlockItemTagPreparation extends ItemTagPreparation {
  public final TagPreparation<Block> blockTagPreparation;

  protected BlockItemTagPreparation(TagPreparation<Block> blockTagPreparation) {
    super(blockTagPreparation.pack, blockTagPreparation.identifier, null, null);
    this.blockTagPreparation = blockTagPreparation;
  }

  @Override
  public boolean add(Item item) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(@NotNull Collection<? extends Item> c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addTag(TagPreparation<Item> items) {
    throw new UnsupportedOperationException();
  }

  @Override
  public IdentifiedTag generateTag() {
    final IdentifiedTag jTag = new IdentifiedTag(toVanillaTag());
    for (Block e : blockTagPreparation.entryList) {
      jTag.add(getIdentifierOf(e.asItem()));
    }
    for (TagPreparation<Block> es : blockTagPreparation.tagList) {
      jTag.addTag(es.identifier);
    }
    return jTag;
  }
}
