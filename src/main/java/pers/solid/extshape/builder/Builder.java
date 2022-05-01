package pers.solid.extshape.builder;


import net.minecraft.util.Identifier;

interface Builder<T> {
  /**
   * 将构造好的对象注册到注册表。
   */
  void register();

  /**
   * 构造对象时，不将其注册到注册表。
   */
  Builder<T> noRegister();

  /**
   * 该构造器需要构造的对象的命名空间id。
   */
  Identifier getBlockId();

  /**
   * 设置构造后注册到的命名空间id。
   *
   * @param identifier 命名空间id。
   */
  Builder<T> setIdentifier(Identifier identifier);

  /**
   * 手动创建将要构造的实例，一般为一个新的对象。
   */
  void createInstance();

  /**
   * 完成构造并进行一系列约定操作，返回实例。
   */
  T build();
}
