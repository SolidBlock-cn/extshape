package pers.solid.extshape.data;

import com.google.common.collect.ImmutableMap;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.IntLimit;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.UniformContainerBase;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LimitCount;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchBlock;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
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
  public static final StatePropertiesPredicate.Builder EXACT_MATCH_DOUBLE_SLAB = StatePropertiesPredicate.Builder.properties().hasProperty(BlockStateProperties.SLAB_TYPE, SlabType.DOUBLE);
  protected final LootTableFunction dropsDoubleWithSilkTouchOrNone = (baseBlock, shape, block, blocks, enchantments, generator) -> dropsDoubleSlabWithSilkTouchOrNone(blocks, block, shape == BlockShape.SLAB, generator);

  /**
   * 对应形状估算的体积，用于与基础方块的掉落数相乘。
   */
  @Contract(pure = true)
  public static float shapeVolume(BlockShape shape) {
    return shape.isConstruction ? shape.logicalCompleteness : 1;
  }

  public static Holder<ContextIntProvider> shapeVolumeConstantProvider(BlockShape shape, float count) {
    return Holder.direct(new ConstantValue((int) (shapeVolume(shape) * count)));
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
  private static UniformContainerBase.Builder<?> entryBuilderConstCount(HolderGetter<Block> blocks, ItemLike drop, float fullCount, BlockShape shape, Block block) {
    final UniformContainerBase.Builder<?> itemEntryBuilder = LootItem.lootTableItem(drop)
        // 根据该方块的形状确定数量。
        .apply(SetItemCountFunction.setCount(shapeVolumeConstantProvider(shape, fullCount)));
    if (shape == BlockShape.SLAB) {
      itemEntryBuilder
          .apply(SetItemCountFunction.setCount(shapeVolumeConstantProvider(shape, fullCount * 2))
              .when(MatchBlock.blockMatches(blocks, block, StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))));
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
  public static LootTable.Builder dropsDoubleSlab(HolderGetter<Block> blocks, Block drop, Holder<LootItemCondition> conditionBuilder, LootPoolEntryContainer.Builder<?> child, @Nullable LootPoolEntryContainer.Builder<?> childWhenDoubleSlab) {
    return addDropsDoubleSlabPool(blocks, LootTable.lootTable(), drop, conditionBuilder, child, childWhenDoubleSlab);
  }

  public static LootTable.Builder addDropsDoubleSlabPool(HolderGetter<Block> blocks, LootTable.Builder builder, Block drop, Holder<LootItemCondition> conditionBuilder, LootPoolEntryContainer.Builder<?> child, @Nullable LootPoolEntryContainer.Builder<?> childWhenDoubleSlab) {
    if (childWhenDoubleSlab == null) {
      builder
          .withPool(LootPool.lootPool()
              .setRolls(ContextIntProviders.exactly(1))
              .add(AlternativesEntry.alternatives(
                  LootItem.lootTableItem(drop)
                      .when(conditionBuilder),
                  child)));
    } else {
      builder
          .withPool(LootPool.lootPool()
              .setRolls(ContextIntProviders.exactly(1))
              .add(AlternativesEntry.alternatives(
                  LootItem.lootTableItem(drop)
                      .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(2))
                          .when(MatchBlock.blockMatches(blocks, drop, StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))))
                      .when(conditionBuilder),
                  childWhenDoubleSlab
                      .when(MatchBlock.blockMatches(blocks, drop, StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))),
                  child)));
    }
    return builder;
  }

  protected Holder<Enchantment> fortune(HolderGetter<Enchantment> enchantments) {
    return enchantments.getOrThrow(Enchantments.FORTUNE);
  }

  protected LootItemConditionalFunction.Builder<?> fortuneFunction(HolderGetter<Enchantment> enchantments) {
    return ApplyBonusCount.addUniformBonusCount(enchantments.getOrThrow(Enchantments.FORTUNE));
  }

  @Unmodifiable
  public ImmutableMap<Block, LootTableFunction> createInstance() {
    final ImmutableMap.Builder<Block, LootTableFunction> builder = new ImmutableMap.Builder<>();
    registerUnusualLootTables(builder);
    return builder.build();
  }

  /**
   * 参照原版的战利品表生成器，生成对应变种的特殊战利品表。如果没有指定，则按照默认战利品表生成。特别需要注意：双台阶的战利品表掉落数量应该为两倍。
   *
   * @see BlockLootSubProvider#generate
   */
  private void registerUnusualLootTables(ImmutableMap.Builder<Block, LootTableFunction> builder) {
    builder.put(Blocks.CLAY, dropsWithSilkTouchOrConst(Items.CLAY_BALL, 4));
    builder.put(Blocks.SNOW_BLOCK, dropsWithSilkTouchOrConst(Items.SNOWBALL, 4));
    builder.put(Blocks.GLOWSTONE, (baseBlock, shape, block, blocks, enchantments, generator) -> {
      final float shapeVolume = shapeVolume(shape);
      return dropsDoubleSlabWithSilkTouch(blocks, block, generator.applyExplosionDecay(block, LootItem.lootTableItem(Items.GLOWSTONE_DUST)
              .apply(SetItemCountFunction.setCount(ContextIntProviders.fromFloat(ContextFloatProviders.between(2 * shapeVolume, 4 * shapeVolume))))
              .apply(fortuneFunction(enchantments))
              .apply(LimitCount.limitCount(IntLimit.range((int) shapeVolume, (int) (shapeVolume * 4))))),
          shape == BlockShape.SLAB ? generator.applyExplosionDecay(block, LootItem.lootTableItem(Items.GLOWSTONE_DUST)
              .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4)))
              .apply(fortuneFunction(enchantments))
              .apply(LimitCount.limitCount(IntLimit.range(1, 4)))) : null, generator);
    });
    builder.put(Blocks.MELON, (baseBlock, shape, block, blocks, enchantments, generator) -> {
      final float shapeVolume = shapeVolume(shape);
      return dropsDoubleSlabWithSilkTouch(blocks, block, generator.applyExplosionDecay(block, LootItem.lootTableItem(Items.MELON_SLICE)
              .apply(SetItemCountFunction.setCount(ContextIntProviders.fromFloat(ContextFloatProviders.between(shapeVolume * 2, shapeVolume * 4))))
              .apply(fortuneFunction(enchantments))
              .apply(LimitCount.limitCount(IntLimit.range((int) shapeVolume, (int) (shapeVolume * 4))))),
          shape == BlockShape.SLAB ? generator.applyExplosionDecay(block, LootItem.lootTableItem(Items.MELON_SLICE)
              .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 4)))
              .apply(fortuneFunction(enchantments))
              .apply(LimitCount.limitCount(IntLimit.range(1, 4)))) : null, generator);
    });
    builder.put(Blocks.SEA_LANTERN, (baseBlock, shape, block, blocks, enchantments, generator) -> {
      final float shapeVolume = shapeVolume(shape);
      return dropsDoubleSlabWithSilkTouch(blocks, block, generator.applyExplosionDecay(block, LootItem.lootTableItem(Items.PRISMARINE_CRYSTALS)
          .apply(SetItemCountFunction.setCount(ContextIntProviders.fromFloat(ContextFloatProviders.between(2 * shapeVolume, 3 * shapeVolume))))
          .apply(fortuneFunction(enchantments))
          .apply(LimitCount.limitCount(IntLimit.range((int) shapeVolume, (int) (5 * shapeVolume))))
      ), shape == BlockShape.SLAB ? generator.applyExplosionDecay(block, LootItem.lootTableItem(Items.PRISMARINE_CRYSTALS)
          .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 3)))
          .apply(fortuneFunction(enchantments))
          .apply(LimitCount.limitCount(IntLimit.range(1, 5)))
      ) : null, generator);
    });
    builder.put(Blocks.GILDED_BLACKSTONE, (baseBlock, shape, block, blocks, enchantments, generator) -> {
      final float shapeVolume = shapeVolume(shape);
      return dropsDoubleSlabWithSilkTouch(blocks, block, generator.applyExplosionCondition(block, LootItem.lootTableItem(Items.GOLD_NUGGET)
          .apply(SetItemCountFunction.setCount(ContextIntProviders.fromFloat(ContextFloatProviders.between(shapeVolume * 2, shapeVolume * 5))))
          .when(BonusLevelTableCondition.bonusLevelFlatChance(fortune(enchantments), 0.1F, 0.14285715F, 0.25F, 1.0F))
          .otherwise(LootItem.lootTableItem(block))), shape == BlockShape.SLAB ? generator.applyExplosionCondition(block, LootItem.lootTableItem(Items.GOLD_NUGGET)
          .apply(SetItemCountFunction.setCount(ContextIntProviders.between(2, 5)))
          .when(BonusLevelTableCondition.bonusLevelFlatChance(fortune(enchantments), 0.1F, 0.14285715F, 0.25F, 1.0F))
          .otherwise(LootItem.lootTableItem(block).apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(2))))) : null, generator);
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
  public LootTableFunction dropsWithSilkTouchOrConst(ItemLike drop, float fullCount) {
    return (baseBlock, shape, block, blocks, enchantments, generator) -> {
      final UniformContainerBase.Builder<?> entryBuilder = entryBuilderConstCount(blocks, drop, fullCount, shape, block);
      if (shape == BlockShape.SLAB) {
        return LootTable.lootTable()
            .withPool(LootPool.lootPool()
                .add(LootItem.lootTableItem(block)
                    .apply(SetItemCountFunction.setCount(ContextIntProviders.exactly(2))
                        .when(MatchBlock.blockMatches(blocks, block, StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE))))
                    .when(generator.hasSilkTouch())
                    .otherwise(entryBuilder)));
      } else {
        return generator.createSilkTouchDispatchTable(block, generator.applyExplosionDecay(block, entryBuilder));
      }
    };
  }

  /**
   * 类似于 {@link BlockLootSubProvider#createSilkTouchDispatchTable(Block, LootPoolEntryContainer.Builder)}，但是若方块为双层台阶，则掉落两倍。
   *
   * @param drop                使用精准采集时掉落的方块。
   * @param child               没有精准采集，且不为双层台阶时，需要使用的战利品表池。
   * @param childWhenDoubleSlab 没有精准采集，且为双层台阶时，需要使用的战利品表池。当方块本身就不是台阶时，此参数应为 {@code null}。
   * @return 战利品表。
   */
  public LootTable.Builder dropsDoubleSlabWithSilkTouch(HolderGetter<Block> blocks, Block drop, LootPoolEntryContainer.Builder<?> child, @Nullable LootPoolEntryContainer.Builder<?> childWhenDoubleSlab, BlockLootSubProvider generator) {
    return dropsDoubleSlab(blocks, drop, generator.hasSilkTouch(), child, childWhenDoubleSlab);
  }

  /**
   * 只有当拥有精准采集附魔时，才会掉落方块，而且如果方块为双层台阶，则掉落两倍。
   *
   * @param drop   需要掉落的方块。
   * @param isSlab 该方块是否为台阶。
   * @return 战利品表。
   * @see BlockLootSubProvider#createSilkTouchDispatchTable(Block, LootPoolEntryContainer.Builder)
   */
  public LootTable.Builder dropsDoubleSlabWithSilkTouchOrNone(HolderGetter<Block> blocks, Block drop, boolean isSlab, BlockLootSubProvider generator) {
    final UniformContainerBase.Builder<?> itemEntryBuilder = LootItem.lootTableItem(drop);
    if (isSlab) {
      itemEntryBuilder.apply(
          SetItemCountFunction.setCount(ContextIntProviders.exactly(2))
              .when(MatchBlock.blockMatches(blocks, drop, StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)))
      );
    }
    return LootTable.lootTable()
        .withPool(LootPool.lootPool()
            .when(generator.hasSilkTouch())
            .setRolls(ContextIntProviders.exactly(1))
            .add(itemEntryBuilder));
  }


  @FunctionalInterface
  public interface LootTableFunction {
    LootTable.Builder apply(Block baseBlock, BlockShape shape, Block block, HolderGetter<Block> blocks, HolderGetter<Enchantment> enchantments, BlockLootSubProvider generator);
  }
}
