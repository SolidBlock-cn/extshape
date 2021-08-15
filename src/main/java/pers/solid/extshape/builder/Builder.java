package pers.solid.extshape.builder;

import net.minecraft.util.Identifier;

interface Builder<T> {
    Builder<T> register();
    Builder<T> noRegister();
    Identifier getIdentifier();
    Builder<T> setIdentifier(Identifier identifier);
    void createInstance();

    T build();
}
