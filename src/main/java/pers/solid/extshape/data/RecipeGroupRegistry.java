package pers.solid.extshape.data;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Contract;

import java.util.HashMap;
import java.util.Map;

public final class RecipeGroupRegistry {
  private RecipeGroupRegistry() {
  }

  public static final Map<Item, String> INSTANCE = new HashMap<>();

  @Contract(pure = true)
  public static String getRecipeGroup(ItemLike itemConvertible) {
    return INSTANCE.getOrDefault(itemConvertible.asItem(), StringUtils.EMPTY);
  }

  public static void setRecipeGroup(ItemLike itemConvertible, String recipeGroup) {
    INSTANCE.put(itemConvertible.asItem(), recipeGroup);
  }
}
