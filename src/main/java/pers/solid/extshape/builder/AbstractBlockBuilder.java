package pers.solid.extshape.builder;

import com.google.common.collect.BiMap;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.loot.JCondition;
import net.devtech.arrp.json.loot.JEntry;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.loot.JPool;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.devtech.arrp.json.recipe.JIngredient;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.recipe.JResult;
import net.devtech.arrp.json.recipe.JStonecuttingRecipe;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.ExtShape;
import pers.solid.extshape.ExtShapeClient;
import pers.solid.extshape.block.ExtShapeVariantBlockInterface;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.mappings.TextureMappings;
import pers.solid.extshape.mixin.AbstractBlockMixin;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("UnusedReturnValue")
public abstract class AbstractBlockBuilder<T extends Block> implements Builder<T> {
    public final Block baseBlock;
    public final boolean addToDefaultTag;
    public final boolean buildItem;
    private final List<ExtShapeBlockTag> tagList = new ArrayList<>();
    public FabricBlockSettings blockSettings;
    public ExtShapeBlockItemBuilder itemBuilder;
    @Nullable
    protected ExtShapeBlockTag defaultTag = ExtShapeBlockTag.EXTSHAPE_BLOCKS;
    protected BiMap<Block, ? super T> mapping;
    protected boolean addToMapping;
    /**
     * 用于构造实例的匿名函数。该值必须非 {@code null}，否则实例化无法进行。
     *
     * @see #createInstance()
     * @see #setInstanceSupplier(Function)
     */
    protected @NotNull Function<AbstractBlockBuilder<T>, T> instanceSupplier;
    protected @Nullable Consumer<? super AbstractBlockBuilder<T>> preparationConsumer;
    /**
     * 构造器的方块实例。需要注意，只有在调用{@link #build()}之后，这个实例才会存在，从而对实例进行实际操作。
     */
    T instance;
    boolean registerBlock, registerItem;
    @Nullable FabricItemSettings itemSettings;
    boolean fireproof;
    @Nullable ItemGroup group;
    private Identifier identifier;

    protected AbstractBlockBuilder(Block baseBlock, FabricBlockSettings settings, @NotNull Function<AbstractBlockBuilder<T>, T> instanceSupplier) {
        this.baseBlock = baseBlock;
        this.registerBlock = true;
        this.registerItem = true;
        this.addToDefaultTag = true;
        this.blockSettings = settings;
        this.buildItem = true;
        this.addToMapping = true;
        this.itemSettings = null;
        this.instanceSupplier = instanceSupplier;
    }

    protected AbstractBlockBuilder(Block baseBlock, @NotNull Function<AbstractBlockBuilder<T>, T> instanceSupplier) {
        this(baseBlock, FabricBlockSettings.copyOf(baseBlock), instanceSupplier);
    }

    /**
     * 将标识符转化为方块标识符并添加后缀。<br>
     * <b>例如：</b>{@code minecraft:oak_slab} 和 {@code _top} 转化为 {@code minecraft:block/oak_slab_top}。
     *
     * @param identifier 要转化的标识符。
     * @param suffix     标识符后缀。
     * @return 转化后的标识符。
     */
    protected static Identifier blockIdentifier(Identifier identifier, String suffix) {
        return new Identifier(identifier.getNamespace(), "block/" + identifier.getPath() + suffix);
    }


    /**
     * 将标识符转化为方块标识符。<br>
     * <b>例如：</b>{@code minecraft:dirt} 转化为 {@code minecraft:block/dirt}。
     *
     * @param identifier 要转化的标识符。
     * @return 转化后的标识符。
     */
    public static Identifier blockIdentifier(Identifier identifier) {
        return new Identifier(identifier.getNamespace(), "block/" + identifier.getPath());
    }

    /**
     * 将标识符转化为战利品表中方块标识符。<br>
     * <b>例如：</b>{@code minecraft:dirt} 转化为 {@code minecraft:blocks/dirt}。
     *
     * @param identifier 要转化的标识符。
     * @return 转化后的标识符。
     */
    public static Identifier blocksIdentifier(Identifier identifier) {
        return new Identifier(identifier.getNamespace(), "blocks/" + identifier.getPath());
    }

    /**
     * 将标识符转化为物品标识符。<br>
     * <b>例如：</b>{@code minecraft:dirt} 转化为 {@code minecraft:item/dirt}。
     *
     * @param identifier 要转化的标识符。
     * @return 转化后的标识符。
     */
    public static Identifier itemIdentifier(Identifier identifier) {
        return new Identifier(identifier.getNamespace(), "item/" + identifier.getPath());
    }

    /**
     * 将方块注册到注册表，不影响其对应方块物品。需确保方块已构造。
     * 如需要在构建时，设置需要注册的命名空间id，应使用{@link #setInstanceSupplier}。
     */
    @Override
    public void register() {
        Registry.register(Registry.BLOCK, this.getIdentifier(), instance);
    }

    /**
     * 设置将要构造方块时的方块设置。
     *
     * @param settings 方块设置。
     */
    public AbstractBlockBuilder<T> setBlockSettings(FabricBlockSettings settings) {
        this.blockSettings = settings;
        return this;
    }

    /**
     * 设置方块对应物品的物品设置。
     *
     * @param settings 物品设置。
     */
    public AbstractBlockBuilder<T> setItemSettings(FabricItemSettings settings) {
        this.itemSettings = settings;
        return this;
    }

    /**
     * 将方块物品设置为防火。
     */
    public AbstractBlockBuilder<T> fireproof() {
        this.fireproof = true;
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
    public Identifier getIdentifier() {
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
    public AbstractBlockBuilder<T> setIdentifier(Identifier identifier) {
        this.identifier = identifier;
        return this;
    }

    /**
     * 设置破坏方块需要的工具。请参见 {@link FabricBlockSettings#breakByTool(Tag)}。
     *
     * @param tag 物品标签，如 <code>FabricToolTags.PICKAXES</code>。
     */
    public AbstractBlockBuilder<T> breakByTool(Tag<Item> tag) {
        this.blockSettings.breakByTool(tag);
        return this;
    }

    /**
     * 设置破坏方块需要的工具。请参见 {@link FabricBlockSettings#breakByTool(Tag, int)}。
     *
     * @param tag         物品标签，如 <code>FabricToolTags.PICKAXES</code>。
     * @param miningLevel 挖掘等级，如 2。
     */
    public AbstractBlockBuilder<T> breakByTool(Tag<Item> tag, int miningLevel) {
        this.blockSettings.breakByTool(tag, miningLevel);
        return this;
    }

    /**
     * 获取后缀字符串。
     */
    protected abstract String getSuffix();

    /**
     * @return 方块所处的默认方块标签。
     */
    protected @Nullable ExtShapeBlockTag getDefaultTag() {
        return this.defaultTag;
    }

    /**
     * 手动设置方块所处的默认方块标签。
     *
     * @param tag 方块标签。
     */
    public AbstractBlockBuilder<T> setDefaultTag(ExtShapeBlockTag tag) {
        this.defaultTag = tag;
        return this;
    }

    /**
     * 不注册方块。
     */
    public AbstractBlockBuilder<T> noRegisterBlock() {
        this.registerBlock = false;
        return this;
    }

    /**
     * 不注册方块和物品。
     */
    @Override
    public AbstractBlockBuilder<T> noRegister() {
        this.registerItem = false;
        return this.noRegisterBlock();
    }

    /**
     * 添加到某个方块标签中。不影响默认标签。
     *
     * @param tag 需要添加到的方块标签。
     */
    protected void addToTag(@Nullable ExtShapeBlockTag tag) {
        if (tag != null) tag.add(this.instance);
    }

    /**
     * 添加到对应的默认方块标签。
     */
    protected void addToDefaultTag() {
        this.addToTag(this.getDefaultTag());
    }

    /**
     * 添加到方块映射表中。如果方块映射表不存在，则不执行。
     */
    protected void addToMapping() {
        if (mapping != null) {
            mapping.put(baseBlock, instance);
            BlockMappings.BASE_BLOCKS.add(baseBlock);
        }
    }

    /**
     * 方块构建后，不要添加到映射表。
     */
    public AbstractBlockBuilder<T> noAddToMapping() {
        this.addToMapping = false;
        return this;
    }

    /**
     * 方块构建后，添加到指定的标签中。
     *
     * @param tag 方块构建后，需要添加到的标签。
     */
    public AbstractBlockBuilder<T> putTag(ExtShapeBlockTag tag) {
        this.tagList.add(tag);
        return this;
    }

    /**
     * 设置实例，手动构建方块。
     *
     * @param instance 方块实例。一般是一个新的方块对象。
     */
    public @Deprecated
    AbstractBlockBuilder<T> setInstance(T instance) {
        this.instance = instance;
        return this;
    }

    public AbstractBlockBuilder<T> setInstanceSupplier(Function<AbstractBlockBuilder<T>, T> supplier) {
        this.instanceSupplier = supplier;
        return this;
    }

    /**
     * 方块构建后，将其方块物品添加到指定的物品组中。
     *
     * @param group 物品组。
     */
    public AbstractBlockBuilder<T> group(ItemGroup group) {
        this.group = group;
        return this;
    }

    /**
     * 方块构建后，其方块物品不添加到物品组中。
     */
    public AbstractBlockBuilder<T> group() {
        this.group = null;
        return this;
    }

    @Override
    public final void createInstance() {
        this.instance = this.instanceSupplier.apply(this);
    }

    public final AbstractBlockBuilder<T> setPreparationConsumer(@Nullable Consumer<? super AbstractBlockBuilder<T>> consumer) {
        this.preparationConsumer = consumer;
        return this;
    }

    public Identifier getBaseTexture() {
        return TextureMappings.getTextureOf(this.baseBlock);
    }

    public Identifier getTopTexture() {
        return TextureMappings.getTopTextureOf(this.baseBlock);
    }

    public Identifier getBottomTexture() {
        return TextureMappings.getBottomTextureOf(this.baseBlock);
    }

    public Identifier getSideTexture() {
        return TextureMappings.getSideTextureOf(this.baseBlock);
    }

    /**
     * 构建方块，并按照构建时的设置进行一系列操作。
     *
     * @return 构建后的方块。
     */
    @Override
    public T build() {
        if (this.preparationConsumer != null) this.preparationConsumer.accept(this);
        if (this.instance == null) this.createInstance();
        if (this.registerBlock) this.register();
        if (this.addToDefaultTag) this.addToDefaultTag();
        this.tagList.forEach(this::addToTag);
        if (this.addToMapping) this.addToMapping();

        if (buildItem) {
            this.itemBuilder = new ExtShapeBlockItemBuilder(this.instance, itemSettings != null ? itemSettings :
                    new FabricItemSettings());
            itemBuilder.setIdentifier(identifier);
            if (group == null) itemBuilder.group();
            else itemBuilder.group(group);
            if (!registerItem) itemBuilder.noRegister();
            if (fireproof) itemBuilder.fireproof();
            this.itemBuilder.setIdentifier(this.getIdentifier()).build();
        }

        // 添加资源包
        final RuntimeResourcePack pack = ExtShape.EXTSHAPE_PACK;
        writeRecipe(pack);
        writeLootTable(pack);
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            final RuntimeResourcePack packClient = ExtShapeClient.EXTSHAPE_PACK_CLIENT;
            writeBlockModel(packClient);
            writeBlockStates(packClient);
            if (buildItem) writeItemModel(packClient);
        }

        return this.instance;
    }

    /**
     * 根据该方块的各类纹理，返回其简单的模型。
     */
    @Environment(EnvType.CLIENT)
    protected JModel simpleModel(String parent) {
        return JModel.model(parent).textures(
                new JTextures().var("bottom", getBottomTexture().toString())
                        .var("top", getTopTexture().toString())
                        .var("side", getSideTexture().toString())
        );
    }

    /**
     * 根据方块的基础纹理，返回其简单的模型，模型内使用名称为 texture 的变量。
     */
    @Environment(EnvType.CLIENT)
    protected JModel simpleTextureModel(String parent) {
        return JModel.model(parent).textures(new JTextures().var("texture", getBaseTexture().toString()));
    }

    /**
     * 返回一个简单的切石配方。
     */
    protected JStonecuttingRecipe simpleStoneCuttingRecipe(int count) {
        return JRecipe.stonecutting(JIngredient.ingredient().item(getBaseIdentifier().toString()), JResult.stackedResult(getIdentifier().toString(), count));
    }

    public boolean isStoneCut() {
        return baseBlock != null && ((AbstractBlockMixin) baseBlock).getMaterial() == Material.STONE;
    }


    /**
     * 返回该方块的方块模型。<br>
     * 若为 {@code null}，则表示没有模型。
     */
    @Environment(EnvType.CLIENT)
    public @Nullable JModel getBlockModel() {
        return null;
    }

    /**
     * 将方块模型注册到资源包中。通常直接使用 {@link #getBlockModel()} 的值，不过对于有多个方块模型或者没有方块模型的方块，你也可以让它注册多次或者不注册。
     */
    @Environment(EnvType.CLIENT)
    public void writeBlockModel(RuntimeResourcePack pack) {
        final JModel blockModel = getBlockModel();
        if (blockModel != null)
            pack.addModel(blockModel, blockIdentifier(getIdentifier()));
    }

    /**
     * 返回该方块的物品模型。通常情况下，该方块的物品模型直接继承方块模型。
     */
    @Environment(EnvType.CLIENT)
    public @Nullable JModel getItemModel() {
        final Identifier identifier = getIdentifier();
        return JModel.model(new Identifier(identifier.getNamespace(), "block/" + identifier.getPath()));
    }

    /**
     * 将物品模型注册到资源包中，通常直接使用 {@link #getItemModel()} 的值，不过对于有多个物品模型或者没有物品模型的方块，你也可以让它注册多次或者不注册。
     */
    @Environment(EnvType.CLIENT)
    public void writeItemModel(RuntimeResourcePack pack) {
        final JModel itemModel = getItemModel();
        if (itemModel != null)
            pack.addModel(itemModel, itemIdentifier(getIdentifier()));
    }

    /**
     * 返回该方块对应的方块状态定义。<br>
     * 若为 {@code null}，则表示没有方块状态定义。<br>
     */
    @Environment(EnvType.CLIENT)
    public @Nullable JState getBlockStates() {
        return JState.state(JState.variant(new JBlockModel(blockIdentifier(getIdentifier()))));
    }

    /**
     * 将方块的方块状态定义注册到资源包中，通常直接使用 {@link #getBlockStates()} 的值。通常一个方块只有一个方块状态定义文件。
     */
    @Environment(EnvType.CLIENT)
    public void writeBlockStates(RuntimeResourcePack pack) {
        final JState blockStates = getBlockStates();
        if (blockStates != null)
            pack.addBlockState(blockStates, getIdentifier());
    }

    /**
     * 该方块对应的战利品表。<br>
     * 若为 {@code null}，则表示没有战利品表。<br>
     * 默认的战利品表格式如下：
     * <pre>
     * {
     *   "type": "minecraft:block",
     *   "pools": [{
     *       "rolls": 1.0,
     *       "bonus_rolls": 0.0,
     *       "entries": [{
     *           "type": "minecraft:item",
     *           "name": "%s"
     *         }],
     *       "conditions": [{
     *           "condition": "minecraft:survives_explosion"
     *         }]}]}
     * </pre>
     */
    public @Nullable JLootTable getLootTable() {
        return new JLootTable("block").pool(new JPool()
                .rolls(1)
                .bonus(0)
                .entry(new JEntry().type("item").name(getIdentifier().toString()))
                .condition(new JCondition("survives_explosion")));
    }

    public void writeLootTable(RuntimeResourcePack pack) {
        final JLootTable lootTable = getLootTable();
        if (lootTable != null)
            pack.addLootTable(blocksIdentifier(getIdentifier()), lootTable);
    }

    /**
     * 该方块对应的合成配方。<br>若为 {@code null}，则表示没有配方。
     */
    public @Nullable JRecipe getCraftingRecipe() {
        return null;
    }

    /**
     * 该方块对应的切石配方。<br>
     * 若为 {@code null}，则表示没有配方。
     */
    public @Nullable JRecipe getStonecuttingRecipe() {
        return null;
    }


    /**
     * 该方块合成配方中对应的 {@code group} 参数。
     */
    public String getRecipeGroup() {
        return "";
    }

    /**
     * 将该方块的合成配方注册到资源包中。会同时注册合成和烧制的。
     *
     * @see #getCraftingRecipe()
     * @see #getStonecuttingRecipe()
     */
    public void writeRecipe(RuntimeResourcePack pack) {
        final JRecipe craftingRecipe = getCraftingRecipe();
        final Identifier identifier = getIdentifier();
        if (craftingRecipe != null)
            pack.addRecipe(identifier, craftingRecipe);
        if (isStoneCut()) {
            final JRecipe stonecuttingRecipe = getStonecuttingRecipe();
            if (stonecuttingRecipe != null)
                pack.addRecipe(new Identifier(identifier.getNamespace(), identifier.getPath() + "_from_stonecutting"), stonecuttingRecipe);
        }
    }
}
