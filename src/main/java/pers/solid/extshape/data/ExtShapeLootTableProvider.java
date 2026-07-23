package pers.solid.extshape.data;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.EmptyTagLookupWrapper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootPredicates;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;

import java.util.concurrent.CompletableFuture;

public class ExtShapeLootTableProvider extends FabricBlockLootSubProvider {
  protected final CompletableFuture<HolderLookup.Provider> registriesFuture;

  protected ExtShapeLootTableProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(dataOutput, registryLookup);
    this.registriesFuture = registryLookup;
  }

  @Override
  public void generate() {
    final ImmutableMap<Block, UnusualLootTables.LootTableFunction> instance = new UnusualLootTables().createInstance();
    for (Block block : ExtShapeBlocks.getBlocks()) {
      if (block.getLootTable().isEmpty()) {
        continue;
      }
      if (block instanceof ExtShapeBlockInterface i) {
        final LootTable.Builder lootTable;
        final Block baseBlock = i.getBaseBlock();
        final UnusualLootTables.LootTableFunction unusual = instance.get(baseBlock);
        if (unusual != null && !(block instanceof ButtonBlock) && !(block instanceof PressurePlateBlock)) {
          lootTable = unusual.apply(baseBlock, BlockShape.getShapeOf(block), block, blocks, enchantments, this);
        } else {
          lootTable = i.getLootTable(this);
        }
        this.add(block, lootTable);
      }
    }
    this.dropSelf(ExtShapeBlocks.PETRIFIED_OAK_PLANKS);
    this.dropSelf(ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB);
  }

  /**
   * 避免在运行时出现以下错误：
   * <pre>java.lang.IllegalStateException: Missing element ResourceKey[minecraft:predicate / minecraft:tool/can_silk_touch]</pre>
   */
  @Override
  public Holder<LootItemCondition> hasSilkTouch() {
    // 此方法是为了避免在数据生成时出现异常；todo 检查 Fabric API 更新后，此版本是否仍存在此问题。说明：当前版本中，predicates（存储战利品表谓词注册表）是空的，因为 Fabric 的数据生成在运行时，可重载注册表会使用空的，此问题暂时未找到解决办法。
//    return super.hasSilkTouch();
    return predicates.get(LootPredicates.TOOL_CAN_SILK_TOUCH).map(r -> (Holder<LootItemCondition>) r).orElseGet(() -> Holder.Reference.createStandAlone(predicates instanceof EmptyTagLookupWrapper<LootItemCondition>(HolderLookup.RegistryLookup<LootItemCondition> parent) ? parent : predicates, LootPredicates.TOOL_CAN_SILK_TOUCH));
  }
}
