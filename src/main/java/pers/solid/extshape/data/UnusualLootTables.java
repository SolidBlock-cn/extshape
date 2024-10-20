package pers.solid.extshape.data;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.SlabType;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.TableBonusLootCondition;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LimitCountLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.operator.BoundedIntUnaryOperator;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.state.property.Properties;
import org.jetbrains.annotations.*;
import pers.solid.extshape.builder.BlockShape;

/**
 * <p>本类记录了用于本模组的不掉落方块本身的基础方块的战利品表。
 * <p>注册在 {@link #createInstance()} 中的方块，在生成战利品表时，直接使用这里面注册了的战利品表函数。
 *
 * @author SolidBlock
 * @since 1.5.1
 */
@ApiStatus.AvailableSince("1.5.1")
public class UnusualLootTables {
  public static final StatePredicate.Builder EXACT_MATCH_DOUBLE_SLAB = StatePredicate.Builder.create().exactMatch(Properties.SLAB_TYPE, SlabType.DOUBLE);
  protected final LootTableFunction dropsDoubleWithSilkTouchOrNone = (baseBlock, shape, block, lookup, generator) -> dropsDoubleSlabWithSilkTouchOrNone(block, shape == BlockShape.SLAB, generator);

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

  /**
   * 构建一个战利品表项，并指定固定掉落数量，且当该方块形状为台阶时，掉落原先两倍数量的物品。
   *
   * @param drop      需要掉落的物品。
   * @param fullCount 掉落的物品对应完整方块大小时的数量。
   * @param shape     方块所属的形状。参见 {@link BlockShape#getShapeOf(Block)}。
   * @param block     方块自身。
   * @return 战利品表项。
   */
  private static LeafEntry.Builder<?> entryBuilderConstCount(@NotNull ItemConvertible drop, float fullCount, @NotNull BlockShape shape, @NotNull Block block) {
    final LeafEntry.Builder<?> itemEntryBuilder = ItemEntry.builder(drop)
        // 根据该方块的形状确定数量。
        .apply(SetCountLootFunction.builder(shapeVolumeConstantProvider(shape, fullCount)));
    if (shape == BlockShape.SLAB) {
      itemEntryBuilder
          .apply(SetCountLootFunction.builder(shapeVolumeConstantProvider(shape, fullCount * 2))
              .conditionally(BlockStatePropertyLootCondition.builder(block)
                  .properties(EXACT_MATCH_DOUBLE_SLAB)));
    }
    return itemEntryBuilder;
  }

  /**
   * 对于双层台阶，需要掉落两倍，其他则一致。
   *
   * @param drop                符合 {@code conditionsBuilder} 的条件时，需要掉落的方块。
   * @param conditionBuilder    掉落该方块的条件。
   * @param child               不符合条件，且不为双层台阶时，需要使用的战利品表池。
   * @param childWhenDoubleSlab 不符合条件，且为双层台阶时，需要使用的战利品表池。当方块本身就不是台阶时，此参数应为 {@code null}。
   * @return 战利品表。
   */
  public static LootTable.Builder dropsDoubleSlab(@NotNull Block drop, @NotNull LootCondition.Builder conditionBuilder, @NotNull LootPoolEntry.Builder<?> child, @Nullable LootPoolEntry.Builder<?> childWhenDoubleSlab) {
    return addDropsDoubleSlabPool(LootTable.builder(), drop, conditionBuilder, child, childWhenDoubleSlab);
  }

  public static LootTable.Builder addDropsDoubleSlabPool(@NotNull LootTable.Builder builder, @NotNull Block drop, LootCondition.@NotNull Builder conditionBuilder, LootPoolEntry.@NotNull Builder<?> child, @Nullable LootPoolEntry.Builder<?> childWhenDoubleSlab) {
    if (childWhenDoubleSlab == null) {
      builder
          .pool(LootPool.builder()
              .rolls(ConstantLootNumberProvider.create(1.0F))
              .with(AlternativeEntry.builder(
                  ItemEntry.builder(drop)
                      .conditionally(conditionBuilder),
                  child)));
    } else {
      builder
          .pool(LootPool.builder()
              .rolls(ConstantLootNumberProvider.create(1.0F))
              .with(AlternativeEntry.builder(
                  ItemEntry.builder(drop)
                      .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2))
                          .conditionally(BlockStatePropertyLootCondition.builder(drop).properties(EXACT_MATCH_DOUBLE_SLAB)))
                      .conditionally(conditionBuilder),
                  childWhenDoubleSlab
                      .conditionally(BlockStatePropertyLootCondition.builder(drop).properties(EXACT_MATCH_DOUBLE_SLAB)),
                  child)));
    }
    return builder;
  }

  protected RegistryEntry<Enchantment> fortune(RegistryWrapper.WrapperLookup registryLookup) {
    return registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE);
  }

  protected ConditionalLootFunction.Builder<?> fortuneFunction(RegistryWrapper.WrapperLookup registryLookup) {
    return ApplyBonusLootFunction.uniformBonusCount(fortune(registryLookup));
  }

  @Unmodifiable
  public ImmutableMap<Block, @NotNull LootTableFunction> createInstance() {
    final ImmutableMap.Builder<Block, LootTableFunction> builder = new ImmutableMap.Builder<>();
    registerUnusualLootTables(builder);
    return builder.build();
  }

  /**
   * 参照原版的战利品表生成器，生成对应变种的特殊战利品表。如果没有指定，则按照默认战利品表生成。特别需要注意：双台阶的战利品表掉落数量应该为两倍。
   *
   * @see net.minecraft.data.server.loottable.BlockLootTableGenerator#accept
   */
  private void registerUnusualLootTables(ImmutableMap.Builder<Block, LootTableFunction> builder) {
    builder.put(Blocks.CLAY, dropsWithSilkTouchOrConst(Items.CLAY_BALL, 4));
    builder.put(Blocks.SNOW_BLOCK, dropsWithSilkTouchOrConst(Items.SNOWBALL, 4));
    builder.put(Blocks.GLOWSTONE, (baseBlock, shape, block, lookup, generator) -> {
      final float shapeVolume = shapeVolume(shape);
      return dropsDoubleSlabWithSilkTouch(block, generator.applyExplosionDecay(block, ItemEntry.builder(Items.GLOWSTONE_DUST)
              .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2 * shapeVolume, 4 * shapeVolume)))
              .apply(fortuneFunction(lookup))
              .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create((int) shapeVolume, (int) (shapeVolume * 4))))),
          shape == BlockShape.SLAB ? generator.applyExplosionDecay(block, ItemEntry.builder(Items.GLOWSTONE_DUST)
              .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 4)))
              .apply(fortuneFunction(lookup))
              .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create(1, 4)))) : null, generator);
    });
    builder.put(Blocks.MELON, (baseBlock, shape, block, lookup, generator) -> {
      final float shapeVolume = shapeVolume(shape);
      return dropsDoubleSlabWithSilkTouch(block, generator.applyExplosionDecay(block, ItemEntry.builder(Items.MELON_SLICE)
              .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(shapeVolume * 2, shapeVolume * 4)))
              .apply(fortuneFunction(lookup))
              .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create((int) shapeVolume, (int) (shapeVolume * 4))))),
          shape == BlockShape.SLAB ? generator.applyExplosionDecay(block, ItemEntry.builder(Items.MELON_SLICE)
              .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 4)))
              .apply(fortuneFunction(lookup))
              .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create(1, 4)))) : null, generator);
    });
    builder.put(Blocks.SEA_LANTERN, (baseBlock, shape, block, lookup, generator) -> {
      final float shapeVolume = shapeVolume(shape);
      return dropsDoubleSlabWithSilkTouch(block, generator.applyExplosionDecay(block, ItemEntry.builder(Items.PRISMARINE_CRYSTALS)
          .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2 * shapeVolume, 3 * shapeVolume)))
          .apply(fortuneFunction(lookup))
          .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create((int) shapeVolume, (int) (5 * shapeVolume))))
      ), shape == BlockShape.SLAB ? generator.applyExplosionDecay(block, ItemEntry.builder(Items.PRISMARINE_CRYSTALS)
          .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 3)))
          .apply(fortuneFunction(lookup))
          .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create(1, 5)))
      ) : null, generator);
    });
    builder.put(Blocks.GILDED_BLACKSTONE, (baseBlock, shape, block, lookup, generator) -> {
      final float shapeVolume = shapeVolume(shape);
      return dropsDoubleSlabWithSilkTouch(block, generator.addSurvivesExplosionCondition(block, ItemEntry.builder(Items.GOLD_NUGGET)
          .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(shapeVolume * 2, shapeVolume * 5)))
          .conditionally(TableBonusLootCondition.builder(fortune(lookup), 0.1F, 0.14285715F, 0.25F, 1.0F))
          .alternatively(ItemEntry.builder(block))), shape == BlockShape.SLAB ? generator.addSurvivesExplosionCondition(block, ItemEntry.builder(Items.GOLD_NUGGET)
          .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2, 5)))
          .conditionally(TableBonusLootCondition.builder(fortune(lookup), 0.1F, 0.14285715F, 0.25F, 1.0F))
          .alternatively(ItemEntry.builder(block).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2))))) : null, generator);
    });
    builder.put(Blocks.ICE, dropsDoubleWithSilkTouchOrNone);
    builder.put(Blocks.BLUE_ICE, dropsDoubleWithSilkTouchOrNone);
    builder.put(Blocks.PACKED_ICE, dropsDoubleWithSilkTouchOrNone);
    builder.put(Blocks.SCULK, dropsDoubleWithSilkTouchOrNone);
  }

  /**
   * 当工具没有精准采集时，掉落固定数量的物品，其中物品数量由 {@code fullCount * volume} 决定。当工具有精准采集时，掉落方块本身，其中，当方块为双台阶时，掉落两个台阶。
   *
   * @param drop      没有精准采集时，掉落的物品。
   * @param fullCount 没有精准采集时，掉落的物品对应完整方块大小时的数量。
   */
  public LootTableFunction dropsWithSilkTouchOrConst(@NotNull ItemConvertible drop, float fullCount) {
    return (baseBlock, shape, block, lookup, generator) -> {
      final LeafEntry.Builder<?> entryBuilder = entryBuilderConstCount(drop, fullCount, shape, block);
      if (shape == BlockShape.SLAB) {
        return LootTable.builder()
            .pool(LootPool.builder()
                .with(ItemEntry.builder(block)
                    .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2))
                        .conditionally(BlockStatePropertyLootCondition.builder(block).properties(EXACT_MATCH_DOUBLE_SLAB)))
                    .conditionally(generator.createSilkTouchCondition())
                    .alternatively(entryBuilder)));
      } else {
        return generator.dropsWithSilkTouch(block, generator.applyExplosionDecay(block, entryBuilder));
      }
    };
  }

  /**
   * 类似于 {@link net.minecraft.data.server.loottable.BlockLootTableGenerator#dropsWithSilkTouch(Block, LootPoolEntry.Builder)}，但是若方块为双层台阶，则掉落两倍。
   *
   * @param drop                使用精准采集时掉落的方块。
   * @param child               没有精准采集，且不为双层台阶时，需要使用的战利品表池。
   * @param childWhenDoubleSlab 没有精准采集，且为双层台阶时，需要使用的战利品表池。当方块本身就不是台阶时，此参数应为 {@code null}。
   * @return 战利品表。
   */
  public LootTable.Builder dropsDoubleSlabWithSilkTouch(@NotNull Block drop, @NotNull LootPoolEntry.Builder<?> child, @Nullable LootPoolEntry.Builder<?> childWhenDoubleSlab, BlockLootTableGenerator generator) {
    return dropsDoubleSlab(drop, generator.createSilkTouchCondition(), child, childWhenDoubleSlab);
  }

  /**
   * 只有当拥有精准采集附魔时，才会掉落方块，而且如果方块为双层台阶，则掉落两倍。
   *
   * @param drop   需要掉落的方块。
   * @param isSlab 该方块是否为台阶。
   * @return 战利品表。
   * @see net.minecraft.data.server.loottable.BlockLootTableGenerator#dropsWithSilkTouch(Block, LootPoolEntry.Builder)
   */
  public LootTable.Builder dropsDoubleSlabWithSilkTouchOrNone(@NotNull Block drop, boolean isSlab, BlockLootTableGenerator generator) {
    final LeafEntry.Builder<?> itemEntryBuilder = ItemEntry.builder(drop);
    if (isSlab) {
      itemEntryBuilder.apply(
          SetCountLootFunction.builder(ConstantLootNumberProvider.create(2))
              .conditionally(BlockStatePropertyLootCondition.builder(drop)
                  .properties(EXACT_MATCH_DOUBLE_SLAB))
      );
    }
    return LootTable.builder()
        .pool(LootPool.builder()
            .conditionally(generator.createSilkTouchCondition())
            .rolls(ConstantLootNumberProvider.create(1.0F))
            .with(itemEntryBuilder));
  }


  @FunctionalInterface
  public interface LootTableFunction {
    LootTable.Builder apply(Block baseBlock, BlockShape shape, Block block, RegistryWrapper.WrapperLookup lookup, BlockLootTableGenerator generator);
  }
}
