package pers.solid.extshape.datagen;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SlabGenerator extends AbstractBlockGenerator<SlabBlock> {
    protected SlabGenerator(Path path, @NotNull SlabBlock block) {
        super(path, block);
    }


    @Override
    public String getBlockStatesString() {
        Identifier baseIdentifier = Registry.BLOCK.getId(this.getBaseBlock());
        return String.format("{\n" +
                        "  \"variants\": {\n" +
                        "    \"type=bottom\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s\"\n" +
                        "    },\n" +
                        "    \"type=double\": {\n" +
                        "      \"model\": \"%3$s:block/%4$s\"\n" +
                        "    },\n" +
                        "    \"type=top\": {\n" +
                        "      \"model\": \"%1$s:block/%2$s_top\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}\n", this.getIdentifier().getNamespace(), this.getIdentifier().getPath(), baseIdentifier.getNamespace(),
                baseIdentifier.getPath());
    }

    @Override
    public String getBlockModelString() {
        return String.format("{\n" +
                "  \"parent\": \"minecraft:block/slab\",\n" +
                "  \"textures\": {\n" +
                "    \"bottom\": \"%s\",\n" +
                "    \"top\": \"%s\",\n" +
                "    \"side\": \"%s\"\n" +
                "  }\n" +
                "}\n", this.getBottomTexture(), this.getTopTexture(), this.getSideTexture());
    }

    public String getTopBlockModelString() {
        return String.format("{\n" +
                "  \"parent\": \"minecraft:block/slab_top\",\n" +
                "  \"textures\": {\n" +
                "    \"bottom\": \"%s\",\n" +
                "    \"top\": \"%s\",\n" +
                "    \"side\": \"%s\"\n" +
                "  }\n" +
                "}\n", this.getBottomTexture(), this.getTopTexture(), this.getSideTexture());
    }

    @Override
    public List<Pair<Identifier, String>> getBlockModelCollection() {
        List<Pair<Identifier, String>> modelCollection = new ArrayList<>();
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier(), this.getBlockModelString()));
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier("_top"), this.getTopBlockModelString()));
        return modelCollection;
    }

    @Override
    public String getCraftingRecipeString() {
        return String.format("{\n" +
                "  \"type\": \"minecraft:crafting_shaped\",\n" +
                "  \"group\": \"%3$s\",\n" +
                "  \"pattern\": [\n" +
                "    \"###\"\n" +
                "  ],\n" +
                "  \"key\": {\n" +
                "    \"#\": {\n" +
                "      \"item\": \"%s\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"result\": {\n" +
                "    \"item\": \"%s\",\n" +
                "    \"count\": 6\n" +
                "  }\n" +
                "}", getBaseBlockIdentifier(), getIdentifier().toString(), this.getRecipeGroup());
    }

    @Override
    public String getStoneCuttingRecipeString() {
        return String.format("{\n" +
                "  \"type\": \"minecraft:stonecutting\",\n" +
                "  \"ingredient\": {\n" +
                "    \"item\": \"%s\"\n" +
                "  },\n" +
                "  \"result\": \"%s\",\n" +
                "  \"count\": 2\n" +
                "}", this.getBaseBlockIdentifier(), this.getIdentifier());
    }

    @Override
    public String getLootTableString() {
        Identifier identifier = this.getIdentifier();
        return String.format("{\n" +
                "  \"type\": \"minecraft:block\",\n" +
                "  \"pools\": [\n" +
                "    {\n" +
                "      \"rolls\": 1.0,\n" +
                "      \"bonus_rolls\": 0.0,\n" +
                "      \"entries\": [\n" +
                "        {\n" +
                "          \"type\": \"minecraft:item\",\n" +
                "          \"functions\": [\n" +
                "            {\n" +
                "              \"function\": \"minecraft:set_count\",\n" +
                "              \"conditions\": [\n" +
                "                {\n" +
                "                  \"condition\": \"minecraft:block_state_property\",\n" +
                "                  \"block\": \"%1$s\",\n" +
                "                  \"properties\": {\n" +
                "                    \"type\": \"double\"\n" +
                "                  }\n" +
                "                }\n" +
                "              ],\n" +
                "              \"count\": 2.0,\n" +
                "              \"add\": false\n" +
                "            },\n" +
                "            {\n" +
                "              \"function\": \"minecraft:explosion_decay\"\n" +
                "            }\n" +
                "          ],\n" +
                "          \"name\": \"%1$s\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}", identifier.toString());
    }


    @Override
    public String getRecipeGroup() {
        Block baseBlock = this.getBaseBlock();
        if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_slab";
        if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_slab";
        if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return "stained_terracotta_slab";
        if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return "glazed_terracotta_slab";
        return "";
    }
}
