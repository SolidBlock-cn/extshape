package pers.solid.extshape.blockus;

import com.brand.blockus.registry.content.BlockusBlocks;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.LimitCountLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.operator.BoundedIntUnaryOperator;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
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
    builder.put(BlockusBlocks.RAINBOW_GLOWSTONE, (baseBlock, shape, block, lookup, generator) -> {
      final float shapeVolume = UnusualLootTables.shapeVolume(shape);
      return dropsDoubleSlabWithSilkTouch(block, generator.applyExplosionDecay(block, ItemEntry.builder(Items.GLOWSTONE_DUST)
              .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2 * shapeVolume, 4 * shapeVolume)))
              .apply(fortuneFunction(lookup))
              .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create((int) shapeVolume, (int) (shapeVolume * 4))))),
          shape == BlockShape.SLAB ? ItemEntry.builder(Items.GLOWSTONE_DUST)
              .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 4)))
              .apply(fortuneFunction(lookup))
              .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create(1, 4))) : null, generator);
    });
    builder.put(BlockusBlocks.BURNT_PAPER_BLOCK, dropsWithSilkTouchOrConst(Items.GUNPOWDER, 2));
    builder.put(BlockusBlocks.NETHER_STAR_BLOCK, (baseBlock, shape, block, lookup, generator) -> {
      final float shapeVolume = UnusualLootTables.shapeVolume(shape);
      return dropsDoubleSlabWithSilkTouch(block, generator.applyExplosionDecay(block, ItemEntry.builder(Items.NETHER_STAR)
              .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(8.0F * shapeVolume, 9.0F * shapeVolume)))
              .apply(ApplyBonusLootFunction.uniformBonusCount(fortune(lookup), 2))
              .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create((int) (8 * shapeVolume), (int) (9 * shapeVolume))))),
          shape == BlockShape.SLAB ? generator.applyExplosionDecay(block, ItemEntry.builder(Items.NETHER_STAR)
              .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(8, 9)))
              .apply(ApplyBonusLootFunction.uniformBonusCount(fortune(lookup), 2))
              .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create(8, 9)))) : null, generator);
    });
  }
}
