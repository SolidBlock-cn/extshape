package pers.solid.extshape.rrp;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class RecipeGroupRegistry {
  private RecipeGroupRegistry() {
  }

  public static final Map<Item, String> INSTANCE = new HashMap<>();

  @Contract(pure = true)
  public static @NotNull String getRecipeGroup(@NotNull ItemConvertible itemConvertible) {
    return INSTANCE.getOrDefault(itemConvertible.asItem(), StringUtils.EMPTY);
  }

  public static void setRecipeGroup(@NotNull ItemConvertible itemConvertible, @NotNull String recipeGroup) {
    INSTANCE.put(itemConvertible.asItem(), recipeGroup);
  }
}
