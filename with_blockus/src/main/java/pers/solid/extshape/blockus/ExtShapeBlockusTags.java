package pers.solid.extshape.blockus;

import com.google.common.base.Functions;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.tag.TagPreparations;

public final class ExtShapeBlockusTags {

  public static final TagPreparations TAG_PREPARATIONS = new TagPreparations();
  public static final ImmutableMap<BlockShape, TagKey<Block>> GLAZED_TERRACOTTA_PILLAR_TAGS = BlockShape.values().stream().filter(blockShape -> blockShape.isConstruction).collect(ImmutableMap.toImmutableMap(Functions.identity(), blockShape -> TagKey.of(RegistryKeys.BLOCK, ExtShapeBlockus.id("glazed_terracotta_" + blockShape.asString()))));

  static TagKey<Block> ofBlockOnly(@NotNull Identifier identifier) {
    return TagKey.of(RegistryKeys.BLOCK, identifier);
  }

  static TagKey<Block> ofBlockAndItem(@NotNull Identifier identifier) {
    final TagKey<Block> blockTag = ofBlockOnly(identifier);
    TAG_PREPARATIONS.forceSetBlockTagWithItem(blockTag);
    return blockTag;
  }

  static TagKey<Block> ofBlockAndItem(@NotNull TagKey<Block> blockTag) {
    return ofBlockAndItem(blockTag, TagKey.of(RegistryKeys.ITEM, blockTag.id()));
  }

  static TagKey<Block> ofBlockAndItem(@NotNull TagKey<Block> blockTag, @NotNull TagKey<Item> itemTag) {
    Preconditions.checkArgument(blockTag.id().equals(itemTag.id()));
    TAG_PREPARATIONS.forceSetBlockTagWithItem(blockTag);
    return blockTag;
  }

}
