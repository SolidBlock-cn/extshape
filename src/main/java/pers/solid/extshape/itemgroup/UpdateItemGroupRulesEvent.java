package pers.solid.extshape.itemgroup;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * 更新物品组的规则时的事件。此事件会在以下情况下调用：
 * <ul>
 *   <li>游戏初次加载后，构建物品组时获取规则。</li>
 *   <li>模组配置有改变后，重新构建规则。</li>
 * </ul>
 * 请注意：该事件不会在游戏初始化时调用，这是考虑到，在此模组初始化后，可能其他模组也会在初始化时注册一些规则。如有其他原因需要重建规则的，可调用 {@link ItemGroupRules#rebuildRules()}。
 */
public interface UpdateItemGroupRulesEvent {
  void updateItemGroupEntries(ItemGroupRulesAccess rulesAccess);

  Event<UpdateItemGroupRulesEvent> EVENT = EventFactory.createArrayBacked(UpdateItemGroupRulesEvent.class, updateItemGroupRulesEvents -> (rulesAccess) -> {
    for (UpdateItemGroupRulesEvent event : updateItemGroupRulesEvents) {
      event.updateItemGroupEntries(rulesAccess);
    }
  });
}
