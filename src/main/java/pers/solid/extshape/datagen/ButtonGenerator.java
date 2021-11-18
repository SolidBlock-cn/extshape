package pers.solid.extshape.datagen;

import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ButtonGenerator extends AbstractBlockGenerator<AbstractButtonBlock> {
    /**
     * 该集合内的方块将不会生成按钮配方。
     */
    private static final Collection<Block> REFUSE_RECIPES = new ExtShapeBlockTag(Blocks.EMERALD_BLOCK, Blocks.IRON_BLOCK, Blocks.GOLD_BLOCK, Blocks.DIAMOND_BLOCK, Blocks.LAPIS_BLOCK);

    protected ButtonGenerator(Path path, @NotNull AbstractButtonBlock block) {
        super(path, block);
    }


    public String getModelString() {
        return String.format("{\n  \"parent\": \"minecraft:block/button\",\n  \"textures\": {\n    \"texture\": \"%s\"\n  }\n}", this.getBaseTexture());
    }

    public String getInventoryModelString() {
        return String.format("  {\n  \"parent\": \"minecraft:block/button_inventory\",\n  \"textures\": {\n    \"texture\": \"%s\"\n  }\n}", this.getBaseTexture());
    }

    public String getPressedModelString() {
        return String.format("{\n  \"parent\": \"minecraft:block/button_pressed\",\n  \"textures\": {\n    \"texture\": \"%s\"\n  }\n}", this.getBaseTexture());
    }


    public Map<Identifier, String> getBlockModelCollection() {
        Map<Identifier, String> modelCollection = new LinkedHashMap<>();
        modelCollection.put(this.getBlockModelIdentifier(), this.getModelString());
        modelCollection.put(this.getBlockModelIdentifier("_inventory"), this.getInventoryModelString());
        modelCollection.put(this.getBlockModelIdentifier("_pressed"), this.getPressedModelString());
        return modelCollection;
    }

    @Override
    public String getBlockStatesString() {
        Identifier identifier = this.getIdentifier();
        return String.format("{\n" +
                "                  \"variants\": {\n" +
                "                    \"face=ceiling,facing=east,powered=false\": {\n" +
                "                      \"model\": \"%1$s:block/%2$s\",\n" +
                "                      \"y\": 270,\n" +
                "                      \"x\": 180\n" +
                "                    },\n" +
                "                    \"face=ceiling,facing=east,powered=true\": {\n" +
                "                      \"model\": \"%1$s:block/%2$s_pressed\",\n" +
                "                      \"y\": 270,\n" +
                "                      \"x\": 180\n" +
                "                    },\n" +
                "                    \"face=ceiling,facing=north,powered=false\": {\n" +
                "                      \"model\": \"%1$s:block/%2$s\",\n" +
                "                      \"y\": 180,\n" +
                "                      \"x\": 180\n" +
                "                    },\n" +
                "                    \"face=ceiling,facing=north,powered=true\": {\n" +
                "                      \"model\": \"%1$s:block/%2$s_pressed\",\n" +
                "                      \"y\": 180,\n" +
                "                      \"x\": 180\n" +
                "                    },\n" +
                "                    \"face=ceiling,facing=south,powered=false\": {\n" +
                "                      \"model\": \"%1$s:block/%2$s\",\n" +
                "                      \"x\": 180\n" +
                "                    },\n" +
                "                    \"face=ceiling,facing=south,powered=true\": {\n" +
                "                      \"model\": \"%1$s:block/%2$s_pressed\",\n" +
                "                      \"x\": 180\n" +
                "                    },\n" +
                "                    \"face=ceiling,facing=west,powered=false\": {\n" +
                "                      \"model\": \"%1$s:block/%2$s\",\n" +
                "                      \"y\": 90,\n" +
                "                      \"x\": 180\n" +
                "                    },\n" +
                "                    \"face=ceiling,facing=west,powered=true\": {\n" +
                "                      \"model\": \"%1$s:block/%2$s_pressed\",\n" +
                "                      \"y\": 90,\n" +
                "                      \"x\": 180\n" +
                "                    },\n" +
                "                    \"face=floor,facing=east,powered=false\": {\n" +
                "                      \"model\": \"%1$s:block/%2$s\",\n" +
                "                      \"y\": 90\n" +
                "                    },\n" +
                "                    \"face=floor,facing=east,powered=true\": {\n" +
                "                      \"model\": \"%1$s:block/%2$s_pressed\",\n" +
                "                      \"y\": 90\n" +
                "                    },\n" +
                "                    \"face=floor,facing=north,powered=false\": {\n" +
                "                      \"model\": \"%1$s:block/%2$s\"\n" +
                "                    },\n" +
                "                    \"face=floor,facing=north,powered=true\": {\n" +
                "                      \"model\": \"%1$s:block/%2$s_pressed\"\n" +
                "                    },\n" +
                "                    \"face=floor,facing=south,powered=false\": {\n" +
                "                      \"model\": \"%1$s:block/%2$s\",\n" +
                "                      \"y\": 180\n" +
                "                    },\n" +
                "                    \"face=floor,facing=south,powered=true\": {\n" +
                "                      \"model\": \"%1$s:block/%2$s_pressed\",\n" +
                "                      \"y\": 180\n" +
                "                    },\n" +
                "                    \"face=floor,facing=west,powered=false\": {\n" +
                "                      \"model\": \"%1$s:block/%2$s\",\n" +
                "                      \"y\": 270\n" +
                "                    },\n" +
                "                    \"face=floor,facing=west,powered=true\": {\n" +
                "                      \"model\": \"%1$s:block/%2$s_pressed\",\n" +
                "                      \"y\": 270\n" +
                "                    },\n" +
                "                    \"face=wall,facing=east,powered=false\": {\n" +
                "                      \"model\": \"%1$s:block/%2$s\",\n" +
                "                      \"y\": 90,\n" +
                "                      \"x\": 90,\n" +
                "                      \"uvlock\": true\n" +
                "                    },\n" +
                "                    \"face=wall,facing=east,powered=true\": {\n" +
                "                      \"model\": \"%1$s:block/%2$s_pressed\",\n" +
                "                      \"y\": 90,\n" +
                "                      \"x\": 90,\n" +
                "                      \"uvlock\": true\n" +
                "                    },\n" +
                "                    \"face=wall,facing=north,powered=false\": {\n" +
                "                      \"model\": \"%1$s:block/%2$s\",\n" +
                "                      \"x\": 90,\n" +
                "                      \"uvlock\": true\n" +
                "                    },\n" +
                "                    \"face=wall,facing=north,powered=true\": {\n" +
                "                      \"model\": \"%1$s:block/%2$s_pressed\",\n" +
                "                      \"x\": 90,\n" +
                "                      \"uvlock\": true\n" +
                "                    },\n" +
                "                    \"face=wall,facing=south,powered=false\": {\n" +
                "                      \"model\": \"%1$s:block/%2$s\",\n" +
                "                      \"y\": 180,\n" +
                "                      \"x\": 90,\n" +
                "                      \"uvlock\": true\n" +
                "                    },\n" +
                "                    \"face=wall,facing=south,powered=true\": {\n" +
                "                      \"model\": \"%1$s:block/%2$s_pressed\",\n" +
                "                      \"y\": 180,\n" +
                "                      \"x\": 90,\n" +
                "                      \"uvlock\": true\n" +
                "                    },\n" +
                "                    \"face=wall,facing=west,powered=false\": {\n" +
                "                      \"model\": \"%1$s:block/%2$s\",\n" +
                "                      \"y\": 270,\n" +
                "                      \"x\": 90,\n" +
                "                      \"uvlock\": true\n" +
                "                    },\n" +
                "                    \"face=wall,facing=west,powered=true\": {\n" +
                "                      \"model\": \"%1$s:block/%2$s_pressed\",\n" +
                "                      \"y\": 270,\n" +
                "                      \"x\": 90,\n" +
                "                      \"uvlock\": true\n" +
                "                    }\n" +
                "                  }\n" +
                "                }", identifier.getNamespace(), identifier.getPath());
    }

    @Override
    public String getItemModelString() {
        return String.format("{\n  \"parent\": \"%s_inventory\"\n}", this.getBlockModelIdentifier().toString());
    }

    @Override
    public String getCraftingRecipeString() {
        if (REFUSE_RECIPES.contains(this.getBaseBlock())) {
            return null;
        }
        return String.format("{\n" +
                "                  \"type\": \"minecraft:crafting_shapeless\",\n" +
                "                  \"group\": \"%s\",\n" +
                "                  \"ingredients\": [\n" +
                "                    {\n" +
                "                      \"item\": \"%s\"\n" +
                "                    }\n" +
                "                  ],\n" +
                "                  \"result\": {\n" +
                "                    \"item\": \"%s\"\n" +
                "                  }\n" +
                "                }", this.getRecipeGroup(), this.getBaseBlockIdentifier(), this.getIdentifier().toString());
    }

    @Override
    public String getRecipeGroup() {
        Block baseBlock = this.getBaseBlock();
        if (ExtShapeBlockTag.WOOLS.contains(baseBlock)) return "wool_button";
        if (ExtShapeBlockTag.CONCRETES.contains(baseBlock)) return "concrete_button";
        if (ExtShapeBlockTag.STAINED_TERRACOTTAS.contains(baseBlock)) return "stained_terracotta_button";
        if (ExtShapeBlockTag.GLAZED_TERRACOTTAS.contains(baseBlock)) return "glazed_terracotta_button";
        if (ExtShapeBlockTag.PLANKS.contains(baseBlock)) return "wooden_button";
        return "";
    }
}
