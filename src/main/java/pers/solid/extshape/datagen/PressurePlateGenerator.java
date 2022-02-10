package pers.solid.extshape.datagen;

import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

@Deprecated
public class PressurePlateGenerator extends AbstractBlockGenerator<PressurePlateBlock> {
    protected PressurePlateGenerator(Path path, @NotNull PressurePlateBlock block) {
        super(path, block);
    }

    @Override
    public String getBlockStatesString() {
        return String.format("{\n" +
                "  \"variants\": {\n" +
                "    \"powered=false\": {\n" +
                "      \"model\": \"%1$s\"\n" +
                "    },\n" +
                "    \"powered=true\": {\n" +
                "      \"model\": \"%1$s_down\"\n" +
                "    }\n" +
                "  }\n" +
                "}", this.getBlockModelIdentifier());
    }

    @Override
    public String getBlockModelString() {
        return String.format("{\n" +
                "  \"parent\": \"minecraft:block/pressure_plate_up\",\n" +
                "  \"textures\": {\n" +
                "    \"texture\": \"%s\"\n" +
                "  }\n" +
                "}", this.getBaseTexture());
    }

    public String getDownModelString() {
        return String.format("{\n" +
                "  \"parent\": \"minecraft:block/pressure_plate_up\",\n" +
                "  \"textures\": {\n" +
                "    \"texture\": \"%s\"\n" +
                "  }\n" +
                "}", this.getBaseTexture());
    }

    @Override
    public Map<Identifier, String> getBlockModelCollection() {
        Map<Identifier, String> map = new LinkedHashMap<>();
        map.put(this.getBlockModelIdentifier(), this.getBlockModelString());
        map.put(this.getBlockModelIdentifier("_down"), this.getDownModelString());
        return map;
    }

    @Override
    public String getCraftingRecipeString() {
        if (!ExtShapeBlockTag.WOOLS.contains(this.getBaseBlock()))
            return String.format("{\n  \"type\": \"minecraft:crafting_shaped\",\n  \"group\": \"%s\",\n  \"pattern\": [\n    \"##\"\n  ],\n  \"key\": {\n    \"#\": {\n      \"item\": \"%s\"\n    }\n  },\n  \"result\": {\n    \"item\": \"%s\"\n  }\n}", this.getRecipeGroup(), this.getBaseBlockIdentifier(), this.getIdentifier());
        else
            return String.format("{\n  \"type\": \"minecraft:crafting_shapeless\",\n  \"group\": \"%s\",\n  \"ingredients\": [\n    {\n      \"item\": \"%s\"\n    }\n  ],\n  \"result\": {\n    \"item\": \"%s\"\n  }\n}", this.getRecipeGroup(), this.getBaseBlockIdentifier().toString().replaceAll("_wool\\b", "_carpet"), this.getIdentifier());
    }

    @Override
    public String getRecipeGroup() {
        Block baseBlock = this.getBaseBlock();
        if (ExtShapeBlockTag.WOOLS.contains(baseBlock)) return "wool_pressure_plate";
        if (ExtShapeBlockTag.CONCRETES.contains(baseBlock)) return "concrete_pressure_plate";
        if (ExtShapeBlockTag.STAINED_TERRACOTTAS.contains(baseBlock)) return "stained_terracotta_pressure_plate";
        if (ExtShapeBlockTag.GLAZED_TERRACOTTAS.contains(baseBlock)) return "glazed_terracotta_pressure_plate";
        if (ExtShapeBlockTag.PLANKS.contains(baseBlock)) return "wooden_pressure_plate";
        return "";
    }
}
