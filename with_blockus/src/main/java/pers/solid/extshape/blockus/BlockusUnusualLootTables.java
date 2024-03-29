package pers.solid.extshape.blockus;

import com.brand.blockus.content.BlockusBlocks;
import com.brand.blockus.content.BlockusItems;
import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.vanilla.VanillaBlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.LimitCountLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.operator.BoundedIntUnaryOperator;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Util;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.rrp.UnusualLootTables;

/**
 * @see pers.solid.extshape.rrp.UnusualLootTables
 */
public final class BlockusUnusualLootTables {
  @Unmodifiable
  public static final ImmutableMap<Block, UnusualLootTables.@NotNull LootTableFunction> INSTANCE = Util.make(() -> {
    final ImmutableMap.Builder<Block, UnusualLootTables.LootTableFunction> builder = new ImmutableMap.Builder<>();
    registerUnusualLootTables(builder);
    return builder.build();
  });

  private BlockusUnusualLootTables() {
  }

  private static void registerUnusualLootTables(ImmutableMap.Builder<Block, UnusualLootTables.LootTableFunction> builder) {
    final VanillaBlockLootTableGenerator blockLootTableGenerator = new VanillaBlockLootTableGenerator();
    builder.put(BlockusBlocks.ICE_BRICKS, UnusualLootTables.DROPS_DOUBLE_WITH_SILK_TOUCH_OR_NONE);
    builder.put(BlockusBlocks.RAINBOW_GLOWSTONE, (baseBlock, shape, block) -> {
      final float shapeVolume = UnusualLootTables.shapeVolume(shape);
      final LootTable.Builder lootTableBuilder = UnusualLootTables.dropsDoubleSlabWithSilkTouch(block, blockLootTableGenerator.applyExplosionDecay(block, ItemEntry.builder(Items.GLOWSTONE_DUST)
              .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2 * shapeVolume, 4 * shapeVolume)))
              .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE))
              .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create((int) shapeVolume, (int) (shapeVolume * 4))))),
          shape == BlockShape.SLAB ? ItemEntry.builder(Items.GLOWSTONE_DUST)
              .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 4)))
              .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE))
              .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create(1, 4))) : null);
      UnusualLootTables.addDropsDoubleSlabPool(lootTableBuilder, block, UnusualLootTables.WITH_SILK_TOUCH, blockLootTableGenerator.applyExplosionDecay(block, ItemEntry.builder(BlockusItems.RAINBOW_PETAL))
              .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1)))
              .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE))
              .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create((int) shapeVolume, (int) (shapeVolume * 4)))),
          shape == BlockShape.SLAB ? blockLootTableGenerator.applyExplosionDecay(block, ItemEntry.builder(BlockusItems.RAINBOW_PETAL))
              .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1)))
              .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE))
              .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create(1, 4))) : null);
      return lootTableBuilder;
    });
    builder.put(BlockusBlocks.BURNT_PAPER_BLOCK, UnusualLootTables.dropsWithSilkTouchOrConst(Items.GUNPOWDER, 2));
    builder.put(BlockusBlocks.NETHER_STAR_BLOCK, (baseBlock, shape, block) -> {
      final float shapeVolume = UnusualLootTables.shapeVolume(shape);
      return UnusualLootTables.dropsDoubleSlabWithSilkTouch(block, blockLootTableGenerator.applyExplosionDecay(block, ItemEntry.builder(Items.NETHER_STAR)
              .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(8.0F * shapeVolume, 9.0F * shapeVolume)))
              .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE, 2))
              .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create((int) (8 * shapeVolume), (int) (9 * shapeVolume))))),
          shape == BlockShape.SLAB ? blockLootTableGenerator.applyExplosionDecay(block, ItemEntry.builder(Items.NETHER_STAR)
              .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(8, 9)))
              .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE, 2))
              .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create(8, 9)))) : null);
    });
  }
}
