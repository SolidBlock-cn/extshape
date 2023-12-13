package pers.solid.extshape.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractBlock.Settings.class)
public interface AbstractBlockSettingsAccessor {
  @Accessor
  void setLootTableId(Identifier lootTableId);

  @Accessor
  void setMaterial(Material material);
}
