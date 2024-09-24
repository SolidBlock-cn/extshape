package pers.solid.extshape.blockus;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import pers.solid.extshape.builder.BlockShape;

public final class ExtShapeBlockusTags {
  public static final ImmutableMap<BlockShape, TagKey<Block>> GLAZED_TERRACOTTA_PILLAR_TAGS = BlockShape.values().stream().filter(blockShape -> blockShape.isConstruction).collect(ImmutableMap.toImmutableMap(Functions.identity(), blockShape -> TagKey.of(RegistryKeys.BLOCK, ExtShapeBlockus.id("glazed_terracotta_" + blockShape.asString()))));
}
