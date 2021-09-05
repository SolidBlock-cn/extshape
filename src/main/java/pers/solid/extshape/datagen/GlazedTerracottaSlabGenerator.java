package pers.solid.extshape.datagen;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.GlazedTerracottaSlabBlock;

import java.nio.file.Path;
import java.util.Objects;

public class GlazedTerracottaSlabGenerator extends SlabGenerator {
    protected GlazedTerracottaSlabGenerator(Path path, @NotNull GlazedTerracottaSlabBlock block) {
        super(path, block);
    }

    public static void init(Generator generator) {
        generator.writeModelFile("extshape", "block/glazed_terracotta_slab", "{\n" +
                "    \"parent\": \"block/cube\",\n" +
                "    \"textures\": {\n" +
                "        \"particle\": \"#side\"\n" +
                "    },\n" +
                "    \"display\": {\n" +
                "        \"firstperson_righthand\": {\n" +
                "            \"rotation\": [ 0, 135, 0 ],\n" +
                "            \"translation\": [ 0, 0, 0 ],\n" +
                "            \"scale\": [ 0.40, 0.40, 0.40 ]\n" +
                "        }\n" +
                "    },\n" +
                "    \"elements\": [\n" +
                "        {   \"from\": [ 0, 0, 0 ],\n" +
                "            \"to\": [ 16, 8, 16 ],\n" +
                "            \"faces\": {\n" +
                "                \"down\":  { \"texture\": \"#bottom\", \"cullface\": \"down\" },\n" +
                "                \"up\":    { \"texture\": \"#top\", \"cullface\": \"up\" },\n" +
                "                \"north\": { \"texture\": \"#side\", \"uv\":[8,0,16,16], \"cullface\": \"north\", \"rotation\": 90 },\n" +
                "                \"south\": { \"texture\": \"#side\", \"uv\":[0,0,8,16], \"cullface\": \"south\", \"rotation\": 270 },\n" +
                "                \"west\":  { \"texture\": \"#side\", \"uv\":[0,8,16,16], \"cullface\": \"west\", \"rotation\": 0 },\n" +
                "                \"east\":  { \"texture\": \"#side\", \"uv\":[0,0,16,8], \"cullface\": \"east\", \"rotation\": 180 }\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}\n");
        generator.writeModelFile("extshape", "block/glazed_terracotta_slab_top", "{\n" +
                "    \"parent\": \"block/cube\",\n" +
                "    \"textures\": {\n" +
                "        \"particle\": \"#side\"\n" +
                "    },\n" +
                "    \"display\": {\n" +
                "        \"firstperson_righthand\": {\n" +
                "            \"rotation\": [ 0, 135, 0 ],\n" +
                "            \"translation\": [ 0, 0, 0 ],\n" +
                "            \"scale\": [ 0.40, 0.40, 0.40 ]\n" +
                "        }\n" +
                "    },\n" +
                "    \"elements\": [\n" +
                "        {   \"from\": [ 0, 8, 0 ],\n" +
                "            \"to\": [ 16, 16, 16 ],\n" +
                "            \"faces\": {\n" +
                "                \"down\":  { \"texture\": \"#bottom\", \"cullface\": \"down\" },\n" +
                "                \"up\":    { \"texture\": \"#top\", \"cullface\": \"up\" },\n" +
                "                \"north\": { \"texture\": \"#side\", \"uv\":[0,0,8,16], \"cullface\": \"north\", \"rotation\": 90 },\n" +
                "                \"south\": { \"texture\": \"#side\", \"uv\":[8,0,16,16], \"cullface\": \"south\", \"rotation\": 270 },\n" +
                "                \"west\":  { \"texture\": \"#side\", \"uv\":[0,0,16,8], \"cullface\": \"west\", \"rotation\": 0 },\n" +
                "                \"east\":  { \"texture\": \"#side\", \"uv\":[0,8,16,16], \"cullface\": \"east\", \"rotation\": 180 }\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}\n");
    }

    public String getTopBlockModelString() {
        return String.format("{\n" +
                "  \"parent\": \"extshape:block/glazed_terracotta_slab_top\",\n" +
                "  \"textures\": {\n" +
                "    \"bottom\": \"%s\",\n" +
                "    \"top\": \"%s\",\n" +
                "    \"side\": \"%s\"\n" +
                "  }\n" +
                "}\n", this.getBottomTexture(), this.getTopTexture(), this.getSideTexture());
    }

    @Override
    public String getBlockModelString() {
        return String.format("{\n" +
                "  \"parent\": \"extshape:block/glazed_terracotta_slab\",\n" +
                "  \"textures\": {\n" +
                "    \"bottom\": \"%s\",\n" +
                "    \"top\": \"%s\",\n" +
                "    \"side\": \"%s\"\n" +
                "  }\n" +
                "}\n", this.getBottomTexture(), this.getTopTexture(), this.getSideTexture());
    }

    @Override
    public String getBlockStatesString() {
        @NotNull Identifier baseIdentifier = Objects.requireNonNull(this.getBaseBlockIdentifier());
        return String.format("{\n" +
                "  \"variants\": {\n" +
                "    \"type=bottom,facing=south\": {\n" +
                "      \"model\": \"%1$s\"\n" +
                "    },\n" +
                "    \"type=double,facing=south\": {\n" +
                "      \"model\": \"%2$s\"\n" +
                "    },\n" +
                "    \"type=top,facing=south\": {\n" +
                "      \"model\": \"%1$s_top\"\n" +
                "    },\n" +
                "    \"type=bottom,facing=west\": {\n" +
                "      \"model\": \"%1$s\",\n" +
                "      \"y\": 90\n" +
                "    },\n" +
                "    \"type=double,facing=west\": {\n" +
                "      \"model\": \"%2$s\",\n" +
                "      \"y\": 90\n" +
                "    },\n" +
                "    \"type=top,facing=west\": {\n" +
                "      \"model\": \"%1$s_top\",\n" +
                "      \"y\": 90\n" +
                "    },\n" +
                "    \"type=bottom,facing=north\": {\n" +
                "      \"model\": \"%1$s\",\n" +
                "      \"y\": 180\n" +
                "    },\n" +
                "    \"type=double,facing=north\": {\n" +
                "      \"model\": \"%2$s\",\n" +
                "      \"y\": 180\n" +
                "    },\n" +
                "    \"type=top,facing=north\": {\n" +
                "      \"model\": \"%1$s_top\",\n" +
                "      \"y\": 180\n" +
                "    },\n" +
                "    \"type=bottom,facing=east\": {\n" +
                "      \"model\": \"%1$s\",\n" +
                "      \"y\": 270\n" +
                "    },\n" +
                "    \"type=double,facing=east\": {\n" +
                "      \"model\": \"%2$s\",\n" +
                "      \"y\": 270\n" +
                "    },\n" +
                "    \"type=top,facing=east\": {\n" +
                "      \"model\": \"%1$s_top\",\n" +
                "      \"y\": 270\n" +
                "    }\n" +
                "  }\n" +
                "}", this.getBlockModelIdentifier(), new Identifier(baseIdentifier.getNamespace(),
                "block/" + baseIdentifier.getPath()));
    }
}
