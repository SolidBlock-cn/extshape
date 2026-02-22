package pers.solid.extshape.mixin;

import com.google.common.collect.Multimap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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

@Mixin(targets = "net.minecraft.world.item.CreativeModeTab$ItemDisplayBuilder")
public abstract class ItemGroupEntriesImplMixin {
  @Shadow
  @Final
  private CreativeModeTab tab;

  @Shadow
  @Final
  private FeatureFlagSet featureFlagSet;

  @Shadow
  @Final
  public Collection<ItemStack> tabContents;

  @Shadow
  @Final
  public Set<ItemStack> searchTabContents;

  @Unique
  public void addSwiftly(ItemStack stack, CreativeModeTab.TabVisibility visibility) {
    if (stack.getItem().isEnabled(featureFlagSet)) {
      switch (visibility) {
        case PARENT_AND_SEARCH_TABS:
          this.tabContents.add(stack);
          this.searchTabContents.add(stack);
          break;
        case PARENT_TAB_ONLY:
          this.tabContents.add(stack);
          break;
        case SEARCH_TAB_ONLY:
          this.searchTabContents.add(stack);
      }
    }
  }

  @Unique
  private Multimap<Item, Item> prependingRule;
  @Unique
  private Multimap<Item, Item> appendingRule;

  @Inject(method = "<init>", at = @At("TAIL"))
  public void preInit(CreativeModeTab group, FeatureFlagSet enabledFeatures, CallbackInfo ci) {
    prependingRule = ExtShapeConfig.CURRENT_CONFIG.addToVanillaGroups ? BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(this.tab).map(VanillaItemGroup::getPrependingRule).orElse(null) : null;
    appendingRule = ExtShapeConfig.CURRENT_CONFIG.addToVanillaGroups ? BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(this.tab).map(VanillaItemGroup::getAppendingRule).orElse(null) : null;
  }

  @Inject(method = "accept", at = @At("HEAD"))
  public void preAdd(ItemStack stack, CreativeModeTab.TabVisibility visibility, CallbackInfo ci) {
    if (prependingRule != null) {
      for (Item item : prependingRule.get(stack.getItem())) {
        addSwiftly(new ItemStack(item), visibility);
      }
    }
  }

  @Inject(method = "accept", at = @At("RETURN"))
  public void postAdd(ItemStack stack, CreativeModeTab.TabVisibility visibility, CallbackInfo ci) {
    if (appendingRule != null) {
      for (Item item : appendingRule.get(stack.getItem())) {
        addSwiftly(new ItemStack(item), visibility);
      }
    }
  }
}
