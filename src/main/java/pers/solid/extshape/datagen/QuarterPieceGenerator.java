package pers.solid.extshape.datagen;

import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.QuarterPieceBlock;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.nio.file.Path;
import java.util.List;

public class QuarterPieceGenerator extends AbstractBlockGenerator<QuarterPieceBlock> {
    protected QuarterPieceGenerator(Path path, @NotNull QuarterPieceBlock block) {
        super(path, block);
    }

    public static void init(Generator generator) {
        generator.writeModelFile("extshape", "block/quarter_piece", """
                {   "parent": "block/block",
                    "textures": {
                        "particle": "#side"
                    },
                    "elements": [
                        {   "from": [ 0, 0, 0 ],
                            "to": [  16, 8, 8 ],
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
        generator.writeModelFile("extshape", "block/quarter_piece_top", """
                {   "parent": "block/block",
                    "textures": {
                        "particle": "#side"
                    },
                    "elements": [
                        {   "from": [ 0, 8, 0 ],
                            "to": [  16, 16, 8 ],
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
                    "half=top,facing=north": { "model": "%1$s_top" , "uvlock":true },
                    "half=top,facing=east":  { "model": "%1$s_top", "y":  90 , "uvlock":true},
                    "half=top,facing=south": { "model": "%1$s_top", "y": 180 , "uvlock":true },
                    "half=top,facing=west":  { "model": "%1$s_top", "y": 270 , "uvlock":true },
                    "half=bottom,facing=north": { "model": "%1$s" , "uvlock":true },
                    "half=bottom,facing=east":  { "model": "%1$s", "y":  90 , "uvlock":true},
                    "half=bottom,facing=south": { "model": "%1$s", "y": 180 , "uvlock":true },
                    "half=bottom,facing=west":  { "model": "%1$s", "y": 270 , "uvlock":true }
                  }
                }""", this.getBlockModelIdentifier());
    }

    @Override
    public String getBlockModelString() {
        return String.format("""
                {
                    "parent": "extshape:block/quarter_piece",
                    "textures": {
                        "bottom": "%s",
                        "top": "%s",
                        "side": "%s"
                    }
                }""", this.getBottomTexture(), this.getTopTexture(), this.getSideTexture());
    }

    public String getTopBlockModelString() {
        return String.format("""
                {
                    "parent": "extshape:block/quarter_piece_top",
                    "textures": {
                        "bottom": "%s",
                        "top": "%s",
                        "side": "%s"
                    }
                }""", this.getBottomTexture(), this.getTopTexture(), this.getSideTexture());
    }

    @Override
    public List<Pair<Identifier, String>> getBlockModelCollection() {
        var modelCollection = super.getBlockModelCollection();
        modelCollection.add(new Pair<>(this.getBlockModelIdentifier("_top"),this.getTopBlockModelString()));
        return modelCollection;
    }

    @Override
    public String getRecipeGroup() {
        Block baseBlock = this.getBaseBlock();
        if ((ExtShapeBlockTag.PLANKS).contains(baseBlock)) return "wooden_quarter_piece";
        if ((ExtShapeBlockTag.WOOLS).contains(baseBlock)) return "wool_quarter_piece";
        if ((ExtShapeBlockTag.CONCRETES).contains(baseBlock)) return "concrete_quarter_piece";
        if ((ExtShapeBlockTag.STAINED_TERRACOTTAS).contains(baseBlock)) return "stained_terracotta_quarter_piece";
        if ((ExtShapeBlockTag.GLAZED_TERRACOTTAS).contains(baseBlock)) return "glazed_terracotta_quarter_piece";
        return "";
    }
}
