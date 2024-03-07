package pers.solid.extshape.mixin;

import com.google.common.collect.Multimap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.resource.featuretoggle.FeatureSet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pers.solid.extshape.VanillaItemGroup;
import pers.solid.extshape.config.ExtShapeConfig;

import java.util.Collection;
import java.util.Set;

@Mixin(targets = "net.minecraft.item.ItemGroup$EntriesImpl")
public abstract class ItemGroupEntriesImplMixin {
  @Shadow
  @Final
  private ItemGroup group;

  @Shadow
  @Final
  private FeatureSet enabledFeatures;

  @Shadow
  @Final
  public Collection<ItemStack> parentTabStacks;

  @Shadow
  @Final
  public Set<ItemStack> searchTabStacks;

  @Unique
  public void addSwiftly(ItemStack stack, ItemGroup.StackVisibility visibility) {
    if (stack.getItem().isEnabled(enabledFeatures)) {
      switch (visibility) {
        case PARENT_AND_SEARCH_TABS:
          this.parentTabStacks.add(stack);
          this.searchTabStacks.add(stack);
          break;
        case PARENT_TAB_ONLY:
          this.parentTabStacks.add(stack);
          break;
        case SEARCH_TAB_ONLY:
          this.searchTabStacks.add(stack);
      }
    }
  }

  @Unique
  private final Multimap<Item, Item> prependingRule = ExtShapeConfig.CURRENT_CONFIG.addToVanillaGroups ? Registries.ITEM_GROUP.getKey(this.group).map(VanillaItemGroup::getPrependingRule).orElse(null) : null;
  @Unique
  private final Multimap<Item, Item> appendingRule = ExtShapeConfig.CURRENT_CONFIG.addToVanillaGroups ? Registries.ITEM_GROUP.getKey(this.group).map(VanillaItemGroup::getAppendingRule).orElse(null) : null;

  @Inject(method = "add", at = @At("HEAD"))
  public void preAdd(ItemStack stack, ItemGroup.StackVisibility visibility, CallbackInfo ci) {
    if (prependingRule != null) {
      for (Item item : prependingRule.get(stack.getItem())) {
        addSwiftly(new ItemStack(item), visibility);
      }
    }
  }

  @Inject(method = "add", at = @At("RETURN"))
  public void postAdd(ItemStack stack, ItemGroup.StackVisibility visibility, CallbackInfo ci) {
    if (appendingRule != null) {
      for (Item item : appendingRule.get(stack.getItem())) {
        addSwiftly(new ItemStack(item), visibility);
      }
    }
  }
}
