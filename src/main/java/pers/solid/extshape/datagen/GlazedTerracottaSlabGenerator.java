package pers.solid.extshape.datagen;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.block.GlazedTerracottaSlabBlock;

import java.nio.file.Path;
import java.util.Objects;

@Deprecated
public class GlazedTerracottaSlabGenerator extends SlabGenerator {
    protected GlazedTerracottaSlabGenerator(Path path, @NotNull GlazedTerracottaSlabBlock block) {
        super(path, block);
    }

    public static void init(Generator generator) {
        generator.writeModelFile("extshape", "block/glazed_terracotta_slab", """
                {
                    "parent": "block/cube",
                    "textures": {
                        "particle": "#side"
                    },
                    "display": {
                        "firstperson_righthand": {
                            "rotation": [ 0, 135, 0 ],
                            "translation": [ 0, 0, 0 ],
                            "scale": [ 0.40, 0.40, 0.40 ]
                        }
                    },
                    "elements": [
                        {   "from": [ 0, 0, 0 ],
                            "to": [ 16, 8, 16 ],
                            "faces": {
                                "down":  { "texture": "#bottom", "cullface": "down" },
                                "up":    { "texture": "#top", "cullface": "up" },
                                "north": { "texture": "#side", "uv":[8,0,16,16], "cullface": "north", "rotation": 90 },
                                "south": { "texture": "#side", "uv":[0,0,8,16], "cullface": "south", "rotation": 270 },
                                "west":  { "texture": "#side", "uv":[0,8,16,16], "cullface": "west", "rotation": 0 },
                                "east":  { "texture": "#side", "uv":[0,0,16,8], "cullface": "east", "rotation": 180 }
                            }
                        }
                    ]
                }
                """);
        generator.writeModelFile("extshape", "block/glazed_terracotta_slab_top", """
                {
                    "parent": "block/cube",
                    "textures": {
                        "particle": "#side"
                    },
                    "display": {
                        "firstperson_righthand": {
                            "rotation": [ 0, 135, 0 ],
                            "translation": [ 0, 0, 0 ],
                            "scale": [ 0.40, 0.40, 0.40 ]
                        }
                    },
                    "elements": [
                        {   "from": [ 0, 8, 0 ],
                            "to": [ 16, 16, 16 ],
                            "faces": {
                                "down":  { "texture": "#bottom", "cullface": "down" },
                                "up":    { "texture": "#top", "cullface": "up" },
                                "north": { "texture": "#side", "uv":[0,0,8,16], "cullface": "north", "rotation": 90 },
                                "south": { "texture": "#side", "uv":[8,0,16,16], "cullface": "south", "rotation": 270 },
                                "west":  { "texture": "#side", "uv":[0,0,16,8], "cullface": "west", "rotation": 0 },
                                "east":  { "texture": "#side", "uv":[0,8,16,16], "cullface": "east", "rotation": 180 }
                            }
                        }
                    ]
                }
                """);
    }

    @Override
    public String getTopBlockModelString() {
        return String.format("""
                {
                  "parent": "extshape:block/glazed_terracotta_slab_top",
                  "textures": {
                    "bottom": "%s",
                    "top": "%s",
                    "side": "%s"
                  }
                }
                """, this.getBottomTexture(), this.getTopTexture(), this.getSideTexture());
    }

    @Override
    public String getBlockModelString() {
        return String.format("""
                {
                  "parent": "extshape:block/glazed_terracotta_slab",
                  "textures": {
                    "bottom": "%s",
                    "top": "%s",
                    "side": "%s"
                  }
                }
                """, this.getBottomTexture(), this.getTopTexture(), this.getSideTexture());
    }

    @Override
    public String getBlockStatesString() {
        @NotNull Identifier baseIdentifier = Objects.requireNonNull(this.getBaseBlockIdentifier());
        return String.format("""
                {
                  "variants": {
                    "type=bottom,facing=south": {
                      "model": "%1$s"
                    },
                    "type=double,facing=south": {
                      "model": "%2$s"
                    },
                    "type=top,facing=south": {
                      "model": "%1$s_top"
                    },
                    "type=bottom,facing=west": {
                      "model": "%1$s",
                      "y": 90
                    },
                    "type=double,facing=west": {
                      "model": "%2$s",
                      "y": 90
                    },
                    "type=top,facing=west": {
                      "model": "%1$s_top",
                      "y": 90
                    },
                    "type=bottom,facing=north": {
                      "model": "%1$s",
                      "y": 180
                    },
                    "type=double,facing=north": {
                      "model": "%2$s",
                      "y": 180
                    },
                    "type=top,facing=north": {
                      "model": "%1$s_top",
                      "y": 180
                    },
                    "type=bottom,facing=east": {
                      "model": "%1$s",
                      "y": 270
                    },
                    "type=double,facing=east": {
                      "model": "%2$s",
                      "y": 270
                    },
                    "type=top,facing=east": {
                      "model": "%1$s_top",
                      "y": 270
                    }
                  }
                }""", this.getBlockModelIdentifier(), new Identifier(baseIdentifier.getNamespace(),
                "block/" + baseIdentifier.getPath()));
    }
}
