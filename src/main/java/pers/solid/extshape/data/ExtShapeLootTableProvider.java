package pers.solid.extshape.data;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.storage.loot.LootTable;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;

import java.util.concurrent.CompletableFuture;

public class ExtShapeLootTableProvider extends FabricBlockLootTableProvider {
  protected ExtShapeLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
    super(dataOutput, registryLookup);
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
          lootTable = unusual.apply(baseBlock, BlockShape.getShapeOf(block), block, registries, this);
        } else {
          lootTable = i.getLootTable(this);
        }
        this.add(block, lootTable);
      }
    }
    this.dropSelf(ExtShapeBlocks.PETRIFIED_OAK_PLANKS);
    this.dropSelf(ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB);
  }
}
