package pers.solid.extshape.mappings;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.generator.BlockResourceGenerator;
import net.devtech.arrp.json.loot.JLootTable;
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
import net.minecraft.state.property.Properties;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;
import pers.solid.extshape.builder.Shape;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 本类记录了用于本模组的不掉落方块本身的基础方块的战利品表。
 * <p>
 * 注册在 {@link #LOOT_TABLE_PROVIDERS} 中的方块，在通过 {@link pers.solid.extshape.ExtShapeRRP#generateServerData(RuntimeResourcePack)} 生成战利品表时，不会使用 {@link BlockResourceGenerator#getLootTable()}，而是直接使用这里面注册了的战利品表函数。
 *
 * @author SolidBlock
 * @since 1.5.1
 */
@ApiStatus.AvailableSince("1.5.1")
public final class UnusualLootTables {
  private static final HashMap<Block, LootTableFunction> LOOT_TABLE_PROVIDERS = new HashMap<>();
  @UnmodifiableView
  public static final Map<Block, LootTableFunction> INSTANCE = Collections.unmodifiableMap(LOOT_TABLE_PROVIDERS);
  private static final LootCondition.Builder WITH_SILK_TOUCH = MatchToolLootCondition.builder(net.minecraft.predicate.item.ItemPredicate.Builder.create().enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1))));
  private static final StatePredicate.Builder EXACT_MATCH_DOUBLE_SLAB = StatePredicate.Builder.create().exactMatch(Properties.SLAB_TYPE, SlabType.DOUBLE);

  static {
    register();
  }

  /**
   * 参照原版的战利品表生成器，生成对应变种的特殊战利品表。如果没有指定，则按照默认战利品表生成。特别需要注意：双台阶的战利品表掉落数量应该为两倍。
   *
   * @see BlockLootTableGenerator#accept
   */
  private static void register() {
    LOOT_TABLE_PROVIDERS.put(Blocks.CLAY, dropsShaped(Items.CLAY_BALL, 4));
    LOOT_TABLE_PROVIDERS.put(Blocks.SNOW_BLOCK, dropsShaped(Items.SNOWBALL, 4));
    LOOT_TABLE_PROVIDERS.put(Blocks.GLOWSTONE, (baseBlock, shape, block) -> {
      final float shapeVolume = shapeVolume(shape);
      final LootTable.Builder builder = dropsDoubleSlabWithSilkTouch(block, BlockLootTableGenerator.applyExplosionDecay(block, ItemEntry.builder(Items.GLOWSTONE_DUST)
          .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2 * shapeVolume, 4 * shapeVolume)))
          .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE))
          .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create((int) shapeVolume, (int) (shapeVolume * 4))))), shape == Shape.SLAB);
      return JLootTable.delegate(builder.build());
    });
    LOOT_TABLE_PROVIDERS.put(Blocks.MELON, (baseBlock, shape, block) -> {
      final float shapeVolume = shapeVolume(shape);
      return JLootTable.delegate(dropsDoubleSlabWithSilkTouch(block, BlockLootTableGenerator.applyExplosionDecay(block, ItemEntry.builder(Items.MELON_SLICE)
          .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(shapeVolume * 2, shapeVolume * 4)))
          .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE))
          .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create((int) shapeVolume, (int) (shapeVolume * 4))))
      ), shape == Shape.SLAB).build());
    });
    LOOT_TABLE_PROVIDERS.put(Blocks.SEA_LANTERN, (baseBlock, shape, block) -> {
      final float shapeVolume = shapeVolume(shape);
      return JLootTable.delegate(dropsDoubleSlabWithSilkTouch(block, BlockLootTableGenerator.applyExplosionDecay(block, ItemEntry.builder(Items.PRISMARINE_CRYSTALS)
          .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2 * shapeVolume, 3 * shapeVolume)))
          .apply(ApplyBonusLootFunction.uniformBonusCount(Enchantments.FORTUNE))
          .apply(LimitCountLootFunction.builder(BoundedIntUnaryOperator.create((int) shapeVolume, (int) (5 * shapeVolume))))
      ), shape == Shape.SLAB).build());
    });
    LOOT_TABLE_PROVIDERS.put(Blocks.GILDED_BLACKSTONE, (baseBlock, shape, block) -> {
      final float shapeVolume = shapeVolume(shape);
      return JLootTable.delegate(dropsDoubleSlabWithSilkTouch(block, BlockLootTableGenerator.addSurvivesExplosionCondition(block, ItemEntry.builder(Items.GOLD_NUGGET)
          .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(shapeVolume * 2, shapeVolume * 5)))
          .conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, 0.1F, 0.14285715F, 0.25F, 1.0F))
          .alternatively(ItemEntry.builder(block))
      ), shape == Shape.SLAB).build());
    });

    // 只有带有精准采集附魔时才会掉落的方块。
    final LootTableFunction dropsWithSilkTouch = (baseBlock, shape, block) -> JLootTable.delegate(dropsDoubleSlabWithSilkTouch(block, shape == Shape.SLAB).build());
    LOOT_TABLE_PROVIDERS.put(Blocks.ICE, dropsWithSilkTouch);
    LOOT_TABLE_PROVIDERS.put(Blocks.BLUE_ICE, dropsWithSilkTouch);
    LOOT_TABLE_PROVIDERS.put(Blocks.PACKED_ICE, dropsWithSilkTouch);
  }

  public interface LootTableFunction extends TriFunction<Block, Shape, Block, JLootTable> {
    @Override
    JLootTable apply(Block baseBlock, Shape shape, Block block);
  }

  /**
   * 对应形状估算的体积，用于与基础方块的掉落数相乘。
   */
  @Contract(pure = true)
  private static float shapeVolume(@NotNull Shape shape) {
    return switch (shape) {
      case SLAB, VERTICAL_SLAB -> 0.5f;
      case QUARTER_PIECE, VERTICAL_QUARTER_PIECE -> 0.25f;
      default -> 1;
    };
  }

  private static ConstantLootNumberProvider shapeVolumeConstantProvider(Shape shape, float count) {
    return ConstantLootNumberProvider.create(shapeVolume(shape) * count);
  }

  private static LootTableFunction dropsShaped(ItemConvertible drop, float fullCount) {
    return (baseBlock, shape, block) -> JLootTable.delegate(dropsDoubleSlabWithSilkTouch(block, BlockLootTableGenerator.applyExplosionDecay(block, ItemEntry.builder(drop)
        .apply(SetCountLootFunction.builder(shapeVolumeConstantProvider(shape, fullCount)))
    ), shape == Shape.SLAB).build());
  }

  /**
   * 对于双层台阶，需要掉落两倍，其他则一致。
   *
   * @param drop             需要掉落的方块。
   * @param conditionBuilder 掉落该方块的条件。
   * @param child            不符合条件时，需要使用的战利品表池。
   * @param isSlab           该方块是否为台阶。如果为 <code>false</code>，则与 {@link BlockLootTableGenerator#drops(Block, LootCondition.Builder, LootPoolEntry.Builder)} 一致。
   * @return 战利品表。
   */
  private static LootTable.Builder dropsDoubleSlab(Block drop, LootCondition.Builder conditionBuilder, LootPoolEntry.Builder<?> child, boolean isSlab) {
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
   * 类似于 {@link BlockLootTableGenerator#dropsWithSilkTouch(Block, LootPoolEntry.Builder)}，但是若方块为双层台阶，则掉落两倍。
   *
   * @param drop   需要掉落的方块。
   * @param child  没有精准采集时，使用的战利品表。
   * @param isSlab 该方块是否为台阶。
   * @return 战利品表。
   */
  private static LootTable.Builder dropsDoubleSlabWithSilkTouch(Block drop, LootPoolEntry.Builder<?> child, boolean isSlab) {
    return dropsDoubleSlab(drop, WITH_SILK_TOUCH, child, isSlab);
  }


  /**
   * 只有当拥有精准采集附魔时，才会掉落方块，而且如果方块为双层台阶，则掉落两倍。
   *
   * @param drop   需要掉落的方块。
   * @param isSlab 该方块是否为台阶。
   * @return 战利品表。
   * @see BlockLootTableGenerator#dropsWithSilkTouch(ItemConvertible)
   */
  private static LootTable.Builder dropsDoubleSlabWithSilkTouch(Block drop, boolean isSlab) {
    final LeafEntry.Builder<?> itemEntryBuilder = ItemEntry.builder(drop);
    if (isSlab) {
      itemEntryBuilder.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2)).conditionally(BlockStatePropertyLootCondition.builder(drop).properties(EXACT_MATCH_DOUBLE_SLAB)));
    }
    return LootTable.builder().pool(LootPool.builder().conditionally(WITH_SILK_TOUCH).rolls(ConstantLootNumberProvider.create(1.0F)).with(itemEntryBuilder));
  }
}
