package pers.solid.extshape.datagen;

import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class WallGenerator extends AbstractBlockGenerator<WallBlock> {
    protected WallGenerator(Path path, @NotNull WallBlock block) {
        super(path, block);
    }

    public String getInventoryModelString() {
        return String.format("{\n" +
                "  \"parent\": \"minecraft:block/wall_inventory\",\n" +
                "  \"textures\": {\n" +
                "    \"wall\": \"%s\"\n" +
                "  }\n" +
                "}", this.getBaseTexture());
    }

    public String getPostModelString() {
        return String.format("{\n" +
                "  \"parent\": \"minecraft:block/template_wall_post\",\n" +
                "  \"textures\": {\n" +
                "    \"wall\": \"%s\"\n" +
                "  }\n" +
                "}", this.getBaseTexture());
    }

    public String getSideModelString() {
        return String.format("{\n" +
                "  \"parent\": \"minecraft:block/template_wall_side\",\n" +
                "  \"textures\": {\n" +
                "    \"wall\": \"%s\"\n" +
                "  }\n" +
                "}", this.getBaseTexture());
    }

    public String getSideTallModelString() {
        return String.format("{\n" +
                "  \"parent\": \"minecraft:block/template_wall_side_tall\",\n" +
                "  \"textures\": {\n" +
                "    \"wall\": \"%s\"\n" +
                "  }\n" +
                "}", this.getBaseTexture());
    }

    public List<Pair<Identifier, String>> getBlockModelCollection() {
        List<Pair<Identifier, String>> modelCollection = new ArrayList<>();
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier("_inventory"), this.getInventoryModelString()));
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier("_post"), this.getPostModelString()));
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier("_side"), this.getSideModelString()));
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier("_side_tall"), this.getSideTallModelString()));
        return modelCollection;
    }

    @Override
    public String getBlockStatesString() {
        Identifier identifier = this.getIdentifier();
        return String.format("{\n" +
                "  \"multipart\": [\n" +
                "    {\n" +
                "      \"when\": {\n" +
                "        \"up\": \"true\"\n" +
                "      },\n" +
                "      \"apply\": {\n" +
                "        \"model\": \"%1$s:block/%2$s_post\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"when\": {\n" +
                "        \"north\": \"low\"\n" +
                "      },\n" +
                "      \"apply\": {\n" +
                "        \"model\": \"%1$s:block/%2$s_side\",\n" +
                "        \"uvlock\": true\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"when\": {\n" +
                "        \"east\": \"low\"\n" +
                "      },\n" +
                "      \"apply\": {\n" +
                "        \"model\": \"%1$s:block/%2$s_side\",\n" +
                "        \"y\": 90,\n" +
                "        \"uvlock\": true\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"when\": {\n" +
                "        \"south\": \"low\"\n" +
                "      },\n" +
                "      \"apply\": {\n" +
                "        \"model\": \"%1$s:block/%2$s_side\",\n" +
                "        \"y\": 180,\n" +
                "        \"uvlock\": true\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"when\": {\n" +
                "        \"west\": \"low\"\n" +
                "      },\n" +
                "      \"apply\": {\n" +
                "        \"model\": \"%1$s:block/%2$s_side\",\n" +
                "        \"y\": 270,\n" +
                "        \"uvlock\": true\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"when\": {\n" +
                "        \"north\": \"tall\"\n" +
                "      },\n" +
                "      \"apply\": {\n" +
                "        \"model\": \"%1$s:block/%2$s_side_tall\",\n" +
                "        \"uvlock\": true\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"when\": {\n" +
                "        \"east\": \"tall\"\n" +
                "      },\n" +
                "      \"apply\": {\n" +
                "        \"model\": \"%1$s:block/%2$s_side_tall\",\n" +
                "        \"y\": 90,\n" +
                "        \"uvlock\": true\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"when\": {\n" +
                "        \"south\": \"tall\"\n" +
                "      },\n" +
                "      \"apply\": {\n" +
                "        \"model\": \"%1$s:block/%2$s_side_tall\",\n" +
                "        \"y\": 180,\n" +
                "        \"uvlock\": true\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"when\": {\n" +
                "        \"west\": \"tall\"\n" +
                "      },\n" +
                "      \"apply\": {\n" +
                "        \"model\": \"%1$s:block/%2$s_side_tall\",\n" +
                "        \"y\": 270,\n" +
                "        \"uvlock\": true\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}", identifier.getNamespace(), identifier.getPath());
    }

    @Override
    public String getItemModelString() {
        return String.format("{\n" +
                "  \"parent\": \"%s_inventory\"\n" +
                "}", this.getBlockModelIdentifier().toString());
    }

    @Override
    public String getCraftingRecipeString() {
        return String.format("{\n" +
                "  \"type\": \"minecraft:crafting_shaped\",\n" +
                "  \"group\": \"%3$s\",\n" +
                "  \"pattern\": [\n" +
                "    \"###\",\n" +
                "    \"###\"\n" +
                "  ],\n" +
                "  \"key\": {\n" +
                "    \"#\": {\n" +
                "      \"item\": \"%1$s\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"result\": {\n" +
                "    \"item\": \"%2$s\",\n" +
                "    \"count\": 6\n" +
                "  }\n" +
                "}", this.getBaseBlockIdentifier(), this.getIdentifier().toString(), this.getRecipeGroup());
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
        if (ExtShapeBlockTag.WOOLS.contains(baseBlock)) return "wool_wall";
        if (ExtShapeBlockTag.CONCRETES.contains(baseBlock)) return "concrete_wall";
        if (ExtShapeBlockTag.STAINED_TERRACOTTAS.contains(baseBlock)) return "stained_terracotta_wall";
        if (ExtShapeBlockTag.GLAZED_TERRACOTTAS.contains(baseBlock)) return "glazed_terracotta_wall";
        if (ExtShapeBlockTag.PLANKS.contains(baseBlock)) return "wooden_wall";
        return "";
    }
}
