package pers.solid.extshape.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Supplier;

@Mixin(AbstractBlock.Settings.class)
public interface AbstractBlockSettingsAccessor {
  @Accessor
  void setLootTableId(Identifier lootTableId);

  @Accessor
  void setLootTableSupplier(Supplier<Identifier> lootTableSupplier);
}
