package pers.solid.extshape.datagen;

import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.VerticalSlabBlock;
import pers.solid.extshape.builder.Shape;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.nio.file.Path;

@Deprecated
public class VerticalSlabGenerator extends AbstractBlockGenerator<VerticalSlabBlock> {
    protected VerticalSlabGenerator(Path path, @NotNull VerticalSlabBlock block) {
        super(path, block);
    }

    public static void init(Generator generator) {
        generator.writeModelFile("extshape", "block/vertical_slab", "{   \"parent\": \"block/block\",\n" +
                "    \"textures\": {\n" +
                "        \"particle\": \"#side\"\n" +
                "    },\n" +
                "    \"elements\": [\n" +
                "        {   \"from\": [ 0, 0, 8 ],\n" +
                "            \"to\": [  16, 16, 16 ],\n" +
                "            \"faces\": {\n" +
                "                \"down\":  {\"texture\": \"#bottom\", \"cullface\": \"down\" },\n" +
                "                \"up\":    {\"texture\": \"#top\",    \"cullface\": \"up\" },\n" +
                "                \"north\": {\"texture\": \"#side\" },\n" +
                "                \"south\": {\"texture\": \"#side\",   \"cullface\": \"south\" },\n" +
                "                \"west\":  { \"texture\": \"#side\",   \"cullface\": \"west\" },\n" +
                "                \"east\":  { \"texture\": \"#side\",   \"cullface\": \"east\" }\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}");
    }

    @Override
    public String getBlockStatesString() {
        return String.format("{\n" +
                "  \"variants\": {\n" +
                "    \"facing=south\": { \"model\": \"%1$s\" , \"uvlock\":true },\n" +
                "    \"facing=west\":  { \"model\": \"%1$s\", \"y\":  90 , \"uvlock\":true},\n" +
                "    \"facing=north\": { \"model\": \"%1$s\", \"y\": 180 , \"uvlock\":true },\n" +
                "    \"facing=east\":  { \"model\": \"%1$s\", \"y\": 270 , \"uvlock\":true }\n" +
                "  }\n" +
                "}", this.getBlockModelIdentifier());
    }

    @Override
    public String getBlockModelString() {
        return String.format("{\n" +
                "    \"parent\": \"extshape:block/vertical_slab\",\n" +
                "    \"textures\": {\n" +
                "        \"bottom\": \"%s\",\n" +
                "        \"top\": \"%s\",\n" +
                "        \"side\": \"%s\"\n" +
                "    }\n" +
                "}", this.getBottomTexture(), this.getTopTexture(), this.getSideTexture());
    }

    @Override
    public String getCraftingRecipeString() {
        return String.format("{\n" +
                        "  \"type\": \"minecraft:crafting_shaped\",\n" +
                        "  \"group\": \"%s\",\n" +
                        "  \"pattern\": [\n" +
                        "    \"#\"\n" +
                        "  ],\n" +
                        "  \"key\": {\n" +
                        "    \"#\": {\n" +
                        "      \"item\": \"%s\"\n" +
                        "    }\n" +
                        "  },\n" +
                        "  \"result\": {\n" +
                        "    \"item\": \"%s\",\n" +
                        "    \"count\": 1\n" +
                        "  }\n" +
                        "}\n", this.getRecipeGroup(), Registry.BLOCK.getId(BlockMappings.getBlockOf(Shape.SLAB,
                        this.getBaseBlock())),
                this.getIdentifier());
    }

    /**
     * 由纵台阶生成台阶的合成表。考虑到要应用于原版，所以生成台阶的合成表由纵台阶生成器生成。
     *
     * @return 由纵台阶制作台阶的合成配方。
     */
    public String getInverseCraftingString() {
        return String.format("{\n" +
                        "  \"type\": \"minecraft:crafting_shaped\",\n" +
                        "  \"group\": \"%s\",\n" +
                        "  \"pattern\": [\n" +
                        "    \"#\"\n" +
                        "  ],\n" +
                        "  \"key\": {\n" +
                        "    \"#\": {\n" +
                        "      \"item\": \"%s\"\n" +
                        "    }\n" +
                        "  },\n" +
                        "  \"result\": {\n" +
                        "    \"item\": \"%s\",\n" +
                        "    \"count\": 1\n" +
                        "  }\n" +
                        "}\n", this.getRecipeGroup().replace("vertical_", ""), this.getIdentifier(),
                Registry.BLOCK.getId(BlockMappings.getBlockOf(Shape.SLAB,
                        this.getBaseBlock())));
    }

    @Override
    public void writeCraftingRecipeFiles() {
        super.writeCraftingRecipeFiles();
        this.writeRecipeFile("extshape", Registry.BLOCK.getId(BlockMappings.getBlockOf(Shape.SLAB,
                this.getBaseBlock())).getPath() + "_from_vertical_slab", this.getInverseCraftingString());
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
    public String getRecipeGroup() {
        Block baseBlock = this.getBaseBlock();
        if ((ExtShapeBlockTag.PLANKS).contains(baseBlock)) return "wooden_vertical_slab";
        if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_vertical_slab";
        if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_vertical_slab";
        if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return "stained_terracotta_vertical_slab";
        if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return "glazed_terracotta_vertical_slab";
        return "";
    }
}
