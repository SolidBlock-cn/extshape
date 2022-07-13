package pers.solid.extshape.builder;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Contract;

public abstract class AbstractItemBuilder<T extends Item> implements Builder<T> {
  protected Item.Settings settings;
  protected Identifier identifier;
  protected boolean registerItem = true;
  T item;

  protected AbstractItemBuilder(Item.Settings settings) {
    this.settings = settings;
  }

  protected AbstractItemBuilder() {
    this(new Item.Settings());
  }

  /**
   * 将物品注册到注册表。
   */
  @Override
  public void register() {
    item.setRegistryName(identifier);
    ForgeRegistries.ITEMS.register(item);
  }

  /**
   * 不注册物品。
   */
  @Override
  public AbstractItemBuilder<T> noRegister() {
    this.registerItem = false;
    return this;
  }

  /**
   * 将物品设置为防火。
   */
  @Contract(value = "-> this", mutates = "this")
  public AbstractItemBuilder<T> fireproof() {
    this.settings.fireproof();
    return this;
  }

  @Override
  public Identifier getBlockId() {
    return this.identifier;
  }

  @Contract(value = "_ -> this", mutates = "this")
  public AbstractItemBuilder<T> setSettings(Item.Settings settings) {
    this.settings = settings;
    return this;
  }

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
