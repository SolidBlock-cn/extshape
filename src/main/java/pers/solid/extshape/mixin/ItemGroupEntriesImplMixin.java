package pers.solid.extshape.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pers.solid.extshape.VanillaItemGroup;
import pers.solid.extshape.config.ExtShapeConfig;

@Mixin(targets = "net.minecraft.item.ItemGroup$EntriesImpl")
public abstract class ItemGroupEntriesImplMixin {
  @Shadow
  @Final
  private ItemGroup group;

  @Shadow
  public abstract void add(ItemStack stack, ItemGroup.StackVisibility visibility);

  @Unique
  private boolean allowTransitive = true;

  @Inject(method = "add", at = @At("HEAD"))
  public void preAdd(ItemStack stack, ItemGroup.StackVisibility visibility, CallbackInfo ci) {
    if (ExtShapeConfig.CURRENT_CONFIG.addToVanillaGroups && allowTransitive) {
      allowTransitive = false;
      // 为了避免递归添加物品，故添加一个布尔值进行控制。下面的 postAdd 同理。
      for (Item item : VanillaItemGroup.getPrependingRule(this.group).get(stack.getItem())) {
        add(new ItemStack(item), visibility);
      }
      allowTransitive = true;
    }
  }

  @Inject(method = "add", at = @At("RETURN"))
  public void postAdd(ItemStack stack, ItemGroup.StackVisibility visibility, CallbackInfo ci) {
    if (ExtShapeConfig.CURRENT_CONFIG.addToVanillaGroups && allowTransitive) {
      allowTransitive = false;
      // 为了避免递归添加物品，故添加一个布尔值进行控制。下面的 postAdd 同理。
      for (Item item : VanillaItemGroup.getAppendingRule(this.group).get(stack.getItem())) {
        add(new ItemStack(item), visibility);
      }
      allowTransitive = true;
    }
  }
}
