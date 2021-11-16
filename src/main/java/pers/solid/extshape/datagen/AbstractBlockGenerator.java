package pers.solid.extshape.datagen;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.mappings.TextureMappings;
import pers.solid.extshape.mixin.AbstractBlockMixin;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractBlockGenerator<T extends Block> extends Generator {

    private final T block;
    private final @Nullable Block baseBlock;

    protected AbstractBlockGenerator(Path path, @NotNull T block) {
        super(path);
        this.block = block;
        this.baseBlock = BlockMappings.getBaseBlockOf(block);
    }

    public T getBlock() {
        return this.block;
    }

    public @Nullable Block getBaseBlock() {
        return this.baseBlock;
    }

    public @Nullable Identifier getBaseBlockIdentifier() {
        // 由于涂蜡的铜块的材质与未涂蜡的一致，在原版 blockstates 里面直接使用对应的未涂蜡的铜的模型，故使用。
        if (this.baseBlock == null) return null;
        return Registry.BLOCK.getId(this.baseBlock);
    }

    public Identifier getIdentifier() {
        return Registry.BLOCK.getId(this.block);
    }

    public Identifier getBlockModelIdentifier() {
        Identifier identifier = this.getIdentifier();
        return new Identifier(identifier.getNamespace(), "block/" + identifier.getPath());
    }

    public Identifier getBlockModelIdentifier(String suffix) {
        Identifier identifier = this.getIdentifier();
        return new Identifier(identifier.getNamespace(), "block/" + identifier.getPath().replaceFirst("^waxed_", "") + suffix);
    }

    public Map<Identifier, String> getBlockModelCollection() {
        Map<Identifier, String> modelCollection = new LinkedHashMap<>();
        final String blockModelString = this.getBlockModelString();
        modelCollection.put(this.getBlockModelIdentifier(), blockModelString);
        return modelCollection;
    }

    public Identifier getItemModelIdentifier() {
        Identifier identifier = this.getIdentifier();
        return new Identifier(identifier.getNamespace(), "item/" + identifier.getPath());
    }

    @Nullable
    public String getBlockModelString() {
        return """
                {
                    "parent": "block/block"
                }
                """;
    }

    @Nullable
    public String getItemModelString() {
        return String.format("""
                {
                    "parent": "%s"
                }
                """, this.getBlockModelIdentifier().toString().replaceFirst("/waxed_", "/"));
    }

    @Nullable
    public String getBlockStatesString() {
        return String.format("""
                {
                    "variants": {
                        "": {"model": "%s"}
                    }
                }
                """, this.getBlockModelIdentifier().toString());
    }

    @Nullable
    public String getCraftingRecipeString() {
        return null;
    }

    @Nullable
    public String getStoneCuttingRecipeString() {
        return null;
    }

    @Nullable
    public String getLootTableString() {
        Identifier identifier = this.getIdentifier();
        return String.format("""
                {
                  "type": "minecraft:block",
                  "pools": [
                    {
                      "rolls": 1.0,
                      "bonus_rolls": 0.0,
                      "entries": [
                        {
                          "type": "minecraft:item",
                          "name": "%s"
                        }
                      ],
                      "conditions": [
                        {
                          "condition": "minecraft:survives_explosion"
                        }
                      ]
                    }
                  ]
                }""", identifier.toString());
    }

    public String getRecipeGroup() {
        // 合成配方中的group参数。
        return "";
    }

    public void writeBlockModelFiles() {
        Map<Identifier, String> collection = this.getBlockModelCollection();
        for (var entry : collection.entrySet()) {
            Identifier identifier = entry.getKey();
            String content = entry.getValue();
            this.writeModelFile(identifier.getNamespace(), identifier.getPath().replaceFirst("/waxed_", "/"), content != null ? content.replaceAll("/waxed_", "/") : content);
        }
    }

    public void writeItemModelFile() {
        Identifier identifier = this.getItemModelIdentifier();
        this.writeModelFile(identifier.getNamespace(), identifier.getPath(), this.getItemModelString());
    }

    public void writeAllFiles() {
        this.writeAllFiles(true, true);
    }

    public void writeBlockStatesFile() {
        Identifier identifier = this.getIdentifier();
        String blockStatesString = this.getBlockStatesString();
        if (blockStatesString != null) blockStatesString = blockStatesString.replaceAll(":block/waxed_", ":block/");
        this.writeBlockStatesFile(identifier.getNamespace(), identifier.getPath(), blockStatesString);
    }

    public void writeCraftingRecipeFile() {
        Identifier identifier = this.getCraftingRecipeIdentifier();
        this.writeRecipeFile(identifier.getNamespace(),
                identifier.getPath(), this.getCraftingRecipeString());
    }

    public void writeStoneCuttingRecipeFile() {
        Identifier identifier = this.getIdentifier();
        this.writeRecipeFile(identifier.getNamespace(), identifier.getPath() + "_from_stonecutting",
                this.getStoneCuttingRecipeString());
    }

    public void writeStoneCuttingRecipeFiles() {
        this.writeStoneCuttingRecipeFile();
    }

    public void writeRecipeFiles() {
        this.writeCraftingRecipeFiles();
        Block baseBlock = this.getBaseBlock();
        // 特定方块允许使用切石机合成。
        if (baseBlock != null && ((AbstractBlockMixin) baseBlock).getMaterial() == Material.STONE) this.writeStoneCuttingRecipeFiles();
    }

    public void writeCraftingRecipeFiles() {
        this.writeCraftingRecipeFile();
    }

    public void writeLootTableFile() {
        this.writeLootTableFile(this.getIdentifier().getNamespace(),
                this.getIdentifier().getPath(),
                this.getLootTableString());
    }

    public void writeAllFiles(boolean assets, boolean data) {
        // 同时写好方块状态、方块模型和物品模型，以及合成配方、战利品表
        if (assets) {
            this.writeBlockStatesFile();
            this.writeBlockModelFiles();
            this.writeItemModelFile();
        }
        if (data) {
            this.writeLootTableFile();
            this.writeRecipeFiles();
        }
    }

    public Identifier getBaseTexture() {
        return TextureMappings.getTextureOf(this.getBaseBlock());
    }

    public Identifier getTopTexture() {
        return TextureMappings.getTopTextureOf(this.getBaseBlock());
    }

    public Identifier getBottomTexture() {
        return TextureMappings.getBottomTextureOf(this.getBaseBlock());
    }

    public Identifier getSideTexture() {
        return TextureMappings.getSideTextureOf(this.getBaseBlock());
    }

    public Identifier getCraftingRecipeIdentifier() {
        return this.getIdentifier();
    }
}
