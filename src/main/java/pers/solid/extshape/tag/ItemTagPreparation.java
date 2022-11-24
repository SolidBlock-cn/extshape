package pers.solid.extshape.tag;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collection;

/**
 * 物品标签的数据生成。如需同时用于方块和物品的，可使用 {@link BlockItemTagPreparation}。
 */
@ApiStatus.AvailableSince("1.5.0")
public class ItemTagPreparation extends TagPreparation<Item> {
  protected ItemTagPreparation(RuntimeResourcePack pack, Identifier identifier, Collection<Item> entryList, Collection<TagPreparation<Item>> tagList) {
    super(pack, identifier, entryList, tagList);
  }

  @Override
  public Identifier getIdentifierOf(Item item) {
    return Registry.ITEM.getId(item.asItem());
  }

  @Override
  public TagKey<Item> toVanillaTag() {
    return TagKey.of(Registry.ITEM_KEY, identifier);
  }
}
