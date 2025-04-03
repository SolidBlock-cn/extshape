package pers.solid.extshape.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractBlock.Settings.class)
public interface AbstractBlockSettingsAccessor {
  @Accessor
  void setLootTableKey(RegistryKey<LootTable> registryKey);
}
