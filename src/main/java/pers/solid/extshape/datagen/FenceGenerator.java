package pers.solid.extshape.datagen;

import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;
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
public class FenceGenerator extends AbstractBlockGenerator<FenceBlock> {
    protected FenceGenerator(Path path, @NotNull FenceBlock block) {
        super(path, block);
    }


    @Override
    public String getBlockStatesString() {
        Identifier identifier = this.getIdentifier();
        return String.format("{\n" +
                "  \"multipart\": [\n" +
                "    {\n" +
                "      \"apply\": {\n" +
                "        \"model\": \"%1$s:block/%2$s_post\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"when\": {\n" +
                "        \"north\": \"true\"\n" +
                "      },\n" +
                "      \"apply\": {\n" +
                "        \"model\": \"%1$s:block/%2$s_side\",\n" +
                "        \"uvlock\": true\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"when\": {\n" +
                "        \"east\": \"true\"\n" +
                "      },\n" +
                "      \"apply\": {\n" +
                "        \"model\": \"%1$s:block/%2$s_side\",\n" +
                "        \"y\": 90,\n" +
                "        \"uvlock\": true\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"when\": {\n" +
                "        \"south\": \"true\"\n" +
                "      },\n" +
                "      \"apply\": {\n" +
                "        \"model\": \"%1$s:block/%2$s_side\",\n" +
                "        \"y\": 180,\n" +
                "        \"uvlock\": true\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"when\": {\n" +
                "        \"west\": \"true\"\n" +
                "      },\n" +
                "      \"apply\": {\n" +
                "        \"model\": \"%1$s:block/%2$s_side\",\n" +
                "        \"y\": 270,\n" +
                "        \"uvlock\": true\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}", identifier.getNamespace(), identifier.getPath());
    }

    public String getInventoryBlockModelString() {
        return String.format("{\n" +
                "  \"parent\": \"minecraft:block/fence_inventory\",\n" +
                "  \"textures\": {\n" +
                "    \"texture\": \"%1$s\"\n" +
                "  }\n" +
                "}", this.getBaseTexture());
    }

    public String getSideBlockModelString() {
        return String.format("{\n" +
                "  \"parent\": \"minecraft:block/fence_side\",\n" +
                "  \"textures\": {\n" +
                "    \"texture\": \"%1$s\"\n" +
                "  }\n" +
                "}", this.getBaseTexture());
    }

    public String getPostBlockModelString() {
        return String.format("{\n" +
                "  \"parent\": \"minecraft:block/fence_post\",\n" +
                "  \"textures\": {\n" +
                "    \"texture\": \"%1$s\"\n" +
                "  }\n" +
                "}\n", this.getBaseTexture());
    }

    @Override
    public Map<Identifier, String> getBlockModelCollection() {
        Map<Identifier, String> modelCollection = new LinkedHashMap<>();
        modelCollection.put(this.getBlockModelIdentifier("_inventory"), this.getInventoryBlockModelString());
        modelCollection.put(this.getBlockModelIdentifier("_side"), this.getSideBlockModelString());
        modelCollection.put(this.getBlockModelIdentifier("_post"), this.getPostBlockModelString());
        return modelCollection;
    }

    @Override
    public String getItemModelString() {
        Identifier identifier = this.getIdentifier();
        return String.format("{\n  \"parent\": \"%1$s:block/%2$s_inventory\"\n}", identifier.getNamespace(), identifier.getPath());
    }


    @Override
    public String getCraftingRecipeString() {
        return String.format("{\n" +
                        "  \"type\": \"minecraft:crafting_shaped\",\n" +
                        "  \"group\": \"%s\",\n" +
                        "  \"pattern\": [\n" +
                        "    \"W#W\",\n" +
                        "    \"W#W\"\n" +
                        "  ],\n" +
                        "  \"key\": {\n" +
                        "    \"W\": {\n" +
                        "      \"item\": \"%s\"\n" +
                        "    },\n" +
                        "    \"#\": {\n" +
                        "      \"item\": \"%s\"\n" +
                        "    }\n" +
                        "  },\n" +
                        "  \"result\": {\n" +
                        "    \"item\": \"%s\",\n" +
                        "    \"count\": 3\n" +
                        "  }\n" +
                        "}", this.getRecipeGroup(), this.getBaseBlockIdentifier(),
                this.getCraftingIngredientIdentifier(),
                this.getIdentifier());
    }

    @Override
    public String getRecipeGroup() {
        Block baseBlock = this.getBaseBlock();
        if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_fence";
        if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_fence";
        if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return "stained_terracotta_fence";
        if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return "glazed_terracotta_fence";
        return "";
    }


    public Item getCraftingIngredient() {
        return IngredientMappings.MAPPING_OF_FENCE_INGREDIENTS.get(this.getBlock());
    }

    public Identifier getCraftingIngredientIdentifier() {
        return Registry.ITEM.getId(this.getCraftingIngredient());
    }
}
