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
        generator.writeModelFile("extshape", "block/vertical_stairs", """
                {   "parent": "block/block",
                    "textures": {
                        "particle": "#side"
                    },
                    "elements": [
                        {   "from": [ 0, 0, 0 ],
                            "to": [ 8, 16, 8 ],
                            "faces": {
                                "down":  { "texture": "#bottom", "cullface": "down" },
                                "up":    { "texture": "#top", "cullface": "top" },
                                "north": { "texture": "#side", "cullface": "north" },
                                "south": { "texture": "#side", "cullface": "south" },
                                "west":  { "texture": "#side", "cullface": "west" },
                                "east":  { "texture": "#side" }
                            }
                        },
                        {   "from": [ 0, 0, 8 ],
                            "to": [ 16, 16, 16 ],
                            "faces": {
                                "down":  { "texture": "#bottom", "cullface": "down" },
                                "up":    { "texture": "#top", "cullface": "up" },
                                "north": { "texture": "#side" },
                                "south": { "texture": "#side", "cullface": "south" },
                                "west":  { "texture": "#side", "cullface": "west" },
                                "east":  { "texture": "#side", "cullface": "east" }
                            }
                        }
                    ]
                }
                """);
    }

    @Override
    public String getBlockStatesString() {
        return String.format("""
                {
                  "variants": {
                    "facing=south_west": { "model": "%1$s" , "uvlock":true },
                    "facing=north_west":  { "model": "%1$s", "y":  90 , "uvlock":true},
                    "facing=north_east": { "model": "%1$s", "y": 180 , "uvlock":true },
                    "facing=south_east":  { "model": "%1$s", "y": 270 , "uvlock":true }
                  }
                }""", this.getBlockModelIdentifier());
    }

    @Override
    public String getBlockModelString() {
        return String.format("""
                {
                    "parent": "extshape:block/vertical_stairs",
                    "textures": {
                        "bottom": "%s",
                        "top": "%s",
                        "side": "%s"
                    }
                }""", this.getBottomTexture(), this.getTopTexture(), this.getSideTexture());
    }

    @Override
    public String getCraftingRecipeString() {
        return String.format("""
                        {
                          "type": "minecraft:crafting_shaped",
                          "group": "%s",
                          "pattern": [
                            "#"
                          ],
                          "key": {
                            "#": {
                              "item": "%s"
                            }
                          },
                          "result": {
                            "item": "%s",
                            "count": 1
                          }
                        }
                        """, this.getRecipeGroup(), Registry.BLOCK.getId(BlockMappings.getBlockOf(Shape.STAIRS,
                        this.getBaseBlock())),
                this.getIdentifier());
    }

    /**
     * 由纵楼梯生成楼梯的合成表。考虑到要应用于原版，所以生成楼梯的合成表由纵楼梯生成器生成。
     *
     * @return 由纵楼梯制作楼梯的合成配方。
     */
    public String getInverseCraftingString() {
        return String.format("""
                        {
                          "type": "minecraft:crafting_shaped",
                          "group": "%s",
                          "pattern": [
                            "#"
                          ],
                          "key": {
                            "#": {
                              "item": "%s"
                            }
                          },
                          "result": {
                            "item": "%s",
                            "count": 1
                          }
                        }
                        """, this.getRecipeGroup().replace("vertical_", ""), this.getIdentifier(),
                Registry.BLOCK.getId(BlockMappings.getBlockOf(Shape.STAIRS,
                        this.getBaseBlock())));
    }

    @Override
    public void writeCraftingRecipeFiles() {
        super.writeCraftingRecipeFiles();
        this.writeRecipeFile("extshape", Registry.BLOCK.getId(BlockMappings.getBlockOf(Shape.STAIRS,
                this.getBaseBlock())).getPath() + "_from_vertical_stairs", this.getInverseCraftingString());
    }

    @Override
    public String getStoneCuttingRecipeString() {
        return String.format("""
                {
                  "type": "minecraft:stonecutting",
                  "ingredient": {
                    "item": "%s"
                  },
                  "result": "%s",
                  "count": 1
                }""", this.getBaseBlockIdentifier(), this.getIdentifier());
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
