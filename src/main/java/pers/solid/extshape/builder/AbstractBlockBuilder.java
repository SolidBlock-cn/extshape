package pers.solid.extshape.builder;

import com.google.common.collect.BiMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapeBlocks;
import pers.solid.extshape.block.ExtShapeVariantBlockInterface;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;
import pers.solid.extshape.tag.ExtShapeBlockTags;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class AbstractBlockBuilder<T extends Block> implements Builder<T> {
  /**
   * 该方块的基础方块。
   */
  public final Block baseBlock;
  /**
   * 是否将方块添加到默认的标签中。默认的标签可以使用 {@link #setDefaultTagToAdd(ExtShapeBlockTag)} 修改。
   */
  public final boolean addToDefaultTag;
  /**
   * 是否为该方块构建物品。
   */
  public final boolean buildItem;
  private final List<@NotNull ExtShapeBlockTag> tagsToAdd = new ArrayList<>();
  public AbstractBlock.Settings blockSettings;
  public ExtShapeBlockItemBuilder itemBuilder;

  protected @Nullable ExtShapeBlockTag defaultTagToAdd = ExtShapeBlockTags.EXTSHAPE_BLOCKS;
  protected BiMap<Block, ? super T> mapping;
  /**
   * 是否将方块添加到相应的映射中。
   */
  protected boolean addToMapping;
  /**
   * 用于构造实例的匿名函数。该值必须非 {@code null}，否则实例化无法进行。
   *
   * @see #createInstance()
   * @see #setInstanceSupplier(Function)
   */
  protected @NotNull Function<AbstractBlockBuilder<T>, T> instanceSupplier;
  /**
   * 构造器的方块实例。需要注意，只有在调用{@link #build()}之后，这个实例才会存在，从而对实例进行实际操作。
   */
  protected T instance;
  /**
   * 是否注册方块和物品。
   */
  protected boolean registerBlock, registerItem;
  /**
   * 物品设置。
   */
  protected Item.Settings itemSettings;
  /**
   * 该物品所属的物品组。通常是原版的物品组。
   */
  protected @Nullable ItemGroup group;
  /**
   * 该方块所拥有的 id。
   */
  private Identifier identifier;

  protected AbstractBlockBuilder(Block baseBlock, AbstractBlock.Settings settings, @NotNull Function<AbstractBlockBuilder<T>, T> instanceSupplier) {
    this.baseBlock = baseBlock;
    this.registerBlock = true;
    this.registerItem = true;
    this.addToDefaultTag = true;
    this.blockSettings = settings;
    this.buildItem = true;
    this.addToMapping = true;
    this.itemSettings = new FabricItemSettings();
    this.instanceSupplier = instanceSupplier;
  }

  protected AbstractBlockBuilder(Block baseBlock, @NotNull Function<AbstractBlockBuilder<T>, T> instanceSupplier) {
    this(baseBlock, FabricBlockSettings.copyOf(baseBlock), instanceSupplier);
  }


  /**
   * 将方块注册到注册表，不影响其对应方块物品。需确保方块已构造。
   * 如需要在构建时，设置需要注册的命名空间id，应使用{@link #setInstanceSupplier}。
   */
  @Override
  public void register() {
    Registry.register(Registry.BLOCK, this.getBlockId(), instance);
  }

  /**
   * 设置将要构造方块时的方块设置。
   *
   * @param settings 方块设置。
   */
  @Contract(value = "_ -> this", mutates = "this")
  public AbstractBlockBuilder<T> setBlockSettings(AbstractBlock.Settings settings) {
    this.blockSettings = settings;
    return this;
  }

  /**
   * 设置方块对应物品的物品设置。
   *
   * @param settings 物品设置。
   */
  @Contract(value = "_ -> this", mutates = "this")
  public AbstractBlockBuilder<T> setItemSettings(Item.Settings settings) {
    this.itemSettings = settings;
    return this;
  }

  /**
   * @return 从注册表获取方块对应基础方块的命名空间id。
   */
  protected Identifier getBaseIdentifier() {
    return Registry.BLOCK.getId(baseBlock);
  }

  /**
   * @return 方块将要注册的命名空间id。
   */
  @Override
  public Identifier getBlockId() {
    if (identifier == null) {
      identifier = ExtShapeVariantBlockInterface.convertIdentifier(getBaseIdentifier(), this.getSuffix());
    }
    return identifier;
  }


  /**
   * 设置方块将要注册的命名空间id。
   *
   * @param identifier 方块将要注册的命名空间id。
   */
  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public AbstractBlockBuilder<T> setIdentifier(Identifier identifier) {
    this.identifier = identifier;
    return this;
  }

  /**
   * @return 后缀字符串。
   */
  @Contract(pure = true)
  protected abstract String getSuffix();

  /**
   * 手动设置方块所处的默认方块标签。
   *
   * @param tag 方块标签。
   */
  @Contract(value = "_ -> this", mutates = "this")
  public AbstractBlockBuilder<T> setDefaultTagToAdd(ExtShapeBlockTag tag) {
    this.defaultTagToAdd = tag;
    return this;
  }

  /**
   * 不注册方块和物品。
   */
  @Override
  @Contract(value = "-> this", mutates = "this")
  public AbstractBlockBuilder<T> noRegister() {
    this.registerBlock = false;
    this.registerItem = false;
    return this;
  }

  /**
   * 添加到方块映射表中。如果方块映射表不存在，则不执行。
   */
  protected void addToMapping() {
    if (mapping != null) {
      mapping.put(baseBlock, instance);
      BlockMappings.BASE_BLOCKS.remove(baseBlock);
      BlockMappings.BASE_BLOCKS.add(baseBlock);
    }
  }

  /**
   * 方块构建后，添加到指定的标签中。
   *
   * @param tag 方块构建后，需要添加到的标签。
   */
  @Contract(value = "_ -> this", mutates = "this")
  public AbstractBlockBuilder<T> addTagToAdd(@NotNull ExtShapeBlockTag tag) {
    this.tagsToAdd.add(tag);
    return this;
  }

  /**
   * 设置实例，手动构建方块。
   *
   * @param instance 方块实例。一般是一个新的方块对象。
   */
  @ApiStatus.Internal
  @Contract(value = "_ -> this", mutates = "this")
  public AbstractBlockBuilder<T> setInstanceDirectly(T instance) {
    this.instance = instance;
    return this;
  }

  @Contract(value = "_ -> this", mutates = "this")
  public AbstractBlockBuilder<T> setInstanceSupplier(Function<AbstractBlockBuilder<T>, T> supplier) {
    this.instanceSupplier = supplier;
    return this;
  }

  /**
   * 方块构建后，将其方块物品添加到指定的物品组中。
   *
   * @param group 物品组。若为 {@code null}，则表示不添加到物品组中。
   */
  @Contract(value = "_ -> this", mutates = "this")
  public AbstractBlockBuilder<T> group(@Nullable ItemGroup group) {
    this.itemSettings.group(group);
    return this;
  }

  @Override
  public final void createInstance() {
    this.instance = this.instanceSupplier.apply(this);
  }

  /**
   * 构建方块，并按照构建时的设置进行一系列操作。
   *
   * @return 构建后的方块。
   */
  @Override
  public T build() {
    if (this.instance == null) this.createInstance();
    if (this.registerBlock) this.register();
    if (this.addToDefaultTag && this.defaultTagToAdd != null) this.defaultTagToAdd.add(this.instance);
    this.tagsToAdd.forEach(tag -> tag.add(this.instance));
    if (this.addToMapping) this.addToMapping();

    if (buildItem) {
      this.itemBuilder = new ExtShapeBlockItemBuilder(this.instance, itemSettings);
      itemBuilder.setIdentifier(identifier);
      if (!registerItem) itemBuilder.noRegister();
      this.itemBuilder.setIdentifier(this.getBlockId()).build();
    }

    // 将方块添加到列表中。
    ExtShapeBlocks.BLOCKS.add(instance);

    return this.instance;
  }
}
