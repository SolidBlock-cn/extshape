package pers.solid.extshape.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryWrapper;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.block.ExtShapeBlocks;

import java.util.concurrent.CompletableFuture;

public class ExtShapeLootTableProvider extends FabricBlockLootTableProvider {
  protected ExtShapeLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
    super(dataOutput, registryLookup);
  }

  @Override
  public void generate() {
    for (Block block : ExtShapeBlocks.getBlocks()) {
      if (block instanceof ExtShapeBlockInterface i) {
        final LootTable.Builder lootTable = i.getLootTable(this);
        this.addDrop(block, lootTable);
      }
    }
    this.addDrop(ExtShapeBlocks.PETRIFIED_OAK_PLANKS);
    this.addDrop(ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB);
  }
}
