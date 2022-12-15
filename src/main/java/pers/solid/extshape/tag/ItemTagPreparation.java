package pers.solid.extshape.tag;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.tags.IdentifiedTag;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraftforge.common.ForgeTagHandler;
import net.minecraftforge.registries.ForgeRegistries;
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
    return ForgeRegistries.ITEMS.getKey(item.asItem());
  }

  @Override
  public Tag<Item> toVanillaTag() {
    return ForgeTagHandler.makeWrapperTag(ForgeRegistries.ITEMS, identifier);
  }

  @Override
  public IdentifiedTag toBRRPDataGenerationTag() {
    return new IdentifiedTag("items", identifier);
  }
}
