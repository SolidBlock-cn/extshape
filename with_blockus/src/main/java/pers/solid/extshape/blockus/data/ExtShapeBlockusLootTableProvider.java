package pers.solid.extshape.blockus.data;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryWrapper;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.blockus.BlockusUnusualLootTables;
import pers.solid.extshape.blockus.ExtShapeBlockusBlocks;
import pers.solid.extshape.builder.BlockShape;
import pers.solid.extshape.rrp.UnusualLootTables;

import java.util.concurrent.CompletableFuture;

public class ExtShapeBlockusLootTableProvider extends FabricBlockLootTableProvider {
  protected ExtShapeBlockusLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
    super(dataOutput, registryLookup);
  }

  @Override
  public void generate() {
    for (Block block : ExtShapeBlockusBlocks.BLOCKUS_BLOCKS) {
      final ImmutableMap<Block, UnusualLootTables.LootTableFunction> instance = new BlockusUnusualLootTables().createInstance();
      if (block instanceof ExtShapeBlockInterface i) {
        final LootTable.Builder lootTable;
        final Block baseBlock = i.getBaseBlock();
        final UnusualLootTables.LootTableFunction unusual = instance.get(baseBlock);
        if (unusual != null) {
          lootTable = unusual.apply(baseBlock, BlockShape.getShapeOf(block), block, registryLookup, this);
        } else {
          lootTable = i.getLootTable(this);
        }
        this.addDrop(block, lootTable);
      }
    }
  }
}
