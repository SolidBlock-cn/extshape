package pers.solid.extshape.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.item.ShearsItem;
import net.minecraft.registry.RegistryEntryLookup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import pers.solid.extshape.tag.ExtShapeTags;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Mixin(ShearsItem.class)
public abstract class ShearsItemMixin {
  /**
   * 通过修改组件，允许快速挖掘本模组中的羊毛方块。
   */
  @ModifyArg(method = "createToolComponent", at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/ToolComponent;<init>(Ljava/util/List;FI)V"), index = 0)
  private static List<ToolComponent.Rule> addMineableTag(List<ToolComponent.Rule> blocks, @Local RegistryEntryLookup<Block> registryEntryLookup) {
    if (!(blocks instanceof ArrayList<ToolComponent.Rule> || blocks instanceof LinkedList<ToolComponent.Rule> || blocks instanceof ObjectArrayList<ToolComponent.Rule>)) {
      blocks = new ArrayList<>(blocks);
    }
    blocks.add(ToolComponent.Rule.of(registryEntryLookup.getOrThrow(ExtShapeTags.WOOLEN_BLOCKS), 5));
    return blocks;
  }
}
