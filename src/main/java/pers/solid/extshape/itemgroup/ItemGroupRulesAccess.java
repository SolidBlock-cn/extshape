package pers.solid.extshape.itemgroup;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.RegistryKey;
import org.jetbrains.annotations.ApiStatus;
import pers.solid.extshape.ExtShape;

import java.util.Map;
import java.util.stream.Collectors;

public interface ItemGroupRulesAccess {
  Multimap<Item, Item> getPrependingRule(RegistryKey<ItemGroup> group);

  Multimap<Item, Item> getAppendingRule(RegistryKey<ItemGroup> group);

  /**
   * 将所有规则的摘要输出到控制台。
   */
  void logRulesInfo();

  static ItemGroupRulesAccess create() {
    return new Simple(new Object2ObjectLinkedOpenHashMap<>(), new Object2ObjectLinkedOpenHashMap<>());
  }

  @ApiStatus.Internal
  record Simple(Map<RegistryKey<ItemGroup>, Multimap<Item, Item>> prependingRules, Map<RegistryKey<ItemGroup>, Multimap<Item, Item>> appendingRules) implements ItemGroupRulesAccess {

    @Override
    public Multimap<Item, Item> getPrependingRule(RegistryKey<ItemGroup> group) {
      return prependingRules.computeIfAbsent(group, itemGroup -> ArrayListMultimap.create());
    }

    @Override
    public Multimap<Item, Item> getAppendingRule(RegistryKey<ItemGroup> group) {
      return appendingRules.computeIfAbsent(group, itemGroup -> ArrayListMultimap.create());
    }

    @Override
    public void logRulesInfo() {
      ExtShape.LOGGER.info("There are {} prepending rules for the following item groups: {}", prependingRules.size(), prependingRules.entrySet().stream().map(entry -> entry.getKey().getValue() + " (" + entry.getValue().size() + ")").collect(Collectors.joining(", ", "[", "]")));
      ExtShape.LOGGER.info("There are {} appending rules for the following item groups: {}", appendingRules.size(), appendingRules.entrySet().stream().map(entry -> entry.getKey().getValue() + " (" + entry.getValue().size() + ")").collect(Collectors.joining(", ", "[", "]")));
    }
  }
}
