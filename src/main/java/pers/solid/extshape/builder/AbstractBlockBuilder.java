package pers.solid.extshape.builder;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.block.ExtShapeVariantBlockInterface;
import pers.solid.extshape.mappings.BlockMapping;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBlockBuilder<T extends Block> implements Builder<T> {
    static String suffix;
    private final List<ExtShapeBlockTag> tagList = new ArrayList<>();
    @Nullable
    protected ExtShapeBlockTag defaultTag = ExtShapeBlockTag.EXTSHAPE_BLOCKS;
    protected BlockMapping<? super T> mapping;
    protected boolean addToMapping;
    Block baseBlock;
    Identifier identifier;
    T block;
    boolean registerBlock, registerItem;
    FabricBlockSettings blockSettings;
    ExtShapeBlockItemBuilder itemBuilder;
    @Nullable FabricItemSettings itemSettings;
    boolean addToDefaultTag;
    boolean buildItem;
    boolean fireproof;

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

    public AbstractBlockBuilder<T> register() {
        Registry.register(Registry.BLOCK, this.getIdentifier(), block);
        return this;
    }

    public AbstractBlockBuilder<T> setBlockSettings(FabricBlockSettings settings) {
        this.blockSettings = settings;
        return this;
    }

    public AbstractBlockBuilder<T> setItemSettings(FabricItemSettings settings) {
        this.itemSettings = settings;
        return this;
    }

    public AbstractBlockBuilder<T> fireproof() {
        this.fireproof = true;
        return this;
    }

    public AbstractBlockBuilder<T> fireproofIf(boolean condition) {
        if (condition) return this.fireproof();
        return this;
    }

    protected Identifier getBaseIdentifier() {
        return Registry.BLOCK.getId(baseBlock);
    }

    public Identifier getIdentifier() {
        if (identifier == null)
            return ExtShapeVariantBlockInterface.convertIdentifier(getBaseIdentifier(), this.getSuffix());
        else return identifier;
    }

    public AbstractBlockBuilder<T> setIdentifier(Identifier identifier) {
        this.identifier = identifier;
        return this;
    }

    protected abstract String getSuffix();

    protected @Nullable ExtShapeBlockTag getDefaultTag() {
        return this.defaultTag;
    }

    public AbstractBlockBuilder<T> setDefaultTag(ExtShapeBlockTag tag) {
        this.defaultTag = tag;
        return this;
    }

    public AbstractBlockBuilder<T> noRegisterBlock() {
        this.registerBlock = false;
        return this;
    }

    @Override
    public AbstractBlockBuilder<T> noRegister() {
        this.registerItem = false;
        return this.noRegisterBlock();
    }

    public AbstractBlockBuilder<T> luminance(int luminance) {
        this.blockSettings.luminance(ignored -> luminance);
        return this;
    }

    protected void addToTag(@Nullable ExtShapeBlockTag tag) {
        if (tag != null) tag.add(this.block);
    }

    protected void addToDefaultTag() {
        this.addToTag(this.defaultTag);
    }

    protected void addToMapping() {
        if (mapping != null)
            mapping.put(baseBlock, block);
    }

    public AbstractBlockBuilder<T> noAddToMapping() {
        this.addToMapping = false;
        return this;
    }

    public AbstractBlockBuilder<T> putTag(ExtShapeBlockTag tag) {
        this.tagList.add(tag);
        return this;
    }

    public AbstractBlockBuilder<T> setInstance(T instance) {
        this.block = instance;
        return this;
    }

    @Override
    public T build() {
        if (this.block == null) this.createInstance();
        if (this.registerBlock) this.register();
        if (this.addToDefaultTag) this.addToDefaultTag();
        this.tagList.forEach(this::addToTag);
        if (this.addToMapping) this.addToMapping();

        this.itemBuilder = new ExtShapeBlockItemBuilder(this.block, itemSettings != null ? itemSettings :
                new FabricItemSettings());
        itemBuilder.setIdentifier(identifier);
        if (!registerItem) itemBuilder.noRegister();
        if (fireproof) itemBuilder.fireproof();
        this.itemBuilder.setIdentifier(this.getIdentifier()).build();
        return this.block;
    }
}
