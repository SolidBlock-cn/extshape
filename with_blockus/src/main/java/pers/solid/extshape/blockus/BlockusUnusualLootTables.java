package pers.solid.extshape.blockus;

import com.brand.blockus.registry.content.BlockusBlocks;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.IntLimit;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.data.UnusualLootTables;

/**
 * @see UnusualLootTables
 */
public class BlockusUnusualLootTables extends UnusualLootTables {

  @Unmodifiable
  public final ImmutableMap<Block, UnusualLootTables.@NotNull LootTableFunction> createInstance() {
    final ImmutableMap.Builder<Block, UnusualLootTables.LootTableFunction> builder = new ImmutableMap.Builder<>();
    registerUnusualLootTables(builder);
    return builder.build();
  }

  private void registerUnusualLootTables(ImmutableMap.Builder<Block, UnusualLootTables.LootTableFunction> builder) {
    builder.put(BlockusBlocks.ICE_BRICKS, dropsDoubleWithSilkTouchOrNone);
    builder.put(BlockusBlocks.RAINBOW_GLOWSTONE, (baseBlock, shape, block, blocks, enchantments, generator) -> {
      final float shapeVolume = UnusualLootTables.shapeVolume(shape);
      return dropsDoubleSlabWithSilkTouch(blocks, block, generator.applyExplosionDecay(block, LootItem.lootTableItem(Items.GLOWSTONE_DUST)
              .apply(SetItemCountFunction.setCount(ContextIntProviders.fromFloat(ContextFloatProviders.between(2 * shapeVolume, 4 * shapeVolume))))
              .apply(fortuneFunction(enchantments))
              .apply(LimitCount.limitCount(IntLimit.range((int) shapeVolume, (int) (shapeVolume * 4))))),
          shape == BlockShape.SLAB ? LootItem.lootTableItem(Items.GLOWSTONE_DUST)
              .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4)))
              .apply(fortuneFunction(enchantments))
              .apply(LimitCount.limitCount(IntLimit.range(1, 4))) : null, generator);
    });
    builder.put(BlockusBlocks.BURNT_PAPER_BLOCK, dropsWithSilkTouchOrConst(Items.GUNPOWDER, 2));
    builder.put(BlockusBlocks.NETHER_STAR_BLOCK, (baseBlock, shape, block, blocks, enchantments, generator) -> {
      final float shapeVolume = UnusualLootTables.shapeVolume(shape);
      return dropsDoubleSlabWithSilkTouch(blocks, block, generator.applyExplosionDecay(block, LootItem.lootTableItem(Items.NETHER_STAR)
              .apply(SetItemCountFunction.setCount(ContextIntProviders.fromFloat(ContextFloatProviders.between(8.0F * shapeVolume, 9.0F * shapeVolume))))
              .apply(ApplyBonusCount.addUniformBonusCount(fortune(enchantments), 2))
              .apply(LimitCount.limitCount(IntLimit.range((int) (8 * shapeVolume), (int) (9 * shapeVolume))))),
          shape == BlockShape.SLAB ? generator.applyExplosionDecay(block, LootItem.lootTableItem(Items.NETHER_STAR)
              .apply(SetItemCountFunction.setCount(ContextIntProviders.between(8, 9)))
              .apply(ApplyBonusCount.addUniformBonusCount(fortune(enchantments), 2))
              .apply(LimitCount.limitCount(IntLimit.range(8, 9)))) : null, generator);
    });
  }
}
