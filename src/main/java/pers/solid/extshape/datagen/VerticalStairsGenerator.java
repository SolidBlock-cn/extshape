package pers.solid.extshape.datagen;

import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.VerticalStairsBlock;
import pers.solid.extshape.builder.Shape;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.nio.file.Path;

public class VerticalStairsGenerator extends AbstractBlockGenerator<VerticalStairsBlock> {
    protected VerticalStairsGenerator(Path path, @NotNull VerticalStairsBlock block) {
        super(path, block);
    }

    public static void init(Generator generator) {
        generator.writeModelFile("extshape", "block/vertical_stairs", "{   \"parent\": \"block/block\",\n" +
                "    \"textures\": {\n" +
                "        \"particle\": \"#side\"\n" +
                "    },\n" +
                "    \"elements\": [\n" +
                "        {   \"from\": [ 0, 0, 0 ],\n" +
                "            \"to\": [ 8, 16, 8 ],\n" +
                "            \"faces\": {\n" +
                "                \"down\":  { \"texture\": \"#bottom\", \"cullface\": \"down\" },\n" +
                "                \"up\":    { \"texture\": \"#top\", \"cullface\": \"top\" },\n" +
                "                \"north\": { \"texture\": \"#side\", \"cullface\": \"north\" },\n" +
                "                \"south\": { \"texture\": \"#side\", \"cullface\": \"south\" },\n" +
                "                \"west\":  { \"texture\": \"#side\", \"cullface\": \"west\" },\n" +
                "                \"east\":  { \"texture\": \"#side\" }\n" +
                "            }\n" +
                "        },\n" +
                "        {   \"from\": [ 0, 0, 8 ],\n" +
                "            \"to\": [ 16, 16, 16 ],\n" +
                "            \"faces\": {\n" +
                "                \"down\":    { \"texture\": \"#top\", \"cullface\": \"down\" },\n" +
                "                \"up\":    { \"texture\": \"#top\", \"cullface\": \"up\" },\n" +
                "                \"north\": { \"texture\": \"#side\" },\n" +
                "                \"south\": { \"texture\": \"#side\", \"cullface\": \"south\" },\n" +
                "                \"west\":  { \"texture\": \"#side\", \"cullface\": \"west\" },\n" +
                "                \"east\":  { \"texture\": \"#side\", \"cullface\": \"east\" }\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}\n");
    }

    @Override
    public String getBlockStatesString() {
        return String.format("{\n" +
                "  \"variants\": {\n" +
                "    \"facing=south_west\": { \"model\": \"%1$s\" , \"uvlock\":true },\n" +
                "    \"facing=north_west\":  { \"model\": \"%1$s\", \"y\":  90 , \"uvlock\":true},\n" +
                "    \"facing=north_east\": { \"model\": \"%1$s\", \"y\": 180 , \"uvlock\":true },\n" +
                "    \"facing=south_east\":  { \"model\": \"%1$s\", \"y\": 270 , \"uvlock\":true }\n" +
                "  }\n" +
                "}", this.getBlockModelIdentifier());
    }

    @Override
    public String getBlockModelString() {
        return String.format("{\n" +
                "    \"parent\": \"extshape:block/vertical_stairs\",\n" +
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
                        "}\n", this.getRecipeGroup(), Registry.BLOCK.getId(BlockMappings.getBlockOf(Shape.stairs,
                        this.getBaseBlock())),
                this.getIdentifier());
    }

    /**
     * 由纵楼梯生成楼梯的合成表。考虑到要应用于原版，所以生成楼梯的合成表由纵楼梯生成器生成。
     *
     * @return 由纵楼梯制作楼梯的合成配方。
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
                Registry.BLOCK.getId(BlockMappings.getBlockOf(Shape.stairs,
                        this.getBaseBlock())));
    }

    @Override
    public void writeCraftingRecipeFiles() {
        super.writeCraftingRecipeFiles();
        this.writeRecipeFile("extshape", Registry.BLOCK.getId(BlockMappings.getBlockOf(Shape.stairs,
                this.getBaseBlock())).getPath() + "_from_vertical_stairs", this.getInverseCraftingString());
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
        if ((ExtShapeBlockTag.PLANKS).contains(baseBlock)) return "wooden_vertical_stairs";
        if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_vertical_stairs";
        if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_vertical_stairs";
        if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return
                "stained_terracotta_vertical_stairs";
        if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return
                "glazed_terracotta_vertical_stairs";
        return "";
    }
}
