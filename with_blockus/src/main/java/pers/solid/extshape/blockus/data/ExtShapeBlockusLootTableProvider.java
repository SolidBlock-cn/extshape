package pers.solid.extshape.blockus.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryWrapper;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.blockus.ExtShapeBlockusBlocks;

import java.util.concurrent.CompletableFuture;

public class ExtShapeBlockusLootTableProvider extends FabricBlockLootTableProvider {
  protected ExtShapeBlockusLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
    super(dataOutput, registryLookup);
  }

  @Override
  public void generate() {
    for (Block block : ExtShapeBlockusBlocks.BLOCKUS_BLOCKS) {
      if (block instanceof ExtShapeBlockInterface i) {
        final LootTable.Builder lootTable = i.getLootTable(this);
        addDrop(block, lootTable);
      }
    }
  }
}
