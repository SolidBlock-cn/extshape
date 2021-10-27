package pers.solid.extshape.datagen;

import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class StairsGenerator extends AbstractBlockGenerator<StairsBlock> {
    protected StairsGenerator(Path path, StairsBlock block) {
        super(path, block);
    }

    @Override
    public String getBlockModelString() {
        return String.format("{\n" +
                "  \"parent\": \"minecraft:block/stairs\",\n" +
                "  \"textures\": {\n" +
                "    \"bottom\": \"%s\",\n" +
                "    \"top\": \"%s\",\n" +
                "    \"side\": \"%s\"\n" +
                "  }\n" +
                "}\n", this.getBottomTexture(), this.getTopTexture(), this.getSideTexture());
    }

    public String getInnerBlockModelString() {
        return String.format("{\n" +
                "  \"parent\": \"minecraft:block/inner_stairs\",\n" +
                "  \"textures\": {\n" +
                "    \"bottom\": \"%s\",\n" +
                "    \"top\": \"%s\",\n" +
                "    \"side\": \"%s\"\n" +
                "  }\n" +
                "}\n", this.getBottomTexture(), this.getTopTexture(), this.getSideTexture());
    }

    public String getOuterBlockModelString() {
        return String.format("{\n" +
                "  \"parent\": \"minecraft:block/outer_stairs\",\n" +
                "  \"textures\": {\n" +
                "    \"bottom\": \"%s\",\n" +
                "    \"top\": \"%s\",\n" +
                "    \"side\": \"%s\"\n" +
                "  }\n" +
                "}\n", this.getBottomTexture(), this.getTopTexture(), this.getSideTexture());
    }

    @Override
    public Map<Identifier, String> getBlockModelCollection() {
        Map<Identifier, String> modelCollection = new LinkedHashMap<>();
        modelCollection.put(this.getBlockModelIdentifier(), this.getBlockModelString());
        modelCollection.put(this.getBlockModelIdentifier("_inner"), this.getInnerBlockModelString());
        modelCollection.put(this.getBlockModelIdentifier("_outer"), this.getOuterBlockModelString());
        return modelCollection;
    }

    @Override
    public String getBlockStatesString() {
        Identifier identifier = this.getIdentifier();
        return String.format(
                "{\n" +
                        "  \"variants\": {\n" +
                        "    \"facing=east,half=bottom,shape=inner_left\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_inner\",\n" +
                        "      \"y\": 270,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=east,half=bottom,shape=inner_right\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_inner\"\n" +
                        "    },\n" +
                        "    \"facing=east,half=bottom,shape=outer_left\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_outer\",\n" +
                        "      \"y\": 270,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=east,half=bottom,shape=outer_right\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_outer\"\n" +
                        "    },\n" +
                        "    \"facing=east,half=bottom,shape=straight\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s\"\n" +
                        "    },\n" +
                        "    \"facing=east,half=top,shape=inner_left\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_inner\",\n" +
                        "      \"x\": 180,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=east,half=top,shape=inner_right\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_inner\",\n" +
                        "      \"x\": 180,\n" +
                        "      \"y\": 90,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=east,half=top,shape=outer_left\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_outer\",\n" +
                        "      \"x\": 180,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=east,half=top,shape=outer_right\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_outer\",\n" +
                        "      \"x\": 180,\n" +
                        "      \"y\": 90,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=east,half=top,shape=straight\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s\",\n" +
                        "      \"x\": 180,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=north,half=bottom,shape=inner_left\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_inner\",\n" +
                        "      \"y\": 180,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=north,half=bottom,shape=inner_right\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_inner\",\n" +
                        "      \"y\": 270,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=north,half=bottom,shape=outer_left\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_outer\",\n" +
                        "      \"y\": 180,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=north,half=bottom,shape=outer_right\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_outer\",\n" +
                        "      \"y\": 270,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=north,half=bottom,shape=straight\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s\",\n" +
                        "      \"y\": 270,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=north,half=top,shape=inner_left\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_inner\",\n" +
                        "      \"x\": 180,\n" +
                        "      \"y\": 270,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=north,half=top,shape=inner_right\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_inner\",\n" +
                        "      \"x\": 180,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=north,half=top,shape=outer_left\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_outer\",\n" +
                        "      \"x\": 180,\n" +
                        "      \"y\": 270,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=north,half=top,shape=outer_right\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_outer\",\n" +
                        "      \"x\": 180,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=north,half=top,shape=straight\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s\",\n" +
                        "      \"x\": 180,\n" +
                        "      \"y\": 270,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=south,half=bottom,shape=inner_left\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_inner\"\n" +
                        "    },\n" +
                        "    \"facing=south,half=bottom,shape=inner_right\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_inner\",\n" +
                        "      \"y\": 90,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=south,half=bottom,shape=outer_left\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_outer\"\n" +
                        "    },\n" +
                        "    \"facing=south,half=bottom,shape=outer_right\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_outer\",\n" +
                        "      \"y\": 90,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=south,half=bottom,shape=straight\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s\",\n" +
                        "      \"y\": 90,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=south,half=top,shape=inner_left\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_inner\",\n" +
                        "      \"x\": 180,\n" +
                        "      \"y\": 90,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=south,half=top,shape=inner_right\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_inner\",\n" +
                        "      \"x\": 180,\n" +
                        "      \"y\": 180,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=south,half=top,shape=outer_left\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_outer\",\n" +
                        "      \"x\": 180,\n" +
                        "      \"y\": 90,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=south,half=top,shape=outer_right\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_outer\",\n" +
                        "      \"x\": 180,\n" +
                        "      \"y\": 180,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=south,half=top,shape=straight\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s\",\n" +
                        "      \"x\": 180,\n" +
                        "      \"y\": 90,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=west,half=bottom,shape=inner_left\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_inner\",\n" +
                        "      \"y\": 90,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=west,half=bottom,shape=inner_right\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_inner\",\n" +
                        "      \"y\": 180,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=west,half=bottom,shape=outer_left\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_outer\",\n" +
                        "      \"y\": 90,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=west,half=bottom,shape=outer_right\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_outer\",\n" +
                        "      \"y\": 180,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=west,half=bottom,shape=straight\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s\",\n" +
                        "      \"y\": 180,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=west,half=top,shape=inner_left\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_inner\",\n" +
                        "      \"x\": 180,\n" +
                        "      \"y\": 180,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=west,half=top,shape=inner_right\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_inner\",\n" +
                        "      \"x\": 180,\n" +
                        "      \"y\": 270,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=west,half=top,shape=outer_left\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_outer\",\n" +
                        "      \"x\": 180,\n" +
                        "      \"y\": 180,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=west,half=top,shape=outer_right\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_outer\",\n" +
                        "      \"x\": 180,\n" +
                        "      \"y\": 270,\n" +
                        "      \"uvlock\": true\n" +
                        "    },\n" +
                        "    \"facing=west,half=top,shape=straight\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s\",\n" +
                        "      \"x\": 180,\n" +
                        "      \"y\": 180,\n" +
                        "      \"uvlock\": true\n" +
                        "    }\n" +
                        "  }\n" +
                        "}\n", identifier.getNamespace(), identifier.getPath());
    }

    @Override
    public String getCraftingRecipeString() {
        Identifier identifier = this.getIdentifier();
        Identifier baseIdentifier = Registry.BLOCK.getId(this.getBaseBlock());
        return String.format("{\n" +
                "  \"type\": \"minecraft:crafting_shaped\",\n" +
                "  \"group\": \"%3$s\",\n" +
                "  \"pattern\": [\n" +
                "    \"#  \",\n" +
                "    \"## \",\n" +
                "    \"###\"\n" +
                "  ],\n" +
                "  \"key\": {\n" +
                "    \"#\": {\n" +
                "      \"item\": \"%s\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"result\": {\n" +
                "    \"item\": \"%s\",\n" +
                "    \"count\": 4\n" +
                "  }\n" +
                "}", baseIdentifier, identifier.toString(), this.getRecipeGroup());
    }

    @Override
    public String getStoneCuttingRecipeString() {
        return String.format("{\n" +
                "  \"type\": \"minecraft:stonecutting\",\n" +
                "  \"ingredient\": {\n" +
                "    \"item\": \"%s\"\n" +
                "  },\n" +
                "  \"result\": \"%s\",\n" +
                "  \"count\": 1\n" +
                "}", this.getBaseBlockIdentifier(), this.getIdentifier());
    }

    @Override
    public String getRecipeGroup() {
        Block baseBlock = this.getBaseBlock();
        if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_stairs";
        if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_stairs";
        if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return "stained_terracotta_stairs";
        if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return "glazed_terracotta_stairs";
        return "";
    }
}
