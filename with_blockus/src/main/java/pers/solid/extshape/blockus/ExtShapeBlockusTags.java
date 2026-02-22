package pers.solid.extshape.blockus;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import pers.solid.extshape.builder.BlockShape;

public final class ExtShapeBlockusTags {
  public static final ImmutableMap<BlockShape, TagKey<Block>> GLAZED_TERRACOTTA_PILLAR_TAGS = BlockShape.values().stream().filter(blockShape -> blockShape.isConstruction).collect(ImmutableMap.toImmutableMap(Functions.identity(), blockShape -> TagKey.create(Registries.BLOCK, ExtShapeBlockus.id("glazed_terracotta_pillar_" + blockShape.getSerializedName()))));
}
