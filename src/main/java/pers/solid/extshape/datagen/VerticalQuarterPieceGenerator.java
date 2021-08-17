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

    public static void init(Generator generator) {
        generator.writeModelFile("extshape", "block/vertical_quarter_piece", """
                {   "parent": "block/block",
                    "textures": {
                        "particle": "#side"
                    },
                    "elements": [
                        {   "from": [ 8, 0, 8 ],
                            "to": [  16, 16, 16 ],
                            "faces": {
                                "down":  {"texture": "#bottom", "cullface": "down" },
                                "up":    {"texture": "#top",    "cullface": "up" },
                                "north": {"texture": "#side",   "cullface": "north" },
                                "south": {"texture": "#side",   "cullface": "south" },
                                "west":  { "texture": "#side",   "cullface": "west" },
                                "east":  { "texture": "#side",   "cullface": "east" }
                            }
                        }
                    ]
                }""");
    }

    @Override
    public String getBlockStatesString() {
        return String.format("""
                {
                  "variants": {
                    "facing=south_east": { "model": "%1$s" , "uvlock":true },
                    "facing=south_west":  { "model": "%1$s", "y":  90 , "uvlock":true},
                    "facing=north_west": { "model": "%1$s", "y": 180 , "uvlock":true },
                    "facing=north_east":  { "model": "%1$s", "y": 270 , "uvlock":true }
                  }
                }""", this.getBlockModelIdentifier());
    }

    @Override
    public String getBlockModelString() {
        return String.format("""
                {
                    "parent": "extshape:block/vertical_quarter_piece",
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
                            "#","#","#"
                          ],
                          "key": {
                            "#": {
                              "item": "%s"
                            }
                          },
                          "result": {
                            "item": "%s",
                            "count": 3
                          }
                        }
                        """, this.getRecipeGroup(), Registry.BLOCK.getId(BlockMappings.getBlockOf(Shape.quarterPiece,
                        this.getBaseBlock())),
                this.getIdentifier());
    }

    public String getCraftingFromVerticalSlabRecipeString() {
        return String.format("""
                        {
                          "type": "minecraft:crafting_shaped",
                          "group": "%s",
                          "pattern": [
                            "#","#","#"
                          ],
                          "key": {
                            "#": {
                              "item": "%s"
                            }
                          },
                          "result": {
                            "item": "%s",
                            "count": 6
                          }
                        }
                        """, this.getRecipeGroup(), Registry.BLOCK.getId(BlockMappings.getBlockOf(Shape.verticalSlab,
                        this.getBaseBlock())),
                this.getIdentifier());
    }

    @Override
    public void writeCraftingRecipeFiles() {
        super.writeCraftingRecipeFile();
        Identifier identifier = this.getIdentifier();
        this.writeRecipeFile(identifier.getNamespace(),
                identifier.getPath()+"_from_vertical_slab", this.getCraftingFromVerticalSlabRecipeString());
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
                  "count": 4
                }""", this.getBaseBlockIdentifier(), this.getIdentifier());
    }

    public String getStoneCuttingFromVerticalSlabRecipeString() {
        return String.format("""
                {
                  "type": "minecraft:stonecutting",
                  "ingredient": {
                    "item": "%s"
                  },
                  "result": "%s",
                  "count": 2
                }""", Registry.BLOCK.getId(BlockMappings.getBlockOf(Shape.verticalSlab,this.getBaseBlock())),
                this.getIdentifier());
    }

    @Override
    public void writeStoneCuttingRecipeFiles() {
        super.writeStoneCuttingRecipeFiles();
        Identifier identifier = this.getIdentifier();
        this.writeRecipeFile(identifier.getNamespace(), identifier.getPath() + "_from_vertical_slab_stonecutting",
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
