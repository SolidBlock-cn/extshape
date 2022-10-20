package pers.solid.extshape.builder;

import net.devtech.arrp.util.CanIgnoreReturnValue;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Contract;

public abstract class AbstractItemBuilder<T extends Item> implements Builder<T> {
  protected Item.Settings settings;
  protected Identifier identifier;
  protected boolean registerItem = true;
  T item;

  protected AbstractItemBuilder(Item.Settings settings) {
    this.settings = settings;
  }

  /**
   * 将物品注册到注册表。
   */
  @Override
  public void register() {
    Registry.register(Registry.ITEM, this.identifier, this.item);
  }

  /**
   * 不注册物品。
   */
  @Override
  public AbstractItemBuilder<T> noRegister() {
    this.registerItem = false;
    return this;
  }

  @Override
  public Identifier getBlockId() {
    return this.identifier;
  }

  @CanIgnoreReturnValue
  @Contract(value = "_ -> this", mutates = "this")
  public AbstractItemBuilder<T> setSettings(Item.Settings settings) {
    this.settings = settings;
    return this;
  }

  @CanIgnoreReturnValue
  @Contract(value = "_ -> this", mutates = "this")
  @Override
  public Builder<T> setIdentifier(Identifier identifier) {
    this.identifier = identifier;
    return this;
  }

  @Override
  public T build() {
    this.createInstance();
    if (this.registerItem) this.register();
    return this.item;
  }
}
