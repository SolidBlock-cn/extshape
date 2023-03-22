package pers.solid.extshape.builder;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import net.devtech.arrp.generator.ItemResourceGenerator;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.*;
import org.spongepowered.include.com.google.common.collect.ImmutableMap;
import pers.solid.extshape.ExtShapeBlockItem;
import pers.solid.extshape.tag.BlockTagPreparation;
import pers.solid.extshape.util.BlockBiMaps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 这个类可以用来方便地创建方块和物品对象，并在创建时进行一系列附加的操作。
 *
 * @param <T>
 */
public abstract class AbstractBlockBuilder<T extends Block> implements Builder<T> {
  /**
   * 该方块的基础方块。
   */
  public final Block baseBlock;
  /**
   * 是否将方块添加到默认的标签中。默认的标签可以使用 {@link #setDefaultTagToAdd(BlockTagPreparation)} 修改。
   */
  public final boolean addToDefaultTag;
  /**
   * 是否为该方块构建物品。
   */
  public final boolean buildItem;
  /**
   * 创建实例并构建后，将自身添加到这些标签中。
   */
  private final List<@NotNull BlockTagPreparation> tagsToAdd = new ArrayList<>();
  public AbstractBlock.Settings blockSettings;
  /**
   * 构建之后将构建后的方块添加到这个集合中。
   */
  protected @Nullable Collection<Block> instanceCollection;
  /**
   * 计算方块注册时使用的 id 时使用的默认命名空间。如果为 {@code null}，则直接使用基础方块的 id。如果使用 {@link #setIdentifier(Identifier)} 设置好了 id，那么不会使用 {@code defaultNamespace} 来计算 id。
   */
  protected @Nullable String defaultNamespace;

  protected @Nullable BlockTagPreparation defaultTagToAdd = null;
  /**
   * 需要创建的方块的方块形状，主要用于在创建之后注册方块映射。
   */
  protected BlockShape shape;
  /**
   * 是否将方块添加到相应的方块映射中。
   */
  protected boolean shouldAddToBlockBiMap;
  /**
   * 用于构造实例的匿名函数。该值必须非 {@code null}，否则实例化无法进行。
   *
   * @see #createInstance()
   * @see #setInstanceSupplier(Function)
   */
  protected @NotNull Function<AbstractBlockBuilder<T>, T> instanceSupplier;
  protected @NotNull Function<AbstractBlockBuilder<T>, BlockItem> itemInstanceSupplier = builder -> new ExtShapeBlockItem(builder.instance, builder.itemSettings);
  /**
   * 构造器的方块实例。需要注意，只有在调用{@link #build()}之后，这个实例才会存在，从而对实例进行实际操作。
   */
  public T instance;
  /**
   * 是否注册方块。
   */
  protected boolean registerBlock, registerItem;
  /**
   * 该方块对应物品的物品设置。
   */
  public Item.Settings itemSettings;
  /**
   * 该方块所拥有的 id。
   */
  private Identifier identifier;
  public BlockItem itemInstance;

  protected AbstractBlockBuilder(@Nullable Block baseBlock, AbstractBlock.Settings settings, @NotNull Function<AbstractBlockBuilder<T>, T> instanceSupplier) {
    this.baseBlock = baseBlock;
    this.registerBlock = true;
    this.registerItem = true;
    this.addToDefaultTag = true;
    this.blockSettings = settings;
    this.buildItem = true;
    this.shouldAddToBlockBiMap = true;
    this.itemSettings = new FabricItemSettings();
    if (baseBlock != null && baseBlock.asItem() != null) {
      if (baseBlock.asItem().isFireproof()) itemSettings.fireproof();
    }
    this.instanceSupplier = instanceSupplier;
  }

  protected AbstractBlockBuilder(Block baseBlock, @NotNull Function<AbstractBlockBuilder<T>, T> instanceSupplier) {
    this(baseBlock, FabricBlockSettings.copyOf(baseBlock), instanceSupplier);
  }

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
   * 将方块注册到注册表，不影响其对应方块物品。需确保方块已构造。
   * 如需要在构建时，设置需要注册的命名空间id，应使用{@link #setInstanceSupplier}。
   */
  @Override
  public void register() {
    Registry.register(Registries.BLOCK, this.getBlockId(), instance);
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
    return Registries.BLOCK.getId(baseBlock);
  }

  /**
   * @return 方块将要注册的命名空间id。
   */
  @Override
  public Identifier getBlockId() {
    if (identifier == null) {
      identifier = convertIdentifier(getBaseIdentifier(), defaultNamespace, this.getSuffix());
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
  @CanIgnoreReturnValue
  @Contract(value = "_ -> this", mutates = "this")
  public AbstractBlockBuilder<T> setDefaultTagToAdd(BlockTagPreparation tag) {
    this.defaultTagToAdd = tag;
    return this;
  }

  /**
   * 不注册方块和物品。
   */
  @Override
  @CanIgnoreReturnValue
  @Contract(value = "-> this", mutates = "this")
  public AbstractBlockBuilder<T> noRegister() {
    this.registerBlock = false;
    this.registerItem = false;
    return this;
  }

  /**
   * 添加到方块映射表中。如果方块映射表不存在，则不执行。
   */
  protected void addToBlockBiMap() {
    if (shape != null) {
      BlockBiMaps.setBlockOf(shape, baseBlock, instance);
    }
  }

  /**
   * 方块构建后，添加到指定的标签中。
   *
   * @param tag 方块构建后，需要添加到的标签。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_ -> this", mutates = "this")
  public AbstractBlockBuilder<T> addTagToAdd(@NotNull BlockTagPreparation tag) {
    this.tagsToAdd.add(tag);
    return this;
  }

  /**
   * 设置实例，手动构建方块。
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
   * 设置如何生成方块实例。
   */
  @CanIgnoreReturnValue
  @Contract(value = "_ -> this", mutates = "this")
  public AbstractBlockBuilder<T> setInstanceSupplier(Function<AbstractBlockBuilder<T>, T> supplier) {
    this.instanceSupplier = supplier;
    return this;
  }

  @CanIgnoreReturnValue
  @Contract(value = "_ -> this", mutates = "this")
  public AbstractBlockBuilder<T> setItemInstanceSupplier(Function<AbstractBlockBuilder<T>, BlockItem> supplier) {
    this.itemInstanceSupplier = supplier;
    return this;
  }

  /**
   * 立即使用当前的 {@link #instanceSupplier} 生成方块实例。
   */
  @Override
  public final void createInstance() {
    this.instance = this.instanceSupplier.apply(this);
  }

  private void createItemInstance() {
    this.itemInstance = itemInstanceSupplier.apply(this);
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
    if (this.shouldAddToBlockBiMap) this.addToBlockBiMap();

    if (buildItem) {
      createItemInstance();
      if (registerItem) Registry.register(Registries.ITEM, identifier, this.itemInstance);
      this.configRecipeCategory(itemInstance);
    }

    // 将方块添加到指定的集合中。
    if (instanceCollection != null) {
      instanceCollection.add(instance);
    }

    return this.instance;
  }

  /**
   * 利用 BRRP 在生成运行时数据之前配置其配方类型，以用于更好地分类。
   */
  protected void configRecipeCategory(ItemConvertible itemConvertible) {
    if (itemConvertible instanceof FenceBlock || itemConvertible instanceof WallBlock) {
      ItemResourceGenerator.ITEM_TO_RECIPE_CATEGORY.put(itemConvertible.asItem(), RecipeCategory.DECORATIONS);
    } else if (itemConvertible instanceof FenceGateBlock || itemConvertible instanceof PressurePlateBlock || itemConvertible instanceof ButtonBlock || itemConvertible instanceof TrapdoorBlock || itemConvertible instanceof DoorBlock) {
      ItemResourceGenerator.ITEM_TO_RECIPE_CATEGORY.put(itemConvertible.asItem(), RecipeCategory.REDSTONE);
    } else {
      ItemResourceGenerator.ITEM_TO_RECIPE_CATEGORY.put(itemConvertible.asItem(), RecipeCategory.BUILDING_BLOCKS);
    }
  }
}
