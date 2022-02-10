package pers.solid.extshape.datagen;

import net.minecraft.block.Block;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.mappings.IngredientMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

@Deprecated
public class FenceGateGenerator extends AbstractBlockGenerator<FenceGateBlock> {
    protected FenceGateGenerator(Path path, @NotNull FenceGateBlock block) {
        super(path, block);
    }


    public Item getCraftingIngredient() {
        return IngredientMappings.MAPPING_OF_FENCE_GATE_INGREDIENTS.get(this.getBlock());
    }

    public Identifier getCraftingIngredientIdentifier() {
        return Registry.ITEM.getId(this.getCraftingIngredient());
    }


    @Override
    public String getBlockStatesString() {
        Identifier identifier = this.getIdentifier();
        return String.format(
                "{\n" +
                        "  \"variants\": {\n" +
                        "    \"facing=east,in_wall=false,open=false\": {\n" +
                        "      \"uvlock\": true,\n" +
                        "      \"y\": 270,\n" +
                        "      \"model\": \"%1$s:block/%2$s\"\n" +
                        "    },\n" +
                        "    \"facing=east,in_wall=false,open=true\": {\n" +
                        "      \"uvlock\": true,\n" +
                        "      \"y\": 270,\n" +
                        "      \"model\": \"%1$s:block/%2$s_open\"\n" +
                        "    },\n" +
                        "    \"facing=east,in_wall=true,open=false\": {\n" +
                        "      \"uvlock\": true,\n" +
                        "      \"y\": 270,\n" +
                        "      \"model\": \"%1$s:block/%2$s_wall\"\n" +
                        "    },\n" +
                        "    \"facing=east,in_wall=true,open=true\": {\n" +
                        "      \"uvlock\": true,\n" +
                        "      \"y\": 270,\n" +
                        "      \"model\": \"%1$s:block/%2$s_wall_open\"\n" +
                        "    },\n" +
                        "    \"facing=north,in_wall=false,open=false\": {\n" +
                        "      \"uvlock\": true,\n" +
                        "      \"y\": 180,\n" +
                        "      \"model\": \"%1$s:block/%2$s\"\n" +
                        "    },\n" +
                        "    \"facing=north,in_wall=false,open=true\": {\n" +
                        "      \"uvlock\": true,\n" +
                        "      \"y\": 180,\n" +
                        "      \"model\": \"%1$s:block/%2$s_open\"\n" +
                        "    },\n" +
                        "    \"facing=north,in_wall=true,open=false\": {\n" +
                        "      \"uvlock\": true,\n" +
                        "      \"y\": 180,\n" +
                        "      \"model\": \"%1$s:block/%2$s_wall\"\n" +
                        "    },\n" +
                        "    \"facing=north,in_wall=true,open=true\": {\n" +
                        "      \"uvlock\": true,\n" +
                        "      \"y\": 180,\n" +
                        "      \"model\": \"%1$s:block/%2$s_wall_open\"\n" +
                        "    },\n" +
                        "    \"facing=south,in_wall=false,open=false\": {\n" +
                        "      \"uvlock\": true,\n" +
                        "      \"model\": \"%1$s:block/%2$s\"\n" +
                        "    },\n" +
                        "    \"facing=south,in_wall=false,open=true\": {\n" +
                        "      \"uvlock\": true,\n" +
                        "      \"model\": \"%1$s:block/%2$s_open\"\n" +
                        "    },\n" +
                        "    \"facing=south,in_wall=true,open=false\": {\n" +
                        "      \"uvlock\": true,\n" +
                        "      \"model\": \"%1$s:block/%2$s_wall\"\n" +
                        "    },\n" +
                        "    \"facing=south,in_wall=true,open=true\": {\n" +
                        "      \"uvlock\": true,\n" +
                        "      \"model\": \"%1$s:block/%2$s_wall_open\"\n" +
                        "    },\n" +
                        "    \"facing=west,in_wall=false,open=false\": {\n" +
                        "      \"uvlock\": true,\n" +
                        "      \"y\": 90,\n" +
                        "      \"model\": \"%1$s:block/%2$s\"\n" +
                        "    },\n" +
                        "    \"facing=west,in_wall=false,open=true\": {\n" +
                        "      \"uvlock\": true,\n" +
                        "      \"y\": 90,\n" +
                        "      \"model\": \"%1$s:block/%2$s_open\"\n" +
                        "    },\n" +
                        "    \"facing=west,in_wall=true,open=false\": {\n" +
                        "      \"uvlock\": true,\n" +
                        "      \"y\": 90,\n" +
                        "      \"model\": \"%1$s:block/%2$s_wall\"\n" +
                        "    },\n" +
                        "    \"facing=west,in_wall=true,open=true\": {\n" +
                        "      \"uvlock\": true,\n" +
                        "      \"y\": 90,\n" +
                        "      \"model\": \"%1$s:block/%2$s_wall_open\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}", identifier.getNamespace(), identifier.getPath());
    }

    @Override
    public String getBlockModelString() {
        return String.format("{\n" +
                "  \"parent\": \"minecraft:block/template_fence_gate\",\n" +
                "  \"textures\": {\n" +
                "    \"texture\": \"%1$s\"\n" +
                "  }\n" +
                "}", this.getBaseTexture());
    }

    public String getOpenBlockModelString() {
        return String.format("{\n" +
                "  \"parent\": \"minecraft:block/template_fence_gate_open\",\n" +
                "  \"textures\": {\n" +
                "    \"texture\": \"%1$s\"\n" +
                "  }\n" +
                "}", this.getBaseTexture());
    }

    public String getWallBlockModelString() {
        return String.format("{\n" +
                "  \"parent\": \"minecraft:block/template_fence_gate_wall\",\n" +
                "  \"textures\": {\n" +
                "    \"texture\": \"%1$s\"\n" +
                "  }\n" +
                "}\n", this.getBaseTexture());
    }

    public String getWallOpenBlockModelString() {
        return String.format("{\n" +
                "  \"parent\": \"minecraft:block/template_fence_gate_wall_open\",\n" +
                "  \"textures\": {\n" +
                "    \"texture\": \"%1$s\"\n" +
                "  }\n" +
                "}\n", this.getBaseTexture());
    }

    @Override
    public Map<Identifier, String> getBlockModelCollection() {
        Map<Identifier, String> modelCollection = new LinkedHashMap<>();
        modelCollection.put(this.getBlockModelIdentifier(), this.getBlockModelString());
        modelCollection.put(this.getBlockModelIdentifier("_open"), this.getOpenBlockModelString());
        modelCollection.put(this.getBlockModelIdentifier("_wall"), this.getWallBlockModelString());
        modelCollection.put(this.getBlockModelIdentifier("_wall_open"), this.getWallOpenBlockModelString());
        return modelCollection;
    }

    @Override
    public String getCraftingRecipeString() {
        return String.format("{\n" +
                        "    \"type\": \"minecraft:crafting_shaped\",\n" +
                        "    \"group\": \"%s\",\n" +
                        "    \"pattern\": [\n" +
                        "      \"#W#\",\n" +
                        "      \"#W#\"\n" +
                        "    ],\n" +
                        "    \"key\": {\n" +
                        "      \"#\": {\n" +
                        "        \"item\": \"%s\"\n" +
                        "      },\n" +
                        "      \"W\": {\n" +
                        "        \"item\": \"%s\"\n" +
                        "      }\n" +
                        "    },\n" +
                        "    \"result\": {\n" +
                        "      \"item\": \"%s\"\n" +
                        "    }\n" +
                        "  }", this.getRecipeGroup(), this.getCraftingIngredientIdentifier(),
                this.getBaseBlockIdentifier(),
                this.getIdentifier());
    }

    @Override
    public String getRecipeGroup() {
        Block baseBlock = this.getBaseBlock();
        if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_fence_gate";
        if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_fence_gate";
        if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return
                "stained_terracotta_fence_gate";
        if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return
                "glazed_terracotta_fence_gate";
        return "";
    }
}
