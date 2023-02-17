package pers.solid.extshape.rrp;

import com.google.common.collect.ImmutableMap;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.generator.BlockResourceGenerator;
import net.devtech.arrp.json.loot.JLootTable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.SlabType;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.data.server.loottable.vanilla.VanillaBlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.TableBonusLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.LimitCountLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.operator.BoundedIntUnaryOperator;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.state.property.Properties;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import pers.solid.extshape.builder.BlockShape;

/**
 * <p>本类记录了用于本模组的不掉落方块本身的基础方块的战利品表。
 * <p>注册在 {@link #INSTANCE} 中的方块，在通过 {@link ExtShapeRRP#generateServerData(RuntimeResourcePack)} 生成战利品表时，不会使用 {@link BlockResourceGenerator#getLootTable()}，而是直接使用这里面注册了的战利品表函数。
 *
 * @author SolidBlock
 * @since 1.5.1
 */
@ApiStatus.AvailableSince("1.5.1")
public final class UnusualLootTables {
  @Unmodifiable
  public static final ImmutableMap<Block, @NotNull LootTableFunction> INSTANCE;
  private static final LootCondition.Builder WITH_SILK_TOUCH = MatchToolLootCondition.builder(net.minecraft.predicate.item.ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1))));
  private static final StatePredicate.Builder EXACT_MATCH_DOUBLE_SLAB = StatePredicate.Builder.create().exactMatch(Properties.SLAB_TYPE, SlabType.DOUBLE);

  static {
    final ImmutableMap.Builder<Block, LootTableFunction> builder = new ImmutableMap.Builder<>();
    registerUnusualLootTables(builder);
    INSTANCE = builder.build();
  }

  /**
   * 参照原版的战利品表生成器，生成对应变种的特殊战利品表。如果没有指定，则按照默认战利品表生成。特别需要注意：双台阶的战利品表掉落数量应该为两倍。
   *
   * @see net.minecraft.data.server.loottable.BlockLootTableGenerator#accept
   */
  private static void registerUnusualLootTables(ImmutableMap.Builder<Block, LootTableFunction> builder) {
    final VanillaBlockLootTableGenerator blockLootTableGenerator = new VanillaBlockLootTableGenerator();
    builder.put(Blocks.CLAY, dropsShaped(Items.CLAY_BALL, 4));
    builder.put(Blocks.SNOW_BLOCK, dropsShaped(Items.SNOWBALL, 4));
    builder.put(Blocks.GLOWSTONE, (baseBlock, shape, block) -> {
      final float shapeVolume = shapeVolume(shape);
      final LootTable.Builder lootTableBuilder = dropsDoubleSlabWithSilkTouch(block, blockLootTableGenerator.applyExplosionDecay(block, ItemEntry.builder(Items.GLOWSTONE_DUST)
          .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2 * shapeVolume, 4 * shapeVolume)))
          .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE))
          .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create((int) shapeVolume, (int) (shapeVolume * 4))))), shape == BlockShape.SLAB);
      return JLootTable.delegate(lootTableBuilder);
    });
    builder.put(Blocks.MELON, (baseBlock, shape, block) -> {
      final float shapeVolume = shapeVolume(shape);
      return JLootTable.delegate(dropsDoubleSlabWithSilkTouch(block, blockLootTableGenerator.applyExplosionDecay(block, ItemEntry.builder(Items.MELON_SLICE)
          .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(shapeVolume * 2, shapeVolume * 4)))
          .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE))
          .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create((int) shapeVolume, (int) (shapeVolume * 4))))
      ), shape == BlockShape.SLAB));
    });
    builder.put(Blocks.SEA_LANTERN, (baseBlock, shape, block) -> {
      final float shapeVolume = shapeVolume(shape);
      return JLootTable.delegate(dropsDoubleSlabWithSilkTouch(block, blockLootTableGenerator.applyExplosionDecay(block, ItemEntry.builder(Items.PRISMARINE_CRYSTALS)
          .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2 * shapeVolume, 3 * shapeVolume)))
          .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE))
          .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create((int) shapeVolume, (int) (5 * shapeVolume))))
      ), shape == BlockShape.SLAB));
    });
    builder.put(Blocks.GILDED_BLACKSTONE, (baseBlock, shape, block) -> {
      final float shapeVolume = shapeVolume(shape);
      return JLootTable.delegate(dropsDoubleSlabWithSilkTouch(block, blockLootTableGenerator.addSurvivesExplosionCondition(block, ItemEntry.builder(Items.GOLD_NUGGET)
          .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(shapeVolume * 2, shapeVolume * 5)))
          .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, 0.1F, 0.14285715F, 0.25F, 1.0F))
          .alternatively(ItemEntry.builder(block))
      ), shape == BlockShape.SLAB));
    });

    // 只有带有精准采集附魔时才会掉落的方块。
    final LootTableFunction dropsWithSilkTouch = (baseBlock, shape, block) -> JLootTable.delegate(dropsDoubleSlabWithSilkTouch(block, shape == BlockShape.SLAB));
    builder.put(Blocks.ICE, dropsWithSilkTouch);
    builder.put(Blocks.BLUE_ICE, dropsWithSilkTouch);
    builder.put(Blocks.PACKED_ICE, dropsWithSilkTouch);
    builder.put(Blocks.SCULK, dropsWithSilkTouch);
  }

  public interface LootTableFunction extends TriFunction<Block, BlockShape, Block, JLootTable> {
    @Override
    JLootTable apply(Block baseBlock, BlockShape shape, Block block);
  }

  /**
   * 对应形状估算的体积，用于与基础方块的掉落数相乘。
   */
  @Contract(pure = true)
  private static float shapeVolume(@NotNull BlockShape shape) {
    return shape.isConstruction ? shape.logicalCompleteness : 1;
  }

  private static ConstantLootNumberProvider shapeVolumeConstantProvider(@NotNull BlockShape shape, float count) {
    return ConstantLootNumberProvider.create(shapeVolume(shape) * count);
  }

  private static LootTableFunction dropsShaped(@NotNull ItemConvertible drop, float fullCount) {
    return (baseBlock, shape, block) -> {
      final LeafEntry.Builder<?> entryBuilder = entryBuilder(drop, fullCount, shape, block);
      final LootTable.Builder builder = BlockLootTableGenerator.dropsWithSilkTouch(block, new VanillaBlockLootTableGenerator().applyExplosionDecay(block, entryBuilder
      ));
      return JLootTable.delegate(builder);
    };
  }

  /**
   * 构建一个战利品表项，且当该方块形状为台阶时，掉落原先两倍数量的物品。
   *
   * @param drop      需要掉落的物品。
   * @param fullCount 完整数量
   * @param shape     方块所属的形状。参见 {@link BlockShape#getShapeOf(Block)}。
   * @param block     方块自身。
   * @return 战利品表项。
   */
  private static LeafEntry.Builder<?> entryBuilder(@NotNull ItemConvertible drop, float fullCount, @NotNull BlockShape shape, @NotNull Block block) {
    final ConstantLootNumberProvider countRange = shapeVolumeConstantProvider(shape, fullCount * 2);
    final LeafEntry.Builder<?> itemEntryBuilder = ItemEntry.builder(drop)
        // 根据该方块的形状确定数量。
        .apply(SetCountLootFunction.builder(shapeVolumeConstantProvider(shape, fullCount)));
    if (shape == BlockShape.SLAB) itemEntryBuilder
        .apply(SetCountLootFunction.builder(countRange)
            .conditionally(BlockStatePropertyLootCondition.builder(block)
                .properties(EXACT_MATCH_DOUBLE_SLAB)));
    return itemEntryBuilder;
  }

  /**
   * 对于双层台阶，需要掉落两倍，其他则一致。
   *
   * @param drop             需要掉落的方块。
   * @param conditionBuilder 掉落该方块的条件。
   * @param child            不符合条件时，需要使用的战利品表池。
   * @param isSlab           该方块是否为台阶。如果为 <code>false</code>，则与 {@link net.minecraft.data.server.loottable.BlockLootTableGenerator#drops(ItemConvertible)} 一致。
   * @return 战利品表。
   */
  private static LootTable.Builder dropsDoubleSlab(@NotNull Block drop, @NotNull LootCondition.Builder conditionBuilder, @NotNull LootPoolEntry.Builder<?> child, boolean isSlab) {
    final LootTable.Builder pool = LootTable.builder()
        .pool(LootPool.builder()
            .rolls(ConstantLootNumberProvider.create(1.0F))
            .with(ItemEntry.builder(drop)
                .conditionally(conditionBuilder)
                .alternatively(child)));
    if (isSlab) pool
        .pool(LootPool.builder()
            .conditionally(BlockStatePropertyLootCondition.builder(drop).properties(EXACT_MATCH_DOUBLE_SLAB))
            .rolls(ConstantLootNumberProvider.create(1.0F))
            .with(ItemEntry.builder(drop)
                .conditionally(conditionBuilder)
                .alternatively(child)));
    return pool;
  }

  /**
   * 类似于 {@link net.minecraft.data.server.loottable.BlockLootTableGenerator#dropsWithSilkTouch(Block, LootPoolEntry.Builder)}，但是若方块为双层台阶，则掉落两倍。
   *
   * @param drop   需要掉落的方块。
   * @param child  没有精准采集时，使用的战利品表。
   * @param isSlab 该方块是否为台阶。
   * @return 战利品表。
   */
  private static LootTable.Builder dropsDoubleSlabWithSilkTouch(@NotNull Block drop, @NotNull LootPoolEntry.Builder<?> child, boolean isSlab) {
    return dropsDoubleSlab(drop, WITH_SILK_TOUCH, child, isSlab);
  }


  /**
   * 只有当拥有精准采集附魔时，才会掉落方块，而且如果方块为双层台阶，则掉落两倍。
   *
   * @param drop   需要掉落的方块。
   * @param isSlab 该方块是否为台阶。
   * @return 战利品表。
   * @see net.minecraft.data.server.loottable.BlockLootTableGenerator#dropsWithSilkTouch(Block, LootPoolEntry.Builder)
   */
  private static LootTable.Builder dropsDoubleSlabWithSilkTouch(@NotNull Block drop, boolean isSlab) {
    final LeafEntry.Builder<?> itemEntryBuilder = ItemEntry.builder(drop);
    if (isSlab) {
      itemEntryBuilder.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2)).conditionally(BlockStatePropertyLootCondition.builder(drop).properties(EXACT_MATCH_DOUBLE_SLAB)));
    }
    return LootTable.builder().pool(LootPool.builder().conditionally(WITH_SILK_TOUCH).rolls(ConstantLootNumberProvider.create(1.0F)).with(itemEntryBuilder));
  }
}
