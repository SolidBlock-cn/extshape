package pers.solid.extshape.builder;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.*;
import pers.solid.extshape.ExtShapeBlockItem;
import pers.solid.extshape.block.BlockExtension;
import pers.solid.extshape.tag.TagPreparations;
import pers.solid.extshape.util.BlockBiMaps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 这个类可以用来方便地创建方块和物品对象，并在创建前后进行一系列附加的操作。
 *
 * @param <T> 需要创建的方块的类型。
 */
public abstract class AbstractBlockBuilder<T extends Block> {
  /**
   * 该方块的基础方块。
   */
  public final Block baseBlock;
  /**
   * 创建对象后，将方块和物品所拥有的标签记录到这个 {@link TagPreparations} 对象中，以后直接对这个对象进行数据生成。
   */
  protected TagPreparations tagPreparations;
  /**
   * 是否为该方块构建物品。
   */
  public final boolean buildItem;
  /**
   * 创建实例并构建后，将自身添加到这些标签中。
   */
  protected List<@NotNull TagKey<? extends ItemConvertible>> extraTags = new ArrayList<>();
  public AbstractBlock.Settings blockSettings;
  /**
   * 构建之后将构建后的方块添加到这个集合中，方便以后进行集中的管理。这个一般是在 {@link BlocksBuilderFactory} 中设置的，然后间接传递到这个参数来。
   */
  protected @Nullable Collection<Block> instanceCollection;
  /**
   * 计算方块注册时使用的 id 时使用的默认命名空间。如果为 {@code null}，则直接使用基础方块的 id。如果使用 {@link #setIdentifier(Identifier)} 设置好了 id，那么不会使用 {@code defaultNamespace} 来计算 id。
   */
  protected @Nullable String defaultNamespace;
  /**
   * 创建对象后需要将这个方块添加到的主要标签，如 {@code slabs}、{@code extshape:vertical_slabs} 等。
   */
  protected @Nullable TagKey<? extends ItemConvertible> primaryTagToAddTo = null;
  /**
   * 需要创建的方块的方块形状，主要用于在创建之后注册 {@link BlockBiMaps}。
   */
  protected BlockShape shape;
  /**
   * 是否将方块添加到相应的 {@link BlockBiMaps} 中。
   */
  protected boolean shouldAddToBlockBiMap;
  /**
   * 用于构造实例的匿名函数。该值必须非 {@code null}，否则实例化无法进行。
   *
   * @see #createInstance()
   * @see #setInstanceSupplier(Function)
   */
  protected @NotNull Function<AbstractBlockBuilder<T>, T> instanceSupplier;
  /**
   * 用于构造其物品实例的匿名函数。
   */
  protected @NotNull Function<AbstractBlockBuilder<T>, BlockItem> itemInstanceSupplier = builder -> new ExtShapeBlockItem(builder.instance, builder.itemSettings);
  /**
   * 方块实例。只有在调用 {@link #build()} 之后，这个字段才有可能非 {@code null}。
   */
  public T instance;
  /**
   * 是否注册方块。
   */
  public boolean registerBlock, registerItem;
  /**
   * 该方块对应物品的 {@link Item.Settings}。
   */
  public Item.Settings itemSettings;
  /**
   * 该方块所拥有的 id，将会在注册时使用。
   */
  public Identifier identifier;
  /**
   * 物品实例。只有在调用 {@link #build()} 之后，这个字段才有可能非 {@code null}。
   */
  public BlockItem itemInstance;

  protected AbstractBlockBuilder(@Nullable Block baseBlock, AbstractBlock.Settings settings, @NotNull Function<AbstractBlockBuilder<T>, T> instanceSupplier) {
    this.baseBlock = baseBlock;
    this.registerBlock = true;
    this.registerItem = true;
    this.blockSettings = settings;
    this.buildItem = true;
    this.shouldAddToBlockBiMap = true;
    this.itemSettings = new Item.Settings();
    if (baseBlock != null && baseBlock.asItem() != null) {
      if (baseBlock.asItem().isFireproof()) itemSettings.fireproof();
    }
    this.instanceSupplier = instanceSupplier;
  }

  protected AbstractBlockBuilder(Block baseBlock, @NotNull Function<AbstractBlockBuilder<T>, T> instanceSupplier) {
    this(baseBlock, AbstractBlock.Settings.copy(baseBlock), instanceSupplier);
  }

  /**
   * 将完整的方块英文名称转换为前缀。
   */
  private static final @Unmodifiable Map<Pattern, String> blockNameConversion = new ImmutableMap.Builder<Pattern, String>()
      .put(Pattern.compile("bamboo_block$"), "bamboo_block")
      .put(Pattern.compile("_planks$"), StringUtils.EMPTY)
      .put(Pattern.compile("_block$"), StringUtils.EMPTY)
      .put(Pattern.compile("^block_of_"), StringUtils.EMPTY)
      .put(Pattern.compile("tiles$"), "tile")
      .put(Pattern.compile("bricks$"), "brick")
      .build();

  /**
   * 获得 {@code path} 对应的名称前缀。
   *
   * @param path 命名空间id中的路径。如 {@code iron_block}、{@code stone_bricks}。
   * @return 转换得到的路径前缀。
   */
  public static @NotNull String getPathPrefixOf(@NotNull String path) {
    for (Map.Entry<Pattern, String> entry : blockNameConversion.entrySet()) {
      final Pattern key = entry.getKey();
      final Matcher matcher = key.matcher(path);
      if (matcher.find()) {
        return matcher.replaceFirst(entry.getValue());
      }
    }
    return path;
  }

  /**
   * 根据基础方块的命名空间id以及指定的后缀，组合一个extshape命名空间下的新的id。
   *
   * @param identifier 基础方块的id，如<code>minecraft:quartz_bricks</code>。
   * @param namespace  命名空间，如果为 null，则使用 {@code identifier} 的命名空间。
   * @param suffix     后缀，例如<code>"_stairs"</code>或<code>"_fence"</code>。
   * @return 组合后的id，例如 <code>minecraft:quartz_bricks</code> 和 <code>"_stairs"</code> 组合形成
   * <code>extshape:quartz_stairs</code>。
   */
  public static Identifier convertIdentifier(@NotNull Identifier identifier, @Nullable String namespace, @NotNull String suffix) {
    String path = identifier.getPath();
    String basePath = getPathPrefixOf(path);
    return new Identifier(namespace == null ? identifier.getNamespace() : namespace, basePath + suffix);
  }


  /**
   * 设置将要构造方块时的方块设置。
   *
   * @param settings 方块设置。
   */
  @CanIgnoreReturnValue
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
  @CanIgnoreReturnValue
  @Contract(value = "_ -> this", mutates = "this")
  public AbstractBlockBuilder<T> setItemSettings(Item.Settings settings) {
    this.itemSettings = settings;
    return this;
  }

  /**
   * @return 从注册表获取方块对应基础方块的命名空间id。
   */
  protected Identifier getBaseIdentifier() {
    return ForgeRegistries.BLOCKS.getKey(baseBlock);
  }

  /**
   * @return 方块将要注册的命名空间id。
   */
  public Identifier getBlockId() {
    if (identifier == null) {
      identifier = convertIdentifier(getBaseIdentifier(), defaultNamespace, this.getSuffix());
    }
    return identifier;
  }


  /**
   * 设置方块将要注册的命名空间 id，应该在注册之前调用。
   *
   * @param identifier 方块将要注册的命名空间id。
   */
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
   * 设置方块所拥有的主要方块标签，注册后就将此方块添加到此标签中。
   *
   * @param tag 方块标签。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_ -> this", mutates = "this")
  public AbstractBlockBuilder<T> setPrimaryTagToAddTo(TagKey<? extends ItemConvertible> tag) {
    this.primaryTagToAddTo = tag;
    return this;
  }

  /**
   * 添加到 {@link BlockBiMaps} 中。如果该对象的 {@link #shape} 为 {@code null}，则不执行操作。
   */
  protected void addToBlockBiMap() {
    if (shape != null) {
      BlockBiMaps.setBlockOf(shape, baseBlock, instance);
    }
  }

  /**
   * 添加一个标签，当方块创建完成后就将其添加到这个标签中。
   *
   * @param tag 方块构建后，需要添加到的标签。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_ -> this", mutates = "this")
  public AbstractBlockBuilder<T> addExtraTag(@NotNull TagKey<? extends ItemConvertible> tag) {
    this.extraTags.add(tag);
    return this;
  }

  /**
   * 设置实例，手动构建方块对象，而不是通过 {@link #build()} 来完成。
   *
   * @param instance 方块实例。一般是一个新的方块对象。
   */
  @ApiStatus.Internal
  @CanIgnoreReturnValue
  @Contract(value = "_ -> this", mutates = "this")
  public AbstractBlockBuilder<T> setInstanceDirectly(T instance) {
    this.instance = instance;
    return this;
  }

  /**
   * 设置如何创建方块实例。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_ -> this", mutates = "this")
  public AbstractBlockBuilder<T> setInstanceSupplier(Function<AbstractBlockBuilder<T>, T> supplier) {
    this.instanceSupplier = supplier;
    return this;
  }

  /**
   * 设置如何创建物品实例。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_ -> this", mutates = "this")
  public AbstractBlockBuilder<T> setItemInstanceSupplier(Function<AbstractBlockBuilder<T>, BlockItem> supplier) {
    this.itemInstanceSupplier = supplier;
    return this;
  }

  /**
   * 方块构建后，将其方块物品添加到指定的物品组中。
   *
   * @param group 物品组。若为 {@code null}，则表示不添加到物品组中。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_ -> this", mutates = "this")
  public AbstractBlockBuilder<T> group(@Nullable ItemGroup group) {
    this.itemSettings.group(group);
    return this;
  }

  /**
   * 立即使用当前的 {@link #instanceSupplier} 生成方块实例。
   */
  protected void createInstance() {
    this.instance = this.instanceSupplier.apply(this);
  }

  /**
   * 立即使用当前的 {@link #itemInstanceSupplier} 生成物品实例。
   */
  protected void createItemInstance() {
    this.itemInstance = itemInstanceSupplier.apply(this);
  }

  /**
   * 构建方块，并按照构建时的设置进行一系列操作。
   *
   * @return 构建后的方块。
   */
  public T build() {
    if (this.instance == null) this.createInstance();
    if (this.registerBlock) {
      ForgeRegistries.BLOCKS.register(this.getBlockId(), instance);
    }
    if (buildItem) {
      createItemInstance();
      if (registerItem) {
        ForgeRegistries.ITEMS.register(identifier, this.itemInstance);
      }
    }

    // 将方块添加到指定的集合中。
    if (instanceCollection != null) {
      instanceCollection.add(instance);
    }

    if (this.primaryTagToAddTo != null) {
      Preconditions.checkNotNull(tagPreparations, "tagPreparations, which must be specified if you need to use it for data generation!");
      tagPreparations.put(primaryTagToAddTo, instance);
    }
    this.extraTags.forEach(tag -> tagPreparations.put(tag, instance));
    if (this.shouldAddToBlockBiMap) this.addToBlockBiMap();
    return this.instance;
  }

  @Contract("_ -> this")
  public AbstractBlockBuilder<T> withExtension(BlockExtension blockExtension) {
    return this;
  }
}
