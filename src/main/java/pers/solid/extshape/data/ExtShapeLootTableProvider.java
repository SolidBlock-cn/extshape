package pers.solid.extshape.data;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryWrapper;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.ExtShapeBlockInterface;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.builder.BlockShape;

import java.util.concurrent.CompletableFuture;

public class ExtShapeLootTableProvider extends FabricBlockLootTableProvider {
  protected ExtShapeLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
    super(dataOutput, registryLookup);
  }

  @Override
  public void generate() {
    final ImmutableMap<Block, UnusualLootTables.@NotNull LootTableFunction> instance = new UnusualLootTables().createInstance();
    for (Block block : ExtShapeBlocks.getBlocks()) {
      if (block.getLootTableKey().isEmpty()) {
        continue;
      }
      if (block instanceof ExtShapeBlockInterface i) {
        final LootTable.Builder lootTable;
        final Block baseBlock = i.getBaseBlock();
        final UnusualLootTables.LootTableFunction unusual = instance.get(baseBlock);
        if (unusual != null) {
          lootTable = unusual.apply(baseBlock, BlockShape.getShapeOf(block), block, registries, this);
        } else {
          lootTable = i.getLootTable(this);
        }
        this.addDrop(block, lootTable);
      }
    }
    this.addDrop(ExtShapeBlocks.PETRIFIED_OAK_PLANKS);
    this.addDrop(ExtShapeBlocks.SMOOTH_STONE_DOUBLE_SLAB);
  }
}
