package pers.solid.extshape.datagen;

import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.mappings.TextureMappings;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static net.minecraft.block.Blocks.*;
import static pers.solid.extshape.tag.ExtShapeBlockTag.*;

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
        return new Identifier(identifier.getNamespace(), "block/" + identifier.getPath() + suffix);
    }

    public List<Pair<Identifier, String>> getBlockModelCollection() {
        List<Pair<Identifier, String>> modelCollection = new ArrayList<>();
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier(), this.getBlockModelString()));
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
                """, this.getBlockModelIdentifier().toString());
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
        List<Pair<Identifier, String>> collection = this.getBlockModelCollection();
        for (Pair<Identifier, String> pair : collection) {
            Identifier identifier = pair.getLeft();
            String content = pair.getRight();
            this.writeModelFile(identifier.getNamespace(), identifier.getPath(), content);
        }
    }

    public void writeItemModelFile() {
        Identifier identifier = this.getItemModelIdentifier();
        this.writeModelFile(identifier.getNamespace(), identifier.getPath(),
                this.getItemModelString());
    }

    public void writeAllFiles() {
        this.writeAllFiles(true, true);
    }

    public void writeBlockStatesFile() {
        Identifier identifier = this.getIdentifier();
        this.writeBlockStatesFile(identifier.getNamespace(), identifier.getPath(),
                this.getBlockStatesString());
    }

    public void writeCraftingRecipeFile() {
        Identifier identifier = this.getIdentifier();
        this.writeRecipeFile(identifier.getNamespace(),
                identifier.getPath(), this.getCraftingRecipeString());
    }

    public void writeStoneCuttingRecipeFile() {
        Identifier identifier = this.getIdentifier();
        this.writeRecipeFile(identifier.getNamespace(), identifier.getPath() + "_from_stonecutting",
                this.getStoneCuttingRecipeString());
    }

    public void writeRecipeFiles() {
        this.writeCraftingRecipeFile();
        Block baseBlock = this.getBaseBlock();
        // 特定方块允许使用切石机合成。
        if (baseBlock == OBSIDIAN || baseBlock == CRYING_OBSIDIAN || STONES.contains(baseBlock) || CONCRETES.contains(baseBlock) || baseBlock == TERRACOTTA || STAINED_TERRACOTTAS.contains(baseBlock) || GLAZED_TERRACOTTAS.contains(baseBlock) || ORE_BLOCKS.contains(baseBlock) || SANDSTONES.contains(baseBlock) || baseBlock == PRISMARINE || baseBlock == DARK_PRISMARINE || baseBlock == PRISMARINE_BRICKS)
            this.writeStoneCuttingRecipeFile();
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
}
