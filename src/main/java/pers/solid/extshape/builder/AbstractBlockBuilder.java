package pers.solid.extshape.builder;

import com.google.common.collect.BiMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapeVariantBlockInterface;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBlockBuilder<T extends Block> implements Builder<T> {
    final Block baseBlock;
    final boolean addToDefaultTag;
    final boolean buildItem;
    private final List<ExtShapeBlockTag> tagList = new ArrayList<>();
    @Nullable
    protected ExtShapeBlockTag defaultTag = ExtShapeBlockTag.EXTSHAPE_BLOCKS;
    protected BiMap<Block, ? super T> mapping;
    protected boolean addToMapping;
    Identifier identifier;

    /**
     * 构造器的方块实例。需要注意，只有在调用{@link #setInstance(Block)}或{@link #build()}之后，这个实例才会存在，从而对实例进行实际操作。
     */
    T block;
    boolean registerBlock, registerItem;
    FabricBlockSettings blockSettings;
    ExtShapeBlockItemBuilder itemBuilder;
    @Nullable FabricItemSettings itemSettings;
    boolean fireproof;
    @Nullable ItemGroup group;

    protected AbstractBlockBuilder(Block baseBlock, FabricBlockSettings settings) {
        this.baseBlock = baseBlock;
        this.registerBlock = true;
        this.registerItem = true;
        this.addToDefaultTag = true;
        this.blockSettings = settings;
        this.buildItem = true;
        this.addToMapping = true;
        this.itemSettings = null;
    }

    protected AbstractBlockBuilder(Block baseBlock) {
        this(baseBlock, FabricBlockSettings.copyOf(baseBlock));
    }

    /**
     * 将方块注册到注册表，不影响其对应方块物品。需确保方块已构造。
     * 如需要在构建时，设置需要注册的命名空间id，应使用{@link #setInstance}。
     */
    public void register() {
        Registry.register(Registry.BLOCK, this.getIdentifier(), block);
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
    public Identifier getIdentifier() {
        if (identifier == null)
            return ExtShapeVariantBlockInterface.convertIdentifier(getBaseIdentifier(), this.getSuffix());
        else return identifier;
    }

    /**
     * 设置方块将要注册的命名空间id。
     *
     * @param identifier 方块将要注册的命名空间id。
     */
    public AbstractBlockBuilder<T> setIdentifier(Identifier identifier) {
        this.identifier = identifier;
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
     * 设置方块亮度。
     *
     * @param luminance 亮度。
     */
    public AbstractBlockBuilder<T> luminance(int luminance) {
        this.blockSettings.luminance(ignored -> luminance);
        return this;
    }

    /**
     * 添加到某个方块标签中。不影响默认标签。
     *
     * @param tag 需要添加到的方块标签。
     */
    protected void addToTag(@Nullable ExtShapeBlockTag tag) {
        if (tag != null) tag.add(this.block);
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
            mapping.put(baseBlock, block);
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
    public AbstractBlockBuilder<T> setInstance(T instance) {
        this.block = instance;
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

    /**
     * 构建方块，并按照构建时的设置进行一系列操作。
     *
     * @return 构建后的方块。
     */
    @Override
    public T build() {
        if (this.block == null) this.createInstance();
        if (this.registerBlock) this.register();
        if (this.addToDefaultTag) this.addToDefaultTag();
        this.tagList.forEach(this::addToTag);
        if (this.addToMapping) this.addToMapping();

        if (buildItem) {
            this.itemBuilder = new ExtShapeBlockItemBuilder(this.block, itemSettings != null ? itemSettings :
                    new FabricItemSettings());
            itemBuilder.setIdentifier(identifier);
            if (group == null) itemBuilder.group();
            else itemBuilder.group(group);
            if (!registerItem) itemBuilder.noRegister();
            if (fireproof) itemBuilder.fireproof();
            this.itemBuilder.setIdentifier(this.getIdentifier()).build();
        }
        return this.block;
    }
}