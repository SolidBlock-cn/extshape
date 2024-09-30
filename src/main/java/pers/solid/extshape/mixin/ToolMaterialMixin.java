package pers.solid.extshape.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.Block;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import pers.solid.extshape.tag.ExtShapeTags;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Mixin(ToolMaterial.class)
public interface ToolMaterialMixin {
  @ModifyArg(method = "createComponent", at = @At(value = "INVOKE", target = "Lnet/minecraft/component/type/ToolComponent;<init>(Ljava/util/List;FI)V"), index = 0)
  private List<ToolComponent.Rule> addExclusiveRule(List<ToolComponent.Rule> rules, @Local(argsOnly = true) TagKey<Block> tag) {
    if (!tag.equals(BlockTags.PICKAXE_MINEABLE) && !tag.equals(BlockTags.AXE_MINEABLE)) {
      return rules;
    }
    if (!(rules instanceof ArrayList<ToolComponent.Rule> || rules instanceof LinkedList<ToolComponent.Rule> || rules instanceof ObjectArrayList<ToolComponent.Rule>)) {
      rules = new ArrayList<>(rules);
    }
    if (tag.equals(BlockTags.PICKAXE_MINEABLE)) {
      rules.addFirst(ToolComponent.Rule.of(ExtShapeTags.PICKAXE_UNMINEABLE, 1));
    } else if (tag.equals(BlockTags.AXE_MINEABLE)) {
      rules.addFirst(ToolComponent.Rule.of(ExtShapeTags.AXE_UNMINEABLE, 1));
    }
    return rules;
  }
}
