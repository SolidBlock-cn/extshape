package pers.solid.extshape.datagen;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.nio.file.Path;
import java.util.List;

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
    public List<Pair<Identifier, String>> getBlockModelCollection() {
        return ImmutableList.of(
                new Pair<>(this.getBlockModelIdentifier(), this.getBlockModelString()),
                new Pair<>(this.getBlockModelIdentifier("_down"), this.getDownModelString())
        );
    }

    @Override
    public String getCraftingRecipeString() {
        return String.format("{\n" +
                "  \"type\": \"minecraft:crafting_shaped\",\n" +
                "  \"group\": \"%s\",\n" +
                "  \"pattern\": [\n" +
                "    \"##\"\n" +
                "  ],\n" +
                "  \"key\": {\n" +
                "    \"#\": {\n" +
                "      \"item\": \"%s\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"result\": {\n" +
                "    \"item\": \"%s\"\n" +
                "  }\n" +
                "}", this.getRecipeGroup(), this.getBaseBlockIdentifier(), this.getIdentifier());
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
