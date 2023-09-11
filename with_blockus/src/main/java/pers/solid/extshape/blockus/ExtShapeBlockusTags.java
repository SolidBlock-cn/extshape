package pers.solid.extshape.blockus;

import com.google.common.base.Preconditions;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.tag.TagPreparations;

public final class ExtShapeBlockusTags {

  public static final TagPreparations TAG_PREPARATIONS = new TagPreparations();


  static TagKey<Block> ofBlockAndItem(@NotNull TagKey<Block> blockTag) {
    return ofBlockAndItem(blockTag, TagKey.of(RegistryKeys.ITEM, blockTag.id()));
  }

  static TagKey<Block> ofBlockAndItem(@NotNull TagKey<Block> blockTag, @NotNull TagKey<Item> itemTag) {
    Preconditions.checkArgument(blockTag.id().equals(itemTag.id()));
    TAG_PREPARATIONS.forceSetBlockTagWithItem(blockTag);
    return blockTag;
  }

}
