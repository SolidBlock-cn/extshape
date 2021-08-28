package pers.solid.extshape.builder;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public abstract class AbstractItemBuilder<T extends Item> implements Builder<T> {
    FabricItemSettings settings;
    Identifier identifier;
    boolean registerItem = true;
    T item;

    protected AbstractItemBuilder(FabricItemSettings settings) {
        this.settings = settings;
    }

    protected AbstractItemBuilder() {
        this(new FabricItemSettings());
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

    /**
     * 将物品设置为防火。
     */
    public AbstractItemBuilder<T> fireproof() {
        this.settings.fireproof();
        return this;
    }

    @Override
    public Identifier getIdentifier() {
        return this.identifier;
    }

    public AbstractItemBuilder<T> setSettings(FabricItemSettings settings) {
        this.settings = settings;
        return this;
    }

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
