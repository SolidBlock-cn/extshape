package pers.solid.extshape.rrp;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.SlabType;
import net.minecraft.data.server.BlockLootTableGenerator;
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
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.state.property.Properties;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import pers.solid.brrp.v1.api.RuntimeResourcePack;
import pers.solid.brrp.v1.generator.BlockResourceGenerator;
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
  @ApiStatus.Internal
  static final ImmutableMap<Block, @NotNull LootTableFunction> INSTANCE;
  public static final LootCondition.Builder WITH_SILK_TOUCH = MatchToolLootCondition.builder(ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1))));
  public static final StatePredicate.Builder EXACT_MATCH_DOUBLE_SLAB = StatePredicate.Builder.create().exactMatch(Properties.SLAB_TYPE, SlabType.DOUBLE);
  /**
   * 只有带有精准采集附魔时才会掉落的方块。
   */
  public static final LootTableFunction DROPS_WITH_SILK_TOUCH = (baseBlock, shape, block) -> dropsDoubleSlabWithSilkTouch(block, shape == BlockShape.SLAB);

  static {
    final ImmutableMap.Builder<Block, LootTableFunction> builder = new ImmutableMap.Builder<>();
    registerUnusualLootTables(builder);
    INSTANCE = builder.build();
  }

  /**
   * 参照原版的战利品表生成器，生成对应变种的特殊战利品表。如果没有指定，则按照默认战利品表生成。特别需要注意：双台阶的战利品表掉落数量应该为两倍。
   *
   * @see BlockLootTableGenerator#accept
   */
  private static void registerUnusualLootTables(ImmutableMap.Builder<Block, LootTableFunction> builder) {
    builder.put(Blocks.CLAY, dropsShaped(Items.CLAY_BALL, 4));
    builder.put(Blocks.SNOW_BLOCK, dropsShaped(Items.SNOWBALL, 4));
    builder.put(Blocks.GLOWSTONE, (baseBlock, shape, block) -> {
      final float shapeVolume = shapeVolume(shape);
      return dropsDoubleSlabWithSilkTouch(block, BlockLootTableGenerator.applyExplosionDecay(block, ItemEntry.builder(Items.GLOWSTONE_DUST)
          .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2 * shapeVolume, 4 * shapeVolume)))
          .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE))
          .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create((int) shapeVolume, (int) (shapeVolume * 4))))), shape == BlockShape.SLAB);
    });
    builder.put(Blocks.MELON, (baseBlock, shape, block) -> {
      final float shapeVolume = shapeVolume(shape);
      return dropsDoubleSlabWithSilkTouch(block, BlockLootTableGenerator.applyExplosionDecay(block, ItemEntry.builder(Items.MELON_SLICE)
          .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(shapeVolume * 2, shapeVolume * 4)))
          .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE))
          .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create((int) shapeVolume, (int) (shapeVolume * 4))))
      ), shape == BlockShape.SLAB);
    });
    builder.put(Blocks.SEA_LANTERN, (baseBlock, shape, block) -> {
      final float shapeVolume = shapeVolume(shape);
      return dropsDoubleSlabWithSilkTouch(block, BlockLootTableGenerator.applyExplosionDecay(block, ItemEntry.builder(Items.PRISMARINE_CRYSTALS)
          .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2 * shapeVolume, 3 * shapeVolume)))
          .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE))
          .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create((int) shapeVolume, (int) (5 * shapeVolume))))
      ), shape == BlockShape.SLAB);
    });
    builder.put(Blocks.GILDED_BLACKSTONE, (baseBlock, shape, block) -> {
      final float shapeVolume = shapeVolume(shape);
      return dropsDoubleSlabWithSilkTouch(block, BlockLootTableGenerator.addSurvivesExplosionCondition(block, ItemEntry.builder(Items.GOLD_NUGGET)
          .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(shapeVolume * 2, shapeVolume * 5)))
          .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, 0.1F, 0.14285715F, 0.25F, 1.0F))
          .alternatively(ItemEntry.builder(block))
      ), shape == BlockShape.SLAB);
    });
    builder.put(Blocks.ICE, DROPS_WITH_SILK_TOUCH);
    builder.put(Blocks.BLUE_ICE, DROPS_WITH_SILK_TOUCH);
    builder.put(Blocks.PACKED_ICE, DROPS_WITH_SILK_TOUCH);
    builder.put(Blocks.SCULK, DROPS_WITH_SILK_TOUCH);
  }

  public interface LootTableFunction extends TriFunction<Block, BlockShape, Block, LootTable.Builder> {
    @Override
    LootTable.Builder apply(Block baseBlock, BlockShape shape, Block block);
  }

  /**
   * 对应形状估算的体积，用于与基础方块的掉落数相乘。
   */
  @Contract(pure = true)
  public static float shapeVolume(@NotNull BlockShape shape) {
    return shape.isConstruction ? shape.logicalCompleteness : 1;
  }

  public static ConstantLootNumberProvider shapeVolumeConstantProvider(@NotNull BlockShape shape, float count) {
    return ConstantLootNumberProvider.create(shapeVolume(shape) * count);
  }

  public static LootTableFunction dropsShaped(@NotNull ItemConvertible drop, float fullCount) {
    return (baseBlock, shape, block) -> {
      final LeafEntry.Builder<?> entryBuilder = entryBuilder(drop, fullCount, shape, block);
      return BlockLootTableGenerator.dropsWithSilkTouch(block, BlockLootTableGenerator.applyExplosionDecay(block, entryBuilder
      ));
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
   * @param isSlab           该方块是否为台阶。如果为 <code>false</code>，则与 {@link BlockLootTableGenerator#drops(ItemConvertible)} 一致。
   * @return 战利品表。
   */
  public static LootTable.Builder dropsDoubleSlab(@NotNull Block drop, @NotNull LootCondition.Builder conditionBuilder, @NotNull LootPoolEntry.Builder<?> child, boolean isSlab) {
    return addDropsDoubleSlabPool(LootTable.builder(), drop, conditionBuilder, child, isSlab);
  }

  public static LootTable.Builder addDropsDoubleSlabPool(@NotNull LootTable.Builder builder, @NotNull Block drop, LootCondition.@NotNull Builder conditionBuilder, LootPoolEntry.@NotNull Builder<?> child, boolean isSlab) {
    builder
        .pool(LootPool.builder()
            .rolls(ConstantLootNumberProvider.create(1.0F))
            .with(ItemEntry.builder(drop)
                .conditionally(conditionBuilder)
                .alternatively(child)));
    if (isSlab) builder
        .pool(LootPool.builder()
            .conditionally(BlockStatePropertyLootCondition.builder(drop).properties(EXACT_MATCH_DOUBLE_SLAB))
            .rolls(ConstantLootNumberProvider.create(1.0F))
            .with(ItemEntry.builder(drop)
                .conditionally(conditionBuilder)
                .alternatively(child)));
    return builder;
  }

  /**
   * 类似于 {@link BlockLootTableGenerator#dropsWithSilkTouch(Block, LootPoolEntry.Builder)}，但是若方块为双层台阶，则掉落两倍。
   *
   * @param drop   需要掉落的方块。
   * @param child  没有精准采集时，使用的战利品表。
   * @param isSlab 该方块是否为台阶。
   * @return 战利品表。
   */
  public static LootTable.Builder dropsDoubleSlabWithSilkTouch(@NotNull Block drop, @NotNull LootPoolEntry.Builder<?> child, boolean isSlab) {
    return dropsDoubleSlab(drop, WITH_SILK_TOUCH, child, isSlab);
  }


  /**
   * 只有当拥有精准采集附魔时，才会掉落方块，而且如果方块为双层台阶，则掉落两倍。
   *
   * @param drop   需要掉落的方块。
   * @param isSlab 该方块是否为台阶。
   * @return 战利品表。
   * @see BlockLootTableGenerator#dropsWithSilkTouch(Block, LootPoolEntry.Builder)
   */
  public static LootTable.Builder dropsDoubleSlabWithSilkTouch(@NotNull Block drop, boolean isSlab) {
    final LeafEntry.Builder<?> itemEntryBuilder = ItemEntry.builder(drop);
    if (isSlab) {
      itemEntryBuilder.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2)).conditionally(BlockStatePropertyLootCondition.builder(drop).properties(EXACT_MATCH_DOUBLE_SLAB)));
    }
    return LootTable.builder().pool(LootPool.builder().conditionally(WITH_SILK_TOUCH).rolls(ConstantLootNumberProvider.create(1.0F)).with(itemEntryBuilder));
  }
}
