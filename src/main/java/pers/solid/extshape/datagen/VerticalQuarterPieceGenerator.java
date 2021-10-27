package pers.solid.extshape.datagen;

import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.VerticalQuarterPieceBlock;
import pers.solid.extshape.builder.Shape;
import pers.solid.extshape.mappings.BlockMappings;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.nio.file.Path;

public class VerticalQuarterPieceGenerator extends AbstractBlockGenerator<VerticalQuarterPieceBlock> {
    protected VerticalQuarterPieceGenerator(Path path, @NotNull VerticalQuarterPieceBlock block) {
        super(path, block);
    }

    /**
     * 生成用于所有纵条方块的模板模型。
     *
     * @param generator 生成器。
     */
    public static void init(Generator generator) {
        generator.writeModelFile("extshape", "block/vertical_quarter_piece", "{   \"parent\": \"block/block\",\n" +
                "    \"textures\": {\n" +
                "        \"particle\": \"#side\"\n" +
                "    },\n" +
                "    \"elements\": [\n" +
                "        {   \"from\": [ 8, 0, 8 ],\n" +
                "            \"to\": [  16, 16, 16 ],\n" +
                "            \"faces\": {\n" +
                "                \"down\":  {\"texture\": \"#bottom\", \"cullface\": \"down\" },\n" +
                "                \"up\":    {\"texture\": \"#top\",    \"cullface\": \"up\" },\n" +
                "                \"north\": {\"texture\": \"#side\" },\n" +
                "                \"south\": {\"texture\": \"#side\",   \"cullface\": \"south\" },\n" +
                "                \"west\":  { \"texture\": \"#side\" },\n" +
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
                "    \"facing=south_east\": { \"model\": \"%1$s\" , \"uvlock\":true },\n" +
                "    \"facing=south_west\":  { \"model\": \"%1$s\", \"y\":  90 , \"uvlock\":true},\n" +
                "    \"facing=north_west\": { \"model\": \"%1$s\", \"y\": 180 , \"uvlock\":true },\n" +
                "    \"facing=north_east\":  { \"model\": \"%1$s\", \"y\": 270 , \"uvlock\":true }\n" +
                "  }\n" +
                "}", this.getBlockModelIdentifier());
    }

    @Override
    public String getBlockModelString() {
        return String.format("{\n" +
                "    \"parent\": \"extshape:block/vertical_quarter_piece\",\n" +
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
                        "}\n", this.getRecipeGroup(), Registry.BLOCK.getId(BlockMappings.getBlockOf(Shape.QUARTER_PIECE,
                        this.getBaseBlock())),
                this.getIdentifier());
    }

    /**
     * @return 由3个纵台阶合成6个纵条的合成表。
     */
    public String getCraftingFromVerticalSlabRecipeString() {
        return String.format("{\n" +
                        "  \"type\": \"minecraft:crafting_shaped\",\n" +
                        "  \"group\": \"%s\",\n" +
                        "  \"pattern\": [\n" +
                        "    \"#\",\"#\",\"#\"\n" +
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
                        "}\n", this.getRecipeGroup(), Registry.BLOCK.getId(BlockMappings.getBlockOf(Shape.VERTICAL_SLAB,
                        this.getBaseBlock())),
                this.getIdentifier());
    }

    @Override
    public void writeCraftingRecipeFiles() {
        super.writeCraftingRecipeFile();
        Identifier identifier = this.getIdentifier();
        this.writeRecipeFile(identifier.getNamespace(),
                identifier.getPath() + "_from_vertical_slab", this.getCraftingFromVerticalSlabRecipeString());
    }

    @Override
    public String getStoneCuttingRecipeString() {
        return String.format("{\n" +
                "  \"type\": \"minecraft:stonecutting\",\n" +
                "  \"ingredient\": {\n" +
                "    \"item\": \"%s\"\n" +
                "  },\n" +
                "  \"result\": \"%s\",\n" +
                "  \"count\": 4\n" +
                "}", this.getBaseBlockIdentifier(), this.getIdentifier());
    }

    /**
     * 一个纵楼梯可以切石形成3个纵条。
     *
     * @return 由1个纵楼梯制作3个纵条的切石配方。
     */
    public String getStoneCuttingFromVerticalStairsRecipeString() {
        return String.format("{\n" +
                        "  \"type\": \"minecraft:stonecutting\",\n" +
                        "  \"ingredient\": {\n" +
                        "    \"item\": \"%s\"\n" +
                        "  },\n" +
                        "  \"result\": \"%s\",\n" +
                        "  \"count\": 3\n" +
                        "}", Registry.BLOCK.getId(BlockMappings.getBlockOf(Shape.VERTICAL_STAIRS,
                        this.getBaseBlock())),
                this.getIdentifier());
    }

    /**
     * @return 由1个纵台阶切石形成2个纵条的合成表。
     */
    public String getStoneCuttingFromVerticalSlabRecipeString() {
        return String.format("{\n" +
                        "  \"type\": \"minecraft:stonecutting\",\n" +
                        "  \"ingredient\": {\n" +
                        "    \"item\": \"%s\"\n" +
                        "  },\n" +
                        "  \"result\": \"%s\",\n" +
                        "  \"count\": 2\n" +
                        "}", Registry.BLOCK.getId(BlockMappings.getBlockOf(Shape.VERTICAL_SLAB, this.getBaseBlock())),
                this.getIdentifier());
    }

    @Override
    public void writeStoneCuttingRecipeFiles() {
        super.writeStoneCuttingRecipeFiles();
        Identifier identifier = this.getIdentifier();
        this.writeRecipeFile(identifier.getNamespace(), identifier.getPath() +
                        "_from_vertical_stairs_stonecutting",
                this.getStoneCuttingFromVerticalStairsRecipeString());
        this.writeRecipeFile(identifier.getNamespace(), identifier.getPath() +
                        "_from_vertical_slab_stonecutting",
                this.getStoneCuttingFromVerticalSlabRecipeString());
    }

    @Override
    public String getRecipeGroup() {
        Block baseBlock = this.getBaseBlock();
        if ((ExtShapeBlockTag.PLANKS).contains(baseBlock)) return "wooden_vertical_quarter_piece";
        if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_vertical_quarter_piece";
        if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_vertical_quarter_piece";
        if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return
                "stained_terracotta_vertical_quarter_piece";
        if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return
                "glazed_terracotta_vertical_quarter_piece";
        return "";
    }
}
