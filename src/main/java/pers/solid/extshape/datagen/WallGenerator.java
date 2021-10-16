package pers.solid.extshape.datagen;

import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import pers.solid.extshape.tag.ExtShapeBlockTag;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class WallGenerator extends AbstractBlockGenerator<WallBlock> {
    protected WallGenerator(Path path, @NotNull WallBlock block) {
        super(path, block);
    }

    public String getInventoryModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/wall_inventory",
                  "textures": {
                    "wall": "%s"
                  }
                }""", this.getBaseTexture());
    }

    public String getPostModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/template_wall_post",
                  "textures": {
                    "wall": "%s"
                  }
                }""", this.getBaseTexture());
    }

    public String getSideModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/template_wall_side",
                  "textures": {
                    "wall": "%s"
                  }
                }""", this.getBaseTexture());
    }

    public String getSideTallModelString() {
        return String.format("""
                {
                  "parent": "minecraft:block/template_wall_side_tall",
                  "textures": {
                    "wall": "%s"
                  }
                }""", this.getBaseTexture());
    }

    public Map<Identifier, String> getBlockModelCollection() {
         Map<Identifier,String> modelCollection = new LinkedHashMap<>();
        modelCollection.put(this.getBlockModelIdentifier("_inventory"), this.getInventoryModelString());
        modelCollection.put(this.getBlockModelIdentifier("_post"), this.getPostModelString());
        modelCollection.put(this.getBlockModelIdentifier("_side"), this.getSideModelString());
        modelCollection.put(this.getBlockModelIdentifier("_side_tall"), this.getSideTallModelString());
        return modelCollection;
    }

    @Override
    public String getBlockStatesString() {
        Identifier identifier = this.getIdentifier();
        return String.format("""
                {
                  "multipart": [
                    {
                      "when": {
                        "up": "true"
                      },
                      "apply": {
                        "model": "%1$s:block/%2$s_post"
                      }
                    },
                    {
                      "when": {
                        "north": "low"
                      },
                      "apply": {
                        "model": "%1$s:block/%2$s_side",
                        "uvlock": true
                      }
                    },
                    {
                      "when": {
                        "east": "low"
                      },
                      "apply": {
                        "model": "%1$s:block/%2$s_side",
                        "y": 90,
                        "uvlock": true
                      }
                    },
                    {
                      "when": {
                        "south": "low"
                      },
                      "apply": {
                        "model": "%1$s:block/%2$s_side",
                        "y": 180,
                        "uvlock": true
                      }
                    },
                    {
                      "when": {
                        "west": "low"
                      },
                      "apply": {
                        "model": "%1$s:block/%2$s_side",
                        "y": 270,
                        "uvlock": true
                      }
                    },
                    {
                      "when": {
                        "north": "tall"
                      },
                      "apply": {
                        "model": "%1$s:block/%2$s_side_tall",
                        "uvlock": true
                      }
                    },
                    {
                      "when": {
                        "east": "tall"
                      },
                      "apply": {
                        "model": "%1$s:block/%2$s_side_tall",
                        "y": 90,
                        "uvlock": true
                      }
                    },
                    {
                      "when": {
                        "south": "tall"
                      },
                      "apply": {
                        "model": "%1$s:block/%2$s_side_tall",
                        "y": 180,
                        "uvlock": true
                      }
                    },
                    {
                      "when": {
                        "west": "tall"
                      },
                      "apply": {
                        "model": "%1$s:block/%2$s_side_tall",
                        "y": 270,
                        "uvlock": true
                      }
                    }
                  ]
                }""", identifier.getNamespace(), identifier.getPath());
    }

    @Override
    public String getItemModelString() {
        return String.format("""
                {
                  "parent": "%s_inventory"
                }""", this.getBlockModelIdentifier().toString());
    }

    @Override
    public String getCraftingRecipeString() {
        return String.format("""
                {
                  "type": "minecraft:crafting_shaped",
                  "group": "%3$s",
                  "pattern": [
                    "###",
                    "###"
                  ],
                  "key": {
                    "#": {
                      "item": "%1$s"
                    }
                  },
                  "result": {
                    "item": "%2$s",
                    "count": 6
                  }
                }""", this.getBaseBlockIdentifier(), this.getIdentifier().toString(), this.getRecipeGroup());
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
        if (ExtShapeBlockTag.WOOLS.contains(baseBlock)) return "wool_wall";
        if (ExtShapeBlockTag.CONCRETES.contains(baseBlock)) return "concrete_wall";
        if (ExtShapeBlockTag.STAINED_TERRACOTTAS.contains(baseBlock)) return "stained_terracotta_wall";
        if (ExtShapeBlockTag.GLAZED_TERRACOTTAS.contains(baseBlock)) return "glazed_terracotta_wall";
        if (ExtShapeBlockTag.PLANKS.contains(baseBlock)) return "wooden_wall";
        return "";
    }
}
